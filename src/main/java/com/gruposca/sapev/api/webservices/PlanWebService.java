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

import java.io.File;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.gruposca.sapev.api.connectors.dao.model.Plans;
import com.gruposca.sapev.api.helper.FilesHelper;
import com.gruposca.sapev.api.modelview.ParamFilterPlanVisits;
import com.gruposca.sapev.api.modelview.Plan;
import com.gruposca.sapev.api.modelview.PlanDetail;
import com.gruposca.sapev.api.modelview.PlanImpl;
import com.gruposca.sapev.api.modelview.PlanSummaryData;
import com.gruposca.sapev.api.modelview.PlanVisit;
import com.gruposca.sapev.api.modelview.PlanVisitList;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.SyncData;
import com.gruposca.sapev.api.modelview.VisitPlan;
import com.sun.jersey.core.util.Base64;

@Path("/plans")
public class PlanWebService extends AbsWebService{
	
	@GET
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getPlan(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/plans") || userPermission("GET","/plans/user")){
								
				Plan plan = getConnector().getPlanService().getPlan(id);		

				if(plan!=null){
					returnedValue = Response.ok(plan).build();
				}else{ 
					returnedValue = Response.status(Response.Status.NOT_FOUND).build(); 
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_PLAN +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response createPlan(PlanImpl plan) {
		Response returnedValue = null;
		try {
			//Object to JSON in String
			//ObjectMapper mapper = new ObjectMapper();
			//String jsonInString = mapper.writeValueAsString(plan);
		
			if(userPermission("POST","/plans")){				
				Plans newPlan = getConnector().getPlanService().createPlan(plan);
				if(newPlan != null ){
					returnedValue = Response.ok(newPlan.getId()).build();
				}else{
					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CREATE_PLAN +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	@PUT
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updatePlan(@PathParam("id")  Integer id, PlanImpl plan) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/plans")){
				Plans updatePlan = getConnector().getPlanService().updatePlan(id, plan);
				if(updatePlan != null ){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}			
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_PLAN +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	
	@DELETE
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response deletePlan(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {

			if(userPermission("DELETE","/plans")){				
				if(getConnector().getPlanService().deletePlan(id)){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{					
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();
				}			
			}else{				
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_DELETE_PLAN +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@GET
	@Path("/{id}/visits")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getVisitList(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/plans")){
				
				List<VisitPlan> visitList = getConnector().getPlanService().getListVisits(getSession(), id);
                if (visitList != null) {
                	returnedValue = Response.ok(visitList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }								
						
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_PLAN_VISITS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@PUT
	@Path("/importdetail")
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response importDetailPlan(SyncData syncData) {
		Response returnedValue = null;
		try {
			
			if(userPermission("PUT","/plans") || userPermission("PUT","/plans/user")){
				
				getConnector().getPlanService().updateSyncFilePlan(syncData);
				Boolean result = getConnector().getHouseService().syncHouses(getSession(), syncData.getPlanId());
				if(result){
					returnedValue = Response.status(Response.Status.CREATED).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}			
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_IMPORT_DFETAIL_PLAN +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@PUT
	@Path("/{id}/importsummary")
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response importSummaryPlan(@PathParam("id")  Integer id, List<PlanSummaryData> listPlanSummaryData) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/plans") || userPermission("PUT","/plans/user")){
				Boolean result = getConnector().getPlanService().importSummaryPlan(id, listPlanSummaryData);		
				if(result){
					returnedValue = Response.status(Response.Status.CREATED).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}			
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_IMPORT_SUMMARY_PLAN +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@GET
	@Path("/{id}/planDetail")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getPlanDetail(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/plans") || userPermission("GET","/plans/user")){
				
				PlanDetail planDetail = getConnector().getPlanService().getPlanDetail(id, getSession());
                if (planDetail != null) {
                	returnedValue = Response.ok(planDetail).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }								
						
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_PLAN_DETAIL +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@GET
	@Path("/user")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getPlans() {
		Response returnedValue = null;
		try {			
			if(userPermission("GET","/plans/user")){				
				List<Plan> planList = getConnector().getPlanService().getListPlans(getSession());
                if (planList != null) {
                	returnedValue = Response.ok(planList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }								
						
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_LIST_PLAN +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	

	@PUT
	@Path("/{id}/sendInspectorReport")
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response sendInspectorReport(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			
			if(userPermission("SEND","/plans")){
				Boolean result = getConnector().getPlanService().sendInspectorReport(id);		
				if(result){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}			
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_SEND_INSPECTOR_REPORT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	@GET
	@Path("/{id}/getInspectorReport")
	@Produces(RESPONSE_XLS_TYPE)
	public Response getInspectorReport(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/plans") || userPermission("GET","/plans/user")){				
				
				String xlsx = getConnector().getPlanService().getInspectorReport(id);		
				File file = new File(xlsx);	
				byte[] bytes = FilesHelper.loadFile(file);
				byte[] encoded = Base64.encode(bytes);
				String encodedString = new String(encoded);
				ResponseBuilder response = Response.ok((Object) encodedString);				
				response.header("Content-Disposition","attachment; filename=inspectorReport.xlsx");
		        return response.build();
		        
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_INSPECTOR_REPORT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	@PUT
	@Path("/{id}/close")
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response closePlan(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {			
			if(userPermission("PUT","/plans")){				
				if(getConnector().getPlanService().closePlan(id)){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{					
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();
				}			
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CLOSE_PLAN +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@POST
	@Path("/{id}/visits")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response createVisitPlan(@PathParam("id")  Integer id, PlanVisit planVisit) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/plans")){
				
				boolean result = getConnector().getPlanService().createVisitPlan(id, planVisit);
				if(result){
					returnedValue = Response.status(Response.Status.CREATED).build();	
				}else{					
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}								
						
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CREATE_VISIT_PLAN +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	@GET
	@Path("/{id}/visitlist")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getListVisitsPlan(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {			
			if(userPermission("GET","/plans/user")){
				List<PlanVisit> list = getConnector().getPlanService().getListVisitsPlan(getSession(), id);
                if (list != null) {
                	returnedValue = Response.ok(list).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}	
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GETLIST_VISIT_PLAN +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@PUT
	@Path("/{id}/visitlist")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)
	public Response getListPlanVisit(@PathParam("id")  Integer id, ParamFilterPlanVisits paramFilterPlanVisits) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/plans")){
				PlanVisitList planVisitList = getConnector().getPlanService().getListPlanVisit(id, paramFilterPlanVisits);
				if (planVisitList != null) {
                	returnedValue = Response.ok(planVisitList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}	
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GETLIST_VISIT_PLAN +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@PUT
	@Path("/{id}/visits/{uuid}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response saveVisitPlan(@PathParam("id")  Integer id, @PathParam("uuid")  String uuid,PlanVisit planVisit) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/plans")){
				
				boolean result = getConnector().getPlanService().saveVisitPlan(id, uuid, planVisit);
				if(result){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{					
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}								
						
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_SAVE_VISIT_PLAN +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
}
