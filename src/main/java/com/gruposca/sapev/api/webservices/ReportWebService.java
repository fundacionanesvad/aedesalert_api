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
import com.gruposca.sapev.api.connectors.dao.model.Reports;
import com.gruposca.sapev.api.helper.FilesHelper;
import com.gruposca.sapev.api.modelview.InspectionListImpl;
import com.gruposca.sapev.api.modelview.ParamFilterReports;
import com.gruposca.sapev.api.modelview.Report;
import com.gruposca.sapev.api.modelview.ReportInspection;
import com.gruposca.sapev.api.modelview.ReportList;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Schedule;
import com.sun.jersey.core.util.Base64;

@Path("/reports")
public class ReportWebService extends AbsWebService{
	
	@PUT
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getReportsList(ParamFilterReports paramFilterReports) {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/reports")){
				
				ReportList reportList = getConnector().getReportService().getReportList(getSession(), paramFilterReports);
                if (reportList != null) {
                	returnedValue = Response.ok(reportList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
 
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_REPORTS_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	
	@GET
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getElement(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/reports")){				
				
				Report report = getConnector().getReportService().getReport(id);
			
	            if (report != null) {
	            	returnedValue = Response.ok(report).build();
	            } else {
	                returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	            }				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_REPORT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	
	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response createReport(Report report) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/reports")){						
				
				Reports newReport = getConnector().getReportService().createReport(getSession(), report);
				if(newReport != null ){
					returnedValue = Response.ok(newReport.getId()).build();	
				}else{
					returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
				}		
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CREATE_REPORT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}		
	
	
	@PUT
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updateReport(@PathParam("id")  Integer id, Report report) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/reports")){
			
				Reports updateReport = getConnector().getReportService().updateReport(id, report);
				if(updateReport != null ){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}							
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_REPORT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	@DELETE
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response deleteReport(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {

			if(userPermission("DELETE","/reports")){
				
				if(getConnector().getReportService().deleteReport(id)){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{					
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();
				}			
				
			}else{				
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_DELETE_REPORT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@GET
	@Path("/{id}/pdf")
	@Produces(RESPONSE_PDF_TYPE)
	public Response getReportPdf(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/reports")){				
				
				String pdf = getConnector().getReportService().createPdf(id);		
				File file = new File(pdf);	
				byte[] bytes = FilesHelper.loadFile(file);
				byte[] encoded = Base64.encode(bytes);
				String encodedString = new String(encoded);
				ResponseBuilder response = Response.ok((Object) encodedString);				
				response.header("Content-Disposition","attachment; filename=report.pdf");
		        return response.build();
		        
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_REPORT_PDF +e.toString());
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
			if(userPermission("GET","/reports")){				
				
				String xls = getConnector().getReportService().createXls(id);		
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
	@Path("/{id}/planReport")
	@Produces(RESPONSE_PDF_TYPE)
	public Response getPlanMapReport(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/reports") || userPermission("GET","/plans/user")){				
				
				String pdf = getConnector().getReportService().createPlanMapReport(id);		
				File file = new File(pdf);	
				byte[] bytes = FilesHelper.loadFile(file);
				byte[] encoded = Base64.encode(bytes);
				String encodedString = new String(encoded);
				ResponseBuilder response = Response.ok((Object) encodedString);				
				response.header("Content-Disposition","attachment; filename=report.pdf");
		        return response.build();
		        
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_PLAN_REPORT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}		

	@GET
	@Path("/{id}/scheduleReport")
	@Produces(RESPONSE_XLS_TYPE)
	public Response getScheduleReport(@PathParam("id")  Integer id)  {

		Response returnedValue = null;
		try {
			if(userPermission("GET","/reports")){				
				String xlsx = getConnector().getReportService().createScheduleReport(id);		
				File file = new File(xlsx);	
				byte[] bytes = FilesHelper.loadFile(file);
				byte[] encoded = Base64.encode(bytes);
				String encodedString = new String(encoded);
				ResponseBuilder response = Response.ok((Object) encodedString);				
				response.header("Content-Disposition","attachment; filename=report.xlsx");
		        return response.build();
		        
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_SCHEDULE_REPORT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@PUT
	@Path("/inspections")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getInspections(ReportInspection reportInspection)  {

		Response returnedValue = null;
		try {
			List<InspectionListImpl> list = new ArrayList<InspectionListImpl>();
			if(userPermission("GET","/reports")){	
				list = getConnector().getReportService().getInspections(reportInspection);				
                returnedValue = Response.ok(list).build();              
		        
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_REPORT_INSPECTIONS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	
	
}
