package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.UserDto;


public interface UserDao {
	
	public UserDto getBy(int userId);
	
	public void insert(UserDto user);
	
	public void deleteBy(int userId);
	
	public List<UserDto> getAll();
	
}
