package com.stm.salesfast.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.stm.salesfast.backend.dto.UserAccountDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAccountEntity {
	private static final long serialVersionUID = -7395917071437157624L;
	private String username;
	private String password;
	
}
