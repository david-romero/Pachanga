/**
 * 
 */
angular.module('pachanga').controller('NotificacionController', 
		[ '$scope', '$http' ,
		  function($scope, $http) {
				$scope.notificaciones = [];
				$scope.paginas = [];
				$scope.numeroPaginas = 1;
				$scope.paginaActual = 1;
				$scope.loadNotificaciones = function(pagina) {
					$scope.notificaciones = [];
					$scope.paginaActual  = pagina;
					$http.get('/P/rest/notificacion/obtenerTodas/'+$scope.paginaActual).success(function(data) {
						for (var i=0; i<data.length; i++){
							$scope.notificaciones.push(data[i])
						}
						console.log(data);
					});
					$http.get('/P/rest/notificacion/obtenerNumeroPaginas').success(function(data) {
						$scope.numeroPaginas = parseInt(data);
						for (var i=0; i<$scope.numeroPaginas; i++){
							$scope.paginas.push(i);
						}
						console.log($scope.paginas);
					});
				}
				
				$scope.getPaginacionClass = function(pagina){
					var cssClass = "";
					if ( $scope.paginaActual = pagina ){
						cssClass = "active";
					}
					return cssClass;
				}
			
			}
		]
);