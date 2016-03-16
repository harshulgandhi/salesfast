/**
 * handles UI interactivity for page 
 * showalignments.html
 */
var selectedAlignments = [];

/**
 * Toggles between selected and de-selected rows of the table
 * Takes care of clicks done on the 'time' element 
 */
$(document).ready(function() {
   var table = $('#aligned-physician-table').DataTable({
	   order: [[16, "desc"]]
   });
    $('#aligned-physician-table tbody').on( 'click', 'tr', function (e) {
    	var cell = $(e.target).get(0);
    	console.log(cell.nodeName);
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
    
    $(".appointment-status-selector").select2();
    
    /*Initialize table with row colors*/
    var myTable = $('#aligned-physician-table').dataTable();
    var tableRows = myTable.fnGetNodes();
    for(var i = 0; i<tableRows.length ; i++){
    	setRowColor($(tableRows[i]));
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
	var appointTimeList = [];
	var appointDateList = [];
	var productIds = [];
	var appointStatusList = [];
	var additionalNotesList = [];
	var isDataInvalid = false; 
	$('.selected').each(function(i, val){
		if ($(val).find('.appointment-time').length != 0 ) {
			var appointDate = $(val).find('.appointment-date').val();
			var appointTime = $(val).find('.appointment-time').val();
			var appointStatus = $(val).find('.appointment-status-selector').val();
			var additionalNotes = $(val).find('.appointment-notes-class').val();
			if((appointTime == '' || appointDate == '') && appointStatus != "NOT INTERESTED"){		//Check if user entered time for all selected physicians
				confirm("Please mention time and date both for all selected physicians");
				isDataInvalid = true;
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
	if(!isDataInvalid){
		fixAppointments(physIds, appointTimeList, productIds, appointDateList, appointStatusList, additionalNotesList);
	}
});


var fixAppointments = function(physIds, appointTimeList, productIds, appointDateList, appointStatusList, additionalNotesList){
	$.ajax({
		type: 'GET',
		url : '/getfutureappointments',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received (Future appoinments): "+JSON.stringify(data));
	    	var fixedAppointmentDetails = createJson(physIds, appointTimeList, productIds, appointDateList, appointStatusList, additionalNotesList);
	    	console.log("Json : "+JSON.stringify(fixedAppointmentDetails));
	    	if(checkFutureAppointmentOverlap(data, fixedAppointmentDetails) && checkTodaysAppointmentOverlap(fixedAppointmentDetails)){
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

var checkFutureAppointmentOverlap = function(futureAppointments, currentAppointments){
	console.log("Checking for overlapping appointments ");
	console.log("Future appointments : "+JSON.stringify(futureAppointments));
	console.log("Current appointments : "+JSON.stringify(currentAppointments));
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


var checkTodaysAppointmentOverlap = function(currentAppointments){
	console.log("Checking for overlapping in today's appointments ");
	console.log("Current appointments : "+JSON.stringify(currentAppointments));
	for(var i = 0; i<currentAppointments.length; i++){
		for(var j = i; j<currentAppointments.length; j++){
			if(currentAppointments[i]["appointmentDate"] == currentAppointments[j]["appointmentDate"] &&
					currentAppointments[i]["physicianId"] != currentAppointments[j]["physicianId"]){
				var time_1 = (new Date (new Date().toDateString() + ' ' + currentAppointments[i]["appointmentTime"]));
				var time_2 = (new Date (new Date().toDateString() + ' ' + currentAppointments[j]["appointmentTime"]));
				var diff = Math.abs(time_1 - time_2);
				console.log("Diff : "+diff);
				if(Math.floor((diff/1000)/60) <= 15){
					alert('Two of the appointments you are trying to fix, for '+currentAppointments[i]["appointmentDate"]+' @ '
							+currentAppointments[i]["appointmentTime"]+', overlap (lie within 15 minutes) with each other.'
							+'Try to rechedule either of these to be able to submit all appointments.');
					return false;
				}
			}
		}
	}
	return true;
}


var getFutureAppointments = function(){
	var futureAppointments = [];
	$.ajax({
		type: 'GET',
		url : '/getfutureappointments',
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

//Function to create JSON to store physician Ids and corresponding 
//appointment time
var createJson = function(physIds, appointTime, productIds, appointDate, appointStatusList, additionalNotesList){
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
