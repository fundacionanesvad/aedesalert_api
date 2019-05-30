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
package com.gruposca.sapev.api.modelview;

import java.math.BigDecimal;
import java.util.List;


public class VisitSync {

	private String         uuid;
	private Long           date;
	private Byte           feverish;
	private BigDecimal     larvicide;
	private String         comments;
	private Integer        resultId;
	private Integer        planId;
	private String         houseUuid;
	private List<InventorySync> listSyncInventories;


	public VisitSync (){}
	
	public VisitSync (String uuid, Long date, Byte feverish, BigDecimal larvicide, String comments, Integer resultId, Integer planId, String houseUuid, List<InventorySync>listSyncInventories){
		
		this.uuid      = uuid;
		this.date      = date;
		this.feverish  = feverish;
		this.larvicide = larvicide;
		this.comments  = comments;
		this.resultId  = resultId;
		this.planId    = planId;
		this.houseUuid = houseUuid;	
		this.listSyncInventories = listSyncInventories;
	}


	public Long getDate() {return date;}
	public void setDate(Long date) {this.date = date;}

	public Byte getFeverish() {return feverish;}
	public void setFeverish(Byte feverish) {this.feverish = feverish;}

	public BigDecimal getLarvicide() {return larvicide;}
	public void setLarvicide(BigDecimal larvicide) {this.larvicide = larvicide;}

	public String getComments() {return comments;}
	public void setComments(String comments) {this.comments = comments;}

	public Integer getResultId() {	return resultId;}
	public void setResultId(Integer resultId) {this.resultId = resultId;}	

	public Integer getPlanId() {return planId;}
	public void setPlanId(Integer planId) {this.planId = planId;}

	public String getHouseUuid() {return houseUuid;}
	public void setHouseUuid(String houseUuid) {this.houseUuid = houseUuid;}

	public List<InventorySync> getListSyncInventories() {return listSyncInventories;}
	public void setListSyncInventories(List<InventorySync> listSyncInventories) {this.listSyncInventories = listSyncInventories;}

	public String getUuid() {return uuid;}
	public void setUuid(String uuid) {this.uuid = uuid;}	
	
	
}
