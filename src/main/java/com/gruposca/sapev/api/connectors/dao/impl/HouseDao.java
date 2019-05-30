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
import java.util.UUID;

import com.gruposca.sapev.api.connectors.dao.base.BaseDao;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Houses;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.modelview.House;
import com.gruposca.sapev.api.modelview.HouseObjectList;
import com.gruposca.sapev.api.modelview.ParamFilterHouses;
import com.gruposca.sapev.api.modelview.SyncHousePlan;

public interface HouseDao extends BaseDao<Houses, String>{

	public List<HouseObjectList> getList(ParamFilterHouses paramFilterHouses,Users user) throws Exception;
	
	public Integer getCountList(ParamFilterHouses paramFilterHouses, Users user) throws Exception;
	
	public boolean existWithArea(Areas area) throws Exception;	
	
	public Houses findByUUID(UUID uuid) throws Exception;
	
	public List<Houses> getHousesList(Integer areaId) throws Exception;
	
	public int getNumberHouse(String code) throws Exception;
	
	public int updateStreetName(String streetNameOld, String streetNameNew, int areaId) throws Exception;
	
	public int updateStreetNumber(String streetNameOld, String streetNumberOld, String streetNumnerNew, int areaId) throws Exception;
	
	public List<House> getList(Integer areaId) throws Exception;
}
