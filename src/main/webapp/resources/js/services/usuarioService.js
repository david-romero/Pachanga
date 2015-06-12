/**
 * 
 */
angular.module('pachanga').factory('usuarioService' ,
		[ "$http" , "$q", function( $http , $q ) {
		
		
		var getUsuarios = function(){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.get('/P/rest/usuarios/inicio')
		    .success(function(data, status) {   
		    	deferred.resolve(data);
		     })
		    .error(function(err, status) {   
		    	deferred.reject(err);
		     });
			return promise;
		}
		
		return {
			getUsuarios : getUsuarios
		}
	}
]);