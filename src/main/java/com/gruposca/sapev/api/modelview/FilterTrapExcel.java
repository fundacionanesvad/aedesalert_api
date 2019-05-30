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

public class FilterTrapExcel {

	private Integer year;
	private Integer microrredId;
	private Integer eessId;
	
	public FilterTrapExcel(){}
	
	public FilterTrapExcel(Integer year, Integer microrredId, Integer eessId){
		this.year = year;
		this.microrredId = microrredId;
		this.eessId = eessId;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMicrorredId() {
		return microrredId;
	}

	public void setMicrorredId(Integer microrredId) {
		this.microrredId = microrredId;
	}

	public Integer getEessId() {
		return eessId;
	}

	public void setEessId(Integer eessId) {
		this.eessId = eessId;
	}
	
}