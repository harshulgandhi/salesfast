package com.stm.salesfast.backend.services.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.UserDto;

public interface UserDetailService {
	public UserDto getUserDetails(int userId);
	public UserDto getUserDetails(String username);
	public List<UserDto> getAllUsers();
	public List<UserDto> getAllSalesReps();
	public int getDistrictManagerId(int userId);
	public String getUserCompleteName(int userId);
	public void insertUserDetails(UserDto userDetails);
	public boolean checkIfUserExists(String firstName, String lastName, String email);
	public int getUserIdByName(String firstName, String lastName, String email);
	public UserDto getUserForPhysicianId(int physicianId);
	public List<UserDto> getAllNonPhysicianUsers();
}
