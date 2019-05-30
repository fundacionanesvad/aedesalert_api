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
package com.gruposca.sapev.api.modelview;

public class UserGetImpl implements User {
	
	private String  mLogin;
	private String  mName;
	private String  mEmail;
	private boolean mEnabled;
	private Integer mProfileId;
	private Integer mLanguageId;
	private Integer mAreaId;
	private String  mAreaName;


	public UserGetImpl(){}
	
	public UserGetImpl( String login, String name, String email, boolean enabled, Integer profileId, Integer languageId, Integer areaId, String  areaName){
		
		mLogin	    = login;
		mName       = name;
		mEmail      = email;
		mEnabled    = enabled;
		mProfileId  = profileId;
		mLanguageId = languageId;
		mAreaId     = areaId;
		mAreaName   = areaName;
	}

	public String getLogin() {return mLogin;}
	public void setLogin(String mLogin) {this.mLogin = mLogin;}

	public String getName() {return mName;}
	public void setName(String mName) {this.mName = mName;}

	public String getEmail() {return mEmail;}
	public void setEmail(String mEmail) {this.mEmail = mEmail;}

	public boolean isEnabled() {return mEnabled;}
	public void setEnabled(boolean mEnabled) {this.mEnabled = mEnabled;}

	public Integer getProfileId() {return mProfileId;}
	public void setProfileId(Integer mProfileId) {this.mProfileId = mProfileId;}

	public Integer getLanguageId() {return mLanguageId;}
	public void setLanguageId(Integer mLanguageId) {this.mLanguageId = mLanguageId;}

	public Integer getAreaId() {return mAreaId;}
	public void setAreaId(Integer mAreaId) {this.mAreaId = mAreaId;}

	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}	
	
}
