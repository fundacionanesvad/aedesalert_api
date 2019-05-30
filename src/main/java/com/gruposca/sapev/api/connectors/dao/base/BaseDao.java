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
package com.gruposca.sapev.api.connectors.dao.base;


import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public interface BaseDao<T extends Serializable, E>  {

	// public void deleteAll(Collection<T> instances) throws Exception;

    public T save(T instance) throws Exception;
 
    // public void saveOrUpdateAll(Collection<T> instances) throws Exception;

    // public void saveOrUpdate(T instance) throws Exception;

    public void insert(T transientInstance) throws Exception;

    public void delete(T persistentInstance) throws Exception;

    // public List<T> findByExample(T instance) throws Exception; 

    // public List<T> findByQuery(String query) throws Exception;

    //public List<Map<String, Object>> findMapByQuery(String queryString) throws Exception;     
   
    //public List<T> findByCriteria(Criteria criteria) throws Exception; 
    
    //public T merge(T detachedInstance) throws Exception;

	public T find(Long primaryKey);
	
	public T find(Integer primaryKey);
	
	public T findById(E id) throws Exception;
	
	public List<T> findByCriteria(DetachedCriteria criteria) throws Exception;
    
	public List<T> findAll() throws Exception;
    
    
}

