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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Inspections;
import com.gruposca.sapev.api.connectors.dao.model.Schedules;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.FocusHouse;
import com.gruposca.sapev.api.modelview.Inspection;
import com.gruposca.sapev.api.modelview.InspectionImpl;
import com.gruposca.sapev.api.modelview.InspectionListImpl;
import com.gruposca.sapev.api.modelview.InspectionSchedule;
import com.gruposca.sapev.api.modelview.InspectionTrapImpl;
import com.gruposca.sapev.api.modelview.ParamFilterInspections;
import com.gruposca.sapev.api.modelview.ReportInspection;
import com.gruposca.sapev.api.modelview.StatsContainers;
import com.gruposca.sapev.api.modelview.StatsEessResults;
import com.gruposca.sapev.api.modelview.StatsMrResults;

@Repository("InspectionDaoImpl")
public class InspectionDaoImpl extends BaseDaoHibernate<Inspections, String> implements InspectionDao{
	
	public InspectionDaoImpl() { super(Inspections.class); }

	@Override
	public List<Inspections> getListByArea(Integer areaId) throws Exception {
		
		List<Inspections> inspectionList = new ArrayList<Inspections>();		
		String sqlQuery = "SELECT INS FROM Inspections AS INS WHERE INS.areas.id = :areaId ";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Inspections.class);
        q.setParameter("areaId", areaId);  
        inspectionList = JpaResultHelper.getResultListAndCast(q);  
		return inspectionList;	
	}
	
	@Override
	public Inspections updateInspectionsDates(Integer inspectionId) throws Exception {
		
		Inspections inspections = this.find(inspectionId);
		String sql = " SELECT MIN(P.date), MAX(P.date) FROM Plans AS P RIGHT JOIN P.inspections AS I WHERE I.id = :inspectionId";
		Query q = this.getEntityManager().createQuery(sql);							
	    q.setParameter("inspectionId", inspectionId);       
		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
		for (Object[] result : results) {	
			inspections.setStartDate((Date) result[0]);
			inspections.setFinishDate((Date) result[1]);	
		}		
		return inspections;
	}

	@Override
	public List<InspectionListImpl> getListByAreaUser(Users user, ParamFilterInspections paramFilterInspections) throws Exception {
		List<InspectionListImpl> inspectionList = new ArrayList<InspectionListImpl>();
		

		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
		
		String WHERE =" WHERE AreaDescendants.areaId = "+user.getAreas().getId()+" AND L1.languageId = "+user.getLanguages().getId()+" AND L2.languageId = "+user.getLanguages().getId();
		
		if(paramFilterInspections.getFilter().getAreaName() != null){
			WHERE += " AND A1.name LIKE '%"+paramFilterInspections.getFilter().getAreaName()+"%'";			
		}
		if(paramFilterInspections.getFilter().getStartDate() != null){
			WHERE += " AND Inspections.startDate >= '"+(fr.format(new Date(paramFilterInspections.getFilter().getStartDate())))+"'";			
		}		
		if(paramFilterInspections.getFilter().getFinishDate() != null){
			WHERE += " AND Inspections.finishDate <= '"+(fr.format(new Date(paramFilterInspections.getFilter().getFinishDate())))+"'";			
		}
		if(paramFilterInspections.getFilter().getTypeId() != null){
			WHERE += " AND Inspections.typeId ="+paramFilterInspections.getFilter().getTypeId();
		}
		if(paramFilterInspections.getFilter().getStateId() != null){
			WHERE += " AND Inspections.stateId ="+paramFilterInspections.getFilter().getStateId();
		}
		if(paramFilterInspections.getFilter().getSize() != null && !paramFilterInspections.getFilter().getSize().equals("")){
			WHERE += " AND inspectionSize ="+paramFilterInspections.getFilter().getSize();
		}
		if(paramFilterInspections.getFilter().getMicrorredName() != null){
			WHERE += " AND A2.name LIKE '%"+paramFilterInspections.getFilter().getMicrorredName()+"%'";			
		}
		if(paramFilterInspections.getFilter().getTrapLatitude() != null){
			if(paramFilterInspections.getFilter().getTrapLatitude().compareTo(BigDecimal.ZERO)==0)
				WHERE += " AND Inspections.trapLatitude IS NULL ";
			else
				WHERE += " AND Inspections.trapLatitude IS NOT NULL ";
		}
		if(paramFilterInspections.getFilter().getReconversionScheduleId() != null){
			if(paramFilterInspections.getFilter().getReconversionScheduleId() == 0)
				WHERE += " AND S.reconversionScheduleId IS NULL ";
			else
				WHERE += " AND S.reconversionScheduleId IS NOT NULL ";
		}	
		
		String ORDER =" ORDER BY";
		if (paramFilterInspections.getSorting().getStartDate() != null){
			ORDER += " Inspections.startDate "+paramFilterInspections.getSorting().getStartDate();
		}else if (paramFilterInspections.getSorting().getFinishDate() != null){
			ORDER += " Inspections.finishDate "+paramFilterInspections.getSorting().getFinishDate();
		}else if(paramFilterInspections.getSorting().getAreaName() != null){
			ORDER += " A1.name "+paramFilterInspections.getSorting().getAreaName();			
		}else if(paramFilterInspections.getSorting().getTypeName() != null){
			ORDER += " L2.value "+paramFilterInspections.getSorting().getTypeName();	
		}else if(paramFilterInspections.getSorting().getStateName() != null){
			ORDER += " L1.value "+paramFilterInspections.getSorting().getStateName();	
		}else if(paramFilterInspections.getSorting().getSize() != null){
			ORDER += " inspectionSize "+paramFilterInspections.getSorting().getSize();	
		}else if(paramFilterInspections.getSorting().getMicrorredName() != null){
			ORDER += " A2.name "+paramFilterInspections.getSorting().getMicrorredName();	
		}else if(paramFilterInspections.getSorting().getTrapLatitude() != null){
			ORDER += " Inspections.trapLatitude "+paramFilterInspections.getSorting().getTrapLatitude();		
		}else if(paramFilterInspections.getSorting().getReconversionScheduleId() != null){
			ORDER += " S.reconversionScheduleId "+paramFilterInspections.getSorting().getReconversionScheduleId();		
		}else{
			ORDER += " Inspections.startDate "+paramFilterInspections.getSorting().getStartDate();
		}		
		
		Integer init = (paramFilterInspections.getPage() - 1) * paramFilterInspections.getCount();		
		
		String queryString = "SELECT DISTINCT Inspections.id,"
				+ " Inspections.startDate,"
				+ " Inspections.finishDate,"
				+ " inspectionSize,"
				+ " L2.value AS V2,"
				+ " L1.value AS V1,"
				+ " A1.id AS areaId,"
				+ " A1.name AS eessName,"
				+ " A2.name AS microrredName,"
				+ " S.reconversionScheduleId AS reconversion,"
				+ " Inspections.trapLatitude"
				+ " FROM Inspections INNER JOIN AreaDescendants"
				+ " ON Inspections.areaId = AreaDescendants.descendantId"
				+ " INNER JOIN Areas AS A1 ON A1.id = Inspections.areaId"
				+ " INNER JOIN Areas AS A2 ON A1.parentId = A2.id"
				+ " INNER JOIN TableElements AS T1 ON T1.id = Inspections.stateId"
				+ " INNER JOIN Labels AS L1 ON L1.tableElementId = T1.id"
				+ " INNER JOIN TableElements AS T2 ON T2.id = Inspections.typeId"
				+ " INNER JOIN Schedules AS S ON S.id = Inspections.scheduleId"
				+ " INNER JOIN Labels AS L2 ON L2.tableElementId = T2.id "+ WHERE + ORDER;

		Query q = this.getEntityManager().createNativeQuery(queryString);	
	    q.setFirstResult(init);
	    q.setMaxResults(paramFilterInspections.getCount());
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
	    for (Object[] result : results) {	
	    
	    	InspectionListImpl inspection = new InspectionListImpl((Integer)result[0],	
		    									((Date)result[1]).getTime(),
		    									((Date)result[2]).getTime(),
		    									(Integer)result[3],
		    									(String)result[4],
		    									(String)result[5],
		    									(Integer)result[6],
		    									(String)result[7],
		    									(String)result[8],
		    									(Integer)result[9],
		    									(BigDecimal)result[10]);				
		    inspectionList.add(inspection);
		}	    
		return inspectionList;    
	}

	@Override
	public List<InspectionSchedule> getListBySchedule(Schedules schedule)throws Exception {
		InspectionSchedule inspectionSchedule;		
		List<InspectionSchedule> inspectionScheduleList = new ArrayList<InspectionSchedule>();
		String sqlQuery = "SELECT INS.id,"
				+ " INS.startDate,"
				+ " INS.finishDate,"
				+ " INS.inspectionSize,"
				+ " INS.coverage,"
				+ " INS.tableElementsByTypeId.id,"
				+ " INS.tableElementsByStateId.id,"
				+ " INS.areas.id"
				+ " FROM Inspections AS INS WHERE INS.schedules = :schedule ";
	    Query q = this.getEntityManager().createQuery(sqlQuery);							
	    q.setParameter("schedule", schedule);       
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
	    
	    for (Object[] result : results) {		    
	    	inspectionSchedule = new InspectionSchedule((Integer)result[0],	
														((Date)result[1]).getTime(),
														((Date)result[2]).getTime(),
														(Integer)result[3],
														(BigDecimal)result[4],
														(Integer)result[5],
														(Integer)result[6],
														(Integer)result[7]);
	    	inspectionScheduleList.add(inspectionSchedule);
		}	
	    
		return inspectionScheduleList;    
	}
	
	@Override
	public Integer getCountList(Users user, ParamFilterInspections paramFilterInspections) throws Exception {
		

		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
		
		String WHERE =" WHERE AreaDescendants.areaId = "+user.getAreas().getId()+" AND L1.languageId = "+user.getLanguages().getId()+" AND L2.languageId = "+user.getLanguages().getId();
		
		if(paramFilterInspections.getFilter().getAreaName() != null){
			WHERE += " AND A1.name LIKE '%"+paramFilterInspections.getFilter().getAreaName()+"%'";			
		}
		if(paramFilterInspections.getFilter().getStartDate() != null){
			WHERE += " AND Inspections.startDate >= '"+(fr.format(new Date(paramFilterInspections.getFilter().getStartDate())))+"'";			
		}		
		if(paramFilterInspections.getFilter().getFinishDate() != null){
			WHERE += " AND Inspections.finishDate <= '"+(fr.format(new Date(paramFilterInspections.getFilter().getFinishDate())))+"'";			
		}
		if(paramFilterInspections.getFilter().getTypeId() != null){
			WHERE += " AND Inspections.typeId ="+paramFilterInspections.getFilter().getTypeId();
		}
		if(paramFilterInspections.getFilter().getStateId() != null){
			WHERE += " AND Inspections.stateId ="+paramFilterInspections.getFilter().getStateId();
		}
		if(paramFilterInspections.getFilter().getSize() != null && !paramFilterInspections.getFilter().getSize().equals("")){
			WHERE += " AND inspectionSize ="+paramFilterInspections.getFilter().getSize();
		}	
		if(paramFilterInspections.getFilter().getMicrorredName() != null){
			WHERE += " AND A2.name LIKE '%"+paramFilterInspections.getFilter().getMicrorredName()+"%'";			
		}	
		
		String queryString = "SELECT DISTINCT Inspections.id,"
				+ " Inspections.startDate,"
				+ " Inspections.finishDate,"
				+ " inspectionSize,"
				+ " L2.value AS V2,"
				+ " L1.value AS V1,"
				+ " A1.id AS areaId,"
				+ " A1.name AS eessName,"
				+ " A2.name AS microrredName"
				+ " FROM Inspections INNER JOIN AreaDescendants"
				+ " ON Inspections.areaId = AreaDescendants.descendantId"
				+ " INNER JOIN Areas AS A1 ON A1.id = Inspections.areaId"
				+ " INNER JOIN Areas AS A2 ON A1.parentId = A2.id"
				+ " INNER JOIN TableElements AS T1 ON T1.id = Inspections.stateId"
				+ " INNER JOIN Labels AS L1 ON L1.tableElementId = T1.id"
				+ " INNER JOIN TableElements AS T2 ON T2.id = Inspections.typeId"
				+ " INNER JOIN Labels AS L2 ON L2.tableElementId = T2.id "+ WHERE;
	
	    Query q = this.getEntityManager().createNativeQuery(queryString);			
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		
	    if(results != null){
	    	return results.size();
	    }else{
	    	return 0;
	    }	 
	}

	@Override
	public int getNumInspection(Inspections inspection, Date date, Users user) throws Exception {	
		
		String queryString = " SELECT SUM(inspected) " + 
				" FROM Plans INNER JOIN VisitSummaries ON VisitSummaries.planId = Plans.id" + 
				" INNER JOIN Users ON Users.id = Plans.userId" + 
				" WHERE inspectionId = "+inspection.getId()+" AND userId = "+user.getId()+" AND Plans.Date ='"+(new SimpleDateFormat("yyyy-MM-dd")).format(date)+"'";			
		
		Query q = this.getEntityManager().createNativeQuery(queryString);		
		BigDecimal totalInspections = (BigDecimal)q.getSingleResult();				    
		return totalInspections.intValue(); 
	}

	@Override
	public int getNumRequalify(Integer inspectionId, Integer areaId) throws Exception {
		

		String queryString = " SELECT IFNULL(COUNT(*),0)" + 
				" FROM Schedules AS S " + 
				" INNER JOIN Inspections AS I ON I.scheduleId = S.id" + 
				" INNER JOIN Schedules AS SR ON SR.id = S.reconversionScheduleId" + 
				" INNER JOIN Inspections AS IR ON IR.scheduleId = SR.id" + 
				" INNER JOIN Plans AS P ON P.inspectionId = IR.id" + 
				" INNER JOIN Visits AS V ON V.planId = P.id" + 
				" INNER JOIN Houses AS H ON H.uuid = V.houseUuid" + 
				" WHERE I.id = "+inspectionId+" AND H.areaId = "+areaId+" AND (V.resultId = 2002 OR V.resultId = 2003 OR V.resultId = 2004)";

		Query q = this.getEntityManager().createNativeQuery(queryString);		
		BigInteger numRequalify = (BigInteger)q.getSingleResult();				    
		return numRequalify.intValue();  
	}

	@Override
	public List<InspectionListImpl> getListReportInspections(ReportInspection reportInspection) throws Exception {
		List<InspectionListImpl> inspectionList = new ArrayList<InspectionListImpl>();

		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
		String selectType = "";
		String WHERE =" WHERE AD.areaId = " + reportInspection.getAreaId() + " AND "+ 
		" ((I.startDate BETWEEN '"+ (fr.format(new Date(reportInspection.getStartDate()))) +"' AND '"+ (fr.format(new Date(reportInspection.getFinishDate()))) +"') "+
		" OR (I.finishDate BETWEEN '"+ (fr.format(new Date(reportInspection.getStartDate()))) +"' AND '"+ (fr.format(new Date(reportInspection.getFinishDate()))) +"') "+
		" OR (I.startDate < '"+ (fr.format(new Date(reportInspection.getStartDate()))) +"' AND I.finishDate > '"+ (fr.format(new Date(reportInspection.getFinishDate()))) +"')) " ;
		
		if(reportInspection.getType() == 1) {
			//selectType = "IF(S.reconversionScheduleId IS NULL, L2.value, CONCAT(L2.value,' (Reconversión)')) AS V2 ,";				
			selectType = "L2.value AS V2,";	
			WHERE += " AND I.typeId = 1002 ";			
		} else if(reportInspection.getType() == 2) {
			selectType = "L2.value AS V2,";	
			WHERE += " AND I.typeId = 1002 AND S.reconversionScheduleId IS NULL ";			
		} else if(reportInspection.getType() == 3) {			
			//selectType = " CONCAT(L2.value,' (Reconversión)') AS V2,";
			selectType = "L2.value AS V2,";	
			WHERE += " AND I.typeId = 1002 AND S.reconversionScheduleId IS NOT NULL ";			
		} else if (reportInspection.getType() == 4) {
			selectType = "L2.value AS V2,";			
			WHERE += " AND I.typeId = 1001 ";	
		} else {
			selectType = "L2.value AS V2,";			
		}
		WHERE +=" AND I.stateId != 3001";
		String queryString = "SELECT DISTINCT I.id," + 
				" I.startDate," + 
				" I.finishDate," + 
				" inspectionSize," + 
				  selectType +
				" L1.value AS V1," + 
				" A1.id AS areaId," + 
				" A1.name AS eessName," + 
				" A2.name AS microrredName," +
				" S.reconversionScheduleId AS reconversion,"+
				" I.trapLatitude"+
				" FROM AreaDescendants  AD INNER JOIN PlansAreas PA ON PA.areaId = AD.descendantId " + 
				" INNER JOIN Plans P ON P.id = PA.planId" + 
				" INNER JOIN Inspections AS I ON I.id = P.inspectionId " + 
				" INNER JOIN Schedules AS S ON S.id = I.scheduleId" + 
				" INNER JOIN Areas AS A1 ON A1.id = I.areaId" + 
				" INNER JOIN Areas AS A2 ON A1.parentId = A2.id" + 
				" INNER JOIN TableElements AS T1 ON T1.id = I.stateId" + 
				" INNER JOIN Labels AS L1 ON L1.tableElementId = T1.id" + 
				" INNER JOIN TableElements AS T2 ON T2.id = I.typeId" +
				" INNER JOIN Labels AS L2 ON L2.tableElementId = T2.id "+ WHERE;			
			
		Query q = this.getEntityManager().createNativeQuery(queryString);
		
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
	    for (Object[] result : results) {	
	    
	    	InspectionListImpl inspection = new InspectionListImpl((Integer)result[0],	
		    									((Date)result[1]).getTime(),
		    									((Date)result[2]).getTime(),
		    									(Integer)result[3],
		    									(String)result[4],
		    									(String)result[5],
		    									(Integer)result[6],
		    									(String)result[7],
		    									(String)result[8],
		    									(Integer)result[9],
		    									(BigDecimal)result[10]);				
		    inspectionList.add(inspection);
		}	    
		return inspectionList;    
	}

	@Override
	public StatsMrResults getStatsMrResult(int type, int areaId, String inspections) throws Exception {
		String cond1 = "";
		String cond2 = "";

		if(type == 1) {
			cond2 = " IS NULL ";	
			cond1 = " IF(SUM(V.closed) IS NULL, 0, SUM(V.closed)) AS closed, IF(SUM(V.reluctant) IS NULL, 0, SUM(V.reluctant)) AS reluctant, IF(SUM(V.abandoned) IS NULL, 0, SUM(V.abandoned)) AS abandoned ";
		}else {
			cond2 = " IS NOT NULL ";
			cond1 = " IF(SUM(V.closedReconverted) IS NULL, 0, SUM(V.closedReconverted)) AS closed, IF(SUM(V.reluctantReconverted) IS NULL, 0, SUM(V.reluctantReconverted)) AS reluctant, IF(SUM(V.abandonedReconverted) IS NULL, 0, SUM(V.abandonedReconverted)) AS abandoned ";

		}
		
		String queryString = " SELECT programmed, inspected, closed, reluctant, abandoned, focus, containers FROM " + 
				" (SELECT S.areaId AS areaId1, IF(SUM(V.inspected) IS NULL, 0, SUM(V.inspected)) AS inspected, "+cond1 +", "+
				" IF(SUM(V.focus) IS NULL, 0, SUM(V.focus)) AS focus FROM Inspections I INNER JOIN Schedules S ON S.id = I.scheduleId " + 
				" INNER JOIN Plans P ON P.inspectionId = I.id " + 
				" INNER JOIN VisitSummaries V ON V.planId = P.id " + 
				" WHERE I.typeId = 1002 " + 
				" AND S.reconversionScheduleId" + cond2 + 
				" AND S.areaId = " + areaId + 
				" AND I.id IN ("+ inspections+")) AS A1 " + 
				" INNER JOIN " + 
				" (SELECT S.areaId AS areaId2, IF(SUM(focus) IS NULL, 0, SUM(focus)) AS containers FROM Inspections I INNER JOIN Schedules S ON S.id = I.scheduleId " + 
				" INNER JOIN Plans P ON P.inspectionId = I.id " + 
				" LEFT JOIN InventorySummaries IV ON IV.planId = P.id " + 
				" WHERE I.typeId = 1002 " + 
				" AND S.reconversionScheduleId " + cond2 +
				" AND S.areaId = " + areaId +
				" AND I.id IN("+ inspections+")) AS A2 ON A1.areaId1 = A2.areaId2 " + 
				" INNER JOIN " + 
				" (SELECT S.areaId AS areaId3, IF(SUM(planSize) IS NULL, 0, SUM(planSize)) AS programmed  FROM Inspections I INNER JOIN Schedules S ON S.id = I.scheduleId " + 
				" INNER JOIN Plans P ON P.inspectionId = I.id " + 
				" WHERE I.typeId = 1002 " + 
				" AND S.reconversionScheduleId " + cond2 +
				" AND S.areaId = " + areaId +
				" AND I.id IN("+ inspections+")) AS A3 " + 
				" ON A2.areaId2 = A3.areaId3 ";
		
		Query q = this.getEntityManager().createNativeQuery(queryString);		    
		
		StatsMrResults result = new StatsMrResults();
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
		
		if(results.size() == 0) {
			result = new StatsMrResults(0, 0, 0, 0, 0, 0, 0);
		}else {
			
			for (Object[] top : results) {	
			    
				result = new StatsMrResults(((BigDecimal)top[0]).intValue(), 
											((BigDecimal)top[1]).intValue(), 
											((BigDecimal)top[2]).intValue(),
											((BigDecimal)top[3]).intValue(),
											((BigDecimal)top[4]).intValue(),
											((BigDecimal)top[5]).intValue(),
											((BigDecimal)top[6]).intValue());

			}	  

		}

		return result;    
	}

	@Override
	public StatsEessResults getStatsEessResult(int type, boolean reconversion, int areaId, String inspections) throws Exception {

		String condReconversion = "";
		String condSelect = "";
		if(reconversion) {
			condReconversion = " IS NOT NULL ";		
			condSelect =  " IF(SUM(V.closedReconverted) IS NULL, 0, SUM(V.closedReconverted)) AS closed, " + 
				          " IF(SUM(V.reluctantReconverted) IS NULL, 0, SUM(V.reluctantReconverted)) AS reluctant, " + 
				          " IF(SUM(V.abandonedReconverted) IS NULL, 0, SUM(V.abandonedReconverted)) AS abandoned, "; 
		}else {
			condReconversion = " IS NULL ";
			condSelect =  " IF(SUM(V.closed) IS NULL, 0, SUM(V.closed)) AS closed, " + 
					      " IF(SUM(V.reluctant) IS NULL, 0, SUM(V.reluctant)) AS reluctant, " + 
					      " IF(SUM(V.abandoned) IS NULL, 0, SUM(V.abandoned)) AS abandoned, "; 
		}		
		
		List<StatsContainers> listContainers = new ArrayList<StatsContainers>();
		
		String queryString = "SELECT inspected, closed,reluctant,abandoned, focus,programmed,inspectedContainers,focusContainers FROM " + 
				" (SELECT  IF(I.areaId IS NULL, -1, I.areaId) AS areaId1,IF(SUM(V.inspected) IS NULL, 0, SUM(V.inspected)) AS inspected, " + condSelect +
				" IF(SUM(V.focus) IS NULL, 0, SUM(V.focus)) AS focus " + 
				" FROM Inspections I INNER JOIN Schedules S ON S.id = I.scheduleId  " + 
				" INNER JOIN Plans P ON P.inspectionId = I.id  " + 
				" INNER JOIN VisitSummaries V ON V.planId = P.id " + 
				" WHERE I.typeId = " + type + 
				" AND S.reconversionScheduleId " + condReconversion +
				" AND I.areaId = "+areaId+" AND I.id IN ("+ inspections+"))AS A1  " + 
				" INNER JOIN " + 
				" (SELECT IF(I.areaId IS NULL, -1, I.areaId) AS areaId2, IF(SUM(planSize) IS NULL, 0, SUM(planSize)) AS programmed " + 
				" FROM Inspections I INNER JOIN Schedules S ON S.id = I.scheduleId " + 
				" INNER JOIN Plans P ON P.inspectionId = I.id " + 
				" WHERE I.typeId = " + type +
				" AND S.reconversionScheduleId " + condReconversion + 
				" AND I.areaId = " + areaId +
				" AND I.id IN ("+ inspections+")) AS A2 " + 
				" ON A1.areaId1 = A2. AREAId2 " + 
				" INNER JOIN " + 
				" (SELECT IF(I.areaId IS NULL, -1, I.areaId) AS areaId3, IF(SUM(focus) IS NULL, 0, SUM(focus)) AS focusContainers, " + 
				" IF(SUM(inspected) IS NULL, 0, SUM(inspected)) AS inspectedContainers " + 
				" FROM Inspections I INNER JOIN Schedules S ON S.id = I.scheduleId " + 
				" INNER JOIN Plans P ON P.inspectionId = I.id " + 
				" LEFT JOIN InventorySummaries IV ON IV.planId = P.id " + 
				" WHERE I.typeId = " + type +
				" AND S.reconversionScheduleId" + condReconversion + 
				" AND I.areaId= " + areaId + 
				" AND I.id IN ("+ inspections+")) AS A3 " + 
				" ON A3.areaId3= A2.areaId2" ;

		Query q = this.getEntityManager().createNativeQuery(queryString);		    
		
		StatsEessResults result = new StatsEessResults();
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
		
	    for (Object[] top : results) {	

			result = new StatsEessResults(((BigDecimal)top[0]).intValue(), 
										((BigDecimal)top[1]).intValue(), 
										((BigDecimal)top[2]).intValue(),
										((BigDecimal)top[3]).intValue(),
										((BigDecimal)top[4]).intValue(),
										((BigDecimal)top[5]).intValue(),
										((BigDecimal)top[6]).intValue(),
										((BigDecimal)top[7]).intValue(),
										listContainers);
		}	  

		return result;   
	}

	@Override
	public List<StatsContainers> getListStatContainers(int type, boolean reconversion, int areaId, String inspections) throws Exception {

		String condReconversion = "";
		if(reconversion) {
			condReconversion = " IS NOT NULL ";			
		}else {
			condReconversion = " IS NULL ";
		}		
		List<StatsContainers> listContainers = new ArrayList<StatsContainers>();
		
		String queryString = " SELECT SUM(focus) AS focus , L.value "+
							" FROM Inspections I INNER JOIN Schedules S ON S.id = I.scheduleId " + 
							" INNER JOIN Plans P ON P.inspectionId = I.id " + 
							" LEFT JOIN InventorySummaries IV ON IV.planId = P.id  " + 
							" LEFT JOIN Labels AS L ON L.tableElementId = IV.containerId " + 
							" WHERE I.typeId = " + type + 
							" AND S.reconversionScheduleId" + condReconversion + 
							" AND I.areaId = " + areaId + 
							" AND I.id IN ("+ inspections+")" + 
							" AND focus > 0 " + 
							" GROUP BY containerId ";			
		
		Query q = this.getEntityManager().createNativeQuery(queryString);		    
		
		StatsContainers result = new StatsContainers();
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
		
	    for (Object[] top : results) {	
			result = new StatsContainers((String)top[1], ((BigDecimal)top[0]).intValue());
			listContainers.add(result);			
		}
		return listContainers;   
	}

	@Override
	public List<FocusHouse> getListFocusHouses(String inspections) throws Exception {

		List<FocusHouse> listFocus = new ArrayList<FocusHouse>();
		
		String queryString = "  SELECT H.uuid, H.latitude, H.longitude, V.requalify FROM Inventories AS I INNER JOIN Visits AS V ON I.visituuid = V.uuid " + 
						" INNER JOIN Houses AS H ON H.uuid = V.houseUuid " + 
						" INNER JOIN Plans AS P ON P.id = V.planId " + 
						" INNER JOIN Inspections AS INS ON INS.id = P.inspectionId " + 
						" WHERE focus > 0 " + 
						" AND INS.id IN ("+ inspections+")" ;
								
		Query q = this.getEntityManager().createNativeQuery(queryString);		    
		
		FocusHouse result = new FocusHouse();
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
		
	    for (Object[] item : results) {	
			result = new FocusHouse((item[0]).toString(), (BigDecimal)item[1] == null ? BigDecimal.ZERO: (BigDecimal)item[1], (BigDecimal)item[2] == null ? BigDecimal.ZERO: (BigDecimal)item[2], (Boolean)item[3]);
			listFocus.add(result);			
		}
		return listFocus;   

	}

	@Override
	public Inspection getInspectionbyId(int inspectionId) throws Exception {
			
		
		String queryString = "SELECT i.startDate," + 
				"i.finishDate," + 
				"i.inspectionSize," + 
				"i.coverage," + 
				"i.typeId," + 
				"i.stateId," + 
				"i.areaId," + 
				"i.scheduleId," + 
				"i.larvicideId," + 
				"i.trapLatitude," + 
				"i.trapLongitude," + 
				"Traps.code," + 
				"WEEK(i.trapDate) + 1," +
				"TrapData.eggs FROM Inspections AS i LEFT JOIN Traps ON i.trapId = Traps.id LEFT JOIN TrapData ON (TrapData.trapId = Traps.id AND TrapData.date = i.trapDate) WHERE i.id = "+ inspectionId; 
				

		
		Query q = this.getEntityManager().createNativeQuery(queryString);		
		Object[] queryResult = (Object[])q.getSingleResult();	
		Inspection result = null;
		
		

		 if  (queryResult != null) {
			 try {
			  result = new InspectionTrapImpl(
					 ((Date)queryResult[0]).getTime(),
					 ((Date)queryResult[1]).getTime(),
					 (Integer)queryResult[2],
					 (BigDecimal)queryResult[3],
					 (Integer)queryResult[4],
					 (Integer)queryResult[5],
					 (Integer)queryResult[6],
					 (Integer)queryResult[7],
					 (Integer)queryResult[8],
					 (BigDecimal)queryResult[9],
					 (BigDecimal)queryResult[10],
					 (String)queryResult[11],
					 (queryResult[12] != null) ? ((BigInteger)queryResult[12]).intValue() : null,
					 (Integer)queryResult[13]
					);	 
			 }
			 catch(NullPointerException e){ e.printStackTrace();}

		 	}

		return result;
	}
	
	@Override
	public BigDecimal getReconversionHouses(Integer scheduleId,Integer areaId) throws Exception {
		BigDecimal result=BigDecimal.ZERO;
		
		//inspeciones
		String queryString = "SELECT id FROM Plans WHERE inspectionId=(SELECT DISTINCT I.id FROM Schedules AS S " + 
				" INNER JOIN Inspections AS I ON I.scheduleId=S.reconversionScheduleId " + 
				" INNER JOIN Areas AS A ON A.parentId=I.areaId " + 
				" WHERE S.id = "+scheduleId+" AND A.parentId=(SELECT parentId FROM Areas WHERE id="+areaId+"))";
		
		Query q = this.getEntityManager().createNativeQuery(queryString);		
		List<Integer> inspectionList = JpaResultHelper.getResultListAndCast(q);

		for (Integer item : inspectionList) {	
			//sum VisitSummary
			String queryString2 = "SELECT IFNULL((SUM(VS.closed)+SUM(VS.abandoned)+SUM(VS.reluctant)),0) "
					+ " FROM VisitSummaries AS VS "
					+ " INNER JOIN Areas AS A ON A.id=VS.areaId "
					+ " WHERE VS.planId="+item+" AND A.parentId="+areaId;
			Query q2 = this.getEntityManager().createNativeQuery(queryString2);		
			BigDecimal numRequalify = (BigDecimal)q2.getSingleResult();
			result=result.add(numRequalify);
			//System.out.println("Reconversion --> A:"+areaId+" S:"+scheduleId+" I:"+item+" R*:"+result+"");
		}
		
		//System.out.println(queryString);
		return result;  
	}
	@Override
	public BigDecimal getControlHouses(Integer scheduleId,Integer areaId) throws Exception {	
		BigDecimal numRequalify=BigDecimal.ZERO;
		//inspeciones
		String queryString = "SELECT DISTINCT I.id FROM Inspections AS I " +
				" WHERE I.scheduleId="+scheduleId+" AND I.areaId=(SELECT parentId FROM Areas WHERE id="+areaId+")";
		
		Query q = this.getEntityManager().createNativeQuery(queryString);		
		List<Integer> inspectionList = JpaResultHelper.getResultListAndCast(q);

		for (Integer item : inspectionList) {	
			//sum VisitSummary
			String queryString2 = " SELECT IFNULL((SUM(scheduledHouses)),0) FROM PlansAreas AS PA " + 
					" INNER JOIN Areas AS A ON A.id=PA.areaId " + 
					" WHERE planId=(SELECT id FROM Plans WHERE inspectionId="+item+") AND A.parentId="+areaId;
			Query q2 = this.getEntityManager().createNativeQuery(queryString2);		
			numRequalify = (BigDecimal)q2.getSingleResult();
			
			//System.out.println("Control --> A:"+areaId+" S:"+scheduleId+" I:"+item+" R*:"+numRequalify+"");
		}
		
		//System.out.println(queryString);
		return numRequalify;  
	}
	
	
}
