/**
 * 
 */


$(document).ready(function () {

	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.add-products').addClass('left-menu-selected');
   $('li.navbar-menu-selected').removeClass("navbar-menu-selected");
   
   if(!$('li#nav-products').hasClass("navbar-menu-selected")){
	   $('li#nav-products').addClass("navbar-menu-selected")
   }
	   
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
		$('.product-type-selector').attr('disabled',false);
		if($('.product-type-selector').val() == 'NEW PRODUCT'){
			$("#add-product-form input, #add-product-form select, #add-product-form submit").attr('disabled',false);
		}
		$('.previous-product-selector').html("");
		$.ajax({
			type: 'GET',
			url : '/getproductsformedfield?medicalfield='+$('.medical-field-selector').val(),
			dataType : 'json',
			success : function(data){
		    	console.log("Data received : "+JSON.stringify(data));
		    	populateExistingProductSelector(data);
			},
			error : function(e){
				console.log("Error : "+JSON.stringify(e));
			}
		}).done(function(){
			console.log("ajax complete!");
		});

	});
	$('.product-type-selector').on('change',function(){
		if( $('.product-type-selector').val() == 'IMPROVED PRODUCT'){
			$('.previous-product-selector').attr('disabled',false)
			$('.category-checkbox').attr('disabled',false);
			$('.product-specifications.improvement-category').css('background','white');
			$("#add-product-form input, #add-product-form select, #add-product-form submit").attr('disabled',true);
		}else{
			$('.previous-product-selector').attr('disabled',true)
			$('.category-checkbox').attr('disabled',true);
			$('.product-specifications.improvement-category').css('background','#dcdbdd');
			$("#add-product-form input, #add-product-form select, #add-product-form submit").attr('disabled',false);
			$('#affordable-checkbox').prop('checked',false);
			$('#less-sideeffects-checkbox').prop('checked',false);
		}
	});
	
//	$('#less-sideeffects-checkbox :checkbox').change(function(){//,'#affordable-checkbox :checkbox'
	$(':checkbox').change(function(){
		console.log('changed');
		if($('#less-sideeffects-checkbox').is(':checked') || $('#affordable-checkbox').is(':checked') ){
			$("#add-product-form input, #add-product-form select, #add-product-form submit").attr('disabled',false);
		}else if(!$('#less-sideeffects-checkbox').is(':checked') && !$('#affordable-checkbox').is(':checked') ){
			$("#add-product-form input, #add-product-form select, #add-product-form submit").attr('disabled',true);
		}
	});
	
	$('#product-name, #prod-release-date').on('change',function(){
		if($('#prod-release-date').val() != "" && $('#product-name').val() != ""){
			$("#upload-files-form input, #upload-files-form button, #upload-files-form submit").attr('disabled',false);
		}else if($('#prod-release-date').val() == "" || $('#product-name').val() == ""){
			$("#upload-files-form input, #upload-files-form button, #upload-files-form submit").attr('disabled',true);
		}
	});
	updateNotificationCounter();
});

var populateMedFieldSelector = function(data){
	for(var i = 0; i<data.length; i++){
		$('.medical-field-selector').append(
				'<option value="'+data[i]["medicalFieldId"]+'">'+data[i]["medicalFieldName"]+'</option>'
		);
	}
	$(".medical-field-selector").select2();
	$(".product-type-selector").select2(); 
}

var populateExistingProductSelector = function(data){
	for(var i = 0; i<data.length; i++){
		if(i == 0){
			$('.previous-product-selector').append(
					'<option value="'+data[i]["productId"]+'" selected="selected">'+data[i]["productName"]+'</option>'
			);
		}else{
			$('.previous-product-selector').append(
					'<option value="'+data[i]["productId"]+'">'+data[i]["productName"]+'</option>'
			);
		}
	}
	$(".previous-product-selector").select2();
}

function getnewproduct() {
	if(confirm('Are you sure you want to add this product?')){
		var d = {};
		d['productName'] = $('#product-name').val();
		d['releaseDate'] = $('#prod-release-date').val();
		d['medicalFieldId'] = $('.medical-field-selector').val();
		d['typeOfProduct'] = $(".product-type-selector").val();
		if($(".product-type-selector").val() == 'NEW PRODUCT'){
			d['hasLessPrice'] = 'false';
			d['hasLessSideEffects'] = 'false';
			d['improvedOverProduct'] = '0';
		}else if($(".product-type-selector").val() == 'IMPROVED PRODUCT'){
			d['hasLessPrice'] = ($('#affordable-checkbox').is(':checked'));// == true) ? true:false;
			d['hasLessSideEffects'] = ($('#less-sideeffects-checkbox').is(':checked'));// == true) ? 'true':'false';
			d['improvedOverProduct'] = $('.previous-product-selector').val();
		}
		console.log("Data captured : "+JSON.stringify(d)); 	
		$.ajax({
			type : 'POST',
	        url : "/addnewproduct",
	        data : JSON.stringify(d),
	        contentType : 'application/json'
	    });
    	document.getElementById('upload-files-form').submit();
    	updateNotificationCounter();
    	return true;
	}else return false;
}
