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
import com.gruposca.sapev.api.modelview.Substitutes;
import org.springframework.stereotype.Repository;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Inspections;
import com.gruposca.sapev.api.connectors.dao.model.Plans;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.ParamFilterInspections;
import com.gruposca.sapev.api.modelview.ParamFilterPlanVisits;
import com.gruposca.sapev.api.modelview.ParamFilterPlans;
import com.gruposca.sapev.api.modelview.Plan;
import com.gruposca.sapev.api.modelview.PlanInspection;
import com.gruposca.sapev.api.modelview.PlanInspectionSearchList;
import com.gruposca.sapev.api.modelview.PlanListSyncImpl;
import com.gruposca.sapev.api.modelview.PlanVisitImpl;


@Repository("PlanDaoImpl")
public class PlanDaoImpl extends BaseDaoHibernate<Plans, String> implements PlanDao{
	
	public PlanDaoImpl() { super(Plans.class); }

	@Override
	public List<PlanVisitImpl> getListByPlanId(Integer id,ParamFilterPlanVisits ParamFilterPlanVisits)throws Exception{
		String uuidHex = "";
		String uuid = "";
		String WHERE =" WHERE v.planId = "+ id ;
		
		if(ParamFilterPlanVisits.getFilter().getHouseStreet() != null){
			WHERE += " AND Houses.streetName LIKE '%"+ParamFilterPlanVisits.getFilter().getHouseStreet() +"%'";}
		if(ParamFilterPlanVisits.getFilter().getHouseNumber() != null){
			WHERE += " AND Houses.streetNumber="+ParamFilterPlanVisits.getFilter().getHouseNumber();}
		if(ParamFilterPlanVisits.getFilter().getResultId() != null){
			WHERE += " AND v.resultId ="+ParamFilterPlanVisits.getFilter().getResultId();}
		if(ParamFilterPlanVisits.getFilter().getPersonsNumber() != null){
			WHERE += " AND Houses.personsNumber ="+ParamFilterPlanVisits.getFilter().getPersonsNumber();}
		if(ParamFilterPlanVisits.getFilter().getSample() != null){
			WHERE += " AND COUNT(Samples.uuid)'sumSamples' ="+ParamFilterPlanVisits.getFilter().getSample();}
		if(ParamFilterPlanVisits.getFilter().getDose() != null){
			WHERE += " AND v.larvicide ="+ParamFilterPlanVisits.getFilter().getDose();
		}
		
String ORDER =" ORDER BY" ;		
		if(ParamFilterPlanVisits.getSorting().getHouseStreet() != null){
			ORDER += " Houses.streetName "+ParamFilterPlanVisits.getSorting().getHouseStreet();}
		else if(ParamFilterPlanVisits.getSorting().getHouseNumber() != null){
			ORDER += " Houses.streetNumber "+ParamFilterPlanVisits.getSorting().getHouseNumber();}
		else if(ParamFilterPlanVisits.getSorting().getResultId() != null){
			ORDER += " v.resultId "+ParamFilterPlanVisits.getSorting().getResultId();}
		else if(ParamFilterPlanVisits.getSorting().getPersonsNumber() != null){
			ORDER += " Houses.personsNumber "+ParamFilterPlanVisits.getSorting().getPersonsNumber();}
		else if(ParamFilterPlanVisits.getSorting().getSample() != null){
			ORDER += " sumSamples "+ParamFilterPlanVisits.getSorting().getSample();}
		else if(ParamFilterPlanVisits.getSorting().getDose() != null){
			ORDER += " v.larvicide "+ParamFilterPlanVisits.getSorting().getDose();
		}else{
			ORDER += " areaId " + ParamFilterPlanVisits.getSorting().getAreaName();
		}	
		
		
		
		Integer init = (ParamFilterPlanVisits.getPage() - 1) * ParamFilterPlanVisits.getCount();		
		List<PlanVisitImpl> list = new ArrayList<PlanVisitImpl>();
		String sql = "SELECT Houses.areaId," + 
		"Houses.streetName," + 
		"Houses.streetNumber," + 
		"v.resultId," + 
		"Houses.personsNumber," + 
		"COUNT(Samples.uuid)'sumSamples'," + 
		"v.larvicide," +
		"HEX(v.uuid) " +
		"FROM Visits AS v " + 
		"INNER JOIN Houses ON v.houseUuid = Houses.uuid " + 
		"LEFT JOIN Samples ON (Houses.uuid = Samples.houseUuid AND Samples.planId =" + id + ") " + 
		WHERE + 
		" GROUP BY Houses.uuid"
		+ ORDER;
		
		System.out.println(sql);
		
		Query q = this.getEntityManager().createNativeQuery(sql);	
	    q.setFirstResult(init);
	    q.setMaxResults(ParamFilterPlanVisits.getCount());
		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);
		
		for (Object[] result : results) {	
			
			
    		if(result[0] != null){	    			
    			uuidHex = ((String)result[7]).toLowerCase();
    			uuid = String.format("%s-%s-%s-%s-%s", uuidHex.substring(0,8), uuidHex.substring(8,12), uuidHex.substring(12,16), uuidHex.substring(16,20),uuidHex.substring(20,uuidHex.length()));
    		}
			
			PlanVisitImpl planVisitList = new PlanVisitImpl((Integer) result[0],
												  (String) result[1],
												  (String) result[2],
												  (Integer)result[3],
												  (Integer)result[4],
												  (BigInteger)result[5],
												  (BigDecimal)result[6],
												  uuid);
			

			list.add(planVisitList);
		}

		return list;
	}
	
	@Override
	public Integer getCountList(Integer id, ParamFilterPlanVisits paramFilterPlanVisits) throws Exception {
				
		String WHERE =" WHERE v.planId = "+ id ;
		
		if(paramFilterPlanVisits.getFilter().getHouseStreet() != null){
			WHERE += " AND Houses.streetName LIKE '%"+paramFilterPlanVisits.getFilter().getHouseStreet() +"%'";
			System.out.println("House streetName: " + paramFilterPlanVisits.getFilter().getHouseStreet());
		}
		if(paramFilterPlanVisits.getFilter().getHouseNumber() != null){
			WHERE += " AND Houses.streetNumber="+paramFilterPlanVisits.getFilter().getHouseNumber();
			System.out.println("House streetNumber: " + paramFilterPlanVisits.getFilter().getHouseNumber());
		}
		if(paramFilterPlanVisits.getFilter().getResultId() != null){
			WHERE += " AND v.resultId ="+paramFilterPlanVisits.getFilter().getResultId();
			System.out.println("Result Id: " + paramFilterPlanVisits.getFilter().getResultId());
		}
		if(paramFilterPlanVisits.getFilter().getPersonsNumber() != null){
			WHERE += " AND Houses.personsNumber ="+paramFilterPlanVisits.getFilter().getPersonsNumber();
			System.out.println("Person Number: " + paramFilterPlanVisits.getFilter().getPersonsNumber());
		}
		if(paramFilterPlanVisits.getFilter().getSample() != null){
			WHERE += " AND COUNT(Samples.uuid)'sumSamples' ="+paramFilterPlanVisits.getFilter().getSample();
			System.out.println("Samples: " + paramFilterPlanVisits.getFilter().getSample());
		}
		if(paramFilterPlanVisits.getFilter().getDose() != null){
			WHERE += " AND v.larvicide ="+paramFilterPlanVisits.getFilter().getDose();
			System.out.println("Dose: " + paramFilterPlanVisits.getFilter().getDose());
		}
		
		String queryString = "SELECT Houses.areaId,"  
				+"Houses.streetName," 
				+"Houses.streetNumber," 
				+"v.resultId," 
				+"Houses.personsNumber," 
				+"COUNT(Samples.uuid)'sumSamples',"  
				+"v.larvicide " 
				+"FROM Visits AS v " 
				+"INNER JOIN Houses ON v.houseUuid = Houses.uuid " 
				+"LEFT JOIN Samples ON (Houses.uuid = Samples.houseUuid AND Samples.planId =" + id + ") " 
				+WHERE
				+" GROUP BY Houses.uuid";
		
		System.out.println(queryString);
		
	    Query q = this.getEntityManager().createNativeQuery(queryString);
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
	    if(results != null){
	    	return results.size();
	    }else{
	    	return 0;
	    }	 
	}
	
	@Override
	public List<Plans> getListPlans(Integer inspectionId) throws Exception {
		String sqlQuery = "SELECT P FROM Plans AS P WHERE P.inspections.id = :inspectionId  ";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Plans.class);
        q.setParameter("inspectionId", inspectionId);        
        List<Plans> listPlans = JpaResultHelper.getResultListAndCast(q);
        System.out.println(sqlQuery);
	   	return listPlans;	
	   	
	}

	@Override
	public List<Plan> getPlanSyncList(Users user) throws Exception {
		List<Plan> plansList = new ArrayList<Plan>();
		String sql = "SELECT PL.id, PL.date, PL.inspections.areas.name  FROM Plans AS PL WHERE PL.users = :user AND (PL.tableElements.id = :statePlanInProgress OR PL.tableElements.id = :statePlanPlanned) AND PL.inspections.tableElementsByStateId.id = :stateInspection ORDER BY PL.date ASC";
		Query q = this.getEntityManager().createQuery(sql);							
	    q.setParameter("user", user);
	    q.setParameter("statePlanInProgress", ConfigurationHelper.getStatePlanInProgress());
	    q.setParameter("statePlanPlanned", ConfigurationHelper.getStatePlanPlanned());
	    q.setParameter("stateInspection", ConfigurationHelper.getStateInspectionActive());

		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		for (Object[] result : results) {		        
		  	Plan plan = new PlanListSyncImpl((Integer) result[0], ((Date) result[1]).getTime(), (String) result[2]);
		   	plansList.add(plan);	
		}
		return plansList;
	}	
	
	@Override
	public boolean allPlansFinished(Integer inspectionId, Integer stateFinished){
		String sqlQuery = "SELECT COUNT(P.id) FROM Plans AS P WHERE P.inspections.id = :inspectionId AND P.tableElements.id != :stateFinished";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Long.class);	    
	    q.setParameter("inspectionId", inspectionId);
	    q.setParameter("stateFinished", stateFinished);
        Long count = (Long)q.getSingleResult();
        if (count > 0){ 
        	return false;
        }else{
        	return true;
        }        
	}

	@Override
	public List<Plans> getListPlansUser(Users user) throws Exception {
		String sqlQuery = "SELECT P FROM Plans AS P WHERE P.users = :user ORDER BY P.date DESC";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Plans.class);
        q.setParameter("user", user);        
        List<Plans> listPlans = JpaResultHelper.getResultListAndCast(q);
	   	return listPlans;	   
	}

	@Override
	public List<Date> getListDatesPlans(Inspections inspection) throws Exception {
		String sqlQuery = "SELECT DISTINCT P.date FROM Plans AS P WHERE P.inspections = :inspection";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Date.class);
        q.setParameter("inspection", inspection);        
        List<Date> listDatesPlans = JpaResultHelper.getResultListAndCast(q);
	   	return listDatesPlans;	  
	}

	@Override
	public List<Users> getListUserPlans(Inspections inspection) throws Exception {
		String sqlQuery = "SELECT DISTINCT P.users FROM Plans AS P WHERE P.inspections = :inspection";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Users.class);
        q.setParameter("inspection", inspection);        
        List<Users> listUsersPlans = JpaResultHelper.getResultListAndCast(q);
	   	return listUsersPlans;	  
	}

	@Override
	public PlanInspection getPlanInspection(Integer inspectionId, ParamFilterPlans paramFilterPlans, Users user) throws Exception {
		
		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");		
		String WHEREAREAS = "";
		String WHERE =" WHERE P.inspectionId = :inspection AND L.id = :languaje ";
		if(paramFilterPlans.getFilter().getPlanSize() != null){
			WHERE += " AND P.planSize  = "+paramFilterPlans.getFilter().getPlanSize();
		}			
		if(paramFilterPlans.getFilter().getDate() != null){
			WHERE += " AND P.date = '"+(fr.format(new Date(paramFilterPlans.getFilter().getDate())))+"'";			
		}		
		if(paramFilterPlans.getFilter().getUserName() != null){
			WHERE += " AND U.name LIKE '%"+paramFilterPlans.getFilter().getUserName()+"%'";			
		}
		if(paramFilterPlans.getFilter().getStateId() != null){
			WHERE += " AND TE.id ="+paramFilterPlans.getFilter().getStateId();
		}		
		if(paramFilterPlans.getFilter().getAreaName() != null){
			WHEREAREAS += " WHERE areasName LIKE '%"+paramFilterPlans.getFilter().getAreaName()+"%'";			
		}			
		
		String ORDER =" ORDER BY";
		if(paramFilterPlans.getSorting().getPlanSize() != null){
			ORDER += " P.planSize "+paramFilterPlans.getSorting().getPlanSize();
		}else if (paramFilterPlans.getSorting().getDate() != null){
			ORDER += " P.date "+paramFilterPlans.getSorting().getDate();
		}else if(paramFilterPlans.getSorting().getStateName() != null){
			ORDER += " LA.value "+paramFilterPlans.getSorting().getStateName();
		}else if(paramFilterPlans.getSorting().getAreaName() != null){
			ORDER += " areasName "+paramFilterPlans.getSorting().getAreaName();
		}else if(paramFilterPlans.getSorting().getUserName() != null){
			ORDER += " U.name "+paramFilterPlans.getSorting().getUserName();	
		}else{
			ORDER += " P.date "+paramFilterPlans.getSorting().getDate();
		}		
		
		Integer init = (paramFilterPlans.getPage() - 1) * paramFilterPlans.getCount();		
		
		List<PlanInspectionSearchList> planInspectionList = new ArrayList<PlanInspectionSearchList>();
		String sql = " SELECT * FROM (SELECT " + 
				" P.id, P.date,LA.value,P.planSize,U.name," + 
				" GROUP_CONCAT(CONCAT(A2.name,\" > \",A.name) SEPARATOR ', ') AS areasName, P.stateId " + 
				" FROM Plans AS P INNER JOIN Users AS U ON U.id = P.userId " + 
				" INNER JOIN TableElements AS TE ON TE.id = P.stateId " + 
				" INNER JOIN Labels AS LA ON LA.tableElementId = TE.id " + 
				" INNER JOIN Languages AS L ON L.id = LA.languageId " + 
				" INNER JOIN PlansAreas AS PA ON PA.planId = P.id " + 
				" INNER JOIN Areas AS A ON A.id = PA.areaId " + 
				" INNER JOIN Areas AS A2 ON A.parentId = A2.id " + WHERE +
				" GROUP BY P.id "+ ORDER+") AS C "+WHEREAREAS;			
		
		Query q = this.getEntityManager().createNativeQuery(sql);
	    q.setParameter("inspection", inspectionId);
	    q.setParameter("languaje", user.getLanguages().getId());        
	    q.setFirstResult(init);
	    q.setMaxResults(paramFilterPlans.getCount());
		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		
		for (Object[] result : results) {		    
			
			PlanInspectionSearchList planInspection = new PlanInspectionSearchList(
											(Integer) result[0],
											((Date) result[1]).getTime(),
		   			                        (String) result[2], 
		   			                        (Integer) result[3], 
		   			                        (String) result[4],
		   			                        (String) result[5],
		   			                        (Integer) result[6]);
			planInspectionList.add(planInspection);	
		}
		
		PlanInspection planInspection = new PlanInspection(planInspectionList, results.size());
		
		return planInspection;
	}

	@Override
	public Integer getWeek(Integer planId) throws Exception {
		String sqlQuery = "SELECT WEEK(P.date,6) AS week FROM Plans AS P WHERE P.id = :planId";
		Query q = this.getEntityManager().createQuery(sqlQuery, Integer.class);
        q.setParameter("planId", planId);  
        Integer week = (Integer) JpaResultHelper.getSingleResultOrNull(q);       
	    return week;	
	}

	@Override
	public Integer getHousesArea(int planId) throws Exception {
		String sqlQuery = " SELECT SUM(houses) FROM Plans INNER JOIN PlansAreas ON Plans.id = PlansAreas.planId " + 
							"INNER JOIN Areas ON Areas.id= PlansAreas.areaId " + 
							"WHERE planId =" +planId;
	    Query q = this.getEntityManager().createNativeQuery(sqlQuery);	    
	    BigDecimal sum = (BigDecimal)q.getSingleResult();
        return sum.intValue(); 
	}

	@Override
	public List<Integer> getListPendingPlans() throws Exception {
		List<Integer> list = new ArrayList<Integer>();
		String sqlQuery = "SELECT P.id FROM Plans AS P WHERE P.tableElements.id = :pending  ";
		Query q = this.getEntityManager().createQuery(sqlQuery, Integer.class);
        q.setParameter("pending", 7005); 
        list = JpaResultHelper.getResultListAndCast(q);  
        
		return list;
	}

	@Override
	public List<Substitutes> getSubstitutes(Integer inspectionId) throws Exception {
		List<Substitutes> list = new ArrayList<Substitutes>();
		String sql = " SELECT planId, DATE_FORMAT(`date`,'%d/%m/%Y'),inspector, `name`, pin, " + 
				" IF(NUM IS NOT NULL, 'Y', 'N') AS assigned, areaId" + 
				" FROM  " + 
				" (SELECT P.id AS planId,P.date,U.name AS inspector,A.name,PA.pin, A.id AS areaId " + 
				" FROM Plans P INNER JOIN PlansAreas PA ON PA.planId = P.id " + 
				" INNER JOIN Areas A ON A.id = PA.areaId " + 
				" INNER JOIN Users U ON U.id = P.userId " + 
				" WHERE inspectionId = "+inspectionId+" AND P.stateId IN (7001,7002) and PA.substitute = true ) AS A " + 
				" LEFT JOIN " + 
 				" (SELECT A.id, CAST(COUNT(PA.areaId) AS UNSIGNED INTEGER) AS NUM " + 
				" FROM Plans P INNER JOIN PlansAreas PA ON PA.planId = P.id " + 
				" INNER JOIN Areas A ON A.id = PA.areaId " + 
				" INNER JOIN Users U ON U.id = P.userId " + 
				" WHERE inspectionId = "+inspectionId+" AND P.stateId IN (7001,7002) and PA.substitute = true " + 
				" GROUP BY A.id " + 
				" HAVING COUNT(A.id)>1) AS A2 " + 
				" ON A.areaId = A2.id " + 
				" ORDER BY DATE ASC, inspector ";		
			
		Query q = this.getEntityManager().createNativeQuery(sql);
		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		
		for (Object[] result : results) {		    
			
			Substitutes substitutes = new Substitutes(
											(Integer) result[0],
											(String) result[1],
		   			                        (String) result[2], 
		   			                        (String) result[3], 
		   			                        (Integer) result[4],
		   			                        ((String) result[5]).equals("N") ? false : true,
		   			                        (Integer) result[6]);
			list.add(substitutes);	
		}	
		
		return list;
	}
	
}
