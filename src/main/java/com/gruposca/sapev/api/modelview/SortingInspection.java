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

public class SortingInspection {

	private String mStartDate;
	private String mFinishDate;
	private String mSize;
	private String mTypeName;
	private String mStateName;
	private String mAreaName;
	private String mMicrorredName;
	private String   mReconversionScheduleId;
	private String   mTrapLatitude;
	
	public SortingInspection(){}
	
	public SortingInspection(String startDate,String finishDate, String size, String typeName, String stateName, String areaName, String microrredName,String reconversionScheduleId,String trapLatitude ){
		mStartDate     = startDate;
		mFinishDate    = finishDate;
		mSize          = size;
		mAreaName      = areaName;
		mTypeName      = typeName;
		mStateName     = stateName;
		mMicrorredName = microrredName;
		mReconversionScheduleId	= reconversionScheduleId;
		mTrapLatitude 	= trapLatitude;
	}

	
	public String getReconversionScheduleId() {return mReconversionScheduleId;}
	public void setReconversionScheduleId(String mReconversionScheduleId) {this.mReconversionScheduleId = mReconversionScheduleId;}

	public String getTrapLatitude() {return mTrapLatitude;}
	public void setTrapLatitude(String mTrapLatitude) {this.mTrapLatitude = mTrapLatitude;}
	
	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}
	
	public String getStartDate() {	return mStartDate;}
	public void setStartDate(String mStartDate) {this.mStartDate = mStartDate;}

	public String getFinishDate() {return mFinishDate;}
	public void setFinishDate(String mFinishDate) {this.mFinishDate = mFinishDate;}

	public String getTypeName() {return mTypeName;}
	public void setTypeName(String mTypeName) {this.mTypeName = mTypeName;	}

	public String getSize() {return mSize;}
	public void setSize(String mSize) {this.mSize = mSize;}

	public String getStateName() {	return mStateName;}
	public void setStateName(String mStateName) {this.mStateName = mStateName;}
		
	public String getMicrorredName() {return mMicrorredName;}
	public void setMicrorredName(String mMicrorredName) {this.mMicrorredName = mMicrorredName;}
}
