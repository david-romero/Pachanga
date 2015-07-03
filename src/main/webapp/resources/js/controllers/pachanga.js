// It is like saying "create a new module"
angular.module('pachanga', ["ui.calendar","ngResource","ui.bootstrap.datetimepicker","formly", 
                            "formlyBootstrap"], 
		function config(formlyConfigProvider) {
		    // set templates here
		    formlyConfigProvider.setWrapper({
		      name: 'horizontalBootstrapLabel',
		      template: [
		        '<div class="input-group m-b-20">' ,         
			        '<span class="input-group-addon"><i class="md md-{{to.icon}}"></i></span>',
			        '<div class="fg-line">',
			          '<formly-transclude></formly-transclude>',
			        '</div>' ,
		        '</div>',
		        '<small style="margin-top: -15px;display:{{to.required ? "block" : "none"}};" class="help-block">Obligatorio</small>',
		      ].join(' ')
		    }) // -> formlyConfigProvider.setWrapper
		    
		    formlyConfigProvider.setType({
		        name: 'horizontalInput',
		        extends: 'input',
		        wrapper: ['horizontalBootstrapLabel', 'bootstrapHasError']
		      });
		    
		} // -> function config(formlyConfigProvider)
);
