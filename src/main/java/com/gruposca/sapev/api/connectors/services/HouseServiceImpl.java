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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.gruposca.sapev.api.connectors.dao.impl.AreaDao;
import com.gruposca.sapev.api.connectors.dao.impl.HouseDao;
import com.gruposca.sapev.api.connectors.dao.impl.InventoryDao;
import com.gruposca.sapev.api.connectors.dao.impl.InventorySummaryDao;
import com.gruposca.sapev.api.connectors.dao.impl.PersonDao;
import com.gruposca.sapev.api.connectors.dao.impl.PlanDao;
import com.gruposca.sapev.api.connectors.dao.impl.PlansAreasDao;
import com.gruposca.sapev.api.connectors.dao.impl.SampleDao;
import com.gruposca.sapev.api.connectors.dao.impl.SamplePhaseDao;
import com.gruposca.sapev.api.connectors.dao.impl.ScenesDao;
import com.gruposca.sapev.api.connectors.dao.impl.SymptomDao;
import com.gruposca.sapev.api.connectors.dao.impl.TableElementsDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.impl.VisitDao;
import com.gruposca.sapev.api.connectors.dao.impl.VisitSummaryDao;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Houses;
import com.gruposca.sapev.api.connectors.dao.model.Inventories;
import com.gruposca.sapev.api.connectors.dao.model.InventorySummaries;
import com.gruposca.sapev.api.connectors.dao.model.Persons;
import com.gruposca.sapev.api.connectors.dao.model.Plans;
import com.gruposca.sapev.api.connectors.dao.model.PlansAreas;
import com.gruposca.sapev.api.connectors.dao.model.SamplePhases;
import com.gruposca.sapev.api.connectors.dao.model.Samples;
import com.gruposca.sapev.api.connectors.dao.model.Scenes;
import com.gruposca.sapev.api.connectors.dao.model.Symptoms;
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.dao.model.VisitSummaries;
import com.gruposca.sapev.api.connectors.dao.model.Visits;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.modelview.House;
import com.gruposca.sapev.api.modelview.HouseAreaImpl;
import com.gruposca.sapev.api.modelview.HouseImpl;
import com.gruposca.sapev.api.modelview.HouseObjectList;
import com.gruposca.sapev.api.modelview.HouseSyncImpl;
import com.gruposca.sapev.api.modelview.HousesList;
import com.gruposca.sapev.api.modelview.InventoryListSummary;
import com.gruposca.sapev.api.modelview.InventorySync;
import com.gruposca.sapev.api.modelview.ParamFilterHouses;
import com.gruposca.sapev.api.modelview.PersonSyncImpl;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.SampleSyncImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.SymptomSyncImpl;
import com.gruposca.sapev.api.modelview.Visit;
import com.gruposca.sapev.api.modelview.VisitSumaryInsertUpdate;
import com.gruposca.sapev.api.modelview.VisitSync;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.HouseService;

public class HouseServiceImpl extends AbsService implements HouseService{

	
	public HouseServiceImpl(AbsConnector connector) { super( connector); }

	@Override
	public HousesList getHouseList(ParamFilterHouses paramFilterHouses, Session session) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<HouseObjectList> list = new ArrayList<HouseObjectList>();	
		HousesList housesList = new HousesList();
		
		try{
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");			
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");	
			Users user = userManager.getUserByToken(session.getAuthorizationToken());			
			list = houseManager.getList(paramFilterHouses,user);			
			Integer count = houseManager.getCountList(paramFilterHouses,user);			
			housesList = new HousesList(count, list);				

		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETHOUSELIST +e.toString());
		    housesList = null;
			
		}finally{
			ctx.close();
		}		
		return housesList;		
	}

	@Override
	public House getHouse(String uuid) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Houses entityHouse = new Houses();
		House house;
		
		try{
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");
			entityHouse = houseManager.findByUUID(UUID.fromString(uuid));
			house = new HouseImpl(entityHouse.getUuid().toString(),
								  entityHouse.getNumber(),
								  entityHouse.getCode(),
								  entityHouse.getQrcode(),
								  entityHouse.getLatitude(),
								  entityHouse.getLongitude(),
								  entityHouse.getStreetName(),
								  entityHouse.getStreetNumber(),
								  entityHouse.getAreas().getId(),
								  entityHouse.getAreas().getName(),
								  entityHouse.getAreas().getCoords(),
								  entityHouse.getPersonsNumber());
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETHOUSE +e.toString());
			house=null;
		}finally{
			ctx.close();
		}
		return  house;
	}
	
	
	@Override
	public Houses createHouse(HouseImpl house) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Houses entityHouse;
		try{	
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			Areas area = areaManager.find(house.getAreaId());			
			entityHouse = new Houses(UUID.fromString(house.getUuid()),
									 house.getNumber(),
									 house.getCode(),
									 (house.getQrcode() != null) ? house.getQrcode() : null,
									 (house.getLatitude() != null) ? house.getLatitude() : null,
									 (house.getLongitude() != null) ? house.getLongitude() : null,
									 (house.getStreetName() != null) ? house.getStreetName() : null,
									 (house.getStreetNumber() != null) ? house.getStreetNumber() : null,
									 area,
									 (house.getPersonsNumber() != null) ? house.getPersonsNumber() : 0);
			
			entityHouse = houseManager.save(entityHouse);			
			if(entityHouse != null){
				return entityHouse;
			}else{
				return null;
			}	

		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_CREATEHOUSE +e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public Houses updateHouse(String uuid, HouseImpl house) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		
		try{		
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			Areas area = areaManager.find(house.getAreaId());			
			Houses updateHouse = houseManager.findByUUID(UUID.fromString(uuid));
			
			if(updateHouse != null){				
				updateHouse.setUuid(UUID.fromString(uuid));
				updateHouse.setNumber(house.getNumber());
				updateHouse.setCode(house.getCode());
				updateHouse.setQrcode( (house.getQrcode() != null && !house.getQrcode().equals("")) ? house.getQrcode() : null);	
				updateHouse.setLatitude( (house.getLatitude() != null) ? house.getLatitude() : null);	
				updateHouse.setLongitude( (house.getLongitude() != null) ? house.getLongitude() : null);	
				updateHouse.setStreetName( (house.getStreetName() != null && !house.getStreetName().equals("")) ? house.getStreetName() : null);	
				updateHouse.setStreetNumber( (house.getStreetNumber() != null && !house.getStreetNumber().equals("")) ? house.getStreetNumber() : null);			
				updateHouse.setAreas(area);		
				updateHouse.setPersonsNumber( (house.getPersonsNumber() != null) ? house.getPersonsNumber() : 0);
				updateHouse = houseManager.save(updateHouse);	
				
				if(updateHouse != null){
					return updateHouse;
				}else{
					return null;
				}				
			}else{
			    logger.error(RestErrorImpl.METHOD_UPDATEHOUSE_ERROR);				
				return null;
			}			
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_UPDATEHOUSE +e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public Boolean deleteHouse(String uuid) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Boolean stateDeleted = false;
		try{
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");
			Houses house = houseManager.findByUUID(UUID.fromString(uuid));	
			houseManager.delete(house);	
			stateDeleted = true;
		}catch (Exception ex){
		    logger.error(RestErrorImpl.METHOD_DELETEHOUSE +RestErrorImpl.ERROR_RESTRICCION_FK);
		
		}finally{
			ctx.close();
		}
		return stateDeleted;
	}

	@Override
	public List<Visit> getVisitsList(Session session, String uuid) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<Visit> visitList = new ArrayList<Visit>();		
		try{	
			VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			visitList = visitManager.getListVisits(user, UUID.fromString(uuid));		
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETVISITHOUSELIST +e.toString());
			visitList = null;
			
		}finally{
			ctx.close();
		}
		
		return visitList;			
	}

	//@Override
	public boolean syncHouses(Session session, Integer planId) {
		System.out.println("INICIO SINCRONIZACIÓN");		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Boolean result = false;	
		String ubigeo = "";
		String code = "";
		int number = 0;
		HashMap<Integer, Integer> areaPlanSync = new HashMap<Integer, Integer>();
		
		List<VisitSync> listVisits = new ArrayList<VisitSync>();
		
		try{			
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");
			AreaDao areaManager = (AreaDao)  ctx.getBean("AreaDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			PlansAreasDao planAreasManager = (PlansAreasDao) ctx.getBean("PlansAreasDaoImpl");
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			InventorySummaryDao inventorySummaryManager = (InventorySummaryDao) ctx.getBean("InventorySummaryDaoImpl");
			VisitSummaryDao visitSummarieManager = (VisitSummaryDao) ctx.getBean("VisitSummaryDaoImpl");
			
			Plans entityPlan = planManager.find(planId);
			List<HouseSyncImpl> listHouses = this.loadHouses(entityPlan);
			Users user = userManager.find(entityPlan.getUsers().getId());			

			System.out.println("Procesando plan "+planId+" PASO(1)");			

			for (int i= 0; i<listHouses.size(); i++){
			
				HouseSyncImpl houseSync = listHouses.get(i);
				
				Areas area = areaManager.find(houseSync.getAreaId());				
				Houses house = houseManager.findByUUID(UUID.fromString(houseSync.getUuid()));				
	
				if(house == null) {					
					ubigeo = this.getUbigeoCodeFromSync(area.getId());
					number = houseManager.getNumberHouse(ubigeo);	
					code =  String.format("%s%04d", ubigeo, number);					
				}else {
					code = house.getCode();
					number = house.getNumber();
				}				
				System.out.println("Procesando plan "+planId+" PASO(2)");

				house = new Houses(UUID.fromString(houseSync.getUuid()),						
						  number,
						  code,	
						  houseSync.getQrcode() != null &&  !houseSync.getQrcode().equals("") ? houseSync.getQrcode() : null,							  
						  houseSync.getLatitude() != null ?  new BigDecimal(houseSync.getLatitude()) : null,
						  houseSync.getLongitude() != null ?  new BigDecimal(houseSync.getLongitude()) : null,
						  houseSync.getStreetName(),
						  houseSync.getStreetNumber(),
						  area,
						  houseSync.getPersonsNumber() != null ?  houseSync.getPersonsNumber() : 0);
				
				houseManager.save(house);		
				if(houseManager.findByUUID(UUID.fromString(houseSync.getUuid())) != null){
					Visits visit = syncVisit(user, house, houseSync.getVisit(), entityPlan);					
					if(visit != null){
						syncPersons(houseSync.getListSyncPerson(), house, visit);
					}					
				
					if(visit != null && visit.getFeverish() != 0){				
						AlertServiceImpl alertServiceImpl = new AlertServiceImpl(getConnector());
						alertServiceImpl.generateFebrilesHouseAlert(ctx, visit);						
					}
				}
				System.out.println("Procesando plan "+planId+" PASO(3)");

				Integer value = areaPlanSync.get(area.getId());
				if (value == null) {
					areaPlanSync.put(area.getId(), planId);
				}
				
				
				 if(houseSync.getVisit() != null)
	                    listVisits.add(houseSync.getVisit());
			}				
			
			if(planId > 0){		
				//Creamos los datos del consolidado
				
				Integer resultInsertUpdate = 0;
				System.out.println("Procesando plan "+planId+" PASO(4)");

				for (Map.Entry<Integer, Integer> entry : areaPlanSync.entrySet()) {	
					int area = entry.getKey();
					int plan = entry.getValue();	
					
					List<InventorySummaries> listInventory = inventorySummaryManager.getList(area, plan);						
					
					if(listInventory.size() == 0){
						resultInsertUpdate = inventorySummaryManager.insertData(area, plan);						
					}else{
						for(int i = 0; i < listInventory.size(); i++){
							InventorySummaries inventorySummaries = listInventory.get(i);							
							InventoryListSummary inventoryListSummary = inventorySummaryManager.getData(area, plan, inventorySummaries.getContainer().getId());							
							inventorySummaries.setDestroyed(inventoryListSummary.getDestroyed());
							inventorySummaries.setFocus(inventoryListSummary.getFocus());
							inventorySummaries.setInspected(inventoryListSummary.getInspected());
							inventorySummaries.setTreated(inventoryListSummary.getTreated());							
							inventorySummaryManager.save(inventorySummaries);	
							resultInsertUpdate++;
						}	
					}					
					VisitSummaries visitSummaries = visitSummarieManager.getVisitSummarie(area, plan);
					VisitSumaryInsertUpdate visitSumaryInsertUpdate = visitSummarieManager.getData(area, plan);

					if(visitSummaries != null){	
						resultInsertUpdate = visitSummarieManager.updateData(area, plan, visitSumaryInsertUpdate); 
					}else{
						resultInsertUpdate = visitSummarieManager.insertData(area, plan, visitSumaryInsertUpdate);
					}
					
					Integer foco = visitSummarieManager.getFocus(area, plan);
					Integer treated = visitSummarieManager.getTreated(area, plan);
					Integer destroyed = visitSummarieManager.getDestroyed(area, plan);	
					Integer persons = visitSummarieManager.getPersons(area, plan);		
					visitSummaries = visitSummarieManager.getVisitSummarie(area, plan);					
					if(visitSummaries != null){
						visitSummaries.setFocus(foco);
						visitSummaries.setTreated(treated);
						visitSummaries.setDestroyed(destroyed);		
						visitSummaries.setPeople(persons);
						visitSummarieManager.save(visitSummaries);	
					}	
					
					PlansAreas plansAreas = planAreasManager.getPlanArea(plan, area);
					if(plansAreas.isSubstitute()) {
						plansAreas.setPin(null);
						plansAreas.setSubstitute(false);
						planAreasManager.save(plansAreas);
					}
					
					System.out.println("Procesando plan "+planId+" PASO(5)");

				}				
				
				//Actualizamos el estado del plan a Terminado			
				TableElements tableElements = tableElementsManager.find(ConfigurationHelper.getStatePlanFinished());	
				entityPlan.setTableElements(tableElements);
				planManager.save(entityPlan);		
				System.out.println("Plan cerrando correctamente. INFORMACION ID PLAN:"+ entityPlan.getId()+" - USUARIO:"+user.getName());				
				
				PlanServiceImpl planServiceImpl = new PlanServiceImpl(getConnector());
				planServiceImpl.sendInspectorReport(planId);			
				result = true;	

			}			
				
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_SYNCHOUSES +e.toString());
			result = false;			

		}finally{
			ctx.close();
		}		
		return result;
	}
	
	
	private Visits syncVisit(Users user, Houses house, VisitSync visitSync, Plans plan )throws Exception{
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Visits visit = null;
		VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
		boolean requalify = false;
		String beforeRecalifying = null;
		try{
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");	
			
			TableElements tableElement = tableElementsManager.find(visitSync.getResultId());
		
			Integer beforeStatus = 0;
			if(plan.getInspections().getSchedules().getReconversionScheduleId() != null && plan.getInspections().getSchedules().getReconversionScheduleId() > 0) {
				requalify = true;
				Integer reconversionScheduleId = plan.getInspections().getSchedules().getReconversionScheduleId();
				beforeStatus = visitManager.reconvertedVisit(reconversionScheduleId, house.getUuid());
			}
			
			if(beforeStatus == 2002) {
				beforeRecalifying = "C";
			}else if(beforeStatus == 2003) {
				beforeRecalifying = "R";
			}else if(beforeStatus == 2004) {
				beforeRecalifying = "A";
			}			
			
			if(visitSync.getLarvicide() == null || visitSync.getLarvicide().compareTo(BigDecimal.ZERO) == 0){
				int packets = 0;
				
				List<InventorySync> list = visitSync.getListSyncInventories();
				for(int n = 0; n < list.size(); n++) {					
					packets += list.get(n).getPacket() != null ? list.get(n).getPacket() : 0;
				}			
                visitSync.setLarvicide(plan.getInspections().getLarvicides().getDose().multiply(new BigDecimal(packets)));

			}
			
			visit = new Visits(UUID.fromString(visitSync.getUuid()),
									   house, 
									   plan,
									   tableElement,
									   user,
									   new Date(visitSync.getDate()),
									   visitSync.getFeverish() != null ? visitSync.getFeverish() : null,
									   visitSync.getLarvicide() != null ? visitSync.getLarvicide() : null,
									   visitSync.getComments() != null ? visitSync.getComments() : null,
									   requalify,
									   beforeRecalifying);	
			
			visitManager.save(visit);	
			syncInventories(visitSync.getListSyncInventories(), visit);			
		
		}catch (Exception e){				
			visitManager.delete(visit);
		    logger.error(RestErrorImpl.METHOD_SYNCVISITS +e.toString());
		}finally{
			ctx.close();
		}	
		return visit;
	}
	
	private void syncInventories(List<InventorySync> listInventories, Visits visit) throws Exception{
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		
		try{	

			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			InventoryDao inventoryManager = (InventoryDao)ctx.getBean("InventoryDaoImpl");
			ScenesDao sceneManager = (ScenesDao) ctx.getBean("ScenesDaoImpl");

			for (int i= 0; i<listInventories.size(); i++){
				
				InventorySync inventorySync = listInventories.get(i);			
				TableElements container = tableElementsManager.find(inventorySync.getContainerId());
				
				Inventories inventory = new Inventories(UUID.fromString(inventorySync.getUuid()), 
														 container, 
														 visit,
														 inventorySync.getInspected(),
														 inventorySync.getFocus(),
														 inventorySync.getTreated(),
														 inventorySync.getPacket(),
														 inventorySync.getDestroyed());			
				inventoryManager.save(inventory);
	
				syncSamples(inventorySync.getListSyncSample(), inventory, visit);				
				
				// Se comprueba el estado del escenario y si no está en nivel 3 se actualiza
				// Por el momento solo hay un escenario con id = 1.
				if(inventorySync.getListSyncSample().size()>0){					
					Scenes scenes = sceneManager.find(1);
					if(scenes.getSceneLevel() != 3){
						scenes.setSceneLevel((byte)3);
						sceneManager.save(scenes);
					}					
				}
			}
		
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_SYNCINVENTORIES +e.toString());
			VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
			visitManager.delete(visit);
			
		}finally{
			ctx.close();
		}
	}
	
	private void syncPersons (List<PersonSyncImpl> listPersonSyncs, Houses house, Visits visit) throws Exception{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		
		try{
			PersonDao personManager = (PersonDao)ctx.getBean("PersonDaoImpl");	
			for (int i= 0; i<listPersonSyncs.size(); i++){
				
				PersonSyncImpl personSyncImpl = listPersonSyncs.get(i);				
				Persons person = new Persons(personSyncImpl.getName(),
									UUID.fromString(personSyncImpl.getUuid()),
									house,
									personSyncImpl.getGenre(),
									new Date(personSyncImpl.getBirthday()),
									personSyncImpl.getBirthdayExact(),      									      
									personSyncImpl.getEnabled(),
									personSyncImpl.getCardId() != null ? personSyncImpl.getCardId(): null);					
										

				personManager.save(person);				
				syncSymptom (personSyncImpl.getListSyncSymptoms(),person, visit);
			}
		
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_SYNCPERSONS +e.toString());
			VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
			visitManager.delete(visit);
		}finally{
			ctx.close();
		}
	}
	
	private void syncSymptom(List<SymptomSyncImpl> listSymptomSync, Persons person, Visits visit) throws Exception {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		
		try{
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");

			SymptomDao symptomManager = (SymptomDao)ctx.getBean("SymptomDaoImpl");			
			for (int i= 0; i<listSymptomSync.size(); i++){
				
				SymptomSyncImpl symptomSyncImpl = listSymptomSync.get(i);		
				
				TableElements symtomName = tableElementsManager.find(symptomSyncImpl.getSymptomId());
				
				Symptoms symptom = new Symptoms(UUID.fromString(symptomSyncImpl.getUuid()), person, symtomName, visit);
				
				symptomManager.save(symptom);
				
			}
		
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_SYNCSYMTOM +e.toString());
			VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
			visitManager.delete(visit);
			
		}finally{
			ctx.close();
		}		
	}
	
	
	private void syncSamples(List<SampleSyncImpl> listSampleSyncImpl, Inventories inventory, Visits visit) throws Exception {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{
			SampleDao sampleMaganer = (SampleDao) ctx.getBean("SampleDaoImpl");
			SamplePhaseDao samplePhaseManager = (SamplePhaseDao)ctx.getBean("SamplePhaseDaoImpl");
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");			
			
			for (int i= 0; i<listSampleSyncImpl.size(); i++){				
				SampleSyncImpl sampleSyncImpl = listSampleSyncImpl.get(i);
				Samples sample = new Samples(UUID.fromString(sampleSyncImpl.getUuid()), visit.getPlans(),inventory.getTableElements(), visit.getHouses(), sampleSyncImpl.getCode(), null) ;
				sample = sampleMaganer.save(sample);	
				
				for(int m = 0; m < sampleSyncImpl.getPhases().size(); m++) {
					Integer phaseId = sampleSyncImpl.getPhases().get(m);						
					SamplePhases samplePhases = new SamplePhases(tableElementsManager.find(phaseId), sample);
					samplePhaseManager.save(samplePhases);
				}
			}
		
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_SYNCSAMPLES +e.toString());
			VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
			visitManager.delete(visit);
			
		}finally{
			ctx.close();
		}		
	}	
	
	
	public String getUbigeoCodeFromSync(int areaId){		
		SyncServiceImpl syncServiceImpl = new SyncServiceImpl(getConnector());		
		return syncServiceImpl.getUbigeoCode(areaId);
	}

	@Override
	public boolean updatecodesHouse() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");
			List<Houses> list = houseManager.findAll();
			for(int i = 0; i<list.size(); i++){
				Houses house = list.get(i);
				String ubigeo = this.getUbigeoCodeFromSync(house.getAreas().getId());
				String code = String.format("%s%04d", ubigeo, house.getNumber());				
				house.setCode(code);
				houseManager.save(house);
			}
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETHOUSE +e.toString());
		    return false;
		}finally{
			ctx.close();
		}
		return true;
	}		
	
	
	public List<HouseSyncImpl> loadHouses(Plans plans){
	    ArrayList<HouseSyncImpl> listHouses = new ArrayList<>();
	    Gson gson = new Gson();
	    JsonParser jsonParser = new JsonParser();
	    try {
	        BufferedReader br = new BufferedReader(new FileReader(plans.getSyncFile()));
	        JsonElement jsonElement = jsonParser.parse(br);
	        return gson.fromJson(jsonElement, new TypeToken<List<HouseSyncImpl>>(){}.getType());

	    } catch (IOException e) {
		    logger.error("Error recuperando la información del fichero. PLAN "+plans.getId()+"" +e.toString());
	        e.printStackTrace();
	    }
	    return listHouses;        
	}

	@Override
	public boolean updateAddresses(String uuid, HouseAreaImpl house) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{
			int updates = 0;
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");
			Houses entityHouse = houseManager.findByUUID(UUID.fromString(uuid));	
			
			if(!house.getStreetName().equals("")) {
	
				updates = houseManager.updateStreetName(entityHouse.getStreetName().trim(), house.getStreetName().trim(), entityHouse.getAreas().getId());
				
			}else if(!house.getStreetNumber().equals("")) {
				
				updates = houseManager.updateStreetNumber(entityHouse.getStreetName(), entityHouse.getStreetNumber(), house.getStreetNumber(), entityHouse.getAreas().getId());				
			}
			
			if(updates > 0) return true;
			else return false;
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETHOUSE +e.toString());
		    return false;
		}finally{
			ctx.close();
		}
	}

}
