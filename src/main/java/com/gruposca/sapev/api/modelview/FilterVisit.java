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

public class FilterVisit {

	private String    mHouseCode;
	private Long      mDate;
	private Integer   mResultId;
	private Integer   mFeverish;
	private String    mAreaName;
	private String    mSectorName;
	private String    mEessName;
	private String    mMicrorredName;
	private Integer   mSample;
	private String    mUserName;
	private String	mHouseStreet;
	private String	mHouseNumber;

	
	public FilterVisit(){}
	
	public FilterVisit(String HouseStreet,String HouseNumber,String houseCode, Long date, Integer resultId, Integer feverish, String areaName, String sectorName, String eessName , String microrredName , Integer sample, String userName){
		mHouseCode = houseCode;
		mDate      = date;
		mResultId  = resultId;
		mFeverish  = feverish;
		mAreaName  = areaName;
		mSectorName = sectorName;
		mEessName   = eessName; 
		mMicrorredName = microrredName; 
		mSample = sample;
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
	public void setHouseCode(String mHouseCode) {this.mHouseCode = mHouseCode;}

	public Long getDate() {return mDate;}
	public void setDate(Long mDate) {this.mDate = mDate;}

	public Integer getResultId() {return mResultId;}
	public void setResultId(Integer mResultId) {this.mResultId = mResultId;}

	public Integer getFeverish() {return mFeverish;}
	public void setFeverish(Integer mFeverish) {this.mFeverish = mFeverish;}

	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}

	public String getSectorName() {return mSectorName;}
	public void setSectorName(String mSectorName) {this.mSectorName = mSectorName;}

	public String getEessName() {return mEessName;}
	public void setEessName(String mEessName) {this.mEessName = mEessName;}

	public String getMicrorredName() {return mMicrorredName;}
	public void setMicrorredName(String mMicrorredName) {this.mMicrorredName = mMicrorredName;}

	public Integer getSample() {return mSample;}
	public void setSample(Integer mSample) {this.mSample = mSample;}

	public String getUserName() {return mUserName;}
	public void setUserName(String mUserName) {this.mUserName = mUserName;}

}
