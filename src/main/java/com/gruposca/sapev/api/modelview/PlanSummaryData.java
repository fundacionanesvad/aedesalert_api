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
import java.util.List;

public class PlanSummaryData {

	private Integer    mAreaId;
	private Integer    mHousesFocus;
	private Integer    mHousesInspected;
	private Integer    mHousesClosed;
	private Integer    mHousesReluctant;
	private Integer    mHousesAbandoned;
	private Integer	   mHousesTreated;
	private Integer	   mHousesDestroyed;
	private Integer    mPeople;
	private BigDecimal mLarvicide;
	private Integer    mFebriles;
	private Integer    mHousesReconverted;
	private List<InventoryListSummary> mListInventories;
	private List<SampleSummary> mListSamples;

	
	public PlanSummaryData(){}

	public PlanSummaryData(Integer housesFocus, Integer housesInspected, Integer housesClosed, Integer housesReluctant, Integer housesAbandoned, Integer housesTreated, Integer housesDestroyed,
						   Integer people, BigDecimal larvicide, Integer febriles, List<InventoryListSummary> listInventories, List<SampleSummary> listSamples, Integer housesReconverted, Integer areaId){
		
		mHousesFocus = housesFocus;
		mHousesInspected = housesInspected;
		mHousesClosed = housesClosed;
		mHousesReluctant = housesReluctant;
		mHousesAbandoned = housesAbandoned;
		mHousesTreated = housesTreated;
		mHousesDestroyed = housesDestroyed;
		mPeople = people;
		mLarvicide = larvicide;
		mFebriles = febriles;
		mListInventories = listInventories;
		mListSamples = listSamples;
		mHousesReconverted = housesReconverted;
		mAreaId = areaId;
	}

	public Integer getHousesFocus() {return mHousesFocus;}
	public void setHousesFocus(Integer mHousesFocus) {this.mHousesFocus = mHousesFocus;}

	public Integer getHousesInspected() {return mHousesInspected;}
	public void setHousesInspected(Integer mHousesInspected) {this.mHousesInspected = mHousesInspected;}

	public Integer getHousesClosed() {return mHousesClosed;}
	public void setHousesClosed(Integer mHousesClosed) {this.mHousesClosed = mHousesClosed;}

	public Integer getHousesReluctant() {return mHousesReluctant;}
	public void setHousesReluctant(Integer mHousesReluctant) {this.mHousesReluctant = mHousesReluctant;}

	public Integer getHousesTreated() {	return mHousesTreated;}
	public void setHousesTreated(Integer mHousesTreated) {this.mHousesTreated = mHousesTreated;	}

	public Integer getHousesDestroyed() {return mHousesDestroyed;}
	public void setHousesDestroyed(Integer mHousesDestroyed) {this.mHousesDestroyed = mHousesDestroyed;}

	public BigDecimal getLarvicide() {return mLarvicide;}
	public void setLarvicide(BigDecimal mLarvicide) {this.mLarvicide = mLarvicide;}

	public Integer getFebriles() {return mFebriles;}
	public void setFebriles(Integer mFebriles) {this.mFebriles = mFebriles;}	

	public Integer getHousesAbandoned() {return mHousesAbandoned;}
	public void setHousesAbandoned(Integer mHousesAbandoned) {	this.mHousesAbandoned = mHousesAbandoned;}

	public Integer getPeople() {return mPeople;}
	public void setPeople(Integer mPeople) {this.mPeople = mPeople;}

	public List<InventoryListSummary> getListInventories() {return mListInventories;}
	public void setListInventories(List<InventoryListSummary> mListInventories) {this.mListInventories = mListInventories;}

	public List<SampleSummary> getListSamples() {return mListSamples;}
	public void setListSamples(List<SampleSummary> mListSamples) {this.mListSamples = mListSamples;}

	public Integer getHousesReconverted() {return mHousesReconverted;}
	public void setHousesReconverted(Integer mHousesReconverted) {this.mHousesReconverted = mHousesReconverted;}

	public Integer getAreaId() {return mAreaId;}
	public void setAreaId(Integer mAreaId) {this.mAreaId = mAreaId;}
	
}