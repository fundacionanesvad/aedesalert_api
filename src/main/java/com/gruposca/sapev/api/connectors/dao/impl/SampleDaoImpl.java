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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Date;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Houses;
import com.gruposca.sapev.api.connectors.dao.model.Plans;
import com.gruposca.sapev.api.connectors.dao.model.Samples;
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.dao.model.Visits;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.Sample;
import com.gruposca.sapev.api.modelview.SampleInspection;
import com.gruposca.sapev.api.modelview.SampleListImpl;
import com.gruposca.sapev.api.modelview.SamplesFile;

@Repository("SampleDaoImpl")
public class SampleDaoImpl extends BaseDaoHibernate<Samples, String> implements SampleDao{

	public SampleDaoImpl() { super(Samples.class); }

	@Override
	public List<Samples> getSanplesList(Visits visit) throws Exception {
		String sqlQuery = "SELECT SA FROM Samples AS SA WHERE SA.plans = :plan AND SA.houses = :houses ORDER BY SA.container.sort, SA.code ";
		List<Samples> samplesList = new ArrayList<Samples>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Samples.class);
        q.setParameter("plan", visit.getPlans());  
        q.setParameter("houses", visit.getHouses());
        samplesList = JpaResultHelper.getResultListAndCast(q);    
		return samplesList;	
	}

	@Override
	public Samples getSampleWithCode(String code) throws Exception {
		String sqlQuery = "select SA from Samples as SA where SA.code = :code ";		
		Query q = this.getEntityManager().createQuery(sqlQuery, Samples.class);
	    q.setParameter("code", code);
	    Samples samples = (Samples) JpaResultHelper.getSingleResultOrNull(q);	    
	    return samples;
	}

	@Override
	public Integer getTotalSamples(Integer reportId) throws Exception {
		
		String queryString = "SELECT COUNT(DISTINCT Samples.uuid) AS sample	" + 
							" FROM Reports R " + 
							" INNER JOIN ReportInspections RI ON R.id = RI.reportId " + 
							" INNER JOIN Plans ON RI.inspectionId = Plans.inspectionId " + 
							" INNER JOIN Samples ON Samples.planId = Plans.id " + 
							" WHERE R.id = "+reportId;
		
	    Query q = this.getEntityManager().createNativeQuery(queryString);	   
	    Integer count = ((BigInteger)q.getSingleResult()).intValue();
	    return count;
	}

	@Override
	public List<Sample> getList(Users user) throws Exception {
		List<Sample> listSamples = new ArrayList<Sample>();		
		String queryString =  " SELECT Inspections.id, MAX(Plans.date) AS DATE,"
				+ " A1.name AS areaName, A3.name AS parentName, COUNT(DISTINCT Samples.code) AS numSamples"
				+ " FROM Inspections"
				+ " INNER JOIN Areas AS A1 ON A1.id = Inspections.areaId"
				+ " INNER JOIN AreaDescendants ON A1.id = AreaDescendants.descendantId"
				+ " INNER JOIN Areas AS A2 ON A2.parentId = A1.id"
				+ " INNER JOIN Areas AS A3 ON A1.parentId = A3.id"
				+ " INNER JOIN Plans ON Inspections.id = Plans.inspectionId"
				+ " INNER JOIN Samples ON Samples.planId = Plans.id"
				+ " WHERE  result IS NULL AND AreaDescendants.areaId = "+user.getAreas().getId()
				+ " GROUP BY Inspections.id"
				+ " ORDER BY  DATE";

		
		Query q = this.getEntityManager().createNativeQuery(queryString);	 
		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		for (Object[] result : results) {	
			Sample sample = new SampleListImpl((Integer) result[0], ((Date) result[1]).getTime() , (String) result[2], (String) result[3], ((BigInteger) result[4]).intValue());
		   	listSamples.add(sample);	
		}
		return listSamples;
	}
	
	@Override
	@Transactional
	public Integer deleteByHouse(Houses house, Plans plans) throws Exception {
		String sqlQuery = "DELETE FROM Samples AS S WHERE S.houses = :house AND S.plans = :plans ";
	    Query q = this.getEntityManager().createQuery(sqlQuery);	    
        q.setParameter("house", house); 
        q.setParameter("plans", plans); 
        return q.executeUpdate();	
	}

	@Override
	public List<SamplesFile> getSanplesFile(Integer inspectionId) throws Exception {
		List<SamplesFile> listSamples = new ArrayList<SamplesFile>();		
		String queryString =  " SELECT  " + 
				"	DISTINCT Samples.code AS codigo," + 
				"	L2.value AS tipoFoco, " + 
				"	DATE_FORMAT(Plans.date, '%d/%m/%Y') AS fecha," + 
				"	CONCAT(TRIM(streetName), ' ', TRIM(streetNumber)) AS direccion," + 
				"	A3.name AS localidad," + 
				"	HEX(Samples.uuid) AS uuid," +
				"	A5.name AS sector"+
				"	FROM Inspections" + 
				"	INNER JOIN Areas AS A1 ON A1.id = Inspections.areaId" + 
				"	INNER JOIN AreaDescendants ON A1.id = AreaDescendants.descendantId" + 
				"	INNER JOIN Areas AS A2 ON A2.parentId = A1.id" + 
				"	INNER JOIN Areas AS A3 ON A1.parentId = A3.id" + 
				"	INNER JOIN Plans ON Inspections.id = Plans.inspectionId" + 
				"	INNER JOIN Samples ON Samples.planId = Plans.id" + 
				"	INNER JOIN Labels AS L2 ON  Samples.containerId = L2.tableElementId" + 
				"	INNER JOIN Houses ON Houses.uuid = Samples.houseUuid" + 
				"	INNER JOIN Areas AS A4 ON A4.id = Houses.areaId"+
				"	INNER JOIN Areas AS A5 ON A5.id = A4.parentId"+
				"	WHERE result IS NULL" + 
				"	AND Inspections.id = " + inspectionId +
				"	ORDER BY fecha,localidad,direccion";

		System.out.println(queryString);
		Query q = this.getEntityManager().createNativeQuery(queryString);	 
		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		for (Object[] result : results) {	
			SamplesFile samplesFile = new SamplesFile((String) result[0], (String) result[1], (String) result[2], (String) result[3], (String) result[4], (String)result[5], "", (String)result[6], "");
		   	listSamples.add(samplesFile);	
		}
		return listSamples;
	}
	
	@Override
	public String getSanplesPhasesNames(String uuid, Integer languageId) throws Exception {
		
		String phaseName = "";		
		String queryString = "SELECT DISTINCT L.value FROM Samples S INNER JOIN SamplePhases SP ON S.uuid = SP.sampleUuid" + 
		" INNER JOIN TableElements  T ON T.id = SP.phaseId" + 
		" INNER JOIN Labels L ON L.tableElementId = T.id " + 
		" WHERE L.languageId = :languageId" + 
		" AND S.uuid = UNHEX('"+uuid+"') " + 
		" ORDER BY T.sort ";
		
		Query q = this.getEntityManager().createNativeQuery(queryString);	 
		q.setParameter("languageId", languageId); 
		List<String> results = JpaResultHelper.getResultListAndCast(q);	 

		for(int i = 0; i < results.size(); i++) {			
			phaseName = phaseName+(String)results.get(i)+", ";
		}
		
		if(!phaseName.equals("")) {
			phaseName = phaseName.substring(0,phaseName.length()-2);
		}
			
		return phaseName;
	}

	@Override
	public String getSanplesPhasesNames(UUID uuid, Integer languageId) throws Exception {
		
		String phaseName = "";		
		String queryString = "SELECT DISTINCT L.value FROM Samples S INNER JOIN SamplePhases SP ON S.uuid = SP.sampleUuid" + 
		" INNER JOIN TableElements  T ON T.id = SP.phaseId" + 
		" INNER JOIN Labels L ON L.tableElementId = T.id " + 
		" WHERE L.languageId = :languageId" + 
		" AND S.uuid = :uuid" + 
		" ORDER BY T.sort ";
		
		Query q = this.getEntityManager().createNativeQuery(queryString);	 
		q.setParameter("uuid", uuid); 
		q.setParameter("languageId", languageId); 
		List<String> results = JpaResultHelper.getResultListAndCast(q);	 

		for(int i = 0; i < results.size(); i++) {			
			phaseName = phaseName+(String)results.get(i)+", ";
		}
		
		if(!phaseName.equals("")) {
			phaseName = phaseName.substring(0,phaseName.length()-2);
		}
			
		return phaseName;
	}


	@Override
	public List<Samples> getSanplesList(Plans plans, TableElements container, Houses houses) throws Exception {
		String sqlQuery = "SELECT SA FROM Samples AS SA WHERE SA.plans = :plans AND SA.houses = :houses AND SA.container = :container ";
		List<Samples> samplesList = new ArrayList<Samples>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Samples.class);
	    q.setParameter("plans", plans);  
        q.setParameter("houses", houses);
        q.setParameter("container", container);
        samplesList = JpaResultHelper.getResultListAndCast(q);    
		return samplesList;	
	}

	@Override
	public List<SampleInspection> getListSampleInspection(Integer inspectionId) throws Exception {
		List<SampleInspection> listSamples = new ArrayList<SampleInspection>();		
		String queryString =  " SELECT  " + 
				" DATE_FORMAT(`date`,'%d/%m/%Y') AS DATE  , " + 
				" CONCAT(streetName,' ',streetNumber) AS dir, " + 
				" A1.name AS mz, A2.name AS sector, "+
				" Samples.CODE, IF(result IS NULL, '', result)result FROM Samples " + 
				" INNER JOIN Plans ON Plans.id = Samples.planId " + 
				" INNER JOIN Houses ON Houses.uuid = Samples.houseUuid " + 
				" INNER JOIN Areas AS A1 ON A1.id = Houses.areaId " + 
				" INNER JOIN Areas AS A2 ON A2.id = A1.parentId " + 
				" WHERE Plans.inspectionId = " + inspectionId + 
				" ORDER BY DATE ";

		
		Query q = this.getEntityManager().createNativeQuery(queryString);	 
		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		for (Object[] result : results) {	
			SampleInspection sampleInspection = new SampleInspection((String) result[0], (String) result[1], (String) result[2], (String) result[3], (String) result[4], (String) result[5]);
		   	listSamples.add(sampleInspection);	
		}
		return listSamples;
	}

	@Override
	public List<SamplesFile> getSanplesInspectionFile(Integer inspectionId) throws Exception {
		List<SamplesFile> listSamples = new ArrayList<SamplesFile>();		
		String queryString =  " SELECT DISTINCT " + 
				"	DATE_FORMAT(`date`,'%d/%m/%Y') AS DATE  , " + 
				"	CONCAT(streetName,' ',streetNumber) AS dir,  " + 
				"	A1.name AS mz, A2.name AS sector, " + 
				"	Samples.CODE, IF(result IS NULL, '', result)result," + 
				"	L2.value AS tipoFoco, " + 
				"	HEX(Samples.uuid) AS uuid " + 
				"	 FROM Samples  " + 
				"	 INNER JOIN Plans ON Plans.id = Samples.planId " + 
				"	 INNER JOIN Houses ON Houses.uuid = Samples.houseUuid  " + 
				"	 INNER JOIN Areas AS A1 ON A1.id = Houses.areaId " + 
				"	 INNER JOIN Areas AS A2 ON A2.id = A1.parentId " + 
				"	INNER JOIN Labels AS L2 ON  Samples.containerId = L2.tableElementId " + 
				"	 WHERE Plans.inspectionId = " + inspectionId +
				"	 ORDER BY DATE";

		
		Query q = this.getEntityManager().createNativeQuery(queryString);	 
		List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	 
		for (Object[] result : results) {	
			SamplesFile samplesFile = new SamplesFile((String) result[4], (String) result[6], (String) result[0], (String) result[1], "", (String) result[7], (String) result[2], (String) result[3], (String) result[5]);
		   	listSamples.add(samplesFile);	
		}
		return listSamples;
	}

	@Override
	public List<Samples> getSanplesList(Integer planId, UUID houseUuid) throws Exception {
		String sqlQuery = "SELECT SA FROM Samples AS SA WHERE SA.plans.id = :plan AND SA.houses.uuid = :houses ";
		List<Samples> samplesList = new ArrayList<Samples>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Samples.class);
        q.setParameter("plan", planId);  
        q.setParameter("houses", houseUuid);
        samplesList = JpaResultHelper.getResultListAndCast(q);    
		return samplesList;	
	}

	@Override
	public List<Samples> getSanplesList(Integer planId, int areaId) throws Exception {
		String sqlQuery = "SELECT SA FROM Samples AS SA JOIN SA.houses AS H WHERE SA.plans.id = :plan AND H.areas.id = :areaId ";
		List<Samples> samplesList = new ArrayList<Samples>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Samples.class);
        q.setParameter("plan", planId);  
        q.setParameter("areaId", areaId);
        samplesList = JpaResultHelper.getResultListAndCast(q);    
		return samplesList;	
	}	
	
	
	

}
