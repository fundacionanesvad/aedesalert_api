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

import java.util.List;

public class StatsEessResults {

	private int inspected;
	private int closed;
	private int reluctant;
	private int abandoned;
	private int focus;
	private int programmed;
	private int inspectedContainers;
	private int focusContainers;
	private List<StatsContainers> containers;	
	
	public StatsEessResults(){};
	   
	public StatsEessResults(int inspected, int closed, int reluctant, int abandoned, int focus, int programmed, int inspectedContainers, int focusContainers, List<StatsContainers> containers){
		this.inspected = inspected;
		this.closed = closed;
		this.reluctant = reluctant;
		this.abandoned = abandoned;
		this.focus = focus;
		this.programmed = programmed;
		this.inspectedContainers = inspectedContainers;
		this.focusContainers = focusContainers;
		this.containers = containers;
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

	public List<StatsContainers> getContainers() {
		return containers;
	}

	public void setContainers(List<StatsContainers> containers) {
		this.containers = containers;
	}

	public int getProgrammed() {
		return programmed;
	}

	public void setProgrammed(int programmed) {
		this.programmed = programmed;
	}

	public int getInspectedContainers() {
		return inspectedContainers;
	}

	public void setInspectedContainers(int inspectedContainers) {
		this.inspectedContainers = inspectedContainers;
	}

	public int getFocusContainers() {
		return focusContainers;
	}

	public void setFocusContainers(int focusContainers) {
		this.focusContainers = focusContainers;
	}	
}
