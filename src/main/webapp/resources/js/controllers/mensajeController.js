/**
 * 
 */
angular.module('pachanga').controller('MensajeController', [ '$scope', '$http' , '$timeout' , 'mensajeService', 'usuarioService' ,
	function($scope, $http, $timeout , mensajeService, usuarioService) {
		$scope.conversacion = new Object();
		$scope.conversacion.receptor = new Object();
		$scope.conversacion.emisor = new Object();
		$scope.conversacion.mensajes = [];
		$scope.mensajesPorLeer = [];
		$scope.numeroMensajesPorLeer = 0;
		
		$scope.loadMensajesSinLeer = function(){
			mensajeService.getMensajesSinLeer()
			.then(function(data) {
				$scope.numeroMensajesPorLeer = data.length;
				for ( var i = 0; i < data.length; i++ ){
					$scope.mensajesPorLeer.push(data[i]);
				}
			}).catch(function(error) {
		    	console.log(error);
		    	notify('Se ha producido un error subiendo la imagen', 'inverse');
		  });
		}
		
		$scope.verConversacion = function(usuario){
			window.location.href = '/P/usuarios/chat/' + usuario.id;
		}
		
		var recibirMensaje = function(event){
			console.log(event.data);
			var mensajes = JSON.parse(event.data)
			for ( var i ; i < mensajes.length; i++ ){
				var mensaje = mensajes[i];
				var existeMensaje = false;
				for ( var ii = 0; ii < $scope.conversacion.mensajes.length; ii++ ){
					if ( $scope.conversacion.mensajes[ii].id == mensaje.id ){
						existeMensaje = true;
					}
				}
				if ( !existeMensaje ){
					$scope.conversacion.mensajes.push(mensaje);
					$scope.numeroMensajesPorLeer = $scope.numeroMensajesPorLeer + 1;
				}
			}
		}
		
		var errorAlRecibirMensaje = function(event){
			console.log(event);
		}
		
		$scope.iniciarEventos = function(){
			if ( $scope.conversacion.receptor.id != undefined ){
				var source = new EventSource('/P/mensajes/alertas/' + $scope.conversacion.receptor.id);
	            /* handle incoming messages */
	            source.onmessage = recibirMensaje;
	            
	            source.onerror = errorAlRecibirMensaje;
			}
		}
		
		$scope.loadConversacionReceptor = function(){
			mensajeService.loadConversacion($scope.conversacion.receptor.id).then(function(data) {
				  for ( var i = 0; i < data.length; i++ ){
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
					  $scope.conversacion.mensajes.push(data[i]);
				  }
			  });
		}
		
	  $scope.loadContactos = function() {
		  $scope.usuarios = [];
		  
		  $scope.conversacion.mensajes = [];
		  usuarioService.getUsuarios()
		  .then(function(data) {
			  for (var i=0; i<data.length; i++){
					if ( i == 0 ){
						data[i].active=true;
						$scope.conversacion.receptor = data[i];
						console.log(data[i]);
					}else{
						data[i].active=false;
					}
					$scope.usuarios.push(data[i])
			  }//End For
			  $scope.loadConversacionReceptor();
			  $timeout(function(){
				  $scope.iniciarEventos();
			  },5000);
		  })
		  .catch(function(error) {
		    	console.log(error);
		    	notify('Se ha producido un error subiendo la imagen', 'inverse');
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
			  mensajeService.loadConversacion(usuario.id).then(function(data) {
				  for ( var i = 0; i < data.length; i++ )
					  $scope.conversacion.mensajes.push(data[i]);
			  });
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
			  if ( idDe == undefined || idPara == undefined  ){
				  if (idPara == undefined){
					  idPara = $scope.receptor;
				  }
				  mensajeService.sendMensaje(idPara,mensajeContenido).then(function(data) {
					  $scope.conversacion.mensajes.push(data)
				    })
				    .catch(function(error) {
				    	console.log(error);
				    	notify('Se ha producido un error subiendo la imagen', 'inverse');
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