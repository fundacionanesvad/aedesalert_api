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

import java.math.BigDecimal;

public class Larvicide {
	
	private String     mName;
	private String     mUnity;
	private BigDecimal mDose;
	private String     mDoseName;
	private Integer    mWaterVolume;
	private boolean    mEnabled;	
	
	public Larvicide(){}
	
	public Larvicide(String name, String unity, BigDecimal dose, String doseName, Integer waterVolume, boolean enabled){
		mName         = name;
		mUnity		  = unity;
		mDose         = dose;
		mDoseName     = doseName;
		mWaterVolume = waterVolume;
		mEnabled      = enabled;	
	}

	public String getName() {return mName;}
	public void setName(String mName) {this.mName = mName;}

	public String getUnity() {return mUnity;}
	public void setUnity(String mUnity) {this.mUnity = mUnity;}

	public BigDecimal getDose() {return mDose;}
	public void setDose(BigDecimal mDose) {this.mDose = mDose;	}	

	public boolean isEnabled() {return mEnabled;}
	public void setEnabled(boolean mEnabled) {this.mEnabled = mEnabled;}

	public String getDoseName() {return mDoseName;}
	public void setDoseName(String mDoseName) {this.mDoseName = mDoseName;}

	public Integer getWaterVolume() {return mWaterVolume;}
	public void setWaterVolume(Integer mWaterVolume) {this.mWaterVolume = mWaterVolume;}	
	
}
