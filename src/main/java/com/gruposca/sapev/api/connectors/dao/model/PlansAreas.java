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
package com.gruposca.sapev.api.connectors.dao.model;
// Generated 15-mar-2018 10:39:23 by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * PlansAreas generated by hbm2java
 */
@Entity
@Proxy(lazy = false)
@Table(name = "PlansAreas")
public class PlansAreas implements java.io.Serializable {

	private Integer id;
	private Areas areas;
	private Plans plans;
	private Integer scheduledHouses;
	private boolean substitute;
	private Integer pin;

	public PlansAreas() {
	}

	public PlansAreas(Areas areas, Plans plans, boolean substitute) {
		this.areas = areas;
		this.plans = plans;
		this.substitute = substitute;
	}

	public PlansAreas(Areas areas, Plans plans, Integer scheduledHouses, boolean substitute, Integer pin) {
		this.areas = areas;
		this.plans = plans;
		this.scheduledHouses = scheduledHouses;
		this.substitute = substitute;
		this.pin = pin;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "areaId", nullable = false)
	public Areas getAreas() {
		return this.areas;
	}

	public void setAreas(Areas areas) {
		this.areas = areas;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "planId", nullable = false)
	public Plans getPlans() {
		return this.plans;
	}

	public void setPlans(Plans plans) {
		this.plans = plans;
	}

	@Column(name = "scheduledHouses")
	public Integer getScheduledHouses() {
		return this.scheduledHouses;
	}

	public void setScheduledHouses(Integer scheduledHouses) {
		this.scheduledHouses = scheduledHouses;
	}

	@Column(name = "substitute", nullable = false)
	public boolean isSubstitute() {
		return this.substitute;
	}

	public void setSubstitute(boolean substitute) {
		this.substitute = substitute;
	}

	@Column(name = "pin")
	public Integer getPin() {
		return this.pin;
	}

	public void setPin(Integer pin) {
		this.pin = pin;
	}



}
