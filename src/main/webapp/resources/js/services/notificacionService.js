/**
 * 
 */
/**
 * 
 */
angular.module('pachanga').factory('notificacionService' ,
		[ "$http" , "$q", function( $http , $q ) {
		
		

		
		var loadNotificaciones = function(){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.get('/P/rest/notificacion/obtenerUltimas/')
			.success(function(data) {
				deferred.resolve(data);
			}).error(function(err, status, headers, config) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			  deferred.reject(err);
		   });
		   return promise;
		}
		
		var loadAllNotificacionesSinLeer = function(){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.get('/P/rest/notificacion/obtenerNoLeidas/')
			.success(function(data) {
				deferred.resolve(data);
			}).error(function(err, status, headers, config) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			  deferred.reject(err);
		   });
		   return promise; 
		}
		
		var leerTodasNoLeidas = function(){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.get('/P/rest/notificacion/leerTodasNoLeidas')
			.success(function(data) {
				deferred.resolve();
			}).error(function(err, status, headers, config) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			  deferred.reject(err);
		   });
		   return promise;
		}
		
		var eliminarNotificaciones = function(arrayNotificaciones){
			var deferred = $q.defer();
			var promise = deferred.promise;
			if ( arrayNotificaciones[0].id != undefined ){
				$http.post('/P/rest/notificacion/eliminar', 
						  {
					  notificaciones:arrayNotificaciones
				  })
				.success(function(data) {
					deferred.resolve();
				}).error(function(err, status, headers, config) {
				    // called asynchronously if an error occurs
				    // or server returns response with an error status.
				  deferred.reject(err);
			   });
			}else{
				$http.post('/P/rest/notificacion/eliminar/ids', 
						  {
					  notificaciones:arrayNotificaciones
				  })
				.success(function(data) {
					deferred.resolve();
				}).error(function(err, status, headers, config) {
				    // called asynchronously if an error occurs
				    // or server returns response with an error status.
				  deferred.reject(err);
			   });
			}
		   return promise;
		}
		
		var marcarComoLeidas =  function(arrayNotificaciones){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.post('/P/rest/notificacion/marcarLeidas', 
					  {
				  notificaciones:arrayNotificaciones
			  })
			.success(function(data) {
				deferred.resolve();
			}).error(function(err, status, headers, config) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			  deferred.reject(err);
		   });
		   return promise;
		}
		
		return {
			loadNotificaciones : loadNotificaciones ,
			loadAllNotificacionesSinLeer : loadAllNotificacionesSinLeer ,
			leerTodasNoLeidas : leerTodasNoLeidas ,
			eliminarNotificaciones : eliminarNotificaciones ,
			marcarComoLeidas : marcarComoLeidas
		}
	}
]);