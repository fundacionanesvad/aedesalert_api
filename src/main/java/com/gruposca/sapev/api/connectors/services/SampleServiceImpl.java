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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.gruposca.sapev.api.connectors.dao.impl.AreaDao;
import com.gruposca.sapev.api.connectors.dao.impl.InspectionDao;
import com.gruposca.sapev.api.connectors.dao.impl.SampleDao;
import com.gruposca.sapev.api.connectors.dao.impl.SamplePhaseDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Inspections;
import com.gruposca.sapev.api.connectors.dao.model.Samples;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.helper.ExcelHelper;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Sample;
import com.gruposca.sapev.api.modelview.SamplesFile;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.SampleService;

public class SampleServiceImpl extends AbsService implements SampleService{

	public SampleServiceImpl(AbsConnector connector) { super( connector); }

	private XSSFWorkbook workbook;

	@Override
	public Boolean uploadFile(File inputfile) throws Exception{
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		FileInputStream fis = null;
		String sampleFileName="";
		try{	
			SampleDao sampleManager = (SampleDao) ctx.getBean("SampleDaoImpl");

			fis = new FileInputStream(inputfile);
			workbook = new XSSFWorkbook(fis);			
			XSSFSheet sheet = workbook.getSheetAt(0);			
			Iterator<Row> rows = sheet.rowIterator();

			while (rows.hasNext ()){
				
				XSSFRow row = (XSSFRow) rows.next ();
				if(row.getRowNum()==0 || row.getRowNum()==1 || row.getRowNum()==2 || row.getRowNum()==3 || row.getRowNum()==4){
					continue; 	  
				}
			  
				Samples sample = null;		 
				Iterator<Cell> cells = row.cellIterator();			  
			  
				while (cells.hasNext()) {
				  				  
					XSSFCell cell = (XSSFCell) cells.next();				  
					String code = cell.getStringCellValue().trim();
				  
					if (cell.getColumnIndex() == 3 && !code.equals("")){					  
						sample = sampleManager.getSampleWithCode(code); 
					}
					
					String result = cell.getStringCellValue().trim();
				  
					if (cell.getColumnIndex() == 6 && !result.equals("") && sample != null){					  
						
						if(result.equals("POSITIVO")) {
							result = "Positivo";
						}else if (result.equals("NEGATIVO")) {
							result = "Negativo";
						}else {
							result = null;
						}						
						sample.setResult(result);	
						sampleManager.save(sample);	
						
						if(result.toUpperCase().equals("POSITIVO")){	
							AlertServiceImpl alertServiceImpl = new AlertServiceImpl(getConnector());
							alertServiceImpl.generateSamplePositiveAlert(ctx, sample);								
						}						
					}				
				}
			}
						
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar cal = Calendar.getInstance();	
			sampleFileName = "sample_"+dateFormat.format(cal.getTime())+".xlsx";
			Path pathIn = Paths.get(inputfile.getPath());
			Path pathOut = Paths.get(ConfigurationHelper.getSamplesPath()+sampleFileName);		
			Files.copy(pathIn, pathOut, StandardCopyOption.REPLACE_EXISTING);

		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_UPLOADFILE + e.toString());
			
		}finally{
			ctx.close();
			if (fis != null) {
				fis.close();
			}
			
		}		
		return true;		
	}

	
	@Override
	public String createXls(Session session, Integer inspectionId) {
		System.out.println("1");
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
			
			String logoLeft =  ConfigurationHelper.getString("configuration.logoLeft");
			String logoRight =  ConfigurationHelper.getString("configuration.LogoRight");
		
			String title = "RESULTADO DEL DIAGNOSTICO TAXONÓMICO LARVARIO DE Aedes aegypti, EN MUESTRAS PROCENTES DEL "+area.getName().toUpperCase()+" - PROVINCIA "+area.getAreas().getAreas().getName().toUpperCase();		
			
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
		  	System.out.println("2");
			XSSFRow rowCabecera = sheet.createRow(4);
			ExcelHelper.createCell(4, 4, 0, 0, 0, "FECHA", sheet, rowCabecera, styleBoldMedium, 4000);			
			ExcelHelper.createCell(4, 4, 1, 1, 1, "DIRECCIÓN", sheet, rowCabecera, styleBoldMedium, 9000);			
			ExcelHelper.createCell(4, 4, 2, 2, 2, "LOCALIDAD", sheet, rowCabecera, styleBoldMedium, 5000);
			ExcelHelper.createCell(4, 4, 3, 3, 3, "SECTOR", sheet, rowCabecera, styleBoldMedium, 5000);	
			ExcelHelper.createCell(4, 4, 4, 4, 4, "CÓDIGO MUESTRA", sheet, rowCabecera, styleBoldMedium, 5000);			
			ExcelHelper.createCell(4, 4, 5, 5, 5, "TIPO DE FOCO", sheet, rowCabecera, styleBoldMedium, 6000);	
			ExcelHelper.createCell(4, 4, 6, 6, 6, "ESTADO VECTOR", sheet, rowCabecera, styleBoldMedium, 5000);			
			ExcelHelper.createCell(4, 4, 7, 7, 7, "RESULTADO", sheet, rowCabecera, styleBoldMedium, 4000);			

			int newRow = 5;				
			List<SamplesFile> list = sampleManager.getSanplesFile(inspectionId);
			System.out.println("3");
			for(int j = 0; j< list.size(); j++){
				System.out.println("#"+j);
				SamplesFile samplesFile = list.get(j);
				XSSFRow row = sheet.createRow(newRow);
				ExcelHelper.createCell(newRow, newRow, 0, 0, 0, samplesFile.getDate(), sheet, row, styleNormalCenter, 0);
				ExcelHelper.createCell(newRow, newRow, 1, 1, 1, samplesFile.getAddress(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(newRow, newRow, 2, 2, 2, samplesFile.getMicrorred(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(newRow, newRow, 3, 3, 3, samplesFile.getSector(), sheet, row, styleNormalCenter, 0);
				ExcelHelper.createCell(newRow, newRow, 4, 4, 4, samplesFile.getCode(), sheet, row, styleNormalCenter, 0);
				ExcelHelper.createCell(newRow, newRow, 5, 5, 5, samplesFile.getType(), sheet, row, styleNormalLeft, 0);
				ExcelHelper.createCell(newRow, newRow, 6, 6, 6, sampleManager.getSanplesPhasesNames(samplesFile.getUuid(), user.getLanguages().getId()), sheet, row, styleNormalLeft, 0);				
				ExcelHelper.createCell(newRow, newRow, 7, 7, 7, "", sheet, row, styleNormalCenter, 0);
				newRow ++;					
			}
			System.out.println("4");
			XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);			
			CellRangeAddressList addressList = new CellRangeAddressList(5, newRow -1, 6, 6);			
			XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(new String[]{"POSITIVO", "NEGATIVO"}); 
			XSSFDataValidation validation = (XSSFDataValidation)dvHelper.createValidation(dvConstraint, addressList);
			validation.setShowErrorBox(true);
			validation.setSuppressDropDownArrow(true);
			validation.setShowPromptBox(true);
			sheet.addValidationData(validation);			
			System.out.println("5");
			excelPathName = ConfigurationHelper.getSamplesPath()+"/"+excelName;
			System.out.println(excelPathName);
			try {
				System.out.println("51");
				FileOutputStream elFichero = new FileOutputStream(ConfigurationHelper.getSamplesPath()+"/"+excelName);
				System.out.println("52");
				libro.write(elFichero);
				System.out.println("53");
				elFichero.close();
			} catch (Exception e) {
				System.out.println("54");
				e.printStackTrace();
				System.out.println("55");
			}		
			System.out.println("6");
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_CREATEXLSSAMPLES +e.toString());
			return null;
		}finally{
			ctx.close();
		}
		System.out.println("7");
		return excelPathName;
	}

	@Override
	public List<Sample> getSampleList(Session session) {		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		SampleDao sampleManager = (SampleDao) ctx.getBean("SampleDaoImpl");
		UserDao userManager = (UserDao)ctx.getBean("UserDaoImpl");
		List<Sample> sampleList = new ArrayList<Sample>();		
		try{	
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			sampleList = sampleManager.getList(user);
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETSAMPLELIST + e.toString());

		    sampleList = null;
			
		}finally{
			ctx.close();
		}
		
		return sampleList;	
	}
			
}
