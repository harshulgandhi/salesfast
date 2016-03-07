/**
 * 
 */
var selectedAlignments = [];
var tableAppointment = null;

$(document).ready(function() {
	$('.slidedown-alignments').click(function(){
		    $('.slidedown-alignments-show').slideToggle('fast');
	});
   var table = $('#aligned-vicinity-physician-table').DataTable({
	   order: [[16, "desc"]]
   });
   
   $('#appointment-fixed-physician-table').find('tr').each(function(i, val){
	   if($(val).find('.confirmation-status').html() == 'CANCELLED'){
		   $(val).css('background-color','mistyrose');
	   }
   })
   $('#future-appointment-fixed-physician-table').find('tr').each(function(i, val){
	   if($(val).find('.confirmation-status').html() == 'CANCELLED'){
		   $(val).css('background-color','mistyrose');
	   }
   })
	   
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
    $('#followup-appointments-table tbody').on( 'click', 'tr', function (e) {
    	var cell = $(e.target).get(0);
    	console.log(cell);
    	if(cell.childElementCount < 1 && cell.nodeName != 'INPUT' && cell.nodeName != 'SPAN' && cell.nodeName != 'TEXTAREA' ){
	    	$(this).toggleClass('selected');
	    	if ( $(this).hasClass('selected') ) {
	    		$(this).css('background-color','#08C');
	            $(this).find('.followup-appointment-paramaters').prop("disabled",false);
	        }else{
				$(this).find('.followup-appointment-paramaters').prop("disabled",false);
	        }
    	}
    });

    $(".appointment-status-selector").select2();
     /*Initialize table with row colors*/
    var myTable = $('#aligned-vicinity-physician-table').dataTable();
    var tableRows = myTable.fnGetNodes();
    for(var i = 0; i<tableRows.length ; i++){
    	setRowColor($(tableRows[i]));
    }
    tableAppointment = $('#appointment-fixed-physician-table').DataTable({
 	   order: [[7, "asc"]]
    });
    var defaultColor;
    $('#appointment-fixed-physician-table tbody').on( 'click', 'tr', function (e) {
    	var cell = $(e.target).get(0);	
    	if ( $(this).hasClass('selected') ) {
    		$(this).removeClass('selected');
    		$('.add-meeting-update-btn').prop("disabled",true);
    		$('.add-meeting-experience-btn').prop("disabled",true)
    		$(this).css('background-color', defaultColor);
    		
        }else{
        	tableAppointment.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
            defaultColor = $(this).css('background-color');
            $(this).css('background-color','#08C');
            var len = tableAppointment.row('.selected').data().length;					
            
            //To toggle meeting update and experience buttons based on database flag for that appointment
            var hasMeetingUpdate = tableAppointment.row('.selected').data()[len-2];		
            var hasMeetingExperience = tableAppointment.row('.selected').data()[len-1];
            toggleMeetingUpdateButtons(hasMeetingUpdate, hasMeetingExperience);
        }
    		
    	$(".phys-status-selector").select2();
     	$('#meetingupdate-add-button').click(addMeetingUpdate);
     	$('#meetingexperience-add-button').click(addMeetingExperience);
    });
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
	var appointTimeList = [];
	var appointDateList = [];
	var productIds = [];
	var appointStatusList = [];
	var additionalNotesList = [];
	
	$('#aligned-vicinity-physician-table selected').each(function(i, val){
		if ($(val).find('.appointment-time').length != 0 ) {
			var appointDate = $(val).find('.appointment-date').val();
			var appointTime = $(val).find('.appointment-time').val();
			var appointStatus = $(val).find('.appointment-status-selector').val();
			var additionalNotes = $(val).find('.appointment-notes-class').val();
			if((appointTime == '' || appointDate == '') && appointStatus != "NOT INTERESTED"){		//Check if user entered time for all selected physicians
				alert("Please mention time and date both for all selected physicians");
				return;
			}
			else{
				console.log("TIME " + appointTime+"; DATE "+appointDate);
				appointTimeList.push(appointTime);
				appointDateList.push(appointDate);
				appointStatusList.push(appointStatus);
				additionalNotesList.push(additionalNotes);
			}
		}
			$(this).find('td').each(function(idx, valTD){
				if(idx == 0) physIds.push($(valTD).html());
				if(idx == 10) productIds.push($(valTD).html());		//Picking product for selected alignments
			});
	});
	var fixedAppointmentDetails = createJson(physIds, appointTimeList, productIds, appointDateList, appointStatusList, additionalNotesList);
	console.log("Json : "+JSON.stringify(fixedAppointmentDetails));
	$.ajax({
		type : 'POST',
		url : "/fixappointments",
		data : JSON.stringify(fixedAppointmentDetails),
		contentType : "application/json; charset=utf-8",
	    dataType : 'json',
	    success : function() {
	        location.reload(true);
	    }
	});
});


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
	var appointTimeList = [];
	var appointDateList = [];
	var productIds = [];
	var appointStatusList = [];
	var additionalNotesList = [];
	var appointmentIdList = [];
	
	$('#followup-appointments-table').find('.selected').each(function(i, val){
		if ($(val).find('.appointment-time').length != 0 ) {
			var appointDate = $(val).find('.appointment-date').val();
			var appointTime = $(val).find('.appointment-time').val();
			var appointStatus = $(val).find('.appointment-status-selector').val();
			var additionalNotes = $(val).find('.appointment-notes-class').val();
			var appointId = $(val).find('.followup-appointmentid').html();
			console.log("appointId : "+appointId);
			if((appointTime == '' || appointDate == '') && appointStatus != "NOT INTERESTED"){		//Check if user entered time for all selected physicians
				alert("Please mention time and date both for all selected physicians");
				return;
			}
			else{
				console.log("TIME " + appointTime+"; DATE "+appointDate);
				appointTimeList.push(appointTime);
				appointDateList.push(appointDate);
				appointStatusList.push(appointStatus);
				additionalNotesList.push(additionalNotes);
				appointmentIdList.push(appointId);
			}
		}
			$(this).find('td').each(function(idx, valTD){
				if(idx == 0) physIds.push($(valTD).html());
				if(idx == 10) productIds.push($(valTD).html());		//Picking product for selected alignments
			});
	});
	var fixedAppointmentDetails = createJsonFollowupAppointment(physIds, appointTimeList, productIds, appointDateList, appointStatusList, additionalNotesList, appointmentIdList);
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
	});
});


var addMeetingUpdate = function(event){
	
	var appointmentId = tableAppointment.row('.selected').data()[0];												//Getting Appointment id
	var physicianId = tableAppointment.row('.selected').data()[1];													//Getting Physician id
	var productName = tableAppointment.row('.selected').data()[tableAppointment.row('.selected').data().length-3];	//Getting product name
	
	var formData = {};
	
	formData['appointmentId'] = appointmentId;
	formData['physicianId'] = physicianId;
	formData['productName'] = productName;
	formData['meetingStatus'] = $('.phys-status-selector').val();
	formData['isEDetailing'] = $('.edetailing-flag-selector').val();
	
	console.log("Form data : "+JSON.stringify(formData));
	
	$.ajax({
		type : 'POST',
        url : "/addmeetingupdate",
        data : JSON.stringify(formData),
        contentType : 'application/json'
    }).done(function() {
        $('#meetingupdate-add-modal').modal('hide');
        location.reload(true);
    });
}


var addMeetingExperience = function(event){
	var appointmentId = tableAppointment.row('.selected').data()[0];													//Getting Appointment id
	var formData_ = {};

	formData_['appointmentId'] = appointmentId;
	formData_['likedTheProduct'] = $('.likedproduct-flag-selector').val() === "1" ? 'true':'false';
	formData_['likedPriceAffordability'] = $('.priceaffordable-flag-selector').val() === "1" ? 'true':'false';
	formData_['impressiveLessSideEffects'] = $('.less-sideeffects-flag-selector').val() === "1" ? 'true':'false';
	formData_['likedPresentation'] = $('.likedpresentation-flag-selector').val() === "1" ? 'true':'false';
	formData_['salesRepConfidence'] = $('.confidence-flag-selector').val() === "1" ? 'true':'false';
	formData_['impressiveCompanyReputation'] = $('.companyreputation-flag-selector').val() === "1" ? 'true':'false';
	

	console.log("Meeting Experience Form data : "+JSON.stringify(formData_));

	$.ajax({
		type : 'POST',
        url : "/addmeetingexperience",
        data : JSON.stringify(formData_),
        contentType : 'application/json'
    }).done(function() {
        $('#meetingexperience-add-modal').modal('hide');
        location.reload(true);
    });
}


var toggleMeetingUpdateButtons = function(hasMeetingUpdate, hasMeetingExperience){
	if(hasMeetingUpdate === 'true') $('.add-meeting-update-btn').prop("disabled",true);
	else $('.add-meeting-update-btn').prop("disabled",false);
	if(hasMeetingExperience === 'true') $('.add-meeting-experience-btn').prop("disabled",true);
	else if (hasMeetingUpdate === 'true' && hasMeetingExperience === 'false') $('.add-meeting-experience-btn').prop("disabled",false);
}

//Function to create JSON to store physician Ids and corresponding 
//appointment time
var createJson = function(physIds, appointTime, productIds,appointDate, appointStatusList, additionalNotesList){
	var appointmentJson = {"appointments":[]};
	var appointJsonList = [];
	for( var i = 0; i<physIds.length;i++){
		appointJsonList.push(
				{
					"physicianId":parseInt(physIds[i][0]), 
					"productId":parseInt(productIds[i][0]),
					"appointmentTime":appointTime[i],
					"appointmentDate":appointDate[i],
					"appointmentStatus":appointStatusList[i],
					"additionalNotes":additionalNotesList[i]
				});
	}
	return appointJsonList;
}

//Function to create JSON of data captured from
//follow up appointments table
var createJsonFollowupAppointment = function(physIds, appointTime, productIds,appointDate, appointStatusList, additionalNotesList, appointmentIdList){
	var appointmentJson = {"appointments":[]};
	var appointJsonList = [];
	for( var i = 0; i<physIds.length;i++){
		appointJsonList.push(
				{
					"physicianId":parseInt(physIds[i][0]), 
					"productId":parseInt(productIds[i][0]),
					"appointmentTime":appointTime[i],
					"appointmentDate":appointDate[i],
					"appointmentStatus":appointStatusList[i],
					"additionalNotes":additionalNotesList[i],
					"appointmentId":appointmentIdList[i]
				});
	}
	return appointJsonList;
}