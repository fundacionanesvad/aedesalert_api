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

public class RestorePass {
	private String mLogin;
	private String mNewPass;
	private String mConfirmPass;
	
	public RestorePass(){}
	
	public RestorePass(String login, String newPass, String confirmPass){
		this.mLogin = login;
		this.mNewPass = newPass;
		this.mConfirmPass = confirmPass;
	}

	public String getLogin() {return mLogin;}
	public void setLogin(String mLogin) {this.mLogin = mLogin;}

	public String getNewPass() {return mNewPass;}
	public void setNewPass(String mNewPass) {this.mNewPass = mNewPass;}

	public String getConfirmPass() {return mConfirmPass;}
	public void setConfirmPass(String mConfirmPass) {this.mConfirmPass = mConfirmPass;}

}
