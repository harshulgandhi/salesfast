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
	
	public UserAccountDto(String firstName, String password2, int userId2) {
		// TODO Auto-generated constructor stub
		this.username = firstName;
		this.password = password2;
		this.userId = userId2;
	}
}
