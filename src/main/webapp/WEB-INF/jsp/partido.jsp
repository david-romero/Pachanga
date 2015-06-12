<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id = "prettyTime" class = "org.ocpsoft.prettytime.PrettyTime" />
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
		
		
		<div class="container" id="content" >
		
			<jsp:include page="masterPage.jsp"></jsp:include>
                    
                    <div class="block-header">
                        <h2 id="tituloValue"><c:out value="${partido.titulo}"></c:out></h2>
                        
                    </div>
                    
                    <div class="card" id="profile-main" ng-controller="PartidoController">
                        <div tabindex="4" style="overflow: hidden;" class="pm-overview c-overflow">
                            <div class="pmo-pic">
                                <div class="p-relative">
                                    <a href="">
                                    	<c:if test="${partido.id != 0 }">
                                        	<img class="img-responsive" src="${partido.urlImagen}" alt="">
                                        </c:if> 
                                        <c:if test="${partido.id == 0 }">
                                        	<img class="img-responsive" src="${pageContext.request.contextPath}/usuarios/getUserImage/${partido.propietario.id}" alt="">
                                        </c:if>
                                    </a>
                                    <sec:authorize access="isAuthenticated()" >
	                				<sec:authentication var="user" property="principal" />
                                    <c:if test="${partido.propietario.email == user.username }">
                                    	<input type="file" ng-model-instant id="fileToUpload"  onchange="angular.element(this).scope().setFiles(this)" style="display:none;"/>
                                    	
	                                    <a href="" class="pmop-edit" onclick="uploadPhoto();">
	                                        <i class="md md-camera-alt"></i> <span class="hidden-xs">Actualizar Imagen</span>
	                                    </a>
                                    </c:if>
                                    </sec:authorize>
                                </div>
                                
                                
                                <div class="pmo-stat">
                                    <h2 class="m-0 c-white">1562</h2>
                                    Visitas
                                </div>
                            </div>
                            
                            <div class="pmo-block pmo-contact hidden-xs">
                                <h2>Datos</h2>
                                <ul>
                                    <li><i class="md md-alarm"></i> 15/06/2015</li>
                                    <li><i class="md md-attach-money"></i> 0.7 &euro;</li>
                                    <li><i class="md md-gps-fixed"></i> Hytasa</li>
                                    <li>
                                        <i class="md fa fa-map-marker"></i>
                                        <address class="m-b-0">
                                            10098 ABC Towers, <br>
                                            Dubai Silicon Oasis, Dubai, <br>
                                            United Arab Emirates
                                        </address>
                                    </li>
                                </ul>
                            </div>
                            
                            
                        </div>
                        
                        <sec:authorize access="isAuthenticated()" >
                        	<c:if test="${partido.id == 0 }">
	                        	<div class="pmb-block" id="messages-main" style="min-height: 0px;max-height: 0px;padding: 0px;margin: 0px;" >
		                            	<a ng-click="savePartido(formCreatePartido)" class="btn btn-primary btn-float waves-effect waves-button waves-float" id="ms-compose">
				                            <i class="md md-send"></i>
				                        </a>
				                </div>
				            </c:if>
                        </sec:authorize>
                        
                        <div class="pm-body clearfix" ng-controller="ProfileController">
                            <ul tabindex="1" style="overflow: hidden;" class="tab-nav tn-justified">
                            	<li ng-click="activeTab = 1;" class="waves-effect" ng-class="getProfileTabCss(1)"><a href="#">Datos</a></li>
                                <li ng-click="activeTab = 2;" class="waves-effect" ng-class="getProfileTabCss(2)"><a href="#">Jugadores</a></li>
                                
                            </ul>
                            
                            <div ng-if="activeTab == 2">
	                            <div class="pmb-block" >
	                                <div class="col-lg-12">
			                            <div class="card">
			                                <div class="card-header bgm-green">
			                                    <h2>Usuarios Apuntados</h2>
			
			                                    <ul class="actions actions-alt">
			                                        <li class="dropdown">
			                                            <a href="" data-toggle="dropdown" aria-expanded="false">
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
			
			                                <div class="card-body card-padding list-view bgm-green" style="background-image:url('/P/resources/imgs/futbolsala.png');background-repeat: no-repeat;min-height: 415px;">
			                                	<div class="col-lg-12">&nbsp;</div>
			                                    <div class="col-lg-12 "><!-- Fila 1 -->
			                                    	<div class="col-lg-1"><!-- Espacio -->
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Portero -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Defensa -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Medio -->
			                                    		<img style="height: 30px;" class="lv-img-sm profilePartido" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/1" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Ataque -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Espacio -->
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Ataque -->
			                                    		
			                                    	</div>
			                                 		<div class="col-lg-1"><!-- Medio -->
			                                    		<img style="height: 30px;" class="lv-img-sm profilePartido" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/2" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Defensa -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Portero -->
			                                    		
			                                    	</div>
			                                    </div>
			                                    <div class="col-lg-12">&nbsp;</div>
			                                    <div class="col-lg-12"><!-- Fila 2 -->
			                                    	<div class="col-lg-1"><!-- Espacio -->
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Portero -->
			                                    		<img style="height: 30px;" class="lv-img-sm profilePartido" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/1" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Defensa -->
			                                    		<img style="height: 30px;" class="lv-img-sm profilePartido" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/2" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Medio -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Ataque -->
			                                    		<img style="height: 30px;" class="lv-img-sm profilePartido" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/3" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Espacio -->
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Ataque -->
			                                    		<img style="height: 30px;" class="lv-img-sm profilePartido" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/1" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Medio -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Defensa -->
			                                    		<img style="height: 30px;" class="lv-img-sm profilePartido" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/2" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Portero -->
			                                    		<img style="height: 30px;" class="lv-img-sm profilePartido" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/3" alt="">
			                                    	</div>
			                                    	
			                                    </div>
			                                    <div class="col-lg-12">&nbsp;</div>
			                                    <div class="col-lg-12"><!-- Fila 3 -->
			                                    	<div class="col-lg-1"><!-- Espacio -->
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Portero -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Defensa -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Medio -->
			                                    		<img style="height: 30px;" class="lv-img-sm profilePartido" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/1" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Ataque -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Espacio -->
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Ataque -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Medio -->
			                                    		<img style="height: 30px;" class="lv-img-sm profilePartido" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/1" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Defensa -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Portero -->
			                                    		
			                                    	</div>
			                                    </div>
			                                </div>
			                            </div><!-- Card -->
			                            <div class="card">
					                        <div class="card-header">
					                            <h2>Convocatoria <small>Jugadores apuntados al partido</small></h2>
					                        </div>
					                        
					                        <div tabindex="1" style="overflow: hidden;" class="table-responsive">
					                            <table class="table table-striped table-hover">
					                                <thead>
					                                    <tr>
					                                        <th>#</th>
					                                        <th>Avatar</th>
					                                        <th>Nombre</th>
					                                        <th>Apellidos</th>
					                                        <th>Username</th>
					                                         <c:if test="${partido.propietario.id == user.id }">
					                                        	<th>Eliminar</th>
					                                        </c:if>
					                                    </tr>
					                                </thead>
					                                <tbody ng-controller="PartidoController">
					                                	<c:forEach items="${partido.jugadores}" var="jugador">
						                                    <tr>
						                                        <td>1</td>
						                                        <td><img style="height: 30px;border-radius: 50%;" class="lv-img-sm" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/${jugador.id}" alt=""></td>
						                                        <td><c:out value=" ${jugador.firstName}"></c:out></td>
						                                        <td><c:out value=" ${jugador.lastName}"></c:out></td>
						                                        <td><c:out value=" ${jugador.email}"></c:out></td>
						                                         <c:if test="${partido.propietario.id == user.id }">
						                                         	<td>
						                                         		<ul class="actions">
		            														<li>
		                														<a ng-click="eliminarJugador(${partido.id},${jugador.id})" style="cursor: pointer;">
		                    														<i class="md md-delete"></i>
		                														</a>
		            														</li>
		        														</ul>
		        													</td>
						                                         </c:if>
						                                    </tr>
						                               </c:forEach>
					                                </tbody>
					                            </table>
					                        </div>
					                    </div>
			                        </div>
	                            </div>
                            </div><!-- TAB 1 -->
                           <div ng-if="activeTab == 1">
                           		<form action="#" method="POST" name="formCreatePartido" ng-init="setFormScope(this)">
                           		<input type="hidden" name="propietario" ng-model="propietario" ng-init="propietario = ${partido.propietario.id}" ng-value="${partido.propietario.id}">
	                            <div class="pmb-block" >
	                                <div class="pmbb-header">
	                                    <h2><i class="md fa fa-bookmark"></i> Título</h2>
	                                    <sec:authorize access="isAuthenticated()" >
	                                    
                                    	<c:if test="${userSigned.email == user.username }">
		                                    <ul class="actions">
		                                        <li class="dropdown">
		                                            <a href="" data-toggle="dropdown">
		                                                <i class="md md-more-vert"></i>
		                                            </a>
		                                            
		                                            <ul class="dropdown-menu dropdown-menu-right">
		                                                <li>
		                                                    <a id="editarTitulo" data-pmb-action="edit" href="">Edit</a>
		                                                </li>
		                                            </ul>
		                                        </li>
		                                    </ul>
	                                    </c:if>
		                                </sec:authorize>
	                                </div>
	                                <div class="pmbb-body p-l-30">
	                                    <div class="pmbb-view">
	                                        ${partido.titulo}
	                                    </div>
	                                    
	                                    <div class="pmbb-edit">
	                                        <div class="fg-line">
	                                            <textarea ng-model="titulo"  name="titulo" class="form-control" rows="5" placeholder="Summary...">${partido.titulo}
	                                            </textarea>
	                                        </div>

	                                    </div>
	                                </div>
	                            </div>
	                            
	                            <div class="pmb-block">
	                                <div class="pmbb-header">
	                                    <h2><i class="md fa fa-user"></i> Información Básica</h2>
	                                    
	                                    <sec:authorize access="isAuthenticated()" >
                                    	<c:if test="${userSigned.email == user.username }">
		                                    <ul class="actions">
		                                        <li class="dropdown">
		                                            <a href="" data-toggle="dropdown">
		                                                <i class="md md-more-vert"></i>
		                                            </a>
		                                            
		                                            <ul class="dropdown-menu dropdown-menu-right">
		                                                <li>
		                                                    <a id="editarBasica" data-pmb-action="edit" href="">Edit</a>
		                                                </li>
		                                            </ul>
			                                        
		                                        </li>
		                                    </ul>
	                                    </c:if>
		                                </sec:authorize>
	                                </div>
	                                <div class="pmbb-body p-l-30">
	                                    <div class="pmbb-view">
	                                        <dl class="dl-horizontal">
	                                            <dt>Full Name</dt>
	                                            <dd>Mallinda Hollaway</dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt>Precio</dt>
	                                            <dd>${partido.precio}&nbsp;&euro;</dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt>Fecha</dt>
	                                            <dd>${partido.fechaRepresentacion}</dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt>Martial Status</dt>
	                                            <dd>Single</dd>
	                                        </dl>
	                                    </div>
	                                    
	                                    <div class="pmbb-edit">
	                                        <dl class="dl-horizontal">
	                                            <dt class="p-t-10">Categor&iacute;a</dt>
	                                            <dd>
	                                                <div class="fg-line">
	                                                    <select ng-model="categoria" class="form-control selectpicker" name="categoría">
	                                                        <option>F&uacute;tbol</option>
	                                                        <option>F&uacute;tbol 7</option>
	                                                        <option>F&uacute;tbol Sala</option>
	                                                        <option>Paddel</option>
	                                                        <option>Baloncesto</option>
	                                                    </select>
	                                                </div>
	                                                
	                                            </dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt class="p-t-10">Precio</dt>
	                                            <dd>
	                                                <div class="fg-line">
	                                                    <input type="number" ng-model="precio" name="precio" class="form-control" value="${partido.precio}" step="0.1" min="0" />
	                                                </div>
	                                            </dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt class="p-t-10">Fecha</dt>
	                                            <dd>
	                                                <div class="dtp-container dropdown fg-line">
	                                                    <input ng-model="fecha" name="fecha" class="form-control date-time-picker" data-toggle="dropdown" placeholder="${partido.fechaRepresentacion}" type="text" >
	                                                </div>
	                                            </dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt class="p-t-10">Plazas</dt>
	                                            <dd>
	                                                <div class="fg-line">
	                                                    <input type="number" ng-model="plazas" name="plazas" class="form-control" value="${partido.plazas}"  min="0" />
	                                                </div>
	                                            </dd>
	                                        </dl>
	                                        

	                                    </div>
	                                </div>
	                            </div>
	                       
	                        
	                             </form>
                            </div><!-- TAB 2 -->
                        </div>
                    </div>
                </div>
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.js"></script>              
<script src="/P/resources/js/bootstrap-datetimepicker.min.js"></script>              
<script type="text/javascript">
$(document).ready(function () {
	 $('#editarTitulo').click();
	 $('#editarBasica').click();
	 $('#editar').click();
	 $('textarea[name=titulo]').keypress(function(e) {
		 actualizarTitulo($(this),$('#tituloValue'));
	    }).focus(function() {
	    	actualizarTitulo($(this),$('#tituloValue'));
	    }).keydown(function(e) {
		 actualizarTitulo($(this),$('#tituloValue'));
	    }).keyup(function(e) {
		 actualizarTitulo($(this),$('#tituloValue'));
	    });
	//Date
    if ($('.date-time-picker')[0]) {
    	$('.date-time-picker').datetimepicker({
    	    format: 'DD/MM/YYYY HH:mm',
    	    locale: 'es',
    	    minDate: new Date()
    	});
    }

});
	function actualizarTitulo(textarea,label){
		label.html(textarea.val())
	}
	function uploadPhoto(){
		$('input[type=file]').click();
	}
</script>
</body>
</html>