package com.stm.salesfast.backend.dao.specs;

import com.stm.salesfast.backend.dto.UserAccountDto;

public interface UserAccountDao {
	public void saveOrUpdateLoginCredentials(UserAccountDto loginCred);
	public UserAccountDto getLoginCredentials(String userName);
	public UserAccountDto getLoginCredentialsByUserId(int userId);
	void insert(UserAccountDto userAccount);
}
