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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Schedules;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.helper.CalendarHelper;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.ParamFilterSchedule;
import com.gruposca.sapev.api.modelview.ScheduleArea;
import com.gruposca.sapev.api.modelview.ScheduleDay;
import com.gruposca.sapev.api.modelview.ScheduleListImpl;

@Repository("ScheduleDaoImpl")
public class ScheduleDaoImpl extends BaseDaoHibernate<Schedules, String> implements ScheduleDao{

	public ScheduleDaoImpl() { super(Schedules.class); }

	@Override
	public List<ScheduleListImpl> getList(Users user,ParamFilterSchedule paramFilterSchedule) throws Exception {
		
		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
		
		String WHERE =" WHERE AD.areaId = "+ user.getAreas().getId()+" AND LA.languageId = "+ user.getLanguages().getId();
		
		if(paramFilterSchedule.getFilter().getAreaName() != null){
			WHERE += " AND A.name LIKE '%"+paramFilterSchedule.getFilter().getAreaName()+"%'";			
		}
		if(paramFilterSchedule.getFilter().getStartDate() != null){
			WHERE += " AND S.startDate >= '"+(fr.format(new Date(paramFilterSchedule.getFilter().getStartDate())))+"'";			
		}		
		if(paramFilterSchedule.getFilter().getFinishDate() != null){
			WHERE += " AND S.finishDate <= '"+(fr.format(new Date(paramFilterSchedule.getFilter().getFinishDate())))+"'";			
		}
		if(paramFilterSchedule.getFilter().getTypeId() != null){
			WHERE += " AND TE.id ="+paramFilterSchedule.getFilter().getTypeId();
		}
		if(paramFilterSchedule.getFilter().getReconversionScheduleId() != null){
			if(paramFilterSchedule.getFilter().getReconversionScheduleId() == 0)
				WHERE += " AND S.reconversionScheduleId IS NULL ";
			else
				WHERE += " AND S.reconversionScheduleId IS NOT NULL ";
		}
		if(paramFilterSchedule.getFilter().getTrapLatitude() != null){
			if(paramFilterSchedule.getFilter().getTrapLatitude().compareTo(BigDecimal.ZERO)==0)
				WHERE += " AND I.trapLatitude IS NULL ";
			else
				WHERE += " AND I.trapLatitude IS NOT NULL ";
		}
		
		String ORDER =" ORDER BY";
		if (paramFilterSchedule.getSorting().getStartDate() != null){
			ORDER += " S.startDate "+paramFilterSchedule.getSorting().getStartDate();
		}else if (paramFilterSchedule.getSorting().getFinishDate() != null){
			ORDER += " S.finishDate "+paramFilterSchedule.getSorting().getFinishDate();
		}else if(paramFilterSchedule.getSorting().getAreaName() != null){
			ORDER += " A.name "+paramFilterSchedule.getSorting().getAreaName();			
		}else if(paramFilterSchedule.getSorting().getTypeName() != null){
			ORDER += " LA.value "+paramFilterSchedule.getSorting().getTypeName();	
		}else if(paramFilterSchedule.getSorting().getReconversionScheduleId() != null){
			ORDER += " S.reconversionScheduleId "+paramFilterSchedule.getSorting().getReconversionScheduleId();		
		}else if(paramFilterSchedule.getSorting().getTrapLatitude() != null){
			ORDER += " I.trapLatitude "+paramFilterSchedule.getSorting().getTrapLatitude();		
		}else{
			ORDER += " S.startDate "+paramFilterSchedule.getSorting().getStartDate();
		}		

		
		Integer init = (paramFilterSchedule.getPage() - 1) * paramFilterSchedule.getCount();		
		
		List<ScheduleListImpl> scheduleList = new ArrayList<ScheduleListImpl>();
		String sql = "SELECT S.id, S.startDate, S.finishDate, LA.value, A.id AS areaId, A.name, COUNT(DISTINCT I.id), S.reconversionScheduleId, L.id AS larvicideId, L.name AS larvicideName,I.trapLatitude"
				+ " FROM AreaDescendants AS AD INNER JOIN Areas AS A ON AD.descendantId = A.id"
				+ " INNER JOIN Schedules AS S ON S.areaId = A.id"
				+ " INNER JOIN TableElements AS TE ON TE.id = S.typeId"
				+ " INNER JOIN Labels AS LA ON LA.tableElementId = TE.id"
				+ " INNER JOIN Larvicides AS L ON L.id = S.larvicideId"
				+ " LEFT JOIN Inspections AS I ON I.scheduleId = S.id "+ WHERE +" GROUP BY S.id "+ ORDER ;
	
	    Query q = this.getEntityManager().createNativeQuery(sql);	
	    q.setFirstResult(init);
	    q.setMaxResults(paramFilterSchedule.getCount());
		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		for (Object[] result : results) {	
			
			ScheduleListImpl schedule = new ScheduleListImpl((Integer) result[0],
                       								 ((Date) result[1]).getTime(),
                       								 ((Date) result[2]).getTime(),
                       								 (String) result[3], 
          		   			                         (Integer) result[4],
          		   			                         (String) result[5],
          		   			                         (BigInteger) result[6],
          		   			                         result[7] != null ? (Integer) result[7] : null,
          		   			                         (Integer) result[8],
          		   			                         (String) result[9],
          		   			                    (BigDecimal) result[10]);
			scheduleList.add(schedule);	
		}
		return scheduleList;
	}	
	
	@Override
	public Integer getCountList(Users user,	ParamFilterSchedule paramFilterSchedule) throws Exception {
		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
		String WHERE =" WHERE AD.areasByAreaId.id = "+ user.getAreas().getId()+" AND LA.languages.id = :languaje ";
		if(paramFilterSchedule.getFilter().getAreaName() != null){
			WHERE += " AND S.areas.name LIKE '%"+paramFilterSchedule.getFilter().getAreaName()+"%'";			
		}
		if(paramFilterSchedule.getFilter().getStartDate() != null){
			WHERE += " AND S.startDate >= '"+(fr.format(new Date(paramFilterSchedule.getFilter().getStartDate())))+"'";			
		}		
		if(paramFilterSchedule.getFilter().getFinishDate() != null){
			WHERE += " AND S.finishDate <= '"+(fr.format(new Date(paramFilterSchedule.getFilter().getFinishDate())))+"'";			
		}
		if(paramFilterSchedule.getFilter().getTypeId() != null){
			WHERE += " AND S.tableElements.id ="+paramFilterSchedule.getFilter().getTypeId();
		}
		if(paramFilterSchedule.getFilter().getReconversionScheduleId() != null){
			if(paramFilterSchedule.getFilter().getReconversionScheduleId() == 0)
				WHERE += " AND S.reconversionScheduleId IS NULL ";
			else
				WHERE += " AND S.reconversionScheduleId IS NOT NULL ";
		}
		
		String sql = "SELECT S.id, S.startDate, S.finishDate, LA.value, S.areas.id, S.areas.name, S.reconversionScheduleId"
				+ " FROM AreaDescendants AS AD JOIN AD.areasByDescendantId AS A  JOIN A.scheduleses AS S "
				+ " JOIN S.tableElements AS TE JOIN TE.labelses AS LA "+ WHERE;
		
		Query q = this.getEntityManager().createQuery(sql);							
	    q.setParameter("languaje", user.getLanguages().getId());        
		
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		
	    if(results != null){
	    	return results.size();
	    }else{
	    	return 0;
	    }	 
	}

	@Override
	public List<ScheduleArea> getListSchedulesByArea(Integer areaId) {
		String sqlQuery = "select S from Schedules as S where S.areas.id = :areaId ";
		List<ScheduleArea> list = new ArrayList<ScheduleArea>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Schedules.class);
        q.setParameter("areaId", areaId);  
		List<Schedules> results = JpaResultHelper.getResultListAndCast(q);	 
		for (int i = 0; i < results.size(); i++ ) {	
			Schedules schedule = results.get(i);
			ScheduleArea scheduleArea = new ScheduleArea(schedule.getId(),
                       								 schedule.getStartDate().getTime(),
                       										schedule.getFinishDate().getTime(),
                       										schedule.getTableElements().getId(),
                       										schedule.getAreas().getId(),
                       										schedule.getReconversionScheduleId(),
                       										schedule.getLarvicide().getId());
			list.add(scheduleArea);	
		}
		return list;
	}

	@Override
	public List<ScheduleDay> getReportDays(Integer scheduleId) throws Exception {
		List<ScheduleDay> list = new ArrayList<ScheduleDay>();		
		String sql =   " SELECT DISTINCT DAY(DATE), DAYOFWEEK(DATE) FROM Schedules INNER JOIN Inspections ON Schedules.id = Inspections.scheduleId"
					 + " INNER JOIN Plans ON Plans.inspectionId = Inspections.id"
					 + " WHERE Schedules.id = "+scheduleId
					 + " ORDER BY DAY(DATE) ASC";
		
	    Query q = this.getEntityManager().createNativeQuery(sql);	  

	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	
    	for (Object[] result : results) {	
    		ScheduleDay scheduleDay = new ScheduleDay((Integer)result[0], CalendarHelper.getDayOfWeekAcronym((Integer)result[1]));	    	
    		list.add(scheduleDay);
    	}
		return list;
	}

	@Override/*666*/
	public BigDecimal getTotalPersons(Integer areaEESS, Date date) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");		
		String sqlQuery= "";
		sqlQuery = " SELECT COUNT(DISTINCT P.users.id) FROM Plans As P JOIN P.inspections AS I "
				 + " WHERE I.areas.id = "+areaEESS+" AND P.date = '"+df.format(date)+"'";

		Query q = this.getEntityManager().createQuery(sqlQuery, Long.class);	    
	    Long count = (Long)q.getSingleResult();
        return new BigDecimal(count);
	}
	
       

	@Override
	public boolean larvicideInSchedule(Integer larvicideId) throws Exception {
		String sql =   " SELECT COUNT(*) FROM Schedules WHERE larvicideId = "+ larvicideId;		
	    Query q = this.getEntityManager().createNativeQuery(sql);	  
	    BigInteger count = (BigInteger)q.getSingleResult();
	    return count.intValue() > 0 ;	    
	}
}
