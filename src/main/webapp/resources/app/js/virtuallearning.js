/**
 * 
 */
var virtualLearningData = [];
$(document).ready(function () {
	
	$.ajax({
		type: 'GET',
		url : '/getvirtuallearningdata',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received : "+JSON.stringify(data));
	    	createLearningEnvironment(data);
	    	virtualLearningData = data;
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
});

var createLearningEnvironment = function(data){
	for(var i = 0; i<data.length; i++){
		$('.btn-left-panel').append(
					'<input type="button" class="form-control btn-success show-doc" value="'+	data[i]["labelToShow"]+'" /><br/>'
				);
	}
	$('.btn-left-panel').find('input[type="button"]').on('click',function(){
//		console.log($('.btn-left-panel').find('input[type="button"]').index(this));
		$('.doc-right-panel').css('display','none');
	    var idx = $('.btn-left-panel').find('input[type="button"]').index(this);
		console.log(typeof($(this).val()));
	    var str = $(this).val();
	    str = str.replace(/\s+/g, '-').toLowerCase();   // Removing space from name
	    str = str.replace('\'', '').toLowerCase();		// Removing apostrophe
	    if($('.row-custom-style').find('#'+str).length == 0){		//To check if Object already exists then don't append, otherwise do.
			$('.row-custom-style').append(
					'<div class="col-lg-6 doc-right-panel" id="'+str+'" style="display: none;">'+
						'<object class="pdf-doc-object pdf-doc-object-slidedown" data="'+virtualLearningData[idx]["filePath"]+'" type="application/pdf" >'+
							'<embed src="'+virtualLearningData[idx]["filePath"]+'" alt="pdf" pluginspage="http://www.adobe.com/products/acrobat/readstep2.html">'+
							'</embed>'+
						'</object>'+
					'</div>'
			);
	    }
		var id = '#'+str;
		$(id).slideToggle('fast');
	});
}
