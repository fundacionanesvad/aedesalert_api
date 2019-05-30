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

public class ParamFilterReports {
	
	private FilterReports   mFilter;
	private SortingReport   mSorting;
	private Integer         mCount;
	private Integer         mPage;

	
	public ParamFilterReports(){}
	
	public ParamFilterReports(FilterReports filter, SortingReport sorting, Integer count, Integer page){
		mFilter   = filter;
		mSorting  = sorting;
		mCount    = count;
		mPage     = page;		
	}

	public FilterReports getFilter() {return mFilter;}
	public void setFilter(FilterReports mFilter) {this.mFilter = mFilter;}

	public SortingReport getSorting() {return mSorting;}
	public void setSorting(SortingReport mSorting) {this.mSorting = mSorting;}

	public Integer getCount() {return mCount;}
	public void setCount(Integer mCount) {this.mCount = mCount;}

	public Integer getPage() {return mPage;}
	public void setPage(Integer mPage) {this.mPage = mPage;}	
	
}
