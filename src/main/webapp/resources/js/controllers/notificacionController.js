/**
 * 
 */
angular.module('pachanga').controller('NotificacionController', 
		[ '$rootScope' ,'$scope', '$http' , '$timeout' , 'notificacionService' ,
		  function($rootScope, $scope, $http,  $timeout, notificacionService) {
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
			        			if ( notificacionEnScope.id == notificacionNueva.id ){
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
					if ( $rootScope.source != undefined ){
						$rootScope.source.addEventListener('notificaciones', function(e) {
							recibirMensaje(e);
							}, false);
					}else{
						console.log($rootScope);
						console.log("EventSource is not initialized");
					}
		            
				}, 10000);
		        

				$scope.existenNotificaciones = function() {
					return $scope.numeroNotificaciones > 0;
				}
				
				$scope.addToSelected = function(notificacion){
					$scope.selected.push(notificacion);
				}
				
				$scope.actualizarNotificacionesALeer = function(){
					for ( var i = 0; i < $scope.selected.length; i++ ){
						for ( var ii = 0; ii < $scope.notificacionesALeer.length; ii++ ){
							if ( $scope.selected[i].id == $scope.notificacionesALeer[ii].id ){
								$scope.notificacionesALeer.slice(ii);
								$scope.numeroNotificaciones = $scope.numeroNotificaciones-1;
							}
						}
					}
				}
				
				
				$scope.eliminarSeleccionadas = function(){
					notificacionService.eliminarNotificaciones($scope.selected)
					.then(function(data) {
						notify('Eliminadas ' + $scope.selected.length + ' notificaciones'  , 'inverse');
						$scope.actualizarNotificacionesALeer();
						$scope.selected = [];
				    })
				    .catch(function(error) {
				    	console.log(error);
				    });
				}
				
				$scope.leerSeleccionadas = function(){
					notificacionService.marcarComoLeidas($scope.selected)
					.then(function(data) {
						$scope.actualizarNotificacionesALeer();
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
						$scope.actualizarNotificacionesALeer();
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
					notificacionService.loadAllNotificacionesSinLeer()
					.then(function(data) {
						for (var i=0; i<data.length; i++){
							$scope.notificaciones.push(data[i])
						}
				    })
				    .catch(function(error) {
				    	console.log(error);
				    });
					
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
