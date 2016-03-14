package com.stm.salesfast.backend.controllers;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stm.salesfast.backend.entity.MeetingUpdateEntity;

@Controller
public class LiveMeetingController {
	
	private Logger log = LoggerFactory.getLogger(AppointmentController.class.getName());
	
	@RequestMapping(value="/livemeetingquestions", method=RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void meetingUpdate(@RequestBody MeetingUpdateEntity meetingUpdate) throws ParseException{
		log.info("Meeting update received  : "+meetingUpdate);
	}
}
