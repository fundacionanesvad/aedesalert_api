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
import java.util.List;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.commons.codec.digest.DigestUtils;
import com.gruposca.sapev.api.connectors.dao.impl.AreaDao;
import com.gruposca.sapev.api.connectors.dao.impl.LanguageDao;
import com.gruposca.sapev.api.connectors.dao.impl.ProfileDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Languages;
import com.gruposca.sapev.api.connectors.dao.model.Profiles;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.modelview.ParamFilterUsers;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.User;
import com.gruposca.sapev.api.modelview.UserCreateIml;
import com.gruposca.sapev.api.modelview.UserGetImpl;
import com.gruposca.sapev.api.modelview.UserList;
import com.gruposca.sapev.api.modelview.UserListImpl;
import com.gruposca.sapev.api.modelview.UserState;
import com.gruposca.sapev.api.modelview.UserUpdateImpl;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.UserService;

public class UserServiceImpl extends AbsService implements UserService{
	
	public UserServiceImpl(AbsConnector connector) { super( connector); }
	
	@Override
	public User getUser(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Users entityUser = new Users();
		User user;
		try{
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			entityUser = userManager.find(id);
			
			user = new UserGetImpl(entityUser.getLogin(),
								   entityUser.getName(),
								   entityUser.getEmail(),
								   entityUser.isEnabled(),
								   entityUser.getProfiles().getId(),
								   entityUser.getLanguages().getId(),
								   entityUser.getAreas().getId(),
								   entityUser.getAreas().getName());
			}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETUSER + e.toString());
			user=null;
		}finally{
			ctx.close();
		}
        return  user;
	}


	@Override
	public Users getUserBySession(Session session) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Users user = new Users();
		try{
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			user = userManager.getUserByToken(session.getAuthorizationToken());
		}catch(Exception e){
		    logger.error(e.getMessage());
		}finally{
			ctx.close();
		}		
		return user;
	}

	@Override
	public Users saveUser(UserCreateIml user) {

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Users entityUser;
		boolean enabled = false;
		try{	
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");		
			ProfileDao profilesManager = (ProfileDao) ctx.getBean("ProfileDaoImpl");	
			LanguageDao languageManager = (LanguageDao) ctx.getBean("LanguageDaoImpl");
			AreaDao areaManager = (AreaDao)  ctx.getBean("AreaDaoImpl");
			if(!userManager.exitsUserLogin(user.getLogin())){
				Profiles profile = profilesManager.find(user.getProfileId());
				Languages language = languageManager.find(user.getLanguageId());
				Areas area = areaManager.find(user.getAreaId());
				if(user.getEnabled() != null) enabled = true;				
				
				entityUser = new Users(language, profile, user.getLogin(), DigestUtils.sha256Hex(user.getPassword1()), user.getName(),user.getEmail(), enabled, area, 0 );
				entityUser = userManager.save(entityUser);			
				if(entityUser != null){
					return entityUser;
				}else{
					return null;
				}		
			}else{
			    logger.error(RestErrorImpl.METHOD_SAVEUSER + RestErrorImpl.ERROR_USEREXIST);
				return null;
			}		

		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_SAVEUSER + e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public Users updateUser(Integer id, UserUpdateImpl user) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		boolean enabled = false;
		try{
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");		
			ProfileDao profilesManager = (ProfileDao) ctx.getBean("ProfileDaoImpl");	
			LanguageDao languageManager = (LanguageDao) ctx.getBean("LanguageDaoImpl");
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			Profiles profile = profilesManager.find(user.getProfileId());
			Languages language = languageManager.find(user.getLanguageId());
			Users updateUser = userManager.find(id);
			Areas area = areaManager.find(user.getAreaId());
			if(updateUser != null){
				
				if(user.getEnabled() != null && user.getEnabled()) enabled = true;				
				
				updateUser.setLogin(user.getLogin());			
				if(user.getPassword1() != null && !user.getPassword1().equals("")) {						
					updateUser.setPassword(DigestUtils.sha256Hex(user.getPassword1()));						
				}			
				updateUser.setName(user.getName());
				updateUser.setEmail(user.getEmail());
				updateUser.setAreas(area);
				updateUser.setEnabled(enabled);
				updateUser.setProfiles(profile);
				updateUser.setLanguages(language);		
				
				updateUser = userManager.save(updateUser);	
				
				if(updateUser != null){
					return updateUser;
				}else{
					return null;
				}				
			}else{
			    logger.error(RestErrorImpl.ERROR_GET_USER_ID);
				return null;
			}			
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_UPDATEUSER + e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public void deleteUser(Integer id) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{	
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");	
			Users user = userManager.find(id);
			userManager.delete(user);
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_DELETEUSER + e.toString());
		}finally{
			ctx.close();
		}		
	}

	@Override
	public Users unlockUser(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");		
		try{
			Users user = userManager.find(id);
			if(user != null){
				user.setLoginErrors(0);
				user = userManager.save(user);	
			}else{
			    logger.error(RestErrorImpl.ERROR_GET_USER_ID);
				return null;
			}		
	
			if(user != null){
				return user;
			}else{
				return null;
			}		
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_UNLOCKUSER + e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public UserList getUserList(Session session,ParamFilterUsers paramFilterUsers) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<UserListImpl> list = new ArrayList<UserListImpl>();
		UserList userList = new UserList();
		try{
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");			
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			list = userManager.getListUsers(user, paramFilterUsers);
			Integer count = userManager.getCountList(user, paramFilterUsers);	
			userList = new UserList(count, list);			

		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETUSERLIST + e.toString());
			userList = null;
			
		}finally{
			ctx.close();
		}
		return userList;
	}

	@Override
	public List<User> getUserList() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
        List<User> userList = new ArrayList<User>();
        Users entityUser = new Users();
        User user;
        try{
            UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
            List<Users> entityUserList = userManager.findAll();
            for(int i = 0; i < entityUserList.size(); i++){
                  entityUser = entityUserList.get(i);                
                user = new UserListImpl(entityUser.getId(),
                                        entityUser.getName(),
                                        entityUser.getEmail(),
                                        entityUser.isEnabled(),
                                        entityUser.getProfiles().getId(),
                                        entityUser.getAreas().getId(),
                                        entityUser.getLoginErrors());
                userList.add(user);
            }
            
        }catch (Exception e){
            logger.error(RestErrorImpl.METHOD_GETUSERLIST + e.toString());
            userList = null;
            
        }finally{
            ctx.close();
        }
        return userList;
     }

	
	@Override	
	public List<UserListImpl> getUserInspectionList(Integer areaId) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
        List<UserListImpl> userList = new ArrayList<UserListImpl>();       
        try{
            UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
            userList= userManager.getUsersInspection(areaId);            
            
        }catch (Exception e){
            logger.error(RestErrorImpl.METHOD_GETUSERINSPECTIONLIST + e.toString());
            userList = null;
            
        }finally{
            ctx.close();
        }
        return userList;
	}
	
	@Override	
	public List<UserListImpl> getUserInspectionListAdd(Integer areaId) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
        List<UserListImpl> userList = new ArrayList<UserListImpl>();       
        try{
            UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
            userList= userManager.getUsersInspectionAdd(areaId);            
            
        }catch (Exception e){
            logger.error(RestErrorImpl.METHOD_GETUSERINSPECTIONLIST + e.toString());
            userList = null;
            
        }finally{
            ctx.close();
        }
        return userList;
	}

	@Override
	public String getUserState(UserState user) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
        String userStateMensaje = "";       
        try{
            UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
            userStateMensaje= userManager.getUserPlan(user);            
            
        }catch (Exception e){
            logger.error(RestErrorImpl.METHOD_GETUSERINSPECTIONLIST + e.toString());
            userStateMensaje = null;
            
        }finally{
            ctx.close();
        }
        return userStateMensaje;
	}	
	

}
