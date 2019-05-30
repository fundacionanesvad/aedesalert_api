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
package com.gruposca.sapev.api.modelview;

import java.util.List;


public class SessionImpl implements Session{
	
	private String  mAuthorizationToken;
	private String  mName;
	private Integer	mId;
	private Integer	mAreaId;
	private List<String> mModules;
	private Boolean mBlocked;
	private Long mTokenExpiration;

	

	public SessionImpl(String authorizationToken) {
		mAuthorizationToken = authorizationToken;
	}
	
	public SessionImpl(String authorizationToken, String name, Integer id, Integer areaId, List<String> modules, Boolean blocked, Long tokenExpiration) {
		mAuthorizationToken  = authorizationToken;
		mName            = name;
		mId              = id; 
		mAreaId          = areaId;
		mModules         = modules;
		mBlocked		 = blocked;
		mTokenExpiration = tokenExpiration;
	}	

	@Override
	public String getAuthorizationToken() { return mAuthorizationToken; }
	
	@Override
	public String getName() { return mName; }	
	
	@Override
	public Integer getId() { return mId; }

	@Override
	public Integer getAreaId() { return mAreaId;	}

	@Override
	public List<String> getModules() { return mModules; }

	@Override
	public Boolean isBlocked() {return mBlocked;}

	@Override
	public Long getTokenExpiration() { return mTokenExpiration;	}	
}
