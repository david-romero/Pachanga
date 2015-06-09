angular.module('pachanga').controller('PartidoController', 
		[ '$scope', '$http' , "partidoService" ,
		  	function($scope, $http, partidoService) {
				 $scope.activeTab = 1;
				 $scope.getProfileTabCss = function(tab){
					var cssClass = "";
					if ( tab == $scope.activeTab ){
						cssClass = "active";
					}
					return cssClass;
				}
				 
				 $scope.setFormScope= function(scope){
					   $scope.formScope = scope;
					} 
				 
				$scope.savePartido = function(form){
					console.log($scope.formScope);
					var precio = $scope.formScope.precio
					var titulo = $scope.formScope.titulo;
					var categoria = $scope.formScope.categoria;
					var plazas = $scope.formScope.plazas
					var fecha = $scope.formScope.fecha;
					var propietario = $scope.formScope.propietario;
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
				
				$scope.getPartidosComunidad = function(idComunidad){
					$scope.partidos = [];
					partidoService.getPartidosComunidad(idComunidad).then(function(data) {
						for (var i=0; i<data.length; i++){
							$scope.partidos.push(data[i])
						}
				    })
				    .catch(function(error) {
				    	console.log(error);
				    	notify('Se ha producido un error obteniendo los partidos de la comunidad', 'inverse');
				    });
				}
				
				$scope.comprobarSiEstaApuntado = function(partido,emailUsuarioLogueado){
					var inscrito = false;
					for ( var i=0; i<partido.jugadores.length; i++ ){
						if ( partido.jugadores[i].email == emailUsuarioLogueado ){
							inscrito = true;
						}
					}
					return inscrito;
				}
				
				$scope.isInDate = function(partido){
					return partido.fecha >= new Date().getTime()
				}
				
				$scope.isFull = function(partido){
					return ( partido.jugadores.length == partido.plazas );
				}
				
				$scope.setFiles = function(element) {
				    $scope.$apply(function(scope) {
				      console.log('files:', element.files);
				      // Turn the FileList object into an Array
				      $scope.files = []
				        for (var i = 0; i < element.files.length; i++) {
				          scope.files.push(element.files[i])
				        }
				      $scope.progressVisible = false
				      
				      });
				    $scope.uploadImagen()
				 };
				 
				 $scope.eliminarJugador = function(idPartido,idJugador){					 
					 partidoService.eliminarJugador(idPartido,idJugador)
				        .then(function(data) {
				        	console.log(data);
				        	console.log($scope);
							//TODO - DRA Iterar sobre los jugadores del partido y eliminar para refrescar UI
				        	for ( var indice = 0; indice < $scope.partidos; indice++ ){
						    	  var partido = $scope.partidos[indice]
						    	  console.log(partido);
						      }
					    })
					    .catch(function(error) {
					    	console.log(error);
					    	notify('Se ha producido un error eliminando al jugador', 'inverse');
					    });
				 }
				  
				
				 $scope.uploadImagen = function() {
					 var fd = new FormData()
				        for (var i in $scope.files) {
				            fd.append("foto", $scope.files[i])
				        }
				        
					 partidoService.uploadImage(fd)
				        .then(function(data) {
				        	$scope.urlProfile='/P/usuarios/getUserImage/' + data.id + "?" + new Date().getTime()
					    })
					    .catch(function(error) {
					    	console.log(error);
					    	notify('Se ha producido un error subiendo la imagen', 'inverse');
					    });
				}
				 
				 $scope.apuntarseAPartido = function(partidoId){
					 partidoService.apuntarse(partidoId)
					    .then(function(data) {
					    	console.log($scope);
					      for ( var indice = 0; indice < $scope.partidos; indice++ ){
					    	  var partido = $scope.partidos[indice]
					    	  console.log(partido);
					      }
					    })
					    .catch(function(error) {
					    	console.log(error);
					    	notify('Se ha producido un error al apuntarse al partido', 'inverse');
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