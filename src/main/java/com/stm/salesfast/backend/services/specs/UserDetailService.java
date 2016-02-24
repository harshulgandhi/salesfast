package com.stm.salesfast.backend.services.specs;

import com.stm.salesfast.backend.dto.UserDto;

public interface UserDetailService {
	public UserDto getUserDetails(int userId);
	public UserDto getUserDetails(String username);
}
