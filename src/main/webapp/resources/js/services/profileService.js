/**
 * 
 */
angular.module('pachanga').factory('profileService' ,
		[ "$http" , "$q", function( $http , $q ) {
		
		
		var uploadImage = function(fd){
			var deferred = $q.defer();
			var promise = deferred.promise;
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
		    	deferred.resolve(data);
		     })
		    .error(function(err, status) {   
		    	deferred.reject(err);
		     });
			return promise;
		}
		
		
		var registrarUsuario = function(usuario){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http({
		        method: 'POST',
		        url: "/P/rest/usuarios/create",
		        data: usuario
		     })
		    .success(function(data, status) {   
		    	deferred.resolve(data);
		     })
		    .error(function(err, status) {   
		    	deferred.reject(err);
		     });
			return promise;
		}
		
		var rememberPassword = function(emailWrapper){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http({
		        method: 'POST',
		        url: "/P/rest/usuarios/remember",
		        data: emailWrapper
		     })
		    .success(function(data, status) {   
		    	deferred.resolve(data);
		     })
		    .error(function(err, status) {   
		    	deferred.reject(err);
		     });
			return promise;
		}
		
		var vote = function(idUsuario,voto){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http({
		        method: 'POST',
		        url: "/P/rest/usuarios/votar/"+idUsuario,
		        data: {
		        	valor: voto
		        }
		     })
		    .success(function(data, status) {   
		    	deferred.resolve(data);
		     })
		    .error(function(err, status) {
		    	var error = {
		    		data : err,
		    		status : status
		    	};
		    	deferred.reject(error);
		     });
			return promise;
		}
		
		var getVotesKarma = function(idUsuario){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http({
		        method: 'GET',
		        url: "/P/rest/usuarios/getVotos/"+idUsuario
		     })
		    .success(function(data, status) {   
		    	deferred.resolve(data);
		     })
		    .error(function(err, status) {   
		    	deferred.reject(err);
		     });
			return promise;
		}
		
		var getRatingMedia = function(idUsuario){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http({
		        method: 'GET',
		        url: "/P/rest/usuarios/getMediaVotos/"+idUsuario
		     })
		    .success(function(data, status) {   
		    	deferred.resolve(data);
		     })
		    .error(function(err, status) {   
		    	deferred.reject(err);
		     });
			return promise;
		}
		
		var getNovedades = function(idUsuario,pagina){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http({
		        method: 'GET',
		        url: "/P/rest/usuarios/getNovedades/"+idUsuario+"/" + pagina
		     })
		    .success(function(data, status) {   
		    	deferred.resolve(data);
		     })
		    .error(function(err, status) {   
		    	deferred.reject(err);
		     });
			return promise;
		}
		
		var createNovedad = function(contenido,idUsuario){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http({
		        method: 'POST',
		        url: "/P/rest/usuarios/novedad/create/"+idUsuario ,
		        data: {
		        	contenido : contenido
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
		
		var createComentario = function(contenido,idNovedad){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http({
		        method: 'POST',
		        url: "/P/rest/usuarios/novedad/comentario/create/"+idNovedad ,
		        data: {
		        	contenido : contenido
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
		
		var upLike = function(idNovedad){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http({
		        method: 'GET',
		        url: "/P/rest/usuarios/novedad/like/"+idNovedad 
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
			uploadImage : uploadImage ,
			registrarUsuario : registrarUsuario ,
			rememberPassword : rememberPassword ,
			vote : vote ,
			getRatingMedia : getRatingMedia ,
			getVotesKarma : getVotesKarma ,
			getNovedades : getNovedades ,
			createNovedad : createNovedad ,
			createComentario : createComentario , 
			upLike : upLike
		}
	}
]);