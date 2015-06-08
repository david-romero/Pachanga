
angular.module('pachanga').controller('InitController', [ '$scope', '$http',

	function($scope, $http) {
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
			$http.get('/P/rest/partido/loadMorePartidos').success(function(data) {
				$scope.showMorePartidos(data);
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
			console.log(data);
			$scope.partidos.push(data[0])
			$scope.partidos.push(data[1])
			$scope.partidos.push(data[2])
			$scope.partidos.push(data[3])
			alert("Funciona!!");
		}
		
		$scope.showPartido = function(partidoId){
			alert(partidoId);
			window.location.href = '/P/partido/show/' + partidoId
		}
		
		$scope.showGrupo = function(grupoId){
			window.location.href = '/P/grupo/show/' + grupoId
		}
	} 
]);

angular.module('pachanga').controller('MensajeController', [ '$scope', '$http' ,
	function($scope, $http) {
		$scope.conversacion = new Object();
		$scope.conversacion.receptor = "";
		$scope.conversacion.emisor = "";
		$scope.conversacion.mensajes = [];
		  $scope.loadContactos = function() {
			  $scope.usuarios = [];
			 
			  $scope.conversacion.mensajes = [];
			  $http.get('/P/rest/usuarios/inicio').success(function(data) {
					for (var i=0; i<data.length; i++){
						if ( i == 0 ){
							data[i].active=true;
							$scope.conversacion.receptor = data[i];
						}else{
							data[i].active=false;
						}
						var cssClass = avatarCss[Math.floor(Math.random() * 21) + 1];
						data[i].avatarCssClass = cssClass;
						$scope.usuarios.push(data[i])
					}
					$http.get('/P/rest/mensaje/conversacion/'+$scope.conversacion.receptor.id).success(function(data) {
						for (var i=0; i<data.length; i++){
							if ( i == 0 ){
								if ( data[i].emisor.id == $scope.conversacion.receptor.id  ){
									/*
									 * Si el emisor del mensaje es el usuario que esta establecido como receptor 
									 * entonces el usuario logueado es el receptor del mensaje en caso contrario
									 * el usuario logueado es el emisor
									 */
									$scope.conversacion.emisor = data[i].receptor;
								}else{
									$scope.conversacion.emisor = data[i].emisor;
								}
							}
							$scope.conversacion.mensajes.push(data[i])
						}
					});
				});
		  }
		  
		  $scope.getCssClass = function(usuario) {
			  return usuario.active;
		  }
		  
		  $scope.loadConversacion = function(usuario) {
			  for (var i=0; i<$scope.usuarios.length; i++){
				  $scope.usuarios[i].active = false;
			  }
			  usuario.active = true;
			  $scope.conversacion.receptor = usuario;
			  $scope.conversacion.mensajes = [];
			  $http.get('/P/rest/mensaje/conversacion/'+usuario.id).success(function(data) {
					for (var i=0; i<data.length; i++){
						$scope.conversacion.mensajes.push(data[i])
					}
				});
		  }
		  
		  var avatarCss = {
			        1: "bgm-red",
			        2: "bgm-white",
			        3: "bgm-black",
			        4: "bgm-brown",
			        5: "bgm-pink",
			        6: "bgm-blue",
			        7: "bgm-purple",
			        8: "bgm-deeppurple",
			        9: "bgm-lightblue",
			        10: "bgm-cyan",
			        11: "bgm-teal",
			        12: "bgm-green",
			        13: "bgm-lightgreen",
			        14: "bgm-lime",
			        15: "bgm-yellow",
			        16: "bgm-amber",
			        17: "bgm-orange",
			        18: "bgm-deeporange",
			        19: "bgm-gray",
			        20: "bgm-bluegray",
			        21: "bgm-indigo"
			      };
		  
		  $scope.getAvatarCssClass = function(usuario){
			  return usuario.avatarCssClass;
		  }	
		  
		  $scope.getMensajeCssClass = function(mensaje){
			  var cssClass= ""
			  if ( mensaje.emisor.id != $scope.conversacion.receptor.id ){
				  cssClass = "right";
			  }
			  return cssClass;
		  }
		  
		  $scope.getMensajeAvatarCssClass = function(mensaje){
			  var cssClass= "pull-left"
			  if ( mensaje.emisor.id != $scope.conversacion.receptor.id ){
				  //En este caso el que ha mandado el mensaje es el usuario que esta logueado
				  cssClass = "pull-right";
			  }
			  return cssClass;
		  }
		  
		  $scope.sendMensaje = function(){
			  var mensajeContenido = $scope.contenido;
			  var idDe = $scope.conversacion.emisor.id;
			  var idPara = $scope.conversacion.receptor.id;
			  if ( idDe == undefined || idPAra == undefined  ){
				  idPara = $scope.receptor;
				  $http.post(
						  '/P/rest/mensaje/conversacion/'+idPara, 
						  {
							  contenido:mensajeContenido
						  }
					    )
				  .success(function(data) {
					  $scope.conversacion.mensajes.push(data)
				   })
				  .error(function(data, status, headers, config) {
					    // called asynchronously if an error occurs
					    // or server returns response with an error status.
				   });
			  }else{
				  $http.post(
							  '/P/rest/mensaje/conversacion/'+idDe+"/"+idPara, 
							  {
								  contenido:mensajeContenido
							  }
						    )
				  .success(function(data) {
					  $scope.conversacion.mensajes.push(data)
				   })
				  .error(function(data, status, headers, config) {
					    // called asynchronously if an error occurs
					    // or server returns response with an error status.
				   });
			  }
		  }
		}
]);

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
angular.module('pachanga').controller('ProfileController', 
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
			    
			
				 $scope.uploadImagen = function() {
				        var fd = new FormData()
				        for (var i in $scope.files) {
				            fd.append("foto", $scope.files[i])
				        }
				        $scope.progressVisible = true
				        $http({
					        method: 'POST',
					        url: "/P/rest/usuarios/editImage",
					        headers: {'Content-Type': undefined},
					        data: fd,
					        transformRequest: function(data, headersGetterFunction) {
					                        return data;
					         }
					     })
					    .success(function(data, status) {   
					                    alert("success");
					     })
					    .error(function(data, status) {   
					                    alert("error");
					     });
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
angular.module('pachanga').controller('GrupoController', 
		[ '$scope', '$http' ,
		  	function($scope, $http) {
			
			$scope.idComunidad = 0;
			
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
		    
			 
		
			 $scope.uploadImagen = function() {
			        var fd = new FormData()
			        for (var i in $scope.files) {
			            fd.append("foto", $scope.files[i])
			        }
			        alert($scope.idComunidad);
			        $http({
				        method: 'POST',
				        url: "/P/rest/comunidad/editImage/" +  $scope.idComunidad,
				        headers: {'Content-Type': undefined},
				        data: fd,
				        transformRequest: function(data, headersGetterFunction) {
				            return data;
				        }
				     })
				    .success(function(data, status) {   				                    
				    	$scope.urlComunidad='/P/rest/comunidad/getImage/' + data.id + "?" + new Date().getTime()
				     })
				    .error(function(data, status) {   
				                    alert("error");
				     });
			    }
			 
			 
			 $scope.showUser = function(userId){
					window.location.href = '/P/usuarios/profile/' + userId
			 };
			 
			 
			  $scope.conversacion = new Object();
			  $scope.conversacion.receptor = "";
			  $scope.conversacion.emisor = "";
			  $scope.conversacion.mensajes = [];
			  
			  $scope.loadMensajes = function() {
				  $scope.usuarios = [];
				  $scope.conversacion.mensajes = [];
						$http.get('/P/rest/comunidad/mensajes/4').success(function(data) {
							for (var i=0; i<data.length; i++){
								$scope.conversacion.mensajes.push(data[i])
								var cssClass = avatarCss[Math.floor(Math.random() * 21) + 1];
								data[i].emisor.avatarCssClass = cssClass;
							}
					});
				};
				
				var avatarCss = {
				        1: "bgm-red",
				        2: "bgm-white",
				        3: "bgm-black",
				        4: "bgm-brown",
				        5: "bgm-pink",
				        6: "bgm-blue",
				        7: "bgm-purple",
				        8: "bgm-deeppurple",
				        9: "bgm-lightblue",
				        10: "bgm-cyan",
				        11: "bgm-teal",
				        12: "bgm-green",
				        13: "bgm-lightgreen",
				        14: "bgm-lime",
				        15: "bgm-yellow",
				        16: "bgm-amber",
				        17: "bgm-orange",
				        18: "bgm-deeporange",
				        19: "bgm-gray",
				        20: "bgm-bluegray",
				        21: "bgm-indigo"
				      };
			  
			  $scope.getAvatarCssClass = function(usuario){
				  return usuario.avatarCssClass;
			  }	
			  
			  $scope.getMensajeCssClass = function(mensaje){
				  var cssClass= ""
				  if ( mensaje.emisor.id == $scope.conversacion.emisor.id ){
					  cssClass = "right";
				  }
				  return cssClass;
			  }
			  
			  $scope.getMensajeAvatarCssClass = function(mensaje){
				  var cssClass= "pull-left"
				  if ( mensaje.emisor.id == $scope.conversacion.emisor.id ){
					  //En este caso el que ha mandado el mensaje es el usuario que esta logueado
					  cssClass = "pull-right";
				  }
				  return cssClass;
			  }
			  
			  $scope.sendMensaje = function(){
				  var mensajeContenido = $scope.contenido;
				  alert(mensajeContenido);
					  $http.post(
							  '/P/rest/comunidad/mensaje/4', 
							  {
								  contenido:mensajeContenido
							  }
						    )
					  .success(function(data) {
						  console.log(data);
						  console.log($scope.conversacion.mensajes);
						  $scope.conversacion.mensajes.push(data)
					   })
					  .error(function(data, status, headers, config) {
						    // called asynchronously if an error occurs
						    // or server returns response with an error status.
					   });
			  }
		}
		]
);

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