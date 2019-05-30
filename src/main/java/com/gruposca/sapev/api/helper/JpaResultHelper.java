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
package com.gruposca.sapev.api.helper;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class JpaResultHelper {	
	
	public static <T> List<T> getResultListAndCast(Query q) {
		@SuppressWarnings("unchecked")
	    List<T> list = q.getResultList();
	    return list;
	}		
	
	
	public static <T> Object getSingleResultOrNull(Query query){
		@SuppressWarnings("unchecked")
        List<T> results = query.getResultList();
        if (results.isEmpty()) return null;
        else if (results.size() == 1) return results.get(0);
        throw new NoResultException();
	}
		
}
