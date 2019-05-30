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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.gruposca.sapev.api.connectors.dao.model.TrapLocations;
import com.gruposca.sapev.api.connectors.dao.model.Traps;
import com.gruposca.sapev.api.helper.FilesHelper;
import com.gruposca.sapev.api.modelview.FilterTrapData;
import com.gruposca.sapev.api.modelview.FilterTrapExcel;
import com.gruposca.sapev.api.modelview.ParamFilterTraps;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.TrapDataModel;
import com.gruposca.sapev.api.modelview.TrapDataModelList;
import com.gruposca.sapev.api.modelview.TrapLocationModel;
import com.gruposca.sapev.api.modelview.TrapLocationsModelList;
import com.gruposca.sapev.api.modelview.TrapModel;
import com.gruposca.sapev.api.modelview.TrapsList;
import com.sun.jersey.core.util.Base64;

@Path("/traps")
public class TrapWebService extends AbsWebService{
	
	@PUT
	@Produces(RESPONSE_MEDIA_TYPE)	
	public Response getTrapList(ParamFilterTraps paramFilterTraps) {
		Response returnedValue = null;
		try {
			
			if(userPermission("PUT","/traps")){
				TrapsList trapsList = getConnector().getTrapService().getListTraps(getSession(), paramFilterTraps);
                if (trapsList != null) {
                	returnedValue = Response.ok(trapsList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_TRAPS_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	
	@GET
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getTrap(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/traps")){
				
				TrapModel trapModel = getConnector().getTrapService().getTrap(id);								

				if(trapModel!=null){
					returnedValue = Response.ok(trapModel).build();
				}else{ 
					returnedValue = Response.status(Response.Status.NOT_FOUND).build(); 
				}	
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_TRAP +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	
	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response createTrap(TrapModel trapModel) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/traps")){
				Traps newTrap = getConnector().getTrapService().createTrap(trapModel);
				if(newTrap != null ){
					returnedValue = Response.ok(newTrap.getId()).build();					
				}else{
					returnedValue = Response.ok(0).build();					
				}
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CREATE_TRAP +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@PUT
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updateTrap(@PathParam("id")  Integer id, TrapModel trapModel) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/traps")){			
				Traps updateTrap = getConnector().getTrapService().updateTrap(id, trapModel);
				if(updateTrap != null ){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_TRAP +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	@GET
	@Path("/{id}/locations")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response getTrapLocationsList(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/traps")){
				List<TrapLocationsModelList> trapLocationList = getConnector().getTrapService().getListTrapLocation(id);
                if (trapLocationList != null) {
                	returnedValue = Response.ok(trapLocationList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_TRAPSLOCATION_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}	
	
	@GET
	@Path("/locations/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response getTrapLocations(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/traps")){
				TrapLocationModel trapLocationModel = getConnector().getTrapService().getTrapLocation(id);							

				if(trapLocationModel!=null){
					returnedValue = Response.ok(trapLocationModel).build();
				}else{ 
					returnedValue = Response.status(Response.Status.NOT_FOUND).build(); 
				}	
				
			}else {				
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();				
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_TRAPLOCATION +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	@POST
	@Path("/{id}/locations")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response createTrapLocation(@PathParam("id")  Integer id, TrapLocationModel trapLocationModel) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/traps")){
				TrapLocations newTrapLocation = getConnector().getTrapService().createTrapLocations(id,trapLocationModel);
				if(newTrapLocation != null ){
					returnedValue = Response.status(Response.Status.CREATED).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CREATE_TRAPLOCATION +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@PUT
	@Path("/locations/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updateTrap(@PathParam("id")  Integer id, TrapLocationModel trapLocationModel) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/traps")){			
				TrapLocations updateTrapLocation = getConnector().getTrapService().updateTrapLocations(id, trapLocationModel);
				if(updateTrapLocation != null ){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_TRAPLOCATION +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@PUT
	@Path("/data")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response getTrapDataList(FilterTrapData filterTrapData) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/traps")){		
				List<TrapDataModelList> list = getConnector().getTrapService().getTrapDataList(filterTrapData);
				if (list != null) {
                	returnedValue = Response.ok(list).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_TRAPDATA_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@POST
	@Path("/data")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response saveTrapData(TrapDataModel trapDataModel) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/traps")){
				
				int saveResult = getConnector().getTrapService().saveTrapData(trapDataModel);
				if(saveResult == 1 ){
					returnedValue = Response.status(Response.Status.CREATED).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_SAVE_TRAPDATA +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@POST
	@Path("/report")
	@Produces(RESPONSE_XLS_TYPE)
	public Response reportTraps(FilterTrapExcel filterTrapExcel) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/traps/report")){		
				String report = getConnector().getTrapService().reportTrap(filterTrapExcel);		
				File file = new File(report);	
				byte[] bytes = FilesHelper.loadFile(file);
				byte[] encoded = Base64.encode(bytes);
				String encodedString = new String(encoded);
				ResponseBuilder response = Response.ok((Object) encodedString);				
				response.header("Content-Disposition","attachment; filename=report.xls");
		        return response.build();
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_REPORT_TRAP +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
}
