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
import java.math.BigInteger;

public class ScheduleListImpl {

	private Integer    mId;
	private Long       mStartDate;
	private Long       mFinishDate;
	private String     mTypeName;
	private Integer    mAreaId;
	private String     mAreaName;
	private BigInteger mInspections;
	private Integer    mReconversionScheduleId;
	private Integer    mLarvicideId;
	private String     mLarvicideName;
	private BigDecimal   mTrapLatitude;

	public ScheduleListImpl(){}
	
	public ScheduleListImpl(Integer id, Long startDate, Long finishDate, String typeName, Integer areaId, String areaName, BigInteger inspections, Integer reconversionScheduleId, Integer larvicideId, String larvicideName,BigDecimal trapLatitude){
		mId          			= id;
		mStartDate   			= startDate;
		mFinishDate  			= finishDate;
		mTypeName    			= typeName;
		mAreaId      			= areaId;
		mAreaName    			= areaName;
		mInspections 			= inspections;
		mReconversionScheduleId = reconversionScheduleId;
		mLarvicideId			= larvicideId;
		mLarvicideName          = larvicideName;
		mTrapLatitude=trapLatitude;
	}
	public BigDecimal getTrapLatitude() {return mTrapLatitude;}
	public void setTrapLatitude(BigDecimal mTrapLatitude) {this.mTrapLatitude = mTrapLatitude;}
	
	public Long getStartDate() {return mStartDate;}
	public void setStartDate(Long mStartDate) {this.mStartDate = mStartDate;}

	public Long getFinishDate() {return mFinishDate;}
	public void setFinishDate(Long mFinishDate) {this.mFinishDate = mFinishDate;}	

	public Integer getAreaId() {return mAreaId;}
	public void setAreaId(Integer mAreaId) {this.mAreaId = mAreaId;}

	public String getTypeName() {return mTypeName;}
	public void setTypeName(String mTypeName) {this.mTypeName = mTypeName;}

	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}

	public Integer getId() {return mId;}
	public void setId(Integer mId) {this.mId = mId;}

	public BigInteger getInspections() {return mInspections;}
	public void setInspections(BigInteger mInspections) {this.mInspections = mInspections;}

	public Integer getReconversionScheduleId() {return mReconversionScheduleId;}
	public void setReconversionScheduleId(Integer mReconversionScheduleId) {this.mReconversionScheduleId = mReconversionScheduleId;}

	public Integer getLarvicideId() {return mLarvicideId;}
	public void setLarvicideId(Integer mLarvicideId) {this.mLarvicideId = mLarvicideId;}

	public String getLarvicideName() {	return mLarvicideName;}
	public void setLarvicideName(String mLarvicideName) {this.mLarvicideName = mLarvicideName;}	

}
