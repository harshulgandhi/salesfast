package com.stm.salesfast.backend.services.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stm.salesfast.backend.entity.NewProductEntity;
import com.stm.salesfast.backend.services.specs.AddNewProductService;
import com.stm.salesfast.backend.services.specs.AlignmentCreationService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.constant.ConstantValues;

@Service
public class AddNewProductServiceImpl implements AddNewProductService {

	@Autowired
    private HttpServletRequest request;
	
	@Autowired
	private AlignmentCreationService alignmentCreation;
	
	@Autowired
	ProductFetchService productService;
	
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
	}

}
