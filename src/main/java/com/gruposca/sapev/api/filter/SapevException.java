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
package com.gruposca.sapev.api.filter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;                      

public class SapevException extends WebApplicationException{
	
    public final static Logger logger = (Logger) LoggerFactory.getLogger(SapevException.class);

	private static final long serialVersionUID = 1L;

	public SapevException(JSONObject jsonObject) {
	    super(Response.status(Response.Status.OK)
	            .entity(jsonObject)
	            .type(MediaType.APPLICATION_JSON)
	            .build());
	}
	
	public SapevException() {
	    super();
	}
	
	public SapevException(int status) {
	    super(status);
	}
	
	public SapevException(String msg) {
		System.out.println(msg);
	}
	
	public SapevException(Status status, int code, String desc) {
		super(status);
		logger.error("ERROR ("+code+") Sapev: "+desc);
	}
	
	public SapevException(Status status) {
		super(status);
	}
	
	public SapevException(Status status, String desc) {
		super(status);
		logger.error(desc);
	}

}
