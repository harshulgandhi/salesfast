/**
 * 
 */
var numDays = {
                '01': 31, '02': 28, '03': 31, '04': 30, '05': 31, '06': 30,
                '07': 31, '08': 31, '09': 30, '10': 31, '11': 30, '12': 31
              };
var table = null;
var pastAppointments = []; 
var isFromAlignmentPage = false;
var physNameParamFromAlignments;
$(document).ready(function() {

	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.past-appointment-li').addClass('left-menu-selected');
	$('li.navbar-menu-selected').removeClass("navbar-menu-selected");
	   
	if(!$('li#nav-appointment').hasClass("navbar-menu-selected")){
		$('li#nav-appointment').addClass("navbar-menu-selected")
	}
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
	
	$('select.search-filter-param').select2({ width: '100%' });
});

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
		var day = $(this).val();
		var searchTerm = year+'-'+month+'-'+day;
		console.log("$('.status-selector').val() : "+$('.status-selector').val());
		if($('.status-selector').val() != 'none'){
			searchTerm  = searchTerm +' '+$('.status-selector').val(); 
			table.search(searchTerm).draw();
		}else{
			table.search(searchTerm).draw();
		}
	}
	else if($(this).hasClass('month-selector')){
		if(!$('select.day-selector').attr('disabled')){
			var year = $('select.year-selector').val();
			var month = $('select.month-selector').val();
			$('select.day-selector').select2('val',currentSelectedDate);
			var day = $('select.day-selector').val();
			console.log('day : '+day)
			console.log("$('.status-selector').val() : "+$('.status-selector').val());
			if(day == '0'){
				var searchTerm = year+'-'+month;
				if($('.status-selector').val() != 'none'){
					searchTerm  = searchTerm +' '+$('.status-selector').val(); 
					table.search(searchTerm).draw();
				}else{
					table.search(searchTerm).draw();
				}
			}
			else{
				var searchTerm = year+'-'+month+'-'+day;
				if($('.status-selector').val() != 'none'){
					searchTerm  = searchTerm +' '+$('.status-selector').val(); 
					table.search(searchTerm).draw();
				}else{
					table.search(searchTerm).draw();
				}
			}
		}
	}else if($(this).hasClass('status-selector')){
		var year = $('select.year-selector').val();
		var month = $('select.month-selector').val();
		var day = $('select.day-selector').val();
		var status = $(this).val();
		if(month == "0" && status != 'none'){
			searchTerm = year+' '+status;
			table.search(searchTerm).draw();
		}else if(month != "0" && day == "0" && status != 'none'){
			searchTerm = year+' '+month+' '+status;
			table.search(searchTerm).draw();
		}else if(month != "0" && day != "0" && status != 'none'){
			searchTerm = year+' '+month+' '+day+' '+status;
			table.search(searchTerm).draw();
		}else if(month == "0" && status == 'none'){
			searchTerm = year+' '+status;
			table.search(searchTerm).draw();
		}
	}else if($(this).hasClass('phys-response')){
		var year = $('select.year-selector').val();
		var month = $('select.month-selector').val();
		var day = $('select.day-selector').val();
		var status = $('select.status-selector').val();
		var physResponseCategory = $(this).val();
		searchTerm = '';
		if(month != '0' ){
			searchTerm += year+' '+month;
		}
		if(month !='0' && day != '0' ){
			searchTerm += ' '+month;
		}
		if(status != 'none') searchTerm += ' '+status;
		if(physResponseCategory != 'none') {
			if(physResponseCategory == 'presentation'){
				searchTerm += ' '+'presentation problem';	
			}else if(physResponseCategory == 'confidence'){
				searchTerm += ' '+'confidence problem';
			}else if(physResponseCategory == 'reputation'){
				searchTerm += ' '+'maintain reputation problem';
			}
		}
		table.search(searchTerm).draw();
		
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
						'<td class="further-detail-btn">'+
							'<div class="further-detail-click">'+
								'<button type="button" id="'+i+'" class="btn btn-default btn-md past-appointment-detail-btn">'+
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
						'<td class="further-detail-btn">'+
							'<div class="further-detail-click">'+
								'<button type="button" id="'+i+'" class="btn btn-default btn-md past-appointment-detail-btn">'+
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
						'<td class="further-detail-btn">'+
							'<div class="further-detail-click">'+
								'<button type="button" id="'+i+'" class="btn btn-default btn-md past-appointment-detail-btn">'+
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
	table = $('table#past-appointments-table').DataTable({
		   "searching": true
	   });
	if(isFromAlignmentPage){
		table.search(physNameParamFromAlignments).draw();
	}
}