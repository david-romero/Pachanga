angular.module('pachanga').controller('PartidoController', 
		[ '$scope', '$http' ,
		  	function($scope, $http) {
				 $scope.activeTab = 1;
				 $scope.getProfileTabCss = function(tab){
					var cssClass = "";
					if ( tab == $scope.activeTab ){
						cssClass = "active";
					}
					return cssClass;
				}
				 
				 $scope.setFormScope= function(scope){
					   this.formScope = scope;
					} 
				 
				$scope.savePartido = function(form){
					console.log(form);
					var precio = $scope.precio
					alert(precio);
					var plazas = $scope.plazas
					alert(plazas)
					$http.post(
							  '/P/rest/partido/save'
						    )
				  .success(function(data) {
					  console.log(data)
				   })
				  .error(function(data, status, headers, config) {
					    // called asynchronously if an error occurs
					    // or server returns response with an error status.
					  console.log(data)
				   });
				}
				
				$scope.getPartidosComunidad = function(idComunidad){
					$scope.partidos = [];
					$http.get('/P/rest/comunidad/partidos/' + idComunidad).success(function(data) {
						console.log(data);
						for (var i=0; i<data.length; i++){
							$scope.partidos.push(data[i])
						}
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
					console.log(partido.fecha);
					console.log(new Date().getTime());
					return partido.fecha >= new Date().getTime()
				}
				
				$scope.isFull = function(partido){
					console.log("jugadores " +partido.jugadores.length);
					console.log("plazas " +partido.plazas);
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
					 $http.post('/P/rest/partido/eliminarJugador/'+idPartido+"/"+idJugador)
					     .success(function(data) {
							console.log(data);
							//TODO - DRA Iterar sobre los jugadores del partido y eliminar para refrescar UI
						}).error(function(data) {
							console.log(data);
						});
				 }
				 
				 
				    
				
				 $scope.uploadImagen = function() {
				        var fd = new FormData()
				        for (var i in $scope.files) {
				            fd.append("foto", $scope.files[i])
				        }
				        var xhr = new XMLHttpRequest()
				        xhr.upload.addEventListener("progress", uploadProgress, false)
				        xhr.addEventListener("load", uploadComplete, false)
				        xhr.addEventListener("error", uploadFailed, false)
				        xhr.addEventListener("abort", uploadCanceled, false)
				        xhr.open("POST", "/P/rest/partido/editImage/5")
				        $scope.progressVisible = true
				        xhr.send(fd)
				    }

				    function uploadProgress(evt) {
				    	$scope.$apply(function(){
				            if (evt.lengthComputable) {
				            	$scope.progress = Math.round(evt.loaded * 100 / evt.total)
				            } else {
				            	$scope.progress = 'unable to compute'
				            }
				        })
				    }

				    function uploadComplete(evt) {
				        /* This event is raised when the server send back a response */
				        alert(evt.target.responseText)
				    }

				    function uploadFailed(evt) {
				        alert("There was an error attempting to upload the file.")
				    }

				    function uploadCanceled(evt) {
				    	$scope.$apply(function(){
				    		$scope.progressVisible = false
				        })
				        alert("The upload has been canceled by the user or the browser dropped the connection.")
				    }
				
			}
		]
);