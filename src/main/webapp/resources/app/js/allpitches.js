/**
 * 
 */

$(document).ready(function(){
	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.all-pitches-li').addClass('left-menu-selected');
	$('.filter-selectors').select2();
	
	updateNotificationCounter();
});

$(document).on('change','select.filter-selectors',function(){
	updateNotificationCounter();
	
	$('.filter-selectors').each(function(){
		console.log($(this).val());
	});
	var data = {};
	data["medicalFieldId"] = $('.med-field-selector').val();
	data["productId"] = $('.product-selector').val();
	data["userId"]= $('.salesrep-selector').val();
	data["physicianId"] = $('.physician-selector').val();
	console.log("Filter created : "+JSON.stringify(data));
	
	$("#view-pitch-table > tbody").html("");
	
	$.ajax({
		type : 'POST',
		url : '/filterparameters',
		data : JSON.stringify(data),
		contentType : 'application/json',
		success : function(data){
			console.log("Pitch to be shown : "+JSON.stringify(data));
			createPitchEnvironment(data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("Sent all pitch paramaters");
	});
	
});

$(document).on('click','button.show-pitch-doc',function(){
	if($(this).hasClass('pitch-button-clicked')){
		$(this).removeClass('pitch-button-clicked');
	}else{
		$(this).addClass('pitch-button-clicked');
	}
//	$('button.pitch-button-clicked').removeClass('pitch-button-clicked');
	var id = $(this).attr('id');
	
	$('#pitch-object-div-'+id).slideToggle('fast');
	$('#pitch-detail-div-'+id).slideToggle('fast');
	
});

var createPitchEnvironment = function(pitchList){
	if(pitchList.length == 0){
		$('table#view-pitch-table tbody').append(
			'<tr>'+
				'<td></td>'+
				'<td>'+
					'<div style="font-weight: 600;" class="no-pitches-div">'+
						'No pitches available for this filter, please try to change the filter values.'+
					'</div>'+
				'</td>'+
				'<td></td>'+
			'</tr>'
		);
		$('table#view-pitch-table tbody').find('tr').css('background-color','#E8D3CE');
	}
	else{
		for(var i = 0; i < pitchList.length; i++){
			$('table#view-pitch-table tbody').append(
					'<tr>'+
						'<td class="pitch-buttons">'+
							'<button type="button" class="btn btn-default btn-md show-pitch-doc form-control" id="'+i+'">'+
								'<span class="button-value">Pitch '+(i+1)+'</span>'+
								'<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>'+
							'</button><br/>'+
						'</td>'+
						'<td class="view-pitch-doc-td">'+
							'<div class="pitch-doc-object-div" id="pitch-object-div-'+i+'" style="display: none;">'+
								'<object class="pdf-doc-object pdf-doc-object-slidedown" id="pitch-object-'+i+'" data="'+pitchList[i]["fileLocation"]+'" type="application/pdf">'+
									'<embed src="'+pitchList[i]["fileLocation"]+'" alt="pdf" pluginspage="http://www.adobe.com/products/acrobat/readstep2.html">'+
									'</embed>'+
								'</object>'+
							'</div>'+
						'</td>'+
						'<td class="view-meeting-details-td">'+
							'<div class="meeting-detail-div" id="pitch-detail-div-'+i+'" style="display: none;">'+
								'<div class="each-detail-row">'+
									'<span class="detail-lable">Uploaded By : </span>'+
									'<Span class="detail-value">'+pitchList[i]["salesRepName"]+'</Span>'+
								'</div>'+
								'<div class="each-detail-row">'+
									'<span class="detail-lable">Meeting with : </span>'+
									'<span class="detail-value">'+pitchList[i]["physicianName"]+'</span>'+
								'</div>'+
								'<div class="each-detail-row">'+
									'<span class="detail-lable">Product detailed : </span>'+
									'<span class="detail-value">'+pitchList[i]["productName"]+'</span>'+
								'</div>'+
								'<div class="each-detail-row">'+
									'<span class="detail-lable">Discussed on : </span>'+
									'<span class="detail-value">'+pitchList[i]["date"]+'</span>'+
								'</div>'+
								'<div class="each-detail-row">'+
									'<span class="detail-lable">Outcome of meeting was : </span>'+
									'<span class="detail-value">'+pitchList[i]["meetingStatus"]+'</span>'+
								'</div>'+
							'</div>'+
						'</td>'+
					'</tr>'
			
			);
		}
	}
	var table = $('#view-pitch-table').dataTable();
	$('#0').trigger("click");
}