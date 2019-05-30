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

public class PlanVisit {

	private HouseImpl house;
	private VisitPlan visit;
	private List<InventorySync> listInventories;
	
	public PlanVisit(){}
	
	public PlanVisit(HouseImpl house, VisitPlan visit, List<InventorySync> listInventories){
		this.house       = house;
		this.visit       = visit;;
		this.listInventories = listInventories;
	}

	public HouseImpl getHouse() {return house;}
	public void setHouse(HouseImpl house) {this.house = house;}

	public VisitPlan getVisit() {return visit;}
	public void setVisit(VisitPlan visit) {this.visit = visit;}

	public List<InventorySync> getListInventories() {return listInventories;}
	public void setListInventories(List<InventorySync> listInventories) {this.listInventories = listInventories;}
	
}
