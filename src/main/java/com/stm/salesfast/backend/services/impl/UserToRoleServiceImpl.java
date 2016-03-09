package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.controllers.LoginController;
import com.stm.salesfast.backend.dao.specs.UserToRoleDao;
import com.stm.salesfast.backend.dto.RolesDto;
import com.stm.salesfast.backend.dto.UserToRoleDto;
import com.stm.salesfast.backend.services.specs.RoleService;
import com.stm.salesfast.backend.services.specs.UserToRoleService;

@Service
public class UserToRoleServiceImpl implements UserToRoleService {
	private Logger log = LoggerFactory.getLogger(UserToRoleServiceImpl.class.getName());
	@Autowired
	UserToRoleDao userRoleDao;
	
	@Autowired
	RoleService roleService;
	
	@Override
	public List<String> getAllRolesForUser(int userId) {
		// TODO Auto-generated method stub
		List<String> roles = new ArrayList<>();
		
		for(UserToRoleDto userRoleDto : userRoleDao.getAllRolesForUser(userId)){
			roles.add(roleService.getBy(userRoleDto.getRoleId()).getShortName());
		}
		return roles;
	}
	
	@Override
	public void insertUserToRoleMapping(UserToRoleDto userRole){
		userRoleDao.insert(userRole);
	}

}
