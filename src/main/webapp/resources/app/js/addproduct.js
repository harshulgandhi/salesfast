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
				'<option value="'+data[i]["medicalFieldId"]+'">'+data[i]["medicalFieldName"]+'</option>'
		);
	}
	$(".medical-field-selector").select2();
}

function getnewproduct() {
	if(confirm('Are you sure you want to add this product?')){
		var d = {};
		d['productName'] = $('#product-name').val();
		d['releaseDate'] = $('#prod-release-date').val();
		d['medicalFieldId'] = $('.medical-field-selector').val();
		console.log("Data captured : "+JSON.stringify(d)); 	
		$.ajax({
			type : 'POST',
	        url : "/addnewproduct",
	        data : JSON.stringify(d),
	        contentType : 'application/json'
	    });
    	document.getElementById('upload-files-form').submit();
    	return true;
	}else return false;
}