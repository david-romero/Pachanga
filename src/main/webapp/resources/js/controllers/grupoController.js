/**
 * 
 */
angular.module('pachanga').controller('GrupoController', 
		[ '$scope', '$http' , 'grupoService' ,
		  	function($scope, $http, grupoService) {
			
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
			        grupoService.uploadImage(fd,$scope.idComunidad)
			        .then(function(data) {
			        	$scope.urlComunidad='/P/rest/comunidad/getImage/' + data.id + "?" + new Date().getTime()
				    })
				    .catch(function(error) {
				    	console.log(error);
				    	notify('Se ha producido un error subiendo la imagen', 'inverse');
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