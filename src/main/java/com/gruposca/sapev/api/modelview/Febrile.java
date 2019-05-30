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

public class Febrile{
	
	private Long    mDate;
	private Integer	mRedId;
	private Integer	mEessId;
	private Integer	mMicrorredId;

	public Febrile(){}
	
	public Febrile(Long date, Integer eessId, Integer microrredId, Integer redId){		
		mDate        = date;
		mRedId       = redId;
		mEessId      = eessId;
		mMicrorredId = microrredId;
	}

	public Long getDate() {	return mDate;}
	public void setDate(Long mDate) {this.mDate = mDate;}

	public Integer getRedId() {return mRedId;}
	public void setRedId(Integer mRedId) {	this.mRedId = mRedId;}

	public Integer getEessId() {return mEessId;}
	public void setEessId(Integer mEessId) {this.mEessId = mEessId;}

	public Integer getMicrorredId() {return mMicrorredId;}
	public void setMicrorredId(Integer mMicrorredId) {this.mMicrorredId = mMicrorredId;}
	
}