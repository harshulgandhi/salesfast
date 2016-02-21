package com.stm.salesfast.backend.services.specs;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.stm.salesfast.backend.entity.CurrentUser;


public interface CurrentUserDetailsService extends UserDetailsService {
	
	CurrentUser loadUserByUsername(String username);
}
