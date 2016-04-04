/**
 * handles UI interactivity for page 
 * showalignments.html
 */
var selectedAlignments = [];
var averageTravelTime = 30; //minutes
var statusParam;
var isFromNotificationsPage=false;
/**
 * Toggles between selected and de-selected rows of the table
 * Takes care of clicks done on the 'time' element 
 */
$(document).ready(function() {
   var table = $('#aligned-physician-table').DataTable({
	   order: [[0, "desc"]],
	   "searching": true
   });
   
   $('li.navbar-menu-selected').removeClass("navbar-menu-selected");
   
   if(!$('li#nav-alignment').hasClass("navbar-menu-selected")){
	   $('li#nav-alignment').addClass("navbar-menu-selected")
   }
   
   console.log("Alignment referrer : "+document.referrer);
   
    $('#aligned-physician-table tbody').on( 'click', 'tr', function (e) {
    	var cell = $(e.target).get(0);
    	if(cell.childElementCount < 1 && cell.nodeName != 'INPUT' && cell.nodeName != 'SPAN' && cell.nodeName != 'TEXTAREA' ){
	    	$(this).toggleClass('selected');
	    	if ( $(this).hasClass('selected') ) {
	    		$(this).css('background-color','#08C');
	            $(this).find('.appointment-paramaters').prop("disabled",false);
	        }else{
	        	setRowColor($(this));
				$(this).find('.appointment-paramaters').prop("disabled",true);
	        }
    	}
    });
    
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
	
    $(".appointment-status-selector").select2();
    
    /*Initialize table with row colors*/
    var myTable = $('#aligned-physician-table').dataTable();
    var tableRows = myTable.fnGetNodes();
    for(var i = 0; i<tableRows.length ; i++){
    	setRowColor($(tableRows[i]));
    }
    
    updateNotificationCounter();
    
  //Physician name received and parsed for table search
	var value;
	if (window.location.search.split('?').length > 1) {
        var params = window.location.search.split('?')[1].split('&');
        for (var i = 0; i < params.length; i++) {
            var key = params[i].split('=')[0];
            statusParam= decodeURIComponent(params[i].split('=')[1]);
            console.log("Status : "+statusParam);
            isFromNotificationsPage = true;
        }
    }
	if(isFromNotificationsPage){
		table.search(statusParam).draw();
	}
});	


$(document).on('click','button.redirect-past-appointments',function(){
	   var name = $(this).parent().parent().find('td.physician-name').html();
	   window.location.replace("/pastappointments?param="+name);
});
	
$('.show-contact').click(function(){
	$(this).parent().parent().find('div.aligned-physician-contact').toggle();
	if($(this).parent().parent().find('div.aligned-physician-contact').css('display') == 'none'){
	    $(this).find('span.button-value').html("Show Contact Details");
	}else if($(this).parent().parent().find('div.aligned-physician-contact').css('display') == 'block'){
	    $(this).find('span.button-value').html("Hide Contact Details");
	}
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
	$('.selected').each(function(i, val){
		if ($(val).find('.appointment-start-time').length != 0 ) {
			var appointDate = $(val).find('.appointment-date').val();
			var appointStartTime = $(val).find('.appointment-start-time').val();
			var appointEndTime = $(val).find('.appointment-end-time').val();
			var appointStatus = $(val).find('.appointment-status-selector').val();
			var additionalNotes = $(val).find('.appointment-notes-class').val();
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
			}
		}
		physIds.push($(val).find('td.physician-id').html());
		productIds.push($(val).find('td.product-id').html());
	});
	if(!isDataInvalid){
		fixAppointments(physIds, appointStartTimeList,appointEndTimeList, productIds, appointDateList, appointStatusList, additionalNotesList);
	}
});


var fixAppointments = function(physIds, appointStartTimeList, appointEndTimeList, productIds, appointDateList, appointStatusList, additionalNotesList){
	$.ajax({
		type: 'GET',
		url : '/getallappointments',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received (Future appoinments): "+JSON.stringify(data));
	    	var fixedAppointmentDetails = createJson(physIds, appointStartTimeList, appointEndTimeList, productIds, appointDateList, appointStatusList, additionalNotesList);
	    	console.log("Appointment fixed : "+JSON.stringify(fixedAppointmentDetails));
	    	if(checkFutureAppointmentOverlap(data, fixedAppointmentDetails) && checkTodaysAppointmentOverlap(fixedAppointmentDetails) && sanityCheck(fixedAppointmentDetails)){
		    	$.ajax({
		    		type : 'POST',
		    		url : "/fixappointments",
		    		data : JSON.stringify(fixedAppointmentDetails),
		    		contentType : "application/json; charset=utf-8",
		    		success: function(){
		    			window.location.replace("/showappointments");
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
	console.log("Futrue Appointment : return true");
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
	console.log("Current Appointment : return true");
	return true;
}


var getFutureAppointments = function(){
	var futureAppointments = [];
	$.ajax({
		type: 'GET',
		url : '/getallappointments',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received (Future appoinments): "+JSON.stringify(data));
	    	futureAppointments = data;
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	});
	return futureAppointments;
}

/*var startTime = (new Date (new Date().toDateString() + ' ' + appointTime[i]));
var endTime = new Date(startTime.getTime() + appointDurationList[i]*60000);*/
var getDiffInMinutes = function (t1, t2){
	var diff = Math.abs(t1 - t2);
	return Math.floor((diff/1000)/60);
}

//Function to create JSON to store physician Ids and corresponding 
//appointment time
var createJson = function(physIds, appointStartTime, appointEndTime, productIds, appointDate, appointStatusList, additionalNotesList, appointDurationList){
	var appointmentJson = {"appointments":[]};
	var appointJsonList = [];
	for( var i = 0; i<physIds.length;i++){
//		console.log("Physician Id : "+parseInt(physIds[i])+"; Product Id  : "+parseInt(productIds[i][0])+"; parseInt(productIds[i]) : "+parseInt(productIds[i])+" productIds[i] : "+productIds[i]);
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
