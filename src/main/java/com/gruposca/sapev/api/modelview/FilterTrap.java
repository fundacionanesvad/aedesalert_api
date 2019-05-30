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

public class FilterTrap {

	private Integer   mNumber;
	private String    mCode;
	private String    mEessName;
	private String    mMicrorredName;
	private String    mRedName;
	private Integer   mEnabled;
	
	public FilterTrap(){}
	
	public FilterTrap(Integer number, String code, String eessName, String microrredName, String redName, Integer enabled){
		mNumber        = number;
		mCode          = code;
		mEessName      = eessName;
		mMicrorredName = microrredName;
		mRedName       = redName;
		mEnabled       = enabled;
	}

	public String getMicrorredName() {return mMicrorredName;}
	public void setMicrorredName(String mMicrorredName) {this.mMicrorredName = mMicrorredName;}

	public Integer getNumber() {return mNumber;}
	public void setNumber(Integer mNumber) {this.mNumber = mNumber;}

	public String getCode() {return mCode;}
	public void setCode(String mCode) {this.mCode = mCode;}

	public String getEessName() {return mEessName;}
	public void setEessName(String mEessName) {this.mEessName = mEessName;}

	public String getRedName() {return mRedName;}
	public void setRedName(String mRedName) {this.mRedName = mRedName;}

	public Integer getEnabled() {return mEnabled;}
	public void setEnabled(Integer mEnabled) {	this.mEnabled = mEnabled;}
	
}