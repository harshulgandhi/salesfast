package com.stm.salesfast.backend.services.specs;

import com.stm.salesfast.backend.dto.UserAccountDto;
import com.stm.salesfast.backend.entity.UserAccountEntity;

public interface UserAccountService {
	public UserAccountDto getUserAccountByUserName(String username);
	public UserAccountDto getUserAccountByUserId(int userId);
	public boolean verifyUserCredentials(String username, String password);
	public String getPassword(String username);
	public int getUserIdByUserName(String username);
	
	public UserAccountEntity getUserAccountEntityByUserName(String username);
	void insertNewUserAccount(UserAccountDto userAccount);
	
}
