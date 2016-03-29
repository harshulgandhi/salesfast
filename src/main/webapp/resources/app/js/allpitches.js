/**
 * 
 */

$(document).ready(function(){
	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.all-pitches-li').addClass('left-menu-selected');
	$('.filter-selectors').select2();
	
	updateNotificationCounter();
	
});

$(document).on('change','select.filter-selectors',function(){
	updateNotificationCounter();
	
	$('.filter-selectors').each(function(){
		console.log($(this).val());
	});
	var data = {};
	data["medicalFieldId"] = $('.med-field-selector').val();
	data["productId"] = $('.product-selector').val();
	data["userId"]= $('.salesrep-selector').val();
	data["physicianId"] = $('.physician-selector').val();
	$.ajax({
		type : 'POST',
		url : '/filterparameters',
		data : JSON.stringify(data),
		contentType : 'application/json'
	}).done(function(){
		console.log("Sent all pitch paramaters");
	});
	
});