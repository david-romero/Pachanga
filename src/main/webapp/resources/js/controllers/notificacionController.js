/**
 * 
 */
angular.module('pachanga').controller('NotificacionController', 
		[ '$scope', '$http' , '$timeout' , 'notificacionService' ,
		  function($scope, $http,  $timeout, notificacionService) {
				//Atributos
				$scope.notificaciones = [];
				$scope.notificacionesALeer = [];
				$scope.selected = [];
				$scope.numeroNotificaciones = 0;
				
				var recibirMensaje = function(event) {
	            	$scope.$apply(function () {
			        	var notificaciones = JSON.parse(event.data)
			        	
			        	for (var i=0; i< notificaciones.length; i++){
			        		var notificacionNueva = notificaciones[i]
			        		var esNueva = true;
			        		for ( var ii = 0; ii < $scope.notificacionesALeer.length ; ii++ ){
			        			var notificacionEnScope = $scope.notificacionesALeer[ii];
			        			if ( notificacionEnScope.id = notificacionNueva.id ){
				        			esNueva = false;
				        		}
			        		}
			        		if ( esNueva ){
			        			$scope.notificacionesALeer.push(notificacionNueva);
			        			$scope.numeroNotificaciones = $scope.numeroNotificaciones+1;
			        		}
			        		
			        	}
		            });
	            };
	            
	            var errorAlRecibirMensaje = function(event) {
	            	console.log("Problemas de comunicación");
	            }
				
				$timeout(function() {
					var source = new EventSource('/P/notificaciones/alertas');
		            /* handle incoming messages */
		            source.onmessage = recibirMensaje;
		            
		            source.onerror = errorAlRecibirMensaje;

		            
				}, 10000);
		        

				$scope.existenNotificaciones = function() {
					return $scope.numeroNotificaciones > 0;
				}
				
				$scope.addToSelected = function(notificacion){
					$scope.selected.push(notificacion);
				}
				
				$scope.eliminarSeleccionadas = function(){
					notificacionService.eliminarNotificaciones($scope.selected)
					.then(function(data) {
						notify('Eliminadas ' + $scope.selected.length + ' notificaciones'  , 'inverse');
						$scope.selected = [];
				    })
				    .catch(function(error) {
				    	console.log(error);
				    });
				}
				
				$scope.leerSeleccionadas = function(){
					notificacionService.marcarComoLeidas($scope.selected)
					.then(function(data) {
						$scope.selected = [];
				    })
				    .catch(function(error) {
				    	console.log(error);
				    });
				}
				
				$scope.loadNotificacionesInicio = function() {
					notificacionService.loadNotificaciones()
					.then(function(data) {
						$scope.numeroNotificaciones = data.length;
						for (var i=0; i<data.length; i++){
							$scope.notificacionesALeer.push(data[i])
						}
						
				    })
				    .catch(function(error) {
				    	console.log(error);
				    });
				}
				
				$scope.leerTodasNoLeidas = function(){
					notificacionService.leerTodasNoLeidas()
					.then(function() {
						$scope.numeroNotificaciones = 0;
				    })
				    .catch(function(error) {
				    	console.log(error);
				    });
				};
				
				$scope.getCssListViewNotificaciones = function(){
					var css = '';
					if ( $scope.numeroNotificaciones == 0 ){
						css = 'empty';
					}
					return css;
				};
				
				
				$scope.loadAllNotificacionesSinLeer = function() {
					$scope.notificaciones = [];
					$timeout(function() {
						notificacionService.loadAllNotificacionesSinLeer()
						.then(function(data) {
							for (var i=0; i<data.length; i++){
								$scope.notificaciones.push(data[i])
							}
					    })
					    .catch(function(error) {
					    	console.log(error);
					    });
					} , 1000 );
				}
				

			
			}
		]
);
//Bootstrap Growl 
function notify(message, type){
	jQuery.growl({
        message: message
    },{
        type: type,
        allow_dismiss: false,
        label: 'Cancel',
        className: 'btn-xs btn-inverse',
        placement: {
            from: 'top',
            align: 'right'
        },
        delay: 2500,
        animate: {
                enter: 'animated bounceIn',
                exit: 'animated bounceOut'
        },
        offset: {
            x: 20,
            y: 85
        }
    });
};
