package com.stm.salesfast.backend.services.specs;

import com.stm.salesfast.backend.dto.UserAccountDto;

public interface UserAccountService {
	public UserAccountDto getUserAccountByUserName(String username);
	public UserAccountDto getUserAccountByUserId(int userId);
	public boolean verifyUserCredentials(String username, String password);
	public String getPassword(String username);
}
