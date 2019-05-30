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

public class VisitSummaryInspector {

	private BigDecimal     inspected;
	private BigDecimal     closed;
	private BigDecimal     renuente;
	private BigDecimal     abandonada;
	private BigDecimal     treated;
	private BigDecimal     focus;

	
	public VisitSummaryInspector (){}
	
	public VisitSummaryInspector ( BigDecimal inspected, BigDecimal closed, BigDecimal renuente, BigDecimal abandonada, BigDecimal treated, BigDecimal focus){
		
		this.inspected   = inspected;
		this.closed      = closed;
		this.renuente    = renuente;
		this.abandonada  = abandonada;	
		this.treated     = treated;	
		this.focus       =  focus;	
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

	public BigDecimal getTreated() {
		return treated;
	}

	public void setTreated(BigDecimal treated) {
		this.treated = treated;
	}

	public BigDecimal getFocus() {
		return focus;
	}

	public void setFocus(BigDecimal focus) {
		this.focus = focus;
	}	
	
	
}
