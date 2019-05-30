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
import java.sql.Date;
import java.util.List;

import com.gruposca.sapev.api.connectors.dao.base.BaseDao;
import com.gruposca.sapev.api.connectors.dao.model.TrapData;
import com.gruposca.sapev.api.modelview.TrapDataModelList;

public interface TrapDataDao extends BaseDao<TrapData, String>{
	
	List<TrapDataModelList> getList(Integer microrredId, Integer eessId, String date) throws Exception;
	
	List<Date> getListDates(Integer areaId, String dateIni, String dateFin) throws Exception;	
	
	TrapData getData(Integer trapId, Date date) throws Exception;
	
	List<BigDecimal> getIPO(Integer areaId, String dateIni, String dateFin) throws Exception;	
	
	List<BigDecimal> getIDH(Integer areaId, String dateIni, String dateFin) throws Exception;

	TrapData getData(Integer trapId, Date date, Integer trapLocationId) throws Exception;
}
