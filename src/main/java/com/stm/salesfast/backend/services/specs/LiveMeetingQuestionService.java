package com.stm.salesfast.backend.services.specs;

import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dto.LiveMeetingQuestionsDto;
import com.stm.salesfast.backend.entity.LiveMeetingQnAEntity;
import com.stm.salesfast.backend.entity.NewQuestionEntity;


public interface LiveMeetingQuestionService {
	
	public void insertQuestionwAnswer(LiveMeetingQuestionsDto liveMeetingQuestion);
	
	public List<LiveMeetingQnAEntity> getAllQuestions();

	public List<LiveMeetingQnAEntity> getSimilarQuestions(String question);

	public double checkSimilarity(String newQuestion, String existingQuestion);

	public void insertQuestionOnly(NewQuestionEntity liveMeetingQuestionOnly) throws ParseException;

	public void sendLiveMeetingNewQuestionEmail(String subject, String body,
			String toEmailId);

	public void notifyAllUsers(int userId);

	public List<LiveMeetingQnAEntity> getAllUnansweredQuestions();

	public void insertAnswerToAQuestion(LiveMeetingQnAEntity quesWithAnswer);

	public List<LiveMeetingQnAEntity> getAllQuestionsAskedBySelf();
}
