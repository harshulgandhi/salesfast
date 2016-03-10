package com.stm.salesfast.backend.services.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stm.salesfast.backend.services.specs.AddNewProductService;
import com.stm.salesfast.constant.ConstantValues;

@Service
public class AddNewProductServiceImpl implements AddNewProductService {

	@Autowired
    private HttpServletRequest request;
	
	@Override
	public void saveUploadedFiles(MultipartFile virtualTrainingFile, MultipartFile eDetailingFile) throws IllegalStateException, IOException {
		//Saving training material file
		String realPathtoUploads =  request.getServletContext().getRealPath(ConstantValues.TRAINING_MATERIAL_PATH);
		String orgName = virtualTrainingFile.getOriginalFilename();
        String filePath = realPathtoUploads + orgName;
        File dest = new File(filePath);
        virtualTrainingFile.transferTo(dest);
        
      //Saving training material file
		String realPathtoUploads2 =  request.getServletContext().getRealPath(ConstantValues.EDETAILING_DOCS_PATH);
		String orgName2 = virtualTrainingFile.getOriginalFilename();
		String filePath2 = realPathtoUploads2 + orgName2;
		File dest2 = new File(filePath2);
		eDetailingFile.transferTo(dest2);
	}
	
	

}
