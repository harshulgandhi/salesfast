package com.stm.salesfast.backend.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserAccountDto {
	
	private int userAccountId;
	private String username;
	private String password;
	private int userId;
}
