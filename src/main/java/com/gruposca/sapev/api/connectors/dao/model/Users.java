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
 ******************************************************************************/
package com.gruposca.sapev.api.connectors.dao.model;

// Generated 15-abr-2015 13:02:34 by Hibernate Tools 4.3.1

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;


@Entity
@Proxy(lazy = false)
@Table(name = "Users")
public class Users implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Languages languages;
	private Profiles profiles;
	private String login;
	private String password;
	private String name;
	private String email;
	private boolean enabled;
	private String token;
	private Calendar tokenExpiration;
	private Areas areas;
	private Integer loginErrors;
	private String urlToken;
	private Calendar urlTokenExpiration;
	//private Set<Visits> visitses = new HashSet<Visits>(0);
	//private Set<Plans> planses = new HashSet<Plans>(0);

	public Users() {
	}

	public Users(Languages languages, Profiles profiles, String login,
			String password, String name, String email, boolean enabled, Areas areas, Integer loginErrors) {
		this.languages = languages;
		this.profiles = profiles;
		this.login = login;
		this.password = password;
		this.name = name;
		this.email = email;
		this.enabled = enabled;
		this.areas = areas;
		this.loginErrors = loginErrors;
	}

	/*public Users(Languages languages, Profiles profiles, String login,
			String password, String name, String email, boolean enabled,
			boolean alerts, String token, Calendar tokenExpiration,
			Set<Visits> visitses, Set<Plans> planses) {
		this.languages = languages;
		this.profiles = profiles;
		this.login = login;
		this.password = password;
		this.name = name;
		this.email = email;
		this.enabled = enabled;
		this.alerts = alerts;
		this.token = token;
		this.tokenExpiration = tokenExpiration;
		this.visitses = visitses;
		this.planses = planses;
	}*/

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
	@JoinColumn(name = "languageId", nullable = false)
	public Languages getLanguages() {
		return this.languages;
	}

	public void setLanguages(Languages languages) {
		this.languages = languages;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profileId", nullable = false)
	public Profiles getProfiles() {
		return this.profiles;
	}

	public void setProfiles(Profiles profiles) {
		this.profiles = profiles;
	}

	@Column(name = "login", nullable = false, length = 50)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "password", nullable = false, length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "name", nullable = false, length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email", length = 200)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}	

	@Column(name = "token", length = 50)
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tokenExpiration", length = 19)
	public Calendar getTokenExpiration() {
		return this.tokenExpiration;
	}

	public void setTokenExpiration(Calendar tokenExpiration) {
		this.tokenExpiration = tokenExpiration;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "areaId", nullable = false)
	public Areas getAreas() {
		return this.areas;
	}

	public void setAreas(Areas areas) {
		this.areas = areas;
	}
	
	@Column(name = "loginErrors")
	public Integer getLoginErrors() {
		return this.loginErrors;
	}

	public void setLoginErrors(Integer loginErrors) {
		this.loginErrors = loginErrors;
	}
	
	@Column(name = "urlToken", length = 50)
	public String getUrlToken() {
		return this.urlToken;
	}

	public void setUrlToken(String urlToken) {
		this.urlToken = urlToken;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "urlTokenExpiration", length = 19)
	public Calendar getUrlTokenExpiration() {
		return this.urlTokenExpiration;
	}

	public void setUrlTokenExpiration(Calendar urlTokenExpiration) {
		this.urlTokenExpiration = urlTokenExpiration;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<Visits> getVisitses() {
		return this.visitses;
	}

	public void setVisitses(Set<Visits> visitses) {
		this.visitses = visitses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<Plans> getPlanses() {
		return this.planses;
	}

	public void setPlanses(Set<Plans> planses) {
		this.planses = planses;
	}*/

}
