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

public class PlanAreas {


	private Integer mId;
	private Integer mScheduledHouses;
	private boolean mSubstitute;
	private Integer mPin;

	
	public PlanAreas(){}
	
	public PlanAreas(Integer id, Integer scheduledHouses, boolean substitute){		
		mId              = id;
		mScheduledHouses = scheduledHouses;
		mSubstitute      = substitute;
	}
	
	public PlanAreas(Integer id, Integer scheduledHouses, boolean substitute, Integer pin){		
		mId              = id;
		mScheduledHouses = scheduledHouses;
		mSubstitute      = substitute;
		mPin             = pin;
	}

	public Integer getId() {return mId;}
	public void setId(Integer mId) {this.mId = mId;}

	public Integer getScheduledHouses() {return mScheduledHouses;}
	public void setScheduledHouses(Integer mScheduledHouses) {this.mScheduledHouses = mScheduledHouses;}

	public boolean isSubstitute() {	return mSubstitute;}
	public void setSubstitute(boolean mSubstitute) {this.mSubstitute = mSubstitute;}

	public Integer getPin() {return mPin;}
	public void setPin(Integer mPin) {this.mPin = mPin;}
	
}
