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

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gruposca.sapev.api.connectors.dao.impl.LanguageDao;
import com.gruposca.sapev.api.connectors.dao.impl.UserDao;
import com.gruposca.sapev.api.connectors.dao.model.Languages;
import com.gruposca.sapev.api.connectors.dao.model.Users;
import com.gruposca.sapev.api.connectors.factory.AbsConnector;
import com.gruposca.sapev.api.modelview.Account;
import com.gruposca.sapev.api.modelview.AccountGetImpl;
import com.gruposca.sapev.api.modelview.AccountUpdateImpl;
import com.gruposca.sapev.api.modelview.RestErrorImpl;
import com.gruposca.sapev.api.modelview.Session;
import com.gruposca.sapev.api.services.AbsService;
import com.gruposca.sapev.api.services.AccountService;

public class AccountServiceImpl extends AbsService implements AccountService{
	
	public AccountServiceImpl(AbsConnector connector) { super( connector); }

	@Override
	public Account getAccount(Session session) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Users user;
		Account account = null;
		try{
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");
			user = userManager.getUserByToken(session.getAuthorizationToken());
			
			if(user != null){				
				account = new AccountGetImpl(user.getLogin(), user.getName(), user.getEmail(), user.getLanguages().getId());
				return account;				
			}		
			
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_GETACCOUNT +e.toString());
		
		}finally{
			ctx.close();
		}
		return account;
	}

	@Override
	public Users updateAccount(Session session, AccountUpdateImpl account) {	
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sapev_context.xml");
		Users user = null;
		try{	
			UserDao userManager = (UserDao) ctx.getBean("UserDaoImpl");		
			LanguageDao languageManager = (LanguageDao) ctx.getBean("LanguageDaoImpl");
			
			Languages language = languageManager.find(account.getLanguageId());
			user = userManager.getUserByToken(session.getAuthorizationToken());
			
			if(user != null){
				user.setName(account.getName());			
				if(account.getPassword1() != null && !account.getPassword1().equals("")) {
					user.setPassword(DigestUtils.sha256Hex(account.getPassword1()));					
				}			
				user.setEmail(account.getEmail());
				user.setLanguages(language);					
				user = userManager.save(user);				
			}			
		
		}catch(Exception e){
		    logger.error(RestErrorImpl.METHOD_UPDATEACCOUNT +e.toString());
		}finally{
			ctx.close();
		}	
		
		return user;
	}

}
