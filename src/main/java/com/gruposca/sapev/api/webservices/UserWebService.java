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

import java.util.Date;
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

import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.modelview.ParamFilterUsers;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.User;
import com.gruposca.sapev.api.modelview.UserCreateIml;
import com.gruposca.sapev.api.modelview.UserList;
import com.gruposca.sapev.api.modelview.UserListImpl;
import com.gruposca.sapev.api.modelview.UserState;
import com.gruposca.sapev.api.modelview.UserUpdateImpl;

import jxl.write.DateTime;

@Path("/users")
public class UserWebService extends AbsWebService{

	
	
	@PUT
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getUserList(ParamFilterUsers paramFilterUsers) {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/users") || userPermission("GET","/plans/user")){
				
				UserList userList = getConnector().getUserService().getUserList(getSession(), paramFilterUsers);
                if (userList != null) {
                	returnedValue = Response.ok(userList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
 
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_USER_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	
	@GET
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getUserList() {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/users")){
				
				List<User> userList = getConnector().getUserService().getUserList();
                if (userList != null && userList.size() > 0) {
                	returnedValue = Response.ok(userList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_USER_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	
	@GET
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getUser(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/users")){
								
				User user = getConnector().getUserService().getUser(id);		

				if(user!=null){
					returnedValue = Response.ok(user).build();
				}else{ 
					returnedValue = Response.status(Response.Status.NOT_FOUND).build(); 
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_USER +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		    //returnedValue = manageError(RestErrorImpl.ERROR_CODE_PRODUCTS_SERVICE_GET_PRODUCTS, RestErrorImpl.ERROR_DESC_PRODUCTS_SERVICE_GET_PRODUCTS, e);
		}		
		return returnedValue;
	}
	
	@POST
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response createUser(UserCreateIml user) {
		Response returnedValue = null;
		try {
			if(userPermission("POST","/users")){
						
				if(user.getPassword1() != null && !user.getPassword1().equals("") && user.getPassword2() != null && !user.getPassword2().equals("") & user.getPassword1().equals(user.getPassword2())){
					
					Users newUser = getConnector().getUserService().saveUser(user);
					if(newUser != null ){
						returnedValue = Response.status(Response.Status.CREATED).build();	
					}else{
						returnedValue = Response.status(Response.Status.NOT_FOUND).build();
					}
					
				}else{
					returnedValue = Response.status(Response.Status.NOT_ACCEPTABLE).build();					
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_CREATE_USER +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@PUT
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updateUser(@PathParam("id")  Integer id, UserUpdateImpl user) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/users")){
			
				if((user.getPassword1() == null && user.getPassword2() == null) || (user.getPassword1().equals(user.getPassword2()))){
					Users updateUser = getConnector().getUserService().updateUser(id, user);
					if(updateUser != null ){
						returnedValue = Response.status(Response.Status.OK).build();	
					}else{
						returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
					}
				
				}else{
					returnedValue = Response.status(Response.Status.NOT_ACCEPTABLE).build();					
				}				
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UPDATE_USER +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	@DELETE
	@Path("/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response deleteUser(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {

			if(userPermission("DELETE","/users")){
				
				getConnector().getUserService().deleteUser(id);		
				returnedValue = Response.status(Response.Status.OK).build();
				
			}else{				
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_DELETE_USER +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	@PUT
	@Path("/{id}/unlock")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response unlockUser(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/users")){			
				Users user = getConnector().getUserService().unlockUser(id);
				if(user != null ){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_MODIFIED).build();
				}
			
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_UNLOCK_USER +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}	
	
	
	@GET
	@Path("/area/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getUserInspectionList(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/users")){				
				List<UserListImpl> userList = getConnector().getUserService().getUserInspectionList(id);
                if (userList != null && userList.size() > 0) {
                	returnedValue = Response.ok(userList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_USER_INSPECTION_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	@GET
	@Path("/areaAdd/{id}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getUserInspectionListAdd(@PathParam("id")  Integer id) {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/users")){				
				List<UserListImpl> userList = getConnector().getUserService().getUserInspectionListAdd(id);
                if (userList != null && userList.size() > 0) {
                	returnedValue = Response.ok(userList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_USER_INSPECTION_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
	@POST
	@Path("/inspection") 
	@Consumes(RESPONSE_MEDIA_TYPE)
	@Produces(RESPONSE_TEXT_PLAIN)
	public Response getUserState( UserState user) {
		System.out.println("hola0");
		Response returnedValue = null;
		try {
			/*Date date = new Date();
			UserState user=new UserState(date.getTime(),70);
			System.out.println("hola");*/
			if(userPermission("GET","/users")){				
				String userStateMensaje = getConnector().getUserService().getUserState(user);
                if (userStateMensaje != null) {
                	returnedValue = Response.ok(userStateMensaje).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_USER_INSPECTION_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
}
