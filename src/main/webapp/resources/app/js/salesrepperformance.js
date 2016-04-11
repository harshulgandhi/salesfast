/**
 * 
 */

$(document).ready(function(){
	
	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.salesrep-performance').addClass('left-menu-selected');
	
	$('li.navbar-menu-selected').removeClass("navbar-menu-selected");
	   
	if(!$('li#nav-data-report').hasClass("navbar-menu-selected")){
		$('li#nav-data-report').addClass("navbar-menu-selected")
	}
});