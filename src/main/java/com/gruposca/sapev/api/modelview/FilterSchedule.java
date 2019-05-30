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

public class FilterSchedule {

	private Long      mStartDate;
	private Long      mFinishDate;
	private String    mAreaName;
	private Integer   mTypeId;
	private Integer   mReconversionScheduleId;
	private BigDecimal   mTrapLatitude;
	
	public FilterSchedule(){}
	
	public FilterSchedule(Long startDate, Long finishDate,  String areaName, Integer typeId, Integer reconversionScheduleId,BigDecimal trapLatitude){
		mStartDate  			= startDate;
		mFinishDate 			= finishDate;
		mAreaName   			= areaName;
		mTypeId     			= typeId;
		mReconversionScheduleId = reconversionScheduleId;
		mTrapLatitude=trapLatitude;
	}
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

	public Integer getReconversionScheduleId() {return mReconversionScheduleId;}
	public void setReconversionScheduleId(Integer mReconversionScheduleId) {this.mReconversionScheduleId = mReconversionScheduleId;}
	
	
}
