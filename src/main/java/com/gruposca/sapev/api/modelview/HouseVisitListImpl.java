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

public class HouseVisitListImpl implements Visit{
	
	private String  mUuid;
	private Long    mDate;
	private String  mResultName;
	private Byte    mFeverish;

	public HouseVisitListImpl(){}
	
	public HouseVisitListImpl(String uuid, Long date, String resultName, Byte feverish){
		mUuid       = uuid;
		mDate       = date;
		mResultName = resultName;
		mFeverish   = feverish;		
	}

	public String getUuid() {return mUuid;}
	public void setUuid(String mUuid) {this.mUuid = mUuid;}

	public Long getDate() {return mDate;}
	public void setDate(Long mDate) {this.mDate = mDate;}

	public Byte getFeverish() {return mFeverish;}	
	public void setFeverish(Byte mFeverish) {this.mFeverish = mFeverish;}

	public String getResultName() {return mResultName;}
	public void setResultName(String mResultName) {this.mResultName = mResultName;}	
	
}
