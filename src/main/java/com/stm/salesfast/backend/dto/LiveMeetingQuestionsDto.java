package com.stm.salesfast.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class LiveMeetingQuestionsDto {
	private int liveMeetingQuestionsId;
	private int userId;
	private String question;
	private String answer;
	private int answeredByUser;
	private double importanceIndex;
	
	public LiveMeetingQuestionsDto(int userId, String question, String answer, int answeredByUser, double importanceFactor){
		this.userId = userId;
		this.question = question;
		this.answer = answer;
		this.answeredByUser = answeredByUser;
		this.importanceIndex = importanceFactor;
	}
}
