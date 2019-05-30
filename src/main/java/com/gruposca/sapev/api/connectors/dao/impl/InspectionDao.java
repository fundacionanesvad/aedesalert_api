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
package com.gruposca.sapev.api.connectors.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.gruposca.sapev.api.connectors.dao.base.BaseDao;
import com.gruposca.sapev.api.connectors.dao.model.Inspections;
import com.gruposca.sapev.api.connectors.dao.model.Schedules;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.modelview.FocusHouse;
import com.gruposca.sapev.api.modelview.Inspection;
import com.gruposca.sapev.api.modelview.InspectionImpl;
import com.gruposca.sapev.api.modelview.InspectionListImpl;
import com.gruposca.sapev.api.modelview.InspectionSchedule;
import com.gruposca.sapev.api.modelview.ParamFilterInspections;
import com.gruposca.sapev.api.modelview.ReportInspection;
import com.gruposca.sapev.api.modelview.StatsContainers;
import com.gruposca.sapev.api.modelview.StatsEessResults;
import com.gruposca.sapev.api.modelview.StatsMrResults;

public interface InspectionDao extends BaseDao<Inspections, String>{

	List<Inspections> getListByArea (Integer areaId) throws Exception;
	
	List<InspectionListImpl> getListByAreaUser (Users user, ParamFilterInspections paramFilterInspections) throws Exception;

	Inspections updateInspectionsDates(Integer inspectionId) throws Exception;	
	
	List<InspectionSchedule> getListBySchedule (Schedules schedule) throws Exception;

	public Integer getCountList(Users user, ParamFilterInspections paramFilterInspections) throws Exception;
	
	int getNumInspection(Inspections inspection, Date date, Users user) throws Exception;
	
	int getNumRequalify(Integer inspectionId, Integer areaId) throws Exception;
	
	List<InspectionListImpl> getListReportInspections (ReportInspection reportInspection) throws Exception;

	StatsMrResults getStatsMrResult (int type, int areaId, String inspections) throws Exception;
	
	StatsEessResults getStatsEessResult(int type, boolean reconversion, int areaId, String inspections) throws Exception;	
	
	List<StatsContainers> getListStatContainers(int type, boolean reconversion, int areaId, String inspections) throws Exception;	

	List<FocusHouse> getListFocusHouses(String inspections) throws Exception;
	
	Inspection getInspectionbyId(int inspectionId) throws Exception;
	
	BigDecimal getControlHouses(Integer scheduleId,Integer areaId) throws Exception;
	BigDecimal getReconversionHouses(Integer scheduleId,Integer areaId) throws Exception;
}
