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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;


@Entity
@Proxy(lazy = false)
@Table(name = "Modules")
public class Modules implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String verb;
	private String module;
	private String group;

	private Set<Permissions> permissionses = new HashSet<Permissions>(0);

	public Modules() {
	}

	public Modules(String verb, String module, String group) {
		this.verb = verb;
		this.module = module;
		this.group = group;
	}

	public Modules(String verb, String module, String group, Set<Permissions> permissionses) {
		this.verb = verb;
		this.module = module;
		this.group = group;
		this.permissionses = permissionses;
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

	@Column(name = "verb", nullable = false, length = 10)
	public String getVerb() {
		return this.verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	@Column(name = "module", nullable = false, length = 50)
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	@Column(name = "group", nullable = false, length = 50)
	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modules")
	public Set<Permissions> getPermissionses() {
		return this.permissionses;
	}

	public void setPermissionses(Set<Permissions> permissionses) {
		this.permissionses = permissionses;
	}

}
