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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.gruposca.sapev.api.modelview.EmailToRestorePass;
import com.gruposca.sapev.api.modelview.LoginImpl;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.RestorePass;
import com.gruposca.sapev.api.modelview.SecurityResult;
import com.gruposca.sapev.api.modelview.SecurityResult.Result;
import com.gruposca.sapev.api.modelview.Session;


@Path("/auth")
public class SecurityWebService extends AbsWebService{

	@GET
	@Path("/login")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response notFound() {
	    logger.error(RestErrorImpl.SERVICE_SECURITY_NOT_FOUND );
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@POST
	@Path("/login")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)

	public Response authorizate(LoginImpl credentials)  {
		Response returnedValue = null;
		try {
			
            String login=credentials.getLogin();
            String password = credentials.getPassword();

			if (login != null && !login.trim().equals("") && password != null && !password.trim().equals("")) {
				   Session authorizationSession = getConnector().getSecurityService().authorizate(login, password);
				   if (authorizationSession != null && (authorizationSession.getAuthorizationToken() != null || authorizationSession.isBlocked())) {
					   returnedValue = Response.ok(authorizationSession).status(Status.OK).build();
				   } else {
					   returnedValue = Response.status(Response.Status.UNAUTHORIZED).build();
				   }
			} else {
				   returnedValue = Response.status(Response.Status.NOT_FOUND).build();
			}
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_AUTHORIZATE +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return returnedValue;
	}	
	
	
	@POST
	@Path("/sendUrl")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response urlRestorePass(EmailToRestorePass emailToRestorePass) {
		Response returnedValue = null;
		try {
			System.out.println("/auth/sendUrl");
			
			boolean reset = getConnector().getSecurityService().sendUrl(emailToRestorePass);
			if(reset){
				returnedValue = Response.status(Response.Status.OK).build();	

			}else{				
				returnedValue = Response.status(Response.Status.NOT_ACCEPTABLE).build();
			}
		
		} catch (Exception e) {
			logger.error(RestErrorImpl.SERVICE_SEND_URL +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return returnedValue;
	}
	
	@GET
	@Path("/validateUrl/{urlToken}")
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response validateUrl(@PathParam("urlToken") String urlToken) {
		Response returnedValue = null;
		try {
			System.out.println("/auth/validateUrl");
							
			SecurityResult security= getConnector().getSecurityService().validateUrl(urlToken);								
			System.out.println(security);				
			if(security!=null && security.getSecurityResult().equals(Result.OK) ){
				returnedValue = Response.ok(security).build();
			}else{ 
				returnedValue = Response.status(Response.Status.FORBIDDEN).build(); 
			}
			getHeaderInfo();
			
		} catch (Exception e) {
			 logger.error(RestErrorImpl.SERVICE_VALIDATE_URL +e.toString());
				returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return returnedValue;
	}
	
	@PUT
	@Path("/restorePassword")
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response restorePassword(RestorePass restorePass) {
		Response returnedValue = null;
		try {
			System.out.println("/auth/restorePassword");		
			if (restorePass.getLogin() != null && !restorePass.getLogin().trim().equals("") && restorePass.getNewPass().equals(restorePass.getConfirmPass())) {
				Boolean validate = getConnector().getSecurityService().validateAndRestore(restorePass);
				if(validate ){
					returnedValue = Response.status(Response.Status.OK).build();	
				}else{
					returnedValue = Response.status(Response.Status.NOT_ACCEPTABLE).build();
				}				
			} else {
				   returnedValue = Response.status(Response.Status.NOT_ACCEPTABLE).build();
			}
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_RESTORESPASSWORD +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return returnedValue;
	}	
	
}
