/**
 * 
 */

$(document).ready(function() {
	getUnansweredQuestions();
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
	}).done(function(){
		console.log("ajax complete!");
	});
}


$('.submit-question-btn').click(function(){
	var formdata = {};
	formdata['question'] = $('#question').val();
	$.ajax({
		type : 'POST',
		url : "/submitquestion",
		contentType : 'application/json',
		data : JSON.stringify(formdata)
	}).done(function(){
		console.log("question submit ajax complete!");
		getSimilarQuestions();
	});
});



var getSimilarQuestions = function(){
	console.log("Fetching similar questions");
	$.ajax({
		type : 'GET',
		url : "/getsimilarqna",
		contentType : 'application/json',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received (Similar questions) : "+JSON.stringify(data));
	    	populateSimilarQuestions(data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
		$('.slidedown-similar-question-answer-show').css("display","block");
	});
}



var populateUnansweredQuestions = function(questions){
	
	for(var i = 0; i<questions.length; i++){
		$('.body-container').append(
				'<div class="row col-lg-12 submit-answer">'+
					'<form id="enter-answer-form" class="form-horizontal" >'+
						'<div class="form-group question-ele">'+
							'<label class="control-label" id="enter-question-label">Question</label>'+
							'<input name="question" type="text" class="form-control validate-ele" id="question" disabled="disabled"/>'+
						'</div>'+
						
						'<div class="form-group write-your-answer-ele" >'+
							'<label class="control-label" id="live-answer-label">Answer</label>'+
							'<textarea name="answer" class="form-control answer" id="submit-answer-id"'+ 
							'placeholder="write your answer" rows="3" required="required" ></textarea>'+
						'</div>'+
						'<button type="button" class="btn btn-default submit-question-btn">Submit</button>'+
					'</form>'+
				'</div>'
		);
	}
//		if(i % 2 == 0){
//			$('#question-answer-table tbody').append(
//				'<tr class="question-tr even-quest-row" style="font-style: italic;">'+
//					'<td style="width: 8%;">Questions : </td>'+
//					'<td style="font-weight: 600;">'+questions[i]["question"]+'</td>'+
//				'</tr>'+
//				'<tr class="answer-tr even-ans-row">'+
//					'<td style="font-style: italic;">Answer : </td>'+
//					'<td>'+questions[i]["answer"]+'</td>'+
//				'</tr>'		
//			);
//		}else{
//			$('#question-answer-table tbody').append(
//					'<tr class="question-tr odd-quest-row" style="font-style: italic;">'+
//						'<td style="width: 8%;">Questions : </td>'+
//						'<td style="font-weight: 600;">'+questions[i]["question"]+'</td>'+
//					'</tr>'+
//					'<tr class="answer-tr odd-ans-row">'+
//						'<td style="font-style: italic;">Answer : </td>'+
//						'<td>'+questions[i]["answer"]+'</td>'+
//					'</tr>'		
//			);
//		}
//		
//	}
}

