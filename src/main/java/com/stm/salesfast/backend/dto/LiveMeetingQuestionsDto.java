package com.stm.salesfast.backend.dto;

import java.sql.Date;
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
	private Date questionAskedOn;
	
	public LiveMeetingQuestionsDto(int userId, String question, String answer, int answeredByUser, double importanceFactor, Date askedOn){
		this.userId = userId;
		this.question = question;
		this.answer = answer;
		this.answeredByUser = answeredByUser;
		this.importanceIndex = importanceFactor;
		this.questionAskedOn = askedOn;
	}

	public LiveMeetingQuestionsDto(int uSER_ID, String question2, Date askedOn) {
		this.userId = uSER_ID;
		this.question = question2;
		this.questionAskedOn = askedOn;
		
	}
}
