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
import java.util.Date;
import java.util.List;
import com.gruposca.sapev.api.connectors.dao.base.BaseDao;
import com.gruposca.sapev.api.connectors.dao.model.Schedules;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.modelview.ParamFilterSchedule;
import com.gruposca.sapev.api.modelview.ScheduleArea;
import com.gruposca.sapev.api.modelview.ScheduleDay;
import com.gruposca.sapev.api.modelview.ScheduleListImpl;

public interface ScheduleDao extends BaseDao<Schedules, String>{	
	
	public List<ScheduleListImpl> getList(Users user, ParamFilterSchedule paramFilterSchedule) throws Exception;
	
	public Integer getCountList(Users user, ParamFilterSchedule paramFilterSchedule) throws Exception;

	public List<ScheduleArea> getListSchedulesByArea(Integer areaId);
	
	public List<ScheduleDay> getReportDays(Integer scheduleId) throws Exception;	
	
	public BigDecimal getTotalPersons(Integer areaEESS, Date date)throws Exception;/*666*/ 
	
	public boolean larvicideInSchedule(Integer larvicideId) throws Exception;
	

}
