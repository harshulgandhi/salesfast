package com.stm.salesfast.backend.controllers;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.stm.salesfast.backend.entity.MedicalFieldEntity;
import com.stm.salesfast.backend.entity.NewProductEntity;
import com.stm.salesfast.backend.services.specs.AddNewProductService;
import com.stm.salesfast.backend.services.specs.MedicalFieldService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.constant.ConstantValues;

@Controller
public class ProductsController {
	private Logger log = LoggerFactory.getLogger(ProductsController.class.getName());
	
	@Autowired
	MedicalFieldService medicalFieldService;
	
	@Autowired
	ProductFetchService productService;
	
	@Autowired
	AddNewProductService addProdService;
	
	@Autowired
    private HttpServletRequest request;
	
	@RequestMapping(value="/addproductspage", method=RequestMethod.GET)
	public String addProductsPage(Model model){
		return "addproduct";
	}
	
	@RequestMapping(value = "/getmedicalfields",method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public MedicalFieldEntity[] getVirtualLearningMaterial(Model model) {
		List<MedicalFieldEntity> medicalFields = medicalFieldService.getAllMedicalFields();
		return medicalFields.toArray(new MedicalFieldEntity[medicalFields.size()]);
	}
	
	@RequestMapping(value = "/uploadfiles", method = RequestMethod.POST) 
    public String importParse(@RequestParam("virtualtrainingfile") MultipartFile virtualTrainingFile, 
    		@RequestParam("edetailingfile") MultipartFile eDetailingFile ) throws IllegalStateException, IOException { 
		log.info("File received ! ");
		log.info("File 1 : "+virtualTrainingFile.getOriginalFilename()+"["+virtualTrainingFile.getContentType()+"]"+"\nFile 2 : "+eDetailingFile.getOriginalFilename()+"["+eDetailingFile.getContentType()+"]");
		addProdService.saveUploadedFiles(virtualTrainingFile, eDetailingFile);
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
				
		return "addproduct";
    }
	
	@RequestMapping(value="/addnewproduct", method=RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void addNewProduct(@RequestBody NewProductEntity newProduct) throws ParseException{
		log.info("New Product received  : "+newProduct);
		productService.insertNewProduct(newProduct);
	}
}
