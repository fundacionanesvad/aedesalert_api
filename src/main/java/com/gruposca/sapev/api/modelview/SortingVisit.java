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

public class SortingVisit {

	private String mHouseCode;
	private String mDate;
	private String mResultName;
	private String mFeverish;
	private String mAreaName;
	private String mSectorName;
	private String mEessName;
	private String mMicrorredName;
	private String mSample;
	private String mUserName;
	private String mHouseStreet;
	private String mHouseNumber;
	
	public SortingVisit(){}
	
	public SortingVisit(String HouseStreet,String HouseNumber,String houseCode, String date, String resultName, String feverish, String areaName, String sectorName, String eessName, String microrredName, String sample, String userName){
		mHouseCode  = houseCode;
		mDate       = date;
		mResultName = resultName;
		mFeverish   = feverish;
		mAreaName   = areaName;
		mSectorName = sectorName;
		mEessName = eessName;
		mMicrorredName = microrredName;
		mSample = sample;
		mUserName = userName;
		mHouseStreet=HouseStreet;
		mHouseNumber=HouseNumber;
	}
	public String getHouseStreet() {
		return mHouseStreet;
	}

	public void setHouseStreet(String mHouseStreet) {
		this.mHouseStreet = mHouseStreet;
	}

	public String getHouseNumber() {
		return mHouseNumber;
	}

	public void setHouseNumber(String mHouseNumber) {
		this.mHouseNumber = mHouseNumber;
	}
	public String getHouseCode() {return mHouseCode;}
	public void setHouseCode(String mHouseCode) {this.mHouseCode = mHouseCode;	}

	public String getDate() {return mDate;}
	public void setDate(String mDate) {this.mDate = mDate;}

	public String getResultName() {return mResultName;}
	public void setResultName(String mResultName) {this.mResultName = mResultName;}

	public String getFeverish() {return mFeverish;}
	public void setFeverish(String mFeverish) {this.mFeverish = mFeverish;}

	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}
	
	public String getSectorName() {return mSectorName;}
	public void setSectorName(String mSectorName) {this.mSectorName = mSectorName;}

	public String getEessName() {return mEessName;}
	public void setEessName(String mEessName) {this.mEessName = mEessName;}

	public String getMicrorredName() {return mMicrorredName;}
	public void setMicrorredName(String mMicrorredName) {this.mMicrorredName = mMicrorredName;}

	public String getSample() {return mSample;}
	public void setSample(String mSample) {this.mSample = mSample;}

	public String getUserName() {return mUserName;}
	public void setUserName(String mUserName) {this.mUserName = mUserName;}
	
}
