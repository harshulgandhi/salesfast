/**
 * 
 */
var chartData = [
	                ['Is Medicine Effective',   40.2],
	                ['Does it have side effects',   27.4],
	                ['Is it affordable', 33.4]
	            ];

$(document).on('click','button.show-analysis',function(){
	createChart('#container-sample', chartData);
});


createChart=function(containerName, chartData){//}, title, data){
    // Build the chart
	$(containerName).highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: 0,
	            plotShadow: false
	        },
	        title: {
	            text: 'Browser<br>shares<br>2015',
	            align: 'center',
	            verticalAlign: 'middle',
	            y: 40
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                dataLabels: {
	                    enabled: true,
	                    distance: -30,
	                    style: {
	                        fontWeight: 'bold',
	                        color: 'black'
	                        
	                    }
	                },
	                startAngle: -90,
	                endAngle: 90,
	                center: ['50%', '75%']
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: 'Browser share',
	            innerSize: '50%',
	            data:chartData
	        }]
	    });
		$('.analysis-right-panel').slideToggle('fast');
}