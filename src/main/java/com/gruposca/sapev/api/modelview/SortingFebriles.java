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

public class SortingFebriles {

	private String mDate;
	private String mEessName;
	private String mMicrorredName;
	private String mRedName;

	
	public SortingFebriles(){}
	
	public SortingFebriles(String date, String eessName, String microrredName, String redName){
		mDate          = date;
		mEessName      = eessName;
		mMicrorredName = microrredName;
		mRedName       = redName;
	}

	public String getDate() {return mDate;}
	public void setDate(String mDate) {this.mDate = mDate;}

	public String getMicrorredName() {return mMicrorredName;}
	public void setMicrorredName(String mMicrorredName) {this.mMicrorredName = mMicrorredName;}

	public String getEessName() {return mEessName;}
	public void setEessName(String mEessName) {this.mEessName = mEessName;}

	public String getRedName() {return mRedName;}
	public void setRedName(String mRedName) {this.mRedName = mRedName;}
	
}
