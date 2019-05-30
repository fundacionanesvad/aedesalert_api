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

import com.gruposca.sapev.api.connectors.dao.model.Scenes;
import com.gruposca.sapev.api.modelview.Scene;
import com.gruposca.sapev.api.modelview.SceneImpl;
import com.gruposca.sapev.api.modelview.Session;

public interface SceneService {

	List<Scene> getScenesList(Session session);
	
	Scene getScene(Integer id);
	
	Scenes createScene(SceneImpl scene);
	
	Scenes updateScene(Integer id, SceneImpl scene);
	
	boolean deleteScene(Integer id);	

}
