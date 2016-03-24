package com.stm.salesfast.backend.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


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

import com.stm.salesfast.backend.entity.ManagerProductViewEntity;
import com.stm.salesfast.backend.entity.MedicalFieldEntity;
import com.stm.salesfast.backend.entity.NewProductEntity;
import com.stm.salesfast.backend.services.specs.AddNewProductService;
import com.stm.salesfast.backend.services.specs.ManagerViewProductService;
import com.stm.salesfast.backend.services.specs.MedicalFieldService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;

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
	ManagerViewProductService managerProdService;
	
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
				
		return "addproduct";
    }
	
	@RequestMapping(value="/addnewproduct", method=RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void addNewProduct(@RequestBody NewProductEntity newProduct) throws ParseException, IOException{
		log.info("New Product received  : "+newProduct);
		addProdService.addNewProduct(newProduct);
	}
	
	@RequestMapping(value="/allproducts", method=RequestMethod.GET)
	public String allProductsPage(Model model){
		return "products";
	}

	@RequestMapping(value = "/getproductdocuments",method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ManagerProductViewEntity[] getProductDocument(@RequestParam(value="productId") int productId) {
		List<ManagerProductViewEntity> allDocsDetails = managerProdService.getDocuments(productId);
		return allDocsDetails.toArray(new ManagerProductViewEntity[allDocsDetails.size()]);
	}
	
	@RequestMapping(value = "/updateproductfile", method = RequestMethod.POST) 
    public String updateProductDocument(@RequestParam("productdocument") MultipartFile productdocument, 
    		@RequestParam("selectedProductId") int selectedProductId,
    		@RequestParam("typeOfDocument") String typeOfDocument) throws IllegalStateException, IOException { 
		log.info("File UPDATE received ! ");
		log.info("File  : "+productdocument.getOriginalFilename()+"["+productdocument.getContentType()+"]"+"\nFor Product : "+selectedProductId+"\nType of file udpted : "+typeOfDocument);
//		addProdService.saveUploadedFiles(virtualTrainingFile, eDetailingFile);
		managerProdService.updateFile(productdocument, selectedProductId, typeOfDocument);
		return "products";
    }
	
}
