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
package com.gruposca.sapev.api.services;

import com.gruposca.sapev.api.modelview.EmailToRestorePass;
import com.gruposca.sapev.api.modelview.RestorePass;
import com.gruposca.sapev.api.modelview.SecurityResult;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.UserPermission;

public interface SecurityService {
	
	Session authorizate(String userId, String password);
	
	SecurityResult validate(Session session);

	UserPermission userPermission(String type, String url, Session session );
	
	boolean userAreaPermission(Session session, Integer areaId);
	
	boolean validateAndRestore(RestorePass restorePass);	
	
	Boolean sendUrl(EmailToRestorePass emailToRestorePass);
	
	SecurityResult validateUrl(String urlToken);
	
}
