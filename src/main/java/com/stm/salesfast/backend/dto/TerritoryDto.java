package com.stm.salesfast.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class TerritoryDto {
	private int territoryId;
	private String territoryName;
	private String zip;
	private int userId;
	private int districtId;
}
