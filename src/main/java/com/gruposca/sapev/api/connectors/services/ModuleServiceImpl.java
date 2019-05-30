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
 ******************************************************************************/
package com.gruposca.sapev.api.connectors.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.gruposca.sapev.api.connectors.dao.impl.ModuleDao;
import com.gruposca.sapev.api.connectors.dao.model.Modules;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.modelview.ModuleList;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.ModuleService;

public class ModuleServiceImpl extends AbsService implements ModuleService{

	
	public ModuleServiceImpl(AbsConnector connector) { super( connector); }

	@Override
	public List<ModuleList> getModules() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<ModuleList> moduleList = new ArrayList<ModuleList>();
		Modules entityModule = new Modules();
		ModuleList module;
		try{
			ModuleDao moduleManager = (ModuleDao) ctx.getBean("ModuleDaoImpl");
			List<Modules> entityModuleList = moduleManager.findAll();
			
			for(int i = 0; i < entityModuleList.size(); i++){				
				entityModule = entityModuleList.get(i);				
				module = new ModuleList(entityModule.getId(), entityModule.getVerb(), entityModule.getModule(),entityModule.getGroup());
				moduleList.add(module);
			}
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETMODULELIST + e.toString());
		    moduleList = null;
			
		}finally{
			ctx.close();
		}
		
		return moduleList;
	}
	
	
	
}
