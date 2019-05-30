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

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
@Table(name = "Schedules")
public class Schedules implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Areas areas;
	private TableElements tableElements;
	private Date startDate;
	private Date finishDate;
	private Integer reconversionScheduleId;
	private Larvicides larvicides;

	public Schedules() {
	}

	public Schedules(Areas areas, TableElements tableElements, Date startDate, Date finishDate, Larvicides larvicides) {
		this.areas = areas;
		this.tableElements = tableElements;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.larvicides = larvicides;
	}

	public Schedules(Areas areas, TableElements tableElements, Date startDate, Date finishDate,
			Integer reconversionScheduleId, Larvicides larvicides) {
		this.areas = areas;
		this.tableElements = tableElements;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.reconversionScheduleId = reconversionScheduleId;
		this.larvicides = larvicides;
	}

	public Schedules(Date startDate, Date finishDate, TableElements tableElements, Areas areas,
			Integer reconversionScheduleId, Larvicides larvicides) {
		this.areas = areas;
		this.tableElements = tableElements;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.reconversionScheduleId = reconversionScheduleId;
		this.larvicides = larvicides;
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
	@JoinColumn(name = "typeId", nullable = false)
	public TableElements getTableElements() {
		return this.tableElements;
	}

	public void setTableElements(TableElements tableElements) {
		this.tableElements = tableElements;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "startDate", nullable = false, length = 10)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "finishDate", nullable = false, length = 10)
	public Date getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	@Column(name = "reconversionScheduleId")
	public Integer getReconversionScheduleId() {
		return this.reconversionScheduleId;
	}

	public void setReconversionScheduleId(Integer reconversionScheduleId) {
		this.reconversionScheduleId = reconversionScheduleId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "larvicideId", nullable = true)
	public Larvicides getLarvicide() {
		return this.larvicides;
	}

	public void setLarvicide(Larvicides larvicides) {
		this.larvicides = larvicides;
	}

}
