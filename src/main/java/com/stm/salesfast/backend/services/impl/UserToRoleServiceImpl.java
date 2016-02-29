package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.UserToRoleDao;
import com.stm.salesfast.backend.dto.RolesDto;
import com.stm.salesfast.backend.dto.UserToRoleDto;
import com.stm.salesfast.backend.services.specs.RoleService;
import com.stm.salesfast.backend.services.specs.UserToRoleService;

@Service
public class UserToRoleServiceImpl implements UserToRoleService {

	@Autowired
	UserToRoleDao userRoleDao;
	
	@Autowired
	RoleService roleService;
	
	@Override
	public List<RolesDto> getAllRolesForUser(int userId) {
		// TODO Auto-generated method stub
		List<RolesDto> rolesForUser = new ArrayList<>();
		for(UserToRoleDto userRoleDto : userRoleDao.getAllRolesForUser(userId)){
			rolesForUser.add(roleService.getBy(userRoleDto.getRoleId()));
		}
		return rolesForUser;
	}

}
