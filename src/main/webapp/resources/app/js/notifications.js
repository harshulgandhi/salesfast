/**
 * 
 */
$(document).ready(function() {
	var table = $('#notifications-table').DataTable();
	
	$('li.navbar-menu-selected').removeClass("navbar-menu-selected");
	   
	if(!$('li#nav-notification').hasClass("navbar-menu-selected")){
		$('li#nav-notification').addClass("navbar-menu-selected")
	}
	
	$('#notifications-table tbody').on( 'click', '.remove-notification', function () {
		var r_ = $(this).parents('tr');
		var userId = r_.find('td.user-id').html();
		var notificationId = r_.find('td.notification-id').html();
		
		table.row( $(this).parents('tr') ).remove().draw();
		$.ajax({
			type: 'POST',
			url: '/deletenotification?notificationId='+notificationId
		}).done(function(){
			console.log()
			updateNotificationCounter();
		});
		
	});
	$('#notifications-table tbody').on( 'click', '.detail-notification', function () {
		var r_ = $(this).parents('tr');
		if(r_.find('.notification-category').html() == "LIVE MEETING QUESTION"){
			window.location.replace("/unansweredques")
		}else if(r_.find('.notification-category').html() == "QUESTION WAS ANSWERED"){
			window.location.replace("/livemeetingquestions?param="+"QUESTION WAS ANSWERED")
		}else if(r_.find('.notification-category').html() == "NEW PRODUCT LOST PHYSICIAN"){
			window.location.replace("/showalignments?status="+"LOST");
		}else if(r_.find('.notification-category').html() == "NEW PRODUCT PRESCRIBING PHYSICIAN"){
			window.location.replace("/showalignments?status="+"PRESCRIBING");
		}else if(r_.find('.notification-category').html() == "NEW PRODUCT NOT INTERESTED PHYSICIAN"){
			window.location.replace("/showalignments?status="+"NOT INTERESTED");
		}else if(r_.find('.notification-category').html() == "CANCELLED APPOINTMENTS BY PHYS"){
			window.location.replace("/showappointments");
		}else if(r_.find('.notification-category').html() == "FOLLOW UP"){
			window.location.replace("/showappointments#followup-appointments-table");
		}else if(r_.find('.notification-category').html() == "NEW PRODUCT TO PHYS"){
			window.location.replace("/edetailing?param="+"NEW_PRODUCT");
		}
		
		
	});
	updateNotificationCounter();
	
});
 
