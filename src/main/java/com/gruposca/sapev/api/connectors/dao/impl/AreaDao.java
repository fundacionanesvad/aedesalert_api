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

import java.util.Date;
import java.util.List;
import com.gruposca.sapev.api.connectors.dao.base.BaseDao;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Languages;
import com.gruposca.sapev.api.modelview.AreaMapFocus;
import com.gruposca.sapev.api.modelview.AreaMapList;
import com.gruposca.sapev.api.modelview.SamplesMapFocus;

public interface AreaDao extends BaseDao<Areas, String>{

	public Long numChilds(Areas area) throws Exception;
	
	public List<Areas> getListAreas(Areas parentArea) throws Exception;/*666*/
	
	public List<Areas> getListAreasEess(Areas redArea) throws Exception;
	
	public List<Areas> getListAreasPlanInspection(Integer inspectionId) throws Exception;
	
	public List<Areas> getListAreasSectorInspection(Integer inspectionId) throws Exception;
	
	public List<Areas> getListAreasManzanaInspection(Integer inspectionId, Integer sectorId) throws Exception;
	 
	public List<AreaMapList> getListAreasMap(Areas area, Date startDate, Date finishDate, int userAreaId) throws Exception;		
	 
	public List<AreaMapFocus> getListAreasMapFocus(Areas area, int userAreaId) throws Exception;		

	public List<SamplesMapFocus> getSamplesMapFocus(Integer areaId, Date startDate, Date finishDate, Languages language) throws Exception;	
	
	public boolean userAreaPermission(Integer userAreaId, Integer areaId) throws Exception;
	
	public List<Areas> getParentsPlan(Integer planId) throws Exception;
	
	public List<Areas> getListChildByType(Areas parentArea, Integer typeId) throws Exception;
	
	public List<Areas> getListByType(Areas parentArea, Integer typeId) throws Exception;
	
	public Integer getAreaParent(String listAreasId, Integer listSize) throws Exception;
	
	public Areas getParentArea() throws Exception;
	
	public List<Areas> getListAreasOrder() throws Exception;
	
	public String getSectorNames(int planId) throws Exception;

	public List<Areas> getListAreasMR(Integer microrredId, Integer eessId) throws Exception;

	public List<Areas> getAreasPlanVisits(Integer planId) throws Exception;

	public Integer executeProc(int areaId) throws Exception;
	
	public List<Areas> getScheduleAteasChilds(int scheduleId) throws Exception;


}
