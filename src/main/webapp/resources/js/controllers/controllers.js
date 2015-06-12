
angular.module('pachanga').controller('InitController', [ '$scope', '$http' , '$timeout' , 'partidoService',

	function($scope, $http, $timeout, partidoService) {
		$scope.pagina = 1;
		$scope.userSigned = new Object()
		$scope.userSigned.id = 0;
		$scope.hayMasPaginas = true;
		$scope.inicio = function() {
			$scope.partidos = [];
			$scope.partidosJugados = [];
			$http.get('/P/rest/partido/inicio').success(function(data) {
				console.log(data);
				for (var i=0; i<data.length; i++){
					$scope.partidos.push(data[i])
				}
			});
			$timeout(function() {
				$scope.partidosJugados = [];
				partidoService.refreshPartidosJugados()
			    .then(function(data) {
			    	for (var i=0; i<data.length; i++){
						$scope.partidosJugados.push(data[i])
					}
			    }).catch(function(error) {
			    	console.log(error);
			    	notify('Error obteniendo tus partidos' , 'inverse');
			    });
		    }, 500);
			
		}
	
		$scope.loadMorePartidos = function() {
			partidoService.loadMorePartidos($scope.pagina+1)
		    .then(function(data) {
		    	$scope.showMorePartidos(data);
				$scope.pagina = $scope.pagina + 1;
		    }).catch(function(error) {
		    	console.log(error);
		    	notify('Algo no está funcionando correctamente... :(' , 'inverse');
		    });
		}
		
		$scope.apuntarseAPartido = function(partidoId){
			  partidoService.apuntarse(partidoId)
			    .then(function(partido) {
			    	for (var i=0; i<$scope.partidos.length; i++){
						  var partidoEnLista = $scope.partidos[i]
						  if ( partidoEnLista.id == partido.id ){
							  $scope.partidos[i] = partido;
							  break;
						  }
					}
			    	if ( $scope.partidosJugados.length < 5 ){
			    		$scope.partidosJugados.push(partido);
			    	}
			    	notify('Te has apuntado a ' + partido.titulo , 'inverse');
			    })
			    .catch(function(error) {
			    	notify('Error apuntandote a un partido' , 'inverse');
			    });
			  
		}
	
		$scope.showMorePartidos = function(data) {
			for (var i=0; i<data.length; i++){
				$scope.partidos.push(data[i])
			}
			if ( data.length < 4 ){
				$scope.hayMasPaginas = false;
			}else{
				$scope.hayMasPaginas = true;
			}
		};
		
		$scope.showPartido = function(partidoId){
			window.location.href = '/P/partido/show/' + partidoId
		};
		
		$scope.showGrupo = function(grupoId){
			window.location.href = '/P/grupo/show/' + grupoId
		};
		
		$scope.getCssLoadMoreButton = function(){
			var css = ''
			if ( !$scope.hayMasPaginas ){
				css = 'no-active'
			}
			return css;
		};
		
		$scope.refreshPartidosJugados = function(){
			$scope.partidosJugados = [];
			partidoService.refreshPartidosJugados()
		    .then(function(data) {
		    	for (var i=0; i<data.length; i++){
					$scope.partidosJugados.push(data[i])
				}
		    }).catch(function(error) {
		    	notify('Error obteniendo tus partidos' , 'inverse');
		    });
		}
		
		$scope.setFormScope= function(scope){
			   $scope.formScope = scope;
			} 
		 
		$scope.savePartido = function(){
			var precio = $scope.formScope.precio
			var titulo = $scope.formScope.titulo;
			var categoria = $scope.formScope.categoriaTitulo;
			var plazas = $scope.formScope.plazas
			var fecha = $scope.formScope.fecha;
			var propietario = $scope.formScope.propietario;
			var publico = $scope.formScope.publico;
			if ( precio == undefined || titulo == undefined || fecha == undefined  || categoria == undefined){
				notify('Para crear un partido debes introducir un precio, un t&iacutetulo, una fecha y el deporte :|' , 'inverse');
			}else{
				var partido = new Object()
				partido.categoria = categoria;
				partido.titulo = titulo;
				partido.plazas = plazas;
				partido.precio = precio;
				partido.fecha = fecha;
				partido.propietario = propietario;
				partido.publico = ( publico != undefined ? publico : false )
				partidoService.save(partido)
				    .then(function(partido) {
				    	if ( partido.publico ){
				    		$scope.partidos.push(partido)
				    	}
				    	notify('¡Tu partido ha sido creado! A jugar! :)' , 'inverse');
				    		
				    })
				    .catch(function(error) {
				    	console.log(error);
				    	notify('Se ha producido un error creando tu partido... :(' , 'inverse');
				    });
			}
		}
		
		$scope.isInDate = function(partido){
			return Date.parse(partido.fecha) >= new Date().getTime();
		}
		
		$scope.isFull = function(partido){
			return ( partido.jugadores.length == partido.plazas );
		}
		
		$scope.isPropio = function(partido){
			return ( partido.propietario.id == $scope.userSigned.id );
		}
		
		$scope.comprobarSiEstaApuntado = function(partido,idUsuario){
			var inscrito = false;
			for ( var i=0; i<partido.jugadores.length; i++ ){
				if ( partido.jugadores[i].id == idUsuario ){
					inscrito = true;
				}
			}
			return inscrito;
		}
	} 
]);
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
