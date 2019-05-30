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
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.modelview.Element;
import com.gruposca.sapev.api.modelview.ElementImpl;
import com.gruposca.sapev.api.modelview.RestErrorImpl;

@Path("/elements")
public class ElementWebService extends AbsWebService{
	
	@GET
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getElement(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/elements")){				
				
				Element element = getConnector().getElementService().getElement(getSession(), id);
			
	            if (element != null) {
	            	returnedValue = Response.ok(element).build();
	            } else {
	                returnedValue = Response.status(Response.Status.NOT_FOUND).build();
	            }				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_ELEMENT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	
	@GET
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getElementsList() {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/elements")){
				
				List<Element> elementList = getConnector().getElementService().getElementList(getSession());
                if (elementList != null) {
                	returnedValue = Response.ok(elementList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_TABLE_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}	
	
	
	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response createElement(ElementImpl element) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/elements")){						
				
				TableElements newElement = getConnector().getElementService().createElement(getSession(), element);
				if(newElement != null ){
					returnedValue = Response.status(Response.Status.CREATED).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}		
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CREATE_ELEMENT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@PUT
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updateArea(@PathParam("id")  Integer id, ElementImpl element) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/elements")){
			
				TableElements updateElement = getConnector().getElementService().updateElement(getSession(), id, element);
				if(updateElement != null ){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}							
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_ELEMENT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response deleteUser(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {

			if(userPermission("DELETE","/elements")){
				
				if( getConnector().getElementService().deleteElement(getSession(), id)){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{					
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();
				}			
				
			}else{				
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_DELETE_ELEMENT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	

	@GET
	@Path("/{id}/up")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response upElement(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/elements")){				
				
				Boolean upRequest =  getConnector().getElementService().upElement(id);
				
				if (upRequest ) {
                    returnedValue = Response.status(Response.Status.OK).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }		
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UP_ELEMENT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@GET
	@Path("/{id}/down")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response downElement(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/elements")){				
				
				Boolean downRequest =  getConnector().getElementService().downElement(id);
				
				if (downRequest ) {
                    returnedValue = Response.status(Response.Status.OK).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }		
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_DOWN_ELEMENT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
}
