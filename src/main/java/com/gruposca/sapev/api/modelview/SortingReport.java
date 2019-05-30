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

public class SortingReport {
	
	private String   mDate;
	private String   mAreaName;
	private String   mStartDate;
	private String   mFinishDate;
	private String   mDataType;
	private String   mDetailLevel;
	private String   mCreateUserId;	
	private String   mName;
	private String   mReconversionScheduleId;
	private String   mTrapLatitude;
	
	public SortingReport(){}
	
	public SortingReport(String date,String name, String areaName, String startDate, String finishDate, String dataType, String detailLevel, String createUserId,String reconversionScheduleId,String trapLatitude ){
		mDate          = date;
		mAreaName      = areaName;
		mStartDate     = startDate;
		mFinishDate    = finishDate;
		mDataType      = dataType;
		mDetailLevel   = detailLevel;
		mCreateUserId  = createUserId;	
		mName          = name;
		mReconversionScheduleId	= reconversionScheduleId;
		mTrapLatitude 	= trapLatitude;
	}

	
	public String getReconversionScheduleId() {return mReconversionScheduleId;}
	public void setReconversionScheduleId(String mReconversionScheduleId) {this.mReconversionScheduleId = mReconversionScheduleId;}

	public String getTrapLatitude() {return mTrapLatitude;}
	public void setTrapLatitude(String mTrapLatitude) {this.mTrapLatitude = mTrapLatitude;}

	public String getDate() {return mDate;}
	public void setDate(String mDate) {this.mDate = mDate;}

	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}

	public String getStartDate() {return mStartDate;}
	public void setStartDate(String mStartDate) {this.mStartDate = mStartDate;}

	public String getFinishDate() {return mFinishDate;}
	public void setFinishDate(String mFinishDate) {this.mFinishDate = mFinishDate;}

	public String getDataType() {return mDataType;}
	public void setDataType(String mDataType) {this.mDataType = mDataType;}

	public String getDetailLevel() {return mDetailLevel;}
	public void setDetailLevel(String mDetailLevel) {this.mDetailLevel = mDetailLevel;}

	public String getName() {return mName;}
	public void setName(String mName) {this.mName = mName;}

	public String getCreateUserId() {return mCreateUserId;}
	public void setCreateUserId(String mCreateUserId) {this.mCreateUserId = mCreateUserId;}
	
}
