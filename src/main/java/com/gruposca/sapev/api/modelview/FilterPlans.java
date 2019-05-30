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

public class FilterPlans {

	private String  mPlanSize;
	private Long    mDate;
	private String  mUserName;
	private Integer mStateId;
	private String  mAreaName;
	
	public FilterPlans(){}
	
	public FilterPlans(String planSize, Long date, String userName, Integer stateId, String areaName){
		mPlanSize     = planSize;
		mAreaName     = areaName;
		mDate         = date;
		mUserName     = userName;
		mStateId      = stateId;		
	}

	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}

	public String getPlanSize() {return mPlanSize;}
	public void setPlanSize(String mPlanSize) {this.mPlanSize = mPlanSize;}

	public Long getDate() {return mDate;}
	public void setDate(Long mDate) {this.mDate = mDate;}

	public String getUserName() {return mUserName;}
	public void setUserName(String mUserName) {this.mUserName = mUserName;}

	public Integer getStateId() {return mStateId;}
	public void setStateId(Integer mStateId) {this.mStateId = mStateId;}

}
