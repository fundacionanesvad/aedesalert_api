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

public class SampleListImpl implements Sample{
	
	private Integer mInspectionId;
	private Long    mDate;
	private String  mAreaName;
	private String  mParentName;
	private Integer mNumSamples;
	
	public SampleListImpl(){}
	
	public SampleListImpl(Integer inspectionId, Long date,  String areaName, String parentName, Integer numSamples){
		mInspectionId = inspectionId;
		mDate         = date;
		mAreaName     = areaName;
		mParentName   = parentName;
		mNumSamples   = numSamples;
	}

	public Integer getInspectionId() {return mInspectionId;}
	public void setInspectionId(Integer mInspectionId) {this.mInspectionId = mInspectionId;}

	public Long getDate() {return mDate;}
	public void setDate(Long mDate) {this.mDate = mDate;}

	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}

	public String getParentName() {return mParentName;}
	public void setParentName(String mParentName) {this.mParentName = mParentName;}

	public Integer getNumSamples() {return mNumSamples;}
	public void setNumSamples(Integer mNumSamples) {this.mNumSamples = mNumSamples;}	
	
}
