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
package com.gruposca.sapev.api.connectors.factory;

import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.services.AccountService;
import com.gruposca.sapev.api.services.AlertService;
import com.gruposca.sapev.api.services.AreaService;
import com.gruposca.sapev.api.services.ConsolidatedService;
import com.gruposca.sapev.api.services.ElementService;
import com.gruposca.sapev.api.services.FebrileService;
import com.gruposca.sapev.api.services.HouseService;
import com.gruposca.sapev.api.services.InspectionService;
import com.gruposca.sapev.api.services.LanguageService;
import com.gruposca.sapev.api.services.LarvicideService;
import com.gruposca.sapev.api.services.ModuleService;
import com.gruposca.sapev.api.services.PlanService;
import com.gruposca.sapev.api.services.ProfileService;
import com.gruposca.sapev.api.services.ReportService;
import com.gruposca.sapev.api.services.SampleService;
import com.gruposca.sapev.api.services.SceneService;
import com.gruposca.sapev.api.services.ScheduleService;
import com.gruposca.sapev.api.services.SecurityService;
import com.gruposca.sapev.api.services.SyncService;
import com.gruposca.sapev.api.services.TableService;
import com.gruposca.sapev.api.services.TrapService;
import com.gruposca.sapev.api.services.UserService;
import com.gruposca.sapev.api.services.VisitService;

public abstract class AbsConnector {

	public abstract Session createSession(String authorizationToken);

	public abstract UserService getUserService();
	
	public abstract SecurityService getSecurityService();
	
	public abstract ProfileService getProfileService();
	
	public abstract LanguageService getLanguageService();
	
	public abstract AccountService getAccountService();
	
	public abstract AreaService getAreaService();
	
	public abstract TableService getTableService();

	public abstract ElementService getElementService();	
	
	public abstract InspectionService getInspectionService();	
	
	public abstract HouseService getHouseService();	
	
	public abstract VisitService getVisitService();	
	
	public abstract SyncService getSyncService();	
	
	public abstract SceneService getSceneService();	
	
	public abstract AlertService getAlertService();	
	
	public abstract PlanService getPlanService();	
	
	public abstract SampleService getSampleService();
	
	public abstract ReportService getReportService();		
	
	public abstract ModuleService getModuleService();	
	
	public abstract FebrileService getFebrileService();	
	
	public abstract ConsolidatedService getConsolidatedService();	

	public abstract TrapService getTrapService();	
	
	public abstract ScheduleService getScheduleService();	
	
	public abstract LarvicideService getLarvicideService();	

}
