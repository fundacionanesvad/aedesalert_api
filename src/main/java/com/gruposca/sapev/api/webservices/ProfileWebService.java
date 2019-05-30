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

import com.gruposca.sapev.api.connectors.dao.model.Profiles;
import com.gruposca.sapev.api.modelview.Profile;
import com.gruposca.sapev.api.modelview.ProfileList;
import com.gruposca.sapev.api.modelview.RestErrorImpl;

@Path("/profiles")
public class ProfileWebService extends AbsWebService{

	
	@GET
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getProfileList() {
		Response returnedValue = null;
		try {			
			if(userPermission("GET","/profiles")){				
				List<ProfileList> profileList = getConnector().getProfileService().getProfileList();
                if (profileList != null && profileList.size() > 0) {
                	returnedValue = Response.ok(profileList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_PROFILE_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	

	@GET
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getProfiles(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/profiles")){				
				Profile profile = getConnector().getProfileService().getProfile(id);
				if(profile!=null){
					returnedValue = Response.ok(profile).build();
				}else{ 
					returnedValue = Response.status(Response.Status.NOT_FOUND).build(); 
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_PROFILE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response createProfile(Profile profile) {
		Response returnedValue = null;
		try {
			
			if(userPermission("POST","/profiles")){				
				Profiles newProfile = getConnector().getProfileService().createProfile(profile);
				if(newProfile != null ){
					returnedValue = Response.status(Response.Status.CREATED).build();	
				}else{
					returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
				}		
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CREATE_PROFILE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	@PUT
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updateProfilea(@PathParam("id")  Integer id, Profile profile) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/profiles")){
				Profiles updateProfile = getConnector().getProfileService().updateProfile(id, profile);
				if(updateProfile != null ){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}						
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}	
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_PROFILE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	@DELETE
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response deleteProfile(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("DELETE","/profiles")){
				if(getConnector().getProfileService().deleteProfile(id)){
					returnedValue = Response.status(Response.Status.OK).build();
				}else{
					returnedValue = Response.status(Response.Status.CONFLICT).build();
				}
			}else{				
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_DELETE_PROFILE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
}
