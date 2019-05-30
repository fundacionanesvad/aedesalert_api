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

import com.gruposca.sapev.api.connectors.dao.impl.LanguageDao;
import com.gruposca.sapev.api.connectors.dao.model.Languages;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.modelview.Language;
import com.gruposca.sapev.api.modelview.LanguageListImpl;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.LanguageService;

public class LanguageServiceImpl extends AbsService implements LanguageService{

	public LanguageServiceImpl(AbsConnector connector) { super( connector); }
	
	@Override
	public List<Language> getLanguageList() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		List<Language> languageList = new ArrayList<Language>();
		Languages entityLanguage = new Languages();
		Language language;
		try{
			LanguageDao languageManager = (LanguageDao) ctx.getBean("LanguageDaoImpl");
			List<Languages> entityLanguagesList = languageManager.findAll();

			for(int i = 0; i < entityLanguagesList.size(); i++){				
				entityLanguage = entityLanguagesList.get(i);				
				language = new LanguageListImpl(entityLanguage.getId(),
											   entityLanguage.getCode(),
											   entityLanguage.getName());
				languageList.add(language);
			}
			
		}catch (Exception e){
		    logger.error(RestErrorImpl.METHOD_GETLANGUAJELIST + e.toString());
			languageList = null;
			
		}finally{
			ctx.close();
		}
		
		return languageList;
	}

}
