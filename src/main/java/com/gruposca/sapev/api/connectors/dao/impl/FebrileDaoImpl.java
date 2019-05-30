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

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Febriles;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.FebrileList;
import com.gruposca.sapev.api.modelview.FebrileListImpl;
import com.gruposca.sapev.api.modelview.ParamFilterFebriles;

@Repository("FebrileDaoImpl")
public class FebrileDaoImpl extends BaseDaoHibernate<Febriles, String> implements FebrileDao{

	public FebrileDaoImpl() { super(Febriles.class); }
	
	@Override
	public FebrileList getListByAreaUser(Users user, ParamFilterFebriles paramFilterFebriles) throws Exception {
		List<FebrileListImpl> list = new ArrayList<FebrileListImpl>();
		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");

		String WHERE =" WHERE AD.areaId = "+user.getAreas().getId();
		
		if(paramFilterFebriles.getFilter().getDate() != null){
			WHERE += " AND F.date = '"+(fr.format(new Date(paramFilterFebriles.getFilter().getDate())))+"'";			
		}	
		if(paramFilterFebriles.getFilter().getEessName() != null){
			WHERE += " AND A1.name LIKE '%"+paramFilterFebriles.getFilter().getEessName()+"%'";			
		}
		if(paramFilterFebriles.getFilter().getMicrorredName() != null){
			WHERE += " AND A2.name LIKE '%"+paramFilterFebriles.getFilter().getMicrorredName()+"%'";			
		}
		if(paramFilterFebriles.getFilter().getRedName() != null){
			WHERE += " AND A3.name LIKE '%"+paramFilterFebriles.getFilter().getRedName()+"%'";			
		}
		
		String ORDER =" ORDER BY";
		if (paramFilterFebriles.getSorting().getDate() != null){
			ORDER += " F.date "+paramFilterFebriles.getSorting().getDate();
		}else if (paramFilterFebriles.getSorting().getEessName() != null){
			ORDER += " A1.name "+paramFilterFebriles.getSorting().getEessName();
		}else if(paramFilterFebriles.getSorting().getMicrorredName() != null){
			ORDER += " A2.name "+paramFilterFebriles.getSorting().getMicrorredName();			
		}else if(paramFilterFebriles.getSorting().getRedName() != null){
			ORDER += " A3.name "+paramFilterFebriles.getSorting().getRedName();	
		}else{
			ORDER += " F.date "+paramFilterFebriles.getSorting().getDate();
		}		
		
		Integer init = (paramFilterFebriles.getPage() - 1) * paramFilterFebriles.getCount();			
		
		String queryString = " SELECT F.id, F.date, A1.name AS EESS, A2.name AS MR, A3.name AS red FROM  " + 
							" Febriles AS F INNER JOIN AreaDescendants AS AD ON F.areaId = AD.descendantId " + 
							" INNER JOIN Areas AS A1 ON A1.id = F.areaId " + 
							" INNER JOIN Areas AS A2 ON A1.parentId = A2.id " + 
							" INNER JOIN Areas AS A3 ON A2.parentId = A3.id "+ WHERE + ORDER;

		Query q = this.getEntityManager().createNativeQuery(queryString);	
	    q.setFirstResult(init);
	    q.setMaxResults(paramFilterFebriles.getCount());
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
	    for (Object[] result : results) {	
	    	FebrileListImpl febrile = new FebrileListImpl((Integer) result[0],
		                       							((Date) result[1]).getTime(),
				    									(String)result[2],
				    									(String)result[3],
				    									(String)result[4]);				
	    	list.add(febrile);
		}
	    
	    String queryStringCount = "SELECT IFNULL(COUNT(*),0) " + 
				" FROM Febriles AS F INNER JOIN AreaDescendants AS AD ON F.areaId = AD.descendantId " + 
				" INNER JOIN Areas AS A1 ON A1.id = F.areaId " + 
				" INNER JOIN Areas AS A2 ON A1.parentId = A2.id " + 
				" INNER JOIN Areas AS A3 ON A2.parentId = A3.id "+ WHERE;			

		
		q = this.getEntityManager().createNativeQuery(queryStringCount);		
		BigInteger totalFebrile = (BigInteger)q.getSingleResult();			
		FebrileList trapList = new FebrileList(totalFebrile.intValue(), list);		    
		return trapList;    
	}

}
