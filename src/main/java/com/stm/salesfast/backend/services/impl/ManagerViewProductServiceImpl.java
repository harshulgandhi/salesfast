package com.stm.salesfast.backend.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stm.salesfast.backend.controllers.ProductsController;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.entity.ManagerProductViewEntity;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.EDetailingMaterialService;
import com.stm.salesfast.backend.services.specs.ManagerViewProductService;
import com.stm.salesfast.backend.services.specs.NotificationService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.services.specs.TrainingMaterialService;
import com.stm.salesfast.backend.services.specs.UserDetailService;
import com.stm.salesfast.backend.utils.SalesFastEmail;
import com.stm.salesfast.backend.utils.SalesFastEmailSendGridImpl;
import com.stm.salesfast.constant.ConstantValues;

@Service
public class ManagerViewProductServiceImpl implements ManagerViewProductService{
	
	private Logger log = LoggerFactory.getLogger(ManagerViewProductServiceImpl.class.getName());
	
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
	EDetailingMaterialService detailingService;
	
	@Autowired
	TrainingMaterialService trainingMatService;
	
	@Autowired
	ProductFetchService productService;
	
	@Autowired
	AlignmentFetchService alignmentService;
	
	@Autowired
	UserDetailService userService;
	
	@Autowired
	PhysicianFetchService physService;
	
	@Autowired
	NotificationService notification;
	
	@Override
	public List<ManagerProductViewEntity> getDocuments(int productId) {
		List<ManagerProductViewEntity> filesForProduct = new ArrayList<>(); 
		String detailingFileName = detailingService.getDetailingFileName(productId);
		String trainingFileName = trainingMatService.getTrainingMaterialUrlForProduct(productId);
		filesForProduct.add(new ManagerProductViewEntity(
				ConstantValues.EDETAILING_DOCS_PATH+detailingFileName,
				"e-Detailing File (for Physician)"
				));
		filesForProduct.add(new ManagerProductViewEntity(
				ConstantValues.TRAINING_MATERIAL_PATH+trainingFileName,
				"Virtual Learning File (for SalesReps)"
				));
		filesForProduct.add(new ManagerProductViewEntity(
				"SampleReport",
				"Sample Medicine Feedback Analysis"
				));
		
		
		
		return filesForProduct;
	}
	
	@Override
	public void updateFile(MultipartFile productFile, int productId, String fileType) throws IllegalStateException, IOException {
		log.info("Updating file ");
		//Updating file
		String realPathtoUploads =   request.getServletContext().getRealPath("/resources/");
		String typeOfFile = fileType.replaceAll("\\s+","").toLowerCase();
		String folderName = "";
		if(typeOfFile.contains("e-detailing")) folderName = "edetailing";
		else if(typeOfFile.contains("virtuallearning")) folderName = "virtuallearning";
		String orgName = "docs\\"+folderName+"\\"+productFile.getOriginalFilename();
		log.info("Updating to : "+orgName);
        String filePath = realPathtoUploads + orgName;
        File dest = new File(filePath);
        productFile.transferTo(dest);
        
        String  medicalField = productService.getMedicalFieldForProduct(productId);
        
        List<Integer> physicianIds = detailingService.getPhysiciansInEdetailingForMedicalField(medicalField);
		updatesAboutDocUpdateToPhysicians(physicianIds, productId);
		
		List<Integer> userIds = alignmentService.getUserIdsWorkingInMedicalField(medicalField);
		updatesAboutDocUpdateToSalesReps(userIds, productId);
        
	}	

	/**
	 * Method to update system for notifications sent to all relevant
	 * physicians for edetialing document update
	 * */
	@Override
	public void updatesAboutDocUpdateToPhysicians(List<Integer> physiciansToBeUpdated, int productId){
		String productName = productService.getProductById(productId).getProductName();
		for(Integer eachPhysicianId : physiciansToBeUpdated){
			
			//insert into notifications for physician
			UserDto physicianAsAUser = userService.getUserForPhysicianId(eachPhysicianId);
			notification.insertNotificationDocUpdatePhysician(physicianAsAUser.getUserId(), productName, "EDETAILING DOCUMENT UPDATE");
			
			//send email to physician
			String emailBody = "e-Detailing document was update for medicine "
					+ productName+". Visit http://127.0.0.1:8080/edetailing for more details.";
			String subject = "Detailing document udpated";
			sendNewProductNotificationEmails(subject, emailBody, physicianAsAUser.getEmail());
		}
	}
	
	
	/**
	 * Method to update system for notifications sent to all relevant
	 * physicians for edetialing document update
	 * */
	@Override
	public void updatesAboutDocUpdateToSalesReps(List<Integer> salesrepsToBeUpdated, int productId){
		String productName = productService.getProductById(productId).getProductName();
		for(Integer eachSalesRepId : salesrepsToBeUpdated){
			
			//insert into notifications for physician
			UserDto salesrep = userService.getUserDetails(eachSalesRepId);
			notification.insertNotificationDocUpdatePhysician(salesrep.getUserId(), productName, "TRAINING DOCUMENT UPDATE");
			
			//send email to physician
			String emailBody = "Training document was update for medicine "
					+ productName+" by your manager. Visit http://127.0.0.1:8080/ for more details.";
			String subject = "Training document udpated";
			sendNewProductNotificationEmails(subject, emailBody, salesrep.getEmail());
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
