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
package com.gruposca.sapev.api.helper;

import java.math.BigDecimal;
import java.sql.Date;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {
	
	public static XSSFFont createFont(XSSFWorkbook libro, boolean bold, short size) {
		XSSFFont font = libro.createFont();
		font.setFontName("Calibri");
		if(bold) {
			font.setBold(true);
		}
		font.setFontHeightInPoints(size);	
		return font;		
	}
	
	public static XSSFCellStyle setBorder(XSSFCellStyle style , short typeBorder) {		
		style.setBorderBottom(typeBorder);
		style.setBorderLeft(typeBorder);
		style.setBorderRight(typeBorder);
		style.setBorderTop(typeBorder);		
		return style;		
	}
	
	
	public static XSSFCellStyle createStyle(XSSFWorkbook libro, short fontSize, boolean bold, short typeAling, boolean border, short typeBorder, int typeFormat ) {
		XSSFCellStyle style = libro.createCellStyle();
		CreationHelper createHelper = libro.getCreationHelper();

		XSSFDataFormat df = libro.createDataFormat();
		style.setFont(createFont(libro, bold, fontSize));	
		style.setAlignment(typeAling);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);	
		if(border) {
			style = setBorder(style,typeBorder);	  	
		}
		style.setWrapText(true);
		
		switch (typeFormat) {
	        case 1: style.setDataFormat(df.getFormat("0"));   
	                break;
	        case 2: style.setDataFormat(createHelper.createDataFormat().getFormat("dd-mmm"));   
            	break;   
	        case 3: style.setDataFormat(createHelper.createDataFormat().getFormat("0.000000"));   
        		    break;   
	        case 4: style.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));   
		    	    break;   
	        default: break;
		}
		style.setWrapText(true);

	  	return style;
	}

	
	public static CellRangeAddress createCell(int firstRow,int lastRow, int firstCol, int lastCol, int numCol, String textCell, XSSFSheet sheet, XSSFRow row, XSSFCellStyle style, Integer width){
		CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
		sheet.addMergedRegion(cellRangeAddress);
		XSSFCell cell = row.createCell(numCol);
		XSSFRichTextString text = new XSSFRichTextString(textCell);
		cell.setCellValue(text);
		cell.setCellStyle(style);
		if(width > 0){
			sheet.setColumnWidth(numCol, width);
		}
		return cellRangeAddress;		
	}
	
	public static void createCellString(XSSFRow row, short numberCell, String text, XSSFCellStyle style ){
		XSSFCell celda = row.createCell((short)numberCell);
		XSSFRichTextString texto = new XSSFRichTextString(text);
		celda.setCellValue(texto);
		celda.setCellStyle(style);		
	}	
	
	public static CellRangeAddress createCellDate(int firstRow,int lastRow, int firstCol, int lastCol, int numCol, Date date, XSSFSheet sheet, XSSFRow row, XSSFCellStyle style, int width){
		CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
		sheet.addMergedRegion(cellRangeAddress);
		XSSFCell cell = row.createCell(numCol);
		cell.setCellValue(date);
		cell.setCellStyle(style);
		if(width > 0){
			sheet.setColumnWidth(numCol, width);
		}
		return cellRangeAddress;	
	}
	
	public static void createCellDecimal(XSSFRow row, short numberCell, BigDecimal number, XSSFCellStyle style ){
		XSSFCell celda = row.createCell((short)numberCell);
		celda.setCellValue(number.doubleValue());		
		celda.setCellStyle(style);	
	}	
	
	public static void createCellByte(XSSFRow row, short numberCell, byte number, XSSFCellStyle style ){
		XSSFCell celda = row.createCell((short)numberCell);
		celda.setCellValue(number);		
		celda.setCellStyle(style);	
	}	
	
	public static void createCellInteger(XSSFRow row, short numberCell, Integer number, XSSFCellStyle style ){
		XSSFCell celda = row.createCell((short)numberCell);
		celda.setCellValue(number);		
		celda.setCellStyle(style);	
	}	
	
	public static void setBorder(Short typeBorder, CellRangeAddress cellRangeAddress, XSSFSheet hoja, XSSFWorkbook libro){		
		RegionUtil.setBorderTop(typeBorder, cellRangeAddress, hoja, libro);
		RegionUtil.setBorderLeft(typeBorder, cellRangeAddress, hoja, libro);
		RegionUtil.setBorderRight(typeBorder, cellRangeAddress, hoja, libro);
		RegionUtil.setBorderBottom(typeBorder, cellRangeAddress, hoja, libro);
	}
	
	
	public static void createCellFormula(XSSFRow row, short numberCell, String formula, XSSFCellStyle style ){
		XSSFCell celda = row.createCell((short)numberCell);
		celda.setCellType(XSSFCell.CELL_TYPE_FORMULA);
		celda.setCellFormula(formula);		
		celda.setCellStyle(style);	
	}	
	
	public static void createCellFormulaEvaluate(XSSFRow row, short numberCell, String formula, XSSFCellStyle style, FormulaEvaluator evaluator){
		XSSFCell celda = row.createCell((short)numberCell);
		celda.setCellType(XSSFCell.CELL_TYPE_FORMULA);
		celda.setCellFormula(formula);		
		celda.setCellStyle(style);	
		evaluator.evaluateFormulaCell(celda);		
	}
	
	
	
	
	
	
	
	
}
