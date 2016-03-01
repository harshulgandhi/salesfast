/**
 * 
 */
var selectedAlignments = [];
var tableAppointment = null;

$(document).ready(function() {
	$('.slidedown-alignments').click(function(){
		    $('.slidedown-alignments-show').slideToggle('fast');
	});
   var table = $('#aligned-vicinity-physician-table').DataTable();
   
   /**
    * Toggles between selected and de-selected rows of the table
    * Takes care of clicks done on the 'time' element 
    */
    $('#aligned-vicinity-physician-table tbody').on( 'click', 'tr', function (e) {
    	var cell = $(e.target).get(0);
    	if(cell.childElementCount < 1 && cell.nodeName != 'INPUT'){
	    	$(this).toggleClass('selected');
	    	if ( $(this).hasClass('selected') ) {
	            $(this).find('.appointment-time').prop("disabled",false);
	            $(this).find('.appointment-date').prop("disabled",false);
	        }else{
	        	 $(this).find('.appointment-time').prop("disabled",true);
	        	 $(this).find('.appointment-date').prop("disabled",true);
	        }
    	}
    });
    
    tableAppointment = $('#appointment-fixed-physician-table').DataTable();
    $('#appointment-fixed-physician-table tbody').on( 'click', 'tr', function (e) {
    	var cell = $(e.target).get(0);	
    	if ( $(this).hasClass('selected') ) {
    		$(this).removeClass('selected');
    		$('.add-meeting-update-btn').prop("disabled",true);
    		$('.add-meeting-experience-btn').prop("disabled",true)
        }else{
        	tableAppointment.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
            var len = tableAppointment.row('.selected').data().length;					
            
            //To toggle meeting update and experience buttons based on database flag for that appointment
            var hasMeetingUpdate = tableAppointment.row('.selected').data()[len-2];		
            var hasMeetingExperience = tableAppointment.row('.selected').data()[len-1];
            console.log("Row length : "+len+"\nHas meeting update : "+hasMeetingUpdate+"\nHas meeting experience entry : "+hasMeetingExperience)
            toggleMeetingUpdateButtons(hasMeetingUpdate, hasMeetingExperience);
//            $('.add-meeting-update-btn').prop("disabled",false);
        }
    	
    	/*
    	 * TO REMOVE A SELECTED ROW ON BUTTON CLICK
    	$('#button').click( function () {
            table.row('.selected').remove().draw( false );
        });*/
    	$(".phys-status-selector").select2();
     	$('#meetingupdate-add-button').click(addMeetingUpdate);
     	$('#meetingexperience-add-button').click(addMeetingExperience);
     	
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
$('.submit-selected-alignments').click(function(){
	var physIds = [];
	var appointTimeList = [];
	var appointDateList = [];
	var productIds = [];
	
	$('.selected').each(function(i, val){
		console.log($(this)[0]);
		$(this).find('td').each(function(idx, val){
			
			if ($(val).find('.appointment-time').length != 0 && $(val).find('.appointment-date').length != 0) {
				var appointTime = $(val).find('.appointment-time').val();
				var appointDate = $(val).find('.appointment-date').val();
				if(appointTime == ''){		//Check if user entered time for all selected physicians
					alert("Please mention time and date both for all selected physicians");
					return;
				}
				else{
					console.log("TIME " + $(val).find('.appointment-time').val()+"; DATE "+$(val).find('.appointment-date').val());
					appointTimeList.push(appointTime);
					appointDateList.push(appointDate);
				}
			}
			if(idx == 0) physIds.push($(val).html());
			if(idx == 10) productIds.push($(val).html());		//Picking product for selected alignments
		});
	});
	var fixedAppointmentDetails = createJson(physIds, appointTimeList, productIds);
	console.log("Json : "+JSON.stringify(fixedAppointmentDetails));
	$.ajax({
		type : 'POST',
		url : "/fixappointments",
		data : JSON.stringify(fixedAppointmentDetails),
		contentType : "application/json; charset=utf-8",
	    dataType : 'json',
	    error : function() {
	        console.log("error");
	    },
	    success : function() {
	        console.log("SUCCESS!!");
	    }
	});
	setTimeout(function(){
		location.reload(true);
	}, 500);
});


var addMeetingUpdate = function(event){
	
	var appointmentId = tableAppointment.row('.selected').data()[0];													//Getting Appointment id
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
var createJson = function(physIds, appointTime, productIds,appointDate){
	var appointmentJson = {"appointments":[]};
	var appointJsonList = [];
	for( var i = 0; i<physIds.length;i++){
		appointJsonList.push(
				{
					"physicianId":parseInt(physIds[i][0]), 
					"productId":parseInt(productIds[i][0]),
					"appointmentTime":appointTime[i],
					"appointmentDate":appointDate[i]

				});
	}
	return appointJsonList;
}