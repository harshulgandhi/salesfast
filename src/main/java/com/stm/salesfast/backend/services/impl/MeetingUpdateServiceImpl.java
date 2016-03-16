package com.stm.salesfast.backend.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dao.specs.MeetingUpdateDao;
import com.stm.salesfast.backend.dto.EDetailingMaterialDto;
import com.stm.salesfast.backend.dto.MeetingUpdateDto;
import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.dto.UserAccountDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.dto.UserToRoleDto;
import com.stm.salesfast.backend.entity.MeetingUpdateEntity;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.EDetailingMaterialService;
import com.stm.salesfast.backend.services.specs.MeetingUpdateService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.services.specs.RoleService;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.backend.services.specs.UserDetailService;
import com.stm.salesfast.backend.services.specs.UserToRoleService;
import com.stm.salesfast.backend.utils.SalesFastEmail;
import com.stm.salesfast.backend.utils.SalesFastEmailSendGridImpl;
import com.stm.salesfast.backend.utils.SalesFastUtilities;

@Service
public class MeetingUpdateServiceImpl implements MeetingUpdateService {
	private Logger log = LoggerFactory.getLogger(MeetingUpdateServiceImpl.class.getName());
	
	@Autowired
	ProductFetchService productService;
	
	@Autowired
	MeetingUpdateDao meetingUpdateDao;
	
	@Autowired
	AppointmentService appointmentService;
	
	@Autowired
	PhysicianFetchService physicianService;
	
	@Autowired
	UserDetailService userService;
	
	@Autowired
	UserAccountService userAccountService;
	
	@Autowired
	RoleService roles;
	
	@Autowired
	UserToRoleService userRole;
	
	@Autowired
	EDetailingMaterialService eDetailingMatService;
	
	/**
	 * Method to add meeting update to db, also updates
	 * has hasMeetingUpdate flag and physician status
	 * */
	@Override
	public void insertMeetingUpdate(MeetingUpdateEntity meetingUpdateEntity) throws ParseException {
		// TODO Auto-generated method stub
		ProductDto product = productService.getProductByName(meetingUpdateEntity.getProductName());
		meetingUpdateDao.insert(new MeetingUpdateDto(
				SalesFastUtilities.getCurrentDate(), 
				meetingUpdateEntity.getMeetingStatus(),
				Boolean.parseBoolean(meetingUpdateEntity.getIsEDetailing()),
				meetingUpdateEntity.getPhysicianId(),
				product.getProductId(),
				product.getMedicalFieldId(),
				meetingUpdateEntity.getAppointmentId()));
		
		appointmentService.setHasMeetingUpdateFlag(meetingUpdateEntity.getAppointmentId(), 1);
		physicianService.updatePhysicianStatus(meetingUpdateEntity.getPhysicianId(), meetingUpdateEntity.getMeetingStatus());
		setupEDetailing(meetingUpdateEntity);
	}
	
	/**
	 * Check if edetailing required. If yes, generate credentials
	 * for phys, send email to him and enter user account details for 
	 * this phys in db
	 * */
	@Override
	public void setupEDetailing(MeetingUpdateEntity meetingUpdateEntity){
		
		if(Boolean.parseBoolean(meetingUpdateEntity.getIsEDetailing()) && !meetingUpdateEntity.getMeetingStatus().equals("LOST")){
			PhysicianStgDto physician = physicianService.getPhysicianById(meetingUpdateEntity.getPhysicianId());
			
			/*If physician already exists as a user of SalesFast
			 * */
			if(userService.checkIfUserExists(physician.getFirstName(), physician.getLastName(), physician.getEmail())){
				log.info("Set up NOT needed");
				UserAccountDto userAccount = userAccountService.getUserAccountByUserId(
						userService.getUserIdByName(physician.getFirstName()
								, physician.getLastName()
								, physician.getEmail()));
				//Send email saying "Log in to enter meeting experience and check out onine documentation about our products"
				String emailSub = "SalesFast - Feedback for meeting experience and checkout latest products";
				String emailBody = "Please provide your valuable feedback about your last meeting with"
						+ "BioPharma Sales Representative by logging into your SalesFast account at"
						+ "http://127.0.0.1:8080/login.";
				sendMail(emailSub, emailBody, physician.getEmail());
				//Add product to for eDetailing
				eDetailingMatService.insert(new EDetailingMaterialDto(
						meetingUpdateEntity.getProductName()+".pdf",
						meetingUpdateEntity.getPhysicianId(),
						physician.getMedicalField(),
						productService.getProductByName(meetingUpdateEntity.getProductName()).getProductId()
					));
				
			}
			/*
			 * Physician getting added as a salesfast user for the first
			 * time.
			 * */
			else{
				log.info("Set up needed");
				//Insert into user, useraccount, userToRole
				userService.insertUserDetails(new UserDto(physician.getFirstName(),
											physician.getLastName(),
											physician.getEmail(),
											physician.getContactNumber(),
											physician.getAddressLineOne(),
											physician.getAddressLineTwo(),
											physician.getCity(),
											physician.getState(),
											physician.getZip(),
											null, null));
				int userId = userService.getUserIdByName(physician.getFirstName(), physician.getLastName(), physician.getEmail());
				String password = physician.getLastName().toLowerCase() + SalesFastUtilities.generateRandomNumber();
				userAccountService.insertNewUserAccount(new UserAccountDto(physician.getFirstName().toLowerCase(),password, userId));
				userRole.insertUserToRoleMapping(new UserToRoleDto(userId, roles.getBy("PH").getRoleId()));
				
				//Send email welcoming physician to BioPharma family with username and password
				String emailSub = "Welcome to BioPharma's special physicians family!";
				String emailBody = "Welcome to BioPharma's SalesFast tool. You can use this tool "
						+ "to provide feedback about each meeting you have with our SalesReps, which "
						+ "will help us serve you better and efficiently. In addition, this tool allows "
						+ "you to go through latest products the BioPharma has released, saving your "
						+ "precious time from Detailing Meetings. Your login information is as mentioned below:  \n"
						+ "URL : http://127.0.0.1:8080/login\n"
						+ "User Name : "+physician.getFirstName().toLowerCase()+"\n"
						+ "Password : "+password;
				sendMail(emailSub, emailBody, physician.getEmail());
				
				//Add product to for eDetailing
				eDetailingMatService.insert(new EDetailingMaterialDto(
							meetingUpdateEntity.getProductName()+".pdf",
							meetingUpdateEntity.getPhysicianId(),
							physician.getMedicalField(),
							productService.getProductByName(meetingUpdateEntity.getProductName()).getProductId()
						));
			}
		}
	}
	
	@Override
	public MeetingUpdateDto getMeetingUpdateByAppointmentId(int appointmentId) {
		// TODO Auto-generated method stub
		return meetingUpdateDao.getByAppointmentId(appointmentId);
	}

	@Override
	public List<Integer> getPrescribingProduct(int physicianId) {
		// TODO Auto-generated method stub
		List<MeetingUpdateDto> prescribingRecords = meetingUpdateDao.getByPrescribingPhysicians(physicianId);
		List<Integer> prescribingProducts = new ArrayList<>();
		for(MeetingUpdateDto eachRecord : prescribingRecords){
			prescribingProducts.add(eachRecord.getProductId());
		}
		return prescribingProducts;
	}

	/**
	 * Method to send email using send grid api
	 * */
	@Override
	public void sendMail(String subject, String body, String toEmailId){
		SalesFastEmail email = new SalesFastEmailSendGridImpl();
		email.setToEmailId(toEmailId);
		email.setFromEmail("no-reply@biopharma.com");
		email.setFromName("BioPharma SalesForce");
		email.addSubject(subject);
		
		email.addTextBody(body);
		log.info("Sending confirmation email with content as :\n"+body);
		email.sendMail();
	}
	
	@Override
	public List<MeetingUpdateDto> getForPhysiciansPortal(String status1, String status2, int physicianId) {
		return meetingUpdateDao.getForPhysiciansPortal(status1, status2, physicianId);
	}
	
	@Override
	public List<Integer> getLostPhysiciansForAUser(int userId){
		return meetingUpdateDao.getLostPhysiciansForUser(userId);
	}
	
	@Override
	public List<Integer> getPrescribingPhysiciansForAUser(int userId){
		return meetingUpdateDao.getPrescribingPhysiciansForUser(userId);
	}
}
