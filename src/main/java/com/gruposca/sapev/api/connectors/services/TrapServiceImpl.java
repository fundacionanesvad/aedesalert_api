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
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.gruposca.sapev.api.connectors.dao.impl.AreaDao;
import com.gruposca.sapev.api.connectors.dao.impl.TableElementsDao;
import com.gruposca.sapev.api.connectors.dao.impl.TrapDao;
import com.gruposca.sapev.api.connectors.dao.impl.TrapDataDao;
import com.gruposca.sapev.api.connectors.dao.impl.TrapLocationDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.connectors.dao.model.TrapData;
import com.gruposca.sapev.api.connectors.dao.model.TrapLocations;
import com.gruposca.sapev.api.connectors.dao.model.Traps;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.helper.ExcelHelper;
import com.gruposca.sapev.api.modelview.FilterTrapData;
import com.gruposca.sapev.api.modelview.FilterTrapExcel;
import com.gruposca.sapev.api.modelview.ParamFilterTraps;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.TrapDataModel;
import com.gruposca.sapev.api.modelview.TrapDataModelList;
import com.gruposca.sapev.api.modelview.TrapLocationModel;
import com.gruposca.sapev.api.modelview.TrapLocationsModelList;
import com.gruposca.sapev.api.modelview.TrapModel;
import com.gruposca.sapev.api.modelview.TrapReportInfo;
import com.gruposca.sapev.api.modelview.TrapsList;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.TrapService;


public class TrapServiceImpl extends AbsService implements TrapService{

	public TrapServiceImpl(AbsConnector connector) { super( connector); }	

	@Override
	public TrapsList getListTraps(Session session, ParamFilterTraps paramFilterTraps) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		TrapsList trapsList = new TrapsList();
		try{
			TrapDao trapManager = (TrapDao) ctx.getBean("TrapDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");	
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			trapsList = trapManager.getListByAreaUser(user, paramFilterTraps);			
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLISTTRAPS + e.toString());
		    trapsList = null;
			
		}finally{
			ctx.close();
		}
		
		return trapsList;
	}

	@Override
	public TrapModel getTrap(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Traps entityTrap = new Traps();
		TrapModel trapModel = null;		
		try{			
			TrapDao trapManager = (TrapDao) ctx.getBean("TrapDaoImpl");
			entityTrap = trapManager.find(id);				
			if(entityTrap.getId() != null){				
				trapModel = new TrapModel(entityTrap.getNumber(), 
										  entityTrap.getCode(), 
										  entityTrap.getAreas().getId(), 
										  entityTrap.getAreas().getAreas().getId(),
										  entityTrap.getAreas().getAreas().getAreas().getId(), 
										  entityTrap.getAreas().getAreas().getAreas().getAreas().getId(), 
										  entityTrap.isEnabled());
			}
			
		}catch(Exception ex){
		    logger.error(RestErrorImpl.METHOD_GETTRAP +ex.toString());
		}finally{
			ctx.close();
		}
		return trapModel;
	}

	@Override
	public Traps createTrap(TrapModel trapModel) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{			
			TrapDao trapManager = (TrapDao) ctx.getBean("TrapDaoImpl");
			AreaDao areaManager = (AreaDao)ctx.getBean("AreaDaoImpl");
			Areas area = areaManager.find(trapModel.getEessId());		
			if(trapManager.existTrap(area.getId(), trapModel.getCode().trim())) {
				return null;
				
			}else {
				Traps traps = new Traps(area, trapModel.getCode(), trapModel.getNumber(), trapModel.getEnabled());
				traps = trapManager.save(traps);		
				return traps;
			}		
			
		}catch(Exception ex){
		    logger.error(RestErrorImpl.METHOD_CREATETRAP +ex.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}

	@Override
	public Traps updateTrap(Integer id, TrapModel trapModel) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{			
			TrapDao trapManager = (TrapDao) ctx.getBean("TrapDaoImpl");
			AreaDao areaManager = (AreaDao)ctx.getBean("AreaDaoImpl");
			
			Traps updateTrap = trapManager.find(id);
			if(updateTrap != null){
				Areas area = areaManager.find(trapModel.getEessId());
				updateTrap.setAreas(area);
				updateTrap.setCode(trapModel.getCode());
				updateTrap.setNumber(trapModel.getNumber());
				updateTrap.setEnabled(trapModel.getEnabled());		
				trapManager.save(updateTrap);
			}			
			return updateTrap;
		
		}catch(Exception ex){
		    logger.error(RestErrorImpl.METHOD_UPDATETRAP +ex.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}

	@Override
	public List<TrapLocationsModelList> getListTrapLocation(Integer trapId) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<TrapLocationsModelList> list = new ArrayList<TrapLocationsModelList>();
		try{
			TrapLocationDao trapLocationManager = (TrapLocationDao) ctx.getBean("TrapLocationDaoImpl");			
			list = trapLocationManager.getTrapLocationsByTrapId(trapId);			
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLISTTRAPSLOCATIONS + e.toString());
		    list = null;
			
		}finally{
			ctx.close();
		}		
		return list;
	}
	

	@Override
	public TrapLocationModel getTrapLocation(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		TrapLocations entityTrapLocation = new TrapLocations();
		TrapLocationModel trapLocationModel = null;		
		try{			
			TrapLocationDao trapLocationManager = (TrapLocationDao) ctx.getBean("TrapLocationDaoImpl");			
			entityTrapLocation = trapLocationManager.find(id);				
			if(entityTrapLocation.getId() != null){				
				trapLocationModel = new TrapLocationModel(entityTrapLocation.getDate().getTime(),
														  entityTrapLocation.getLatitude(),
														  entityTrapLocation.getLongitude(),
														  entityTrapLocation.getAltitude(), 
														  entityTrapLocation.getAddress(),
														  entityTrapLocation.getLocation());			
			}
			
		}catch(Exception ex){
		    logger.error(RestErrorImpl.METHOD_GETTRAPSLOCATION +ex.toString());
		}finally{
			ctx.close();
		}
		return trapLocationModel;
	}

	@Override
	public TrapLocations createTrapLocations(Integer trapId,TrapLocationModel trapLocationModel) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{			
			TrapLocationDao trapLocationManager = (TrapLocationDao) ctx.getBean("TrapLocationDaoImpl");			
			TrapDao trapManager = (TrapDao) ctx.getBean("TrapDaoImpl");
			Traps trap = trapManager.find(trapId);
			trapLocationManager.updateEnabledValues(trapId);
		
			TrapLocations trapLocation = new TrapLocations(trap, 
														   Calendar.getInstance().getTime(),
														   trapLocationModel.getLongitude(),
														   trapLocationModel.getLatitude(),
														   trapLocationModel.getAltitude(), 
														   trapLocationModel.getAddress(), 
														   trapLocationModel.getLocation(),
														   true);
			trapLocation = trapLocationManager.save(trapLocation);
			return trapLocation;			
			
		}catch(Exception ex){
		    logger.error(RestErrorImpl.METHOD_CREATETRAPSLOCATION +ex.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}

	@Override
	public TrapLocations updateTrapLocations(Integer id, TrapLocationModel trapLocationModel) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{	
			
			System.out.println("LATITUDE:"+trapLocationModel.getLatitude());
			System.out.println("LONGITUDE:"+trapLocationModel.getLongitude());

			
			TrapLocationDao trapLocationManager = (TrapLocationDao) ctx.getBean("TrapLocationDaoImpl");			
			TrapLocations updateTrapLocation = trapLocationManager.find(id);			
			if(updateTrapLocation != null){
				updateTrapLocation.setLatitude(trapLocationModel.getLatitude());
				updateTrapLocation.setLongitude(trapLocationModel.getLongitude());
				updateTrapLocation.setAltitude(trapLocationModel.getAltitude());
				updateTrapLocation.setAddress(trapLocationModel.getAddress());
				updateTrapLocation.setLocation(trapLocationModel.getLocation());
				trapLocationManager.save(updateTrapLocation);			
			}			
			return updateTrapLocation;
		
		}catch(Exception ex){
		    logger.error(RestErrorImpl.METHOD_UPDATETRAPSLOCATION +ex.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}

	@Override
	public List<TrapDataModelList> getTrapDataList(FilterTrapData filterTrapData) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{
			Calendar c = Calendar.getInstance();
		    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		    c.set(Calendar.WEEK_OF_YEAR, filterTrapData.getWeek());
		    c.set(Calendar.YEAR, filterTrapData.getYear());
	    
			TrapDataDao trapDataManager = (TrapDataDao) ctx.getBean("TrapDataDaoImpl");	
			List<TrapDataModelList> list = new ArrayList<TrapDataModelList>();
			
			String date = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DAY_OF_MONTH);
			list  = trapDataManager.getList(filterTrapData.getMicrorredId(),filterTrapData.getEessId(), date);					
			return list;
		
		}catch(Exception ex){
		    logger.error(RestErrorImpl.METHOD_GETLISTTRAPDATA +ex.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}

	@Override
	public int saveTrapData(TrapDataModel trapDataModel) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{
			Calendar c = Calendar.getInstance();
		    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		    c.set(Calendar.WEEK_OF_YEAR, trapDataModel.getWeek());
		    c.set(Calendar.YEAR, trapDataModel.getYear());   
		    
		    c.get(Calendar.DAY_OF_WEEK);
			TrapDataDao trapDataManager = (TrapDataDao) ctx.getBean("TrapDataDaoImpl");	
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");	
			TrapDao trapManager = (TrapDao) ctx.getBean("TrapDaoImpl");
			TrapLocationDao trapLocationManager = (TrapLocationDao) ctx.getBean("TrapLocationDaoImpl");	

			List<TrapDataModelList> list = trapDataModel.getData();
			for(int i = 0; i < list.size(); i++) {
				TrapDataModelList trapData = list.get(i);
				Traps traps = trapManager.find(trapData.getTrapId());
				TrapData entityTrapData = new TrapData();
				TableElements result = null;				
				TrapData trapDataExits = trapDataManager.getData(trapData.getTrapId(), new java.sql.Date(c.getTimeInMillis()), trapLocationManager.getTrapLocation(traps).getId());
					
				if(trapData.getTrapDataId() != null) {
					entityTrapData = trapDataManager.find(trapData.getTrapDataId());
				}else if (trapDataExits != null) {
					entityTrapData = trapDataExits;
				}else {
					entityTrapData.setTrapLocations(trapLocationManager.getTrapLocation(traps));	
					entityTrapData.setDate(c.getTime());
					entityTrapData.setTraps(traps);					
				}
				
				if(trapData.getResultId() != null) {
					result = tableElementsManager.find(trapData.getResultId());
				}

				entityTrapData.setEggs(trapData.getEggs());
				entityTrapData.setTableElements(result);				
				trapDataManager.save(entityTrapData);	
			}
		
		}catch(Exception ex){
		    logger.error(RestErrorImpl.METHOD_SAVETRAPDATA +ex.toString());
			return 0;
		}finally{
			ctx.close();
		}	
		return 1;
	}

	@Override
	public String reportTrap(FilterTrapExcel filterTrapExcel) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		String excelPathName = "";
		int numRowIdh = 0;
		XSSFRow rowIDH = null;
		XSSFRow rowTraps = null;
		int sheetsCreated = 0;
		try{			
			TrapDataDao trapDataManager = (TrapDataDao) ctx.getBean("TrapDataDaoImpl");	
			TrapDao trapManager = (TrapDao) ctx.getBean("TrapDaoImpl");
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");	
			
			List<Areas> listaAreas = areaManager.getListAreasMR(filterTrapExcel.getMicrorredId(), filterTrapExcel.getEessId());			
			XSSFWorkbook libro = new XSSFWorkbook();		

		  	XSSFCellStyle styleTitle = ExcelHelper.createStyle(libro, (short)25, true, XSSFCellStyle.ALIGN_CENTER , false,  (short)0, 0);  	
		  	XSSFCellStyle styleBoldMedium = ExcelHelper.createStyle(libro, (short)12, true, XSSFCellStyle.ALIGN_CENTER , true,  CellStyle.BORDER_THIN, 0);
		  	XSSFCellStyle styleNormalCenter = ExcelHelper.createStyle(libro, (short)11, false, XSSFCellStyle.ALIGN_CENTER , true,  CellStyle.BORDER_THIN, 0);
		  	XSSFCellStyle styleBoldCenter = ExcelHelper.createStyle(libro, (short)11, true, XSSFCellStyle.ALIGN_CENTER , true,  CellStyle.BORDER_THIN, 0);
		  	XSSFCellStyle styleNormalLeft= ExcelHelper.createStyle(libro, (short)11, false, XSSFCellStyle.ALIGN_LEFT , false, (short)0, 0);
		  	XSSFCellStyle styleNormalLeftBorder= ExcelHelper.createStyle(libro, (short)11, false, XSSFCellStyle.ALIGN_LEFT , true,  CellStyle.BORDER_THIN, 0);
		  	XSSFCellStyle styleBoldMediumBlue = ExcelHelper.createStyle(libro, (short)12, true, XSSFCellStyle.ALIGN_CENTER , true,  CellStyle.BORDER_THIN, 0);
		  	XSSFCellStyle styleBoldMediumDate = ExcelHelper.createStyle(libro, (short)12, true, XSSFCellStyle.ALIGN_CENTER , true,  CellStyle.BORDER_THIN, 2);
		  	XSSFCellStyle styleNormalDecimalCenter = ExcelHelper.createStyle(libro, (short)11, false, XSSFCellStyle.ALIGN_CENTER , true,  CellStyle.BORDER_THIN, 3);		  	
		  	XSSFCellStyle styleNormalYellowCenter = ExcelHelper.createStyle(libro, (short)11, false, XSSFCellStyle.ALIGN_CENTER , true,  CellStyle.BORDER_THIN, 0);
		  	XSSFCellStyle styleNormalTwoDecimalCenter = ExcelHelper.createStyle(libro, (short)11, false, XSSFCellStyle.ALIGN_CENTER , true,  CellStyle.BORDER_THIN, 4);		  	

		  	styleNormalYellowCenter.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		  	styleNormalYellowCenter.setFillPattern(CellStyle.SOLID_FOREGROUND); 
		  	
		  	styleBoldMedium.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		  	styleBoldMedium.setFillPattern(CellStyle.SOLID_FOREGROUND); 

		  	styleBoldMediumBlue.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		  	styleBoldMediumBlue.setFillPattern(CellStyle.SOLID_FOREGROUND); 
		  	  	
		  	Calendar c = Calendar.getInstance();
		    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		    c.set(Calendar.WEEK_OF_YEAR, 1);
		    c.set(Calendar.YEAR, filterTrapExcel.getYear());   	    
			String dateIni = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DAY_OF_MONTH);		
			
		    Calendar c2 = Calendar.getInstance();
		    c2.set(Calendar.YEAR, filterTrapExcel.getYear());	    
		    c2.set(Calendar.WEEK_OF_YEAR, c2.getActualMaximum(Calendar.WEEK_OF_YEAR));
		    c2.set(Calendar.MONTH, 11);  
		    c2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);	    
			String dateFin = c2.get(Calendar.YEAR) + "-" + (c2.get(Calendar.MONTH)+1) + "-" + c2.get(Calendar.DAY_OF_MONTH);	
		  	
		  	
		  	
		  	for(int i = 0; i < listaAreas.size(); i++) {
		  		
		  		Areas area = listaAreas.get(i);		  
		  		
		  		List<Date> listdates = trapDataManager.getListDates(area.getId(), dateIni, dateFin );		  		
			  	
		  		if(listdates.size() > 0) {		  			
	  			
		  			XSSFSheet sheet = libro.createSheet(area.getName());
		  			sheetsCreated ++;
					XSSFRow rowTitle = sheet.createRow(0);	
					
				  	ExcelHelper.createCell(0, 1, 0, 13, 0, "VIGILANCIA ENTOMOLÓGICA MEDIANTE OVITRAMPAS", sheet, rowTitle, styleTitle, 0);
					XSSFRow row = sheet.createRow(3);	

					String departamento = "DEPARTAMENTO: "+area.getAreas().getAreas().getAreas().getName();
					String provincia = "PROVINCIA: "+area.getAreas().getAreas().getName();
					String distrito = "DISTRITO: "+area.getAreas().getName();
					String locallidad = "LOCALIDAD: "+area.getName();
					
				  	ExcelHelper.createCell(3, 3, 1, 4, 1, departamento.toUpperCase(), sheet, row, styleNormalLeft, 0);
					ExcelHelper.createCell(3, 3, 5, 7, 5, provincia.toUpperCase(), sheet, row, styleNormalLeft, 0);			
					
					row = sheet.createRow(4);
					ExcelHelper.createCell(4, 4, 1, 4, 1, locallidad.toUpperCase(), sheet, row, styleNormalLeft, 0);
					ExcelHelper.createCell(4, 4, 5, 7, 5, distrito.toUpperCase(), sheet, row, styleNormalLeft, 0);			

					row = sheet.createRow(5);
					
					ExcelHelper.setBorder(CellStyle.BORDER_THIN, ExcelHelper.createCell(5, 5, 9, 8 + listdates.size(), 9, filterTrapExcel.getYear().toString(), sheet, row, styleNormalCenter, 0), sheet, libro);

					XSSFRow rowSE = sheet.createRow(6);
					ExcelHelper.createCell(6, 6, 8, 8 , 8, "SE", sheet, rowSE, styleBoldCenter, 0);
					
						
					XSSFRow rowNine = sheet.createRow(9);

					ExcelHelper.createCell(9, 9, 1, 1, 1, "Nº", sheet, rowNine, styleBoldMedium, 0);
					ExcelHelper.setBorder(CellStyle.BORDER_THIN,ExcelHelper.createCell(9, 9, 2, 3, 2, "Código", sheet, rowNine, styleBoldMedium, 0), sheet, libro);
					ExcelHelper.createCell(9, 9, 4, 4, 4, "latitud", sheet, rowNine, styleBoldMedium, 4000);
					ExcelHelper.createCell(9, 9, 5, 5, 5, "longitud", sheet, rowNine, styleBoldMedium, 4000);
					row = sheet.createRow(7);
					ExcelHelper.setBorder(CellStyle.BORDER_THIN,ExcelHelper.createCell(7, 8, 1, 3, 1, "Ovitrampas", sheet, row, styleBoldMedium, 0), sheet, libro);
					ExcelHelper.setBorder(CellStyle.BORDER_THIN,ExcelHelper.createCell(7, 8, 4, 5, 4, "Coordenadas", sheet, row, styleBoldMedium, 0), sheet, libro);
					ExcelHelper.setBorder(CellStyle.BORDER_THIN,ExcelHelper.createCell(7, 9, 6, 6, 6, "Altitud (m)", sheet, row, styleBoldMedium, 0), sheet, libro);
					ExcelHelper.setBorder(CellStyle.BORDER_THIN,ExcelHelper.createCell(7, 9, 7, 7, 7, "Dirección de la vivienda", sheet, row, styleBoldMedium, 6500), sheet, libro);
					ExcelHelper.setBorder(CellStyle.BORDER_THIN,ExcelHelper.createCell(7, 9, 8, 8, 8, "Ubicación de ovitrampas", sheet, row, styleBoldMedium, 5000), sheet, libro);
	  
					Calendar cal = Calendar.getInstance();
					int ini = 9;
					
					for( int j = 0; j < listdates.size(); j++){
						Date date = listdates.get(j);						
						cal.setTime(date);						
						ExcelHelper.createCell(6, 6, ini, ini , ini, String.valueOf(cal.get(Calendar.WEEK_OF_YEAR)), sheet, rowSE, styleNormalCenter, 0);
						ExcelHelper.createCellDate(9, 9, ini, ini, ini,date, sheet, rowNine, styleBoldMediumDate,0);

						ini = ini +1;
					}								

					ExcelHelper.setBorder(CellStyle.BORDER_THIN,ExcelHelper.createCell(7, 8, 9, 8 + listdates.size(), 9, "Fecha de inspección/N° huevos", sheet, row, styleBoldMedium, 0), sheet, libro);
		  		
					List<TrapReportInfo> trapsList = trapManager.getTrapsReport(area.getId(), dateIni, dateFin);
					
					rowTraps = sheet.createRow(10);
					int rowIni = 10;
					
					for (TrapReportInfo trapReport : trapsList) {				        	

						ExcelHelper.createCellInteger(rowTraps, (short)1,  trapReport.getNumber(), styleNormalCenter);
						ExcelHelper.setBorder(CellStyle.BORDER_THIN,ExcelHelper.createCell(rowIni, rowIni, 2, 3 , 2, trapReport.getCode(), sheet, rowTraps, styleNormalCenter, 0), sheet, libro);						;
						ExcelHelper.createCellDecimal(rowTraps, (short)4,trapReport.getLatitude(), styleNormalDecimalCenter);
						ExcelHelper.createCellDecimal(rowTraps, (short)5,trapReport.getLongitude(), styleNormalDecimalCenter);
						ExcelHelper.createCellDecimal(rowTraps, (short)6,trapReport.getAltitude(), styleNormalCenter);
						ExcelHelper.createCell(rowIni, rowIni, 7, 7 , 7, trapReport.getAddress(), sheet, rowTraps, styleNormalLeftBorder, 0);
						ExcelHelper.createCell(rowIni, rowIni, 8, 8 , 8, trapReport.getLocation(), sheet, rowTraps, styleNormalLeftBorder, 0);
						
						int k = 9;
						for (Date date : listdates) {	
							TrapData trapData = trapDataManager.getData(trapReport.getId(), date);							
							if(trapData==null) {
								ExcelHelper.createCell(rowIni, rowIni, k, k, k,"", sheet, rowTraps, styleNormalCenter, 0);

							}else if(trapData.getTableElements() != null) {
								String state = "";
								switch (trapData.getTableElements().getId()) {
							        case 11001: state = "P";   
							                	break;
							        case 11002: state = "SP";
						            			break; 
							        case 11003: state = "D";
					            		        break; 
							        case 11004: state = "CC";
					            				break; 
							        default: break;
								}
								ExcelHelper.createCell(rowIni, rowIni, k, k, k,state, sheet, rowTraps, styleNormalCenter, 0);
							}else if(trapData.getEggs() != null){
								ExcelHelper.createCellInteger(rowTraps, (short)k,  trapData.getEggs(), trapData.getEggs() > 0 ? styleNormalYellowCenter : styleNormalCenter);
			
							}else {
								ExcelHelper.createCell(rowIni, rowIni, k, k, k,"", sheet, rowTraps, styleNormalCenter, 0);
							}						
							k++;							
						}						
						rowIni++;
						rowTraps = sheet.createRow(rowIni);
					}
					
					
					ExcelHelper.setBorder(CellStyle.BORDER_THIN,ExcelHelper.createCell(rowIni, rowIni, 1, 8 , 1, "IPO", sheet, rowTraps, styleNormalCenter, 0), sheet, libro);						

					numRowIdh = rowIni +1;
					rowIDH = sheet.createRow(numRowIdh);					
					ExcelHelper.setBorder(CellStyle.BORDER_THIN,ExcelHelper.createCell(numRowIdh, numRowIdh, 1, 8 , 1, "IDH", sheet, rowIDH, styleNormalCenter, 0), sheet, libro);						

					int k = 9; 
					
					List<BigDecimal> listIPO = trapDataManager.getIPO(area.getId(), dateIni, dateFin);
					List<BigDecimal> listIDH = trapDataManager.getIDH(area.getId(), dateIni, dateFin);

					for (int n = 0; n < listdates.size() ; n++) {						
						ExcelHelper.createCellString(rowTraps , (short)k, (listIPO.get(n)+"%").replace(".", ","), styleNormalCenter);
						ExcelHelper.createCellDecimal(rowIDH , (short)k, listIDH.get(n), styleNormalTwoDecimalCenter);
						k++;
					}		
					
					rowIni = numRowIdh + 3;
					XSSFRow rowLeyend = sheet.createRow(rowIni);					
					ExcelHelper.setBorder(CellStyle.BORDER_THIN,ExcelHelper.createCell(rowIni, rowIni, 9, 10, 9, "LEYENDA", sheet, rowLeyend, styleBoldMediumBlue, 0), sheet, libro);
					rowIni++;
					rowLeyend = sheet.createRow(rowIni);
					ExcelHelper.createCell(rowIni, rowIni, 9, 10, 9, "P: Perdida", sheet, rowLeyend, styleNormalLeft, 0);
					rowIni++;
					rowLeyend = sheet.createRow(rowIni);
					ExcelHelper.createCell(rowIni, rowIni, 9, 10, 9, "SP: Sin Papel", sheet, rowLeyend, styleNormalLeft, 0);
					rowIni++;
					rowLeyend = sheet.createRow(rowIni);
					ExcelHelper.createCell(rowIni, rowIni,9, 10, 9, "D: Destruida", sheet, rowLeyend, styleNormalLeft, 0);
					rowIni++;
					rowLeyend = sheet.createRow(rowIni);
					ExcelHelper.createCell(rowIni, rowIni, 9, 10, 9, "CC: Casa Cerrada", sheet, rowLeyend, styleNormalLeft, 0);
			  	}
		  	} 
		  	
		  	if(sheetsCreated == 0) {
	  			XSSFSheet sheet = libro.createSheet("Hoja1");
		  	}

			String excelName = "Ovitrampas_"+Calendar.getInstance().getTimeInMillis()+".xlsx";
			excelPathName = ConfigurationHelper.getTrapsReportPath()+"/"+excelName;
			try {
				FileOutputStream elFichero = new FileOutputStream(ConfigurationHelper.getTrapsReportPath()+"/"+excelName);
				libro.write(elFichero);
				elFichero.close();
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
		}catch(Exception e){
			  logger.error(RestErrorImpl.METHOD_REPORT_TRAP +e.toString());		
		}finally{
			ctx.close();			
		}		
		return excelPathName;
		
	}


}
