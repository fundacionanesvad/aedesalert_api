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

import com.gruposca.sapev.api.connectors.dao.model.Larvicides;

public class PlanDetail {

	private Integer               mInspectionId;
	private Long                  mDate;
	private String                mUserName;
	private String                mStateName;
	private List<AreasPlanDetail> mAreas;
	private Larvicides            mLarvicide;
	
	public PlanDetail(){}

	public PlanDetail(Integer inspectionId, Long date, String userName, String stateName, List<AreasPlanDetail> areas, Larvicides larvicide){
		
		mInspectionId = inspectionId;
		mDate = date;
		mUserName = userName;
		mStateName = stateName;
		mAreas = areas;
		mLarvicide = larvicide;
	}

	public Long getDate() {return mDate;}
	public void setDate(Long mDate) {this.mDate = mDate;}

	public String getUserName() {return mUserName;}
	public void setUserName(String mUserName) {this.mUserName = mUserName;}

	public String getStateName() {return mStateName;}
	public void setStateName(String mStateName) {this.mStateName = mStateName;}

	public List<AreasPlanDetail> getAreas() {return mAreas;}
	public void setAreas(List<AreasPlanDetail> mAreas) {this.mAreas = mAreas;}

	public Integer getInspectionId() {return mInspectionId;}
	public void setInspectionId(Integer mInspectionId) {this.mInspectionId = mInspectionId;}

	public Larvicides getLarvicide() {return mLarvicide;}
	public void setLarvicide(Larvicides mLarvicide) {this.mLarvicide = mLarvicide;}	

}
