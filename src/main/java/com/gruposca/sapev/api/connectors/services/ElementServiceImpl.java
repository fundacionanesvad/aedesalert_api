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

import com.gruposca.sapev.api.connectors.dao.impl.LabelDao;
import com.gruposca.sapev.api.connectors.dao.impl.LanguageDao;
import com.gruposca.sapev.api.connectors.dao.impl.TableElementsDao;
import com.gruposca.sapev.api.connectors.dao.impl.TableHeadersDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.model.Labels;
import com.gruposca.sapev.api.connectors.dao.model.TableElements;
import com.gruposca.sapev.api.connectors.dao.model.TableHeaders;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.modelview.Element;
import com.gruposca.sapev.api.modelview.ElementImpl;
import com.gruposca.sapev.api.modelview.LabelImpl;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.ElementService;

public class ElementServiceImpl extends AbsService implements ElementService{

	public ElementServiceImpl(AbsConnector connector) { super( connector); }

	@Override
	public Element getElement(Session session, Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Element element;	
		try{	
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			Users user = userManager.getUserByToken(session.getAuthorizationToken());	
			element = tableElementsManager.getElement(user.getProfiles(), id);						   
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETELEMENT +e.toString());
			return null;
		}finally{
			ctx.close();
		}		
		return element;
	}

	@Override
	public TableElements createElement(Session session, ElementImpl element) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		TableElements entityTableElements;
		
		try{	
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			TableElementsDao tableElementsManager = (TableElementsDao)  ctx.getBean("TableElementsDaoImpl");
			LabelDao labelManager = (LabelDao)  ctx.getBean("LabelDaoImpl");
			TableHeadersDao tableHeadersManager = (TableHeadersDao)  ctx.getBean("TableHeadersDaoImpl");
			LanguageDao languagesManager = (LanguageDao)  ctx.getBean("LanguageDaoImpl");
			Users user = userManager.getUserByToken(session.getAuthorizationToken());	
			TableHeaders tableHeaders = tableHeadersManager.find(element.getTableHeaderId());
			
			if(tableHeaders.isSystem() && user.getProfiles().getId() != ConfigurationHelper.getProfileAdminId()){
				return null;
			}else{
				
				Integer maxSort = tableElementsManager.getMaxSort(tableHeaders).intValue();				
				entityTableElements = new TableElements(maxSort+1,tableHeaders); 
				entityTableElements = tableElementsManager.save(entityTableElements);
				
				if(entityTableElements != null){					
					for(int i = 0; i < element.getLabels().size(); i++){						
						LabelImpl label = element.getLabels().get(i);
						Labels newLabel = new Labels(languagesManager.find(label.getLanguageId()), entityTableElements, label.getValue());
						newLabel = labelManager.save(newLabel);						
					}					
					return entityTableElements;		
					
				}else{
					return null;
				}				
			}						

		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_CREATEELEMENT +e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public TableElements updateElement(Session session,Integer id, ElementImpl element) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		
		try{	
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			TableElementsDao tableElementsManager = (TableElementsDao)  ctx.getBean("TableElementsDaoImpl");
			LabelDao labelManager = (LabelDao)  ctx.getBean("LabelDaoImpl");
			TableHeadersDao tableHeadersManager = (TableHeadersDao)  ctx.getBean("TableHeadersDaoImpl");
			LanguageDao languagesManager = (LanguageDao)  ctx.getBean("LanguageDaoImpl");	
			Users user = userManager.getUserByToken(session.getAuthorizationToken());	
			TableHeaders tableHeaders = tableHeadersManager.find(element.getTableHeaderId());
			if(tableHeaders.isSystem() && user.getProfiles().getId() != ConfigurationHelper.getProfileAdminId()){
				return null;
			}else{
				
				TableElements tableElements = tableElementsManager.find(id);				
				labelManager.deleteByElement(tableElements);
				for(int i = 0; i < element.getLabels().size(); i++){						
					LabelImpl label = element.getLabels().get(i);
					Labels newLabel = new Labels(languagesManager.find(label.getLanguageId()), tableElements, label.getValue());
					newLabel = labelManager.save(newLabel);						
				}
				return tableElements;		
			}						

		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_UPDATEELEMENT +e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public Boolean deleteElement(Session session, Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<TableElements> listElements = new ArrayList<TableElements>();
		Boolean stateDeleted = false;

		try{	
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			TableElementsDao tableElementsManager = (TableElementsDao)  ctx.getBean("TableElementsDaoImpl");
			TableHeadersDao tableHeadersManager = (TableHeadersDao)  ctx.getBean("TableHeadersDaoImpl");
			
			TableElements tableElements = tableElementsManager.find(id);		
			Users user = userManager.getUserByToken(session.getAuthorizationToken());	
			TableHeaders tableHeaders = tableHeadersManager.find(tableElements.getTableHeaders().getId());

			if(user.getProfiles().getId() == ConfigurationHelper.getProfileAdminId() || (user.getProfiles().getId() != ConfigurationHelper.getProfileAdminId() && !tableHeaders.isSystem())){
				try{
					listElements = tableElementsManager.getList(tableHeaders);					
					tableElementsManager.delete(tableElements);	
					stateDeleted = true;

					//Reordenar los elementos					
					this.reorderSortElements(listElements, tableElements);
					
				}catch (Exception ex){
				    logger.error(RestErrorImpl.METHOD_DELETEELEMENT +RestErrorImpl.ERROR_RESTRICCION_FK);
				}
			}							
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_DELETEELEMENT +e.toString());
		}finally{
			ctx.close();
		}	
		return stateDeleted;
	}	
	
	
	public void reorderSortElements(List<TableElements> listElements, TableElements elementDeleted){		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Integer cont = 0;
		try{
			TableElementsDao tableElementsManager = (TableElementsDao)  ctx.getBean("TableElementsDaoImpl");

			if(listElements.size() > 1 && elementDeleted.getSort() < listElements.size()){				
				cont = elementDeleted.getSort();				
				while (cont < listElements.size()){
					
					TableElements element = listElements.get(cont);
					element.setSort(element.getSort()-1);
					tableElementsManager.save(element);
					cont ++;
				}				
			}
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_REORDERSORTELEMENT +e.toString());
			
		}finally{
			ctx.close();
		}	
	}

	@Override
	public Boolean upElement(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<TableElements> listElements = new ArrayList<TableElements>();
		Boolean upStastus = false;

		try{	
			TableElementsDao tableElementsManager = (TableElementsDao)  ctx.getBean("TableElementsDaoImpl");
			TableHeadersDao tableHeadersManager = (TableHeadersDao)  ctx.getBean("TableHeadersDaoImpl");
			TableElements elementToUp = tableElementsManager.find(id);		
			TableHeaders tableHeaders = tableHeadersManager.find(elementToUp.getTableHeaders().getId());
			listElements = tableElementsManager.getList(tableHeaders);					

			if(listElements.size() > 1 && elementToUp.getSort() > 1){				
				TableElements elementToDown = listElements.get(elementToUp.getSort()-2);
				elementToDown.setSort(elementToDown.getSort()+1);
				elementToUp.setSort(elementToUp.getSort()-1);				
				tableElementsManager.save(elementToUp);
				tableElementsManager.save(elementToDown);				
				upStastus = true;
			}			
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_UPELEMENT +e.toString());
		}finally{
			ctx.close();
		}	
		return upStastus;
	}

	@Override
	public Boolean downElement(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<TableElements> listElements = new ArrayList<TableElements>();
		Boolean downStastus = false;

		try{	
			TableElementsDao tableElementsManager = (TableElementsDao)  ctx.getBean("TableElementsDaoImpl");
			TableHeadersDao tableHeadersManager = (TableHeadersDao)  ctx.getBean("TableHeadersDaoImpl");
			TableElements elementToDown = tableElementsManager.find(id);		
			TableHeaders tableHeaders = tableHeadersManager.find(elementToDown.getTableHeaders().getId());
			listElements = tableElementsManager.getList(tableHeaders);					

			if(listElements.size() > 1 && elementToDown.getSort() < listElements.size()){				
				TableElements elementToUp = listElements.get(elementToDown.getSort());
				elementToUp.setSort(elementToUp.getSort()-1);				
				elementToDown.setSort(elementToDown.getSort()+1);
				tableElementsManager.save(elementToUp);
				tableElementsManager.save(elementToDown);				
				downStastus = true;
			}
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_DOWNELEMENT +e.toString());
		}finally{
			ctx.close();
		}	
		return downStastus;
	}

	@Override
	public List<Element> getElementList(Session session) {

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<Element> listElements = new ArrayList<Element>();
		try{
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			TableElementsDao tableElementsManager = (TableElementsDao) ctx.getBean("TableElementsDaoImpl");
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			listElements = tableElementsManager.getList(user.getLanguages().getId());			
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_SYNC_GETPLAN + e.toString());
		    listElements = null;
		}finally{
			ctx.close();
		}	
		return listElements;		
	}
}
