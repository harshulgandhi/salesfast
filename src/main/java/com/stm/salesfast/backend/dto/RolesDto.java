package com.stm.salesfast.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RolesDto {

	private int roleId;
	private String roleName;
	private String shortName;
}
