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
 ******************************************************************************/
package com.gruposca.sapev.api.services;

import java.util.List;

import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.modelview.Element;
import com.gruposca.sapev.api.modelview.ElementImpl;
import com.gruposca.sapev.api.modelview.Session;

public interface ElementService {

	Element getElement(Session session, Integer id);
	
	TableElements createElement(Session session, ElementImpl element);
	
	TableElements updateElement(Session session, Integer id, ElementImpl element);
	
	Boolean deleteElement(Session session, Integer id);

	Boolean upElement(Integer id);
	
	Boolean downElement(Integer id);
	
	List<Element> getElementList(Session session);

}
