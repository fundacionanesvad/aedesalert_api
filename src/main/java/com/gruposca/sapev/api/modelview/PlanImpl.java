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

import java.util.List;

public class PlanImpl implements Plan{
	
	private Integer         mPlanSize;
	private Long            mDate;
	private Integer         mUserId;
	private Integer         mInspectionId;
	private Integer         mStateId;
	private List<PlanAreas> mAreas;
	private Byte		    mHouseInterval;
	private Byte            mHouseIni;
	
	public PlanImpl(){}
	
	public PlanImpl(Integer size, Long date, Integer userId, Integer inspectionId, Integer stateId, List<PlanAreas> areas, Byte houseInterval, Byte houseIni){		
		mPlanSize      = size;
		mDate          = date;
		mUserId        = userId;
		mInspectionId  = inspectionId;
		mStateId       = stateId;
		mAreas         = areas;		
		mHouseInterval = houseInterval;
		mHouseIni      = houseIni;
	}

	public Integer getPlanSize() {return mPlanSize;}
	public void setPlanSize(Integer mPlanSize) {this.mPlanSize = mPlanSize;}

	public Long getDate() {return mDate;}
	public void setDate(Long mDate) {this.mDate = mDate;}

	public Integer getUserId() {return mUserId;}
	public void setUserId(Integer mUserId) {this.mUserId = mUserId;}

	public List<PlanAreas> getAreas() {return mAreas;}
	public void setAreas(List<PlanAreas> mAreas) {this.mAreas = mAreas;}

	public Integer getInspectionId() {return mInspectionId;}
	public void setInspectionId(Integer mInspectionId) {this.mInspectionId = mInspectionId;}

	public Integer getStateId() {return mStateId;}
	public void setStateId(Integer mStateId) {this.mStateId = mStateId;}

	public Byte getHouseIni() {return mHouseIni;}
	public void setHouseIni(Byte mHouseIni) {this.mHouseIni = mHouseIni;}

	public Byte getHouseInterval() {return mHouseInterval;}
	public void setHouseInterval(Byte mHouseInterval) {this.mHouseInterval = mHouseInterval;}	

}
