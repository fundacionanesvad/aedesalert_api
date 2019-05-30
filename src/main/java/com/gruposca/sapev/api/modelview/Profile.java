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

public class Profile {
	
	private String  mName;
	private String  mDescription;
	private List<Integer> mModules;
	private List<Integer> mAlerts;

	public Profile() {}
	
	public Profile(String name, String description, List<Integer> modules, List<Integer> alerts) {
		this.mName        = name;
		this.mDescription = description;
		this.mModules     = modules;
		this.mAlerts      = alerts;
	}

	public String getName() {return mName;}
	public void setName(String mName) {this.mName = mName;}

	public String getDescription() {return mDescription;}
	public void setDescription(String mDescription) {this.mDescription = mDescription;}

	public List<Integer> getModules() {return mModules;}
	public void setModules(List<Integer> mModules) {this.mModules = mModules;}

	public List<Integer> getAlerts() {return mAlerts;}
	public void setAlerts(List<Integer> mAlerts) {this.mAlerts = mAlerts;}	

}
