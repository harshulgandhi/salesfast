/**
 * 
 */

updateNotificationCounter();

$(document).ready(function(){
	$('li.navbar-menu-selected').removeClass("navbar-menu-selected");
	   
	if(!$('li#nav-home').hasClass("navbar-menu-selected")){
		$('li#nav-home').addClass("navbar-menu-selected")
	}
});