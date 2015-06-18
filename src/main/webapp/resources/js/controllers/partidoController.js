angular.module('pachanga').controller('PartidoController', 
		[ '$scope', '$http' , "partidoService" ,
		  	function($scope, $http, partidoService) {
				 $scope.activeTab = 1;
				 $scope.userSigned = new Object()
				 $scope.userSigned.id = 0;
				 $scope.partidosVinculados = [];
				 $scope.paginas = [];
				 $scope.page = new Object();
				 $scope.page.size = 10;
				 var date = new Date();
				 var d = date.getDate();
				 var m = date.getMonth();
				 var y = date.getFullYear();
				 $scope.uiConfig = {
					      calendar:{
					    	  
						        theme: true,
						        editable: false,
						        selectable: true,
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
			    $scope.events = [
			      {title: 'All Day Event',start: new Date(y, m, 1)},
			      {title: 'Long Event',start: new Date(y, m, d - 5),end: new Date(y, m, d - 2)},
			      {id: 999,title: 'Repeating Event',start: new Date(y, m, d - 3, 16, 0),allDay: false},
			      {id: 999,title: 'Repeating Event',start: new Date(y, m, d + 4, 16, 0),allDay: false},
			      {title: 'Birthday Party',start: new Date(y, m, d + 1, 19, 0),end: new Date(y, m, d + 1, 22, 30),allDay: false},
			      {title: 'Click for Google',start: new Date(y, m, 28),end: new Date(y, m, 29),url: 'http://google.com/'}
			    ];
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
				 
				 $scope.getProfileTabCss = function(tab){
					var cssClass = "";
					if ( tab == $scope.activeTab ){
						cssClass = "active";
					}
					return cssClass;
				}
				 
				$scope.getEventosCalendario = function(){
					alert(new Date());
					var y = 2015;
					var m = 6;
					$scope.partidos = [
				                        {
				                            title: 'Hangout with friends',
				                            start: new Date(y, m, 1),
				                            end: new Date(y, m, 2),
				                            className: 'bgm-cyan'
				                        },
				                        {
				                            title: 'Meeting with client',
				                            start: new Date(y, m, 10),
				                            allDay: true,
				                            className: 'bgm-red'
				                        },
				                        {
				                            title: 'Repeat Event',
				                            start: new Date(y, m, 18),
				                            allDay: true,
				                            className: 'bgm-blue'
				                        },
				                        {
				                            title: 'Semester Exam',
				                            start: new Date(y, m, 20),
				                            end: new Date(y, m, 23),
				                            className: 'bgm-green'
				                        },
				                        {
				                            title: 'Soccor match',
				                            start: new Date(y, m, 5),
				                            end: new Date(y, m, 6),
				                            className: 'bgm-purple'
				                        },
				                        {
				                            title: 'Coffee time',
				                            start: new Date(y, m, 21),
				                            className: 'bgm-orange'
				                        },
				                        {
				                            title: 'Job Interview',
				                            start: new Date(y, m, 5),
				                            className: 'bgm-dark'
				                        },
				                        {
				                            title: 'IT Meeting',
				                            start: new Date(y, m, 5),
				                            className: 'bgm-cyan'
				                        },
				                        {
				                            title: 'Brunch at Beach',
				                            start: new Date(y, m, 1),
				                            className: 'bgm-purple'
				                        },
				                        {
				                            title: 'Live TV Show',
				                            start: new Date(y, m, 15),
				                            end: new Date(y, m, 17),
				                            className: 'bgm-orange'
				                        },
				                        {
				                            title: 'Software Conference',
				                            start: new Date(y, m, 25),
				                            end: new Date(y, m, 28),
				                            className: 'bgm-blue'
				                        },
				                        {
				                            title: 'Coffee time',
				                            start: new Date(y, m, 30),
				                            className: 'bgm-orange'
				                        },
				                        {
				                            title: 'Job Interview',
				                            start: new Date(y, m, 30),
				                            className: 'bgm-dark'
				                        },
				                    ];
				}
				 
				$scope.setFormScope= function(scope){
					$scope.formScope = scope;
				}

				 
				$scope.savePartido = function(form){
					console.log($scope.formScope);
					var precio = $scope.formScope.precio
					var titulo = $scope.formScope.titulo;
					var categoria = $scope.formScope.categoria;
					var plazas = $scope.formScope.plazas
					var fecha = $scope.formScope.fecha;
					var hora = $scope.formScope.hora;
					if ( hora != undefined ){
						var fechaParseada = new Date(fecha);
						fecha = fechaParseada.getUTCDate() + "/" + (fechaParseada.getUTCMonth()+1) + "/" + fechaParseada.getFullYear() +
						" " + hora.split(" ")[0].split(":")[0] + ":" + hora.split(" ")[0].split(":")[1];
					}else{
						var fechaParseada = new Date(fecha);
						fecha = fechaParseada.getUTCDate() + "/" + (fechaParseada.getUTCMonth()+1) + "/" + fechaParseada.getFullYear() +
						" " + fechaParseada.getUTCHours() + ":" + fechaParseada.getUTCMinutes();
					}
					var propietario = $scope.formScope.propietario;
					var publico = $scope.formScope.publico;
					if ( precio == undefined || titulo == undefined || fecha == undefined  || categoria == undefined){
						notifyError('Para crear un partido debes introducir un precio, un t&iacutetulo, una fecha y el deporte :|' );
					}else{
						var partido = new Object()
						partido.categoria = categoria;
						partido.titulo = titulo;
						partido.plazas = plazas;
						partido.precio = precio;
						partido.fecha = fecha;
						partido.publico = ( publico != undefined ? publico : false )
						partido.propietario = propietario;
						partidoService.save(partido)
						    .then(function(data) {
						      $scope.partidos.push(data)
						    })
						    .catch(function(error) {
						    	console.log(error);
						    	notify('Se ha producido un error guardando el partido... :(' , 'inverse');
						    });
					}
				}
				
				$scope.getPartidosComunidad = function(idComunidad){
					$scope.partidos = [];
					partidoService.getPartidosComunidad(idComunidad).then(function(data) {
						for (var i=0; i<data.length; i++){
							$scope.partidos.push(data[i])
						}
				    })
				    .catch(function(error) {
				    	console.log(error);
				    	notify('Se ha producido un error obteniendo los partidos de la comunidad', 'inverse');
				    });
				}
				
				$scope.comprobarSiEstaApuntado = function(partido,emailUsuarioLogueado){
					var inscrito = false;
					for ( var i=0; i<partido.jugadores.length; i++ ){
						if ( partido.jugadores[i].email == emailUsuarioLogueado ){
							inscrito = true;
						}
					}
					return inscrito;
				}
				
				$scope.isInDate = function(partido){
					console.log("Partido: " + partido.titulo + " " + Date.parse(partido.fecha) >= new Date().getTime());
					return partido.fecha >= new Date().getTime()
				}
				
				$scope.isFull = function(partido){
					return ( partido.jugadores.length == partido.plazas );
				}
				
				$scope.setFiles = function(element) {
				    $scope.$apply(function(scope) {
				      console.log('files:', element.files);
				      // Turn the FileList object into an Array
				      $scope.files = []
				        for (var i = 0; i < element.files.length; i++) {
				          scope.files.push(element.files[i])
				        }
				      $scope.progressVisible = false
				      
				      });
				    $scope.uploadImagen()
				 };
				 
				 $scope.eliminarJugador = function(idPartido,idJugador){					 
					 partidoService.eliminarJugador(idPartido,idJugador)
				        .then(function(data) {
				        	window.location.href= '/P/partido/show/' + data.id
					    })
					    .catch(function(error) {
					    	console.log(error);
					    	notify('Se ha producido un error eliminando al jugador', 'inverse');
					    });
				 }
				 
				 $scope.relacionados = function(pagina){
					 
					 if ( pagina == 0 || pagina > $scope.page.totalPages   ){
						 
					 }else{
						 $scope.paginas = [];
						 partidoService.relacionados(pagina,$scope.page.size)
						 	.then(function(data) {
						 		$scope.page = data;
						 		for ( var indice = 1; indice <= data.totalPages; indice++ ){
						 			$scope.paginas.push(indice);
						 		}
						    })
						    .catch(function(error) {
						    	console.log(error);
						    	notify('Se ha producido un error obteniendo tus partidos... :(', 'inverse');
						    });
					 }
				 }
				 
				 $scope.getAllPartidos = function(){
					 partidoService.relacionados(1,999999999)
					 	.then(function(data) {
					 		$scope.page = data;
					 		for ( var indice = 1; indice <= data.totalPages; indice++ ){
					 			$scope.paginas.push(indice);
					 		}
					    })
					    .catch(function(error) {
					    	console.log(error);
					    	notify('Se ha producido un error obteniendo tus partidos... :(', 'inverse');
					    });
				 }
				 
				 $scope.getCssPaginaActiva = function(pagina){
					 var css = '';
					 if ( pagina == ($scope.page.number+1) ){
						 css = 'active';
					 }
					 return css;
				 };
				 
				 $scope.getAriaSelectedPageSize = function(pageSize){
					 return pageSize == ($scope.page.size);
				 };
				 
				 $scope.getCssPageSize = function(pageSize){
					 var css = '';
					 if ( pageSize == ($scope.page.size) ){
						 css = 'active';
					 }
					 return css;
				 }
				 
				 $scope.remove = function(idPartido){
					 partidoService.eliminar(idPartido)
					 	.then(function(data) {
					 		notify('Se ha eliminado el partido correctamente!', 'inverse');
					 		console.log($scope.page.content);
					 		for ( var indice = 0; indice < $scope.page.content.length; indice++ ){
					 			var partido = $scope.page.content[indice];
					 			if ( partido.id == idPartido ){
					 				$scope.page.content.slice(indice);
					 				break;
					 			}
					 		}
					    })
					    .catch(function(error) {
					    	console.log(error);
					    	notify('Se ha producido un error eliminando al partido... :(', 'inverse');
					    });
				 }
				
				 $scope.uploadImagen = function() {
					 var fd = new FormData()
				        for (var i in $scope.files) {
				            fd.append("foto", $scope.files[i])
				        }
				        
					 partidoService.uploadImage(fd)
				        .then(function(data) {
				        	$scope.urlProfile='/P/usuarios/getUserImage/' + data.id + "?" + new Date().getTime()
					    })
					    .catch(function(error) {
					    	console.log(error);
					    	notify('Se ha producido un error subiendo la imagen', 'inverse');
					    });
				}
				 
				 $scope.apuntarseAPartido = function(partidoId){
					 partidoService.apuntarse(partidoId)
					    .then(function(data) {
					    	console.log($scope);
					      for ( var indice = 0; indice < $scope.partidos; indice++ ){
					    	  var partido = $scope.partidos[indice]
					    	  console.log(partido);
					      }
					    })
					    .catch(function(error) {
					    	console.log(error);
					    	notify('Se ha producido un error al apuntarse al partido', 'inverse');
					    });
				}

				    
				
			}
		]
);
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
//Bootstrap Growl 
function notifyError(message){
	jQuery.growl({
        message: message
    },{
        type: 'danger',
        allow_dismiss: false,
        label: 'Cancel',
        className: 'btn-xs btn-inverse',
        placement: {
            from: 'bottom',
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