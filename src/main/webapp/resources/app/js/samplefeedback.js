/**
 * 
 */

$(document).ready(function() {
});


$(document).on('change','select.side-effects-selector', function(){
	if($('.side-effects-selector').val() == "1"){
	  $('.side-effects').attr('disabled',false);
	}else{
	 $('.side-effects').attr('disabled',true);
	}
});


$(document).on('click', 'button.submit-feedback-btn', function(){
	var formData = {};
	var productName = $('.product-name').html();
	var productId = $('.product-id').html();
	formData['isMedicineEffective'] = $('.med-effective-selector').val() === "1" ? 'true':'false';
	var sideEffectSelect = $('.side-effects-selector').val() ;
	formData['hasSideEffects'] = sideEffectSelect === "1" ? 'true':'false';
	formData['isAffordable'] = $('.is-affordable-selector').val() === "1" ? 'true':'false';
	var sideEffectComments = $('.side-effects').val();
	formData['sideEffectsComments'] = sideEffectComments;
	formData['otherComments'] = $('.other-comments').val();
	formData['productId'] = $('.product-id').html();
	console.log("json : "+JSON.stringify(formData));
	if(sideEffectSelect == "0" || (sideEffectSelect=="1" && sideEffectComments != '')){
		$.ajax({
			type : 'POST',
	        url : "/submitsamplefeedback",
	        data : JSON.stringify(formData),
	        contentType : 'application/json'
	    }).done(function() {
	    	console.log("submitted");
	    	window.location.replace("/samplefeedbacksubmitted");
	    });
	}else{
		alert("Please mention what side effect you are facing after consuming this medicine.");
	}
})