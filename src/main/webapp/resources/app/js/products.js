/**
 * 
 */

$(document).ready(function(){
	
	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.view-products').addClass('left-menu-selected');
	
	updateNotificationCounter();
	$('.product-selector').select2();
	
	$('.product-selector').on('change',function(){
		var id = $('.product-selector').val();
		$('.product-description').css('display','block');
		$('#products-description-table').find('tr.description').css('display','none');
		$('#products-description-table').find('tr.product-'+id).css('display','block');
		
		$.ajax({
			type: 'GET',
			url : '/getproductdocuments?productId='+id,
			dataType : 'json',
			success : function(data){
		    	console.log("Data received : "+JSON.stringify(data));
			},
			error : function(e){
				console.log("Error : "+JSON.stringify(e));
			}
		}).done(function(){
			console.log("ajax complete!");
		});
	});
	
});