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

import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Labels;
import com.gruposca.sapev.api.connectors.dao.model.Languages;
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.helper.JpaResultHelper;

@Repository("LabelDaoImpl")
public class LabelDaoImpl extends BaseDaoHibernate<Labels, String> implements LabelDao{

	public LabelDaoImpl() { super(Labels.class); }

	@Override
	@Transactional
	public Integer deleteByElement(TableElements tableElements)throws Exception {
		String sqlQuery = "DELETE FROM Labels AS LB WHERE LB.tableElements = :tableElements ";
	    Query q = this.getEntityManager().createQuery(sqlQuery);	    
        q.setParameter("tableElements", tableElements); 
        return q.executeUpdate();
	}

	@Override
	public String getValueElement(Languages languages,TableElements tableElements)throws Exception {
		String sqlQuery = "select LA.value from Labels as LA where LA.tableElements = :tableElements and LA.languages = :languages ";
		Query q = this.getEntityManager().createQuery(sqlQuery, String.class);
        q.setParameter("tableElements", tableElements);
        q.setParameter("languages", languages);
        String value = (String) JpaResultHelper.getSingleResultOrNull(q);
	    return value;	        
    }

	@Override
	public String getValueByElementId(Languages languages, int tableElementsId) throws Exception {
		String sqlQuery = "select LA.value from Labels as LA where LA.tableElements.id = :tableElementsId and LA.languages = :languages ";
		Query q = this.getEntityManager().createQuery(sqlQuery, String.class);
        q.setParameter("tableElementsId", tableElementsId);
        q.setParameter("languages", languages);
        String value = (String) JpaResultHelper.getSingleResultOrNull(q);
	    return value;	        
	}
}
