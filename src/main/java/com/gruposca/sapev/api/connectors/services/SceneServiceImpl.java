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

import com.gruposca.sapev.api.connectors.dao.impl.AreaDao;
import com.gruposca.sapev.api.connectors.dao.impl.ScenesDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.model.Areas;
import com.gruposca.sapev.api.connectors.dao.model.Scenes;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Scene;
import com.gruposca.sapev.api.modelview.SceneImpl;
import com.gruposca.sapev.api.modelview.SceneListImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.SceneService;

public class SceneServiceImpl extends AbsService implements SceneService{
	
	public SceneServiceImpl(AbsConnector connector) { super( connector); }

	@Override
	public List<Scene> getScenesList(Session session) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<Scene> sceneList = new ArrayList<Scene>();
		Scenes entityScene = new Scenes();
		Scene scene;
		try{
			ScenesDao sceneManager = (ScenesDao) ctx.getBean("ScenesDaoImpl");
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			Users user = userManager.getUserByToken(session.getAuthorizationToken());
			List<Scenes> entityScenesList = sceneManager.getListScenes(user.getAreas().getId());
			
			for(int i = 0; i < entityScenesList.size(); i++){				
				entityScene = entityScenesList.get(i);				
				scene = new SceneListImpl(entityScene.getId(), entityScene.getAreas().getId(), entityScene.getAreas().getName(), entityScene.getSceneLevel());
				sceneList.add(scene);
			}
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETSCENESLIST + e.toString());
			sceneList = null;
			
		}finally{
			ctx.close();
		}
		
		return sceneList;
	}

	@Override
	public Scene getScene(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Scene scene;
		
		try{			
			ScenesDao sceneManager = (ScenesDao) ctx.getBean("ScenesDaoImpl");
			Scenes entityScene = sceneManager.find(id);	
			scene = new SceneImpl(entityScene.getAreas().getId(), entityScene.getSceneLevel());
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETSCENE + e.toString());
			scene=null;
		}finally{
			ctx.close();
		}
		return scene;
	}

	@Override
	public Scenes createScene(SceneImpl scene) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Scenes entityScenes;
		
		try{
			ScenesDao sceneManager = (ScenesDao) ctx.getBean("ScenesDaoImpl");
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			Areas area = areaManager.find(scene.getAreaId());
			entityScenes = new Scenes(area, scene.getSceneLevel());		
			entityScenes = sceneManager.save(entityScenes);
			if(entityScenes != null){
				return entityScenes;
			}else{
				return null;
			}					
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_CREATESCENE + e.toString());
			return null;
		}finally{
			ctx.close();
		}		
	}

	@Override
	public Scenes updateScene(Integer id, SceneImpl scene) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");

		try{		
			ScenesDao sceneManager = (ScenesDao) ctx.getBean("ScenesDaoImpl");
			AreaDao areaManager = (AreaDao) ctx.getBean("AreaDaoImpl");
			Scenes updateScene = sceneManager.find(id);
			Areas area = areaManager.find(scene.getAreaId());
			
			updateScene.setAreas(area);
			updateScene.setSceneLevel(scene.getSceneLevel());
			updateScene = sceneManager.save(updateScene);	
			return updateScene;					
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_UPDATESCENE + e.toString());
			return null;
		}finally{
			ctx.close();
		}	
	}

	@Override
	public boolean deleteScene(Integer id) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		boolean result = false;
		
		try{	
			ScenesDao sceneManager = (ScenesDao) ctx.getBean("ScenesDaoImpl");
			Scenes scene = sceneManager.find(id);				
			sceneManager.delete(scene);
			result = true;			
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_DELETESCENE + e.toString());
		}finally{
			ctx.close();
		}	
		return result;
	}
}
