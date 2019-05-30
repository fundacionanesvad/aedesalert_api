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

import java.util.Calendar;

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
	@Table(name = "Alerts")
	public class Alerts implements java.io.Serializable {


		private static final long serialVersionUID = 1L;
		private Integer id;
		private Calendar date;
		private String link;
		private boolean closed;
		private TableElements tableElementsByTypeId;		
		private Users users;
		

		public Alerts() {
		}

		public Alerts(Integer id, Calendar date, String link, boolean closed, TableElements tableElementsByTypeId, Users users ) {
			this.id = id;
			this.date = date;
			this.link = link;
			this.closed = closed;
			this.tableElementsByTypeId = tableElementsByTypeId;
			this.users = users;
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
		@JoinColumn(name = "typeId", nullable = false)
		public TableElements getTableElementsByTypeId() {
			return this.tableElementsByTypeId;
		}

		public void setTableElementsByTypeId(TableElements tableElementsByTypeId) {
			this.tableElementsByTypeId = tableElementsByTypeId;
		}		
		
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name = "date", nullable = false, length = 10)
		public Calendar getDate() {
			return this.date;
		}

		public void setDate(Calendar date) {
			this.date = date;
		}

		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "userId")
		public Users getUsers() {
			return this.users;
		}

		public void setUsers(Users users) {
			this.users = users;
		}
		
		@Column(name = "closed", nullable = false)
		public boolean isClosed() {
			return this.closed;
		}

		public void setClosed(boolean closed) {
			this.closed = closed;
		}

		@Column(name = "link", nullable = false, length = 200)
		public String getLink() {
			return this.link;
		}

		public void setLink(String link) {
			this.link = link;
		}
		
	}
