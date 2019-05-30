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
 ******************************************************************************/
package com.gruposca.sapev.api.connectors.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gruposca.sapev.api.connectors.dao.impl.ModuleDao;
import com.gruposca.sapev.api.connectors.dao.impl.PermissionDao;
import com.gruposca.sapev.api.connectors.dao.impl.ProfileAlertDao;
import com.gruposca.sapev.api.connectors.dao.impl.ProfileDao;
import com.gruposca.sapev.api.connectors.dao.impl.TableElementsDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.model.Modules;
import com.gruposca.sapev.api.connectors.dao.model.Permissions;
import com.gruposca.sapev.api.connectors.dao.model.ProfileAlerts;
import com.gruposca.sapev.api.connectors.dao.model.Profiles;
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.modelview.Profile;
import com.gruposca.sapev.api.modelview.ProfileList;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.ProfileService;

public class ProfileServiceImpl extends AbsService implements ProfileService{
	
	public ProfileServiceImpl(AbsConnector connector) { super( connector); }

	@Override
	public List<ProfileList> getProfileList() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<ProfileList> profileList = new ArrayList<ProfileList>();
		Profiles entityProfile = new Profiles();
		ProfileList profile;
		try{
			ProfileDao profileManager = (ProfileDao) ctx.getBean("ProfileDaoImpl");
			List<Profiles> entityProfileList = profileManager.findAll();

			for(int i = 0; i < entityProfileList.size(); i++){				
				entityProfile = entityProfileList.get(i);				
				profile = new ProfileList(entityProfile.getId(),
						                      entityProfile.getName(),
						                      entityProfile.getDescription());
				profileList.add(profile);
			}
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETPROFILELIST + e.toString());
			profileList = null;
			
		}finally{
			ctx.close();
		}
		
		return profileList;
	}

	@Override
	public Profile getProfile(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Profile entityProfile = new Profile();
		List<Integer> listModules = new ArrayList<Integer>();
		List<Integer> listAlertsTypes = new ArrayList<Integer>();
		try{
			ProfileDao profileManager = (ProfileDao) ctx.getBean("ProfileDaoImpl");
			ProfileAlertDao profileAlertManager = (ProfileAlertDao) ctx.getBean("ProfileAlertDaoImpl");
			PermissionDao permissiomManager = (PermissionDao) ctx.getBean("PermissionDaoImpl");

			Profiles profiles = profileManager.find(id);
			if(profiles != null){
				listModules = permissiomManager.getModuleByProfile(profiles);
				listAlertsTypes = profileAlertManager.getListAlertTypes(profiles);		
			}
			entityProfile = new Profile(profiles.getName(),profiles.getDescription(), listModules, listAlertsTypes);			
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETPROFILE + e.toString());
			
		}finally{
			ctx.close();
		}		
		return entityProfile;
	}

	@Override
	public Boolean deleteProfile(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Boolean result = false;
		try{	
			ProfileDao profileManager = (ProfileDao) ctx.getBean("ProfileDaoImpl");	
			PermissionDao permissionManager = (PermissionDao) ctx.getBean("PermissionDaoImpl");
			ProfileAlertDao profileAlertManager =  (ProfileAlertDao) ctx.getBean("ProfileAlertDaoImpl");			
			UserDao userManager =  (UserDao) ctx.getBean("UserDaoImpl");			
			Profiles profile = profileManager.find(id);		
			if(!userManager.exitsUserProfile(profile)){
				permissionManager.deleteByProfile(profile);
				profileAlertManager.deleteByProfile(profile);
				profileManager.delete(profile);
				result = true;			}
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_DELETEPROFILE + e.toString());
		    return result;	
		}finally{
			ctx.close();
		}	
		return result;		
	}

	@Override
	public Profiles createProfile(Profile profile) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Profiles profiles;
		try{	
			ProfileDao profileManager = (ProfileDao) ctx.getBean("ProfileDaoImpl");	
			PermissionDao permissionManager = (PermissionDao) ctx.getBean("PermissionDaoImpl");
			ProfileAlertDao profileAlertManager =  (ProfileAlertDao) ctx.getBean("ProfileAlertDaoImpl");	
			ModuleDao modulesManager = (ModuleDao) ctx.getBean("ModuleDaoImpl");	 
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");		

			profiles = new Profiles(profile.getName(), profile.getDescription());
			profileManager.save(profiles);
			profiles = profileManager.getLastProfile();
			if(profiles != null){				
				for(int i = 0; i < profile.getModules().size(); i++){					
					Integer moduleId = profile.getModules().get(i);
					Modules modules = modulesManager.find(moduleId);
					Permissions permissions = new Permissions(modules, profiles);
					permissionManager.save(permissions);					
				}
				
				for(int j = 0; j < profile.getAlerts().size(); j++){					
					Integer alertTypeId = profile.getAlerts().get(j);
					TableElements tableElement = tableElementsManager.find(alertTypeId);
					ProfileAlerts profileAlerts = new ProfileAlerts(profiles, tableElement);
					profileAlertManager.save(profileAlerts);					
				}				
			}		
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_CREATEPROFILE + e.toString());
			return null;
		}finally{
			ctx.close();
		}			
		return profiles;
	}

	@Override
	public Profiles updateProfile(Integer id, Profile profile) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Profiles updateProfile;

		try{	
			ProfileDao profileManager = (ProfileDao) ctx.getBean("ProfileDaoImpl");	
			PermissionDao permissionManager = (PermissionDao) ctx.getBean("PermissionDaoImpl");
			ProfileAlertDao profileAlertManager =  (ProfileAlertDao) ctx.getBean("ProfileAlertDaoImpl");	
			ModuleDao modulesManager = (ModuleDao) ctx.getBean("ModuleDaoImpl");	 
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");					
			updateProfile = profileManager.find(id);			
			if(updateProfile != null){
				
				updateProfile.setName(profile.getName());
				updateProfile.setDescription(profile.getDescription());				
				profileManager.save(updateProfile);	
				
				if(updateProfile != null){
					permissionManager.deleteByProfile(updateProfile);
					profileAlertManager.deleteByProfile(updateProfile);
					
					for(int i = 0; i < profile.getModules().size(); i++){					
						Integer moduleId = profile.getModules().get(i);
						Modules modules = modulesManager.find(moduleId);
						Permissions permissions = new Permissions(modules, updateProfile);
						permissionManager.save(permissions);					
					}
					
					for(int j = 0; j < profile.getAlerts().size(); j++){					
						Integer alertTypeId = profile.getAlerts().get(j);
						TableElements tableElement = tableElementsManager.find(alertTypeId);
						ProfileAlerts profileAlerts = new ProfileAlerts(updateProfile, tableElement);
						profileAlertManager.save(profileAlerts);					
					}					
				}
				
			}else{
			    logger.error(RestErrorImpl.ERROR_GET_PROFILE_ID);
				return null;
			}			
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_UPDATEPROFILE + e.toString());
			return null;
		}finally{
			ctx.close();
		}
		return updateProfile;	
	}	
	

}
