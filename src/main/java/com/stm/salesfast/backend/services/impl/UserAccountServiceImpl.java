package com.stm.salesfast.backend.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.controllers.LoginController;
import com.stm.salesfast.backend.dao.impl.UserAccountDaoImpl;
import com.stm.salesfast.backend.dao.specs.UserAccountDao;
import com.stm.salesfast.backend.dto.UserAccountDto;
import com.stm.salesfast.backend.entity.UserAccountEntity;
import com.stm.salesfast.backend.services.specs.UserAccountService;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	private Logger log = LoggerFactory.getLogger(UserAccountServiceImpl.class.getName());
	
	@Autowired
	private UserAccountDao userAccountDao;
	
	@Override
	public UserAccountDto getUserAccountByUserName(String username) {
		// TODO Auto-generated method stub
		return userAccountDao.getLoginCredentials(username);
	}

	@Override
	public UserAccountDto getUserAccountByUserId(int userId) {
		// TODO Auto-generated method stub
		return userAccountDao.getLoginCredentialsByUserId(userId);
	}
	
	@Override
	public String getPassword(String username) {
		// TODO Auto-generated method stub
		return userAccountDao.getLoginCredentials(username).getPassword();
	}
	
	@Override
	public boolean verifyUserCredentials(String username, String password) {
		// TODO Auto-generated method stub
		return getPassword(username).equals(password);
	}

	@Override
	public UserAccountEntity getUserAccountEntityByUserName(String username) {
		// TODO Auto-generated method stub
		UserAccountDto userAccountDto = userAccountDao.getLoginCredentials(username);
		return new UserAccountEntity(userAccountDto.getUsername(), userAccountDto.getPassword());
	}

	@Override
	public int getUserIdByUserName(String username) {
		// TODO Auto-generated method stub
		int userId = userAccountDao.getLoginCredentials(username).getUserId();
		log.info("User id for username : "+username+" is : "+userId);
		return userId;
	}

	

}
