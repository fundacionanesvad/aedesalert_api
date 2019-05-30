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

import java.util.List;

public class PersonListImpl implements Person{

	private Character     mGenre;
	private Long          mBirthday;
	private Boolean       mBirthdayExact;
	private String        mName;
	private String        mCardId;
	private List<String>  mSymptoms;
	
	public PersonListImpl(){}
	
	public PersonListImpl(Character genre, String name, Long birthday, Boolean birthdayExact, String cardId, List<String> symptoms){
		mGenre         = genre;
		mName          = name;
		mBirthday      = birthday;
		mBirthdayExact = birthdayExact;
		mCardId        = cardId;
		mSymptoms      = symptoms;	
	}

	public Character getGenre() {return mGenre;}
	public void setGenre(Character mGenre) {this.mGenre = mGenre;}	

	public Long getBirthday() {return mBirthday;}
	public void setBirthday(Long mBirthday) {this.mBirthday = mBirthday;}

	public Boolean getBirthdayExact() {return mBirthdayExact;}
	public void setBirthdayExact(Boolean mBirthdayExact) {this.mBirthdayExact = mBirthdayExact;}

	public String getName() {return mName;}
	public void setName(String mName) {this.mName = mName;}

	public List<String> getSymptoms() {return mSymptoms;}
	public void setSymptoms(List<String> mSymptoms) {this.mSymptoms = mSymptoms;}

	public String getCardId() {return mCardId;}
	public void setCardId(String mCardId) {this.mCardId = mCardId;}

}
