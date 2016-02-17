package com.stm.salesfast.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.impl.UserAccountDaoImpl;
import com.stm.salesfast.backend.dao.specs.UserAccountDao;
import com.stm.salesfast.backend.dto.UserAccountDto;
import com.stm.salesfast.backend.services.specs.UserAccountService;

@Service
public class UserAccountServiceImpl implements UserAccountService {

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

	

}
