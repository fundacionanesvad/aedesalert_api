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

public class SyncElementPlan implements Element{

	
	private Integer  mId;
	private String   mName;
	private Integer  mSort;
	private Integer  mTableId;
	
	public SyncElementPlan(){}
	
	public SyncElementPlan(Integer id, String name, Integer sort, Integer tableId){
		mId       = id;
		mName     = name;
		mSort     = sort;
		mTableId = tableId;
	}

	public Integer getId() {return mId;}
	public void setId(Integer mId) {this.mId = mId;}

	public String getName() {return mName;}
	public void setName(String mName) {this.mName = mName;}

	public Integer getSort() {return mSort;}
	public void setSort(Integer mSort) {this.mSort = mSort;}

	public Integer getTableId() {return mTableId;}
	public void setTableId(Integer mTablerId) {this.mTableId = mTablerId;}	
	
}
