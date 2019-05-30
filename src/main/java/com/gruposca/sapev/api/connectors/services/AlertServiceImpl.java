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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.gruposca.sapev.api.connectors.dao.impl.AlertDao;
import com.gruposca.sapev.api.connectors.dao.impl.AreaDao;
import com.gruposca.sapev.api.connectors.dao.impl.LabelDao;
import com.gruposca.sapev.api.connectors.dao.impl.TableElementsDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.model.Alerts;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Febriles;
import com.gruposca.sapev.api.connectors.dao.model.Inspections;
import com.gruposca.sapev.api.connectors.dao.model.Samples;
import com.gruposca.sapev.api.connectors.dao.model.Scenes;
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.dao.model.Visits;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.helper.SendEmailHelper;
import com.gruposca.sapev.api.modelview.AlertImpl;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.AlertService;

public class AlertServiceImpl  extends AbsService implements AlertService{

	public AlertServiceImpl(AbsConnector connector) { super( connector); }


	
	@Override
	public List<AlertImpl> getAlertList(Session session, Boolean closed) {

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<AlertImpl> alertsList = new ArrayList<AlertImpl>();
		Alerts entityAlert = new Alerts();
		AlertImpl alertImpl;
		try{	
			AlertDao alertManager = (AlertDao) ctx.getBean("AlertDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			Users user = userManager.getUserByToken(session.getAuthorizationToken());	

			List<Alerts> entityAlertsList = alertManager.getListAlerts(user, closed);
			
			for(int i = 0; i < entityAlertsList.size(); i++){				
				entityAlert = entityAlertsList.get(i);	
				alertImpl = new AlertImpl(entityAlert.getId(), entityAlert.getDate().getTimeInMillis(), entityAlert.getLink(), entityAlert.getTableElementsByTypeId().getId());
				alertsList.add(alertImpl);
			}
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETSCENESLIST + e.toString());
		    alertsList = null;
			
		}finally{
			ctx.close();
		}
		
		return alertsList;
	}

	@Override
	public Boolean closeAlert(Integer id) {		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Alerts alert;
		boolean result = false;
		
		try{
			AlertDao alertManager = (AlertDao) ctx.getBean("AlertDaoImpl");
			alert = alertManager.find(id);
			alert.setClosed(true);
			alertManager.save(alert);
			result = true;	
			
		}catch (Exception e){
			
		}finally{
			ctx.close();
		}		
		return result;		
	}		
	
	public void generateSamplePositiveAlert(ClassPathXmlApplicationContext ctx, Samples sample){		
		
		try{		
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			AlertDao alertManager = (AlertDao) ctx.getBean("AlertDaoImpl");
			TableElements typeAlert = tableElementsManager.find(ConfigurationHelper.getAlertSamplePositive());			
			List<Users> listUserAlerts = userManager.getListUsersAlerts(sample.getHouses().getAreas().getId(), typeAlert.getId());	
			for(int i = 0; i<listUserAlerts.size(); i++){				
				Users users = listUserAlerts.get(i);			
				Alerts alert = new Alerts();	
				alert.setUsers(users);
				alert.setClosed(false);
				alert.setLink("");
				alert.setTableElementsByTypeId(typeAlert);
				alert.setDate(Calendar.getInstance());
				alertManager.save(alert);	
			}
		
		}catch (Exception e){
			logger.error("ERROR generateSamplePositiveAlert(String visitUuid)"+e.toString());		
		}
	} 		
	
	public void generateNewFebrileAlert(ClassPathXmlApplicationContext ctx, Febriles febrile){		
		try{		
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			AlertDao alertManager = (AlertDao) ctx.getBean("AlertDaoImpl");
			TableElements typeAlert = tableElementsManager.find(ConfigurationHelper.getAlertNewFebrile());
			String link = String.format(ConfigurationHelper.getAlertFebrileLink(),febrile.getId());
			List<Users> listUserAlerts = userManager.getListUsersAlerts(febrile.getAreas().getId(), typeAlert.getId());	

			for(int i = 0; i<listUserAlerts.size(); i++){				
				Users users = listUserAlerts.get(i);			
				Alerts alert = new Alerts();	
				alert.setUsers(users);
				alert.setClosed(false);
				alert.setLink(link);
				alert.setTableElementsByTypeId(typeAlert);
				alert.setDate(Calendar.getInstance());
				alertManager.save(alert);	
			}
		
		}catch (Exception e){
			logger.error("ERROR generateNewFebrileAlert(Integer febrileId)"+e.toString());		
		}
	} 	

	public void generateFebrilesHouseAlert(ClassPathXmlApplicationContext ctx, Visits visit){		
		try{		
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			AlertDao alertManager = (AlertDao) ctx.getBean("AlertDaoImpl");
			TableElements typeAlert = tableElementsManager.find(ConfigurationHelper.getAlertFeverish());			
			String link = String.format(ConfigurationHelper.getAlertVisitLink(),visit.getUuid().toString());
			List<Users> listUserAlerts = userManager.getListUsersAlerts(visit.getHouses().getAreas().getId(), typeAlert.getId());	
			for(int i = 0; i<listUserAlerts.size(); i++){				
				Users users = listUserAlerts.get(i);			
				Alerts alert = new Alerts();	
				alert.setUsers(users);
				alert.setClosed(false);
				alert.setLink(link);
				alert.setTableElementsByTypeId(typeAlert);
				alert.setDate(Calendar.getInstance());
				alertManager.save(alert);	
			}
		
		}catch (Exception e){
			logger.error("ERROR generateNewFebrileAlert(Integer febrileId)"+e.toString());		
		}
	} 	
	
	
	public void generateNewTrapAlert(ClassPathXmlApplicationContext ctx, Integer areaId){		
		try{		
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			AlertDao alertManager = (AlertDao) ctx.getBean("AlertDaoImpl");
			TableElements typeAlert = tableElementsManager.find(ConfigurationHelper.getAlertNewTrap());
			
			//Enlace al mapa de calor de las ovitrampas???
			String link = ConfigurationHelper.getAlertTrapLink();
			List<Users> listUserAlerts = userManager.getListUsersAlerts(areaId, typeAlert.getId());	

			for(int i = 0; i<listUserAlerts.size(); i++){				
				Users users = listUserAlerts.get(i);			
				Alerts alert = new Alerts();	
				alert.setUsers(users);
				alert.setClosed(false);
				alert.setLink(link);
				alert.setTableElementsByTypeId(typeAlert);
				alert.setDate(Calendar.getInstance());
				alertManager.save(alert);	
			}
		
		}catch (Exception e){
			logger.error("ERROR generateNewTrapAlert(ClassPathXmlApplicationContext ctx, Traps trap)"+e.toString());		
		}
	}	
	
	public void generateChangeSceneAlert(ClassPathXmlApplicationContext ctx, Scenes scene){		
		try{		
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			AlertDao alertManager = (AlertDao) ctx.getBean("AlertDaoImpl");
			LabelDao labelManager = (LabelDao) ctx.getBean("LabelDaoImpl");
			TableElements typeAlert = tableElementsManager.find(ConfigurationHelper.getAlertChangeScene());
			String link = String.format(ConfigurationHelper.getAlertChangeSceneLink(),scene.getId());
			List<Users> listUserAlerts = userManager.getListUsersAlerts(scene.getAreas().getId(), typeAlert.getId());	

			for(int i = 0; i<listUserAlerts.size(); i++){				
				Users users = listUserAlerts.get(i);			
				Alerts alert = new Alerts();	
				alert.setUsers(users);
				alert.setClosed(false);
				alert.setLink(link);
				alert.setTableElementsByTypeId(typeAlert);
				alert.setDate(Calendar.getInstance());
				alertManager.save(alert);	
				//Envío email				
				this.sendEmailAlert(users, labelManager.getValueElement(users.getLanguages(), typeAlert),ConfigurationHelper.getEmailLink(),ConfigurationHelper.getEmailLink());
			}
		
		}catch (Exception e){
			logger.error("ERROR generateChangeSceneAlert()"+e.toString());		
		}
	} 	
	
	
	public void generateNewInspectionAlert(ClassPathXmlApplicationContext ctx, Inspections inspection){		
		try{		
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			AlertDao alertManager = (AlertDao) ctx.getBean("AlertDaoImpl");
			LabelDao labelManager = (LabelDao) ctx.getBean("LabelDaoImpl");
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");

			TableElements typeAlert = tableElementsManager.find(ConfigurationHelper.getAlertNewInspection());
			String link = String.format(ConfigurationHelper.getAlertNewInspectionLink(),inspection.getId());
			List<Users> listUserAlerts = userManager.getListUsersAlerts(inspection.getAreas().getId(), typeAlert.getId());	
			
			Areas area = areaManager.find(inspection.getAreas().getId());
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String dateIni = df.format(inspection.getStartDate());
			String dateFinish = df.format(inspection.getFinishDate());
			
			for(int i = 0; i<listUserAlerts.size(); i++){				
				Users users = listUserAlerts.get(i);			
				Alerts alert = new Alerts();	
				alert.setUsers(users);
				alert.setClosed(false);
				alert.setLink(link);
				alert.setTableElementsByTypeId(typeAlert);
				alert.setDate(Calendar.getInstance());
				alertManager.save(alert);
				
				//Envío email	
				String linkEmail = ConfigurationHelper.getEmailLink()+"/#/app/inspections/"+inspection.getId();
				this.sendEmailAlertNewInspection(users, labelManager.getValueElement(users.getLanguages(), typeAlert), area.getName(), dateIni, dateFinish, linkEmail, "Ir a la inspección");
			}
		
		}catch (Exception e){
			logger.error("ERROR generateChangeSceneAlert()"+e.toString());		
		}
	} 	
	
	public boolean sendEmailAlert(Users user, String alert, String link, String text){
		
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
				tabla.put("alert" , alert);
				tabla.put("link" , link);
				tabla.put("text" , text);

				SendEmailHelper sendEmailHelper = new SendEmailHelper();
				to = user.getEmail();
				
				emailFrom =ConfigurationHelper.getEmailUserName();				
				emailSender = ConfigurationHelper.getEmailUserName();
				if(ConfigurationHelper.getEmailAutentication()){
					autentificarse=true;
					username =  ConfigurationHelper.getEmailUserName();
					password =  ConfigurationHelper.getEmailUserPassword();
				}	
				
				sendEmailHelper.setSender(ConfigurationHelper.getEmailUserName());
				sendEmailHelper.setFrom(emailFrom);
				sendEmailHelper.setFromAlias("Aedes Alert");
				sendEmailHelper.setTo(to);
				sendEmailHelper.setAutentificarse(autentificarse);
				sendEmailHelper.setUsername(username);
				sendEmailHelper.setPassword(password);
				sendEmailHelper.setHost(ConfigurationHelper.getEmailHost());
				sendEmailHelper.setSsl( ConfigurationHelper.getEmailSsl());
				sendEmailHelper.setPort( ConfigurationHelper.getEmailPort());
				
				sendEmailHelper.setAsunto("Notificación Aedes Alert");			
				sendEmailHelper.setTabla(tabla);
				sendEmailHelper.setPath(ConfigurationHelper.getEmailTemplateAlert());		
				
				if(sendEmailHelper.sendWithTemplate() == 1){
					sesultReset = true;				
				}				
			}		
			
		} catch (Exception e) {
			logger.error(RestErrorImpl.ERROR_SEND_URL_TO_RESTORE_PASSWORD + e.getMessage());
			
		}
		return sesultReset;
	}
	
	
	public boolean sendEmailAlertNewInspection(Users user, String alert, String areaName, String dateIni, String dateFinish, String link, String text){
		
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
				tabla.put("alert" , alert);
				tabla.put("link" , link);
				tabla.put("text" , text);
				tabla.put("fechas", dateIni+ " - "+dateFinish );
				tabla.put("zona", areaName);


				SendEmailHelper sendEmailHelper = new SendEmailHelper();
				to = user.getEmail();
				
				emailFrom =ConfigurationHelper.getEmailUserName();				
				emailSender = ConfigurationHelper.getEmailUserName();
				if(ConfigurationHelper.getEmailAutentication()){
					autentificarse=true;
					username =  ConfigurationHelper.getEmailUserName();
					password =  ConfigurationHelper.getEmailUserPassword();
				}	
				
				sendEmailHelper.setSender(ConfigurationHelper.getEmailUserName());
				sendEmailHelper.setFrom(emailFrom);
				sendEmailHelper.setFromAlias("Aedes Alert");
				sendEmailHelper.setTo(to);
				sendEmailHelper.setAutentificarse(autentificarse);
				sendEmailHelper.setUsername(username);
				sendEmailHelper.setPassword(password);
				sendEmailHelper.setHost(ConfigurationHelper.getEmailHost());
				sendEmailHelper.setSsl( ConfigurationHelper.getEmailSsl());
				sendEmailHelper.setPort( ConfigurationHelper.getEmailPort());
				
				sendEmailHelper.setAsunto("Notificación Aedes Alert");			
				sendEmailHelper.setTabla(tabla);
				sendEmailHelper.setPath(ConfigurationHelper.getEmailTemplateAlertNewInspection());		
				
				if(sendEmailHelper.sendWithTemplate() == 1){
					sesultReset = true;				
				}				
			}		
			
		} catch (Exception e) {
			logger.error(RestErrorImpl.ERROR_SEND_URL_TO_RESTORE_PASSWORD + e.getMessage());
			
		}
		return sesultReset;
	}
	
}
