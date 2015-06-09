/**
 * 
 */
angular.module('pachanga').controller('ProfileController', 
		[ '$scope', '$http', 'profileService' ,
		  	function($scope, $http , profileService) {
				$scope.activeTab = 1;
				$scope.getProfileTabCss = function(tab){
					var cssClass = "";
					if ( tab == $scope.activeTab ){
						cssClass = "active";
					}
					return cssClass;
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