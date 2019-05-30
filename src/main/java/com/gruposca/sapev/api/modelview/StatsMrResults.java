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

public class StatsMrResults {

	private int programmed;
	private int inspected;
	private int closed;
	private int reluctant;
	private int abandoned;
	private int focus;
	private int containers;	
	
	public StatsMrResults(){};
	   
	public StatsMrResults(int programmed, int inspected, int closed, int reluctant, int abandoned, int focus, int containers){
		this.programmed = programmed;
		this.inspected = inspected;
		this.closed = closed;
		this.reluctant = reluctant;
		this.abandoned = abandoned;
		this.focus = focus;
		this.containers = containers;
	}

	public int getProgrammed() {
		return programmed;
	}

	public void setProgrammed(int programmed) {
		this.programmed = programmed;
	}

	public int getInspected() {
		return inspected;
	}

	public void setInspected(int inspected) {
		this.inspected = inspected;
	}

	public int getClosed() {
		return closed;
	}

	public void setClosed(int closed) {
		this.closed = closed;
	}

	public int getReluctant() {
		return reluctant;
	}

	public void setReluctant(int reluctant) {
		this.reluctant = reluctant;
	}

	public int getAbandoned() {
		return abandoned;
	}

	public void setAbandoned(int abandoned) {
		this.abandoned = abandoned;
	}

	public int getFocus() {
		return focus;
	}

	public void setFocus(int focus) {
		this.focus = focus;
	}

	public int getContainers() {
		return containers;
	}

	public void setContainers(int containers) {
		this.containers = containers;
	}	
}
