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

public class PlanInspectionSearchList {

	private Integer       mId;
	private Integer       mPlanSize;
	private Long          mDate;
	private String        mUserName;
	private String        mStateName;
	private String        mAreaName;
	private Integer       mStateId;
	
	public PlanInspectionSearchList(){}
	
	public PlanInspectionSearchList(Integer id, Long date, String stateName, Integer size, String userName, String areaName, Integer stateId){		
		mId           = id;
		mPlanSize     = size;
		mDate         = date;
		mUserName     = userName;
		mStateName    = stateName;
		mAreaName     = areaName;	
		mStateId      = stateId;
	
	}

	public Integer getPlanSize() {return mPlanSize;}
	public void setPlanSize(Integer mPlanSize) {this.mPlanSize = mPlanSize;}

	public Long getDate() {return mDate;}
	public void setDate(Long mDate) {this.mDate = mDate;}	

	public Integer getId() {return mId;}
	public void setId(Integer mId) {this.mId = mId;}	

	public String getUserName() {return mUserName;}
	public void setUserName(String mUserName) {this.mUserName = mUserName;}

	public String getStateName() {return mStateName;}
	public void setStateName(String mStateName) {this.mStateName = mStateName;}

	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}

	public Integer getStateId() {return mStateId;}
	public void setStateId(Integer mStateId) {	this.mStateId = mStateId;}
	
}
