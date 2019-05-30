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

public class SyncPersonPlan implements Person{

	private String	  mUuid;
	private Character mGenre;
	private Long      mBirthday;
	private Boolean   mBirthdayExact;
	private Boolean   mEnabled;
	private String	  mName;
	private String	  mCardId;

	public SyncPersonPlan(){}
	
	public SyncPersonPlan(String uuid, Character genre, Long birthday, Boolean birthdayExact, Boolean enabled, String name, String cardId){
		
		mUuid          = uuid;
		mGenre         = genre;
		mBirthday      = birthday;
		mBirthdayExact = birthdayExact;
		mEnabled       = enabled;
		mName          = name;
		mCardId        = cardId;
	}

	public String getUuid() {return mUuid;}
	public void setUuid(String mUuid) {this.mUuid = mUuid;}

	public Character getGenre() {return mGenre;}
	public void setGenre(Character mGenre) {this.mGenre = mGenre;}

	public Long getBirthday() {return mBirthday;}
	public void setBirthday(Long mBirthday) {this.mBirthday = mBirthday;}

	public Boolean getBirthdayExact() {return mBirthdayExact;}
	public void setBirthdayExact(Boolean mBirthdayExact) {this.mBirthdayExact = mBirthdayExact;}

	public Boolean getEnabled() {return mEnabled;}
	public void setEnabled(Boolean mEnabled) {this.mEnabled = mEnabled;}

	public String getName() {return mName;}
	public void setName(String mName) {this.mName = mName;}

	public String getCardId() {return mCardId;}
	public void setCardId(String mCardId) {this.mCardId = mCardId;}
	
}
