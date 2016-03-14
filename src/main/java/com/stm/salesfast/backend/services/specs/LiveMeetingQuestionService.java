package com.stm.salesfast.backend.services.specs;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dto.LiveMeetingQuestionsDto;


public interface LiveMeetingQuestionService {
	
	public void insertQuestion(LiveMeetingQuestionsDto liveMeetingQuestion);
	
	public List<LiveMeetingQuestionsDto> getAllQuestions();
}
