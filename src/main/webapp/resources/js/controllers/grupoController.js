/**
 * 
 */
angular.module('pachanga')
	.controller('GrupoController', 
		[ '$scope', '$http'  , 'grupoService' ,'$timeout' , 'usuarioService' ,
		  	function($scope, $http, grupoService, $timeout, usuarioService) {
			
			$scope.idComunidad = 0;
			
			$scope.usuariosGrupo = [];
			$scope.usuariosCandidatos = [];
			
			$scope.user = { name: '' };
			
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
		    
			 $scope.aniadirUsuarioAGrupo = function(usuario){
				 $timeout(function() {
					 $scope.usuariosGrupo.push(usuario);
					 for ( var i = 0; i < $scope.usuariosCandidatos.length ; i++ ){
						 if ( $scope.usuariosCandidatos[i].id == usuario.id ){
							 $scope.usuariosCandidatos.splice(i,1);
						 }
					 }
				 }, 500);
			 }
			 
			 $scope.eliminarUsuarioGrupo = function(usuario){
				 $timeout(function() {
					 $scope.usuariosCandidatos.push(usuario);
					 for ( var i = 0; i < $scope.usuariosGrupo.length ; i++ ){
						 if ( $scope.usuariosGrupo[i].id == usuario.id ){
							 $scope.usuariosGrupo.splice(i,1);
						 }
					 }
				 }, 500);
			 }
			 
			 $scope.update = function(){
				 if ($scope.user.name.length > 3){
					 usuarioService.find($scope.user.name)
					    .then(function(data) {
				        	$scope.usuariosCandidatos = [];
				        	for (var i=0; i<data.length; i++){
				        		$scope.usuariosCandidatos.push(data[i]);
				        	}
					    })
					    .catch(function(error) {
					    	console.log(error);
					    	notify('Se ha producido un error consultando los usuarios', 'inverse');
					    });
				 }
			 }
			 
		
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
			 
			 
			 
			 $scope.obtenerUsuariosCandidatos = function(){
				 $timeout(function() {
					 grupoService.getUsuariosCandidatos()
				        .then(function(data) {
				        	$scope.usuariosCandidatos = [];
				        	for (var i=0; i<data.length; i++){
				        		$scope.usuariosCandidatos.push(data[i]);
				        	}
					    })
					    .catch(function(error) {
					    	console.log(error);
					    	notify('Se ha producido un error obteniendo los usuarios candidatos para su grupo...', 'inverse');
					    });
				 }, 500);
			 }
			 
			 $scope.setFormScope= function(scope){
				 $scope.formScope = scope;
			 }
			 
			 $scope.getBackgroundImage = function(id){
				 var number = id % 9;
				 number++;
				 return number
			 }
			 
			 $scope.saveGrupo = function(){
					var titulo = $scope.formScope.titulo;
					if ( titulo == undefined ){
						notify('Para crear un grupo debes introducir  un t&iacutetulo :|' , 'inverse');
					}else{
						console.log($scope.usuariosGrupo);
						var grupo = new Object()
						grupo.titulo = titulo;
						grupo.usuarios = $scope.usuariosGrupo;
						grupoService.save(grupo)
						    .then(function(grupo) {
						    	notify('¡Tu grupo ha sido creado! A jugar! :)' , 'inverse');
						    	window.location.href = '/P/grupo/showAll';	
						    })
						    .catch(function(error) {
						    	console.log(error);
						    	notify('Se ha producido un error creando tu grupo... :(' , 'inverse');
						    });
					}
			 }
			 
			 $scope.eliminarUsuarioGrupo = function(idGrupo){
				 grupoService.eliminarUsuario(idGrupo)
				    .then(function(grupo) {
				    	notify('Has abandonado el grupo...' , 'inverse');
				    	 $timeout(function() {
				    		 window.location.href = '/P/grupo/showAll';
				    	 }, 300);
				    })
				    .catch(function(error) {
				    	console.log(error);
				    	notify('Se ha producido un error saliendo del grupo... :(' , 'inverse');
				    });
			 }
			 
			 $scope.showUser = function(userId){
					window.location.href = '/P/usuarios/profile/' + userId
			 };
			 
			 
			  $scope.conversacion = new Object();
			  $scope.conversacion.receptor = "";
			  $scope.conversacion.emisor = "";
			  $scope.conversacion.mensajes = [];
			  
			  $scope.hasProfileName = function(usuario){
				  return usuario != undefined && usuario.firstName != undefined && usuario.firstName != '' && 
				  usuario.firstName.trim() != '' ;
			  }
			  
			  $scope.getProfileImg = function(usuario){
				  var number = usuario.id % 9;
				  number++;
				  return number
			  }
			  
			  $scope.loadMensajes = function() {
				  $scope.usuarios = [];
				  $scope.conversacion.mensajes = [];
				  if ($scope.idComunidad > 0){
					    grupoService.getMensajesComunidad($scope.idComunidad)
					    .then(function(data) {
					    	for (var i=0; i<data.length; i++){
								$scope.conversacion.mensajes.push(data[i])
							}
					    })
					    .catch(function(error) {
					    	console.log(error);
					    	notify('Se ha producido un error obteniendo los mensajes del grupo', 'inverse');
					    });
				  }
				};
				
			  $scope.getAvatarCssClass = function(usuario){
				  return usuario.avatar;
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
			  
			  $scope.split = function(string,delimitador, indice){
				  return string.split(delimitador)[indice]
			  };
			  
			  $scope.sendMensaje = function(){
				  var mensajeContenido = $scope.contenido;
				  if ($scope.idComunidad > 0){
					  grupoService.enviarMensaje(mensajeContenido,$scope.idComunidad)
					    .then(function(data) {
							$scope.conversacion.mensajes.push(data)
					    })
					    .catch(function(error) {
					    	console.log(error);
					    	notify('Se ha producido un error enviando el mensaje al grupo... :(', 'inverse');
					    });
				  }
			 };
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