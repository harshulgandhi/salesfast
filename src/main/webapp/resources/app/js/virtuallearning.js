/**
 * 
 */
var virtualLearningData = [];
$(document).ready(function () {
	$('li.navbar-menu-selected').removeClass("navbar-menu-selected");
	   
	if(!$('li#nav-virtual-learning').hasClass("navbar-menu-selected")){
		$('li#nav-virtual-learning').addClass("navbar-menu-selected")
	}
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
	
	updateNotificationCounter();
});

var createLearningEnvironment = function(data){
	for(var i = 0; i<data.length; i++){
		$('.btn-left-panel').append(
					'<button type="button" class="btn btn-default btn-md show-doc form-control" id="'+i+'">'+
					'	<span class="button-value">'+data[i]["labelToShow"]+'</span> '+
					'	<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> '+
					'</button><br/>'
				);
	}
	$('.btn-left-panel').find('button').on('click',function(){
		$('.doc-right-panel').css('display','none');
	    var idx = $('.btn-left-panel').find('.show-doc').index(this);
	    $('button.training-button-clicked').removeClass('training-button-clicked');
		$(this).addClass('training-button-clicked');
		
	    var str = $(this).find('.button-value').html();
	    str = str.replace(/\s+/g, '-').toLowerCase();   // Removing space from name
	    str = str.replace('\'', '').toLowerCase();		// Removing apostrophe
	    if($('.row-custom-style').find('#'+str).length == 0){		//To check if Object already exists then don't append, otherwise do.
			$('.row-custom-style').append(
					'<div class="col-lg-6 doc-right-panel" id="'+str+'" style="display: none;">'+
						'<object class="pdf-doc-object pdf-doc-object-slidedown" data="'+data[idx]["filePath"]+'" type="application/pdf" >'+
							'<embed src="'+data[idx]["filePath"]+'" alt="pdf" pluginspage="http://www.adobe.com/products/acrobat/readstep2.html">'+
							'</embed>'+
						'</object>'+
					'</div>'
			);
	    }
		var id = '#'+str;
		$(id).slideToggle('fast');
	});
	
	//Triggering click event to show first graph
	var list = $('button.show-doc');
	var btn = list[0];
	$('#'+btn.id).trigger( "click" );
	$(window).resize();
}
