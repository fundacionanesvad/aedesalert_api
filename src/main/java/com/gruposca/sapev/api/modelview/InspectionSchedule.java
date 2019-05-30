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
 ******************************************************************************/
package com.gruposca.sapev.api.modelview;

import java.math.BigDecimal;

public class InspectionSchedule{

	private Integer    mId;
	private Long       mStartDate;
	private Long       mFinishDate;
	private Integer    mInspectionSize;
	private BigDecimal mCoverage;
	private Integer    mStateId;
	private Integer    mTypeId;
	private Integer    mAreaId;
	
	public InspectionSchedule(){}
	
	public InspectionSchedule(Integer id,Long startDate, Long finishDate, Integer inspectionSize, BigDecimal coverage, Integer typeId, Integer stateId, Integer areaId){
		
		mStartDate      = startDate;
		mFinishDate     = finishDate;
		mInspectionSize = inspectionSize;
		mCoverage       = coverage;
		mTypeId         = typeId;
		mStateId        = stateId;
		mAreaId         = areaId;
		mId             = id;
	}

	public Long getStartDate() {return mStartDate;}
	public void setStartDate(Long mStartDate) {this.mStartDate = mStartDate;}

	public Long getFinishDate() {return mFinishDate;}
	public void setFinishDate(Long mFinishDate) {this.mFinishDate = mFinishDate;}

	public Integer getInspectionSize() {return mInspectionSize;}
	public void setInspectionSize(Integer mInspectionSize) {this.mInspectionSize = mInspectionSize;}

	public BigDecimal getCoverage() {return mCoverage;}
	public void setCoverage(BigDecimal mCoverage) {this.mCoverage = mCoverage;}

	public Integer getTypeId() {return mTypeId;}
	public void setTypeId(Integer mTypeId) {this.mTypeId = mTypeId;}

	public Integer getStateId() {return mStateId;}
	public void setStateId(Integer mStateId) {this.mStateId = mStateId;}

	public Integer getAreaId() {return mAreaId;}
	public void setAreaId(Integer mAreaId) {this.mAreaId = mAreaId;}

	public Integer getId() {return mId;}
	public void setId(Integer mId) {this.mId = mId;}
	
}
