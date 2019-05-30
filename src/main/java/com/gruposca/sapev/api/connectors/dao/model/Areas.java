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

// Generated 25-jun-2015 17:37:36 by Hibernate Tools 4.3.1

import java.math.BigDecimal;
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
@Table(name = "Areas")

public class Areas implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Areas areas;
	private String code;
	private String name;
	private Integer houses;
	private String coords;
	private boolean leaf;
	private TableElements tableElementsByTypeId;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private Set<Scenes> sceneses = new HashSet<Scenes>(0);
	private Set<Inspections> inspectionses = new HashSet<Inspections>(0);
	private Set<PlansAreas> plansAreases = new HashSet<PlansAreas>(0);
	private Set<Areas> areases = new HashSet<Areas>(0);
	private Set<Houses> houseses = new HashSet<Houses>(0);
	private Set<Reports> reportses = new HashSet<Reports>(0);
	private Set<Users> userses = new HashSet<Users>(0);
	private Set<Febriles> febrileses = new HashSet<Febriles>(0);
	private Set<Schedules> scheduleses = new HashSet<Schedules>(0);

	public Areas() {
	}

	public Areas(String code, String name, boolean leaf) {
		this.code = code;
		this.name = name;
		this.leaf = leaf;
	}

	public Areas(Areas areas, String code, String name, Integer houses,
			String coords, boolean leaf, Set<Scenes> sceneses, 
			TableElements tableElementsByTypeId, BigDecimal latitude, BigDecimal longitude,
			Set<Inspections> inspectionses, Set<PlansAreas> plansAreases,
			Set<Areas> areases, Set<Houses> houseses) {
		this.areas = areas;
		this.code = code;
		this.name = name;
		this.houses = houses;
		this.coords = coords;
		this.leaf = leaf;
		this.tableElementsByTypeId = tableElementsByTypeId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sceneses = sceneses;
		this.inspectionses = inspectionses;
		this.plansAreases = plansAreases;
		this.areases = areases;
		this.houseses = houseses;
	}

	public Areas( String code, String name, Integer houses,
			String coords, boolean leaf, Areas areas,
			TableElements tableElementsByTypeId, BigDecimal latitude, BigDecimal longitude) {
		this.areas = areas;
		this.code = code;
		this.name = name;
		this.houses = houses;
		this.coords = coords;	
		this.leaf = leaf;
		this.tableElementsByTypeId = tableElementsByTypeId;
		this.latitude = latitude;
		this.longitude = longitude;
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
	@JoinColumn(name = "parentId")
	public Areas getAreas() {
		return this.areas;
	}

	public void setAreas(Areas areas) {
		this.areas = areas;
	}

	@Column(name = "code", nullable = false, length = 10)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", nullable = false, length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "houses")
	public Integer getHouses() {
		return this.houses;
	}

	public void setHouses(Integer houses) {
		this.houses = houses;
	}

	@Column(name = "coords", length = 5000)
	public String getCoords() {
		return this.coords;
	}

	public void setCoords(String coords) {
		this.coords = coords;
	}

	@Column(name = "leaf", nullable = false)
	public boolean isLeaf() {
		return this.leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "areas")
	public Set<Scenes> getSceneses() {
		return this.sceneses;
	}

	public void setSceneses(Set<Scenes> sceneses) {
		this.sceneses = sceneses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "areas")
	public Set<Inspections> getInspectionses() {
		return this.inspectionses;
	}

	public void setInspectionses(Set<Inspections> inspectionses) {
		this.inspectionses = inspectionses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "areas")
	public Set<PlansAreas> getPlansAreases() {
		return this.plansAreases;
	}

	public void setPlansAreases(Set<PlansAreas> plansAreases) {
		this.plansAreases = plansAreases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "areas")
	public Set<Areas> getAreases() {
		return this.areases;
	}

	public void setAreases(Set<Areas> areases) {
		this.areases = areases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "areas")
	public Set<Houses> getHouseses() {
		return this.houseses;
	}

	public void setHouseses(Set<Houses> houseses) {
		this.houseses = houseses;
	}	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeId", nullable = false)
	public TableElements getTableElementsByTypeId() {
		return this.tableElementsByTypeId;
	}

	public void setTableElementsByTypeId(TableElements tableElementsByTypeId) {
		this.tableElementsByTypeId = tableElementsByTypeId;
	}
	
	@Column(name = "latitude", precision = 9, scale = 6)
	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	@Column(name = "longitude", precision = 9, scale = 6)
	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "areas")
	public Set<Users> getUserses() {
		return this.userses;
	}

	public void setUserses(	Set<Users> userses) {
		this.userses = userses;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "areas")
	public Set<Febriles> getFebrileses() {
		return this.febrileses;
	}

	public void setFebrileses(	Set<Febriles> febrileses) {
		this.febrileses = febrileses;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "areas")
	public Set<Schedules> getScheduleses() {
		return this.scheduleses;
	}

	public void setScheduleses(	Set<Schedules> scheduleses) {
		this.scheduleses = scheduleses;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "areas")
	public Set<Reports> getReports() {
		return this.reportses;
	}

	public void setReports(	Set<Reports> reportses) {
		this.reportses = reportses;
	}
	
	
	
}
