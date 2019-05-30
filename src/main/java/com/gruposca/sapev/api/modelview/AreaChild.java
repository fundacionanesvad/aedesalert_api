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

public class AreaChild {

	private Integer mId;
	private String  mCode;
	private String  mName;
	private Integer mHouses;
	private String  mCoords;
	private Integer mTypeId;
	private BigDecimal mLatitude;
	private BigDecimal mLongitude;
	private Integer mParentId;
	
	public AreaChild() {}
	
	public AreaChild(Integer id, String code, String name, Integer houses, String coords , Integer typeId, BigDecimal latitude, BigDecimal longitude, Integer parentId){		
		mId        = id;
		mCode      = code;
		mName      = name;
		mHouses    = houses;
		mCoords    = coords;
		mTypeId    = typeId;
		mLatitude  = latitude;
		mLongitude = longitude;
		mParentId  = parentId;
	}
	
	public Integer getId() {return mId;}
	public void setId(Integer mId) {this.mId = mId;}

	public String getCode() {return mCode;}
	public void setCode(String mCode) {this.mCode = mCode;}

	public String getName() {return mName;}
	public void setName(String mName) {this.mName = mName;}

	public Integer getHouses() {return mHouses;}
	public void setHouses(Integer mHouses) {this.mHouses = mHouses;}

	public String getCoords() {return mCoords;}
	public void setCoords(String mCoords) {this.mCoords = mCoords;}

	public Integer getTypeId() {return mTypeId;}
	public void setTypeId(Integer mTypeId) {this.mTypeId = mTypeId;}

	public BigDecimal getLatitude() {return mLatitude;}
	public void setLatitude(BigDecimal mLatitude) {this.mLatitude = mLatitude;}

	public BigDecimal getLongitude() {return mLongitude;}
	public void setLongitude(BigDecimal mLongitude) {this.mLongitude = mLongitude;}

	public Integer getParentId() {return mParentId;}
	public void setParentId(Integer mParentId) {this.mParentId = mParentId;}
	
}
