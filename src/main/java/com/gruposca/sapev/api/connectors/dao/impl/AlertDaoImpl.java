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

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Alerts;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.helper.JpaResultHelper;


@Repository("AlertDaoImpl")
public class AlertDaoImpl extends BaseDaoHibernate<Alerts, String> implements AlertDao{

	public AlertDaoImpl() { super(Alerts.class); }	
	
	@Override
	public List<Alerts> getListAlerts(Users user, Boolean closed) throws Exception {		
		String sqlQuery = "select AL from Alerts as AL where AL.closed = :closed AND AL.users = :user";
		List<Alerts> listAlerts = new ArrayList<Alerts>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Alerts.class);
        q.setParameter("closed", closed); 
        q.setParameter("user", user); 
	    listAlerts = JpaResultHelper.getResultListAndCast(q);       
		return listAlerts;
	}	
	
	@Override
	public boolean exitsAlert(String uuid, int typeId, int userId) throws Exception {
		String queryString =  " SELECT * "
							+ " FROM Alerts"
							+ " WHERE link LIKE '%"+uuid+"%' AND typeId ="+ typeId +" AND userId = "+ userId;
		
		Query q = this.getEntityManager().createNativeQuery(queryString);	 
		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		if(results.size() > 0){
			return true;			
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public Integer deleteAlert(String link) throws Exception {
		String sqlQuery = "DELETE FROM Alerts AS A WHERE A.link = :link ";
	    Query q = this.getEntityManager().createQuery(sqlQuery);	    
        q.setParameter("link", link); 
        return q.executeUpdate();
 	}
	
}
