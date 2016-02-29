package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.UserDto;

public interface UserDetailService {
	public UserDto getUserDetails(int userId);
	public UserDto getUserDetails(String username);
	public List<UserDto> getAllUsers();
	public List<UserDto> getAllSalesReps();
}
