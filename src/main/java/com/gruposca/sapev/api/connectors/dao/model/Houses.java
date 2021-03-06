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
package com.gruposca.sapev.api.connectors.dao.model;

// Generated 15-abr-2015 13:02:34 by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * Houses generated by hbm2java
 */
@Entity
@Proxy(lazy = false)
@Table(name = "Houses")
public class Houses implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	
    private UUID uuid;
	private Areas areas;
	private int number;
	private String code;
	private String qrcode;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String streetName;
	private String streetNumber;
	private Set<Visits> visitses = new HashSet<Visits>(0);
	private Set<Persons> personses = new HashSet<Persons>(0);
	private Set<Samples> sampleses = new HashSet<Samples>(0);

	private int personsNumber;


	public Houses() {
	}

	public Houses(UUID uuid, Areas areas, int number, String code) {
		this.uuid = uuid;
		this.areas = areas;
		this.number = number;
		this.code = code;
	}
	
	public Houses(UUID uuid, Integer number, String code, String qrcode, BigDecimal latitude, BigDecimal longitude, String streetName, String streetNumber, Areas areas, Integer personsNumber) {
		this.uuid          = uuid;
		this.number        = number;
		this.code          = code;
		this.qrcode        = qrcode;
		this.latitude      = latitude;
		this.longitude     = longitude;
		this.streetName    = streetName;
		this.streetNumber  = streetNumber;
		this.areas         = areas;
		this.personsNumber = personsNumber;
	}

	/*public Houses(byte[] uuid, Areas areas, int number, String code,
			String qrcode, BigDecimal latitude, BigDecimal longitude,
			String streetName, String streetNumber, Set<Persons> personses,
			Set<Visits> visitses) {
		this.uuid = uuid;
		this.areas = areas;
		this.number = number;
		this.code = code;
		this.qrcode = qrcode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.personses = personses;
		this.visitses = visitses;
	}*/

	@Id
	@Column(name = "uuid",columnDefinition = "BINARY(16)", unique = true, nullable = false)
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}		

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "areaId", nullable = false)
	public Areas getAreas() {
		return this.areas;
	}

	public void setAreas(Areas areas) {
		this.areas = areas;
	}

	@Column(name = "number", nullable = false)
	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Column(name = "code", nullable = false, length = 100)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "qrcode", length = 100)
	public String getQrcode() {
		return this.qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	@Column(name = "latitude", precision = 9, scale = 6)
	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	@Column(name = "longitude", precision = 9, scale = 6)
	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	@Column(name = "streetName", length = 250)
	public String getStreetName() {
		return this.streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	@Column(name = "streetNumber", length = 10)
	public String getStreetNumber() {
		return this.streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "houses")
	public Set<Visits> getVisitses() {
		return this.visitses;
	}

	public void setVisitses(Set<Visits> visitses) {
		this.visitses = visitses;
	}

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "houses")
	public Set<Persons> getPersonses() {
		return this.personses;
	}

	public void setPersonses(Set<Persons> personses) {
		this.personses = personses;
	}
	
	@Column(name = "personsNumber", nullable = false)
	public int getPersonsNumber() {
		return this.personsNumber;
	}

	public void setPersonsNumber(int personsNumber) {
		this.personsNumber = personsNumber;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "houses")
	public Set<Samples> getSampleses() {
		return this.sampleses;
	}

	public void setSampleses(Set<Samples> sampleses) {
		this.sampleses = sampleses;
	}
}
