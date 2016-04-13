package com.stm.salesfast.backend.controllers;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stm.salesfast.backend.entity.AssignedSalesRepInfoEntity;
import com.stm.salesfast.backend.entity.MeetingExpAnalysisFilterEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceAnalyzedDataEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceDataEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceEntity;
import com.stm.salesfast.backend.entity.SalesRepDailyPerformanceEntity;
import com.stm.salesfast.backend.entity.SalesRepMeetingPerformanceEntity;
import com.stm.salesfast.backend.entity.ViewAllPitchEntity;
import com.stm.salesfast.backend.entity.ViewPitchFilterParamEntity;
import com.stm.salesfast.backend.services.specs.AnalysisService;
import com.stm.salesfast.backend.services.specs.SalesRepPerformanceService;
import com.stm.salesfast.backend.services.specs.TerritoryService;
import com.stm.salesfast.backend.utils.DataReportMapper;
import com.stm.salesfast.constant.SessionConstants;

@Controller
public class DataReportController {
	private Logger log = LoggerFactory.getLogger(DataReportController.class.getName());
	
	@Autowired
	private AnalysisService analysis;
	
	@Autowired
	private SalesRepPerformanceService salesRepPerformance;
	
	@Autowired
	private TerritoryService territory;
	
	@RequestMapping(value="/datareport", method=RequestMethod.GET)
	public String loginPage(){
		if(SessionConstants.CURRENT_USER_ROLES.contains("DM")){
			return "allmeetingsdata";
		}else if(SessionConstants.CURRENT_USER_ROLES.contains("SalesRep")){
			return "allmeetingsdatasr";
		}
		return "home";
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
	
	@RequestMapping(value="/getfilteredanalyzeddata", method=RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public MeetingExperienceAnalyzedDataEntity[] applyFilterForDataAnalysis(@RequestBody MeetingExpAnalysisFilterEntity dataFilter){
		log.info("Filter Parameters received : "+dataFilter);
		dataFilter.setMedicalFieldId(dataFilter.getMedicalFieldId().equals("none") ? null : dataFilter.getMedicalFieldId() );
		dataFilter.setStatus(dataFilter.getStatus().equals("none") ? null : dataFilter.getStatus());
		log.info("Filter Parameters received : "+dataFilter);
		List<MeetingExperienceAnalyzedDataEntity> filteredAnalyzedData = analysis.analyseMeetingExperience(dataFilter);		
		return filteredAnalyzedData.toArray(new MeetingExperienceAnalyzedDataEntity[filteredAnalyzedData.size()]);
	}
	
	@RequestMapping(value="/salesrepperformance", method=RequestMethod.GET)
	public String salesRepPerformance(){
		if(SessionConstants.CURRENT_USER_ROLES.contains("DM")){
			return "salesrepperformance";
		}else if(SessionConstants.CURRENT_USER_ROLES.contains("SalesRep")){
			return "salesrepperformancesr";
		}
		return "home";
	}
	
	@RequestMapping(value="/getdailymeetingcount", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public SalesRepDailyPerformanceEntity[] getDailyMeetingsAttendedBySalesRep(@RequestParam(value="userId") int userId, @RequestParam(value="month") int month){
		List<SalesRepDailyPerformanceEntity> dailyPerformance = salesRepPerformance.getDailyPerformanceData(userId, month);
		log.info("Record count : "+dailyPerformance.size());
		for(SalesRepDailyPerformanceEntity eachEntity : dailyPerformance) log.info(""+eachEntity);
		return dailyPerformance.toArray(new SalesRepDailyPerformanceEntity[dailyPerformance.size()]);
	}
	
	@RequestMapping(value="/getallassignedsalesrep", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public AssignedSalesRepInfoEntity[] getSalesRepInformation(){
		List<AssignedSalesRepInfoEntity> assignedSalesrep = new ArrayList<>();
		if(SessionConstants.CURRENT_USER_ROLES.contains("DM")){
			assignedSalesrep = territory.getSalesRepByDMId(SessionConstants.USER_ID);
		}
		return assignedSalesrep.toArray(new AssignedSalesRepInfoEntity[assignedSalesrep.size()]);
	}
	
	@RequestMapping(value="/getmeetingstatusanalysis", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public SalesRepMeetingPerformanceEntity getMeetingStatusAnalysis(@RequestParam(value="userId") int userId){
		SalesRepMeetingPerformanceEntity meetingStatusAnalysis = salesRepPerformance.getMeetingStatuses(userId);
		return meetingStatusAnalysis;
	}
	
}
