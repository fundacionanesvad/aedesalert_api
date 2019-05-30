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

public class VisitImpl implements Visit{

	private Long           mDate;
	private Byte           mFeverish;
	private BigDecimal     mLarvicide;
	private String         mComments;
	private String         mResultName;
	private Integer        mInspectionId;
	private HouseVisitImpl mHouse; 	
	private Boolean        mReconverted; 	


	public VisitImpl() {}
	
	public VisitImpl (Long date, Byte feverish, BigDecimal larvicide, String comments, String resultName, Integer inspectionId, HouseVisitImpl house, Boolean reconverted ){
		mDate         = date;
		mFeverish     = feverish;
		mLarvicide    = larvicide;
		mComments     = comments;
		mResultName   = resultName;
		mInspectionId = inspectionId;
		mHouse        = house;	
		mReconverted  = reconverted;
	}

	public Long getDate() {return mDate;}
	public void setDate(Long mDate) {this.mDate = mDate;}

	public Byte getFeverish() {return mFeverish;}
	public void setFeverish(Byte mFeverish) {this.mFeverish = mFeverish;}

	public BigDecimal getLarvicide() {return mLarvicide;}
	public void setLarvicide(BigDecimal mLarvicide) {this.mLarvicide = mLarvicide;}

	public String getComments() {return mComments;}
	public void setComments(String mComments) {this.mComments = mComments;}

	public String getResultName() {return mResultName;}
	public void setResultname(String mResultName) {this.mResultName = mResultName;}

	public HouseVisitImpl getHouse() {return mHouse;}
	public void setHouse(HouseVisitImpl mHouse) {this.mHouse = mHouse;}

	public Integer getInspectionId() {return mInspectionId;}
	public void setInspectionId(Integer mInspectionId) {this.mInspectionId = mInspectionId;}

	public Boolean getReconverted() {return mReconverted;}
	public void setReconverted(Boolean mReconverted) {this.mReconverted = mReconverted;}

	
}
