package com.stm.salesfast.backend.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.impl.UserDaoImpl;
import com.stm.salesfast.backend.entity.CurrentUser;
import com.stm.salesfast.backend.entity.UserAccountEntity;
import com.stm.salesfast.backend.services.specs.CurrentUserDetailsService;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.constant.SessionConstants;

@Service
public class CurrentUserDetailsServiceImpl implements CurrentUserDetailsService {
	
	Logger log = LoggerFactory.getLogger(CurrentUserDetailsServiceImpl.class.getName());
	
	@Autowired
	private final UserAccountService userService;
	
	@Autowired
    public CurrentUserDetailsServiceImpl(UserAccountService userService) {
        this.userService = userService;
    }
	
	
	@Override
	public CurrentUser loadUserByUsername(String username) {
		UserAccountEntity user = userService.getUserAccountEntityByUserName(username);
		
        if (user == null) {
        	throw new UsernameNotFoundException(String.format("User with id=%s was not found", username));
        }
        SessionConstants.CURRENTUSERNAME = user.getUsername();
        SessionConstants.USER_ID = userService.getUserAccountByUserName(username).getUserId();
        
        return new CurrentUser(user);
	}

}
