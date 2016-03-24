/**
 * 
 */
var virtualLearningData = [];
$(document).ready(function () {
	
	$.ajax({
		type: 'GET',
		url : '/getedetailingdata',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received : "+JSON.stringify(data));
	    	createEDetailingEnvironment(data);
	    	virtualLearningData = data;
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
	
	updateNotificationCounter();
});

var createEDetailingEnvironment = function(data){
	for(var i = 0; i<data.length; i++){
		$('.btn-left-panel').append(
				'<button type="button" class="btn btn-default btn-md show-doc-edetail" >'+
				'	<span class="button-value">'+data[i]["productName"]+'</span> '+
				'	<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> '+
				'</button><br/>'
		);
	}
	$('.btn-left-panel').find('button').on('click',function(){
		$('.doc-middle-panel').css('display','none');
		$('.info-right-panel').css('display','none');
	    var idx = $('.btn-left-panel').find('.show-doc-edetail').index(this);
	    var str = $(this).find('.button-value').html();
	    str = str.replace(/\s+/g, '-').toLowerCase();   // Removing space from name
	    str = str.replace('\'', '').toLowerCase();		// Removing apostrophe
	    if($('.row-custom-style').find('#'+str).length == 0){		//To check if Object already exists then don't append, otherwise do.
			$('.row-custom-style').append(
					'<div class="col-lg-6 doc-middle-panel" id="'+str+'" style="display: none;">'+
						'<object class="pdf-doc-object pdf-doc-object-slidedown" data="'+(data[idx]["filePath"] + data[idx]["fileName"]+".pdf")+'" type="application/pdf" >'+
							'<embed src="'+(data[idx]["filePath"] + data[idx]["fileName"]+".pdf")+'" alt="pdf" pluginspage="http://www.adobe.com/products/acrobat/readstep2.html">'+
							'</embed>'+
						'</object>'+
					'</div>'+
					'<div class="col-lg-3 info-right-panel" id="'+str+'-salesrep-info" style="display: none;>'+
						'<span class="salesrep-info-span">Contact our Sales Representative in case you need answers to any questions.</span>'+
						'<table id="salesrep-info-table" class="table">'+
							'<thead>'+
							'</thead>'+
							'<tbody>'+
								'<tr>'+
									'<td id="caption">Sales Representative : </td>'+
									'<td id="value">'+data[idx]["salesRepName"]+'</td>'+
								'</tr>'+
								'<tr>'+
									'<td id="caption">Contact Number : </td>'+
									'<td id="value">'+data[idx]["salesRepContact"]+'</td>'+
								'</tr>'+
							'</tbody>'+
						'</table>'+
			        '</div>'
			);
	    }
		var id = '#'+str;
		var id_salesrep_info = '#'+str+'-salesrep-info'; 
		$(id).slideToggle('fast');
		$(id_salesrep_info).slideToggle('fast');
	});
}