package com.stm.salesfast.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.stm.salesfast.backend.dto.AlignmentsDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlignmentEntity {
	private String physicianName;
	private String email;
	private String contactNumber;
	private String addressLineOne;
}
