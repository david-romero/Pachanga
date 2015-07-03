/**
 * 
 */
angular.module('pachanga').factory('grupoService' ,
		[ "$http" , "$q", function( $http , $q ) {
		
		
		var uploadImage = function(fd,idComunidad){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http({
		        method: 'POST',
		        url: "/P/rest/comunidad/editImage/"+ idComunidad,
		        headers: {'Content-Type': undefined},
		        data: fd,
		        transformRequest: function(data, headersGetterFunction) {
		        	return data;
		        }
		     })
		    .success(function(data, status) {   
		    	deferred.resolve(data);
		     })
		    .error(function(err, status) {   
		    	deferred.reject(err);
		     });
			return promise;
		}
		
		var getUsuariosCandidatos = function(){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http({
		        method: 'GET',
		        url: "/P/rest/comunidad/getCandidatos",
		     })
		    .success(function(data, status) {   
		    	deferred.resolve(data);
		     })
		    .error(function(err, status) {   
		    	deferred.reject(err);
		     });
			return promise;
		}
		
		var getMensajesComunidad = function(id){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.get('/P/rest/comunidad/mensajes/'+ id)
		    .success(function(data, status) {   
		    	deferred.resolve(data);
		     })
		    .error(function(err, status) {   
		    	deferred.reject(err);
		     });
			return promise;
		}
		
		var save = function(grupo){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.post(
					  '/P/rest/comunidad/save', 
					  {
						  titulo:grupo.titulo,
						  usuarios:grupo.usuarios
					  })
		  .success(function(data) {
			  deferred.resolve(data);
		   })
		  .error(function(err) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			  console.log(err);
			  deferred.reject(err);
		   });
			return promise;
		}
		
		var eliminarUsuario = function(idGrupo){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.get('/P/rest/comunidad/eliminar/' + idGrupo + '/usuario')
		    .success(function(data, status) {   
		    	deferred.resolve(data);
		     })
		    .error(function(err, status) {   
		    	deferred.reject(err);
		     });
			return promise;
		}
		
		var enviarMensaje = function(contenido,idComunidad){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.post(
					  '/P/rest/comunidad/mensaje/'+ idComunidad, 
					  {
						  contenido:contenido
					  }
				    )
			  .success(function(data) {
				  deferred.resolve(data);
			   })
			  .error(function(data, status, headers, config) {
				    // called asynchronously if an error occurs
				    // or server returns response with an error status.
				  deferred.reject(data);
			   });
			return promise;
		}
		
		return {
			uploadImage : uploadImage ,
			getUsuariosCandidatos : getUsuariosCandidatos ,
			getMensajesComunidad : getMensajesComunidad ,
			save : save ,
			eliminarUsuario : eliminarUsuario ,
			enviarMensaje : enviarMensaje
		}
	}
]);