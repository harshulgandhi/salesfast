package com.stm.salesfast.backend.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.stm.salesfast.backend.dto.UserAccountDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserAccountEntity {
	private static final long serialVersionUID = -7395917071437157624L;
	private String username;
	private String password;
	private List<String> roles;
	
}
