/**
 * 
 */

$(document).ready(function(){
	
	$.ajax({
		type: 'GET',
		url : '/getcurrentuser',
		success : function(data){
	    	console.log("Curent logged in user is : "+JSON.stringify(data));
	    	$('span.loggedin-user-span').html("Welcome! "+data)
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	});
	
	
});

//$.extend( $.fn.dataTable.defaults, {
//    searching: false,
//    ordering:  false
//    
//} );

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