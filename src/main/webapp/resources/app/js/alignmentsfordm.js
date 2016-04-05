/**
 * 
 */

$(document).ready(function(){
	
	updateNotificationCounter();
	getAlignedPhysicians();
});

var getAlignedPhysicians(){
	var salesRepId = $('.salesrep-selector').val(); 
	$.ajax({
		type : 'GET',
		url : '/getalignmentsforsr?userId='+salesRepId,
		contentType : 'application/json',
		success : function(data){
			console.log("Alignments to be shown : "+JSON.stringify(data));
//			createPitchEnvironment(data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("Received alignments");
	});
	
}