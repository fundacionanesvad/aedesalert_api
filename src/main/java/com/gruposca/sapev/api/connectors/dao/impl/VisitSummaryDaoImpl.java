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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.VisitSummaries;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.VisitSumaryInsertUpdate;
import com.gruposca.sapev.api.modelview.VisitSummaryInspector;

@Repository("VisitSummaryDaoImpl")
public class VisitSummaryDaoImpl extends BaseDaoHibernate<VisitSummaries, String> implements VisitSummaryDao {

	public VisitSummaryDaoImpl() { super(VisitSummaries.class); }

	@Override
	public VisitSummaries getVisitSummarie(Integer areaId, Integer planId) throws Exception {
		String sqlQuery = "select VS from VisitSummaries as VS where VS.areas.id = :areaId AND VS.plans.id = :planId ";
		Query q = this.getEntityManager().createQuery(sqlQuery, VisitSummaries.class);
        q.setParameter("areaId", areaId); 
        q.setParameter("planId", planId);           
        List<Object> results = q.getResultList();
        if (results.isEmpty()) return null;
        else return (VisitSummaries)results.get(0);
    }
	
	@Override
	public Integer getFocus(Integer areaId, Integer planId ) throws Exception {

		String sql = "SELECT"
				+ " COUNT(DISTINCT Houses.uuid) AS foco"
				+ " FROM"
				+ " Visits"
				+ " INNER JOIN"
				+ " Houses  ON Houses.uuid = Visits.houseUuid"
				+ " INNER JOIN Inventories"
				+ " ON (Visits.uuid = Inventories.visitUuid"
				+ " AND Inventories.focus > 0)"
				+ " WHERE Houses.areaId =" +areaId +" AND Visits.planId = "+planId;
		
		Query q = this.getEntityManager().createNativeQuery(sql);
        BigInteger focus = (BigInteger)q.getSingleResult();
        return focus.intValue();
	}

	@Override
	public Integer getTreated(Integer areaId,Integer planId ) throws Exception {
		String sql = "SELECT"
				+ " COUNT(DISTINCT Houses.uuid) AS treated"
				+ " FROM"
				+ " Visits"
				+ " INNER JOIN"
				+ " Houses  ON Houses.uuid = Visits.houseUuid"
				+ " INNER JOIN Inventories"
				+ " ON (Visits.uuid = Inventories.visitUuid"
				+ " AND Inventories.treated > 0)"
				+ " WHERE Houses.areaId =" +areaId +" AND Visits.planId = "+planId;
		
		Query q = this.getEntityManager().createNativeQuery(sql);
        BigInteger focus = (BigInteger)q.getSingleResult();
        return focus.intValue();
	}

	@Override
	public Integer getDestroyed(Integer areaId, Integer planId ) throws Exception {
		String sql = "SELECT"
				+ " COUNT(DISTINCT Houses.uuid) AS destroyed"
				+ " FROM"
				+ " Visits"
				+ " INNER JOIN"
				+ " Houses  ON Houses.uuid = Visits.houseUuid"
				+ " INNER JOIN Inventories"
				+ " ON (Visits.uuid = Inventories.visitUuid"
				+ " AND Inventories.destroyed > 0)"
				+ " WHERE Houses.areaId =" +areaId +" AND Visits.planId = "+planId;
		
		Query q = this.getEntityManager().createNativeQuery(sql);	
        BigInteger focus = (BigInteger)q.getSingleResult();
        return focus.intValue();
	}

	@Override
	@Transactional
	public Integer insertData(Integer areaId, Integer planId, VisitSumaryInsertUpdate visitSumaryInsertUpdate) throws Exception {
		Integer result = 0;
		String sql = "";
		sql = "INSERT INTO VisitSummaries (houses, inspected, closed,reluctant, abandoned, reconverted, people, febriles, larvicide,planId,areaId) "
				+ " VALUES ("
				+ visitSumaryInsertUpdate.getHouses()+","
				+ visitSumaryInsertUpdate.getInspected()+","
				+ visitSumaryInsertUpdate.getClosed()+","
				+ visitSumaryInsertUpdate.getRenuente()+","
				+ visitSumaryInsertUpdate.getAbandonada()+","
				+ visitSumaryInsertUpdate.getReconverted()+","
				+ visitSumaryInsertUpdate.getPersons()+","
				+ visitSumaryInsertUpdate.getFeverish()+","
				+ visitSumaryInsertUpdate.getLarvicide()+","
				+ planId+","
				+ areaId+")";		
	 	Query q = this.getEntityManager().createNativeQuery(sql);	  
	    result = q.executeUpdate();			
		return result;
	}
	
	@Override
	@Transactional
	public Integer updateData(Integer areaId, Integer planId, VisitSumaryInsertUpdate visitSumaryInsertUpdate) throws Exception {
		Integer result = 0;
		String sql = "";
		sql = "UPDATE VisitSummaries SET"
				+ " houses = "+visitSumaryInsertUpdate.getHouses()+","
				+ " inspected = "+visitSumaryInsertUpdate.getInspected()+","
				+ " closed = "+visitSumaryInsertUpdate.getClosed()+","
				+ " reluctant = "+visitSumaryInsertUpdate.getRenuente()+","
				+ " abandoned = "+visitSumaryInsertUpdate.getAbandonada()+","
				+ " reconverted = "+visitSumaryInsertUpdate.getReconverted()+","
				+ " people = "+visitSumaryInsertUpdate.getPersons()+","
				+ " febriles = "+visitSumaryInsertUpdate.getFeverish()+","
				+ " larvicide = "+visitSumaryInsertUpdate.getLarvicide()
				+ " WHERE VisitSummaries.areaId = "+areaId+" AND VisitSummaries.planId = "+planId;	
		Query q = this.getEntityManager().createNativeQuery(sql);	  
		result = q.executeUpdate();			
		return result;
	}

	@Override
	public VisitSumaryInsertUpdate getData(Integer areaId, Integer planId) throws Exception {
		VisitSumaryInsertUpdate visitSumaryInsertUpdate = null;
		String sql =   " SELECT"
				+ " COUNT(Visits.houseUuid) AS houses,"
				+ " SUM(IF((Visits.resultId = 2001),1,0)) AS inspected,"
				+ " SUM(IF((Visits.resultId = 2002),1,0)) AS closed,"
				+ " SUM(IF((Visits.resultId = 2003),1,0)) AS renuente,"
				+ " SUM(IF((Visits.resultId = 2004),1,0)) AS abandonada,"
				+ " SUM(IF((Visits.requalify = TRUE),1,0)) AS reconverted, "
				+ " SUM(personsNumber),"
				+ " SUM(feverish),"
				+ " SUM(Visits.larvicide) AS larvicide ,"
				+ " SUM(IF((Visits.beforeReconversion = 'C'), 1 ,0)) AS closedReconverted, "
				+ " SUM(IF((Visits.beforeReconversion = 'R'), 1 ,0)) AS reluctantReconverted, "
				+ " SUM(IF((Visits.beforeReconversion = 'A'), 1 ,0)) AS abandonedReconverted, "
				+ " planId,"
				+ " areaId"
				+ " FROM Houses INNER JOIN Visits ON Houses.uuid = Visits.houseUuid"
				+ " WHERE Houses.areaId = "+areaId+" AND planId = "+planId
				+ " GROUP BY planId, areaId" ;	
		Query q = this.getEntityManager().createNativeQuery(sql);	  

	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	
	    if (results.size() == 1){
	    	
	    	for (Object[] result : results) {	
	    		visitSumaryInsertUpdate = new VisitSumaryInsertUpdate((BigInteger)result[0],
	    															  (BigDecimal)result[1],
	    															  (BigDecimal)result[2],
	    															  (BigDecimal)result[3],
	    															  (BigDecimal)result[4],
	    															  (BigDecimal)result[5],
	    															  (BigDecimal)result[6],
	    															  (BigDecimal)result[7],
	    															  (BigDecimal)result[8],
	    															  (BigDecimal)result[9],
	    															  (BigDecimal)result[10],
	    															  (BigDecimal)result[11],
	    															  (Integer)result[12],
	    															  (Integer)result[13]);
	    	}	
	    }
		return visitSumaryInsertUpdate;
	}

	@Override
	public VisitSummaryInspector getSummary(Integer planId) throws Exception {
		VisitSummaryInspector visitSummaryInspector = null;
		String sql =   " SELECT SUM(inspected),SUM(closed), SUM(reluctant), SUM(abandoned),SUM(treated),SUM(focus) FROM VisitSummaries WHERE planId = " +planId ;	
		Query q = this.getEntityManager().createNativeQuery(sql);	  

	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	
	    if (results.size() == 1){
	    	
	    	for (Object[] result : results) {	
	    		visitSummaryInspector = new VisitSummaryInspector((BigDecimal)result[0],
    															  (BigDecimal)result[1],
    															  (BigDecimal)result[2],
    															  (BigDecimal)result[3],
    															  (BigDecimal)result[4],
    															  (BigDecimal)result[5]);
	    	}	
	    }
		return visitSummaryInspector;
	}

	@Override
	public Integer getPersons(Integer areaId, Integer planId) throws Exception {
		String sql = "SELECT SUM(personsNumber) FROM(" + 
				" SELECT " + 
				" DISTINCT Houses.* FROM " + 
				" Visits " + 
				" INNER JOIN " + 
				" Houses  ON Houses.uuid = Visits.houseUuid " + 
				" WHERE Houses.areaId =" +areaId +" AND Visits.planId = "+planId+") AS H";
		
		Query q = this.getEntityManager().createNativeQuery(sql);	
		BigDecimal persons = (BigDecimal)q.getSingleResult();
        return persons.intValue();
	}

	@Override
	public List<VisitSummaries> getList(Integer planId) throws Exception {
		String sqlQuery = "select V from VisitSummaries as V where V.plans.id = :planId";
		List<VisitSummaries> list = new ArrayList<VisitSummaries>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, VisitSummaries.class);
        q.setParameter("planId", planId); 
        list = JpaResultHelper.getResultListAndCast(q);       
		return list;
	}
	
}
