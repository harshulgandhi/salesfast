package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.UserDao;
import com.stm.salesfast.backend.dto.RolesDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.services.specs.RoleService;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.backend.services.specs.UserDetailService;
import com.stm.salesfast.backend.services.specs.UserToRoleService;

@Service
public class UserDetailServiceImpl implements UserDetailService{

	@Autowired
	UserDao userDetailDao;
	
	@Autowired
	UserAccountService userAccount;
	
	@Autowired
	UserToRoleService userRoleService;
	
	@Autowired
	RoleService roleService;
	
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

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		return userDetailDao.getAll();
	}

	@Override
	public List<UserDto> getAllSalesReps() {
		// TODO Auto-generated method stub
		List<UserDto> allUsers = getAllUsers();
		List<UserDto> allSalesReps = new ArrayList<>();
		
		for(UserDto eachUser : allUsers){
			List<RolesDto> thisUsersRole = userRoleService.getAllRolesForUser(eachUser.getUserId());
			for(RolesDto eachRole : thisUsersRole){
				if(eachRole.getShortName().equals("SalesRep")) allSalesReps.add(eachUser);
			}
		}
		return allSalesReps;
	}
	
}
