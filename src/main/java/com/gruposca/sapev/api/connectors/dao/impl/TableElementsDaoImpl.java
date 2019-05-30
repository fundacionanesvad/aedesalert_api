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
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Languages;
import com.gruposca.sapev.api.connectors.dao.model.Profiles;
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.connectors.dao.model.TableHeaders;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.modelview.Element;
import com.gruposca.sapev.api.modelview.ElementImpl;
import com.gruposca.sapev.api.modelview.LabelImpl;
import com.gruposca.sapev.api.modelview.SyncElementPlan;

@Repository("TableElementsDaoImpl")
public class TableElementsDaoImpl extends BaseDaoHibernate<TableElements, String> implements TableElementsDao{

	public TableElementsDaoImpl() { super(TableElements.class); }

	@Override
	public Element getElement(Profiles profile, Integer id) throws Exception {
				
		List<LabelImpl> listLabels = new ArrayList<LabelImpl>();
		LabelImpl label;
		Element element;
		String sql= "";
		Integer headerId = 0;
		sql = "SELECT TH.id,LB.id,LB.languages,LB.value FROM Labels LB RIGHT JOIN LB.tableElements AS TE RIGHT JOIN TE.tableHeaders AS TH WHERE TE.id = :elementId ";
		
		if(profile.getId() != ConfigurationHelper.getProfileAdminId()){	
			
			sql +=" AND TH.system = false ";			
		} 				
		
		Query q = this.getEntityManager().createQuery(sql);
		q.setParameter("elementId", id);
		
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);		 
	    if(results.size() > 0){

		    for (Object[] result : results) {	
		    	headerId = (Integer) result[0];
		    	Languages languages = (Languages)result[2];
		    	label = new LabelImpl(languages.getId(), (String) result[3]);
		    	listLabels.add(label);	    	
		    }
		    
		    element = new ElementImpl(headerId, listLabels);
		    return element;
	    }else{
	    	return null;
	    }   
	}

	@Override
	public Integer getMaxSort(TableHeaders tableHeaders) throws Exception {
		String sqlQuery = "select MAX(TE.sort) from TableElements as TE where TE.tableHeaders = :tableHeaders ";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Integer.class);	    
        q.setParameter("tableHeaders", tableHeaders);  
        Integer count = (Integer)q.getSingleResult();
        if(count == null) count = 0;
        return count;
	}

	@Override
	public List<TableElements> getList(TableHeaders tableHeaders) throws Exception {
		List<TableElements> listElements = new ArrayList<TableElements>();
		String sqlQuery = "SELECT TE FROM TableElements AS TE WHERE  TE.tableHeaders = :tableHeaders ORDER BY TE.sort ASC";		    
		Query q = this.getEntityManager().createQuery(sqlQuery, TableElements.class);
        q.setParameter("tableHeaders", tableHeaders);  
        listElements = JpaResultHelper.getResultListAndCast(q);
		return listElements;
	}	
	
	@Override
	public List<Element> getList(Integer languageId) throws Exception {
		List<Element> listElements = new ArrayList<Element>();		
		String sql = " SELECT TE.id,  LA.value,TE.sort, TE.tableHeaders.id FROM TableElements AS TE "
				   + " LEFT JOIN TE.labelses AS LA WHERE LA.languages.id = :language ORDER BY TE.tableHeaders.id, TE.sort";
		Query q = this.getEntityManager().createQuery(sql);							
	    q.setParameter("language", languageId);        

		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		for (Object[] result : results) {		        
		   	Element element = new SyncElementPlan((Integer) result[0], (String) result[1], (Integer) result[2], (Integer) result[3]);	   			
		   	listElements.add(element);	
		}
		return listElements;
	}	
}
