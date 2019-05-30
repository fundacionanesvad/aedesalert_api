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
import java.util.UUID;

public class InspectorRecordData {
	
	private String  streetName;
	private String  streetNumber;
	private String  nameMz;
	private Integer persons;
	private BigDecimal larvicide;
	private Integer feverish;
	private UUID    visitUuid;
	private UUID    houseUuid;
	private String  sectorName;
	private String  tipe;

	
	public InspectorRecordData(){}
	
	public InspectorRecordData(String streetName, String streetNumber, String nameMz, Integer persons, BigDecimal larvicide, Integer feverish, UUID visitUuid, UUID houseUuid,String sectorName,String tipe){
		this.streetName  	= streetName;
		this.streetNumber	= streetNumber;
		this.nameMz      	= nameMz;
		this.persons     	= persons;
		this.larvicide   	= larvicide;
		this.feverish    	= feverish;
		this.visitUuid   	= visitUuid;
		this.houseUuid    	= houseUuid;
		this.sectorName		= sectorName;
		this.tipe			= tipe;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getNameMz() {
		return nameMz;
	}

	public void setNameMz(String nameMz) {
		this.nameMz = nameMz;
	}

	public Integer getPersons() {
		return persons;
	}

	public void setPersons(Integer persons) {
		this.persons = persons;
	}

	public BigDecimal getLarvicide() {
		return larvicide;
	}

	public void setLarvicide(BigDecimal larvicide) {
		this.larvicide = larvicide;
	}

	public Integer getFeverish() {
		return feverish;
	}

	public void setFeverish(Integer feverish) {
		this.feverish = feverish;
	}

	public UUID getVisitUuid() {
		return visitUuid;
	}

	public void setVisitUuid(UUID visitUuid) {
		this.visitUuid = visitUuid;
	}

	public UUID getHouseUuid() {
		return houseUuid;
	}

	public void setHouseUuid(UUID houseUuid) {
		this.houseUuid = houseUuid;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getTipe() {
		return tipe;
	}

	public void setTipe(String tipe) {
		this.tipe = tipe;
	}
	
}
