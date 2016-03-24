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
import com.stm.salesfast.backend.entity.ManagerProductViewEntity;
import com.stm.salesfast.backend.services.specs.EDetailingMaterialService;
import com.stm.salesfast.backend.services.specs.ManagerViewProductService;
import com.stm.salesfast.backend.services.specs.TrainingMaterialService;
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
	}	

}
