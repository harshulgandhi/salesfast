package com.stm.salesfast.backend.dto;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserToRoleDto {
	public UserToRoleDto(int userId2, int roleId2) {
		this.userId = userId2;
		this.roleId = roleId2;
	}
	private int userRoleId;
	private int userId;
	private int roleId;
		
}
