/**
 * common custom javascript added to footer i.e. to all pages
 */

$.extend( $.fn.dataTable.defaults, {
    searching: false,
    ordering:  false
} );


var updateNotificationCounter = function(){
	$.ajax({
		type : 'GET',
		url : "/getnotificationcount",
		contentType : 'application/json',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received  : "+JSON.stringify(data));
	    	document.getElementById("notification-count").textContent=data[0];
	    },
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	});
}

//$(document).on('click','ul#salesfast-navbar li',function(){
//	if(!$(this).hasClass("navbar-menu-selected")){
//		$(this).addClass("navbar-menu-selected");
//	}
//});

//For updating the notification counter
//document.getElementById("myspan").textContent="newtext";
