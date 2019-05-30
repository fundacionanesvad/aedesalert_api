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
package com.gruposca.sapev.api.connectors.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Permissions;
import com.gruposca.sapev.api.connectors.dao.model.Profiles;
import com.gruposca.sapev.api.helper.JpaResultHelper;

@Repository("PermissionDaoImpl")
public class PermissionDaoImpl extends BaseDaoHibernate<Permissions, String> implements PermissionDao{

	public PermissionDaoImpl() { super(Permissions.class); }

	@Override
	public boolean userPermission(String type, String url, Integer profileId) throws Exception {
		String sqlQuery = "select PE from Permissions as PE where PE.modules.verb = :type AND PE.modules.module = :url and PE.profiles.id = :profileId ";
		Query q = this.getEntityManager().createQuery(sqlQuery, Permissions.class);
	    q.setParameter("type", type);    
	    q.setParameter("url", url);    
	    q.setParameter("profileId", profileId);        
	    List<Permissions> listPermissions = JpaResultHelper.getResultListAndCast(q);		
	    if(listPermissions!= null && listPermissions.size() ==1){
			return true;
		}else{
		   	return false;
		}	    
	}

	@Override
	public List<Integer> getModuleByProfile(Profiles profile) throws Exception {
		List<Integer> listModules = new ArrayList<Integer>();
		String sqlQuery = "select P.modules.id  from Permissions as P where P.profiles = :profiles ";
		Query q = this.getEntityManager().createQuery(sqlQuery, Integer.class);
        q.setParameter("profiles", profile); 
        listModules = JpaResultHelper.getResultListAndCast(q);  
		return listModules;
	}

	@Override
	public List<String> getModuleGroupByProfile(Profiles profile)throws Exception {
		List<String> listModules = new ArrayList<String>();
		String sqlQuery = "select DISTINCT M.group from Permissions as P RIGHT JOIN P.modules as M where P.profiles = :profiles ";
		Query q = this.getEntityManager().createQuery(sqlQuery, String.class);
        q.setParameter("profiles", profile); 
        listModules = JpaResultHelper.getResultListAndCast(q);  
		return listModules;
	}

	@Override
	@Transactional
	public Integer deleteByProfile(Profiles profiles) throws Exception {
		String sqlQuery = "DELETE FROM Permissions AS P WHERE P.profiles = :profiles ";
	    Query q = this.getEntityManager().createQuery(sqlQuery);	    
        q.setParameter("profiles", profiles); 
        return q.executeUpdate();
		
	}

	
}
