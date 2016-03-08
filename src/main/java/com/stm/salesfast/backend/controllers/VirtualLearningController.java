package com.stm.salesfast.backend.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stm.salesfast.backend.entity.VirtualLearningEntity;
import com.stm.salesfast.backend.services.specs.TrainingMaterialService;
import com.stm.salesfast.constant.SessionConstants;


@Controller
public class VirtualLearningController {
	private Logger log = LoggerFactory.getLogger(VirtualLearningController.class.getName());

	@Autowired
	TrainingMaterialService trainingMatService;
	
	@RequestMapping(value = "/virtuallearning")
	public String virtualLearningLandingPage(Model model) {
		log.info("Get entities for user "+SessionConstants.USER_ID);
		return "virtuallearning";
	}
	
	@RequestMapping(value = "/getvirtuallearningdata",method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public VirtualLearningEntity[] getVirtualLearningMaterial(Model model) {
		log.info("Get entities for user "+SessionConstants.USER_ID);
		List<VirtualLearningEntity> virtualLearningMat = trainingMatService.getAllDocumentsPath(SessionConstants.USER_ID);
		return virtualLearningMat.toArray(new VirtualLearningEntity[virtualLearningMat.size()]);
	}
}
