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

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Plans;
import com.gruposca.sapev.api.connectors.dao.model.PlansAreas;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.PlanAreas;

@Repository("PlansAreasDaoImpl")
public class PlansAreasDaoImpl extends BaseDaoHibernate<PlansAreas, String> implements PlansAreasDao{

	@Override
	public boolean existWithArea(Areas area) throws Exception {
		String sqlQuery = "select COUNT(PA.id) from PlansAreas as PA where PA.areas = :area ";
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
	public List<Integer> getListAreasId(Plans plans) throws Exception {
		List<Integer> listAreas = new ArrayList<Integer>();
		String sqlQuery = "select PA.areas.id  from PlansAreas as PA where PA.plans = :plans order by PA.areas.name";
		Query q = this.getEntityManager().createQuery(sqlQuery, Integer.class);
        q.setParameter("plans", plans); 
        listAreas = JpaResultHelper.getResultListAndCast(q);  
		return listAreas;
	}

	@Override
	public List<Areas> getListAreas(Integer planId) throws Exception {
		List<Areas> listAreas = new ArrayList<Areas>();
		String sqlQuery = "select PA.areas from PlansAreas as PA where PA.plans.id = :planId order by PA.areas.name";
		Query q = this.getEntityManager().createQuery(sqlQuery, Areas.class);
        q.setParameter("planId", planId); 
        listAreas = JpaResultHelper.getResultListAndCast(q);
        return listAreas;
	}

	@Override
	@Transactional
	public Integer deleteByPlan(Plans plan) throws Exception {
		String sqlQuery = "DELETE FROM PlansAreas AS PA WHERE PA.plans = :plan ";
	    Query q = this.getEntityManager().createQuery(sqlQuery);	    
        q.setParameter("plan", plan); 
        return q.executeUpdate();
	}

	@Override
	public List<String> getListAreasName(Plans plans) throws Exception {
		List<String> listAreas = new ArrayList<String>();
		String sqlQuery = "select PA.areas.name  from PlansAreas as PA where PA.plans = :plans order by PA.areas.name";
		Query q = this.getEntityManager().createQuery(sqlQuery, String.class);
        q.setParameter("plans", plans); 
        listAreas = JpaResultHelper.getResultListAndCast(q);  
		return listAreas;
	}

	@Override
	public List<PlanAreas> getListPlanAreas(Plans plans) throws Exception {
		List<PlanAreas> listAreas = new ArrayList<PlanAreas>();
		String sqlQuery = "select PA.areas.id, PA.scheduledHouses,PA.substitute, PA.pin from PlansAreas as PA where PA.plans = :plans order by PA.areas.name";
		Query q = this.getEntityManager().createQuery(sqlQuery);
        q.setParameter("plans", plans); 
	    List<Object[]> results = JpaResultHelper.getResultListAndCast(q);	
	    for (Object[] result : results) {			
			PlanAreas planAreas = new PlanAreas((Integer)result[0], (Integer)result[1], (boolean)result[2], (Integer)result[3]);	
			listAreas.add(planAreas);	    	
	    }					
		return listAreas;	
	}

	@Override
	public List<PlansAreas> getList(Plans plan) throws Exception {
		List<PlansAreas> list = new ArrayList<PlansAreas>();
		String sqlQuery = "select PA from PlansAreas as PA where PA.plans = :plan";
		Query q = this.getEntityManager().createQuery(sqlQuery, PlansAreas.class);
        q.setParameter("plan", plan); 
        list = JpaResultHelper.getResultListAndCast(q);
        return list;
	}

	@Override
	public PlansAreas getPlanArea(Integer planId, Integer areaId) throws Exception {
		String sqlQuery = "select PA from PlansAreas as PA where PA.areas.id = :areaId AND PA.plans.id = :planId ";
		Query q = this.getEntityManager().createQuery(sqlQuery, PlansAreas.class);
        q.setParameter("areaId", areaId); 
        q.setParameter("planId", planId);
        PlansAreas plansAreas = (PlansAreas)q.getSingleResult();
        if(plansAreas.getId() != null) {
            return plansAreas;              	

        }else return null;
	}

	@Override
	public List<Areas> getAreasNotSubstitutes(Integer planId) throws Exception {
		List<Areas> listAreas = new ArrayList<Areas>();
		String sqlQuery = "select PA.areas from PlansAreas as PA where PA.plans.id = :planId AND PA.substitute = false order by PA.areas.name";
		Query q = this.getEntityManager().createQuery(sqlQuery, Areas.class);
        q.setParameter("planId", planId); 
        listAreas = JpaResultHelper.getResultListAndCast(q);
        return listAreas; 
	}

	
}
