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
import com.gruposca.sapev.api.connectors.dao.model.Schedules;
import com.gruposca.sapev.api.modelview.Area;
import com.gruposca.sapev.api.modelview.InspectionSchedule;
import com.gruposca.sapev.api.modelview.ParamFilterSchedule;
import com.gruposca.sapev.api.modelview.Schedule;
import com.gruposca.sapev.api.modelview.ScheduleList;
import com.gruposca.sapev.api.modelview.Session;

public interface ScheduleService {
	
	ScheduleList getScheduleList(Session session, ParamFilterSchedule paramFilterSchedule);
	
	void deleteSchedule(Integer id);

	Schedule getSchedule(Integer id); 	
	
	Schedules createSchedule(Schedule schedule);
	
	Schedules updateSchedule(Integer id, Schedule schedule);
	
	List<InspectionSchedule> getScheduleInspections(Integer id);

	Area getAreaChilds(Integer id);

}
