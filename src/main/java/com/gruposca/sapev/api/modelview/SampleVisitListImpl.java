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

public class SampleVisitListImpl implements Sample{
	
	private Integer mContainerId;
	private String  mContainerName;
	private String  mCode;
	private String  mPhaseName;
	private String  mResult;
	
	public SampleVisitListImpl(){}
	
	public SampleVisitListImpl(Integer containerId, String containerName, String code, String phaseName, String result){
		mContainerId   = containerId;
		mContainerName = containerName;
		mCode          = code;
		mPhaseName     = phaseName;
		mResult        = result;
	}

	public Integer getContainerId() {return mContainerId;}
	public void setContainerId(Integer mContainerId) {this.mContainerId = mContainerId;}

	public String getCode() {return mCode;}
	public void setCode(String mCode) {this.mCode = mCode;}
	
	public String getResult() {return mResult;}
	public void setResult(String mResult) {this.mResult = mResult;}

	public String getContainerName() {return mContainerName;}
	public void setContainerName(String mContainerName) {this.mContainerName = mContainerName;}

	public String getPhaseName() {return mPhaseName;}
	public void setPhaseName(String mPhaseName) {this.mPhaseName = mPhaseName;}		

}
