package com.stm.salesfast.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.LiveMeetingQuestionsDao;
import com.stm.salesfast.backend.dto.LiveMeetingQuestionsDto;
import com.stm.salesfast.backend.services.specs.LiveMeetingQuestionService;

@Service
public class LiveMeetingQuestionServiceImpl implements LiveMeetingQuestionService{

	@Autowired
	LiveMeetingQuestionsDao liveMeetingDao;
	@Override
	public void insertQuestion(LiveMeetingQuestionsDto liveMeetingQuestion) {
		liveMeetingDao.insert(liveMeetingQuestion);
	}

	@Override
	public List<LiveMeetingQuestionsDto> getAllQuestions() {
		return liveMeetingDao.getAll();
	}

}
