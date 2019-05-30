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
package com.gruposca.sapev.api.connectors.dao.impl;

import java.util.List;
import com.gruposca.sapev.api.connectors.dao.base.BaseDao;
import com.gruposca.sapev.api.connectors.dao.model.Profiles;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.modelview.ParamFilterUsers;
import com.gruposca.sapev.api.modelview.UserListImpl;
import com.gruposca.sapev.api.modelview.UserState;

public interface UserDao extends BaseDao<Users, String>{

	public Users login(String login, String password)throws Exception;
	
	public boolean isAuthorized(String token)throws Exception;
	
	public Users getUserByToken(String token) throws Exception;
	
	public Users getUserByEmail(String email) throws Exception;
	
	public int updateToken(Integer ID, String token)throws Exception;

	public int updateDateToken(String token)throws Exception;	
	
	public boolean exitsUserLogin(String login) throws Exception;
	
	public List<Users> getUserReportsAlertList(Integer profileId) throws Exception;
	
	public Users getUserByLogin(String login) throws Exception;

	public boolean userLocked(String login) throws Exception;
	
	public boolean urlIsAuthorized(String urlToken)throws Exception;
	
	public boolean exitsUserProfile(Profiles profile) throws Exception;
	
	public List<Users> getListUsersAlerts(Integer areaId, Integer alertTypeId) throws Exception;
	
	public List<UserListImpl> getListUsers(Users user, ParamFilterUsers paramFilterUsers) throws Exception;
	
	public Integer getCountList(Users user,ParamFilterUsers paramFilterUsers) throws Exception;
	
	public List<UserListImpl> getUsersInspection(Integer areaId) throws Exception;
	
	public List<UserListImpl> getUsersInspectionAdd(Integer areaId) throws Exception;
	
	public String getUserPlan(UserState user) throws Exception;

	public Long getUsersTokenexpiration(Users user) throws Exception;

}
