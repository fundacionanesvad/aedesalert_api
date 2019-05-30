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

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.gruposca.sapev.api.modelview.AlertImpl;
import com.gruposca.sapev.api.modelview.RestErrorImpl;

@Path("/alerts")
public class AlertsWebService extends AbsWebService{
	
	@GET
	@Path("/{closed}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getAlertsList(@PathParam("closed")  Boolean closed) {		
		Response returnedValue = null;
		try {
			if(userPermission("GET","/alerts")){
				List<AlertImpl> alertList = getConnector().getAlertService().getAlertList(getSession(), closed);				
                if (alertList != null ) {
                	returnedValue = Response.ok(alertList).build();
                } else {
                    returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.ALERT_GET_ALERTS_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}	
	
	@PUT
	@Path("/{id}/closed")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response closeAlert(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/alerts")){				
				
				Boolean closed =  getConnector().getAlertService().closeAlert(id);
				
				if (closed ) {
                    returnedValue = Response.status(Response.Status.OK).build();
                } else {
                    returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }		
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.ALERT_CLOSE_ALERT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	

}
