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

import com.gruposca.sapev.api.connectors.dao.model.Reports;
import com.gruposca.sapev.api.modelview.InspectionListImpl;
import com.gruposca.sapev.api.modelview.ParamFilterReports;
import com.gruposca.sapev.api.modelview.Report;
import com.gruposca.sapev.api.modelview.ReportInspection;
import com.gruposca.sapev.api.modelview.ReportList;
import com.gruposca.sapev.api.modelview.Schedule;
import com.gruposca.sapev.api.modelview.Session;

public interface ReportService {

	ReportList getReportList(Session session, ParamFilterReports paramFilterReports);
	
	Report getReport(Integer id);
	
	Reports createReport(Session session, Report report);
	
	Reports updateReport(Integer id, Report report);
	
	boolean deleteReport(Integer id);
	
	String createPdf(Integer id);
	
	String createXls(Integer id);
	
	String createPlanMapReport(Integer id);
	
	String createScheduleReport(Integer scheduleId);
	
	List<InspectionListImpl> getInspections(ReportInspection reportInspection);

}
