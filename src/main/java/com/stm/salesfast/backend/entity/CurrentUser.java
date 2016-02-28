package com.stm.salesfast.backend.entity;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;


public class CurrentUser extends User{
	
	private static final long serialVersionUID = -4568931000872570880L;

	private UserAccountEntity userAccountEntity;
	
	/**This has been hard coded for now, will be
	 * fetched from database once roles table is
	 * populated
	 */
	private static List<String> rolesList = Arrays.asList("SalesRep","DM","NH");
	
	public CurrentUser(UserAccountEntity userAccountEntity){
		
		super(userAccountEntity.getUsername(), userAccountEntity.getPassword(), AuthorityUtils.
				createAuthorityList(rolesList.toArray(new String[rolesList.size()]))
				);
		
		this.userAccountEntity = userAccountEntity;
	}
	
	


	public UserAccountEntity getUser() {
		return userAccountEntity;
	}
	
	public String getId() {
		return userAccountEntity.getUsername();
	}
	
	
}
