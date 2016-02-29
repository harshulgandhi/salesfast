package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.RolesDto;

public interface RoleService {
	public RolesDto getBy(int rolesId);
	public RolesDto getBy(String shortName);
	public List<RolesDto> getAllRoles();
}
