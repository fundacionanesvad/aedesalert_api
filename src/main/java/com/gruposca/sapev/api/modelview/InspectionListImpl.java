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

import java.math.BigDecimal;

public class InspectionListImpl{

	private Integer mId;
	private Long    mStartDate;
	private Long    mFinishDate;
	private Integer mInspectionSize;
	private String  mTypeName;
	private String  mStateName;
	private Integer mAreaId;
	private String  mAreaName;
	private String  mMicrorredName;
	private Integer   mReconversionScheduleId;
	private BigDecimal   mTrapLatitude;
	
	public InspectionListImpl(){}
	 
	public InspectionListImpl(Integer id, Long starDate, Long finishDate, Integer inspectionSize, String typeName, String stateName, Integer areaId, String areaName, String microrredName,Integer reconversionScheduleId,BigDecimal trapLatitude){
		
		mId             = id;
		mStartDate      = starDate;
		mFinishDate     = finishDate;
		mInspectionSize = inspectionSize;
		mTypeName       = typeName;
		mStateName      = stateName;
		mAreaId         = areaId;
		mAreaName       = areaName;
		mMicrorredName  = microrredName;
		mReconversionScheduleId	= reconversionScheduleId;
		mTrapLatitude 	= trapLatitude;
	}
	public Integer getReconversionScheduleId() {return mReconversionScheduleId;}
	public void setReconversionScheduleId(Integer mReconversionScheduleId) {this.mReconversionScheduleId = mReconversionScheduleId;}

	public BigDecimal getTrapLatitude() {return mTrapLatitude;}
	public void setTrapLatitude(BigDecimal mTrapLatitude) {this.mTrapLatitude = mTrapLatitude;}
	
	public Integer getId() {return mId;}
	public void setId(Integer mId) {this.mId = mId;}
	
	public Long getStartDate() {return mStartDate;}
	public void setStartDate(Long mStartDate) {this.mStartDate = mStartDate;}

	public Long getFinishDate() {return mFinishDate;}
	public void setFinishDate(Long mFinishDate) {this.mFinishDate = mFinishDate;}

	public Integer getInspectionSize() {return mInspectionSize;}
	public void setInspectionSize(Integer mInspectionSize) {this.mInspectionSize = mInspectionSize;}

	public String getTypeName() {return mTypeName;}
	public void setTypeName(String mTypeName) {this.mTypeName = mTypeName;}

	public String getStateName() {return mStateName;}
	public void setStateName(String mStateName) {this.mStateName = mStateName;}

	public Integer getAreaId() {return mAreaId;}
	public void setAreaId(Integer mAreaId) {this.mAreaId = mAreaId;}

	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}
	
	public String getMicrorredName() {return mMicrorredName;}
	public void setMicrorredName(String mMicrorredName) {this.mMicrorredName = mMicrorredName;}
}
