package com.stm.salesfast.backend.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAccountDto {
	
	private int userAccountId;
	private String username;
	private String password;
	private String userId;
}
