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
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.gruposca.sapev.api.connectors.dao.impl.AreaDao;
import com.gruposca.sapev.api.connectors.dao.impl.HouseDao;
import com.gruposca.sapev.api.connectors.dao.impl.InventoryDao;
import com.gruposca.sapev.api.connectors.dao.impl.PersonDao;
import com.gruposca.sapev.api.connectors.dao.impl.PlanDao;
import com.gruposca.sapev.api.connectors.dao.impl.PlansAreasDao;
import com.gruposca.sapev.api.connectors.dao.impl.TableElementsDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.impl.VisitDao;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Houses;
import com.gruposca.sapev.api.connectors.dao.model.Persons;
import com.gruposca.sapev.api.connectors.dao.model.Plans;
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.dao.model.Visits;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.modelview.Area;
import com.gruposca.sapev.api.modelview.Element;
import com.gruposca.sapev.api.modelview.House;
import com.gruposca.sapev.api.modelview.Person;
import com.gruposca.sapev.api.modelview.Plan;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.SyncAreaPlan;
import com.gruposca.sapev.api.modelview.SyncHousePlan;
import com.gruposca.sapev.api.modelview.SyncPersonPlan;
import com.gruposca.sapev.api.modelview.SyncPlanImpl;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.SyncService;
import com.gruposca.sapev.api.connectors.dao.model.PlansAreas;


public class SyncServiceImpl extends AbsService implements SyncService{

	public SyncServiceImpl(AbsConnector connector) { super( connector); }

	@Override
	public List<Plan> getPlansList(Session session) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<Plan> plansList = new ArrayList<Plan>();		
		
		try{	
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			plansList = planManager.getPlanSyncList(user);
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_SYNC_GETPLANLIST + e.toString());
			plansList = null;
			
		}finally{
			ctx.close();
		}
		
		return plansList;	
	}

	@Override
	public Plan getPlan(Session session, Integer id) {
		
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Plan plan;
		Plans entityPlan;
		List<Element> listElements = new ArrayList<Element>();
		try{
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			PlansAreasDao planAreasManager = (PlansAreasDao) ctx.getBean("PlansAreasDaoImpl");
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			entityPlan = planManager.find(id);
			List<PlansAreas> listPlanAreas = planAreasManager.getList(entityPlan);
			listElements = tableElementsManager.getList(user.getLanguages().getId());	
			Integer typeId = entityPlan.getInspections().getSchedules().getTableElements().getId();
			Integer reconversionScheduleId = entityPlan.getInspections().getSchedules().getReconversionScheduleId();	
			plan = new SyncPlanImpl(entityPlan.getPlanSize(), this.getAreaList(listPlanAreas), listElements, typeId, reconversionScheduleId, entityPlan.getInspections().getLarvicides());
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_SYNC_GETPLAN + e.toString());
			plan = null;
		}finally{
			ctx.close();
		}	
		return plan;
	}
	
	//***//
	public List<Area> getAreaList(List<PlansAreas> listPlanAreas){
		List<Area> areaList = new ArrayList<Area>();	
		try {			
			for(int i = 0; i < listPlanAreas.size(); i++){
				PlansAreas planArea = listPlanAreas.get(i);		
				int areaId = planArea.getAreas().getId();		
				System.out.println("areaId:"+areaId);
				SyncAreaPlan syncAreaPlan = new SyncAreaPlan(areaId, planArea.getAreas().getName(), this.getUbigeoCode(areaId), this.getHouseList(areaId), planArea.isSubstitute(), planArea.getPin(),planArea.getAreas().getAreas().getName());				
				areaList.add(syncAreaPlan);		
			}
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_SYNC_GETAREALIST + e.toString());
			areaList = null;
		}		
		return areaList;		
	}	
	
	
	public List<House> getHouseList(Integer areaId){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<House> houseList = new ArrayList<House>();		
		try{	
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");
			houseList = houseManager.getList(areaId);	

		}catch (Exception e){
		    logger.error("METHOD_SYNC_GETHOUSELIST" + e.toString());
			houseList = null;
		}finally{
			ctx.close();
		}
		return houseList;		
	}	
	
	/*public List<House> getHouseList(Integer areaId){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<House> houseList = new ArrayList<House>();		
		try{	
			
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");
			VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
			InventoryDao inventoryDaoManager = (InventoryDao) ctx.getBean("InventoryDaoImpl");
			List<Houses> entityListHouses = houseManager.getHousesList(areaId);	
			for(int i = 0; i < entityListHouses.size(); i++){
				Houses entityHouses = entityListHouses.get(i);	
				Visits entityVisit = visitManager.getLastVisit(entityHouses.getUuid());	
				Integer lastVisitScheduleId = entityVisit.getUuid() != null ? visitManager.getScheduleId(entityVisit.getUuid()) : null;

				House house = new SyncHousePlan(entityHouses.getUuid().toString(),
												entityHouses.getNumber(),
												entityHouses.getCode(),
												entityHouses.getQrcode(),
												entityHouses.getLatitude(),
												entityHouses.getLongitude(),
												entityHouses.getStreetName(),
												entityHouses.getStreetNumber(),												
												(entityVisit.getDate() != null) ? entityVisit.getDate().getTime() : null,
												(entityVisit.getTableElements() != null) ? entityVisit.getTableElements().getId() : null,
												(inventoryDaoManager.focusInInventories(entityVisit.getUuid())) ? true : false,
												(entityVisit.getFeverish() == new Byte("1")) ? true : false,
												lastVisitScheduleId,
												entityHouses.getPersonsNumber(),																
												this.getPersonList(entityHouses.getUuid()));						
				houseList.add(house);					
			}			
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_SYNC_GETHOUSELIST + e.toString());
			houseList = null;
		}finally{
			ctx.close();
		}
		return houseList;		
	}*/
	
	public List<Person> getPersonList(UUID houseUuid){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<Person> personList = new ArrayList<Person>();		
		try{	
			
			PersonDao personManager = (PersonDao) ctx.getBean("PersonDaoImpl");
			List<Persons> entityListPersons = personManager.getPersonLisByHouse(houseUuid)	;
			for(int i = 0; i < entityListPersons.size(); i++){
				Persons entityPersons = entityListPersons.get(i);				
				Person person = new SyncPersonPlan(entityPersons.getUuid().toString(),
												   entityPersons.getGenre(),
												   entityPersons.getBirthday().getTime(),
												   entityPersons.isBirthdayExact(),
												   entityPersons.isEnabled(),
												   entityPersons.getName(),
												   entityPersons.getCardId());		
				
				personList.add(person);
			}			
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_SYNC_GETPERSONLIST + e.toString());
			personList = null;
		}finally{
			ctx.close();
		}
		return personList;		
	}
	
	public String getUbigeoCode(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		String ubigeoCode = "";		
		try{	
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");		
			Areas entityArea = areaManager.find(id);			
			
			if(entityArea.getAreas() == null){				
				return ubigeoCode;				
			}else{		
				
				ubigeoCode = entityArea.getCode();
				
				Areas areaParent = areaManager.find(entityArea.getAreas().getId());		
				while (areaParent.getAreas() != null){					
					ubigeoCode = areaParent.getCode() + ubigeoCode;				
					areaParent = areaManager.find(areaParent.getAreas().getId());					
				}				
			}
			return ubigeoCode;
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETUBIGEOCODE +e.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}

	@Override
	public void updatePlanState(Integer planId, Integer state) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{	
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");	
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			TableElements tableElements = tableElementsManager.find(state);	
			Plans plan = planManager.find(planId);
			plan.setTableElements(tableElements);
			planManager.save(plan);
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_SYNC_GETHOUSELIST + e.toString());
		}finally{
			ctx.close();
		}
			
	}

}
