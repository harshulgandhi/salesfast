package com.stm.salesfast.backend.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stm.salesfast.backend.utils.DataReportMapper;
import com.stm.salesfast.frontend.LoginUI;

@Controller
public class DataReportController {
	
	private List<DataReportMapper> dataReport;
	private Logger log = LoggerFactory.getLogger(DataReportController.class.getName());

	public void helper(){
		dataReport.add(new DataReportMapper("Microsoft Internet Explorer", (float) 56.33));
		dataReport.add(new DataReportMapper("Chrome", (float) 24.03));
		dataReport.add(new DataReportMapper("Firefox", (float) 10.38));
		dataReport.add(new DataReportMapper("Safari", (float) 4.77));
		dataReport.add(new DataReportMapper("Proprietary or Undetectable", (float) 0.2));
	}
	
	@RequestMapping(value="/datareport", method=RequestMethod.GET)
	public String loginPage(){
		return "allmeetingsdata";
	}
	
	@RequestMapping(value="/getdata", method=RequestMethod.GET)
	@ResponseBody
	public List<DataReportMapper> getData(){
		log.info("Fetching data for the report");
		return dataReport;
	}
}
