<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


	<!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation" id="header">
            
            <ul class="header-inner">
            	<sec:authorize access="isAuthenticated()" >
                <li class="" id="menu-trigger" data-trigger="#sidebar">
                    <div class="line-wrap">
                        <div class="line top"></div>
                        <div class="line center"></div>
                        <div class="line bottom"></div>
                    </div>
                </li>
                </sec:authorize>
            
                <li class="logo hidden-xs">
                    <a href="/P/">Pachanga</a>
                </li>
                
                <li class="pull-right">
                <ul class="top-menu">
                	<sec:authorize access="isAuthenticated()" >
                    <li id="toggle-width">
                        <div class="toggle-switch">
                            <input id="tw-switch" type="checkbox" hidden="hidden">
                            <label for="tw-switch" class="ts-helper"></label>
                        </div>
                    </li>
                    </sec:authorize>
                    <li id="top-search">
                        <a class="tm-search" href=""></a>
                    </li>
                    <sec:authorize access="isAuthenticated()" >
                    <li class="dropdown" ng-controller="MensajeController" ng-init="loadMensajesSinLeer();iniciarEventos();">
                        <a data-toggle="dropdown" class="tm-message" href=""><i class="tmn-counts" ng-if="numeroMensajesPorLeer > 0">{{numeroMensajesPorLeer}}</i></a>
                        <div class="dropdown-menu dropdown-menu-lg pull-right">
                            <div class="listview">
                                <div class="lv-header">
                                    Mensajes
                                </div>
                                <div tabindex="1" style="overflow: hidden;" class="lv-body c-overflow" >
                                    <a class="lv-item" href="" ng-repeat="mensaje in mensajesPorLeer" ng-click="verConversacion(mensaje.emisor)">
                                        <div class="media">
                                            <div class="pull-left" ng-if="mensaje.emisor.tieneAvatar == true">
                                                <img class="lv-img-sm"  ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/{{mensaje.emisor.id}}" alt="">
                                            </div>
                                            <div ng-if="mensaje.emisor.tieneAvatar == false" ng-class="mensaje.emisor.avatar"  class="lv-avatar  pull-left">
                                            	{{mensaje.emisor.email.substring( 0, 1 )}}
                                            </div>
                                            <div class="media-body">
                                                <div class="lv-title">{{mensaje.emisor.firstName}}&nbsp;{{mensaje.emisor.lastName}}</div>
                                                <small class="lv-small">{{mensaje.contenido}}</small>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                                <a href="/P/usuarios/chat" class="lv-footer" href="">Ver Todos</a>
                            </div>
                        <div style="width: 0px; z-index: 9; cursor: default; position: absolute; top: 0px; left: 298px; height: 275px; display: block; opacity: 0;" class="nicescroll-rails nicescroll-rails-vr" id="ascrail2002"><div class="nicescroll-cursors" style="position: relative; top: 0px; float: right; width: 0px; height: 271px; background-color: rgba(0, 0, 0, 0.5); border: 0px none; background-clip: padding-box; border-radius: 0px;"></div></div><div style="height: 0px; z-index: 9; top: 275px; left: 0px; position: absolute; cursor: default; display: none; width: 298px; opacity: 0;" class="nicescroll-rails nicescroll-rails-hr" id="ascrail2002-hr"><div class="nicescroll-cursors" style="position: absolute; top: 0px; height: 0px; width: 298px; background-color: rgba(0, 0, 0, 0.5); border: 0px none; background-clip: padding-box; border-radius: 0px;"></div></div></div>
                    </li>
                    <li class="dropdown" ng-controller="NotificacionController" ng-init="loadNotificacionesInicio()">
                        <a  data-toggle="dropdown" class="tm-notification" href=""><i ng-show="existenNotificaciones()" class="tmn-counts">{{numeroNotificaciones}}</i></a>
                        <div class="dropdown-menu dropdown-menu-lg pull-right">
                            <div class="listview" id="notifications" ng-class="getCssListViewNotificaciones()">
                                <div class="lv-header">
                                    Notificaci&oacute;n
                    
                                    <ul class="actions" ng-show="existenNotificaciones()">
                                        <li class="dropdown">
                                            <a href="" data-clear="notification" ng-click="leerTodasNoLeidas()">
                                                <i class="md md-done-all"></i>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                                <div tabindex="2" style="overflow: hidden;" class="lv-body c-overflow">
                                    <a class="lv-item" href="" ng-repeat="notificacion in notificacionesALeer">
                                        <div class="media">
                                            <div class="pull-left">
                                                <img class="lv-img-sm" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/{{notificacion.emisor.id}}" alt="">
                                            </div>
                                            <div class="media-body">
                                                <div class="lv-title">{{notificacion.emisor.firstName}}&nbsp;{{notificacion.emisor.lastName}}</div>
                                                <small class="lv-small">{{notificacion.titulo}}</small>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                    
                                <a href="/P/notificaciones" class="lv-footer" href="">Ver todas</a>
                            </div>
                    
                        <div style="width: 0px; z-index: 9; cursor: default; position: absolute; top: 0px; left: 298px; height: 275px; display: block; opacity: 0;" class="nicescroll-rails nicescroll-rails-vr" id="ascrail2003"><div class="nicescroll-cursors" style="position: relative; top: 0px; float: right; width: 0px; height: 271px; background-color: rgba(0, 0, 0, 0.5); border: 0px none; background-clip: padding-box; border-radius: 0px;"></div></div><div style="height: 0px; z-index: 9; top: 275px; left: 0px; position: absolute; cursor: default; display: none; width: 298px; opacity: 0;" class="nicescroll-rails nicescroll-rails-hr" id="ascrail2003-hr"><div class="nicescroll-cursors" style="position: absolute; top: 0px; height: 0px; width: 298px; background-color: rgba(0, 0, 0, 0.5); border: 0px none; background-clip: padding-box; border-radius: 0px;"></div></div></div>
                    </li>
                    </sec:authorize>
                    <li class="dropdown">
                        <a data-toggle="dropdown" class="tm-settings" href=""></a>
                        <ul class="dropdown-menu dm-icon pull-right">
                            <li>
                                <a data-action="fullscreen" href=""><i class="md md-fullscreen"></i> Toggle Fullscreen</a>
                            </li>
                            <li>
                                <a data-action="clear-localstorage" href=""><i class="md md-delete"></i> Clear Local Storage</a>
                            </li>
                            <li>
                                <a href=""><i class="md md-person"></i> Privacy Settings</a>
                            </li>
                            <li>
                                <a href=""><i class="md md-settings"></i> Other Settings</a>
                            </li>
                        </ul>
                    </li>
                    <sec:authorize access="isAuthenticated()" >
	                <sec:authentication var="user" property="principal" />
		                <li class="dropdown">
		                    <a href="#" class="dropdown-toggle profile-pic" data-toggle="dropdown" title="<c:out value="${user.username}" />">
		                        <img class="profile-nav" src="${pageContext.request.contextPath}/usuarios/getUserImage/${userSigned.id}" alt="profile">
		                    </a>
		                    <ul class="dropdown-menu dm-icon pull-right">
		                        <li><a href="/P/app">Inicio</a></li>
		                        <li class="divider"></li>
		                        <li ><a href="/P/usuarios/profile">Perfil</a></li>
		                        <li><a href="pages-timeline.html">Timeline</a></li>
		                        <li><a href="/P/usuarios/chat">Mensajes</a></li>
		                        <li class="divider"></li>
		                        <li><a href="pages-help.html">Ayuda</a></li>
		                        <li class="divider"></li>
		                        <li><a href="/P/sign-out">Salir</a></li>
		                    </ul>
		                </li>
	                </sec:authorize>
	                <sec:authorize access="isAnonymous()">
	                	<li id="sign-in">
	                        <a class="" href="/P/secure"><i class="md md-input"></i></a>
	                    </li>
	                	
	                </sec:authorize>
                    </ul>
                </li>
                
                
            </ul>
            
            <!-- Top Search Content -->
            <div id="top-search-wrap">
                <input type="text">
                <i id="top-search-close">×</i>
            </div>
    </nav>
    			<sec:authorize access="isAuthenticated()" >
                <div id="sidebar" class="sidebar-inner">
                    <div class="si-inner">
                        <div class="profile-menu">
                            <a href="">
                                <div class="profile-pic">
                                    <img src="${pageContext.request.contextPath}/usuarios/getUserImage/${userSigned.id}" alt="<c:out value="${user.username}" />">
                                </div>
                
                                <div class="profile-info">
                                    <c:out value="${user.username}" />
                
                                    <i class="md md-arrow-drop-down"></i>
                                </div>
                            </a>
                
                            <ul class="main-menu" style="display: none;">
                                <li>
                                    <a href="/P/usuarios/profile"><i class="md md-person"></i> Perfil</a>
                                </li>
                                <li>
                                    <a href=""><i class="md md-settings"></i> Configuración</a>
                                </li>
                                <li>
                                    <a href="/P/sign-out"><i class="md md-history"></i> Salir</a>
                                </li>
                            </ul>
                        </div>
                
                        <ul class="main-menu">
                            <li><a href="/P/app"><i class="md md-home"></i> Inicio</a></li>
                            <li><a href="/P/notificaciones/leidas"><i class="md md-done"></i> Notificaciones Le&iacute;das</a></li>
                            <li class="sub-menu">
                                <a href=""><i class="md md-now-widgets"></i> Widgets</a>

                                <ul>
                                    <li><a href="widget-templates.html">Templates</a></li>
                                    <li><a class="active" href="widgets.html">Widgets</a></li>
                                </ul>
                            </li>
                            <li class="sub-menu">
                                <a href=""><i class="md md-view-list"></i> Tables</a>
                
                                <ul>
                                    <li><a href="tables.html">Normal Tables</a></li>
                                    <li><a href="data-tables.html">Data Tables</a></li>
                                </ul>
                            </li>
                            <li class="sub-menu">
                                <a href=""><i class="md md-my-library-books"></i> Forms</a>
                
                                <ul>
                                    <li><a href="form-elements.html">Basic Form Elements</a></li>
                                    <li><a href="form-components.html">Form Components</a></li>
                                    <li><a href="form-examples.html">Form Examples</a></li>
                                    <li><a href="form-validations.html">Form Validation</a></li>
                                </ul>
                            </li>
                            <li class="sub-menu">
                                <a href=""><i class="md md-swap-calls"></i>User Interface</a>
                                <ul>
                                    <li><a href="colors.html">Colors</a></li>
                                    <li><a href="animations.html">Animations</a></li>
                                    <li><a href="box-shadow.html">Box Shadow</a></li>
                                    <li><a href="buttons.html">Buttons</a></li>
                                    <li><a href="icons.html">Icons</a></li>
                                    <li><a href="alerts.html">Alerts</a></li>
                                    <li><a href="notification-dialog.html">Notifications &amp; Dialogs</a></li>
                                    <li><a href="media.html">Media</a></li>
                                    <li><a href="components.html">Components</a></li>
                                    <li><a href="other-components.html">Others</a></li>
                                </ul>
                            </li>
                            <li class="sub-menu">
                                <a href=""><i class="md md-trending-up"></i>Charts</a>
                                <ul>
                                    <li><a href="flot-charts.html">Flot Charts</a></li>
                                    <li><a href="other-charts.html">Other Charts</a></li>
                                </ul>
                            </li>
                            <li><a href="/P/partido/calendario"><i class="md md-today"></i> Calendario</a></li>
                            <li><a href="/P/partido/all"><i class="md md-recent-actors"></i> Partidos</a></li>
                            <li class="sub-menu active toggled">
                                <a href=""><i class="md md-content-copy"></i> Sample Pages</a>
                                <ul>
                                    <li><a href="profile-about.html">Profile</a></li>
                                    <li><a class="active" href="list-view.html">List View</a></li>
                                    <li><a href="messages.html">Messages</a></li>
                                    <li><a href="login.html">Login and Sign Up</a></li>
                                    <li><a href="404.html">Error 404</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
                </sec:authorize>

	<!-- jQuery -->
    <script src="/P/resources/js/jquery.js"></script>
    <!-- jQuery -->
    <script src="/P/resources/js/jquery.nicescroll.min.js"></script>
    
     <!-- jQuery AutoSize -->
    <script src="/P/resources/js/jquery.autosize.min.js"></script>
    
    <!-- jQuery Flot -->
    <script src="/P/resources/js/jquery.flot.min.js"></script>
    
    <!-- jQuery Flot -->
    <script src="/P/resources/js/jquery.flot.resize.min.js"></script>
    
    <!-- jQuery Spark Line -->
    <script src="/P/resources/js/jquery.sparkline.min.js"></script>
    
    <!-- jQuery Easy Pie Chart -->
    <script src="/P/resources/js/jquery.easypiechart.min.js"></script>
    
     <!-- Light Gallery -->
    <script src="/P/resources/js/lightGallery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="/P/resources/js/bootstrap.min.js"></script>
    
    <!-- Moment -->
    <script src="/P/resources/js/moment.min.js"></script>
    
    <!-- Bootstrap DateTimePicker -->
    <script src="/P/resources/js/bootstrap-datetimepicker.min.js"></script>
    
    <!-- Bootstrap Select -->
    <script src="/P/resources/js/bootstrap-select.min.js"></script>
    
    <!-- Bootstrap Growl -->
    <script src="/P/resources/js/bootstrap-growl.min.js"></script>
    
    <!-- Charts -->
    <script src="/P/resources/js/curvedLines.js"></script>
    
    <!-- Waves -->
    <script src="/P/resources/js/waves.min.js"></script>
    
    <!-- Fullcalendar -->
    <script src="/P/resources/js/calendar.min.js"></script>
    <!-- i18n Fullcalendar -->
    <script type="text/javascript" src="/P/resources/js/lang-all.js"></script>
    <!-- Functions -->
    <script src="/P/resources/js/functions.js"></script>
    
    
    
    <!-- ANGULAR JS -->
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.21/angular.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.21/angular-resource.js"></script>
    
    
    <!-- Angular JS Controllers -->
    <script src="/P/resources/js/controllers/pachanga.js"></script>
    
    <!-- Angular JS Controllers -->
    <script src="/P/resources/js/controllers/controllers.js"></script>
    
    <!-- Angular JS Controllers -->
    <script src="/P/resources/js/controllers/partidoController.js"></script>
    
    <!-- Angular JS Controllers -->
    <script src="/P/resources/js/controllers/mensajeController.js"></script>
    
    <!-- Angular JS Controllers -->
    <script src="/P/resources/js/controllers/notificacionController.js"></script>
    
    <!-- Angular JS Controllers -->
    <script src="/P/resources/js/controllers/profileController.js"></script>
    
    <!-- Angular JS Controllers -->
    <script src="/P/resources/js/controllers/grupoController.js"></script>
    
    <!-- Angular JS Services -->
    <script src="/P/resources/js/services.js"></script>
    
    <!-- Angular JS Services -->
    <script src="/P/resources/js/services/partidoService.js"></script>
    
    <!-- Angular JS Services -->
    <script src="/P/resources/js/services/profileService.js"></script>
    
    <!-- Angular JS Services -->
    <script src="/P/resources/js/services/grupoService.js"></script>
    
     <!-- Angular JS Services -->
    <script src="/P/resources/js/services/mensajeService.js"></script>
    
    <!-- Angular JS Services -->
    <script src="/P/resources/js/services/notificacionService.js"></script>
    
    <!-- Angular JS Services -->
    <script src="/P/resources/js/services/usuarioService.js"></script>
    
    <!-- Angular JS Directives -->
    <script src="/P/resources/js/controllers/directives.js"></script>
	
	<!-- Fullcalendar -->
    <script src="/P/resources/js/calendar.js"></script>
    
    <!-- Angular Bootstrap DateTimePicker -->
    <script src="/P/resources/js/datetimepicker.js"></script>
	
	
</body>
</html>