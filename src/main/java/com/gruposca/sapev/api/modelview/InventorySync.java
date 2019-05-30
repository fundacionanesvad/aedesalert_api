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

public class InventorySync{
	
	private String  uuid;
	private Integer    inspected;
	private Integer    focus;
	private Integer    treated;
	private Integer    packet;
	private Integer    destroyed;
	private Integer containerId;
	private String  visitUuid;
	private List<SampleSyncImpl> listSyncSample;
 
	public InventorySync(){}
	
	public InventorySync (String  uuid, int inspected, Integer focus, Integer treated, Integer packet, Integer destroyed, Integer containerId, String visitUuid, List<SampleSyncImpl> listSyncSample){
		this.uuid          = uuid;
		this.inspected     = inspected;
		this.focus         = focus;
		this.treated       = treated;
		this.packet        = packet;
		this.destroyed     = destroyed;
		this.containerId   = containerId;
		this.visitUuid     = visitUuid;
		this.listSyncSample = listSyncSample;		
	}

	public String getUuid() {return uuid;}
	public void setUuid(String uuid) {this.uuid = uuid;}

	public int getInspected() {return inspected;}
	public void setInspected(Integer inspected) {this.inspected = inspected;}

	public Integer getFocus() {return focus;}
	public void setFocus(Integer focus) {this.focus = focus;}

	public Integer getTreated() {return treated;}
	public void setTreated(Integer treated) {this.treated = treated;}

	public Integer getPacket() {return packet;}
	public void setPacket(Integer packet) {this.packet = packet;}

	public Integer getDestroyed() {return destroyed;}
	public void setDestroyed(Integer destroyed) {this.destroyed = destroyed;}

	public Integer getContainerId() {return containerId;}
	public void setContainerId(Integer containerId) {this.containerId = containerId;}

	public String getVisitUuid() {return visitUuid;}
	public void setVisitUuid(String visitUuid) {this.visitUuid = visitUuid;}

	public List<SampleSyncImpl> getListSyncSample() {return listSyncSample;}
	public void setListSyncSample(List<SampleSyncImpl> listSyncSample) {this.listSyncSample = listSyncSample;}


}
