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

public class AccountUpdateImpl implements Account{
	
	private String  mPassword1;
	private String  mPassword2;
	private String  mName;
	private String  mEmail;
	private Integer mLanguageId;
	
	public AccountUpdateImpl(){}
	
	public AccountUpdateImpl(String password1, String password2, String name, String email, Integer languageId ){
		
		mPassword1  = password1;
		mPassword2  = password2;
		mName       = name; 
		mEmail      = email;
		mLanguageId = languageId;		
	}

	public String getPassword1() {return mPassword1;}
	public void setPassword1(String mPassword1) {this.mPassword1 = mPassword1;}

	public String getPassword2() {return mPassword2;}
	public void setPassword2(String mPassword2) {this.mPassword2 = mPassword2;}

	public String getName() {return mName;}
	public void setName(String mName) {this.mName = mName;}

	public String getEmail() {return mEmail;}
	public void setEmail(String mEmail) {this.mEmail = mEmail;}

	public Integer getLanguageId() {return mLanguageId;}
	public void setLanguageId(Integer mLanguageId) {this.mLanguageId = mLanguageId;}
}
