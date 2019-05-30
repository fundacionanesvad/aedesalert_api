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
import java.util.List;

public class AreaChildsImpl implements Area{

	private Integer     mId;
	private String      mName;
	private String      mParentName;
	private String      mCoords;
	private Integer     mHouses;
	private Integer     mTypeId;
	private BigDecimal  mLatitude;
	private BigDecimal  mLongitude;
	private List<Child> mChilds;
	
	public AreaChildsImpl() {}
	
	public AreaChildsImpl(Integer id, String name, String parentName, String coords, Integer houses, Integer typeId, BigDecimal latitude, BigDecimal longitude, List<Child> childs){
		
		mId     = id;
		mName   = name;
		mParentName = parentName;
		mCoords = coords;
		mHouses = houses;
		mTypeId = typeId;
		mLatitude  = latitude;
		mLongitude = longitude;
		mChilds = childs;
	}

	public Integer getId() {return mId;}
	public void setId(Integer mId) {this.mId = mId;}

	public String getName() {return mName;}
	public void setName(String mName) {this.mName = mName;}
	
	public String getParentName() {return mParentName;}
	public void setParentName(String mParentName) {this.mParentName = mParentName;}

	public String getCoords() {return mCoords;}
	public void setCoords(String mCoords) {this.mCoords = mCoords;}

	public List<Child> getChilds() {return mChilds;}
	public void setChilds(List<Child> mChilds) {this.mChilds = mChilds;}

	public Integer getHouses() {return mHouses;}
	public void setHouses(Integer mHouses) {this.mHouses = mHouses;}
	
	public Integer getTypeId() {return mTypeId;}
	public void setTypeId(Integer mTypeId) {this.mTypeId = mTypeId;}	
	
	public BigDecimal getLatitude() {return mLatitude;}
	public void setLatitude(BigDecimal mLatitude) {this.mLatitude = mLatitude;}

	public BigDecimal getLongitude() {return mLongitude;}
	public void setLongitude(BigDecimal mLongitude) {this.mLongitude = mLongitude;}
}
