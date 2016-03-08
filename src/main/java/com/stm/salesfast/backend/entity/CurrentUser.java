package com.stm.salesfast.backend.entity;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.backend.services.specs.UserToRoleService;
import com.stm.salesfast.constant.SessionConstants;


public class CurrentUser extends User{
	
	private static final long serialVersionUID = -4568931000872570880L;

	private UserAccountEntity userAccountEntity;
	
	@Autowired
	UserToRoleService userRoleService;
	
	
	@Autowired
	UserAccountService userAccount;
	
	public CurrentUser(UserAccountEntity userAccountEntity){
		super(userAccountEntity.getUsername(), userAccountEntity.getPassword(), AuthorityUtils.
				createAuthorityList(userAccountEntity.getRoles().toArray(new String[userAccountEntity.getRoles().size()]))
				);

		this.userAccountEntity = userAccountEntity;
		
		SessionConstants.CURRENTUSERNAME = this.userAccountEntity.getUsername();
	}

	public UserAccountEntity getUser() {
		return userAccountEntity;
	}
	
	public String getId() {
		return userAccountEntity.getUsername();
	}
	
	public List<String> getRole() {
		return userAccountEntity.getRoles();
	}
}
