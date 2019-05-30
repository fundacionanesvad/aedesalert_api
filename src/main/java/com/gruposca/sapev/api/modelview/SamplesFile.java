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


public class SamplesFile {
	
	private String code;
	private String type;
	private String date;
	private String address;
	private String microrred;
	private String uuid;
	private String mz;
	private String sector;
	private String result;
	
	public SamplesFile(){}
	
	public SamplesFile(String code, String type, String date, String address, String microrred, String uuid, String mz, String sector, String result){
		this.code	   = code;
		this.date      = date;
		this.type      = type;
		this.address   = address;
		this.microrred = microrred;
		this.uuid      = uuid;
		this.mz        = mz;
		this.sector    = sector;
		this.result    = result;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMicrorred() {
		return microrred;
	}

	public void setMicrorred(String microrred) {
		this.microrred = microrred;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMz() {
		return mz;
	}

	public void setMz(String mz) {
		this.mz = mz;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}	
	
}
