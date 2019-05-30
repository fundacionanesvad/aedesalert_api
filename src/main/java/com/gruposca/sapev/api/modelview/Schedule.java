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

public class Schedule {
	
	private Long    mStartDate;
	private Long    mFinishDate;
	private Integer mTypeId;
	private Integer mAreaId;
	private Integer mReconversionScheduleId;
	private Integer mLarvicideId;
	
	public Schedule(){}
	
	public Schedule(Long startDate, Long finishDate, Integer typeId, Integer areaId, Integer reconversionScheduleId, Integer larvicideId){		
		mStartDate   			= startDate;
		mFinishDate  			= finishDate;
		mTypeId      			= typeId;
		mAreaId                 = areaId;
		mReconversionScheduleId = reconversionScheduleId;
		mLarvicideId            = larvicideId;
	}
	public Integer getTypeId() {return mTypeId;}
	public void setTypeId(Integer mTypeId) {this.mTypeId = mTypeId;}

	public Integer getAreaId() {return mAreaId;}
	public void setAreaId(Integer mAreaId) {this.mAreaId = mAreaId;}

	public Long getStartDate() {return mStartDate;}
	public void setStartDate(Long mStartDate) {this.mStartDate = mStartDate;}

	public Long getFinishDate() {return mFinishDate;}
	public void setFinishDate(Long mFinishDate) {this.mFinishDate = mFinishDate;}

	public Integer getReconversionScheduleId() {return mReconversionScheduleId;}
	public void setReconversionScheduleId(Integer mReconversionScheduleId) {this.mReconversionScheduleId = mReconversionScheduleId;}

	public Integer getLarvicideId() {return mLarvicideId;}
	public void setLarvicideId(Integer mLarvicideId) {this.mLarvicideId = mLarvicideId;}
	
}
