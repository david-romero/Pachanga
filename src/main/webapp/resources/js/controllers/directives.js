/**
 * 
 */
angular.module('pachanga').directive('niceScroll', function() {
    return{
        restrict: 'A',
        link: function(scope, element, attribute) {

            var nicescrolConf = {
                "cursorcolor": "#bdbdbd",
                "background": "#ffffff",
                "cursorwidth": "5px",
                "cursorborder": "none",
                "cursorborderradius": "2px",
                "zindex": 1,
                "autohidemode": false
            };

           element.niceScroll(nicescrolConf);
        }
    };
});
angular.module('pachanga').directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

angular.module('pachanga').directive('scroll', function($timeout) {
	  return {
	    restrict: 'A',
	    link: function(scope, element, attr) {
	      scope.$watchCollection(attr.scroll, function(newVal) {
	        $timeout(function() {
	         
	         element[0].scrollTop = element[0].scrollHeight;
	        });
	      });
	    }
	  }
	});

angular.module('pachanga').directive(
		'searchBar', function(){
			return {
			    restrict: 'A',
			    scope : {
			    	busqueda : '='
			    },
			    template: '<div class="form-group">' +
			    		  	'<input type="text" ' +
			    		  		'class="form-control"' +
			    		  		'placeholder="Introduce tu texto a buscar"' +
			    		  		'value={{busqueda}}' +
			    		  '</div>'
			    		  
			}
});


// =========================================================================
// Curved Line Chart 
// =========================================================================

angular.module('pachanga').directive('curvedlineChart', function(){
    return {
        restrict: 'A',
        link: function(scope, element) {
            
            /* Make some random data for the Chart*/

            var d1 = [];
            var d2 = [];
            var d3 = [];
            
            for (var i = 0; i <= 10; i += 1) {
                d1.push([i, parseInt(Math.random() * 30)]);
            }
            
            for (var i = 0; i <= 20; i += 1) {
                d2.push([i, parseInt(Math.random() * 30)]);
            }    
            
            for (var i = 0; i <= 10; i += 1) {
                d3.push([i, parseInt(Math.random() * 30)]);
            }

            
            /* Chart Options */

            var options = {
                series: {
                    shadowSize: 0,
                    curvedLines: { //This is a third party plugin to make curved lines
                        apply: true,
                        active: true,
                        monotonicFit: true
                    },
                    lines: {
                        show: false,
                        lineWidth: 0,
                    },
                },
                grid: {
                    borderWidth: 0,
                    labelMargin:10,
                    hoverable: true,
                    clickable: true,
                    mouseActiveRadius:6,

                },
                xaxis: {
                    tickDecimals: 0,
                    ticks: false
                },

                yaxis: {
                    tickDecimals: 0,
                    ticks: false
                },

                legend: {
                    show: false
                }
            };

            /* Let's create the chart */

            $.plot($(element), [
                {data: d1, lines: { show: true, fill: 0.98 }, label: 'Product 1', stack: true, color: '#e3e3e3' },
                {data: d3, lines: { show: true, fill: 0.98 }, label: 'Product 2', stack: true, color: '#f1dd2c' }
            ], options);

            /* Tooltips for Flot Charts */

            if ($(".flot-chart")[0]) {
                $(".flot-chart").bind("plothover", function (event, pos, item) {
                    if (item) {
                        var x = item.datapoint[0].toFixed(2),
                            y = item.datapoint[1].toFixed(2);
                        $(".flot-tooltip").html(item.series.label + " of " + x + " = " + y).css({top: item.pageY+5, left: item.pageX+5}).show();
                    }
                    else {
                        $(".flot-tooltip").hide();
                    }
                });

                $("<div class='flot-tooltip' class='chart-tooltip'></div>").appendTo("body");
            }
        }
    }
})


    // =========================================================================
    // SPARKLINE CHARTS
    // =========================================================================
    
    //Bar Chart

angular.module('pachanga').directive('sparklineBar', function(){

        return {
            restrict: 'A',
            link: function(scope, element) {
                function sparkLineBar(selector, values, height, barWidth, barColor, barSpacing) {
                   $(selector).sparkline(values, {
                        type: 'bar',
                        height: height,
                        barWidth: barWidth,
                        barColor: barColor,
                        barSpacing: barSpacing
                   });
                }
                var datos = [];
                console.log(element);
                var id = element[0].id;
                if ( element[0].attributes[0].value != undefined && element[0].attributes[0].value != "" && element[0].attributes[0].value.length > 0 ){
                	datos = JSON.parse(element[0].attributes[0].value)
                }else{
                	datos = [9,4,6,5,6,4,5,7,9,3,6,5];
                }
                sparkLineBar('#'+id, datos, '45px', 3, '#fff', 2);
                //sparkLineBar('.stats-bar-2', [4,7,6,2,5,3,8,6,6,4,8,6,5,8,2,4,6], '45px', 3, '#fff', 2);
            }
        }
    });

//Line Chart

angular.module('pachanga').directive('sparklineLine', function(){

    return {
        restrict: 'A',
        link: function(scope, element) {
            function sparkLineLine(selector, values, width, height, lineColor, fillColor, lineWidth, maxSpotColor, minSpotColor, spotColor, spotRadius, hSpotColor, hLineColor) {
                $(selector).sparkline(values, {
                    type: 'line',
                    width: width,
                    height: height,
                    lineColor: lineColor,
                    fillColor: fillColor,
                    lineWidth: lineWidth,
                    maxSpotColor: maxSpotColor,
                    minSpotColor: minSpotColor,
                    spotColor: spotColor,
                    spotRadius: spotRadius,
                    highlightSpotColor: hSpotColor,
                    highlightLineColor: hLineColor
                });
            }
            var datos = [];
            var id = element[0].id;
            if ( element[0].attributes[0].value != undefined && element[0].attributes[0].value != "" && element[0].attributes[0].value.length > 0 ){
            	datos = JSON.parse(element[0].attributes[0].value)
            }else{
            	datos = [9,4,6,5,6,4,5,7,9,3,6,5];
            }
            if ( id == "errores-sparkline" || id == "others-sparkline" ){
            	sparkLineLine('#'+id, datos , 85, 45, '#fff', 'rgba(0,0,0,0)', 1.25, 'rgba(255,255,255,0.4)', 'rgba(255,255,255,0.4)', 'rgba(255,255,255,0.4)', 3, '#fff', 'rgba(255,255,255,0.4)');
            }else if ( id == "visitantes-sparkline" ){
            	sparkLineLine('#'+id, datos, '100%', '95px', 'rgba(255,255,255,0.7)', 'rgba(0,0,0,0)', 2, 'rgba(255,255,255,0.4)', 'rgba(255,255,255,0.4)', 'rgba(255,255,255,0.4)', 5, 'rgba(255,255,255,0.4)', '#fff');
            }
            //sparkLineLine('.stats-line', datos , 85, 45, '#fff', 'rgba(0,0,0,0)', 1.25, 'rgba(255,255,255,0.4)', 'rgba(255,255,255,0.4)', 'rgba(255,255,255,0.4)', 3, '#fff', 'rgba(255,255,255,0.4)');
            //sparkLineLine('.stats-line-2', [5,6,3,9,7,5,4,6,5,6,4,9], 85, 45, '#fff', 'rgba(0,0,0,0)', 1.25, 'rgba(255,255,255,0.4)', 'rgba(255,255,255,0.4)', 'rgba(255,255,255,0.4)', 3, '#fff', 'rgba(255,255,255,0.4)');
            

        }
    }
});

//=========================================================================
// EASY PIE CHARTS
// =========================================================================

angular.module('pachanga').directive('easypieChart', function(){
    return {
        restrict: 'A',
        link: function(scope, element) {
            function easyPieChart(selector, trackColor, scaleColor, barColor, lineWidth, lineCap, size) {
                $(selector).easyPieChart({
                    trackColor: trackColor,
                    scaleColor: scaleColor,
                    barColor: barColor,
                    lineWidth: lineWidth,
                    lineCap: lineCap,
                    size: size
                });
            }

            easyPieChart('.main-pie', 'rgba(255,255,255,0.2)', 'rgba(255,255,255,0.5)', 'rgba(255,255,255,0.7)', 7, 'butt', 148);
            easyPieChart('.sub-pie-1', '#eee', '#ccc', '#2196F3', 4, 'butt', 95);
            easyPieChart('.sub-pie-2', '#eee', '#ccc', '#FFC107', 4, 'butt', 95);
        }
    }
})

	// =========================================================================
    // Regular Line Charts
    // =========================================================================
    
    angular.module('pachanga').directive('lineChart', function(){
        return {
            restrict: 'A',
            link: function(scope, element){
                
            	//DRA - Obteniendo datos desde la BBDD
            	var id = element[0].id;
            	var datos = []
            	var totalPoints = 100;
                var updateInterval = 30;
                
                if ( element[0].attributes[1].value != undefined && element[0].attributes[1].value != "" && element[0].attributes[1].value.length > 0 ){
                	var aux = JSON.parse(element[0].attributes[1].value);
                	for ( var i = 0; i < aux.length; i++ ){
                		datos.push([i,aux[i]])
                	}
                }else{
                	datos = getRandomData();
                }
            	
            	
                /* Make some random data for Recent Items chart */

                
                

                function getRandomData() {
                    if (datos.length > 0)
                    	datos = datos.slice(1);

                    while (datos.length < totalPoints) {

                        var prev = datos.length > 0 ? datos[datos.length - 1] : 50,
                            y = prev + Math.random() * 10 - 5;
                        if (y < 0) {
                            y = 0;
                        } else if (y > 90) {
                            y = 90;
                        }

                        datos.push(y);
                    }

                    var res = [];
                    for (var i = 0; i < datos.length; ++i) {
                        res.push([i, datos[i]])
                    }

                    return res;
                }

                /* Make some random data for Flot Line Chart */

                var d1 = [];
                var d2 = [];
                var d3 = [];
                
                for (var i = 0; i <= 10; i += 1) {
                    d1.push([i, parseInt(Math.random() * 30)]);
                }
                
                for (var i = 0; i <= 20; i += 1) {
                    d2.push([i, parseInt(Math.random() * 30)]);
                }    
                
                for (var i = 0; i <= 10; i += 1) {
                    d3.push([i, parseInt(Math.random() * 30)]);
                }

                /* Chart Options */

                var options = {
                    series: {
                        shadowSize: 0,
                        lines: {
                            show: false,
                            lineWidth: 0,
                        },
                    },
                    grid: {
                        borderWidth: 0,
                        labelMargin:10,
                        hoverable: true,
                        clickable: true,
                        mouseActiveRadius:6,

                    },
                    xaxis: {
                        tickDecimals: 0,
                        ticks: false
                    },

                    yaxis: {
                        tickDecimals: 0,
                        ticks: false
                    },

                    legend: {
                        show: false
                    }
                };

                /* Regular Line Chart */
                if ($("#line-chart")[0]) {
                    $.plot($("#line-chart"), [
                        {data: d1, lines: { show: true, fill: 0.98 }, label: 'Product 1', stack: true, color: '#e3e3e3' },
                        {data: d3, lines: { show: true, fill: 0.98 }, label: 'Product 2', stack: true, color: '#FFC107' }
                    ], options);
                }

                /* Recent Items Table Chart */
                if ($("#recent-items-chart")[0]) {
                    $.plot($("#recent-items-chart"), [
                        {data: datos, lines: { show: true, fill: 0.8 }, label: 'Partidos', stack: true, color: '#00BCD4' },
                    ], options);
                }
            }
        }
    });

//-----------------------------------------------
// PIE AND DONUT
//-----------------------------------------------

angular.module('pachanga').directive('pieDonut', function(){
    return {
        restrict: 'A',
        link: function(scope, element, attrs){
            var pieData = [
                {data: 1, color: '#F44336', label: 'Toyota'},
                {data: 2, color: '#03A9F4', label: 'Nissan'},
                {data: 3, color: '#8BC34A', label: 'Hyundai'},
                {data: 4, color: '#FFEB3B', label: 'Scion'},
                {data: 4, color: '#009688', label: 'Daihatsu'},
            ];

            /* Pie Chart */

            if($('#pie-chart')[0]){
                $.plot('#pie-chart', pieData, {
                    series: {
                        pie: {
                            show: true,
                            stroke: { 
                                width: 2,
                            },
                        },
                    },
                    legend: {
                        container: '.flc-pie',
                        backgroundOpacity: 0.5,
                        noColumns: 0,
                        backgroundColor: "white",
                        lineWidth: 0
                    },
                    grid: {
                        hoverable: true,
                        clickable: true
                    },
                    tooltip: true,
                    tooltipOpts: {
                        content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
                        shifts: {
                            x: 20,
                            y: 0
                        },
                        defaultTheme: false,
                        cssClass: 'flot-tooltip'
                    }

                });
            }

            /* Donut Chart */

            if($('#donut-chart')[0]){
                $.plot('#donut-chart', pieData, {
                    series: {
                        pie: {
                            innerRadius: 0.5,
                            show: true,
                            stroke: { 
                                width: 2,
                            },
                        },
                    },
                    legend: {
                        container: '.flc-donut',
                        backgroundOpacity: 0.5,
                        noColumns: 0,
                        backgroundColor: "white",
                        lineWidth: 0
                    },
                    grid: {
                        hoverable: true,
                        clickable: true
                    },
                    tooltip: true,
                    tooltipOpts: {
                        content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
                        shifts: {
                            x: 20,
                            y: 0
                        },
                        defaultTheme: false,
                        cssClass: 'flot-tooltip'
                    }

                });
            }
        }
    }
})


