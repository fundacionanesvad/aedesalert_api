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

import com.gruposca.sapev.api.connectors.services.AccountServiceImpl;
import com.gruposca.sapev.api.connectors.services.AlertServiceImpl;
import com.gruposca.sapev.api.connectors.services.AreaServiceImpl;
import com.gruposca.sapev.api.connectors.services.ConsolidatedServiceImpl;
import com.gruposca.sapev.api.connectors.services.ElementServiceImpl;
import com.gruposca.sapev.api.connectors.services.FebrileServiceImpl;
import com.gruposca.sapev.api.connectors.services.HouseServiceImpl;
import com.gruposca.sapev.api.connectors.services.InspectionServiceImpl;
import com.gruposca.sapev.api.connectors.services.LanguageServiceImpl;
import com.gruposca.sapev.api.connectors.services.LarvicideServiceImpl;
import com.gruposca.sapev.api.connectors.services.ModuleServiceImpl;
import com.gruposca.sapev.api.connectors.services.PlanServiceImpl;
import com.gruposca.sapev.api.connectors.services.ProfileServiceImpl;
import com.gruposca.sapev.api.connectors.services.ReportServiceImpl;
import com.gruposca.sapev.api.connectors.services.SampleServiceImpl;
import com.gruposca.sapev.api.connectors.services.SceneServiceImpl;
import com.gruposca.sapev.api.connectors.services.ScheduleServiceImpl;
import com.gruposca.sapev.api.connectors.services.SecurityServiceImpl;
import com.gruposca.sapev.api.connectors.services.SyncServiceImpl;
import com.gruposca.sapev.api.connectors.services.TableServiceImpl;
import com.gruposca.sapev.api.connectors.services.TrapServiceImpl;
import com.gruposca.sapev.api.connectors.services.UserServiceImpl;
import com.gruposca.sapev.api.connectors.services.VisitServiceImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.SessionImpl;
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

public final class ServiceFactory {
	
	public static Session createSession(String authorizationToken) { 
		return new SessionImpl(authorizationToken); 
	}	
	 
	public static UserService getUserService(Connector connector) { 
		return new UserServiceImpl(connector); 
	}

	public static SecurityService getSecurityService(Connector connector) {
		return new SecurityServiceImpl(connector);
	}
	
	public static ProfileService getProfileService(Connector connector) {
		return new ProfileServiceImpl(connector);
	}

	public static LanguageService getLanguageService(Connector connector) {
		return new LanguageServiceImpl(connector);
	}
	
	public static AccountService getAccountService(Connector connector) {
		return new AccountServiceImpl(connector);
	}
	
	public static AreaService getAreaService(Connector connector) {
		return new AreaServiceImpl(connector);
	}
	
	public static TableService getTableService(Connector connector) {
		return new TableServiceImpl(connector);
	}
	
	public static ElementService getElementService(Connector connector) {
		return new ElementServiceImpl(connector);
	}
	
	public static InspectionService getInspectionService(Connector connector) {
		return new InspectionServiceImpl(connector);
	}
	
	public static HouseService getHouseService(Connector connector) {
		return new HouseServiceImpl(connector);
	}
	
	public static VisitService getVisitService(Connector connector) {
		return new VisitServiceImpl(connector);
	}
	
	public static SyncService getSyncService(Connector connector) {
		return new SyncServiceImpl(connector);
	}
	
	public static SceneService getSceneService(Connector connector) {
		return new SceneServiceImpl(connector);
	}
	
	public static PlanService getPlanService(Connector connector) {
		return new PlanServiceImpl(connector);
	}
	
	public static AlertService getAlertService(Connector connector) {
		return new AlertServiceImpl(connector);
	}
	
	public static SampleService getSampleService(Connector connector) {
		return new SampleServiceImpl(connector);
	}
	
	public static ReportService getReportService(Connector connector) {
		return new ReportServiceImpl(connector);
	}
	
	public static ModuleService getModuleService(Connector connector) {
		return new ModuleServiceImpl(connector);
	}
	
	public static FebrileService getFebrileService(Connector connector) {
		return new FebrileServiceImpl(connector);
	}
	
	public static ConsolidatedService getConsolidatedService(Connector connector) {
		return new ConsolidatedServiceImpl(connector);
	}
	
	public static TrapService getTrapService(Connector connector) {
		return new TrapServiceImpl(connector);
	}
	
	public static ScheduleService getScheduleService(Connector connector) {
		return new ScheduleServiceImpl(connector);
	}
	
	public static LarvicideService getLarvicideService(Connector connector) {
		return new LarvicideServiceImpl(connector);
	}
}
