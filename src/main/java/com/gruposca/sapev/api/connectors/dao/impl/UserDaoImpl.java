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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Profiles;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.modelview.ParamFilterUsers;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.UserListImpl;
import com.gruposca.sapev.api.modelview.UserState;

@Repository("UserDaoImpl")
public class UserDaoImpl extends BaseDaoHibernate<Users, String> implements UserDao{
	public UserDaoImpl() { super(Users.class); }
	
	
	@Override 
	public Users login(String login, String password) throws Exception {
		
		String sqlQuery = "select US from Users as US where US.login = :login and US.password = :password ";
		Query q = this.getEntityManager().createQuery(sqlQuery, Users.class);
	    q.setParameter("login", login);
	    q.setParameter("password", password);
	    Users users = (Users) JpaResultHelper.getSingleResultOrNull(q);
	    if (users != null) return users;
	    //else throw new NoResultException(RestErrorImpl.ERROR_LOGIN);
	    else return null;
	}
 
	@Override
	public boolean isAuthorized(String token) throws Exception{
		
        String sqlQuery = "select US from Users as US where US.token = :token  and US.tokenExpiration > CURRENT_TIMESTAMP  ";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Users.class);
        q.setParameter("token", token);    
        Users user = (Users) JpaResultHelper.getSingleResultOrNull(q);
        if (user != null) return true;
	    else return false;   
    }

	@Override
	public Users getUserByToken(String token) throws Exception {	
		
		String sqlQuery = "select US from Users as US where US.token = :token ";
		Query q = this.getEntityManager().createQuery(sqlQuery, Users.class);
	    q.setParameter("token", token);
	    Users users = (Users) JpaResultHelper.getSingleResultOrNull(q);
	    if (users != null) return users;
	    else throw new NoResultException(RestErrorImpl.ERROR_GETTING_USER_BY_TOKEN);
	}

	@Override
	@Transactional
	public int updateToken(Integer ID, String token) throws Exception {
		
		int updatedEntities = 0;					
		Users users = this.find(ID);		
		users.setToken(token);
		Calendar date = Calendar.getInstance();	
		int minutes = ConfigurationHelper.minutesSession();
		date.add(Calendar.MINUTE, minutes);
		users.setTokenExpiration(date);			
		
		if(this.save(users).getToken().equals(token))
			updatedEntities =1;			
				    
		return updatedEntities;
	}

	@Override
	@Transactional
	public int updateDateToken(String token) throws Exception {
		
		int updatedEntities = 0;				
		Users user = this.getUserByToken(token);		
		Calendar date = Calendar.getInstance();
		int minutes = ConfigurationHelper.minutesSession();
		date.add(Calendar.MINUTE, minutes);
		user.setTokenExpiration(date);			
		
		if(this.save(user).getToken().equals(token))
			updatedEntities =1;			
			    
		return updatedEntities;
	}
	

	@Override
	public boolean exitsUserLogin(String login) throws Exception {		

		String sqlQuery = "select US from Users as US where US.login = :login ";
		Query q = this.getEntityManager().createQuery(sqlQuery, Users.class);
	    q.setParameter("login", login);	    
	    if( q.getResultList().size() > 0 ){
	    	return true;
	    }else{ 
	    	return false;
	    }	
	}
	
	@Override
	public List<Users> getUserReportsAlertList(Integer profileId) throws Exception {
		String sqlQuery = "select US from Users as US where US.alerts = true and US.profiles.id = :profileId";
		List<Users> listUsers = new ArrayList<Users>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Users.class);
	    q.setParameter("profileId", profileId);	   
	    listUsers = JpaResultHelper.getResultListAndCast(q);       
		return listUsers;		
	}


	@Override
	public Users getUserByLogin(String login) throws Exception {
		String sqlQuery = "select US from Users as US where US.login = :login ";
		Query q = this.getEntityManager().createQuery(sqlQuery, Users.class);
	    q.setParameter("login", login);	    
	    Users users = (Users) JpaResultHelper.getSingleResultOrNull(q);
	    if (users != null) return users;
	    else throw new NoResultException(RestErrorImpl.ERROR_GETTING_USER_BY_LOGIN);
	}


	@Override
	public boolean userLocked(String login) throws Exception {
		String sqlQuery = "select US from Users as US where US.login = :login AND loginErrors = 10";
		Query q = this.getEntityManager().createQuery(sqlQuery, Users.class);
	    q.setParameter("login", login);	    
	    if( q.getResultList().size() > 0 ){
	    	return true;
	    }else{ 
	    	return false;
	    }	
	}

	@Override
	public Users getUserByEmail(String email) throws Exception {
		String sqlQuery = "select US from Users as US where US.email = :email ";
		Query q = this.getEntityManager().createQuery(sqlQuery, Users.class);
	    q.setParameter("email", email);	    
	    Users users = (Users) JpaResultHelper.getSingleResultOrNull(q);
	    if (users != null) return users;
	    else throw new NoResultException(RestErrorImpl.ERROR_GETTING_USER_BY_EMAIL);
	}

	@Override
	public boolean urlIsAuthorized(String urlToken) throws Exception {
		String sqlQuery = "select U from Users as U where U.urlToken = :urlToken and U.urlTokenExpiration > CURRENT_TIMESTAMP ";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Users.class);
        q.setParameter("urlToken", urlToken);  
        List<Users> listUsers = JpaResultHelper.getResultListAndCast(q);
	    if(listUsers!= null && listUsers.size() ==1){
	    	return true;
	    }else{
	    	return false;
	    }
	}

	@Override
	public boolean exitsUserProfile(Profiles profile) throws Exception {
		String sqlQuery = "select US from Users as US where US.profiles = :profiles ";
		Query q = this.getEntityManager().createQuery(sqlQuery, Users.class);
	    q.setParameter("profiles", profile);	    
	    if( q.getResultList().size() > 0 ){
	    	return true;
	    }else{ 
	    	return false;
	    }	
	}	
	
	@Override
	public List<Users> getListUsersAlerts(Integer areaId, Integer alertTypeId){
		List<Users> listUsers = new ArrayList<Users>();
		String sqlQuery = "SELECT DISTINCT U "
				+ " FROM AreaDescendants AS AD JOIN AD.areasByAreaId AS A JOIN A.userses AS U "
				+ " JOIN U.profiles AS P JOIN P.profileAlertses AS PA"
				+ " WHERE PA.tableElementsByAlertTypeId.id = :alertTypeId AND AD.areasByDescendantId.id = :areaId";
		
		
		Query q = this.getEntityManager().createQuery(sqlQuery, Users.class);
	    q.setParameter("areaId", areaId);	    
	    q.setParameter("alertTypeId", alertTypeId);	    

		listUsers = JpaResultHelper.getResultListAndCast(q);       
	    return listUsers;		
	}


	@Override
	public List<UserListImpl> getListUsers(Users user,ParamFilterUsers paramFilterUsers)throws Exception {
		
		String WHERE =" WHERE AD.areasByAreaId.id = "+ user.getAreas().getId();
		
		if(paramFilterUsers.getFilter().getName() != null){
			WHERE += " AND U.name LIKE '%"+paramFilterUsers.getFilter().getName()+"%'";			
		}
		if(paramFilterUsers.getFilter().getEmail() != null){
			WHERE += " AND U.email LIKE '%"+paramFilterUsers.getFilter().getEmail()+"%'";					
		}
		if(paramFilterUsers.getFilter().getEnabled() != null){
			if(paramFilterUsers.getFilter().getEnabled() == 0){
				WHERE += " AND U.enabled = false";	
			}else{
				WHERE += " AND U.enabled = true";			
			}
		}
		if(paramFilterUsers.getFilter().getProfile() != null){
			WHERE += " AND U.profiles.name LIKE '%"+paramFilterUsers.getFilter().getProfile()+"%'";		
		}	
		
		if(paramFilterUsers.getFilter().getArea() != null){
			WHERE += " AND U.areas.name LIKE '%"+paramFilterUsers.getFilter().getArea()+"%'";		
		}	
		
		String ORDER =" ORDER BY";
		if (paramFilterUsers.getSorting().getName() != null){
			ORDER += " U.name "+paramFilterUsers.getSorting().getName();
		}else if(paramFilterUsers.getSorting().getEmail() != null){
			ORDER += " U.email "+paramFilterUsers.getSorting().getEmail();			
		}else if(paramFilterUsers.getSorting().getProfile() != null){
			ORDER += " U.profiles.name "+paramFilterUsers.getSorting().getProfile();
		}else if(paramFilterUsers.getSorting().getEnabled() != null){
			ORDER += " U.enabled "+paramFilterUsers.getSorting().getEnabled();	
		}else if(paramFilterUsers.getSorting().getArea() != null){
			ORDER += " U.areas.name "+paramFilterUsers.getSorting().getArea();	
		}else{
			ORDER += " U.name "+paramFilterUsers.getSorting().getName();
		}		

		
		Integer init = (paramFilterUsers.getPage() - 1) * paramFilterUsers.getCount();		
		
		List<UserListImpl> list = new ArrayList<UserListImpl>();
		String sql = "SELECT U.id, U.name, U.email, U.enabled, U.profiles.id, U.areas.id, U.loginErrors "
				+ " FROM AreaDescendants AS AD JOIN AD.areasByDescendantId AS A  JOIN A.userses AS U "+ WHERE + ORDER  ;
		
		Query q = this.getEntityManager().createQuery(sql);							
	    q.setFirstResult(init);
	    q.setMaxResults(paramFilterUsers.getCount());
		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		for (Object[] result : results) {		
			
			UserListImpl userList = new UserListImpl((Integer) result[0],
												  (String) result[1],
												  (String) result[2],
												  (Boolean)result[3],
												  (Integer) result[4],
												  (Integer) result[5],
												  (Integer) result[6]);
			
			list.add(userList);	
		}
		System.out.println("#"+sql);
		return list;
	}


	@Override
	public Integer getCountList(Users user,ParamFilterUsers paramFilterUsers)throws Exception {
		
		String WHERE =" WHERE AD.areasByAreaId.id = "+ user.getAreas().getId();
		
		if(paramFilterUsers.getFilter().getName() != null){
			WHERE += " AND U.name LIKE '%"+paramFilterUsers.getFilter().getName()+"%'";			
		}
		if(paramFilterUsers.getFilter().getEmail() != null){
			WHERE += " AND U.email LIKE '%"+paramFilterUsers.getFilter().getEmail()+"%'";					
		}
		if(paramFilterUsers.getFilter().getEnabled() != null){
			if(paramFilterUsers.getFilter().getEnabled() == 0){
				WHERE += " AND U.enabled = false";	
			}else{
				WHERE += " AND U.enabled = true";			
			}
		}
		if(paramFilterUsers.getFilter().getProfile() != null){
			WHERE += " AND U.profiles.name LIKE '%"+paramFilterUsers.getFilter().getProfile()+"%'";		
		}			
		
		String sql = "SELECT U.id, U.name, U.email, U.enabled, U.profiles.id, U.areas.id, U.loginErrors "
				+ " FROM AreaDescendants AS AD JOIN AD.areasByDescendantId AS A  JOIN A.userses AS U "+ WHERE  ;
	
		Query q = this.getEntityManager().createQuery(sql);		
		
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
	    System.out.println("#2"+sql);
	    if(results != null){
	    	return results.size();
	    }else{
	    	return 0;
	    }	
		
	}


	@Override
	public List<UserListImpl> getUsersInspection(Integer areaId) throws Exception {
		List<UserListImpl> users = new ArrayList<UserListImpl>();
		
		String queryString = "SELECT Users.id, Users.name, Users.email, Users.enabled, Users.profileId, Users.areaId, Users.loginErrors FROM Users" + 
				" WHERE Users.profileId = 2 AND Users.enabled = TRUE ORDER BY Users.name";
		System.out.println("#1"+queryString);
		Query q = this.getEntityManager().createNativeQuery(queryString);	
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
	    for (Object[] result : results) { 	
	    	
	    	UserListImpl user = new UserListImpl((Integer)result[0],	
		    									(String)result[1],
		    									(String)result[2],
		    									(Boolean)result[3],
		    									(Integer)result[4],
		    									(Integer)result[5],
		    									(Integer)result[6]);				
	    	users.add(user);
		}	    
		return users;    
	}
	@Override
	public List<UserListImpl> getUsersInspectionAdd(Integer areaId) throws Exception {
		List<UserListImpl> users = new ArrayList<UserListImpl>();
		
		String queryString = "SELECT Users.id, Users.name, Users.email, Users.enabled, Users.profileId, Users.areaId, Users.loginErrors FROM Users" + 
				" INNER JOIN AreaDescendants ON Users.areaId = AreaDescendants.areaId WHERE AreaDescendants.descendantId = "+areaId +
				" AND Users.profileId = 2 AND Users.enabled = TRUE ORDER BY Users.name";
		
		System.out.println("#1"+queryString);
		Query q = this.getEntityManager().createNativeQuery(queryString);	
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
	    for (Object[] result : results) { 	
	    	
	    	UserListImpl user = new UserListImpl((Integer)result[0],	
		    									(String)result[1],
		    									(String)result[2],
		    									(Boolean)result[3],
		    									(Integer)result[4],
		    									(Integer)result[5],
		    									(Integer)result[6]);				
	    	users.add(user);
		}	    
		return users;    
	}
	@Override
	public String getUserPlan(UserState user) throws Exception {
		String listAreas="";
		int cont=0;
		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fr2 = new SimpleDateFormat("dd/MM/yyyy");
		
		String queryString = "SELECT COUNT(*),A.name " + 
				"FROM Plans AS P " + 
				"INNER JOIN Inspections AS I ON I.id=P.inspectionId " + 
				"INNER JOIN Areas AS A ON A.id=I.areaId " + 
				"WHERE P.userId="+user.getUserId()+" AND P.date='"+fr.format(new Date(user.getDate()))+"'";
		
		Query q = this.getEntityManager().createNativeQuery(queryString);
		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
		for (Object[] result : results) {
			 cont++;
			 if(results.size()>1 && result[1]!=null) {
				 
				 
				 if(results.size()-1==cont) {
					 listAreas+=(String)result[1]+" y "; 
				 }else if(results.size()==cont) {
					 listAreas+=(String)result[1]; 
				 }else {
					 listAreas+=(String)result[1]+", ";
				 }
			 }
			 else if(result[1]!=null){
				 listAreas+=(String)result[1];
			 }else {
				 listAreas="";
			 }
			 
		}
		String mensaje="Este inspector esta ya planificado para el dia "+fr2.format(new Date(user.getDate()))+" para el establecimiento "+listAreas;
		if(results.size()>1) {
			 mensaje="Este inspector esta ya planificado para el dia "+fr2.format(new Date(user.getDate()))+" para los establecimientos "+listAreas;
		}else if(listAreas==""){
			System.out.println(mensaje);
			mensaje="Empty";
			System.out.println(mensaje);
		}
		System.out.println(results.size()+","+listAreas);
		return mensaje;
	}	
	
	@Override
	public Long getUsersTokenexpiration(Users user) throws Exception {
		String sqlQuery = "select US from Users as US where US.id = :userId ";
		Query q = this.getEntityManager().createQuery(sqlQuery, Users.class);
	    q.setParameter("userId", user.getId());	    
	    Users userReturn = (Users) JpaResultHelper.getSingleResultOrNull(q);
	    return userReturn.getTokenExpiration().getTimeInMillis();	   	
	}	

}
