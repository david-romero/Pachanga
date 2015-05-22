/**
 * 
 */
var app = angular.module('pachanga', [ "ngResource" ]);



app.controller('InitController', [ '$scope', '$http',

	function($scope, $http) {
		$scope.inicio = function() {
			$scope.partidos = [];
			$http.get('/P/rest/inicio').success(function(data) {
				console.log(data);
				$scope.partidos.push(data[0])
				$scope.partidos.push(data[1])
				$scope.partidos.push(data[2])
				$scope.partidos.push(data[3])
			});
		}
	
		$scope.loadMorePartidos = function() {
			$http.get('/P/rest/loadMorePartidos').success(function(data) {
				$scope.msg = 'Pelicula creada correctamente';
				$scope.showMorePartidos(data);
			}).error(function(data) {
				console.log(data);
				$scope.msg = 'Se ha producido un error';
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
	} 
]);

app.controller('MensajeController', [ '$scope', '$http' ,
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
					console.log(data);
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
]);

app.directive('scroll', function($timeout) {
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
app.directive('niceScroll', function() {
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