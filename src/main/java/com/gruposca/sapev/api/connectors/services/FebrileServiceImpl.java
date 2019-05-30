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
package com.gruposca.sapev.api.connectors.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.gruposca.sapev.api.connectors.dao.impl.AlertDao;
import com.gruposca.sapev.api.connectors.dao.impl.AreaDao;
import com.gruposca.sapev.api.connectors.dao.impl.FebrileDao;
import com.gruposca.sapev.api.connectors.dao.impl.ScenesDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Febriles;
import com.gruposca.sapev.api.connectors.dao.model.Scenes;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.helper.ConfigurationHelper;
import com.gruposca.sapev.api.modelview.Febrile;
import com.gruposca.sapev.api.modelview.FebrileList;
import com.gruposca.sapev.api.modelview.ParamFilterFebriles;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.FebrileService;

public class FebrileServiceImpl extends AbsService implements FebrileService{
	
	public FebrileServiceImpl(AbsConnector connector) { super( connector); }

	@Override
	public FebrileList getFebrileList(Session session, ParamFilterFebriles paramFilterFebriles) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		FebrileList febrileList = new FebrileList();
		try{
			FebrileDao febrileManager = (FebrileDao) ctx.getBean("FebrileDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			Users user = userManager.getUserByToken(session.getAuthorizationToken());						
			febrileList = febrileManager.getListByAreaUser(user, paramFilterFebriles);
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLISTFEBRILES + e.toString());
		    febrileList = null;
			
		}finally{
			ctx.close();
		}
		
		return febrileList;
	}

	@Override
	public Febrile getFebrile(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Febriles entityFebrile = new Febriles();
		Febrile febrile = null;		
		try{			
			FebrileDao febrileManager = (FebrileDao) ctx.getBean("FebrileDaoImpl");
			entityFebrile = febrileManager.find(id);				
			if(entityFebrile.getId() != null){				
				febrile = new Febrile(entityFebrile.getDate().getTime(), entityFebrile.getAreas().getId(), entityFebrile.getAreas().getAreas().getId(), entityFebrile.getAreas().getAreas().getAreas().getId());
			}
			
		}catch(Exception ex){
		    logger.error(RestErrorImpl.METHOD_GETFEBRILE +ex.toString());
		}finally{
			ctx.close();
		}
		return febrile;
	}

	@Override
	public Febriles createFebrile(Febrile febrile) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		FebrileDao febrileManager = (FebrileDao) ctx.getBean("FebrileDaoImpl");
		AreaDao areaManager = (AreaDao)ctx.getBean("AreaDaoImpl");
		ScenesDao scenesManager = (ScenesDao)ctx.getBean("ScenesDaoImpl");
		Febriles entityFebriles;
		try{
			Areas area = areaManager.find(febrile.getEessId());			
			SimpleDateFormat converterUTC = new SimpleDateFormat();
			converterUTC.setTimeZone(TimeZone.getTimeZone("UTC"));		
			entityFebriles = new Febriles(new Date(febrile.getDate()), area);
			entityFebriles = febrileManager.save(entityFebriles);
			if(entityFebriles != null){
				
				//Hay que crear alerta nuevo febril y cambiar el nivel de los escenarios del area del febril y de todas las areas descendientes a 3				
				AlertServiceImpl alertServiceImpl = new AlertServiceImpl(getConnector());
				alertServiceImpl.generateNewFebrileAlert(ctx, entityFebriles);
				List<Scenes> listScenes = scenesManager.getListScenes(area.getId());
				for(int i=0; i< listScenes.size(); i++ ){
					Scenes scene = listScenes.get(i);					
					if(scene.getSceneLevel() < 3){
						scene.setSceneLevel((byte)3);
						scenesManager.save(scene);						
						alertServiceImpl.generateChangeSceneAlert(ctx, scene);
					}				
				}
				return entityFebriles;
				
			}else{
				return null;
			}				
		}catch (Exception ex){
		    logger.error(RestErrorImpl.METHOD_CREATEFEBRILE +ex.toString());
		    return null;
		
		}finally{
			ctx.close();
		}		
	}

	@Override
	public Febriles updateFebrile(Integer id, Febrile febrile) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		try{			
			FebrileDao febrileManager = (FebrileDao) ctx.getBean("FebrileDaoImpl");
			AreaDao areaManager = (AreaDao)ctx.getBean("AreaDaoImpl");
			
			Febriles updateFebrile = febrileManager.find(id);
			if(updateFebrile != null){
				Areas area = areaManager.find(febrile.getEessId());
				updateFebrile.setAreas(area);
				updateFebrile.setDate(new Date(febrile.getDate()));				
				updateFebrile = febrileManager.save(updateFebrile);	
			}
			
			return updateFebrile;
		
		}catch(Exception ex){
		    logger.error(RestErrorImpl.METHOD_UPDATEFEBRILE +ex.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}

	@Override
	public void deleteFebrile(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		FebrileDao febrileManager = (FebrileDao) ctx.getBean("FebrileDaoImpl");
		AlertDao alertManager = (AlertDao) ctx.getBean("AlertDaoImpl");
		try{
			Febriles febrile = febrileManager.find(id);	
			febrileManager.delete(febrile);
			//Borrar alertas de nuevo febril			
			String link = String.format(ConfigurationHelper.getAlertFebrileLink(),febrile.getId());
			alertManager.deleteAlert(link);
			
		}catch (Exception ex){
		    logger.error(RestErrorImpl.METHOD_DELETEFEBRILE +ex.toString());
		
		}finally{
			ctx.close();
		}		
	}	

}
