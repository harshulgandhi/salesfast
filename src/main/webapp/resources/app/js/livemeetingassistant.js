/**
 * 
 */

$(document).ready(function() {

	$('.slidedown-questions').click(function(){
		$('.slidedown-question-answer-show').slideToggle('fast');
	});
	getAllQuestions();
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



var populateAllQuestions = function(questions){
	
	for(var i = 0; i<questions.length; i++){
		if(i % 2 == 0){
			$('#question-answer-table tbody').append(
				'<tr class="question-tr even-quest-row" style="font-style: italic;">'+
					'<td style="width: 8%;">Questions : </td>'+
					'<td style="font-weight: 600;">'+questions[i]["question"]+'</td>'+
				'</tr>'+
				'<tr class="answer-tr even-ans-row">'+
					'<td style="font-style: italic;">Answer : </td>'+
					'<td>'+questions[i]["answer"]+'</td>'+
				'</tr>'		
			);
		}else{
			$('#question-answer-table tbody').append(
					'<tr class="question-tr odd-quest-row" style="font-style: italic;">'+
						'<td style="width: 8%;">Questions : </td>'+
						'<td style="font-weight: 600;">'+questions[i]["question"]+'</td>'+
					'</tr>'+
					'<tr class="answer-tr odd-ans-row">'+
						'<td style="font-style: italic;">Answer : </td>'+
						'<td>'+questions[i]["answer"]+'</td>'+
					'</tr>'		
			);
		}
		
	}
}

var populateSimilarQuestions = function(questions){
	
	for(var i = 0; i<questions.length; i++){
		if(i % 2 == 0){
			$('#similar-question-answer-table tbody').append(
				'<tr class="question-tr even-quest-row" style="font-style: italic;">'+
					'<td style="width: 8%;">Questions : </td>'+
					'<td style="font-weight: 600;">'+questions[i]["question"]+'</td>'+
				'</tr>'+
				'<tr class="answer-tr even-ans-row">'+
					'<td style="font-style: italic;">Answer : </td>'+
					'<td>'+questions[i]["answer"]+'</td>'+
				'</tr>'		
			);
		}else{
			$('#similar-question-answer-table tbody').append(
					'<tr class="question-tr odd-quest-row" style="font-style: italic;">'+
						'<td style="width: 8%;">Questions : </td>'+
						'<td style="font-weight: 600;">'+questions[i]["question"]+'</td>'+
					'</tr>'+
					'<tr class="answer-tr odd-ans-row">'+
						'<td style="font-style: italic;">Answer : </td>'+
						'<td>'+questions[i]["answer"]+'</td>'+
					'</tr>'		
			);
		}
		
	}
	
	
}
