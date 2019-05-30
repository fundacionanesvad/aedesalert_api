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
import java.sql.Date;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.TrapData;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.TrapDataModelList;

@Repository("TrapDataDaoImpl")
public class TrapDataDaoImpl extends BaseDaoHibernate<TrapData, String> implements TrapDataDao{
	public TrapDataDaoImpl() { super(TrapData.class);}
	@Override
	public List<TrapDataModelList> getList(Integer microrredId, Integer eessId, String date) throws Exception {
		
		String condArea =" AND AR.parentId = "+ microrredId;
		if(eessId != null) {
			condArea =" AND AR.id = "+eessId;
		}
		
		String sqlQuery = " SELECT `code`, id, trapDataId, eggs, resultId, latitude,longitude, areaId FROM " + 
						" (SELECT T.id,T.code,TL.latitude,TL.longitude, T.areaId FROM Traps T INNER JOIN TrapLocations TL ON TL.trapId = T.id " + 
						" INNER JOIN Areas AR ON AR.id = T.areaId " + 
						" WHERE T.enabled = TRUE "+condArea+" AND TL.enabled = TRUE ) AS A1 " + 
						" LEFT JOIN " + 
						" (SELECT TD.trapId, TD.id AS trapDataId, eggs, resultId FROM TrapData TD INNER JOIN Traps T " + 
						" ON T.id = TD.trapId " + 
						" WHERE TD.date = :date) AS A2 " + 
						" ON A1.id = A2.trapId";
		List<TrapDataModelList> list = new ArrayList<TrapDataModelList>();
	    Query q = this.getEntityManager().createNativeQuery(sqlQuery);
        q.setParameter("date", date);         
        List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	
        for (Object[] result : results) {			
        	TrapDataModelList trapDataModelList = new TrapDataModelList((String)result[0], (Integer)result[1], (Integer)result[2], (Integer)result[3], (Integer)result[4], (BigDecimal)result[5], (BigDecimal)result[6], (Integer)result[7]);	
			list.add(trapDataModelList);				
	    }					
		return list;      
	}
	
	@Override
	public List<Date> getListDates(Integer areaId, String dateIni, String dateFin) throws Exception {
		String sqlQuery = " SELECT DISTINCT TP.date FROM Traps T INNER JOIN TrapLocations TL ON T.id = TL.trapId " + 
				" INNER JOIN TrapData TP ON T.id = TP.trapId " + 
				" WHERE TL.enabled AND T.areaId = " + areaId+" AND TP.date >= '"+dateIni+ "' AND TP.date <= '"+dateFin+"'"+
				" ORDER BY TP.date ASC ";
		
		System.out.println(sqlQuery);		

	    Query q = this.getEntityManager().createNativeQuery(sqlQuery);
        
        List<Date> results = JpaResultHelper.getResultListAndCast(q);	
		return results;
	}
	@Override
	public TrapData getData(Integer trapId, Date date) throws Exception {
		String sqlQuery = "select T from TrapData as T where T.traps.id = :trapId and T.date = :date ";
		Query q = this.getEntityManager().createQuery(sqlQuery, TrapData.class);
	    q.setParameter("trapId", trapId);
	    q.setParameter("date", date);
	    TrapData trapData = (TrapData) JpaResultHelper.getSingleResultOrNull(q);
	    if (trapData != null) return trapData;
	    //else throw new NoResultException(RestErrorImpl.ERROR_LOGIN);
	    else return null;
	}
	@Override
	public List<BigDecimal> getIPO(Integer areaId, String dateIni, String dateFin) throws Exception {
				
		String sqlQuery = " SELECT " + 
				" IFNULL(TRUNCATE((SUM(CASE WHEN eggs > 0 THEN 1 ELSE 0 END)/COUNT(*)) * 100, 2),0.00) AS IPO " + 
				" FROM Traps T INNER JOIN TrapLocations TL ON T.id = TL.trapId " + 
				" INNER JOIN TrapData TP ON T.id = TP.trapId " + 
				" WHERE TL.enabled AND T.areaId = "+areaId+"  AND TP.date >= '"+dateIni+ "' AND TP.date <= '"+dateFin+"'"+
				" GROUP BY TP.`date` " + 
				" ORDER BY TP.date ASC ";

	    Query q = this.getEntityManager().createNativeQuery(sqlQuery);    
        List<BigDecimal> results = JpaResultHelper.getResultListAndCast(q);	
		return results;

	}
	@Override
	public List<BigDecimal> getIDH(Integer areaId, String dateIni, String dateFin) throws Exception {
		String sqlQuery = " SELECT " + 
				" IFNULL(TRUNCATE(SUM(eggs)/SUM(CASE WHEN eggs > 0 THEN 1 ELSE 0 END),2), 0.00) AS IDH " + 
				" FROM Traps T INNER JOIN TrapLocations TL ON T.id = TL.trapId " + 
				" INNER JOIN TrapData TP ON T.id = TP.trapId " + 
				" WHERE TL.enabled AND T.areaId = "+areaId+" AND TP.date >= '"+dateIni+ "' AND TP.date <= '"+dateFin+"'"+
				" GROUP BY TP.`date` " + 
				" ORDER BY TP.date ASC ";

	    Query q = this.getEntityManager().createNativeQuery(sqlQuery);    
        List<BigDecimal> results = JpaResultHelper.getResultListAndCast(q);	
		return results;
	}
	@Override
	public TrapData getData(Integer trapId, Date date, Integer trapLocationId) throws Exception {
		String sqlQuery = "select T from TrapData as T where T.traps.id = :trapId and T.date = :date and T.trapLocations.id = :trapLocationId ";
		Query q = this.getEntityManager().createQuery(sqlQuery, TrapData.class);
	    q.setParameter("trapId", trapId);
	    q.setParameter("date", date);
	    q.setParameter("trapLocationId", trapLocationId);
	    TrapData trapData = (TrapData) JpaResultHelper.getSingleResultOrNull(q);
	    if (trapData != null) return trapData;
	    else return null;
	}	
	
}
