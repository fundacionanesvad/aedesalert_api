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
/*******************************************************************************
 * Aedes Alert, Support to collect data to combat dengue
 * Copyright (C) 2017  Anesvad
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
 ******************************************************************************/package com.gruposca.sapev.api.modelview;

public class InspectorReportTotals {

	private Integer totalFocus;
	private Integer totalInspected;
	private Integer totalTreated;
	private Integer totalDestroyed;
	private Integer numberRow;
	
	public InspectorReportTotals() {
	}

	public InspectorReportTotals(Integer totalFocus, Integer totalInspected,Integer totalTreated,Integer totalDestroyed,Integer numberRow) {
		this.totalFocus = totalFocus;
		this.totalInspected = totalInspected;
		this.totalTreated = totalTreated;
		this.totalDestroyed = totalDestroyed;
		this.numberRow = numberRow;
	}

	public Integer getTotalFocus() {
		return totalFocus;
	}

	public void setTotalFocus(Integer totalFocus) {
		this.totalFocus = totalFocus;
	}

	public Integer getTotalInspected() {
		return totalInspected;
	}

	public void setTotalInspected(Integer totalInspected) {
		this.totalInspected = totalInspected;
	}

	public Integer getTotalTreated() {
		return totalTreated;
	}

	public void setTotalTreated(Integer totalTreated) {
		this.totalTreated = totalTreated;
	}

	public Integer getTotalDestroyed() {
		return totalDestroyed;
	}

	public void setTotalDestroyed(Integer totalDestroyed) {
		this.totalDestroyed = totalDestroyed;
	}

	public Integer getNumberRow() {
		return numberRow;
	}

	public void setNumberRow(Integer numberRow) {
		this.numberRow = numberRow;
	}
}