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

import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.modelview.Area;
import com.gruposca.sapev.api.modelview.AreaChild;
import com.gruposca.sapev.api.modelview.AreaCreateUpdateImpl;
import com.gruposca.sapev.api.modelview.AreaTree;
import com.gruposca.sapev.api.modelview.FocusHouse;
import com.gruposca.sapev.api.modelview.House;
import com.gruposca.sapev.api.modelview.Inspection;
import com.gruposca.sapev.api.modelview.MapAedic;
import com.gruposca.sapev.api.modelview.MapDates;
import com.gruposca.sapev.api.modelview.MapFocus;
import com.gruposca.sapev.api.modelview.ReportArea;
import com.gruposca.sapev.api.modelview.ScheduleArea;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.StatsEessAreas;
import com.gruposca.sapev.api.modelview.StatsMrAreas;

public interface AreaService {

	Area getArea(Integer id);
	
	Areas createArea(AreaCreateUpdateImpl area);
	
	Areas updateArea(Integer id, AreaCreateUpdateImpl area);
	
	boolean deleteArea(Integer id);
	
	Area getChilds(Integer id);
	
	List<Area> getParents(Session session, Integer id);

	List<House> getHousesArea(Integer id);
	
	List<Area> getListArea();
	
	List<Inspection> getInspectionsArea(Session session, Integer id);
	
	List<ReportArea> getReportsArea(Integer id);
	 
	MapAedic getMapAedico(Integer id, MapDates mapDates, Session session);
	 
	MapFocus getMapFocus(Session session, Integer id, MapDates mapDates);
	
	List<AreaChild> getSectors(Integer id);

	List<AreaChild> getBlocks(Integer id);
	
	List<AreaChild> getDescendants(Integer id, Integer typeId);
	
	List<ScheduleArea> getSchedulesArea(Integer id);	

	AreaTree getTreeAreas();

	List<StatsMrAreas> getListStatsMr(Session session, Integer id, List<Integer> listInspections);

	List<StatsEessAreas> getListStatsEess(Session session, Integer id, List<Integer> listInspections);

	List<FocusHouse> getListFocusHouses(Session session, Integer id, List<Integer> listInspections);


}
