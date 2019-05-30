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
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.InventorySummaries;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.InventoryListSummary;

@Repository("InventorySummaryDaoImpl")
public class InventorySummaryDaoImpl extends BaseDaoHibernate<InventorySummaries, String> implements InventorySummaryDao {

	public InventorySummaryDaoImpl() { super(InventorySummaries.class); }

	@Override
	@Transactional
	public Integer insertData(Integer areaId, Integer planId) throws Exception {
		String sql = "INSERT INTO InventorySummaries (inspected,focus,treated,destroyed,containerId,planId,areaId) "+
				" SELECT SUM(inspected) AS inspected, SUM(focus) AS focus, SUM(treated) AS treated,  SUM(destroyed) AS destroyed, Inventories.containerId, Visits.planId, Houses.areaId " + 
				" FROM  Visits INNER JOIN Houses ON Visits.houseUuid = Houses.uuid " + 
				" INNER JOIN Inventories ON Inventories.visitUuid = Visits.uuid " +
				" WHERE Visits.planId = "+planId+" AND Houses.areaId = "+areaId+"  GROUP BY  containerId ";		
	    Query q = this.getEntityManager().createNativeQuery(sql);	    
        return q.executeUpdate();
	}

	@Override
	public List<InventorySummaries> getList(Integer areaId, Integer planId)	throws Exception {
		String sqlQuery = "select I from InventorySummaries as I where I.plans.id = :planId AND I.areas.id = :areaId";
		List<InventorySummaries> list = new ArrayList<InventorySummaries>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, InventorySummaries.class);
        q.setParameter("areaId", areaId); 
        q.setParameter("planId", planId); 
        list = JpaResultHelper.getResultListAndCast(q);       
		return list;
	}

	@Override
	public InventoryListSummary getData(Integer areaId, Integer planId,	Integer containerId) throws Exception {
		InventoryListSummary inventoryListSummary = new InventoryListSummary();
		String sql = " SELECT SUM(inspected) AS inspected, SUM(focus) AS focus, SUM(treated) AS treated,  SUM(destroyed) AS destroyed, Inventories.containerId " + 
				" FROM  Visits INNER JOIN Houses ON Visits.houseUuid = Houses.uuid " + 
				" INNER JOIN Inventories ON Inventories.visitUuid = Visits.uuid " + 
				" WHERE Houses.areaId = "+areaId+" AND Visits.planId = "+planId+" AND containerId = "+containerId;	
		
		 Query q = this.getEntityManager().createNativeQuery(sql);	  
		 List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	
		 if (results.size() == 1){
			 for (Object[] result : results) {
				inventoryListSummary = new InventoryListSummary((Integer)result[4],
																((BigDecimal)result[0]).intValue(), 
																 ((BigDecimal)result[1]).intValue(), 
																 ((BigDecimal)result[2]).intValue(), 
																 ((BigDecimal)result[3]).intValue());
			 }	
		 }		
		 return inventoryListSummary;
	}

	@Override
	public InventorySummaries getDataByContainer(Integer areaId, Integer planId, Integer containerId)	throws Exception {
		String sqlQuery = "select I from InventorySummaries as I where I.plans.id = :planId AND I.areas.id = :areaId AND I.container.id = :containerId";
	    Query q = this.getEntityManager().createQuery(sqlQuery, InventorySummaries.class);
        q.setParameter("areaId", areaId); 
        q.setParameter("planId", planId); 
        q.setParameter("containerId", containerId); 
        InventorySummaries inventorySummaries = (InventorySummaries) JpaResultHelper.getSingleResultOrNull(q);
	    return inventorySummaries;
	}	
}
