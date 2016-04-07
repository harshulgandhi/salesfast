package com.stm.salesfast.backend.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MeetingExperienceAnalyzedDataEntity {
	private String category;
	private List<MeetingExperienceDataEntity> analyzedData;
}
