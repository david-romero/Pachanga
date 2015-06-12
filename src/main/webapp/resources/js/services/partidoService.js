/**
 * 
 */
angular.module('pachanga').factory('partidoService' ,
		[ "$http" , "$q" , "$timeout", function( $http , $q, $timeout ) {
		
		
		var save = function(partido){
				var deferred = $q.defer();
				var promise = deferred.promise;
				$http.post(
						  '/P/rest/partido/save'
						, 
						  {
							  titulo:partido.titulo,
							  precio:partido.precio,
							  plazas:partido.plazas,
							  fecha: partido.fecha,
							  categoriaTitulo:partido.categoria,
							  propietario:partido.propietario
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
		
		var apuntarse = function(partidoId){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.post('/P/rest/partido/apuntarse/'+partidoId)
		    .success(function(data) {
			  deferred.resolve(data);
		    })
		    .error(function(err) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			  deferred.reject(err);
		    });
			return promise;
		}
		
		var uploadImage = function(fd,partidoId){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http({
		        method: 'POST',
		        url: "/P/rest/partido/editImage/"+partidoId,
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
		
		var getPartidosComunidad = function(idComunidad){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$timeout( function(){ 
				$http.get('/P/rest/comunidad/partidos/' + idComunidad).success(function(data) {
					deferred.resolve(data);
				}).error(function(err, status) {   
			    	deferred.reject(err);
			     });
			}, 5000);
			
			return promise;
		}
		
		var eliminarJugador = function(idPartido,idJugador){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.post('/P/rest/partido/eliminarJugador/'+idPartido+"/"+idJugador)
		     .success(function(data) {
		    	 deferred.resolve(data);
			}).error(function(err) {
				deferred.reject(err);
			});
			return promise;
		}
		
		var loadMorePartidos = function(pagina){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.get('/P/rest/partido/loadMorePartidos/' + pagina)
			.success(function(data) {
				deferred.resolve(data);
			}).error(function(err) {
				deferred.reject(err);
			});
			return promise;
		}
		
		var refreshPartidosJugados = function(){
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http.get('/P/rest/partido/jugados').success(function(data) {
				deferred.resolve(data);
			}).error(function(err) {
				deferred.reject(err);
			});
			return promise;
		}
		
		return {
			save : save ,
			apuntarse : apuntarse ,
			uploadImage : uploadImage ,
			getPartidosComunidad : getPartidosComunidad ,
			eliminarJugador : eliminarJugador ,
			loadMorePartidos : loadMorePartidos ,
			refreshPartidosJugados : refreshPartidosJugados
		}
	}
]);