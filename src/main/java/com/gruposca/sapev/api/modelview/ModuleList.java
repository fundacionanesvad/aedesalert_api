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

public class ModuleList {

	private Integer mId;
	private String  mVerb;
	private String  mModule;
	private String  mGroup;
	
	public ModuleList(){}
	
	public ModuleList(Integer id, String verb, String module, String group ){
		
		mId      = id;
		mVerb    = verb; 
		mModule  = module;
		mGroup   = group;		
	}

	public Integer getId() {return mId;}
	public void setId(Integer mId) {this.mId = mId;}

	public String getVerb() {return mVerb;}
	public void setVerb(String mVerb) {this.mVerb = mVerb;}

	public String getModule() {return mModule;}
	public void setModule(String mModule) {this.mModule = mModule;}

	public String getGroup() {return mGroup;}
	public void setGroup(String mGroup) {this.mGroup = mGroup;}
	
}
