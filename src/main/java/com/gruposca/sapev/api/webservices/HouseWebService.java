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
import com.gruposca.sapev.api.connectors.dao.model.Houses;
import com.gruposca.sapev.api.modelview.House;
import com.gruposca.sapev.api.modelview.HouseAreaImpl;
import com.gruposca.sapev.api.modelview.HouseImpl;
import com.gruposca.sapev.api.modelview.HousesList;
import com.gruposca.sapev.api.modelview.ParamFilterHouses;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Visit;

@Path("/houses")
public class HouseWebService extends AbsWebService{

	@PUT
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getHousesList(ParamFilterHouses paramFilterHouses) {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/houses")){
				
				HousesList houseList = getConnector().getHouseService().getHouseList(paramFilterHouses, getSession());				
                if (houseList != null) {
                	returnedValue = Response.ok(houseList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_HOUSES_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	
	@GET
	@Path("/{uuid}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getHouse(@PathParam("uuid")  String uuid) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/houses")){
						
				House house = getConnector().getHouseService().getHouse(uuid);
				if(house!=null){
					returnedValue = Response.ok(house).build();
				}else{ 
					returnedValue = Response.status(Response.Status.NOT_FOUND).build(); 
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_HOUSE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	

	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response createHouse(HouseImpl house) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/houses")){						
					
				Houses newHouse = getConnector().getHouseService().createHouse(house);
				if(newHouse != null ){
					returnedValue = Response.status(Response.Status.CREATED).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}			
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CREATE_HOUSE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	@PUT
	@Path("/{uuid}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updateHouse(@PathParam("uuid")  String uuid, HouseImpl house) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/houses")){	
				Houses updateHouse = getConnector().getHouseService().updateHouse(uuid, house);
				if(updateHouse != null ){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}			
			
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_HOUSE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@DELETE
	@Path("/{uuid}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response deleteHouse(@PathParam("uuid")  String uuid) {
		Response returnedValue = null;
		try {

			if(userPermission("DELETE","/houses")){				
				Boolean deleted = getConnector().getHouseService().deleteHouse(uuid);
				if(deleted){
					returnedValue = Response.status(Response.Status.OK).build();
				}else{
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();
				}				
			}else{				
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_DELETE_HOUSE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	


	@GET
	@Path("/{uuid}/visits")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getVisitList(@PathParam("uuid")  String uuid) {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/houses")){
				
				List<Visit> visitList = getConnector().getHouseService().getVisitsList(getSession(), uuid);
                if (visitList != null) {
                	returnedValue = Response.ok(visitList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_HOUSE_VISITS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	@GET
	@Path("/update")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response updatecodesHouse() {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/houses")){
				
				boolean update = getConnector().getHouseService().updatecodesHouse();
                if (update) {
					returnedValue = Response.status(Response.Status.OK).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_CODES +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@PUT
	@Path("/{uuid}/updateaddress")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response updateAddress(@PathParam("uuid")  String uuid, HouseAreaImpl house) {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/houses")){
				
				boolean update = getConnector().getHouseService().updateAddresses(uuid, house);
                if (update) {
					returnedValue = Response.status(Response.Status.OK).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_ADDRESS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	
}
