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

public class HouseSyncImpl implements House{

	private String        uuid;
	private String        code;
	private String        qrcode;
	private Double        latitude;
	private Double        longitude;
	private String        streetName;
	private String        streetNumber;	
	private Integer	      personsNumber;	
	private Integer       areaId;
	private VisitSync     visit;
	private List<PersonSyncImpl> listSyncPerson;
	
	public HouseSyncImpl (){}
	
	public HouseSyncImpl (String uuid, String code, String qrcode, Double latitude, Double longitude, String streetName, String streetNumber, Integer areaId, VisitSync visit, List<PersonSyncImpl> listSyncPerson, Integer personsNumber){
		
		this.uuid         = uuid;
		this.code         = code;
		this.qrcode       = qrcode;
		this.latitude     = latitude;
		this.longitude    = longitude;
		this.streetName   = streetName;
		this.streetNumber = streetNumber;
		this.areaId       = areaId;
		this.visit        = visit;
		this.listSyncPerson = listSyncPerson;
		this.personsNumber  = personsNumber;

	}

	public String getUuid() {return uuid;}
	public void setUuid(String uuid) {this.uuid = uuid;}	

	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}

	public String getQrcode() {return qrcode;}
	public void setQrcode(String qrcode) {this.qrcode = qrcode;}

	public Double getLatitude() {return latitude;}
	public void setLatitude(Double latitude) {this.latitude = latitude;}

	public Double getLongitude() {return longitude;}
	public void setLongitude(Double longitude) {this.longitude = longitude;}

	public String getStreetName() {return streetName;}
	public void setStreetName(String streetName) {this.streetName = streetName;}

	public String getStreetNumber() {return streetNumber;}
	public void setStreetNumber(String streetNumber) {this.streetNumber = streetNumber;}

	public Integer getAreaId() {return areaId;}
	public void setAreaId(Integer areaId) {this.areaId = areaId;}

	public VisitSync getVisit() {return visit;}
	public void setVisit(VisitSync visit) {this.visit = visit;}

	public List<PersonSyncImpl> getListSyncPerson() {return listSyncPerson;}
	public void setListSyncPerson(List<PersonSyncImpl> listSyncPerson) {this.listSyncPerson = listSyncPerson;}

	public Integer getPersonsNumber() {return personsNumber;}
	public void setPersonsNumber(Integer personsNumber) {this.personsNumber = personsNumber;}
	
}
