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

import java.util.Date;
import java.util.List;
import com.gruposca.sapev.api.connectors.dao.base.BaseDao;
import com.gruposca.sapev.api.connectors.dao.model.Inspections;
import com.gruposca.sapev.api.connectors.dao.model.Plans;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.modelview.InspectionListImpl;
import com.gruposca.sapev.api.modelview.ParamFilterInspections;
import com.gruposca.sapev.api.modelview.ParamFilterPlanVisits;
import com.gruposca.sapev.api.modelview.ParamFilterPlans;
import com.gruposca.sapev.api.modelview.Plan;
import com.gruposca.sapev.api.modelview.PlanInspection;
import com.gruposca.sapev.api.modelview.PlanVisitImpl;
import com.gruposca.sapev.api.modelview.Substitutes;

public interface PlanDao extends BaseDao<Plans, String>{

	List<PlanVisitImpl> getListByPlanId (Integer planId, ParamFilterPlanVisits paramFilterPlanVisits) throws Exception;
	
	public Integer getCountList(Integer planId, ParamFilterPlanVisits paramFilterPlanVisits) throws Exception;
	
	public List<Plans> getListPlans(Integer inspectionId) throws Exception;
	
	public List<Plan> getPlanSyncList(Users user) throws Exception;
	
	boolean allPlansFinished(Integer inspectionId, Integer stateFinished) throws Exception;
	
	public List<Plans> getListPlansUser(Users user) throws Exception;

	public List<Date> getListDatesPlans(Inspections inspection) throws Exception;
	
	public List<Users> getListUserPlans(Inspections inspection) throws Exception;
	
    public PlanInspection getPlanInspection(Integer inspectionId, ParamFilterPlans paramFilterPlans, Users user) throws Exception;
	
	public Integer getWeek(Integer planId) throws Exception;	
	
	public Integer getHousesArea(int planId) throws Exception;
	
	public List<Integer> getListPendingPlans() throws Exception;
	
	public List<Substitutes> getSubstitutes(Integer inspetcionId) throws Exception;

}
