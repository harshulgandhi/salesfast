package com.stm.salesfast.backend.controllers;


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

import com.stm.salesfast.backend.entity.ProductEntity;
import com.stm.salesfast.backend.entity.SampleFeedbackDataEntity;
import com.stm.salesfast.backend.entity.SampleFeedbackEntity;
import com.stm.salesfast.backend.entity.VirtualLearningEntity;
import com.stm.salesfast.backend.services.specs.SampleFeedbackAnalysisService;
import com.stm.salesfast.backend.services.specs.SampleFeedbackService;
import com.stm.salesfast.constant.SessionConstants;

@Controller
public class SampleFeedbackController {

	private Logger log = LoggerFactory.getLogger(SampleFeedbackController.class.getName());

	@Autowired
	SampleFeedbackService sampleFeedbackService;
	
	@Autowired
	SampleFeedbackAnalysisService feedbackAnalysisService;
	
	@RequestMapping(value="/samplefeedback", method=RequestMethod.GET)
	public String samplefeedbackpage(@RequestParam(value="id") int productId,  @RequestParam(value="name") String productName, Model model){
		model.addAttribute("productId",productId);
		model.addAttribute("productName",productName);
		return "samplefeedback";
	}
	
	@RequestMapping(value="/submitsamplefeedback", method=RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public void sampleFeedbackSubmission(@RequestBody SampleFeedbackEntity sampleFeedback){
		log.info("Sample feedback received  : "+sampleFeedback);
		sampleFeedbackService.insertFeedback(sampleFeedback);
	}
	
	@RequestMapping(value="/samplefeedbacksubmitted", method=RequestMethod.GET)
	public String sampleFeedbackSubmitted(){
		return "samplefeedbacksubmitted";
	}
	
	@RequestMapping(value="/samplefeedbackreport", method=RequestMethod.GET)
	public String samplefeedbackpage(){
		return "samplefeedbackreport";
	}
	
	@RequestMapping(value = "/getproductsforuser",method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ProductEntity[] getProductsForUser() {
		log.info("Get products for user "+SessionConstants.USER_ID);
		List<ProductEntity> allProducts = feedbackAnalysisService.getProductsForUser(SessionConstants.USER_ID);
		return allProducts.toArray(new ProductEntity[allProducts.size()]);
	}
	
	@RequestMapping(value="/getanalysisforproduct", method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public SampleFeedbackDataEntity[] getFeedbackAnalysisForProduct(@RequestParam(value="id") int productId){
		List<SampleFeedbackDataEntity> feedbackAnalysis = feedbackAnalysisService.analyseFeedbackData(productId);
		for(SampleFeedbackDataEntity eachFeed : feedbackAnalysis) log.info(productId+" ==> "+eachFeed);
		return feedbackAnalysis.toArray(new SampleFeedbackDataEntity[feedbackAnalysis.size()]);
	}
	
	@RequestMapping(value="/getsideeffectcomments", method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public String[] getSideEffectComments(@RequestParam(value="id") int productId){
		List<String> sideEffectComments = sampleFeedbackService.getSideEffectComments(productId);
		return sideEffectComments.toArray(new String[sideEffectComments.size()]);
	}
	
	@RequestMapping(value="/getothercomments", method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public String[] getOtherComments(@RequestParam(value="id") int productId){
		List<String> otherComments = sampleFeedbackService.getOtherComments(productId);
		return otherComments.toArray(new String[otherComments.size()]);
	}
}
