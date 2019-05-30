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

import java.util.List;

import com.gruposca.sapev.api.connectors.dao.model.Larvicides;

public class SyncPlanImpl implements Plan{

	private Integer       mPlanSize;
	private Integer       mTypeId;
	private Integer       mReconversionScheduleId;
	private List<Area>    mAreas;
	private List<Element> mElements;
	private Larvicides    mLarvicide;
	
	public SyncPlanImpl(){}
	
	public SyncPlanImpl(Integer planSize, List<Area> areas, List<Element> elements, Integer typeId, Integer reconversionScheduleId, Larvicides larvicide){
		mPlanSize = planSize;
		mAreas    				= areas;
		mElements 				= elements;
		mTypeId 				= typeId;
		mReconversionScheduleId = reconversionScheduleId;
		mLarvicide              = larvicide;
	}

	public List<Area> getAreas() {	return mAreas;}
	public void setAreas(List<Area> mAreas) {this.mAreas = mAreas;}

	public Integer getPlanSize() {return mPlanSize;}
	public void setPlanSize(Integer mPlanSize) {this.mPlanSize = mPlanSize;}

	public List<Element> getElements() {return mElements;}
	public void setElements(List<Element> mElements) {this.mElements = mElements;}

	public Integer getTypeId() {return mTypeId;}
	public void setTypeId(Integer mTypeId) {this.mTypeId = mTypeId;}

	public Integer getReconversionScheduleId() {return mReconversionScheduleId;}
	public void setReconversionScheduleId(Integer mReconversionScheduleId) {this.mReconversionScheduleId = mReconversionScheduleId;}

	public Larvicides getLarvicide() {return mLarvicide;}
	public void setLarvicide(Larvicides mLarvicide) {this.mLarvicide = mLarvicide;}
	
}
