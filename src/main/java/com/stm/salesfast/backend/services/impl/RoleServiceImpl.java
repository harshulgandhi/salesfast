package com.stm.salesfast.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.RolesDao;
import com.stm.salesfast.backend.dto.RolesDto;
import com.stm.salesfast.backend.services.specs.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	RolesDao roleDao;
	
	@Override
	public RolesDto getBy(int rolesId) {
		// TODO Auto-generated method stub
		return roleDao.getById(rolesId);
	}

	@Override
	public RolesDto getBy(String shortName) {
		// TODO Auto-generated method stub
		return roleDao.getByRoleName(shortName);
	}

	@Override
	public List<RolesDto> getAllRoles() {
		// TODO Auto-generated method stub
		return roleDao.getAllRoles();
	}
	
}
