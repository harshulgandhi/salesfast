package com.stm.salesfast.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.UserDao;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.backend.services.specs.UserDetailService;

@Service
public class UserDetailServiceImpl implements UserDetailService{

	@Autowired
	UserDao userDetailDao;
	
	@Autowired
	UserAccountService userAccount;
	
	@Override
	public UserDto getUserDetails(int userId) {
		// TODO Auto-generated method stub
		return userDetailDao.getBy(userId);
	}

	@Override
	public UserDto getUserDetails(String username) {
		// TODO Auto-generated method stub
		return getUserDetails(userAccount.getUserIdByUserName(username));
	}
	
}
