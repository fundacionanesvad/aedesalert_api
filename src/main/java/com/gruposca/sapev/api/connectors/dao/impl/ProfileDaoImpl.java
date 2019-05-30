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
import com.gruposca.sapev.api.helper.JpaResultHelper;

@Repository("ProfileDaoImpl")
public class ProfileDaoImpl  extends BaseDaoHibernate<Profiles, String> implements ProfileDao{

	public ProfileDaoImpl() { super(Profiles.class); }

	@Override
	public Profiles getLastProfile() throws Exception {
		Profiles profile = new Profiles();
		String sqlQuery = "SELECT P FROM Profiles AS P ORDER BY P.id DESC";
		List<Profiles> list = new ArrayList<Profiles>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Profiles.class);
        list = JpaResultHelper.getResultListAndCast(q);   
		if(list.size() > 0){
			profile = list.get(0);
		}
        return profile;	
	}

}
