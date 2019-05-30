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

public class LabelImpl implements Label{
	
	private Integer mLanguageId;
	private String  mValue;
	
	public LabelImpl(){}
	
	public LabelImpl(Integer languageId, String value){
		mLanguageId = languageId;
		mValue      = value;
	}

	public Integer getLanguageId() {return mLanguageId;}
	public void setLanguageId(Integer mLanguageId) {this.mLanguageId = mLanguageId;}

	public String getValue() {return mValue;}
	public void setValue(String mValue) {this.mValue = mValue;}
	
}
