/**
 * 
 */
var numDays = {
                '01': 31, '02': 28, '03': 31, '04': 30, '05': 31, '06': 30,
                '07': 31, '08': 31, '09': 30, '10': 31, '11': 30, '12': 31
              };
var pastAppointmentDetail = null;
var pastAppointments = [];
var loggedInUser = "";
$(document).ready(function(){
	
	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.salesrep-performance').addClass('left-menu-selected');
	
	$('li.navbar-menu-selected').removeClass("navbar-menu-selected");
	   
	if(!$('li#nav-data-report').hasClass("navbar-menu-selected")){
		$('li#nav-data-report').addClass("navbar-menu-selected")
	}
	
	$('.performance-month-selector').select2();
	updateNotificationCounter();
//	getAssignedSalesRepList();
	getLoggedInSalesRepId();
//	$('div#layout').append(
//			getReportEnvironmentHtml(loggedInUser)
//	);
//	$('select.search-filter-param').select2({ width: '100%' });
//	getDailyMeetingCount(loggedInUser, $('.performance-month-selector').val());
//	getMeetingStatusAnalysis(loggedInUser);
//	getPhysicianResponse();
	
});

var getLoggedInSalesRepId = function(){
	$.ajax({
		type : 'GET',
		url : '/getloggedinuserid',
		contentType : 'application/json',
		success : function(data){
			console.log("Current userId : "+JSON.stringify(data));
			loggedInUser = data;
			$('div#layout').append(
					getReportEnvironmentHtml(loggedInUser)
			);
			$('select.search-filter-param').select2({ width: '100%' });
			getDailyMeetingCount(data, $('.performance-month-selector').val());
			getMeetingStatusAnalysis(data);
			getPhysicianResponse();
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("Filter sent and data received");
	});
}

$(document).on('change','select.filter-selectors', function(){
	updateNotificationCounter();
	$('div#layout').html("");
	$('div#layout').append(
			getReportEnvironmentHtml(loggedInUser)
	);
	$('select.search-filter-param').select2({ width: '100%' });
	getDailyMeetingCount(loggedInUser, $('.performance-month-selector').val());
	getMeetingStatusAnalysis(loggedInUser);
	getPhysicianResponse();
});

$(document).on('click','button.past-appointment-detail-btn',function(){
	/*var btnId = $(this).attr('id');
	var salesRepId = btnId.split('-')[btnId.split('-').length - 1];
	getPastAppointments(salesRepId);*/
	$('.selected-btn').removeClass('selected-btn');
	if(!$(this).hasClass('selected-btn')) $(this).addClass('selected-btn');
	else $(this).removeClass('selected-btn')
	if($('div.salesrep-appointment-details').css('display') == 'none' || 
			($('div.salesrep-appointment-details').css('display') == 'block' && $(this).hasClass('selected-btn'))){
		$('div.salesrep-appointment-details').slideToggle('fast');
		$('div.table-row').slideToggle('fast');
		$('.performance-detail-btn')[0].scrollIntoView( true );
	}
	if($(this).hasClass('lost-isto-prescribing')){
		$('select.status-selector').select2('val','LOST');
	}else if($(this).hasClass('phys-response-detail')){
		$('select.phys-response').select2('val','presentation');
	}
});

var getMeetingStatusAnalysis = function(salesRepId){
	$.ajax({
		type: 'GET',
		url : '/getmeetingstatusanalysis?userId='+salesRepId,
		dataType : 'json',
		success : function(data){
	    	console.log("Data received : "+JSON.stringify(data));
//	    	createDailyAppointmentChart("#container-daily-meeting",	data, $('.salesrep-selector').find('option[selected="selected"]').html());
	    	createMeetingStatusChart('#container-salesrep-meetingperformance',data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
}

var createMeetingStatusChart = function(containerId, data){
	var analysisData = [];
	var color = [];
	$.each(data, function(k,v){
		k.split('_').join(' ');
		if(k == "prescribing") color.push('lightgreen');
		else if(k == "lost") color.push('#D46565');
		else if(k == "not interested") color.push('gray');
		analysisData.push([k, v]);
	});
	
	$(containerId).highcharts({
		colors: color,
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: 0,
            plotShadow: false
        },
        title: {
            text: 'Your<br>Performance',
            align: 'center',
            verticalAlign: 'middle',
            y: 40
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                dataLabels: {
                    enabled: true,
                    distance: -30,
                    style: {
                        fontWeight: 'bold',
                        color: 'white',
                        textShadow: '0px 1px 2px black'
                    }
                },
                startAngle: -90,
                endAngle: 90,
                center: ['50%', '75%']
            }
        },
        series: [{
            type: 'pie',
            name: 'Meeting Status',
            innerSize: '60%',
            data: analysisData
        }]
    });
}

var currentSelectedDate = '0';
$(document).on('change','select.month-selector',function(){
	var month = $(this).val();
	var numOfDays = numDays[month];
	if(month != '0'){
		$('select.day-selector').attr('disabled',false);
		if($('select.day-selector').val() != "0"){
			currentSelectedDate = $('select.day-selector').val();
		}
		$('select.day-selector').html("");
		$('select.day-selector').append('<option value="0">day</option>');
		console.log('number of days '+numOfDays);
		for(var i = 1 ; i <= numOfDays ; i++){
			if(i<10){
				$('select.day-selector').append('<option value="0'+i+'">'+i+'</option>');
			}else{
				$('select.day-selector').append('<option value="'+i+'">'+i+'</option>');
			}
		}
	}
	else if(month == "0"){
		$('select.day-selector').attr('disabled',true);
	}
	
});

$(document).on('change','select.search-filter-param',function(){
	if($(this).hasClass('day-selector')){
		var year = $('select.year-selector').val();
		var month = $('select.month-selector').val();
		var day = $('select.day-selector').val();
		var physResponseCategory = $('.phys-response').val();
		var status = $('select.status-selector').val();
		console.log('year : '+year+"; month : "+month+"; day : "+day+"; physResponseCategory : "+physResponseCategory+"; status : "+status);
		searchTerm = '';
		if(month != '0') searchTerm += year+' '+month;
		if(day != '0') searchTerm += ' '+day;
		if(status != ' ') searchTerm += ' '+status;
		if(physResponseCategory != 'none'){
			if(physResponseCategory == 'presentation'){
				searchTerm += ' '+'presentation problem';	
			}else if(physResponseCategory == 'confidence'){
				searchTerm += ' '+'confidence problem';
			}else if(physResponseCategory == 'reputation'){
				searchTerm += ' '+'maintain reputation problem';
			}
		}
		pastAppointmentDetail.search(searchTerm).draw();
//		var year = $('select.year-selector').val();
//		var month = $('select.month-selector').val();
//		var day = $(this).val();
//		var searchTerm = year+'-'+month+'-'+day;
//		console.log("$('.status-selector').val() : "+$('.status-selector').val());
//		if($('.status-selector').val() != ' '){
//			searchTerm  = searchTerm +' '+$('.status-selector').val(); 
//			pastAppointmentDetail.search(searchTerm).draw();
//		}else{
//			pastAppointmentDetail.search(searchTerm).draw();
//		}
	}
	else if($(this).hasClass('month-selector')){
		var year = $('select.year-selector').val();
		var month = $('select.month-selector').val();
		var day = $('select.day-selector').val();
		var physResponseCategory = $('.phys-response').val();
		var status = $('select.status-selector').val();
		console.log('year : '+year+"; month : "+month+"; day : "+day+"; physResponseCategory : "+physResponseCategory+"; status : "+status);
		searchTerm = '';
		if(month != '0') searchTerm += year+' '+month;
		if(day != '0') searchTerm += ' '+day;
		if(status != ' ') searchTerm += ' '+status;
		if(physResponseCategory != 'none'){
			if(physResponseCategory == 'presentation'){
				searchTerm += ' '+'presentation problem';	
			}else if(physResponseCategory == 'confidence'){
				searchTerm += ' '+'confidence problem';
			}else if(physResponseCategory == 'reputation'){
				searchTerm += ' '+'maintain reputation problem';
			}
		}
		pastAppointmentDetail.search(searchTerm).draw();
//		if(!$('select.day-selector').attr('disabled')){
//			var year = $('select.year-selector').val();
//			var month = $('select.month-selector').val();
//			$('select.day-selector').select2('val',currentSelectedDate);
//			var day = $('select.day-selector').val();
//			console.log('day : '+day)
//			console.log("$('.status-selector').val() : "+$('.status-selector').val());
//			if(day == '0'){
//				var searchTerm = year+'-'+month;
//				if($('.status-selector').val() != ' '){
//					searchTerm  = searchTerm +' '+$('.status-selector').val(); 
//					pastAppointmentDetail.search(searchTerm).draw();
//				}else{
//					pastAppointmentDetail.search(searchTerm).draw();
//				}
//			}
//			else{
//				var searchTerm = year+'-'+month+'-'+day;
//				if($('.status-selector').val() != ' '){
//					searchTerm  = searchTerm +' '+$('.status-selector').val(); 
//					pastAppointmentDetail.search(searchTerm).draw();
//				}else{
//					pastAppointmentDetail.search(searchTerm).draw();
//				}
//			}
//		}
	}else if($(this).hasClass('status-selector')){
		var year = $('select.year-selector').val();
		var month = $('select.month-selector').val();
		var day = $('select.day-selector').val();
		var physResponseCategory = $('.phys-response').val();
		var status = $(this).val();
		console.log('year : '+year+"; month : "+month+"; day : "+day+"; physResponseCategory : "+physResponseCategory+"; status : "+status);
		searchTerm = '';
		if(month != '0') searchTerm += year+' '+month;
		if(day != '0') searchTerm += ' '+day;
		if(status != ' ') searchTerm += ' '+status;
		if(physResponseCategory != 'none'){
			if(physResponseCategory == 'presentation'){
				searchTerm += ' '+'presentation problem';	
			}else if(physResponseCategory == 'confidence'){
				searchTerm += ' '+'confidence problem';
			}else if(physResponseCategory == 'reputation'){
				searchTerm += ' '+'maintain reputation problem';
			}
		}
		pastAppointmentDetail.search(searchTerm).draw();
//		if(month == "0" && status != ' '){
//			searchTerm = year+' '+status;
//			pastAppointmentDetail.search(searchTerm).draw();
//		}else if(month != "0" && day == "0" && status != ' '){ 
//			searchTerm = year+' '+month+' '+status;
//			pastAppointmentDetail.search(searchTerm).draw();
//		}else if(month != "0" && day != "0" && status != ' '){
//			searchTerm = year+' '+month+' '+day+' '+status;
//			pastAppointmentDetail.search(searchTerm).draw();
//		}else if(month == "0" && status == ' '){
//			searchTerm = year+' '+status;
//			pastAppointmentDetail.search(searchTerm).draw();
//		}else if(status == ' '){
//			searchTerm = '';
//			if(month != '0') searchTerm += ' '+month;
//			if(day != '0') searchTerm += ' '+day;
//			pastAppointmentDetail.search(searchTerm).draw();
//		}
	}else if($(this).hasClass('phys-response')){
		var year = $('select.year-selector').val();
		var month = $('select.month-selector').val();
		var day = $('select.day-selector').val();
		var physResponseCategory = $('.phys-response').val();
		var status = $('select.status-selector').val();
		console.log('year : '+year+"; month : "+month+"; day : "+day+"; physResponseCategory : "+physResponseCategory+"; status : "+status);
		searchTerm = '';
		if(month != '0') searchTerm += year+' '+month;
		if(day != '0') searchTerm += ' '+day;
		if(status != ' ') searchTerm += ' '+status;
		if(physResponseCategory != 'none'){
			if(physResponseCategory == 'presentation'){
				searchTerm += ' '+'presentation problem';	
			}else if(physResponseCategory == 'confidence'){
				searchTerm += ' '+'confidence problem';
			}else if(physResponseCategory == 'reputation'){
				searchTerm += ' '+'maintain reputation problem';
			}
		}
		pastAppointmentDetail.search(searchTerm).draw();
//		var year = $('select.year-selector').val();
//		var month = $('select.month-selector').val();
//		var day = $('select.day-selector').val();
//		var status = $('select.status-selector').val();
//		var physResponseCategory = $(this).val();
//		searchTerm = '';
//		if(month != '0' ){
//			searchTerm += year+' '+month;
//		}
//		if(month !='0' && day != '0' ){
//			searchTerm += ' '+month;
//		}
//		if(status != ' ') searchTerm += ' '+status;
//		if(physResponseCategory != 'none') {
//			if(physResponseCategory == 'presentation'){
//				searchTerm += ' '+'presentation problem';	
//			}else if(physResponseCategory == 'confidence'){
//				searchTerm += ' '+'confidence problem';
//			}else if(physResponseCategory == 'reputation'){
//				searchTerm += ' '+'maintain reputation problem';
//			}
//		}
//		pastAppointmentDetail.search(searchTerm).draw();
		
	}
	
});

$(document).on('click','button.further-detail-btn', function(){
	$('#pastappointment-details-modal').modal('show');
	var $row = $(this).parent().parent(); 
	var idx = $(this).attr('id');
	console.log(" idx : "+idx);
	
	$('ul.salesrep-response').html("");
	$('ul.physician-response').html("");
	$('div.modal-body-3').html("");
	
	if(pastAppointments[idx]["reasonsBySalesRep"].length == 0){
		$('ul.salesrep-response').append('<li class="meetingexp-salesrep-empty">SalesRep did not submit your experience about this meeting</li>');
		$('li.meetingexp-salesrep-empty').parent().parent().css('background','#EABEBE');
	}else{
		for( var i = 0; i< pastAppointments[idx]["reasonsBySalesRep"].length; i++){
			$('ul.salesrep-response').append(
					'<li class="sales-rep-response-pointers meeting-exp-li">'+pastAppointments[idx]["reasonsBySalesRep"][i]+'</li>'
			);
			
		}
		$('li.sales-rep-response-pointers').parent().parent().css('background','aliceblue');
	}
	
	if(pastAppointments[idx]["reasonsByPhysician"].length == 0){
		$('ul.physician-response').append('<li class="meetingexp-phys-empty">Physician did not submit experience about this meeting</li>');
		$('li.meetingexp-phys-empty').parent().parent().css('background','#EABEBE');
	}else{
		for( var i = 0; i< pastAppointments[idx]["reasonsByPhysician"].length; i++){
			$('ul.physician-response').append(
					'<li class="physician-response-pointers meeting-exp-li">'+pastAppointments[idx]["reasonsByPhysician"][i]+'</li>'
			);
		}
		$('li.physician-response-pointers').parent().parent().css('background','aliceblue');
	}
	
	if(pastAppointments[idx]["pitch"] == null) {
		$('div.modal-body-3').append('<span>SalesRep did not upload any pitch for this meeting</span>');
		$('div.modal-body-3').css('background','#EABEBE');
	}else{
		$('div.modal-body-3').append(
				'<div class="pitch-doc-div" style="display: none;">'+
					'<object class="pdf-doc-object pdf-doc-object-slidedown" data="'+pastAppointments[idx]["pitch"]["fileLocation"]+'" type="application/pdf"">'+
						'<embed src="'+pastAppointments[idx]["pitch"]["fileLocation"]+'" alt="pdf" pluginspage="http://www.adobe.com/products/acrobat/readstep2.html">'+
						'</embed>'+
					'</object>'+
				'</div>'
			);
		$('div.pitch-doc-div').slideToggle('fast');
		$('div.modal-body-3').css('background','white');
	}
	
});

var getDailyMeetingCount = function(salesRepId, month){
	
	$.ajax({
		type: 'GET',
		url : '/getdailymeetingcount?userId='+salesRepId+'&month='+month,
		dataType : 'json',
		success : function(data){
	    	console.log("Data received : "+JSON.stringify(data));
	    	createDailyAppointmentChart("#container-daily-meeting",	data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
}

var createDailyAppointmentChart = function(containerId, data){
	var listOfDates = [];
	var listOfLostNumbers = [];
	var listOfPrescribingNumbers = [];
	
	for(var i = 0; i< data.length; i++){
		listOfDates.push(data[i]["date"]);
		listOfLostNumbers.push(data[i]["noOfLostAppointments"]);
		listOfPrescribingNumbers.push(data[i]["noOfPrescribingAppointments"]);
	}
	
	 $(containerId).highcharts({
		 	colors: ['#D46565','lightgreen'],
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: 'Meetings executed by You'
	        },
	        xAxis: {
	            categories: listOfDates
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: 'No of Appointments'
	            },
	            stackLabels: {
	                enabled: true,
	                style: {
	                    fontWeight: 'bold',
	                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
	                }	
	            }
	        },
	        legend: {
	            align: 'right',
	            x: -30,
	            verticalAlign: 'top',
	            y: 25,
	            floating: true,
	            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
	            borderColor: '#CCC',
	            borderWidth: 1,
	            shadow: false
	        },
	        tooltip: {
	            headerFormat: '<b>{point.x}</b><br/>',
	            pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
	        },
	        plotOptions: {
	            column: {
	                stacking: 'normal',
	                dataLabels: {
	                    enabled: true,
	                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
	                    style: {
	                        textShadow: '0 0 3px black'
	                    }
	                }
	            }
	        },
	        series: [{
	            name: 'Lost',
	            data: listOfLostNumbers
	        }, {
	            name: 'Prescribing',
	            data: listOfPrescribingNumbers
	        }]
	    });
}

var populatePastAppTable = function(appointments){
	
	for(var i = 0; i < appointments.length ; i++){
		console.log(appointments[i]["meetingStatus"]);
		if(appointments[i]["meetingStatus"] == "LOST"){
			var physicianInput = "";
			if(appointments[i]["reasonsByPhysician"].length > 0 && appointments[i]["reasonsByPhysician"][5].indexOf("You need to be more confident while pitching") >= 0){
				physicianInput += 'confidence problem';
			}
			if(appointments[i]["reasonsByPhysician"].length > 0 && appointments[i]["reasonsByPhysician"][2].indexOf("Physician did not like the presentation") >= 0){
				physicianInput += 'presentation problem';
			}
			if(appointments[i]["reasonsByPhysician"].length > 0 && appointments[i]["reasonsByPhysician"][3].indexOf("Physician was not impressed by company's reputation") >= 0){
				physicianInput += 'maintain reputation problem';
			}
			$('table#past-appointments-table tbody').append(
					'<tr class="lost-appointment-tr danger">'+
						'<td style="display:none" class="physician-id">'+appointments[i]["physicianId"]+'</td>'+
						'<td>'+appointments[i]["physicianName"]+'</td>'+
						'<td style="display:none" class="product-id">'+appointments[i]["productId"]+'</td>'+
						'<td>'+appointments[i]["productName"]+'</td>'+
						'<td>'+appointments[i]["date"]+'</td>'+
						'<td>'+appointments[i]["meetingStatus"]+'</td>'+
						'<td class="further-detail-td">'+
							'<div class="further-detail-click">'+
								'<button type="button" id="'+i+'" class="btn btn-default btn-md further-detail-btn">'+
									'<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>'+
									'<span class="further-detail-btn-span">View Further Details</span>'+
								'</button>'+
							'</div>'+
						'</td>'+
						'<td class="physician-input" style="display : none">'+physicianInput+'</td>'+
					'</tr>'
			);
		}
		else if(appointments[i]["meetingStatus"] == "PRESCRIBING"){
			if(appointments[i]["reasonsByPhysician"].length > 0 && appointments[i]["reasonsByPhysician"][5].indexOf("You need to be more confident while pitching") >= 0){
				physicianInput += 'confidence problem';
			}
			if(appointments[i]["reasonsByPhysician"].length > 0 && appointments[i]["reasonsByPhysician"][2].indexOf("Physician did not like the presentation") >= 0){
				physicianInput += 'presentation problem';
			}
			if(appointments[i]["reasonsByPhysician"].length > 0 && appointments[i]["reasonsByPhysician"][3].indexOf("Physician was not impressed by company's reputation") >= 0){
				physicianInput += 'maintain reputation problem';
			}
			$('table#past-appointments-table tbody').append(
					'<tr class="prescribing-appointment-tr success">'+
						'<td style="display:none" class="physician-id">'+appointments[i]["physicianId"]+'</td>'+
						'<td>'+appointments[i]["physicianName"]+'</td>'+
						'<td style="display:none" class="product-id">'+appointments[i]["productId"]+'</td>'+
						'<td>'+appointments[i]["productName"]+'</td>'+
						'<td>'+appointments[i]["date"]+'</td>'+
						'<td>'+appointments[i]["meetingStatus"]+'</td>'+
						'<td class="further-detail-td">'+
							'<div class="further-detail-click">'+
								'<button type="button" id="'+i+'" class="btn btn-default btn-md further-detail-btn">'+
									'<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>'+
									'<span class="further-detail-btn-span">View Further Details</span>'+
								'</button>'+
							'</div>'+
						'</td>'+
						'<td class="physician-input" style="display : none">'+physicianInput+'</td>'+				
					'</tr>'
			);
		}
		else{
			if(appointments[i]["reasonsByPhysician"].length > 0 && appointments[i]["reasonsByPhysician"][5].indexOf("You need to be more confident while pitching") >= 0){
				physicianInput += 'confidence problem';
			}
			if(appointments[i]["reasonsByPhysician"].length > 0 && appointments[i]["reasonsByPhysician"][2].indexOf("Physician did not like the presentation") >= 0){
				physicianInput += 'presentation problem';
			}
			if(appointments[i]["reasonsByPhysician"].length > 0 && appointments[i]["reasonsByPhysician"][3].indexOf("Physician was not impressed by company's reputation") >= 0){
				physicianInput += 'maintain reputation problem';
			}
			$('table#past-appointments-table tbody').append(
					
					'<tr class="other-appointment-tr">'+
						'<td style="display:none" class="physician-id">'+appointments[i]["physicianId"]+'</td>'+
						'<td>'+appointments[i]["physicianName"]+'</td>'+
						'<td style="display:none" class="product-id">'+appointments[i]["productId"]+'</td>'+
						'<td>'+appointments[i]["productName"]+'</td>'+
						'<td>'+appointments[i]["date"]+'</td>'+
						'<td>'+appointments[i]["meetingStatus"]+'</td>'+
						'<td class="further-detail-td">'+
							'<div class="further-detail-click">'+
								'<button type="button" id="'+i+'" class="btn btn-default btn-md further-detail-btn">'+
									'<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>'+
									'<span class="further-detail-btn-span">View Further Details</span>'+
								'</button>'+
							'</div>'+
						'</td>'+
						'<td class="physician-input" style="display : none">'+physicianInput+'</td>'+				
					'</tr>'
			);
		}
		
	}
	pastAppointmentDetail = $('table#past-appointments-table').DataTable({
		   "searching": true
	   });
}

var getPastAppointments = function(salesRepId){
	console.log("Fetching similar questions");
	$.ajax({
		type : 'GET',
		url : "/getpastappointments?salesRepId="+salesRepId,
		dataType : 'json',
		success : function(data){
	    	console.log("Data received (Past Appointments) : "+JSON.stringify(data));
	    	pastAppointments = data;
	    	populatePastAppTable(data);
//	    	populatePastAppTable(data);
//	    	pastAppointments =  data;
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
}


var getPhysicianResponse = function(){
	var data = {};
	data["medicalFieldId"] = "none";
	data["productId"] = "0";
	data["userId"]= loggedInUser;
	data["status"] = "none";
//	console.log("Filter created : "+JSON.stringify(data));
	
//	$("#view-pitch-table > tbody").html("");
	
	$.ajax({
		type : 'POST',
		url : '/getfilteredanalyzeddata',
		data : JSON.stringify(data),
		contentType : 'application/json',
		success : function(data){
			console.log("Analysis for data : "+JSON.stringify(data));
			createPhysicianResponseChart("#container-salesrep-presentation",data);
//			container-salesrep-presentation
//			populateDropDowns(data);
//			createPitchEnvironment(data);
//			seggregateAnalyzedData(data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("Filter sent and data received");
	});
}

var createPhysicianResponseChart=function(containerId, data){
    // Build the chart
	var dataToShow = [];
	for(var i = 0; i < data.length; i++){
		if(data[i]["category"] == "According to Physicians"){
			var requiredData = data[i]["analyzedData"];
			console.log("Required Data : "+JSON.stringify(requiredData));
			for(var j = 0; j<requiredData.length; j++){
				if(requiredData[j]["name"] == "Influenced by presentation" || 
						requiredData[j]["name"] == "Impressed by SalesRep's confidence" ||
							requiredData[j]["name"] == "Impressed with organisation's reputations"){
					var tempJson = {};
					tempJson["name"] = requiredData[j]["name"];
					tempJson["y"] = requiredData[j]["y"]; 
					dataToShow.push(tempJson);
				}
			}
		}
	}
	console.log("dataToShow : "+JSON.stringify(dataToShow));
	
    $(containerId).highcharts({
    	chart: {
            type: 'column'
        },
        title: {
            text: 'What physicians think about you'
        },
        
        xAxis: {
            type: 'category'
        },
        yAxis: {
            title: {
                text: 'Percentage out of all responses'
            }
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y:.1f}%'
                }
            }
        },

        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
        },

        series: [{
            name: 'Meeting experience responses',
            colorByPoint: true,
            data: dataToShow
        }]
        
    });
    
    getPastAppointments(loggedInUser);
    
}

var getReportEnvironmentHtml = function(salesRepId){
	var envHtml = '<div class="row row-header page-header"><h1>Sales Representative\'s performance</h1></div>'+
					'<div class="row row-chart">'+
					'<div id="container-daily-meeting"  class="col-md-12" style="min-width: 310px; height: 400px; margin: 0 auto"></div>'+
					'</div>'+
					'<div class="row row-chart">'+
						'<div id="container-salesrep-meetingperformance" class="col-md-6" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>'+
						
						'<div id="container-salesrep-presentation" class="col-md-6" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>'+
						
					'</div>'+
					'<div class="row performance-detail-btn">'+
						'<button type="button" id="lost-isto-prescribing-'+salesRepId+'" class="btn btn-default btn-md past-appointment-detail-btn lost-isto-prescribing">'+
							'<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>'+
							'<span class="further-detail-btn-span">View Further Details</span>'+
						'</button>'+
						'<button type="button" id="phys-response-'+salesRepId+'" class="btn btn-default btn-md past-appointment-detail-btn phys-response-detail">'+
							'<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>'+
							'<span class="further-detail-btn-span">View Further Details</span>'+
						'</button>'+
					'</div>'+
					'<div class="salesrep-appointment-details" style="display : none;">'+
					'<div class="row row-header page-header"></div>'+
					'<div class="row date-filter-row">'+
						'<div class="basic-search-div">'+
							'<div class="filter-label">Filter for basic search</div>'+
							'<span class="year-label date-selector-label">Year : </span>'+
							'<div class="year-selector-div date-div">'+
								'<select class="year-selector search-filter-param" name="year">'+
									'<option value="2016" selected="selected">2016</option>'+
									'<option value="2015">2015</option>'+
									'<option value="2014">2014</option>'+
									'<option value="2013">2013</option>'+
									'<option value="2012">2012</option>'+
									'<option value="2011">2011</option>'+
									'<option value="2010">2010</option>'+
									'<option value="2009">2009</option>'+
									'<option value="2008">2008</option>'+
									'<option value="2007">2007</option>'+
								'</select>'+
							'</div>'+
							'<span class="month-label date-selector-label">Month : </span>'+
							'<div class="month-selector-div date-div">'+
								'<select class="month-selector search-filter-param" name="day">'+
									'<option value="0">month</option>'+
									'<option value="01">Jan</option>'+
									'<option value="02">Feb</option>'+
									'<option value="03">March</option>'+
									'<option value="04">April</option>'+
									'<option value="05">May</option>'+
									'<option value="06">June</option>'+
									'<option value="07">July</option>'+
									'<option value="08">August</option>'+
									'<option value="09">September</option>'+
									'<option value="10">October</option>'+
									'<option value="11">November</option>'+
									'<option value="12">December</option>'+
								'</select>'+
							'</div>'+
							'<span class="day-label date-selector-label">Day : </span>'+
							'<div class="day-selector-div date-div">'+
								'<select class="day-selector search-filter-param" name="day" disabled="disabled">'+
									'<option value="0">day</option>'+
								'</select>'+
							'</div>'+
							'<span class="status-label date-selector-label">Status : </span>'+
							'<div class="status-selector-div date-div">'+
								'<select class="status-selector search-filter-param" name="status">'+
									'<option value=" " selected="selected">select status</option>'+
									'<option value="LOST">LOST</option>'+
									'<option value="PRESCRIBING">PRESCRIBING</option>'+
								'</select>'+
							'</div>'+
						'</div>'+
						'<div class="advanced-search">'+
							'<div class="filter-label advanced-search-label">Advanced Search</div>'+
							'<span class="phys-response-label phys-response-selector-label">Physician responses : </span>'+
							'<div class="phys-response-selector-div advance-filter-div">'+
								'<select class="phys-response search-filter-param" name="year">'+
									'<option value="none: selected="selected">remove this filter</option>'+
									'<option value="presentation">Physician did not like presentation</option>'+
									'<option value="confidence">SalesRep was not confident</option>'+
									'<option value="reputation">Physician was not impressed with org\'s reputation</option>'+
								'</select>'+
							'</div>'+
						'</div>'+
					'</div>'+
					
					'</div>'+
					'<div class="row table-row" style="display: none;">'+
						'<div class="col-lg-12 col-sm-12">'+
							'<table id="past-appointments-table" class="table table-bordered past-appointment-table-class">'+
								'<thead>'+
									'<tr>'+
										'<th style="display:none" class="physician-id">Physician Id</th>'+
										'<th>Physician Name</th>'+
										'<th style="display:none" class="product-id">Product Id</th>'+
										'<th>Product</th>'+
										'<th>Date</th>'+
										'<th>Status</th>'+
										'<th>Further Details</th>'+	
										'<th style="display: none" class="physician-input">Physician Input</th>'+			
									'</tr>'+
								'</thead>'+
								'<tbody>'+
								'</tbody>'+
							'</table>'+
						'</div>'+
					'</div>';
	return envHtml;
}
