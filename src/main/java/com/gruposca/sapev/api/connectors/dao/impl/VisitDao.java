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

import java.util.List;
import java.util.UUID;
import com.gruposca.sapev.api.connectors.dao.base.BaseDao;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.dao.model.Visits;
import com.gruposca.sapev.api.modelview.InspectorRecordData;
import com.gruposca.sapev.api.modelview.ParamFilterVisits;
import com.gruposca.sapev.api.modelview.Visit;
import com.gruposca.sapev.api.modelview.VisitList;
import com.gruposca.sapev.api.modelview.VisitPlan;

public interface VisitDao extends BaseDao<Visits, String>{
	
	public List<Visit> getListVisits(Users users, UUID houseuuid) throws Exception;		
	
	public VisitList getList(Users user, ParamFilterVisits paramFilterVisits, Integer inspectionId) throws Exception;	
	
	public Visits findByUUID(UUID uuid) throws Exception;

	public List<VisitPlan> getVisitPlanList(Users user, Integer planId) throws Exception;

	public Visits getLastVisit(UUID houseUuid) throws Exception;	
	
	public List<VisitPlan> getVisitInspectionList(Users user, Integer inspectionId) throws Exception;	
	
	public Visits getVisitClosed(Integer inspectionId, Integer resultId, UUID houseUuid) throws Exception;
	
	public Integer getScheduleId(UUID uuid) throws Exception;	
	
	public Integer reconvertedVisit(Integer scheduleId, UUID houseUuid) throws Exception;	

	public List<InspectorRecordData> geListInspectorRecodData(Integer planId) throws Exception;	
	
	public Integer deleteVisit(UUID uuid);

}
