/**
 * 
 */
$(document).ready(function() {
	var table = $('#notifications-table').DataTable();
	 
	$('#notifications-table tbody').on( 'click', '.remove-notification', function () {
		var r_ = $(this).parents('tr');
		var userId = r_.find('td.user-id').html();
		var notificationId = r_.find('td.notification-id').html();
		
		table.row( $(this).parents('tr') ).remove().draw();
		$.ajax({
			type: 'POST',
			url: '/deletenotification?notificationId='+notificationId,
			contentType: 'application/json; charset=utf-8',
			dataType: 'json'
		});
		
	});
	$('#notifications-table tbody').on( 'click', '.detail-notification', function () {
		var r_ = $(this).parents('tr');
		if(r_.find('.notification-category').html() == "LIVE MEETING QUESTION"){
			window.location.replace("/unansweredques")
		}else if(r_.find('.notification-category').html() == "QUESTION WAS ANSWERED"){
			window.location.replace("/livemeetingquestions")
		}
		
		
	});
});
 
    
