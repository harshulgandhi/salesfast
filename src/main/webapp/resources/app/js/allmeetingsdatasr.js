/**
 * 
 */

$(function () {
	
    $(document).ready(function () {
    	$('li.left-menu-selected').removeClass('left-menu-selected');
    	$('li.meeting-analysis').addClass('left-menu-selected');
    	
	   $('li.navbar-menu-selected').removeClass("navbar-menu-selected");
		   
	   if(!$('li#nav-data-report').hasClass("navbar-menu-selected")){
		   $('li#nav-data-report').addClass("navbar-menu-selected")
	   }
	   $.ajax({
			type : 'GET',
			url : '/getloggedinuserid',
			contentType : 'application/json',
			success : function(data){
				console.log("Current userId : "+JSON.stringify(data));
				getAnalyzedData(data);
			},
			error : function(e){
				console.log("Error : "+JSON.stringify(e));
			}
		}).done(function(){
			console.log("Filter sent and data received");
		});
	   
		updateNotificationCounter();
		$('.filter-selectors').select2();
		
    });
});

$(document).on('change','select.filter-selectors',function(){
	updateNotificationCounter();
	$('.filter-selectors').each(function(i, val){
		console.log("filter "+i+" : "+$(this).val());
	});
	getAnalyzedData();
});

var getAnalyzedData = function(salesRepId){
	var data = {};
	data["medicalFieldId"] = 'none';
	data["productId"] = $('.product-selector').val();
	data["userId"]= salesRepId;
	data["status"] = $('.status-selector').val();
	console.log("Filter created : "+JSON.stringify(data));
	
//	$("#view-pitch-table > tbody").html("");
	
	$.ajax({
		type : 'POST',
		url : '/getfilteredanalyzeddata',
		data : JSON.stringify(data),
		contentType : 'application/json',
		success : function(data){
			console.log("Analysis for data : "+JSON.stringify(data));
			seggregateAnalyzedData(data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("Filter sent and data received");
	});
}
var seggregateAnalyzedData = function(data){
	$('div#layout').html("");
	$('div#layout').append(
			'<div class="row row-header page-header"><h1>Factors effecting the detailing meetings</h1></div>'+
			'<div class="row row-chart">'+
				'<div id="container-according-to-physicians"  class="col-md-6" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>'+
				'<div id="container-according-to-salesreps" class="col-md-6" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>'+
			'</div>'+
			'<div class="row row-chart aggregated-report">'+
				'<div id="container-aggregated" class="col-md-6" style="min-width: 310px; height: 400px; width: 680px; margin: 0 auto"></div>'+
			'</div>'
	);
	for(var i = 0; i<data.length;i++){
		createAnalysisCharts(data[i]);
	}
	
}

var createAnalysisCharts = function(eachCatData){
	var containerId = '#container-'+eachCatData["category"].replace(/\s+/g, '-').toLowerCase();
	var data = eachCatData["analyzedData"];
	$.each(data, function(k, v) {
		if(data[0]["y"] == "NaN"){
    		for(var i = 0; i<data.length; i++){
    			data[i]["y"] = 0.0;
    		}
		}
	});
	var title = eachCatData["category"];
	createChart(containerId, title, data);
}

var createChart=function(containerName, title, data){
    // Build the chart
	console.log("Container Name : "+containerName+", Title : "+title+", data : "+JSON.stringify(data));
    $(containerName).highcharts({
    	chart: {
            type: 'column'
        },
        title: {
            text: title
        },
        subtitle: {
            text: 'Meeting experience parameters'
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
            data: data
        }]
        
    });
}