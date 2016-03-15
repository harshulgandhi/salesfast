package com.stm.salesfast.backend.controllers;


import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stm.salesfast.backend.entity.LiveMeetingQnAEntity;
import com.stm.salesfast.backend.entity.NewQuestionEntity;
import com.stm.salesfast.backend.services.specs.LiveMeetingQuestionService;


@Controller
public class LiveMeetingController {
	
	private Logger log = LoggerFactory.getLogger(LiveMeetingController.class.getName());
	
	private String newQuestionSubmitted = "";
	
	@Autowired
	LiveMeetingQuestionService liveMeetingService;
	
	@RequestMapping(value="/livemeetingquestions", method=RequestMethod.GET)
	public String liveMeetingQuestionnAnswer(){
		return "livemeetingassistant";
	}
	
	@RequestMapping(value="/getallqna",  headers="Accept=*/*", method=RequestMethod.GET, produces="appliation/json")
	@ResponseBody
	public LiveMeetingQnAEntity[] getAllQuestionsWithAnswers(){
		log.info("Fetching all questions and answers!");
		List<LiveMeetingQnAEntity> allQuestions = liveMeetingService.getAllQuestions();
		for(LiveMeetingQnAEntity eachQues : allQuestions){
			log.info(""+eachQues);
		}
		return allQuestions.toArray(new LiveMeetingQnAEntity[allQuestions.size()]);
	}
	
	@RequestMapping(value="/getsimilarqna",  headers="Accept=*/*", method=RequestMethod.GET, produces="appliation/json")
	@ResponseBody
	public LiveMeetingQnAEntity[] getSimilarQuestionsWithAnswers(){
		log.info("Fetching SIMILAR questions and answers!");
		List<LiveMeetingQnAEntity> allQuestions = liveMeetingService.getSimilarQuestions(newQuestionSubmitted);
		for(LiveMeetingQnAEntity eachQues : allQuestions){
			log.info(""+eachQues);
		}
		return allQuestions.toArray(new LiveMeetingQnAEntity[allQuestions.size()]);
	}
	
	@RequestMapping(value="/getquestionaskedbyself",  headers="Accept=*/*", method=RequestMethod.GET, produces="appliation/json")
	@ResponseBody
	public LiveMeetingQnAEntity[] getQuestionAskedBySelf(){
		log.info("Fetching SELF ASKED questions and answers!");
		List<LiveMeetingQnAEntity> allQuestions = liveMeetingService.getAllQuestionsAskedBySelf();
		for(LiveMeetingQnAEntity eachQues : allQuestions){
			log.info(""+eachQues);
		}
		return allQuestions.toArray(new LiveMeetingQnAEntity[allQuestions.size()]);
	}
	
	@RequestMapping(value="/submitquestion", method=RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public  void addNewQuestion(@RequestBody NewQuestionEntity newQuestion) throws ParseException {
		log.info("New question received  : "+newQuestion);
		newQuestionSubmitted = newQuestion.getQuestion();
		liveMeetingService.insertQuestionOnly(newQuestion);
	}

	@RequestMapping(value="/unansweredques", method=RequestMethod.GET)
	public String answerAQuesPage(){
		return "answeraquestion";
	}
	
	@RequestMapping(value="/getunansweredques", headers="Accept=*/*", method=RequestMethod.GET, produces="appliation/json")
	@ResponseBody
	public  LiveMeetingQnAEntity[] getAllUnansweredQuestions() throws ParseException {
		log.info("Fetching UNANSWERED questions and answers!");
		List<LiveMeetingQnAEntity> allQuestions = liveMeetingService.getAllUnansweredQuestions();
		return allQuestions.toArray(new LiveMeetingQnAEntity[allQuestions.size()]);
	}
	
	@RequestMapping(value="/submitanswer", method=RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public  void submitAnswer(@RequestBody LiveMeetingQnAEntity answer) throws ParseException {
		log.info("New Answer received  : "+answer);
		liveMeetingService.insertAnswerToAQuestion(answer);
	}
}
