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
 ******************************************************************************/
package com.gruposca.sapev.api.connectors.dao.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Scenes;
import com.gruposca.sapev.api.helper.JpaResultHelper;

@Repository("ScenesDaoImpl")
public class ScenesDaoImpl extends BaseDaoHibernate<Scenes, String> implements ScenesDao{

	public ScenesDaoImpl() { super(Scenes.class); }
	
	@Override
	public boolean existWithArea(Areas area) throws Exception {
		String sqlQuery = "select COUNT(SC.id) from Scenes as SC where SC.areas = :area ";
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
	public List<Scenes> getListScenes(Integer areaId) throws Exception {
		List<Scenes> listScenes = new ArrayList<Scenes>();
		String sqlQuery = "SELECT S FROM AreaDescendants AS AD JOIN AD.areasByDescendantId AS A "
				+ " JOIN A.sceneses AS S WHERE AD.areasByAreaId.id = :areaId";
		Query q = this.getEntityManager().createQuery(sqlQuery, Scenes.class);
        q.setParameter("areaId", areaId);  
		listScenes = JpaResultHelper.getResultListAndCast(q);       
	    return listScenes;	   
	}
	
}
