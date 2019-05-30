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

import java.math.BigDecimal;

public class ScheduleAreaList {
	private Integer    mAreaEESS;
	private String     mNameEESS;
	private Integer    mAreaSector;
	private String     mNameSector;
	private BigDecimal mNumHouses;
	
	public ScheduleAreaList(){}
	
	public ScheduleAreaList(Integer areaEESS, String nameEESS, Integer areaSector, String nameSector, BigDecimal numHouses){		
		mAreaEESS   = areaEESS;
		mNameEESS   = nameEESS;
		mAreaSector = areaSector;
		mNameSector = nameSector;
		mNumHouses  = numHouses;
	}

	public Integer getAreaEESS() {return mAreaEESS;}
	public void setAreaEESS(Integer mAreaEESS) {this.mAreaEESS = mAreaEESS;}

	public String getNameEESS() {return mNameEESS;}
	public void setNameEESS(String mNameEESS) {this.mNameEESS = mNameEESS;}

	public Integer getAreaSector() {return mAreaSector;}
	public void setAreaSector(Integer mAreaSector) {this.mAreaSector = mAreaSector;}

	public String getNameSector() {return mNameSector;}
	public void setNameSector(String mNameSector) {this.mNameSector = mNameSector;}

	public BigDecimal getNumHouses() {return mNumHouses;}
	public void setNumHouses(BigDecimal mNumHouses) {this.mNumHouses = mNumHouses;}
	
}
