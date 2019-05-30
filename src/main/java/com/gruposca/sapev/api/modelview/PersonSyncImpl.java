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

public class PersonSyncImpl {

	private String        uuid;
	private Character     genre;
	private Long          birthday;
	private Boolean       birthdayExact;
	private Boolean       enabled;
	private String        name;
	private String		  cardId;	
	private List<SymptomSyncImpl> listSyncSymptoms;
	
	public PersonSyncImpl (){}
	
	public PersonSyncImpl (String uuid, Character genre, Long birthday, Boolean birthdayExact, Boolean enabled, String name, String cardId, List<SymptomSyncImpl> listSyncSymptoms){
		
		this.uuid             = uuid;
		this.genre            = genre;
		this.birthday         = birthday;
		this.birthdayExact    = birthdayExact;
		this.enabled          = enabled;
		this.name             = name;
		this.cardId           = cardId;
		this.listSyncSymptoms = listSyncSymptoms;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Character getGenre() {
		return genre;
	}

	public void setGenre(Character genre) {
		this.genre = genre;
	}

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}

	public Boolean getBirthdayExact() {
		return birthdayExact;
	}

	public void setBirthdayExact(Boolean birthdayExact) {
		this.birthdayExact = birthdayExact;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SymptomSyncImpl> getListSyncSymptoms() {
		return listSyncSymptoms;
	}

	public void setListSyncSymptoms(List<SymptomSyncImpl> listSyncSymptoms) {
		this.listSyncSymptoms = listSyncSymptoms;
	}

	public String getCardId() {return cardId;}
	public void setCardId(String cardId) {this.cardId = cardId;}
	
}
