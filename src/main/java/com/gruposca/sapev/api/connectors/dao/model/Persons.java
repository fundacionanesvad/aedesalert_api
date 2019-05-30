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
package com.gruposca.sapev.api.connectors.dao.model;

// Generated 15-abr-2015 13:02:34 by Hibernate Tools 4.3.1

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
@Table(name = "Persons")
public class Persons implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private UUID uuid;
	private Houses houses;
	private char genre;
	private Date birthday;
	private boolean birthdayExact;
	private boolean enabled;
	private String cardId;
	private Set<Symptoms> symptomses = new HashSet<Symptoms>(0);

	public Persons() {
	}

	public Persons(String name, UUID uuid, Houses houses, char genre, Date birthday, boolean birthdayExact, boolean enabled, String cardId) {
		this.uuid = uuid;
		this.houses = houses;
		this.genre = genre;
		this.birthday = birthday;
		this.birthdayExact = birthdayExact;
		this.enabled = enabled;
		this.name = name;
		this.cardId = cardId;
	}

	public Persons(String name, UUID uuid, Houses houses, char genre,
			Date birthday, boolean birthdayExact, boolean enabled,
			Set<Symptoms> symptomses, String cardId) {
		this.uuid = uuid;
		this.houses = houses;
		this.genre = genre;
		this.birthday = birthday;
		this.birthdayExact = birthdayExact;
		this.enabled = enabled;
		this.symptomses = symptomses;
		this.name = name;
	}

	@Id
	@Column(name = "uuid", columnDefinition = "BINARY(16)", unique = true, nullable = false)
	public UUID getUuid() {
		return this.uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "houseUuid", nullable = false)
	public Houses getHouses() {
		return this.houses;
	}

	public void setHouses(Houses houses) {
		this.houses = houses;
	}

	@Column(name = "genre", nullable = false, length = 1)
	public char getGenre() {
		return this.genre;
	}

	public void setGenre(char genre) {
		this.genre = genre;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birthday", nullable = false, length = 10)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "birthdayExact", nullable = false)
	public boolean isBirthdayExact() {
		return this.birthdayExact;
	}

	public void setBirthdayExact(boolean birthdayExact) {
		this.birthdayExact = birthdayExact;
	}

	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "persons")
	public Set<Symptoms> getSymptomses() {
		return this.symptomses;
	}

	public void setSymptomses(Set<Symptoms> symptomses) {
		this.symptomses = symptomses;
	}

	@Column(name = "name", nullable = false, length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "cardId", nullable = false, length = 50)
	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

}
