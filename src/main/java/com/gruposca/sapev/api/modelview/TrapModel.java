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

public class TrapModel {
	
	private Integer   number;
	private String    code;
	private Integer   eessId;
	private Integer   microrredId;
	private Integer   redId;
	private Integer   regionId;
	private Boolean   enabled;

	public TrapModel(){}
	
	public TrapModel(Integer number, String code, Integer eessId, Integer microrredId, Integer redId, Integer regionId, Boolean enabled){
		this.number      = number;
		this.code        = code;
		this.eessId      = eessId;
		this.microrredId = microrredId;
		this.redId       = redId;
		this.regionId    = regionId;
		this.enabled     = enabled;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getEessId() {
		return eessId;
	}

	public void setEessId(Integer eessId) {
		this.eessId = eessId;
	}

	public Integer getMicrorredId() {
		return microrredId;
	}

	public void setMicrorredId(Integer microrredId) {
		this.microrredId = microrredId;
	}

	public Integer getRedId() {
		return redId;
	}

	public void setRedId(Integer redId) {
		this.redId = redId;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
}
