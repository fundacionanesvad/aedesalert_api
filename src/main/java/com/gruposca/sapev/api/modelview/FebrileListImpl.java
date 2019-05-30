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

public class FebrileListImpl{
	
	private Integer   mId;
	private Long      mDate;
	private String    mEessName;
	private String    mMicrorredName;
	private String    mRedName;

	
	
	public FebrileListImpl(){}
	
	public FebrileListImpl(int id, Long date, String eessName, String microrredName, String redName){
		
		mId             = id;
		mDate           = date;
		mEessName       = eessName;
		mMicrorredName  = microrredName;
		mRedName        = redName;
	}

	public int getId() {return mId;}
	public void setId(int mId) {this.mId = mId;}	

	public Long getDate() {	return mDate;}
	public void setDate(Long mDate) {this.mDate = mDate;}

	public String getEessName() {return mEessName;}
	public void setEessName(String mEessName) {this.mEessName = mEessName;}

	public String getMicrorredName() {return mMicrorredName;}
	public void setMicrorredName(String mMicrorredName) {this.mMicrorredName = mMicrorredName;}

	public String getRedName() {return mRedName;}
	public void setRedName(String mRedName) {this.mRedName = mRedName;}	
	
}
