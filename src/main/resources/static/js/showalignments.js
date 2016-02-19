/**
 * handles UI interactivity for page 
 * showalignments.html
 */
var d = null;
//var cell = null;
var selectedAlignments = [];
$(document).ready(function() {
   var table = $('#aligned-physician-table').DataTable();
    $('#aligned-physician-table tbody').on( 'click', 'tr', function (e) {
    	var cell = $(e.target).get(0);
    	if( e.target == $(this).find('#appointment-time')) alert("this is it!");
    	d = table.row( this ).data();
    	if(cell.childElementCount < 1 && cell.nodeName != 'INPUT'){
	    	$(this).toggleClass('selected');
	    	if ( $(this).hasClass('selected') ) {
	            $(this).find('.appointment-time').prop("disabled",false);
	        }else{
	        	 $(this).find('.appointment-time').prop("disabled",true);
	        }
	    	console.log("This data == "+ d);
	    	selectedAlignments.push(d.slice());
    	}
    } );
    $('.appointment-time').change(function(){
        alert(this.value);         //Date in full format alert(new Date(this.value));
        var inputDate = new Date(this.value);
    });
});

$('.submit-selected-alignments').click(function(){
	var physIds = selectedAlignments;
	var appointTime = ["13:20", "16:50"];
	var fixedAppointmentDetails = createJson(physIds, appointTime);
	console.log("Json : "+JSON.stringify(fixedAppointmentDetails));
	selectedAlignments.forEach(function(alignment){
		console.log("Alignments   : "+alignment)
		physIds.push(alignment[0]);
		appointTime.push("13:20");
	});
	
	var idList = {physIdList : physIds}
	$.ajax({
		type : 'POST',
		url : "/fixappointments",
		data : idList,
		contentType : "application/json; charset=utf-8",
	    dataType : 'json',
	    error : function() {
	        console.log("error");
	    },
	    success : function() {
	        console.log("SUCCESS!!");
	    }
	});
});

//Function to create JSON to store physician Ids and corresponding 
//appointment time
var createJson = function(physIds, appointTime){
	var appointmentJson = {"appointments":[]};
	for( var i = 0; i<physIds.length;i++){
		appointmentJson["appointments"].push(
				{
					"physicianId":physIds[i][0], 
					"appointmentTime":appointTime[i]
				});
	}
	return appointmentJson;
}

