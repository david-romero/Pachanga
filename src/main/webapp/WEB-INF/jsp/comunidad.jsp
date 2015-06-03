<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<sec:authorize access="isAuthenticated()" >
		<jsp:include page="masterPage.jsp"></jsp:include>
		
		<div class="container" id="content" ng-app="pachanga">
                    
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
                    
                    <div class="card" id="profile-main" ng-controller="GrupoController">
                        
                        <div tabindex="4" style="overflow: hidden;" class="pm-overview c-overflow" ng-init="urlComunidad='${pageContext.request.contextPath}/rest/comunidad/getImage/${grupo.id}'">
                            <div class="pmo-pic">
                                <div class="p-relative">
                                    <a href="">
                                    	<c:if test="${grupo.id != 0 }">
                                        	<img id="imagenGrupo" class="img-responsive" ng-src="{{urlComunidad}}" alt="">
                                        </c:if> 
                                        <c:if test="${grupo.id == 0 }">
                                        	<img class="img-responsive" src="${pageContext.request.contextPath}/usuarios/getUserImage/1" alt="">
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
                                    <h2 class="m-0 c-white"><c:out value="${grupo.usuarios.size()}"></c:out> </h2>
                                    Usuarios
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
                        
                        <div class="pm-body clearfix" ng-controller="ProfileController">
                            <ul tabindex="1" style="overflow: hidden;" class="tab-nav tn-justified">
                                <li ng-click="activeTab = 1;" class="waves-effect" ng-class="getProfileTabCss(1)"><a href="#">Usuarios</a></li>
                                <li ng-click="activeTab = 2;" class="waves-effect" ng-class="getProfileTabCss(2)"><a href="#">Mensajes</a></li>
                                <li ng-click="activeTab = 3;" class="waves-effect" ng-class="getProfileTabCss(3)"><a href="#">Partidos</a></li>
                            </ul>
                            
                            <div ng-if="activeTab == 1">
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
	                                
	                                <div class="contacts clearfix row">
	                                	<c:forEach items="${grupo.usuarios}" var="usuario">
		                                    <div class="col-md-3 col-sm-6 col-xs-6" ng-click="showUser(${usuario.id})">
		                                        <div class="c-item">
		                                            <a href="" class="ci-avatar">
		                                                <img src="${pageContext.request.contextPath}/usuarios/getUserImage/${usuario.id}" alt="">
		                                            </a>
		                                            
		                                            <div class="c-info">
		                                                <strong><c:out value="${usuario.firstName}"></c:out>&nbsp;<c:out value="${usuario.lastName}"></c:out> </strong>
		                                                <small><c:out value="${usuario.email}"></c:out></small>
		                                            </div>
		                                        </div>
		                                    </div>
	                                    </c:forEach>
	                                </div>
	                                
	                            </div>
                            </div><!-- TAB 1 -->
                           <div ng-if="activeTab == 2">
	                            <div class="pmb-block" id="messages-main" >
	                            	<div class="ms-body" style="padding-left: 0px;">
			                            <div class="listview lv-message" >
			                                <div class="lv-body" nice-scroll>                                    
			                                    <div class="lv-item media">
			                                        <div class="lv-avatar pull-left">
			                                            <img src="img/profile-pics/1.jpg" alt="">
			                                        </div>
			                                        <div class="media-body">
			                                            <div class="ms-item">
			                                                Quisque consequat arcu eget odio cursus, ut tempor arcu vestibulum. Etiam ex arcu, porta a urna non, lacinia pellentesque orci. Proin semper sagittis erat, eget condimentum sapien viverra et. Mauris volutpat magna nibh, et condimentum est rutrum a. Nunc sed turpis mi. In eu massa a sem pulvinar lobortis.
			                                            </div>
			                                            <small class="ms-date"><i class="md md-access-time"></i> 20/02/2015 at 09:00</small>
			                                        </div>
			                                    </div>
			                                    
			                                    <div class="lv-item media right">
			                                        <div class="lv-avatar pull-right">
			                                            <img src="img/profile-pics/8.jpg" alt="">
			                                        </div>
			                                        <div class="media-body">
			                                            <div class="ms-item">
			                                                Mauris volutpat magna nibh, et condimentum est rutrum a. Nunc sed turpis mi. In eu massa a sem pulvinar lobortis.
			                                            </div>
			                                            <small class="ms-date"><i class="md md-access-time"></i> 20/02/2015 at 09:30</small>
			                                        </div>
			                                    </div>
			                                    
			                                    <div class="lv-item media">
			                                        <div class="lv-avatar pull-left">
			                                            <img src="img/profile-pics/1.jpg" alt="">
			                                        </div>
			                                        <div class="media-body">
			                                            <div class="ms-item">
			                                                Etiam ex arcumentum
			                                            </div>
			                                            <small class="ms-date"><i class="md md-access-time"></i> 20/02/2015 at 09:33</small>
			                                        </div>
			                                    </div>
			                                    
			                                    <div class="lv-item media right">
			                                        <div class="lv-avatar pull-right">
			                                            <img src="img/profile-pics/8.jpg" alt="">
			                                        </div>
			                                        <div class="media-body">
			                                            <div class="ms-item">
			                                                Etiam nec facilisis lacus. Nulla imperdiet augue ullamcorper dui ullamcorper, eu laoreet sem consectetur. Aenean et ligula risus. Praesent sed posuere sem. Cum sociis natoque penatibus et magnis dis parturient montes,
			                                            </div>
			                                            <small class="ms-date"><i class="md md-access-time"></i> 20/02/2015 at 10:10</small>
			                                        </div>
			                                    </div>
			                                    
			                                    <div class="lv-item media">
			                                        <div class="lv-avatar pull-left">
			                                            <img src="img/profile-pics/1.jpg" alt="">
			                                        </div>
			                                        <div class="media-body">
			                                            <div class="ms-item">
			                                                Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam ac tortor ut elit sodales varius. Mauris id ipsum id mauris malesuada tincidunt. Vestibulum elit massa, pulvinar at sapien sed, luctus vestibulum eros. Etiam finibus tristique ante, vitae rhoncus sapien volutpat eget
			                                            </div>
			                                            <small class="ms-date"><i class="md md-access-time"></i> 20/02/2015 at 10:24</small>
			                                        </div>
			                                    </div>
			                                </div>
			                                
			                                <div class="lv-footer ms-reply">
			                                    <textarea placeholder="What's on your mind..."></textarea>
			                                    
			                                    <button><i class="md md-send"></i></button>
			                                </div>
			                            </div>
			                        </div>
	                            </div>    
                            </div><!-- TAB 2 -->
                            <div ng-if="activeTab == 3" ng-controller="PartidoController" ng-init="getPartidosComunidad(${grupo.id})" class="col-md-12 col-sm-12" style="margin-top: 15px;">
                            		 <div ng-repeat='partido in partidos' class="col-md-3 col-sm-6" >
		                                <div  id="best-selling" class="dash-widget-item card">
		                                    <div class="dash-widget-header card-header" style="padding: 0;" ng-click="showPartido(partido.id)">
		                                        <div class="dash-widget-title">{{partido.titulo}}</div>
		                                        <img src="{{partido.urlImagen}}" alt="">
		                                        <div class="main-item">
		                                            <small>{{partido.lugar.titulo}} - {{partido.fechaRepresentacion}}</small>
		                                            <h2>{{partido.precio}}&euro;<small>{{ (partido.plazas * 1) - (partido.jugadores.length * 1) }} <i class="md md-account-child"></i></small></h2>
		                                        </div>
		                                        <sec:authorize access="isAuthenticated()" >
		                                        	<div ng-if="!comprobarSiEstaApuntado(partido,'${user.username}')">
			                                        	<button ng-if="isFull(partido)"  class="btn bgm-red btn-float waves-effect waves-button waves-float" style="cursor: initial;" title="Completo">
			                                        		<i class="md md-dnd-on"></i>
			                                        	</button>
			                                        	<button ng-if="!isFull(partido) && !isInDate(partido)"  class="btn bgm-red btn-float waves-effect waves-button waves-float" style="cursor: initial;" title="Fecha Pasada">
			                                        		<i class="md md-timer-off"></i>
			                                        	</button>
			                                        	<button ng-if="isInDate(partido) && !isFull(partido)" ng-click="apuntarseAPartido(partido.id)" class="btn bgm-cyan btn-float waves-effect waves-button waves-float" title="Apuntate">
			                                        		<i class="md md-person-add"></i>
			                                        	</button>
		                                        	</div>
		                                        	<div ng-if="comprobarSiEstaApuntado(partido,'${user.username}')"> 
			                                        	<button  class="btn bgm-green btn-float waves-effect waves-button waves-float" style="cursor: initial;" title="Ya Apuntado">
			                                        		<i class="md md-check"></i>
			                                        	</button>
			                                        </div>
		                                        </sec:authorize>
		                                    </div>
		                                
		                                    <div class="listview p-t-5">
		                                        <a href="/P/usuarios/profile/{{jugador.id}}" class="lv-item" href="" ng-repeat='jugador in partido.jugadores'>
		                                            <div class="media">
		                                                <div class="pull-left">
		                                                    <img class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/{{jugador.email}}" alt="">
		                                                </div>
		                                                <div class="media-body">
		                                                    <div class="lv-title">{{jugador.email}}</div>
		                                                    <small class="lv-small">
		                                                    	<i class="md md-star"></i>
		                                                    	<i class="md md-star"></i>
		                                                    	<i class="md md-star"></i>
		                                                    	<i class="md md-star-outline"></i>
		                                                    	<i class="md md-star-outline"></i>
		                                                    </small>
		                                                </div>
		                                            </div>
		                                        </a>
		                                        
		                                        <a class="lv-footer" href="/P/partido/show/{{partido.id}}">
		                                            Ver detalles
		                                        </a>
		                                    </div>
		                                </div>
                           			 </div>
                            	<div class="pmb-block" id="messages-main" style="padding: 0px;min-height: 0px;max-height: 0px;margin: 0px;" >
	                            	<a href="/P/partido/create" class="btn bgm-red btn-float waves-effect waves-button waves-float" id="ms-compose">
			                            <i class="md md-add"></i>
			                        </a>
			                    </div>
                            </div><!-- TAB 3 -->
                        </div>
                    </div>
                </div>
                <script type="text/javascript">
                function uploadPhoto(){
            		$('input[type=file]').click();
            	}
                </script>
</sec:authorize>