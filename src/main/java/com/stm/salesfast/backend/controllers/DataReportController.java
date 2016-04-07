package com.stm.salesfast.backend.controllers;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stm.salesfast.backend.entity.MeetingExpAnalysisFilterEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceDataEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceEntity;
import com.stm.salesfast.backend.services.specs.AnalysisService;
import com.stm.salesfast.backend.utils.DataReportMapper;

@Controller
public class DataReportController {
	private Logger log = LoggerFactory.getLogger(DataReportController.class.getName());
	
	@Autowired
	private AnalysisService analysis;
	
	@RequestMapping(value="/datareport", method=RequestMethod.GET)
	public String loginPage(){
		return "allmeetingsdata";
	}
	
	@RequestMapping(value="/getDataOverall", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public MeetingExperienceDataEntity[] getOverallData(){
		log.info("Fetching data for the report");
		List<MeetingExperienceDataEntity> analysisOverAll = analysis.analyseOverall();
		log.info("Analysed data : ");
//		for(MeetingExperienceDataEntity analysedData : analysisOverAll){
//			log.info(""+analysedData);
//		}
		log.info(""+analysis.analyseMeetingExperience(new MeetingExpAnalysisFilterEntity(null,0,0,"LOST")));
		return analysisOverAll.toArray(new MeetingExperienceDataEntity[0]);
	}
	
	@RequestMapping(value="/getPhyResp", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public MeetingExperienceDataEntity[] getOnlyPhysicianData(){
		log.info("Fetching data for the report");
		List<MeetingExperienceDataEntity> analysisOverAll = analysis.analysePhysicianResponse();
		log.info("Analysed data : ");
//		for(MeetingExperienceDataEntity analysedData : analysisOverAll){
//			log.info(""+analysedData);
//		}
		return analysisOverAll.toArray(new MeetingExperienceDataEntity[0]);
	}
	
	@RequestMapping(value="/getSalesRepResp", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public MeetingExperienceDataEntity[] getOnlySalesRepData(){
		log.info("Fetching data for the report");
		List<MeetingExperienceDataEntity> analysisOverAll = analysis.analyseSalesRepResponse();
		log.info("Analysed data : ");
//		for(MeetingExperienceDataEntity analysedData : analysisOverAll){
//			log.info(""+analysedData);
//		}
		return analysisOverAll.toArray(new MeetingExperienceDataEntity[0]);
	}
	
	@RequestMapping(value="/getStatusLostData", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public MeetingExperienceDataEntity[] getLostStatusData(){
		log.info("Fetching data for the report");
		List<MeetingExperienceDataEntity> analysisOverAll = analysis.analyseLostStatusRecords();
		log.info("Analysed data : ");
//		for(MeetingExperienceDataEntity analysedData : analysisOverAll){
//			log.info(""+analysedData);
//		}
		return analysisOverAll.toArray(new MeetingExperienceDataEntity[0]);
	}
}
