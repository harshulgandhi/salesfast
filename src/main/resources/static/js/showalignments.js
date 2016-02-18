/**
 * handles UI interactivity for page 
 * showalignments.html
 */
$(document).ready(function() {
    var table = $('#aligned-physician-table').DataTable();
 
    $('#aligned-physician-tabletbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
    } );
 
    /*$('#button').click( function () {
        alert( table.rows('.selected').data().length +' row(s) selected' );
    } );*/
});