package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.UserDao;
import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.dto.RolesDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.RoleService;
import com.stm.salesfast.backend.services.specs.TerritoryService;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.backend.services.specs.UserDetailService;
import com.stm.salesfast.backend.services.specs.UserToRoleService;

@Service
public class UserDetailServiceImpl implements UserDetailService{

	@Autowired
	UserDao userDetailDao;
	
	@Autowired
	UserAccountService userAccount;
	
	@Autowired
	UserToRoleService userRoleService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	TerritoryService terrService;
	
	@Autowired
	PhysicianFetchService physService;
	
	@Override
	public UserDto getUserDetails(int userId) {
		// TODO Auto-generated method stub
		return userDetailDao.getBy(userId);
	}

	@Override
	public UserDto getUserDetails(String username) {
		// TODO Auto-generated method stub
		return getUserDetails(userAccount.getUserIdByUserName(username));
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		return userDetailDao.getAll();
	}

	@Override
	public List<UserDto> getAllNonPhysicianUsers() {
		// TODO Auto-generated method stub
		return userDetailDao.getAllNonPhysicians();
	}
	
	@Override
	public List<UserDto> getAllSalesReps() {
		// TODO Auto-generated method stub
		List<UserDto> allUsers = getAllUsers();
		List<UserDto> allSalesReps = new ArrayList<>();
		
		for(UserDto eachUser : allUsers){
			List<String> thisUsersRoles = userRoleService.getAllRolesForUser(eachUser.getUserId());
			if(thisUsersRoles.contains("SalesRep")) allSalesReps.add(eachUser);
		}
		return allSalesReps;
	}

	@Override
	public int getDistrictManagerId(int userId) {
		// TODO Auto-generated method stub
		return terrService.getByUser(userId).getDistrictId();
	}

	@Override
	public String getUserCompleteName(int userId) {
		// TODO Auto-generated method stub
		return userDetailDao.getBy(userId).getFirstName()+" "
				+userDetailDao.getBy(userId).getLastName();
	}
	
	@Override
	public void insertUserDetails(UserDto userDetails){
		userDetailDao.insert(userDetails);
	}
	
	@Override
	public boolean checkIfUserExists(String firstName, String lastName, String email){
		return userDetailDao.getBy(firstName, lastName, email) != null;
	}
	
	@Override
	public int getUserIdByName(String firstName, String lastName, String email){
		return userDetailDao.getBy(firstName, lastName, email).getUserId();
	}

	@Override
	public UserDto getUserForPhysicianId(int physicianId){
		PhysicianStgDto physician = physService.getPhysicianById(physicianId);
		int userId = getUserIdByName(physician.getFirstName(), physician.getLastName(), physician.getEmail());
		return getUserDetails(userId);
	}
}
