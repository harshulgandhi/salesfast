/**
 * 
 */

var tableAppointment = null;

$(document).ready(function() {
	tableAppointment = $('#detailed-meeting-table').DataTable({
		   order: [[0, "desc"]]
	   });
	var defaultColor = "";
	$('#detailed-meeting-table tbody').on( 'click', 'tr', function (e) {
    	var cell = $(e.target).get(0);	
    	if ( $(this).hasClass('selected') ) {
    		$(this).removeClass('selected');
    		$('.add-meeting-exp-phy-btn').prop("disabled",true);
    		
        }else{
        	tableAppointment.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
            var len = tableAppointment.row('.selected').data().length;					
            
            //To toggle meeting update and experience buttons based on database flag for that appointment
            var hasMeetingExperienceFromPH = tableAppointment.$('tr.selected').find('.has-meetingexp-flag').html();
            if(hasMeetingExperienceFromPH === 'false') {
            	$('.add-meeting-exp-phy-btn').prop("disabled",false);
            }
        }
    	
    	$('#meetingexperience-add-phy-button').click(addMeetingExperienceFromPhy);
    });
	updateNotificationCounter();
});

var addMeetingExperienceFromPhy = function(event){
	var appointmentId = tableAppointment.$('tr.selected').find('.appointment-id').html();			//Getting Appointment id
	var formData_ = {};

	formData_['appointmentId'] = appointmentId;
	formData_['likedTheProduct'] = $('.likedproduct-flag-selector').val() === "1" ? 'true':'false';
	formData_['likedPriceAffordability'] = $('.priceaffordable-flag-selector').val() === "1" ? 'true':'false';
	formData_['impressiveLessSideEffects'] = $('.less-sideeffects-flag-selector').val() === "1" ? 'true':'false';
	formData_['likedPresentation'] = $('.likedpresentation-flag-selector').val() === "1" ? 'true':'false';
	formData_['salesRepConfidence'] = $('.confidence-flag-selector').val() === "1" ? 'true':'false';
	formData_['impressiveCompanyReputation'] = $('.companyreputation-flag-selector').val() === "1" ? 'true':'false';
	

	console.log("Meeting Experience Form data from Phy : "+JSON.stringify(formData_));

	$.ajax({
		type : 'POST',
        url : "/addmeetingexpfromphy",
        data : JSON.stringify(formData_),
        contentType : 'application/json'
    }).done(function() {
        $('#meetingexperience-add-phy-modal').modal('hide');
        location.reload(true);
    });
}
