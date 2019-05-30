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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.gruposca.sapev.api.modelview.Language;
import com.gruposca.sapev.api.modelview.RestErrorImpl;

@Path("/languages")
public class LanguageWebService extends AbsWebService{

	
	@GET
	@Produces(RESPONSE_MEDIA_TYPE)
	public Response getLanguagesList() {
		Response returnedValue = null;
		try {
			
			if(userPermission("GET","/languages")){
				
				List<Language> languageList = getConnector().getLanguageService().getLanguageList();
                if (languageList != null && languageList.size() > 0) {
                	returnedValue = Response.ok(languageList).build();
                } else {
                    returnedValue = Response.status(Response.Status.NOT_FOUND).build();
                }
				
			}else{
				returnedValue = Response.status(Response.Status.FORBIDDEN).build();
			}			
			
		} catch (Exception e) {
		    logger.error(RestErrorImpl.SERVICE_GET_LANGUAJE_LIST +e.toString());
			returnedValue = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return returnedValue;
	}
	
}
