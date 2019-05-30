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
 ******************************************************************************/package com.gruposca.sapev.api.connectors.dao.model;
// Generated 20-mar-2018 10:47:30 by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;


@Entity
@Proxy(lazy = false)
@Table(name = "Traps")
public class Traps implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Areas areas;
	private String code;
	private int number;
	private boolean enabled;
	private Set<TrapLocations> trapLocationses = new HashSet<TrapLocations>(0);

	
	public Traps() {
	}

	public Traps(Areas areas, String code, int number, boolean enabled) {
		this.areas = areas;
		this.code = code;
		this.number = number;
		this.enabled = enabled;
	}

	public Traps(Areas areas, String code, int number, boolean enabled, Set<TrapLocations> trapLocationses) {
		this.areas = areas;
		this.code = code;
		this.number = number;
		this.enabled = enabled;
		this.trapLocationses = trapLocationses;
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

	@Column(name = "code", nullable = false, length = 50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "number", nullable = false)
	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "traps")
	public Set<TrapLocations> getTrapLocationses() {
		return this.trapLocationses;
	}

	public void setTrapLocationses(Set<TrapLocations> trapLocationses) {
		this.trapLocationses = trapLocationses;
	}

}
