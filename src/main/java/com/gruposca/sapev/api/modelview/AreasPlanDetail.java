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

public class AreasPlanDetail implements Area{

	private String  mName;
	private Integer mId;
	private Boolean mSubstitute; 
	private List<HouseAreaImpl> mHouses;

	
	public AreasPlanDetail(){}
	
	public AreasPlanDetail(Integer id, String name, List<HouseAreaImpl> houses, Boolean substitute){		
		mName = name;
		mId   = id;	
		mHouses = houses;
		mSubstitute = substitute;
	}
	
	public String getName() { return mName; }
	public void setName(String mName) { this.mName = mName; }

	public Integer getId() { return mId; }
	public void setId(Integer mId) { this.mId = mId; }

	public List<HouseAreaImpl> getHouses() {return mHouses;}
	public void setHouses(List<HouseAreaImpl> mHouses) {this.mHouses = mHouses;}

	public Boolean getSubstitute() {return mSubstitute;}
	public void setSubstitute(Boolean mSubstitute) {this.mSubstitute = mSubstitute;}	

}
