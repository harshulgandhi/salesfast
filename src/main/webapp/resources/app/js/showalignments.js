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
	   order: [[14, "desc"]]
   });
    $('#aligned-physician-table tbody').on( 'click', 'tr', function (e) {
    	var cell = $(e.target).get(0);
    	if(cell.childElementCount < 1 && cell.nodeName != 'INPUT'){
	    	$(this).toggleClass('selected');
	    	if ( $(this).hasClass('selected') ) {
	    		$(this).css('background-color','#08C')
	            $(this).find('.appointment-time').prop("disabled",false);
	            $(this).find('.appointment-date').prop("disabled",false);
	        }else{
	        	setRowColor($(this));
				 $(this).find('.appointment-time').prop("disabled",true);
				 $(this).find('.appointment-date').prop("disabled",true);
	        }
    	}
    });
    
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
	
	$('.selected').each(function(i, val){
		console.log($(this)[0]);
		$(this).find('td').each(function(idx, valTD){
			if ($(valTD).find('.appointment-time').length != 0 ) {
				var appointDate = $(valTD).find('.appointment-date').val();
				var appointTime = $(valTD).find('.appointment-time').val();
				console.log("TIME " + appointTime+"; DATE "+appointDate);
				if(appointTime == '' || appointDate == ''){		//Check if user entered time for all selected physicians
					alert("Please mention time and date both for all selected physicians");
					return;
				}
				else{
					console.log("TIME " + appointTime+"; DATE "+appointDate);
					appointTimeList.push(appointTime);
//					appointDateList.push(appointDate);
				}
			}
			if(idx == 0) physIds.push($(valTD).html());
			if(idx == 10) productIds.push($(valTD).html());		//Picking product for selected alignments
		});
		appointDateList.push("2016-03-04");
	});
	var fixedAppointmentDetails = createJson(physIds, appointTimeList, productIds, appointDateList);
	console.log("Json : "+JSON.stringify(fixedAppointmentDetails));
	$.ajax({
		type : 'POST',
		url : "/fixappointments",
		data : JSON.stringify(fixedAppointmentDetails),
		contentType : "application/json; charset=utf-8",
	});
	/*setTimeout(function(){
		location.reload(true);
	}, 500);*/
});

//Function to create JSON to store physician Ids and corresponding 
//appointment time
var createJson = function(physIds, appointTime, productIds, appointDate){
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
