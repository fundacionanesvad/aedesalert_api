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
// Generated 09-feb-2018 11:08:42 by Hibernate Tools 4.3.5.Final

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
@Table(name = "Reports")
public class Reports implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Areas areas;
	private TableElements tableElements;
	private Users users;
	private Date date;
	private String name;
	private Date startDate;
	private Date finishDate;
	private int dataType;
	private Set<ReportInspections> reportinspectionses = new HashSet<ReportInspections>(0);

	public Reports() {
	}

	public Reports(Areas areas, TableElements tableelements, Users users, Date date, String name, Date startDate,
			Date finishDate, int dataType) {
		this.areas = areas;
		this.tableElements = tableelements;
		this.users = users;
		this.date = date;
		this.name = name;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.dataType = dataType;
	}

	public Reports(Areas areas, TableElements tableelements, Users users, Date date, String name, Date startDate,
			Date finishDate, int dataType, Set<ReportInspections> reportinspectionses) {
		this.areas = areas;
		this.tableElements = tableelements;
		this.users = users;
		this.date = date;
		this.name = name;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.dataType = dataType;
		this.reportinspectionses = reportinspectionses;
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
	@JoinColumn(name = "detailLevel", nullable = false)
	public TableElements getTableElements() {
		return this.tableElements;
	}

	public void setTableElements(TableElements tableelements) {
		this.tableElements = tableelements;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createUserId", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", nullable = false, length = 19)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startDate", nullable = false, length = 19)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "finishDate", nullable = false, length = 19)
	public Date getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	@Column(name = "dataType", nullable = false)
	public int getDataType() {
		return this.dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reports")
	public Set<ReportInspections> getReportinspectionses() {
		return this.reportinspectionses;
	}

	public void setReportinspectionses(Set<ReportInspections> reportinspectionses) {
		this.reportinspectionses = reportinspectionses;
	}

}
