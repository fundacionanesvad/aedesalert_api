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
import com.gruposca.sapev.api.connectors.dao.model.Larvicides;
import com.gruposca.sapev.api.modelview.Larvicide;
import com.gruposca.sapev.api.modelview.LarvicideList;
import com.gruposca.sapev.api.modelview.Session;

public interface LarvicideService {	
	
	List<LarvicideList> getLarvicideList(Session session);

	Larvicide getLarvicide(Integer id);
	
	Larvicides createLarvicide(Larvicide larvicide);
	
	Larvicides updateLarvicide(Integer id, Larvicide larvicide);
	
	Boolean deleteLarvicide(Integer id);	
	
}
