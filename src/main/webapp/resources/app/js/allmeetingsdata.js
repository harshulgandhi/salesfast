/**
 * 
 */
var dataReceived = null;
$(function () {
	
    $(document).ready(function () {
    	
    	$.ajax({
    		type: 'GET',
    		url : '/getdata',
    		dataType : 'json',
    		success : function(data){
    	    	console.log("Data received : "+JSON.stringify(data));
    	    	createChart1(data);
    	    	createChart2(data);
    		},
    		error : function(e){
    			console.log("Error : "+JSON.stringify(e));
    		}
    	});
    	
    });
});

createChart1=function(data){
    // Build the chart
    $('#container1').highcharts({

        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Browser market shares January, 2015 to May, 2015'
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

createChart2=function(data){
    // Build the chart
    $('#container2').highcharts({

        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Browser market shares January, 2015 to May, 2015'
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