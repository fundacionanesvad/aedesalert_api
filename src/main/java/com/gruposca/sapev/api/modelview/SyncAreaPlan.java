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

public class SyncAreaPlan implements Area{

	private Integer     mId;
	private String      mName;
	private String      mUbigeoCode;
	private List<House> mHouses;
	private boolean     mSubstitute;
	private Integer     mPin;
	private String      mSector;
	
	public SyncAreaPlan(){}
	
	public SyncAreaPlan(Integer id, String name, String ubigeoCode, List<House> houses, boolean substitute, Integer pin,String sector){
		mId         = id;
		mName       = name;
		mUbigeoCode = ubigeoCode;
		mHouses     = houses;
		mSubstitute = substitute;
		mPin        = pin;
		mSector		=sector;
	}

	public String getSector() {
		return mSector;
	}

	public void setSector(String mSector) {
		this.mSector = mSector;
	}

	public Integer getId() {return mId;}
	public void setId(Integer mId) {this.mId = mId;}

	public String getName() {return mName;}
	public void setName(String mName) {this.mName = mName;}

	public List<House> getHouses() {return mHouses;}
	public void setHouses(List<House> mHouses) {this.mHouses = mHouses;}

	public String getUbigeoCode() {return mUbigeoCode;}
	public void setUbigeoCode(String mUbigeoCode) {this.mUbigeoCode = mUbigeoCode;}

	public boolean isSubstitute() {return mSubstitute;}
	public void setSubstitute(boolean mSubstitute) {this.mSubstitute = mSubstitute;}

	public Integer getPin() {return mPin;}
	public void setPin(Integer mPin) {this.mPin = mPin;}
	
}
