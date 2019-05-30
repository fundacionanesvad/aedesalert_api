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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.dao.model.Visits;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.HouseVisitListImpl;
import com.gruposca.sapev.api.modelview.InspectorRecordData;
import com.gruposca.sapev.api.modelview.ParamFilterVisits;
import com.gruposca.sapev.api.modelview.Visit;
import com.gruposca.sapev.api.modelview.VisitList;
import com.gruposca.sapev.api.modelview.VisitListImpl;
import com.gruposca.sapev.api.modelview.VisitPlan;

@Repository("VisitDaoImpl")
public class VisitDaoImpl extends BaseDaoHibernate<Visits, String> implements VisitDao {

	public VisitDaoImpl() { super(Visits.class); }

	@Override
	public List<Visit> getListVisits(Users user, UUID houseUuid) throws Exception {
		List<Visit> visitList = new ArrayList<Visit>();
		String sql = "SELECT VI.uuid, VI.date, LA.value, VI.feverish FROM Visits AS VI RIGHT JOIN VI.tableElements AS TE LEFT JOIN TE.labelses AS LA WHERE VI.houses.uuid = :houseUuid AND LA.languages.id = :languaje";
		Query q = this.getEntityManager().createQuery(sql);							
        q.setParameter("houseUuid", houseUuid);  	   
        q.setParameter("languaje", user.getLanguages().getId());        
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);		 
	    for (Object[] result : results) {	
	    	
	    	Visit visit = new HouseVisitListImpl(((UUID)result[0]).toString(), ((Date) result[1]).getTime(), (String) result[2], (Byte) result[3]);		    	
	    	visitList.add(visit);	
	    }
		    
	    return visitList;
	}

	@Override
	public VisitList getList(Users user, ParamFilterVisits paramFilterVisits, Integer inspectionId) throws Exception {
		
		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
		
		String WHERE =" WHERE AD.areaId = "+ user.getAreas().getId()+" AND L.languageId = :languaje ";
		
		if(inspectionId > 0) {
			WHERE += " AND P.inspectionId = "+inspectionId;		
		}	
		
		if(paramFilterVisits.getFilter().getHouseCode() != null){
			WHERE += " AND H.code LIKE '%"+paramFilterVisits.getFilter().getHouseCode()+"%'";
		}
		if(paramFilterVisits.getFilter().getDate() != null){
			WHERE += " AND V.date = '"+(fr.format(new Date(paramFilterVisits.getFilter().getDate())))+"'";			
		}
		if(paramFilterVisits.getFilter().getResultId() != null){
			WHERE += " AND T.id ="+paramFilterVisits.getFilter().getResultId();
		}
		if(paramFilterVisits.getFilter().getFeverish() != null){			
			if(paramFilterVisits.getFilter().getFeverish() == 0)
				WHERE += " AND V.feverish = 0 ";
			else		
				WHERE += " AND V.feverish = 1 ";
		}
		if(paramFilterVisits.getFilter().getAreaName() != null){
			WHERE += " AND A1.name LIKE '%"+paramFilterVisits.getFilter().getAreaName()+"%'";			
		}
		if(paramFilterVisits.getFilter().getSectorName() != null){
			WHERE += " AND A2.name LIKE '%"+paramFilterVisits.getFilter().getSectorName()+"%'";			
		}
		if(paramFilterVisits.getFilter().getEessName() != null){
			WHERE += " AND A3.name LIKE '%"+paramFilterVisits.getFilter().getEessName()+"%'";			
		}
		if(paramFilterVisits.getFilter().getMicrorredName() != null){
			WHERE += " AND A4.name LIKE '%"+paramFilterVisits.getFilter().getMicrorredName()+"%'";			
		}	
		if(paramFilterVisits.getFilter().getSample() != null){
			if(paramFilterVisits.getFilter().getSample() == 0)
				WHERE += " AND S.uuid IS NULL ";
			else		
				WHERE += " AND S.uuid IS NOT NULL ";
		}
		if(paramFilterVisits.getFilter().getUserName() != null){
			WHERE += " AND U.name LIKE '%"+paramFilterVisits.getFilter().getUserName()+"%'";
		}
		if(paramFilterVisits.getFilter().getHouseStreet() != null){
			WHERE += " AND H.streetName LIKE '%"+paramFilterVisits.getFilter().getHouseStreet()+"%'";
		}
		if(paramFilterVisits.getFilter().getHouseNumber() != null){
			WHERE += " AND H.streetNumber LIKE '%"+paramFilterVisits.getFilter().getHouseNumber()+"%'";
		}
		
		String ORDER =" ORDER BY";
		if(paramFilterVisits.getSorting().getHouseCode() != null){
			ORDER += " H.code "+paramFilterVisits.getSorting().getHouseCode();
		}else if (paramFilterVisits.getSorting().getDate() != null){
			ORDER += " V.date "+paramFilterVisits.getSorting().getDate();
		}else if(paramFilterVisits.getSorting().getResultName() != null){
			ORDER += " L.value "+paramFilterVisits.getSorting().getResultName();
		}else if(paramFilterVisits.getSorting().getFeverish() != null){
			ORDER += " V.feverish "+paramFilterVisits.getSorting().getFeverish();
		}else if(paramFilterVisits.getSorting().getAreaName() != null){
			ORDER += " A1.name "+paramFilterVisits.getSorting().getAreaName();	
		}else if(paramFilterVisits.getSorting().getSectorName() != null){
			ORDER += " A2.name "+paramFilterVisits.getSorting().getSectorName();	
		}else if(paramFilterVisits.getSorting().getEessName() != null){
			ORDER += " A3.name "+paramFilterVisits.getSorting().getEessName();	
		}else if(paramFilterVisits.getSorting().getMicrorredName() != null){
			ORDER += " A4.name "+paramFilterVisits.getSorting().getMicrorredName();	
		}else if(paramFilterVisits.getSorting().getSample() != null){
			ORDER += " S.uuid "+paramFilterVisits.getSorting().getSample();
		}else if(paramFilterVisits.getSorting().getUserName() != null){
			ORDER += " U.name "+paramFilterVisits.getSorting().getUserName();	
		}else if (paramFilterVisits.getSorting().getHouseStreet() != null){
			ORDER += " H.streetName "+paramFilterVisits.getSorting().getHouseStreet();
		}else if(paramFilterVisits.getSorting().getHouseNumber() != null){
			ORDER += " H.streetNumber "+paramFilterVisits.getSorting().getHouseNumber();
		}else{
			ORDER += " V.date "+paramFilterVisits.getSorting().getDate();
		}		
		
		Integer init = (paramFilterVisits.getPage() - 1) * paramFilterVisits.getCount();		
		
		List<VisitListImpl> visitList = new ArrayList<VisitListImpl>();
				

		String sql =" SELECT HEX(V.uuid) AS visitUUID, HEX(H.uuid) AS houseUUID,  H.code, H.streetName, H.streetNumber, V.date, T.id AS idTableElements," + 
				" L.value,  V.feverish, A1.id AS idArea, A1.name AS name1, A2.name AS name2, A3.name AS name3, " + 
				" A4.name AS name4, S.uuid , U.name AS userName " + 
				" FROM AreaDescendants AD " + 
				" INNER JOIN  Houses H ON H.areaId = AD.descendantId " + 
				" INNER JOIN Visits V ON V.houseUuid = H.uuid " + 
				" INNER JOIN Plans P ON P.id = V.planId " + 
				" INNER JOIN Users U ON U.id = V.userId " + 
				" INNER JOIN TableElements T ON T.id = V.resultId " + 
				" INNER JOIN Labels L ON L.tableElementId = T.id " + 
				" INNER JOIN Areas A1 ON A1.id = H.areaId " + 
				" INNER JOIN Areas A2 ON A2.id = A1.parentId " + 
				" INNER JOIN Areas A3 ON A3.id = A2.parentId " + 
				" INNER JOIN Areas A4 ON A4.id = A3.parentId " + 
				" LEFT JOIN Samples S ON (S.houseUuid = H.uuid AND V.planId = S.planId) " + WHERE +
				" GROUP BY V.uuid " + ORDER;		
		
		Query q = this.getEntityManager().createNativeQuery(sql);							
	    q.setParameter("languaje", user.getLanguages().getId());        
	    q.setFirstResult(init);
	    q.setMaxResults(paramFilterVisits.getCount());
		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		
		for (Object[] result : results) {		    
					
			String visiUuidHex = ((String)result[0]).toLowerCase();
			String visiUuid = String.format("%s-%s-%s-%s-%s", visiUuidHex.substring(0,8), visiUuidHex.substring(8,12), visiUuidHex.substring(12,16), visiUuidHex.substring(16,20),visiUuidHex.substring(20,visiUuidHex.length()));
			
			String houseUuidHex = ((String)result[1]).toLowerCase();
			String houseUuid = String.format("%s-%s-%s-%s-%s", houseUuidHex.substring(0,8), houseUuidHex.substring(8,12), houseUuidHex.substring(12,16), houseUuidHex.substring(16,20),houseUuidHex.substring(20,houseUuidHex.length()));
		
			VisitListImpl visit = new VisitListImpl(visiUuid,
											houseUuid,
		   			                        (String) result[2],
		   			                        (String) result[3],
		   			                        (String) result[4],
		   			                        ((Date) result[5]).getTime(),
		   			                        (Integer) result[6], 
		   			                        (String) result[7],
		   			                        (Byte) result[8],
		   			                        (Integer) result[9],
		   			                        (String) result[10],
		   			                        (String) result[11],
		   			                        (String) result[12],
		   			                        (String) result[13],
		   			                        result[14] == null ? false : true,
		   			                        (String) result[15]);
		   	visitList.add(visit);	
		}


		String sql2 =" SELECT COUNT(DISTINCT V.uuid) " +
				" FROM AreaDescendants AD " + 
				" INNER JOIN  Houses H ON H.areaId = AD.descendantId " + 
				" INNER JOIN Visits V ON V.houseUuid = H.uuid " + 
				" INNER JOIN Plans P ON P.id = V.planId " + 
				" INNER JOIN Users U ON U.id = V.userId " + 
				" INNER JOIN TableElements T ON T.id = V.resultId " + 
				" INNER JOIN Labels L ON L.tableElementId = T.id " + 
				" INNER JOIN Areas A1 ON A1.id = H.areaId " + 
				" INNER JOIN Areas A2 ON A2.id = A1.parentId " + 
				" INNER JOIN Areas A3 ON A3.id = A2.parentId " + 
				" INNER JOIN Areas A4 ON A4.id = A3.parentId " + 
				" LEFT JOIN Samples S ON (S.houseUuid = H.uuid AND V.planId = S.planId) " + WHERE ;				
		
		q = this.getEntityManager().createNativeQuery(sql2);	
		q.setParameter("languaje", user.getLanguages().getId());    
		Integer result = ((BigInteger)q.getSingleResult()).intValue();
		VisitList visiList = new VisitList(result, visitList);
		return visiList;
	}

	@Override
	public Visits findByUUID(UUID uuid) throws Exception {
		String sqlQuery = "select VI from Visits as VI where VI.uuid = :uuid ";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Visits.class);
        q.setParameter("uuid", uuid);        
        Visits visit = (Visits) JpaResultHelper.getSingleResultOrNull(q);
	    return visit;	   
	}

	@Override
	public List<VisitPlan> getVisitPlanList(Users user, Integer planId) throws Exception {
		List<VisitPlan> visitPlantList = new ArrayList<VisitPlan>();
		String sql = "SELECT VI.uuid, VI.date, LA.value, VI.feverish, TE.id FROM Visits AS VI RIGHT JOIN VI.tableElements AS TE LEFT JOIN TE.labelses AS LA WHERE VI.plans.id = :planId AND LA.languages.id = :languaje";
		Query q = this.getEntityManager().createQuery(sql);							
        q.setParameter("planId", planId);  	   
        q.setParameter("languaje", user.getLanguages().getId());        
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);		 
	    for (Object[] result : results) {	
	    	VisitPlan visitPlan = new VisitPlan(((UUID)result[0]).toString(), ((Date) result[1]).getTime(), (String) result[2], (Byte) result[3], (Integer) result[4]);		    	
	    	visitPlantList.add(visitPlan);	
	    }		    
		return visitPlantList;
	}
	
	@Override
	public Visits getLastVisit(UUID houseUuid) throws Exception {
		
		Visits visit = new Visits();
		
		String sql = "SELECT VI FROM Visits AS VI WHERE VI.houses.uuid = :houseUuid ORDER BY VI.date DESC";
		Query q = this.getEntityManager().createQuery(sql);							
        q.setParameter("houseUuid", houseUuid);  	 

        List<Visits> lista = JpaResultHelper.getResultListAndCast(q);
        if(lista.size()>0){        	
        	visit= lista.get(0);
        }         
       	    
		return visit;
	}

	@Override
	public List<VisitPlan> getVisitInspectionList(Users user, Integer inspectionId) throws Exception {
		List<VisitPlan> visitList = new ArrayList<VisitPlan>();
		String sql = "SELECT VI.uuid, VI.date, LA.value, VI.feverish FROM Visits AS VI RIGHT JOIN VI.tableElements AS TE LEFT JOIN TE.labelses AS LA "
				+ " RIGHT JOIN VI.plans AS PL RIGHT JOIN PL.inspections AS INSP "
				+ " WHERE INSP.id = :inspectionId AND LA.languages.id = :languaje";
		Query q = this.getEntityManager().createQuery(sql);							
	    q.setParameter("inspectionId", inspectionId);  	   
	    q.setParameter("languaje", user.getLanguages().getId());        
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);		 
	    for (Object[] result : results) {	
	    	VisitPlan visit = new VisitPlan(((UUID)result[0]).toString(), ((Date) result[1]).getTime(), (String) result[2], (Byte) result[3]);		    	
	    	visitList.add(visit);	
	    }		    
		return visitList;
	}

	@Override
	public Visits getVisitClosed(Integer inspectionId, Integer resultId, UUID houseUuid) throws Exception {		
		String sql = "SELECT VI FROM Inspections AS I JOIN I.planses AS P JOIN P.visitses AS VI WHERE VI.houses.uuid = :houseUuid AND VI.tableElements.id = :resultId "
				+ " AND I.id = :inspectionId ";
		Query q = this.getEntityManager().createQuery(sql);							
        q.setParameter("houseUuid", houseUuid);  	
        q.setParameter("resultId", resultId);  	 
        q.setParameter("inspectionId", inspectionId);
        List<Visits> lista = JpaResultHelper.getResultListAndCast(q);
        if(lista.size()>0){        	
        	return lista.get(0);
        }else{
        	return null;
        }
	}

	@Override
	public Integer getScheduleId(UUID uuid) throws Exception {
		String sqlQuery= "";
		sqlQuery = " SELECT I.schedules.id FROM Visits AS V JOIN V.plans AS P JOIN P.inspections AS I WHERE V.uuid = :uuid ";			
		Query q = this.getEntityManager().createQuery(sqlQuery, Integer.class);	    
        q.setParameter("uuid", uuid);  	 
        Integer scheduleId = (Integer)q.getSingleResult();
        return scheduleId;	
	}

	@Override
	public Integer reconvertedVisit(Integer scheduleId, UUID houseUuid) throws Exception {
		Integer result = 0;
		String sqlQuery = " SELECT Visits.resultId AS NUM FROM Inspections INNER JOIN Plans ON " + 
				" Plans.inspectionId = Inspections.id" + 
				" INNER JOIN Visits ON Visits.planId = Plans.Id " + 
				" WHERE Visits.houseUuid = :uuid AND Inspections.scheduleId = :id" + 
				" AND (Visits.resultId = 2002 OR Visits.resultId = 2003 OR Visits.resultId = 2004) ";			
		
		Query q = this.getEntityManager().createNativeQuery(sqlQuery);	
    	q.setParameter("uuid", houseUuid); 
        q.setParameter("id", scheduleId);     
        List<Integer> lista = JpaResultHelper.getResultListAndCast(q);
        if(lista.size() > 0){
        	 result = (Integer)lista.get(0);
        }                
		return result;
	}

	@Override
	public List<InspectorRecordData> geListInspectorRecodData(Integer planId) throws Exception {
		List<InspectorRecordData> list = new ArrayList<InspectorRecordData>();
		String sql = " SELECT  DISTINCT H.streetName, H.streetNumber, A.name as aname, H.personsNumber,VI.larvicide, VI.feverish,HEX(VI.uuid) as vuuid, HEX(H.uuid), A2.name, "+
				"IF((L.value='Inspeccionada'),'CI',IF((L.value='Abandonada'),'CA',IF((L.value='Cerrada'),'CC',IF((L.value='Renuente'),'CR','')))) AS tipo " + 
				" FROM Visits AS VI INNER JOIN Plans AS P ON P.id=VI.planId " + 
				" INNER JOIN Labels AS L ON L.tableElementId = VI.resultId " + 
				" INNER JOIN Houses AS H ON H.uuid=VI.houseUuid " + 
				" INNER JOIN Areas AS A ON A.id = H.areaId " + 
				" INNER JOIN Areas AS A2 ON A2.id = A.parentId " + 
				" WHERE P.id ="+planId;	
	
		Query q = this.getEntityManager().createNativeQuery(sql);							
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);		 
	    for (Object[] result : results) {	
 System.out.println(result[6].getClass());

 
 String visiUuidHex = ((String)result[6]).toLowerCase();
	String visiUuid = String.format("%s-%s-%s-%s-%s", visiUuidHex.substring(0,8), visiUuidHex.substring(8,12), visiUuidHex.substring(12,16), visiUuidHex.substring(16,20),visiUuidHex.substring(20,visiUuidHex.length()));
	
	String houseUuidHex = ((String)result[7]).toLowerCase();
	String houseUuid = String.format("%s-%s-%s-%s-%s", houseUuidHex.substring(0,8), houseUuidHex.substring(8,12), houseUuidHex.substring(12,16), houseUuidHex.substring(16,20),houseUuidHex.substring(20,houseUuidHex.length()));
	System.out.println(houseUuid);
 	    	InspectorRecordData inspectorRecordData = new InspectorRecordData((String) result[0],
	    																	(String) result[1],
	    																	(String) result[2],
	    																	(Integer) result[3],
	    																	(BigDecimal) result[4],	    																	
	    																	((Byte) result[5]).intValue(),
	    																	UUID.fromString(visiUuid),
	    																	UUID.fromString(houseUuid),
	    																	(String) result[8],
	    																	(String) result[9]);
	    	list.add(inspectorRecordData);	
	    }
	    System.out.println(sql);
	    return list;
	}
	
	@Override
	@Transactional
	public Integer deleteVisit(UUID uuid) {
		String sqlQuery = " DELETE Samples FROM Visits INNER JOIN Samples ON (Visits.planId = Samples.planId AND Visits.houseUuid = Samples.houseuuid) " + 
						  " WHERE Visits.uuid = :uuid AND Samples.result IS NULL";
	    Query q = this.getEntityManager().createNativeQuery(sqlQuery);	    
        q.setParameter("uuid", uuid); 
        return q.executeUpdate();
	}	
}
