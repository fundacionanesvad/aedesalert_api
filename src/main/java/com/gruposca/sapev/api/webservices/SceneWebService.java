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

import com.gruposca.sapev.api.connectors.dao.model.Scenes;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Scene;
import com.gruposca.sapev.api.modelview.SceneImpl;

@Path("/scenes")
public class SceneWebService extends AbsWebService{

	@GET
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getScenesList() {		
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/scenes")){
				List<Scene> sceneList = getConnector().getSceneService().getScenesList(getSession());				
                if (sceneList != null ) {
                	returnedValue = Response.ok(sceneList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_SCENES_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	
	
	@GET
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getScene(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/scenes")){
				
				Scene scene = getConnector().getSceneService().getScene(id);						
				if(scene!=null){
					returnedValue = Response.ok(scene).build();
				}else{ 
					returnedValue = Response.status(Response.Status.NOT_FOUND).build(); 
				}	
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_SCENE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	
	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response createScene(SceneImpl scene) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/scenes")){						
				
				Scenes newScene = getConnector().getSceneService().createScene(scene);
				if(newScene != null ){
					returnedValue = Response.status(Response.Status.CREATED).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_FOUND).build();
				}		
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CREATE_SCENE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}		
	
	@PUT
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updateScene(@PathParam("id")  Integer id, SceneImpl scene) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/scenes")){
			
				Scenes updateScene = getConnector().getSceneService().updateScene(id, scene);
				if(updateScene != null ){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}							
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_SCENE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response deleteScene(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {

			if(userPermission("DELETE","/scenes")){
				
				if(getConnector().getSceneService().deleteScene(id)){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{					
					returnedValue = Response.status(Response.Status.FORBIDDEN).build();
				}			
				
			}else{				
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_DELETE_SCENE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	
}
