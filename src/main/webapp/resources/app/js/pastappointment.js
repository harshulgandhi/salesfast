/**
 * 
 */
$(document).ready(function() {

	$('.past-appointment-li').on('click', function(){
		$('.future-appointment-div').css('display','none');
	});
   
	$('.left-menu-ul').on('click','li',function(){
		$('li.left-menu-selected').removeClass('left-menu-selected');
		$(this).addClass('left-menu-selected');
		$(this).find('a').addClass('left-menu-selected-anchor');
	});
}
   