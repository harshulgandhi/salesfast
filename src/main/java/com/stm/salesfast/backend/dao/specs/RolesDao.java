package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.RolesDto;

public interface RolesDao {
	public RolesDto getById(int rolesId);
	public RolesDto getByRoleName(String shortName);
	public List<RolesDto> getAllRoles();
}
