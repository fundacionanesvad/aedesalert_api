/*******************************************************************************
 * Aedes Alert, Support to collect data to combat dengue
 * Copyright (C) 2017 Fundación Anesvad
 *   
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *   
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *   
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.gruposca.sapev.api.webservices;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.modelview.Plan;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.SyncData;

@Path("/sync")
public class SyncWebService extends AbsWebService{

	
	static BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
	static Thread running;
	
	@GET
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getPList() {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/sync")){
				
				List<Plan> plansList = getConnector().getSyncService().getPlansList(getSession());				
                if (plansList != null ) {
                	returnedValue = Response.ok(plansList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_SYNC_PLAN_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	@GET
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getPlan(@PathParam("id")  Integer id ) {
		Response returnedValue = null;
		try {			
			if(userPermission("GET","/sync")){				
				
			    logger.error("TIME INI GET PLAN:" + Calendar.getInstance().getTimeInMillis());

				
				Plan plan = getConnector().getSyncService().getPlan(getSession(), id);	
				if(plan!=null){		
				    logger.error("TIME TENGO EL PLAN:" + Calendar.getInstance().getTimeInMillis());

					getConnector().getSyncService().updatePlanState(id, ConfigurationHelper.getStatePlanInProgress());
					
				    logger.error("PLAN ACTUALIZADO:" + Calendar.getInstance().getTimeInMillis());

					returnedValue = Response.ok(plan).build();
				    logger.error("BUILD RESPONSE:" + Calendar.getInstance().getTimeInMillis());

				}else{ 
					returnedValue = Response.status(Response.Status.NOT_FOUND).build(); 
				}	
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_SYNC_PLAN +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}	
	    logger.error("DEVUELVO EL PLAN:" + Calendar.getInstance().getTimeInMillis());

		return returnedValue;
	}
	
	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response syncHouses(final SyncData syncData) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/sync")){		
				
				try {
					
					if(getConnector().getPlanService().checkPlan(syncData.getPlanId())) {
						
						boolean updatePlan = getConnector().getPlanService().updateSyncFilePlan(syncData);
						if(updatePlan) {
							returnedValue = Response.status(Response.Status.CREATED).build();	
				
							if(running == null) {   	
								System.out.println("Nuevo Thread , se van a añdir los planes en estado 7005 a la cola de ejcución");
		                        loadQueue();		                        
		                        newThread();		    	

							}else {	
						    	queue.offer(syncData.getPlanId());	
		                        System.err.println("Thread en proceso, se ha añadido el Plan "+syncData.getPlanId()+" a la cola de ejecucíón");
						    }
								
						}else {
	                        System.out.println("ERROR PLAN "+syncData.getPlanId()+": No se ha podido actualizar el estado del plan o guardar el file de datos");
							returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();	
						}
							
					}else {		
						
                        System.out.println("El plan "+syncData.getPlanId()+" ya está Terminado o Pendiente de Procesar ");
                        if(running == null) {						    	
	                        System.out.println("El Thread está parado, se van a añdir los planes en estado 7005 a la cola de ejecución");	                        
	                        loadQueue();
	                        newThread();  	
						}                        
 
                        returnedValue = Response.status(Response.Status.CREATED).build();	

					}					
					return returnedValue;
					
				}catch (Exception e) {
                    System.out.println("catch 1::");

					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
            System.out.println("catch 2::");

		    logger.error(RestErrorImpl.SERVICE_CREATE_HOUSE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		
		return returnedValue;	
	}
	
	
	private void loadQueue() throws IOException {
		
		List<Integer> listPending = getConnector().getPlanService().getListPendingPlans();			
        for (int i = 0; i < listPending.size(); i++) {
	        queue.offer(listPending.get(i));
            System.out.println("Plan "+listPending.get(i)+" añadido a la cola");

	    }
   	}
	
	private void newThread() {		
		running = new Thread(new Runnable() {
            @Override
            public void run() {
            	while (queue.peek() != null) {
                    try {
                    	int planId = queue.take();
						getConnector().getHouseService().syncHouses(getSession(), planId);
                    
                    } catch (InterruptedException e) {
                        System.err.println("Error occurred:" + e);
                    
                    } catch (IOException e) {
                        logger.error("ERROR sincronizando las visitas" + e.toString());	
						e.printStackTrace();
					}
                }            	
            	running = null;
            }
        });
		running.start();
	}
	
}
