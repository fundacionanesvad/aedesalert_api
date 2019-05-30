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
package com.gruposca.sapev.api.connectors.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.gruposca.sapev.api.connectors.dao.base.BaseDaoHibernate;
import com.gruposca.sapev.api.connectors.dao.model.Inventories;
import com.gruposca.sapev.api.connectors.dao.model.Visits;
import com.gruposca.sapev.api.helper.JpaResultHelper;
import com.gruposca.sapev.api.modelview.InspectorContainerData;

@Repository("InventoryDaoImpl")
public class InventoryDaoImpl extends BaseDaoHibernate<Inventories, String> implements InventoryDao {

	public InventoryDaoImpl() { super(Inventories.class); }

	@Override
	public List<Inventories> getInventories(UUID visitUuid) throws Exception {
		String sqlQuery = "SELECT INV FROM Inventories AS INV WHERE INV.visits.uuid = :visitUuid ORDER BY INV.tableElements.sort";
		List<Inventories> listInventories = new ArrayList<Inventories>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Inventories.class);
        q.setParameter("visitUuid", visitUuid);  
        listInventories = JpaResultHelper.getResultListAndCast(q);   
		return listInventories;	
	}

	@Override
	public Boolean focusInInventories(UUID visitUuid) throws Exception {		
		String sqlQuery = "SELECT INV FROM Inventories AS INV WHERE INV.visits.uuid = :visitUuid AND INV.focus = :focus";
		List<Inventories> listInventories = new ArrayList<Inventories>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Inventories.class);
        q.setParameter("visitUuid", visitUuid);  
        q.setParameter("focus", new Byte("1"));  
        listInventories = JpaResultHelper.getResultListAndCast(q);  		
		if(listInventories.size() > 0){
			return true;
		}else { 
			return false;		
		}	
	}

	@Override
	public InspectorContainerData getInspectorContainerData(UUID visitUuid) throws Exception {
		
		InspectorContainerData inspectorContainerData = new InspectorContainerData();
		String sqlQuery = "SELECT INV FROM Inventories AS INV WHERE INV.visits.uuid = :visitUuid ORDER BY INV.tableElements.id";
		List<Inventories> listInventories = new ArrayList<Inventories>();
	    Query q = this.getEntityManager().createQuery(sqlQuery, Inventories.class);
        q.setParameter("visitUuid", visitUuid);  
        listInventories = JpaResultHelper.getResultListAndCast(q);  		
		if(listInventories.size() > 0){
			for(int i = 0; i < listInventories.size(); i++) {
				Inventories inventories = listInventories.get(i);				

				if(inventories.getTableElements().getId() == 4001) {
					inspectorContainerData.setInspected4001(inventories.getInspected());
					inspectorContainerData.setFocus4001(inventories.getFocus());
					inspectorContainerData.setTreated4001(inventories.getTreated());
					inspectorContainerData.setDestroyed4001(inventories.getDestroyed());

				}
				if(inventories.getTableElements().getId() == 4002) {
					inspectorContainerData.setInspected4002(inventories.getInspected());
					inspectorContainerData.setFocus4002(inventories.getFocus());
					inspectorContainerData.setTreated4002(inventories.getTreated());
					inspectorContainerData.setDestroyed4002(inventories.getDestroyed());
}
				if(inventories.getTableElements().getId() == 4003) {
					inspectorContainerData.setInspected4003(inventories.getInspected());
					inspectorContainerData.setFocus4003(inventories.getFocus());
					inspectorContainerData.setTreated4003(inventories.getTreated());
					inspectorContainerData.setDestroyed4003(inventories.getDestroyed());
}
				if(inventories.getTableElements().getId() == 4004) {
					inspectorContainerData.setInspected4004(inventories.getInspected());
					inspectorContainerData.setFocus4004(inventories.getFocus());
					inspectorContainerData.setTreated4004(inventories.getTreated());
					inspectorContainerData.setDestroyed4004(inventories.getDestroyed());
}
				if(inventories.getTableElements().getId() == 4005) {
					inspectorContainerData.setInspected4005(inventories.getInspected());
					inspectorContainerData.setFocus4005(inventories.getFocus());
					inspectorContainerData.setTreated4005(inventories.getTreated());
					inspectorContainerData.setDestroyed4005(inventories.getDestroyed());
}
				if(inventories.getTableElements().getId() == 4006) {
					inspectorContainerData.setInspected4006(inventories.getInspected());
					inspectorContainerData.setFocus4006(inventories.getFocus());
					inspectorContainerData.setTreated4006(inventories.getTreated());
					inspectorContainerData.setDestroyed4006(inventories.getDestroyed());
}
				if(inventories.getTableElements().getId() == 4007) {
					inspectorContainerData.setInspected4007(inventories.getInspected());
					inspectorContainerData.setFocus4007(inventories.getFocus());
					inspectorContainerData.setTreated4007(inventories.getTreated());
					inspectorContainerData.setDestroyed4007(inventories.getDestroyed());
}
				if(inventories.getTableElements().getId() == 4008) {
					inspectorContainerData.setInspected4008(inventories.getInspected());
					inspectorContainerData.setFocus4008(inventories.getFocus());
					inspectorContainerData.setTreated4008(inventories.getTreated());
					inspectorContainerData.setDestroyed4008(inventories.getDestroyed());
}
				if(inventories.getTableElements().getId() == 4009) {
					inspectorContainerData.setInspected4009(inventories.getInspected());
					inspectorContainerData.setFocus4009(inventories.getFocus());
					inspectorContainerData.setTreated4009(inventories.getTreated());
					inspectorContainerData.setDestroyed4009(inventories.getDestroyed());
}
				if(inventories.getTableElements().getId() == 4010) {
					inspectorContainerData.setInspected4010(inventories.getInspected());
					inspectorContainerData.setFocus4010(inventories.getFocus());
					inspectorContainerData.setTreated4010(inventories.getTreated());
					inspectorContainerData.setDestroyed4010(inventories.getDestroyed());
}				
			}
			
		}	
		
		return inspectorContainerData;	
		
	}

	@Override
	public Inventories findByUUID(UUID uuid) throws Exception {
		String sqlQuery = "select I from Inventories as I where I.uuid = :uuid ";
	    Query q = this.getEntityManager().createQuery(sqlQuery, Inventories.class);
        q.setParameter("uuid", uuid);          
        Inventories inventory = (Inventories) JpaResultHelper.getSingleResultOrNull(q);
	    return inventory;
	}

	@Override
	@Transactional
	public Integer	deleteByVisit(Visits visit) throws Exception {
		String sqlQuery = "DELETE FROM Inventories AS I WHERE I.visits = :visits ";
	    Query q = this.getEntityManager().createQuery(sqlQuery);	    
        q.setParameter("visits", visit); 
        return q.executeUpdate();	
	}	

	
}
