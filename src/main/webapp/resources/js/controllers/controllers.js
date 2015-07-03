
angular.module('pachanga').controller('InitController', [ '$scope', '$http' , '$timeout' , 'partidoService',

	function($scope, $http, $timeout, partidoService) {
		$scope.pagina = 1;
		$scope.userSigned = new Object()
		$scope.userSigned.id = 0;
		$scope.hayMasPaginas = true;
		$scope.desktopPorcentaje = 46;
		$scope.textLoadMoreButton = 'Mostrar M\u00E1s...'
		var date = new Date();
	    var d = date.getDate();
	    var m = date.getMonth();
	    var y = date.getFullYear();
		$scope.uiConfig = {
			      calendar:{
			    	  contentHeight: 'auto',
				        theme: true,
				        editable: false,
				        selectable: false,
		                lang : 'es',
		                header: {
		                    right: '',
		                    center: 'prev, title, next',
		                    left: ''
		                },
		                defaultDate: new Date(),
			      }
			    };
		
		/* event source that pulls from google.com */
	    $scope.eventSource = {
	            url: "http://www.google.com/calendar/feeds/usa__en%40holiday.calendar.google.com/public/basic",
	            className: 'gcal-event',           // an option!
	            currentTimezone: 'America/Chicago' // an option!
	    };
	    /* event source that contains custom events on the scope */
	    $scope.events = [ ];
	    /* event source that calls a function on every view switch */
	    $scope.eventsF = function (start, end, timezone, callback) {
	      var s = new Date(start).getTime() / 1000;
	      var e = new Date(end).getTime() / 1000;
	      var m = new Date(start).getMonth();
	      var events = [{title: 'Feed Me ' + m,start: s + (50000),end: s + (100000),allDay: false, className: ['customFeed']}];
	      callback(events);
	    };
	    
	    
	    /* event sources array*/
	    $scope.eventSources = [$scope.events];
		
		$scope.inicio = function() {
			$scope.partidos = [];
			$http.get('/P/rest/partido/inicio').success(function(data) {
				for (var i=0; i<data.length; i++){
					$scope.partidos.push(data[i])
				}
			});
			$timeout(function() {
				$scope.partidosJugados = [];
				partidoService.refreshPartidosJugados()
			    .then(function(data) {
			    	for (var i=0; i<data.length; i++){
						$scope.partidosJugados.push(data[i])
					}
			    }).catch(function(error) {
			    	console.log(error);
			    	notify('Error obteniendo tus partidos' , 'inverse');
			    });
		    }, 500);
			
		}
	
		$scope.loadMorePartidos = function() {
			partidoService.loadMorePartidos($scope.pagina+1)
		    .then(function(data) {
		    	$scope.showMorePartidos(data);
				$scope.pagina = $scope.pagina + 1;
		    }).catch(function(error) {
		    	console.log(error);
		    	notify('Algo no est\u00E1 funcionando correctamente... :(' , 'inverse');
		    });
		}
		
		$scope.apuntarseAPartido = function(partidoId){
			  partidoService.apuntarse(partidoId)
			    .then(function(partido) {
			    	for (var i=0; i<$scope.partidos.length; i++){
						  var partidoEnLista = $scope.partidos[i]
						  if ( partidoEnLista.id == partido.id ){
							  $scope.partidos[i] = partido;
							  break;
						  }
					}
			    	if ( $scope.partidosJugados.length < 5 ){
			    		$scope.partidosJugados.push(partido);
			    	}
			    	notify('Te has apuntado a ' + partido.titulo , 'inverse');
			    })
			    .catch(function(error) {
			    	notify('Error apuntandote a un partido' , 'inverse');
			    });
			  
		}
	
		$scope.showMorePartidos = function(data) {
			for (var i=0; i<data.length; i++){
				$scope.partidos.push(data[i])
			}
			if ( data.length < 4 ){
				$scope.hayMasPaginas = false;
				$scope.textLoadMoreButton = 'No existen m\u00E1s partidos. \u00BFTe animas a crear uno\u003F :)'
			}else{
				$scope.hayMasPaginas = true;
			}
		};
		
		$scope.showPartido = function(partidoId){
			window.location.href = '/P/partido/show/' + partidoId
		};
		
		$scope.showGrupo = function(grupoId){
			window.location.href = '/P/grupo/show/' + grupoId
		};
		
		$scope.getCssLoadMoreButton = function(){
			var css = ''
			if ( !$scope.hayMasPaginas ){
				css = 'no-active'
			}
			return css;
		};
		
		
		$scope.refreshPartidosJugados = function(){
			$scope.partidosJugados = [];
			partidoService.refreshPartidosJugados()
		    .then(function(data) {
		    	for (var i=0; i<data.length; i++){
					$scope.partidosJugados.push(data[i])
				}
		    }).catch(function(error) {
		    	notify('Error obteniendo tus partidos' , 'inverse');
		    });
		}
		
		$scope.setFormScope= function(scope){
			   $scope.formScope = scope;
		}
		
		$scope.getAllPartidos = function(){
			if ( $scope.userSigned.id > 0 ){ 
				partidoService.relacionados(1,999999999)
				 	.then(function(data) {
				 		for ( var indice = 0; indice < data.content.length; indice++ ){
				 			var partido = data.content[indice];
				 			var fechaParseada = partido.fecha.split(" ")[0]
				 			var horasParseada = partido.fecha.split(" ")[1]
				 			var diaParseado = fechaParseada.split("/")[0];
				 			var mesParseado = fechaParseada.split("/")[1];
				 			var anioParseado = fechaParseada.split("/")[2];
				 			var horaParseada = horasParseada.split(":")[0]
				 			var minutoParseada = horasParseada.split(":")[1]
				 			var fechaStart = new Date(anioParseado,(mesParseado-1),diaParseado,horaParseada,minutoParseada,0,0);
				 			var miliseconds = fechaStart.getUTCMilliseconds();
				 			miliseconds += 3600000
				 			var fechaEnd = new Date(miliseconds)
				 			$scope.events.push({
				 				id: partido.id,
				 		        title: partido.titulo,
				 		        start: fechaStart,
				 		        end: fechaEnd,
				 		        className: ['bgm-red'] ,
				 		        url : '/P/partido/show/' + partido.id
				 		      });
				 		}
				 		
				    })
				    .catch(function(error) {
				    	console.log(error);
				    	notify('Se ha producido un error obteniendo tus partidos... :(', 'inverse');
				    });
			}
		}
		 
		$scope.savePartido = function(){
			var precio = $scope.formScope.precio
			var titulo = $scope.formScope.titulo;
			var categoria = $scope.formScope.categoriaTitulo;
			var plazas = $scope.formScope.plazas
			var fecha = $scope.formScope.fecha;
			var propietario = $scope.formScope.propietario;
			var publico = $scope.formScope.publico;
			if ( precio == undefined || titulo == undefined || fecha == undefined  || categoria == undefined){
				notify('Para crear un partido debes introducir un precio, un t&iacutetulo, una fecha y el deporte :|' , 'inverse');
			}else{
				var partido = new Object()
				partido.categoria = categoria;
				partido.titulo = titulo;
				partido.plazas = plazas;
				partido.precio = precio;
				partido.fecha = fecha;
				partido.propietario = propietario;
				partido.publico = ( publico != undefined ? publico : false )
				partidoService.save(partido)
				    .then(function(partido) {
				    	if ( partido.publico ){
				    		$scope.partidos.push(partido)
				    	}
				    	notify('¡Tu partido ha sido creado! A jugar! :)' , 'inverse');
				    		
				    })
				    .catch(function(error) {
				    	console.log(error);
				    	notify('Se ha producido un error creando tu partido... :(' , 'inverse');
				    });
			}
		}
		
		$scope.isInDate = function(partido){
			return Date.parse(partido.fecha) >= new Date().getTime();
		}
		
		$scope.isFull = function(partido){
			return ( partido.jugadores.length == partido.plazas );
		}
		
		$scope.isPropio = function(partido){
			return ( partido.propietario.id == $scope.userSigned.id );
		}
		
		$scope.comprobarSiEstaApuntado = function(partido,idUsuario){
			var inscrito = false;
			for ( var i=0; i<partido.jugadores.length; i++ ){
				if ( partido.jugadores[i].id == idUsuario ){
					inscrito = true;
				}
			}
			return inscrito;
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
