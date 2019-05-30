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
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Houses;
import com.gruposca.sapev.api.connectors.dao.model.Inventories;
import com.gruposca.sapev.api.connectors.dao.model.Persons;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.dao.model.Visits;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.House;
import com.gruposca.sapev.api.modelview.HouseObjectList;
import com.gruposca.sapev.api.modelview.ParamFilterHouses;
import com.gruposca.sapev.api.modelview.Person;
import com.gruposca.sapev.api.modelview.SyncHousePlan;
import com.gruposca.sapev.api.modelview.SyncPersonPlan;

@Repository("HouseDaoImpl")
public class HouseDaoImpl extends BaseDaoHibernate<Houses, String> implements HouseDao{

	public HouseDaoImpl() { super(Houses.class); }

	@Override
	public boolean existWithArea(Areas area) throws Exception {
		String sqlQuery = "select COUNT(HO.id) from Houses as HO where HO.areas = :area ";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Long.class);	    
        q.setParameter("area", area);  
        Long count = (Long)q.getSingleResult();
        if (count > 0){ 
        	return true;
        }else{
        	return false;
        }        		
	}

	@Override
	public Houses findByUUID(UUID uuid) throws Exception {
		String sqlQuery = "select HO from Houses as HO where HO.uuid = :uuid ";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Houses.class);
        q.setParameter("uuid", uuid);          
        Houses house = (Houses) JpaResultHelper.getSingleResultOrNull(q);
	    return house;
	}

	@Override
	public List<Houses> getHousesList(Integer areaId) throws Exception {
		List<Houses> housesList = new ArrayList<Houses>();		
		String sqlQuery = "SELECT HO FROM Houses AS HO WHERE HO.areas.id = :areaId ";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Houses.class);
        q.setParameter("areaId", areaId);  
        housesList = JpaResultHelper.getResultListAndCast(q);    
		return housesList;	
	}

	@Override
	public List<HouseObjectList> getList(ParamFilterHouses paramFilterHouses,Users user) throws Exception {
		
		String WHERE =" WHERE AD.areasByAreaId.id = "+ user.getAreas().getId();
		
		if(paramFilterHouses.getFilter().getAreaName() != null){
			WHERE += " AND H.areas.name LIKE '%"+paramFilterHouses.getFilter().getAreaName()+"%'";
		}
		if(paramFilterHouses.getFilter().getCode() != null){
			WHERE += " AND  H.code LIKE '%"+paramFilterHouses.getFilter().getCode()+"%'";		
		}
		if(paramFilterHouses.getFilter().getStreetName() != null){
			WHERE += " AND  H.streetName LIKE '%"+paramFilterHouses.getFilter().getStreetName()+"%'";		
		}
		if(paramFilterHouses.getFilter().getStreetNumber() != null){
			WHERE += " AND  H.streetNumber LIKE '%"+paramFilterHouses.getFilter().getStreetNumber()+"%'";		
		}	
		if(paramFilterHouses.getFilter().getSectorName() != null){
			WHERE += " AND A2.name LIKE '%"+paramFilterHouses.getFilter().getSectorName()+"%'";			
		}
		if(paramFilterHouses.getFilter().getEessName() != null){
			WHERE += " AND A3.name LIKE '%"+paramFilterHouses.getFilter().getEessName()+"%'";			
		}
		if(paramFilterHouses.getFilter().getMicrorredName() != null){
			WHERE += " AND A4.name LIKE '%"+paramFilterHouses.getFilter().getMicrorredName()+"%'";			
		}	
		
		String ORDER =" ORDER BY";
		
		if(paramFilterHouses.getSorting().getAreaName() != null){
			ORDER += " H.areas.name "+paramFilterHouses.getSorting().getAreaName();
		}else if (paramFilterHouses.getSorting().getCode() != null){
			ORDER += " H.code "+paramFilterHouses.getSorting().getCode();
		}else if(paramFilterHouses.getSorting().getStreetName() != null){
			ORDER += " H.streetName "+paramFilterHouses.getSorting().getStreetName();
		}else if(paramFilterHouses.getSorting().getStreetNumber() != null){
			ORDER += " H.streetNumber "+paramFilterHouses.getSorting().getStreetNumber();
		}else if(paramFilterHouses.getSorting().getSectorName() != null){
			ORDER += " A2.name "+paramFilterHouses.getSorting().getSectorName();	
		}else if(paramFilterHouses.getSorting().getEessName() != null){
			ORDER += " A3.name "+paramFilterHouses.getSorting().getEessName();	
		}else if(paramFilterHouses.getSorting().getMicrorredName() != null){
			ORDER += " A4.name "+paramFilterHouses.getSorting().getMicrorredName();	
		}else{
			ORDER += " H.code ";
		}		
		
		Integer init = (paramFilterHouses.getPage() - 1) * paramFilterHouses.getCount();			
		List<HouseObjectList> list = new ArrayList<HouseObjectList>();		
		
		
		String sql = " SELECT H.uuid, H.number, H.code, H.areas.id, H.areas.name, H.streetName, H.streetNumber , A2.name, A3.name, A4.name "
				+ " FROM AreaDescendants AS AD RIGHT JOIN AD.areasByDescendantId AS A  LEFT JOIN A.houseses AS H "
				+ " RIGHT JOIN H.areas.areas AS A2 "
				+ " RIGHT JOIN A2.areas AS A3 "
				+ " RIGHT JOIN A3.areas AS A4 "+ WHERE + ORDER;

		
		Query q = this.getEntityManager().createQuery(sql);					
		q.setFirstResult(init);
	    q.setMaxResults(paramFilterHouses.getCount());  

		
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		for (Object[] result : results) {		        
			HouseObjectList house = new HouseObjectList(((UUID)result[0]).toString(),
					 									 (Integer) result[1], 
					 									 (String) result[2],
					 									 (Integer) result[3], 
					 									 (String) result[4],
					 									 (String) result[5],
					 									 (String) result[6],
					 									 (String) result[7],
						   			                     (String) result[8],
						   			                     (String) result[9]);						
			list.add(house);	
		}
		return list;
	}

	@Override
	public Integer getCountList(ParamFilterHouses paramFilterHouses,Users user)throws Exception {
		
		String WHERE =" WHERE AD.areasByAreaId.id = "+ user.getAreas().getId();
		
		if(paramFilterHouses.getFilter().getAreaName() != null){
			WHERE += " AND H.areas.name LIKE '%"+paramFilterHouses.getFilter().getAreaName()+"%'";
		}
		if(paramFilterHouses.getFilter().getCode() != null){
			WHERE += " AND  H.code LIKE '%"+paramFilterHouses.getFilter().getCode()+"%'";		
		}
		if(paramFilterHouses.getFilter().getStreetName() != null){
			WHERE += " AND  H.streetName LIKE '%"+paramFilterHouses.getFilter().getStreetName()+"%'";		
		}
		if(paramFilterHouses.getFilter().getStreetNumber() != null){
			WHERE += " AND  H.streetNumber LIKE '%"+paramFilterHouses.getFilter().getStreetNumber()+"%'";		
		}		
		if(paramFilterHouses.getFilter().getSectorName() != null){
			WHERE += " AND A2.name LIKE '%"+paramFilterHouses.getFilter().getSectorName()+"%'";			
		}
		if(paramFilterHouses.getFilter().getEessName() != null){
			WHERE += " AND A3.name LIKE '%"+paramFilterHouses.getFilter().getEessName()+"%'";			
		}
		if(paramFilterHouses.getFilter().getMicrorredName() != null){
			WHERE += " AND A4.name LIKE '%"+paramFilterHouses.getFilter().getMicrorredName()+"%'";			
		}	
		
		String sql = " SELECT H.uuid, H.number, H.code, H.areas.id, H.streetName, H.streetNumber, H.areas.name, A2.name, A3.name, A4.name  "
				+ " FROM AreaDescendants AS AD RIGHT JOIN AD.areasByDescendantId AS A  LEFT JOIN A.houseses AS H "
				+ " RIGHT JOIN H.areas.areas AS A2 "
				+ " RIGHT JOIN A2.areas AS A3 "
				+ " RIGHT JOIN A3.areas AS A4 "+ WHERE;
		
		Query q = this.getEntityManager().createQuery(sql);		
	    
		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);		
	    
	    if(results != null){
	    	return results.size();
	    }else{
	    	return 0;
	    }	 
	}

	@Override
	public int getNumberHouse(String code) throws Exception {
		int number = 1;		
		String sql = " SELECT IFNULL(MAX(number)+1,1) AS number FROM Houses WHERE CODE LIKE '"+code+"%'";
		Query q = this.getEntityManager().createNativeQuery(sql);		
		number = ((BigInteger)q.getSingleResult()).intValue();		
        return number;
	}	


	@Override
	@Transactional
	public int updateStreetName(String streetNameOld, String streetNameNew, int areaId) throws Exception {
		 String query = "UPDATE Houses AS H SET H.streetName = '"+streetNameNew+"' WHERE H.streetName = '"+streetNameOld+"' AND H.areas.id = "+areaId;
		 return this.updateList(query);			
	}

	@Override
	@Transactional
	public int updateStreetNumber(String streetNameOld, String streetNumberOld, String streetNumnerNew, int areaId)	throws Exception {
		 String query = "UPDATE Houses AS H SET H.streetNumber = '"+streetNumnerNew+"' WHERE H.streetName = '"+streetNameOld+"' AND streetNumber = '"+streetNumberOld+"' AND H.areas.id = "+areaId;
		 return this.updateList(query);				
	}

	@Override
	public List<House> getList(Integer areaId) throws Exception {
		
		List<House> list = new ArrayList<House>();			
		
		String sql = "SELECT HEX(UUID),number,CODE,qrcode,latitude,longitude, streetName,streetNumber,personsNumber FROM Houses WHERE areaId = "+ areaId;
		
		Query q = this.getEntityManager().createNativeQuery(sql);
		
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		for (Object[] result : results) {		
			
     		String uuidHex = "";
			String uuid = "";
    		if(result[0] != null){	    			
    			uuidHex = ((String)result[0]).toLowerCase();
    			uuid = String.format("%s-%s-%s-%s-%s", uuidHex.substring(0,8), uuidHex.substring(8,12), uuidHex.substring(12,16), uuidHex.substring(16,20),uuidHex.substring(20,uuidHex.length()));
    		}    		
  		
    		String sql2  = "SELECT PE FROM Persons AS PE WHERE PE.houses.uuid = :housetUuid ";
    		List<Persons> personsList = new ArrayList<Persons>();
    	    Query q2 = this.getEntityManager().createQuery(sql2, Persons.class);
            q2.setParameter("housetUuid", UUID.fromString(uuid));  
            personsList = JpaResultHelper.getResultListAndCast(q2);   
    		List<Person> personList = new ArrayList<Person>();		
            for(int i= 0; i<personsList.size(); i++) {            	 
            	Persons p = personsList.get(i);
            	Person person = new SyncPersonPlan(p.getUuid().toString(),
  					   p.getGenre(),
  					   p.getBirthday().getTime(),
  					   p.isBirthdayExact(),
  					   p.isEnabled(),
  					   p.getName(),
  					   p.getCardId());	            	
            	personList.add(person);
            }
           
			Visits visit = new Visits();
			
			String sql3 = "SELECT VI FROM Visits AS VI WHERE VI.houses.uuid = :houseUuid ORDER BY VI.date DESC";
			Query q3 = this.getEntityManager().createQuery(sql3);							
	        q3.setParameter("houseUuid", UUID.fromString(uuid));  	 

	        List<Visits> lista = JpaResultHelper.getResultListAndCast(q3);
	        if(lista.size()>0){        	
	        	visit= lista.get(0);
	        }  
	        
	        Integer lastVisitScheduleId = 0;
	        boolean focus = false;
	        if(visit.getUuid() != null) {	        	
	    		String sql4 = " SELECT I.schedules.id FROM Visits AS V JOIN V.plans AS P JOIN P.inspections AS I WHERE V.uuid = :uuid ";			
	    		Query q4 = this.getEntityManager().createQuery(sql4, Integer.class);	    
	    		q4.setParameter("uuid", visit.getUuid());  	 
	    		lastVisitScheduleId = (Integer)q4.getSingleResult();
	        	
	    		String sql5 = "SELECT INV FROM Inventories AS INV WHERE INV.visits.uuid = :visitUuid AND INV.focus = :focus";
				List<Inventories> listInventories = new ArrayList<Inventories>();
				Query q5 = this.getEntityManager().createQuery(sql5, Inventories.class);
		        q5.setParameter("visitUuid", visit.getUuid());  
		        q5.setParameter("focus", 1);  
		        listInventories = JpaResultHelper.getResultListAndCast(q5);  		
				if(listInventories.size() > 0){
					focus = true;
				}       	
	        }		

			House house = new SyncHousePlan(uuid,
	 									 (Integer) result[1], 
	 									 (String) result[2],
	 									 (String) result[3], 
	 									 (BigDecimal) result[4],
	 									 (BigDecimal) result[5],
	 									 (String) result[6],
	 									 (String) result[7],
	 									 (visit.getDate() != null) ? visit.getDate().getTime() : null,
	 									 (visit.getTableElements() != null) ? visit.getTableElements().getId() : null,
	 									 focus,
		   			      			     (visit.getFeverish() > 0) ? true : false,
		   			      			     lastVisitScheduleId != 0 ? lastVisitScheduleId : null,   			                     
		   			                     (Integer) result[8], 
		   			                     personList);						
			list.add(house);	
					
			
		}
		return list;
	}	

}
