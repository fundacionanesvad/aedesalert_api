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

import com.gruposca.sapev.api.connectors.dao.model.TableHeaders;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Table;
import com.gruposca.sapev.api.modelview.TableElementListImpl;
import com.gruposca.sapev.api.modelview.TableImpl;
import com.gruposca.sapev.api.modelview.TableListImpl;

@Path("/tables")
public class TableWebService extends AbsWebService{
	
	@GET
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getTableList() {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/tables")){
				
				List<TableListImpl> tableList = getConnector().getTableService().getTableList(getSession());
                if (tableList != null) {
                	returnedValue = Response.ok(tableList).build();
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
	
	@GET
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getTable(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/tables")){
				
				Table table = getConnector().getTableService().getTable(getSession(), id);								

				if(table!=null){
					returnedValue = Response.ok(table).build();
				}else{ 
					returnedValue = Response.status(Response.Status.NOT_FOUND).build(); 
				}	
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_TABLE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response createTable(TableImpl table) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/tables")){						
				
				TableHeaders newTable = getConnector().getTableService().createTable(table);
				if(newTable != null ){
					returnedValue = Response.ok(newTable.getId()).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}		
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CREATE_TABLE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@PUT
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updateArea(@PathParam("id")  Integer id, TableImpl table) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/tables")){
			
				TableHeaders updateTable = getConnector().getTableService().updateTable(id, table);
				if(updateTable != null ){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}							
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_TABLE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response deleteTable(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {

			if(userPermission("DELETE","/tables")){
				
				if(getConnector().getTableService().deleteTable(id)){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{					
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();
				}			
				
			}else{				
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_DELETE_TABLE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	@GET
	@Path("/{id}/elements")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getTableElements(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/tables")){				
				
				List<TableElementListImpl> listTableElements = getConnector().getTableService().getTableElementList(getSession(), id);
				
				if (listTableElements != null ) {
                	returnedValue = Response.ok(listTableElements).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }		
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_TABLE_ELEMENTS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
}
