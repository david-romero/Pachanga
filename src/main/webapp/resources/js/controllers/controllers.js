
angular.module('pachanga').controller('InitController', [ '$scope', '$http' , 'partidoService',

	function($scope, $http, partidoService) {
		$scope.pagina = 1;
		$scope.inicio = function() {
			$scope.partidos = [];
			$scope.partidosJugados = [];
			$http.get('/P/rest/partido/inicio').success(function(data) {
				console.log(data);
				for (var i=0; i<data.length; i++){
					$scope.partidos.push(data[i])
				}
			});
			$http.get('/P/rest/partido/jugados').success(function(data) {
				console.log(data);
				for (var i=0; i<data.length; i++){
					$scope.partidosJugados.push(data[i])
				}
			});
		}
	
		$scope.loadMorePartidos = function() {
			$http.get('/P/rest/partido/loadMorePartidos/'+($scope.pagina+1))
			.success(function(data) {
				$scope.showMorePartidos(data);
				$scope.pagina = $scope.pagina + 1;
			}).error(function(data) {
				console.log(data);
			});
		}
		
		$scope.apuntarseAPartido = function(partidoId){
			  $http.post('/P/rest/partido/apuntarse/'+partidoId)
			  .success(function(partido) {
				  alert("Alistado!");
			   })
			  .error(function(data, status, headers, config) {
				    // called asynchronously if an error occurs
				    // or server returns response with an error status.
			   });
		}
	
		$scope.showMorePartidos = function(data) {
			for (var i=0; i<data.length; i++){
				$scope.partidos.push(data[i])
			}
		}
		
		$scope.showPartido = function(partidoId){
			window.location.href = '/P/partido/show/' + partidoId
		}
		
		$scope.showGrupo = function(grupoId){
			window.location.href = '/P/grupo/show/' + grupoId
		}
		
		$scope.refreshPartidosJugados = function(){
			$scope.partidosJugados = [];
			$http.get('/P/rest/partido/jugados').success(function(data) {
				console.log(data);
				for (var i=0; i<data.length; i++){
					$scope.partidosJugados.push(data[i])
				}
			});
		}
		
		$scope.setFormScope= function(scope){
			   $scope.formScope = scope;
			} 
		 
		$scope.savePartido = function(){
			var precio = $scope.formScope.precio
			var titulo = $scope.formScope.titulo;
			var categoria = "Futbol";
			var plazas = $scope.formScope.plazas
			var fecha = $scope.formScope.fecha;
			var propietario = $scope.formScope.propietario;
			if ( precio == undefined || titulo == undefined || fecha == undefined ){
				alert("Error");
				console.log($scope.formScope);
			}else{
				var partido = new Object()
				partido.categoria = categoria;
				partido.titulo = titulo;
				partido.plazas = plazas;
				partido.precio = precio;
				partido.fecha = fecha;
				partido.propietario = propietario;
				partidoService.save(partido)
				    .then(function(data) {
				      $scope.partidos.push(data)
				    })
				    .catch(function(error) {
				    	console.log(error);
				    	alert("Error");
				    });
			}
		}
	} 
]);

