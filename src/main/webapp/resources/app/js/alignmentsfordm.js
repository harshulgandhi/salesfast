/**
 * 
 */
$(document).ready(function(){
	$('.filter-selectors').select2();
	updateNotificationCounter();
	getAlignedPhysicians();
	getSuggestionPhysicians();
	
	$('li.navbar-menu-selected').removeClass("navbar-menu-selected");
	   
	if(!$('li#nav-view-alignments').hasClass("navbar-menu-selected")){
		$('li#nav-view-alignments').addClass("navbar-menu-selected")
	}
});

$(document).on('change','select.salesrep-selector',function(){
	$("table#view-automatic-alignments-table tbody").html("");
	updateNotificationCounter();
	getAlignedPhysicians();
	getSuggestionPhysicians();
});

var getAlignedPhysicians = function(){
	var salesRepId = $('.salesrep-selector').val(); 
	$.ajax({
		type : 'GET',
		url : '/getalignmentsfordm?userId='+salesRepId,
		contentType : 'application/json',
		success : function(data){
			console.log("Alignments to be shown : "+JSON.stringify(data));
			populateSelectedUsersAlignment(data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("Received alignments");
	});
	
}


var getSuggestionPhysicians = function(){
	var salesRepId = $('.salesrep-selector').val(); 
	$.ajax({
		type : 'GET',
		url : '/getsuggestivealignmentsfordm?userId='+salesRepId,
		contentType : 'application/json',
		success : function(data){
			console.log("Suggestion to be shown : "+JSON.stringify(data));
			populateSuggestionPhysicians(data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("Received alignments");
	});
	
}

$(document).on('click','button.show-contact',function(){
	$(this).closest('tr').find('div.aligned-physician-contact').toggle();
	if($(this).closest('tr').find('div.aligned-physician-contact').css('display') == 'none'){
	    $(this).find('span.button-value').html("Show Contact Details");
	}else if($(this).closest('tr').find('div.aligned-physician-contact').css('display') == 'block'){
	    $(this).find('span.button-value').html("Hide Contact Details");
	}
});


$('#view-automatic-alignments-table tbody').on( 'click', '.remove-alignment', function () {
	var r_ = $(this).parents('tr');
	var alignmentId = r_.find('td.alignment-id').html();
	if(confirm("Are you sure you want to delete this alignment?")){
		$.ajax({
			type: 'POST',
			url: '/deletealignment?alignmentId='+alignmentId
		}).done(function(){
			console.log()
			updateNotificationCounter();
		});
		var _table = $('#view-automatic-alignments-table').DataTable();
		_table.row( $(this).parents('tr') ).remove().draw();	
	}
});


var populateSelectedUsersAlignment = function(alignmentsData){
	if(alignmentsData.length == 0){
		$('table#view-automatic-alignments-table tbody').append(
			'<tr class="no-alignment-tr">'+
				'<td class="no-alignments-message">'+
					'<div style="font-weight: 600;" class="no-alignments-div">'+
						'No alignments available for this SalesRep. Align manually or change the selection above.'+
					'</div>'+
				'</td>'+
			'</tr>'
		);
		$('table#view-pitch-table tbody').find('tr').css('background-color','#E8D3CE');
	}
	else{
		for(var i = 0; i < alignmentsData.length; i++){
			if(alignmentsData[i]["physicianNew"] == true) alignmentsData[i]["physicianNew"] = 'YES';
			else alignmentsData[i]["physicianNew"] = 'NO';
			$('table#view-automatic-alignments-table tbody').append(
					'<tr>'+
						'<td class="alignment-id" style="display: none">'+alignmentsData[i]["alignmentId"]+'</td>'+
						'<td class="physician-id" style="display: none">'+alignmentsData[i]["physicianId"]+'</td>'+
						'<td class="physician-name">'+alignmentsData[i]["physicianName"]+'</td>'+
						'<td class="contact-details">'+
							'<div class="aligned-phys-contact-td">'+
								'<div class="phys-contact-detail-btn">'+
									'<button type="button" class="btn btn-default btn-md show-contact form-control">'+
										'<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>'+
										'<span class="button-value">Show Contact Details</span>'+
									'</button>'+
								'</div>'+
								'<div class="aligned-physician-contact"  style="display: none">'+
									'<div class="phys-address"><span class="contactdetail-label">Address : </span>'+
									'<span class="contactdetail-content">'+alignmentsData[i]["physicianAddressLineOne"]+' '+alignmentsData[i]["physicianAddressLineTwo"]+' '+alignmentsData[i]["physicianCity"]+' '+alignmentsData[i]["physicianState"]+' '+alignmentsData[i]["physicianZip"]+'</span>'+
									'</div>'+
									'<div class="phys-phone"><span class="contactdetail-label">Phone : </span><span class="contactdetail-content"'+ 
									'>'+alignmentsData[i]["phone"]+'</span></div>'+
									'<div class="phys-email"><span class="contactdetail-label">Email : </span><span class="contactdetail-content"'+ 
									'>'+alignmentsData[i]["email"]+'</span></div>'+
								'</div>'+
							'</div>'+
						'</td>'+
						'<td class="product-id" style="display: none">'+alignmentsData[i]["productId"]+'</td>'+
						'<td class="product-name">'+alignmentsData[i]["productName"]+'</td>'+
						'<td class="is-new">'+alignmentsData[i]["physicianNew"]+'</td>'+
						'<td class="view-past-appointment">'+
							'<button type="button" class="btn btn-default btn-md view-past-appointments form-control">'+
								'<span class="glyphicon" aria-hidden="true"></span>'+
								'<span class="button-value">Past Appointment</span>'+
							'</button>'+
						'</td>'+
						'<td class="remove-alignment-btn">'+
							'<button type="button" class="btn btn-default btn-sm btn-warning remove-alignment">'+
				          		'<span class="glyphicon glyphicon-trash"></span> Remove Alignment'+ 
					        '</button>'+
						'</td>'+
					'</tr>'
			
			);
		}
	}
	var table = $('#view-automatic-alignments-table').DataTable({
		'iDisplayLength': 5
	});
//	{
//			retrieve: true
//	  });
}

var populateSuggestionPhysicians = function(physiciansData){
	var selectedSalesRep = $('.salesrep-selector').find('option[selected="selected"]').html();
	if(physiciansData.length == 0){
		$('table#suggestion-physician-table tbody').append(
			'<tr class="no-suggestion-tr">'+
				'<td class="no-suggestion-message">'+
					'<div style="font-weight: 600;" class="no-suggestion-div">'+
						'No physicians available who can be aligned to this SalesRep.'+
					'</div>'+
				'</td>'+
			'</tr>'
		);
		$('table#suggestion-physician-table tbody tbody').find('tr').css('background-color','#E8D3CE');
	}
	else{
		for(var i = 0; i < physiciansData.length; i++){
			if(physiciansData[i]["physicianNew"] == true) physiciansData[i]["physicianNew"] = 'YES';
			else physiciansData[i]["physicianNew"] = 'NO';
			$('table#suggestion-physician-table tbody').append(
					'<tr>'+
						'<td class="alignment-id" style="display: none">'+physiciansData[i]["alignmentId"]+'</td>'+
						'<td class="physician-id" style="display: none">'+physiciansData[i]["physicianId"]+'</td>'+
						'<td class="physician-name">'+physiciansData[i]["physicianName"]+'</td>'+
						'<td class="contact-details">'+
							'<div class="aligned-phys-contact-td">'+
								'<div class="phys-contact-detail-btn">'+
									'<button type="button" class="btn btn-default btn-md show-contact form-control">'+
										'<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>'+
										'<span class="button-value">Show Contact Details</span>'+
									'</button>'+
								'</div>'+
								'<div class="aligned-physician-contact"  style="display: none">'+
									'<div class="phys-address"><span class="contactdetail-label">Address : </span>'+
									'<span class="contactdetail-content">'+physiciansData[i]["physicianAddressLineOne"]+' '+physiciansData[i]["physicianAddressLineTwo"]+' '+physiciansData[i]["physicianCity"]+' '+physiciansData[i]["physicianState"]+' '+physiciansData[i]["physicianZip"]+'</span>'+
									'</div>'+
									'<div class="phys-phone"><span class="contactdetail-label">Phone : </span><span class="contactdetail-content"'+ 
									'>'+physiciansData[i]["phone"]+'</span></div>'+
									'<div class="phys-email"><span class="contactdetail-label">Email : </span><span class="contactdetail-content"'+ 
									'>'+physiciansData[i]["email"]+'</span></div>'+
								'</div>'+
							'</div>'+
						'</td>'+
						'<td class="product-id" style="display: none">'+physiciansData[i]["productId"]+'</td>'+
						'<td class="product-name">'+physiciansData[i]["productName"]+'</td>'+
						'<td class="is-new">'+physiciansData[i]["physicianNew"]+'</td>'+
						'<td class="view-past-appointment">'+
							'<button type="button" class="btn btn-default btn-md view-past-appointments form-control">'+
								'<span class="glyphicon" aria-hidden="true"></span>'+
								'<span class="button-value">Past Appointment</span>'+
							'</button>'+
						'</td>'+
						'<td class="add-alignment-btn">'+
							'<button type="button" class="btn btn-default btn-sm btn-success remove-alignment">'+
				          		'<span class="glyphicon glyphicon-plus add-alignment-glyphicon"></span> Align to '+selectedSalesRep+ 
					        '</button>'+
						'</td>'+
					'</tr>'
			
			);
		}
	}
	var table = $('#suggestion-physician-table').DataTable({
		'iDisplayLength': 5
	});
//	{
//			retrieve: true
//	   });
}