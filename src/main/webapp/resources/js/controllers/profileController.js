/**
 * 
 */
angular.module('pachanga').controller('ProfileController', 
		[ '$scope', '$http', 'profileService' ,
		  	function($scope, $http , profileService) {
				$scope.activeTab = 1;
				$scope.ratingMedia = 0;
				$scope.karma = [1,2,3,4,5]
				$scope.paginaNovedades = 1;
				$scope.novedades = [];
				$scope.limit = {};
				$scope.comentario = {
						contenido : ""
				};
				$scope.karmaVotes = {
						0 : 0,
						1 : 0,
						2 : 0,
						3 : 0,
						4 : 0,
						5 : 0
				}
				$scope.numeroKarmaVotes = $scope.karmaVotes[0];
				
				$scope.userSigned = {};
				
				$scope.getProfileTabCss = function(tab){
					var cssClass = "";
					if ( tab == $scope.activeTab ){
						cssClass = "active";
					}
					return cssClass;
				}
				
				$scope.getVotesKarma = function(idUsuario){
					profileService.getVotesKarma(idUsuario)
			        .then(function(data) {
			        	for ( var i = 0; i <= $scope.karma.length; i++ ){
			        		$scope.karmaVotes[i] = data[i];
			        	}
			        	$scope.numeroKarmaVotes = $scope.karmaVotes[0];
				    })
				    .catch(function(error) {
				    	console.log(error);
				    	notify('Se ha producido un error obteniendo el karma... :(', 'inverse');
				    });
				}
				
				
				
				$scope.vote = function(idUsuario,voto){
					if ( $scope.userSigned > 0 ){
						profileService.vote(idUsuario,voto)
				        .then(function(data) {
				        	$scope.numeroKarmaVotes++;
				        	$scope.karmaVotes[voto] = $scope.karmaVotes[voto] + 1;
				        	var acumVotos = 0;
				        	for ( var i = 1; i <= 5; i++ ){
				        		acumVotos += $scope.karmaVotes[i] * i; 
				        	}
				        	var totalPosibleVotes = $scope.numeroKarmaVotes * 5;
				        	$scope.ratingMedia = parseInt(acumVotos * 5 / totalPosibleVotes);
				        	notify('Le has dado ' + voto + ' puntos de karma a ' + data.receptor.email.split('@')[0] + '. Gracias! :)', 'inverse');
					    })
					    .catch(function(error) {
					    	if ( error.status == 409 ){
					    		notify('Ya has votado a este usuario... Solo lo permitimos una vez :)', 'inverse');
					    	}else{
					    		notify('Se ha producido un error enviando el karma...', 'inverse');
					    	}
					    });
					}else{
						notify('Para votar es necesario que est\u00E9s logueado', 'inverse');
					}
				}
				
				$scope.getCssProgressBarKarma = function(karmaPoint){
					var css = 'progress-bar-';
					if ( karmaPoint == 1  ){
						css += 'warning';
					}else if ( karmaPoint == 2 ){
						css += 'error';
					}else if ( karmaPoint == 3 ){
						css += 'info';
					}else if ( karmaPoint == 4 ){
						css += 'success';
					}else if ( karmaPoint == 5 ){
						css += 'warning';
					}
					return css;
				}
				
				$scope.getNumeroKarmaVotes = function(karmaPoint){
					return $scope.karmaVotes[karmaPoint]
				}
				
				$scope.getCssKarma = function(karmaPoint){
					var css = '';
					if ( karmaPoint <= $scope.ratingMedia ){
						css = 'active';
					}
					return css;
				};
				
				$scope.initializeKarma = function(idUsuario){
					profileService.getRatingMedia(idUsuario)
			        .then(function(data) {
			        	$scope.ratingMedia = parseInt(data);
			        	$scope.getVotesKarma(idUsuario);
				    })
				    .catch(function(error) {
				    	console.log(error);
				    	notify('Se ha producido un error enviando el karma...', 'inverse');
				    });
				}
				
				$scope.getTvType = function(novedad){
					var tvType = "text";
					if ( novedad.tipo ){
						if ( novedad.tipo == 'PARTIDO' ){
							tvType = "image";
						}else if ( novedad.tipo == 'GRUPO' ){
							tvType = "video";
						}
					}
					return tvType;
				}
				
				$scope.getNovedades = function( idUsuario ){
					$scope.userSigned.id = idUsuario;
					profileService.getNovedades(idUsuario,$scope.paginaNovedades)
			        .then(function(data) {
			        	for ( var i = 0; i < data.content.length ; i++ ){
			        		data.content[i].views = 0;
			        		$scope.novedades.push(data.content[i]);
			        		$scope.limit[''+data.content[i].id] = 3;
			        	}
			        	$scope.paginaNovedades++;
				    })
				    .catch(function(error) {
				    	console.log(error);
				    	notify('Se ha producido un error obteniendo las novedades...', 'inverse');
				    });
				}
				
				$scope.getLimitComentarios = function(novedad){
					var limit = 3;
					if ( $scope.limit[''+ novedad.id] ){
						limit = $scope.limit[''+ novedad.id];
					}
					return limit;
				}
				
				$scope.disableLimit = function(novedad){
					if ( $scope.limit[''+ novedad.id] ){
						$scope.limit[''+ novedad.id] = novedad.comentarios.length;
					}
				}
				
				$scope.sendNovedad = function(){
					if ($scope.formNovedadScope.contenido.length > 3){
						profileService.createNovedad($scope.formNovedadScope.contenido,$scope.formNovedadScope.usuario)
				        .then(function(data) {
				        	$scope.novedades.unshift(data);
					    })
					    .catch(function(error) {
					    	console.log(error);
					    	notify('Se ha producido un error enviando el comentario...', 'inverse');
					    });
					}else{
						notify('\u00BFPorqu\u00E9 no le env\u00ED algo m\u00E1s interesante\u003F...', 'inverse');
					}
				}
				
				$scope.upLike = function(novedad){
					if ( $scope.userSigned > 0 ){
						profileService.upLike(novedad.id)
				        .then(function(data) {
				        	novedad.likes++;
					    })
					    .catch(function(error) {
					    	console.log(error);
					    	notify('Se ha producido un error... :(', 'inverse');
					    });
					}
				}
				
				$scope.sendComentario = function(scope,idNovedad){
					var comentario = angular.element( document.querySelector('#comentario-'+idNovedad))[0].value;
					if ( comentario != undefined && comentario.length > 3  ){
						profileService.createComentario(comentario,idNovedad)
				        .then(function(novedad) {
				        	for ( var i = 0; i < $scope.novedades.length ; i++ ){
				        		if ( $scope.novedades[i].id == novedad.id ){
				        			$scope.novedades[i] = novedad;
				        		}
				        	}
				        	angular.element( document.querySelector('#comentario-'+idNovedad))[0].value = '';
					    })
					    .catch(function(error) {
					    	console.log(error);
					    	notify('Se ha producido un error enviando el comentario...', 'inverse');
					    });
					}else{
						notify('\u00BFPorqu\u00E9 no le env\u00ED algo m\u00E1s interesante\u003F...', 'inverse');
					}
				}
				
				$scope.setFormNovedadScope = function(scope){
					   $scope.formNovedadScope = scope;
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
			    
			
				 $scope.uploadImagen = function() {
			        var fd = new FormData()
			        for (var i in $scope.files) {
			            fd.append("foto", $scope.files[i])
			        }
			        
			        profileService.uploadImage(fd)
			        .then(function(data) {
			        	$scope.urlProfile='/P/usuarios/getUserImage/' + data.id + "?" + new Date().getTime()
				    })
				    .catch(function(error) {
				    	console.log(error);
				    	notify('Se ha producido un error subiendo la imagen', 'inverse');
				    });
				 }
				 
				var vm = this;
				 
				// funcation assignment
			    vm.onRegister = onRegister;
			    
			    // funcation assignment
			    vm.onRemember = onRemember;

			    // The model object that we reference
			    // on the  element in index.html
			    vm.model = {};
			    
			    vm.remember = {};
			    
			    // An array of our form fields with configuration
			    // and options set. We make reference to this in
			    // the 'fields' attribute on the  element
			    vm.rentalFields = [
			        {
			            key: 'email',
			            type: 'horizontalInput',
			            templateOptions: {
			                type: 'email',
			                label: '',
			                placeholder: 'Email',
			                required: true,
			                fixedLabel: true,
			                icon: "mail",
			            }
			        },
			        {
			            key: 'password',
			            type: 'horizontalInput',
			            templateOptions: {
			                type: 'password',
			                label: '',
			                placeholder: 'Clave',
			                required: true ,
			                icon: "accessibility"
			            },
			            validators: {
			                // Custom validator to check whether the driver's license
			                // number that the user enters is valid or not
			                password: function($viewValue, $modelValue, scope) {
			                    var value = $modelValue || $viewValue;
			                    if(value) {
			                        // call the validateDriversLicense function
			                        // which either returns true or false
			                        // depending on whether the entry is valid
			                        return validatePassword(value)
			                    }
			               }
			            }
			        },
			        {
			            key: 'confirmPassword',
			            type: 'horizontalInput',
			            templateOptions: {
			                type: 'password',
			                label: '',
			                placeholder: 'Confirmar Clave',
			                required: true,
			                icon: "accessibility"
			            },
			            validators: {
			                // Custom validator to check whether the password
			                password: function($viewValue, $modelValue, scope) {
			                    var value = $modelValue || $viewValue;
			                    if(value) {
			                        // call the validateDriversLicense function
			                        // which either returns true or false
			                        // depending on whether the entry is valid
			                        return validatePassword(value)
			                    }
			               }
			            }
			        },
			    ];
			    
			    vm.rememberFields = [
 			        {
 			            key: 'email',
 			            type: 'horizontalInput',
 			            templateOptions: {
 			                type: 'email',
 			                label: '',
 			                placeholder: 'Email',
 			                required: true,
 			                fixedLabel: true,
 			                icon: "mail",
 			            }
 			        },
			    ];
			    
			    // function definition
			    function onRegister() {
			    	if ( vm.model.password != vm.model.confirmPassword ){
			    		notify('Las claves deben ser iguales :|', 'inverse');
			    	}else{
				    	var bcrypt = new bCrypt();
				    	bcrypt.hashpw(vm.model.password, bcrypt.gensalt('10'), function(hash){
				    		vm.model.password = hash;
				    		bcrypt.hashpw(vm.model.confirmPassword, bcrypt.gensalt('10'), function(hash){
					    		vm.model.confirmPassword = hash;
					    		 profileService.registrarUsuario(vm.model)
							        .then(function(data) {
							        	notify('Registrado! Revise su email :)', 'inverse');
								    })
								    .catch(function(error) {
								    	console.log(error);
								    	notify('Se ha producido un error registrandote... :(', 'inverse');
								    });
					    	}, function(){});
				    	}, function(){});
			    	}
			    }
			    
			    function onRemember() {
			    	console.log(vm.remember);
			    	profileService.rememberPassword(vm.remember)
			        .then(function(data) {
			        	notify('Se ha regenerado su contraseña y se ha enviado por email :)', 'inverse');
				    })
				    .catch(function(error) {
				    	console.log(error);
				    	notify('Se ha producido un error regenerando la contraseña... :(', 'inverse');
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

// Tests the password
function validatePassword(inputtxt) {
	var passw = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;  
	if(inputtxt.match(passw))   
	{     
		return true;  
	}  
	else  
	{    
		return false;  
	}
}