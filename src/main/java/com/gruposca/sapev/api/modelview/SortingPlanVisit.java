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

public class SortingPlanVisit {

	private String		mAreaName;
	private String		mHouseStreet;
	private String		mHouseNumber;
	private String		mResultId;
	private String		mPersonsNumber;
	private String   	mSample;
	private String		mDose;
	
	public SortingPlanVisit(){}
	
	public SortingPlanVisit(String areaName,String houseStreet, String houseNumber, String resultId,String personsNumber, String sample, String dose){
		mAreaName=		areaName;
		mHouseStreet=	houseStreet;
		mHouseNumber=	houseNumber;
		mResultId=		resultId;
		mPersonsNumber=	personsNumber;
		mSample=		sample;
		mDose=			dose;
	}
	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}
	
	public String getHouseStreet() {return mHouseStreet;}
	public void setHouseStreet(String mHouseStreet) {this.mHouseStreet = mHouseStreet;}

	public String getHouseNumber() {return mHouseNumber;}
	public void setHouseNumber(String mHouseNumber) {this.mHouseNumber = mHouseNumber;}
	
	public String getResultId() {return mResultId;}
	public void setResultId(String mResultId) {this.mResultId = mResultId;}
	
	public String getPersonsNumber() {return mPersonsNumber;}
	public void setPersonsNumber(String mPersonsNumber) {this.mPersonsNumber = mPersonsNumber;}

	public String getSample() {return mSample;}
	public void setSample(String mSample) {this.mSample = mSample;}

	public String getDose() {return mDose;}
	public void setDose(String mDose) {this.mDose = mDose;}

}
