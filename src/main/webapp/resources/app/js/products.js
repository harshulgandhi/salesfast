/**
 * 
 */
var chartData = [];
var currentProductId = 0;
var productFiles = [];

$(document).ready(function(){
	
	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.view-products').addClass('left-menu-selected');
	$('li.navbar-menu-selected').removeClass("navbar-menu-selected");
	   
	if(!$('li#nav-products').hasClass("navbar-menu-selected")){
		$('li#nav-products').addClass("navbar-menu-selected")
	}
	updateNotificationCounter();
	
//	$('.product-selector').select2();
	
	$.ajax({
		type: 'GET',
		url : '/getallproducts',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received (All products): "+JSON.stringify(data));
	    	populateProductSelector(data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
	
	$('.product-selector').on('change',function(){
		var id = $('.product-selector').val();
		currentProductId = id;
		$('.product-description').css('display','block');
		
		$('#products-description-table').find('tr.description').css('display','none');
		$('#products-description-table').find('tr.product-'+id).css('display','block');
		
		$('.btn-container').css('display','none');
		$('.update-right-panel').css('display','none');
		$('.doc-middle-panel').css('display','none');
		$('.analysis-right-panel').css('display','none');
		productFiles = [];
		productFiles.length = 0;
		$.ajax({
			type: 'GET',
			url : '/getproductdocuments?productId='+id,
			dataType : 'json',
			success : function(data){
		    	productFiles = data;
		    	console.log("Data received (Product Files): "+JSON.stringify(data));
		    	createDocsViewEnvironment(data, currentProductId);
			},
			error : function(e){
				console.log("Error : "+JSON.stringify(e));
			}
		}).done(function(){
			console.log("ajax complete!");
		});
		$('.btn-container-div-product-'+id).css('display','block');
	});
	
});

var populateProductSelector = function(products){
	for(var i = 0; i < products.length; i++ ){
		$('select.product-selector').append(
			'<option  value="'+products[i]["productId"]+'">'+products[i]["productName"]+'</option>'	
		);
		$('table#products-description-table').append(
				'<tr class="product-'+products[i]["productId"]+' description" style="display:none">'+
					'<td class="descr-label label-name">Product : </td>'+
					'<td class="descr-value product-name">'+products[i]["productName"]+'</td>'+
				'</tr>'+
				'<tr class="product-'+products[i]["productId"]+' description" style="display:none">'+
					'<td class="descr-label label-med-field">Medical Field : </td>'+
					'<td class="descr-value medical-field">'+products[i]["medicalFieldId"]+'</td>'+
				'</tr>'+
				'<tr class="product-'+products[i]["productId"]+' description" style="display:none">'+
					'<td class="descr-label label-release-date">Released On : </td>'+
					'<td class="descr-value release-date">'+products[i]["releasedOn"]+'</td>'+
				'</tr>'
		);
	}
	$('#product-selector-id').select2().val('1').trigger("change");
}

var createDocsViewEnvironment = function(data, productId){
	if($('.btn-left-panel').find('.btn-for-product-'+currentProductId).length == 0){
		$('.btn-left-panel').append(
				'<div class="btn-container-div-product-'+currentProductId+' btn-container">'+
				'</div>'
		);
	for(var i = 0; i<data.length; i++){
		$('.btn-container-div-product-'+currentProductId).append(
				'<button type="button" class="btn btn-default btn-md show-doc form-control btn-for-product-'+currentProductId+'" >'+
				'	<span class="button-value">'+data[i]["labelToShow"]+'</span> '+
				'	<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> '+
				'</button><br/>'
			);
		}
	attachClickEvent(currentProductId);
	}
	var b = $('.btn-for-product-'+productId)[0];
	$(b).click();
}

var attachClickEvent = function(productId){
	$(document).on('click','button.btn-for-product-'+productId,function(){
		$('button.show-doc').removeClass('product-doc-button-clicked');
		$(this).addClass('product-doc-button-clicked');
		$('.doc-middle-panel').css('display','none');
		$('.update-right-panel').css('display','none');
		$('.analysis-right-panel').css('display','none');
	    var idx = $('.btn-container-div-product-'+productId).find('.show-doc').index(this);
	    console.log("idx : "+idx);
	    var str = $(this).find('.button-value').html();
	    str = str.replace(/\s+/g, '-').toLowerCase();   // Removing space from name
	    str = str.replace('\'', '').toLowerCase();		// Removing apostrophe
	    str = str.replace('(', '').toLowerCase();		// Removing ( from name
	    str = str.replace(')', '').toLowerCase();		// Removing ) from name
	    console.log("str : "+str);
	    if($('.row-custom-style').find('#'+str+'-product'+currentProductId).length == 0){		//To check if Object already exists then don't append, otherwise do.
	    	if(str == 'sample-medicine-feedback-analysis'){
	    		$('.row-custom-style').append(
	    				'<div class="col-lg-9 analysis-right-panel" id="analysis-right-panel-'+currentProductId+'" style="display: none;">'+
	    			      	'<div id="container-sample-'+currentProductId+'"  class="col-md-6" style="min-width: 310px; height: 400px;'+ 
	    			      	'max-width: 600px; margin: 0 auto"></div>'+
	    			      	'<button type="button" class="btn btn-default btn-md show-comments form-control" id= "'+currentProductId+'">'+
	    					'	<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> '+
	    					'	<span class="button-value">View all comments</span> '+
	    					'</button><br/>'+
	    					'<div class="row comment-row">'+
	    						'<div id="side-effect-comments"  class="col-md-4 comments-div comments-div-'+currentProductId+'" style="display: none;">'+
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
	    						'<div id="other-comments" class="col-md-4 comments-div comments-div-'+currentProductId+'" style="display: none;">'+
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
	    		
	    		var containerId = '#container-sample-'+currentProductId;
	    		var productName = $('.product-selector').find('option[value="'+currentProductId+'"]').html();
	    		getAnalysisData(currentProductId, containerId, productName);
	    		$('.analysis-right-panel').css('display','none');
	    		var rightPanelId = '#analysis-right-panel-'+currentProductId;
	    		$(rightPanelId).slideToggle('fast');
	    		
	    	}else{
				$('.row-custom-style').append(
						'<div class="col-lg-6 doc-middle-panel" id="'+str+'-product'+currentProductId+'" style="display: none;">'+
							'<object class="pdf-doc-object pdf-doc-object-slidedown" data="'+productFiles[idx]["filePath"] +'" type="application/pdf" >'+
								'<embed src="'+productFiles[idx]["filePath"] + '" alt="pdf" pluginspage="http://www.adobe.com/products/acrobat/readstep2.html">'+
								'</embed>'+
							'</object>'+
						'</div>'+
						'<div class="col-lg-3 update-right-panel" id="'+str+'-update-doc-'+currentProductId+'" style="display: none;">'+
							'<form id="update-files-form-'+currentProductId+'" class="form-horizontal" action="/updateproductfile" method="post" enctype="multipart/form-data">'+
								'<div class="form-group">'+
									'<label for="product-doc" class="control-label" id="product-doc-label">Update This Doc</label>'+
									'<input name="productdocument" type="file" class="form-control validate-ele" id="product-doc-update"/>'+
								'</div>'+
								'<div class="form-group">'+
									'<input type="hidden" class="hiddent-param selected-product-id" name="selectedProductId" value="'+currentProductId+'"></input>'+
								'</div>'+
								'<div class="form-group">'+
									'<input type="hidden" class="hiddent-param type-of-doc" name="typeOfDocument" value="'+productFiles[idx]["labelToShow"]+'"></input>'+
								'</div>'+
								'<button type="submit" id="update-file-form-submit-'+currentProductId+'" class="btn btn-default submit-updated-doc">Update Document</button>'+
							 '</form>'+
					    '</div>'
				);
	    	}
	    }
	    
		var id = '#'+str+'-product'+currentProductId;
		var id_doc_update = '#'+str+'-update-doc-'+currentProductId; 
		$(id).slideToggle('fast');
		$(id_doc_update).slideToggle('fast');
	});
	
}

$(document).on('click','button.show-comments',function(){
	var id = $(this).attr('id');
	$('.comments-div-'+id).slideToggle('fast');
});

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
		name: 'Is it affordable?',
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
	                text: 'Percentage of total feedbacks'
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


