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
import com.gruposca.sapev.api.connectors.dao.model.Houses;
import com.gruposca.sapev.api.connectors.dao.model.Plans;
import com.gruposca.sapev.api.connectors.dao.model.Samples;
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.dao.model.Visits;
import com.gruposca.sapev.api.modelview.Sample;
import com.gruposca.sapev.api.modelview.SampleInspection;
import com.gruposca.sapev.api.modelview.SamplesFile;

public interface SampleDao extends BaseDao<Samples, String>{

	public List<Samples> getSanplesList(Visits visit) throws Exception;
	
	public List<Samples> getSanplesList(Integer planId, UUID houseUuid) throws Exception;
	
	public List<Samples> getSanplesList(Integer planId, int areaId) throws Exception;
	
	public Samples getSampleWithCode(String code) throws Exception;
	
	public Integer getTotalSamples(Integer reportId) throws Exception;
	
	List<Sample> getList(Users users) throws Exception;		

	public Integer deleteByHouse(Houses house, Plans plans) throws Exception;
	
	public List<SamplesFile> getSanplesFile(Integer inspectionId) throws Exception;
	
	public String getSanplesPhasesNames(String uuid, Integer languageId) throws Exception;
	
	public String getSanplesPhasesNames(UUID uuid, Integer languageId) throws Exception;
	
	public List<Samples> getSanplesList(Plans plans, TableElements container, Houses houses) throws Exception;
	
	public List<SampleInspection> getListSampleInspection(Integer inspectionId) throws Exception;

	public List<SamplesFile> getSanplesInspectionFile(Integer inspectionId) throws Exception;

}
