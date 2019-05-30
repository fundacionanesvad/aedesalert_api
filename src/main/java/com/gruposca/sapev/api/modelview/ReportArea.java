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

public class ReportArea {
		
	private Integer mId;
	private Integer mAreaId;
	private Long    mDate;
	private String  mName;
	private Long    mStartDate;
	private Long    mFinishDate;
	private Integer mDataType;
	private Integer mDetailLevel;
	private Integer mCreateUserId;

	public ReportArea(){}
	
	public ReportArea (Integer id, Long date, Integer areaId,  String name, Long startDate, Long finishDate, Integer dataType, Integer detailLevel, Integer createUserId){
		mId = id;
		mAreaId = areaId;
		mDate = date;
		mName = name;
		mStartDate = startDate;
		mFinishDate = finishDate;
		mDataType = dataType;
		mDetailLevel = detailLevel;
		mCreateUserId = createUserId;
	}

	public Integer getId() {return mId;}
	public void setId(Integer mId) {this.mId = mId;}

	public Integer getAreaId() {return mAreaId;}
	public void setAreaId(Integer mAreaId) {this.mAreaId = mAreaId;}
	
	public Long getDate() {return mDate;}
	public void setDate(Long mDate) {this.mDate = mDate;}

	public String getName() {return mName;}
	public void setName(String mName) {	this.mName = mName;}

	public Long getStartDate() {return mStartDate;}
	public void setStartDate(Long mStartDate) {this.mStartDate = mStartDate;}

	public Long getFinishDate() {return mFinishDate;}
	public void setFinishDate(Long mFinishDate) {this.mFinishDate = mFinishDate;}

	public Integer getDataType() {return mDataType;}
	public void setDataType(Integer mDataType) {this.mDataType = mDataType;}

	public Integer getDetailLevel() {return mDetailLevel;}
	public void setDetailLevel(Integer mDetailLevel) {	this.mDetailLevel = mDetailLevel;}

	public Integer getCreateUserId() {return mCreateUserId;}
	public void setCreateUserId(Integer mCreateUserId) {this.mCreateUserId = mCreateUserId;}	
	
}
