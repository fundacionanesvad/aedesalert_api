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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.sun.jersey.api.container.ContainerException;
import com.sun.jersey.core.util.ReaderWriter;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class LogFileFilter extends AbsFilter implements ContainerRequestFilter{
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		try{
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();      
	        
			if(request.getPath().equals("sync") && request.getMethod().equals("POST")){
	        	InputStream in = request.getEntityInputStream();
			   
			        try {
			            ReaderWriter.writeTo(in, baos);
			        } catch (IOException ex) {
			            throw new ContainerException(ex);
			        }
			        
			        in = new ByteArrayInputStream(baos.toByteArray());
			        request.setEntityInputStream(in);

			    InputStream input = new ByteArrayInputStream(baos.toByteArray());
			    File f=new File(ConfigurationHelper.getSyncFilePath()+Calendar.getInstance().getTimeInMillis()+".txt");

			    OutputStream output=new FileOutputStream(f);
			    ReaderWriter.writeTo(input, output);
			}
			
		}catch(Exception e){			
			if  (!(e instanceof SapevException)) {
				throw new SapevException(Response.Status.INTERNAL_SERVER_ERROR);
			 }else{
				 WebApplicationException webex = (WebApplicationException)e;
				 throw new SapevException(webex.getResponse().getStatus());
			 }			
		}
		
	    return request;
	}	
}
