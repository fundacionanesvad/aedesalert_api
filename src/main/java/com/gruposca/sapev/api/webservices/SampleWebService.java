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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.gruposca.sapev.api.helper.FilesHelper;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Sample;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.multipart.FormDataParam;

@Path("/samples")
public class SampleWebService extends AbsWebService{

	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_DATA_TYPE)	
	@Path("/upload")
	public Response uploadFile(@FormDataParam("file") File inputfile) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/samples")){				
				
				Boolean result = getConnector().getSampleService().uploadFile(inputfile);
				if (result ) {
                    returnedValue = Response.status(Response.Status.OK).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }	
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPLOAD_FILE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@GET
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getSamplesList() {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/samples")){
				
				List<Sample> sampleList = getConnector().getSampleService().getSampleList(getSession());
                if (sampleList != null) {
                	returnedValue = Response.ok(sampleList).build();
                } else {
                    returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_SAMPLES_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}	
	
	@GET
	@Path("/{id}/excel")
	@Produces(RESPONSE_XLS_TYPE)
	public Response getSampleXls(@PathParam("id")  Integer inspectionId) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/samples")){				
				
				String pdf = getConnector().getSampleService().createXls(getSession(),inspectionId);		
				File file = new File(pdf);	
				byte[] bytes = FilesHelper.loadFile(file);
				byte[] encoded = Base64.encode(bytes);
				String encodedString = new String(encoded);
				ResponseBuilder response = Response.ok((Object) encodedString);				
				response.header("Content-Disposition","attachment; filename=samplesReport.xls");
		        return response.build();
		        
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_SAMPLE_XLS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
}
