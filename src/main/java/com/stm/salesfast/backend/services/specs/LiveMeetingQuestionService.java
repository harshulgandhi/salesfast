package com.stm.salesfast.backend.services.specs;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dto.LiveMeetingQuestionsDto;
import com.stm.salesfast.backend.entity.LiveMeetingQnAEntity;


public interface LiveMeetingQuestionService {
	
	public void insertQuestion(LiveMeetingQuestionsDto liveMeetingQuestion);
	
	public List<LiveMeetingQnAEntity> getAllQuestions();

	public List<LiveMeetingQnAEntity> getSimilarQuestions(String question);

	public double checkSimilarity(String newQuestion, String existingQuestion);
}
