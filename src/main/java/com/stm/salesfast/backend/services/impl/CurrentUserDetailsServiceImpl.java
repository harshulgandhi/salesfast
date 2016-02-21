package com.stm.salesfast.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.entity.CurrentUser;
import com.stm.salesfast.backend.entity.UserAccountEntity;
import com.stm.salesfast.backend.services.specs.CurrentUserDetailsService;
import com.stm.salesfast.backend.services.specs.UserAccountService;

@Service
public class CurrentUserDetailsServiceImpl implements CurrentUserDetailsService {

	private final UserAccountService userService;
	
	@Autowired
    public CurrentUserDetailsServiceImpl(UserAccountService userService) {
        this.userService = userService;
    }
	
	
	@Override
	public CurrentUser loadUserByUsername(String username) {
		// TODO Auto-generated method stub
		UserAccountEntity user = userService.getUserAccountEntityByUserName(username);
        if (user == null) {
        	throw new UsernameNotFoundException(String.format("User with id=%s was not found", username));
        }
        
        return new CurrentUser(user);
	}

}
