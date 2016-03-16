package com.stm.salesfast.backend.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.classgen.genArrayAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.LiveMeetingQuestionsDao;
import com.stm.salesfast.backend.dto.LiveMeetingQuestionsDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.entity.LiveMeetingQnAEntity;
import com.stm.salesfast.backend.entity.NewQuestionEntity;
import com.stm.salesfast.backend.services.specs.LiveMeetingQuestionService;
import com.stm.salesfast.backend.services.specs.NotificationService;
import com.stm.salesfast.backend.services.specs.UserDetailService;
import com.stm.salesfast.backend.utils.SalesFastEmail;
import com.stm.salesfast.backend.utils.SalesFastEmailSendGridImpl;
import com.stm.salesfast.backend.utils.SalesFastUtilities;
import com.stm.salesfast.constant.ConstantValues;
import com.stm.salesfast.constant.SessionConstants;

@Service
public class LiveMeetingQuestionServiceImpl implements LiveMeetingQuestionService{
	
	private Logger log = LoggerFactory.getLogger(AddNewProductServiceImpl.class.getName());
	
	@Autowired
	LiveMeetingQuestionsDao liveMeetingDao;
	
	@Autowired
	UserDetailService userService;
	
	@Autowired
	NotificationService notification;
	
	@Override
	public void insertQuestionwAnswer(LiveMeetingQuestionsDto liveMeetingQuestion) {
		liveMeetingDao.insert(liveMeetingQuestion);
	}

	@Override
	public void insertQuestionOnly(NewQuestionEntity liveMeetingQuestionOnly) throws ParseException {
		
		liveMeetingDao.insertQuestionOnly(new LiveMeetingQuestionsDto(
					SessionConstants.USER_ID,
					liveMeetingQuestionOnly.getQuestion(),
					SalesFastUtilities.getCurrentDate()
				));
		//notify all users (if no suggestion found?)
		notifyAllUsers(SessionConstants.USER_ID);
	}
	
	@Override
	public void insertAnswerToAQuestion(LiveMeetingQnAEntity quesWithAnswer){
		liveMeetingDao.insertAnswerToAQuestion(quesWithAnswer.getAnswer(),
				SessionConstants.USER_ID,
				quesWithAnswer.getLiveMeetingQuestionId());
		
		//Notify the user who asked the question
		String answeredBy = userService.getUserCompleteName(SessionConstants.USER_ID);
		notification.insertNotificationQuestionAnswered(answeredBy, quesWithAnswer.getUserId(), "QUESTION WAS ANSWERED");
		
		//send email to user who asked the question
		String emailBody = answeredBy+" answered a question that you have asked."
				+ " Visit http://127.0.0.1/livemeetingquestions to see the answer";
		String subject = "Your question just got answered";
		sendLiveMeetingNewQuestionEmail(subject, emailBody, userService.getUserDetails(quesWithAnswer.getUserId()).getEmail());
	}
	
	@Override
	public void notifyAllUsers(int userId){
		String salesRepName = userService.getUserCompleteName(userId);
		List<UserDto> biopharmaUsers = userService.getAllNonPhysicianUsers();
		for(UserDto eachUser : biopharmaUsers){
			if(eachUser.getUserId() != userId){
				//insert into notifications for all users
				notification.insertNotificationLiveMeetingQuestion(salesRepName, eachUser.getUserId(), "LIVE MEETING QUESTION");
				
				//send email to all users
				String emailBody = "Sales Representative "+salesRepName+" has posted a question "
						+ "that he is not able to answer to a physician he is detailing right now. "
						+ "Log into SalesFast @ http://127.0.0.1/unansweredquest to read the question"
						+ "and answer it if possible.";
				String subject = "A SalesRep needs your help.";
				sendLiveMeetingNewQuestionEmail(subject, emailBody, eachUser.getEmail());
			}
		}
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
	 * Questions that need to be answered
	 * */
	@Override
	public List<LiveMeetingQnAEntity> getAllUnansweredQuestions() {
		
		List<LiveMeetingQuestionsDto> questionAnswer = liveMeetingDao.getAllwoAnswer(SessionConstants.USER_ID);  
		List<LiveMeetingQnAEntity> questionAnswerEntities = new ArrayList<>();
		for(LiveMeetingQuestionsDto eachQnA : questionAnswer){
			questionAnswerEntities.add(new LiveMeetingQnAEntity(
					eachQnA.getLiveMeetingQuestionsId(),
					eachQnA.getUserId(),
					eachQnA.getQuestion(),
					eachQnA.getAnswer(),
					eachQnA.getImportanceIndex()
					));
		}
		
		return questionAnswerEntities;
	}

	
	/**
	 * Questions that were asked by logged in user
	 * */
	@Override
	public List<LiveMeetingQnAEntity> getAllQuestionsAskedBySelf() {
		
		List<LiveMeetingQuestionsDto> questionAnswer = liveMeetingDao.getAllAskedBySelf(SessionConstants.USER_ID);  
		List<LiveMeetingQnAEntity> questionAnswerEntities = new ArrayList<>();
		for(LiveMeetingQuestionsDto eachQnA : questionAnswer){
			questionAnswerEntities.add(new LiveMeetingQnAEntity(
					eachQnA.getLiveMeetingQuestionsId(),
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
	
	@Override
	public void sendLiveMeetingNewQuestionEmail(String subject, String body, String toEmailId) {
		SalesFastEmail email = new SalesFastEmailSendGridImpl();
		email.setToEmailId(toEmailId);
		email.setFromEmail("no-reply@biopharma.com");
		email.setFromName("BioPharma SalesForce");
		email.addSubject(subject);
		
		email.addTextBody(body);
		log.info("Sending new product email with content as :\n"+body);
		email.sendMail();
	}

	
}
