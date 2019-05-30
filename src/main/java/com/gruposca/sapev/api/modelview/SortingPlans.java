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

public class SortingPlans {

	private String mPlanSize;
	private String mDate;
	private String mStateName;
	private String mUserName;
	private String mAreaName;

	public SortingPlans(){}
	
	public SortingPlans(String planSize, String date, String stateName, String userName, String areaName){
		mPlanSize  = planSize;
		mDate      = date;
		mUserName  = stateName;
		mAreaName  = userName;
		mStateName = areaName;		
	}

	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}

	public String getPlanSize() {return mPlanSize;}
	public void setPlanSize(String mPlanSize) {this.mPlanSize = mPlanSize;}

	public String getDate() {return mDate;}
	public void setDate(String mDate) {this.mDate = mDate;}

	public String getStateName() {return mStateName;}
	public void setStateName(String mStateName) {this.mStateName = mStateName;}

	public String getUserName() {return mUserName;}
	public void setUserName(String mUserName) {this.mUserName = mUserName;}
	
}
