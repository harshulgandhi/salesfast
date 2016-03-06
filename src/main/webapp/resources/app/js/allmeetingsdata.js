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
//                    	  url: '/getPhyResp'
                    	  url: '/getDataOverall'
                      },
                      {
                    	  title: 'According to SalesReps',
                    	  containerName: '#container-salesrep-resp',
//                    	  url: '/getSalesRepResp'
                    	  url: '/getDataOverall'
                      },
                      {
                    	  title: 'Probable reason for physician being lost',
                    	  containerName: '#container-phy-lost',
//                    	  url: '/getStatusLostData'
                    	  url: '/getDataOverall'
                      }]
$(function () {
	
    $(document).ready(function () {
    	for(var i = 0; i< chartParameters.length; i++){
    		constructPieCharts(chartParameters[i]['url'],chartParameters[i]['containerName'], chartParameters[i]['title']);
    	}
    });
});

constructPieCharts = function(url, containerName, title){
	var dataReceived = null;
	$.ajax({
		type: 'GET',
		url : url,
		dataType : 'json',
		success : function(data){
	    	console.log("Data received : "+JSON.stringify(data));
	    	createChart(containerName, title, data);
		},
		error : function(e){
			console.log("Error : "+JSON.stringify(e));
		}
	}).done(function(){
		console.log("ajax complete!");
	});
	
	
}


createChart=function(containerName, title, data){
    // Build the chart
	console.log("Container Name : "+containerName+", Title : "+title+", data : "+data);
    $(containerName).highcharts({

        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: title
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            name: 'Brands',
            colorByPoint: true,
            data: data
        }]
    });
}
