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

public class SceneListImpl implements Scene{

	private Integer mId;
	private Integer mAreaId;
	private String  mAreaName;
	private Byte    mSceneLevel;
	
	public SceneListImpl(){}
	
	public SceneListImpl(Integer id, Integer areaId, String areaName, Byte sceneLevel){
		mId         = id;
		mAreaId     = areaId;
		mAreaName   = areaName;
		mSceneLevel = sceneLevel;
	}

	public Integer getId() {return mId;}
	public void setId(Integer mId) {this.mId = mId;}

	public Integer getAreaId() {return mAreaId;}
	public void setAreaId(Integer mAreaId) {this.mAreaId = mAreaId;}

	public String getAreaName() {return mAreaName;}
	public void setAreaName(String mAreaName) {this.mAreaName = mAreaName;}

	public Byte getSceneLevel() {return mSceneLevel;}
	public void setSceneLevel(Byte mSceneLevel) {this.mSceneLevel = mSceneLevel;}
	
}
