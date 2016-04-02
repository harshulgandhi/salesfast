/**
 * 
 */

var tableAppointment = null;
var currentSelectedAppointment = 0;


$(document).ready(function() {
	tableAppointment = $('#detailed-meeting-table').DataTable({
		   order: [[0, "desc"]]
	   });
	
	$('li.navbar-menu-selected').removeClass("navbar-menu-selected");
	   
	if(!$('li#nav-meeting-phys').hasClass("navbar-menu-selected")){
		$('li#nav-meeting-phys').addClass("navbar-menu-selected")
	}
	
	var defaultColor = "";
	/*$('#detailed-meeting-table tbody').on( 'click', 'tr', function (e) {
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
    });*/
	$('td.has-meetingexp-flag').each(function(){
		if($(this).html() === "false"){
			$(this).parent('tr').find('td.appointment-time-td').append(
						   '<span class="warning-meeting-update top" title="Double click to share your experience"'+
						   'data-original-title="Double click to share your experience" style="color:orange;">&#9888;</span>'
						   );
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
		
	$('#detailed-meeting-table tbody').on( 'dblclick', 'tr', function (e) {
		if ( $(this).hasClass('selected') ) {
    		$(this).removeClass('selected');
		}else{
			tableAppointment.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
          //To toggle meeting update and experience buttons based on database flag for that appointment
            var hasMeetingExperienceFromPH = tableAppointment.$('tr.selected').find('.has-meetingexp-flag').html();
            if(hasMeetingExperienceFromPH === 'false') {
            	currentSelectedAppointment = $(this).find('.appointment-id').html();
            	$("#meetingexperience-add-phys-modal").modal('show');
            }
		}
		
	});
	
	updateNotificationCounter();
});

$(document).on('click','button#meetingexperience-add-phy-button',function(){
	addMeetingExperienceFromPhy();
});

var addMeetingExperienceFromPhy = function(event){
	var appointmentId = tableAppointment.$('tr.selected').find('.appointment-id').html();			//Getting Appointment id
	var formData_ = {};

	formData_['appointmentId'] = appointmentId;
	formData_['likedTheProduct'] = $('.likedproduct-flag-selector').is(":checked") === true ? 'true':'false';
	formData_['likedPriceAffordability'] = $('.priceaffordable-flag-selector').is(":checked") === true ? 'true':'false';
	formData_['impressiveLessSideEffects'] = $('.less-sideeffects-flag-selector').is(":checked") === true ? 'true':'false';
	formData_['likedPresentation'] = $('.likedpresentation-flag-selector').is(":checked") === true ? 'true':'false';
	formData_['salesRepConfidence'] = $('.confidence-flag-selector').is(":checked") === true ? 'true':'false';
	formData_['impressiveCompanyReputation'] = $('.companyreputation-flag-selector').is(":checked") === true ? 'true':'false';
	

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
