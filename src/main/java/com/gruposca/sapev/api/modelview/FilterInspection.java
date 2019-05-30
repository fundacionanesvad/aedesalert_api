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
import java.math.BigInteger;

public class FilterInspection {

	private Long      mStartDate;
	private Long      mFinishDate;
	private String    mSize;
	private Integer   mTypeId;
	private Integer   mStateId;
	private String    mAreaName;
	private String    mMicrorredName;
	private Integer   mReconversionScheduleId;
	private BigDecimal   mTrapLatitude;

	
	public FilterInspection(){}
	
	public FilterInspection(Long startDate, Long finishDate, String size, Integer typeId, Integer stateId, String areaName, String microrredName,Integer reconversionScheduleId,BigDecimal trapLatitude){
		mStartDate     = startDate;
		mFinishDate    = finishDate;
		mSize          = size;
		mAreaName      = areaName;
		mTypeId        = typeId;
		mStateId       = stateId;
		mMicrorredName 	= microrredName;
		mReconversionScheduleId	= reconversionScheduleId;
		mTrapLatitude 	= trapLatitude;
	}

	
	public Integer getReconversionScheduleId() {return mReconversionScheduleId;}
	public void setReconversionScheduleId(Integer mReconversionScheduleId) {this.mReconversionScheduleId = mReconversionScheduleId;}

	public BigDecimal getTrapLatitude() {return mTrapLatitude;}
	public void setTrapLatitude(BigDecimal mTrapLatitude) {this.mTrapLatitude = mTrapLatitude;}

	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}

	public Integer getTypeId() {return mTypeId;}
	public void setTypeId(Integer mTypeId) {this.mTypeId = mTypeId;}

	public Long getStartDate() {return mStartDate;}
	public void setStartDate(Long mStartDate) {this.mStartDate = mStartDate;}

	public Long getFinishDate() {return mFinishDate;}
	public void setFinishDate(Long mFinishDate) {this.mFinishDate = mFinishDate;}

	public String getSize() {return mSize;}
	public void setSize(String mSize) {this.mSize = mSize;}

	public Integer getStateId() {return mStateId;}
	public void setStateId(Integer mStateId) {this.mStateId = mStateId;}
	
	public String getMicrorredName() {return mMicrorredName;}
	public void setMicrorredName(String mMicrorredName) {this.mMicrorredName = mMicrorredName;}
			
}
