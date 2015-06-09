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
		
		return {
			uploadImage : uploadImage
		}
	}
]);