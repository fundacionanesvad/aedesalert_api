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
 ******************************************************************************/
package com.gruposca.sapev.api.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.gruposca.sapev.api.connectors.dao.model.Febriles;
import com.gruposca.sapev.api.modelview.Febrile;
import com.gruposca.sapev.api.modelview.FebrileList;
import com.gruposca.sapev.api.modelview.ParamFilterFebriles;
import com.gruposca.sapev.api.modelview.RestErrorImpl;


@Path("/febriles")
public class FebrileWebService extends AbsWebService{
	
	@PUT
	@Produces(RESPONSE_MEDIA_TYPE)	
	public Response getFebrileList(ParamFilterFebriles paramFilterFebriles) {
		Response returnedValue = null;
		try {			
			if(userPermission("POST","/febriles")){
				FebrileList febrileList = getConnector().getFebrileService().getFebrileList(getSession(), paramFilterFebriles);
                
				if (febrileList != null) {
                	returnedValue = Response.ok(febrileList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_FEBRILE_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}	
	
	@GET
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getFebrile(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/febriles")){
								
				Febrile febrile = getConnector().getFebrileService().getFebrile(id);		

				if(febrile!=null){
					returnedValue = Response.ok(febrile).build();
				}else{ 
					returnedValue = Response.status(Response.Status.NOT_FOUND).build(); 
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_FEBRILE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response createFebrile(Febrile febrile) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/febriles")){
				Febriles newFebrile = getConnector().getFebrileService().createFebrile(febrile);
				if(newFebrile != null ){
					returnedValue = Response.status(Response.Status.CREATED).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CREATE_FEBRILE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@PUT
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updateFebrile(@PathParam("id")  Integer id, Febrile febrile) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/febriles")){			
				Febriles updateFebrile = getConnector().getFebrileService().updateFebrile(id, febrile);
				if(updateFebrile != null ){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_FEBRILE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@DELETE
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response deleteFebrile(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("DELETE","/febriles")){				
				getConnector().getFebrileService().deleteFebrile(id);		
				returnedValue = Response.status(Response.Status.OK).build();
				
			}else{				
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_DELETE_FEBRILE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
}
