/**
 * 
 */

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