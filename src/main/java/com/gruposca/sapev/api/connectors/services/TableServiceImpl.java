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
package com.gruposca.sapev.api.connectors.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gruposca.sapev.api.connectors.dao.impl.TableHeadersDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.model.Profiles;
import com.gruposca.sapev.api.connectors.dao.model.TableHeaders;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.modelview.Table;
import com.gruposca.sapev.api.modelview.TableImpl;
import com.gruposca.sapev.api.modelview.TableElementListImpl;
import com.gruposca.sapev.api.modelview.TableListImpl;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.TableService;

public class TableServiceImpl extends AbsService implements TableService{

	
	public TableServiceImpl(AbsConnector connector) { super( connector); }
	
	@Override
	public List<TableListImpl> getTableList(Session session) {		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Users user;
		List<TableListImpl> listTables  = new ArrayList<TableListImpl>();

		try{	
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			TableHeadersDao tableHeadersManager = (TableHeadersDao)  ctx.getBean("TableHeadersDaoImpl");
			user = userManager.getUserByToken(session.getAuthorizationToken());			
			listTables = tableHeadersManager.getListTables(user.getProfiles());
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETTABLELIST + e.toString());
		}finally{
			ctx.close();
		}
		return  listTables;
	}

	@Override
	public Table getTable(Session session, Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		TableHeaders entityTableHeaders = new TableHeaders();
		Users user;
		Profiles profile;
		Table table;
		
		try{	
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			TableHeadersDao tableHeadersManager = (TableHeadersDao)  ctx.getBean("TableHeadersDaoImpl");
			user = userManager.getUserByToken(session.getAuthorizationToken());			
			profile = user.getProfiles();
			entityTableHeaders = tableHeadersManager.find(id);				

			if(ConfigurationHelper.getProfileAdminId() != profile.getId() && entityTableHeaders.isSystem()){				
				table = null;
			}else{
				
				table = new TableImpl(entityTableHeaders.getName(), entityTableHeaders.isSystem());
			}
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETTABLE + e.toString());
			table=null;
		}finally{
			ctx.close();
		}
		return table;
	}

	@Override
	public TableHeaders createTable(TableImpl table) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		TableHeaders entityTableHeaders;
		try{	
			
			TableHeadersDao tableHeadersManager = (TableHeadersDao)  ctx.getBean("TableHeadersDaoImpl");
			entityTableHeaders = new TableHeaders(table.getName(), table.isSystem());
			entityTableHeaders = tableHeadersManager.save(entityTableHeaders);				
				
			if(entityTableHeaders != null){
				return entityTableHeaders;
			}else{
				return null;
			}					

		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_CREATETABLE + e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public TableHeaders updateTable(Integer id, TableImpl table) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		
		try{			
			TableHeadersDao tableHeadersManager = (TableHeadersDao)  ctx.getBean("TableHeadersDaoImpl");
			TableHeaders updateTableHeaders = tableHeadersManager.find(id);
			updateTableHeaders.setName(table.getName());
			updateTableHeaders.setSystem(table.isSystem());
			updateTableHeaders = tableHeadersManager.save(updateTableHeaders);
			return updateTableHeaders;					
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_UPDATETABLE + e.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}

	@Override
	public boolean deleteTable(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		boolean result = false;
		
		try{	
			TableHeadersDao tableHeadersManager = (TableHeadersDao)  ctx.getBean("TableHeadersDaoImpl");
			TableHeaders tableHeaders = tableHeadersManager.find(id);					
			if(tableHeaders != null){				
				Long numElements = tableHeadersManager.numElements(tableHeaders);				
				if(numElements == 0){				
					tableHeadersManager.delete(tableHeaders);
					result = true;
				}				
			}			
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_DELETETABLE + e.toString());
		}finally{
			ctx.close();
		}	
		return result;
	}

	@Override
	public List<TableElementListImpl> getTableElementList(Session session, Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<TableElementListImpl> listTableElements  = new ArrayList<TableElementListImpl>();
		Users user;

		try{	
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			TableHeadersDao tableHeadersManager = (TableHeadersDao)  ctx.getBean("TableHeadersDaoImpl");
			user = userManager.getUserByToken(session.getAuthorizationToken());			
			listTableElements = tableHeadersManager.getListTableElements(user, id);
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETTABLEELEMENTLIST + e.toString());
		}finally{
			ctx.close();
		}
		return  listTableElements;
	}
	
}
