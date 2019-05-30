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

import java.math.BigDecimal;
import java.math.BigInteger;

public class FilterPlanVisit {

	private Integer		mAreaName;
	private String		mHouseStreet;
	private String		mHouseNumber;
	private Integer		mResultId;
	private Integer		mPersonsNumber;
	private BigInteger  mSample;
	private BigDecimal	mDose;

	public FilterPlanVisit(){}
	
	public FilterPlanVisit(Integer areaName,String HouseStreet, String HouseNumber, Integer resultId,Integer personsNumber, BigInteger sample, BigDecimal dose){
		mAreaName=		areaName;
		mHouseStreet=	HouseStreet;
		mHouseNumber=	HouseNumber;
		mResultId=		resultId;
		mPersonsNumber=	personsNumber;
		mSample=		sample;
		mDose=			dose;
	}
	public Integer getAreaName() {return mAreaName;}
	public void setAreaName(Integer mAreaName) {this.mAreaName = mAreaName;}
	
	public String getHouseStreet() {return mHouseStreet;}
	public void setHouseStreet(String mHouseStreet) {this.mHouseStreet = mHouseStreet;}

	public String getHouseNumber() {return mHouseNumber;}
	public void setHouseNumber(String mHouseNumber) {this.mHouseNumber = mHouseNumber;}
	
	public Integer getResultId() {return mResultId;}
	public void setResultId(Integer mResultId) {this.mResultId = mResultId;}
	
	public Integer getPersonsNumber() {return mPersonsNumber;}
	public void setPersonsNumber(Integer mPersonsNumber) {this.mPersonsNumber = mPersonsNumber;}

	public BigInteger getSample() {return mSample;}
	public void setSample(BigInteger mSample) {this.mSample = mSample;}

	public BigDecimal getDose() {return mDose;}
	public void setDose(BigDecimal mDose) {this.mDose = mDose;}

}
