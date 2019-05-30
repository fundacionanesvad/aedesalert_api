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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gruposca.sapev.api.connectors.dao.impl.AreaDao;
import com.gruposca.sapev.api.connectors.dao.impl.PermissionDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.helper.SendEmailHelper;
import com.gruposca.sapev.api.modelview.EmailToRestorePass;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.RestorePass;
import com.gruposca.sapev.api.modelview.SecurityResult;
import com.gruposca.sapev.api.modelview.SecurityResult.Result;
import com.gruposca.sapev.api.modelview.SecurityResultImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.SessionImpl;
import com.gruposca.sapev.api.modelview.UserPermission;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.SecurityService;

public class SecurityServiceImpl extends AbsService implements SecurityService{

	public SecurityServiceImpl(AbsConnector connector) { super(connector); }

	@Override
	public SecurityResult validate(Session session) {
		String authorization =null;
		boolean authorized=false;
		if(session != null){
			authorization = session.getAuthorizationToken();
			if(authorization==null){
				return new SecurityResultImpl(Result.NF);
			}else{
				ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
				UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
				try {
					authorized = userManager.isAuthorized(authorization);
					if(authorized)  userManager.updateDateToken(authorization);					
					
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					ctx.close();
				}

				if(authorized){
					return new SecurityResultImpl(Result.OK);
				}else{
					return new SecurityResultImpl(Result.KO);
				}
			}
		}else{
			return new SecurityResultImpl(Result.NF);
		}
	}
	
	

	@Override
	public Session authorizate(String login, String password) {
		String newToken=null;
		Session sessionNew=null;
		List<String> listModules = new ArrayList<String>();

		try {
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			PermissionDao permissionManager = (PermissionDao) ctx.getBean("PermissionDaoImpl");
			try {
				if(!userManager.userLocked(login)){

					Users user = userManager.login(login,DigestUtils.sha256Hex(password));
					if(user != null){						
						if(user.getLoginErrors() > 0){
							user.setLoginErrors(0);
							userManager.save(user);	
						}						
						newToken=genetareHash();			
						int result = userManager.updateToken(user.getId(), newToken);
						if(result==1){						
							listModules = permissionManager.getModuleGroupByProfile(user.getProfiles());								
							sessionNew = new SessionImpl(newToken, user.getName(), user.getId(), user.getAreas().getId(),listModules,!user.isEnabled(), userManager.getUsersTokenexpiration(user));
						}
					}else{// incrementamos el numero de intentos de login con error si existe un usuario con el nombre indicado					
		
						user = userManager.getUserByLogin(login);			
						if(user != null){
							user.setLoginErrors(user.getLoginErrors()+1);
							userManager.save(user);		
							if(user.getLoginErrors() >= 10){
								sessionNew = new SessionImpl(null, user.getName(), user.getId(), user.getAreas().getId(),listModules,true, null);
							}
						}				
					}					
				}else{
					Users user = userManager.getUserByLogin(login);			
					sessionNew = new SessionImpl(null, user.getName(), user.getId(), user.getAreas().getId(),listModules,true, null);		
				}
				
			} catch (Exception e) {
			    logger.error(e.getMessage());

			}finally{
				ctx.close();
			}

		} catch (Exception e) {
		    logger.error(RestErrorImpl.ERROR_GENERAL_LOGIN + e.getMessage());
		}
		return sessionNew;
	}	
	
	
	private String genetareHash() throws NoSuchAlgorithmException {
		String retunedValue = null;		
		Calendar date = GregorianCalendar.getInstance(Locale.getDefault());
		String   localTime = Long.toString(date.getTimeInMillis());		
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(localTime.getBytes());		
		retunedValue = digest.digest().toString();		
		return retunedValue;
	}

	@Override
	public UserPermission userPermission(String type, String url, Session session) {
		Users user;
		boolean permission = false;
		UserPermission userPermission = new UserPermission();
		try {
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
			PermissionDao permissionManager = (PermissionDao) ctx.getBean("PermissionDaoImpl");		

			try {				
				user= getConnector().getUserService().getUserBySession(session);	
				permission = permissionManager.userPermission(type, url, user.getProfiles().getId());
				userPermission = new UserPermission(user, permission);
				
			} catch (Exception e) {
				logger.error(RestErrorImpl.ERROR_GETTING_USER_PERMISSION + e.getMessage());
				e.printStackTrace();
			}finally{
				ctx.close();
			}

		} catch (Exception e) {
			logger.error(RestErrorImpl.ERROR_GENERAL_USER_PERMISSION + e.getMessage());
		}
		
		return userPermission;
	}

	@Override
	public boolean userAreaPermission(Session session, Integer areaId) {
		Users user;
		boolean returnedValue = false;
		
		try {
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");		

			try {				
				user= getConnector().getUserService().getUserBySession(session);					
				returnedValue = areaManager.userAreaPermission(user.getAreas().getId(), areaId);
			
			} catch (Exception e) {
				logger.error(RestErrorImpl.ERROR_GETTING_USERAREA_PERMISSION + e.getMessage());
				e.printStackTrace();
			}finally{
				ctx.close();
			}

		} catch (Exception e) {
			logger.error(RestErrorImpl.ERROR_GETTING_USERAREA_PERMISSION + e.getMessage());
		}		
		return returnedValue;
	}

	@Override
	public boolean validateAndRestore(RestorePass restorePass) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
		Users user;
		Boolean answer= false;
		try{			
			user = userManager.getUserByLogin(restorePass.getLogin());			
			if(user != null){				
				user.setPassword(DigestUtils.sha256Hex(restorePass.getNewPass()));
				user.setUrlToken(null);
				user.setUrlTokenExpiration(null);
				user.setLoginErrors(0);
				userManager.save(user);
				answer =  true;				
			} 			
		}catch(Exception e){
			logger.error(RestErrorImpl.ERROR_VALIDATE_RESTORE_PASSWORD + e.getMessage());
		}finally{
			ctx.close();
		}
		return answer;
	}

	@Override
	public Boolean sendUrl(EmailToRestorePass emailToRestorePass) {
		Users user = null;
		Boolean sesultReset = false;
		boolean autentificarse = false;
		String to = "";
		String emailFrom = "";
		String emailSender = "";
		String username = "";
		String password = "";
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		SecureRandom random = new SecureRandom();

		try {
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			user = userManager.getUserByEmail(emailToRestorePass.getEmail().trim());
			
			if(user != null){				
				//Se genera un token para la url y la fecha de expiracion de la url
				String urlToken = new BigInteger(300, random).toString(32);
				
				Calendar date = Calendar.getInstance();
				int minutes = ConfigurationHelper.getUrlExpirationMinutes();
				date.add(Calendar.MINUTE, minutes);
				
				user.setUrlToken(urlToken);
				user.setUrlTokenExpiration(date);
				userManager.save(user);

				String urlRestorePass = ConfigurationHelper.getUrlRestorePass()+urlToken;
				Hashtable<String,String> tabla=new Hashtable<String,String>();
				tabla.put("url" , urlRestorePass);

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
				sendEmailHelper.setAutentificarse(autentificarse);
				sendEmailHelper.setUsername(username);
				sendEmailHelper.setPassword(password);
				sendEmailHelper.setHost(ConfigurationHelper.getEmailHost());
				sendEmailHelper.setSsl( ConfigurationHelper.getEmailSsl());
				sendEmailHelper.setPort( ConfigurationHelper.getEmailPort());
				sendEmailHelper.setAsunto("Solicitud cambio contraseña ");			
				sendEmailHelper.setTabla(tabla);
				sendEmailHelper.setPath(ConfigurationHelper.getEmailTemplateRestore());		
				
				if(sendEmailHelper.sendWithTemplate() == 1){
					sesultReset = true;				
				}				
			}		
			
		} catch (Exception e) {
			logger.error(RestErrorImpl.ERROR_SEND_URL_TO_RESTORE_PASSWORD + e.getMessage());
			
		}finally{
			ctx.close();
		}		
		return sesultReset;
	}

	@Override
	public SecurityResult validateUrl(String urlToken) {
		boolean authorized=false;
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
		try {
			authorized = userManager.urlIsAuthorized(urlToken);
		} catch (Exception e) {
			logger.error(" SecurityServiceImpl.class - validateUrl("+urlToken+") =>"+e.toString());
			e.printStackTrace();
		}finally{
			ctx.close();
		}

		if( authorized){			
			return new SecurityResultImpl(Result.OK);
		}else{
			logger.error(" SecurityServiceImpl.class - validateUrl("+urlToken+") => NO AUTORIZADO!!!");
			return new SecurityResultImpl(Result.KO);
		}		
	}
}
