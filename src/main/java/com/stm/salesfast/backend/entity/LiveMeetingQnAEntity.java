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
	
	private int liveMeetingQuestionId;
	private int userId;
	private String question;
	private String answer;
	private double importanceIndex;
	
	public LiveMeetingQnAEntity(int userId2, String question2, String answer2,
			double importanceIndex2) {
		this.userId = userId2;
		this.question = question2;
		this.answer = answer2;
		this.importanceIndex = importanceIndex2;
	}
}	
