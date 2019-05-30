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

public class AreaInspectionListImpl implements Inspection{
	private Integer mId;
	private Long    mStartDate;
	private Long    mFinishDate;
	private Integer mInspectionSize;
	private String  mTypeName;
	private String  mStateName;
	
	public AreaInspectionListImpl(){}
	 
	public AreaInspectionListImpl(Integer id, Long starDate, Long finishDate, Integer inspectionSize, String typeName, String stateName){
		
		mId             = id;
		mStartDate      = starDate;
		mFinishDate     = finishDate;
		mInspectionSize = inspectionSize;
		mTypeName       = typeName;
		mStateName      = stateName;		
	}

	public Integer getId() {return mId;}
	public void setId(Integer mId) {this.mId = mId;}
	
	public Long getStartDate() {return mStartDate;}
	public void setStartDate(Long mStartDate) {this.mStartDate = mStartDate;}

	public Long getFinishDate() {return mFinishDate;}
	public void setFinishDate(Long mFinishDate) {this.mFinishDate = mFinishDate;}

	public Integer getInspectionSize() {return mInspectionSize;}
	public void setInspectionSize(Integer mInspectionSize) {this.mInspectionSize = mInspectionSize;}

	public String getTypeName() {return mTypeName;}
	public void setTypeName(String mTypeName) {this.mTypeName = mTypeName;}

	public String getStateName() {return mStateName;}
	public void setStateName(String mStateName) {this.mStateName = mStateName;}
}
