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


public class SymptomSyncImpl {
	
	private String  uuid;
	private Integer symptomId;
	private String  personUuid;
	private String  visitUuid;	

	public SymptomSyncImpl(){}
	
	public SymptomSyncImpl (String uuid, Integer symptomId, String personUuid, String visitUuid){
		this.uuid = uuid;
		this.symptomId  = symptomId;
		this.personUuid = personUuid;
		this.visitUuid  = visitUuid;	
	}

	public String getUuid() {return uuid;}
	public void setUuid(String uuid) {this.uuid = uuid;}

	public Integer getSymptomId() {return symptomId;}
	public void setSymptomId(Integer symptomId) {this.symptomId = symptomId;}

	public String getPersonUuid() {return personUuid;}
	public void setPersonUuid(String personUuid) {this.personUuid = personUuid;}

	public String getVisitUuid() {return visitUuid;}
	public void setVisitUuid(String visitUuid) {this.visitUuid = visitUuid;}		
	
}
