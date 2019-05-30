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

import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Modules;
import com.gruposca.sapev.api.helper.JpaResultHelper;

@Repository("ModuleDaoImpl")
public class ModuleDaoImpl extends BaseDaoHibernate<Modules, String> implements ModuleDao{

	public ModuleDaoImpl() { super(Modules.class); }
	
	@Override
	public Modules getModule(String type, String url) throws Exception {
		String sqlQuery = "select MO from Modules as MO where MO.verb = :verb and MO.module = :module ";
		Query q = this.getEntityManager().createQuery(sqlQuery, Modules.class);
        q.setParameter("verb", type);
        q.setParameter("module", url);
        Modules module = (Modules) JpaResultHelper.getSingleResultOrNull(q);
	    return module;	   		
	}	

}
