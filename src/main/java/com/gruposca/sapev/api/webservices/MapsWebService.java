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

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.gruposca.sapev.api.modelview.MapAedic;
import com.gruposca.sapev.api.modelview.MapDates;
import com.gruposca.sapev.api.modelview.MapFocus;
import com.gruposca.sapev.api.modelview.RestErrorImpl;

@Path("/maps")
public class MapsWebService extends AbsWebService{
	
	@POST 
	@Path("/{id}/aedico")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response getMapAedic(@PathParam("id")  Integer id, MapDates mapDates) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/maps")){
				if(userAreaPermission(id)){
					MapAedic mapAedic = getConnector().getAreaService().getMapAedico(id, mapDates, getSession());								

					if(mapAedic!=null){
						returnedValue = Response.ok(mapAedic).build();
					}else{ 
						returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); 
					}					
				}else{
				    logger.error(RestErrorImpl.USER_AREA_NOT_PERMISSION);
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();				
				}					
				
			}else{
			    logger.error(RestErrorImpl.USER_MODULE_NOT_PERMISSION);
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_MAP_AEDIC +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;		
	}
	
	
	@POST
	@Path("/{id}/focus")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response getMapFocus(@PathParam("id")  Integer id, MapDates mapDates) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/maps")){
				if(userAreaPermission(id)){
					MapFocus mapFocus = getConnector().getAreaService().getMapFocus(getSession(), id, mapDates);
					if(mapFocus!=null){
						returnedValue = Response.ok(mapFocus).build();
					}else{ 
						returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); 
					}
				}else{
				    logger.error(RestErrorImpl.USER_AREA_NOT_PERMISSION);
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();				
				}				
			}else{
			    logger.error(RestErrorImpl.USER_MODULE_NOT_PERMISSION);
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_MAP_FOCUS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;		
	}
}
