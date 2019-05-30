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
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.AreaDescendants;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Languages;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.AreaMapFocus;
import com.gruposca.sapev.api.modelview.AreaMapList;
import com.gruposca.sapev.api.modelview.SamplesMapFocus;


@Repository("AreaDaoImpl")
public class AreaDaoImpl extends BaseDaoHibernate<Areas, String> implements AreaDao{

	public AreaDaoImpl() { super(Areas.class); }

	@Override
	public Long numChilds(Areas area) throws Exception {	
		
		String sqlQuery = "select COUNT(AR.id) from Areas as AR where AR.areas = :area ";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Long.class);	    
        q.setParameter("area", area);  
        Long count = (Long)q.getSingleResult();
        return count;
	}

	@Override/*666*/
	public List<Areas> getListAreas(Areas parentArea) throws Exception {
		
		String sqlQuery = "select AR from Areas as AR where AR.areas = :area ORDER BY AR.name";
		List<Areas> listAreas = new ArrayList<Areas>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Areas.class);
        q.setParameter("area", parentArea);  
        listAreas = JpaResultHelper.getResultListAndCast(q);
        System.out.println("#1#"+sqlQuery);
		return listAreas;
	}	

	@Override
	public List<Areas> getListAreasPlanInspection(Integer inspectionId) throws Exception {
		String sqlQuery = "select PA.areas from Inspections AS I RIGHT JOIN I.planses AS PL RIGHT JOIN PL.plansAreases AS PA where I.id = :inspectionId GROUP BY PA.areas";
		List<Areas> listAreas = new ArrayList<Areas>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Areas.class);
        q.setParameter("inspectionId", inspectionId);  
        listAreas = JpaResultHelper.getResultListAndCast(q);       
		return listAreas;
	}

	@Override 
	public List<AreaMapList> getListAreasMap(Areas area , Date startDate, Date finishDate, int userAreaId) throws Exception {
		List<AreaMapList> listAreaMapList = new ArrayList<AreaMapList>();
		AreaMapList areaMapList;
		String sql= "";
		sql = " SELECT A.id, A.name, A.coords, A.leaf FROM Areas AS A WHERE A.areas IN (select A2.id from Areas AS A2 WHERE A2.areas IN(SELECT A3.areas.id FROM Areas AS A3 WHERE A3.id = :areaId))";
		Query q = this.getEntityManager().createQuery(sql);
		q.setParameter("areaId", area.getId());			
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);		  
		
		if(results.size() == 0 && !area.isLeaf()){
			sql = " SELECT A.id, A.name, A.coords, A.leaf FROM Areas AS A WHERE A.areas.id = :areaId";
			q = this.getEntityManager().createQuery(sql);
			q.setParameter("areaId", area.getId());	
			results = JpaResultHelper.getResultListAndCast(q);			
		}
		
		for (Object[] result : results) {			
			if(userPermissionArea(userAreaId, (Integer)result[0])) {
				areaMapList = new AreaMapList((Integer)result[0], (String)result[1], (String)result[2], (Boolean)result[3], getIndexAedico((Integer)result[0], startDate, finishDate));	
		    	listAreaMapList.add(areaMapList);	    	
			}
	    }					
		return listAreaMapList;
	}
	
	public boolean userPermissionArea(int userAreaId, int areaId) {
		String sqlQuery = " SELECT COUNT(*) FROM AreaDescendants " + 
					" WHERE ((areaId = "+areaId+" AND descendantId = "+userAreaId+") OR (areaId = "+userAreaId+" AND descendantId = "+areaId+"))";
		 Query q = this.getEntityManager().createQuery(sqlQuery, Long.class);	    
	     Long count = (Long)q.getSingleResult();
	     
	     if(count > 0) return true;
	     else return false;				
	}
	
	 
	public BigDecimal getIndexAedico(Integer areaId, Date startDate, Date finishDate){
		BigDecimal indexConsolidado = BigDecimal.ZERO;	
		BigDecimal focusHousesConsolidado = this.getFocusHousesConsolidado(areaId, startDate, finishDate);
		BigDecimal inspectedConsolidado = this.getInspectedHousesConsolidado(areaId, startDate, finishDate);		
		if(inspectedConsolidado.compareTo(BigDecimal.ZERO) == 0){
			indexConsolidado = new BigDecimal("-1");
		}else{			
			indexConsolidado = ((focusHousesConsolidado).multiply(new BigDecimal("100"))).divide(inspectedConsolidado,2, RoundingMode.HALF_UP);	
			 //System.out.println(focusHousesConsolidado+"//"+inspectedConsolidado);
		}
		System.out.println(focusHousesConsolidado+"//"+inspectedConsolidado);
        return indexConsolidado;		
	}
	
	
	public BigDecimal getIspectedHouses(Integer areaId, Date startDate, Date finishDate){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sqlQuery= "";

		sqlQuery = "SELECT COUNT(DISTINCT H.uuid) FROM Areas AS A JOIN A.houseses AS H "
				+ " JOIN H.visitses AS V"
				+ " WHERE A.id IN (SELECT AD.areasByDescendantId.id FROM AreaDescendants AS AD WHERE AD.areasByAreaId.id  = :areaId ) "
				+ " AND V.tableElements.id = 2001 "
				+ " AND (V.date BETWEEN '"+df.format(startDate)+"' AND '"+df.format(finishDate)+"')";

		
		 Query q = this.getEntityManager().createQuery(sqlQuery, Long.class);	    
	     q.setParameter("areaId", areaId);  
	     Long count = (Long)q.getSingleResult();
	     
	     return new BigDecimal(count);		
	}
	
	 
	public BigDecimal getInspectedHousesConsolidado(Integer areaId, Date startDate, Date finishDate){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sqlQuery= "";
		sqlQuery = "SELECT IFNULL(SUM(inspected), 0)"
				+ " FROM VisitSummaries AS V"
				+ " INNER JOIN Plans AS P ON V.planId = P.id"
				+ " INNER JOIN Inspections AS I ON I.id = P.inspectionId"
				+ " WHERE V.areaId IN (SELECT descendantId FROM AreaDescendants WHERE areaId = "+areaId+") "
				+ " AND (P.date BETWEEN '"+df.format(startDate)+"' AND '"+df.format(finishDate)+"')"
				+ " AND I.typeId=1001";	
		Query q = this.getEntityManager().createNativeQuery(sqlQuery);	
		BigDecimal inspected = (BigDecimal)q.getSingleResult();
		return inspected;	
	}
	
	
	public BigDecimal getFocusHouses(Integer areaId, Date startDate, Date finishDate){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sqlQuery= "";
		sqlQuery = "SELECT COUNT(DISTINCT H.uuid) FROM Areas AS A JOIN A.houseses AS H "
				+ " JOIN H.visitses AS V"
				+ " JOIN V.inventorieses AS I with I.focus > 0 "
				+ " WHERE A.id IN (SELECT AD.areasByDescendantId.id FROM AreaDescendants AS AD WHERE AD.areasByAreaId.id  = :areaId ) "
				+ " AND (V.date BETWEEN '"+df.format(startDate)+"' AND '"+df.format(finishDate)+"')";

		 Query q = this.getEntityManager().createQuery(sqlQuery, Long.class);	    
	     q.setParameter("areaId", areaId);  
	     Long count = (Long)q.getSingleResult();
	     
	     return new BigDecimal(count);		
	}
	
	 
	public BigDecimal getFocusHousesConsolidado(Integer areaId, Date startDate, Date finishDate){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sqlQuery= "";
		sqlQuery = "SELECT IFNULL(SUM(focus), 0)"
				+ " FROM VisitSummaries AS V"
				+ " INNER JOIN Plans AS P ON V.planId = P.id"
				+ " INNER JOIN Inspections AS I ON I.id = P.inspectionId"
				+ " WHERE V.areaId IN (SELECT descendantId FROM AreaDescendants WHERE areaId = "+areaId+") "
				+ " AND (P.date BETWEEN '"+df.format(startDate)+"' AND '"+df.format(finishDate)+"')"
				+ " AND I.typeId=1001";	
		System.out.println(sqlQuery);
		Query q = this.getEntityManager().createNativeQuery(sqlQuery);	
		BigDecimal focus = (BigDecimal)q.getSingleResult();
		return focus;	     
	}
	
	
	
	@Override
	public List<AreaMapFocus> getListAreasMapFocus(Areas area, int userAreaId) throws Exception {
		List<AreaMapFocus> list = new ArrayList<AreaMapFocus>();
		AreaMapFocus areaMapFocus;
		String sql= "";
		sql = " SELECT A.id, A.name, A.coords, A.leaf, A.latitude, A.longitude FROM Areas AS A WHERE A.areas IN (select A2.id from Areas AS A2 WHERE A2.areas IN(SELECT A3.areas.id FROM Areas AS A3 WHERE A3.id = :areaId))";
		Query q = this.getEntityManager().createQuery(sql);
		q.setParameter("areaId", area.getId());			
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);		    
		
		if(results.size() == 0 && !area.isLeaf()){
			sql = " SELECT A.id, A.name, A.coords, A.leaf, A.latitude, A.longitude  FROM Areas AS A WHERE A.areas.id = :areaId";
			q = this.getEntityManager().createQuery(sql);
			q.setParameter("areaId", area.getId());	
			results = JpaResultHelper.getResultListAndCast(q);	 
		}
		
		for (Object[] result : results) {	
			
			if(userPermissionArea(userAreaId, (Integer)result[0])) {
				areaMapFocus = new AreaMapFocus((Integer)result[0], (String)result[1], (String)result[2], (Boolean)result[3],(BigDecimal)result[4], (BigDecimal)result[5], null);	
				list.add(areaMapFocus);	 
			}
	    }		    
		return list;
	}	
	
	@Override
	public List<SamplesMapFocus> getSamplesMapFocus(Integer areaId, Date startDate, Date finishDate, Languages language) throws Exception{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<SamplesMapFocus> list = new ArrayList<SamplesMapFocus>();
		SamplesMapFocus samplesMapFocus;	
		
		String sql =   "SELECT * FROM"
				+ "(SELECT HEX(V.uuid), P.date, S.result, H.latitude, H.longitude, H.areaId, HEX(S.uuid)"
				+ " FROM Samples AS S INNER JOIN Houses AS H ON S.houseUuid = H.uuid"
				+ " INNER JOIN Plans AS P ON P.id = S.planId"
				+ " LEFT JOIN Visits AS V ON (V.planId = S.planId AND V.houseUuid = S.houseUuid)"
				+ " WHERE H.areaId IN (SELECT AD.descendantId FROM AreaDescendants AS AD WHERE AD.areaId  = "+areaId+" )"
				+ " AND (P.date BETWEEN '"+df.format(startDate)+"' AND '"+df.format(finishDate)+"') AND result != '' "
				+ " ORDER BY H.areaId, S.houseUuid, result) AS A1"
				+ " UNION ALL"
				+ " SELECT * FROM"
				+ " (SELECT HEX(V.uuid), P.date, S.result, H.latitude, H.longitude, H.areaId , HEX(S.uuid)"
				+ " FROM Samples AS S INNER JOIN Houses AS H ON S.houseUuid = H.uuid"
				+ " INNER JOIN Plans AS P ON P.id = S.planId"
				+ " LEFT JOIN Visits AS V ON (V.planId = S.planId AND V.houseUuid = S.houseUuid)"
				+ " WHERE H.areaId IN (SELECT AD.descendantId FROM AreaDescendants AS AD WHERE AD.areaId  = "+areaId+" )"
				+ " AND (P.date BETWEEN '"+df.format(startDate)+"' AND '"+df.format(finishDate)+"') AND result IS NULL "
				+ " ) AS A2" ;
		
	    Query q = this.getEntityManager().createNativeQuery(sql);	  

	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	
	    if (results.size() > 0){
	    	
	    	for (Object[] result : results) {	
	    		String uuid = "";
	    		String uuidHex = "";
	    		if(result[0] != null){	    			
	    			uuidHex = ((String)result[0]).toLowerCase();
	    			uuid = String.format("%s-%s-%s-%s-%s", uuidHex.substring(0,8), uuidHex.substring(8,12), uuidHex.substring(12,16), uuidHex.substring(16,20),uuidHex.substring(20,uuidHex.length()));
	    		}
	    		
	    		String phaseName = "";		
	    		String queryString = "SELECT DISTINCT L.value FROM Samples S INNER JOIN SamplePhases SP ON S.uuid = SP.sampleUuid" + 
	    		" INNER JOIN TableElements  T ON T.id = SP.phaseId" + 
	    		" INNER JOIN Labels L ON L.tableElementId = T.id " + 
	    		" WHERE L.languageId = :languageId" + 
	    		" AND S.uuid = UNHEX('"+(String)result[6]+"') " + 
	    		" ORDER BY T.sort ";
	    		
	    		Query q2 = this.getEntityManager().createNativeQuery(queryString);	 
	    		q2.setParameter("languageId", language.getId()); 
	    		List<String> samples = JpaResultHelper.getResultListAndCast(q2);	 

	    		for(int i = 0; i < samples.size(); i++) {			
	    			phaseName = phaseName+(String)samples.get(i)+", ";
	    		}
	    		
	    		if(!phaseName.equals("")) {
	    			phaseName = phaseName.substring(0,phaseName.length()-2);
	    		}
   		
		    	samplesMapFocus = new SamplesMapFocus( uuid,
									    			  ((Date)result[1]).getTime(), 
                                                      ((String)result[2] == null ? "" : (String)result[2]), 
									    			  phaseName, 
									    			  (BigDecimal)result[3],  
									    			  (BigDecimal)result[4], 
									    			  (Integer)result[5]);
		    	list.add(samplesMapFocus);
	    	}	
	    }
        return list;		
	}

	@Override
	public boolean userAreaPermission(Integer userAreaId, Integer areaId)throws Exception {
		String sqlQuery = "select AD from AreaDescendants as AD where AD.areasByAreaId.id = :userAreaId AND AD.areasByDescendantId.id = :areaId";
		List<AreaDescendants> list = new ArrayList<AreaDescendants>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, AreaDescendants.class);
        q.setParameter("userAreaId", userAreaId); 
        q.setParameter("areaId", areaId); 
	    list = JpaResultHelper.getResultListAndCast(q);       
	    if(list.size() > 0){
			return true;			
		} else {
			return false;
		}
	}

	@Override
	public List<Areas> getParentsPlan(Integer planId) throws Exception {
		List<Areas> list = new ArrayList<Areas>();
		String sqlQuery= "";
		sqlQuery = "SELECT A2"
				+ " FROM PlansAreas AS P JOIN P.areas AS A1 JOIN A1.areas AS A2 WHERE P.plans.id = "+planId
				+ " GROUP BY A2.id ";		
	    Query q = this.getEntityManager().createQuery(sqlQuery, Areas.class);
	    list = JpaResultHelper.getResultListAndCast(q);       
		return list;     
	}

	@Override
	public List<Areas> getListChildByType(Areas parentArea, Integer typeId) throws Exception {
		String sqlQuery = "select AR from AreaDescendants as AD RIGHT JOIN AD.areasByDescendantId AS AR where AD.areasByAreaId = :area AND AD.areasByDescendantId != :area AND AR.tableElementsByTypeId.id = :typeId";
		List<Areas> listAreas = new ArrayList<Areas>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Areas.class);
        q.setParameter("area", parentArea); 
        q.setParameter("typeId", typeId); 
        listAreas = JpaResultHelper.getResultListAndCast(q);       
		return listAreas;
	}

	@Override
	public List<Areas> getListByType(Areas parentArea, Integer typeId)throws Exception {
		String sqlQuery = "select AR from AreaDescendants as AD RIGHT JOIN AD.areasByDescendantId AS AR where AD.areasByAreaId = :area AND AR.tableElementsByTypeId.id = :typeId";
		List<Areas> listAreas = new ArrayList<Areas>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Areas.class);
        q.setParameter("area", parentArea); 
        q.setParameter("typeId", typeId); 
        listAreas = JpaResultHelper.getResultListAndCast(q);       
		return listAreas;
	}

	@Override
	public Integer getAreaParent(String listAreasId, Integer listSize)	throws Exception {
		String sql = "SELECT areaId"
				+ " FROM AreaDescendants"
				+ " WHERE descendantId IN ("+listAreasId+")"
				+ " GROUP BY areaId"
				+ " HAVING COUNT(*) = "+listSize
				+ " ORDER BY areaId DESC"
				+ " LIMIT 1";
		
		Query q = this.getEntityManager().createNativeQuery(sql);
        Integer areaId = (Integer)q.getSingleResult();
        return areaId;
	}

	@Override
	public Areas getParentArea() throws Exception {
		System.out.println("--- getParentArea");
		String sqlQuery = "select A from Areas as A where A.areas is null";
		Query q = this.getEntityManager().createQuery(sqlQuery, Areas.class);
		Areas area = (Areas) JpaResultHelper.getSingleResultOrNull(q);
	    return area;	   
	}

	@Override
	public List<Areas> getListAreasOrder() throws Exception {
		System.out.println("--- getListAreasOrder");
		String sqlQuery = "select AR from Areas as AR WHERE AR.tableElementsByTypeId.id < 9006 ORDER BY AR.areas.id";
		List<Areas> listAreas = new ArrayList<Areas>();
		System.out.println("sqlQuery::"+sqlQuery);
	    Query q = this.getEntityManager().createQuery(sqlQuery, Areas.class);
        listAreas = JpaResultHelper.getResultListAndCast(q);         
		return listAreas;
	}

	@Override
	public List<Areas> getListAreasSectorInspection(Integer inspectionId) throws Exception {
		String sqlQuery = "SELECT DISTINCT A1.* FROM Plans "
				+ " INNER JOIN PlansAreas ON Plans.id = PlansAreas.planId" + 
				" INNER JOIN Areas ON PlansAreas.areaId = Areas.Id" + 
				" INNER JOIN Areas AS A1 ON A1.id = Areas.parentId" + 
				" WHERE inspectionId = "+inspectionId;
		List<Areas> listAreas = new ArrayList<Areas>();
	    Query q = this.getEntityManager().createNativeQuery(sqlQuery, Areas.class);       
        listAreas = JpaResultHelper.getResultListAndCast(q);       
		return listAreas;
	}

	@Override
	public List<Areas> getListAreasManzanaInspection(Integer inspectionId, Integer sectorId) throws Exception {
		String sqlQuery = "SELECT DISTINCT Areas.* FROM Plans" + 
				" INNER JOIN PlansAreas ON Plans.id = PlansAreas.planId" + 
				" INNER JOIN Areas ON PlansAreas.areaId = Areas.Id" + 
				" INNER JOIN Areas AS A1 ON A1.id = Areas.parentId" + 
				" WHERE inspectionId = "+inspectionId+" AND A1.id = "+ sectorId;
		List<Areas> listAreas = new ArrayList<Areas>();
	    Query q = this.getEntityManager().createNativeQuery(sqlQuery, Areas.class);       
        listAreas = JpaResultHelper.getResultListAndCast(q);       
		return listAreas;
	}

	@Override
	public String getSectorNames(int planId) throws Exception {
		String sqlQuery= "";
		sqlQuery = "SELECT " + 
				" GROUP_CONCAT(DISTINCT A2.name) AS NAME FROM " + 
				" Areas AS A INNER JOIN Areas AS A2 ON A.parentId = A2.id " + 
				" INNER JOIN PlansAreas AS P ON P.areaId = A.id " + 
				" WHERE P.planId = :planId" + 
				" GROUP BY P.planId ";			

		Query q = this.getEntityManager().createNativeQuery(sqlQuery);	    
	    q.setParameter("planId", planId);  
	    String name = (String)q.getSingleResult();
	    return name;		
	}

	@Override
	public List<Areas> getListAreasMR(Integer microrredId, Integer eessId) throws Exception {
		String sqlQuery = "SELECT A FROM Areas AS A WHERE A.areas.id = "+microrredId; 
		if(eessId != null) {
			sqlQuery +=" AND A.id = "+eessId;
		}
		sqlQuery += " ORDER BY A.name ";
		List<Areas> listAreas = new ArrayList<Areas>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Areas.class);
        listAreas = JpaResultHelper.getResultListAndCast(q);         
		return listAreas;
	}

	@Override
	public List<Areas> getAreasPlanVisits(Integer planId) throws Exception {
		String sqlQuery = " SELECT DISTINCT Areas.* FROM Visits " + 
				" INNER JOIN Houses ON Houses.uuid = Visits.houseUuid " + 
				" INNER JOIN Areas ON Areas.id = Houses.areaId " + 				
				" WHERE planId = "+planId;
		List<Areas> listAreas = new ArrayList<Areas>();
	    Query q = this.getEntityManager().createNativeQuery(sqlQuery, Areas.class);       
        listAreas = JpaResultHelper.getResultListAndCast(q);       
		return listAreas;
	}

	@Override
	@Transactional

	public Integer executeProc(int areaId) throws Exception {

		String sqlQuery = "CALL updateAreaHouses("+areaId+");";
		
		System.out.println(sqlQuery);
		
		Query q = this.getEntityManager().createNativeQuery(sqlQuery);    
		
        return q.executeUpdate();
	}

	@Override
	public List<Areas> getListAreasEess(Areas redArea) throws Exception {
		String sqlQuery = "select AEESS from Areas as AEESS JOIN AEESS.areas AS AMR JOIN AMR.areas As AR where AR.id = :id ORDER BY AEESS.name";		
		List<Areas> listAreas = new ArrayList<Areas>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Areas.class);
        q.setParameter("id", redArea.getId());  
        listAreas = JpaResultHelper.getResultListAndCast(q);          
		return listAreas;
	}

	@Override
	public List<Areas> getScheduleAteasChilds(int scheduleId) throws Exception {
		String sqlQuery = "select I.areas from Inspections as I where I.schedules.id = :scheduleId";
		List<Areas> listAreas = new ArrayList<Areas>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Areas.class);
        q.setParameter("scheduleId", scheduleId);  
        listAreas = JpaResultHelper.getResultListAndCast(q);           
		return listAreas;
	}
	
}
