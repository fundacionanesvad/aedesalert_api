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
import com.gruposca.sapev.api.connectors.dao.model.Larvicides;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.LarvicideList;

@Repository("LarvicideDaoImpl")
public class LarvicideDaoImpl extends BaseDaoHibernate<Larvicides, String> implements LarvicideDao {

	public LarvicideDaoImpl() { super(Larvicides.class); }


	@Override
	public List<LarvicideList> getListLarvicides() throws Exception {
		List<LarvicideList> listLarvicides = new ArrayList<LarvicideList>();
	 
		String sql =   " SELECT  L.*, COUNT(S.id) AS num FROM Larvicides L LEFT JOIN Schedules S ON S.larvicideId = L.id GROUP BY L.id " ;		
	    Query q = this.getEntityManager().createNativeQuery(sql);	  

	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	
	    if (results.size() > 0){
	    	
	    	for (Object[] result : results) {	
	    		LarvicideList larvicideList = new LarvicideList( (Integer)result[0],
	    				 							  (String)result[1], 
	    				 							  (String)result[2],
	    				 							  (BigDecimal)result[3],
	    				 							  (String)result[4],
	    				 							  (Integer)result[5],
	    				 							  (boolean)result[6],
	    				 							  ((BigInteger)result[7]).intValue());
	    		listLarvicides.add(larvicideList);
	    	}	
	    }
        return listLarvicides;				
	}


	@Override
	public Larvicides getLarvicide() throws Exception {
		List<Larvicides> listLarvicides = new ArrayList<Larvicides>();
		String sqlQuery = "SELECT L FROM Larvicides AS L WHERE L.enabled = true Order by L.id DESC";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Larvicides.class);
		listLarvicides = JpaResultHelper.getResultListAndCast(q);  
		if(listLarvicides.size() > 0)
			return listLarvicides.get(0);
		else 
			return null;
	}
}
