<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id = "prettyTime" class = "org.ocpsoft.prettytime.PrettyTime" />
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<sec:authorize access="isAuthenticated()" >
		<jsp:include page="masterPage.jsp"></jsp:include>
		
		<div class="container" id="content" ng-controller="GrupoController">
			<div class="block-header">
                        <h2><c:out value="${grupo.titulo}"></c:out></h2>
                        <ul class="actions">
                            <li class="dropdown">
                                <a href="" data-toggle="dropdown">
                                    <i class="md md-more-vert"></i>
                                </a>
                                
                                <ul class="dropdown-menu dropdown-menu-right">
                                    <li>
                                        <a data-pmb-action="edit" href="">Edit</a>
                                    </li>
                                </ul>
                             
                            </li>
                        </ul>
                    </div>
                    
                    <div class="card" id="profile-main" >
                        <c:if test="${not empty grupo.id}">
                        <div tabindex="4"  style="overflow: hidden;" class="pm-overview c-overflow" ng-init="urlComunidad='${pageContext.request.contextPath}/rest/comunidad/getImage/${grupo.id}';idComunidad=${grupo.id}">
                        </c:if>
                        <c:if test="${empty grupo.id}">
                        	<div tabindex="4"  style="overflow: hidden;" class="pm-overview c-overflow" ng-init="">
                        </c:if>
                            <div class="pmo-pic">
                                <div class="p-relative">
                                    <a href="" title="${grupo.id}">
                                    	<c:if test="${not empty grupo.id}">
                                        	<img id="imagenGrupo" class="img-responsive" ng-src="{{urlComunidad}}" alt="">
                                        </c:if> 
                                        <c:if test="${empty grupo.id}">
                                        	<img class="img-responsive" src="${pageContext.request.contextPath}/resources/imgs/profile-pics/4.jpg" alt="">
                                        </c:if>
                                    </a>
                                    <sec:authorize access="isAuthenticated()" >
	                				<sec:authentication var="user" property="principal" />
	                                    <input type="file" ng-model-instant id="fileToUpload"  onchange="angular.element(this).scope().idComunidad=${grupo.id};angular.element(this).scope().setFiles(this)" style="display:none;"/>
                                    	
	                                    <a href="" class="pmop-edit" onclick="uploadPhoto();">
	                                        <i class="md md-camera-alt"></i> <span class="hidden-xs">Actualizar Imagen</span>
	                                    </a>
                                    </sec:authorize>
                                </div>
                                
                                
                                <div class="pmo-stat">
                                    <h2 class="m-0 c-white"><c:out value="${grupo.size}"></c:out> </h2>
                                    Usuarios
                                </div>
                            </div>
                            
                            <div class="pmo-block pmo-contact hidden-xs">
                                <h2>Datos</h2>
                                <ul>
                                    <li><i class="md md-alarm"></i> <c:out value="${grupo.fechaRepresentacion}"></c:out></li>
                                </ul>
                            </div>
                        </div>
                        
                        <div class="pm-body clearfix" >
                            <ul tabindex="1" style="overflow: hidden;" class="tab-nav tn-justified" ng-init="activeTab = 1;" >
                                <li ng-click="activeTab = 1;" class="waves-effect" ng-class="activeTab == 1 ? 'active' : ''"><a href="#">Datos</a></li>
                                <li ng-click="activeTab = 2;" class="waves-effect" ng-class="activeTab == 2 ? 'active' : ''"><a href="#">Usuarios</a></li>
                            </ul>
                            
                            <div ng-if="activeTab == 1" >
                            	<form action="#" method="POST" name="formCreateGrupo" ng-init="setFormScope(this)">
	                            	<div class="pmb-block" >
		                                <div class="pmbb-header">
		                                    <h2><i class="md fa fa-bookmark"></i> Nombre</h2>
		                                </div>
		                                <div class="pmbb-body p-l-30">
	                                        <div class="fg-line">
	                                            <textarea ng-model="titulo"  name="titulo" class="form-control" rows="5" placeholder="Summary...">${partido.titulo}
	                                            </textarea>
	                                        </div>
		                                </div>
		                            </div>
                            	</form>
                            </div>
                            
                            <div ng-if="activeTab == 2" >
	                            <div class="pmb-block">
	                                <div class="p-header">
	                                    
	                                    
	                                    <ul class="actions m-t-20 hidden-xs">
	                                        <li class="dropdown">
	                                            <a href="" data-toggle="dropdown">
	                                                <i class="md md-more-vert"></i>
	                                            </a>
	                                
	                                            <ul class="dropdown-menu dropdown-menu-right">
	                                                <li>
	                                                    <a href="">Refresh</a>
	                                                </li>
	                                                <li>
	                                                    <a href="">Settings</a>
	                                                </li>
	                                            </ul>
	                                        </li>
	                                    </ul>
	                                </div>
	                                
	                                <div class="contacts clearfix row" >
	                                	<c:forEach items="${usuariosGrupo}" var="usuario">
		                                    <div class="col-md-3 col-sm-6 col-xs-6" ng-click="showUser(${usuario.id})">
		                                        <div class="c-item">
		                                            <a href="/P/usuarios/profile/${usuario.id}" class="ci-avatar">
		                                                <img src="${pageContext.request.contextPath}/usuarios/getUserImage/${usuario.id}" alt="">
		                                            </a>
		                                            
		                                            <div class="c-info">
		                                                <strong><c:out value="${usuario.firstName}"></c:out>&nbsp;<c:out value="${usuario.lastName}"></c:out> </strong>
		                                                <small><c:out value="${usuario.email}"></c:out></small>
		                                            </div>
		                                        </div>
		                                    </div>
	                                    </c:forEach>
	                                    <div class="col-md-3 col-sm-6 col-xs-6" ng-repeat="usuario in usuariosGrupo" >
		                                        <div class="c-item">
		                                            <a href="/P/usuarios/profile/{{usuario.id}}" class="ci-avatar">
		                                                <img ng-if="usuario.tieneAvatar" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/{{usuario.id}}" alt="">
		                                                <img ng-if="!usuario.tieneAvatar" ng-src="${pageContext.request.contextPath}/resources/imgs/profile-pics/{{getProfileImg(usuario)}}.jpg" alt="">
		                                            </a>
		                                            
		                                            <div class="c-info">
		                                            	<strong ng-if="hasProfileName(usuario)">{{usuario.firstName}} {{usuario.lastName}}</strong>
		                                            	<strong ng-if="!hasProfileName(usuario)">{{split(usuario.email,'@',0)}}</strong>
		                                            </div>
		                                            
		                                            <div class="c-footer ">
		                                                <button ng-click="eliminarUsuarioGrupo(usuario)" class="waves-effect"><i class="md md-delete"></i> Eliminar</button>
		                                            </div>
		                                        </div>
		                                    </div>
	                                </div>
	                                
	                                <hr>
	                                
	                                <div class="contacts clearfix row" ng-init="obtenerUsuariosCandidatos()" >
	                                
	                                	<div class="p-header">
		                                    <ul class="p-menu">
		                                        <li class="active"><a href=""><i class="md md-person-add hidden-xs"></i> Recommanded</a></li>
		                                        <li><a href=""><i class="md md-people hidden-xs"></i> Connected</a></li>
		                                        <li class="pm-search">
		                                            <div class="pms-inner">
		                                                <i class="md md-search"></i>
		                                                <input placeholder="Buscar..." type="text" ng-model="user.name"  ng-model-options="{ debounce: 1000 }" ng-change="update()" >
		                                            </div>
		                                        </li>
		                                    </ul>
		                                    
		                                    <ul class="actions m-t-20 hidden-xs">
		                                        <li class="dropdown">
		                                            <a href="" data-toggle="dropdown">
		                                                <i class="md md-more-vert"></i>
		                                            </a>
		                                
		                                            <ul class="dropdown-menu dropdown-menu-right">
		                                                <li>
		                                                    <a href="">Refresh</a>
		                                                </li>
		                                                <li>
		                                                    <a href="">Settings</a>
		                                                </li>
		                                            </ul>
		                                        </li>
		                                    </ul>
		                                </div>
	             
		                                <div class="col-md-3 col-sm-6 col-xs-6"  ng-repeat="usuario in usuariosCandidatos">
	                                        <div class="c-item animation-demo">
	                                            <a href="/P/usuarios/profile/{{usuario.id}}" class="ci-avatar">
	                                                <img ng-if="usuario.tieneAvatar" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/{{usuario.id}}" alt="">
	                                                <img ng-if="!usuario.tieneAvatar" ng-src="${pageContext.request.contextPath}/resources/imgs/profile-pics/{{getProfileImg(usuario)}}.jpg" alt="">
	                                            </a>
	                                            
	                                            <div class="c-info">
	                                            	<strong ng-if="hasProfileName(usuario)">{{usuario.firstName}} {{usuario.lastName}}</strong>
	                                            	<strong ng-if="!hasProfileName(usuario)">{{split(usuario.email,'@',0)}}</strong>
	                                            </div>
	                                            
	                                            <div class="c-footer ">
	                                                <button ng-click="aniadirUsuarioAGrupo(usuario)" class="waves-effect"><i class="md md-person-add"></i> Add</button>
	                                            </div>
	                                        </div>
	                                    </div>
	                                    
	                                    
	                                </div><!-- End Contacts -->
	                                
	                            </div>
                            </div><!-- TAB 1 -->
                        </div>
                        <div class="pmb-block" id="messages-main" style="min-height: 0px;max-height: 0px;padding: 0px;margin: 0px;" >
                           	<a ng-click="saveGrupo(formCreateGrupo)" class="btn btn-primary btn-float waves-effect waves-button waves-float" id="ms-compose">
	                            <i class="md md-send"></i>
	                        </a>
		                </div>
                    </div>
		</div>
		
</sec:authorize>
<script type="text/javascript">
$(document).ready(function(){
    $('body').on('click', '.animation-demo .c-footer .waves-effect', function(){
        var animation = 'zoomOut';
        var card = $(this).closest('.c-item')
        var cardImg = card.find('img')
        if (animation === "hinge") {
            animationDuration = 2100;
        }
        else {
            animationDuration = 700;
        }
        
        cardImg.removeAttr('class');
        cardImg.addClass('animated '+animation);
        
        setTimeout(function(){
            card.hide();
        }, animationDuration);
    });
});
</script>
</body>
</html>