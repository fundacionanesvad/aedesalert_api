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

public class VisitList {

	private Integer mCount;
	private List<VisitListImpl> mVisits;
	
	public VisitList(){}
	
	public VisitList(Integer count, List<VisitListImpl> visits){
		mCount = count;
		mVisits = visits;		
	}

	public Integer getCount() {return mCount;}
	public void setCount(Integer mCount) {	this.mCount = mCount;}

	public List<VisitListImpl> getVisits() {return mVisits;}
	public void setVisits(List<VisitListImpl> mVisits) {this.mVisits = mVisits;}	
	
}
