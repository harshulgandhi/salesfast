/**
 * 
 */
var chartData = [];
	               /* ['Is Medicine Effective',   60.2],
	                ['Does it have side effects',   42.4],
	                ['Is it affordable', 50.4]
	            ];*/

//[{"isMedicineEffective":30,"hasSideEffects":60,"isAffordable":30}]


var allProducts = [];
$(document).ready(function(){
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
}

var getAnalysisData = function(productId, containerId, productName){
	$.ajax({
		type: 'GET',
		url : '/getanalysisforproduct?id='+productId,
		dataType : 'json',
		success : function(data){
	    	console.log("Data received : "+JSON.stringify(data));
	    	formatData(data,containerId, productName,productId);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
}

var formatData = function(rawData, containerId, productName,productId){
	chartData.push(['Is Medicine Effective', rawData[0]["isMedicineEffective"]]);
	chartData.push(['Does it have side effects', rawData[0]["hasSideEffects"]]);
	chartData.push(['Is it affordable', rawData[0]["isAffordable"]]);
	createChart(containerId, chartData, productName,productId);
	chartData.length = 0;
}

var createChart=function(containerName, data, productName,productId){
    // Build the chart
	$(containerName).highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: 0,
	            plotShadow: false
	        },
	        title: {
	            text: productName+'<br>Feedback',
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
	                        color: 'black'
	                        
	                    }
	                },
	                startAngle: -90,
	                endAngle: 90,
	                center: ['50%', '75%']
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: 'Browser share',
	            innerSize: '50%',
	            data: data
	        }]
	    });
	
	getSideEffectComments(productId);
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