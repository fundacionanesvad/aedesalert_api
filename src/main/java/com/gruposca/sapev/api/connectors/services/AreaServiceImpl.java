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
import com.gruposca.sapev.api.connectors.dao.impl.HouseDao;
import com.gruposca.sapev.api.connectors.dao.impl.InspectionDao;
import com.gruposca.sapev.api.connectors.dao.impl.LabelDao;
import com.gruposca.sapev.api.connectors.dao.impl.PlansAreasDao;
import com.gruposca.sapev.api.connectors.dao.impl.ReportDao;
import com.gruposca.sapev.api.connectors.dao.impl.ScenesDao;
import com.gruposca.sapev.api.connectors.dao.impl.ScheduleDao;
import com.gruposca.sapev.api.connectors.dao.impl.TableElementsDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Houses;
import com.gruposca.sapev.api.connectors.dao.model.Inspections;
import com.gruposca.sapev.api.connectors.dao.model.Reports;
import com.gruposca.sapev.api.connectors.dao.model.Scenes;
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.modelview.Area;
import com.gruposca.sapev.api.modelview.AreaChild;
import com.gruposca.sapev.api.modelview.AreaChildsImpl;
import com.gruposca.sapev.api.modelview.AreaGetImpl;
import com.gruposca.sapev.api.modelview.AreaCreateUpdateImpl;
import com.gruposca.sapev.api.modelview.AreaInspectionListImpl;
import com.gruposca.sapev.api.modelview.AreaListImpl;
import com.gruposca.sapev.api.modelview.AreaMapFocus;
import com.gruposca.sapev.api.modelview.AreaMapList;
import com.gruposca.sapev.api.modelview.AreaParentsImpl;
import com.gruposca.sapev.api.modelview.AreaTree;
import com.gruposca.sapev.api.modelview.Child;
import com.gruposca.sapev.api.modelview.FocusHouse;
import com.gruposca.sapev.api.modelview.House;
import com.gruposca.sapev.api.modelview.HouseAreaImpl;
import com.gruposca.sapev.api.modelview.Inspection;
import com.gruposca.sapev.api.modelview.MapAedic;
import com.gruposca.sapev.api.modelview.MapDates;
import com.gruposca.sapev.api.modelview.MapFocus;
import com.gruposca.sapev.api.modelview.ReportArea;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.SamplesMapFocus;
import com.gruposca.sapev.api.modelview.ScheduleArea;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.StatsEessAreas;
import com.gruposca.sapev.api.modelview.StatsEessResults;
import com.gruposca.sapev.api.modelview.StatsMrAreas;
import com.gruposca.sapev.api.modelview.StatsMrResults;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.AreaService;

public class AreaServiceImpl extends AbsService implements AreaService{

	public AreaServiceImpl(AbsConnector connector) { super( connector); }

	public List<Areas> listAreas = new ArrayList<Areas>();	
	
	@Override
	public Area getArea(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Areas entityArear = new Areas();
		Integer parentId = null;
		Integer typeId = null;
		Area area;		
		try{			
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			entityArear = areaManager.find(id);				
			if(entityArear.getAreas() != null){
				parentId = entityArear.getAreas().getId() ;
				typeId = entityArear.getTableElementsByTypeId().getId();
			}
			area = new AreaGetImpl(entityArear.getCode(), entityArear.getName(), entityArear.getHouses(), entityArear.getCoords(), parentId, typeId, entityArear.getLatitude(), entityArear.getLongitude() );				 			   
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETAREA +e.toString());
			area=null;
		}finally{
			ctx.close();
		}
		return area;
	}

	@Override
	public Areas createArea(AreaCreateUpdateImpl area) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Areas entityArea;
		try{	
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");	
			ScenesDao sceneManager = (ScenesDao) ctx.getBean("ScenesDaoImpl");	

			Areas parentArea = areaManager.find(area.getParentId());
			TableElements type = tableElementsManager.find(area.getTypeId());

			Integer houses = 0;
			if((area.getTypeId() == ConfigurationHelper.getAreaTypeBlock() || area.getTypeId() == ConfigurationHelper.getAreaTypeSector()) && (area.getHouses() == null || area.getHouses() == 0)) {				
				houses = 1;
			}else {
				houses = area.getHouses();
			}
			
			if (parentArea != null && type != null){
				
				entityArea = new Areas(area.getCode(), 
									   area.getName(),
									   houses,
									   area.getCoords(),
									   true, 
									   parentArea,
									   type, 
									   (area.getLatitude() != null) ? area.getLatitude() : null,
									   (area.getLongitude() != null) ? area.getLongitude() : null);
				entityArea = areaManager.save(entityArea);
				
				parentArea.setLeaf(false);
				areaManager.save(parentArea);
				
				//Si es un EESS hay que crear un escenario asociado
				if(ConfigurationHelper.getAreaTypeEESS() == type.getId()){
					Scenes scenes = new Scenes(entityArea, (byte)1);
					sceneManager.save(scenes);
				}
				
				if(entityArea != null){
					return entityArea;
				}else{
					return null;
				}		
					
			}else{
				logger.error(RestErrorImpl.ERROR_GET_FATHER_ID);
				return null;
			}			

		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_CREATEAREA +e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public Areas updateArea(Integer id, AreaCreateUpdateImpl area) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{			
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");	
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");		
			
			if(area.getParentId() == 0){
				area.setParentId(1);
			}			
			Areas parentNewArea = areaManager.find(area.getParentId());
			TableElements type = tableElementsManager.find(area.getTypeId());

			if (parentNewArea != null && type != null){
				
				Areas updateArea= areaManager.find(id);				
				Areas parentOldArea = areaManager.find(updateArea.getAreas().getId());		
				
				updateArea.setCode(area.getCode());
				updateArea.setName(area.getName());
				
				Integer houses = 0;
				if((area.getTypeId() == ConfigurationHelper.getAreaTypeBlock() || area.getTypeId() == ConfigurationHelper.getAreaTypeSector()) && (area.getHouses() == null || area.getHouses() == 0)) {				
					houses = 1;
				}else {
					houses = area.getHouses();
				}				
				updateArea.setHouses(houses);
				updateArea.setCoords(area.getCoords());
				updateArea.setAreas(parentNewArea);
				updateArea.setTableElementsByTypeId(type);
				if(area.getLatitude() != null){
					updateArea.setLatitude(area.getLatitude());
				}
				if(area.getLongitude() != null){
					updateArea.setLongitude(area.getLongitude());
				}				
				updateArea = areaManager.save(updateArea);			
			
				//Hay que actualizar el leaf
				if(areaManager.numChilds(parentOldArea) == 0 && !parentOldArea.isLeaf()){	
					parentOldArea.setLeaf(true);	
					areaManager.save(parentOldArea);
				}					
				parentNewArea.setLeaf(false);
				areaManager.save(parentNewArea);	
				
				areaManager.executeProc(updateArea.getAreas().getId());			
				
				return updateArea;					
			
			}else{
				logger.error(RestErrorImpl.ERROR_GET_FATHER_ID);
				return null;				
			}			
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_UPDATEAREA +e.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}

	@Override
	public boolean deleteArea(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");		
		boolean result = false;
		try{	
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");		
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");
			PlansAreasDao plansAreaManager =(PlansAreasDao) ctx.getBean("PlansAreasDaoImpl");
			Areas area = areaManager.find(id);				
			Areas parentArea = areaManager.find(area.getAreas().getId());
			
			//ScenesDao scenesManager = (ScenesDao) ctx.getBean("ScenesDaoImpl");
			//if(!houseManager.existWithArea(area) && !plansAreaManager.existWithArea(area) && !scenesManager.existWithArea(area) && areaManager.numChilds(area) == 0){
			if(!houseManager.existWithArea(area) && !plansAreaManager.existWithArea(area) && areaManager.numChilds(area) == 0){

				areaManager.delete(area);
				result = true;				
				if(areaManager.numChilds(parentArea) == 0){					
					parentArea.setLeaf(true);
					areaManager.save(parentArea);
				}				
			}
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_DELETEAREA +e.toString());
		}finally{
			ctx.close();
		}	
		return result;
	}

	@Override
	public Area getChilds(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Area area;
		List<Areas> listAreas = new ArrayList<Areas>();
		try{	
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");		
			Areas entityArea = areaManager.find(id);
			String parentArea = "";
			if(entityArea.getAreas() != null){
				parentArea = areaManager.find(entityArea.getAreas().getId()).getName();
			}			
			listAreas = areaManager.getListAreas(entityArea);				
			area = new AreaChildsImpl(entityArea.getId(), entityArea.getName(),parentArea, entityArea.getCoords(), entityArea.getHouses(), entityArea.getTableElementsByTypeId().getId(), entityArea.getLatitude(), entityArea.getLongitude(), this.getListChilds(listAreas));
			return area;
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETCHILDS +e.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}
	
	public List<Child> getListChilds (List<Areas> listAreas){		
		
		List<Child> listChilds = new ArrayList<Child>();
		try {			
			for(int i = 0; i<listAreas.size(); i++){				
				Areas areas = listAreas.get(i);				
				Child child = new Child(areas.getId(), areas.getCode(), areas.getName(), areas.getHouses(), areas.getCoords(), areas.getTableElementsByTypeId().getId(), areas.getLatitude(), areas.getLongitude());				
				listChilds.add(child);				
			}		
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLISTCHILDS +e.toString());
			return null;
		}		
		return listChilds;			
	}

	@Override
	public List<Area> getParents(Session session, Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<Area> listAreas = new ArrayList<Area>();		
		try{	
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");	
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");	
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			Areas entityArea = areaManager.find(id);			
			
			if(entityArea.getAreas() == null){				
				return listAreas;				
			}else{				
				Areas areaParent = areaManager.find(entityArea.getAreas().getId());		
				while (areaParent.getAreas() != null && areaManager.userAreaPermission(user.getAreas().getId(), areaParent.getAreas().getId())){					
					Area area = new AreaParentsImpl(areaParent.getId(), areaParent.getName());
					listAreas.add(0,area);					
					areaParent = areaManager.find(areaParent.getAreas().getId());					
				}	
				
				if(areaManager.userAreaPermission(user.getAreas().getId(), areaParent.getId())){
					Area area = new AreaParentsImpl(areaParent.getId(), areaParent.getName());
					listAreas.add(0,area);				
				}
			}
			return listAreas;
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETPARENTS +e.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}

	@Override
	public List<House> getHousesArea(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<House> listHouse = new ArrayList<House>();
		Houses entityHouse = new Houses();
		House house;
		try{
			HouseDao houseManager = (HouseDao) ctx.getBean("HouseDaoImpl");		
			List<Houses> entityHouseList = houseManager.getHousesList(id);
			
			for(int i = 0; i < entityHouseList.size(); i++){					
				entityHouse = entityHouseList.get(i);				
				house = new HouseAreaImpl(entityHouse.getUuid().toString(), entityHouse.getCode(), entityHouse.getStreetName(), entityHouse.getStreetNumber(), entityHouse.getNumber());
				listHouse.add(house);
			}
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETHOUSESAREA +e.toString());
			listHouse = null;
			
		}finally{
			ctx.close();
		}		
		return listHouse;
	}

	@Override
	public List<Area> getListArea() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<Area> areaList = new ArrayList<Area>();
		Areas entityArea = new Areas();
		Area area;		
		try{
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			List<Areas> entityAreaList = areaManager.findAll();
			for(int i = 0; i < entityAreaList.size(); i++){
				
				entityArea = entityAreaList.get(i);		
				area = new AreaListImpl(entityArea.getId(),
										entityArea.getCode(),
									    entityArea.getName(),
									    (entityArea.getAreas() != null) ? entityArea.getAreas().getId(): null,
									    entityArea.getTableElementsByTypeId().getId(),
									    entityArea.getLatitude(),
									    entityArea.getLongitude());
				areaList.add(area);
			}
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLISTAREA +e.toString());
			areaList = null;
			
		}finally{
			ctx.close();
		}
		
		return areaList;
	}

	@Override
	public List<Inspection> getInspectionsArea(Session session, Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");		
		List<Inspection> inspectionList = new ArrayList<Inspection>();
		Inspections entityInspection = new Inspections();
		Inspection inspection;		
		try{
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");
			LabelDao labelManager = (LabelDao) ctx.getBean("LabelDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");	
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			List<Inspections> entityInspectionList = inspectionManager.getListByArea(id);

			for(int i = 0; i < entityInspectionList.size(); i++){				
				entityInspection = entityInspectionList.get(i);		
				inspection = new AreaInspectionListImpl(entityInspection.getId(),
														entityInspection.getStartDate().getTime(),
														entityInspection.getFinishDate().getTime(), 
														entityInspection.getInspectionSize(), 
														labelManager.getValueElement(user.getLanguages(), entityInspection.getTableElementsByTypeId()),
														labelManager.getValueElement(user.getLanguages(), entityInspection.getTableElementsByStateId()));		
				inspectionList.add(inspection);
			}
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETINSPECTIONSAREA +e.toString());
			inspectionList = null;
			
		}finally{
			ctx.close();
		}
		
		return inspectionList;
	}

	@Override
	public List<ReportArea> getReportsArea(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<ReportArea> listReport = new ArrayList<ReportArea>();
		Reports entityReport = new Reports();
		try{
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");		
			Areas entityArea = areaManager.find(id);		

			ReportDao reportManager = (ReportDao) ctx.getBean("ReportDaoImpl");		
			List<Reports> entityReportAreaList = reportManager.getReportsAreaList(entityArea);
			
			for(int i = 0; i < entityReportAreaList.size(); i++){					
				entityReport = entityReportAreaList.get(i);				
				ReportArea report = new ReportArea(entityReport.getId(),										    
										    entityReport.getDate().getTime(),
										    entityReport.getAreas().getId(),
										    entityReport.getName(),
										    entityReport.getStartDate().getTime(),
										    entityReport.getFinishDate().getTime(),
										    entityReport.getDataType(),
										    entityReport.getTableElements().getId(),
										    (entityReport.getUsers() != null) ? entityReport.getUsers().getId() : null);
				listReport.add(report);
			}
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETREPORTSAREA +e.toString());
		    listReport = null;
			
		}finally{
			ctx.close();
		}		
		return listReport;
	}

	@Override 
	public MapAedic getMapAedico(Integer id, MapDates mapDates, Session session) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<AreaMapList> areaMapLists = new ArrayList<AreaMapList>();
		Areas entityArea = new Areas();
		MapAedic mapAedic;		
		try{
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");	
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			entityArea = areaManager.find(id);					
			Date startDate = new Date(mapDates.getStartDate());
			Date finishDate = new Date(mapDates.getFinishDate());
			areaMapLists = areaManager.getListAreasMap(entityArea, startDate, finishDate, user.getAreas().getId());			
			mapAedic = new MapAedic(entityArea.getName(), areaMapLists);
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETMAPAEDIC +e.toString());
		    mapAedic = null;
		}finally{
			ctx.close();
		}		
		return mapAedic;
	}

	@Override
	public MapFocus getMapFocus(Session session, Integer id, MapDates mapDates) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<AreaMapFocus> list = new ArrayList<AreaMapFocus>();
		List<AreaMapFocus> resultList = new ArrayList<AreaMapFocus>();
		Areas entityArea = new Areas();
		MapFocus mapFocus;		
		try{
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");	
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			entityArea = areaManager.find(id);					
			Date startDate = new Date(mapDates.getStartDate());
			Date finishDate = new Date(mapDates.getFinishDate());			
			
			list = areaManager.getListAreasMapFocus(entityArea, user.getAreas().getId());
			
			for(int i = 0; i < list.size(); i++){
				AreaMapFocus areaMapFocus = list.get(i);				
				List<SamplesMapFocus> listSamples = areaManager.getSamplesMapFocus(areaMapFocus.getId(), startDate, finishDate, user.getLanguages());				
				areaMapFocus.setSamples(listSamples);
				resultList.add(areaMapFocus);
			}			
			mapFocus = new MapFocus(entityArea.getName(), resultList);

			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETMAPFOCUS +e.toString());
		    mapFocus = null;
		}finally{
			ctx.close();
		}		
		return mapFocus;
	}

	@Override
	public List<AreaChild> getSectors(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<Areas> listAreas = new ArrayList<Areas>();
		List<AreaChild> listSectors = new ArrayList<AreaChild>();
		try{	
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");		
			Areas entityArea = areaManager.find(id);			
			listAreas = areaManager.getListChildByType(entityArea, ConfigurationHelper.getAreaTypeSector());				
			listSectors = this.getListAreaChilds (listAreas);

			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETSECTORS +e.toString());
			return null;
		}finally{
			ctx.close();
		}
		return listSectors;
	}		
	
	@Override
	public List<AreaChild> getBlocks(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<Areas> listAreas = new ArrayList<Areas>();
		List<AreaChild> listSectors = new ArrayList<AreaChild>();
		try{	
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");		
			Areas entityArea = areaManager.find(id);			
			listAreas = areaManager.getListChildByType(entityArea, ConfigurationHelper.getAreaTypeBlock());				
			listSectors = this.getListAreaChilds (listAreas);

			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETBLOCKS +e.toString());
			return null;
		}finally{
			ctx.close();
		}
		return listSectors;
	}	
	
	
	public List<AreaChild> getListAreaChilds (List<Areas> listAreas){		
		
		List<AreaChild> listChilds = new ArrayList<AreaChild>();
		try {			
			for(int i = 0; i<listAreas.size(); i++){				
				Areas areas = listAreas.get(i);				
				AreaChild areaChild = new AreaChild(areas.getId(), areas.getCode(), areas.getName(), areas.getHouses(), areas.getCoords(), areas.getTableElementsByTypeId().getId(), areas.getLatitude(), areas.getLongitude(), areas.getAreas().getId());				
				listChilds.add(areaChild);				
			}		
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLISTCHILDS +e.toString());
			return null;
		}		
		return listChilds;			
	}

	@Override
	public List<AreaChild> getDescendants(Integer id, Integer typeId) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<Areas> listAreas = new ArrayList<Areas>();
		List<AreaChild> listDescendants = new ArrayList<AreaChild>();
		try{	
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");		
			Areas entityArea = areaManager.find(id);			
			listAreas = areaManager.getListByType(entityArea, typeId);				
			listDescendants = this.getListAreaChilds (listAreas);
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETBLOCKS +e.toString());
			return null;
		}finally{
			ctx.close();
		}
		return listDescendants;
	}

	@Override
	public List<ScheduleArea> getSchedulesArea(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<ScheduleArea> listSchedules = new ArrayList<ScheduleArea>();
		try{
			ScheduleDao schedulesManager = (ScheduleDao) ctx.getBean("ScheduleDaoImpl");	
			listSchedules = schedulesManager.getListSchedulesByArea(id);

		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETSCHEDULESAREA +e.toString());
		    listSchedules = null;
			
		}finally{
			ctx.close();
		}		
		return listSchedules;
	}	
	
	@Override
	public AreaTree getTreeAreas() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		System.out.println("--- getTreeAreas");

		AreaTree areaTree = new AreaTree();
		try{
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");	
			listAreas = areaManager.getListAreasOrder();			
			Areas area = areaManager.getParentArea();
			System.out.println("--- 1");
			areaTree.setId(area.getId());
			System.out.println("--- 2");
			areaTree.setTitle(area.getName());
			System.out.println("--- 3");
			areaTree.setNodes(this.getChilds(area,listAreas));
			System.out.println("--- 4");
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETTREEAREA + e.toString());
		    System.out.println("--- 5");
		}finally{
			ctx.close();
		}
		System.out.println("areaTree");
        return  areaTree;
	}
	
	public List<AreaTree> getChilds(Areas area, List<Areas> listAreas){

		List<AreaTree> list = new ArrayList<AreaTree>();
		List<AreaTree> listVacia = new ArrayList<AreaTree>();
		try{
			List<Areas> listChilds = getListChilds(area.getId(), listAreas);			
			list = new ArrayList<AreaTree>();
			 for (Areas s : listChilds) {

				 AreaTree areaTree = new AreaTree();
				 areaTree.setId(s.getId());
				 areaTree.setTitle(s.getName());
				 if(s.getTableElementsByTypeId().getId() == 9005){
					 areaTree.setNodes(listVacia);
				 }else{
					 areaTree.setNodes(getChilds(s, listAreas));
				 }
				 list.add(areaTree);			
		     }
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETCHILDSTREE + e.toString());
		}
		return list;
	}
	
	public List<Areas> getListChilds(Integer areaId, List<Areas> listAreas){	
		List<Areas> listChilds = new ArrayList<Areas>();
		List<Areas> listAux = listAreas;
		for (int i = listAux.size()-1; i>=0 ; i--) {
			Areas area = listAux.get(i);
			if(area.getAreas()!= null && area.getAreas().getId() == areaId){
				listChilds.add(area);
				listAreas.remove(i);
			} 					
	    }		
		return listChilds;		
	}

	@Override
	public List<StatsMrAreas> getListStatsMr(Session session, Integer id, List<Integer> listInspections) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Areas entityArea = new Areas();
		List<StatsMrAreas> list = new ArrayList<StatsMrAreas>();
		String insp = "";
		try{
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			InspectionDao inspectionManager =  (InspectionDao) ctx.getBean("InspectionDaoImpl");			
			entityArea = areaManager.find(id);					
			
			List<Areas> listAreas = areaManager.getListAreas(entityArea);
			
			for(int i = 0 ; i < listAreas.size(); i ++) {				
				Areas area = listAreas.get(i);	
				if(listInspections != null && listInspections.size() > 0) {
					insp = this.inspectionString(listInspections);
				} else {
					insp = "0";
				}
				StatsMrResults control = inspectionManager.getStatsMrResult(1, area.getId(), insp);
				StatsMrResults reconversion = inspectionManager.getStatsMrResult(2, area.getId(), insp);	

				StatsMrAreas statsMrAreas = new StatsMrAreas(area.getId(), area.getName(), area.getCoords(), control, reconversion);
				list.add(statsMrAreas);
			}
	
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLISTSTATSMR +e.toString());
		}finally{
			ctx.close();
		}		
		return list;
	}
	
	
	public String inspectionString(List<Integer> listInspections) {
		String inspections = "";		
		for (Integer value : listInspections) {
			
			inspections += value+",";
		}		
		return inspections.substring(0, inspections.length() - 1);
	}

	@Override
	public List<StatsEessAreas> getListStatsEess(Session session, Integer id, List<Integer> listInspections) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Areas entityArea = new Areas();
		List<StatsEessAreas> list = new ArrayList<StatsEessAreas>();
		String insp = "";
		try{
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			InspectionDao inspectionManager =  (InspectionDao) ctx.getBean("InspectionDaoImpl");			
			entityArea = areaManager.find(id);					
			
			List<Areas> listAreas = areaManager.getListAreasEess(entityArea);
			
			for(int i = 0 ; i < listAreas.size(); i ++) {				
				Areas area = listAreas.get(i);	
				if(listInspections != null && listInspections.size() > 0) {
					insp = this.inspectionString(listInspections);
				} else {
					insp = "0";
				}

				StatsEessResults control = inspectionManager.getStatsEessResult(1002, false, area.getId(), insp);
				StatsEessResults reconversion = inspectionManager.getStatsEessResult(1002, true, area.getId(), insp);	
				StatsEessResults vigilancia = inspectionManager.getStatsEessResult(1001, false, area.getId(), insp);
				control.setContainers(inspectionManager.getListStatContainers(1002, false, area.getId(), insp));
				reconversion.setContainers(inspectionManager.getListStatContainers(1002, true, area.getId(), insp));
				vigilancia.setContainers(inspectionManager.getListStatContainers(1001, false, area.getId(), insp));
				StatsEessAreas statsMrAreas = new StatsEessAreas(area.getId(), area.getAreas().getId(),area.getName(), area.getCoords(), control, reconversion, vigilancia);
				list.add(statsMrAreas);
			}
	
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLISTSTATSEESS +e.toString());
		}finally{
			ctx.close();
		}		
		return list;
	}

	@Override
	public List<FocusHouse> getListFocusHouses(Session session, Integer id, List<Integer> listInspections) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<FocusHouse> list = new ArrayList<FocusHouse>();
		String insp = "";
		try{
			InspectionDao inspectionManager =  (InspectionDao) ctx.getBean("InspectionDaoImpl");			
			if(listInspections != null && listInspections.size() > 0) {
				insp = this.inspectionString(listInspections);
			} else {
				insp = "0";
			}
			
			list = inspectionManager.getListFocusHouses(insp);		
	
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLISFOCUSHOUSES +e.toString());
		}finally{
			ctx.close();
		}		
		return list;
		
		
		
		

		
	}

}
