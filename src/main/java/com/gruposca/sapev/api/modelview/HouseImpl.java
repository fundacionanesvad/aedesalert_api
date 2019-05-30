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

import java.math.BigDecimal;

public class HouseImpl implements House{

	private String     mUuid;
	private Integer    mNumber;
	private String     mCode;
	private String     mQrcode;
	private BigDecimal mLatitude;
	private BigDecimal mLongitude;
	private String     mStreetName;
	private String     mStreetNumber;
	private Integer    mAreaId;
	private String     mAreaName;
	private String     mAreaCoords;
	private Integer	   mPersonsNumber;	

	
	public HouseImpl(){}
	
	public HouseImpl(String uuid, Integer number, String code, String qrcode, BigDecimal latitude, BigDecimal longitude, String streetName, String streetNumber, Integer areaId, String areaName, String areaCoords, Integer personsNumber){
		mUuid          = uuid;
		mNumber        = number;
		mCode          = code;
		mQrcode        = qrcode;
		mLatitude      = latitude;
		mLongitude     = longitude;
		mStreetName    = streetName;
		mStreetNumber  = streetNumber;
		mAreaId        = areaId;
		mAreaName      = areaName;
		mAreaCoords    = areaCoords;
		mPersonsNumber = personsNumber;
	}

	public String getUuid() {return mUuid;}
	public void setUuid(String mUuid) {this.mUuid = mUuid;}

	public Integer getNumber() {return mNumber;}
	public void setNumber(Integer mNumber) {this.mNumber = mNumber;}

	public String getCode() {return mCode;}
	public void setCode(String mCode) {this.mCode = mCode;}

	public String getQrcode() {return mQrcode;}
	public void setQrcode(String mQrcode) {this.mQrcode = mQrcode;}

	public BigDecimal getLatitude() {return mLatitude;}
	public void setLatitude(BigDecimal mLatitude) {this.mLatitude = mLatitude;}

	public BigDecimal getLongitude() {return mLongitude;}
	public void setLongitude(BigDecimal mLongitude) {this.mLongitude = mLongitude;	}

	public String getStreetName() {return mStreetName;}
	public void setStreetName(String mStreetName) {this.mStreetName = mStreetName;}

	public String getStreetNumber() {return mStreetNumber;}
	public void setStreetNumber(String mStreetNumber) {this.mStreetNumber = mStreetNumber;}

	public Integer getAreaId() {return mAreaId;}
	public void setAreaId(Integer mAreaId) {this.mAreaId = mAreaId;}

	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}

	public String getAreaCoords() {return mAreaCoords;}
	public void setAreaCoords(String mAreaCoords) {this.mAreaCoords = mAreaCoords;}

	public Integer getPersonsNumber() {return mPersonsNumber;}
	public void setPersonsNumber(Integer mPersonsNumber) {	this.mPersonsNumber = mPersonsNumber;}		
	
}
