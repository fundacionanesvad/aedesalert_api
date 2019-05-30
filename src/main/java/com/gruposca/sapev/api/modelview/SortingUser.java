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

public class SortingUser {

	private String mName;
	private String mEmail;
	private String mEnabled;
	private String mProfile;
	private String mArea;
	
	public SortingUser(){}
	
	public SortingUser(String name, String email, String enabled, String profile, String area){
		mName     = name;		
		mEmail    = email;
		mEnabled  = enabled;
		mProfile  = profile;
		mArea     = area;
	}

	public String getName() {return mName;}
	public void setName(String mName) {this.mName = mName;}

	public String getEmail() {return mEmail;}
	public void setEmail(String mEmail) {this.mEmail = mEmail;}

	public String getEnabled() {return mEnabled;}
	public void setEnabled(String mEnabled) {this.mEnabled = mEnabled;}

	public String getProfile() {return mProfile;}
	public void setProfile(String mProfile) {this.mProfile = mProfile;}
	
	public String getArea() {return mArea;}
	public void setArea(String mArea) {this.mArea = mArea;}
}
