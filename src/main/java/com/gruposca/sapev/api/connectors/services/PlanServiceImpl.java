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

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Hashtable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.google.gson.Gson;
import com.gruposca.sapev.api.connectors.dao.impl.AreaDao;
import com.gruposca.sapev.api.connectors.dao.impl.HouseDao;
import com.gruposca.sapev.api.connectors.dao.impl.InspectionDao;
import com.gruposca.sapev.api.connectors.dao.impl.InventoryDao;
import com.gruposca.sapev.api.connectors.dao.impl.InventorySummaryDao;
import com.gruposca.sapev.api.connectors.dao.impl.LabelDao;
import com.gruposca.sapev.api.connectors.dao.impl.PlanDao;
import com.gruposca.sapev.api.connectors.dao.impl.PlansAreasDao;
import com.gruposca.sapev.api.connectors.dao.impl.SampleDao;
import com.gruposca.sapev.api.connectors.dao.impl.SamplePhaseDao;
import com.gruposca.sapev.api.connectors.dao.impl.TableElementsDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.impl.VisitDao;
import com.gruposca.sapev.api.connectors.dao.impl.VisitSummaryDao;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Houses;
import com.gruposca.sapev.api.connectors.dao.model.Inspections;
import com.gruposca.sapev.api.connectors.dao.model.Inventories;
import com.gruposca.sapev.api.connectors.dao.model.InventorySummaries;
import com.gruposca.sapev.api.connectors.dao.model.Plans;
import com.gruposca.sapev.api.connectors.dao.model.PlansAreas;
import com.gruposca.sapev.api.connectors.dao.model.SamplePhases;
import com.gruposca.sapev.api.connectors.dao.model.Samples;
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.dao.model.VisitSummaries;
import com.gruposca.sapev.api.connectors.dao.model.Visits;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.helper.ExcelHelper;
import com.gruposca.sapev.api.helper.SendEmailHelper;
import com.gruposca.sapev.api.modelview.AreasPlanDetail;
import com.gruposca.sapev.api.modelview.HouseAreaImpl;
import com.gruposca.sapev.api.modelview.HouseImpl;
import com.gruposca.sapev.api.modelview.InspectionList;
import com.gruposca.sapev.api.modelview.InspectionListImpl;
import com.gruposca.sapev.api.modelview.InspectorContainerData;
import com.gruposca.sapev.api.modelview.InspectorRecordData;
import com.gruposca.sapev.api.modelview.InspectorReportTotals;
import com.gruposca.sapev.api.modelview.InventoryListSummary;
import com.gruposca.sapev.api.modelview.InventorySync;
import com.gruposca.sapev.api.modelview.ParamFilterPlanVisits;
import com.gruposca.sapev.api.modelview.Plan;
import com.gruposca.sapev.api.modelview.PlanAreas;
import com.gruposca.sapev.api.modelview.PlanDetail;
import com.gruposca.sapev.api.modelview.PlanImpl;
import com.gruposca.sapev.api.modelview.PlanSummaryData;
import com.gruposca.sapev.api.modelview.PlanUserListImpl;
import com.gruposca.sapev.api.modelview.PlanVisit;
import com.gruposca.sapev.api.modelview.PlanVisitImpl;
import com.gruposca.sapev.api.modelview.PlanVisitList;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.SampleSummary;
import com.gruposca.sapev.api.modelview.SampleSyncImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.SyncData;
import com.gruposca.sapev.api.modelview.VisitPlan;
import com.gruposca.sapev.api.modelview.VisitSumaryInsertUpdate;
import com.gruposca.sapev.api.modelview.VisitSummaryInspector;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.PlanService;

public class PlanServiceImpl extends AbsService implements PlanService{

	public PlanServiceImpl(AbsConnector connector) { super( connector); }
	
	@Override
	public PlanVisitList getListPlanVisit(Integer planId, ParamFilterPlanVisits ParamFilterPlanVisits){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<PlanVisitImpl> list = new ArrayList<PlanVisitImpl>();
		PlanVisitList planVisitList = new PlanVisitList();
		try{
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			list = planManager.getListByPlanId(planId, ParamFilterPlanVisits);		
			Integer count = planManager.getCountList(planId, ParamFilterPlanVisits);	
			planVisitList = new PlanVisitList(count, list);
			
		}catch (Exception e){
		   logger.error(RestErrorImpl.METHOD_GET_LIST_PLAN_VISIT + e.toString());
		    planVisitList = null;
			
		}finally{
			ctx.close();
		}
		
		return planVisitList;
	}

	
	@Override
	public Plan getPlan(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Plans entityPlan = new Plans();
		Plan plan;
		
		try{
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			PlansAreasDao planAreaManager = (PlansAreasDao) ctx.getBean("PlansAreasDaoImpl");
			entityPlan = planManager.find(id);	
			List<PlanAreas> listAreasId = planAreaManager.getListPlanAreas(entityPlan);			
		
			plan = new PlanImpl( entityPlan.getPlanSize(),
					             entityPlan.getDate().getTime(), 
					             entityPlan.getUsers().getId(), 
					             entityPlan.getInspections().getId(), 
					             entityPlan.getTableElements().getId(), 
					             listAreasId,
					             entityPlan.getHouseInterval(),
					             entityPlan.getHouseIni());

		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETPLAN + e.toString());
			plan = null;
			
		}finally{
			ctx.close();
		}
		
		return plan;
	}

	@Override
	public boolean deletePlan(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
		//InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");
		boolean result = false;

		try{
			Plans plan = planManager.find(id);	
			planManager.delete(plan);	
			result = true;
			
		}catch (Exception ex){
		    logger.error(RestErrorImpl.METHOD_DELETEPLAN + RestErrorImpl.ERROR_RESTRICCION_FK);
		
		}finally{
			ctx.close();
		}	
		return result;
	}

	@Override
	public Plans createPlan(PlanImpl plan) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Plans entityPlan;
		Integer pin = null;
		try{		
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");	
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			PlansAreasDao planAreasManager = (PlansAreasDao) ctx.getBean("PlansAreasDaoImpl");		
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			TableElements state = tableElementsManager.find(plan.getStateId());
			Inspections inspection = inspectionManager.find(plan.getInspectionId());
			Users user = userManager.find(plan.getUserId());
			entityPlan = new Plans(inspection, state, user, new Date(plan.getDate()) , plan.getPlanSize(), plan.getHouseInterval(), plan.getHouseIni(), null);

			entityPlan = planManager.save(entityPlan);			
			if(entityPlan != null){				
				List<PlanAreas> listAreasId = plan.getAreas();
				for(int i = 0; i< listAreasId.size(); i++){					
					PlanAreas planAreaObject =  listAreasId.get(i);	
					
					if(planAreaObject.isSubstitute()) {						
						Random rand = new Random();
						pin = rand.nextInt(9000) + 1000;
					}
					
					PlansAreas planArea = new PlansAreas(areaManager.find(planAreaObject.getId()), 
														 entityPlan, 
														 planAreaObject.getScheduledHouses(),
														 planAreaObject.isSubstitute(),
														 pin);
					planAreasManager.save(planArea);					
				}
				
				return entityPlan;
				
			}else{
				return null;
			}

		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_CREATEPLAN + e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public Plans updatePlan(Integer id, PlanImpl plan) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");

		try{	
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");	
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			PlansAreasDao planAreasManager = (PlansAreasDao) ctx.getBean("PlansAreasDaoImpl");		
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			TableElements state = tableElementsManager.find(plan.getStateId());
			Inspections inspection = inspectionManager.find(plan.getInspectionId());
			Users user = userManager.find(plan.getUserId());
			Plans updatePlan = planManager.find(id);
			Integer pin = null;
			if(updatePlan != null){
				
				updatePlan.setDate(new Date(plan.getDate()));
				updatePlan.setInspections(inspection);
				updatePlan.setPlanSize(plan.getPlanSize());
				updatePlan.setTableElements(state);
				updatePlan.setUsers(user);		
				updatePlan.setHouseInterval(plan.getHouseInterval());
				updatePlan.setHouseIni(plan.getHouseIni());
				updatePlan = planManager.save(updatePlan);	
				
				if(updatePlan != null){					
					planAreasManager.deleteByPlan(updatePlan);
					List<PlanAreas> listAreasId = plan.getAreas();
					for(int i = 0; i< listAreasId.size(); i++){					
						PlanAreas planAreaObject =  listAreasId.get(i);	

						pin = planAreaObject.getPin();
						if(planAreaObject.isSubstitute() && planAreaObject.getPin() ==  null) {						
							Random rand = new Random();
							pin = rand.nextInt(9000) + 1000;
						}
						
						PlansAreas planArea = new PlansAreas(areaManager.find(planAreaObject.getId()), updatePlan, planAreaObject.getScheduledHouses(),planAreaObject.isSubstitute(), pin);
						planAreasManager.save(planArea);					
					}	
						
					return updatePlan;
				}else{
					return null;
				}				
			}else{
			    logger.error(RestErrorImpl.ERROR_GET_PLAN_ID);
				return null;
			}			
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_UPDATEPLAN + e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public List<VisitPlan> getListVisits(Session session, Integer planId) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<VisitPlan> visitList = new ArrayList<VisitPlan>();	
		
		try{
			VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			visitList = visitManager.getVisitPlanList(user, planId);			
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLISTVISITS + e.toString());
			
		}finally{
			ctx.close();
		}		
		return visitList;		
	}

	@Override
	public boolean importSummaryPlan(Integer planId,  List<PlanSummaryData> listPlanSummaryData) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		boolean result = true;
		try{
			VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
			InventorySummaryDao inventorySummaryManager = (InventorySummaryDao) ctx.getBean("InventorySummaryDaoImpl");
			VisitSummaryDao visitSummaryManager = (VisitSummaryDao) ctx.getBean("VisitSummaryDaoImpl");
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			PlansAreasDao planAreasManager = (PlansAreasDao) ctx.getBean("PlansAreasDaoImpl");
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");
			SampleDao sampleManager = (SampleDao) ctx.getBean("SampleDaoImpl");
			SamplePhaseDao samplePhaseManager = (SamplePhaseDao)ctx.getBean("SamplePhaseDaoImpl");

			Plans plan = planManager.find(planId);

			for(int i = 0; i < listPlanSummaryData.size(); i++) {
				
				PlanSummaryData planSummaryData = listPlanSummaryData.get(i);				
				Areas area = areaManager.find(planSummaryData.getAreaId());					
				Integer inspected = (planSummaryData.getHousesInspected() != null) ? planSummaryData.getHousesInspected() : 0;
				Integer closed = (planSummaryData.getHousesClosed() != null) ? planSummaryData.getHousesClosed() : 0;
				Integer reluctant = (planSummaryData.getHousesReluctant() != null) ? planSummaryData.getHousesReluctant() : 0;
				Integer abandoned = (planSummaryData.getHousesAbandoned() != null) ? planSummaryData.getHousesAbandoned() : 0;
				Integer focus = (planSummaryData.getHousesFocus() != null) ? planSummaryData.getHousesFocus() : 0;
				Integer treated = (planSummaryData.getHousesTreated() != null) ? planSummaryData.getHousesTreated() : 0;
				Integer destroyed = (planSummaryData.getHousesDestroyed() != null) ? planSummaryData.getHousesDestroyed() : 0;
				Integer people = (planSummaryData.getPeople() != null) ? planSummaryData.getPeople() : 0;
				Integer febriles = (planSummaryData.getFebriles() != null) ? planSummaryData.getFebriles() : 0;
				
				BigDecimal larvicide = (planSummaryData.getLarvicide() != null) ? planSummaryData.getLarvicide() : BigDecimal.ZERO;
				Integer houses =  inspected + closed + reluctant + abandoned;
				Integer reconverted = (planSummaryData.getHousesReconverted() != null) ? planSummaryData.getHousesReconverted() : 0;
				
				if(houses > 0) {
					VisitSummaries visitSummaries = new VisitSummaries(houses, focus,  inspected, closed, reluctant, abandoned, treated, destroyed,people, febriles, larvicide, plan, area, reconverted, 0, 0, 0);
					visitSummaryManager.save(visitSummaries);
					PlansAreas planAreas= planAreasManager.getPlanArea(planId, planSummaryData.getAreaId());
					planAreas.setSubstitute(false);
					planAreasManager.save(planAreas);
					//***//
					
					 
					 
					for(int j=0; j < planSummaryData.getListInventories().size(); j++ ){
						InventoryListSummary inventoryListSummary = planSummaryData.getListInventories().get(j);					
						TableElements container = tableElementsManager.find(inventoryListSummary.getContainerId());					
						InventorySummaries inventorySummaries = new InventorySummaries(inventoryListSummary.getInspected(), 
																					   inventoryListSummary.getFocus(), 
																					   inventoryListSummary.getTreated(),
																					   inventoryListSummary.getDestroyed(),
																					   container,
																					   plan,
																					   area);
						inventorySummaryManager.save(inventorySummaries);			
					}
				}			
				
				List<SampleSummary> listSamples = planSummaryData.getListSamples();
				if(listSamples.size() > 0){
					for(int k = 0; k < listSamples.size(); k++){
						SampleSummary sampleSummary = listSamples.get(k);
						TableElements container = tableElementsManager.find(sampleSummary.getContainerId());
						Houses houseSample = houseManager.findByUUID(UUID.fromString(sampleSummary.getHouse().getUuid()));		
						
	                    if(houseSample == null){
	                        String ubigeo = this.getUbigeoCodeFromSync(area.getId());
	                        int number = houseManager.getNumberHouse(ubigeo);    
	                        String code =  String.format("%s%04d", ubigeo, number);                        
	                        Areas areaHouse = areaManager.find(area.getId());                
	                        houseSample = new Houses(UUID.fromString(sampleSummary.getHouse().getUuid()), number, code, null, areaHouse.getLatitude(), areaHouse.getLongitude(), sampleSummary.getHouse().getStreetName(), sampleSummary.getHouse().getStreetNumber(), areaHouse, 0);
	                        houseManager.save(houseSample);
	                    }                  
						Samples samples = new Samples(UUID.fromString(sampleSummary.getSample().getUuid()), plan, container, houseSample, sampleSummary.getSample().getCode());
						samples = sampleManager.save(samples);
						
						for(int m = 0; m < sampleSummary.getSample().getPhases().size(); m++) {
							Integer phaseId = sampleSummary.getSample().getPhases().get(m);						
							SamplePhases samplePhases = new SamplePhases(tableElementsManager.find(phaseId), samples);
							samplePhaseManager.save(samplePhases);
						}
						
					}
				}					
			}
			
			//Actualizamos el estado del plan a Terminado			
			TableElements tableElements = tableElementsManager.find(ConfigurationHelper.getStatePlanFinished());	
			plan.setTableElements(tableElements);
			planManager.save(plan);		
			
		
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_IMPORT_SUMMARY + e.toString());
		    result = false;
			
		}finally{
			ctx.close();
		}	
		return result;	
	}

	@Override
	public PlanDetail getPlanDetail(Integer id, Session session) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		PlanDetail planDetail = new PlanDetail();		
		try{		
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			PlansAreasDao planAreasManager = (PlansAreasDao) ctx.getBean("PlansAreasDaoImpl");		
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");		
			LabelDao labelManager = (LabelDao) ctx.getBean("LabelDaoImpl");	
			Plans plan = planManager.find(id);
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			List<Areas> areaslist = planAreasManager.getListAreas(id);
			List<AreasPlanDetail> list = new ArrayList<AreasPlanDetail>();					
			
			for(int i = 0; i < areaslist.size(); i++ ){
				Areas area = areaslist.get(i);	
				List<Houses> entityHouseList = houseManager.getHousesList(area.getId());				
				List<HouseAreaImpl> listHouses = new ArrayList<HouseAreaImpl>();

				for(int j = 0; j < entityHouseList.size(); j++){					
					Houses entityHouse = entityHouseList.get(j);				
					HouseAreaImpl house = new HouseAreaImpl(entityHouse.getUuid().toString(), entityHouse.getCode(), entityHouse.getStreetName(), entityHouse.getStreetNumber(), entityHouse.getNumber());
					listHouses.add(house);
				}
				
				PlansAreas planAreaEntity = planAreasManager.getPlanArea(id, area.getId());
				
				AreasPlanDetail areaPlanDetail = new AreasPlanDetail(area.getId(), area.getName(), listHouses, planAreaEntity.isSubstitute());
				
				list.add(areaPlanDetail);
			}				
		
			planDetail = new PlanDetail(plan.getInspections().getId(),
										plan.getDate().getTime(),
										plan.getUsers().getName(),
										labelManager.getValueElement(user.getLanguages(), plan.getTableElements()),
										list,
										plan.getInspections().getLarvicides());			
			return planDetail;				

		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GET_PLANDETAIL + e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public List<Plan> getListPlans(Session session) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
		PlansAreasDao planAreaManager = (PlansAreasDao) ctx.getBean("PlansAreasDaoImpl");
		UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
		List<Plan> planList = new ArrayList<Plan>();
		List<String> listAreasName = new ArrayList<String>();
		Plans entityPlan = new Plans();
	
		try{
			Users user = userManager.getUserByToken(session.getAuthorizationToken());			
			List<Plans> entityPlanList = planManager.getListPlansUser(user);

			
			for(int i = 0; i < entityPlanList.size(); i++){
				
				entityPlan = entityPlanList.get(i);				
				listAreasName = planAreaManager.getListAreasName(entityPlan);				
				Plan plan = new PlanUserListImpl(entityPlan.getId(),
												  entityPlan.getPlanSize(),
												  entityPlan.getDate().getTime(),
												  entityPlan.getUsers().getId(),
												  entityPlan.getInspections().getId(),
												  entityPlan.getTableElements().getId(),
												  listAreasName,
												  entityPlan.getInspections().getTableElementsByStateId().getId());
				planList.add(plan);	
			}
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLISTPLANS + e.toString());
			planList = null;
			
		}finally{
			ctx.close();
		}		
		return planList;		
	}
	
	public String getUbigeoCodeFromSync(int areaId){		
		SyncServiceImpl syncServiceImpl = new SyncServiceImpl(getConnector());		
		return syncServiceImpl.getUbigeoCode(areaId);
	}

	public boolean sendEmailExcel(Users user, String excelPathName, String fileName, String day){		
			Boolean sesultReset = false;
			boolean autentificarse = false;
			String to = "";
			String emailFrom = "";
			String emailSender = "";
			String username = "";
			String password = "";

			try {			
				if(user != null){		
					Hashtable<String,String> tabla=new Hashtable<String,String>();
					tabla.put("day" , day);
					SendEmailHelper sendEmailHelper = new SendEmailHelper();
					to = user.getEmail();
					
					emailFrom =ConfigurationHelper.getEmailUserName();				
					emailSender = ConfigurationHelper.getEmailUserName();
					if(ConfigurationHelper.getEmailAutentication()){
						autentificarse=true;
						username =  ConfigurationHelper.getEmailUserName();
						password =  ConfigurationHelper.getEmailUserPassword();
					}				
					sendEmailHelper.setSender(emailSender);
					sendEmailHelper.setFrom(emailFrom);
					sendEmailHelper.setFromAlias("Aedes Alert");
					sendEmailHelper.setTo(to);
					sendEmailHelper.setCc(ConfigurationHelper.getEmailInspections());
					sendEmailHelper.setAutentificarse(autentificarse);
					sendEmailHelper.setUsername(username);
					sendEmailHelper.setPassword(password);
					sendEmailHelper.setHost(ConfigurationHelper.getEmailHost());
					sendEmailHelper.setSsl( ConfigurationHelper.getEmailSsl());
					sendEmailHelper.setPort( ConfigurationHelper.getEmailPort());				
					sendEmailHelper.setAsunto("Resumen Inspección Aedes Alert");			
					sendEmailHelper.setTabla(tabla);
					sendEmailHelper.setAtach(excelPathName);	
					sendEmailHelper.setAtach_name(fileName);		
					sendEmailHelper.setPath(ConfigurationHelper.getEmailTemplateInspectionExcel());	
					if(sendEmailHelper.sendWithTemplate() == 1){
						sesultReset = true;				
					}				
				}		
				
			} catch (Exception e) {
				logger.error(RestErrorImpl.ERROR_SEND_EMAIL_TO_INSPECTOR + e.getMessage());
				
			}
			
			return sesultReset;
		}
	 
	 @Override
	public boolean sendInspectorReport(Integer planId) {
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
	        String excelPathName = "";
	        String fileName = "";
	        String datePlan = "";
	        try
	        {
	            PlanDao planManager = (PlanDao)ctx.getBean("PlanDaoImpl");	            
	            Plans plan = planManager.find(planId);
	            Users user = plan.getUsers();
	            fileName = user.getName().replace(" ", "")+"_"+ new Date().getTime()+".xlsx";       
                datePlan = (new SimpleDateFormat("dd/MM/yyyy")).format(plan.getDate());
            	excelPathName = this.getInspectorReport(planId);          	
                sendEmailExcel(user, excelPathName, fileName, datePlan);
            }
            catch(Exception e)
            {
                e.printStackTrace();
			    logger.error(RestErrorImpl.METHOD_SENDSPECTOREXCEL +e.toString());
                return false;
                        
			}finally{
				ctx.close();
			}	 
	        
	        return true;
		}

	@Override/*PEPITO*/
	public String getInspectorReport(Integer planId) {		
		
		BigDecimal inspected = BigDecimal.ZERO, closed = BigDecimal.ZERO, reluctant = BigDecimal.ZERO, abandoned = BigDecimal.ZERO , treated = BigDecimal.ZERO, focus = BigDecimal.ZERO;
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
        String excelPathName = "";
        String fileName = "";
		BigDecimal coberturaInspection = BigDecimal.ZERO;

        try
        {
        	
            PlanDao planManager = (PlanDao)ctx.getBean("PlanDaoImpl");	            
            AreaDao areaManager = (AreaDao)ctx.getBean("AreaDaoImpl");
            VisitDao visitManager = (VisitDao)ctx.getBean("VisitDaoImpl");	   
            SampleDao sampleManager = (SampleDao)ctx.getBean("SampleDaoImpl");	
            SamplePhaseDao samplePhaseManager = (SamplePhaseDao)ctx.getBean("SamplePhaseDaoImpl");	
            VisitSummaryDao visitSummaryManager = (VisitSummaryDao)ctx.getBean("VisitSummaryDaoImpl");
            Plans plan = planManager.find(planId);
            Users user = plan.getUsers();
            String date = "";
            String EESS = plan.getInspections().getAreas().getName();	            
            String MicroRed = plan.getInspections().getAreas().getAreas().getName();	           
            fileName = user.getName().replace(" ", "")+"_"+ new Date().getTime()+".xlsx"; 
            String sector = areaManager.getSectorNames(planId);
			Integer typeInspection = plan.getInspections().getTableElementsByTypeId().getId().intValue();
			boolean reconversion = plan.getInspections().getSchedules().getReconversionScheduleId() != null ;
			VisitSummaryInspector visitSummaryInspector = visitSummaryManager.getSummary(planId);	            
			InventoryDao inventoryManager = (InventoryDao)ctx.getBean("InventoryDaoImpl");
			InventorySummaryDao inventorySummaryManager = (InventorySummaryDao)ctx.getBean("InventorySummaryDaoImpl");
			
			XSSFWorkbook libro = new XSSFWorkbook();		
			XSSFDataFormat df = libro.createDataFormat();
			
			if(visitSummaryInspector != null) {
				FormulaEvaluator evaluator = libro.getCreationHelper().createFormulaEvaluator();
				
				//FUENTE NEGRITA 25
				XSSFFont fontTitle=libro.createFont();
				fontTitle.setBold(true);
				fontTitle.setFontName("Calibri");
				fontTitle.setFontHeightInPoints((short) 25);	

				//FUENTE NEGRITA 12
				XSSFFont fontBoldMedium=libro.createFont();
				fontBoldMedium.setBold(true);
				fontBoldMedium.setFontName("Calibri");
				fontBoldMedium.setFontHeightInPoints((short) 12);
				
				//FUENTE NEGRITA PEQUEÑA
				XSSFFont fontBoldSmall=libro.createFont();
				fontBoldSmall.setBold(true);
				fontBoldSmall.setFontName("Calibri");
				fontBoldSmall.setFontHeightInPoints((short) 10);
				
				//FUENTE NORMAL
				XSSFFont fontNormal = libro.createFont();
				fontNormal.setFontName("Calibri");
				fontNormal.setFontHeightInPoints((short) 11);	
			  
					
				/*STYLE VERTICAL*/
			  	XSSFCellStyle styleBoldMediumVertical = libro.createCellStyle();
			  	styleBoldMediumVertical.setRotation((short)90);
			  	styleBoldMediumVertical.setFont(fontBoldMedium);
			  	styleBoldMediumVertical.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			  	styleBoldMediumVertical.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);	
			  	styleBoldMediumVertical.setWrapText(true);

			  	/*STYLE HORIZONTAL FONTTITLE*/			
			  	XSSFCellStyle styleTitle = libro.createCellStyle();
			  	styleTitle.setFont(fontTitle);	
			  	styleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			  	styleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);	
			  	styleTitle.setWrapText(true);

				/*STYLE HORIZONTAL*/			
			  	XSSFCellStyle styleBoldMedium = libro.createCellStyle();
			  	styleBoldMedium.setFont(fontBoldMedium);	
			  	styleBoldMedium.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			  	styleBoldMedium.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);	
			  	styleBoldMedium.setWrapText(true);
			  	
			  	
			  	/*STYLE HORIZONTAL LEFT*/			
			  	XSSFCellStyle styleBoldMediumLeft = libro.createCellStyle();
			  	styleBoldMediumLeft.setFont(fontBoldMedium);	
			  	styleBoldMediumLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);
			  	styleBoldMediumLeft.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);	
			  	styleBoldMediumLeft.setWrapText(true);
			  	
			  	/*STYLE NORMAL IZQUIERDA BORDER*/
			  	XSSFCellStyle styleNormalLeftBorder = libro.createCellStyle();	
			  	styleNormalLeftBorder.setFont(fontNormal);	
			  	styleNormalLeftBorder.setBorderBottom(CellStyle.BORDER_MEDIUM);
			  	styleNormalLeftBorder.setBorderLeft(CellStyle.BORDER_MEDIUM);
			  	styleNormalLeftBorder.setBorderRight(CellStyle.BORDER_MEDIUM);
			  	styleNormalLeftBorder.setBorderTop(CellStyle.BORDER_MEDIUM);
			  	
				/*STYLE NORMAL CENTER BORDER*/
			  	XSSFCellStyle styleNormalCenter = libro.createCellStyle();	
			  	styleNormalCenter.setFont(fontNormal);
			  	styleNormalCenter.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			  	styleNormalCenter.setBorderBottom(CellStyle.BORDER_MEDIUM);
			  	styleNormalCenter.setBorderLeft(CellStyle.BORDER_MEDIUM);
			  	styleNormalCenter.setBorderRight(CellStyle.BORDER_MEDIUM);
			  	styleNormalCenter.setBorderTop(CellStyle.BORDER_MEDIUM);
			  	
			  	/*STYLE NORMAL DECIMAL BORDER*/
			  	XSSFCellStyle styleNormalDecimal = libro.createCellStyle();	
			  	styleNormalDecimal.setFont(fontNormal);
			  	styleNormalDecimal.setDataFormat(df.getFormat("#,##0.00"));   
			  	styleNormalDecimal.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			  	styleNormalDecimal.setBorderBottom(CellStyle.BORDER_MEDIUM);
			  	styleNormalDecimal.setBorderLeft(CellStyle.BORDER_MEDIUM);
			  	styleNormalDecimal.setBorderRight(CellStyle.BORDER_MEDIUM);
			  	styleNormalDecimal.setBorderTop(CellStyle.BORDER_MEDIUM);
			  	
			  	/*STYLE NORMAL IZQUIERDA*/
			  	XSSFCellStyle styleNormalLeft = libro.createCellStyle();	
			  	styleNormalLeft.setFont(fontNormal);
			  	styleNormalLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);
			  	styleNormalLeft.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);	

				/*STYLE NORMAL DECIMAL IZQUIERDA*/
			  	XSSFCellStyle styleNormalDecimalLeft = libro.createCellStyle();	
			  	styleNormalDecimalLeft.setFont(fontNormal);
			  	styleNormalDecimalLeft.setDataFormat(df.getFormat("#,##0.00"));   
			  	styleNormalDecimalLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);
			  	 	
			  	/*STYLE NEGRITA BORDER BOTTOM*/
			  	XSSFCellStyle styleBoldBorderBottom = libro.createCellStyle();	
			  	styleBoldBorderBottom.setFont(fontBoldSmall);
			  	styleBoldBorderBottom.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			  	styleBoldBorderBottom.setBorderBottom(CellStyle.BORDER_MEDIUM);
			  	
			  	/*STYLE NEGRITA BORDER BOTTOM*/
			  	XSSFCellStyle styleBoldSmall = libro.createCellStyle();	
			  	styleBoldSmall.setFont(fontBoldSmall);
			  	styleBoldSmall.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			  	styleBoldSmall.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);	

			  	
			  	XSSFSheet sheet = libro.createSheet("Consolidado");
				XSSFRow rowTitle = sheet.createRow(1);
				XSSFRow rowCellsBig = sheet.createRow(8);
				XSSFRow rowNumRecipientes = sheet.createRow(10);
				XSSFRow rowHouses = sheet.createRow(11);
				XSSFRow rowNumInfected = sheet.createRow(13);
				
				Integer week = planManager.getWeek(planId);
				date= new SimpleDateFormat("dd/MM/yyyy").format(plan.getDate());				

				//TITULO DEL EXCEL
			  	ExcelHelper.createCell(1, 2, 1, 33, 1, "REGISTRO DE INSPECCIÓN DE VIGILANCIA Y CONTROL DE AEDES AEGYPTI", sheet, rowTitle, styleTitle, 0);
			  	
			  	//DIRECCION			
				CellRangeAddress cellRangeDir = ExcelHelper.createCell(8, 13, 0, 1, 0, "DIRECCIÓN", sheet, rowCellsBig, styleBoldMedium, 6000);

				//DISTRITO Y SECTOR
				CellRangeAddress cellRangeNumHouse = ExcelHelper.createCell(8, 13, 2, 2, 2, "Nº CASA", sheet, rowCellsBig, styleBoldMedium, 3000);
				
				//SECTOR
				CellRangeAddress cellRangeSector = ExcelHelper.createCell(8, 13, 3, 3, 3, "SECTOR", sheet, rowCellsBig, styleBoldMedium, 5000);
				
				//MZ
				CellRangeAddress cellRangeMz = ExcelHelper.createCell(8, 13, 4, 4, 4, "MANZANA", sheet, rowCellsBig, styleBoldMedium, 5000);
				
				//RESID. PRTEG
				CellRangeAddress cellRangeResidentes = ExcelHelper.createCell(8, 13, 5, 5, 5, "Nº RESIDENTES", sheet, rowCellsBig, styleBoldMedium, 4000);
				
				//Tipo
				CellRangeAddress cellRangeTipo = ExcelHelper.createCell(8, 13, 6, 6, 6, "TIPO", sheet, rowCellsBig, styleBoldMedium, 3000);
				
				//TIPOS RECIPIENTES
				CellRangeAddress cellRangeTypesRecipients = ExcelHelper.createCell(8, 9, 7, 36, 7, "TIPOS DE RECIPIENTES INFESTADOS POR Aedes aegypti", sheet, rowCellsBig, styleBoldMedium, 0);
				
				//TIPOS RECIPIENTES_NUMBERS
				CellRangeAddress cellRangeTypesRecipients1   = ExcelHelper.createCell(10, 10, 7, 9, 7, "1", sheet, rowNumRecipientes, styleBoldMedium, 0);
				CellRangeAddress cellRangeTypesRecipients2   = ExcelHelper.createCell(10, 10, 10, 12, 10, "2", sheet, rowNumRecipientes, styleBoldMedium, 0);
				CellRangeAddress cellRangeTypesRecipients3   = ExcelHelper.createCell(10, 10, 13, 15, 13, "3", sheet, rowNumRecipientes, styleBoldMedium, 0);
				CellRangeAddress cellRangeTypesRecipients4   = ExcelHelper.createCell(10, 10, 16, 18, 16, "4", sheet, rowNumRecipientes, styleBoldMedium, 0);
				CellRangeAddress cellRangeTypesRecipients5   = ExcelHelper.createCell(10, 10, 19, 21, 19, "5", sheet, rowNumRecipientes, styleBoldMedium, 0);
				CellRangeAddress cellRangeTypesRecipients6   = ExcelHelper.createCell(10, 10, 22, 24, 22, "6", sheet, rowNumRecipientes, styleBoldMedium, 0);
				CellRangeAddress cellRangeTypesRecipients7   = ExcelHelper.createCell(10, 10, 25, 27, 25, "7", sheet, rowNumRecipientes, styleBoldMedium, 0);
				CellRangeAddress cellRangeTypesRecipients8   = ExcelHelper.createCell(10, 10, 28, 30, 28, "8", sheet, rowNumRecipientes, styleBoldMedium, 0);
				CellRangeAddress cellRangeTypesRecipients9   = ExcelHelper.createCell(10, 10, 31, 33, 31, "9", sheet, rowNumRecipientes, styleBoldMedium, 0);
				CellRangeAddress cellRangeTypesRecipients10  = ExcelHelper.createCell(10, 10, 34, 36, 34, "10", sheet, rowNumRecipientes, styleBoldMedium, 0);

				//TIPOS RECIPIENTES_NAMES
				CellRangeAddress cellRangeTanqueElevado = ExcelHelper.createCell(11, 12, 7, 9, 7, "Tanque elevado", sheet, rowHouses, styleBoldMedium, 0);
				CellRangeAddress cellRangeTanqueBajo    = ExcelHelper.createCell(11, 12, 10, 12, 10, "Tanque Bajo", sheet, rowHouses, styleBoldMedium, 0);
				CellRangeAddress cellRangeCanaletas     = ExcelHelper.createCell(11, 12, 13, 15, 13, "Canaletas", sheet, rowHouses, styleBoldMedium, 0);
				CellRangeAddress cellRangeBarriles      = ExcelHelper.createCell(11, 12, 16, 18, 16, "Barriles, Toneles", sheet, rowHouses, styleBoldMedium, 0);
				CellRangeAddress cellRangeCantaro       = ExcelHelper.createCell(11, 12, 19, 21, 19, "Cántaro de Barro", sheet, rowHouses, styleBoldMedium, 0);
				CellRangeAddress cellRangeFloreros      = ExcelHelper.createCell(11, 12, 22, 24, 22, "Llantas", sheet, rowHouses, styleBoldMedium, 0);
				CellRangeAddress cellRangeLlantas       = ExcelHelper.createCell(11, 12, 25, 27, 25, "Floreros", sheet, rowHouses, styleBoldMedium, 0);
				CellRangeAddress cellRangeTinajas       = ExcelHelper.createCell(11, 12, 28, 30, 28, "Tinas, Baldes, Bateas", sheet, rowHouses, styleBoldMedium, 0);
				CellRangeAddress cellRangeOllas         = ExcelHelper.createCell(11, 12, 31, 33, 31, "Ollas", sheet, rowHouses, styleBoldMedium, 0);
				CellRangeAddress cellRangeOtros         = ExcelHelper.createCell(11, 12, 34, 36, 34, "Otros", sheet, rowHouses, styleBoldMedium, 0);
				
				
				//TIPOS RECIPIENTES_NUMBER_INFECTED
				CellRangeAddress cellRangeRecipient_1I  = ExcelHelper.createCell(13, 13, 7, 7, 7, "I", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_1F  = ExcelHelper.createCell(13, 13, 8, 8, 8, "F", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_1TD = ExcelHelper.createCell(13, 13, 9, 9, 9, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250);
				
				CellRangeAddress cellRangeRecipient_2I  = ExcelHelper.createCell(13, 13, 10, 10, 10, "I", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_2F  = ExcelHelper.createCell(13, 13, 11, 11, 11, "F", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_2TD = ExcelHelper.createCell(13, 13, 12, 12, 12, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250);
				
				CellRangeAddress cellRangeRecipient_3I  = ExcelHelper.createCell(13, 13, 13, 13, 13, "I", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_3F  = ExcelHelper.createCell(13, 13, 14, 14, 14, "F", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_3TD = ExcelHelper.createCell(13, 13, 15, 15, 15, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250);
			//**+1
				CellRangeAddress cellRangeRecipient_4I  = ExcelHelper.createCell(13, 13, 16, 16, 16, "I", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_4F  = ExcelHelper.createCell(13, 13, 17, 17, 17, "F", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_4TD = ExcelHelper.createCell(13, 13, 18, 18, 18, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250);

				CellRangeAddress cellRangeRecipient_5I  = ExcelHelper.createCell(13, 13, 19, 19, 19, "I", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_5F  = ExcelHelper.createCell(13, 13, 20, 20, 20, "F", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_5TD = ExcelHelper.createCell(13, 13, 21, 21, 21, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250);
				
				CellRangeAddress cellRangeRecipient_6I  = ExcelHelper.createCell(13, 13, 22, 22, 22, "I", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_6F  = ExcelHelper.createCell(13, 13, 23, 23, 23, "F", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_6TD = ExcelHelper.createCell(13, 13, 24, 24, 24, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250);
				
				CellRangeAddress cellRangeRecipient_7I  = ExcelHelper.createCell(13, 13, 25, 25, 25, "I", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_7F  = ExcelHelper.createCell(13, 13, 26, 26, 26, "F", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_7TD = ExcelHelper.createCell(13, 13, 27, 27, 27, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250);
				
				CellRangeAddress cellRangeRecipient_8I  = ExcelHelper.createCell(13, 13, 28, 28, 28, "I", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_8F  = ExcelHelper.createCell(13, 13, 29, 29, 29, "F", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_8TD = ExcelHelper.createCell(13, 13, 30, 30, 30, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250);
				
				CellRangeAddress cellRangeRecipient_9I  = ExcelHelper.createCell(13, 13, 31, 31, 31, "I", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_9F  = ExcelHelper.createCell(13, 13, 32, 32, 32, "F", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_9TD = ExcelHelper.createCell(13, 13, 33, 33, 33, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250);
				
				CellRangeAddress cellRangeRecipient_10I  = ExcelHelper.createCell(13, 13, 34, 34, 34, "I", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_10F  = ExcelHelper.createCell(13, 13, 35, 35, 35, "F", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeRecipient_10TD = ExcelHelper.createCell(13, 13, 36, 36, 36, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250);
				
				CellRangeAddress cellRangeTotal = ExcelHelper.createCell(8, 12, 37, 39, 37, "TOTAL", sheet, rowCellsBig, styleBoldMedium, 4000);
				CellRangeAddress cellRangeTotal_I  = ExcelHelper.createCell(13, 13, 37, 37, 37, "I", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeTotal_F  = ExcelHelper.createCell(13, 13, 38, 38, 38, "F", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeTotal_D = ExcelHelper.createCell(13, 13, 39, 39, 39, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250);
				CellRangeAddress cellRangeFocus = ExcelHelper.createCell(8, 9, 40, 44, 40, "FOCOS Aedes aegypti", sheet, rowCellsBig, styleBoldMedium, 0);
			
				CellRangeAddress cellRangeFocus1 = ExcelHelper.createCell(10, 13,40, 40, 40, "HUEVOS", sheet, rowNumRecipientes, styleBoldMediumVertical, 1400);
				CellRangeAddress cellRangeFocus2 = ExcelHelper.createCell(10, 13, 41, 41, 41, "LARVAS", sheet, rowNumRecipientes, styleBoldMediumVertical, 1400);
				CellRangeAddress cellRangeFocus3 = ExcelHelper.createCell(10, 13, 42, 42, 42, "PUPAS", sheet, rowNumRecipientes, styleBoldMediumVertical, 1400);
				CellRangeAddress cellRangeFocus4 = ExcelHelper.createCell(10, 13, 43, 43, 43, "ADULTO", sheet, rowNumRecipientes, styleBoldMediumVertical, 1400);
				CellRangeAddress cellRangeFocus5 = ExcelHelper.createCell(10, 13, 44, 44, 44, "TOTAL", sheet, rowNumRecipientes, styleBoldMediumVertical, 1400);
				
				
				CellRangeAddress cellRangeLarvicide = ExcelHelper.createCell(8, 13, 45, 45, 45, "CONSUMO DE LARVICIDAS", sheet, rowCellsBig, styleBoldMediumVertical, 2000);
				CellRangeAddress cellRangeFebriles = ExcelHelper.createCell(8, 13, 46, 46, 46, "Nº FEBRILES", sheet, rowCellsBig, styleBoldMediumVertical, 2000);

				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeFocus1, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeFocus2, sheet, libro);	
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeDir, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeSector, sheet, libro);	
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeMz, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeResidentes, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTipo, sheet, libro);	
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeNumHouse, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTotal_I, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTotal_F, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTotal_D, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTotal, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTypesRecipients10, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTypesRecipients9, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTypesRecipients8, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTypesRecipients7, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTypesRecipients6, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTypesRecipients5, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTypesRecipients4, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTypesRecipients3, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTypesRecipients2, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTypesRecipients1, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTypesRecipients, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTanqueElevado, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTanqueBajo, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeCanaletas, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeBarriles, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeCantaro, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeFloreros, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeLlantas, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTinajas, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeOllas, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeOtros, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_1I, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_1F, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_1TD, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_2I, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_2F, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_2TD, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_3I, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_3F, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_3TD, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_4I, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_4F, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_4TD, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_5I, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_5F, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_5TD, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_6I, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_6F, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_6TD, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_7I, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_7F, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_7TD, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_8I, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_8F, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_8TD, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_9I, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_9F, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_9TD, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_10I, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_10F, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeRecipient_10TD, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeFocus, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeFocus3, sheet, libro);	
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeFocus4, sheet, libro);	
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeFocus5, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeLarvicide, sheet, libro);	
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeFebriles, sheet, libro);	

				XSSFRow row;
				int numberRow = 14;
				
				InspectorReportTotals inspectorReportTotals = new InspectorReportTotals();
				List<InspectorRecordData> list = visitManager.geListInspectorRecodData(planId);
				if(list != null && list.size() > 0) {
					
					inspectorReportTotals =  infoFromVisits(list, planId, sheet, numberRow, styleNormalCenter, styleNormalLeftBorder, inventoryManager, sampleManager, samplePhaseManager);
				}else {
					List<VisitSummaries> listSummary = visitSummaryManager.getList(planId);					
					inspectorReportTotals =  infoFromSummaries(listSummary, planId, sheet, numberRow, styleNormalCenter, styleNormalLeftBorder, inventorySummaryManager, sampleManager, samplePhaseManager);
				}		
		
				numberRow = inspectorReportTotals.getNumberRow();
				//LINEA CON LOS TOTALES
				row = sheet.createRow(numberRow);
				ExcelHelper.createCellString(row, (short)0, "", styleNormalCenter);
				ExcelHelper.createCellString(row, (short)1, "", styleNormalCenter);
				ExcelHelper.createCellString(row, (short)2, "", styleNormalCenter);
				ExcelHelper.createCellString(row, (short)3, "", styleNormalCenter);
				ExcelHelper.createCellString(row, (short)4, "TOTALES", styleNormalCenter);
				
				ExcelHelper.createCellFormulaEvaluate(row, (short)5, "SUM(F15:F"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellString(row, (short)6, "", styleNormalCenter);
				ExcelHelper.createCellFormulaEvaluate(row, (short)7, "SUM(H15:H"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)8, "SUM(I15:I"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)9, "SUM(J15:J"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)10, "SUM(K15:K"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)11, "SUM(L15:L"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)12, "SUM(M15:M"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)13, "SUM(N15:N"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)14, "SUM(O15:O"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)15, "SUM(P15:P"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)16, "SUM(Q15:Q"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);			
				ExcelHelper.createCellFormulaEvaluate(row, (short)17, "SUM(R15:R"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)18, "SUM(S15:S"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)19, "SUM(T15:T"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)20, "SUM(U15:U"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)21, "SUM(V15:V"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)22, "SUM(W15:W"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)23, "SUM(X15:X"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)24, "SUM(Y15:Y"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)25, "SUM(Z15:Z"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)26, "SUM(AA15:AA"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)27, "SUM(AB15:AB"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)28, "SUM(AC15:AC"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);			
				ExcelHelper.createCellFormulaEvaluate(row, (short)29, "SUM(AD15:AD"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)30, "SUM(AE15:AE"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)31, "SUM(AF15:AF"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)32, "SUM(AG15:AG"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)33, "SUM(AH15:AH"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)34, "SUM(AI15:AI"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)35, "SUM(AJ15:AJ"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)36, "SUM(AK15:AK"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)37, "SUM(AL15:AL"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)38, "SUM(AM15:AM"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)39, "SUM(AN15:AN"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)40, "SUM(AO15:AO"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)41, "SUM(AP15:AP"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)42, "SUM(AQ15:AQ"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)43, "SUM(AR15:AR"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)44, "SUM(AS15:AS"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)45, "SUM(AT15:AT"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				ExcelHelper.createCellFormulaEvaluate(row, (short)46, "SUM(AU15:AU"+(15+(list.size()-1))+")" , styleNormalCenter, evaluator);
				
				int rowTotales = numberRow;
				numberRow++;	

		        inspected = visitSummaryInspector.getInspected() != null ? visitSummaryInspector.getInspected() : BigDecimal.ZERO;
		        closed = visitSummaryInspector.getClosed() != null ? visitSummaryInspector.getClosed() : BigDecimal.ZERO;
		        reluctant = visitSummaryInspector.getRenuente() != null ? visitSummaryInspector.getRenuente() : BigDecimal.ZERO;	
		        abandoned = visitSummaryInspector.getAbandonada() != null ? visitSummaryInspector.getAbandonada() : BigDecimal.ZERO;	
		        treated = visitSummaryInspector.getTreated() != null ? visitSummaryInspector.getTreated() : BigDecimal.ZERO;	
		        focus = visitSummaryInspector.getFocus() != null ? visitSummaryInspector.getFocus() : BigDecimal.ZERO;	
		        
		        row = sheet.getRow(rowTotales);
		        XSSFCell cell = row.getCell(42);		 
		        Double totalLarvicide = cell.getNumericCellValue();				
				numberRow = numberRow+2;
				row = sheet.createRow(numberRow);
				ExcelHelper.createCellString(row, (short)1, "1. CASAS CON FOCO", styleNormalLeft);
				ExcelHelper.createCell(numberRow, numberRow, 3, 4, 3, focus.toString(), sheet, row, styleNormalLeft, 0);
			  	ExcelHelper.createCell(numberRow, numberRow, 7, 12, 7, "1. DEPÓSITOS FOCO", sheet, row, styleNormalLeft, 0);
			  	ExcelHelper.createCell(numberRow, numberRow, 13, 14, 13, inspectorReportTotals.getTotalFocus().toString(), sheet, row, styleNormalLeft, 0);
			  	ExcelHelper.createCell(numberRow, numberRow, 16, 20, 16, "I = INSPECCIONADO", sheet, row, styleNormalLeft, 0);
				numberRow++;
				row = sheet.createRow(numberRow);
				ExcelHelper.createCellString(row, (short)1, "2. CASAS INSPECCIONADAS", styleNormalLeft);
				ExcelHelper.createCell(numberRow, numberRow, 3, 4, 3, inspected.toString(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(numberRow, numberRow, 7, 12, 7, "2. DEPÓSITOS INSPECCIONADOS", sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(numberRow, numberRow, 13, 14, 13, inspectorReportTotals.getTotalInspected().toString(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(numberRow, numberRow, 16, 20, 16, "F = RECIPIENTE FOCO", sheet, row, styleNormalLeft, 0);

				numberRow++;
				row = sheet.createRow(numberRow);
				ExcelHelper.createCellString(row, (short)1, "3. CASAS CERRADAS", styleNormalLeft);
				ExcelHelper.createCell(numberRow, numberRow, 3, 4, 3, closed.toString(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(numberRow, numberRow, 7, 12, 7, "1. DEPÓSITOS TRATADOS", sheet, row, styleNormalLeft, 0);
			  	ExcelHelper.createCell(numberRow, numberRow, 13, 14, 13, inspectorReportTotals.getTotalTreated().toString(), sheet, row, styleNormalLeft, 0);
			  	ExcelHelper.createCell(numberRow, numberRow, 16, 20, 16, "T = TRATADO(Abatizado)", sheet, row, styleNormalLeft, 0);
				numberRow++;
				row = sheet.createRow(numberRow);
				ExcelHelper.createCellString(row, (short)1, "4. CASAS RENUENTES", styleNormalLeft);
				ExcelHelper.createCell(numberRow, numberRow, 3, 4, 3, reluctant.toString(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(numberRow, numberRow, 7, 12, 7, "1. DEPÓSITOS DESTRUIDOS", sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(numberRow, numberRow, 13, 14, 13, inspectorReportTotals.getTotalDestroyed().toString(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(numberRow, numberRow, 16, 20, 16, "D = DESTRUIDO", sheet, row, styleNormalLeft, 0);

				numberRow++;
				row = sheet.createRow(numberRow);
				ExcelHelper.createCellString(row, (short)1, "5. CASAS TRATADAS", styleNormalLeft);
				ExcelHelper.createCell(numberRow, numberRow, 3, 4, 3, treated.toString(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(numberRow, numberRow+1, 16, 20, 16, "COBERTURA DE INSPECCION", sheet, row, styleNormalLeft, 0);
			  	CellRangeAddress cellRangeNumHouses = ExcelHelper.createCell(numberRow, numberRow, 21, 26, 21, "Número de Viviendas Inspeccionadas", sheet, row, styleBoldBorderBottom, 0);
			  	ExcelHelper.createCell(numberRow, numberRow+1, 27, 27, 27, "X 100", sheet, row, styleBoldSmall, 0);
			  	RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, cellRangeNumHouses, sheet, libro);
				numberRow++;
				row = sheet.createRow(numberRow);
				ExcelHelper.createCellString(row, (short)1, "6. CASAS ABANDONADAS", styleNormalLeft);
				ExcelHelper.createCell(numberRow, numberRow, 3, 4, 3, abandoned.toString(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(numberRow, numberRow, 21, 26, 21, "Número de Viviendas Programadas", sheet, row, styleBoldSmall, 0);
				ExcelHelper.createCell(numberRow, numberRow, 29, 31, 29, "INSPECTOR", sheet, row, styleBoldMedium, 0);
			  	CellRangeAddress cellRangeEmpty = ExcelHelper.createCell(numberRow, numberRow, 32, 40, 32, user.getName(), sheet, row, styleBoldBorderBottom, 0);
				RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, cellRangeEmpty, sheet, libro);
				
				numberRow++;
				
				BigDecimal totalPrograming = new BigDecimal(planManager.getHousesArea(planId));

				row = sheet.createRow(numberRow);
				ExcelHelper.createCellString(row, (short)1, "7. CASAS PROGRAMADAS", styleNormalLeft);
				ExcelHelper.createCell(numberRow, numberRow, 3, 4, 3, totalPrograming.toString(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(numberRow, numberRow, 16, 29, 16, "CONSUMO LARVICIDA ("+(plan.getInspections().getLarvicides().getName()).toUpperCase()+"):"+totalLarvicide, sheet, row, styleNormalLeft, 0);
				  	
			  	//DATOS CABECERA
			  	if(totalPrograming.compareTo(BigDecimal.ZERO) != 0) {
			  		coberturaInspection =  new BigDecimal(inspectorReportTotals.getTotalInspected()).multiply(new BigDecimal(100)).divide(totalPrograming, 2, RoundingMode.HALF_UP);
			  	}
				String cobertura = coberturaInspection.toString();
			
				row = sheet.createRow(4);
				ExcelHelper.createCell(4, 4, 2, 2, 2, "PROVINCIA:", sheet, row, styleBoldMediumLeft, 0);
				ExcelHelper.createCell(4, 4, 3, 5, 3, "Trujillo", sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(4, 4, 9, 10, 9, "DISTRITO:", sheet, row, styleBoldMediumLeft, 0);
				ExcelHelper.createCell(4, 4, 11, 13, 11, MicroRed, sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(4, 4, 18, 20, 18, "EST. DE SALUD:", sheet, row, styleBoldMediumLeft, 0);
				ExcelHelper.createCell(4, 4, 21, 28, 21, EESS, sheet, row, styleNormalLeft, 0);			

				
				ExcelHelper.createCell(4, 4, 30, 31, 30, "SECTOR:", sheet, row, styleBoldMediumLeft, 0);
				ExcelHelper.createCell(4, 4, 32, 38, 32, sector, sheet, row, styleNormalLeft, 0);

			
				if(typeInspection == 1001){
					ExcelHelper.createCell(4, 4, 40, 43, 40, "(X) VIGILANCIA", sheet, row, styleBoldMediumLeft, 0);
				}else{
					ExcelHelper.createCell(4, 4, 40, 43, 40, "(  ) VIGILANCIA", sheet, row, styleBoldMediumLeft, 0);
				}
				
				row = sheet.createRow(5);
				
				if(typeInspection == 1002){
					ExcelHelper.createCell(5, 5, 40, 43, 40, "(X) CONTROL", sheet, row, styleBoldMediumLeft, 0);
				}else{
					ExcelHelper.createCell(5, 5,  40, 43, 40, "(  ) CONTROL", sheet, row, styleBoldMediumLeft, 0);
				}	
				
				row = sheet.createRow(6);
				ExcelHelper.createCell(6, 6, 2, 2, 2, "FECHA:", sheet, row, styleBoldMediumLeft, 0);
				ExcelHelper.createCell(6, 6, 3, 4, 3, date, sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(6, 6, 9, 13, 9, "SEMANA EPIDEMIOLÓGICA:", sheet, row, styleBoldMediumLeft, 0);
				ExcelHelper.createCell(6, 6, 14, 14, 14, week.toString(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(6, 6, 17, 23, 17, "COBERTURA DE INSPECCIÓN:", sheet, row, styleBoldMediumLeft, 0);
				ExcelHelper.createCell(6, 6, 24, 26, 24, cobertura+"%", sheet, row, styleNormalLeft, 0);
				
				
				if (reconversion) {				
					ExcelHelper.createCell(6, 6, 40, 43, 40, "(X) RECONVERSIÓN", sheet, row, styleBoldMediumLeft, 0);

				}else {
					ExcelHelper.createCell(6, 6, 40, 43, 40, "(  ) RECONVERSIÓN", sheet, row, styleBoldMediumLeft, 0);

				}				
			}
			excelPathName = (new StringBuilder(String.valueOf(ConfigurationHelper.getInspectorExcelPath()))).append("/").append(fileName).toString();
            FileOutputStream elFichero = new FileOutputStream((new StringBuilder(String.valueOf(ConfigurationHelper.getInspectorExcelPath()))).append("/").append(fileName).toString());

            try
            {
                libro.write(elFichero);
                elFichero.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
			    logger.error(RestErrorImpl.METHOD_CREATEREPORTINSPECTOR +e.toString());
            }     
            
        }catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_CREATEREPORTINSPECTOR +e.toString());
		}finally{
			ctx.close();
		}  
        
        return excelPathName;	        
	}
	
	private InspectorReportTotals infoFromVisits(List<InspectorRecordData> list,int planId, XSSFSheet sheet, int numberRow, XSSFCellStyle styleNormalCenter, XSSFCellStyle styleNormalLeftBorder,InventoryDao inventoryManager,SampleDao sampleManager, SamplePhaseDao samplePhaseManager) {
		
		Integer totalFocus = 0, totalInspected = 0, totalTreated = 0, totalDestroyed = 0;

		try {
			sheet.setColumnWidth(0, 1250);
			sheet.setColumnWidth(1, 6000);

			if(list.size() > 0){
				XSSFRow row;				
				for(int j = 0; j < list.size(); j++){
					InspectorRecordData inspectorRecordData = list.get(j);	
					Integer order = j+1;
					InspectorContainerData inspectorContainerData = inventoryManager.getInspectorContainerData(inspectorRecordData.getVisitUuid());
					
					row = sheet.createRow(numberRow);
					ExcelHelper.createCellString(row, (short)0, order.toString(), styleNormalLeftBorder);
					ExcelHelper.createCellString(row, (short)1, inspectorRecordData.getStreetName(), styleNormalLeftBorder);
					ExcelHelper.createCellString(row, (short)2, inspectorRecordData.getStreetNumber(), styleNormalLeftBorder);
					ExcelHelper.createCellString(row, (short)3, inspectorRecordData.getSectorName(), styleNormalLeftBorder);
					ExcelHelper.createCellString(row, (short)4, inspectorRecordData.getNameMz(), styleNormalLeftBorder);
					ExcelHelper.createCellInteger(row, (short)5, inspectorRecordData.getPersons(), styleNormalCenter);
					ExcelHelper.createCellString(row, (short)6, inspectorRecordData.getTipe(), styleNormalCenter);

					ExcelHelper.createCellInteger(row, (short)7, inspectorContainerData.getInspected4001(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)8, inspectorContainerData.getFocus4001(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)9, inspectorContainerData.getTreated4001() + inspectorContainerData.getDestroyed4001(), styleNormalCenter);

					ExcelHelper.createCellInteger(row, (short)10, inspectorContainerData.getInspected4002(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)11, inspectorContainerData.getFocus4002(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)12, inspectorContainerData.getTreated4002() + inspectorContainerData.getDestroyed4002(), styleNormalCenter);
					
					ExcelHelper.createCellInteger(row, (short)13, inspectorContainerData.getInspected4003(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)14, inspectorContainerData.getFocus4003(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)15, inspectorContainerData.getTreated4003() + inspectorContainerData.getDestroyed4003(), styleNormalCenter);
					
					ExcelHelper.createCellInteger(row, (short)16, inspectorContainerData.getInspected4004(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)17, inspectorContainerData.getFocus4004(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)18, inspectorContainerData.getTreated4004() + inspectorContainerData.getDestroyed4004(), styleNormalCenter);
					
					ExcelHelper.createCellInteger(row, (short)19, inspectorContainerData.getInspected4005(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)20, inspectorContainerData.getFocus4005(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)21, inspectorContainerData.getTreated4005() + inspectorContainerData.getDestroyed4005(), styleNormalCenter);
					
					ExcelHelper.createCellInteger(row, (short)22, inspectorContainerData.getInspected4007(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)23, inspectorContainerData.getFocus4007(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)24, inspectorContainerData.getTreated4007() + inspectorContainerData.getDestroyed4006(), styleNormalCenter);
					
					ExcelHelper.createCellInteger(row, (short)25, inspectorContainerData.getInspected4006(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)26, inspectorContainerData.getFocus4006(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)27, inspectorContainerData.getTreated4006() + inspectorContainerData.getDestroyed4007(), styleNormalCenter);
					
					ExcelHelper.createCellInteger(row, (short)28, inspectorContainerData.getInspected4008(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)29, inspectorContainerData.getFocus4008(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)30, inspectorContainerData.getTreated4008() + inspectorContainerData.getDestroyed4008(), styleNormalCenter);
					
					ExcelHelper.createCellInteger(row, (short)31, inspectorContainerData.getInspected4009(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)32, inspectorContainerData.getFocus4009(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)33, inspectorContainerData.getTreated4009() + inspectorContainerData.getDestroyed4009(), styleNormalCenter);
					
					ExcelHelper.createCellInteger(row, (short)34, inspectorContainerData.getInspected4010(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)35, inspectorContainerData.getFocus4010(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)36, inspectorContainerData.getTreated4010() + inspectorContainerData.getDestroyed4010(), styleNormalCenter);
					
					byte totalI = (byte)(inspectorContainerData.getInspected4001() + inspectorContainerData.getInspected4002() +inspectorContainerData.getInspected4003() +inspectorContainerData.getInspected4004() +
							inspectorContainerData.getInspected4005() + inspectorContainerData.getInspected4006() + inspectorContainerData.getInspected4007() + inspectorContainerData.getInspected4008() +
							inspectorContainerData.getInspected4009() + inspectorContainerData.getInspected4010());
					
					byte totalF = (byte)(inspectorContainerData.getFocus4001() + inspectorContainerData.getFocus4002() +inspectorContainerData.getFocus4003() +inspectorContainerData.getFocus4004() +
							inspectorContainerData.getFocus4005() + inspectorContainerData.getFocus4006() + inspectorContainerData.getFocus4007() + inspectorContainerData.getFocus4008() +
							inspectorContainerData.getFocus4009() + inspectorContainerData.getFocus4010());
					
					byte totalTD = (byte)(inspectorContainerData.getTreated4001() + inspectorContainerData.getTreated4002() +inspectorContainerData.getTreated4003() +inspectorContainerData.getTreated4004() +
							inspectorContainerData.getTreated4005() + inspectorContainerData.getTreated4006() + inspectorContainerData.getTreated4007() + inspectorContainerData.getTreated4008() +
							inspectorContainerData.getTreated4009() + inspectorContainerData.getTreated4010() + inspectorContainerData.getDestroyed4001() + inspectorContainerData.getDestroyed4002() +
							inspectorContainerData.getDestroyed4003() +inspectorContainerData.getDestroyed4004() + inspectorContainerData.getDestroyed4005() + inspectorContainerData.getDestroyed4006() + 
							inspectorContainerData.getDestroyed4007() + inspectorContainerData.getDestroyed4008() + inspectorContainerData.getDestroyed4009() + inspectorContainerData.getDestroyed4010());
					
					ExcelHelper.createCellByte(row, (short)37, totalI, styleNormalCenter);
					ExcelHelper.createCellByte(row, (short)38, totalF, styleNormalCenter);
					ExcelHelper.createCellByte(row, (short)39, totalTD, styleNormalCenter);
										
					
					List<Samples> samplesList = sampleManager.getSanplesList(planId, inspectorRecordData.getHouseUuid());
					
					int huevos= 0;
					int larvas = 0;
					int pupas = 0;
					int adultos = 0;
										
					for(int k = 0; k < samplesList.size(); k++) {						
						List<Integer> phases = samplePhaseManager.getSamplesPhases(samplesList.get(k));
						
						if(phases.get(0) == 5001) {
							huevos ++;
						}else if (phases.get(0) == 5002) {
							larvas ++;
						}else if (phases.get(0) == 5003) {
							pupas ++;
						}else if (phases.get(0) == 5004) {
							adultos ++;
						}						
					}					
					
					ExcelHelper.createCellInteger(row, (short)40, huevos, styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)41, larvas, styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)42, pupas, styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)43, adultos, styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)44, huevos+larvas+pupas+adultos, styleNormalCenter);
					
					ExcelHelper.createCellDecimal(row, (short)45, inspectorRecordData.getLarvicide(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)46, inspectorRecordData.getFeverish(), styleNormalCenter);					
					numberRow++;				
					
					totalInspected += inspectorContainerData.getInspected4001()+ inspectorContainerData.getInspected4002() +
							          inspectorContainerData.getInspected4003()+inspectorContainerData.getInspected4004() +
							          inspectorContainerData.getInspected4005()+inspectorContainerData.getInspected4006() +
			          				  inspectorContainerData.getInspected4007()+inspectorContainerData.getInspected4008() +
			                          inspectorContainerData.getInspected4009()+inspectorContainerData.getInspected4010();

					totalFocus += inspectorContainerData.getFocus4001()+ inspectorContainerData.getFocus4002() +
					              inspectorContainerData.getFocus4003()+inspectorContainerData.getFocus4004() +
					              inspectorContainerData.getFocus4005()+inspectorContainerData.getFocus4006() +
	          				      inspectorContainerData.getFocus4007()+inspectorContainerData.getFocus4008() +
	                              inspectorContainerData.getFocus4009()+inspectorContainerData.getFocus4010();
					
					totalTreated += inspectorContainerData.getTreated4001()+ inspectorContainerData.getTreated4002() +
					          inspectorContainerData.getTreated4003()+inspectorContainerData.getTreated4004() +
					          inspectorContainerData.getTreated4005()+inspectorContainerData.getTreated4006() +
	          				  inspectorContainerData.getTreated4007()+inspectorContainerData.getTreated4008() +
	                          inspectorContainerData.getTreated4009()+inspectorContainerData.getTreated4010();
					
					totalDestroyed += inspectorContainerData.getDestroyed4001()+ inspectorContainerData.getDestroyed4002() +
					          inspectorContainerData.getDestroyed4003()+inspectorContainerData.getDestroyed4004() +
					          inspectorContainerData.getDestroyed4005()+inspectorContainerData.getDestroyed4006() +
	          				  inspectorContainerData.getDestroyed4007()+inspectorContainerData.getDestroyed4008() +
	                          inspectorContainerData.getDestroyed4009()+inspectorContainerData.getDestroyed4010();
				}
				
			}
		
		}catch (Exception e) {			
			logger.error(RestErrorImpl.METHOD_INFOFROMVISITS + e.toString());		
		}
		
		return new InspectorReportTotals(totalFocus, totalInspected, totalTreated, totalDestroyed, numberRow);
		
	}
	
	private InspectorReportTotals infoFromSummaries(List<VisitSummaries> list,int planId, XSSFSheet sheet, int numberRow, XSSFCellStyle styleNormalCenter, XSSFCellStyle styleNormalLeftBorder,InventorySummaryDao inventorySummaryManager,SampleDao sampleManager, SamplePhaseDao samplePhaseManager) {
		
		Integer totalFocus = 0, totalInspected = 0, totalTreated = 0, totalDestroyed = 0;
		
		try {
			if(list.size() > 0){

				XSSFRow row;				
				for(int j = 0; j < list.size(); j++){
					
					VisitSummaries infoVisitSummary = list.get(j);		
					

					InventorySummaries type4001 = inventorySummaryManager.getDataByContainer(infoVisitSummary.getAreas().getId(), planId, 4001);
					InventorySummaries type4002 = inventorySummaryManager.getDataByContainer(infoVisitSummary.getAreas().getId(), planId, 4002);
					InventorySummaries type4003 = inventorySummaryManager.getDataByContainer(infoVisitSummary.getAreas().getId(), planId, 4003);
					InventorySummaries type4004 = inventorySummaryManager.getDataByContainer(infoVisitSummary.getAreas().getId(), planId, 4004);
					InventorySummaries type4005 = inventorySummaryManager.getDataByContainer(infoVisitSummary.getAreas().getId(), planId, 4005);
					InventorySummaries type4006 = inventorySummaryManager.getDataByContainer(infoVisitSummary.getAreas().getId(), planId, 4006);
					InventorySummaries type4007 = inventorySummaryManager.getDataByContainer(infoVisitSummary.getAreas().getId(), planId, 4007);
					InventorySummaries type4008 = inventorySummaryManager.getDataByContainer(infoVisitSummary.getAreas().getId(), planId, 4008);
					InventorySummaries type4009 = inventorySummaryManager.getDataByContainer(infoVisitSummary.getAreas().getId(), planId, 4009);
					InventorySummaries type4010 = inventorySummaryManager.getDataByContainer(infoVisitSummary.getAreas().getId(), planId, 4010);

					row = sheet.createRow(numberRow);

					ExcelHelper.createCellString(row, (short)0, "", styleNormalLeftBorder);
					ExcelHelper.createCellString(row, (short)1, "", styleNormalLeftBorder);
					ExcelHelper.createCellString(row, (short)2, infoVisitSummary.getAreas().getName(), styleNormalLeftBorder);
					ExcelHelper.createCellInteger(row, (short)3, infoVisitSummary.getPeople(), styleNormalCenter);

					ExcelHelper.createCellInteger(row, (short)4, type4001.getInspected(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)5, type4001.getFocus(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)6, type4001.getTreated() + type4001.getDestroyed(), styleNormalCenter);

					ExcelHelper.createCellInteger(row, (short)7, type4002.getInspected(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)8, type4002.getFocus(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)9, type4002.getTreated() + type4002.getDestroyed(), styleNormalCenter);

					ExcelHelper.createCellInteger(row, (short)10, type4003.getInspected(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)11, type4003.getFocus(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)12, type4003.getTreated() + type4003.getDestroyed(), styleNormalCenter);

					ExcelHelper.createCellInteger(row, (short)13, type4004.getInspected(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)14, type4004.getFocus(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)15, type4004.getTreated() + type4004.getDestroyed(), styleNormalCenter);

					ExcelHelper.createCellInteger(row, (short)16, type4005.getInspected(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)17, type4005.getFocus(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)18, type4005.getTreated() + type4005.getDestroyed(), styleNormalCenter);

					ExcelHelper.createCellInteger(row, (short)19, type4007.getInspected(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)20, type4007.getFocus(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)21, type4007.getTreated() + type4007.getDestroyed(), styleNormalCenter);

					ExcelHelper.createCellInteger(row, (short)22, type4006.getInspected(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)23, type4006.getFocus(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)24, type4006.getTreated() + type4006.getDestroyed(), styleNormalCenter);

					ExcelHelper.createCellInteger(row, (short)25, type4008.getInspected(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)26, type4008.getFocus(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)27, type4008.getTreated() + type4008.getDestroyed(), styleNormalCenter);

					ExcelHelper.createCellInteger(row, (short)28, type4009.getInspected(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)29, type4009.getFocus(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)30, type4009.getTreated() + type4009.getDestroyed(), styleNormalCenter);

					ExcelHelper.createCellInteger(row, (short)31, type4010.getInspected(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)32, type4010.getFocus(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)33, type4010.getTreated() + type4010.getDestroyed(), styleNormalCenter);
					
					byte totalI = (byte)(type4001.getInspected() + type4002.getInspected() + type4003.getInspected() +type4004.getInspected() +
								  type4005.getInspected() + type4006.getInspected() + type4007.getInspected() + type4008.getInspected() +
								  type4009.getInspected() + type4010.getInspected());
					
					byte totalF = (byte)(type4001.getFocus() + type4002.getFocus() + type4003.getFocus() +type4004.getFocus() +
								  type4005.getFocus() + type4006.getFocus() + type4007.getFocus() + type4008.getFocus() +
								  type4009.getFocus() + type4010.getFocus());
					
					byte totalTD = (byte)(type4001.getTreated() + type4002.getTreated() + type4003.getTreated() +type4004.getTreated() +
							type4005.getTreated() + type4006.getTreated() + type4007.getTreated() + type4008.getTreated() +
							type4009.getTreated() + type4010.getTreated() + type4001.getDestroyed() + type4002.getDestroyed() + 
							type4003.getDestroyed() +type4004.getDestroyed() + type4005.getDestroyed() + type4006.getDestroyed() + 
							type4007.getDestroyed() + type4008.getDestroyed() + type4009.getDestroyed() + type4010.getDestroyed());
					
					ExcelHelper.createCellByte(row, (short)34, totalI, styleNormalCenter);
					ExcelHelper.createCellByte(row, (short)35, totalF, styleNormalCenter);
					ExcelHelper.createCellByte(row, (short)36, totalTD, styleNormalCenter);
					
					List<Samples> samplesList = sampleManager.getSanplesList(planId, infoVisitSummary.getAreas().getId());
					
					int huevos= 0;
					int larvas = 0;
					int pupas = 0;
					int adultos = 0;
										
					for(int k = 0; k < samplesList.size(); k++) {						
						List<Integer> phases = samplePhaseManager.getSamplesPhases(samplesList.get(k));
						
						if(phases.get(0) == 5001) {
							huevos ++;
						}else if (phases.get(0) == 5002) {
							larvas ++;
						}else if (phases.get(0) == 5003) {
							pupas ++;
						}else if (phases.get(0) == 5004) {
							adultos ++;
						}						
					}	
						
					ExcelHelper.createCellInteger(row, (short)37, huevos, styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)38, larvas, styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)39, pupas, styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)40, adultos, styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)41, huevos+pupas+larvas+adultos, styleNormalCenter);					
					ExcelHelper.createCellDecimal(row, (short)42, infoVisitSummary.getLarvicide(), styleNormalCenter);
					ExcelHelper.createCellInteger(row, (short)43, infoVisitSummary.getFebriles(), styleNormalCenter);					
					numberRow++;				
					
					totalInspected += type4001.getInspected()+ type4002.getInspected() +
									  type4003.getInspected()+type4004.getInspected() +
									  type4005.getInspected()+type4006.getInspected() +
									  type4007.getInspected()+type4008.getInspected() +
									  type4009.getInspected()+type4010.getInspected();					

					totalFocus += type4001.getFocus()+ type4002.getFocus() +
								  type4003.getFocus()+ type4004.getFocus() +
								  type4005.getFocus()+ type4006.getFocus() +
								  type4007.getFocus()+ type4008.getFocus() +
								  type4009.getFocus()+ type4010.getFocus();

					
					totalTreated += type4001.getTreated()+ type4002.getTreated() +
								  type4003.getTreated()+ type4004.getTreated() +
								  type4005.getTreated()+ type4006.getTreated() +
								  type4007.getTreated()+ type4008.getTreated() +
								  type4009.getTreated()+ type4010.getTreated();

					
					totalDestroyed += type4001.getDestroyed()+ type4002.getDestroyed() +
									  type4003.getDestroyed()+ type4004.getDestroyed() +
									  type4005.getDestroyed()+ type4006.getDestroyed() +
									  type4007.getDestroyed()+ type4008.getDestroyed() +
									  type4009.getDestroyed()+ type4010.getDestroyed();

				}
				//sheet.autoSizeColumn(0);
			}
		
		}catch (Exception e) {			
			logger.error(RestErrorImpl.METHOD_INFOFROMSUMMARIRES + e.toString());		
		}
		return new InspectorReportTotals(totalFocus, totalInspected, totalTreated, totalDestroyed, numberRow);
		
	}
	
	@Override
	public boolean updateSyncFilePlan(SyncData syncData) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Plans entityPlan = new Plans();
		TableElements entityTableElements = new TableElements();
		try{
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			entityPlan = planManager.find(syncData.getPlanId());
			String json = new Gson().toJson(syncData.getListHouses());					 
			String routeFile = ConfigurationHelper.getSyncFilePath()+Calendar.getInstance().getTimeInMillis()+".txt";
			File f = new File(routeFile);
			FileWriter fileWriter = new FileWriter(f);
			fileWriter.write(json);
			fileWriter.flush();
			fileWriter.close();		
			entityTableElements = tableElementsManager.find(7005);				
			entityPlan.setSyncFile(routeFile);
			entityPlan.setTableElements(entityTableElements);
			planManager.save(entityPlan);
		}catch (Exception e){
		    logger.error(RestErrorImpl.UPDATE_SYNC_FILE + e.toString());
		    return false;			
		}finally{
			ctx.close();
		}
		System.out.println("PLAN("+syncData.getPlanId()+") Actualizado estado y File correctamente ");
		return true;
	}

	@Override
	public boolean checkPlan(Integer planId) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Plans entityPlan = new Plans();
		try{
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			entityPlan = planManager.find(planId);			
			if(entityPlan.getId() == null || entityPlan.getTableElements().getId() == 7003 || entityPlan.getTableElements().getId() == 7004 || entityPlan.getTableElements().getId() == 7005) {				
				return false;
			}
		}catch (Exception e){
			logger.error(RestErrorImpl.CHECK_PLAN + e.toString());
			return false;			
		}finally{
			ctx.close();
		}
		return true;
	}

	@Override
	public List<Integer> getListPendingPlans() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<Integer> list = new ArrayList<Integer>();
		try{
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			return planManager.getListPendingPlans();
		}catch (Exception e){
			logger.error(RestErrorImpl.GET_LIST_PENDING_PLANS + e.toString());
		}finally{
			ctx.close();
		}
		return list;
	}

	@Override
	public boolean closePlan(Integer planId) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Plans entityPlan = new Plans();
		try{
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			entityPlan = planManager.find(planId);				
			TableElements tableElements = tableElementsManager.find(ConfigurationHelper.getStatePlanFinished());	
			entityPlan.setTableElements(tableElements);
			planManager.save(entityPlan);				
			List<Areas> listAreas = areaManager.getAreasPlanVisits(planId);			
			this.createConsolidateInfo(entityPlan.getId(), listAreas);		
	
		}catch (Exception e){
			logger.error(RestErrorImpl.METHOD_CLOSE_PLAN + e.toString());
			return false;			
		}finally{
			ctx.close();
		}
		return true;
	}

	@Override
	public boolean createVisitPlan(Integer planId, PlanVisit planVisit) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			VisitDao visitManager = (VisitDao)ctx.getBean("VisitDaoImpl");
			SampleDao sampleManager = (SampleDao)ctx.getBean("SampleDaoImpl");
			InventoryDao inventoryManager = (InventoryDao)ctx.getBean("InventoryDaoImpl");
			AreaDao areaManager = (AreaDao)  ctx.getBean("AreaDaoImpl");
			PlansAreasDao planAreasManager = (PlansAreasDao) ctx.getBean("PlansAreasDaoImpl");
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");
			SamplePhaseDao samplePhaseManager = (SamplePhaseDao)ctx.getBean("SamplePhaseDaoImpl");
			boolean requalify = false;			
			String beforeReconversion = null;
			Plans plan = planManager.find(planId);	
			Areas area = areaManager.find(planVisit.getHouse().getAreaId());

			Houses house;
			if(planVisit.getHouse().getUuid() != null) {				
				house = houseManager.findByUUID(UUID.fromString(planVisit.getHouse().getUuid()));
				house.setPersonsNumber(planVisit.getHouse().getPersonsNumber());
				house.setStreetName(planVisit.getHouse().getStreetName());
				house.setStreetNumber(planVisit.getHouse().getStreetNumber());				 
			}else {				
				String ubigeo = this.getUbigeoCodeFromSync(area.getId());
				int number = houseManager.getNumberHouse(ubigeo);	
				String code =  String.format("%s%04d", ubigeo, number);					
				house = new Houses(UUID.randomUUID(), number, code, null, area.getLatitude(), area.getLongitude(), planVisit.getHouse().getStreetName(), planVisit.getHouse().getStreetNumber(), area, planVisit.getHouse().getPersonsNumber());
			}		
			houseManager.save(house);
			
			TableElements result = tableElementsManager.find(planVisit.getVisit().getResultId());
			
			int packets = 0;			
			List<InventorySync> list = planVisit.getListInventories();
			for(int n = 0; n < list.size(); n++) {					
				packets += list.get(n).getPacket() != null ? list.get(n).getPacket() : 0;
			}  			
			Integer beforeStatus = 0;
			if(plan.getInspections().getSchedules().getReconversionScheduleId() != null && plan.getInspections().getSchedules().getReconversionScheduleId() > 0) {
				requalify = true;
				Integer reconversionScheduleId = plan.getInspections().getSchedules().getReconversionScheduleId();
				beforeStatus = visitManager.reconvertedVisit(reconversionScheduleId, house.getUuid());
			}	
			
			if(beforeStatus == 2002) {
				beforeReconversion = "C";
			}else if(beforeStatus == 2003) {
				beforeReconversion = "R";
			}else if(beforeStatus == 2004) {
				beforeReconversion = "A";
			}

			Visits visit = new Visits(UUID.randomUUID(), house, plan, result, plan.getUsers(), plan.getDate(), planVisit.getVisit().getFeverish(), plan.getInspections().getLarvicides().getDose().multiply(new BigDecimal(packets)), "", requalify, beforeReconversion);

			visitManager.save(visit);
			
			for(int n = 0; n < list.size(); n++) {	
				InventorySync inventorySync = list.get(n);
				Inventories inventory;					
				inventory = new Inventories();					
				inventory.setUuid(UUID.randomUUID());
				inventory.setVisits(visit);
				inventory.setDestroyed(inventorySync.getDestroyed());
				inventory.setFocus(inventorySync.getFocus());
				inventory.setInspected(inventorySync.getInspected());
				inventory.setPacket(inventorySync.getPacket());
				inventory.setTreated(inventorySync.getTreated());
				inventory.setTableElements(tableElementsManager.find(inventorySync.getContainerId()));				
				inventoryManager.save(inventory);				
				
				for(int k = 0; k < inventorySync.getListSyncSample().size(); k++) {	
					SampleSyncImpl sample = inventorySync.getListSyncSample().get(k);	
					Samples sampleEntity = new Samples(UUID.randomUUID(), plan, tableElementsManager.find(inventorySync.getContainerId()), house, sample.getCode());
					sampleEntity = sampleManager.save(sampleEntity);	
					for(int m = 0; m < sample.getPhases().size(); m++) {
							Integer phaseId = sample.getPhases().get(m);						
						SamplePhases samplePhases = new SamplePhases(tableElementsManager.find(phaseId), sampleEntity);
						samplePhaseManager.save(samplePhases);
					}
				}				
			} 
			
			//Si la manzana era sustituta hay que actualizarla a no sustituta
			PlansAreas plansAreas = planAreasManager.getPlanArea(plan.getId(), area.getId());
			if(plansAreas.isSubstitute()) {
				plansAreas.setPin(null);
				plansAreas.setSubstitute(false);
				planAreasManager.save(plansAreas);
			}
	
		}catch (Exception e){
			logger.error(RestErrorImpl.METHOD_CREATE_VISIT_PLAN + e.toString());
			return false;			
		}finally{
			ctx.close();
		}
		return true;
	}

	@Override
	public boolean saveVisitPlan(Integer planId, String uuid, PlanVisit planVisit) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			VisitDao visitManager = (VisitDao)ctx.getBean("VisitDaoImpl");
			SampleDao sampleManager = (SampleDao)ctx.getBean("SampleDaoImpl");
			InventoryDao inventoryManager = (InventoryDao)ctx.getBean("InventoryDaoImpl");
			AreaDao areaManager = (AreaDao)  ctx.getBean("AreaDaoImpl");
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");
			SamplePhaseDao samplePhaseManager = (SamplePhaseDao)ctx.getBean("SamplePhaseDaoImpl");
			Plans plan = planManager.find(planId);	
			Areas area = areaManager.find(planVisit.getHouse().getAreaId());
			String beforeReconversion = null;
			Houses house;
			if(planVisit.getHouse().getUuid() != null) {				
				house = houseManager.findByUUID(UUID.fromString(planVisit.getHouse().getUuid()));
				house.setPersonsNumber(planVisit.getHouse().getPersonsNumber());
				house.setStreetName(planVisit.getHouse().getStreetName());
				house.setStreetNumber(planVisit.getHouse().getStreetNumber());
				 
			}else {
				
				String ubigeo = this.getUbigeoCodeFromSync(area.getId());
				int number = houseManager.getNumberHouse(ubigeo);	
				String code =  String.format("%s%04d", ubigeo, number);					
				house = new Houses(UUID.randomUUID(), number, code, null, area.getLatitude(), area.getLongitude(), planVisit.getHouse().getStreetName(), planVisit.getHouse().getStreetNumber(), area, planVisit.getHouse().getPersonsNumber());
			}			
			
			houseManager.save(house);
			
			TableElements result = tableElementsManager.find(planVisit.getVisit().getResultId());
			
			int packets = 0;
			
			List<InventorySync> list = planVisit.getListInventories();
			for(int n = 0; n < list.size(); n++) {					
				packets += list.get(n).getPacket() != null ? list.get(n).getPacket() : 0;
			} 
			
			Visits visit = visitManager.findByUUID(UUID.fromString(uuid));			
			visit.setHouses(house);
			visit.setLarvicide(plan.getInspections().getLarvicides().getDose().multiply(new BigDecimal(packets)));
			visit.setFeverish(planVisit.getVisit().getFeverish());
			visit.setTableElements(result);			
			
			Integer beforeStatus = 0;
			if(plan.getInspections().getSchedules().getReconversionScheduleId() != null && plan.getInspections().getSchedules().getReconversionScheduleId() > 0) {
				Integer reconversionScheduleId = plan.getInspections().getSchedules().getReconversionScheduleId();
				beforeStatus = visitManager.reconvertedVisit(reconversionScheduleId, house.getUuid());
			}			
			if(beforeStatus == 2002) {
				beforeReconversion = "C";
			}else if(beforeStatus == 2003) {
				beforeReconversion = "R";
			}else if(beforeStatus == 2004) {
				beforeReconversion = "A";
			}
			visit.setBeforeReconversion(beforeReconversion);			
			visitManager.save(visit);
			
			inventoryManager.deleteByVisit(visit);			
			sampleManager.deleteByHouse(house, plan);

			for(int n = 0; n < list.size(); n++) {	
				InventorySync inventorySync = list.get(n);
				Inventories inventory = new Inventories();					
				inventory.setUuid(UUID.randomUUID());
				inventory.setVisits(visit);				
				inventory.setDestroyed(inventorySync.getDestroyed());
				inventory.setFocus(inventorySync.getFocus());
				inventory.setInspected(inventorySync.getInspected());
				inventory.setPacket(inventorySync.getPacket());
				inventory.setTreated(inventorySync.getTreated());
				inventory.setTableElements(tableElementsManager.find(inventorySync.getContainerId()));				
				inventoryManager.save(inventory);				
				
				for(int k = 0; k < inventorySync.getListSyncSample().size(); k++) {					
					SampleSyncImpl sample = inventorySync.getListSyncSample().get(k);					
					Samples sampleEntity = new Samples(UUID.randomUUID(), plan, tableElementsManager.find(inventorySync.getContainerId()), house, sample.getCode());					
					sampleEntity = sampleManager.save(sampleEntity);	
					
					for(int m = 0; m < sample.getPhases().size(); m++) {
						Integer phaseId = sample.getPhases().get(m);						
						SamplePhases samplePhases = new SamplePhases(tableElementsManager.find(phaseId), sampleEntity);
						samplePhaseManager.save(samplePhases);
					}
				}		
			} 
	
		}catch (Exception e){
			logger.error(RestErrorImpl.METHOD_SAVE_VISIT_PLAN + e.toString());
			return false;			
		}finally{
			ctx.close();
		}
		return true;
	}
	
	public Integer createConsolidateInfo(Integer plan, List<Areas> listAreas) {
		
		Integer resultInsertUpdate = 0;
		try {
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
			InventorySummaryDao inventorySummaryManager = (InventorySummaryDao) ctx.getBean("InventorySummaryDaoImpl");
			VisitSummaryDao visitSummarieManager = (VisitSummaryDao) ctx.getBean("VisitSummaryDaoImpl");

			for(int k = 0 ; k< listAreas.size(); k++) {
				
				Areas areas = listAreas.get(k);
				Integer area = areas.getId();
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
			}
		
		}catch (Exception e) {
			resultInsertUpdate = 0;
			logger.error(RestErrorImpl.METHOD_CREATE_CONSOLIDATE_INFO + e.toString());
		}
		return resultInsertUpdate;
		
	}

	@Override//***//
	public List<PlanVisit> getListVisitsPlan(Session session,Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
	    List<PlanVisit> listPlanVisits = new ArrayList<PlanVisit>();
		try{
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			SampleDao sampleManager = (SampleDao) ctx.getBean("SampleDaoImpl");
	    	SamplePhaseDao samplePhaseManager = (SamplePhaseDao) ctx.getBean("SamplePhaseDaoImpl");
			Users user = userManager.getUserByToken(session.getAuthorizationToken());	
	    	HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");
	    	InventoryDao inventoryManager = (InventoryDao) ctx.getBean("InventoryDaoImpl");
		    List<VisitPlan> listVisits = visitManager.getVisitPlanList(user, id);
		    Plans plans = planManager.find(id);
		    for(int i = 0; i < listVisits.size(); i++) {
		    	VisitPlan visitPlan = listVisits.get(i);		    	
		    	Visits visits = visitManager.findByUUID(UUID.fromString(visitPlan.getUuid()));		    	
		    	Houses entityHouse = new Houses();
				entityHouse = houseManager.findByUUID(visits.getHouses().getUuid());
				HouseImpl house = new HouseImpl(entityHouse.getUuid().toString(),
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
	    	
				List<Inventories> listInventories = inventoryManager.getInventories(visits.getUuid());
				List<InventorySync> listInventorySync = new ArrayList<InventorySync>();

				
				for(int k = 0; k < listInventories.size(); k++) {
					List<SampleSyncImpl> list = new ArrayList<SampleSyncImpl>();

					Inventories inventories = listInventories.get(k);

			    	List<Samples> listSamples = sampleManager.getSanplesList(plans, inventories.getTableElements(), entityHouse);
			    	
			    	
			    	
			    	for(int m = 0; m < listSamples.size(); m++) {
			    		Samples samples = listSamples.get(m);	
			    		SampleSyncImpl sampleSyncImpl = new SampleSyncImpl(samples.getUuid().toString(), samples.getCode(), samplePhaseManager.getSamplesPhases(samples));
			    		list.add(sampleSyncImpl);
			    	}
			    	
			    	InventorySync inventorySync = new InventorySync(inventories.getUuid().toString(),
							inventories.getInspected(),
							inventories.getFocus(),
							inventories.getTreated(),
							inventories.getPacket(),
							inventories.getDestroyed(),
							inventories.getTableElements().getId(),
							inventories.getVisits().getUuid().toString(),
							list);

			    	listInventorySync.add(inventorySync);	

				}

				PlanVisit planVisit = new PlanVisit(house, visitPlan, listInventorySync);	
				listPlanVisits.add(planVisit);
		    }		    

			
		}catch (Exception e){
			logger.error(RestErrorImpl.METHOD_GET_LIST_PLAN_VISIT+ e.toString());
		}finally{
			ctx.close();
		}
		return listPlanVisits;
	}
	
	
}
