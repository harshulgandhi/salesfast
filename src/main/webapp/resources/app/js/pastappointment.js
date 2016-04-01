/**
 * 
 */

var table = null;
var pastAppointments = []; 
var isFromAlignmentPage = false;
var physNameParamFromAlignments;
$(document).ready(function() {

	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.past-appointment-li').addClass('left-menu-selected');
	
	updateNotificationCounter();
	
	getPastAppointments();
	
	//Physician name received and parsed for table search
	var value;
	if (window.location.search.split('?').length > 1) {
        var params = window.location.search.split('?')[1].split('&');
        for (var i = 0; i < params.length; i++) {
            var key = params[i].split('=')[0];
            physNameParamFromAlignments = decodeURIComponent(params[i].split('=')[1]);
            console.log("Name : "+physNameParamFromAlignments);
            isFromAlignmentPage = true;
        }
    }
	
});
   
var getPastAppointments = function(){
	console.log("Fetching similar questions");
	$.ajax({
		type : 'GET',
		url : "/getpastappointments",
		dataType : 'json',
		success : function(data){
	    	console.log("Data received (Past Appointments) : "+JSON.stringify(data));
	    	populatePastAppTable(data);
	    	pastAppointments =  data;
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
}

$(document).on('click','button.past-appointment-detail-btn', function(){
	$('#pastappointment-details-modal').modal('show');
	var $row = $(this).parent().parent(); 
	var idx = $(this).attr('id');
	console.log(" idx : "+idx);
	
	$('ul.salesrep-response').html("");
	$('ul.physician-response').html("");
	$('div.modal-body-3').html("");
	
	if(pastAppointments[idx]["reasonsBySalesRep"].length == 0){
		$('ul.salesrep-response').append('<li class="meetingexp-salesrep-empty">You did not submit your experience about this meeting</li>');
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
		$('div.modal-body-3').append('<span>You did not upload any pitch for this meeting</span>');
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

var populatePastAppTable = function(appointments){
	
	for(var i = 0; i < appointments.length ; i++){
		console.log(appointments[i]["meetingStatus"]);
		if(appointments[i]["meetingStatus"] == "LOST"){
			$('table#past-appointments-table tbody').append(
					'<tr class="lost-appointment-tr danger">'+
						'<td style="display:none" class="physician-id">'+appointments[i]["physicianId"]+'</td>'+
						'<td>'+appointments[i]["physicianName"]+'</td>'+
						'<td style="display:none" class="product-id">'+appointments[i]["productId"]+'</td>'+
						'<td>'+appointments[i]["productName"]+'</td>'+
						'<td>'+appointments[i]["date"]+'</td>'+
						'<td>'+appointments[i]["meetingStatus"]+'</td>'+
						'<td class="further-detail-btn">'+
							'<div class="further-detail-click">'+
								'<button type="button" id="'+i+'" class="btn btn-default btn-md past-appointment-detail-btn">'+
									'<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>'+
									'<span class="further-detail-btn-span">View Further Details</span>'+
								'</button>'+
							'</div>'+
						'</td>'+				
					'</tr>'
			);
		}
		else if(appointments[i]["meetingStatus"] == "PRESCRIBING"){
			$('table#past-appointments-table tbody').append(
					'<tr class="prescribing-appointment-tr success">'+
						'<td style="display:none" class="physician-id">'+appointments[i]["physicianId"]+'</td>'+
						'<td>'+appointments[i]["physicianName"]+'</td>'+
						'<td style="display:none" class="product-id">'+appointments[i]["productId"]+'</td>'+
						'<td>'+appointments[i]["productName"]+'</td>'+
						'<td>'+appointments[i]["date"]+'</td>'+
						'<td>'+appointments[i]["meetingStatus"]+'</td>'+
						'<td class="further-detail-btn">'+
							'<div class="further-detail-click">'+
								'<button type="button" id="'+i+'" class="btn btn-default btn-md past-appointment-detail-btn">'+
									'<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>'+
									'<span class="further-detail-btn-span">View Further Details</span>'+
								'</button>'+
							'</div>'+
						'</td>'+				
					'</tr>'
			);
		}
		else{
			$('table#past-appointments-table tbody').append(
					'<tr class="other-appointment-tr">'+
						'<td style="display:none" class="physician-id">'+appointments[i]["physicianId"]+'</td>'+
						'<td>'+appointments[i]["physicianName"]+'</td>'+
						'<td style="display:none" class="product-id">'+appointments[i]["productId"]+'</td>'+
						'<td>'+appointments[i]["productName"]+'</td>'+
						'<td>'+appointments[i]["date"]+'</td>'+
						'<td>'+appointments[i]["meetingStatus"]+'</td>'+
						'<td class="further-detail-btn">'+
							'<div class="further-detail-click">'+
								'<button type="button" id="'+i+'" class="btn btn-default btn-md past-appointment-detail-btn">'+
									'<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>'+
									'<span class="further-detail-btn-span">View Further Details</span>'+
								'</button>'+
							'</div>'+
						'</td>'+				
					'</tr>'
			);
		}
		
	}
	table = $('table#past-appointments-table').DataTable();
	if(isFromAlignmentPage){
		table.search(physNameParamFromAlignments).draw();
	}
}