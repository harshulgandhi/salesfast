package com.stm.salesfast.backend.controllers;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stm.salesfast.backend.entity.MeetingExperienceDataEntity;
import com.stm.salesfast.backend.entity.MeetingExperienceEntity;
import com.stm.salesfast.backend.services.specs.AnalysisService;
import com.stm.salesfast.backend.utils.DataReportMapper;

@Controller
public class DataReportController {
	private Logger log = LoggerFactory.getLogger(DataReportController.class.getName());
	
	@Autowired
	private AnalysisService analysis;
	
	DataReportMapper[] dataReport ={ new DataReportMapper("Microsoft Internet Explorer", (float) 56.33), 
			new DataReportMapper("Chrome", (float) 24.03), 
			new DataReportMapper("Firefox", (float) 10.38), 
			new DataReportMapper("Safari", (float) 4.77),
			new DataReportMapper("Proprietary or Undetectable", (float) 0.2)};

	public void helper(){
		/*dataReport.add(new DataReportMapper("Microsoft Internet Explorer", (float) 56.33));
		dataReport.add(new DataReportMapper("Chrome", (float) 24.03));
		dataReport.add(new DataReportMapper("Firefox", (float) 10.38));
		dataReport.add(new DataReportMapper("Safari", (float) 4.77));
		dataReport.add(new DataReportMapper("Proprietary or Undetectable", (float) 0.2));*/
	}
	
	@RequestMapping(value="/datareport", method=RequestMethod.GET)
	public String loginPage(){
		return "allmeetingsdata";
	}
	
	@RequestMapping(value="/getDataOverall", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public MeetingExperienceDataEntity[] getData(){
		log.info("Fetching data for the report");
		List<MeetingExperienceDataEntity> analysisOverAll = analysis.analyseOverall();
		log.info("Analysed data : ");
		for(MeetingExperienceDataEntity analysedData : analysisOverAll){
			log.info(""+analysedData);
		}
		return analysisOverAll.toArray(new MeetingExperienceDataEntity[0]);
	}
}
