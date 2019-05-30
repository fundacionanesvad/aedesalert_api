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
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Symptoms;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.helper.JpaResultHelper;

@Repository("SymptomDaoImpl")
public class SymptomDaoImpl extends BaseDaoHibernate<Symptoms, String> implements SymptomDao{

	public SymptomDaoImpl() { super(Symptoms.class); }

	@Override
	public List<String> getSymptomList(UUID personUuid, UUID visitUuuid, Users user) throws Exception {
		String sqlQuery = "SELECT LA.value FROM Symptoms AS SY RIGHT JOIN SY.tableElements AS TE LEFT JOIN TE.labelses AS LA WHERE SY.persons.uuid = :personUuid AND SY.visits.uuid = :visitUuuid AND LA.languages.id = :languaje";
		List<String> symotomsList = new ArrayList<String>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, String.class);
        q.setParameter("personUuid", personUuid);  
        q.setParameter("visitUuuid", visitUuuid);  
        q.setParameter("languaje", user.getLanguages().getId());        
        symotomsList = JpaResultHelper.getResultListAndCast(q);    
		return symotomsList;	
	}
}
