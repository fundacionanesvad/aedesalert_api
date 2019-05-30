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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.TrapLocations;
import com.gruposca.sapev.api.connectors.dao.model.Traps;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.TrapLocationsModelList;

@Repository("TrapLocationDaoImpl")
public class TrapLocationDaoImpl extends BaseDaoHibernate<TrapLocations, String> implements TrapLocationDao{
	
	public TrapLocationDaoImpl() { super(TrapLocations.class); }
	
	@Override
	public List<TrapLocationsModelList> getTrapLocationsByTrapId(Integer trapId) throws Exception {
		List<TrapLocationsModelList> list = new ArrayList<TrapLocationsModelList>();
		String sqlQuery = "SELECT TL.id,TL.date,TL.latitude,TL.longitude,TL.altitude,TL.address, TL.location, TL.enabled FROM TrapLocations TL WHERE TL.traps.id = :trapId"  ;		
	    Query q = this.getEntityManager().createQuery(sqlQuery);
        q.setParameter("trapId", trapId);  

        List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
	    for (Object[] result : results) {	
	    
	    	TrapLocationsModelList trapLocationsModelList = new TrapLocationsModelList((Integer)result[0],	
		    									((Date)result[1]).getTime(),
		    									(BigDecimal)result[2],
		    									(BigDecimal)result[3],
		    									(BigDecimal)result[4],
		    									(String)result[5],
		    									(String)result[6],
		    									(Boolean)result[7]);				
	    	list.add(trapLocationsModelList);
		}	    
		return list;		
	}

	@Override
	@Transactional
	public int updateEnabledValues(Integer trapId) throws Exception {
	    String query = "UPDATE TrapLocations AS T SET enabled = false WHERE T.traps.id = "+trapId;
		return this.updateList(query);		
	}

	@Override
	public TrapLocations getTrapLocation(Traps traps) throws Exception {
		String sqlQuery = "select T from TrapLocations as T where T.traps = :traps AND T.enabled = true ";
		Query q = this.getEntityManager().createQuery(sqlQuery, TrapLocations.class);
	    q.setParameter("traps", traps);
	    TrapLocations trapLocations = (TrapLocations) JpaResultHelper.getSingleResultOrNull(q);
	    if (trapLocations != null) return trapLocations;
	    //else throw new NoResultException(RestErrorImpl.ERROR_LOGIN);
	    else return null;
	}	
}
