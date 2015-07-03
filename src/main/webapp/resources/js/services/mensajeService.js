/**
 * 
 */
angular.module('pachanga').factory('mensajeService' ,
		[ "$http" , "$q", function( $http , $q ) {
		
		
		var sendMensaje = function(idReceptor,contenido,idEmisor){
			var url = '';
			if ( idEmisor == undefined ){
				url = '/P/rest/mensaje/conversacion/'+idReceptor
			}else{
				url = '/P/rest/mensaje/conversacion/'+idEmisor+"/"+idReceptor
			}
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.post( url,  
					{
						  contenido:contenido
					})
			   .success(function(data) {
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
			if (idUsuario == undefined){
				deferred.reject("Id usuario receptor undefinied");
			}else{
				$http.get('/P/rest/mensaje/conversacion/'+idUsuario).success(function(data) {
					deferred.resolve(data);
				}).error(function(err, status, headers, config) {
				    // called asynchronously if an error occurs
				    // or server returns response with an error status.
				  deferred.reject(err);
			   });
			}
		   return promise;
		}
		
		var getMensajesSinLeer = function(){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.get('/P/rest/mensaje/getMensajesSinLeer')
			.success(function(data) {
				deferred.resolve(data);
			}).error(function(err, status, headers, config) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			  deferred.reject(err);
		   });
			
			return promise;
		}
		
		var leerMensajes = function(idEmisor,idReceptor){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.get('/P/rest/mensaje/leerMensajes/'+idEmisor+'/'+idReceptor+'/')
			.success(function(data) {
				deferred.resolve(data);
			}).error(function(err, status, headers, config) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			  deferred.reject(err);
		   });
			
			return promise;
		}
		
		var eliminarConversacion = function(idReceptor){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http({
		        method: 'DELETE',
		        url: "/P/rest/mensaje/conversacion/"+idReceptor 
		     })
		    .success(function(data, status) {   
		    	deferred.resolve(data);
		     })
		    .error(function(err, status) {   
		    	deferred.reject(err);
		     });
			return promise;
		}
		
		return {
			sendMensaje : sendMensaje ,
			loadConversacion : loadConversacion ,
			getMensajesSinLeer : getMensajesSinLeer ,
			leerMensajes : leerMensajes ,
			eliminarConversacion : eliminarConversacion
		}
	}
]);