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

public class InspectionImpl implements Inspection{

	
	private Long       mStartDate;
	private Long       mFinishDate;
	private Integer    mInspectionSize;
	private BigDecimal mCoverage; 
	private Integer    mTypeId;
	private Integer    mStateId;
	private Integer    mAreaId;
	private Integer    mScheduleId;
	private Integer    mLarvicideId;
	private BigDecimal mTrapLatitude; 
	private BigDecimal mTrapLongitude; 
	private Integer	   mTrapId;
	private Long       mTrapDate;
	private String	   mTrapCode;
	private Integer    mTrapEggs;
	private Integer	   mTrapWeek;


	
	public InspectionImpl(){}
	
	public InspectionImpl(Long startDate, Long finishDate, Integer inspectionSize, BigDecimal coverage, Integer typeId, Integer stateId, Integer areaId, Integer scheduleId, Integer larvicideId, BigDecimal trapLatitude, BigDecimal trapLongitude, Integer trapId, Long trapDate, String trapCode, Integer trapEggs, Integer trapWeek){
		
		mStartDate      = startDate;
		mFinishDate     = finishDate;
		mInspectionSize = inspectionSize;
		mCoverage       = coverage;
		mTypeId         = typeId;
		mStateId        = stateId;
		mAreaId         = areaId;
		mScheduleId     = scheduleId;
		mLarvicideId    = larvicideId;
		mTrapLatitude   = trapLatitude;
		mTrapLongitude  = trapLongitude;
		mTrapId			= trapId;
		mTrapDate		= trapDate;
		mTrapCode		= trapCode;
		mTrapEggs		= trapEggs;
		mTrapWeek		= trapWeek;
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

	public Integer getScheduleId() {return mScheduleId;}
	public void setScheduleId(Integer mScheduleId) {this.mScheduleId = mScheduleId;}

	public Integer getLarvicideId() {return mLarvicideId;}
	public void setLarvicideId(Integer mLarvicideId) {this.mLarvicideId = mLarvicideId;}

	public BigDecimal getTrapLatitude() {return mTrapLatitude;}
	public void setTrapLatitude(BigDecimal mTrapLatitude) {this.mTrapLatitude = mTrapLatitude;}

	public BigDecimal getTrapLongitude() {return mTrapLongitude;}
	public void setTrapLongitude(BigDecimal mTrapLongitude) {this.mTrapLongitude = mTrapLongitude;}
	
	public Integer getTrapId() {return mTrapId;}
	public void setTrapId(Integer mTrapId) {this.mTrapId = mTrapId;}
	
	public Long getTrapDate() {return mTrapDate;}
	public void setTrapDate(Long mTrapDate) {this.mTrapDate = mTrapDate;}
	
	public String getTrapCode() {return mTrapCode;}
	public void setTrapCode(String mTrapCode) {this.mTrapCode = mTrapCode;}
	
	public Integer getTrapEggs() {return mTrapEggs;}
	public void setTrapEggs(Integer mTrapEggs) {this.mTrapEggs = mTrapEggs;}
	
	public Integer getTrapWeek() {return mTrapWeek;}
	public void setTrapWeek(Integer mTrapWeek) {this.mTrapWeek = mTrapWeek;}
}
