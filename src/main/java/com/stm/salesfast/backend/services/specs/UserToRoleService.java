package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.RolesDto;
import com.stm.salesfast.backend.dto.UserToRoleDto;

public interface UserToRoleService {
	public List<String> getAllRolesForUser(int userId);

	void insertUserToRoleMapping(UserToRoleDto userRole);
}
