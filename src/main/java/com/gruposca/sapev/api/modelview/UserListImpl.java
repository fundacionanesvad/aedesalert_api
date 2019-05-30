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

public class UserListImpl implements User {
	
	private int     mId;
	private String  mName;
	private String  mEmail;
	private boolean mEnabled;
	private Integer mProfileId;
	private Integer	mAreaId;
	private Integer	mLoginErrors;

	
	public UserListImpl(){}
	
	public UserListImpl(int id, String name, String email, boolean enabled, Integer profileId, Integer areaId, Integer loginErrors){
		
		mId          = id;
		mName        = name;
		mEmail       = email;
		mEnabled     = enabled;
		mProfileId   = profileId;
		mAreaId      = areaId;
		mLoginErrors = loginErrors;
	}

	public int getId() {return mId;}
	public void setId(int mId) {this.mId = mId;}

	public String getName() {return mName;}
	public void setName(String mName) {this.mName = mName;}

	public String getEmail() {return mEmail;}
	public void setEmail(String mEmail) {this.mEmail = mEmail;}

	public boolean isEnabled() {return mEnabled;}
	public void setEnabled(boolean mEnabled) {	this.mEnabled = mEnabled;}

	public Integer getProfileId() {return mProfileId;}
	public void setProfileId(Integer mProfileId) {this.mProfileId = mProfileId;}

	public Integer getAreaId() {return mAreaId;}
	public void setAreaId(Integer mAreaId) {this.mAreaId = mAreaId;}

	public Integer getLoginErrors() {return mLoginErrors;}
	public void setloginErrors(Integer mLoginErrors) {this.mLoginErrors = mLoginErrors;}	
	
}
