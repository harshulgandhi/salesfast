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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stm.salesfast.backend.entity.LiveMeetingQnAEntity;
import com.stm.salesfast.backend.entity.NewQuestionEntity;
import com.stm.salesfast.backend.entity.ViewAllPitchEntity;
import com.stm.salesfast.backend.entity.ViewPitchFilterParamEntity;
import com.stm.salesfast.backend.services.specs.LiveMeetingQuestionService;
import com.stm.salesfast.backend.services.specs.PitchesService;


@Controller
public class LiveMeetingController {
	
	private Logger log = LoggerFactory.getLogger(LiveMeetingController.class.getName());
	
	private String newQuestionSubmitted = "";
	
	@Autowired
	LiveMeetingQuestionService liveMeetingService;
	
	@Autowired
	PitchesService pitchService;
	
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
	public LiveMeetingQnAEntity[] getSimilarQuestionsWithAnswers(@RequestParam(value="ques") String ques){
		log.info("Fetching SIMILAR questions and answers!");
		List<LiveMeetingQnAEntity> allQuestions = liveMeetingService.getSimilarQuestions(ques);
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
	
	@RequestMapping(value="/allpitches", method=RequestMethod.GET)
	public String allPitchesPage(){
		return "allpitches";
	}
	
	@RequestMapping(value="/filterparameters", method=RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ViewAllPitchEntity[] applyFilterForAllPitches(@RequestBody ViewPitchFilterParamEntity filterEntity){
		log.info("Filter Parameters received : "+filterEntity);
		if(filterEntity.getMedicalFieldId().equals("none")) {
			log.info("Medical field id is none");
			filterEntity.setMedicalFieldId(null);
		}
		log.info("Filter Parameters received : "+filterEntity);
		List<ViewAllPitchEntity> allPitchesToShow =  pitchService.getAllPitchesForFilter(filterEntity);
		return allPitchesToShow.toArray(new ViewAllPitchEntity[allPitchesToShow.size()]);
	}
	
	@RequestMapping(value="/upvotepitch", method=RequestMethod.POST)
	@ResponseBody
	public void upvotePitch( @RequestParam(value="pitchId") int pitchId){
		log.info("Pitch to be upvoted "+pitchId);
		pitchService.upvotePitch(pitchId);
	}
	
	@RequestMapping(value="/downvotepitch", method=RequestMethod.POST)
	@ResponseBody
	public void downvotePitch( @RequestParam(value="pitchId") int pitchId){
		log.info("Pitch to be downvoted "+pitchId);
		pitchService.downvotePitch(pitchId);
	}
	
}	
