/**
 * 
 */


$(document).ready(function () {
	
	$.ajax({
		type: 'GET',
		url : '/getmedicalfields',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received : "+JSON.stringify(data));
	    	populateMedFieldSelector(data)
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
	
	$("#upload-files-form input, #upload-files-form button, #upload-files-form submit").attr('disabled',true);
	$("#add-product-form input, #add-product-form select, #add-product-form submit").attr('disabled',true);
	$('.medical-field-selector').on('change',function(){
		$("#add-product-form input, #add-product-form select, #add-product-form submit").attr('disabled',false);
	});
	
	$('#product-name, #prod-release-date').on('change',function(){
		if($('#prod-release-date').val() != "" && $('#product-name').val() != ""){
			$("#upload-files-form input, #upload-files-form button, #upload-files-form submit").attr('disabled',false);
		}else if($('#prod-release-date').val() == "" || $('#product-name').val() == ""){
			$("#upload-files-form input, #upload-files-form button, #upload-files-form submit").attr('disabled',true);
		}
	});
	
});

var populateMedFieldSelector = function(data){
	for(var i = 0; i<data.length; i++){
		$('.medical-field-selector').append(
				'<option value="'+data[i]["medicalFieldName"]+'">'+data[i]["medicalFieldName"]+'</option>'
		);
	}
	$(".medical-field-selector").select2();
}

function clicked() {
    if(confirm('checks before submitting')){
    	document.getElementById('upload-files-form').submit();
    }
}