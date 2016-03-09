package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.UserToRoleDto;

public interface UserToRoleDao {
	public List<UserToRoleDto> getAllRolesForUser(int userId);

	void insert(UserToRoleDto userRole);
}
