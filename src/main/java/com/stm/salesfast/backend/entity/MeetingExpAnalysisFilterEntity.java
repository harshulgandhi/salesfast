package com.stm.salesfast.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MeetingExpAnalysisFilterEntity {
	private String medicalFieldId;
	private int productId;
	private int userId;
	private String status;
}
