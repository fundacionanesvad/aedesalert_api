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
import com.gruposca.sapev.api.connectors.dao.model.Persons;
import com.gruposca.sapev.api.helper.JpaResultHelper;

@Repository("PersonDaoImpl")
public class PersonDaoImpl  extends BaseDaoHibernate<Persons, String> implements PersonDao{

	public PersonDaoImpl() { super(Persons.class); }

	@Override
	public List<Persons> getPersonList(UUID visitUuid) throws Exception {
		List<Persons> personsList = new ArrayList<Persons>();

		try{
		String sqlQuery  = "SELECT PE FROM Visits AS VI LEFT JOIN VI.houses AS HO RIGHT JOIN HO.personses AS PE WHERE VI.uuid = :visitUuid ORDER BY PE.name";
			
	    Query q = this.getEntityManager().createQuery(sqlQuery, Persons.class);
        q.setParameter("visitUuid", visitUuid);  
        personsList = JpaResultHelper.getResultListAndCast(q);    
		}catch(Exception e){
		    logger.error("ERROR getPersonList(UUID visitUuid) " +e.toString());

		}
		return personsList;	
	}

	@Override
	public List<Persons> getPersonLisByHouse(UUID housetUuid) throws Exception {
		String sqlQuery  = "SELECT PE FROM Persons AS PE WHERE PE.houses.uuid = :housetUuid ";
		List<Persons> personsList = new ArrayList<Persons>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Persons.class);
        q.setParameter("housetUuid", housetUuid);  
        personsList = JpaResultHelper.getResultListAndCast(q);   
		return personsList;	
	}
}
