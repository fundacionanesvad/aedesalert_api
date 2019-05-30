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

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gruposca.sapev.api.connectors.dao.impl.VisitSummaryDao;
import com.gruposca.sapev.api.connectors.dao.model.VisitSummaries;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.ConsolidatedService;

public class ConsolidatedServiceImpl extends AbsService implements ConsolidatedService{

	public ConsolidatedServiceImpl(AbsConnector connector) { super( connector); }

	@Override
	public int updateVisitSummaries() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		
		int num=0;
			try{			
			VisitSummaryDao manager = (VisitSummaryDao) ctx.getBean("VisitSummarieDaoImpl");	
			List<VisitSummaries> list = manager.findAll();
			for(int i = 0 ; i< list.size() ; i++){
				
				VisitSummaries visitSummaries = list.get(i);				
				Integer foco = manager.getFocus(visitSummaries.getAreas().getId(), visitSummaries.getPlans().getId());
				Integer treated = manager.getTreated(visitSummaries.getAreas().getId(), visitSummaries.getPlans().getId());
				Integer destroyed = manager.getDestroyed(visitSummaries.getAreas().getId(), visitSummaries.getPlans().getId());				
				visitSummaries.setFocus(foco);
				visitSummaries.setTreated(treated);
				visitSummaries.setDestroyed(destroyed);				
				manager.save(visitSummaries);
				num++;				
			}										

		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETREPORT +e.toString());
			
		}finally{
			ctx.close();
		}		
		return num;
	}

	

}
