package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.classgen.genArrayAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.LiveMeetingQuestionsDao;
import com.stm.salesfast.backend.dto.LiveMeetingQuestionsDto;
import com.stm.salesfast.backend.entity.LiveMeetingQnAEntity;
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
	public List<LiveMeetingQnAEntity> getAllQuestions() {
		
		List<LiveMeetingQuestionsDto> questionAnswer = liveMeetingDao.getAll();  
		List<LiveMeetingQnAEntity> questionAnswerEntities = new ArrayList<>();
		for(LiveMeetingQuestionsDto eachQnA : questionAnswer){
			questionAnswerEntities.add(new LiveMeetingQnAEntity(
					eachQnA.getUserId(),
					eachQnA.getQuestion(),
					eachQnA.getAnswer(),
					eachQnA.getImportanceIndex()
					));
		}
		
		return questionAnswerEntities;
	}
	
	
	/**
	 * Fetching all similar questions based on 
	 * Apache lang's  Levenshtein distance algorithm
	 * reference : http://commons.apache.org/proper/commons-lang/
	 * */
	@Override
	public List<LiveMeetingQnAEntity> getSimilarQuestions(String question){
		
		List<LiveMeetingQnAEntity> allQuestions = getAllQuestions();
		List<LiveMeetingQnAEntity> similarQuestions = new ArrayList<>();
		
		for(LiveMeetingQnAEntity eachQuestion : allQuestions){
			if( checkSimilarity(question, eachQuestion.getQuestion()) > 0.4){
				similarQuestions.add(eachQuestion);
			}
		}
		
		return similarQuestions;
	}
	
	@Override
	public double checkSimilarity(String newQuestion, String existingQuestion){
		String longer  = newQuestion.length() >= existingQuestion.length() ? newQuestion : existingQuestion;
		String shorter= newQuestion.length() >= existingQuestion.length() ? existingQuestion : newQuestion;
		int longerLength = longer.length();
		return (longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) / (double) longerLength;
	}

}
