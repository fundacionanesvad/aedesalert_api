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
import java.util.UUID;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.gruposca.sapev.api.connectors.dao.impl.InventoryDao;
import com.gruposca.sapev.api.connectors.dao.impl.LabelDao;
import com.gruposca.sapev.api.connectors.dao.impl.PersonDao;
import com.gruposca.sapev.api.connectors.dao.impl.SampleDao;
import com.gruposca.sapev.api.connectors.dao.impl.SymptomDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.impl.VisitDao;
import com.gruposca.sapev.api.connectors.dao.model.Inventories;
import com.gruposca.sapev.api.connectors.dao.model.Persons;
import com.gruposca.sapev.api.connectors.dao.model.Samples;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.dao.model.Visits;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.modelview.HouseVisitImpl;
import com.gruposca.sapev.api.modelview.InventoryList;
import com.gruposca.sapev.api.modelview.ParamFilterVisits;
import com.gruposca.sapev.api.modelview.Person;
import com.gruposca.sapev.api.modelview.PersonListImpl;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Sample;
import com.gruposca.sapev.api.modelview.SampleVisitListImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.Visit;
import com.gruposca.sapev.api.modelview.VisitImpl;
import com.gruposca.sapev.api.modelview.VisitInventory;
import com.gruposca.sapev.api.modelview.VisitList;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.VisitService;

public class VisitServiceImpl extends AbsService implements VisitService{

	public VisitServiceImpl(AbsConnector connector) { super( connector); }

	@Override
	public VisitList getVisitList(Session session, ParamFilterVisits paramFilterVisits) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
		UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
		VisitList visitList = new VisitList();
		try{		
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			visitList = visitManager.getList(user, paramFilterVisits, 0);		
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETVISITLIST +e.toString());
			visitList = null;
			
		}finally{
			ctx.close();
		}
		
		return visitList;	
	}

	@Override
	public Visit getVisit(Session session,String uuid) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
		LabelDao labelManager = (LabelDao) ctx.getBean("LabelDaoImpl");
		UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
		Visits entityVisits = new Visits();
		Visit visit = new Visit() {
		};
		try{
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			entityVisits = visitManager.findByUUID(UUID.fromString(uuid));				
			String labelValue = labelManager.getValueElement(user.getLanguages(), entityVisits.getTableElements());
			
			visit = new VisitImpl(entityVisits.getDate().getTime(),
                                  entityVisits.getFeverish(),
                                  entityVisits.getLarvicide(),
                                  entityVisits.getComments(),
                                  labelValue,
                                  entityVisits.getPlans().getInspections().getId(),
                                  new HouseVisitImpl(entityVisits.getHouses().getUuid().toString(), entityVisits.getHouses().getCode()),
                                  entityVisits.isRequalify());			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETVISIT +e.toString());
			visit=null;
		}finally{
			ctx.close();
		}
		return  visit;
	}

	@Override
	public VisitInventory getInventories(Session session, String uuid) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		InventoryDao inventoryManager = (InventoryDao) ctx.getBean("InventoryDaoImpl");
		LabelDao labelManager = (LabelDao) ctx.getBean("LabelDaoImpl");
		UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
		VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
		VisitInventory visitInventory;
		List<InventoryList> list = new ArrayList<InventoryList>();
		Inventories entityInventories = new Inventories();
		InventoryList inventoryList;
		try{
			List<Inventories> entityInventoryList = inventoryManager.getInventories(UUID.fromString(uuid));		
			Users user = userManager.getUserByToken(session.getAuthorizationToken());

			for(int i = 0; i < entityInventoryList.size(); i++){				
				
				entityInventories = entityInventoryList.get(i);
				String labelValue = labelManager.getValueElement(user.getLanguages(), entityInventories.getTableElements());

				inventoryList = new InventoryList(entityInventories.getTableElements().getId(),
												 labelValue,
												 entityInventories.getInspected(),
												 entityInventories.getFocus(),
												 entityInventories.getTreated(),
												 entityInventories.getPacket(),
												 entityInventories.getDestroyed());
				list.add(inventoryList);
			}
			
			Visits visit = visitManager.findByUUID(UUID.fromString(uuid));			
			visitInventory = new VisitInventory(visit.getPlans().getInspections().getLarvicides(), list);
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETINVENTORIES +e.toString());
		    visitInventory = null;
		}finally{
			ctx.close();
		}
		return  visitInventory;
	}

	@Override
	public List<Sample> getSamples(Session session, String uuid) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		SampleDao sampleManager = (SampleDao) ctx.getBean("SampleDaoImpl");
		LabelDao labelManager = (LabelDao) ctx.getBean("LabelDaoImpl");
		UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
		VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
		List<Sample> sampletList = new ArrayList<Sample>();
		Samples entitySample = new Samples();
		Sample sample;
		try{
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			Visits visit = visitManager.findByUUID(UUID.fromString(uuid));
			List<Samples> entitySamplesList = sampleManager.getSanplesList(visit);	
			for(int i = 0; i < entitySamplesList.size(); i++){
				entitySample = entitySamplesList.get(i);				
				String containerValue = labelManager.getValueElement(user.getLanguages(), entitySample.getContainer());
				String phaseNames = sampleManager.getSanplesPhasesNames(entitySample.getUuid(), user.getLanguages().getId());
				sample = new SampleVisitListImpl(entitySample.getContainer().getId(),
											containerValue,
											entitySample.getCode(),
											phaseNames,
											entitySample.getResult());					
				sampletList.add(sample);
			}
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETSAMPLES +e.toString());
			sampletList=null;
		}finally{
			ctx.close();
		}
		return  sampletList;
	}

	@Override
	public List<Person> getPersons(Session session, String uuid) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		PersonDao personManager = (PersonDao) ctx.getBean("PersonDaoImpl");
		SymptomDao symptomManager = (SymptomDao) ctx.getBean("SymptomDaoImpl");
		UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
		List<Person> persontList = new ArrayList<Person>();
		List<String> symptomsList = new ArrayList<String>();
		Persons entityPerson = new Persons();
		Person person;
		try{			
			List<Persons> entityPersonsList = personManager.getPersonList(UUID.fromString(uuid));		

			Users user = userManager.getUserByToken(session.getAuthorizationToken());

			for(int i = 0; i < entityPersonsList.size(); i++){
				
				entityPerson = entityPersonsList.get(i);					
				symptomsList = symptomManager.getSymptomList(entityPerson.getUuid(),UUID.fromString(uuid), user);				
				person = new PersonListImpl(entityPerson.getGenre(),
						                    entityPerson.getName(),
											entityPerson.getBirthday().getTime(),
											entityPerson.isBirthdayExact(),
											entityPerson.getCardId(),
											symptomsList);						
				persontList.add(person);
			}
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETPERSONS +e.toString());
			persontList=null;
		}finally{
			ctx.close();
		}
		return  persontList;
	}

	@Override
	public boolean deleteVisit(String uuid) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		boolean result = false;		
		try{	
			VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
			Visits visits = visitManager.findByUUID(UUID.fromString(uuid));
			visitManager.deleteVisit(UUID.fromString(uuid));
			visitManager.delete(visits);			
			result = true;			
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_DELETEVISIT + e.toString());
		}finally{
			ctx.close();
		}	
		return result;
	}


}
