/**
 * 
 */

var currentProductId = 0;
var productFiles = [];

$(document).ready(function(){
	
	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.view-products').addClass('left-menu-selected');
	
	updateNotificationCounter();
	$('.product-selector').select2();
	
	$('.product-selector').on('change',function(){
		var id = $('.product-selector').val();
		currentProductId = id;
		$('.product-description').css('display','block');
		$('#products-description-table').find('tr.description').css('display','none');
		$('#products-description-table').find('tr.product-'+id).css('display','block');
		$('.btn-container').css('display','none');
		$('.update-right-panel').css('display','none');
		$('.doc-middle-panel').css('display','none');
		productFiles = [];
		productFiles.length = 0;
		$.ajax({
			type: 'GET',
			url : '/getproductdocuments?productId='+id,
			dataType : 'json',
			success : function(data){
		    	productFiles = data;
		    	console.log("Data received (Product Files): "+JSON.stringify(data));
		    	createDocsViewEnvironment(data);
			},
			error : function(e){
				console.log("Error : "+JSON.stringify(e));
			}
		}).done(function(){
			console.log("ajax complete!");
		});
		$('.btn-container-div-product-'+id).css('display','block');
	});
	
});

var createDocsViewEnvironment = function(data){
	if($('.btn-left-panel').find('.btn-for-product-'+currentProductId).length == 0){
		$('.btn-left-panel').append(
				'<div class="btn-container-div-product-'+currentProductId+' btn-container">'+
				'</div>'
		);
	for(var i = 0; i<data.length; i++){
		$('.btn-container-div-product-'+currentProductId).append(
				'<button type="button" class="btn btn-default btn-md show-doc form-control btn-for-product-'+currentProductId+'" >'+
				'	<span class="button-value">'+data[i]["labelToShow"]+'</span> '+
				'	<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> '+
				'</button><br/>'
			);
		}
	attachClickEvent(currentProductId);
	}
	
	
}

var attachClickEvent = function(productId){
	$(document).on('click','button.btn-for-product-'+productId,function(){
		$('.doc-middle-panel').css('display','none');
		$('.update-right-panel').css('display','none');
	    var idx = $('.btn-container-div-product-'+productId).find('.show-doc').index(this);
	    console.log("idx : "+idx);
	    var str = $(this).find('.button-value').html();
	    str = str.replace(/\s+/g, '-').toLowerCase();   // Removing space from name
	    str = str.replace('\'', '').toLowerCase();		// Removing apostrophe
	    str = str.replace('(', '').toLowerCase();		// Removing ( from name
	    str = str.replace(')', '').toLowerCase();		// Removing ) from name
	    if($('.row-custom-style').find('#'+str+'-product'+currentProductId).length == 0){		//To check if Object already exists then don't append, otherwise do.
			$('.row-custom-style').append(
					'<div class="col-lg-6 doc-middle-panel" id="'+str+'-product'+currentProductId+'" style="display: none;">'+
						'<object class="pdf-doc-object pdf-doc-object-slidedown" data="'+productFiles[idx]["filePath"] +'" type="application/pdf" >'+
							'<embed src="'+productFiles[idx]["filePath"] + '" alt="pdf" pluginspage="http://www.adobe.com/products/acrobat/readstep2.html">'+
							'</embed>'+
						'</object>'+
					'</div>'+
					'<div class="col-lg-3 update-right-panel" id="'+str+'-update-doc-'+currentProductId+'" style="display: none;">'+
						'<form id="update-files-form-'+currentProductId+'" class="form-horizontal" action="/updateproductfile" method="post" enctype="multipart/form-data">'+
							'<div class="form-group">'+
								'<label for="product-doc" class="control-label" id="product-doc-label">Update This Doc</label>'+
								'<input name="productdocument" type="file" class="form-control validate-ele" id="product-doc-update"/>'+
							'</div>'+
							'<div class="form-group">'+
								'<input type="hidden" class="hiddent-param selected-product-id" name="selectedProductId" value="'+currentProductId+'"></input>'+
							'</div>'+
							'<div class="form-group">'+
								'<input type="hidden" class="hiddent-param type-of-doc" name="typeOfDocument" value="'+productFiles[idx]["labelToShow"]+'"></input>'+
							'</div>'+
							'<button type="submit" id="update-file-form-submit-'+currentProductId+'" class="btn btn-default submit-updated-doc">Submit Product</button>'+
						 '</form>'+
				    '</div>'
			);
	    }
	    
		var id = '#'+str+'-product'+currentProductId;
		var id_doc_update = '#'+str+'-update-doc-'+currentProductId; 
		$(id).slideToggle('fast');
		$(id_doc_update).slideToggle('fast');
	});
	
}


