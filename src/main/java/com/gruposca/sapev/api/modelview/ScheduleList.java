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

public class ScheduleList implements Inspection{

	private Integer mCount;
	private List<ScheduleListImpl> mSchedules;
	
	public ScheduleList(){}
	
	public ScheduleList(Integer count, List<ScheduleListImpl> schedules){
		mCount = count;
		mSchedules = schedules;		
	}

	public Integer getCount() {return mCount;}
	public void setCount(Integer mCount) {	this.mCount = mCount;}

	public List<ScheduleListImpl> getSchedules() {return mSchedules;}
	public void setSchedules(List<ScheduleListImpl> mSchedules) {this.mSchedules = mSchedules;}	
}
