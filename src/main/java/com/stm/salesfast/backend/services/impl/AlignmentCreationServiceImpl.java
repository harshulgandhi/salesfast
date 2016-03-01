package com.stm.salesfast.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stm.salesfast.backend.dto.AlignmentsDto;
import com.stm.salesfast.backend.dto.PhysicianStgDto;
import com.stm.salesfast.backend.dto.ProductDto;
import com.stm.salesfast.backend.dto.UserDto;
import com.stm.salesfast.backend.services.specs.AlignmentCreationService;
import com.stm.salesfast.backend.services.specs.AlignmentFetchService;
import com.stm.salesfast.backend.services.specs.PhysicianFetchService;
import com.stm.salesfast.backend.services.specs.ProductFetchService;
import com.stm.salesfast.backend.services.specs.TerritoryService;
import com.stm.salesfast.backend.services.specs.TrainingMaterialService;
import com.stm.salesfast.backend.services.specs.UserDetailService;

@Service
public class AlignmentCreationServiceImpl implements AlignmentCreationService {
	private Logger log = LoggerFactory.getLogger(AlignmentCreationServiceImpl.class.getName());
	
	@Autowired
	PhysicianFetchService physicianFetchService;
	
	@Autowired
	UserDetailService userDetailService;
	
	@Autowired
	TrainingMaterialService trainingMaterial;
	
	@Autowired
	ProductFetchService productService;
	
	@Autowired
	TerritoryService territoryService;
	
	@Autowired
	AlignmentFetchService alignmentService;
	
	private List<UserDto> salesReps = new ArrayList<>();
	private List<PhysicianStgDto> physicians = new ArrayList<>();
	
	public AlignmentCreationServiceImpl() {
		// TODO Auto-generated constructor stub
		log.info("Instantiating Automatic Alignment Creator!!");
	}
	
	@Override
	public List<AlignmentsDto> calculateAlignments() {
		// TODO Auto-generated method stub
		salesReps = userDetailService.getAllSalesReps();
		physicians = physicianFetchService.getAllPhysicians();
		List<AlignmentsDto> alignments = new ArrayList<>();
		int i = 0;
		for(PhysicianStgDto eachPhysician : physicians){
			for(UserDto eachSalesRep : salesReps){
				String medicalField = trainingMaterial.getMedicalFieldForUser(eachSalesRep.getUserId());
				if(eachPhysician.getZip().equals(eachSalesRep.getZip()) && eachPhysician.getMedicalField().equals(medicalField)){
					List<ProductDto> productsForMedField = productService.getProductByMedicalField(medicalField);
					for(ProductDto eachProduct : productsForMedField){
						alignments.add(new AlignmentsDto(
								eachPhysician.getPhysicianId(),
								eachSalesRep.getUserId(),
								territoryService.getBy(eachSalesRep.getZip()).getTerritoryId(),
								territoryService.getBy(eachSalesRep.getZip()).getDistrictId(),
								eachSalesRep.getZip(),
								eachProduct.getProductId()
								));
						
						log.info("Alignment number : "+(++i)+" is : \n"+alignments.get(i-1));
					}
				}
				
			}
		}
		return alignments;
	}


	@Override
	public void createAlignments() {
		// TODO Auto-generated method stub
		List<AlignmentsDto> alignments = calculateAlignments();
		for(AlignmentsDto alignment : alignments) alignmentService.insert(alignment);
	}
	
	
	

}
