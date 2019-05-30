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
import java.util.List;

public class SyncHousePlan implements House{

	private String               mUuid;
	private Integer              mNumber;
	private String               mCode;
	private String               mQrcode;
	private BigDecimal           mLatitude;
	private BigDecimal           mLongitude;
	private String               mStreetName;
	private String               mStreetNumber;
	private Long                 mLastVisitDate;
	private Integer				 mLastVisitResult;
	private Boolean    			 mLastVisitFocus;
	private Boolean    			 mLastVisitFeverish;
	private Integer				 mPersonsNumber;
	private List<Person>         mPersons;
	private Integer				 mLastVisitScheduleId ;

	
	public SyncHousePlan(){}
	
	public SyncHousePlan(String uuid, Integer number, String code, String qrCode, BigDecimal latitude, BigDecimal longitude, String streetName, String streetNumber, Long lastVisitDate, Integer lastVisitResult, Boolean lastVisitFocus,  Boolean lastVisitFeverish, Integer lastVisitScheduleId, Integer personsNumber, List<Person> persons){
		mUuid                = uuid;
		mNumber              = number;
		mCode                = code;
		mQrcode              = qrCode;
		mLatitude            = latitude;
		mLongitude           = longitude;
		mStreetName          = streetName;
		mStreetNumber        = streetNumber;
		mLastVisitDate       = lastVisitDate;
		mLastVisitResult     = lastVisitResult;
		mLastVisitFeverish   = lastVisitFeverish;
		mLastVisitFocus      = lastVisitFocus;
		mLastVisitScheduleId = lastVisitScheduleId;
		mPersonsNumber       = personsNumber;
		mPersons             = persons;		
	}

	public String getUuid() {return mUuid;}
	public void setUuid(String mUuid) {this.mUuid = mUuid;}

	public Integer getNumber() {return mNumber;}
	public void setNumber(Integer mNumber) {this.mNumber = mNumber;}

	public String getCode() {return mCode;}
	public void setCode(String mCode) {this.mCode = mCode;}

	public BigDecimal getLatitude() {return mLatitude;}
	public void setLatitude(BigDecimal mLatitude) {this.mLatitude = mLatitude;}

	public BigDecimal getLongitude() {return mLongitude;}
	public void setLongitude(BigDecimal mLongitude) {this.mLongitude = mLongitude;}

	public String getStreetName() {return mStreetName;}
	public void setStreetName(String mStreetName) {this.mStreetName = mStreetName;}

	public String getStreetNumber() {return mStreetNumber;}
	public void setStreetNumber(String mStreetNumber) {this.mStreetNumber = mStreetNumber;	}

	public List<Person> getPersons() {return mPersons;}
	public void setPersons(List<Person> mPersons) {this.mPersons = mPersons;}

	public String getQrcode() {return mQrcode;}
	public void setQrcode(String mQrcode) {this.mQrcode = mQrcode;}

	public Long getLastVisitDate() {return mLastVisitDate;}
	public void setLastVisitDate(Long mLastVisitDate) {this.mLastVisitDate = mLastVisitDate;}

	public Integer getLastVisitResult() {return mLastVisitResult;}
	public void setLastVisitResult(Integer mLastVisitResult) {this.mLastVisitResult = mLastVisitResult;}

	public Integer getPersonsNumber() {return mPersonsNumber;}
	public void setPersonsNumber(Integer mPersonsNumber) {	this.mPersonsNumber = mPersonsNumber;}

	public Boolean getLastVisitFocus() {return mLastVisitFocus;}
	public void setLastVisitFocus(Boolean mLastVisitFocus) {this.mLastVisitFocus = mLastVisitFocus;}

	public Boolean getLastVisitFeverish() {return mLastVisitFeverish;}
	public void setLastVisitFeverish(Boolean mLastVisitFeverish) {this.mLastVisitFeverish = mLastVisitFeverish;}

	public Integer getLastVisitScheduleId() {return mLastVisitScheduleId;}
	public void setLastVisitScheduleId(Integer mLastVisitScheduleId) {this.mLastVisitScheduleId = mLastVisitScheduleId;}	
	
}
