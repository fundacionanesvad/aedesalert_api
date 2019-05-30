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
import com.gruposca.sapev.api.connectors.dao.model.Reports;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.modelview.ParamFilterReports;
import com.gruposca.sapev.api.modelview.ReportExcel;
import com.gruposca.sapev.api.modelview.ReportList;

public interface ReportDao extends BaseDao<Reports, String>{

	public List<Reports> getReportsAreaList(Areas area) throws Exception;		
	
	public Integer getWeek(Integer reportId) throws Exception;
	
	public String getLarvicides(Integer reportId) throws Exception;
	
	public ReportList getListByAreaUser (Users user, ParamFilterReports paramFilterReports) throws Exception;
	
	public List<ReportExcel> getListExcel (Integer reportId, Integer detailLevel) throws Exception;	
	
}
