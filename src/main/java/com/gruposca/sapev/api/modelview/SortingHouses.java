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

public class SortingHouses {

	private String mCode;
	private String mAreaName;
	private String mStreetName;
	private String mStreetNumber;
	private String mSectorName;
	private String mEessName;
	private String mMicrorredName;	
	
	public SortingHouses(){}
	
	public SortingHouses(String code, String areaName, String streetName, String streetNumber, String sectorName, String eessName, String microrredName){
		mCode         = code;
		mAreaName     = areaName;
		mStreetName   = streetName;
		mStreetNumber = streetNumber;
		mSectorName = sectorName;
		mEessName = eessName;
		mMicrorredName = microrredName;
	}

	public String getCode() {return mCode;}
	public void setCode(String mCode) {this.mCode = mCode;}

	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}

	public String getStreetName() {return mStreetName;}
	public void setStreetName(String mStreetName) {this.mStreetName = mStreetName;}

	public String getStreetNumber() {return mStreetNumber;}
	public void setStreetNumber(String mStreetNumber) {this.mStreetNumber = mStreetNumber;}
	
	public String getSectorName() {return mSectorName;}
	public void setSectorName(String mSectorName) {this.mSectorName = mSectorName;}

	public String getEessName() {return mEessName;}
	public void setEessName(String mEessName) {this.mEessName = mEessName;}

	public String getMicrorredName() {return mMicrorredName;}
	public void setMicrorredName(String mMicrorredName) {this.mMicrorredName = mMicrorredName;}
}
