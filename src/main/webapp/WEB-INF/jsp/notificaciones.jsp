<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" >

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
    


</head>

<body ng-app="pachanga">
<sec:authorize access="isAuthenticated()">
	<jsp:include page="masterPage.jsp"></jsp:include>
	<sec:authentication var="user" property="principal" />
	<div class="container" id="content" >
	
	    <div ng-controller="NotificacionController" ng-init="loadAllNotificacionesSinLeer();">
                    <div class="block-header">
                        <h2>Notificaciones</h2>
                    </div>
                
                    <div class="card" >
                        <div class="listview lv-bordered lv-lg" >
                            <div class="lv-header-alt">
                                
                                
                                <ul class="lv-actions actions">
                                    <li>
                                    	<a href="" data-clear="notification" ng-click="leerTodasNoLeidas()">
                                                <i class="md md-done-all"></i>
                                        </a>
                                    </li>
                                    <li>
                                    	<a href="" data-clear="seleccionados" ng-click="leerSeleccionadas()">
                                                <i class="md md-done"></i>
                                        </a>
                                    </li>
                                    <li>
                                    	<a href="" data-clear="seleccionados" ng-click="eliminarSeleccionadas()">
                                                <i class="md md-delete"></i>
                                        </a>
                                    </li>
                                    <li class="dropdown">
                                        <a href="" data-toggle="dropdown" aria-expanded="true">
                                            <i class="md md-sort"></i>
                                        </a>
                            
                                        <ul class="dropdown-menu dropdown-menu-right">
                                            <li>
                                                <a href="">Last Modified</a>
                                            </li>
                                            <li>
                                                <a href="">Last Edited</a>
                                            </li>
                                            <li>
                                                <a href="">Name</a>
                                            </li>
                                            <li>
                                                <a href="">Date</a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="">
                                            <i class="md md-info"></i>
                                        </a>
                                    </li>
                                    <li class="dropdown">
                                        <a href="" data-toggle="dropdown" aria-expanded="true">
                                            <i class="md md-more-vert"></i>
                                        </a>
                            
                                        <ul class="dropdown-menu dropdown-menu-right">
                                            <li ng-click="loadNotificaciones(paginaActual)">
                                                <a href="">Refresh</a>
                                            </li>
                                            <li>
                                                <a href="">Listview Settings</a>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </div>
                            
                            <div class="lv-body">
                                <div class="lv-item media" ng-repeat="notificacion in notificaciones"><!-- Bloque Notificacion -->
                                    <div class="checkbox pull-left">
                                        <label>
                                            <input ng-click="addToSelected(notificacion)" type="checkbox" value="{{notificacion.id}}" ng-value="{{notificacion.id}}">
                                            <i class="input-helper"></i>
                                        </label>
                                    </div>
                                    <div class="pull-left">
                                        <img class="lv-img-sm" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/{{notificacion.emisor.id}}" alt="">
                                    </div>
                                    <div class="media-body">
                                        <div class="lv-title">{{notificacion.titulo}}</div>
                                        <small class="lv-small">{{notificacion.contenido}}</small>
                                        <ul class="lv-attrs">
                                            <li>{{notificacion.fechaRepresentacion}}</li>
                                            <li ng-if="notificacion.propietario != null">Usuarios: 78954</li>
     
                                        </ul>
                                        
                                        <div class="lv-actions actions dropdown">
                                            <a href="" data-toggle="dropdown" aria-expanded="true">
                                                <i class="md md-more-vert"></i>
                                            </a>
                                
                                            <ul class="dropdown-menu dropdown-menu-right">
                                                <li ng-show="!notificacion.leido">
                                                    <a href="">Marcar como leida</a>
                                                </li>
                                                <li>
                                                    <a href="">Eliminar</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </div>
                        </div>
                        
                        
                    </div>
                </div>
</sec:authorize>
</body>
</html>