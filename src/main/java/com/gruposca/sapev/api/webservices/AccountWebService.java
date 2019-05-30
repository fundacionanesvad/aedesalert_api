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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.modelview.Account;
import com.gruposca.sapev.api.modelview.AccountUpdateImpl;
import com.gruposca.sapev.api.modelview.RestErrorImpl;

@Path("/account")
public class AccountWebService extends AbsWebService{

	@GET
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getAccount() {
		Response returnedValue = null;
		try {
			if(userPermission("GET","/account")){
				
				Account account = getConnector().getAccountService().getAccount(getSession());				
				if(account != null){					
					returnedValue = Response.ok(account).build();
				}else{
					returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); 
				}						
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_ACCOUNT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
	
	
	@PUT
	@Produces(RESPONSE_MEDIA_TYPE)
	@Consumes(RESPONSE_MEDIA_TYPE)	
	public Response updateAccount(AccountUpdateImpl account) {
		Response returnedValue = null;
		try {
			if(userPermission("PUT","/account")){
			
				if((account.getPassword1() == null && account.getPassword2() == null) || (account.getPassword1().equals(account.getPassword2()))){
					Users user = getConnector().getAccountService().updateAccount(getSession(), account);
					if(user != null ){
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
		    logger.error(RestErrorImpl.SERVICE_UPDATE_ACCOUNT +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}		
		return returnedValue;
	}
}
