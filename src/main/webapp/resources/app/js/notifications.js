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
		}
		else if(r_.find('.notification-category').html() == "QUESTION WAS ANSWERED"){
			window.location.replace("/livemeetingquestions?param="+"QUESTION WAS ANSWERED")
		}
		else if(r_.find('.notification-category').html().indexOf("NEW PRODUCT LOST PHYSICIAN") > -1){
			var notifCat = r_.find('.notification-category').html();
			var productName = notifCat.split(" ")[notifCat.split(" ").length - 1]; 
			window.location.replace("/showalignments?status="+"LOST "+productName);
		}
		else if(r_.find('.notification-category').html() == "NEW PRODUCT PRESCRIBING PHYSICIAN"){
			window.location.replace("/showalignments?status="+"PRESCRIBING");
		}
		else if(r_.find('.notification-category').html().indexOf("NEW PRODUCT NOT INTERESTED PHYSICIAN") > -1){
			var notifCat = r_.find('.notification-category').html();
			var productName = notifCat.split(" ")[notifCat.split(" ").length - 1]
			window.location.replace("/showalignments?status="+"NOT INTERESTED "+productName);
		}
		
		else if(r_.find('.notification-category').html().indexOf("IMPROVED PRODUCT LOST PHYSICIAN MORE AFFORDABLE LESS SIDE EFFECTS") > -1){
			var notifCat = r_.find('.notification-category').html();
			var productName = notifCat.split(" ")[notifCat.split(" ").length - 1]
			window.location.replace("/showalignments?status="+"LOST MORE AFFORDABLE LESS SIDE EFFECTS "+productName);
		}
		
		else if(r_.find('.notification-category').html().indexOf("IMPROVED PRODUCT LOST PHYSICIAN MORE AFFORDABLE") > -1){
			var notifCat = r_.find('.notification-category').html();
			var productName = notifCat.split(" ")[notifCat.split(" ").length - 1]
			window.location.replace("/showalignments?status="+"LOST MORE AFFORDABLE "+productName);
		}
		else if(r_.find('.notification-category').html().indexOf("IMPROVED PRODUCT LOST PHYSICIAN LESS SIDE EFFECTS") > -1){
			var notifCat = r_.find('.notification-category').html();
			var productName = notifCat.split(" ")[notifCat.split(" ").length - 1]
			window.location.replace("/showalignments?status="+"LOST LESS SIDE EFFECTS "+productName);
		}
		
		else if(r_.find('.notification-category').html() == "CANCELLED APPOINTMENTS BY PHYS"){
			window.location.replace("/showappointments");
		}
		else if(r_.find('.notification-category').html() == "FOLLOW UP"){
			window.location.replace("/showappointments#followup-appointments-table");
		}
		else if(r_.find('.notification-category').html() == "NEW PRODUCT TO PHYS"){
			window.location.replace("/edetailing?param="+"NEW_PRODUCT");
		}
		
		
	});
	updateNotificationCounter();
	
});
 
