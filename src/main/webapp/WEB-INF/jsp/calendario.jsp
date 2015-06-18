<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="prettyTime" class="org.ocpsoft.prettytime.PrettyTime" />
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Index</title>

<!-- Bootstrap Core CSS -->
<link href="/P/resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="/P/resources/css/shop-homepage.css" rel="stylesheet">

<!-- APP -->
<link href="/P/resources/css/app.css" rel="stylesheet">

<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

<!-- Vendor CSS -->
<link href="/P/resources/css/fullcalendar.css" rel="stylesheet">
<link href="/P/resources/css/animate.min.css" rel="stylesheet">
<link href="/P/resources/css/sweet-alert.min.css" rel="stylesheet">
<link href="/P/resources/css/material-design-iconic-font.min.css"
	rel="stylesheet">
<link href="/P/resources/css/socicon.min.css" rel="stylesheet">

<!-- CSS -->
<link href="/P/resources/css/app.min.1.css" rel="stylesheet">
<link href="/P/resources/css/app.min.2.css" rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->



</head>
<body ng-app="pachanga">
	<sec:authorize access="isAuthenticated()" >
		<jsp:include page="masterPage.jsp"></jsp:include>
		<section id="main">
		    <section id="content" >
				<div class="container" ng-controller="PartidoController" ng-init="getEventosCalendario()">
                    <div class="block-header">
                        <h2>Calendario <small>FullCalendar is a jQuery plugin that provides a full-sized, drag &amp; drop event calendar like the one below. It uses AJAX to fetch events on-the-fly and is easily configured to use your own feed format. It is visually customizable with a rich API.</small></h2>
                    
                        <ul class="actions">
                            <li>
                                <a href="">
                                    <i class="md md-trending-up"></i>
                                </a>
                            </li>
                            <li>
                                <a href="">
                                    <i class="md md-done-all"></i>
                                </a>
                            </li>
                            <li class="dropdown">
                                <a href="" data-toggle="dropdown">
                                    <i class="md md-more-vert"></i>
                                </a>
                    
                                <ul class="dropdown-menu dropdown-menu-right">
                                    <li>
                                        <a href="">Refresh</a>
                                    </li>
                                    <li>
                                        <a href="">Manage Widgets</a>
                                    </li>
                                    <li>
                                        <a href="">Widgets Settings</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    
                    </div>
                    
                    <div class="fc fc-ltr ui-widget" ui-calendar="uiConfig.calendar" ng-model="eventSources"></div>
                    
                    <!-- Add event -->
                    <div class="modal fade" id="addNew-event" data-backdrop="static" data-keyboard="false">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Crear una Pachanga</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="addEvent" role="form" ng-init="setFormScope(this)">
                                    	<input type="hidden" name="propietario" ng-model="propietario" ng-init="propietario = ${userSigned.id}" ng-value="${userSigned.id}">
                                        <div class="form-group">
                                            <label for="eventName">T&iacute;tulo de la Pachanga</label>
                                            <div class="fg-line">
                                                <input class="input-sm form-control" id="eventName" placeholder="ej: Pachanga..." type="text" ng-model="titulo">
                                            </div>
                                        </div>
                                        
                                        <div class="form-group">
                                        	<label for="categoria">Tipo de Pachanga</label>
                                            <div class="fg-line">
                                                <select ng-model="categoria" class="form-control selectpicker" name="categoría">
	                                                        <option>F&uacute;tbol</option>
	                                                        <option>F&uacute;tbol 7</option>
	                                                        <option>F&uacute;tbol Sala</option>
	                                                        <option>Paddel</option>
	                                                        <option>Baloncesto</option>
	                                            </select>
                                            </div>
                                        </div>
                                        
                                        <div class="form-group">
                                        	<label>Hora</label>
                                        	<input aria-expanded="false" class="form-control time-picker" ng-model="hora" data-toggle="dropdown" placeholder="Click here..." type="text">
                                        </div>
                                        
                                        <div class="form-group">
                                            <label for="plazas">Plazas</label>
                                            <div class="fg-line">
                                        		<input type="number" placeholder="Plazas" ng-model="plazas" name="plazas" class="form-control" value="0"  min="0" />
                                        	</div>
                                        </div>
				                                  
				                                 
				                         <div class="form-group">
                                            <label for="precio">Precio</label>
                                            <div class="fg-line">      
				                               <input type="number" placeholder="Precio" ng-model="precio" name="precio" class="form-control" value="0.0" step="0.1" min="0" />
				                            </div>
				                         </div>
                                        
                                        <input id="getStart" type="text" ng-model="fecha" style="display:none;">
                                        <input id="getEnd" type="hidden">
                                    </form>
                                </div>
                                
                                <div class="modal-footer">
                                    <button type="button" ng-click="savePartido()"  class="btn btn-info waves-effect waves-button waves-float" id="addEvent">Guardar</button>
                                    <button type="button" class="btn btn-link waves-effect waves-button waves-float" data-dismiss="modal">Cerrar</button>
                                </div>
                            </div>
                        </div>
                    </div>
				</div>
			</section>
		</section>
		<script type="text/javascript">
            $(document).ready(function() {
                var date = new Date();
                var d = date.getDate();
                var m = date.getMonth();
                var y = date.getFullYear();

                var cId = $('#calendar'); //Change the name if you want. I'm also using thsi add button for more actions

                //Generate the Calendar
                cId.fullCalendar({
                    header: {
                        right: '',
                        center: 'prev, title, next',
                        left: ''
                    },

                    theme: true, //Do not remove this as it ruin the design
                    selectable: true,
                    selectHelper: true,
                    editable: false,
                    lang : 'es',

                    //Add Events
                    events: angular.element('[ng-controller=PartidoController]').scope().partidosCalendario,
                     
                    //On Day Select
                    select: function(start, end, allDay) {
                        $('#addNew-event').modal('show');   
                        $('#addNew-event input:text').val('');
                        $('#getStart').val(start);
                        $('#getEnd').val(end);
                        angular.element('[ng-controller=PartidoController]').scope().fecha = start;
                    }
                });

                //Create and ddd Action button with dropdown in Calendar header. 
                var actionMenu = '<ul class="actions actions-alt" id="fc-actions">' +
                                    '<li class="dropdown">' +
                                        '<a href="" data-toggle="dropdown"><i class="md md-more-vert"></i></a>' +
                                        '<ul class="dropdown-menu dropdown-menu-right">' +
                                            '<li class="active">' +
                                                '<a data-view="month" href="">Vista Mensual</a>' +
                                            '</li>' +
                                            '<li>' +
                                                '<a data-view="basicWeek" href="">Vista Semanal</a>' +
                                            '</li>' +
                                            '<li>' +
                                                '<a data-view="agendaWeek" href="">Vista Semanal Agenda</a>' +
                                            '</li>' +
                                            '<li>' +
                                                '<a data-view="basicDay" href="">Vista Diaria</a>' +
                                            '</li>' +
                                            '<li>' +
                                                '<a data-view="agendaDay" href="">Vista Diaria Agenda</a>' +
                                            '</li>' +
                                        '</ul>' +
                                    '</div>' +
                                '</li>';


                cId.find('.fc-toolbar').append(actionMenu);
                
                //Event Tag Selector
                (function(){
                    $('body').on('click', '.event-tag > span', function(){
                        $('.event-tag > span').removeClass('selected');
                        $(this).addClass('selected');
                    });
                })();
            
                //Add new Event
                /*$('body').on('click', '#addEvent', function(){
                    var eventName = $('#eventName').val();
                    var tagColor = $('.event-tag > span.selected').attr('data-tag');
                        
                    if (eventName != '') {
                        //Render Event
                        $('#calendar').fullCalendar('renderEvent',{
                            title: eventName,
                            start: $('#getStart').val(),
                            end:  $('#getEnd').val(),
                            allDay: true,
                            className: tagColor
                            
                        },true ); //Stick the event
                        
                        $('#addNew-event form')[0].reset()
                        $('#addNew-event').modal('hide');
                    }else {
                        $('#eventName').closest('.form-group').addClass('has-error');
                    }
                }); */  

                //Calendar views
                $('body').on('click', '#fc-actions [data-view]', function(e){
                    e.preventDefault();
                    var dataView = $(this).attr('data-view');
                    
                    $('#fc-actions li').removeClass('active');
                    $(this).parent().addClass('active');
                    cId.fullCalendar('changeView', dataView);  
                });
            });                        
        </script>
        
	</sec:authorize>
</body>
</html>