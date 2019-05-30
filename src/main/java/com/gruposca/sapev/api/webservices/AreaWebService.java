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

import java.util.ArrayList;
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
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.modelview.Area;
import com.gruposca.sapev.api.modelview.AreaChild;
import com.gruposca.sapev.api.modelview.AreaCreateUpdateImpl;
import com.gruposca.sapev.api.modelview.AreaTree;
import com.gruposca.sapev.api.modelview.FocusHouse;
import com.gruposca.sapev.api.modelview.House;
import com.gruposca.sapev.api.modelview.Inspection;
import com.gruposca.sapev.api.modelview.ReportArea;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.ScheduleArea;
import com.gruposca.sapev.api.modelview.StatsEessAreas;
import com.gruposca.sapev.api.modelview.StatsMrAreas;
import com.gruposca.sapev.api.modelview.StatsMrInspections;

@Path("/areas")
public class AreaWebService  extends AbsWebService{
	
	@GET
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getArea(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/areas") || userPermission("GET","/plans/user")){
				if(userAreaPermission(id)){
					Area area = getConnector().getAreaService().getArea(id);					
					if(area!=null){
						returnedValue = Response.ok(area).build();
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
		    logger.error(RestErrorImpl.SERVICE_GET_AREA +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response createArea(AreaCreateUpdateImpl area) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/areas")){
				if(userAreaPermission(area.getParentId())){
					Areas newArea = getConnector().getAreaService().createArea(area);
					if(newArea != null ){
						returnedValue = Response.status(Response.Status.CREATED).build();	
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
		    logger.error(RestErrorImpl.SERVICE_CREATE_AREA +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@PUT
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updateArea(@PathParam("id")  Integer id, AreaCreateUpdateImpl area) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/areas")){
				if(userAreaPermission(id)){
					Areas updateArea = getConnector().getAreaService().updateArea(id, area);
					if(updateArea != null ){
						returnedValue = Response.status(Response.Status.OK).build();	
					}else{
						returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
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
		    logger.error(RestErrorImpl.SERVICE_UPDATE_AREA +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@DELETE
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response deleteArea(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("DELETE","/areas")){
				if(userAreaPermission(id)){
					if(getConnector().getAreaService().deleteArea(id)){
						returnedValue = Response.status(Response.Status.OK).build();	
					}else{					
						returnedValue = Response.status(Response.Status.FORBIDDEN).build();
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
		    logger.error(RestErrorImpl.SERVICE_DELETE_AREA +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@GET
	@Path("/{id}/childs")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getChilds(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/areas")){				
				if(userAreaPermission(id)){
					Area area = getConnector().getAreaService().getChilds(id);					
		            if (area != null) {
		            	returnedValue = Response.ok(area).build();
		            } else {
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
		    logger.error(RestErrorImpl.SERVICE_GET_CHILDS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}		

	@GET
	@Path("/{id}/parents")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getParents(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/areas")){				
				if(userAreaPermission(id)){
					List<Area> listArea = getConnector().getAreaService().getParents(getSession(), id);
					
					if (listArea != null ) {
	                	returnedValue = Response.ok(listArea).build();
	                } else {
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
		    logger.error(RestErrorImpl.SERVICE_GET_PARENTS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}		
	
	@GET
	@Path("/{id}/houses")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getHouses(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/areas") || userPermission("GET","/plans/user")){					
				if(userAreaPermission(id)){
					List<House> listHouse = getConnector().getAreaService().getHousesArea(id);
					
					if (listHouse != null ) {
	                	returnedValue = Response.ok(listHouse).build();
	                } else {
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
		    logger.error(RestErrorImpl.SERVICE_GET_HOUSES +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}		
	
	@GET
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getListArea() {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/areas")){
				
				List<Area> areaList = getConnector().getAreaService().getListArea();
				if (areaList != null ) {
                	returnedValue = Response.ok(areaList).build();
                } else {
                    returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_LISTAREA +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return returnedValue;
	}
	
	@GET
	@Path("/{id}/inspections")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getInspections(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/areas")){				
				if(userAreaPermission(id)){
					List<Inspection> inspectionsAreas = getConnector().getAreaService().getInspectionsArea(getSession(), id);					
		            if (inspectionsAreas != null) {
		            	returnedValue = Response.ok(inspectionsAreas).build();
		            } else {
		                returnedValue = Response.status(Response.Status.NOT_FOUND).build();
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
		    logger.error(RestErrorImpl.SERVICE_GET_INSPECTIONS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}		

	
	@GET
	@Path("/{id}/reports")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getReports(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/areas")){				
				if(userAreaPermission(id)){
					List<ReportArea> listReport = getConnector().getAreaService().getReportsArea(id);
					
					if (listReport != null ) {
	                	returnedValue = Response.ok(listReport).build();
	                } else {
	                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
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
		    logger.error(RestErrorImpl.SERVICE_GET_REPORTS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@GET
	@Path("/{id}/sectors")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getSectors(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		List<AreaChild> list = new ArrayList<AreaChild>();
		try {
			if(userPermission("GET","/areas")){				
				if(userAreaPermission(id)){
					list = getConnector().getAreaService().getSectors(id);
	                returnedValue = Response.ok(list).build();
	                	
	            }else{
				    logger.error(RestErrorImpl.USER_AREA_NOT_PERMISSION);
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();				
				}
			}else{
			    logger.error(RestErrorImpl.USER_MODULE_NOT_PERMISSION);
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_SECTORS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@GET
	@Path("/{id}/blocks")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getBlocks(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		List<AreaChild> list = new ArrayList<AreaChild>();
		try {
			if(userPermission("GET","/areas")){				
				if(userAreaPermission(id)){
					list = getConnector().getAreaService().getBlocks(id);
	                returnedValue = Response.ok(list).build();
	                	
	            }else{
				    logger.error(RestErrorImpl.USER_AREA_NOT_PERMISSION);
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();				
				}
			}else{
			    logger.error(RestErrorImpl.USER_MODULE_NOT_PERMISSION);
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_BLOCKS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	
	@GET
	@Path("/{id}/descendants/{typeId}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getDescendants(@PathParam("id")  Integer id, @PathParam("typeId")  Integer typeId) {
		Response returnedValue = null;
		List<AreaChild> list = new ArrayList<AreaChild>();
		try {
			if(userPermission("GET","/areas")){				
				if(userAreaPermission(id)){
					list = getConnector().getAreaService().getDescendants(id, typeId);
	                returnedValue = Response.ok(list).build();
	                	
	            }else{
				    logger.error(RestErrorImpl.USER_AREA_NOT_PERMISSION);
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();				
				}
			}else{
			    logger.error(RestErrorImpl.USER_MODULE_NOT_PERMISSION);
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_DESCENDANTS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	@GET
	@Path("/{id}/schedules")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getSchedules(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/areas")){					
				if(userAreaPermission(id)){
					List<ScheduleArea> listSchedules = getConnector().getAreaService().getSchedulesArea(id);
					if (listSchedules != null ) {
	                	returnedValue = Response.ok(listSchedules).build();
	                } else {
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
		    logger.error(RestErrorImpl.SERVICE_GET_HOUSES +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}		
	
	
	@GET
	@Path("/tree")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getTreeAreas() {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/areas")){			
				
				AreaTree tree = getConnector().getAreaService().getTreeAreas();
				List<AreaTree> list = new ArrayList<AreaTree>();
				if (tree != null ) {
					list.add(tree);
                	returnedValue = Response.ok(list).build();
                } else {
                    returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }		
				
			}else{
			    logger.error(RestErrorImpl.USER_MODULE_NOT_PERMISSION);
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_PARENTS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	
	@PUT
	@Path("/{id}/statsmr")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	

	public Response getStatsMr(@PathParam("id")  Integer id, StatsMrInspections statsMrInspections) {
		Response returnedValue = null;
		try {
			List<StatsMrAreas> list = new ArrayList<StatsMrAreas>();

			if(userPermission("PUT","/areas")){
				if(userAreaPermission(id)){
					list = getConnector().getAreaService().getListStatsMr(getSession(), id, statsMrInspections.getInspections());
	                returnedValue = Response.ok(list).build();
	                	
	            }else{
				    logger.error(RestErrorImpl.USER_AREA_NOT_PERMISSION);
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();				
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_STATSMR +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	

	@PUT
	@Path("/{id}/statseess")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	

	public Response getStatsEess(@PathParam("id")  Integer id, StatsMrInspections statsMrInspections) {
		Response returnedValue = null;
		try {
			List<StatsEessAreas> list = new ArrayList<StatsEessAreas>();

			if(userPermission("PUT","/areas")){
				if(userAreaPermission(id)){
					list = getConnector().getAreaService().getListStatsEess(getSession(), id, statsMrInspections.getInspections());
	                returnedValue = Response.ok(list).build();
	                	
	            }else{
				    logger.error(RestErrorImpl.USER_AREA_NOT_PERMISSION);
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();				
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_STATSEESS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	

	@PUT
	@Path("/{id}/focushouses")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	

	public Response getFocusHouses(@PathParam("id")  Integer id, StatsMrInspections statsMrInspections) {
		Response returnedValue = null;
		try {
			List<FocusHouse> list = new ArrayList<FocusHouse>();

			if(userPermission("PUT","/areas")){
				if(userAreaPermission(id)){
					list = getConnector().getAreaService().getListFocusHouses(getSession(), id, statsMrInspections.getInspections());
	                returnedValue = Response.ok(list).build();
	                	
	            }else{
				    logger.error(RestErrorImpl.USER_AREA_NOT_PERMISSION);
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();				
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_STATS_HOUSESFOCUS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
}
