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

public class ParamFilterUsers {
	
	private FilterUsers mFilter;
	private SortingUser mSorting;
	private Integer     mCount;
	private Integer     mPage;

	
	public ParamFilterUsers(){}
	
	public ParamFilterUsers(FilterUsers filter, SortingUser sorting, Integer count, Integer page){
		mFilter   = filter;
		mSorting  = sorting;
		mCount    = count;
		mPage     = page;		
	}

	public FilterUsers getFilter() {return mFilter;}
	public void setFilter(FilterUsers mFilter) {this.mFilter = mFilter;}

	public SortingUser getSorting() {return mSorting;}
	public void setSorting(SortingUser mSorting) {this.mSorting = mSorting;}

	public Integer getCount() {return mCount;}
	public void setCount(Integer mCount) {this.mCount = mCount;}

	public Integer getPage() {return mPage;}
	public void setPage(Integer mPage) {this.mPage = mPage;}	
	
}
