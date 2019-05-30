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

public class LabelCreateImpl implements Label{
	
	private Integer mLanguageId;
	private String  mValue;
	private Integer mElementId;
	
	public LabelCreateImpl(){}
	
	public LabelCreateImpl(Integer languageId, String value, Integer id){
		mLanguageId = languageId;
		mValue      = value;
		mElementId  = id;
	}

	public Integer getLanguageId() {return mLanguageId;}
	public void setLanguageId(Integer mLanguageId) {this.mLanguageId = mLanguageId;}

	public String getValue() {return mValue;}
	public void setValue(String mValue) {this.mValue = mValue;}

	public Integer getElementId() {return mElementId;}
	public void setElementId(Integer mElementId) {this.mElementId = mElementId;}
	
}
