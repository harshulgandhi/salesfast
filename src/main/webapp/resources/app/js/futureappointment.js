/**
 * 
 */
var futureAppointmentTable;
$(document).ready(function(){
	
	futureAppointmentTable = $('#future-appointment-fixed-physician-table').dataTable();
	
	$('#future-appointment-fixed-physician-table').find('tr').each(function(i, val){
		   if($(val).find('.cancelled-appointment-status').html() == 'CANCELLED'){
			   $(val).css('background-color','mistyrose');
		   }
	});
	
	var defaultColorFuture;
	$('#future-appointment-fixed-physician-table tbody').on('click','tr', function(e){
		var cell = $(e.target).get(0);
		if(cell.childElementCount < 1 && cell.nodeName != 'INPUT' 
			&& cell.nodeName != 'SPAN' && cell.nodeName != 'TEXTAREA' 
			&& $(this).find('.cancelled-appointment-status').html() != 'CANCELLED'){
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
			contentType: 'application/json; charset=utf-8',
	        dataType: 'json'
		}).done(function(){
			location.reload(true);
		});
	}
	
});

$('#update-future-appointment').click(function(){
	var appointTimeList = [];
	var appointDateList = [];
	var appointStatusList = [];
	var additionalNotesList = [];
	var appointmentIdList = [];
	var isDataInvalid = false; 
	
	$('#future-appointment-fixed-physician-table').find('.selected').each(function(i, val){
		if ($(val).find('.appointment-time').length != 0 ) {
			var appointDate = $(val).find('.appointment-date').val();
			var appointTime = $(val).find('.appointment-time').val();
			var appointStatus = $(val).find('.appointment-status-selector').val();
			var additionalNotes = $(val).find('.appointment-notes-class').val();
			var appointId = $(val).find('.future-appointmentid').html();
			if((appointTime == '' || appointDate == '') && appointStatus != "NOT INTERESTED"){		//Check if user entered time for all selected physicians
				alert("Please mention time and date both for all selected physicians");
				isDataInvalid = true;
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
		
	});
	
	if(!isDataInvalid){
		updateAppointments(appointTimeList, appointDateList, appointStatusList, additionalNotesList, appointmentIdList);
	}
});

var updateAppointments = function( appointTimeList, appointDateList, appointStatusList, additionalNotesList, appointmentIdList){
	$.ajax({
		type: 'GET',
		url : '/getallappointments',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received (Future appoinments): "+JSON.stringify(data));
	    	var fixedAppointmentDetails = createJsonFutureAppointment(appointTimeList, appointDateList, appointStatusList, additionalNotesList, appointmentIdList);
	    	console.log("Future appointment update Json : "+JSON.stringify(fixedAppointmentDetails));
	    	if(checkFutureAppointmentOverlap(data, fixedAppointmentDetails)){
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


//Function to create JSON of data captured from
//future  appointments table
var createJsonFutureAppointment = function(appointTime,appointDate, appointStatusList, additionalNotesList, appointmentIdList){
	var appointmentJson = {"appointments":[]};
	var appointJsonList = [];
	for( var i = 0; i<appointTime.length;i++){
		appointJsonList.push(
				{
					"appointmentTime":appointTime[i],
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
				var time_1 = (new Date (new Date().toDateString() + ' ' + currentAppointments[i]["appointmentTime"]));
				var time_2 = (new Date (new Date().toDateString() + ' ' + futureAppointments[j]["time"]));
				var diff = Math.abs(time_1 - time_2);
				console.log("Diff : "+diff);
				if(Math.floor((diff/1000)/60) <= 15){
					alert("One of the appointments you are booking overlaps (lies within 15 minutes) with already confirmed appointment with Dr. " +
							futureAppointments[j]["physicianName"]+" on "+ futureAppointments[j]["date"]+ " at "+ futureAppointments[j]["time"]+". Please" +
							" try to reschedule this appointment.");
					return false;
				}
			}
		}
	}
	return true;
}