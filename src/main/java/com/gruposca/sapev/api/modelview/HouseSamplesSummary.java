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

public class HouseSamplesSummary {

	private String  mUuid;
	private String  mStreetName;
	private String  mStreetNumber;
	
	public HouseSamplesSummary(){};
	
	public HouseSamplesSummary(String uuid, String streetName, String StreetNumber){		
		mUuid = uuid;
		mStreetName = streetName;
		mStreetNumber = StreetNumber;
	}

	public String getUuid() {return mUuid;}
	public void setUuid(String mUuid) {this.mUuid = mUuid;	}

	public String getStreetName() {	return mStreetName;}
	public void setStreetName(String mStreetName) {this.mStreetName = mStreetName;}

	public String getStreetNumber() {return mStreetNumber;}
	public void setStreetNumber(String mStreetNumber) {this.mStreetNumber = mStreetNumber;	}
	
}
