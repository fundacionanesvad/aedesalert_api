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

import java.io.IOException;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.connectors.factory.Connector;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.helper.ExceptionsHelper;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.sun.jersey.spi.container.ContainerRequest;

public class AbsFilter {
	private Connector mConnector;
	
	/**
	 * Usa este mÃ©todo para establecer el nombre del paquete del conector.
	 * @return Devuelve el nombre del paquete asociado al conector de la API.
	 * @throws IOException 
	 */
	protected String getConnectorPackage() throws IOException {
		String connectorPackage = ConfigurationHelper.getConnector();
		return connectorPackage; 
	}
	
	/**
	 * Usa este mÃ©todo para obtener la instancia a la implementaciÃ³n del conector.
	 * @param connectorPackage, Nombre completo del paquete del conector.
	 * @return Retorna la intancia con la implementaciÃ³n del conector o un null si este no existe.
	 * @throws IOException 
	 */
	public AbsConnector getConnector() throws IOException {
		String connectorPackage = getConnectorPackage();
		
		if (connectorPackage != null && !connectorPackage.trim().equals("")) {
			if (mConnector == null) {
				return new Connector();
			}
		}
		
		return mConnector;
	}
	
	/**
	 * Usa este metodo para obtener la informaciÃ³n de la sesion desde el Request del cliente.
	 * @return
	 * @throws IOException
	 */
	protected Session getSession(ContainerRequest request) throws IOException {
		Session returnedValue = null;
			
		if (request != null)
			returnedValue = getConnector().createSession(request.getHeaderValue(HttpHeaders.AUTHORIZATION));
		
		return returnedValue;
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

	
	
	
}
