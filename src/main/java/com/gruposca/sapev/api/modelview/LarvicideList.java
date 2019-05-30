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

public class LarvicideList implements User {
	
	private Integer    id;
	private String     name;
	private String     unity;
	private BigDecimal dose;
	private String     doseName;
	private Integer    waterVolume;
	private boolean    enabled;
	private Integer    schedules;
	
	public LarvicideList(){}
	
	public LarvicideList(Integer id, String name, String unity, BigDecimal dose, String doseName, Integer waterVolume, boolean enabled, Integer schedules){
		this.id = id;
		this.name = name;
		this.unity = unity;
		this.dose = dose;
		this.doseName = doseName;
		this.waterVolume = waterVolume;
		this.enabled = enabled;
		this.schedules = schedules;
	}

	public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	public String getUnity() {return unity;}
	public void setUnity(String unity) {this.unity = unity;}

	public BigDecimal getDose() {return dose;}
	public void setDose(BigDecimal dose) {this.dose = dose;}	

	public boolean isEnabled() {return enabled;}
	public void setEnabled(boolean enabled) {this.enabled = enabled;}

	public Integer getSchedules() {return schedules;}
	public void setSchedules(Integer schedules) {this.schedules = schedules;}

	public String getDoseName() {return doseName;}
	public void setDoseName(String doseName) {this.doseName = doseName;}

	public Integer getWaterVolume() {return waterVolume;}
	public void setWaterVolume(Integer waterVolume) {this.waterVolume = waterVolume;	}

	
}
