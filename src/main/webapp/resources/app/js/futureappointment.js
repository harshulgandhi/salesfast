/**
 * 
 */
var futureAppointmentTable;
var averageTravelTime = 30; //minutes
$(document).ready(function(){
	
	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.future-appointment-li').addClass('left-menu-selected');
	
	$('li.navbar-menu-selected').removeClass("navbar-menu-selected");
	   
	if(!$('li#nav-appointment').hasClass("navbar-menu-selected")){
		$('li#nav-appointment').addClass("navbar-menu-selected")
	}
	
	futureAppointmentTable = $('#future-appointment-fixed-physician-table').dataTable();
	
	$(".appointment-status-selector").select2();
	
	$('#future-appointment-fixed-physician-table').find('tr').each(function(i, val){
		   if($(val).find('.cancelled-appointment-status').html() == 'CANCELLED'){
			   $(val).css('background-color','mistyrose');
		   }
	});
	
	var defaultColorFuture;
	$('#future-appointment-fixed-physician-table tbody').on('click','tr', function(e){
		var cell = $(e.target).get(0);
		console.log(cell.nodeName);
		if(cell.childElementCount < 1 && cell.nodeName != 'INPUT' 
			&& cell.nodeName != 'SPAN' && cell.nodeName != 'TEXTAREA' 
			&& $(this).find('.cancelled-appointment-status').html() != 'CANCELLED'
			&& cell.nodeName != 'BUTTON'){
	    	if ($(this).hasClass('selected') ) {
	    		$(this).removeClass('selected');
	    		$(this).css('background-color', defaultColorFuture);
	    		$(this).find('.future-appointment-paramaters').prop("disabled",true);
	            $('.future-appointment-change').prop("disabled",true);
	        }else{
	        	futureAppointmentTable.$('tr.selected').css('background-color',defaultColorFuture);
	        	futureAppointmentTable.$('tr.selected').removeClass('selected');
	        	defaultColorFuture = $(this).css('background-color');
	        	$(this).addClass('selected');
	        	$(this).css('background-color','#08C');
	            $(this).find('.future-appointment-paramaters').prop("disabled",false);
	            $('.future-appointment-change').prop("disabled",false);
	        }
		}
	});
	
	console.log(futureAppointmentTable.$('.cancelled-appointment-status'));
});

$('#appointment-cancellation-button').click(function(){
	var appointmentId =	futureAppointmentTable.$('tr.selected').find('.future-appointmentid').html();
	var cancellationReason = $('#cancellation-reason-id').val();
	console.log("Cancellation parameters : "+appointmentId+" and "+cancellationReason);
	if(cancellationReason == '') alert("Please provide reason for cancellation.");
	else{
		$.ajax({
			type: 'POST',
			url : "/cancelappointmentbysr?appointmentId="+appointmentId+"&cancellationReason="+cancellationReason,
			success : function(){
				console.log("success");
			},
			error: function(e){
				console.log("Error : "+JSON.stringify(e));
			}
		}).done(function(){
			console.log("appointment cancellation by sales rep done");
			location.reload(true);
		});
	}
	
});

$('#update-future-appointment').click(function(){
	var appointStartTimeList = [];
	var appointEndTimeList = [];
	var appointDateList = [];
	var appointStatusList = [];
	var additionalNotesList = [];
	var appointmentIdList = [];
	var isDataInvalid = false; 
	
	$('#future-appointment-fixed-physician-table').find('.selected').each(function(i, val){
		if ($(val).find('.appointment-start-time').length != 0 ) {
			var appointDate = $(val).find('.appointment-date').val();
			var appointStartTime = $(val).find('.appointment-start-time').val();
			var appointEndTime = $(val).find('.appointment-end-time').val();
			var appointStatus = $(val).find('.appointment-status-selector').val();
			var additionalNotes = $(val).find('.appointment-notes-class').val();
			var appointId = $(val).find('.future-appointmentid').html();
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
		
	});
	
	if(!isDataInvalid){
		updateAppointments(appointStartTimeList,appointEndTimeList, appointDateList, appointStatusList, additionalNotesList, appointmentIdList);
	}
});

var updateAppointments = function( appointStartTimeList, appointEndTimeList, appointDateList, appointStatusList, additionalNotesList, appointmentIdList){
	$.ajax({
		type: 'GET',
		url : '/getallappointments',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received (Future appoinments): "+JSON.stringify(data));
	    	var fixedAppointmentDetails = createJsonFutureAppointment(appointStartTimeList, appointEndTimeList, appointDateList, appointStatusList, additionalNotesList, appointmentIdList);
	    	console.log("Future appointment update Json : "+JSON.stringify(fixedAppointmentDetails));
	    	if(checkFutureAppointmentOverlap(data, fixedAppointmentDetails) && sanityCheck(fixedAppointmentDetails)){
	    		$.ajax({
	    			type : 'POST',
	    			url : "/updatefutureappointment",
	    			data : JSON.stringify(fixedAppointmentDetails),
	    			contentType : "application/json; charset=utf-8"
	    		}).done(function(){
	    			location.reload(true);
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

/*var startTime = (new Date (new Date().toDateString() + ' ' + appointTime[i]));
var endTime = new Date(startTime.getTime() + appointDurationList[i]*60000);*/
var getDiffInMinutes = function (t1, t2){
	var diff = Math.abs(t1 - t2);
	return Math.floor((diff/1000)/60);
}

//Function to create JSON of data captured from
//future  appointments table
var createJsonFutureAppointment = function(appointStartTimeList, appointEndTimeList,appointDate, appointStatusList, additionalNotesList, appointmentIdList){
	var appointmentJson = {"appointments":[]};
	var appointJsonList = [];
	for( var i = 0; i<appointStartTimeList.length;i++){
		appointJsonList.push(
				{
					"appointmentStartTime":appointStartTimeList[i],
					"appointmentEndTime": appointEndTimeList[i],
					"appointmentDate":appointDate[i],
					"appointmentStatus":appointStatusList[i],
					"additionalNotes":additionalNotesList[i],
					"appointmentId":appointmentIdList[i]
				});
	}
	return appointJsonList;
}

var checkFutureAppointmentOverlap = function(futureAppointments, currentAppointments){
	console.log("Checking for overlapping appointments ");
	console.log("Future appointments : "+JSON.stringify(futureAppointments));
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