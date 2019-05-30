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
import java.util.Date;

public class ReportExcel {
	
	private Integer    mAreaId;
	private BigDecimal mAllInspected;
	private BigDecimal mAllFocus;
	private BigDecimal mAllTreated;
	private BigDecimal mInspected401;
	private BigDecimal mFocus401;
	private BigDecimal mTreated401;
	private BigDecimal mDestroyed401;
	private BigDecimal mInspected402;
	private BigDecimal mFocus402;
	private BigDecimal mTreated402;
	private BigDecimal mDestroyed402;
	private BigDecimal mInspected403;
	private BigDecimal mFocus403;
	private BigDecimal mTreated403;
	private BigDecimal mDestroyed403;
	private BigDecimal mInspected404;
	private BigDecimal mFocus404;
	private BigDecimal mTreated404;
	private BigDecimal mDestroyed404;
	private BigDecimal mInspected405;
	private BigDecimal mFocus405;
	private BigDecimal mTreated405;
	private BigDecimal mDestroyed405;
	private BigDecimal mInspected406;
	private BigDecimal mFocus406;
	private BigDecimal mTreated406;
	private BigDecimal mDestroyed406;
	private BigDecimal mInspected407;
	private BigDecimal mFocus407;
	private BigDecimal mTreated407;
	private BigDecimal mDestroyed407;
	private BigDecimal mInspected408;
	private BigDecimal mFocus408;
	private BigDecimal mTreated408;
	private BigDecimal mDestroyed408;
	private BigDecimal mInspected409;
	private BigDecimal mFocus409;
	private BigDecimal mTreated409;
	private BigDecimal mDestroyed409;
	private BigDecimal mInspected410;
	private BigDecimal mFocus410;
	private BigDecimal mTreated410;
	private BigDecimal mDestroyed410;
	private String 	   mDate;
	private String     mAreaName;
	private BigDecimal mHouses;
	private BigDecimal mInspected;
	private BigDecimal mClosed;
	private BigDecimal mRenuente;
	private BigDecimal mAbandanada;
	private BigDecimal mLarvicide;
	private BigDecimal mFoco;
	private BigDecimal mTreated;
	private BigDecimal mDestroyed;
	private BigInteger mNumMz;
	private BigDecimal mPeople;
	private BigDecimal mScheduledHouses;
	private Date   mStartDate;
	private Date   mFinishDate;

	public ReportExcel(){}
	
	public ReportExcel (Integer areaId, String date, String areaName, BigDecimal foco, BigDecimal treated, BigDecimal destroyed, BigDecimal houses, BigDecimal inspected, BigDecimal closed, 
			BigDecimal renuente, BigDecimal abandanada, BigDecimal larvicide, BigDecimal people, BigInteger numMz, BigDecimal scheduledHouses, 
			BigDecimal allInspected, BigDecimal allFocus,	BigDecimal allTreated, BigDecimal inspected401, BigDecimal focus401,
			BigDecimal treated401, BigDecimal destroyed401, BigDecimal inspected402, BigDecimal focus402, BigDecimal treated402, BigDecimal destroyed402, BigDecimal inspected403, BigDecimal focus403,
			BigDecimal treated403, BigDecimal destroyed403, BigDecimal inspected404,	BigDecimal focus404, BigDecimal treated404, BigDecimal destroyed404, BigDecimal inspected405, BigDecimal focus405,
			BigDecimal treated405, BigDecimal destroyed405, BigDecimal inspected406, BigDecimal focus406, BigDecimal treated406, BigDecimal destroyed406,	BigDecimal inspected407, BigDecimal focus407,
			BigDecimal treated407,	BigDecimal destroyed407, BigDecimal inspected408,	BigDecimal focus408, BigDecimal treated408, BigDecimal destroyed408,	BigDecimal inspected409, BigDecimal focus409,
			BigDecimal treated409,	BigDecimal destroyed409, BigDecimal inspected410,	BigDecimal focus410, BigDecimal treated410, BigDecimal destroyed410, Date startDate, Date finishDate){
		
		
		mAreaId          = areaId;
		mAllInspected    = allInspected;
		mAllFocus        = allFocus;
		mAllTreated      = allTreated;
		mInspected401    = inspected401;
		mFocus401        = focus401;
		mTreated401      = treated401;
		mDestroyed401    = destroyed401;
		mInspected402    = inspected402;
		mFocus402        = focus402;
		mTreated402      = treated402;
		mDestroyed402    = destroyed402;
		mInspected403    = inspected403;
		mFocus403        = focus403;
		mTreated403      = treated403;
		mDestroyed403    = destroyed403;
		mInspected404    = inspected404;
		mFocus404        = focus404;
		mTreated404      = treated404;
		mDestroyed404    = destroyed404;
		mInspected405    = inspected405;
		mFocus405        = focus405;
		mTreated405      = treated405;
		mDestroyed405    = destroyed405;
		mInspected406    = inspected406;
		mFocus406        = focus406;
		mTreated406      = treated406;
		mDestroyed406    = destroyed406;
		mInspected407    = inspected407;
		mFocus407        = focus407;
		mTreated407      = treated407;
		mDestroyed407    = destroyed407;
		mInspected408    = inspected408;
		mFocus408        = focus408;
		mTreated408      = treated408;
		mDestroyed408    = destroyed408;
		mInspected409    = inspected409;
		mFocus409        = focus409;
		mTreated409      = treated409;
		mDestroyed409    = destroyed409;
		mInspected410    = inspected410;
		mFocus410        = focus410;
		mTreated410      = treated410;
		mDestroyed410    = destroyed410;		
		mDate		     = date;
		mAreaName	     = areaName;
		mHouses		     = houses;
		mInspected	     = inspected;
		mClosed          = closed;
		mRenuente        = renuente;
		mAbandanada      = abandanada;
		mLarvicide       = larvicide;
		mFoco            = foco;
		mTreated         = treated;
		mDestroyed       = destroyed;	
		mNumMz           = numMz;
		mPeople          = people;
		mScheduledHouses = scheduledHouses;
		mStartDate       = startDate;
		mFinishDate      = finishDate;
	}	

	public Integer getAreaId() {
		return mAreaId;
	}

	public void setAreaId(Integer mAreaId) {
		this.mAreaId = mAreaId;
	}

	public BigDecimal getAllInspected() {
		return mAllInspected;
	}

	public void setAllInspected(BigDecimal mAllInspected) {
		this.mAllInspected = mAllInspected;
	}

	public BigDecimal getAllFocus() {
		return mAllFocus;
	}

	public void setAllFocus(BigDecimal mAllFocus) {
		this.mAllFocus = mAllFocus;
	}

	public BigDecimal getAllTreated() {
		return mAllTreated;
	}

	public void setAllTreated(BigDecimal mAllTreated) {
		this.mAllTreated = mAllTreated;
	}

	public BigDecimal getInspected401() {
		return mInspected401;
	}

	public void setInspected401(BigDecimal mInspected401) {
		this.mInspected401 = mInspected401;
	}

	public BigDecimal getFocus401() {
		return mFocus401;
	}

	public void setFocus401(BigDecimal mFocus401) {
		this.mFocus401 = mFocus401;
	}

	public BigDecimal getTreated401() {
		return mTreated401;
	}

	public void setTreated401(BigDecimal mTreated401) {
		this.mTreated401 = mTreated401;
	}

	public BigDecimal getDestroyed401() {
		return mDestroyed401;
	}

	public void setDestroyed401(BigDecimal mDestroyed401) {
		this.mDestroyed401 = mDestroyed401;
	}

	public BigDecimal getInspected402() {
		return mInspected402;
	}

	public void setInspected402(BigDecimal mInspected402) {
		this.mInspected402 = mInspected402;
	}

	public BigDecimal getFocus402() {
		return mFocus402;
	}

	public void setFocus402(BigDecimal mFocus402) {
		this.mFocus402 = mFocus402;
	}

	public BigDecimal getTreated402() {
		return mTreated402;
	}

	public void setTreated402(BigDecimal mTreated402) {
		this.mTreated402 = mTreated402;
	}

	public BigDecimal getDestroyed402() {
		return mDestroyed402;
	}

	public void setDestroyed402(BigDecimal mDestroyed402) {
		this.mDestroyed402 = mDestroyed402;
	}

	public BigDecimal getInspected403() {
		return mInspected403;
	}

	public void setInspected403(BigDecimal mInspected403) {
		this.mInspected403 = mInspected403;
	}

	public BigDecimal getFocus403() {
		return mFocus403;
	}

	public void setFocus403(BigDecimal mFocus403) {
		this.mFocus403 = mFocus403;
	}

	public BigDecimal getTreated403() {
		return mTreated403;
	}

	public void setTreated403(BigDecimal mTreated403) {
		this.mTreated403 = mTreated403;
	}

	public BigDecimal getDestroyed403() {
		return mDestroyed403;
	}

	public void setDestroyed403(BigDecimal mDestroyed403) {
		this.mDestroyed403 = mDestroyed403;
	}

	public BigDecimal getInspected404() {
		return mInspected404;
	}

	public void setInspected404(BigDecimal mInspected404) {
		this.mInspected404 = mInspected404;
	}

	public BigDecimal getFocus404() {
		return mFocus404;
	}

	public void setFocus404(BigDecimal mFocus404) {
		this.mFocus404 = mFocus404;
	}

	public BigDecimal getTreated404() {
		return mTreated404;
	}

	public void setTreated404(BigDecimal mTreated404) {
		this.mTreated404 = mTreated404;
	}

	public BigDecimal getDestroyed404() {
		return mDestroyed404;
	}

	public void setDestroyed404(BigDecimal mDestroyed404) {
		this.mDestroyed404 = mDestroyed404;
	}

	public BigDecimal getInspected405() {
		return mInspected405;
	}

	public void setInspected405(BigDecimal mInspected405) {
		this.mInspected405 = mInspected405;
	}

	public BigDecimal getFocus405() {
		return mFocus405;
	}

	public void setFocus405(BigDecimal mFocus405) {
		this.mFocus405 = mFocus405;
	}

	public BigDecimal getTreated405() {
		return mTreated405;
	}

	public void setTreated405(BigDecimal mTreated405) {
		this.mTreated405 = mTreated405;
	}

	public BigDecimal getDestroyed405() {
		return mDestroyed405;
	}

	public void setDestroyed405(BigDecimal mDestroyed405) {
		this.mDestroyed405 = mDestroyed405;
	}

	public BigDecimal getInspected406() {
		return mInspected406;
	}

	public void setInspected406(BigDecimal mInspected406) {
		this.mInspected406 = mInspected406;
	}

	public BigDecimal getFocus406() {
		return mFocus406;
	}

	public void setFocus406(BigDecimal mFocus406) {
		this.mFocus406 = mFocus406;
	}

	public BigDecimal getTreated406() {
		return mTreated406;
	}

	public void setTreated406(BigDecimal mTreated406) {
		this.mTreated406 = mTreated406;
	}

	public BigDecimal getDestroyed406() {
		return mDestroyed406;
	}

	public void setDestroyed406(BigDecimal mDestroyed406) {
		this.mDestroyed406 = mDestroyed406;
	}

	public BigDecimal getInspected407() {
		return mInspected407;
	}

	public void setInspected407(BigDecimal mInspected407) {
		this.mInspected407 = mInspected407;
	}

	public BigDecimal getFocus407() {
		return mFocus407;
	}

	public void setFocus407(BigDecimal mFocus407) {
		this.mFocus407 = mFocus407;
	}

	public BigDecimal getTreated407() {
		return mTreated407;
	}

	public void setTreated407(BigDecimal mTreated407) {
		this.mTreated407 = mTreated407;
	}

	public BigDecimal getDestroyed407() {
		return mDestroyed407;
	}

	public void setDestroyed407(BigDecimal mDestroyed407) {
		this.mDestroyed407 = mDestroyed407;
	}

	public BigDecimal getInspected408() {
		return mInspected408;
	}

	public void setInspected408(BigDecimal mInspected408) {
		this.mInspected408 = mInspected408;
	}

	public BigDecimal getFocus408() {
		return mFocus408;
	}

	public void setFocus408(BigDecimal mFocus408) {
		this.mFocus408 = mFocus408;
	}

	public BigDecimal getTreated408() {
		return mTreated408;
	}

	public void setTreated408(BigDecimal mTreated408) {
		this.mTreated408 = mTreated408;
	}

	public BigDecimal getDestroyed408() {
		return mDestroyed408;
	}

	public void setDestroyed408(BigDecimal mDestroyed408) {
		this.mDestroyed408 = mDestroyed408;
	}

	public BigDecimal getInspected409() {
		return mInspected409;
	}

	public void setInspected409(BigDecimal mInspected409) {
		this.mInspected409 = mInspected409;
	}

	public BigDecimal getFocus409() {
		return mFocus409;
	}

	public void setFocus409(BigDecimal mFocus409) {
		this.mFocus409 = mFocus409;
	}

	public BigDecimal getTreated409() {
		return mTreated409;
	}

	public void setTreated409(BigDecimal mTreated409) {
		this.mTreated409 = mTreated409;
	}

	public BigDecimal getDestroyed409() {
		return mDestroyed409;
	}

	public void setDestroyed409(BigDecimal mDestroyed409) {
		this.mDestroyed409 = mDestroyed409;
	}

	public BigDecimal getInspected410() {
		return mInspected410;
	}

	public void setInspected410(BigDecimal mInspected410) {
		this.mInspected410 = mInspected410;
	}

	public BigDecimal getFocus410() {
		return mFocus410;
	}

	public void setFocus410(BigDecimal mFocus410) {
		this.mFocus410 = mFocus410;
	}

	public BigDecimal getTreated410() {
		return mTreated410;
	}

	public void setTreated410(BigDecimal mTreated410) {
		this.mTreated410 = mTreated410;
	}

	public BigDecimal getDestroyed410() {
		return mDestroyed410;
	}

	public void setDestroyed410(BigDecimal mDestroyed410) {
		this.mDestroyed410 = mDestroyed410;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String mDate) {
		this.mDate = mDate;
	}

	public String getAreaName() {
		return mAreaName;
	}

	public void setAreaName(String mAreaName) {
		this.mAreaName = mAreaName;
	}

	public BigDecimal getHouses() {
		return mHouses;
	}

	public void setHouses(BigDecimal mHouses) {
		this.mHouses = mHouses;
	}

	public BigDecimal getInspected() {
		return mInspected;
	}

	public void setInspected(BigDecimal mInspected) {
		this.mInspected = mInspected;
	}

	public BigDecimal getClosed() {
		return mClosed;
	}

	public void setClosed(BigDecimal mClosed) {
		this.mClosed = mClosed;
	}

	public BigDecimal getRenuente() {
		return mRenuente;
	}

	public void setRenuente(BigDecimal mRenuente) {
		this.mRenuente = mRenuente;
	}

	public BigDecimal getAbandanada() {
		return mAbandanada;
	}

	public void setAbandanada(BigDecimal mAbandanada) {
		this.mAbandanada = mAbandanada;
	}

	public BigDecimal getLarvicide() {
		return mLarvicide;
	}

	public void setLarvicide(BigDecimal mLarvicide) {
		this.mLarvicide = mLarvicide;
	}

	public BigDecimal getFoco() {
		return mFoco;
	}

	public void setFoco(BigDecimal mFoco) {
		this.mFoco = mFoco;
	}

	public BigDecimal getTreated() {
		return mTreated;
	}

	public void setTreated(BigDecimal mTreated) {
		this.mTreated = mTreated;
	}

	public BigDecimal getDestroyed() {
		return mDestroyed;
	}

	public void setDestroyed(BigDecimal mDestroyed) {
		this.mDestroyed = mDestroyed;
	}

	public BigDecimal getPeople() {
		return mPeople;
	}

	public void setPeople(BigDecimal mPeople) {
		this.mPeople = mPeople;
	}

	public BigDecimal getScheduledHouses() {
		return mScheduledHouses;
	}

	public void setScheduledHouses(BigDecimal mScheduledHouses) {
		this.mScheduledHouses = mScheduledHouses;
	}

	public BigInteger getNumMz() {return mNumMz;}
	public void setNumMz(BigInteger mNumMz) {this.mNumMz = mNumMz;}

	public Date getStartDate() {
		return mStartDate;
	}

	public void setStartDate(Date mStartDate) {
		this.mStartDate = mStartDate;
	}

	public Date getFinishDate() {
		return mFinishDate;
	}

	public void setFinishDate(Date mFinishDate) {
		this.mFinishDate = mFinishDate;
	}	
	
	
}
