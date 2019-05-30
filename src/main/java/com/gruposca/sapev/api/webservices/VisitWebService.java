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

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.helper.ImageHelper;
import com.gruposca.sapev.api.modelview.ParamFilterVisits;
import com.gruposca.sapev.api.modelview.Person;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Sample;
import com.gruposca.sapev.api.modelview.Visit;
import com.gruposca.sapev.api.modelview.VisitInventory;
import com.gruposca.sapev.api.modelview.VisitList;

@Path("/visits")
public class VisitWebService extends AbsWebService{
	
	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getVisitList(ParamFilterVisits paramFilterVisits) {
		
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/visits")){				
				VisitList visitList = getConnector().getVisitService().getVisitList(getSession(), paramFilterVisits);
				if (visitList != null ) {
                	returnedValue = Response.ok(visitList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }

			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_VISIT_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return returnedValue;
	}	
	
	@GET
	@Path("/{uuid}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getVisit(@PathParam("uuid")  String uuid) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/visits")){
								
				Visit visit = getConnector().getVisitService().getVisit(getSession(),uuid);		

				if(visit!=null){
					returnedValue = Response.ok(visit).build();
				}else{ 
					returnedValue = Response.status(Response.Status.NOT_FOUND).build(); 
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_VISIT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@GET
	@Path("/{uuid}/photo")
	@Produces(RESPONSE_IMAGE_TYPE)
	public Response getPhoto(@PathParam("uuid")  String uuid) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/visits")){								
				String pathImages=ConfigurationHelper.getImagesPath();				
				File file = new File(pathImages + uuid + ConfigurationHelper.getImageExtension());		
				
				if (!file.exists()){
					file = new File(pathImages + ConfigurationHelper.getImageNoDisponible());
				}
				
				if (file.isFile()  && file.canRead() ) {
					BufferedImage img = ImageIO.read(file);
			        String imgstr  = ImageHelper.encodeToString(img, "jpg");
					ResponseBuilder response = Response.ok((Object) imgstr);
					response.header("Content-Disposition","attachment; filename=visit_image.jpg");
					returnedValue = response.build();
				} else {
					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_PHOTO +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@GET
	@Path("/{uuid}/inventories")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getInventories(@PathParam("uuid")  String uuid) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/visits")){
				
				VisitInventory visitInventory = getConnector().getVisitService().getInventories(getSession(), uuid);
                if (visitInventory != null) {
                	returnedValue = Response.ok(visitInventory).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
								
						
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_INVENTORIES +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@GET
	@Path("/{uuid}/samples")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getSamples(@PathParam("uuid")  String uuid) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/visits")){
				
				List<Sample> sampleList = getConnector().getVisitService().getSamples(getSession(), uuid);
                if (sampleList != null) {
                	returnedValue = Response.ok(sampleList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
								
						
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_SAMPLES +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@GET
	@Path("/{uuid}/persons")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getPersons(@PathParam("uuid")  String uuid) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/visits")){
				
				List<Person> personList = getConnector().getVisitService().getPersons(getSession(), uuid);
                if (personList != null) {
                	returnedValue = Response.ok(personList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
						
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_PERSONS +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@DELETE
	@Path("/{uuid}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response deleteVisit(@PathParam("uuid")  String uuid) {
		Response returnedValue = null;
		try {

			if(userPermission("PUT","/plans/user")){
				
				if(getConnector().getVisitService().deleteVisit(uuid)){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{					
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();
				}			
				
			}else{				
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_DELETE_VISIT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}

}
