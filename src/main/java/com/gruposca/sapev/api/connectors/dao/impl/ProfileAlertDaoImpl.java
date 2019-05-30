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
import com.gruposca.sapev.api.connectors.dao.model.ProfileAlerts;
import com.gruposca.sapev.api.connectors.dao.model.Profiles;
import com.gruposca.sapev.api.helper.JpaResultHelper;

@Repository("ProfileAlertDaoImpl")
public class ProfileAlertDaoImpl extends BaseDaoHibernate<ProfileAlerts, String> implements ProfileAlertDao{

	public ProfileAlertDaoImpl() { super(ProfileAlerts.class); }

	@Override
	public List<Integer> getListAlertTypes(Profiles profile) throws Exception {
		List<Integer> listAlertsTypes = new ArrayList<Integer>();
		String sqlQuery = "select P.tableElementsByAlertTypeId.id from ProfileAlerts as P where P.profiles = :profile ";
		Query q = this.getEntityManager().createQuery(sqlQuery, Integer.class);
        q.setParameter("profile", profile); 
        listAlertsTypes = JpaResultHelper.getResultListAndCast(q);  
		return listAlertsTypes;
	}

	@Override
	@Transactional
	public Integer deleteByProfile(Profiles profiles) throws Exception {
		String sqlQuery = "DELETE FROM ProfileAlerts AS P WHERE P.profiles = :profiles ";
	    Query q = this.getEntityManager().createQuery(sqlQuery);	    
        q.setParameter("profiles", profiles); 
        return q.executeUpdate();
	}

}
