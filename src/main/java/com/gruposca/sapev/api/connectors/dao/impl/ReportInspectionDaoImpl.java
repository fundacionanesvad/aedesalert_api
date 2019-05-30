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
import com.gruposca.sapev.api.connectors.dao.model.ReportInspections;
import com.gruposca.sapev.api.connectors.dao.model.Reports;
import com.gruposca.sapev.api.helper.JpaResultHelper;

@Repository("ReportInspectionDaoImpl")
public class ReportInspectionDaoImpl extends BaseDaoHibernate<ReportInspections, String> implements ReportInspectionDao{
	public ReportInspectionDaoImpl() { super(ReportInspections.class); }

	@Override
	@Transactional
	public Integer delete(Reports report) throws Exception {
		String sqlQuery = "DELETE FROM ReportInspections AS R WHERE R.reports = :reports ";
	    Query q = this.getEntityManager().createQuery(sqlQuery);	    
        q.setParameter("reports", report); 
        return q.executeUpdate();
	}

	@Override
	public List<Integer> listInspectionsId(Reports report) throws Exception {
		List<Integer> listModules = new ArrayList<Integer>();
		String sqlQuery = "select RI.inspections.id  from ReportInspections as RI where RI.reports = :reports ";
		Query q = this.getEntityManager().createQuery(sqlQuery, Integer.class);
        q.setParameter("reports", report); 
        listModules = JpaResultHelper.getResultListAndCast(q);  
		return listModules;
	}

}
