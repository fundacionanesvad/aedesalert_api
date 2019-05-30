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

import java.util.List;

public class HousesList {

	private Integer mCount;
	private List<HouseObjectList> mHouses;
	
	public HousesList(){}
	
	public HousesList(Integer count, List<HouseObjectList> houses){
		mCount  = count;
		mHouses = houses;	
	}

	public Integer getCount() {return mCount;}
	public void setCount(Integer mCount) {	this.mCount = mCount;}

	public List<HouseObjectList> getHouses() {return mHouses;}
	public void setHouses(List<HouseObjectList> mHouses) {this.mHouses = mHouses;}
	
}
