package com.stm.salesfast.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class LiveMeetingQnAEntity {
	private int userId;
	private String question;
	private String answer;
	private double importanceIndex;
}	
