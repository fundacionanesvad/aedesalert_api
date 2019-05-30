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
package com.gruposca.sapev.api.services;

import java.util.List;

import com.gruposca.sapev.api.connectors.dao.model.TrapLocations;
import com.gruposca.sapev.api.connectors.dao.model.Traps;
import com.gruposca.sapev.api.modelview.FilterTrapData;
import com.gruposca.sapev.api.modelview.FilterTrapExcel;
import com.gruposca.sapev.api.modelview.ParamFilterTraps;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.TrapDataModel;
import com.gruposca.sapev.api.modelview.TrapDataModelList;
import com.gruposca.sapev.api.modelview.TrapLocationModel;
import com.gruposca.sapev.api.modelview.TrapLocationsModelList;
import com.gruposca.sapev.api.modelview.TrapModel;
import com.gruposca.sapev.api.modelview.TrapsList;

public interface TrapService {

	TrapsList getListTraps(Session session, ParamFilterTraps paramFilterTraps);
	
	TrapModel getTrap(Integer id);
	
	Traps createTrap(TrapModel trapModel);
	
	Traps updateTrap(Integer id, TrapModel trapModel);
	
	List<TrapLocationsModelList> getListTrapLocation(Integer trapId);

	TrapLocationModel getTrapLocation(Integer id);
	
	TrapLocations createTrapLocations(Integer trapId,TrapLocationModel trapLocationModel);
	
	TrapLocations updateTrapLocations(Integer id, TrapLocationModel trapLocationModel);
	
	List<TrapDataModelList> getTrapDataList(FilterTrapData filterTrapData);

	int saveTrapData(TrapDataModel trapDataModel);
	
	String reportTrap(FilterTrapExcel filterTrapExcel);
	
}
