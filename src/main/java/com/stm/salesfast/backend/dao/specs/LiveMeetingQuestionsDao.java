package com.stm.salesfast.backend.dao.specs;

import java.util.List;

import com.stm.salesfast.backend.dto.LiveMeetingQuestionsDto;

public interface LiveMeetingQuestionsDao {
	
	public void insert(LiveMeetingQuestionsDto liveMeetingQuestion);
	
	public List<LiveMeetingQuestionsDto> getAll();

	public void insertQuestionOnly(LiveMeetingQuestionsDto liveMeetingQuestion);

	public List<LiveMeetingQuestionsDto> getAllwoAnswer();

	public void insertAnswerToAQuestion(LiveMeetingQuestionsDto liveMeetingQuestion);
	
}
