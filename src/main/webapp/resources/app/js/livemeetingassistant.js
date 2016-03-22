/**
 * 
 */

$(document).ready(function() {

	$('.slidedown-questions').click(function(){
		$('.slidedown-question-answer-show').slideToggle('fast');
	});
	$('.slidedown-questions-self').click(function(){
		$('.slidedown-self-question-answer-show').slideToggle('fast');
	});
	
	$('li.left-menu-selected').removeClass('left-menu-selected');
	$('li.live-meeting-qna-li').addClass('left-menu-selected');
	
	getAllQuestions();
	getAllQuestionsAskedBySelf();
	updateNotificationCounter();
});


var getAllQuestions = function(){
	$.ajax({
		type: 'GET',
		url : '/getallqna',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received (All questions): "+JSON.stringify(data));
	    	populateAllQuestions(data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
}

var getAllQuestionsAskedBySelf = function(){
	$.ajax({
		type: 'GET',
		url : '/getquestionaskedbyself',
		dataType : 'json',
		success : function(data){
	    	console.log("Data received (Self questions): "+JSON.stringify(data));
	    	populateAllQuestionsAskedBySelf(data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
}



$('.submit-question-btn').click(function(){
	getSimilarQuestions();
});


$(document).on('click','button.submit-question-post-suggest-btn',function(){
	var formdata = {};
	formdata['question'] = $('#question').val();
	$.ajax({
		type : 'POST',
		url : "/submitquestion",
		contentType : 'application/json',
		data : JSON.stringify(formdata)
	}).done(function(){
		console.log("question submit ajax complete!");
		location.reload(true);
	});
});


var getSimilarQuestions = function(){
	var formdata = {};
	formdata['question'] = $('#question').val();
	var quest = $('#question').val();
	console.log("Fetching similar questions");
	$.ajax({
		type : 'GET',
		url : "/getsimilarqna?ques="+quest,
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

var populateAllQuestions = function(questions){
	
	for(var i = 0; i<questions.length; i++){
		if(i % 2 == 0){
			$('#question-answer-table tbody').append(
				'<tr class="question-tr even-quest-row" style="font-style: italic;">'+
					'<td style="width: 8%;"><div class="ques-ans-label-td">'+
						'<div class="question-label">Question : </div>'+
						'<div class="answer-label">Answer : </div>'+
					'</div></td>'+
					'<td style="font-weight: 600;"><div class="ques-ans-td">'+
						'<div class="question">'+questions[i]["question"]+'</div>'+
						'<div class="answer">'+questions[i]["answer"]+'</div>'+
					'</div></td>'+
				'</tr>'
			);
		}else{
			$('#question-answer-table tbody').append(
				'<tr class="question-tr odd-quest-row" style="font-style: italic;">'+
					'<td style="width: 8%;"><div class="ques-ans-label-td">'+
						'<div class="question-label">Questions : </div>'+
						'<div class="answer-label">Answer : </div>'+
					'</div></td>'+
					'<td style="font-weight: 600;"><div class="ques-ans-td">'+
						'<div class="question">'+questions[i]["question"]+'</div>'+
						'<div class="answer">'+questions[i]["answer"]+'</div>'+
					'</div></td>'+
				'</tr>'	
			);
		}
	}
	var table = $('#question-answer-table').dataTable({
		"bSort": false
	});
}

var populateAllQuestionsAskedBySelf = function(questions){
	
	for(var i = 0; i<questions.length; i++){
		if(questions[i]["answer"] == null) questions[i]["answer"] = '<i style="color: red;">This question has not been answered yet.</i>';
		if(i % 2 == 0){
			$('#self-question-answer-table tbody').append(
				'<tr class="question-tr even-quest-row" style="font-style: italic;">'+
					'<td style="width: 8%;"><div class="ques-ans-label-td">'+
						'<div class="question-label">Question : </div>'+
						'<div class="answer-label">Answer : </div>'+
					'</div></td>'+
					'<td style="font-weight: 600;"><div class="ques-ans-td">'+
						'<div class="question">'+questions[i]["question"]+'</div>'+
						'<div class="answer">'+questions[i]["answer"]+'</div>'+
					'</div></td>'+
				'</tr>'
			);
		}else{
			$('#self-question-answer-table tbody').append(
				'<tr class="question-tr odd-quest-row" style="font-style: italic;">'+
					'<td style="width: 8%;"><div class="ques-ans-label-td">'+
						'<div class="question-label">Question : </div>'+
						'<div class="answer-label">Answer : </div>'+
					'</div></td>'+
					'<td style="font-weight: 600;"><div class="ques-ans-td">'+
						'<div class="question">'+questions[i]["question"]+'</div>'+
						'<div class="answer">'+questions[i]["answer"]+'</div>'+
					'</div></td>'+
				'</tr>'	
			);
		}
	}
	
	var table = $('#self-question-answer-table').dataTable({
		"bSort": false
	});
}

$(document).on('click', 'button.get-filtered-rows', function get_filtered_datatable() {
    var filteredrows = $("#question-answer-table").dataTable()._('tr', {"filter": "applied"});

    for ( var i = 0; i < filteredrows.length; i++ ) {
        console.log(filteredrows[i]);
    };
});

var populateSimilarQuestions = function(questions){
	
	for(var i = 0; i<questions.length; i++){
		if(i % 2 == 0){
			$('#similar-question-answer-table tbody').append(
				'<tr class="question-tr even-quest-row" style="font-style: italic;">'+
					'<td style="width: 8%;"><div class="ques-ans-label-td">'+
						'<div class="question-label">Questions : </div>'+
						'<div class="answer-label">Answer : </div>'+
					'</div></td>'+
					'<td style="font-weight: 600;"><div class="ques-ans-td">'+
						'<div class="question">'+questions[i]["question"]+'</div>'+
						'<div class="answer">'+questions[i]["answer"]+'</div>'+
					'</div></td>'+
				'</tr>'	
			);
		}else{
			$('#similar-question-answer-table tbody').append(
					'<tr class="question-tr odd-quest-row" style="font-style: italic;">'+
						'<td style="width: 8%;"><div class="ques-ans-label-td">'+
							'<div class="question-label">Questions : </div>'+
							'<div class="answer-label">Answer : </div>'+
						'</div></td>'+
						'<td style="font-weight: 600;"><div class="ques-ans-td">'+
							'<div class="question">'+questions[i]["question"]+'</div>'+
							'<div class="answer">'+questions[i]["answer"]+'</div>'+
						'</div></td>'+
					'</tr>'		
			);
		}
	}
	var table = $('#similar-question-answer-table').dataTable({
		"bSort": false
	});
}
