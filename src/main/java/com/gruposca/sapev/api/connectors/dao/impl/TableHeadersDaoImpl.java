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

import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Profiles;
import com.gruposca.sapev.api.connectors.dao.model.TableHeaders;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.modelview.TableElementListImpl;
import com.gruposca.sapev.api.modelview.TableListImpl;

@Repository("TableHeadersDaoImpl")
public class TableHeadersDaoImpl extends BaseDaoHibernate<TableHeaders, String> implements TableHeadersDao{

	
	public TableHeadersDaoImpl() { super(TableHeaders.class); }

	@Override
	public List<TableListImpl> getListTables(Profiles profile) throws Exception {
		
		List<TableListImpl> listTables = new ArrayList<TableListImpl>();
		TableListImpl tableListImpl;
		String sql= "";
		Query q;
		if(profile.getId() != ConfigurationHelper.getProfileAdminId()){			
			sql = "SELECT TH.id,TH.name, COUNT(TE.id) FROM TableHeaders AS TH LEFT JOIN TH.tableElementses AS TE WHERE TH.system = :profilePermission GROUP BY TH.id ";
			q = this.getEntityManager().createQuery(sql); 
			q.setParameter("profilePermission", false);
		
		} else{				
			sql = "SELECT TH.id,TH.name, COUNT(TE.id) FROM TableHeaders AS TH LEFT JOIN TH.tableElementses AS TE GROUP BY TH.id ";
			q = this.getEntityManager().createQuery(sql);
		}				
		
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
	    for (Object[] result : results) {		        
	    	tableListImpl = new TableListImpl(((Number) result[0]).intValue(), (String) result[1], ((Number) result[2]).intValue());
	    	listTables.add(tableListImpl);	    	
	    }	    
		return listTables;
	}

	@Override
	public Long numElements(TableHeaders tableHeaders) throws Exception {
		String sqlQuery = "select COUNT(TE) from TableElements as TE where TE.tableHeaders = :tableHeaders ";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Long.class);	    
        q.setParameter("tableHeaders", tableHeaders);  
        Long count = (Long)q.getSingleResult();
        return count;
	}

	@Override
	public List<TableElementListImpl> getListTableElements(Users user, Integer tableId) throws Exception {
		List<TableElementListImpl> listTableElements = new ArrayList<TableElementListImpl>();
		TableElementListImpl tableElementListImpl;
		String sql= "";
		sql = " SELECT TE.id,LB.value,TE.sort FROM TableHeaders AS TH LEFT JOIN TH.tableElementses AS TE LEFT JOIN TE.labelses AS LB "
			+ " WHERE TH.id = :tableId AND LB.languages = :language ORDER BY TE.sort";
			
		if(user.getProfiles().getId() != ConfigurationHelper.getProfileAdminId()){	
			sql += " AND TH.system = false ";				
		} 
			
		Query q = this.getEntityManager().createQuery(sql);
		q.setParameter("tableId", tableId);
		q.setParameter("language", user.getLanguages());
	
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
	    for (Object[] result : results) {		        
	    	tableElementListImpl = new TableElementListImpl((Integer)result[0], (String)result[1], (Integer)result[2]);
	    	listTableElements.add(tableElementListImpl);	    	
	    }
		    
		return listTableElements;
	}
}
