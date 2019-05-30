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
import javax.ws.rs.core.Response.ResponseBuilder;
import com.gruposca.sapev.api.connectors.dao.model.Inspections;
import com.gruposca.sapev.api.helper.FilesHelper;
import com.gruposca.sapev.api.modelview.Inspection;
import com.gruposca.sapev.api.modelview.InspectionBlock;
import com.gruposca.sapev.api.modelview.InspectionImpl;
import com.gruposca.sapev.api.modelview.InspectionList;
import com.gruposca.sapev.api.modelview.ParamFilterInspections;
import com.gruposca.sapev.api.modelview.ParamFilterPlans;
import com.gruposca.sapev.api.modelview.ParamFilterVisits;
import com.gruposca.sapev.api.modelview.Plan;
import com.gruposca.sapev.api.modelview.PlansInspectionList;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.SampleInspection;
import com.gruposca.sapev.api.modelview.VisitList;
import com.sun.jersey.core.util.Base64;

@Path("/inspections")
public class InspectionWebService extends AbsWebService{

	@PUT
	@Produces(RESPONSE_MEDIA_TYPE)	
	
	public Response getInspectionsList(ParamFilterInspections paramFilterInspections) {
		Response returnedValue = null;
		try {
			
			if(userPermission("PUT","/inspections")){
				InspectionList inspectionList = getConnector().getInspectionService().getListInspection(getSession(), paramFilterInspections);
                if (inspectionList != null) {
                	returnedValue = Response.ok(inspectionList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_INSPECTION_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	@GET
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getInspection(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/inspections")){
								
				Inspection inpection = getConnector().getInspectionService().getInspection(id);		

				if(inpection!=null){
					returnedValue = Response.ok(inpection).build();
				}else{ 
					returnedValue = Response.status(Response.Status.NOT_FOUND).build(); 
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_INSPECTION +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response createInspections(InspectionImpl inspection) {
		Response returnedValue = null;
		try {
			
			if(userPermission("POST","/inspections")){				
				Inspections newInspection = getConnector().getInspectionService().createInspection(inspection);
				if(newInspection != null ){
					returnedValue = Response.ok(newInspection.getId()).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CREATE_INSPECTION +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	@PUT
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updateInspections(@PathParam("id")  Integer id, InspectionImpl inspection) {
		Response returnedValue = null;
		try {
			
			if(userPermission("PUT","/inspections")){
				Inspections updateInspection = getConnector().getInspectionService().updateInspections(id, inspection);
				if(updateInspection != null ){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}			
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_INSPECTION +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	
	@DELETE
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response deleteInspection(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {

			if(userPermission("DELETE","/inspections")){				
				if(getConnector().getInspectionService().deleteInspection(id)){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{					
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();
				}			
			}else{				
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_DELETE_INSPECTION +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@GET
	@Path("/{id}/plans")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getPlans(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/inspections")){
				
				List<Plan> planList = getConnector().getInspectionService().getListPlans(id);
                if (planList != null) {
                	returnedValue = Response.ok(planList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }								
						
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_INSPECTION_PERSONS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@PUT
	@Path("/{id}/planList")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getPlans(@PathParam("id")  Integer id, ParamFilterPlans paramFilterPlans) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/inspections")){
				
				PlansInspectionList plansInspectionList = getConnector().getInspectionService().getListPlans(getSession(), id, paramFilterPlans);
                if (plansInspectionList != null) {
                	returnedValue = Response.ok(plansInspectionList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }								
						
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_INSPECTION_PERSONS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}


	
	@POST
	@Path("/{id}/visits")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getVisits(@PathParam("id")  Integer id, ParamFilterVisits paramFilterVisits) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/inspections")){
				
				VisitList visitList = getConnector().getInspectionService().getListVisit(getSession(), id, paramFilterVisits);
                if (visitList != null) {
                	returnedValue = Response.ok(visitList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }							
						
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_INSPECTION_VISITS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@GET
	@Path("/{id}/close")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response closeInspection(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/inspections")){
				Boolean closeRequest =  getConnector().getInspectionService().closeInspection(id);
				if (closeRequest ) {
                    returnedValue = Response.status(Response.Status.OK).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_ACCEPTABLE).build();
                }		
				
			}else{				
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_POST_CLOSE_INSPECTION +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	
	@GET
	@Path("/{id}/xlsx")
	@Produces(RESPONSE_XLS_TYPE)
	public Response getReportXls(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/inspections")){				
				
				String xls = getConnector().getInspectionService().createXls(id);		
				File file = new File(xls);	
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
		    logger.error(RestErrorImpl.SERVICE_REPORT_XLSX +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	
	@GET
	@Path("/{id}/blocks")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getInspectionBlocks(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		List<InspectionBlock> list = new ArrayList<InspectionBlock>();
		try {

			if(userPermission("GET","/inspections")){	
				list = getConnector().getInspectionService().getBlocks(id);
                returnedValue = Response.ok(list).build();
				
			}else{

			    logger.error(RestErrorImpl.USER_MODULE_NOT_PERMISSION);
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_INSPECTION_BLOCKS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@GET
	@Path("/{id}/substitutes")
	@Produces(RESPONSE_XLS_TYPE)
	public Response getSusbstitutesReport(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/reports")){				
				
				String xls = getConnector().getInspectionService().getSusbstitutesReport(id);		
				File file = new File(xls);	
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
		    logger.error(RestErrorImpl.SERVICE_GET_SUBSTITUTES_EXCEL +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@GET
	@Path("/{id}/samples")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getSamples(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/inspections")){
				
				List<SampleInspection> list = getConnector().getInspectionService().getListSamples(id);
                if (list != null) {
                	returnedValue = Response.ok(list).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }							
						
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_INSPECTION_SAMPLES +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	@GET
	@Path("/{id}/samplesExcel")
	@Produces(RESPONSE_XLS_TYPE)
	public Response getInspectionSampleXls(@PathParam("id")  Integer inspectionId) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/samples")){				
				
				String pdf = getConnector().getInspectionService().createSamplesXls(getSession(),inspectionId);		
				File file = new File(pdf);	
				byte[] bytes = FilesHelper.loadFile(file);
				byte[] encoded = Base64.encode(bytes);
				String encodedString = new String(encoded);
				ResponseBuilder response = Response.ok((Object) encodedString);				
				response.header("Content-Disposition","attachment; filename=SamplesInspection.xls");
		        return response.build();
		        
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_INSPECTION_SAMPLE_XLS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
}
