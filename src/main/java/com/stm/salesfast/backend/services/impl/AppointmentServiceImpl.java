package com.stm.salesfast.backend.services.impl;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.controllers.LoginController;
import com.stm.salesfast.backend.dao.specs.AlignmentsDao;
import com.stm.salesfast.backend.dao.specs.AppointmentDao;
import com.stm.salesfast.backend.dto.AppointmentDto;
import com.stm.salesfast.backend.dto.MeetingUpdateDto;
import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.entity.AlignedPhysicianFollowUpEntity;
import com.stm.salesfast.backend.entity.AppointmentCountPerDayEntity;
import com.stm.salesfast.backend.entity.AppointmentEntity;
import com.stm.salesfast.backend.entity.MeetingStatusCountEntity;
import com.stm.salesfast.backend.entity.PastAppointmentEntity;
import com.stm.salesfast.backend.entity.PitchViewEntity;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.MeetingExperienceService;
import com.stm.salesfast.backend.services.specs.MeetingUpdateService;
import com.stm.salesfast.backend.services.specs.NotificationService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.PitchesService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.services.specs.UserAccountService;
import com.stm.salesfast.backend.services.specs.UserDetailService;
import com.stm.salesfast.backend.utils.SalesFastEmail;
import com.stm.salesfast.backend.utils.SalesFastEmailSendGridImpl;
import com.stm.salesfast.backend.utils.SalesFastUtilities;
import com.stm.salesfast.constant.ConstantValues;
import com.stm.salesfast.constant.SessionConstants;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	private Logger log = LoggerFactory.getLogger(AppointmentServiceImpl.class.getName());

	String CURRENTUSERNAME = "";
	
	@Autowired
	AppointmentDao appointmentDao;
	
	@Autowired
	UserAccountService userAccountService;
	
	@Autowired
	AlignmentFetchService alignmentFetchService;
	
	@Autowired
	PhysicianFetchService physicianService;
	
	@Autowired
	ProductFetchService productFetchService;
	
	@Autowired
	UserDetailService userDetails;

	@Autowired
	NotificationService notificationService;
	
	@Autowired
	MeetingUpdateService meetingUpdate;

	@Autowired
	MeetingExperienceService meetingExp;

	@Autowired
	PitchesService pitch;
	
	@Override
	public void addAppointment(int physId, Time startTime, Time endTime, Date date,  String confirmationStatus, int productId, String additionalNotes) throws ParseException {
		// TODO Auto-generated method stub
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CURRENTUSERNAME = user.getUsername(); //get logged in user name
		int userId = userAccountService.getUserIdByUserName(CURRENTUSERNAME);
		UserDto userDetail = userDetails.getUserDetails(userId);
		String zip = physicianService.getPhysicianZipById(physId);
		appointmentDao.insertAppointment(new AppointmentDto(startTime, endTime, date, physId, userId, productId,confirmationStatus, zip, new String(""), additionalNotes, false, false, false, false));
		
		/* Send confirmation email to physician */
		int appointmentId = appointmentDao.getIdByPhysIdUserIdProductId(physId, userId, productId);
		if(confirmationStatus.equals("CONFIRMED")){ 
			String emailText = "Your appointment has been fixed with "+(userDetails.getUserDetails(userId).getFirstName() + " " +userDetails.getUserDetails(userId).getLastName())+" at "
					+startTime+" on "+ date + ". If you wish to cancel, please visit following link: \n "
					+ "http://127.0.0.1:8080/yourappointment?id="+appointmentId;
			String emailSub = "Your appointment has been confirmed";
			sendMail( emailSub, emailText, userDetail.getEmail());
		}
		
	}
	
	
	/**
	 * This method returns CURRENT DAY's appointments of a user based on whether
	 * he/she has entered meeting update or meeting experience details. Also confirms
	 * if the appointment status is confirmed 
	 * @throws ParseException 
	 * */
	@Override
	public List<AppointmentEntity> getTodaysAppointmentToShow(int userId) throws ParseException {
		// TODO Auto-generated method stub
		List<AppointmentDto> appointmentDtos = appointmentDao.getAppointmentByUserId(userId);
		List<AppointmentEntity> todaysAppointmentEntitiesList = new ArrayList<>();
		for(AppointmentDto eachAppointment : appointmentDtos){
			PhysicianStgDto physicianDto = physicianService.getPhysicianById(eachAppointment.getPhysicianId());
			ProductDto productDto = productFetchService.getProductById(eachAppointment.getProductId());
			if(eachAppointment.getDate().getTime() == SalesFastUtilities.getCurrentDate().getTime()){
				todaysAppointmentEntitiesList.add(new AppointmentEntity(eachAppointment.getAppointmnetId(),
						physicianDto.getPhysicianId(),
						physicianDto.getFirstName()+" "+physicianDto.getLastName(), 
						physicianDto.getAddressLineOne()+" "+physicianDto.getAddressLineTwo()+" "+physicianDto.getCity()+"-"+physicianDto.getZip(),
						physicianDto.getContactNumber(), 
						physicianDto.getEmail(), 
						eachAppointment.getConfirmationStatus(),
						eachAppointment.getStartTime(),
						eachAppointment.getEndTime(),
						eachAppointment.getDate(),
						productDto.getProductName(),
						eachAppointment.isHasMeetingUpdate(),
						eachAppointment.isHasMeetingExperienceFromSR(),
						eachAppointment.getCancellationReason(),
						eachAppointment.getAdditionalNotes(),
						eachAppointment.isHasPitch()));
			}
		}
		log.info("Appointment fetched are : \n");
		return todaysAppointmentEntitiesList;
	}

	/**
	 * This method returns FUTURE DATE's appointments of a user based on whether
	 * he/she has entered meeting update or meeting experience details 
	 * @throws ParseException 
	 * */
	@Override
	public List<AppointmentEntity> getFutureAppointmentToShow(int userId) throws ParseException {
		// TODO Auto-generated method stub
		List<AppointmentDto> appointmentDtos = appointmentDao.getAppointmentByUserId(userId);
		List<AppointmentEntity> futureAppointmentEntitiesList = new ArrayList<>();
		for(AppointmentDto eachAppointment : appointmentDtos){
			PhysicianStgDto physicianDto = physicianService.getPhysicianById(eachAppointment.getPhysicianId());
			ProductDto productDto = productFetchService.getProductById(eachAppointment.getProductId());
			if(eachAppointment.getDate().getTime() > SalesFastUtilities.getCurrentDate().getTime()){
				futureAppointmentEntitiesList.add(new AppointmentEntity(eachAppointment.getAppointmnetId(),
						physicianDto.getPhysicianId(),
						physicianDto.getFirstName()+" "+physicianDto.getLastName(), 
						physicianDto.getAddressLineOne()+" "+physicianDto.getAddressLineTwo()+" "+physicianDto.getCity()+"-"+physicianDto.getZip(),
						physicianDto.getContactNumber(), 
						physicianDto.getEmail(), 
						eachAppointment.getConfirmationStatus(),
						eachAppointment.getStartTime(),
						eachAppointment.getEndTime(),
						eachAppointment.getDate(),
						productDto.getProductName(),
						eachAppointment.isHasMeetingUpdate(),
						eachAppointment.isHasMeetingExperienceFromSR(),
						eachAppointment.getCancellationReason(),
						eachAppointment.getAdditionalNotes(),
						eachAppointment.isHasPitch()));
			}
		}
		return futureAppointmentEntitiesList;
	}

	/**
	 * This method returns PAST DATE's appointments of a user based  
	 * @throws ParseException 
	 * */
	@Override
	public List<PastAppointmentEntity> getPastAppointmentToShow(int userId) throws ParseException {
		// TODO Auto-generated method stub
		List<AppointmentDto> appointmentDtos = appointmentDao.getPastAppointmentByUserId(userId);
		List<PastAppointmentEntity> pastAppointmentEntitiesList = new ArrayList<>();
		for(AppointmentDto eachAppointment : appointmentDtos){
			PhysicianStgDto physicianDto = physicianService.getPhysicianById(eachAppointment.getPhysicianId());
			ProductDto productDto = productFetchService.getProductById(eachAppointment.getProductId());
			MeetingUpdateDto meetingUpdateDto = meetingUpdate.getMeetingUpdateByAppointmentId(eachAppointment.getAppointmnetId());
			String meetingStatus = (meetingUpdateDto == null) ? "STATUS NA" : meetingUpdateDto.getStatus();
			pastAppointmentEntitiesList.add(new PastAppointmentEntity(
						physicianDto.getPhysicianId(),
						physicianDto.getFirstName()+" "+physicianDto.getLastName(),
						productDto.getProductId(),
						productDto.getProductName(),
						eachAppointment.getDate(),
						meetingStatus,
						meetingExp.salesRepResponse(eachAppointment.getAppointmnetId()),
						meetingExp.physicianResponse(eachAppointment.getAppointmnetId()),
						pitch.getPitchForAppointment(eachAppointment.getAppointmnetId())
					));
		}
		return pastAppointmentEntitiesList;
	}
	
	/**
	 * This method returns ALL appointments of a user based on whether
	 * he/she has entered meeting update or meeting experience details 
	 * @throws ParseException 
	 * */
	@Override
	public List<AppointmentEntity> getAllAppointmentToShow(int userId) {
		// TODO Auto-generated method stub
		List<AppointmentDto> appointmentDtos = appointmentDao.getAppointmentByUserId(userId);
		List<AppointmentEntity> futureAppointmentEntitiesList = new ArrayList<>();
		for(AppointmentDto eachAppointment : appointmentDtos){
			PhysicianStgDto physicianDto = physicianService.getPhysicianById(eachAppointment.getPhysicianId());
			ProductDto productDto = productFetchService.getProductById(eachAppointment.getProductId());
				futureAppointmentEntitiesList.add(new AppointmentEntity(eachAppointment.getAppointmnetId(),
						physicianDto.getPhysicianId(),
						physicianDto.getFirstName()+" "+physicianDto.getLastName(), 
						physicianDto.getAddressLineOne()+" "+physicianDto.getAddressLineTwo()+" "+physicianDto.getCity()+"-"+physicianDto.getZip(),
						physicianDto.getContactNumber(), 
						physicianDto.getEmail(), 
						eachAppointment.getConfirmationStatus(),
						eachAppointment.getStartTime(),
						eachAppointment.getEndTime(),
						eachAppointment.getDate(),
						productDto.getProductName(),
						eachAppointment.isHasMeetingUpdate(),
						eachAppointment.isHasMeetingExperienceFromSR(),
						eachAppointment.getCancellationReason(),
						eachAppointment.getAdditionalNotes(),
						eachAppointment.isHasPitch()));
		}
		return futureAppointmentEntitiesList;
	}
	
	
	/**
	 * This method returns ALL appointments of a user based on whether
	 * he/she has entered meeting update or meeting experience details
	 * and appointments belonging to a particular DATE 
	 * @throws ParseException 
	 * */
	@Override
	public List<AppointmentEntity> getAllAppointmentForADate(int userId, Date date) {
		// TODO Auto-generated method stub
		List<AppointmentDto> appointmentDtos = appointmentDao.getAppointmentByUserIdForADate(userId, date);
		List<AppointmentEntity> futureAppointmentEntitiesList = new ArrayList<>();
		for(AppointmentDto eachAppointment : appointmentDtos){
			PhysicianStgDto physicianDto = physicianService.getPhysicianById(eachAppointment.getPhysicianId());
			ProductDto productDto = productFetchService.getProductById(eachAppointment.getProductId());
				futureAppointmentEntitiesList.add(new AppointmentEntity(eachAppointment.getAppointmnetId(),
						physicianDto.getPhysicianId(),
						physicianDto.getFirstName()+" "+physicianDto.getLastName(), 
						physicianDto.getAddressLineOne()+" "+physicianDto.getAddressLineTwo()+" "+physicianDto.getCity()+"-"+physicianDto.getZip(),
						physicianDto.getContactNumber(), 
						physicianDto.getEmail(), 
						eachAppointment.getConfirmationStatus(),
						eachAppointment.getStartTime(),
						eachAppointment.getEndTime(),
						eachAppointment.getDate(),
						productDto.getProductName(),
						eachAppointment.isHasMeetingUpdate(),
						eachAppointment.isHasMeetingExperienceFromSR(),
						eachAppointment.getCancellationReason(),
						eachAppointment.getAdditionalNotes(),
						eachAppointment.isHasPitch()));
		}
		return futureAppointmentEntitiesList;
	}
	
	
	@Override
	public int getAppointmentId(String username, int physicianId) {
		// TODO Auto-generated method stub
		int userId = userAccountService.getUserAccountByUserName(username).getUserId();
		return appointmentDao.getIdByPhysIdUserId(userId, physicianId);
	}
	
	@Override
	public int getAppointmentId(String username, int physicianId, int productId) {
		// TODO Auto-generated method stub
		int userId = userAccountService.getUserAccountByUserName(username).getUserId();
		return appointmentDao.getIdByPhysIdUserIdProductId(userId, physicianId,productId);
	}

	@Override
	public AppointmentDto getById(int appointmentId) {
		// TODO Auto-generated method stub
		return appointmentDao.getAppointmentById(appointmentId);
	}


	@Override
	public void setHasMeetingUpdateFlag(int appointmentId, int flag) {
		appointmentDao.setMeetinUpdateFlag(appointmentId, flag);
	}

	@Override
	public void setHasMeetingExperienceFlagFromSR(int appointmentId, int isSalesRepEntry) {
		appointmentDao.setMeetinExperienceFlagFromSR(appointmentId, isSalesRepEntry);
	}
	
	@Override
	public void setHasMeetingExperienceFlagFromPH(int appointmentId,  int isPhyEntry) {
		appointmentDao.setMeetinExperienceFlagFromPH(appointmentId, isPhyEntry);
	}
	
	@Override
	public void setHasPitchFlag(int appointmentId) {
		appointmentDao.updatePitchFlagTrue(appointmentId);
	}
	
	@Override
	public List<AppointmentDto> getFollowUpAppointments(int userId){
		return appointmentDao.getAppointmentByStatus("FOLLOW UP", userId);
	}
	
	@Override
	public void cancelAppointment(int appointmentId, String reason){
		appointmentDao.updateStatus(appointmentId,"CANCELLED", reason);
		
		AppointmentDto appointment = appointmentDao.getAppointmentById(appointmentId);
		UserDto salesrep = userDetails.getUserDetails(appointment.getUserId());
		
		/*Send notification email to SalesRep*/
		int physicianId = appointmentDao.getAppointmentById(appointmentId).getPhysicianId();
		PhysicianStgDto physician = physicianService.getPhysicianById(physicianId); 
		String physicianName = physician.getFirstName() + " "+ physician.getLastName();
		
		String emailText = "Physician "+ physicianName +" cancelled the appointment at "+appointment.getStartTime()+" on "
						+ appointment.getDate()+". The reason for cancellation is - "+reason+". Call him at "+physician.getContactNumber()
						+ " to confirm.";
		String emailSub = "Appointment cancelled by Dr."+physicianName;
		sendMail( emailSub, emailText, salesrep.getEmail());
		/*Notification for sales rep*/
		notificationService.insertNotificationAppointmentCancellation(appointment.getUserId(), physicianName, "CANCELLED APPOINTMENTS BY PHYS");
		
		/*Send cancellation confirmation to physician*/
		String emailText2 = "You have cancelled the appointment with "+salesrep.getFirstName()+" "+salesrep.getLastName()
				+ ". To fix again, you can call him at "+salesrep.getContactNumber();
		String emailSub2 = "Appointment cancelled";
		sendMail( emailSub2, emailText2, physician.getEmail());
	}
	
	@Override
	public void cancelAppointmentBySR(int appointmentId, String reason){
		appointmentDao.updateStatus(appointmentId,"CANCELLED", reason);
		
		AppointmentDto appointment = appointmentDao.getAppointmentById(appointmentId);
		UserDto salesrep = userDetails.getUserDetails(appointment.getUserId());
		PhysicianStgDto physician = physicianService.getPhysicianById(appointment.getPhysicianId());
		
		/*Send notification email to Physician*/
		String physicianName = physician.getFirstName() + " "+ physician.getLastName();
		String salesRepName = salesrep.getFirstName()+" "+salesrep.getLastName();
		
		String emailText = "BioPharma Sales Representative Mr. "+salesRepName+" had to "
				+ "cancel the appointment he had fixed for "+appointment.getDate()+ " at "
				+ appointment.getStartTime()+ " for following reason : "+reason+". Apologies "
				+ "the inconvinience. He will get in touch with you again to book appointment "
				+ "for some other time & day. Thanks!";
		String emailSub = "Appointment cancelled by SalesRep."+salesRepName;
		sendMail( emailSub, emailText, physician.getEmail());
		
		/*Notification for physician - if e-detailing*/
		UserDto physAsUser = userDetails.getUserForPhysicianId(physician.getPhysicianId());
		if (physAsUser!=null){
			notificationService.insertNotificationAppointmentCancellationBySR(physAsUser.getUserId(), salesRepName, "CANCELLED APPOINTMENTS");
		}
		
		/*Send cancellation confirmation to SalesRep*/
		String emailText2 = "You have cancelled the appointment with Dr."+physicianName
				+ ". To fix again, you can call him at "+physician.getContactNumber();
		String emailSub2 = "Appointment cancelled";
		sendMail( emailSub2, emailText2, salesrep.getEmail());
	}
	
	@Override
	public void updateFollowUpAppointmentStatus(Time startTime, Time endTime, Date date, String status, String additionalNotes, int appointmentId){
		appointmentDao.updateAppointment(startTime, endTime, date, status, additionalNotes, appointmentId);
	}
	
	@Override
	public void updateFutureAppointmentStatus(Time startTime, Time endTime, Date date, String status, String additionalNotes, int appointmentId){
		appointmentDao.updateAppointment(startTime, endTime, date, status, additionalNotes, appointmentId);
		
		AppointmentDto appointment = appointmentDao.getAppointmentById(appointmentId);
		UserDto salesrep = userDetails.getUserDetails(appointment.getUserId());
		PhysicianStgDto physician = physicianService.getPhysicianById(appointment.getPhysicianId());
		
		/*Send notification email to Physician*/
		String salesRepName = salesrep.getFirstName()+" "+salesrep.getLastName();
		
		String emailText = "BioPharma Sales Representative Mr. "+salesRepName+" has "
				+ "rescheduled an appointment for "+appointment.getDate()+ " at "
				+ appointment.getStartTime() + ".";
		String emailSub = "Appointment Rescheduled by SalesRep."+salesRepName;
		sendMail( emailSub, emailText, physician.getEmail());
		
		/*Notification for physician - if e-detailing*/
		UserDto physAsUser = userDetails.getUserForPhysicianId(physician.getPhysicianId());
		if (physAsUser!=null){
			notificationService.insertNotificationAppointmentReschedulingBySR(physAsUser.getUserId(), salesRepName, 
					appointment.getDate().toString(), appointment.getStartTime().toString(),"RESCHEDULED APPOINTMENTS");
		}
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
	public List<AlignedPhysicianFollowUpEntity> followUpAppointmentsToShow() throws ParseException{
		log.info("Appointments to follow up");
		List<AlignedPhysicianFollowUpEntity> followUpAppointments = new ArrayList<>();
		List<AppointmentDto> followUpAppointmentDto = getFollowUpAppointments(SessionConstants.USER_ID);
		log.info("Number of follow up appointments : "+followUpAppointmentDto.size());
		for(AppointmentDto appointment : followUpAppointmentDto){
			PhysicianStgDto physician = physicianService.getPhysicianById(appointment.getPhysicianId());
			if(appointment.getDate().getTime() == SalesFastUtilities.getCurrentDate().getTime()){
				followUpAppointments.add(new AlignedPhysicianFollowUpEntity(appointment.getAppointmnetId(),
						appointment.getPhysicianId(),
						physician.getFirstName(),
						physician.getLastName(),
						physician.getEmail(),
						physician.getContactNumber(),
						physician.getAddressLineOne(),
						physician.getAddressLineTwo(),
						physician.getCity(),
						physician.getState(),
						physician.getZip(),
						physician.getMedicalField(),
						physician.isNew(),
						appointment.getConfirmationStatus(),
						appointment.getProductId(),
						productFetchService.getProductById(appointment.getProductId()).getProductName(),
						physician.getImportanceFactor(),
						appointment.getDate(),
						appointment.getStartTime(),
						appointment.getEndTime(),
						appointment.getAdditionalNotes()
				));
				
			}
		}
		
		return followUpAppointments;
	}
	
	/**
	 * Returns the list of appointments for a physician
	 * that are not CANCELLED. These will be shown on 
	 * meetings page of physician's portal 
	 * */
	@Override
	public List<AppointmentDto> getAppointmentsForPhysician(String confirmationStatus1, String confirmationStatus2, int physicianId){
		List<AppointmentDto> appointments = appointmentDao.getAppointmentForPhysician(confirmationStatus1,confirmationStatus1, physicianId);
		log.info("No Appointments fetched : "+appointments.size());
		return appointments;
	}
	
	/**
	 * Physicians who said they were not interested even before meeting
	 * the sales rep
	 * */
	@Override
	public List<Integer> getPhysiciansNotInterestedBeforeDetailing(int userId){
		return appointmentDao.getNotInterestedPhysicians(userId);
	}
	
	@Override
	public List<AppointmentDto> getAppointmentsByMedicalField(String medicalFieldId){
		return appointmentDao.getAppointmentByMedicalField(medicalFieldId);
	}
	
	@Override
	public List<AppointmentDto> getAppointmentsByProduct(int productId){
		return appointmentDao.getAppointmentByProduct(productId);
	}
	@Override
	public List<AppointmentDto> getAppointmentsBySalesRep(int userId){
		return appointmentDao.getAppointmentBySalesRep(userId);
	}
	@Override
	public List<AppointmentDto> getAppointmentsByPhysician(int physicianId){
		return appointmentDao.getAppointmentByPhysician(physicianId);
	}
	@Override
	public List<AppointmentDto> getAllAppointmentsHavingPitch(){
		return appointmentDao.getAllAppointmentHavingPitch();
	}
	
	@Override
	public String getNotInterestedAppointmentStatus(int physicianId, int userId){
		return appointmentDao.getNotInterestedStatus(physicianId, userId);
	}
	
	@Override
	public AppointmentDto getAppointmentPhysIdUserIdProductId(int physicianId, int userId, int productId){
		int appointmentId = appointmentDao.getIdByPhysIdUserIdProductId(physicianId, userId, productId);
		return appointmentDao.getAppointmentById(appointmentId);
	}
	
	@Override
	public List<AppointmentCountPerDayEntity> getPerDayAppointmentCountByPerformance(int userId, String status){
		return appointmentDao.getCountPerDayPerformanceBased(userId, status);
	}
	
	@Override
	public List<MeetingStatusCountEntity> getAppointmentStatusCount(int userId){
		return appointmentDao.getAppointmentStatusCount(userId);
	}
} 


