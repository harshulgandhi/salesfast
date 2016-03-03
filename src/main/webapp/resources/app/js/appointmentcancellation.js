/**
 * 
 */

$('#appointment-cancellation-button').click(function(){
	var appointmentId = $('.appointment-id').html();
	var cancellationReason = $('#cancellation-reason-id').val();
	console.log("Cancellation parameters : "+appointmentId+" and "+cancellationReason);
	if(cancellationReason == '') alert("Please provide reason for cancellation.");
	else{
		$.ajax({
			type: 'POST',
			url : "/cancelappointment?appointmentId="+appointmentId+"&cancellationReason="+cancellationReason,
			contentType: 'application/json; charset=utf-8',
	        dataType: 'json'
		});
		setTimeout(function(){
			location.reload(true);
		}, 500);
	}
	
	
});