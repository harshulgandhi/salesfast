/**
 * 
 */
var chartData = [];


var allProducts = [];
$(document).ready(function(){
	
	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.sample-med-li').addClass('left-menu-selected');
	$('li.navbar-menu-selected').removeClass("navbar-menu-selected");
	   
	if(!$('li#nav-quick-assist').hasClass("navbar-menu-selected")){
		$('li#nav-quick-assist').addClass("navbar-menu-selected")
	}
	$.ajax({
		type: 'GET',
		url : '/getproductsforuser',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received : "+JSON.stringify(data));
	    	allProducts = data;
	    	addButtons(data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
	updateNotificationCounter();
});


$(document).on('click','button.show-analysis',function(){
	
	$('button.show-analysis-button-clicked').removeClass('show-analysis-button-clicked');
	$(this).addClass('show-analysis-button-clicked');
	
	$('.analysis-right-panel').css('display','none');
	var btnId = $(this).attr('id');
	var rightPanelId = '#analysis-right-panel-'+btnId;
	$(rightPanelId).slideToggle('fast');
	
});

$(document).on('click','button.show-comments',function(){
	var id = $(this).attr('id');
	$('.comments-div-'+id).slideToggle('fast');
});

var addButtons = function(productList){
	for(var i = 0; i<productList.length;i++){
		$('.btn-left-panel').append(
				'<button type="button" id="'+productList[i]["productId"]+'" class="btn btn-default btn-md show-analysis form-control" >'+
				'	<span class="button-value">'+productList[i]["productName"]+'</span> '+
				'	<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> '+
				'</button><br/>'
			);
		$('.row-custom-style').append(
				'<div class="col-lg-9 analysis-right-panel" id="analysis-right-panel-'+productList[i]["productId"]+'" style="display: none;">'+
			      	'<div id="container-sample-'+productList[i]["productId"]+'"  class="col-md-6" style="min-width: 310px; height: 400px;'+ 
			      	'max-width: 600px; margin: 0 auto"></div>'+
			      	'<button type="button" class="btn btn-default btn-md show-comments form-control" id= "'+productList[i]["productId"]+'">'+
					'	<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> '+
					'	<span class="button-value">View all comments</span> '+
					'</button><br/>'+
					'<div class="row comment-row">'+
						'<div id="side-effect-comments"  class="col-md-4 comments-div comments-div-'+productList[i]["productId"]+'" style="display: none;">'+
							'<table class="table">'+
								'<thead>'+
									'<tr>'+
										'<th>Side Effect Comments</th>'+
									'</tr>'+
								'</thead>'+
								'<tbody>'+
								'</tbody>'+
							'</table>'+
						'</div>'+
						'<div id="other-comments" class="col-md-4 comments-div comments-div-'+productList[i]["productId"]+'" style="display: none;">'+
							'<table class="table">'+
								'<thead>'+
									'<tr>'+
										'<th>Other Comments</th>'+
									'</tr>'+
								'</thead>'+
								'<tbody>'+
								'</tbody>'+
							'</table>'+
						'</div>'+
					'</div>'+
			    '</div>'
			);
		
		var containerId = '#container-sample-'+productList[i]["productId"];
		getAnalysisData(productList[i]["productId"], containerId, productList[i]["productName"]);
		
	}
	
	//Triggering click event to show first graph
	var list = $('button.show-analysis');
	var btn = list[0];
	$('#'+btn.id).trigger( "click" );
	$(window).resize();
}

var getAnalysisData = function(productId, containerId, productName){
	$.ajax({
		type: 'GET',
		url : '/getanalysisforproduct?id='+productId,
		dataType : 'json',
		success : function(data){
	    	console.log("Data received : "+JSON.stringify(data));
	    	if(data[0]["isMedicineEffective"] == "NaN") {
	    		for(var i = 0; i < data.length; i++){
	    		    data[i]["isMedicineEffective"] = 0.0;
	    		    data[i]["hasSideEffects"] = 0.0;
	    		    data[i]["isAffordable"] = 0.0;
	    		}
	    		console.log("Data after updating to 0 : "+JSON.stringify(data));
	    		formatData(data,containerId, productName,productId);
	    	}else{
	    		formatData(data,containerId, productName,productId);
	    	}
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
}

var formatData = function(rawData, containerId, productName,productId){
	var data = {};
	data['name'] = "Feedback";
	data['colorByPoint'] = true;
	data['data'] = [{
		 name: 'Is Medicine Effective?',
         y: rawData[0]["isMedicineEffective"],
         drilldown: null
	},{
		name: 'Does it have side effects?',
        y: rawData[0]["hasSideEffects"],
        drilldown: null
	},{
		name: 'Some Metric',
        y: rawData[0]["isAffordable"],
        drilldown: null
	}];
	chartData.push(data);
	createBarChart(containerId, chartData, productName,productId);
	chartData.length = 0;
}


var createBarChart = function(containerName, data, productName,productId){
	$(function () {
	    // Create the chart
	    $(containerName).highcharts({
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: 'Medicine '+productName+': patient\'s feedback'
	        },
	        subtitle: {
	            text: 'Firsthand feedback from medicine consumers'
	        },
	        xAxis: {
	            type: 'category'
	        },
	        yAxis: {
	            title: {
	                text: 'Percentage out of all feedbacks'
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

	        series: chartData
	      });
	});
	getSideEffectComments(productId);
	chartData.length = 0;
}


var getSideEffectComments = function(productId){
	$.ajax({
		type: 'GET',
		url : '/getsideeffectcomments?id='+productId,
		dataType : 'json',
		success : function(data){
	    	console.log("Data received for product "+productId+" : "+JSON.stringify(data));
	    	appendSideEffectComments(productId, data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
}

var appendSideEffectComments = function(productId, comments){
	for(var i = 0; i<comments.length; i++){
		$('#analysis-right-panel-'+productId).find('#side-effect-comments').find('table tbody').append(
				'<tr>'+
					'<td>'+comments[i]+'</td>'+
				'</tr>'
				);
	}
	getOtherComments(productId);
}

var getOtherComments = function(productId){
	$.ajax({
		type: 'GET',
		url : '/getothercomments?id='+productId,
		dataType : 'json',
		success : function(data){
	    	console.log("Data received for product "+productId+" : "+JSON.stringify(data));
	    	appendOtherComments(productId, data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
}

var appendOtherComments = function(productId, comments){
	for(var i = 0; i<comments.length; i++){
		$('#analysis-right-panel-'+productId).find('#other-comments').find('table tbody').append(
				'<tr>'+
					'<td>'+comments[i]+'</td>'+
				'</tr>'
				);
	}
}