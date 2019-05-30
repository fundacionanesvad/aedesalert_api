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
import com.gruposca.sapev.api.connectors.dao.model.Larvicides;
import com.gruposca.sapev.api.modelview.Larvicide;
import com.gruposca.sapev.api.modelview.LarvicideList;
import com.gruposca.sapev.api.modelview.RestErrorImpl;


@Path("/larvicides")
public class LarvicideWebService extends AbsWebService{
	
	
	@GET
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getLarvicideList() {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/larvicides")){
				
				List<LarvicideList> larvicideList = getConnector().getLarvicideService().getLarvicideList(getSession());
                if (larvicideList != null) {
                	returnedValue = Response.ok(larvicideList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_LARVICIDE_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}	
	
	@GET
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getLarvicide(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/larvicides")){
								
				Larvicide larvicide = getConnector().getLarvicideService().getLarvicide(id);		

				if(larvicide!=null){
					returnedValue = Response.ok(larvicide).build();
				}else{ 
					returnedValue = Response.status(Response.Status.NOT_FOUND).build(); 
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_LARVICIDE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response createLarvicide(Larvicide larvicide) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/larvicides")){
				Larvicides newLarvicide = getConnector().getLarvicideService().createLarvicide(larvicide);
				if(newLarvicide != null ){
					returnedValue = Response.status(Response.Status.CREATED).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CREATE_LARVICIDE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@PUT
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updateLarvicide(@PathParam("id")  Integer id, Larvicide larvicide) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/larvicides")){			
				Larvicides updateLarvicide = getConnector().getLarvicideService().updateLarvicide(id, larvicide);
				if(updateLarvicide != null ){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_LARVICIDE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@DELETE
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response deleteLarvicide(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("DELETE","/larvicides")){				
				if(getConnector().getLarvicideService().deleteLarvicide(id)) {
					returnedValue = Response.status(Response.Status.OK).build();
				}else {
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();
				}
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_DELETE_LARVICIDE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
}
