/**
 * 
 */

var unansweredQuestions = {}

$(document).ready(function() {
	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.submit-answer-li').addClass('left-menu-selected');
	getUnansweredQuestions();
	updateNotificationCounter();
});

$(document).on('click', "button.submit-answer-btn", function() {
	console.log("clicked");
	var id = $(this).attr('id')
	var answer  = $('#submit-answer-id-'+id).val();
	console.log("Answer provided is : "+answer);
	var formdata = {};
	formdata["liveMeetingQuestionId"] = unansweredQuestions[id]["liveMeetingQuestionId"];
	formdata["userId"] = unansweredQuestions[id]["userId"];
	formdata["question"] = unansweredQuestions[id]["question"];
	formdata["answer"] = answer;
	formdata["importanceIndex"] = unansweredQuestions[id]["importanceIndex"];
	$.ajax({
		type : 'POST',
		url : "/submitanswer",
		contentType : 'application/json',
		data : JSON.stringify(formdata),
		success : function(){
			console.log("Answer submitted successfully.");
		}
	}).done(function(){
		console.log("ajax complete!");
		location.reload(true);
	});      
});


var getUnansweredQuestions = function(){
	$.ajax({
		type: 'GET',
		url : '/getunansweredques',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received (All questions): "+JSON.stringify(data));
	    	populateUnansweredQuestions(data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	});
}

var populateUnansweredQuestions = function(questions){
	unansweredQuestions = questions;
	if(questions.length == 0){
		$('.body-container').append(
				'<div class="row no-questions" style=" '+
				    'font-style: italic;'+
				    'font-variant: small-caps;'+
				    'color: green;'+
				    'font-style: bold;'+
				'"><h5>No questions to be answered!</h5></div>'
		);
	}
	for(var i = 0; i<questions.length; i++){
		$('.body-container').append(
				'<div class="row col-lg-12 submit-answer">'+
					'<form id="enter-answer-form" class="form-horizontal" >'+
						'<div class="form-group question-ele">'+
							'<label class="control-label" id="enter-question-label">Question</label>'+
							'<input name="question" type="text" class="form-control validate-ele" id="question-'+i+'" disabled="disabled" />'+
						'</div>'+
						
						'<div class="form-group write-your-answer-ele" >'+
							'<label class="control-label" id="live-answer-label">Answer</label>'+
							'<textarea name="answer" class="form-control answer" id="submit-answer-id-'+i+'"'+ 
							' placeholder="write your answer" rows="3" required="required" ></textarea>'+
						'</div>'+
						'<button type="button" class="btn btn-default submit-answer-btn" id="'+i+'">Submit Answer</button>'+
					'</form>'+
				'</div>'
		);
		document.getElementById("question-"+i).value = questions[i].question;
	}
}


