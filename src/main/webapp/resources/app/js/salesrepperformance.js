/**
 * 
 */

$(document).ready(function(){
	
	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.salesrep-performance').addClass('left-menu-selected');
	
	$('li.navbar-menu-selected').removeClass("navbar-menu-selected");
	   
	if(!$('li#nav-data-report').hasClass("navbar-menu-selected")){
		$('li#nav-data-report').addClass("navbar-menu-selected")
	}
	
	$('.month-selector').select2();
	updateNotificationCounter();
	getAssignedSalesRepList();
	
});

$(document).on('change','select.filter-selectors', function(){
	updateNotificationCounter();
	$('div#layout').html("");
	$('div#layout').append(
			'<div class="row row-header page-header"><h1>Sales Representative\'s performance</h1></div>'+
			'<div class="row row-chart">'+
				'<div id="container-daily-meeting"  class="col-md-12" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>'+
			'</div>'+
			'<div class="row row-chart">'+
				'<div id="container-salesrep-meetingperformance" class="col-md-6" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>'+
				'<div id="container-salesrep-presentation" class="col-md-6" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>'+
			'</div>'+
			'<div class="row row-table">'+
			'</div>'
				
	);
	getDailyMeetingCount($('.salesrep-selector').val(), $('.month-selector').val());
	getMeetingStatusAnalysis($('.salesrep-selector').val());
	getPhysicianResponse();
});

var getPhysicianResponse = function(){
	var data = {};
	data["medicalFieldId"] = "none";
	data["productId"] = "0";
	data["userId"]= $('.salesrep-selector').val();
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
			createPhysicianResponseChart("#container-salesrep-presentation",data, $('.salesrep-selector').find('option[selected="selected"]').html());
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


var getAssignedSalesRepList = function(){
	$.ajax({
		type: 'GET',
		url : '/getallassignedsalesrep',
		dataType : 'json',
		success : function(data){
//	    	console.log("Data received : "+JSON.stringify(data));
	    	populateSalesRepDropDown(data);
//	    	if(data[0]["isMedicineEffective"] == "NaN") {
//	    		for(var i = 0; i < data.length; i++){
//	    		    data[i]["isMedicineEffective"] = 0.0;
//	    		    data[i]["hasSideEffects"] = 0.0;
//	    		    data[i]["isAffordable"] = 0.0;
//	    		}
//	    		console.log("Data after updating to 0 : "+JSON.stringify(data));
//	    		formatData(data,containerId, productName,productId);
//	    	}else{
//	    		formatData(data,containerId, productName,productId);
//	    	}
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
}

var populateSalesRepDropDown = function(allSalesRep){
	var salesRepName = [];
	var salesRepId = [];
	for(var i = 0 ; i < allSalesRep.length; i++){
		var fullName = allSalesRep[i]["firstName"]+" "+allSalesRep[i]["lastName"];
		if(salesRepName.indexOf(fullName) < 0){
			salesRepName.push(fullName);
			salesRepId.push(allSalesRep[i]["userId"]);
		}
	}
	
	for(var i = 0; i< salesRepName.length; i++){
		if(salesRepName[i] == 'Johny Depp'){
			$('select.salesrep-selector').append(
				'<option value="'+salesRepId[i]+'" selected="selected">'+salesRepName[i]+'</option>'
			);
		}else{
			$('select.salesrep-selector').append(
					'<option value="'+salesRepId[i]+'">'+salesRepName[i]+'</option>'
				);
		}
	}
	$('.salesrep-selector').select2();
	$('div#layout').append(
			'<div class="row row-header page-header"><h1>Sales Representative\'s performance</h1></div>'+
			'<div class="row row-chart">'+
				'<div id="container-daily-meeting"  class="col-md-12" style="min-width: 310px; height: 400px; margin: 0 auto"></div>'+
			'</div>'+
			'<div class="row row-chart">'+
				'<div id="container-salesrep-meetingperformance" class="col-md-6" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>'+
				'<div id="container-salesrep-presentation" class="col-md-6" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>'+
			'</div>'+
			'<div class="row row-table">'+
			'</div>'
				
	);
	getDailyMeetingCount($('.salesrep-selector').val(), $('.month-selector').val());
	getMeetingStatusAnalysis($('.salesrep-selector').val());
	getPhysicianResponse();
}



var getMeetingStatusAnalysis = function(salesRepId){
	$.ajax({
		type: 'GET',
		url : '/getmeetingstatusanalysis?userId='+salesRepId,
		dataType : 'json',
		success : function(data){
	    	console.log("Data received : "+JSON.stringify(data));
//	    	createDailyAppointmentChart("#container-daily-meeting",	data, $('.salesrep-selector').find('option[selected="selected"]').html());
	    	createMeetingStatusChart('#container-salesrep-meetingperformance',data,$('.salesrep-selector').find('option[selected="selected"]').html());
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
}

var getDailyMeetingCount = function(salesRepId, month){
	
	$.ajax({
		type: 'GET',
		url : '/getdailymeetingcount?userId='+salesRepId+'&month='+month,
		dataType : 'json',
		success : function(data){
	    	console.log("Data received : "+JSON.stringify(data));
	    	createDailyAppointmentChart("#container-daily-meeting",	data, $('.salesrep-selector').find('option[selected="selected"]').html());
//	    	if(data[0]["isMedicineEffective"] == "NaN") {
//	    		for(var i = 0; i < data.length; i++){
//	    		    data[i]["isMedicineEffective"] = 0.0;
//	    		    data[i]["hasSideEffects"] = 0.0;
//	    		    data[i]["isAffordable"] = 0.0;
//	    		}
//	    		console.log("Data after updating to 0 : "+JSON.stringify(data));
//	    		formatData(data,containerId, productName,productId);
//	    	}else{
//	    		formatData(data,containerId, productName,productId);
//	    	}
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
}

var createPhysicianResponseChart=function(containerId, data, salesRepName){
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
            text: 'What physicians think about '+salesRepName
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
}


var createMeetingStatusChart = function(containerId, data, salesRepName){
	var analysisData = [];
	$.each(data, function(k,v){
		k.split('_').join(' ');
		analysisData.push([k, v]);
	});
	
	$(containerId).highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: 0,
            plotShadow: false
        },
        title: {
            text: salesRepName+'<br>Performance',
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

var createDailyAppointmentChart = function(containerId, data, salesRepName){
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
	            text: 'Meetings executed by Mr.'+salesRepName
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