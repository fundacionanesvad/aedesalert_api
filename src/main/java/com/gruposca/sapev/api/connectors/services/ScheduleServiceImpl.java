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
import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.gruposca.sapev.api.connectors.dao.impl.AreaDao;
import com.gruposca.sapev.api.connectors.dao.impl.InspectionDao;
import com.gruposca.sapev.api.connectors.dao.impl.LarvicideDao;
import com.gruposca.sapev.api.connectors.dao.impl.ScheduleDao;
import com.gruposca.sapev.api.connectors.dao.impl.TableElementsDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Larvicides;
import com.gruposca.sapev.api.connectors.dao.model.Schedules;
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.modelview.Area;
import com.gruposca.sapev.api.modelview.AreaChildsImpl;
import com.gruposca.sapev.api.modelview.Child;
import com.gruposca.sapev.api.modelview.InspectionSchedule;
import com.gruposca.sapev.api.modelview.ParamFilterSchedule;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Schedule;
import com.gruposca.sapev.api.modelview.ScheduleList;
import com.gruposca.sapev.api.modelview.ScheduleListImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.ScheduleService;

public class ScheduleServiceImpl extends AbsService implements ScheduleService{

	public ScheduleServiceImpl(AbsConnector connector) { super( connector); }	
	
	@Override
	public ScheduleList getScheduleList(Session session, ParamFilterSchedule paramFilterSchedule) {		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");		
		List<ScheduleListImpl> scheduleListImpl = new ArrayList<ScheduleListImpl>();
		ScheduleList scheduleList = new ScheduleList();
		try{		
			ScheduleDao scheduleManager = (ScheduleDao) ctx.getBean("ScheduleDaoImpl");		
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");	
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			scheduleListImpl = scheduleManager.getList(user, paramFilterSchedule);			
			Integer count = scheduleManager.getCountList(user, paramFilterSchedule);
			scheduleList = new ScheduleList(count, scheduleListImpl);
			
		}catch (Exception e){
			 logger.error(RestErrorImpl.METHOD_GETLISTSCHEDULE + e.toString());
			 scheduleList = null;
		}finally{
			ctx.close();
		}
		return scheduleList;	
	}

	@Override
	public void deleteSchedule(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		ScheduleDao scheduleManager = (ScheduleDao) ctx.getBean("ScheduleDaoImpl");
		try{
			Schedules schedule = scheduleManager.find(id);	
			scheduleManager.delete(schedule);
			
		}catch (Exception ex){
		    logger.error(RestErrorImpl.METHOD_DELETESCHEDULE +ex.toString());
		
		}finally{
			ctx.close();
		}		
	}

	@Override
	public Schedule getSchedule(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Schedules entitySchedules = new Schedules();
		Schedule schedule = null;	
 		try{			
			ScheduleDao scheduleManager = (ScheduleDao) ctx.getBean("ScheduleDaoImpl");
			entitySchedules = scheduleManager.find(id);		
			if(entitySchedules.getId() != null){
				schedule = new Schedule(entitySchedules.getStartDate().getTime(),
										entitySchedules.getFinishDate().getTime(), 
										entitySchedules.getTableElements().getId(), 
										entitySchedules.getAreas().getId(), 
										entitySchedules.getReconversionScheduleId(), 
										entitySchedules.getLarvicide().getId());
			}
			
		}catch(Exception ex){
		    logger.error(RestErrorImpl.METHOD_GETSCHEDULE +ex.toString());
		}finally{
			ctx.close();
		}
		return schedule;
	}

	@Override
	public Schedules createSchedule(Schedule schedule) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		ScheduleDao scheduleManager = (ScheduleDao) ctx.getBean("ScheduleDaoImpl");
		AreaDao areaManager = (AreaDao)ctx.getBean("AreaDaoImpl");
		TableElementsDao tableManager = (TableElementsDao)ctx.getBean("TableElementsDaoImpl");		
		LarvicideDao larvicideManager = (LarvicideDao)ctx.getBean("LarvicideDaoImpl");
		Schedules entitySchedules;
		try{
			Areas area = areaManager.find(schedule.getAreaId());
			TableElements table = tableManager.find(schedule.getTypeId());		
			Larvicides larvicide;
			if(schedule.getLarvicideId() == null || schedule.getLarvicideId() == 0) {
				larvicide = larvicideManager.getLarvicide();
			}else {
				larvicide = larvicideManager.find(schedule.getLarvicideId());
			}			
			
			entitySchedules = new Schedules(new Date(schedule.getStartDate()), 
											new Date(schedule.getFinishDate()), 
											table, 
											area, 
											schedule.getReconversionScheduleId(), 
											larvicide);			
			entitySchedules = scheduleManager.save(entitySchedules);			
			return entitySchedules;
				
		}catch (Exception ex){
		    logger.error(RestErrorImpl.METHOD_CREATESCHEDULE +ex.toString());
		    return null;		
		}finally{
			ctx.close();
		}		
	}

	@Override
	public Schedules updateSchedule(Integer id, Schedule schedule) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{	
			ScheduleDao scheduleManager = (ScheduleDao) ctx.getBean("ScheduleDaoImpl");
			AreaDao areaManager = (AreaDao)ctx.getBean("AreaDaoImpl");			
			TableElementsDao tableManager = (TableElementsDao)ctx.getBean("TableElementsDaoImpl");
			LarvicideDao larvicideManager = (LarvicideDao)ctx.getBean("LarvicideDaoImpl");

			Schedules updateSchedule = scheduleManager.find(id);
			if(updateSchedule != null){
				Areas area = areaManager.find(schedule.getAreaId());
				TableElements table = tableManager.find(schedule.getTypeId());	
				Larvicides larvicide = larvicideManager.find(schedule.getLarvicideId());
				updateSchedule.setAreas(area);
				updateSchedule.setTableElements(table);
				updateSchedule.setStartDate(new Date(schedule.getStartDate()));		
				updateSchedule.setFinishDate(new Date(schedule.getFinishDate()));	 
				updateSchedule.setReconversionScheduleId(schedule.getReconversionScheduleId());
				updateSchedule.setLarvicide(larvicide);
				updateSchedule = scheduleManager.save(updateSchedule);	
			}			
			return updateSchedule;
		
		}catch(Exception ex){
		    logger.error(RestErrorImpl.METHOD_UPDATESCHEDULE +ex.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}

	@Override
	public List<InspectionSchedule> getScheduleInspections(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");		
		List<InspectionSchedule> inspectionsList = new ArrayList<InspectionSchedule>();
		try{		
			ScheduleDao scheduleManager = (ScheduleDao) ctx.getBean("ScheduleDaoImpl");		
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");	
			Schedules schedule = scheduleManager.find(id);			
			inspectionsList = inspectionManager.getListBySchedule(schedule);
			
		}catch (Exception e){
			 logger.error(RestErrorImpl.METHOD_GETLISTSCHEDULE + e.toString());
			 inspectionsList = null;	
		}finally{
			ctx.close();
		}
		return inspectionsList;	
	}

	@Override
	public Area getAreaChilds(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Area area;
		try{	
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");		
			ScheduleDao scheduleManager = (ScheduleDao) ctx.getBean("ScheduleDaoImpl");		
			Schedules schedule = scheduleManager.find(id);			

			Areas entityArea = areaManager.find(schedule.getAreas().getId());
			String parentArea = "";
			if(entityArea.getAreas() != null){
				parentArea = areaManager.find(entityArea.getAreas().getId()).getName();
			}	
			List<Child> listChilds = new ArrayList<Child>();
			List<Areas> listAreas = areaManager.getScheduleAteasChilds(id);

			for(int i = 0; i<listAreas.size(); i++){				
				Areas areas = listAreas.get(i);				
				Child child = new Child(areas.getId(), areas.getCode(), areas.getName(), areas.getHouses(), areas.getCoords(), areas.getTableElementsByTypeId().getId(), areas.getLatitude(), areas.getLongitude());				
				listChilds.add(child);				
			}	
				area = new AreaChildsImpl(entityArea.getId(), entityArea.getName(),parentArea, entityArea.getCoords(), entityArea.getHouses(), entityArea.getTableElementsByTypeId().getId(), entityArea.getLatitude(), entityArea.getLongitude(), listChilds);
			
			return area;
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETCHILDS +e.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}
	

			
}
