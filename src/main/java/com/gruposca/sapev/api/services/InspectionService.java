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
import com.gruposca.sapev.api.connectors.dao.model.Inspections;
import com.gruposca.sapev.api.modelview.Inspection;
import com.gruposca.sapev.api.modelview.InspectionBlock;
import com.gruposca.sapev.api.modelview.InspectionImpl;
import com.gruposca.sapev.api.modelview.InspectionList;
import com.gruposca.sapev.api.modelview.ParamFilterInspections;
import com.gruposca.sapev.api.modelview.ParamFilterPlans;
import com.gruposca.sapev.api.modelview.ParamFilterVisits;
import com.gruposca.sapev.api.modelview.Plan;
import com.gruposca.sapev.api.modelview.PlansInspectionList;
import com.gruposca.sapev.api.modelview.SampleInspection;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.VisitList;

public interface InspectionService {

	InspectionList getListInspection(Session session, ParamFilterInspections paramFilterInspections);	
	
	Inspection getInspection(Integer id);
	
	boolean deleteInspection(Integer id);
	
	Inspections createInspection(InspectionImpl inspection);

	Inspections updateInspections(Integer id, InspectionImpl inspection);
	
	List<Plan> getListPlans(Integer inspectionId);
	
	VisitList getListVisit(Session session, Integer inspectionId, ParamFilterVisits paramFilterVisits);
	
	boolean closeInspection(Integer id);
	
	String createXls(Integer id);
	
	PlansInspectionList getListPlans(Session session, Integer inspectionId, ParamFilterPlans paramFilterPlans);

	List<InspectionBlock> getBlocks(Integer id);
	
	String getSusbstitutesReport(Integer id);
	
	List<SampleInspection> getListSamples(Integer inspectionId);

	String createSamplesXls(Session session, Integer inspectionId);
	
	
}