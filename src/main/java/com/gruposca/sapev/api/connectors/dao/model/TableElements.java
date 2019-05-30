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

// Generated 15-abr-2015 13:02:34 by Hibernate Tools 4.3.1

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

/**
 * TableElements generated by hbm2java
 */
@Entity
@Proxy(lazy = false)
@Table(name = "TableElements")
public class TableElements implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private TableHeaders tableHeaders;
	private Integer sort;
	private Set<Inspections> inspectionsesForTypeId = new HashSet<Inspections>(0);
	private Set<Inventories> inventorieses = new HashSet<Inventories>(0);
	private Set<Visits> visitses = new HashSet<Visits>(0);
	private Set<Labels> labelses = new HashSet<Labels>(0);
	private Set<Symptoms> symptomses = new HashSet<Symptoms>(0);
	private Set<Plans> planses = new HashSet<Plans>(0);
	private Set<Inspections> inspectionsesForStateId = new HashSet<Inspections>(0);

	public TableElements() {
	}

	public TableElements(Integer sort, TableHeaders tableHeaders) {
		this.sort = sort;
		this.tableHeaders = tableHeaders;
	}

	public TableElements(TableHeaders tableHeaders, Integer sort,
			Set<Inspections> inspectionsesForTypeId,
			Set<Inventories> inventorieses, Set<Visits> visitses,
			Set<Labels> labelses, Set<Symptoms> symptomses,
			Set<Inspections> inspectionsesForStateId) {
		this.tableHeaders = tableHeaders;
		this.sort = sort;
		this.inspectionsesForTypeId = inspectionsesForTypeId;
		this.inventorieses = inventorieses;
		this.visitses = visitses;
		this.labelses = labelses;
		this.symptomses = symptomses;
		this.inspectionsesForStateId = inspectionsesForStateId;
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
	@JoinColumn(name = "tableHeaderId", nullable = false)
	public TableHeaders getTableHeaders() {
		return this.tableHeaders;
	}

	public void setTableHeaders(TableHeaders tableHeaders) {
		this.tableHeaders = tableHeaders;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tableElementsByTypeId")
	public Set<Inspections> getInspectionsesForTypeId() {
		return this.inspectionsesForTypeId;
	}

	public void setInspectionsesForTypeId(
			Set<Inspections> inspectionsesForTypeId) {
		this.inspectionsesForTypeId = inspectionsesForTypeId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tableElements")
	public Set<Inventories> getInventorieses() {
		return this.inventorieses;
	}

	public void setInventorieses(Set<Inventories> inventorieses) {
		this.inventorieses = inventorieses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tableElements")
	public Set<Visits> getVisitses() {
		return this.visitses;
	}

	public void setVisitses(Set<Visits> visitses) {
		this.visitses = visitses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tableElements")
	public Set<Labels> getLabelses() {
		return this.labelses;
	}

	public void setLabelses(Set<Labels> labelses) {
		this.labelses = labelses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tableElements")
	public Set<Symptoms> getSymptomses() {
		return this.symptomses;
	}

	public void setSymptomses(Set<Symptoms> symptomses) {
		this.symptomses = symptomses;
	}	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tableElements")
	public Set<Plans> getPlanses() {
		return this.planses;
	}

	public void setPlanses(Set<Plans> planses) {
		this.planses = planses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tableElementsByStateId")
	public Set<Inspections> getInspectionsesForStateId() {
		return this.inspectionsesForStateId;
	}

	public void setInspectionsesForStateId(
			Set<Inspections> inspectionsesForStateId) {
		this.inspectionsesForStateId = inspectionsesForStateId;
	}

}
