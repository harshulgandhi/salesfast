package com.stm.salesfast.backend.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stm.salesfast.backend.dto.EDetailingMaterialDto;
import com.stm.salesfast.backend.dto.TrainingMaterialDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.entity.NewProductEntity;
import com.stm.salesfast.backend.services.specs.AddNewProductService;
import com.stm.salesfast.backend.services.specs.AlignmentCreationService;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.AppointmentService;
import com.stm.salesfast.backend.services.specs.EDetailingMaterialService;
import com.stm.salesfast.backend.services.specs.MeetingUpdateService;
import com.stm.salesfast.backend.services.specs.NotificationService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.services.specs.TrainingMaterialService;
import com.stm.salesfast.backend.services.specs.UserDetailService;
import com.stm.salesfast.backend.utils.SalesFastEmail;
import com.stm.salesfast.backend.utils.SalesFastEmailSendGridImpl;
import com.stm.salesfast.constant.ConstantValues;

@Service
public class AddNewProductServiceImpl implements AddNewProductService {
	
	private Logger log = LoggerFactory.getLogger(AddNewProductServiceImpl.class.getName());
	
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
	private AlignmentCreationService alignmentCreation;
	
	@Autowired
	ProductFetchService productService;
	
	@Autowired
	EDetailingMaterialService edailingService;
	
	@Autowired
	NotificationService notification;
	
	@Autowired
	UserDetailService userService;
	
	@Autowired
	PhysicianFetchService physService;
	
	@Autowired
	TrainingMaterialService training;
	
	@Autowired
	AlignmentFetchService alignmentService;
	
	@Autowired
	MeetingUpdateService meetingUpdate;
	
	@Autowired
	AppointmentService appointmentService;
	
	@Override
	public void saveUploadedFiles(MultipartFile virtualTrainingFile, MultipartFile eDetailingFile) throws IllegalStateException, IOException {
		//Saving training material file
		String realPathtoUploads =   request.getServletContext().getRealPath("/resources/");
		String orgName = "docs\\virtuallearning\\"+virtualTrainingFile.getOriginalFilename();
        String filePath = realPathtoUploads + orgName;
        File dest = new File(filePath);
        virtualTrainingFile.transferTo(dest);
        
        //Saving training material file
		String realPathtoUploads2 =  request.getServletContext().getRealPath("/resources/");
		String orgName2 = "docs\\edetailing\\"+virtualTrainingFile.getOriginalFilename();
		String filePath2 = realPathtoUploads2 + orgName2;
		File dest2 = new File(filePath2);
		eDetailingFile.transferTo(dest2);
	}
	
	@Override
	public void recalculateAlignments() throws IOException{
		alignmentCreation.createAlignments();
	}
	
	/*
	 * Primary method where new product is added to the system
	 * */
	@Override
	public void addNewProduct(NewProductEntity newProduct) throws IOException{
		productService.insertNewProduct(newProduct);
		recalculateAlignments();
		
		
		List<Integer> physicianIds = edailingService.getPhysiciansInEdetailingForMedicalField(newProduct.getMedicalFieldId());
		updatesAboutNewProductToPhysicians(physicianIds, newProduct);
		
		List<Integer> userIds = alignmentService.getUserIdsWorkingInMedicalField(newProduct.getMedicalFieldId());
		updatesAboutNewProductToSalesReps(userIds, newProduct);
		
	}
	
	
	/**
	 * Method to update system for notifications sent to all relevant
	 * physicians for a new product launch
	 * */
	@Override
	public void updatesAboutNewProductToPhysicians(List<Integer> physiciansToBeUpdated, NewProductEntity newProduct){
		for(Integer eachPhysicianId : physiciansToBeUpdated){
			//insert into eDetailingMaterial table
			edailingService.insert(new EDetailingMaterialDto(
						newProduct.getProductName()+".pdf",
						eachPhysicianId,
						newProduct.getMedicalFieldId(),
						productService.getProductByName(newProduct.getProductName()).getProductId()
					));
			
			//insert into notifications for physician
			UserDto physicianAsAUser = userService.getUserForPhysicianId(eachPhysicianId);
			notification.insertNotificationNewProductPhysician(physicianAsAUser.getUserId(), newProduct.getProductName(), "NEW PRODUCT");
			
			//send email to physician
			String emailBody = "We have released new product "+newProduct.getProductName()+" which might interest you. Please visit your eDetailing "
					+ "portal at http://127.0.0.1/edetailing to know more abou the product. You can contact our Sales Representative for further details."
					+ "Sales Rep's contact details can found on the e-detailing page itself.";
			String subject = "New product launched by BioPharma";
			sendNewProductNotificationEmails(subject, emailBody, physicianAsAUser.getEmail());
		}
	}
	
	/**
	 * Method to update system for notifications sent to SalesReps
	 * for a new product launch
	 * */
	@Override
	public void updatesAboutNewProductToSalesReps(List<Integer> usersToBeUpdated, NewProductEntity newProduct){
		for(Integer eachUserId : usersToBeUpdated){
			//insert into virtual learning material table
			training.insert(new TrainingMaterialDto(
						newProduct.getProductName()+".pdf",
						eachUserId,
						newProduct.getMedicalFieldId(),
						productService.getProductByName(newProduct.getProductName()).getProductId()
					));
			
			//insert into notifications for sales reps
			
			//for all lost physicians
			List<Integer> lostPhysicians = meetingUpdate.getLostPhysiciansForAUser(eachUserId);
			UserDto user = userService.getUserDetails(eachUserId);
			for(Integer eachPhysicianId : lostPhysicians){
				notification.insertNotificationNewProductSalesRep(eachUserId, newProduct.getProductName(), physService.getPhysicianName(eachPhysicianId), "NEW PRODUCT");
				
				//send email to sales rep
				String emailBody = "We have released new product "+newProduct.getProductName()+". Dr. "+physService.getPhysicianName(eachPhysicianId)
						+" did not show much interest last time for other previous products. Try to fix "
						+ "appointment with him from 'Alignments' page on SalesFast.";
				String subject = "New product - detail lost physicians";
				sendNewProductNotificationEmails(subject, emailBody, user.getEmail());
			}
			
			//for all not interested physicians - before appointment
			List<Integer> notInterestedPhysicians = appointmentService.getPhysiciansNotInterestedBeforeDetailing(eachUserId);
			for(Integer eachPhysicianId : notInterestedPhysicians){
				notification.insertNotificationSalesRepPhysNotInterest(eachUserId, newProduct.getProductName(), physService.getPhysicianName(eachPhysicianId), "NEW PRODUCT");
				
				//send email to sales rep
				String emailBody = "We have released new product "+newProduct.getProductName()+". Dr. "+physService.getPhysicianName(eachPhysicianId)
						+" said he was not interested in our previous product, even before appointment. Try to fix "
						+ "appointment with him from 'Alignments' page on SalesFast.";
				String subject = "New product - detail not interested physicians";
				sendNewProductNotificationEmails(subject, emailBody, user.getEmail());
			}
			
			//for all prescribing physicians
			List<Integer> prescribingPhysicians = meetingUpdate.getPrescribingPhysiciansForAUser(eachUserId);
			for(Integer eachPhysicianId : prescribingPhysicians){
				notification.insertNotificationSalesRepPhysPrescribing(eachUserId, newProduct.getProductName(), physService.getPhysicianName(eachPhysicianId), "NEW PRODUCT");
				
				//send email to sales rep
				String emailBody = "We have released new product "+newProduct.getProductName()+". Dr. "+physService.getPhysicianName(eachPhysicianId)
						+" has been prescribing our products. Try to fix "
						+ "appointment with him from 'Alignments' page on SalesFast to detail about this product.";
				String subject = "New product - detail prescribing physicians";
				sendNewProductNotificationEmails(subject, emailBody, user.getEmail());
			}
			
			
		}
	}
	
	
	@Override
	public void sendNewProductNotificationEmails(String subject, String body, String toEmailId) {
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
