/**
 * 
 */
var selectedAlignments = [];
var tableAppointment = null;
var currentSelectedAppointment = 0;
var averageTravelTime = 30; //minutes
$(document).ready(function() {
	 var table = $('#aligned-vicinity-physician-table').DataTable({
		   order: [[0, "desc"]],
		   "searching": true
	   });
	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.today-appointment-li').addClass('left-menu-selected');
	
	$('li.navbar-menu-selected').removeClass("navbar-menu-selected");
	   
	if(!$('li#nav-appointment').hasClass("navbar-menu-selected")){
		$('li#nav-appointment').addClass("navbar-menu-selected")
	}
	
	$('.slidedown-alignments').click(function(){
		    $('.slidedown-alignments-show').slideToggle('fast');
	});
   
//	table.order( [ 17, 'desc' ] ).draw();
   
   $('#appointment-fixed-physician-table').find('tr').each(function(i, val){
	   if($(val).find('.confirmation-status').html() == 'CANCELLED'){
		   $(val).css('background-color','mistyrose');
	   }
   })

   
   $('td.physician-name-td').append(
		   '<span class="warning-meeting-update top" title="Double click to submit meeting update"'+
		   'data-original-title="Double click to submit meeting update" style="color:orange;">&#9888;</span>'
		   );
   $(".top").tooltip({
	    placement: "top"
	});
	$(".right").tooltip({
	    placement: "right"
	});
	$(".bottom").tooltip({
	    placement: "bottom"
	});
	$(".left").tooltip({
	    placement: "left"
	});
	
   updateNotificationCounter();
   
//   $('.today-appointment-li').on('click', function(){
//	   $('.future-appointment-div').css('display','none');
//	   $('.today-appointment-div').css('display','block');
//   });
//   
//   $('.future-appointment-li').on('click', function(){
//	   $('.today-appointment-div').css('display','none');
//	   $('.future-appointment-div').css('display','block');
//   });
//   
//   $('.left-menu-ul').on('click','li',function(){
//	   $('li.left-menu-selected').removeClass('left-menu-selected');
//	   $(this).addClass('left-menu-selected');
//	   $(this).find('a').addClass('left-menu-selected-anchor');
//   });
//   
//   $( ".today-appointment-li" ).trigger( "click" );
	   
   /**
    * Toggles between selected and de-selected rows of the table
    * Takes care of clicks done on the 'time' element 
    */
    $('#aligned-vicinity-physician-table tbody').on( 'click', 'tr', function (e) {
    	var cell = $(e.target).get(0);
    	console.log(cell);
    	if(cell.childElementCount < 1 && cell.nodeName != 'INPUT' && cell.nodeName != 'SPAN' && cell.nodeName != 'TEXTAREA' ){
	    	$(this).toggleClass('selected');
	    	if ( $(this).hasClass('selected') ) {
	    		$(this).css('background-color','#08C');
	            $(this).find('.appointment-paramaters').prop("disabled",false);
	        }else{
	        	setRowColor($(this));
				$(this).find('.appointment-paramaters').prop("disabled",false);
	        }
    	}
    });
    

	/**
    * Toggles between selected and de-selected rows of the follow up
    * appointment table. Takes care of clicks done on the 'time' element 
    */
    var defaultColorFollowup;
    $('#followup-appointments-table tbody').on( 'click', 'tr', function (e) {
    	var cell = $(e.target).get(0);
    	console.log(cell);
    	if(cell.childElementCount < 1 && cell.nodeName != 'INPUT' && cell.nodeName != 'SPAN' && cell.nodeName != 'TEXTAREA' ){
	    	$(this).toggleClass('selected');
	    	if ( $(this).hasClass('selected') ) {
	    		defaultColorFollowup = $(this).css('background-color');
	    		$(this).css('background-color','#08C');
	    		$(this).css('color','white');
	            $(this).find('.followup-appointment-paramaters').prop("disabled",false);
	        }else{
	        	$(this).css('background-color', defaultColorFollowup);
	        	$(this).css('color','black');
				$(this).find('.followup-appointment-paramaters').prop("disabled",true);
	        }
    	}
    });

    /*Initialize table with row colors*/
    var myTable = $('#aligned-vicinity-physician-table').dataTable();
    var tableRows = myTable.fnGetNodes();
    for(var i = 0; i<tableRows.length ; i++){
    	setRowColor($(tableRows[i]));
    }
    
    $(".appointment-status-selector").select2();
    
    tableAppointment = $('#appointment-fixed-physician-table').DataTable({
 	   order: [[7, "asc"]]
    });
    
 /* //ON Single click of row, my pitch button appears
    var defaultColor_;
    $('#appointment-fixed-physician-table tbody').on( 'click', 'tr', function (e) {
    	var cell = $(e.target).get(0);	
    	if ($(this).hasClass('selected-pitch') ) {
    		$(this).removeClass('selected');
    		$(this).css('background-color', defaultColor);
    		
        }else{
        	tableAppointment.$('tr.selected').css('background-color',defaultColor);
        	tableAppointment.$('tr.selected').removeClass('selected');
        	
            defaultColor = $(this).css('background-color');
            $(this).addClass('selected');
            $(this).css('background-color','#08C');
            
        }
    });*/
    
    
    //ON Double click of row, modal opens
    var defaultColor;
    $('#appointment-fixed-physician-table tbody').on( 'dblclick', 'tr', function (e) {
    	if ( $(this).hasClass('selected') ) {
    		$(this).removeClass('selected');
    		$(this).css('background-color', defaultColor);
    	}else{
        	tableAppointment.$('tr.selected').css('background-color',defaultColor);
        	tableAppointment.$('tr.selected').removeClass('selected');
            defaultColor = $(this).css('background-color');
            $(this).addClass('selected');
            $(this).css('background-color','#08C');
    	}
    	currentSelectedAppointment = $(this).find('.appointment-id').html()
    	$("#meetingupdate-add-modal").modal('show');
    	
    	$(".phys-status-selector").select2();
    });
    
//    if( $('#meetingupdate-add-modal').css('display') == 'none' ){
//    	var row = $('#appointment-fixed-physician-table').find('tr.selected')[0];
//    	$(row).removeClass('selected');
//    	$(row).css('background-color',defaultColor);
//    }
    
    
    if( $('table#followup-appointments-table tbody tr').length == 0){
    	 $('table#followup-appointments-table tbody').append(
	    	   '<tr style="background-color: white;">'+
	    	   		'<td class="no-followup-td" style="border: none;padding: 0px;">'+
	    	   			'<div class="no-followup-message-div">'+ 
	    	   				'<span class="no-followups-message-span" style="vertical-align: -webkit-baseline-middle;">NO FOLLOW UPs FOR TODAY</span>'+
	    	   			'</div>'+
	    	   		'</td>'+
	    	   	'</tr>'
    	 	);
    	 $('table#followup-appointments-table').css('border','none');
    	 $('table#followup-appointments-table').css('border-top','solid 1px #ddd');
    	 
    	}
    
    
    
    
});	

$('.show-contact').click(function(){
	$(this).parent().parent().find('div.aligned-physician-contact').toggle();
	if($(this).parent().parent().find('div.aligned-physician-contact').css('display') == 'none'){
	    $(this).find('span.button-value').html("Show Contact Details");
	}else if($(this).parent().parent().find('div.aligned-physician-contact').css('display') == 'block'){
	    $(this).find('span.button-value').html("Hide Contact Details");
	}
});


$(document).on('click','.my-pitch-buttons',function(){
	var $row = $(this).parent()  
	                  .parent(); 
	var appointmentId = $row.find('.appointment-id').text();
	var hasPitch = $row.find('.has-pitch-td').html();
	if(hasPitch == 'false'){
		$('input[name="uploadModalAppointmentId"]').val(appointmentId);
		console.log($('input[name="uploadModalAppointmentId"]').val());
		$('#add-meeting-pitch-modal').modal('show');
	}else if(hasPitch = 'true'){
		$('input[name="viewModalAppointmentId"]').val(appointmentId);
		console.log($('input[name="viewModalAppointmentId"]').val());
		getPitchFile(appointmentId);
//		$('#view-meeting-pitch-modal').modal('show');
	}
});

$(document).on('click','button#pitchdoc-upload-button', function(){
	console.log("Uploading pitch document");
	$('#upload-pitch-form').submit();
	$('#add-meeting-pitch-modal').modal('hide');
});

$(document).on('click','button#pitchdoc-update-button', function(){
	console.log("UPDATING pitch document");
	$('#view-pitch-form').submit();
	$('#view-meeting-pitch-modal').modal('hide');
});

var getPitchFile = function(appointmentId){
	$.ajax({
		type : 'GET',
		url : '/getpitchforappointment?appointmentId='+appointmentId,
		dataType : 'json',
		success : function(data){
			console.log("Pitch received "+JSON.stringify(data));
			attachObjectToView(data);
		},
		error: function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		
	});
}

var attachObjectToView = function(pitchEntity){
	if($('#pitch-'+pitchEntity["appointmentId"]).length == 0){
		$('div.view-pitch-modal-body').append(
			'<div class="form-group view-pitch-div" id="pitch-'+pitchEntity["appointmentId"]+'"  style="display: none;">'+
				'<label class="control-label" id="view-pitch-label">Your pitch</label>'+
				'<object class="pdf-doc-object pdf-doc-object-slidedown" data="'+pitchEntity["fileLocation"]+'" type="application/pdf"">'+
					'<embed src="'+pitchEntity["fileLocation"]+'" alt="pdf" pluginspage="http://www.adobe.com/products/acrobat/readstep2.html">'+
					'</embed>'+
				'</object>'+
			'</div>'
		);
	}
		$('#view-meeting-pitch-modal').modal('show');
		$('div.view-pitch-div').css('display','none');
		$('#pitch-'+pitchEntity["appointmentId"]).css('display','block');
	
}


$(document).on('click','button#meetingupdate-add-button',function(){
	//addMeetingExperience(); called inside addMeetingUpdate();
	addMeetingUpdate();
});

/**
 * Set color of the row based on the 
 * importance factor of corresponding physician
 * */
var setRowColor = function(row){
	var importanceFactor =  $(row).find(".importance-factor").html();
	if(importanceFactor > 1.2) $(row).css('background-color','#93CA94');
	else if(importanceFactor > 0.5) $(row).css('background-color','#D2F9D3');
	else $(row).css('background-color','#ECFBEC');
}

/**
 * On click of submit button, DOM is traversed 
 * and TDs of all selected TRs are listed.
 * First (physId) and last (appointTime) are
 * selected as two lists which are then converted to JSON for AJAX call
 * @TODO : Add validation in case of user clicking on submit button
 * without entering time for all selected physicians.
 */
$('.submit-selected-alignments').click(function(){
	var physIds = [];
	var appointStartTimeList = [];
	var appointEndTimeList = [];
	var appointDateList = [];
	var productIds = [];
	var appointStatusList = [];
	var additionalNotesList = [];
	var isDataInvalid = false; 
	
	$('#aligned-vicinity-physician-table').find('.selected').each(function(i, val){
		if ($(val).find('.appointment-start-time').length != 0 ) {
			var appointDate = $(val).find('.appointment-date').val();
			var appointStartTime = $(val).find('.appointment-start-time').val();
			var appointEndTime = $(val).find('.appointment-end-time').val();
			var appointStatus = $(val).find('.appointment-status-selector').val();
			var additionalNotes = $(val).find('.appointment-notes-class').val();
			if((appointStartTime == '' || appointDate == '' || appointEndTime== '') && appointStatus != "NOT INTERESTED"){		//Check if user entered time for all selected physicians
				alert("Please mention start time, end time and date both for all selected physicians");
				isDataInvalid = true;
				return;
			}
			else{
				appointStartTimeList.push(appointStartTime);
				appointEndTimeList.push(appointEndTime);
				appointDateList.push(appointDate);
				appointStatusList.push(appointStatus);
				additionalNotesList.push(additionalNotes);
			}
		}
		physIds.push($(val).find('td.physician-id').html());
		productIds.push($(val).find('td.product-id').html());
	});
	
	if(!isDataInvalid){
		fixAppointments(physIds, appointStartTimeList, appointEndTimeList, productIds, appointDateList, appointStatusList, additionalNotesList);
	}
	
	/*var fixedAppointmentDetails = createJson(physIds, appointTimeList, productIds, appointDateList, appointStatusList, additionalNotesList);
	console.log("Vicinity alignment Json : "+JSON.stringify(fixedAppointmentDetails));
	$.ajax({
		type : 'POST',
		url : "/fixappointments",
		data : JSON.stringify(fixedAppointmentDetails),
		contentType : "application/json",
	    success : function() {
	        location.reload(true);
	    }
	});*/
});


var fixAppointments = function(physIds, appointStartTimeList, appointEndTimeList, productIds, appointDateList, appointStatusList, additionalNotesList){
	$.ajax({
		type: 'GET',
		url : '/getallappointments',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received (Future appoinments): "+JSON.stringify(data));
	    	var fixedAppointmentDetails = createJson(physIds, appointStartTimeList, appointEndTimeList, productIds, appointDateList, appointStatusList, additionalNotesList);
	    	console.log("Appointment to be fixed : "+JSON.stringify(fixedAppointmentDetails));
	    	if(checkFutureAppointmentOverlap(data, fixedAppointmentDetails) && checkTodaysAppointmentOverlap(fixedAppointmentDetails) && sanityCheck(fixedAppointmentDetails)){
		    	$.ajax({
		    		type : 'POST',
		    		url : "/fixappointments",
		    		data : JSON.stringify(fixedAppointmentDetails),
		    		contentType : "application/json; charset=utf-8",
		    		success: function(){
		    			location.reload(true);
		    		}
		    	});
	    	}
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	});
	
}

var sanityCheck = function(currentAppointments){
	for(var i = 0; i<currentAppointments.length; i++){
		if(currentAppointments[i]["appointmentStartTime"] >= currentAppointments[i]["appointmentEndTime"]){
			alert("Appointment you are trying to book at "+currentAppointments[i]["appointmentStartTime"]
			+" has Start Time after End Time. Please correct the time values and submit again");
			return false; 
		}
	}
	console.log("Sanity check : return true");
	return true;
} 

var checkFutureAppointmentOverlap = function(futureAppointments, currentAppointments){
	console.log("Checking for overlapping appointments ");
	console.log("Future appointments : "+JSON.stringify(futureAppointments));
	console.log("Current appointments : "+JSON.stringify(currentAppointments));
	for(var i = 0; i<currentAppointments.length; i++){
		for(var j = 0; j<futureAppointments.length; j++){
			if(currentAppointments[i]["appointmentDate"] == futureAppointments[j]["date"] ){
				var startTime1 = (new Date (new Date().toDateString() + ' ' + currentAppointments[i]["appointmentStartTime"]));
				var endTime1 = (new Date (new Date().toDateString() + ' ' + currentAppointments[i]["appointmentEndTime"]));
				var startTime2 = (new Date (new Date().toDateString() + ' ' + futureAppointments[j]["startTime"]));
				var endTime2 = (new Date (new Date().toDateString() + ' ' + futureAppointments[j]["endTime"]));
				
				if(startTime2 <= endTime1 && endTime2 >= startTime1){
					alert("One of the appointments you are booking overlaps with already confirmed appointment with Dr. " +
							futureAppointments[j]["physicianName"]+" on "+ futureAppointments[j]["date"]+ " at "+ futureAppointments[j]["startTime"]+". Please" +
							" try to reschedule the appointment you are booking now.");
					return false;
				}
				else if(endTime1 < startTime2){
					if(getDiffInMinutes(startTime2, endTime1) < averageTravelTime){
						alert("One of the appointments you are booking is very close with already confirmed appointment with Dr. " +
								futureAppointments[j]["physicianName"]+" on "+ futureAppointments[j]["date"]+ " at "+ futureAppointments[j]["startTime"]+". Please" +
								" try to reschedule the appointment you are booking now.");
						return false;
					}
				}
				else if(endTime2 < startTime1){
					if(getDiffInMinutes(startTime1, endTime2) < averageTravelTime){
						alert("One of the appointments you are booking is very close with already confirmed appointment with Dr. " +
								futureAppointments[j]["physicianName"]+" on "+ futureAppointments[j]["date"]+ " at "+ futureAppointments[j]["startTime"]+". Please" +
								" try to reschedule the appointment you are booking now.");
						return false;
					}
				}
			}
		}
	}
	return true;
}

var checkTodaysAppointmentOverlap = function(currentAppointments){
	console.log("Checking for overlapping in today's appointments ");
	console.log("Current appointments : "+JSON.stringify(currentAppointments));
	for(var i = 0; i<currentAppointments.length; i++){
		for(var j = i; j<currentAppointments.length; j++){
			if(currentAppointments[i]["appointmentDate"] == currentAppointments[j]["appointmentDate"] &&
					currentAppointments[i]["physicianId"] != currentAppointments[j]["physicianId"]){
				var startTime1 = (new Date (new Date().toDateString() + ' ' + currentAppointments[i]["appointmentStartTime"]));
				var endTime1 = (new Date (new Date().toDateString() + ' ' + currentAppointments[i]["appointmentEndTime"]));
				var startTime2 = (new Date (new Date().toDateString() + ' ' + currentAppointments[j]["appointmentStartTime"]));
				var endTime2 = (new Date (new Date().toDateString() + ' ' + currentAppointments[j]["appointmentEndTime"]));
				
				if(startTime2 <= endTime1 && endTime2 >= startTime1){
					alert('Two of the appointments you are trying to fix, for '+currentAppointments[i]["appointmentDate"]+' @ '
							+startTime1+' and '+startTime2+', overlap with each other.'
							+'Try to rechedule either of these to be able to submit appointments.');
					return false;
				}
				else if(endTime1 < startTime2){
					if(getDiffInMinutes(startTime2, endTime1) < averageTravelTime){
						alert('Two of the appointments you are trying to fix now, @ '
							+startTime1+' and @ '+startTime2+', are very close to each other.'
							+'Try to rechedule either of these to be able to submit appointments.');
						return false;
					}
				}
				else if(endTime2 < startTime1){
					if(getDiffInMinutes(startTime1, endTime2) < averageTravelTime){
						alert('Two of the appointments you are trying to fix now, @ '
								+startTime1+' and @ '+startTime2+', are very close to each other.'
								+'Try to rechedule either of these to be able to submit appointments.');
						return false;
					}
				}
			}
		}
	}
	return true;
}

/**
 * On click of submit button, DOM is traversed 
 * and TDs of all selected TRs are listed.
 * First (physId) and last (appointTime) are
 * selected as two lists which are then converted to JSON for AJAX call
 * @TODO : Add validation in case of user clicking on submit button
 * without entering time for all selected physicians.
 */
$('.submit-selected-followup-alignments').click(function(){
	var physIds = [];
	var appointStartTimeList = [];
	var appointEndTimeList = [];
	var appointDateList = [];
	var productIds = [];
	var appointStatusList = [];
	var additionalNotesList = [];
	var appointmentIdList = [];
	var isDataInvalid = false; 
	$('#followup-appointments-table').find('.selected').each(function(i, val){
		if ($(val).find('.appointment-start-time').length != 0 ) {
			var appointDate = $(val).find('.appointment-date').val();
			var appointStartTime = $(val).find('.appointment-start-time').val();
			var appointEndTime = $(val).find('.appointment-end-time').val();
			var appointStatus = $(val).find('.appointment-status-selector').val();
			var additionalNotes = $(val).find('.appointment-notes-class').val();
			var appointId = $(val).find('.followup-appointmentid').html();
			console.log("appointId : "+appointId);
			if((appointStartTime == '' || appointDate == '' || appointEndTime== '') && appointStatus != "NOT INTERESTED"){		//Check if user entered time for all selected physicians
				confirm("Please mention start time, end time and date for all selected physicians");
				isDataInvalid = true;
				return;
			}
			else{
				appointStartTimeList.push(appointStartTime);
				appointEndTimeList.push(appointEndTime);
				appointDateList.push(appointDate);
				appointStatusList.push(appointStatus);
				additionalNotesList.push(additionalNotes);
				appointmentIdList.push(appointId);
			}
		}
		physIds.push($(val).find('td.physician-id').html());
		productIds.push($(val).find('td.product-id').html());
	});
	
	if(!isDataInvalid){
		fixFollowupAppointments(physIds, appointStartTimeList,appointEndTimeList, productIds, appointDateList, appointStatusList, additionalNotesList, appointmentIdList);
	}
	
	/*var fixedAppointmentDetails = createJsonFollowupAppointment(physIds, appointTimeList, productIds, appointDateList, appointStatusList, additionalNotesList, appointmentIdList);
	console.log("Json : "+JSON.stringify(fixedAppointmentDetails));
	$.ajax({
		type : 'POST',
		url : "/updatefollowupappointment",
		data : JSON.stringify(fixedAppointmentDetails),
		contentType : "application/json; charset=utf-8",
	    dataType : 'json',
	    success : function() {
	        location.reload(true);
	    }
	}).done(function(){
		location.reload(true);
	});*/
});

/*
 * To fix follow up appointments 
 * */
var fixFollowupAppointments = function(physIds, appointStartTimeList, appointEndTimeList, productIds, appointDateList, appointStatusList, additionalNotesList, appointmentIdList){
	$.ajax({
		type: 'GET',
		url : '/getallappointments',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received (Future appoinments): "+JSON.stringify(data));
	    	var fixedAppointmentDetails = createJsonFollowupAppointment(physIds, appointStartTimeList, appointEndTimeList, productIds, appointDateList, appointStatusList, additionalNotesList, appointmentIdList);
	    	console.log("Appointment to be fixed : "+JSON.stringify(fixedAppointmentDetails));
	    	if(checkFutureAppointmentOverlap(data, fixedAppointmentDetails) && checkTodaysAppointmentOverlap(fixedAppointmentDetails) && sanityCheck(fixedAppointmentDetails)){
		    	$.ajax({
		    		type : 'POST',
		    		url : "/updatefollowupappointment",
		    		data : JSON.stringify(fixedAppointmentDetails),
		    		contentType : "application/json; charset=utf-8",
		    		success: function(){
		    			location.reload(true);
		    		}
		    	});
	    	}
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	});
	
}


var addMeetingUpdate = function(){
	
	var appointmentId = currentSelectedAppointment;//tableAppointment.row('.selected').data()[0];												//Getting Appointment id
	var physicianId = $('tr.selected').find('td.physician-id').html();
	var product_td = $('table#appointment-fixed-physician-table').find('tr.selected').find('td.product-name-td')[0]
	var productName =  product_td.textContent;
	
	var formData = {};
	
	formData['appointmentId'] = appointmentId;
	formData['physicianId'] = physicianId;
	formData['productName'] = productName;
	formData['meetingStatus'] = $('.phys-status-selector').val();
	formData['isEDetailing'] = $('.edetailing-flag-selector').is(":checked") === true ? 'true':'false';
	
	console.log("Form data : "+JSON.stringify(formData));
	
	$.ajax({
		type : 'POST',
        url : "/addmeetingupdate",
        data : JSON.stringify(formData),
        contentType : 'application/json'
    }).done(function() {
    	addMeetingExperience();
    });
}


var addMeetingExperience = function(){
	var appointmentId = currentSelectedAppointment;//tableAppointment.row('.selected').data()[0];													//Getting Appointment id
	var formData_ = {};

	formData_['appointmentId'] = appointmentId;
	
	formData_['likedTheProduct'] = $('.likedproduct-flag-selector').is(":checked") === true ? 'true':'false';
	formData_['likedPriceAffordability'] = $('.priceaffordable-flag-selector').is(":checked") === true ? 'true':'false';
	formData_['impressiveLessSideEffects'] = $('.less-sideeffects-flag-selector').is(":checked") === true ? 'true':'false';
	formData_['likedPresentation'] = $('.likedpresentation-flag-selector').is(":checked") === true ? 'true':'false';
	formData_['salesRepConfidence'] = $('.confidence-flag-selector').is(":checked") === true ? 'true':'false';
	formData_['impressiveCompanyReputation'] = $('.companyreputation-flag-selector').is(":checked")=== true ? 'true':'false';

	console.log("Meeting Experience Form data : "+JSON.stringify(formData_));

	$.ajax({
		type : 'POST',
        url : "/addmeetingexperience",
        data : JSON.stringify(formData_),
        contentType : 'application/json'
    }).done(function() {
    	$('#meetingupdate-add-modal').modal('hide');
        location.reload(true);
    });
}




var toggleMeetingUpdateButtons = function(hasMeetingUpdate, hasMeetingExperience){
	if(hasMeetingUpdate === 'true') $('.add-meeting-update-btn').prop("disabled",true);
	else $('.add-meeting-update-btn').prop("disabled",false);
	if(hasMeetingExperience === 'true') $('.add-meeting-experience-btn').prop("disabled",true);
	else if (hasMeetingUpdate === 'true' && hasMeetingExperience === 'false') $('.add-meeting-experience-btn').prop("disabled",false);
}

/*var startTime = (new Date (new Date().toDateString() + ' ' + appointTime[i]));
var endTime = new Date(startTime.getTime() + appointDurationList[i]*60000);*/
var getDiffInMinutes = function (t1, t2){
	var diff = Math.abs(t1 - t2);
	return Math.floor((diff/1000)/60);
}


//Function to create JSON to store physician Ids and corresponding 
//appointment time
var createJson = function(physIds, appointStartTime, appointEndTime, productIds,appointDate, appointStatusList, additionalNotesList){
	var appointmentJson = {"appointments":[]};
	console.log("physIds : "+physIds);
	var appointJsonList = [];
	for( var i = 0; i<physIds.length;i++){
		appointJsonList.push(
				{
					"physicianId":parseInt(physIds[i]),
					"productId":parseInt(productIds[i]),
					"appointmentStartTime":appointStartTime[i],
					"appointmentEndTime": appointEndTime[i], 
					"appointmentDate":appointDate[i],
					"appointmentStatus":appointStatusList[i],	
					"additionalNotes":additionalNotesList[i]
				});
	}
	return appointJsonList;
}

//Function to create JSON of data captured from
//follow up appointments table
var createJsonFollowupAppointment = function(physIds, appointStartTimeList, appointEndTimeList, productIds, appointDateList, appointStatusList, additionalNotesList, appointmentIdList){
	var appointmentJson = {"appointments":[]};
	var appointJsonList = [];
	for( var i = 0; i<physIds.length;i++){
		appointJsonList.push(
				{
					"physicianId":parseInt(physIds[i]), 
					"productId":parseInt(productIds[i]),
					"appointmentStartTime":appointStartTimeList[i],
					"appointmentEndTime": appointEndTimeList[i],
					"appointmentDate":appointDateList[i],
					"appointmentStatus":appointStatusList[i],
					"additionalNotes":additionalNotesList[i],
					"appointmentId":appointmentIdList[i]
				});
	}
	return appointJsonList;
}