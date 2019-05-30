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
package com.gruposca.sapev.api.modelview;

import java.math.BigDecimal;

public class TrapDataModelList {
	
	private String  code;
	private Integer trapId;
	private Integer trapDataId;
	private Integer eggs;
	private Integer resultId;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private Integer areaId;

	public TrapDataModelList(){}
	
	public TrapDataModelList(String code, Integer trapId, Integer trapDataId, Integer eggs, Integer resultId, BigDecimal latitude, BigDecimal longitude, Integer areaId){
		this.code       = code;
		this.trapDataId = trapDataId;
		this.trapId     = trapId;
		this.resultId   = resultId;
		this.eggs       = eggs;
		this.latitude   = latitude;
		this.longitude  = longitude;
		this.areaId     = areaId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getTrapId() {
		return trapId;
	}

	public void setTrapId(Integer trapId) {
		this.trapId = trapId;
	}

	public Integer getTrapDataId() {
		return trapDataId;
	}

	public void setTrapDataId(Integer trapDataId) {
		this.trapDataId = trapDataId;
	}

	public Integer getEggs() {
		return eggs;
	}

	public void setEggs(Integer eggs) {
		this.eggs = eggs;
	}

	public Integer getResultId() {
		return resultId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}	
}
