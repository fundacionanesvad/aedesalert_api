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

public class SortingSchedules {

	private String mStartDate;
	private String mFinishDate;
	private String mAreaName;
	private String mTypeName;
	private String mReconversionScheduleId;
	private String   mTrapLatitude;
	
	
	
	public SortingSchedules(){}
	
	public SortingSchedules(String trapLatitude,String startDate,String finishDate, String areaName, String typeName, String reconversionScheduleId){
		mStartDate  			= startDate;
		mFinishDate 			= finishDate;
		mAreaName   			= areaName;
		mTypeName   			= typeName;
		mReconversionScheduleId = reconversionScheduleId;
		mTrapLatitude=trapLatitude;
	}
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

	public String getReconversionScheduleId() {return mReconversionScheduleId;}
	public void setReconversionScheduleId(String mReconversionScheduleId) {this.mReconversionScheduleId = mReconversionScheduleId;}
	
}
