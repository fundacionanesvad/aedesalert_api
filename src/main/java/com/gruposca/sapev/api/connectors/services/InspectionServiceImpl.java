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

import java.io.FileOutputStream;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.gruposca.sapev.api.connectors.dao.impl.AlertDao;
import com.gruposca.sapev.api.connectors.dao.impl.AreaDao;
import com.gruposca.sapev.api.connectors.dao.impl.InspectionDao;
import com.gruposca.sapev.api.connectors.dao.impl.LarvicideDao;
import com.gruposca.sapev.api.connectors.dao.impl.PlanDao;
import com.gruposca.sapev.api.connectors.dao.impl.PlansAreasDao;
import com.gruposca.sapev.api.connectors.dao.impl.SampleDao;
import com.gruposca.sapev.api.connectors.dao.impl.ScheduleDao;
import com.gruposca.sapev.api.connectors.dao.impl.TableElementsDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.impl.VisitDao;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Inspections;
import com.gruposca.sapev.api.connectors.dao.model.Larvicides;
import com.gruposca.sapev.api.connectors.dao.model.Plans;
import com.gruposca.sapev.api.connectors.dao.model.Schedules;
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.helper.ExcelHelper;
import com.gruposca.sapev.api.modelview.Inspection;
import com.gruposca.sapev.api.modelview.InspectionBlock;
import com.gruposca.sapev.api.modelview.InspectionImpl;
import com.gruposca.sapev.api.modelview.InspectionList;
import com.gruposca.sapev.api.modelview.InspectionListImpl;
import com.gruposca.sapev.api.modelview.ParamFilterInspections;
import com.gruposca.sapev.api.modelview.ParamFilterPlans;
import com.gruposca.sapev.api.modelview.ParamFilterVisits;
import com.gruposca.sapev.api.modelview.Plan;
import com.gruposca.sapev.api.modelview.PlanAreas;
import com.gruposca.sapev.api.modelview.PlanInspection;
import com.gruposca.sapev.api.modelview.PlanInspectionListImpl;
import com.gruposca.sapev.api.modelview.PlansInspectionList;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.SampleInspection;
import com.gruposca.sapev.api.modelview.SamplesFile;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.Substitutes;
import com.gruposca.sapev.api.modelview.VisitList;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.InspectionService;

public class InspectionServiceImpl extends AbsService implements InspectionService{

	public InspectionServiceImpl(AbsConnector connector) { super( connector); }

	@Override
	public InspectionList getListInspection(Session session, ParamFilterInspections paramFilterInspections) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<InspectionListImpl> list = new ArrayList<InspectionListImpl>();
		InspectionList inspectionList = new InspectionList();
		try{
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");	
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			list = inspectionManager.getListByAreaUser(user, paramFilterInspections);		
			Integer count = inspectionManager.getCountList(user, paramFilterInspections);	
			inspectionList = new InspectionList(count, list);
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLISTINSPECTION + e.toString());
			inspectionList = null;
			
		}finally{
			ctx.close();
		}
		
		return inspectionList;
	}

	@Override
	public Inspection getInspection(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Inspection inspection = null;		
		try{
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");
			inspection = inspectionManager.getInspectionbyId(id);
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETINSPECTION + e.toString());
			inspection = null;
			
		}finally{
			ctx.close();
		}
		
		return inspection;
	}

	@Override
	public boolean deleteInspection(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		boolean result = false;
		try{
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");
			AlertDao alertManager = (AlertDao) ctx.getBean("AlertDaoImpl");
			Inspections inspection = inspectionManager.find(id);		
			inspectionManager.delete(inspection);	
			//Borrar alertas de nueva inspeccion			
			String link = String.format(ConfigurationHelper.getAlertNewInspectionLink(),id);
			alertManager.deleteAlert(link);
			result = true;
			
		}catch (Exception ex){
		    logger.error(RestErrorImpl.METHOD_DELETEINSPECTION + RestErrorImpl.ERROR_RESTRICCION_FK);
		
		}finally{
			ctx.close();
		}	
		return result;
	}

	@Override
	public Inspections createInspection(InspectionImpl inspection) {
				
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Inspections entityInspection = null;

		try{	
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");		
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");		
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");
			ScheduleDao scheduleManager = (ScheduleDao) ctx.getBean("ScheduleDaoImpl");
			LarvicideDao larvicideManager = (LarvicideDao) ctx.getBean("LarvicideDaoImpl");
			Areas area = areaManager.find(inspection.getAreaId());
			TableElements type = tableElementsManager.find(inspection.getTypeId());
			TableElements state = tableElementsManager.find(inspection.getStateId());
			Schedules schedule = scheduleManager.find(inspection.getScheduleId());
			Larvicides larvicide = larvicideManager.find(inspection.getLarvicideId());
			
			entityInspection = new Inspections(type,
					                           state,
					                           inspection.getInspectionSize(),
					                           (inspection.getStartDate() != null) ? new Date(inspection.getStartDate()) : null,
					                           (inspection.getFinishDate() != null) ? new Date(inspection.getFinishDate()) : null,
					                           (inspection.getCoverage() != null) ?inspection.getCoverage() : null,
					                            area,
					                        	schedule,
					                        	larvicide,
					                        	(inspection.getTrapLatitude() != null) ?inspection.getTrapLatitude() : null,
					                        	(inspection.getTrapLongitude() != null) ?inspection.getTrapLongitude() : null,								
												(inspection.getTrapId() != null) ?inspection.getTrapId() : null,
												(inspection.getTrapDate() != null) ? new Date(inspection.getTrapDate()) : null
												);	

			entityInspection = inspectionManager.save(entityInspection);			
			if(entityInspection != null){
				//Generar alerta de Nueva Inspección Planificada
				AlertServiceImpl alertServiceImpl = new AlertServiceImpl(getConnector());
				alertServiceImpl.generateNewInspectionAlert(ctx, entityInspection);
				return entityInspection;
			}else{
				return null;
			}		
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_CREATEINSPECTION + e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public Inspections updateInspections(Integer id, InspectionImpl inspection) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{	
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");		
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");		
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");
			Areas area = areaManager.find(inspection.getAreaId());
			ScheduleDao scheduleManager = (ScheduleDao) ctx.getBean("ScheduleDaoImpl");
			LarvicideDao larvicideManager = (LarvicideDao) ctx.getBean("LarvicideDaoImpl");
			TableElements type = tableElementsManager.find(inspection.getTypeId());
			TableElements state = tableElementsManager.find(inspection.getStateId());
			Schedules schedule = scheduleManager.find(inspection.getScheduleId());
			Larvicides larvicide = larvicideManager.find(inspection.getLarvicideId());
			Inspections updateInspection = inspectionManager.find(id);
			if(updateInspection != null){
				
				updateInspection.setAreas(area);
				if(inspection.getCoverage() != null){
					updateInspection.setCoverage(inspection.getCoverage());
				}
				if(inspection.getStartDate() != null){
					updateInspection.setStartDate(new Date(inspection.getStartDate()));
				}
				if(inspection.getFinishDate() != null){
					updateInspection.setFinishDate(new Date(inspection.getFinishDate()));
				}
				updateInspection.setInspectionSize(inspection.getInspectionSize());
				updateInspection.setTableElementsByTypeId(type);
				updateInspection.setTableElementsByStateId(state);
				updateInspection.setSchedules(schedule);
				updateInspection.setLarvicides(larvicide);
				updateInspection.setTrapLatitude(inspection.getTrapLatitude());
				updateInspection.setTrapLongitude(inspection.getTrapLongitude());
				updateInspection = inspectionManager.save(updateInspection);	
				if(updateInspection != null){
					return updateInspection;
				}else{
					return null;
				}				
			}else{
			    logger.error(RestErrorImpl.ERROR_GET_INSPECTION_ID);
				return null;
			}			
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_UPDATEINSPECTION + e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public List<Plan> getListPlans(Integer inspectionId) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
		PlansAreasDao planAreaManager = (PlansAreasDao) ctx.getBean("PlansAreasDaoImpl");
		List<Plan> planList = new ArrayList<Plan>();
		List<PlanAreas> listPlanAreas = new ArrayList<PlanAreas>();
		Plans entityPlan = new Plans();
	
		try{
			List<Plans> entityPlanList = planManager.getListPlans(inspectionId);

			for(int i = 0; i < entityPlanList.size(); i++){
				
				entityPlan = entityPlanList.get(i);				
				listPlanAreas = planAreaManager.getListPlanAreas(entityPlan);				
				Plan plan = new PlanInspectionListImpl(entityPlan.getId(),
												  entityPlan.getPlanSize(),
												  entityPlan.getDate().getTime(),
												  entityPlan.getUsers().getId(),
												  inspectionId,
												  entityPlan.getTableElements().getId(),
												  listPlanAreas,
												  entityPlan.getHouseInterval(),
												  entityPlan.getHouseIni());

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
	

	@Override
	public PlansInspectionList getListPlans(Session session, Integer inspectionId, ParamFilterPlans paramFilterPlans) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");			
		PlansInspectionList plansInspectionList = new PlansInspectionList();
		try{	
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");	
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			PlanInspection planInspection = planManager.getPlanInspection(inspectionId, paramFilterPlans, user);			
			plansInspectionList = new PlansInspectionList(planInspection.getCount(), planInspection.getList());
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLISTPLANS + e.toString());
			
		}finally{
			ctx.close();
		}		
		return plansInspectionList;		
	}


	@Override
	public VisitList getListVisit(Session session, Integer inspectionId, ParamFilterVisits paramFilterVisits) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		VisitDao visitManager = (VisitDao) ctx.getBean("VisitDaoImpl");
		UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
		VisitList visitList = new VisitList();
		try{		
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			visitList = visitManager.getList(user, paramFilterVisits, inspectionId);			
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETVISITLIST +e.toString());
		    visitList = null;
			
		}finally{
			ctx.close();
		}
		
		return visitList;	
	}

	@Override
	public boolean closeInspection(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Boolean closeStastus = false;

		try{
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			
			if(planManager.allPlansFinished(id, ConfigurationHelper.getStatePlanFinished())){
				TableElements elementFinished = tableElementsManager.find(ConfigurationHelper.getStateInspectionRealized());	
				Inspections inspection = inspectionManager.find(id);
				inspection.setTableElementsByStateId(elementFinished);
				inspectionManager.save(inspection);		

				//Se crea la alerta correspondiente
				AlertServiceImpl alertServiceImpl = new AlertServiceImpl(getConnector());					
				closeStastus = true;
			}
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_CLOSEINSPECTION +e.toString());
		}finally{
			ctx.close();
		}	
		return closeStastus;
	}

	@Override
	public String createXls(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");

		String excelPathName = "";
		
		try{
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");	
			PlanDao planManager = (PlanDao) ctx.getBean("PlanDaoImpl");			
			
			Inspections inspection = inspectionManager.find(id);
			
			String EESS = inspection.getAreas().getName();
			String MR = inspection.getAreas().getAreas().getName();			
			Date dateStart = inspection.getStartDate();
			Date dateFinish = inspection.getFinishDate();
			String start = (new SimpleDateFormat("dd/MM/yyyy")).format(dateStart);			
			String finish = (new SimpleDateFormat("dd/MM/yyyy")).format(dateFinish);


			List<Date> listDatesPlans = planManager.getListDatesPlans(inspection);				
			List<Users> listUsersPlans = planManager.getListUserPlans(inspection);			
			XSSFWorkbook libro = new XSSFWorkbook();			

			//FUENTE NEGRITA 25
			XSSFFont fontTitle=libro.createFont();
			fontTitle.setBold(true);
			fontTitle.setFontName("Calibri");
			fontTitle.setFontHeightInPoints((short) 15);	

			//FUENTE NEGRITA 12
			XSSFFont fontBoldMedium=libro.createFont();
			fontBoldMedium.setBold(true);
			fontBoldMedium.setFontName("Calibri");
			fontBoldMedium.setFontHeightInPoints((short) 12);	
			
			//FUENTE NORMAL
			XSSFFont fontNormal = libro.createFont();
			fontNormal.setFontName("Calibri");
			fontNormal.setFontHeightInPoints((short) 11);	

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
		  	
			/*STYLE NORMAL CENTER BORDER*/
		  	XSSFCellStyle styleNormalCenter = libro.createCellStyle();	
		  	styleNormalCenter.setFont(fontNormal);
		  	styleNormalCenter.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		  	styleNormalCenter.setBorderBottom(CellStyle.BORDER_THIN);
		  	styleNormalCenter.setBorderLeft(CellStyle.BORDER_THIN);
		  	styleNormalCenter.setBorderRight(CellStyle.BORDER_THIN);
		  	styleNormalCenter.setBorderTop(CellStyle.BORDER_THIN);
		  	
		  	/*STYLE NORMAL IZQUIERDA*/
		  	XSSFCellStyle styleNormalLeft = libro.createCellStyle();	
		  	styleNormalLeft.setFont(fontNormal);
		  	styleNormalLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		  	styleNormalLeft.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);	
		  	
		  	XSSFSheet sheet = libro.createSheet("Producción Inspectores");
			XSSFRow rowTitle = sheet.createRow(0);		
			
			//TITULO DEL EXCEL
		  	ExcelHelper.createCell(0, 1, 0, 3, 0, "PRODUCCIÓN DE INSPECTORES", sheet, rowTitle, styleTitle, 0);
		  	
			XSSFRow rowMR = sheet.createRow(2);
			ExcelHelper.createCell(2, 2, 1, 6, 1, "MR: "+MR, sheet, rowMR, styleNormalLeft, 0);
			
			XSSFRow rowEESS = sheet.createRow(3);
			ExcelHelper.createCell(3, 3, 1, 6, 1, "EESS: "+EESS, sheet, rowEESS, styleNormalLeft, 0);
			
			XSSFRow rowDateStar = sheet.createRow(4);
			ExcelHelper.createCell(4, 4, 1, 6, 1, "Fecha Inicio: "+start, sheet, rowDateStar, styleNormalLeft, 0);
			
			XSSFRow rowDateFinish = sheet.createRow(5);
			ExcelHelper.createCell(5, 5, 1, 6, 1, "Fecha Fin: "+finish, sheet, rowDateFinish, styleNormalLeft, 0);			
			XSSFRow rowCabecera = sheet.createRow(7);
			

			CellRangeAddress cellRange = ExcelHelper.createCell(7, 8, 0, 0, 0, "Nº", sheet, rowCabecera, styleBoldMedium, 0);			
			ExcelHelper.setBorder(CellStyle.BORDER_THIN, cellRange, sheet, libro);
			
			CellRangeAddress cellRangeName = ExcelHelper.createCell(7, 8, 1, 1, 1, "NOMBRE INSPECTOR", sheet, rowCabecera, styleBoldMedium, 0);			
			ExcelHelper.setBorder(CellStyle.BORDER_THIN, cellRangeName, sheet, libro);
			
			for(int j = 0; j< listDatesPlans.size(); j++){
				
				CellRangeAddress cellRangedate = ExcelHelper.createCell(7, 8, j+2, j+2, j+2, (new SimpleDateFormat("dd/MM/yyyy")).format(listDatesPlans.get(j)) , sheet, rowCabecera, styleBoldMedium, 3000);			
				ExcelHelper.setBorder(CellStyle.BORDER_THIN, cellRangedate, sheet, libro);
			}		
			
			int newRow= 8;			
			for(int j = 0; j< listUsersPlans.size(); j++){				
				newRow ++;
				XSSFRow row = sheet.createRow(newRow);
				ExcelHelper.createCell(newRow, newRow, 0, 0, 0, String.valueOf(j+1), sheet, row, styleNormalCenter, 0);
				ExcelHelper.createCell(newRow, newRow, 1, 1, 1, listUsersPlans.get(j).getName(), sheet, row, styleNormalCenter, 10000);
				
				for(int k = 0; k< listDatesPlans.size(); k++){					
					
					XSSFCell celda3 = row.createCell((short)k+2);
					XSSFRichTextString texto3 = new XSSFRichTextString(String.valueOf(inspectionManager.getNumInspection(inspection, listDatesPlans.get(k), listUsersPlans.get(j))));
					celda3.setCellValue(texto3);
					celda3.setCellStyle(styleNormalCenter);				
				}			
			}	
			String excelName = "ProduccionInspectores_"+Calendar.getInstance().getTimeInMillis()+".xlsx";
			excelPathName = ConfigurationHelper.getInspectorExcelPath()+"/"+excelName;
			try {
				FileOutputStream elFichero = new FileOutputStream(ConfigurationHelper.getInspectorExcelPath()+"/"+excelName);
				libro.write(elFichero);
				elFichero.close();
			} catch (Exception e) {
				e.printStackTrace();
			}	
			
		}catch(Exception e){
		    logger.error("ERROR::" +e.toString());
		
		}finally{
			ctx.close();			
		}
		
		return excelPathName;
	}

	@Override
	public List<InspectionBlock> getBlocks(Integer inspectionId) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<Areas> listAreas = new ArrayList<Areas>();
		List<InspectionBlock> list = new ArrayList<InspectionBlock>();
		try{	
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");		
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");
			Inspections entityInspection = inspectionManager.find(inspectionId);
			Areas entityArea = areaManager.find(entityInspection.getAreas().getId());			
			listAreas = areaManager.getListChildByType(entityArea, ConfigurationHelper.getAreaTypeBlock());	
			
			for(int i = 0; i<listAreas.size(); i++){				
				Areas areas = listAreas.get(i);	
				
				int numRequalify = inspectionManager.getNumRequalify(inspectionId, areas.getId());
				
				InspectionBlock inspectionBlock = new InspectionBlock(areas.getId(), areas.getCode(), areas.getName(), areas.getHouses(), areas.getCoords(), areas.getTableElementsByTypeId().getId(), areas.getLatitude(), areas.getLongitude(), areas.getAreas().getId(),numRequalify);				
				list.add(inspectionBlock);		
			}

		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_INSPECTION_GETBLOCKS +e.toString());
			return null;
		}finally{
			ctx.close();
		}
		return list;
	}

	@Override
	public String getSusbstitutesReport(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		String excelPathName = "";
		try{
			
			PlanDao planManager =  (PlanDao) ctx.getBean("PlanDaoImpl");	
			XSSFWorkbook libro = new XSSFWorkbook();			

			//FUENTE NEGRITA 25
			XSSFFont fontTitle=libro.createFont();
			fontTitle.setBold(true);
			fontTitle.setFontName("Calibri");
			fontTitle.setFontHeightInPoints((short) 18);	

			//FUENTE NEGRITA 12
			XSSFFont fontBoldMedium=libro.createFont();
			fontBoldMedium.setBold(true);
			fontBoldMedium.setFontName("Calibri");
			fontBoldMedium.setFontHeightInPoints((short) 12);	
			
			//FUENTE NORMAL
			XSSFFont fontNormal = libro.createFont();
			fontNormal.setFontName("Calibri");
			fontNormal.setFontHeightInPoints((short) 11);	

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
		  	styleBoldMedium.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		  	styleBoldMedium.setBorderBottom(CellStyle.BORDER_THIN);
		  	styleBoldMedium.setBorderLeft(CellStyle.BORDER_THIN);
		  	styleBoldMedium.setBorderRight(CellStyle.BORDER_THIN);
		  	styleBoldMedium.setBorderTop(CellStyle.BORDER_THIN);
		  	styleBoldMedium.setWrapText(true);
		  	
			/*STYLE NORMAL CENTER BORDER*/
		  	XSSFCellStyle styleNormalCenter = libro.createCellStyle();	
		  	styleNormalCenter.setFont(fontNormal);
		  	styleNormalCenter.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		  	styleNormalCenter.setBorderBottom(CellStyle.BORDER_THIN);
		  	styleNormalCenter.setBorderLeft(CellStyle.BORDER_THIN);
		  	styleNormalCenter.setBorderRight(CellStyle.BORDER_THIN);
		  	styleNormalCenter.setBorderTop(CellStyle.BORDER_THIN);
		  	
		  	/*STYLE NORMAL IZQUIERDA*/
		  	XSSFCellStyle styleNormalLeft = libro.createCellStyle();	
		  	styleNormalLeft.setFont(fontNormal);
		  	styleNormalLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		  	styleNormalLeft.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);	
		  	styleNormalLeft.setBorderBottom(CellStyle.BORDER_THIN);
		  	styleNormalLeft.setBorderLeft(CellStyle.BORDER_THIN);
		  	styleNormalLeft.setBorderRight(CellStyle.BORDER_THIN);
		  	styleNormalLeft.setBorderTop(CellStyle.BORDER_THIN);
		  	
		  	XSSFSheet sheet = libro.createSheet("Manzanas Suplentes");
			XSSFRow rowTitle = sheet.createRow(0);		
			
			//TITULO DEL EXCEL
		  	ExcelHelper.createCell(0, 1, 0, 4, 0, "LISTADO DE MANZANAS SUPLENTES", sheet, rowTitle, styleTitle, 0);
		
			XSSFRow rowCabecera = sheet.createRow(2);
			ExcelHelper.createCell(2, 2, 0, 0, 0, "FECHA", sheet, rowCabecera, styleBoldMedium, 4000);			
			ExcelHelper.createCell(2, 2, 1, 1, 1, "NOMBRE INSPECTOR", sheet, rowCabecera, styleBoldMedium, 9000);			
			ExcelHelper.createCell(2, 2, 2, 2, 2, "MANZANA", sheet, rowCabecera, styleBoldMedium, 9000);			
			ExcelHelper.createCell(2, 2, 3, 3, 3, "PIN", sheet, rowCabecera, styleBoldMedium, 0);			
			ExcelHelper.createCell(2, 2, 4, 4, 4, "EN VARIOS PLANES", sheet, rowCabecera, styleBoldMedium, 5000);			
		
			int newRow = 3;	
			
			List<Substitutes> list = planManager.getSubstitutes(id);
			
			for(int j = 0; j< list.size(); j++){	
				Substitutes substitutes = list.get(j);
				XSSFRow row = sheet.createRow(newRow);
				ExcelHelper.createCell(newRow, newRow, 0, 0, 0, substitutes.getDate(), sheet, row, styleNormalCenter, 0);
				ExcelHelper.createCell(newRow, newRow, 1, 1, 1, substitutes.getInspector(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(newRow, newRow, 2, 2, 2, substitutes.getArea(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(newRow, newRow, 3, 3, 3, substitutes.getPin().toString(), sheet, row, styleNormalCenter, 0);
				ExcelHelper.createCell(newRow, newRow, 4, 4, 4, substitutes.isAssigned() ? "Si" : "No", sheet, row, styleNormalCenter, 0);
				newRow ++;					
			}	
		
			String excelName = "Manzanas_Suplentes_("+id+")_"+Calendar.getInstance().getTimeInMillis()+".xlsx";
			excelPathName = ConfigurationHelper.getSubstitutesExcelPath()+"/"+excelName;
			try {
				FileOutputStream elFichero = new FileOutputStream(ConfigurationHelper.getSubstitutesExcelPath()+"/"+excelName);
				libro.write(elFichero);
				elFichero.close();
			} catch (Exception e) {
				e.printStackTrace();
			}		

		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETSUBSTITUTESREPORT +e.toString());
			return null;
		}finally{
			ctx.close();
		}
		return excelPathName;
	}

	@Override
	public List<SampleInspection> getListSamples(Integer inspectionId) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<SampleInspection> list = new ArrayList<SampleInspection>();
		
		try{	
			SampleDao sampleManager = (SampleDao) ctx.getBean("SampleDaoImpl");
			list = sampleManager.getListSampleInspection(inspectionId);

		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETSAMPLESINSPECTION +e.toString());
			return null;
		}finally{
			ctx.close();
		}
		return list;
	}

	@Override
	public String createSamplesXls(Session session, Integer inspectionId) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		String excelPathName = "";
		try{
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");
			AreaDao areaManager = (AreaDao)ctx.getBean("AreaDaoImpl");
			SampleDao sampleManager = (SampleDao)ctx.getBean("SampleDaoImpl");
			Inspections inspection = inspectionManager.find(inspectionId);
			Areas area = areaManager.find(inspection.getAreas().getId());
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			Users user = userManager.getUserByToken(session.getAuthorizationToken());

			String excelName = "Muestras_"+Calendar.getInstance().getTimeInMillis()+".xlsx";	
			
		
			String title = "RESULTADO DEL DIAGNOSTICO TAXONÓMICO LARVARIO DE Aedes aegypti, EN MUESTRAS PROCENTES DEL "+area.getName().toUpperCase()+ " - " +area.getAreas().getName().toUpperCase()+"  - PROVINCIA "+area.getAreas().getAreas().getName().toUpperCase();		
			
			XSSFWorkbook libro = new XSSFWorkbook();			

			//FUENTE NEGRITA 25
			XSSFFont fontTitle=libro.createFont();
			fontTitle.setBold(true);
			fontTitle.setFontName("Calibri");
			fontTitle.setFontHeightInPoints((short) 12);	

			//FUENTE NEGRITA 12
			XSSFFont fontBoldMedium=libro.createFont();
			fontBoldMedium.setBold(true);
			fontBoldMedium.setFontName("Calibri");
			fontBoldMedium.setFontHeightInPoints((short) 12);	
			
			//FUENTE NORMAL
			XSSFFont fontNormal = libro.createFont();
			fontNormal.setFontName("Calibri");
			fontNormal.setFontHeightInPoints((short) 11);	

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
		  	styleBoldMedium.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		  	styleBoldMedium.setBorderBottom(CellStyle.BORDER_THIN);
		  	styleBoldMedium.setBorderLeft(CellStyle.BORDER_THIN);
		  	styleBoldMedium.setBorderRight(CellStyle.BORDER_THIN);
		  	styleBoldMedium.setBorderTop(CellStyle.BORDER_THIN);
		  	styleBoldMedium.setWrapText(true);
		  	
			/*STYLE NORMAL CENTER BORDER*/
		  	XSSFCellStyle styleNormalCenter = libro.createCellStyle();	
		  	styleNormalCenter.setFont(fontNormal);
		  	styleNormalCenter.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		  	styleNormalCenter.setBorderBottom(CellStyle.BORDER_THIN);
		  	styleNormalCenter.setBorderLeft(CellStyle.BORDER_THIN);
		  	styleNormalCenter.setBorderRight(CellStyle.BORDER_THIN);
		  	styleNormalCenter.setBorderTop(CellStyle.BORDER_THIN);
		  	
		  	/*STYLE NORMAL IZQUIERDA*/
		  	XSSFCellStyle styleNormalLeft = libro.createCellStyle();	
		  	styleNormalLeft.setFont(fontNormal);
		  	styleNormalLeft.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		  	styleNormalLeft.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);	
		  	styleNormalLeft.setBorderBottom(CellStyle.BORDER_THIN);
		  	styleNormalLeft.setBorderLeft(CellStyle.BORDER_THIN);
		  	styleNormalLeft.setBorderRight(CellStyle.BORDER_THIN);
		  	styleNormalLeft.setBorderTop(CellStyle.BORDER_THIN);
		  	
		  	XSSFSheet sheet = libro.createSheet("Muestras");
			XSSFRow rowTitle = sheet.createRow(0);		
			
			//TITULO DEL EXCEL
		  	ExcelHelper.createCell(0, 3, 0, 7, 0, title, sheet, rowTitle, styleTitle, 0);
		
			XSSFRow rowCabecera = sheet.createRow(4);
			ExcelHelper.createCell(4, 4, 0, 0, 0, "FECHA", sheet, rowCabecera, styleBoldMedium, 4000);			
			ExcelHelper.createCell(4, 4, 1, 1, 1, "DIRECCIÓN", sheet, rowCabecera, styleBoldMedium, 9000);			
			ExcelHelper.createCell(4, 4, 2, 2, 2, "MANZANA", sheet, rowCabecera, styleBoldMedium, 5000);	
			ExcelHelper.createCell(4, 4, 3, 3, 3, "SECTOR", sheet, rowCabecera, styleBoldMedium, 5000);	
			ExcelHelper.createCell(4, 4, 4, 4, 4, "CÓDIGO MUESTRA", sheet, rowCabecera, styleBoldMedium, 5000);			
			ExcelHelper.createCell(4, 4, 5, 5, 5, "TIPO DE FOCO", sheet, rowCabecera, styleBoldMedium, 6000);	
			ExcelHelper.createCell(4, 4, 6, 6, 6, "ESTADO VECTOR", sheet, rowCabecera, styleBoldMedium, 5000);			
			ExcelHelper.createCell(4, 4, 7, 7, 7, "RESULTADO", sheet, rowCabecera, styleBoldMedium, 4000);			

			int newRow = 5;				
			List<SamplesFile> list = sampleManager.getSanplesInspectionFile(inspectionId);
			
			for(int j = 0; j< list.size(); j++){	
				SamplesFile samplesFile = list.get(j);
				XSSFRow row = sheet.createRow(newRow);
				ExcelHelper.createCell(newRow, newRow, 0, 0, 0, samplesFile.getDate(), sheet, row, styleNormalCenter, 0);
				ExcelHelper.createCell(newRow, newRow, 1, 1, 1, samplesFile.getAddress(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(newRow, newRow, 2, 2, 2, samplesFile.getMz(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(newRow, newRow, 3, 3, 3, samplesFile.getSector(), sheet, row, styleNormalCenter, 0);
				ExcelHelper.createCell(newRow, newRow, 4, 4, 4, samplesFile.getCode(), sheet, row, styleNormalCenter, 0);
				ExcelHelper.createCell(newRow, newRow, 5, 5, 5, samplesFile.getType(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(newRow, newRow, 6, 6, 6, sampleManager.getSanplesPhasesNames(samplesFile.getUuid(), user.getLanguages().getId()), sheet, row, styleNormalLeft, 0);				
				ExcelHelper.createCell(newRow, newRow, 7, 7, 7, samplesFile.getResult(), sheet, row, styleNormalCenter, 0);
				newRow ++;					
			}			
		
			excelPathName = ConfigurationHelper.getSamplesPath()+"/"+excelName;
			try {
				FileOutputStream elFichero = new FileOutputStream(ConfigurationHelper.getSamplesPath()+"/"+excelName);
				libro.write(elFichero);
				elFichero.close();
			} catch (Exception e) {
				e.printStackTrace();
			}		

		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_CREATEXLSSAMPLES +e.toString());
			return null;
		}finally{
			ctx.close();
		}
		return excelPathName;
	}
}
