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

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

public class ConfigurationHelper {

	public static final String CONNECTOR_PACKAGE         = "configuration.connector"; 
	public static final String DEBUG_MODE        		 = "configuration.debug"; 
	public static final String SESSION_MINUTES   		 = "configuration.session.minutes"; 
	public static final String PROFILE_ADMIN_ID  		 = "configuration.profile.adminId"; 
	public static final String VISIT_IMAGES      		 = "configuration.server.images"; 
	public static final String IMAGE_EXTENSION           = "configuration.image.extension";
	public static final String IMAGE_NO_DISPONOBLE       = "configuration.image.nodisponible"; 
	public static final String STATE_PLAN_FINISHED       = "configuration.statePlanFinished"; 
	public static final String STATE_PLAN_IN_PROGRESS    = "configuration.statePlanInProgress"; 
	public static final String STATE_PLAN_PLANNED        = "configuration.statePlanPlanned"; 
	public static final String STATE_INSPECTION_REALIZED = "configuration.stateInspectionRealized"; 
	public static final String STATE_INSPECTION_ACTIVE   = "configuration.stateInspectionActive"; 
	public static final String ALERT_FEVERISH            = "configuration.alertFeverish"; 
	public static final String ALERT_VISIT_LINK          = "configuration.alertVisitLink"; 
	public static final String ALERT_SAMPLE_POSITIVE     = "configuration.alertSamplePositive"; 
	public static final String ALERT_REPORT              = "configuration.alertReport"; 
	public static final String ALERT_REPORT_LINK         = "configuration.alertReportLink"; 
	public static final String ALERT_REPORT_LIST_LINK    = "configuration.alertReportListLink"; 	
	public static final String ALERT_NEW_FEBRILE         = "configuration.alertNewFebrile"; 
	public static final String ALERT_FEBRILE_LINK        = "configuration.alertFebrileLink"; 
	public static final String ALERT_NEW_TRAP            = "configuration.alertNewTrap"; 
	public static final String ALERT_TRAP_LINK           = "configuration.alertTrapsLink"; 
	public static final String ALERT_CHANGE_SCENE        = "configuration.alertChangeScene"; 
	public static final String ALERT_CHANGE_SCENE_LINK   = "configuration.alertChangeSceneLink"; 
	public static final String ALERT_NEW_INSPECTION      = "configuration.alertNewInspection"; 
	public static final String ALERT_NEW_INSPECTION_LINK = "configuration.alertInspectionLink"; 
	public static final String REPORT_IMAGE              = "configuration.reportImage"; 
	public static final String REPORT_JASPER_PATH        = "configuration.reportJasper"; 
	public static final String REPORT_JASPER_SEC_PATH    = "configuration.reportJasperSec"; 
	public static final String REPORT_PATH               = "configuration.reportPath"; 
	public static final String TRAPS_REPORT_PATH         = "configuration.trapsReportPath"; 
	public static final String SAMPLES_PATH              = "configuration.samplesPath"; 
	public static final String SYNC_FILE_PATH            = "configuration.syncFilePath"; 
	public static final String INSPECTOR_EXCEL_PATH      = "configuration.inspectorExcelPath"; 
	public static final String SUBSTITUTES_EXCEL_PATH    = "configuration.substitutesExcelPath"; 	
	public static final String URLEXPIRATION_MINUTES     = "configuration.urlexpiration.minutes"; 
	public static final String URL_RESTORE_PASSWORD      = "configuration.urlRestorePassword"; 
	public static final String EMAIL_USERNAME            = "configuration.username"; 
	public static final String EMAIL_PASSWORD            = "configuration.password"; 
	public static final String EMAIL_AUTENTICATION       = "configuration.autentication"; 
	public static final String EMAIL_HOST                = "configuration.host"; 
	public static final String EMAIL_PORT                = "configuration.port"; 
	public static final String EMAIL_SSL                 = "configuration.ssl"; 
	public static final String EMAIL_TLS                 = "configuration.tls"; 
	public static final String EMAIL_TEMPLATE_RESTORE    = "configuration.templateRestorePassword"; 
	public static final String EMAIL_INSPECTIONS         = "configuration.emailInspections"; 	
	public static final String EMAIL_TEMPLATE_ALERT      = "configuration.templateEmailAlert";
	public static final String EMAIL_TEMPLATE_INSPECTION_EXCEL = "configuration.templateEmailInspection";	
	public static final String EMAIL_TEMPLATE_ALERT_NEW_INSPECTION = "configuration.templateEmailAlertNewInspection";
	public static final String EMAIL_LINK                = "configuration.linkDefault"; 
	public static final String REPORT_PLAN_MAP           = "configuration.planMapReportJasper"; 
	public static final String SUBREPORT_PLAN_MAP        = "configuration.planMapSubReportJasper"; 
	public static final String AREA_TYPE_SECTOR          = "configuration.areaTypeSector"; 
	public static final String AREA_TYPE_BLOCK           = "configuration.areaTypeBlock"; 
	public static final String AREA_TYPE_EESS            = "configuration.areaTypeEESS"; 
	public static final String VISIT_INSPECTED           = "configuration.visit.inspected"; 
	public static final String VISIT_CLOSED              = "configuration.visit.closed"; 
	public static final String VISIT_RENUENT             = "configuration.visit.renuent"; 
	public static final String VISIT_ABANDONED           = "configuration.visit.abandoned"; 

	public static final String TRAPS_PATH                = "configuration.trapsPath"; 

	public static final String MAS_Z                     = "configuration.MAS_Z"; 
	public static final String MAS_e                     = "configuration.MAS_e"; 
	public static final String MAS_p                     = "configuration.MAS_p"; 
	public static final String REQUIRED_STAFF_PERCENTAJE = "configuration.requiredStaffPercentage"; 



	private static Properties mProperties;

	public static String getString(String key) throws IOException { 
		return getProperties().getProperty(key);		
	}
	
	public static String getConnector(){
		String returnedValue = "";
		
		try{
			returnedValue = getProperties().getProperty(CONNECTOR_PACKAGE);
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static int minutesSession(){
		int returnedValue = 10;
		
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(SESSION_MINUTES));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static boolean debugMode() {
		boolean returnedValue = false;
		
		try {
			returnedValue = Boolean.parseBoolean(getProperties().getProperty(DEBUG_MODE));
		} catch (Exception e) { }
		
		return returnedValue;
	}	
	
	public static int getProfileAdminId(){
		int returnedValue = 1;
		
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(PROFILE_ADMIN_ID));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getImagesPath(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(VISIT_IMAGES);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getImageExtension(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(IMAGE_EXTENSION);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getImageNoDisponible(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(IMAGE_NO_DISPONOBLE);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	
	public static int getStatePlanFinished(){
		int returnedValue = 7003;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(STATE_PLAN_FINISHED));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static int getStatePlanInProgress(){
		int returnedValue = 7002;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(STATE_PLAN_IN_PROGRESS));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static int getStatePlanPlanned(){
		int returnedValue = 7001;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(STATE_PLAN_PLANNED));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static int getStateInspectionRealized(){
		int returnedValue = 3003;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(STATE_INSPECTION_REALIZED));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static int getStateInspectionActive(){
		int returnedValue = 3002;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(STATE_INSPECTION_ACTIVE));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static int getAlertFeverish(){
		int returnedValue = 8003;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(ALERT_FEVERISH));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static int getAlertSamplePositive(){
		int returnedValue = 8002;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(ALERT_SAMPLE_POSITIVE));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	
	public static String getAlertVisitLink(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(ALERT_VISIT_LINK);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}	
	
	public static int getAlertNewFebrile(){
		int returnedValue = 8006;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(ALERT_NEW_FEBRILE));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getAlertFebrileLink(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(ALERT_FEBRILE_LINK);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static int getAlertNewTrap(){
		int returnedValue = 8007;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(ALERT_NEW_TRAP));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getAlertTrapLink(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(ALERT_TRAP_LINK);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}	
	
	
	public static int getAlertReport(){
		int returnedValue = 8005;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(ALERT_REPORT));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	
	public static String getAlertReportLink(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(ALERT_REPORT_LINK);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getAlertReportListLink(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(ALERT_REPORT_LIST_LINK);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getReportImage(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(REPORT_IMAGE);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getReportJasper(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(REPORT_JASPER_PATH);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getReportJasperSec(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(REPORT_JASPER_SEC_PATH);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getReportPath(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(REPORT_PATH);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getTrapsReportPath(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(TRAPS_REPORT_PATH);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	
	public static String getSamplesPath(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(SAMPLES_PATH);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getSyncFilePath(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(SYNC_FILE_PATH);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getInspectorExcelPath(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(INSPECTOR_EXCEL_PATH);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	
	public static String getSubstitutesExcelPath(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(SUBSTITUTES_EXCEL_PATH);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	private static Properties getProperties() throws IOException {
		if (mProperties == null) {
			InputStream input = ConfigurationHelper.class.getResourceAsStream("/configuration.properties");
			if (input != null) {
				mProperties = new Properties();
				mProperties.load(input);
				
				input.close();
				input = null;
			}
		}		
		return mProperties;
	}
	
	public static int getUrlExpirationMinutes(){
		int returnedValue = 10;
		
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(URLEXPIRATION_MINUTES));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getUrlRestorePass(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(URL_RESTORE_PASSWORD);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getEmailUserName(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(EMAIL_USERNAME);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	
	public static String getEmailUserPassword(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(EMAIL_PASSWORD);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}

	public static boolean getEmailAutentication() {
		boolean returnedValue = false;
		
		try {
			returnedValue = Boolean.parseBoolean(getProperties().getProperty(EMAIL_AUTENTICATION));
		} catch (Exception e) { }
		
		return returnedValue;
	}	
	
	public static String getEmailHost(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(EMAIL_HOST);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getEmailPort(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(EMAIL_PORT);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getEmailSsl(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(EMAIL_SSL);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getEmailTsl(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(EMAIL_TLS);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getEmailTemplateRestore(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(EMAIL_TEMPLATE_RESTORE);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	

	public static String getEmailInspections(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(EMAIL_INSPECTIONS);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getEmailTemplateAlert(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(EMAIL_TEMPLATE_ALERT);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getEmailTemplateInspectionExcel(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(EMAIL_TEMPLATE_INSPECTION_EXCEL);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getEmailTemplateAlertNewInspection(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(EMAIL_TEMPLATE_ALERT_NEW_INSPECTION);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	
	public static String getEmailLink(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(EMAIL_LINK);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getReportPlanMapJasper(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(REPORT_PLAN_MAP);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getSubReportPlanMapJasper(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(SUBREPORT_PLAN_MAP);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static int getAreaTypeSector(){
		int returnedValue = 9006;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(AREA_TYPE_SECTOR));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static int getAreaTypeBlock(){
		int returnedValue = 9007;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(AREA_TYPE_BLOCK));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	

	public static int getAreaTypeEESS(){
		int returnedValue = 9005;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(AREA_TYPE_EESS));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static int getVisitInspected(){
		int returnedValue = 2001;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(VISIT_INSPECTED));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static int getVisitClosed(){
		int returnedValue = 2002;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(VISIT_CLOSED));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static int getVisitRenuent(){
		int returnedValue = 2003;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(VISIT_RENUENT));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static int getVisitAbandoned(){
		int returnedValue = 2004;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(VISIT_ABANDONED));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getTrapsPath(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(TRAPS_PATH);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static int getAlertChangeScene(){
		int returnedValue = 8004;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(ALERT_CHANGE_SCENE));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getAlertChangeSceneLink(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(ALERT_CHANGE_SCENE_LINK);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static BigDecimal getMAS_Z(){
		BigDecimal returnedValue = new BigDecimal(1.96);
		try{
			returnedValue =  new BigDecimal(getProperties().getProperty(MAS_Z));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static BigDecimal getMAS_e(){
		BigDecimal returnedValue = new BigDecimal(0.05);
		try{
			returnedValue =  new BigDecimal(getProperties().getProperty(MAS_e));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	
	public static BigDecimal getMAS_p(){
		BigDecimal returnedValue = new BigDecimal(0.5);
		try{
			returnedValue =  new BigDecimal(getProperties().getProperty(MAS_p));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static BigDecimal getRequiredStaffPercentage(){
		BigDecimal returnedValue = new BigDecimal(0.5);
		try{
			returnedValue =  new BigDecimal(getProperties().getProperty(REQUIRED_STAFF_PERCENTAJE));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}	

	public static int getAlertNewInspection(){
		int returnedValue = 8008;
		try{
			returnedValue =  Integer.parseInt(getProperties().getProperty(ALERT_NEW_INSPECTION));
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	public static String getAlertNewInspectionLink(){
		String returnedValue = "";		
		try{
			returnedValue =  getProperties().getProperty(ALERT_NEW_INSPECTION_LINK);
		
		}catch (Exception e) {}
		
		return returnedValue;
	}
	
	
}
