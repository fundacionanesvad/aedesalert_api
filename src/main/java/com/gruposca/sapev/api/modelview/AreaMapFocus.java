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
import java.util.List;

public class AreaMapFocus {
	
	private Integer  mId;
	private String   mName;
	private String   mCoords;
	private Boolean  mLeaf;
	private BigDecimal mLatitude;
	private BigDecimal mLongitude;
	private List<SamplesMapFocus>  mSamples;	
	
	public AreaMapFocus(){}
	
	public AreaMapFocus(Integer id, String name,  String coords, Boolean leaf, BigDecimal latitude, BigDecimal longitude, List<SamplesMapFocus> samples){
		mId        = id;
		mName      = name;
		mCoords    = coords;
		mLeaf      = leaf;
		mLatitude  = latitude;
		mLongitude = longitude;
		mSamples   = samples;
	}

	public Integer getId() {return mId;}
	public void setId(Integer mId) {this.mId = mId;}

	public String getName() {return mName;}
	public void setName(String mName) {this.mName = mName;}

	public String getCoords() {return mCoords;}
	public void setCoords(String mCoords) {this.mCoords = mCoords;}

	public Boolean getLeaf() {return mLeaf;}
	public void setLeaf(Boolean mLeaf) {this.mLeaf = mLeaf;}

	public List<SamplesMapFocus> getSamples() {return mSamples;}
	public void setSamples(List<SamplesMapFocus> mSamples) {this.mSamples = mSamples;}

	public BigDecimal getLatitude() {return mLatitude;}
	public void setLatitude(BigDecimal mLatitude) {this.mLatitude = mLatitude;}

	public BigDecimal getLongitude() {return mLongitude;}
	public void setLongitude(BigDecimal mLongitude) {this.mLongitude = mLongitude;}	
	
}
