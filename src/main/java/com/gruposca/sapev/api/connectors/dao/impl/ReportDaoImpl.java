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
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Reports;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.ParamFilterReports;
import com.gruposca.sapev.api.modelview.ReportArea;
import com.gruposca.sapev.api.modelview.ReportExcel;
import com.gruposca.sapev.api.modelview.ReportList;
import com.gruposca.sapev.api.modelview.Sample;
import com.gruposca.sapev.api.modelview.SampleListImpl;

@Repository("ReportDaoImpl")
public class ReportDaoImpl extends BaseDaoHibernate<Reports, String> implements ReportDao{
	public ReportDaoImpl() { super(Reports.class); }

	@Override
	public List<Reports> getReportsAreaList(Areas area) throws Exception {
		
		String sqlQuery = "select RE from Reports as RE where RE.areas = :area ";
		List<Reports> listReports = new ArrayList<Reports>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Reports.class);
        q.setParameter("area", area);  
        listReports = JpaResultHelper.getResultListAndCast(q);       
		return listReports;
	}
	
	
	@Override
	public Integer getWeek(Integer reportId) throws Exception {
		String sqlQuery = "SELECT WEEK(R.date,6) AS week FROM Reports AS R WHERE R.id = :reportId";
		Query q = this.getEntityManager().createQuery(sqlQuery, Integer.class);
        q.setParameter("reportId", reportId);  
        Integer week = (Integer) JpaResultHelper.getSingleResultOrNull(q);       
	    return week;	   	
	}

	@Override
	public String getLarvicides(Integer reportId) throws Exception {
		
		String sqlQuery = "SELECT DISTINCT L.name FROM ReportInspections AS R INNER JOIN Inspections AS I ON I.id =R.inspectionId INNER JOIN Larvicides AS L ON L.id=I.larvicideId	WHERE R.reportId = "+ reportId;		
		System.out.println(sqlQuery);
		String larvicideNames = "";
        
        Query q = this.getEntityManager().createNativeQuery(sqlQuery);	
       
		List<String> results = JpaResultHelper.getResultListAndCast(q);	 
		for (int i = 0; i < results.size(); i++) {	
			
			larvicideNames+=(String)results.get(i)+",";	
		}
	    return larvicideNames.substring(0,larvicideNames.length()-1);	   	
	}

	@Override
	public ReportList getListByAreaUser(Users user, ParamFilterReports paramFilterReports) throws Exception {		
		List<ReportArea> reportAreasList = new ArrayList<ReportArea>();
		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
		
		String WHERE =" WHERE AreaDescendants.areaId = "+user.getAreas().getId();
		
		if(paramFilterReports.getFilter().getDate() != null){
			WHERE += " AND Reports.date = '"+(fr.format(new Date(paramFilterReports.getFilter().getDate())))+"'";			
		}	
		
		if(paramFilterReports.getFilter().getAreaId() != null){
			WHERE += " AND Reports.areaId = "+ paramFilterReports.getFilter().getAreaId();			
		}	
		if(paramFilterReports.getFilter().getName() != null){
			WHERE += " AND Reports.name LIKE '%"+paramFilterReports.getFilter().getName()+"%'";				
		}	
		if(paramFilterReports.getFilter().getStartDate() != null){
			WHERE += " AND Reports.startDate = '"+(fr.format(new Date(paramFilterReports.getFilter().getStartDate())))+"'";			
		}
		if(paramFilterReports.getFilter().getFinishDate() != null){
			WHERE += " AND Reports.finishDate = '"+(fr.format(new Date(paramFilterReports.getFilter().getFinishDate())))+"'";			
		}
		if(paramFilterReports.getFilter().getDataType()!= null){
			WHERE += " AND Reports.dataType = "+ paramFilterReports.getFilter().getDataType();			
		}	
		if(paramFilterReports.getFilter().getDetailLevel() != null){
			WHERE += " AND Reports.detailLevel = "+ paramFilterReports.getFilter().getDetailLevel();			
		}	
		if(paramFilterReports.getFilter().getCreateUserId() != null){
			WHERE += " AND Reports.createUserId = "+ paramFilterReports.getFilter().getCreateUserId();			
		}	
		
		String ORDER =" ORDER BY";
		if (paramFilterReports.getSorting().getDate() != null){
			ORDER += " Reports.date "+paramFilterReports.getSorting().getDate();
		}else if(paramFilterReports.getSorting().getName() != null){
			ORDER += " Reports.name "+paramFilterReports.getSorting().getName();			
		}else if(paramFilterReports.getSorting().getCreateUserId() != null){
			ORDER += " Users.name "+paramFilterReports.getSorting().getCreateUserId();
		}else if(paramFilterReports.getSorting().getAreaName() != null){
			ORDER += " Areas.name "+paramFilterReports.getSorting().getAreaName();	
		}else if(paramFilterReports.getSorting().getStartDate() != null){
			ORDER += " Reports.startDate "+paramFilterReports.getSorting().getStartDate();	
		}else if(paramFilterReports.getSorting().getFinishDate() != null){
			ORDER += " Reports.finishDate "+paramFilterReports.getSorting().getFinishDate();	
		}else if(paramFilterReports.getSorting().getDataType() != null){
			ORDER += " Reports.dataType "+paramFilterReports.getSorting().getDataType();	
		}else if(paramFilterReports.getSorting().getDetailLevel() != null){
			ORDER += " Reports.detailLevel "+paramFilterReports.getSorting().getDetailLevel();	
		}else{
			ORDER += " Reports.date "+paramFilterReports.getSorting().getDate();
		}		
			
		Integer init = (paramFilterReports.getPage() - 1) * paramFilterReports.getCount();		
	
		String queryString = "SELECT DISTINCT Reports.* FROM Reports INNER JOIN AreaDescendants  ON  Reports.areaId = AreaDescendants.descendantId "
								+ "INNER JOIN Areas ON Areas.id = Reports.areaId "  
								+ " INNER JOIN Users ON Users.id = Reports.createUserId"								
								+ WHERE +ORDER ;

	    Query q = this.getEntityManager().createNativeQuery(queryString);	
	    q.setFirstResult(init);
	    q.setMaxResults(paramFilterReports.getCount());
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
	    
	    for (Object[] result : results) {	
	    	ReportArea report = new ReportArea((Integer)result[0],
	    						((Date)result[1]).getTime(),
	    						(Integer)result[2],
	    						(String)result[3],
	    						((Date)result[4]).getTime(),
	    						((Date)result[5]).getTime(),
	    						(Integer)result[6],
	    						(Integer)result[7],
	    						(Integer)result[8]);	
	    	
	    	reportAreasList.add(report);
		}	
	    
	    
	    
	    String queryStringCount = "SELECT IFNULL(COUNT(DISTINCT Reports.id),0) " + 
	    		" FROM Reports INNER JOIN AreaDescendants  ON  Reports.areaId = AreaDescendants.descendantId " +
				" INNER JOIN Areas ON Areas.id = Reports.areaId " + 
				" INNER JOIN Users ON Users.id = Reports.createUserId"+ WHERE;		

		
		q = this.getEntityManager().createNativeQuery(queryStringCount);		
		BigInteger totalReports = (BigInteger)q.getSingleResult();		
	    ReportList reportList = new ReportList(totalReports.intValue(), reportAreasList);
	    
		return reportList;    
	}
	
	@Override
	public List<ReportExcel> getListExcel(Integer reportId, Integer detailLevel) throws Exception {
		ReportExcel reportExcel;		
		List<ReportExcel> list = new ArrayList<ReportExcel>();
		String level = "";
		String name = "";
		String order = "";

		if (detailLevel == 9007) {				
			level = "A1.id";	
			name = "A1.name AS `name`, A2.name AS name2, A3.name AS name3, A4.name AS name4, A5.name AS name5, A6.name AS name6,";
			order = " name6, name5, name4, name3, name2, `name` ";
		}else if(detailLevel == 9006) {
			level = "A2.id";	
			name = "A2.name AS `name`, A3.name AS name3, A4.name AS name4, A5.name AS name5, A6.name AS name6,";
			order = " name6, name5, name4, name3, `name` ";
		}else if(detailLevel == 9005) {					
			level = "A3.id";	
			name = "A3.name AS `name`, A4.name AS name4, A5.name AS name5, A6.name AS name6,";
			order = " name6, name5, name4, `name` ";
		}else if(detailLevel == 9004) {					
			level = "A4.id";			
			name = "A4.name AS `name`, A5.name AS name5, A6.name AS name6,";
			order = " name6, name5, `name` ";
		}else if(detailLevel == 9003) {					
			level = "A5.id";			
			name = "A5.name AS `name`, A6.name AS name6,";
			order = " name6, `name` ";
		}else if(detailLevel == 9002) {					
			level = "A6.id";			
			name = "A6.name AS `name`,";
			order = " `name` ";

		}

		String queryString = " SELECT ID1," + 
							" `date`, `name`, focus, treated, destroyed, houses, inspected, closed, renuente, abandonada, larvicide, people, numMz,scheduledHouses, IFNULL(`all.inspected`,0),IFNULL(`all.focus`,0), IFNULL(`all.treated`,0), " + 
							" IFNULL(`4001.inspected`,0), IFNULL(`4001.focus`,0), IFNULL(`4001.treated`,0), IFNULL(`4001.destroyed`,0), IFNULL(`4002.inspected`,0), IFNULL(`4002.focus`,0), IFNULL(`4002.treated`,0), IFNULL(`4002.destroyed`,0), " + 
							" IFNULL(`4003.inspected`,0), IFNULL(`4003.focus`,0), IFNULL(`4003.treated`,0), IFNULL(`4003.destroyed`,0), IFNULL(`4004.inspected`,0), IFNULL(`4004.focus`,0), IFNULL(`4004.treated`,0), IFNULL(`4004.destroyed`,0), " + 
							" IFNULL(`4005.inspected`,0), IFNULL(`4005.focus`,0), IFNULL(`4005.treated`,0), IFNULL(`4005.destroyed`,0), IFNULL(`4006.inspected`,0), IFNULL(`4006.focus`,0), IFNULL(`4006.treated`,0), IFNULL(`4006.destroyed`,0), " + 
							" IFNULL(`4007.inspected`,0), IFNULL(`4007.focus`,0), IFNULL(`4007.treated`,0), IFNULL(`4007.destroyed`,0), IFNULL(`4008.inspected`,0), IFNULL(`4008.focus`,0), IFNULL(`4008.treated`,0), IFNULL(`4008.destroyed`,0), " + 
							" IFNULL(`4009.inspected`,0), IFNULL(`4009.focus`,0), IFNULL(`4009.treated`,0), IFNULL(`4009.destroyed`,0), IFNULL(`4010.inspected`,0), IFNULL(`4010.focus`,0), IFNULL(`4010.treated`,0), IFNULL(`4010.destroyed`,0), " + 
							" minDate, maxDate "+
							" FROM "+
							" (SELECT "+level+" AS ID1," + 
							" IF(MIN(P.date) = MAX(P.date),DATE_FORMAT(MAX(P.date),'%d/%m/%y') ,CONCAT (DATE_FORMAT(MIN(P.date),'%d/%m/%y') ,' - ',DATE_FORMAT(MAX(P.date),'%d/%m/%y'))) AS `date`, " + 
							" MIN(P.date) AS minDate, MAX(P.date) AS maxDate ," +
							  name + 
							" SUM(focus) AS focus," + 
							" SUM(treated) AS treated," + 
							" SUM(destroyed) AS destroyed," + 
							" SUM(VS.houses) AS houses," + 
							" SUM(inspected) AS inspected," + 
							" SUM(closed) AS closed," + 
							" SUM(reluctant) AS renuente," + 
							" SUM(abandoned) AS abandonada," + 
							" SUM(larvicide) AS larvicide," + 
							" SUM(people) AS people," + 
							" COUNT(DISTINCT A1.id) AS numMz," + 
							" SUM(PA.scheduledHouses) AS scheduledHouses" + 
							" FROM Reports R" + 
							" INNER JOIN ReportInspections RI ON R.id = RI.reportId" + 
							" INNER JOIN Inspections I ON I.id = RI.inspectionId" + 
							" INNER JOIN Schedules S ON I.scheduleId = S.id" + 
							" INNER JOIN Plans P ON (P.inspectionId = I.id AND P.date >= R.startDate AND P.date <= R.finishDate) " + 
							" INNER JOIN VisitSummaries VS ON VS.planId = P.id " + 
							" INNER JOIN PlansAreas PA ON (PA.planId = P.id AND VS.areaId = PA.areaId)" + 
							" INNER JOIN Areas AS A1 ON A1.id = VS.areaId" + 
							" INNER JOIN Areas AS A2 ON A2.id = A1.parentId" + 
							" INNER JOIN Areas AS A3 ON A3.id = A2.parentId" + 
							" INNER JOIN Areas AS A4 ON A4.id = A3.parentId" + 
							" INNER JOIN Areas AS A5 ON A5.id = A4.parentId" + 
							" INNER JOIN Areas AS A6 ON A6.id = A5.parentId" + 
							" WHERE R.id = "+reportId+" GROUP BY "+level+") AS Q1" + 
							" LEFT JOIN " + 
							" (SELECT "+level+" AS ID2," + 
							" SUM(INV.inspected) AS `all.inspected`," + 
							" SUM(INV.focus) AS `all.focus`," + 
							" SUM((INV.treated + INV.destroyed)) AS `all.treated`," + 
							" SUM(IF((INV.containerId = 4001),INV.inspected,0)) AS `4001.inspected`," + 
							" SUM(IF((INV.containerId = 4001),INV.focus,0)) AS `4001.focus`," + 
							" SUM(IF((INV.containerId = 4001),INV.treated,0)) AS `4001.treated`," + 
							" SUM(IF((INV.containerId = 4001),INV.destroyed,0)) AS `4001.destroyed`," + 
							" SUM(IF((INV.containerId = 4002),INV.inspected,0)) AS `4002.inspected`," + 
							" SUM(IF((INV.containerId = 4002),INV.focus,0)) AS `4002.focus`," + 
							" SUM(IF((INV.containerId = 4002),INV.treated,0)) AS `4002.treated`," + 
							" SUM(IF((INV.containerId = 4002),INV.destroyed,0)) AS `4002.destroyed`," + 
							" SUM(IF((INV.containerId = 4003),INV.inspected,0)) AS `4003.inspected`," + 
							" SUM(IF((INV.containerId = 4003),INV.focus,0)) AS `4003.focus`," + 
							" SUM(IF((INV.containerId = 4003),INV.treated,0)) AS `4003.treated`," + 
							" SUM(IF((INV.containerId = 4003),INV.destroyed,0)) AS `4003.destroyed`," + 
							" SUM(IF((INV.containerId = 4004),INV.inspected,0)) AS `4004.inspected`," + 
							" SUM(IF((INV.containerId = 4004),INV.focus,0)) AS `4004.focus`," + 
							" SUM(IF((INV.containerId = 4004),INV.treated,0)) AS `4004.treated`," + 
							" SUM(IF((INV.containerId = 4004),INV.destroyed,0)) AS `4004.destroyed`," + 
							" SUM(IF((INV.containerId = 4005),INV.inspected,0)) AS `4005.inspected`," + 
							" SUM(IF((INV.containerId = 4005),INV.focus,0)) AS `4005.focus`," + 
							" SUM(IF((INV.containerId = 4005),INV.treated,0)) AS `4005.treated`," + 
							" SUM(IF((INV.containerId = 4005),INV.destroyed,0)) AS `4005.destroyed`," + 
							" SUM(IF((INV.containerId = 4006),INV.inspected,0)) AS `4006.inspected`," + 
							" SUM(IF((INV.containerId = 4006),INV.focus,0)) AS `4006.focus`," + 
							" SUM(IF((INV.containerId = 4006),INV.treated,0)) AS `4006.treated`," + 
							" SUM(IF((INV.containerId = 4006),INV.destroyed,0)) AS `4006.destroyed`," + 
							" SUM(IF((INV.containerId = 4007),INV.inspected,0)) AS `4007.inspected`," + 
							" SUM(IF((INV.containerId = 4007),INV.focus,0)) AS `4007.focus`," + 
							" SUM(IF((INV.containerId = 4007),INV.treated,0)) AS `4007.treated`," + 
							" SUM(IF((INV.containerId = 4007),INV.destroyed,0)) AS `4007.destroyed`," + 
							" SUM(IF((INV.containerId = 4008),INV.inspected,0)) AS `4008.inspected`," + 
							" SUM(IF((INV.containerId = 4008),INV.focus,0)) AS `4008.focus`," + 
							" SUM(IF((INV.containerId = 4008),INV.treated,0)) AS `4008.treated`," + 
							" SUM(IF((INV.containerId = 4008),INV.destroyed,0)) AS `4008.destroyed`," + 
							" SUM(IF((INV.containerId = 4009),INV.inspected,0)) AS `4009.inspected`," + 
							" SUM(IF((INV.containerId = 4009),INV.focus,0)) AS `4009.focus`," + 
							" SUM(IF((INV.containerId = 4009),INV.treated,0)) AS `4009.treated`," + 
							" SUM(IF((INV.containerId = 4009),INV.destroyed,0)) AS `4009.destroyed`," + 
							" SUM(IF((INV.containerId = 4010),INV.inspected,0)) AS `4010.inspected`," + 
							" SUM(IF((INV.containerId = 4010),INV.focus,0)) AS `4010.focus`," + 
							" SUM(IF((INV.containerId = 4010),INV.treated,0)) AS `4010.treated`," + 
							" SUM(IF((INV.containerId = 4010),INV.destroyed,0)) AS `4010.destroyed` " + 
							" FROM Reports R" + 
							" INNER JOIN ReportInspections RI ON R.id = RI.reportId" + 
							" INNER JOIN Inspections I ON I.id = RI.inspectionId" + 
							" INNER JOIN Schedules S ON I.scheduleId = S.id" + 
							" INNER JOIN Plans P ON (P.inspectionId = I.id AND P.date >= R.startDate AND P.date <= R.finishDate) " + 
							" INNER JOIN InventorySummaries INV ON INV.planId = P.id " + 
							" INNER JOIN Areas AS A1 ON A1.id = INV.areaId" + 
							" INNER JOIN Areas AS A2 ON A2.id = A1.parentId" + 
							" INNER JOIN Areas AS A3 ON A3.id = A2.parentId" + 
							" INNER JOIN Areas AS A4 ON A4.id = A3.parentId" + 
							" INNER JOIN Areas AS A5 ON A5.id = A4.parentId" + 
							" INNER JOIN Areas AS A6 ON A6.id = A5.parentId" + 
							" WHERE R.id = "+reportId+" GROUP BY "+level+") AS Q2" + 
							" ON (Q1.ID1 = Q2.ID2) " + 
							" ORDER BY "+ order;		
		
		System.out.println(queryString);
		System.out.println("R.id = "+reportId+" GROUP BY = "+level+" ORDER BY = "+ order+" Name = "+name);
	    Query q = this.getEntityManager().createNativeQuery(queryString);	
	    
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
	    
	    for (Object[] result : results) {	
    	
	    	reportExcel = new ReportExcel((Integer)result[0], (String)result[1], (String)result[2], (BigDecimal)result[3], (BigDecimal)result[4], (BigDecimal)result[5], (BigDecimal)result[6], (BigDecimal)result[7], (BigDecimal)result[8],
	    			(BigDecimal)result[9], (BigDecimal)result[10], (BigDecimal)result[11], (BigDecimal)result[12], (BigInteger)result[13], (BigDecimal)result[14], (BigDecimal)result[15],(BigDecimal)result[16],(BigDecimal)result[17],
	    			(BigDecimal)result[18], (BigDecimal)result[19],(BigDecimal)result[20],(BigDecimal)result[21],(BigDecimal)result[22],(BigDecimal)result[23],(BigDecimal)result[24],(BigDecimal)result[25],(BigDecimal)result[26],(BigDecimal)result[27],
	    			(BigDecimal)result[28],(BigDecimal)result[29],(BigDecimal)result[30],(BigDecimal)result[31],(BigDecimal)result[32],(BigDecimal)result[33],(BigDecimal)result[34],(BigDecimal)result[35],
	    			(BigDecimal)result[36],(BigDecimal)result[37], (BigDecimal)result[38], (BigDecimal)result[39], (BigDecimal)result[40], (BigDecimal)result[41], (BigDecimal)result[42], (BigDecimal)result[43],
	    			(BigDecimal)result[44],(BigDecimal)result[45],(BigDecimal)result[46],(BigDecimal)result[47],(BigDecimal)result[48],(BigDecimal)result[49],(BigDecimal)result[50],(BigDecimal)result[51],
	    			(BigDecimal)result[52],(BigDecimal)result[53],(BigDecimal)result[54],(BigDecimal)result[55],(BigDecimal)result[56],(BigDecimal)result[57],(Date)result[58],(Date)result[59]);

	    	list.add(reportExcel);
		}	
	    
		return list;   
	}	
	
}
