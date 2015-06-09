/**
 * 
 */
angular.module('pachanga').directive('niceScroll', function() {
    return{
        restrict: 'A',
        link: function(scope, element, attribute) {

            var nicescrolConf = {
                "cursorcolor": "#bdbdbd",
                "background": "#ffffff",
                "cursorwidth": "5px",
                "cursorborder": "none",
                "cursorborderradius": "2px",
                "zindex": 1,
                "autohidemode": false
            };

           element.niceScroll(nicescrolConf);
        }
    };
});
angular.module('pachanga').directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

angular.module('pachanga').directive('scroll', function($timeout) {
	  return {
	    restrict: 'A',
	    link: function(scope, element, attr) {
	      scope.$watchCollection(attr.scroll, function(newVal) {
	        $timeout(function() {
	         
	         element[0].scrollTop = element[0].scrollHeight;
	        });
	      });
	    }
	  }
	});
