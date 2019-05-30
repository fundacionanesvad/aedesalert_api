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

public class VisitListImpl implements Visit{

	private String  mUuid;
	private String  mHouseUuid;
	private String  mHouseCode;
	private Long    mDate;
	private Integer mResultId;
	private String  mResultName;
	private Byte    mFeverish;
	private Integer mAreaId;
	private String  mAreaName;
	private String  mSectorName;
	private String  mEessName;
	private String  mMicrorredName;
	private Boolean mSample;
	private String  mUserName; 
	private String	mHouseStreet;
	private String	mHouseNumber;
	
	public VisitListImpl(){}
	
	public VisitListImpl(String uuid,String houseUuid, String houseCode, String HouseStreet,String HouseNumber,Long date, Integer resultId,String  resultName, Byte feverish, Integer areaId, String areaName, String sectorName, String eessName, String microrredName , Boolean sample, String userName){
		mUuid       = uuid;
		mHouseUuid  = houseUuid;
		mHouseCode  = houseCode;
		mHouseStreet= HouseStreet;
		mHouseNumber= HouseNumber;
		mDate       = date;
		mResultId   = resultId;
		mResultName = resultName;
		mFeverish   = feverish;
		mAreaId     = areaId;
		mAreaName   = areaName;
		mSectorName = sectorName;
		mEessName   = eessName; 
		mMicrorredName = microrredName; 
		mSample        = sample;
		mUserName      = userName;
		
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

	public String getUuid() {return mUuid;}
	public void setUuid(String mUuid) {this.mUuid = mUuid;}

	public String getHouseCode() {return mHouseCode;}
	public void setHouseCode(String mHouseCode) {this.mHouseCode = mHouseCode;}

	public Long getDate() {return mDate;}
	public void setDate(Long mDate) {this.mDate = mDate;}

	public Integer getResultId() {return mResultId;}
	public void setResultId(Integer mResultId) {this.mResultId = mResultId;}

	public Byte getFeverish() {return mFeverish;}
	public void setFeverish(Byte mFeverish) {this.mFeverish = mFeverish;}

	public Integer getAreaId() {return mAreaId;}
	public void setAreaId(Integer mAreaId) {this.mAreaId = mAreaId;}

	public String getHouseUuid() {return mHouseUuid;}
	public void setHouseUuid(String mHouseUuid) {this.mHouseUuid = mHouseUuid;}

	public String getResultName() {return mResultName;}
	public void setResultName(String mResultName) {this.mResultName = mResultName;}

	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}		
	
	public String getSectorName() {return mSectorName;}
	public void setSectorName(String mSectorName) {this.mSectorName = mSectorName;}

	public String getEessName() {return mEessName;}
	public void setEessName(String mEessName) {this.mEessName = mEessName;}	
	
	public String getMicrorredName() {return mMicrorredName;}
	public void setMicrorredName(String mMicrorredName) {this.mMicrorredName = mMicrorredName;}

	public Boolean getSample() {return mSample;}
	public void setSample(Boolean mSample) {this.mSample = mSample;}

	public String getUserName() {return mUserName;}
	public void setUserName(String mUserName) {this.mUserName = mUserName;}
	
}
