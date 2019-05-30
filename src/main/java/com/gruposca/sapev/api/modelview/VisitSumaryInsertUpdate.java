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
import java.math.BigInteger;

public class VisitSumaryInsertUpdate {
	
	private BigInteger     houses;
	private BigDecimal     inspected;
	private BigDecimal     closed;
	private BigDecimal     renuente;
	private BigDecimal     abandonada;
	private BigDecimal     reconverted;	
	private BigDecimal     persons;
	private BigDecimal     feverish;
	private BigDecimal     larvicide;
	private Integer        areaId;
	private Integer		   planId;	
	private BigDecimal     closedReconverted;
	private BigDecimal     reluctantReconverted;
	private BigDecimal     abandonedReconverted;


	public VisitSumaryInsertUpdate (){}
	
	public VisitSumaryInsertUpdate (BigInteger houses, BigDecimal inspected, BigDecimal closed, BigDecimal renuente, BigDecimal abandonada, BigDecimal reconverted, BigDecimal persons, BigDecimal feverish, BigDecimal larvicide, BigDecimal closedReconverted, BigDecimal reluctantReconverted, BigDecimal abandonedReconverted, Integer areaId, Integer planId){
		
		this.houses      = houses;
		this.inspected   = inspected;
		this.closed      = closed;
		this.renuente    = renuente;
		this.abandonada  = abandonada;
		this.persons     = persons;
		this.feverish    = feverish;
		this.larvicide   = larvicide;
		this.areaId      = areaId;
		this.planId      = planId;
		this.reconverted = reconverted;
		this.closedReconverted    = closedReconverted;
		this.reluctantReconverted = reluctantReconverted;
		this.abandonedReconverted = abandonedReconverted;
	}

		
	public BigInteger getHouses() {
		return houses;
	}

	public void setHouses(BigInteger houses) {
		this.houses = houses;
	}

	public BigDecimal getInspected() {
		return inspected;
	}

	public void setInspected(BigDecimal inspected) {
		this.inspected = inspected;
	}

	public BigDecimal getClosed() {
		return closed;
	}

	public void setClosed(BigDecimal closed) {
		this.closed = closed;
	}

	public BigDecimal getRenuente() {
		return renuente;
	}

	public void setRenuente(BigDecimal renuente) {
		this.renuente = renuente;
	}

	public BigDecimal getAbandonada() {
		return abandonada;
	}

	public void setAbandonada(BigDecimal abandonada) {
		this.abandonada = abandonada;
	}	
	
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public BigDecimal getPersons() {
		return persons;
	}

	public void setPersons(BigDecimal persons) {
		this.persons = persons;
	}

	public BigDecimal getFeverish() {
		return feverish;
	}

	public void setFeverish(BigDecimal feverish) {
		this.feverish = feverish;
	}

	public BigDecimal getLarvicide() {
		return larvicide;
	}

	public void setLarvicide(BigDecimal larvicide) {
		this.larvicide = larvicide;
	}

	public BigDecimal getReconverted() {
		return reconverted;
	}

	public void setReconverted(BigDecimal reconverted) {
		this.reconverted = reconverted;
	}

	public BigDecimal getClosedReconverted() {
		return closedReconverted;
	}

	public void setClosedReconverted(BigDecimal closedReconverted) {
		this.closedReconverted = closedReconverted;
	}

	public BigDecimal getReluctantReconverted() {
		return reluctantReconverted;
	}

	public void setReluctantReconverted(BigDecimal reluctantReconverted) {
		this.reluctantReconverted = reluctantReconverted;
	}

	public BigDecimal getAbandonedReconverted() {
		return abandonedReconverted;
	}

	public void setAbandonedReconverted(BigDecimal abandonedReconverted) {
		this.abandonedReconverted = abandonedReconverted;
	}	
	
}
