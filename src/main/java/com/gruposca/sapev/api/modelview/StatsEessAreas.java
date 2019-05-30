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

public class StatsEessAreas {

	private int id;
	private int parentId;
	private String name;
	private String coords;
	private StatsEessResults control;
	private StatsEessResults reconversion;	
	private StatsEessResults vigilance;
	
	public StatsEessAreas(){};
	   
	public StatsEessAreas(int id, int parentId, String name, String coords, StatsEessResults control, StatsEessResults reconversion, StatsEessResults vigilance){
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.coords = coords;
		this.control = control;
		this.reconversion = reconversion;
		this.vigilance = vigilance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoords() {
		return coords;
	}

	public void setCoords(String coords) {
		this.coords = coords;
	}

	public StatsEessResults getControl() {
		return control;
	}

	public void setControl(StatsEessResults control) {
		this.control = control;
	}

	public StatsEessResults getReconversion() {
		return reconversion;
	}

	public void setReconversion(StatsEessResults reconversion) {
		this.reconversion = reconversion;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public StatsEessResults getVigilance() {
		return vigilance;
	}

	public void setVigilance(StatsEessResults vigilance) {
		this.vigilance = vigilance;
	}
	
	
	
}
