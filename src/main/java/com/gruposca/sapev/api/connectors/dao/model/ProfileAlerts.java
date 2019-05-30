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


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * Profiles generated by hbm2java
 */
@Entity
@Proxy(lazy = false)
@Table(name = "ProfileAlerts")
public class ProfileAlerts implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Profiles profiles;
	private TableElements tableElementsByAlertTypeId;

	//private Set<Users> userses = new HashSet<Users>(0);
	//private Set<Permissions> permissionses = new HashSet<Permissions>(0);

	public ProfileAlerts() {
	}

	public ProfileAlerts(Profiles profiles,TableElements tableElementsByAlertTypeId) {
		this.profiles = profiles;
		this.tableElementsByAlertTypeId = tableElementsByAlertTypeId;
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
	@JoinColumn(name = "profileId", nullable = false)
	public Profiles getProfiles() {
		return this.profiles;
	}

	public void setProfiles(Profiles profiles) {
		this.profiles = profiles;
	}	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "alertTypeId", nullable = false)
	public TableElements getTableElementsByAlertTypeId() {
		return this.tableElementsByAlertTypeId;
	}

	public void setTableElementsByAlertTypeId(TableElements tableElementsByAlertTypeId) {
		this.tableElementsByAlertTypeId = tableElementsByAlertTypeId;
	}
	
}