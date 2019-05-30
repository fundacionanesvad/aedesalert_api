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


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.connectors.factory.Connector;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.helper.ExceptionsHelper;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.UserPermission;
import com.sun.jersey.spi.container.ContainerRequest;

public class AbsWebService {

	protected final static String RESPONSE_MEDIA_TYPE = MediaType.APPLICATION_JSON + "; charset=UTF-8";
	protected final static String RESPONSE_IMAGE_TYPE = "image/jpeg";
	protected final static String RESPONSE_PDF_TYPE   = "application/pdf";
	protected final static String RESPONSE_XLS_TYPE   = "application/xls";
	protected final static String RESPONSE_DATA_TYPE = MediaType.MULTIPART_FORM_DATA;
	protected final static String RESPONSE_TEXT_PLAIN = MediaType.TEXT_PLAIN;
	
    public final static Logger logger = LoggerFactory.getLogger(AbsWebService.class);

	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
	private ContainerRequest mClientRequest;
	private Connector        mConnector;
	
	/**
	 * Constructor principal de la clase.
	 */
	public AbsWebService() { }
	
	/**
	 * Este metodo captura las peticiones y gestiona el Request.
	 * @param request
	 */
	@Context
	public void setRequest(Request request) {
		mClientRequest = ((ContainerRequest)request);
	}
	
	/**
	 * Usa este metodo para establecer el nombre del paquete del conector.
	 * @return Devuelve el nombre del paquete asociado al conector de la API.
	 * @throws IOException 
	 */
	protected String getConnectorPackage() throws IOException {
		String connectorPackage = ConfigurationHelper.getString(ConfigurationHelper.CONNECTOR_PACKAGE);
		return connectorPackage; 
	}
	
	/**
	 * Usa este método para obtener la instancia a la implementación del conector.
	 * @param connectorPackage, Nombre completo del paquete del conector.
	 * @return Retorna la intancia con la implementación del conector o un null si este no existe.
	 * @throws IOException 
	 */
	public AbsConnector getConnector() throws IOException {
		String connectorPackage = getConnectorPackage();
		if (connectorPackage != null && !connectorPackage.trim().equals("")) {
			if (mConnector == null) {
				return new Connector();
			}else System.out.println("mConnector != null");
		}else System.out.println("connectorPackage = null");

		return mConnector;
	}

	protected Session getSession() throws IOException {
		Session returnedValue = null;			
		if (mClientRequest != null)
			returnedValue = getConnector().createSession(mClientRequest.getHeaderValue(HttpHeaders.AUTHORIZATION));
		return returnedValue;
	}


	protected Response manageError(Integer code, String description) {
		return manageError(code, description, null);
	}
	

	protected Response manageError(Integer code, String description, Exception e) {
		ExceptionsHelper.manage(e);
		
		RestErrorImpl error = null;
		if (!ConfigurationHelper.debugMode() || e == null) {
			error = new RestErrorImpl(this.getClass(), code, description);
		} else {
			error = new RestErrorImpl(this.getClass(), code, e.toString());
		}

		Response returnedValue = Response
								.status(500)   	//INTERNAL_SERVER_ERROR.
								.entity(error)
								.build();		
		return returnedValue;
	}
	
	
	protected ContainerRequest getContainerRequest(){
		return mClientRequest;
	}
	
	
	
	protected void getHeaderInfo() throws IOException {
		if (mClientRequest != null){
			System.out.println("ACCEPT:"+mClientRequest.getHeaderValue(HttpHeaders.ACCEPT));
			System.out.println("ACCEPT_CHARSET:"+mClientRequest.getHeaderValue(HttpHeaders.ACCEPT_CHARSET));
			System.out.println("ACCEPT_ENCODING:"+mClientRequest.getHeaderValue(HttpHeaders.ACCEPT_ENCODING));
			System.out.println("ACCEPT_LANGUAGE:"+mClientRequest.getHeaderValue(HttpHeaders.ACCEPT_LANGUAGE));
			System.out.println("AUTHORIZATION:"+mClientRequest.getHeaderValue(HttpHeaders.AUTHORIZATION));
			System.out.println("CACHE_CONTROL:"+mClientRequest.getHeaderValue(HttpHeaders.CACHE_CONTROL));
			System.out.println("CONTENT_ENCODING:"+mClientRequest.getHeaderValue(HttpHeaders.CONTENT_ENCODING));
			System.out.println("CONTENT_LANGUAGE:"+mClientRequest.getHeaderValue(HttpHeaders.CONTENT_LANGUAGE));
			System.out.println("CONTENT_LENGTH:"+mClientRequest.getHeaderValue(HttpHeaders.CONTENT_LENGTH));
			System.out.println("CONTENT_LOCATION:"+mClientRequest.getHeaderValue(HttpHeaders.CONTENT_LOCATION));
			System.out.println("CONTENT_TYPE:"+mClientRequest.getHeaderValue(HttpHeaders.CONTENT_TYPE));
			System.out.println("COOKIE:"+mClientRequest.getHeaderValue(HttpHeaders.COOKIE));
			System.out.println("DATE:"+mClientRequest.getHeaderValue(HttpHeaders.DATE));
			System.out.println("ETAG:"+mClientRequest.getHeaderValue(HttpHeaders.ETAG));
			System.out.println("EXPIRES:"+mClientRequest.getHeaderValue(HttpHeaders.EXPIRES));
			System.out.println("HOST:"+mClientRequest.getHeaderValue(HttpHeaders.HOST));
			System.out.println("IF_MATCH:"+mClientRequest.getHeaderValue(HttpHeaders.IF_MATCH));
			System.out.println("IF_MODIFIED_SINCE:"+mClientRequest.getHeaderValue(HttpHeaders.IF_MODIFIED_SINCE));
			System.out.println("IF_NONE_MATCH:"+mClientRequest.getHeaderValue(HttpHeaders.IF_NONE_MATCH));
			System.out.println("IF_UNMODIFIED_SINCE:"+mClientRequest.getHeaderValue(HttpHeaders.IF_UNMODIFIED_SINCE));
			System.out.println("LAST_MODIFIED:"+mClientRequest.getHeaderValue(HttpHeaders.LAST_MODIFIED));
			System.out.println("LOCATION:"+mClientRequest.getHeaderValue(HttpHeaders.LOCATION));
			System.out.println("SET_COOKIE:"+mClientRequest.getHeaderValue(HttpHeaders.SET_COOKIE));
			System.out.println("USER_AGENT:"+mClientRequest.getHeaderValue(HttpHeaders.USER_AGENT));
			System.out.println("VARY:"+mClientRequest.getHeaderValue(HttpHeaders.VARY));
			System.out.println("WWW_AUTHENTICATE:"+mClientRequest.getHeaderValue(HttpHeaders.WWW_AUTHENTICATE));
		}
	}
	
	protected void getUriInfo(Users user) throws IOException {
		if (mClientRequest != null){			
		    System.out.println(dateFormat.format(new Date())+" |- USER: "+user.getName()+" |- METHOD: "+mClientRequest.getMethod()+" |- URL: "+mClientRequest.getRequestUri());
		}		
	}	
	
	protected boolean userPermission(String type, String url) throws IOException{		
		UserPermission userPermission = getConnector().getSecurityService().userPermission(type, url, getSession());		
		this.getUriInfo(userPermission.getUser());
		return userPermission.isPermission();
	}	
	
	
	 protected boolean userAreaPermission(Integer areaId) throws IOException   {
        return getConnector().getSecurityService().userAreaPermission(getSession(), areaId);
    } 
	
}
