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
package com.gruposca.sapev.api.services;

import java.util.List;
import com.gruposca.sapev.api.modelview.ParamFilterVisits;
import com.gruposca.sapev.api.modelview.Person;
import com.gruposca.sapev.api.modelview.Sample;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.Visit;
import com.gruposca.sapev.api.modelview.VisitInventory;
import com.gruposca.sapev.api.modelview.VisitList;

public interface VisitService {

	VisitList getVisitList(Session session , ParamFilterVisits paramFilterVisits);
	
	Visit getVisit(Session session,String uuid);
	
	VisitInventory getInventories(Session session, String uuid);
	
	List<Sample> getSamples(Session session, String uuid);
	
	List<Person> getPersons(Session session, String uuid);
	
	boolean deleteVisit(String uuid);	

	
}
