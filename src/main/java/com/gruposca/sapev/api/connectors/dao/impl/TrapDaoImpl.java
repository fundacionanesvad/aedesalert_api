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
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Traps;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.ParamFilterTraps;
import com.gruposca.sapev.api.modelview.TrapModelList;
import com.gruposca.sapev.api.modelview.TrapReportInfo;
import com.gruposca.sapev.api.modelview.TrapsList;

@Repository("TrapDaoImpl")
public class TrapDaoImpl extends BaseDaoHibernate<Traps, String> implements TrapDao{
	public TrapDaoImpl() { super(Traps.class); }

	@Override
	public TrapsList getListByAreaUser(Users user, ParamFilterTraps paramFilterTraps) throws Exception {
		List<TrapModelList> list = new ArrayList<TrapModelList>();

		String WHERE =" WHERE AD.areaId = "+user.getAreas().getId();
		
		if(paramFilterTraps.getFilter().getCode() != null){
			WHERE += " AND T.code LIKE '%"+paramFilterTraps.getFilter().getCode()+"%'";			
		}
		if(paramFilterTraps.getFilter().getNumber() != null){
			WHERE += " AND T.number = "+paramFilterTraps.getFilter().getNumber();			
		}		
		if(paramFilterTraps.getFilter().getEessName() != null){
			WHERE += " AND A1.name LIKE '%"+paramFilterTraps.getFilter().getEessName()+"%'";			
		}
		if(paramFilterTraps.getFilter().getMicrorredName() != null){
			WHERE += " AND A2.name LIKE '%"+paramFilterTraps.getFilter().getMicrorredName()+"%'";			
		}
		if(paramFilterTraps.getFilter().getRedName() != null){
			WHERE += " AND A3.name LIKE '%"+paramFilterTraps.getFilter().getRedName()+"%'";			
		}
		if(paramFilterTraps.getFilter().getEnabled() != null){
			if(paramFilterTraps.getFilter().getEnabled() == 0){
				WHERE += " AND T.enabled = false";	
			}else{
				WHERE += " AND T.enabled = true";			
			}
		}	
		
		String ORDER =" ORDER BY";
		if (paramFilterTraps.getSorting().getCode() != null){
			ORDER += " T.code "+paramFilterTraps.getSorting().getCode();
		}else if (paramFilterTraps.getSorting().getEessName() != null){
			ORDER += " A1.name "+paramFilterTraps.getSorting().getEessName();
		}else if(paramFilterTraps.getSorting().getMicrorredName() != null){
			ORDER += " A2.name "+paramFilterTraps.getSorting().getMicrorredName();			
		}else if(paramFilterTraps.getSorting().getRedName() != null){
			ORDER += " A3.name "+paramFilterTraps.getSorting().getRedName();	
		}else if(paramFilterTraps.getSorting().getNumber() != null){
			ORDER += " T.number "+paramFilterTraps.getSorting().getNumber();	
		}else if(paramFilterTraps.getSorting().getEnabled() != null){
			ORDER += " T.enabled "+paramFilterTraps.getSorting().getEnabled();	
		}else{
			ORDER += " A3.name "+paramFilterTraps.getSorting().getRedName();
		}		
		
		Integer init = (paramFilterTraps.getPage() - 1) * paramFilterTraps.getCount();		
		
		String queryString = " SELECT T.id, T.number, T.code, A1.name AS EESS, A2.name AS MR, A3.name AS red, T.enabled FROM  " + 
							" Traps AS T INNER JOIN AreaDescendants AS AD ON T.areaId = AD.descendantId " + 
							" INNER JOIN Areas AS A1 ON A1.id = T.areaId " + 
							" INNER JOIN Areas AS A2 ON A1.parentId = A2.id " + 
							" INNER JOIN Areas AS A3 ON A2.parentId = A3.id "+ WHERE + ORDER;

		Query q = this.getEntityManager().createNativeQuery(queryString);	
	    q.setFirstResult(init);
	    q.setMaxResults(paramFilterTraps.getCount());
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
	    for (Object[] result : results) {	
	    	TrapModelList trap = new TrapModelList((Integer)result[0],
												(Integer)result[1],
		    									(String)result[2],
		    									(String)result[3],
		    									(String)result[4],
		    									(String)result[5],
												(Boolean)result[6]);				
	    	list.add(trap);
		}
	    
	    String queryStringCount = "SELECT IFNULL(COUNT(*),0) " + 
				" FROM Traps AS T INNER JOIN AreaDescendants AS AD ON T.areaId = AD.descendantId " + 
				" INNER JOIN Areas AS A1 ON A1.id = T.areaId " + 
				" INNER JOIN Areas AS A2 ON A1.parentId = A2.id " + 
				" INNER JOIN Areas AS A3 ON A2.parentId = A3.id "+ WHERE;			

		
		q = this.getEntityManager().createNativeQuery(queryStringCount);		
		BigInteger totalTraps = (BigInteger)q.getSingleResult();			
		TrapsList trapList = new TrapsList(totalTraps.intValue(), list);		    
		return trapList;    
	}

	@Override
	public List<TrapReportInfo> getTrapsReport(Integer areaId, String dateIni, String dateFin) throws Exception {
		String sqlQuery = " SELECT DISTINCT T.id,T.number, T.code, TL.latitude, TL.longitude, TL.altitude, TL.address, TL.location "+
				" FROM Traps T INNER JOIN TrapLocations TL ON T.id = TL.trapId " + 
				" INNER JOIN TrapData TP ON T.id = TP.trapId " + 
				" WHERE TL.enabled AND T.areaId = :areaId AND TP.date >= '"+dateIni+ "' AND TP.date <= '"+dateFin+"'"+ 
				" ORDER BY number";
		List<TrapReportInfo> list = new ArrayList<TrapReportInfo>();
		Query q = this.getEntityManager().createNativeQuery(sqlQuery);
		q.setParameter("areaId", areaId); 
		
		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	
		for (Object[] result : results) {			
			TrapReportInfo trapReportInfo = new TrapReportInfo((Integer)result[0],
															   (Integer)result[1],
															   (String)result[2],
															   (BigDecimal)result[3],
															   (BigDecimal)result[4],
															   (BigDecimal)result[5],
															   (String)result[6],
															   (String)result[7]);				
			list.add(trapReportInfo);				
		}					
		return list;      
	}

	@Override
	public boolean existTrap(Integer areaId, String code) throws Exception {
		String sqlQuery = "select COUNT(T.id) from Traps as T where T.areas.id = :areaId AND T.code = :code ";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Long.class);	    
        q.setParameter("areaId", areaId); 
        q.setParameter("code", code);  
        Long count = (Long)q.getSingleResult();
        if (count > 0){ 
        	return true;
        }else{
        	return false;
        }        	
	}
	

	
	
}
