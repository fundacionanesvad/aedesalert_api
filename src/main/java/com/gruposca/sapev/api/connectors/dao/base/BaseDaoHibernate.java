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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public abstract class BaseDaoHibernate<T extends Serializable, E> implements BaseDao<T, E> {
	
	@PersistenceContext 
	private  EntityManager entityManager;
	private Class<T> entityClass;
	
	
    public final static Logger logger = LoggerFactory.getLogger(BaseDaoHibernate.class);
	
	public BaseDaoHibernate(Class<T> entityClass) {
	        this.entityClass = entityClass;
	}
	public BaseDaoHibernate(){}
	
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
	
	// GENERIC METHOD 
	
	// Test Ok...
	@Override
	public T find( Long primaryKey) { 
        return this.entityManager.find( this.entityClass, primaryKey);
    }

	@Override
	public T find( Integer primaryKey) { 
        return this.entityManager.find( this.entityClass, primaryKey);
    }

	
	public T findById(E id) throws Exception{
		 return this.entityManager.find(this.entityClass, id);
	};
	
	
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria) throws Exception{
		
		return null;
	}
    
	
	
	@Override
	@Transactional
	public void insert(T transientInstance) throws Exception {
		try {
			this.entityManager.persist(transientInstance);
		} catch (final Exception e) {
	        throw e;
	    }
	}
	
	@Override
	@Transactional
	public T save(T transientInstance) throws Exception {
		try { 
			return this.entityManager.merge(transientInstance);
		} catch (final Exception e) {
	        throw e;
	    }
	}
	
	
	/*
	//Abstract
	public abstract List<T> findAll() throws Exception;
	
	//Abstract
	public abstract T findById(E id) throws Exception;
	*/

	public List<T> findAll() {
		@SuppressWarnings("unchecked")
		CriteriaQuery<T> cq = (CriteriaQuery<T>) this.entityManager.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return this.entityManager.createQuery(cq).getResultList();		
	}
		

	@Override
	@Transactional	
	public void delete(T persistentInstance) throws Exception{
		try {
			this.entityManager.remove(this.entityManager.merge(persistentInstance));
			//this.entityManager.remove(persistentInstance);
		} catch (final Exception e) {
	        throw e;
	    }
	}	 


	@Transactional	
	public int updateList(final String query){
	    final int changes = entityManager.createQuery(query).executeUpdate();
	    return changes;
	}
	
	
	/*
	 * Metodos abstractos a ser desarrollados en la clase implementadora
	 */
	//public abstract List<T> findAll() throws Exception;

	//public abstract T findById(E id) throws Exception;
	



	
	
	
	
}
