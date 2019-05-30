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

import com.gruposca.sapev.api.connectors.dao.model.Schedules;
import com.gruposca.sapev.api.modelview.Area;
import com.gruposca.sapev.api.modelview.InspectionSchedule;
import com.gruposca.sapev.api.modelview.ParamFilterSchedule;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Schedule;
import com.gruposca.sapev.api.modelview.ScheduleList;

@Path("/schedules")
public class ScheduleWebService extends AbsWebService{

	@PUT
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getScheduleList(ParamFilterSchedule paramFilterSchedule) {
		Response returnedValue = null;
		try {			
			if(userPermission("PUT","/schedules")){
				ScheduleList scheduleList = getConnector().getScheduleService().getScheduleList(getSession(), paramFilterSchedule);
                
				if (scheduleList != null) {
                	returnedValue = Response.ok(scheduleList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_SCHEDULE_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@GET
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getSchedule(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/schedules")){								
				Schedule schedule = getConnector().getScheduleService().getSchedule(id);
				if(schedule!=null){
					returnedValue = Response.ok(schedule).build();
				}else{ 
					returnedValue = Response.status(Response.Status.NOT_FOUND).build(); 
				}				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_SCHEDULE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	@GET
	@Path("/{id}/inspections")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getScheduleInspections(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/schedules")){								
				List<InspectionSchedule> list = getConnector().getScheduleService().getScheduleInspections(id);
				if (list != null) {
                	returnedValue = Response.ok(list).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }			
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_SCHEDULE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response createSchedule(Schedule schedule) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/schedules")){
				Schedules newSchedule = getConnector().getScheduleService().createSchedule(schedule);				
				if(newSchedule != null ){
					returnedValue = Response.ok(newSchedule.getId()).build();
				}else{
					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CREATE_SCHEDULE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	@PUT
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updateSchedule(@PathParam("id")  Integer id, Schedule schedule) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/schedules")){			
				Schedules updateSchedule = getConnector().getScheduleService().updateSchedule(id, schedule);
				if(updateSchedule != null ){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_SCHEDULE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	
	@DELETE
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response deleteSchedule(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("DELETE","/schedules")){				
				getConnector().getScheduleService().deleteSchedule(id);;		
				returnedValue = Response.status(Response.Status.OK).build();
				
			}else{				
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_DELETE_SCHEDULE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	


	@GET
	@Path("/{id}/areachilds")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getChilds(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/schedules")){				
				Area area = getConnector().getScheduleService().getAreaChilds(id);					
	            if (area != null) {
	            	returnedValue = Response.ok(area).build();
	            } else {
	                returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	            }
            
			}else{
			    logger.error(RestErrorImpl.USER_MODULE_NOT_PERMISSION);
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_AREASCHILDS_SCHEDULE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}		

	
	
	
	
	
}
