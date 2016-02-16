package com.stm.salesfast.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class IncentivesDto {
	private int incentiveId;
	private int ammountUsd;
	private int userId;
}
