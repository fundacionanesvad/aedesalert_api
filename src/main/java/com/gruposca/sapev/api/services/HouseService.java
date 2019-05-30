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
package com.gruposca.sapev.api.services;

import java.util.List;

import com.gruposca.sapev.api.connectors.dao.model.Houses;
import com.gruposca.sapev.api.modelview.House;
import com.gruposca.sapev.api.modelview.HouseAreaImpl;
import com.gruposca.sapev.api.modelview.HouseImpl;
import com.gruposca.sapev.api.modelview.HousesList;
import com.gruposca.sapev.api.modelview.ParamFilterHouses;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.Visit;

public interface HouseService {
	
	HousesList getHouseList(ParamFilterHouses paramFilterHouses, Session session);

	House getHouse(String uuid);
	
	
	
	Houses createHouse(HouseImpl house);
	
	Houses updateHouse(String uuid, HouseImpl house);
	
	Boolean deleteHouse(String uuid);
	
	List<Visit> getVisitsList(Session session, String uuid);
	
	boolean syncHouses(Session session, Integer planId);
	
	boolean updatecodesHouse();
	
	boolean updateAddresses(String uuid, HouseAreaImpl house);

}
