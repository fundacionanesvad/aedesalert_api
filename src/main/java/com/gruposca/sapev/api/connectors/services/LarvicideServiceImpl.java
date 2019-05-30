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
package com.gruposca.sapev.api.connectors.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.gruposca.sapev.api.connectors.dao.impl.LarvicideDao;
import com.gruposca.sapev.api.connectors.dao.impl.ScheduleDao;
import com.gruposca.sapev.api.connectors.dao.model.Larvicides;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.modelview.Larvicide;
import com.gruposca.sapev.api.modelview.LarvicideList;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.LarvicideService;

public class LarvicideServiceImpl extends AbsService implements LarvicideService{

	public LarvicideServiceImpl(AbsConnector connector) { super( connector); }

	@Override
	public List<LarvicideList> getLarvicideList(Session session) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<LarvicideList> listLarvicides  = new ArrayList<LarvicideList>();

		try{	
			LarvicideDao larvicideManager = (LarvicideDao) ctx.getBean("LarvicideDaoImpl");			
			listLarvicides = larvicideManager.getListLarvicides();
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLISTLARVICIDE + e.toString());
		}finally{
			ctx.close();
		}
		return  listLarvicides;
	}

	@Override
	public Larvicide getLarvicide(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Larvicides entityLarvicide = new Larvicides();
		Larvicide larvicide = null;		
		try{			
			LarvicideDao larvicideManager = (LarvicideDao) ctx.getBean("LarvicideDaoImpl");
			entityLarvicide = larvicideManager.find(id);				
			if(entityLarvicide.getId() != null){				
				larvicide = new Larvicide(entityLarvicide.getName(),
										  entityLarvicide.getUnity(), 
										  entityLarvicide.getDose(), 
										  entityLarvicide.getDoseName(), 
										  entityLarvicide.getWaterVolume(),
										  entityLarvicide.isEnabled());
			}
			
		}catch(Exception ex){
		    logger.error(RestErrorImpl.METHOD_GETLARVICIDE +ex.toString());
		}finally{
			ctx.close();
		}
		return larvicide;
	}

	@Override
	public Larvicides createLarvicide(Larvicide larvicide) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		LarvicideDao larvicideManager = (LarvicideDao) ctx.getBean("LarvicideDaoImpl");
		Larvicides entityLarvicide;
		try{
			entityLarvicide = new Larvicides(larvicide.getName(), larvicide.getUnity(), larvicide.getDose(),larvicide.getDoseName(), larvicide.getWaterVolume(), larvicide.isEnabled());
			entityLarvicide = larvicideManager.save(entityLarvicide);
			if(entityLarvicide != null){
				return entityLarvicide;								
			}else{
				return null;
			}				
		}catch (Exception ex){
		    logger.error(RestErrorImpl.METHOD_CREATELARVICIDE +ex.toString());
		    return null;
		
		}finally{
			ctx.close();
		}		
	}

	@Override
	public Larvicides updateLarvicide(Integer id, Larvicide larvicide) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{			
			LarvicideDao larvicideManager = (LarvicideDao) ctx.getBean("LarvicideDaoImpl");
			Larvicides updateLarvicide = larvicideManager.find(id);
			if(updateLarvicide != null){
				updateLarvicide.setName(larvicide.getName());
				updateLarvicide.setUnity(larvicide.getUnity());
				updateLarvicide.setDose(larvicide.getDose());	
				updateLarvicide.setDoseName(larvicide.getDoseName());
				updateLarvicide.setWaterVolume(larvicide.getWaterVolume());				
				updateLarvicide.setEnabled(larvicide.isEnabled());				
				updateLarvicide = larvicideManager.save(updateLarvicide);	
			}
			
			return updateLarvicide;
		
		}catch(Exception ex){
		    logger.error(RestErrorImpl.METHOD_UPDATELARVICIDE +ex.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}

	@Override
	public Boolean deleteLarvicide(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		LarvicideDao larvicideManager = (LarvicideDao) ctx.getBean("LarvicideDaoImpl");
		ScheduleDao scheduleManager = (ScheduleDao) ctx.getBean("ScheduleDaoImpl");
		try{
			Larvicides larvicide = larvicideManager.find(id);			
			if(scheduleManager.larvicideInSchedule(larvicide.getId())) {
				return false;
			}else {
				larvicideManager.delete(larvicide);
			}		
			
		}catch (Exception ex){
		    logger.error(RestErrorImpl.METHOD_DELETELARVICIDE +ex.toString());
		
		}finally{
			ctx.close();
		}	
		return true;
	}	
	
			
}
