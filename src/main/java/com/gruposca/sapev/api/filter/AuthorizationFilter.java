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
package com.gruposca.sapev.api.filter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.SecurityResult;
import com.gruposca.sapev.api.modelview.SecurityResult.Result;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class AuthorizationFilter extends AbsFilter implements ContainerRequestFilter{
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		
		String baseUri = request.getBaseUri().toString();
		String requestUri = request.getRequestUri().toString().replace(baseUri, "");
		if (!requestUri.startsWith("auth/")) {
			try {
 				SecurityResult securityResult = getConnector().getSecurityService().validate(getSession(request));
				if(securityResult!=null && securityResult.getSecurityResult().equals(Result.KO) ){
					throw new SapevException(Response.Status.UNAUTHORIZED,RestErrorImpl.ERROR_CODE_AUTHORIZATION_FILTER,RestErrorImpl.ERROR_DESC_AUTHORIZATION_FILTER);
				}else if (securityResult!=null && securityResult.getSecurityResult().equals(Result.NF)) {
					throw new SapevException(Response.Status.NOT_FOUND,RestErrorImpl.ERROR_CODE_AUTHORIZATION_FILTER_NOTFOUND,RestErrorImpl.ERROR_DESC_AUTHORIZATION_FILTER_NOTFOUND);
				}
			} catch (Exception e) {
				
				if  (!(e instanceof SapevException)) {
					throw new SapevException(Response.Status.INTERNAL_SERVER_ERROR);
				 }else{
					 WebApplicationException webex = (WebApplicationException)e;
					 throw new SapevException(webex.getResponse().getStatus());
				 }
			}
		}
		return request;
	}

}
