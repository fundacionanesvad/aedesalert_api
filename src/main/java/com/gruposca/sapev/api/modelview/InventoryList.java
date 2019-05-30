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

public class InventoryList {

	private Integer mContainerId;
	private String  mContainerName;
	private Integer    mInspected;
	private Integer    mFocus;
	private Integer    mTreated;
	private Integer    mPacket;
	private Integer    mDestroyed;

	public InventoryList(){}
	
	public InventoryList(Integer containerId, String containerName, Integer inspected, Integer focus, Integer treated, Integer packet, Integer destroyed){
		
		mContainerId   = containerId;
		mContainerName = containerName;
		mInspected     = inspected;
		mFocus         = focus;
		mTreated       = treated;
		mPacket        = packet;
		mDestroyed     = destroyed;
	}

	public Integer getContainerId() {return mContainerId;}
	public void setContainerId(Integer mContainerId) {this.mContainerId = mContainerId;}

	public Integer getInspected() {return mInspected;}
	public void setInspected(Integer mInspected) {this.mInspected = mInspected;}

	public Integer getFocus() {return mFocus;}
	public void setFocus(Integer mFocus) {this.mFocus = mFocus;}

	public Integer getTreated() {return mTreated;}
	public void setTreated(Integer mTreated) {this.mTreated = mTreated;}

	public Integer getPacket() {return mPacket;}
	public void setPacket(Integer mPacket) {this.mPacket = mPacket;}

	public Integer getDestroyed() {return mDestroyed;}
	public void setDestroyed(Integer mDestroyed) {this.mDestroyed = mDestroyed;}

	public String getContainerName() {return mContainerName;}
	public void setContainerName(String mContainerName) {this.mContainerName = mContainerName;}		
	
}
