/**
 * 
 */
angular.module('pachanga').factory('mensajeService' ,
		[ "$http" , "$q", function( $http , $q ) {
		
		
		var sendMensaje = function(idReceptor,contenido){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.post( '/P/rest/mensaje/conversacion/'+idPara,  {
						  contenido:mensajeContenido
			}).success(function(data) {
				  deferred.resolve(data);
			   })
			  .error(function(err, status, headers, config) {
				    // called asynchronously if an error occurs
				    // or server returns response with an error status.
				  deferred.reject(err);
			   });
			
			return promise;
		}
		
		var loadConversacion = function(idUsuario){
			var deferred = $q.defer();
			var promise = deferred.promise;
				$http.get('/P/rest/mensaje/conversacion/'+idUsuario).success(function(data) {
				deferred.resolve(data);
			}).error(function(err, status, headers, config) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			  deferred.reject(err);
		   });
		   return promise;
		}
		
		return {
			sendMensaje : sendMensaje ,
			loadConversacion : loadConversacion
		}
	}
]);