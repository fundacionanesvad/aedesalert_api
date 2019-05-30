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
import com.gruposca.sapev.api.connectors.dao.model.InventorySummaries;
import com.gruposca.sapev.api.modelview.InventoryListSummary;

public interface InventorySummaryDao extends BaseDao<InventorySummaries, String>{

	public Integer insertData(Integer areaId, Integer planId) throws Exception;	
	
	public List<InventorySummaries> getList(Integer areaId, Integer planId) throws Exception;		
	
	public InventoryListSummary getData(Integer areaId, Integer planId, Integer containerId) throws Exception;	
	
	public InventorySummaries getDataByContainer(Integer areaId, Integer planId, Integer containerId) throws Exception;		

}
