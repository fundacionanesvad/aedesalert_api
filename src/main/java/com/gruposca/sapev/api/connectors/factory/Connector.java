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

public class Connector extends AbsConnector{
 
	@Override
	public UserService getUserService() {return ServiceFactory.getUserService(this);}

	@Override
	public SecurityService getSecurityService() {return ServiceFactory.getSecurityService(this);}

	@Override
	public Session createSession(String authorizationToken) {return ServiceFactory.createSession(authorizationToken);}

	@Override
	public ProfileService getProfileService() {return ServiceFactory.getProfileService(this);}

	@Override
	public LanguageService getLanguageService() {return ServiceFactory.getLanguageService(this);}

	@Override
	public AccountService getAccountService() {return ServiceFactory.getAccountService(this);}

	@Override
	public AreaService getAreaService() {return ServiceFactory.getAreaService(this);}

	@Override
	public TableService getTableService() {return ServiceFactory.getTableService(this);}

	@Override
	public ElementService getElementService() {return ServiceFactory.getElementService(this);}

	@Override
	public InspectionService getInspectionService() {return ServiceFactory.getInspectionService(this);}

	@Override
	public HouseService getHouseService() {	return ServiceFactory.getHouseService(this);}

	@Override
	public VisitService getVisitService() {return ServiceFactory.getVisitService(this);}

	@Override
	public SyncService getSyncService() {return ServiceFactory.getSyncService(this);}

	@Override
	public SceneService getSceneService() {return ServiceFactory.getSceneService(this);}

	@Override
	public PlanService getPlanService() { return ServiceFactory.getPlanService(this);}
	
	@Override
	public AlertService getAlertService() { return ServiceFactory.getAlertService(this);}
	
	@Override
	public SampleService getSampleService() { return ServiceFactory.getSampleService(this);}
	
	@Override
	public ReportService getReportService() { return ServiceFactory.getReportService(this);}	

	@Override
	public ModuleService getModuleService() {return ServiceFactory.getModuleService(this);	}

	@Override
	public FebrileService getFebrileService() {return ServiceFactory.getFebrileService(this);}

	@Override
	public ConsolidatedService getConsolidatedService() {return ServiceFactory.getConsolidatedService(this);}
	
	@Override
	public TrapService getTrapService() {return ServiceFactory.getTrapService(this);}
	
	@Override
	public ScheduleService getScheduleService() {return ServiceFactory.getScheduleService(this);}	
	
	@Override
	public LarvicideService getLarvicideService() {return ServiceFactory.getLarvicideService(this);}	
}
