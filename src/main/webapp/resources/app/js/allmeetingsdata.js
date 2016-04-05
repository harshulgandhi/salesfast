/**
 * 
 */

var chartParameters = [{
                    	  title: 'Physician and SalesReps response',
                    	  containerName: '#container-overall',
	                      url: '/getDataOverall'
                      },
                      {
                    	  title: 'According to physicians',
                    	  containerName: '#container-phy-resp',
                    	  url: '/getPhyResp'
                      },
                      {
                    	  title: 'According to SalesReps',
                    	  containerName: '#container-salesrep-resp',
                    	  url: '/getSalesRepResp'
                      },
                      {
                    	  title: 'Probable reason for physician being lost',
                    	  containerName: '#container-phy-lost',
                    	  url: '/getStatusLostData'
                      }]
$(function () {
	
    $(document).ready(function () {
       $('li.navbar-menu-selected').removeClass("navbar-menu-selected");
    	   
	   if(!$('li#nav-data-report').hasClass("navbar-menu-selected")){
		   $('li#nav-data-report').addClass("navbar-menu-selected")
	   }
    	for(var i = 0; i< chartParameters.length; i++){
    		constructPieCharts(chartParameters[i]['url'],chartParameters[i]['containerName'], chartParameters[i]['title']);
    	}
    	
    	updateNotificationCounter();
    });
});

constructPieCharts = function(url, containerName, title){
	var dataReceived = null;
	$.ajax({
		type: 'GET',
		url : url,
		dataType : 'json',
		success : function(data){
	    	$.each(data, function(k, v) {
	    		if(data[0]["y"] == "NaN"){
		    		for(var i = 0; i<data.length; i++){
		    			data[i]["y"] = 0.0;
		    		}
	    		}
    		});
	    	console.log("Data received : "+JSON.stringify(data));
	    	createChart(containerName, title, data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
	
	updateNotificationCounter();
}


createChart=function(containerName, title, data){
    // Build the chart
	console.log("Container Name : "+containerName+", Title : "+title+", data : "+data);
    $(containerName).highcharts({
    	chart: {
            type: 'column'
        },
        title: {
            text: title
        },
        subtitle: {
            text: 'Meeting experience parameters'
        },
        xAxis: {
            type: 'category'
        },
        yAxis: {
            title: {
                text: 'Percentage out of all responses'
            }
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y:.1f}%'
                }
            }
        },

        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
        },

        series: [{
            name: 'Meeting experience responses',
            colorByPoint: true,
            data: data
        }]
        
    });
}


  