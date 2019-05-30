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

import java.math.BigDecimal;

public class SamplesMapFocus {

	private String mUuid;
	private Long mDate;
	private String mResult;
	private String mPhase;
	private BigDecimal mLatitude;
	private BigDecimal mLongitude;
	private Integer mAreaId;
	
	public SamplesMapFocus(){}
	
	public SamplesMapFocus(String uuid, Long date, String result, String phase, BigDecimal latitude, BigDecimal longitude, Integer areaId){
		mUuid       = uuid;
		mDate       = date;
		mResult 	= result;
		mPhase      = phase;
		mLatitude   = latitude;
		mLongitude  = longitude;
		mAreaId     = areaId;	
	}

	public String getUuid() {return mUuid;}
	public void setUuid(String mUuid) {this.mUuid = mUuid;}

	public Long getDate() {return mDate;}
	public void setDate(Long mDate) {this.mDate = mDate;}

	public String getResult() {return mResult;}
	public void setResult(String mResult) {this.mResult = mResult;}

	public String getPhase() {return mPhase;}
	public void setPhase(String mPhase) {this.mPhase = mPhase;}

	public BigDecimal getLatitude() {return mLatitude;}
	public void setLatitude(BigDecimal mLatitude) {this.mLatitude = mLatitude;}

	public BigDecimal getLongitude() {return mLongitude;}
	public void setLongitude(BigDecimal mLongitude) {this.mLongitude = mLongitude;}

	public Integer getAreaId() {return mAreaId;}
	public void setAreaId(Integer mAreaId) {this.mAreaId = mAreaId;}
	
}
