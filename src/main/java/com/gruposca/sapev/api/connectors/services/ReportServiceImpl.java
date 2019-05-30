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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.gruposca.sapev.api.connectors.dao.impl.AlertDao;
import com.gruposca.sapev.api.connectors.dao.impl.AreaDao;
import com.gruposca.sapev.api.connectors.dao.impl.InspectionDao;
import com.gruposca.sapev.api.connectors.dao.impl.LabelDao;
import com.gruposca.sapev.api.connectors.dao.impl.LanguageDao;
import com.gruposca.sapev.api.connectors.dao.impl.PlanDao;
import com.gruposca.sapev.api.connectors.dao.impl.PlansAreasDao;
import com.gruposca.sapev.api.connectors.dao.impl.ReportDao;
import com.gruposca.sapev.api.connectors.dao.impl.ReportInspectionDao;
import com.gruposca.sapev.api.connectors.dao.impl.SampleDao;
import com.gruposca.sapev.api.connectors.dao.impl.ScheduleDao;
import com.gruposca.sapev.api.connectors.dao.impl.TableElementsDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Inspections;
import com.gruposca.sapev.api.connectors.dao.model.Plans;
import com.gruposca.sapev.api.connectors.dao.model.ReportInspections;
import com.gruposca.sapev.api.connectors.dao.model.Reports;
import com.gruposca.sapev.api.connectors.dao.model.Schedules;
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.helper.CalendarHelper;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.helper.ExcelHelper;
import com.gruposca.sapev.api.modelview.CoordsArea;
import com.gruposca.sapev.api.modelview.InspectionListImpl;
import com.gruposca.sapev.api.modelview.InspectionSchedule;
import com.gruposca.sapev.api.modelview.ParamFilterReports;
import com.gruposca.sapev.api.modelview.Report;
import com.gruposca.sapev.api.modelview.ReportExcel;
import com.gruposca.sapev.api.modelview.ReportInspection;
import com.gruposca.sapev.api.modelview.ReportList;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Schedule;
import com.gruposca.sapev.api.modelview.ScheduleDay;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.ReportService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class ReportServiceImpl extends AbsService implements ReportService{

	public ReportServiceImpl(AbsConnector connector) { super( connector); }

	@Override
	public ReportList getReportList(Session session, ParamFilterReports paramFilterReports) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		ReportList reportList = new ReportList();
		try{			
			ReportDao reportManager = (ReportDao) ctx.getBean("ReportDaoImpl");	
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			Users user = userManager.getUserByToken(session.getAuthorizationToken());	
			reportList = reportManager.getListByAreaUser(user, paramFilterReports);
			
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETREPORTSLIST +e.toString());
		    reportList = null;
			
		}finally{
			ctx.close();
		}		
		return reportList;
	}

	@Override
	public Report getReport(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Reports entityReport = new Reports();
		Report report = new Report();
		try{
			
			ReportDao reportManager = (ReportDao) ctx.getBean("ReportDaoImpl");	
			ReportInspectionDao reportInspectionManager = (ReportInspectionDao) ctx.getBean("ReportInspectionDaoImpl");		

			entityReport= reportManager.find(id);	
			List<Integer> list = reportInspectionManager.listInspectionsId(entityReport);			
			
			report = new Report(entityReport.getAreas().getId(), 
					 entityReport.getDate().getTime(),
					 entityReport.getName(),
					 entityReport.getStartDate().getTime(),
					 entityReport.getFinishDate().getTime(),
					 entityReport.getDataType(),
					 entityReport.getTableElements().getId(), 
					 entityReport.getUsers().getId(), 
					 list);
	
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETREPORT +e.toString());
		    report = null;
			
		}finally{
			ctx.close();
		}		
		return report;
	}

	@Override
	public Reports createReport(Session session, Report report) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Reports entityReport;		
		try{
			ReportDao reportManager = (ReportDao) ctx.getBean("ReportDaoImpl");
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			TableElementsDao tableElementManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");
			ReportInspectionDao reportInspectionManager = (ReportInspectionDao) ctx.getBean("ReportInspectionDaoImpl");

			Areas area = areaManager.find(report.getAreaId());
			TableElements tableElements = tableElementManager.find(report.getDetailLevel());
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			entityReport = new Reports(area, tableElements, user, new Date(report.getDate()), report.getName().trim(), new Date(report.getStartDate()), new Date(report.getFinishDate()), report.getDataType());
			entityReport = reportManager.save(entityReport);		
			
			
			for(int i = 0; i < report.getInspections().size(); i++ ) {		
				Inspections inspections = inspectionManager.find(report.getInspections().get(i));				
				ReportInspections reportInspections = new ReportInspections(inspections, entityReport);				
				reportInspections = reportInspectionManager.save(reportInspections);
			}
			
			if(entityReport != null){
				return entityReport;
			}else{
				return null;
			}					
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_CREATEREPORT + e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public Reports updateReport(Integer id, Report report) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");

		try{		
			ReportDao reportManager = (ReportDao) ctx.getBean("ReportDaoImpl");
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			TableElementsDao tableElementManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");
			ReportInspectionDao reportInspectionManager = (ReportInspectionDao) ctx.getBean("ReportInspectionDaoImpl");
			
			Reports updateReport = reportManager.find(id);
			Areas area = areaManager.find(report.getAreaId());
			TableElements tableElements = tableElementManager.find(report.getDetailLevel());

			updateReport.setAreas(area);
			updateReport.setDate(new Date(report.getDate()));
			updateReport.setDataType(report.getDataType());
			updateReport.setFinishDate(new Date(report.getFinishDate()));
			updateReport.setName(report.getName().trim());
			updateReport.setStartDate(new Date(report.getStartDate()));
			updateReport.setTableElements(tableElements);
			updateReport = reportManager.save(updateReport);	
			
			reportInspectionManager.delete(updateReport);
			
			for(int i = 0; i < report.getInspections().size(); i++ ) {				
				Inspections inspections = inspectionManager.find(report.getInspections().get(i));				
				ReportInspections reportInspections = new ReportInspections(inspections, updateReport);				
				reportInspections = reportInspectionManager.save(reportInspections);
			}	
			
			return updateReport;					
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_UPDATEREPORT + e.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}

	@Override
	public boolean deleteReport(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		boolean result = false;
		
		try{	
			ReportDao reportManager = (ReportDao) ctx.getBean("ReportDaoImpl");
			AlertDao alertManager = (AlertDao) ctx.getBean("AlertDaoImpl");
			Reports report = reportManager.find(id);
			reportManager.delete(report);
			//Borrar alertas si existen
			String link = String.format(ConfigurationHelper.getAlertReportLink(),id);
			alertManager.deleteAlert(link);
			result = true;			
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_DELETEREPORT + e.toString());
		}finally{
			ctx.close();
		}	
		return result;
	}
		
	@Override
	public String createPdf(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");

		String pdfPathName = "";
		try{

			SampleDao sampleManager = (SampleDao) ctx.getBean("SampleDaoImpl");	
			ReportDao reportManager = (ReportDao) ctx.getBean("ReportDaoImpl");	
			Reports reportModel = reportManager.find(id);		
			String pdfFileName = "Report_"+Calendar.getInstance().getTimeInMillis()+".pdf";			
			String jrxmlFileName = ConfigurationHelper.getReportJasper();
			pdfPathName = ConfigurationHelper.getReportPath()+pdfFileName;		  
			String reportImage = ConfigurationHelper.getReportImage();			
			//Integer typeInspection = reportManager.getTypeInspection(id);
			Integer typeInspection = 0;

			String distrito = "";
			String EESS = "";
			String sector = "";

			if (reportModel.getAreas().getTableElementsByTypeId().getId() == 9006) {				
				sector = reportModel.getAreas().getName();
				EESS = reportModel.getAreas().getAreas().getName();
				distrito = reportModel.getAreas().getAreas().getAreas().getName();	
				jrxmlFileName = ConfigurationHelper.getReportJasperSec();

			}else if (reportModel.getAreas().getTableElementsByTypeId().getId() == 9005) {
				
				EESS = reportModel.getAreas().getName();
				distrito = reportModel.getAreas().getAreas().getName();				

			}else if (reportModel.getAreas().getTableElementsByTypeId().getId() == 9004) {				
				distrito = reportModel.getAreas().getName();			
			}				
			
			Map<String, Object> parameters = new HashMap<String, Object>();			
			
			File f = new File(reportImage);
			if(f.exists()){				
				parameters.put("image", reportImage);	
			}			
			parameters.put("idReport", id);	
			parameters.put("totalSamples", sampleManager.getTotalSamples(id));
			parameters.put("week", reportManager.getWeek(id));
			parameters.put("date", new SimpleDateFormat("dd/MM/yyyy").format(new Date(reportModel.getDate().getTime())));				
			parameters.put("distrito", distrito);	
			parameters.put("EESS", EESS);				
			parameters.put("sector", sector);	
			parameters.put("tipoInspeccion", typeInspection);	
			
			BasicDataSource dataSource = (BasicDataSource) ctx.getBean("dataSource");
            Connection conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());	            
			JasperReport report = JasperCompileManager.compileReport(jrxmlFileName);
			JasperPrint print = JasperFillManager.fillReport(report, parameters, conn);

			// Exporta el informe a PDF
			JasperExportManager.exportReportToPdfFile(print,pdfPathName);			
			
		}catch(Exception e){
		    logger.error("ERROR::" +e.toString());
		
	}finally{
		ctx.close();
		
	}
		return pdfPathName;		
	}

	@Override
	public String createXls(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<ReportExcel> list = new ArrayList<ReportExcel>();
		BigDecimal totalFocos = BigDecimal.ZERO;
		BigDecimal totalInspected = BigDecimal.ZERO;
		BigDecimal totalClosed = BigDecimal.ZERO;
		BigDecimal totalRenuentes = BigDecimal.ZERO;
		BigDecimal totalTreated = BigDecimal.ZERO;
		BigDecimal totalAbandonadas = BigDecimal.ZERO;
		BigDecimal totalPrograming = BigDecimal.ZERO;		
		BigDecimal totalDepositoFoco = BigDecimal.ZERO;
		BigDecimal totalDepositoInspected = BigDecimal.ZERO;
		BigDecimal totalDepositoTreated = BigDecimal.ZERO;
		BigDecimal totalDepositoDestroyed = BigDecimal.ZERO;
		BigDecimal totalLarvicide = BigDecimal.ZERO;
		BigDecimal iaTotal = BigDecimal.ZERO;
		BigDecimal irTotal = BigDecimal.ZERO;
		BigDecimal ibTotal = BigDecimal.ZERO;
		BigDecimal coberturaInspection = BigDecimal.ZERO;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");		
		Date startDateIni = null;
		Date finishDateIni = null;
		Date startDate = null;
		Date finishDate = null;
		
		String date = "";
		String region = "";
		String provincia = "";
		String distrito = "";
		String EESS = "";
		String sector = "";
		String excelPathName = "";
		
		try{
			
			ReportDao reportManager = (ReportDao) ctx.getBean("ReportDaoImpl");	
			SampleDao sampleManager = (SampleDao) ctx.getBean("SampleDaoImpl");	
			Reports reportModel = reportManager.find(id);		
			LabelDao labelManager =  (LabelDao) ctx.getBean("LabelDaoImpl");	
			LanguageDao languageManager = (LanguageDao) ctx.getBean("LanguageDaoImpl");	
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");

			String detailLevelName = labelManager.getValueElement(languageManager.find(1), reportModel.getTableElements());			
			
			if (reportModel.getAreas().getTableElementsByTypeId().getId() == 9006) {				
				sector = reportModel.getAreas().getName();
				EESS = reportModel.getAreas().getAreas().getName();
				distrito = reportModel.getAreas().getAreas().getAreas().getName();	
				provincia = reportModel.getAreas().getAreas().getAreas().getAreas().getName();
				region = reportModel.getAreas().getAreas().getAreas().getAreas().getAreas().getName();
			}else if (reportModel.getAreas().getTableElementsByTypeId().getId() == 9005) {				
				EESS = reportModel.getAreas().getName();
				distrito = reportModel.getAreas().getAreas().getName();	
				provincia = reportModel.getAreas().getAreas().getAreas().getName();
				region = reportModel.getAreas().getAreas().getAreas().getAreas().getName();
			}else if (reportModel.getAreas().getTableElementsByTypeId().getId() == 9004) {				
				distrito = reportModel.getAreas().getName();	
				provincia = reportModel.getAreas().getAreas().getName();
				region = reportModel.getAreas().getAreas().getAreas().getName();				
			}else if (reportModel.getAreas().getTableElementsByTypeId().getId() == 9003) {				
				provincia = reportModel.getAreas().getName();
				region = reportModel.getAreas().getAreas().getName();	
			}else if (reportModel.getAreas().getTableElementsByTypeId().getId() == 9002) {				
				region = reportModel.getAreas().getName();	
			}	
			
			//Número de columnas extra
			int areaType = reportModel.getAreas().getTableElementsByTypeId().getId();
			int detailLevel = reportModel.getTableElements().getId();			
			//Número adicional de columnas que hay que añadir
			int k = detailLevel - areaType - 1;
	
			XSSFWorkbook libro = new XSSFWorkbook();			
			XSSFDataFormat df = libro.createDataFormat();
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
		  	XSSFCellStyle styleTitle = ExcelHelper.createStyle(libro, (short)25, true, XSSFCellStyle.ALIGN_CENTER , false,  (short)0, 0);  	

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

		  	/*STYLE NORMAL CENTER BORDER TOP*/
		  	XSSFCellStyle styleNormalBorderThin = libro.createCellStyle();	
		  	styleNormalBorderThin.setFont(fontNormal);
		  	styleNormalBorderThin.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		  	styleNormalBorderThin.setBorderTop(CellStyle.BORDER_THIN);
		  	styleNormalBorderThin.setBorderBottom(CellStyle.BORDER_THIN);
		  	styleNormalBorderThin.setBorderLeft(CellStyle.BORDER_THIN);
		  	styleNormalBorderThin.setBorderRight(CellStyle.BORDER_THIN);	
		  	
		  	/*STYLE NORMAL CENTER BORDER TOP*/
		  	XSSFCellStyle styleBoldBorderThin = libro.createCellStyle();	
		  	styleBoldBorderThin.setFont(fontBoldMedium);
		  	styleBoldBorderThin.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		  	styleBoldBorderThin.setBorderTop(CellStyle.BORDER_THIN);
		  	styleBoldBorderThin.setBorderBottom(CellStyle.BORDER_THIN);
		  	styleBoldBorderThin.setBorderLeft(CellStyle.BORDER_THIN);
		  	styleBoldBorderThin.setBorderRight(CellStyle.BORDER_THIN);	
		  	
		  	XSSFSheet sheet = libro.createSheet("Consolidado");
			XSSFRow rowTitle = sheet.createRow(1);
			XSSFRow rowCellsBig = sheet.createRow(8);
			XSSFRow rowNumRecipientes = sheet.createRow(10);
			XSSFRow rowHouses = sheet.createRow(11);
			XSSFRow rowNumInfected = sheet.createRow(13);
			
			Integer week = reportManager.getWeek(id);			
			
			//TITULO DEL EXCEL
		  	ExcelHelper.createCell(1, 2, 7, 39, 7, "CONSOLIDADO DE REGISTRO DE INSPECCIÓN DE VIGILANCIA Y CONTROL DE AEDES AEGYPTI", sheet, rowTitle, styleTitle, 0);		  	
		  	
			ArrayList<CellRangeAddress> headList = new ArrayList<>();
		  	
		  	//FECHA		
			headList.add(ExcelHelper.createCell(8, 13, 0, 0, 0, "FECHA", sheet, rowCellsBig, styleBoldMedium, 3000));

			//COLUMNAS VARIABLES			
			int tbId = reportModel.getTableElements().getId()-1;
			for(int w = k ; w >= 1 ; w--) {
				headList.add(ExcelHelper.createCell(8, 13, w, w, w, (labelManager.getValueByElementId(languageManager.find(1), tbId)).toUpperCase(), sheet, rowCellsBig, styleBoldMedium, 6500));
				tbId--;
			}
			//DISTRITO Y SECTOR
			
			headList.add(ExcelHelper.createCell(8, 13, 1+k, 1+k, 1+k, detailLevelName.toUpperCase(), sheet, rowCellsBig, styleBoldMedium, 6500));
			
			//MZ
			headList.add(ExcelHelper.createCell(8, 13, 2+k, 2+k, 2+k, "MZ TRAB", sheet, rowCellsBig, styleBoldMediumVertical, 2000));
			
			//RESID. PRTEG
			headList.add(ExcelHelper.createCell(8, 13, 3+k, 3+k, 3+k, "RESID. PRTEG", sheet, rowCellsBig, styleBoldMediumVertical, 2000));
			
			//CASAS
			headList.add(ExcelHelper.createCell(8, 10, 4+k, 9+k, 4+k, "CASAS", sheet, rowCellsBig, styleBoldMedium, 0));			
			//CASA_I
			headList.add(ExcelHelper.createCell(11, 13, 4+k, 4+k, 4+k, "I", sheet, rowHouses, styleBoldMedium, 1250));					
			//CASA_F
			headList.add(ExcelHelper.createCell(11, 13, 5+k, 5+k, 5+k, "F", sheet, rowHouses, styleBoldMedium, 1250));
			//CASA_C
			headList.add(ExcelHelper.createCell(11, 13, 6+k, 6+k, 6+k, "C", sheet, rowHouses, styleBoldMedium, 1250));
			//CASA_A
			headList.add(ExcelHelper.createCell(11, 13, 7+k, 7+k, 7+k, "A", sheet, rowHouses, styleBoldMedium, 1250));
			//CASA_R
			headList.add(ExcelHelper.createCell(11, 13, 8+k, 8+k, 8+k, "R", sheet, rowHouses, styleBoldMedium, 1250));
			//CASA_TD
			headList.add(ExcelHelper.createCell(11, 13, 9+k, 9+k, 9+k, "T/D", sheet, rowHouses, styleBoldMedium, 1250));
			//CASA_T
			//headList.add(ExcelHelper.createCell(11, 13, 10+k, 10+k, 10+k, "T", sheet, rowHouses, styleBoldMedium, 1250));				
				
			//TOTAL DEPOSITOS
			headList.add(ExcelHelper.createCell(8, 10, 10+k, 12+k, 10+k, "TOTAL DEPÓSITOS", sheet, rowCellsBig, styleBoldMedium, 0));
			//DEPOSITO_I
			headList.add( ExcelHelper.createCell(11, 13, 10+k, 10+k, 10+k, "I", sheet, rowHouses, styleBoldMedium, 1500));					
			//DEPOSITO_F
			headList.add(ExcelHelper.createCell(11, 13, 11+k, 11+k, 11+k, "F", sheet, rowHouses, styleBoldMedium, 1500));					
			//DEPOSITO_TD
			headList.add(ExcelHelper.createCell(11, 13, 12+k, 12+k, 12+k, "T/D", sheet, rowHouses, styleBoldMedium, 1500));				
			
			//ABATE
			headList.add(ExcelHelper.createCell(8, 13, 13+k, 13+k, 13+k, "CONSUMO LARVICIDA", sheet, rowCellsBig, styleBoldMediumVertical, 0));

			//TIPOS RECIPIENTES
			headList.add(ExcelHelper.createCell(8, 9, 14+k, 43+k, 14+k, "TIPOS DE RECIPIENTES INFESTADOS POR Aedes aegypti", sheet, rowCellsBig, styleBoldMedium, 0));
			
			//TIPOS RECIPIENTES_NUMBERS
			headList.add(ExcelHelper.createCell(10, 10, 14+k, 16+k, 14+k, "1", sheet, rowNumRecipientes, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(10, 10, 17+k, 19+k, 17+k, "2", sheet, rowNumRecipientes, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(10, 10, 20+k, 22+k, 20+k, "3", sheet, rowNumRecipientes, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(10, 10, 23+k, 25+k, 23+k, "4", sheet, rowNumRecipientes, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(10, 10, 26+k, 28+k, 26+k, "5", sheet, rowNumRecipientes, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(10, 10, 29+k, 31+k, 29+k, "6", sheet, rowNumRecipientes, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(10, 10, 32+k, 34+k, 32+k, "7", sheet, rowNumRecipientes, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(10, 10, 35+k, 37+k, 35+k, "8", sheet, rowNumRecipientes, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(10, 10, 38+k, 40+k, 38+k, "9", sheet, rowNumRecipientes, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(10, 10, 41+k, 43+k, 41+k, "10", sheet, rowNumRecipientes, styleBoldMedium, 0));

			//TIPOS RECIPIENTES_NAMES
			headList.add(ExcelHelper.createCell(11, 12, 14+k, 16+k, 14+k, "Tanque elevado", sheet, rowHouses, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(11, 12, 17+k, 19+k, 17+k, "Tanque Bajo", sheet, rowHouses, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(11, 12, 20+k, 22+k, 20+k, "Canaletas", sheet, rowHouses, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(11, 12, 23+k, 25+k, 23+k, "Barriles, Toneles", sheet, rowHouses, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(11, 12, 26+k, 28+k, 26+k, "Cántaro de Barro", sheet, rowHouses, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(11, 12, 29+k, 31+k, 29+k, "LLantas", sheet, rowHouses, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(11, 12, 32+k, 34+k, 32+k, "Floreros", sheet, rowHouses, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(11, 12, 35+k, 37+k, 35+k, "Tinas, Baldes, Bateas", sheet, rowHouses, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(11, 12, 38+k, 40+k, 38+k, "Ollas", sheet, rowHouses, styleBoldMedium, 0));
			headList.add(ExcelHelper.createCell(11, 12, 41+k, 43+k, 41+k, "Otros", sheet, rowHouses, styleBoldMedium, 0));
				
			//TIPOS RECIPIENTES_NUMBER_INFECTED
			headList.add( ExcelHelper.createCell(13, 13, 14+k, 14+k, 14+k, "I", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 15+k, 15+k, 15+k, "F", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 16+k, 16+k, 16+k, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250));
			
			headList.add(ExcelHelper.createCell(13, 13, 17+k, 17+k, 17+k, "I", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 18+k, 18+k, 18+k, "F", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 19+k, 19+k, 19+k, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250));
			
			headList.add(ExcelHelper.createCell(13, 13, 20+k, 20+k, 20+k, "I", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 21+k, 21+k, 21+k, "F", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 22+k, 22+k, 22+k, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250));
			
			headList.add(ExcelHelper.createCell(13, 13, 23+k, 23+k, 23+k, "I", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 24+k, 24+k, 24+k, "F", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 25+k, 25+k, 25+k, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250));
			
			headList.add(ExcelHelper.createCell(13, 13, 26+k, 26+k, 26+k, "I", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 27+k, 27+k, 27+k, "F", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 28+k, 28+k, 28+k, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250));
			
			headList.add(ExcelHelper.createCell(13, 13, 29+k, 29+k, 29+k, "I", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 30+k, 30+k, 30+k, "F", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 31+k, 31+k, 31+k, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250));
			
			headList.add(ExcelHelper.createCell(13, 13, 32+k, 32+k, 32+k, "I", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 33+k, 33+k, 33+k, "F", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 34+k, 34+k, 34+k, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250));
			
			headList.add(ExcelHelper.createCell(13, 13, 35+k, 35+k, 35+k, "I", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 36+k, 36+k, 36+k, "F", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 37+k, 37+k, 37+k, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250));
			
			headList.add(ExcelHelper.createCell(13, 13, 38+k, 38+k, 38+k, "I", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 39+k, 39+k, 39+k, "F", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 40+k, 40+k, 40+k, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250));
			
			headList.add(ExcelHelper.createCell(13, 13, 41+k, 41+k, 41+k, "I", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 42+k, 42+k, 42+k, "F", sheet, rowNumInfected, styleBoldMedium, 1250));
			headList.add(ExcelHelper.createCell(13, 13, 43+k, 43+k, 43+k, "T/D", sheet, rowNumInfected, styleBoldMedium, 1250));			
			
			//TOTAL DESTRUIDO
			headList.add(ExcelHelper.createCell(8, 13, 44+k, 44+k, 44+k, "TOTAL REC. DESTRUIDOS", sheet, rowCellsBig, styleBoldMediumVertical, 2000));
			System.out.println("##"+reportModel.getDataType());
			//INDICE AEDICO
			if(reportModel.getDataType() ==4){
			//IA**#
			headList.add(ExcelHelper.createCell(8, 13, 45+k, 45+k, 45+k, "I.A.", sheet, rowCellsBig, styleBoldMedium, 2000));
					
			//IR**#
			headList.add(ExcelHelper.createCell(8, 13, 46+k, 46+k, 46+k, "I.R.", sheet, rowCellsBig, styleBoldMedium, 2000));
					
			//IB**#
			headList.add(ExcelHelper.createCell(8, 13, 47+k, 47+k, 47+k, "I.B.", sheet, rowCellsBig, styleBoldMedium, 2000));
			}
			//COBERTURA
			else{
					headList.add(ExcelHelper.createCell(8, 13, 45+k, 45+k, 45+k, "COBERTURA", sheet, rowCellsBig, styleBoldMediumVertical, 2000));
			}
			//BORDER IN HEAD
			for(int j = 0; j < headList.size(); j++) {				
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, headList.get(j), sheet, libro);				
			}
			
			list = reportManager.getListExcel(reportModel.getId(),reportModel.getTableElements().getId());
			System.out.println(list.size());
			XSSFRow row;
			int numberRow = 14;
			
			if(list.size() > 0){
				
				startDateIni = (Date) list.get(0).getStartDate();
				finishDateIni = (Date) list.get(0).getFinishDate();	
				
				for(int j = 0; j < list.size(); j++){
					ReportExcel reportExcel = list.get(j);	

					startDate = (Date) reportExcel.getStartDate();
					finishDate = (Date) reportExcel.getFinishDate();
					if(startDate.compareTo(startDateIni) < 0) {						
						startDateIni = startDate;						
					}
					
					if(finishDate.compareTo(finishDateIni) > 0) {						
						finishDateIni = finishDate;						
					}	
					row = sheet.createRow(numberRow);

					ExcelHelper.createCellString(row, (short)0, reportExcel.getDate(), styleNormalLeftBorder);
					
					Areas area = areaManager.find(reportExcel.getAreaId());
					
					if(k == 5) {	
						ExcelHelper.createCellString(row, (short)1, area.getAreas().getAreas().getAreas().getAreas().getAreas().getName(), styleNormalLeftBorder);
						ExcelHelper.createCellString(row, (short)2, area.getAreas().getAreas().getAreas().getAreas().getName(), styleNormalLeftBorder);
						ExcelHelper.createCellString(row, (short)3, area.getAreas().getAreas().getAreas().getName(), styleNormalLeftBorder);
						ExcelHelper.createCellString(row, (short)4, area.getAreas().getAreas().getName(), styleNormalLeftBorder);
						ExcelHelper.createCellString(row, (short)5, area.getAreas().getName(), styleNormalLeftBorder);

					}else if(k == 4) {
						
						ExcelHelper.createCellString(row, (short)1, area.getAreas().getAreas().getAreas().getAreas().getName(), styleNormalLeftBorder);
						ExcelHelper.createCellString(row, (short)2, area.getAreas().getAreas().getAreas().getName(), styleNormalLeftBorder);
						ExcelHelper.createCellString(row, (short)3, area.getAreas().getAreas().getName(), styleNormalLeftBorder);
						ExcelHelper.createCellString(row, (short)4, area.getAreas().getName(), styleNormalLeftBorder);
					
					}else if(k == 3) {
						
						ExcelHelper.createCellString(row, (short)1, area.getAreas().getAreas().getAreas().getName(), styleNormalLeftBorder);
						ExcelHelper.createCellString(row, (short)2, area.getAreas().getAreas().getName(), styleNormalLeftBorder);
						ExcelHelper.createCellString(row, (short)3, area.getAreas().getName(), styleNormalLeftBorder);
					
					}else if(k == 2) {
						
						ExcelHelper.createCellString(row, (short)1, area.getAreas().getAreas().getName(), styleNormalLeftBorder);
						ExcelHelper.createCellString(row, (short)2, area.getAreas().getName(), styleNormalLeftBorder);
						
					}else if(k == 1) {
						
						ExcelHelper.createCellString(row, (short)1, area.getAreas().getName(), styleNormalLeftBorder);

					}

					ExcelHelper.createCellString(row, (short)(1+k), reportExcel.getAreaName(), styleNormalLeftBorder);
					ExcelHelper.createCellDecimal(row, (short)(2+k),new BigDecimal(reportExcel.getNumMz()), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(3+k), reportExcel.getPeople(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(4+k), reportExcel.getInspected(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(5+k), reportExcel.getFoco(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(6+k), reportExcel.getClosed(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(7+k), reportExcel.getAbandanada(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(8+k), reportExcel.getRenuente(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(9+k), (reportExcel.getTreated()).add(reportExcel.getDestroyed()), styleNormalCenter);						
					//BigDecimal totalHouses = reportExcel.getInspected().add(reportExcel.getClosed()).add(reportExcel.getAbandanada()).add(reportExcel.getRenuente());					
					//ExcelHelper.createCellDecimal(row, (short)(10+k), totalHouses, styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(10+k), reportExcel.getAllInspected(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(11+k), reportExcel.getAllFocus(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(12+k), reportExcel.getAllTreated(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(13+k), reportExcel.getLarvicide(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(14+k), reportExcel.getInspected401(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(15+k), reportExcel.getFocus401(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(16+k), reportExcel.getTreated401().add(reportExcel.getDestroyed401()), styleNormalCenter);
					
					totalDepositoFoco = totalDepositoFoco.add(reportExcel.getFocus401());
					totalDepositoInspected = totalDepositoInspected.add(reportExcel.getInspected401());
					totalDepositoDestroyed = totalDepositoDestroyed.add(reportExcel.getDestroyed401());
					totalDepositoTreated = totalDepositoTreated.add(reportExcel.getTreated401());					

					ExcelHelper.createCellDecimal(row, (short)(17+k), reportExcel.getInspected402(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(18+k), reportExcel.getFocus402(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(19+k), reportExcel.getTreated402().add(reportExcel.getDestroyed402()), styleNormalCenter);

					totalDepositoFoco = totalDepositoFoco.add(reportExcel.getFocus402());
					totalDepositoInspected = totalDepositoInspected.add(reportExcel.getInspected402());
					totalDepositoDestroyed = totalDepositoDestroyed.add(reportExcel.getDestroyed402());
					totalDepositoTreated = totalDepositoTreated.add(reportExcel.getTreated402());	

					ExcelHelper.createCellDecimal(row, (short)(20+k), reportExcel.getInspected403(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(21+k), reportExcel.getFocus403(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(22+k), reportExcel.getTreated403().add(reportExcel.getDestroyed403()), styleNormalCenter);

					totalDepositoFoco = totalDepositoFoco.add(reportExcel.getFocus403());
					totalDepositoInspected = totalDepositoInspected.add(reportExcel.getInspected403());
					totalDepositoDestroyed = totalDepositoDestroyed.add(reportExcel.getDestroyed403());
					totalDepositoTreated = totalDepositoTreated.add(reportExcel.getTreated403());	

					ExcelHelper.createCellDecimal(row, (short)(23+k), reportExcel.getInspected404(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(24+k), reportExcel.getFocus404(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(25+k), reportExcel.getTreated404().add(reportExcel.getDestroyed404()), styleNormalCenter);					
					totalDepositoFoco = totalDepositoFoco.add(reportExcel.getFocus404());
					totalDepositoInspected = totalDepositoInspected.add(reportExcel.getInspected404());
					totalDepositoDestroyed = totalDepositoDestroyed.add(reportExcel.getDestroyed404());
					totalDepositoTreated = totalDepositoTreated.add(reportExcel.getTreated404());	

					ExcelHelper.createCellDecimal(row, (short)(26+k), reportExcel.getInspected405(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(27+k), reportExcel.getFocus405(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(28+k), reportExcel.getTreated405().add(reportExcel.getDestroyed405()), styleNormalCenter);
					
					totalDepositoFoco = totalDepositoFoco.add(reportExcel.getFocus405());
					totalDepositoInspected = totalDepositoInspected.add(reportExcel.getInspected405());
					totalDepositoDestroyed = totalDepositoDestroyed.add(reportExcel.getDestroyed405());
					totalDepositoTreated = totalDepositoTreated.add(reportExcel.getTreated405());						
					
					ExcelHelper.createCellDecimal(row, (short)(29+k), reportExcel.getInspected407(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(30+k), reportExcel.getFocus407(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(31+k), reportExcel.getTreated407().add(reportExcel.getDestroyed407()), styleNormalCenter);
					
					totalDepositoFoco = totalDepositoFoco.add(reportExcel.getFocus407());
					totalDepositoInspected = totalDepositoInspected.add(reportExcel.getInspected407());
					totalDepositoDestroyed = totalDepositoDestroyed.add(reportExcel.getDestroyed407());
					totalDepositoTreated = totalDepositoTreated.add(reportExcel.getTreated407());
					
					ExcelHelper.createCellDecimal(row, (short)(32+k), reportExcel.getInspected406(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(33+k), reportExcel.getFocus406(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(34+k), reportExcel.getTreated406().add(reportExcel.getDestroyed406()), styleNormalCenter);
					
					totalDepositoFoco = totalDepositoFoco.add(reportExcel.getFocus406());
					totalDepositoInspected = totalDepositoInspected.add(reportExcel.getInspected406());
					totalDepositoDestroyed = totalDepositoDestroyed.add(reportExcel.getDestroyed406());
					totalDepositoTreated = totalDepositoTreated.add(reportExcel.getTreated407());	
					
					ExcelHelper.createCellDecimal(row, (short)(35+k), reportExcel.getInspected408(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(36+k), reportExcel.getFocus408(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(37+k), reportExcel.getTreated408().add(reportExcel.getDestroyed408()), styleNormalCenter);
					
					totalDepositoFoco = totalDepositoFoco.add(reportExcel.getFocus408());
					totalDepositoInspected = totalDepositoInspected.add(reportExcel.getInspected408());
					totalDepositoDestroyed = totalDepositoDestroyed.add(reportExcel.getDestroyed408());
					totalDepositoTreated = totalDepositoTreated.add(reportExcel.getTreated408());	
					
					ExcelHelper.createCellDecimal(row, (short)(38+k), reportExcel.getInspected409(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(39+k), reportExcel.getFocus409(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(40+k), reportExcel.getTreated409().add(reportExcel.getDestroyed409()), styleNormalCenter);
					
					totalDepositoFoco = totalDepositoFoco.add(reportExcel.getFocus409());
					totalDepositoInspected = totalDepositoInspected.add(reportExcel.getInspected409());
					totalDepositoDestroyed = totalDepositoDestroyed.add(reportExcel.getDestroyed409());
					totalDepositoTreated = totalDepositoTreated.add(reportExcel.getTreated409());	
					
					ExcelHelper.createCellDecimal(row, (short)(41+k), reportExcel.getInspected410(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(42+k), reportExcel.getFocus410(), styleNormalCenter);
					ExcelHelper.createCellDecimal(row, (short)(43+k), reportExcel.getTreated410().add(reportExcel.getDestroyed410()), styleNormalCenter);
					totalDepositoFoco = totalDepositoFoco.add(reportExcel.getFocus410());
					totalDepositoInspected = totalDepositoInspected.add(reportExcel.getInspected410());
					totalDepositoDestroyed = totalDepositoDestroyed.add(reportExcel.getDestroyed410());
					totalDepositoTreated = totalDepositoTreated.add(reportExcel.getTreated410());	

					BigDecimal totalDestroyed = reportExcel.getDestroyed401().add(reportExcel.getDestroyed402()).add(reportExcel.getDestroyed403()).add(reportExcel.getDestroyed404()).add(reportExcel.getDestroyed405());
					totalDestroyed = totalDestroyed.add(reportExcel.getDestroyed406()).add(reportExcel.getDestroyed407()).add(reportExcel.getDestroyed408()).add(reportExcel.getDestroyed409()).add(reportExcel.getDestroyed410());
					ExcelHelper.createCellDecimal(row, (short)(44+k), totalDestroyed, styleNormalCenter);
					
					//INDICE AEDICO
					if(reportModel.getDataType() ==4){
						BigDecimal ia = BigDecimal.ZERO;
						BigDecimal ib = BigDecimal.ZERO;
	
						if(reportExcel.getInspected().compareTo(BigDecimal.ZERO) != 0) {
							ia = reportExcel.getFoco().multiply(new BigDecimal(100)).divide(reportExcel.getInspected(), 2, RoundingMode.HALF_UP );
							ib = reportExcel.getAllFocus().multiply(new BigDecimal(100)).divide(reportExcel.getInspected(), 2, RoundingMode.HALF_UP);
						}				
						
						ExcelHelper.createCellDecimal(row, (short)(45+k), ia, styleNormalDecimal);
						
						BigDecimal ir = BigDecimal.ZERO;
						if(reportExcel.getAllInspected().compareTo(BigDecimal.ZERO) != 0) {
							ir = reportExcel.getAllFocus().multiply(new BigDecimal(100)).divide(reportExcel.getAllInspected(), 2, RoundingMode.HALF_UP);
						}					
						ExcelHelper.createCellDecimal(row, (short)(46+k), ir, styleNormalDecimal);				
						ExcelHelper.createCellDecimal(row, (short)(47+k), ib, styleNormalDecimal);
					}
					//COBERTURA
					else{
						BigDecimal cobertura =  BigDecimal.ZERO;
						System.out.println(reportExcel.getScheduledHouses());
						if(reportExcel.getScheduledHouses().compareTo(BigDecimal.ZERO)!= 0) {
							System.out.println("#");
							cobertura = reportExcel.getInspected().multiply(new BigDecimal(100)).divide(reportExcel.getScheduledHouses(), 2, RoundingMode.HALF_UP);
						}
						ExcelHelper.createCellDecimal(row, (short)(45+k), cobertura, styleNormalDecimal);
						
					}
					totalFocos = totalFocos.add(reportExcel.getFoco());
					totalInspected = totalInspected.add(reportExcel.getInspected());
					totalClosed = totalClosed.add(reportExcel.getClosed());
					totalAbandonadas = totalAbandonadas.add(reportExcel.getAbandanada());
					totalRenuentes = totalRenuentes.add(reportExcel.getRenuente());
					totalTreated = totalTreated.add(reportExcel.getTreated());
					totalLarvicide = totalLarvicide.add(reportExcel.getLarvicide());
					totalPrograming = totalPrograming.add(reportExcel.getScheduledHouses());
					numberRow++;	
				}
			}		
			
			sheet.setAutoFilter(CellRangeAddress.valueOf("A14:"+CellReference.convertNumToColString(1+k)+"14"));
			numberRow++;			
			//LINEA CON LOS TOTALES
			if(list.size() > 0) {			
				XSSFRow newRowT = sheet.createRow(numberRow);
			    ExcelHelper.createCellString(newRowT, (short)(1+k), "TOTALES ", styleBoldBorderThin);
			    for(int m = 2 ; m < 45; m++) {
			    	String columnLetter = CellReference.convertNumToColString(m+k);
					ExcelHelper.createCellFormulaEvaluate(newRowT, (short)(m+k), "SUBTOTAL(9,"+columnLetter+"15:"+columnLetter+""+(15+(list.size()-1))+")" , styleNormalBorderThin, evaluator);
//					sheet.autoSizeColumn(m+k);			    
			    }
			 }
						
			if(totalInspected.compareTo(BigDecimal.ZERO)!= 0) {
				iaTotal = totalFocos.multiply(new BigDecimal(100)).divide(totalInspected, 2, RoundingMode.HALF_UP );	
				ibTotal = totalDepositoFoco.multiply(new BigDecimal(100)).divide(totalInspected, 2, RoundingMode.HALF_UP);
			}
				
			if(totalDepositoInspected.compareTo(BigDecimal.ZERO)!= 0) {
				irTotal = totalDepositoFoco.multiply(new BigDecimal(100)).divide(totalDepositoInspected, 2, RoundingMode.HALF_UP);
			}
				
			numberRow = numberRow+3;
			row = sheet.createRow(numberRow);
			ExcelHelper.createCellString(row, (short)(1+k), "1. CASAS CON FOCO", styleNormalLeft);
		  	ExcelHelper.createCell(numberRow, numberRow, 2+k, 3+k, 2+k, totalFocos.toString(), sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 6+k, 11+k, 6+k, "1. DEPÓSITOS FOCO", sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 12+k, 13+k, 12+k, totalDepositoFoco.toString(), sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 16+k, 20+k, 16+k, "I = INSPECCIONADO", sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 24+k, 25+k, 24+k, "I.A. TOTAL:", sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 26+k, 27+k, 26+k, iaTotal.toString().replace(".", ","), sheet, row, styleNormalDecimalLeft, 0);
			numberRow++;
			row = sheet.createRow(numberRow);
			ExcelHelper.createCellString(row, (short)(1+k), "2. CASAS INSPECCIONADAS", styleNormalLeft);
		  	ExcelHelper.createCell(numberRow, numberRow, 2+k, 3+k, 2+k, totalInspected.toString(), sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 6+k, 11+k, 6+k, "2. DEPÓSITOS INSPECCIONADOS", sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 12+k, 13+k, 12+k, totalDepositoInspected.toString(), sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 16+k, 20+k, 16+k, "F = RECIPIENTE FOCO", sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 24+k, 25+k, 24+k, "I.R. TOTAL:", sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 26+k, 27+k, 26+k, irTotal.toString().replace(".", ","), sheet, row, styleNormalDecimalLeft, 0);

			numberRow++;
			row = sheet.createRow(numberRow);
			ExcelHelper.createCellString(row, (short)(1+k), "3. CASAS CERRADAS", styleNormalLeft);
		  	ExcelHelper.createCell(numberRow, numberRow, 2+k, 3+k, 2+k, totalClosed.toString(), sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 6+k, 11+k, 6+k, "1. DEPÓSITOS TRATADOS", sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 12+k, 13+k, 12+k, totalDepositoTreated.toString(), sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 16+k, 20+k, 16+k, "T = TRATADO(Abatizado)", sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 24+k, 25+k, 24+k, "I.B. TOTAL:", sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 26+k, 27+k, 26+k, ibTotal.toString().replace(".", ","), sheet, row, styleNormalDecimalLeft, 0);
			numberRow++;
			row = sheet.createRow(numberRow);
			ExcelHelper.createCellString(row, (short)(1+k), "4. CASAS RENUENTES", styleNormalLeft);
		  	ExcelHelper.createCell(numberRow, numberRow, 2+k, 3+k, 2+k, totalRenuentes.toString(), sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 6+k, 11+k, 6+k, "1. DEPÓSITOS DESTRUIDOS", sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 12+k, 13+k, 12+k, totalDepositoDestroyed.toString(), sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 16+k, 20+k, 16+k, "D = DESTRUIDO", sheet, row, styleNormalLeft, 0);

			numberRow++;
			row = sheet.createRow(numberRow);
			ExcelHelper.createCellString(row, (short)(1+k), "5. CASAS TRATADAS", styleNormalLeft);
		  	ExcelHelper.createCell(numberRow, numberRow, 2+k, 3+k, 2+k, totalTreated.toString(), sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow+1, 16+k, 20+k, 16+k, "COBERTURA DE INSPECCION", sheet, row, styleNormalLeft, 0);
		  	CellRangeAddress cellRangeNumHouses = ExcelHelper.createCell(numberRow, numberRow, 21+k, 26+k, 21+k, "Número de Viviendas Inspeccionadas", sheet, row, styleBoldBorderBottom, 0);
		  	ExcelHelper.createCell(numberRow, numberRow+1, 27+k, 27+k, 27+k, "X 100", sheet, row, styleBoldSmall, 0);
			RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, cellRangeNumHouses, sheet, libro);
			numberRow++;
			row = sheet.createRow(numberRow);
			ExcelHelper.createCellString(row, (short)(1+k), "6. CASAS ABANDONADAS", styleNormalLeft);
		  	ExcelHelper.createCell(numberRow, numberRow, 2+k, 3+k, 2+k, totalAbandonadas.toString(), sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 21+k, 26+k, 21+k, "Número de Viviendas Programadas", sheet, row, styleBoldSmall, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 29+k, 31+k, 29+k, "SUPERVISIÓN", sheet, row, styleBoldMedium, 0);
		  	CellRangeAddress cellRangeEmpty = ExcelHelper.createCell(numberRow, numberRow, 32+k, 36+k, 32+k, "", sheet, row, styleBoldBorderBottom, 0);
			RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, cellRangeEmpty, sheet, libro);
			numberRow++;
			row = sheet.createRow(numberRow);
			ExcelHelper.createCellString(row, (short)(1+k), "7. CASAS PROGRAMADAS", styleNormalLeft);
		  	ExcelHelper.createCell(numberRow, numberRow, 2+k, 3+k, 2+k, totalPrograming.toString(), sheet, row, styleNormalLeft, 0);
		  	ExcelHelper.createCell(numberRow, numberRow, 16+k, 21+k, 16+k, "CONSUMO LARVICIDA: "+totalLarvicide.toString(), sheet, row, styleNormalLeft, 0);
		  	//**//
		  	numberRow++;
		  	row = sheet.createRow(numberRow);
			String Larviocida= reportManager.getLarvicides(reportModel.getId());
			System.out.println(Larviocida);
			ExcelHelper.createCell(numberRow, numberRow, 16+k, 26+k, 16+k, "LARVICIDAS:"+reportManager.getLarvicides(reportModel.getId()), sheet, row, styleNormalLeft, 0);
			
		  	numberRow++;
			row = sheet.createRow(numberRow);
		  	ExcelHelper.createCell(numberRow, numberRow, 16+k, 26+k, 16+k, "NÚMERO DE MUESTRAS ENTOMOLÓGICAS ENVIADAS AL LAB: "+sampleManager.getTotalSamples(id), sheet, row, styleNormalLeft, 0);

			//DATOS CABECERA
		  	
		  	if(totalPrograming.compareTo(BigDecimal.ZERO) != 0) {
		  		coberturaInspection = totalInspected.multiply(new BigDecimal(100)).divide(totalPrograming, 2, RoundingMode.HALF_UP);
		  	}
			String c = coberturaInspection.toString();
			
			row = sheet.createRow(4);
			ExcelHelper.createCell(4, 4, 2+k, 3+k, 2+k, "REGIÓN:", sheet, row, styleBoldMediumLeft, 0);
			ExcelHelper.createCell(4, 4, 4+k, 6+k, 4+k, region, sheet, row, styleNormalLeft, 0);			
			ExcelHelper.createCell(4, 4, 8+k, 10+k, 8+k, "PROVINCIA:", sheet, row, styleBoldMediumLeft, 0);
			ExcelHelper.createCell(4, 4, 11+k, 13+k, 11+k, provincia, sheet, row, styleNormalLeft, 0);
			ExcelHelper.createCell(4, 4, 15+k, 16+k, 15+k, "DISTRITO:", sheet, row, styleBoldMediumLeft, 0);
			ExcelHelper.createCell(4, 4, 17+k, 19+k, 17+k, distrito, sheet, row, styleNormalLeft, 0);
			ExcelHelper.createCell(4, 4, 24+k, 26+k, 24+k, "EST. DE SALUD:", sheet, row, styleBoldMediumLeft, 0);
			ExcelHelper.createCell(4, 4, 27+k, 34+k, 27+k, EESS, sheet, row, styleNormalLeft, 0);			
			ExcelHelper.createCell(4, 4, 36+k, 37+k, 36+k, "SECTOR:", sheet, row, styleBoldMediumLeft, 0);
			ExcelHelper.createCell(4, 4, 38+k, 44+k, 38+k, sector, sheet, row, styleNormalLeft, 0);

		
			if(reportModel.getDataType() == 4){
				ExcelHelper.createCell(4, 4, 46+k, 49+k, 46+k, "(X) VIGILANCIA", sheet, row, styleBoldMediumLeft, 0);
			}else{
				ExcelHelper.createCell(4, 4, 46+k, 49+k, 46+k, "(  ) VIGILANCIA", sheet, row, styleBoldMediumLeft, 0);
			}
			
			row = sheet.createRow(5);
			
			if(reportModel.getDataType() == 1 || reportModel.getDataType() == 2){
				ExcelHelper.createCell(5, 5, 46+k, 49+k, 46+k, "(X) CONTROL", sheet, row, styleBoldMediumLeft, 0);
			}else{
				ExcelHelper.createCell(5, 5, 46+k, 49+k, 46+k, "(  ) CONTROL", sheet, row, styleBoldMediumLeft, 0);
			}	
	
			row = sheet.createRow(6);
			
			String dateStart = "";
			if(startDateIni != null) {
				dateStart = format.format(startDateIni);
			}
			String dateFinish = "";
			if(finishDateIni != null) {
				dateFinish = format.format(finishDateIni);
			}
			
			if(dateStart.equals(dateFinish)) {
				date = dateStart;
			}else {
				date = dateStart+" - "+dateFinish;
			}
			
			
			ExcelHelper.createCell(6, 6, 8+k, 10+k, 8+k, "FECHA:", sheet, row, styleBoldMediumLeft, 0);
			ExcelHelper.createCell(6, 6, 11+k, 14+k, 11+k, date, sheet, row, styleNormalLeft, 0);
			ExcelHelper.createCell(6, 6, 15+k, 19+k, 15+k, "SEMANA EPIDEMIOLÓGICA:", sheet, row, styleBoldMediumLeft, 0);
			ExcelHelper.createCell(6, 6, 20+k, 20+k, 20+k, week.toString(), sheet, row, styleNormalLeft, 0);
			ExcelHelper.createCell(6, 6, 24+k, 29+k, 24+k, "COBERTURA DE INSPECCIÓN:", sheet, row, styleBoldMediumLeft, 0);
			ExcelHelper.createCell(6, 6, 30+k, 32+k, 30+k, c+"%", sheet, row, styleNormalLeft, 0);
			
			if (reportModel.getDataType() == 1 || reportModel.getDataType() == 3 ) {				
				ExcelHelper.createCell(6, 6, 46+k, 49+k, 46+k, "(X) RECONVERSIÓN", sheet, row, styleBoldMediumLeft, 0);

			}else {
				ExcelHelper.createCell(6, 6, 46+k, 49+k, 46+k, "(  ) RECONVERSIÓN", sheet, row, styleBoldMediumLeft, 0);

			}

			sheet.autoSizeColumn(0);			    

			
			String excelName = "Consolidado_"+Calendar.getInstance().getTimeInMillis()+".xlsx";
			excelPathName = ConfigurationHelper.getReportPath()+"/"+excelName;
			try {
				FileOutputStream elFichero = new FileOutputStream(ConfigurationHelper.getReportPath()+"/"+excelName);
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
	public String createPlanMapReport(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		String pdfPathName = "";
		String name = "";
		String url = "";
		String url2 = "";
		String markerEESS = "";
		String markers = "";
		String arrayPaths = "";
		String path = "";
		String leyend1 = "";
		String leyend2 = "";
		String leyend3 = "";
		String leyend4 = "";
		String nameMz = "";		
		int index1 = 1;
		int index2 = 1;
		int index3 = 1;
		int index4 = 1;
		
		try{		
			url = "https://maps.googleapis.com/maps/api/staticmap?key=AIzaSyByh9VuHBEa08Q0r_7BECKbTksG0VTytlI&size=398x415&scale=2&maptype=roadmap";
			url2 = "https://maps.googleapis.com/maps/api/staticmap?key=AIzaSyByh9VuHBEa08Q0r_7BECKbTksG0VTytlI&size=398x415&scale=2&maptype=roadmap";
			PlansAreasDao plansAreasManager = (PlansAreasDao) ctx.getBean("PlansAreasDaoImpl");
			PlanDao plansManager = (PlanDao) ctx.getBean("PlanDaoImpl");
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");	
			
			String pdfFileName = "PlanMapReport_"+Calendar.getInstance().getTimeInMillis()+".pdf";					
			String jrxmlFileName = ConfigurationHelper.getReportPlanMapJasper();
			pdfPathName = ConfigurationHelper.getReportPath()+pdfFileName;		
			
			Map<String, Object> parameters = new HashMap<String, Object>();			
			List<Areas> listAreasParent = areaManager.getParentsPlan(id);
			List<CoordsArea> lista = new ArrayList<CoordsArea>();	
			
			Plans plan = plansManager.find(id);					
			name = listAreasParent.get(0).getName();
			
			if(listAreasParent.size() > 1){
				for(int i = 1 ; i < listAreasParent.size(); i++){
					Areas area = listAreasParent.get(i);
					name += " - "+area.getName();
				}				
			}			
		
			Areas eess = plan.getInspections().getAreas();			
			markerEESS = "&markers=color:green%7Clabel:*%7C"+eess.getLatitude()+","+eess.getLongitude();
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(plan.getDate());
			parameters.put("userName", plan.getUsers().getName());
			parameters.put("name", name);			
			parameters.put("date",CalendarHelper.getDateToPlanMap(cal));			
			parameters.put("planSize", String.valueOf(plan.getPlanSize()));
			parameters.put("interval", String.valueOf(plan.getHouseInterval()));
			parameters.put("house", String.valueOf(plan.getHouseIni()));
			parameters.put("id", id);	
			parameters.put("nameEESS", eess.getName());		

			List<Areas> listaAreasPlan = plansAreasManager.getAreasNotSubstitutes(id);
			
			String[] arrayLetters =  this.createArray();			
			int k = 1;
			String label = "";
			for(int i=0; i<listaAreasPlan.size(); i++){
				Areas area = listaAreasPlan.get(i);
				
				if(i >= 26) {
					label = String.valueOf(k);
					k++;
				}else {
					label = arrayLetters[i];
				}
				
				nameMz = area.getName();
				if(area.getName().length() > 25) {					
					nameMz = area.getName().substring(1,25)+"...";
				}

				if(index1 < 10) {
					leyend1 += label +" - " + nameMz + "<br>";
					index1++;
				}else if(index2 < 10) {
					leyend2 += label +" - " + nameMz + "<br>";
					index2++;
				}else if(index3 < 10) {
					leyend3 += label +" - " + nameMz + "<br>";
					index3++;
				}else if(index4 < 10) {
					leyend4 += label +" - " + nameMz + "<br>";
					index4++;
				}			

				markers += "&markers=color:red%7Clabel:"+label+"%7C"+area.getLatitude()+","+area.getLongitude();				
				
    			ObjectMapper mapper = new ObjectMapper();
				lista = mapper.readValue(area.getCoords(),TypeFactory.defaultInstance().constructCollectionType(List.class,CoordsArea.class));				
			
				path = "&path=color:0x00000070%7Cweight:3%7C";
				
				for(int j = 0; j <lista.size(); j++){					
					CoordsArea coordsArea = lista.get(j);					
					path += new BigDecimal(coordsArea.getLatitude()).setScale(6, RoundingMode.HALF_UP)+","+new BigDecimal(coordsArea.getLongitude()).setScale(6, RoundingMode.HALF_UP)+"%7C";
				}	
				
				CoordsArea coordsAreaIni = lista.get(0);	
				path += coordsAreaIni.getLatitude()+","+coordsAreaIni.getLongitude();
				
				arrayPaths += path;
				path = "";
			}		

			parameters.put("url",url+markers+arrayPaths);
			parameters.put("url2",url2+markerEESS+arrayPaths);
			parameters.put("leyend1",leyend1);
			parameters.put("leyend2",leyend2);
			parameters.put("leyend3",leyend3);
			parameters.put("leyend4",leyend4);

			BasicDataSource dataSource = (BasicDataSource) ctx.getBean("dataSource");
            Connection conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());	 
            JasperReport report = JasperCompileManager.compileReport(jrxmlFileName);
			JasperPrint print = JasperFillManager.fillReport(report, parameters, conn);

			// Exporta el informe a PDF
			JasperExportManager.exportReportToPdfFile(print,pdfPathName);				
			System.out.println("PDF Mapa plan creado correctamente");
			//tempPathsMapManager.deleteByUser(user);			
		}catch(Exception e){
		    logger.error("ERROR::" +e.toString());
		
		}finally{
			ctx.close();		
		}
		return pdfPathName;
	}	


	@Override
	public String createScheduleReport(Integer scheduleId) {
		//System.out.println("TYPE="+scheduledata.getTypeId());

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		BigDecimal MAS = BigDecimal.ZERO;
		Areas sector;
		String persons = "";
		int totalDays = 0;
		String excelPathName = "";
		Schedules entitySchedules = new Schedules();
		Schedule scheduleData = null;
		List<InspectionSchedule> inspectionsList = new ArrayList<InspectionSchedule>();
		try{
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");	
			ScheduleDao scheduleManager = (ScheduleDao) ctx.getBean("ScheduleDaoImpl");	
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");	
			Schedules schedule = scheduleManager.find(scheduleId);
			
			inspectionsList = inspectionManager.getListBySchedule(schedule);
			System.out.println(inspectionsList.get(0).getAreaId());
			
			List<ScheduleDay> listDays = scheduleManager.getReportDays(scheduleId);
			
			scheduleData = new Schedule(schedule.getStartDate().getTime(),
										schedule.getFinishDate().getTime(), 
										schedule.getTableElements().getId(), 
										schedule.getAreas().getId(), 
										schedule.getReconversionScheduleId(), 
										schedule.getLarvicide().getId());
			List<Areas> listEESS = areaManager.getListAreas(schedule.getAreas());
			
			Areas area = areaManager.find(schedule.getAreas().getId());
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(schedule.getStartDate());
		    String month = CalendarHelper.getMonth(calendar.get(Calendar.MONTH)).toUpperCase();
		    String year = String.valueOf(calendar.get(Calendar.YEAR));
		    
			XSSFWorkbook libro = new XSSFWorkbook();
		  	XSSFCellStyle styleTitle = ExcelHelper.createStyle(libro, (short)16, true, XSSFCellStyle.ALIGN_CENTER , false,  (short)0, 0);  	
		  	XSSFCellStyle styleBoldMedium = ExcelHelper.createStyle(libro, (short)12, true, XSSFCellStyle.ALIGN_CENTER , true,  CellStyle.BORDER_MEDIUM, 0);
		  	XSSFCellStyle styleNormalCenter = ExcelHelper.createStyle(libro, (short)11, false, XSSFCellStyle.ALIGN_CENTER , true,  CellStyle.BORDER_MEDIUM, 0);
		  	XSSFCellStyle styleNormalDecimal =  ExcelHelper.createStyle(libro, (short)11, false, XSSFCellStyle.ALIGN_CENTER , true,  CellStyle.BORDER_MEDIUM, 1);
		  	  
		  	XSSFSheet sheet = libro.createSheet("Programación");
			XSSFRow rowTitle = sheet.createRow(1);			
			XSSFRow rowCellsBig = sheet.createRow(4);			
			XSSFRow rowCellsDaysAcronym = sheet.createRow(6);
			XSSFRow rowCellsDays = sheet.createRow(7);
			
			Integer type=scheduleData.getTypeId();
			Long dateStart = scheduleData.getStartDate();
			Long dateFinish = scheduleData.getFinishDate();
			String start = (new SimpleDateFormat("dd/MM/yyyy")).format(dateStart);			
			String finish = (new SimpleDateFormat("dd/MM/yyyy")).format(dateFinish);
			Integer reconversion=scheduleData.getReconversionScheduleId();
			if(type==1002 && reconversion!=null){
				type=1003;
			}
			System.out.println(scheduleData.getTypeId()+" , "+scheduleData.getReconversionScheduleId());
			
			
			//TITULO DEL EXCEL  			  	
			if(type==1001) {
			  	ExcelHelper.createCell(1, 2, 0, 15, 0, "PROGRAMACIÓN DE VIGILANCIA DE LOS EESS DE LA MR "+area.getName().toUpperCase()+" - "+start+ " AL "+finish, sheet, rowTitle, styleTitle, 0);

			}else if(type==1002) {
			  	ExcelHelper.createCell(1, 2, 0, 15, 0, "PROGRAMACIÓN DE CONTROL DE LOS EESS DE LA MR "+area.getName().toUpperCase()+" - "+start+ " AL "+finish, sheet, rowTitle, styleTitle, 0);

			}else if(type==1003) {
			  	ExcelHelper.createCell(1, 2, 0, 15, 0, "PROGRAMACIÓN DE RECONVERSION DE LOS EESS DE LA MR "+area.getName().toUpperCase()+" - "+start+ " AL "+finish, sheet, rowTitle, styleTitle, 0);

			}

		  	//EESS			
			CellRangeAddress cellRangeEESS = ExcelHelper.createCell(4, 8, 1, 1, 1, "EE.SS", sheet, rowCellsBig, styleBoldMedium, 7000);

			//SECTOR
			CellRangeAddress cellRangeDistrit = ExcelHelper.createCell(4, 8, 2, 2, 2, "SECTORES", sheet, rowCellsBig, styleBoldMedium, 7000);
			
			//VIVIENDAS TOTALES
			CellRangeAddress cellRangeHouses = ExcelHelper.createCell(4, 8, 3, 3, 3, "Nº VIVIENDAS TOTALES", sheet, rowCellsBig, styleBoldMedium, 3000);
			
			//PROPORCION
			CellRangeAddress cellRangeProporcion = ExcelHelper.createCell(4, 8, 4, 4, 4, "Nº VIVIENDAS PROGRAMADAS", sheet, rowCellsBig, styleBoldMedium, 3500);
			int col = 5;
			if(type==1001) {
				//INTERVALO
				CellRangeAddress cellRangeIntervalo = ExcelHelper.createCell(4, 8, 5, 5, 5, "INTERVALO", sheet, rowCellsBig, styleBoldMedium, 4000);
				
				//PERSONAL
				//CellRangeAddress cellRangePersonal = ExcelHelper.createCell(4, 8, 6, 6, 6, "PERSONAL NECESARIO", sheet, rowCellsBig, styleBoldMedium, 3500);
				
				//DIAS INTERVENCION
				CellRangeAddress cellRangeDaysIntervencion = ExcelHelper.createCell(4, 5, 6, (5+listDays.size()), 6, "DIAS DE INTERVENCIÓN - "+month+ " "+year, sheet, rowCellsBig, styleBoldMedium, 0);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeIntervalo, sheet, libro);
				//ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangePersonal, sheet, libro);			
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeDaysIntervencion, sheet, libro);
				col = 6;
			}else {
				
				//DIAS INTERVENCION
				CellRangeAddress cellRangeDaysIntervencion = ExcelHelper.createCell(4, 5, 5, (4+listDays.size()), 5, "DIAS DE INTERVENCIÓN - "+month+ " "+year, sheet, rowCellsBig, styleBoldMedium, 0);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeDaysIntervencion, sheet, libro);
				
			}
			
			for(int i = 0; i< listDays.size(); i++){
				ScheduleDay scheduleDay = listDays.get(i);				
				CellRangeAddress cellRangeDaysAcronym = ExcelHelper.createCell(6, 6, col, col, col, scheduleDay.getDayOfWeek(), sheet, rowCellsDaysAcronym, styleBoldMedium, 0);
				CellRangeAddress cellRangeDays = ExcelHelper.createCell(7, 8, col, col, col, scheduleDay.getDay().toString(), sheet, rowCellsDays, styleBoldMedium, 0);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeDaysAcronym, sheet, libro);
				ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeDays, sheet, libro);
				col++;				
			}

			//TOTAL DIAS
			CellRangeAddress cellRangeTotalDays = ExcelHelper.createCell(4, 8, col, col, col, "TOTAL DIAS ", sheet, rowCellsBig, styleBoldMedium, 2500);			
			ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeEESS, sheet, libro);
			ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeDistrit, sheet, libro);
			ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeHouses, sheet, libro);	
			ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeProporcion, sheet, libro);
			
		
			
		
				
			
			ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTotalDays, sheet, libro);			

			int lineIni = 9;
			for(int j = 0; j < listEESS.size(); j++ ){
				Areas EESS = listEESS.get(j);
				
				for(int h = 0; h < inspectionsList.size(); h++ ) {
					BigDecimal countHouses=BigDecimal.ZERO;
					BigDecimal totalCountHouses=BigDecimal.ZERO;
					int areaInspection = inspectionsList.get(h).getAreaId();
					int areaEESS = listEESS.get(j).getId();
					if(areaInspection == areaEESS) {
						List<Areas> listSectors = areaManager.getListAreas(EESS);
						System.out.print("#@|"+listEESS.get(j).getId());
				
						BigDecimal totalHouses = BigDecimal.ZERO;
						if(listSectors.size() > 0){
							if(EESS.getHouses() != null && EESS.getHouses() > 0){
								totalHouses = new BigDecimal(EESS.getHouses());
							}
		
							MAS = getMAS(totalHouses);
							sector = listSectors.get(0);					
							
							XSSFRow rowCellsEESS = sheet.createRow(lineIni);
							CellRangeAddress cellRangeNameEESS = ExcelHelper.createCell(lineIni, lineIni+listSectors.size(), 1, 1, 1, EESS.getName(), sheet, rowCellsEESS, styleNormalCenter,0);
							ExcelHelper.createCellString(rowCellsEESS, (short)2, sector.getName(), styleNormalCenter);
							ExcelHelper.createCellDecimal(rowCellsEESS, (short)3, new BigDecimal(sector.getHouses()), styleNormalDecimal);
							System.out.println(sector.getId()+"//"+inspectionsList.get(h).getId());
	
							if(type==1001) {
								countHouses=getProportion(new BigDecimal(sector.getHouses()), MAS, totalHouses);
							}else if(type==1002) {
								countHouses=inspectionManager.getControlHouses(scheduleId,sector.getId());
								//countHouses=getProportion(new BigDecimal(sector.getHouses()), MAS, totalHouses);
							}else if(type==1003) {
								countHouses=inspectionManager.getReconversionHouses(scheduleId,sector.getId());	
							}
							ExcelHelper.createCellDecimal(rowCellsEESS, (short)4, countHouses, styleNormalDecimal);
							totalCountHouses=totalCountHouses.add(countHouses);
							//ExcelHelper.createCell(lineIni, lineIni, 2, 2, 2, sector.getName(), sheet, rowCellsEESS, styleNormalCenter,0);
							//ExcelHelper.createCell(lineIni, lineIni, 3, 3, 3, sector.getHouses().toString(), sheet, rowCellsEESS, styleNormalCenter,0);
							//ExcelHelper.createCell(lineIni, lineIni, 4, 4, 4,getProportion(new BigDecimal(sector.getHouses()), MAS, totalHouses).toString(), sheet, rowCellsEESS, styleNormalCenter,0);
							
							
							
							CellRangeAddress cellRangeIntervalEESS = ExcelHelper.createCell(lineIni, lineIni+listSectors.size(), 5, 5, 5, getInterval(totalHouses, MAS).toString(), sheet, rowCellsEESS, styleNormalCenter,0);
							//CellRangeAddress cellRangePersonalEESS = ExcelHelper.createCell(lineIni, lineIni+listSectors.size(), 6, 6, 6, getRequiredStaff(MAS).toString(), sheet, rowCellsEESS, styleNormalCenter,0);
		
							if(listSectors.size()>1){			
								int line = lineIni+1;	
								Integer houses = 0;
								for(int k = 1; k < listSectors.size(); k++ ){
									countHouses=BigDecimal.ZERO;
									sector = listSectors.get(k);
									if(sector.getHouses() != null){
										houses = sector.getHouses();
									}
									if(type==1001) {
										countHouses=getProportion(new BigDecimal(houses), MAS, totalHouses);
									}else if(type==1002) {
										countHouses=inspectionManager.getControlHouses(scheduleId,sector.getId());
										//countHouses=getProportion(new BigDecimal(houses), MAS, totalHouses);
									}else if(type==1003) {
										countHouses=inspectionManager.getReconversionHouses(scheduleId,sector.getId());	
									}
									XSSFRow rowCell = sheet.createRow(line);							
									ExcelHelper.createCell(line, line, 2, 2, 2, sector.getName(), sheet, rowCell, styleNormalCenter,0);
									ExcelHelper.createCell(line, line, 3, 3, 3, houses.toString(), sheet, rowCell, styleNormalCenter,0);
									System.out.println(sector.getId()+"//"+inspectionsList.get(h).getId());
									
									ExcelHelper.createCell(line, line, 4, 4, 4,countHouses.toString(), sheet, rowCell, styleNormalCenter,0);
									totalCountHouses=totalCountHouses.add(countHouses);
									line++;							
								}						
							}
		
							int lineSubTotal = lineIni+listSectors.size();
							XSSFRow rowCells = sheet.createRow(lineSubTotal);
							ExcelHelper.createCell(lineSubTotal, lineSubTotal, 2, 2, 2, "Sub Total", sheet, rowCells, styleBoldMedium,0);
							ExcelHelper.createCell(lineSubTotal, lineSubTotal, 3, 3, 3, totalHouses.toString(), sheet, rowCells, styleBoldMedium,0);
							ExcelHelper.createCell(lineSubTotal, lineSubTotal, 4, 4, 4, MAS.toString(), sheet, rowCells, styleBoldMedium,0);					
							if(type==1001) {
								ExcelHelper.createCell(lineSubTotal, lineSubTotal, 4, 4, 4, MAS.toString(), sheet, rowCells, styleBoldMedium,0);					
							}else if(type==1003 || type==1002) {
								ExcelHelper.createCell(lineSubTotal, lineSubTotal, 4, 4, 4, totalCountHouses.toString(), sheet, rowCells, styleBoldMedium,0);					
							}
							int colIni = 6;
							if(type!=1001) {colIni = 5;}
							
							for(int m = 0; m < listDays.size(); m++){
								
								ScheduleDay scheduleDay = listDays.get(m);
								calendar.set(Calendar.DAY_OF_MONTH, scheduleDay.getDay());					
								
								BigDecimal totalpersons = scheduleManager.getTotalPersons(EESS.getId(), calendar.getTime());
								if(totalpersons.compareTo(BigDecimal.ZERO) > 0){
									persons = totalpersons.toString();							
									totalDays++;
								}
								CellRangeAddress cellRangePerson = ExcelHelper.createCell(lineIni, lineIni+listSectors.size(), colIni, colIni, colIni, persons, sheet, rowCellsEESS, styleNormalCenter,0);
								ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangePerson, sheet, libro);
								colIni++;						
							}
		
							CellRangeAddress cellRangeTotal = ExcelHelper.createCell(lineIni, lineIni+listSectors.size(), colIni, colIni, colIni, String.valueOf(totalDays) , sheet, rowCellsEESS, styleNormalCenter,0);
							ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeTotal, sheet, libro);
							
							totalDays = 0;	
		
							ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeNameEESS, sheet, libro);
							ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangeIntervalEESS, sheet, libro);
							//ExcelHelper.setBorder(CellStyle.BORDER_MEDIUM, cellRangePersonalEESS, sheet, libro);						
							lineIni = lineIni+listSectors.size()+1;	
						}
					}
				}
			}	

			String excelName = "Programacion_"+Calendar.getInstance().getTimeInMillis()+".xlsx";
			excelPathName = ConfigurationHelper.getReportPath()+"/"+excelName;
			try {

				FileOutputStream elFichero = new FileOutputStream(ConfigurationHelper.getReportPath()+"/"+excelName);
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
	
	private BigDecimal getMAS(BigDecimal totalHouses){	
		BigDecimal MAS = BigDecimal.ZERO;	
		try{
			BigDecimal dividend = ((totalHouses.multiply(ConfigurationHelper.getMAS_Z().multiply(ConfigurationHelper.getMAS_Z()))).multiply(ConfigurationHelper.getMAS_p())).multiply(new BigDecimal(1).subtract(ConfigurationHelper.getMAS_p()));
			BigDecimal dividerA = ((totalHouses.subtract(new BigDecimal(1))).multiply(ConfigurationHelper.getMAS_e().multiply(ConfigurationHelper.getMAS_e())));
			BigDecimal dividerB = ((ConfigurationHelper.getMAS_Z().multiply(ConfigurationHelper.getMAS_Z())).multiply(ConfigurationHelper.getMAS_p())).multiply(new BigDecimal(1).subtract(ConfigurationHelper.getMAS_p()));
			BigDecimal divider = dividerA.add(dividerB);			
			if(divider.compareTo(BigDecimal.ZERO)> 0){
				MAS = dividend.divide(divider, 0, BigDecimal.ROUND_HALF_UP);
			}		
		
		}catch(Exception e){
			System.out.println("ERROR ::: ReportServiceImpl.getMAS("+totalHouses+")::"+e.toString());
		}

		return MAS;		
	}
	
	private BigDecimal getProportion(BigDecimal sectorHouses, BigDecimal MAS, BigDecimal totalHouses){	
		BigDecimal proportion = BigDecimal.ZERO;
		
		try{
			if(totalHouses.compareTo(BigDecimal.ZERO) > 0){
				if(totalHouses.compareTo(new BigDecimal(3500))<=0) {
					proportion = (sectorHouses.multiply(new BigDecimal(0.1))).setScale(0, BigDecimal.ROUND_HALF_UP);	
				}else if(totalHouses.compareTo(new BigDecimal(3500)) > 0){
					proportion = (sectorHouses.multiply(MAS)).divide(totalHouses, 0, BigDecimal.ROUND_HALF_UP);	
				}
					
			}
		}catch (Exception e){
			System.out.println("ERROR ::: ReportServiceImpl.getProportion("+sectorHouses+","+MAS+","+totalHouses+")::"+e.toString());
		}		
		return proportion;		
	}
	
	private BigDecimal getInterval(BigDecimal totalHouses, BigDecimal MAS){	
		BigDecimal interval = BigDecimal.ZERO;
		try{
			if(MAS.compareTo(BigDecimal.ZERO) > 0){
				if(totalHouses.compareTo(new BigDecimal(3500))<=0) {
					interval = new BigDecimal(10);	
				}else if(totalHouses.compareTo(new BigDecimal(3500)) > 0){
					interval = totalHouses.divide(MAS, 0, BigDecimal.ROUND_HALF_UP);
				}
					
			}			
		}catch(Exception e){
			System.out.println("ERROR ::: ReportServiceImpl.getInterval("+totalHouses+","+MAS+")::"+e.toString());
		}
		
		return interval;		
	}
	
	private BigDecimal getRequiredStaff(BigDecimal MAS){
		BigDecimal required = BigDecimal.ZERO;		
		try{
			required = MAS.divide(ConfigurationHelper.getRequiredStaffPercentage(), 0, BigDecimal.ROUND_FLOOR);	
		}catch(Exception e){
			System.out.println("ERROR ::: ReportServiceImpl.getRequiredStaff("+MAS+")::"+e.toString());
		}		
		return required;		
	}	
	
	private String[] createArray(){
		String[] s;
		s=new String[26];
		for ( int i=0; i<26; i++) {
			s[i] =   String.valueOf((char) ('A' + i )); 
		}
		return s;
	}

	@Override
	public List<InspectionListImpl> getInspections(ReportInspection reportInspection) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<InspectionListImpl> list = new ArrayList<InspectionListImpl>();
		try{
			InspectionDao inspectionManager = (InspectionDao) ctx.getBean("InspectionDaoImpl");
			list = inspectionManager.getListReportInspections(reportInspection);		
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLISTINSPECTION + e.toString());
			
		}finally{
			ctx.close();
		}
		
		return list;
	}

	
}
