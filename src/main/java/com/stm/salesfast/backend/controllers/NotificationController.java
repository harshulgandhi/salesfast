package com.stm.salesfast.backend.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stm.salesfast.backend.entity.AlignedPhysicianEntity;
import com.stm.salesfast.backend.entity.NotificationEntity;
import com.stm.salesfast.backend.services.specs.NotificationService;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.constant.SessionConstants;

@Controller
public class NotificationController {
	
	private static String CURRENTUSERNAME = "";
	
	private Logger log = LoggerFactory.getLogger(NotificationController.class.getName());
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	UserAccountService userAccountService;
	
	
	@RequestMapping(value="/shownotifications", method=RequestMethod.GET)
	public String showAlignments(Model model){
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CURRENTUSERNAME = user.getUsername(); //get logged in user name
	    log.info("\nLogged in user is : "+CURRENTUSERNAME+" and his role is "+user.getAuthorities());
		
		List<NotificationEntity> notificationEntities = notificationService.getAlreadyReadNotifications(
				(userAccountService.getUserAccountByUserName(CURRENTUSERNAME)).getUserId());
		model.addAttribute("listOfNotifications", notificationEntities);
		return "notifications";
	}
	
	@RequestMapping(value="/deletenotification", method=RequestMethod.POST)
	@ResponseBody
	public void cancelAppointment( @RequestParam(value="notificationId") int notificationId){
		log.info("Delete notification with id "+notificationId);
		notificationService.deleteNotification(notificationId);
	}
	
	@RequestMapping(value="/getnotificationcount", method=RequestMethod.GET)
	@ResponseBody
	public int[] notificationCount(){
		int notificationCount = notificationService.getNotificationCountForUser(SessionConstants.USER_ID);
		return new int[]{notificationCount};
	}
}
