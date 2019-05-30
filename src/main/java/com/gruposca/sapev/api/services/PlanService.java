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

import com.gruposca.sapev.api.connectors.dao.model.Plans;
import com.gruposca.sapev.api.modelview.ParamFilterPlanVisits;
import com.gruposca.sapev.api.modelview.Plan;
import com.gruposca.sapev.api.modelview.PlanDetail;
import com.gruposca.sapev.api.modelview.PlanImpl;
import com.gruposca.sapev.api.modelview.PlanSummaryData;
import com.gruposca.sapev.api.modelview.PlanVisit;
import com.gruposca.sapev.api.modelview.PlanVisitList;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.SyncData;
import com.gruposca.sapev.api.modelview.VisitPlan;

public interface PlanService {
	
	PlanVisitList getListPlanVisit(Integer planId, ParamFilterPlanVisits ParamFilterPlanVisits);
	
	Plan getPlan(Integer id);

	boolean deletePlan(Integer id);
	
	Plans createPlan(PlanImpl plan);

	Plans updatePlan(Integer id, PlanImpl plan);	
	
	List<VisitPlan> getListVisits(Session session, Integer planId);
	
	boolean importSummaryPlan(Integer planId,  List<PlanSummaryData> listPlanSummaryData);
	
	PlanDetail getPlanDetail(Integer id, Session session);	

	List<Plan> getListPlans(Session session);	
	
	boolean sendInspectorReport(Integer planId);

	String getInspectorReport(Integer planId);	
	
	boolean updateSyncFilePlan(SyncData syncData);	
	
	boolean checkPlan(Integer planId);
	
	List<Integer> getListPendingPlans();
	
	boolean closePlan(Integer planId);

	boolean createVisitPlan(Integer planId, PlanVisit planVisit);
	
	boolean saveVisitPlan(Integer planId, String uuid, PlanVisit planVisit);
	
	List<PlanVisit> getListVisitsPlan(Session session,Integer id);

}
