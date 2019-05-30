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
import com.gruposca.sapev.api.connectors.dao.model.SamplePhases;
import com.gruposca.sapev.api.connectors.dao.model.Samples;
import com.gruposca.sapev.api.helper.JpaResultHelper;

@Repository("SamplePhaseDaoImpl")
public class SamplePhaseDaoImpl extends BaseDaoHibernate<SamplePhases, String> implements SamplePhaseDao{

	public SamplePhaseDaoImpl() { super(SamplePhases.class); }

	@Override
	public List<Integer> getSamplesPhases(Samples samples) throws Exception {
		String sqlQuery = "SELECT DISTINCT SP.tableElements.id FROM SamplePhases SP WHERE SP.samples = :samples ORDER BY SP.tableElements.id ";
		List<Integer> list = new ArrayList<Integer>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Integer.class);
        q.setParameter("samples", samples);  
        list = JpaResultHelper.getResultListAndCast(q);    
		return list;	
	}

	


}
