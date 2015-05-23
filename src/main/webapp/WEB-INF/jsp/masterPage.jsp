<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>P</title>

    <!-- Bootstrap Core CSS -->
    <link href="/P/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/P/resources/css/shop-homepage.css" rel="stylesheet">
    
    <!-- APP -->
    <link href="/P/resources/css/app.css" rel="stylesheet">
	
	<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
	
	 <!-- Vendor CSS -->
     <link href="/P/resources/css/fullcalendar.css" rel="stylesheet">
     <link href="/P/resources/css/animate.min.css" rel="stylesheet">
     <link href="/P/resources/css/sweet-alert.min.css" rel="stylesheet">
     <link href="/P/resources/css/material-design-iconic-font.min.css" rel="stylesheet">
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
    
    <style type="text/css">
    	@media (min-width: 1200px){
			.top-menu #toggle-width .toggle-switch {
			  margin: -22px 30px 0 0;
			}
		}
    </style>
    
</head>
<body>

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
                    <li class="dropdown">
                        <a data-toggle="dropdown" class="tm-message" href=""><i class="tmn-counts">6</i></a>
                        <div class="dropdown-menu dropdown-menu-lg pull-right">
                            <div class="listview">
                                <div class="lv-header">
                                    Mensajes
                                </div>
                                <div tabindex="1" style="overflow: hidden;" class="lv-body c-overflow">
                                    <a class="lv-item" href="">
                                        <div class="media">
                                            <div class="pull-left">
                                                <img class="lv-img-sm" src="img/profile-pics/1.jpg" alt="">
                                            </div>
                                            <div class="media-body">
                                                <div class="lv-title">David Belle</div>
                                                <small class="lv-small">Cum sociis natoque penatibus et magnis dis parturient montes</small>
                                            </div>
                                        </div>
                                    </a>
                                    <a class="lv-item" href="">
                                        <div class="media">
                                            <div class="pull-left">
                                                <img class="lv-img-sm" src="img/profile-pics/2.jpg" alt="">
                                            </div>
                                            <div class="media-body">
                                                <div class="lv-title">Jonathan Morris</div>
                                                <small class="lv-small">Nunc quis diam diamurabitur at dolor elementum, dictum turpis vel</small>
                                            </div>
                                        </div>
                                    </a>
                                    <a class="lv-item" href="">
                                        <div class="media">
                                            <div class="pull-left">
                                                <img class="lv-img-sm" src="img/profile-pics/3.jpg" alt="">
                                            </div>
                                            <div class="media-body">
                                                <div class="lv-title">Fredric Mitchell Jr.</div>
                                                <small class="lv-small">Phasellus a ante et est ornare accumsan at vel magnauis blandit turpis at augue ultricies</small>
                                            </div>
                                        </div>
                                    </a>
                                    <a class="lv-item" href="">
                                        <div class="media">
                                            <div class="pull-left">
                                                <img class="lv-img-sm" src="img/profile-pics/4.jpg" alt="">
                                            </div>
                                            <div class="media-body">
                                                <div class="lv-title">Glenn Jecobs</div>
                                                <small class="lv-small">Ut vitae lacus sem ellentesque maximus, nunc sit amet varius dignissim, dui est consectetur neque</small>
                                            </div>
                                        </div>
                                    </a>
                                    <a class="lv-item" href="">
                                        <div class="media">
                                            <div class="pull-left">
                                                <img class="lv-img-sm" src="img/profile-pics/4.jpg" alt="">
                                            </div>
                                            <div class="media-body">
                                                <div class="lv-title">Bill Phillips</div>
                                                <small class="lv-small">Proin laoreet commodo eros id faucibus. Donec ligula quam, imperdiet vel ante placerat</small>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                                <a href="/P/usuarios/chat" class="lv-footer" href="">Ver Todos</a>
                            </div>
                        <div style="width: 0px; z-index: 9; cursor: default; position: absolute; top: 0px; left: 298px; height: 275px; display: block; opacity: 0;" class="nicescroll-rails nicescroll-rails-vr" id="ascrail2002"><div class="nicescroll-cursors" style="position: relative; top: 0px; float: right; width: 0px; height: 271px; background-color: rgba(0, 0, 0, 0.5); border: 0px none; background-clip: padding-box; border-radius: 0px;"></div></div><div style="height: 0px; z-index: 9; top: 275px; left: 0px; position: absolute; cursor: default; display: none; width: 298px; opacity: 0;" class="nicescroll-rails nicescroll-rails-hr" id="ascrail2002-hr"><div class="nicescroll-cursors" style="position: absolute; top: 0px; height: 0px; width: 298px; background-color: rgba(0, 0, 0, 0.5); border: 0px none; background-clip: padding-box; border-radius: 0px;"></div></div></div>
                    </li>
                    <li class="dropdown">
                        <a data-toggle="dropdown" class="tm-notification" href=""><i class="tmn-counts">9</i></a>
                        <div class="dropdown-menu dropdown-menu-lg pull-right">
                            <div class="listview" id="notifications">
                                <div class="lv-header">
                                    Notificaci&oacute;n
                    
                                    <ul class="actions">
                                        <li class="dropdown">
                                            <a href="" data-clear="notification">
                                                <i class="md md-done-all"></i>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                                <div tabindex="2" style="overflow: hidden;" class="lv-body c-overflow">
                                    <a class="lv-item" href="">
                                        <div class="media">
                                            <div class="pull-left">
                                                <img class="lv-img-sm" src="img/profile-pics/1.jpg" alt="">
                                            </div>
                                            <div class="media-body">
                                                <div class="lv-title">David Belle</div>
                                                <small class="lv-small">Cum sociis natoque penatibus et magnis dis parturient montes</small>
                                            </div>
                                        </div>
                                    </a>
                                    <a class="lv-item" href="">
                                        <div class="media">
                                            <div class="pull-left">
                                                <img class="lv-img-sm" src="img/profile-pics/2.jpg" alt="">
                                            </div>
                                            <div class="media-body">
                                                <div class="lv-title">Jonathan Morris</div>
                                                <small class="lv-small">Nunc quis diam diamurabitur at dolor elementum, dictum turpis vel</small>
                                            </div>
                                        </div>
                                    </a>
                                    <a class="lv-item" href="">
                                        <div class="media">
                                            <div class="pull-left">
                                                <img class="lv-img-sm" src="img/profile-pics/3.jpg" alt="">
                                            </div>
                                            <div class="media-body">
                                                <div class="lv-title">Fredric Mitchell Jr.</div>
                                                <small class="lv-small">Phasellus a ante et est ornare accumsan at vel magnauis blandit turpis at augue ultricies</small>
                                            </div>
                                        </div>
                                    </a>
                                    <a class="lv-item" href="">
                                        <div class="media">
                                            <div class="pull-left">
                                                <img class="lv-img-sm" src="img/profile-pics/4.jpg" alt="">
                                            </div>
                                            <div class="media-body">
                                                <div class="lv-title">Glenn Jecobs</div>
                                                <small class="lv-small">Ut vitae lacus sem ellentesque maximus, nunc sit amet varius dignissim, dui est consectetur neque</small>
                                            </div>
                                        </div>
                                    </a>
                                    <a class="lv-item" href="">
                                        <div class="media">
                                            <div class="pull-left">
                                                <img class="lv-img-sm" src="img/profile-pics/4.jpg" alt="">
                                            </div>
                                            <div class="media-body">
                                                <div class="lv-title">Bill Phillips</div>
                                                <small class="lv-small">Proin laoreet commodo eros id faucibus. Donec ligula quam, imperdiet vel ante placerat</small>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                    
                                <a href="/P/usuarios/notificaciones" class="lv-footer" href="">Ver todas</a>
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
		                        <img class="profile-nav" src="${pageContext.request.contextPath}/usuarios/getUserImage/${user.username}" alt="profile">
		                        <!--<c:out value="${user.username}" /> <i class="fa fa-angle-down"></i>-->
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
                                    <img src="${pageContext.request.contextPath}/usuarios/getUserImage/${user.username}" alt="<c:out value="${user.username}" />">
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
                            <li><a href="/P/app"><i class="md md-home"></i> Home</a></li>
                            <li><a href="typography.html"><i class="md md-format-underline"></i> Typography</a></li>
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
                            <li><a href="calendar.html"><i class="md md-today"></i> Calendar</a></li>
                            <li><a href="generic-classes.html"><i class="md md-layers"></i> Generic Classes</a></li>
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

    <!-- Bootstrap Core JavaScript -->
    <script src="/P/resources/js/bootstrap.min.js"></script>
    
    <!-- Waves -->
    <script src="/P/resources/js/waves.min.js"></script>
    <!-- Moment -->
    <script src="/P/resources/js/moment.min.js"></script>
    <!-- Fullcalendar -->
    <script src="/P/resources/js/calendar.min.js"></script>
    <!-- Functions -->
    <script src="/P/resources/js/functions.js"></script>
    
    <!-- ANGULAR JS -->
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.21/angular.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.21/angular-resource.js"></script>
    
    
    <!-- Angular JS Services -->
    <script src="/P/resources/js/services.js"></script>
    
    <!-- Angular JS Controllers -->
    <script src="/P/resources/js/controllers/controllers.js"></script>

</body>
</html>