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

import java.util.List;
import com.gruposca.sapev.api.connectors.dao.base.BaseDao;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Plans;
import com.gruposca.sapev.api.connectors.dao.model.PlansAreas;
import com.gruposca.sapev.api.modelview.PlanAreas;

public interface PlansAreasDao extends BaseDao<PlansAreas, String>{

	public boolean existWithArea(Areas area) throws Exception;	
	
	public List<Integer> getListAreasId(Plans plans) throws Exception;
	
	public List<String> getListAreasName(Plans plans) throws Exception;

	public List<Areas> getListAreas(Integer planId) throws Exception;
	
	public List<Areas> getAreasNotSubstitutes(Integer planId) throws Exception;
	
	public Integer deleteByPlan(Plans plan) throws Exception;	
	
	public List<PlanAreas> getListPlanAreas(Plans plans) throws Exception;

    public List<PlansAreas> getList(Plans plans) throws Exception;
    
    public PlansAreas getPlanArea(Integer planId, Integer areaId) throws Exception;
    


}
