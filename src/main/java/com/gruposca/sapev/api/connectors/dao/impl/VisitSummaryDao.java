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
import com.gruposca.sapev.api.connectors.dao.model.VisitSummaries;
import com.gruposca.sapev.api.modelview.VisitSumaryInsertUpdate;
import com.gruposca.sapev.api.modelview.VisitSummaryInspector;


public interface VisitSummaryDao extends BaseDao<VisitSummaries, String>{
	
	public VisitSummaries getVisitSummarie(Integer areaId, Integer planId) throws Exception;
	
	public Integer insertData(Integer areaId, Integer planId, VisitSumaryInsertUpdate visitSumaryInsertUpdate) throws Exception;	
	
	public Integer updateData(Integer areaId, Integer planId, VisitSumaryInsertUpdate visitSumaryInsertUpdate) throws Exception;
	
	public Integer getFocus(Integer areaId, Integer planId ) throws Exception;		
	
	public Integer getTreated(Integer areaId, Integer planId ) throws Exception;		

	public Integer getDestroyed(Integer areaId, Integer planId ) throws Exception;	
	
	public Integer getPersons(Integer areaId, Integer planId ) throws Exception;
	
	public VisitSumaryInsertUpdate getData(Integer areaId, Integer planId) throws Exception;
	
	public VisitSummaryInspector getSummary(Integer planId) throws Exception;
	
	public List<VisitSummaries> getList(Integer planId) throws Exception;	

}
