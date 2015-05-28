<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<jsp:include page="masterPage.jsp"></jsp:include>
		
		<div class="container" id="content" ng-app="pachanga">
                    
                    <div class="block-header">
                        <h2><c:out value="${partido.titulo}"></c:out></h2>
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
                    
                    <div class="card" id="profile-main" ng-controller="PartidoController">
                        <div tabindex="4" style="overflow: hidden;" class="pm-overview c-overflow">
                            <div class="pmo-pic">
                                <div class="p-relative">
                                    <a href="">
                                    	<c:if test="${partido.id != 0 }">
                                        	<img class="img-responsive" src="${partido.urlImagen}" alt="">
                                        </c:if> 
                                        <c:if test="${partido.id == 0 }">
                                        	<img class="img-responsive" src="${pageContext.request.contextPath}/usuarios/getUserImage/${partido.propietario.email}" alt="">
                                        </c:if>
                                    </a>
                                    <sec:authorize access="isAuthenticated()" >
	                				<sec:authentication var="user" property="principal" />
                                    <c:if test="${partido.propietario.email == user.username }">
	                                    <a href="" class="pmop-edit">
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
		                            	<a ng-click="savePartido()" class="btn btn-primary btn-float waves-effect waves-button waves-float" id="ms-compose">
				                            <i class="md md-send"></i>
				                        </a>
				                </div>
				            </c:if>
                        </sec:authorize>
                        
                        <div class="pm-body clearfix" ng-controller="ProfileController">
                            <ul tabindex="1" style="overflow: hidden;" class="tab-nav tn-justified">
                                <li ng-click="activeTab = 1;" class="waves-effect" ng-class="getProfileTabCss(1)"><a href="#">Jugadores</a></li>
                                <li ng-click="activeTab = 2;" class="waves-effect" ng-class="getProfileTabCss(2)"><a href="#">Datos</a></li>
                            </ul>
                            
                            <div ng-if="activeTab == 1">
	                            <div class="pmb-block" >
	                                <div class="col-lg-12">
			                            <div class="card">
			                                <div class="card-header bgm-green">
			                                    <h2>Usuarios Apuntados<small>You can type anything here...</small></h2>
			
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
			
			                                <div class="card-body card-padding list-view" style="min-height: 200px;">
			                                	<div class="col-lg-12">&nbsp;</div>
			                                    <div class="col-lg-12 "><!-- Fila 1 -->
			                                    	<div class="col-lg-1"><!-- Espacio -->
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Portero -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Defensa -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Medio -->
			                                    		<img style="height: 30px;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Ataque -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Espacio -->
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Ataque -->
			                                    		
			                                    	</div>
			                                 		<div class="col-lg-1"><!-- Medio -->
			                                    		<img style="height: 30px;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt="">
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
			                                    		<img style="height: 30px;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Defensa -->
			                                    		<img style="height: 30px;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Medio -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Ataque -->
			                                    		<img style="height: 30px;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Espacio -->
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Ataque -->
			                                    		<img style="height: 30px;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Medio -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Defensa -->
			                                    		<img style="height: 30px;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Portero -->
			                                    		<img style="height: 30px;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt="">
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
			                                    		<img style="height: 30px;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt="">
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Ataque -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Espacio -->
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Ataque -->
			                                    		
			                                    	</div>
			                                    	<div class="col-lg-1"><!-- Medio -->
			                                    		<img style="height: 30px;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt="">
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
					                            <h2>Lista <small>Jugadores apuntados al partido</small></h2>
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
					                                         <c:if test="${partido.propietario.email == user.username }">
					                                        <th>Eliminar</th>
					                                        </c:if>
					                                    </tr>
					                                </thead>
					                                <tbody>
					                                    <tr>
					                                        <td>1</td>
					                                        <td><img style="height: 30px;border-radius: 50%;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt=""></td>
					                                        <td>Alexandra</td>
					                                        <td>Christopher</td>
					                                        <td>@makinton</td>
					                                         <c:if test="${partido.propietario.email == user.username }">
					                                         	<td>
					                                         		<ul class="actions">
	            														<li>
	                														<a href="#">
	                    														<i class="md md-delete"></i>
	                														</a>
	            														</li>
	        														</ul>
	        													</td>
					                                         </c:if>
					                                    </tr>
					                                    <tr>
					                                        <td>2</td>
					                                        <td><img style="height: 30px;border-radius: 50%;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt=""></td>
					                                        <td>Madeleine</td>
					                                        <td>Hollaway</td>
					                                        <td>@hollway</td>
					                                        <c:if test="${partido.propietario.email == user.username }">
					                                         	<td>
					                                         		<ul class="actions">
	            														<li>
	                														<a href="#">
	                    														<i class="md md-delete"></i>
	                														</a>
	            														</li>
	        														</ul>
	        													</td>
					                                         </c:if>
					                                    </tr>
					                                    <tr>
					                                        <td>3</td>
					                                        <td><img style="height: 30px;border-radius: 50%;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt=""></td>
					                                        <td>Sebastian</td>
					                                        <td>Johnston</td>
					                                        <td>@sebastian</td>
					                                        <c:if test="${partido.propietario.email == user.username }">
					                                         	<td>
					                                         		<ul class="actions">
	            														<li>
	                														<a href="#">
	                    														<i class="md md-delete"></i>
	                														</a>
	            														</li>
	        														</ul>
	        													</td>
					                                         </c:if>
					                                    </tr>
					                                    <tr>
					                                        <td>4</td>
					                                        <td><img style="height: 30px;border-radius: 50%;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt=""></td>
					                                        <td>Mitchell</td>
					                                        <td>Christin</td>
					                                        <td>@mitchell4u</td>
					                                        <c:if test="${partido.propietario.email == user.username }">
					                                         	<td>
					                                         		<ul class="actions">
	            														<li>
	                														<a href="#">
	                    														<i class="md md-delete"></i>
	                														</a>
	            														</li>
	        														</ul>
	        													</td>
					                                         </c:if>
					                                    </tr>
					                                    <tr>
					                                        <td>5</td>
					                                        <td><img style="height: 30px;border-radius: 50%;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt=""></td>
					                                        <td>Elizabeth</td>
					                                        <td>Belkitt</td>
					                                        <td>@belkitt</td>
					                                        <c:if test="${partido.propietario.email == user.username }">
					                                         	<td>
					                                         		<ul class="actions">
	            														<li>
	                														<a href="#">
	                    														<i class="md md-delete"></i>
	                														</a>
	            														</li>
	        														</ul>
	        													</td>
					                                         </c:if>
					                                    </tr>
					                                    <tr>
					                                        <td>6</td>
					                                        <td><img style="height: 30px;border-radius: 50%;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt=""></td>
					                                        <td>Benjamin</td>
					                                        <td>Parnell</td>
					                                        <td>@wayne234</td>
					                                        <c:if test="${partido.propietario.email == user.username }">
					                                         	<td>
					                                         		<ul class="actions">
	            														<li>
	                														<a href="#">
	                    														<i class="md md-delete"></i>
	                														</a>
	            														</li>
	        														</ul>
	        													</td>
					                                         </c:if>
					                                    </tr>
					                                    <tr>
					                                        <td>7</td>
					                                        <td><img style="height: 30px;border-radius: 50%;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt=""></td>
					                                        <td>Katherine</td>
					                                        <td>Buckland</td>
					                                        <td>@anitabelle</td>
					                                        <c:if test="${partido.propietario.email == user.username }">
					                                         	<td>
					                                         		<ul class="actions">
	            														<li>
	                														<a href="#">
	                    														<i class="md md-delete"></i>
	                														</a>
	            														</li>
	        														</ul>
	        													</td>
					                                         </c:if>
					                                    </tr>
					                                    <tr>
					                                        <td>8</td>
					                                        <td><img style="height: 30px;border-radius: 50%;" class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/bent@test.com" alt=""></td>
					                                        <td>Nicholas</td>
					                                        <td>Walmart</td>
					                                        <td>@mwalmart</td>
					                                        <c:if test="${partido.propietario.email == user.username }">
					                                         	<td>
					                                         		<ul class="actions">
	            														<li>
	                														<a href="#">
	                    														<i class="md md-delete"></i>
	                														</a>
	            														</li>
	        														</ul>
	        													</td>
					                                        </c:if>
					                                    </tr>
					                                </tbody>
					                            </table>
					                        </div>
					                    </div>
			                        </div>
	                            </div>
                            </div><!-- TAB 1 -->
                           <div ng-if="activeTab == 2">
	                            <div class="pmb-block" >
	                                <div class="pmbb-header">
	                                    <h2><i class="md fa fa-bookmark"></i> Biografía</h2>
	                                    <sec:authorize access="isAuthenticated()" >
                                    	<c:if test="${userSigned.email == user.username }">
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
	                                    </c:if>
		                                </sec:authorize>
	                                </div>
	                                <div class="pmbb-body p-l-30">
	                                    <div class="pmbb-view">
	                                        Sed eu est vulputate, fringilla ligula ac, maximus arcu. Donec sed felis vel magna mattis ornare ut non turpis. Sed id arcu elit. Sed nec sagittis tortor. Mauris ante urna, ornare sit amet mollis eu, aliquet ac ligula. Nullam dolor metus, suscipit ac imperdiet nec, consectetur sed ex. Sed cursus porttitor leo.
	                                    </div>
	                                    
	                                    <div class="pmbb-edit">
	                                        <div class="fg-line">
	                                            <textarea class="form-control" rows="5" placeholder="Summary...">Sed eu est vulputate, fringilla ligula ac, maximus arcu. Donec sed felis vel magna mattis ornare ut non turpis. Sed id arcu elit. Sed nec sagittis tortor. Mauris ante urna, ornare sit amet mollis eu, aliquet ac ligula. Nullam dolor metus, suscipit ac imperdiet nec, consectetur sed ex. Sed cursus porttitor leo.</textarea>
	                                        </div>
	                                        <div class="m-t-10">
	                                            <button class="btn btn-primary btn-sm waves-effect waves-button waves-float">Save</button>
	                                            <button data-pmb-action="reset" class="btn btn-link btn-sm waves-effect waves-button waves-float">Cancel</button>
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
		                                                    <a data-pmb-action="edit" href="">Edit</a>
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
	                                            <dt>Gender</dt>
	                                            <dd>Female</dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt>Birthday</dt>
	                                            <dd>June 23, 1990</dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt>Martial Status</dt>
	                                            <dd>Single</dd>
	                                        </dl>
	                                    </div>
	                                    
	                                    <div class="pmbb-edit">
	                                        <dl class="dl-horizontal">
	                                            <dt class="p-t-10">Full Name</dt>
	                                            <dd>
	                                                <div class="fg-line">
	                                                    <input class="form-control" placeholder="eg. Mallinda Hollaway" type="text">
	                                                </div>
	                                                
	                                            </dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt class="p-t-10">Gender</dt>
	                                            <dd>
	                                                <div class="fg-line">
	                                                    <select class="form-control">
	                                                        <option>Male</option>
	                                                        <option>Female</option>
	                                                        <option>Other</option>
	                                                    </select>
	                                                </div>
	                                            </dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt class="p-t-10">Birthday</dt>
	                                            <dd>
	                                                <div class="dtp-container dropdown fg-line">
	                                                    <input class="form-control date-picker" data-toggle="dropdown" placeholder="Click here..." type="text">
	                                                </div>
	                                            </dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt class="p-t-10">Martial Status</dt>
	                                            <dd>
	                                                <div class="fg-line">
	                                                    <select class="form-control">
	                                                        <option>Single</option>
	                                                        <option>Married</option>
	                                                        <option>Other</option>
	                                                    </select>
	                                                </div>
	                                            </dd>
	                                        </dl>
	                                        
	                                        <div class="m-t-30">
	                                            <button class="btn btn-primary btn-sm waves-effect waves-button waves-float">Save</button>
	                                            <button data-pmb-action="reset" class="btn btn-link btn-sm waves-effect waves-button waves-float">Cancel</button>
	                                        </div>
	                                    </div>
	                                </div>
	                            </div>
	                       
	                        
	                            <div class="pmb-block">
	                                <div class="pmbb-header">
	                                    <h2><i class="md fa fa-phone"></i> Información de contacto</h2>
	                                    
	                                    <sec:authorize access="isAuthenticated()" >
                                    	<c:if test="${userSigned.email == user.username }">
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
	                                    </c:if>
		                                </sec:authorize>
	                                </div>
	                                <div class="pmbb-body p-l-30">
	                                    <div class="pmbb-view">
	                                        <dl class="dl-horizontal">
	                                            <dt>Mobile Phone</dt>
	                                            <dd>00971 12345678 9</dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt>Email Address</dt>
	                                            <dd>malinda.h@gmail.com</dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt>Twitter</dt>
	                                            <dd>@malinda</dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt>Skype</dt>
	                                            <dd>malinda.hollaway</dd>
	                                        </dl>
	                                    </div>
	                                    
	                                    <div class="pmbb-edit">
	                                        <dl class="dl-horizontal">
	                                            <dt class="p-t-10">Mobile Phone</dt>
	                                            <dd>
	                                                <div class="fg-line">
	                                                    <input class="form-control" placeholder="eg. 00971 12345678 9" type="text">
	                                                </div>
	                                            </dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt class="p-t-10">Email Address</dt>
	                                            <dd>
	                                                <div class="fg-line">
	                                                    <input class="form-control" placeholder="eg. malinda.h@gmail.com" type="email">
	                                                </div>
	                                            </dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt class="p-t-10">Twitter</dt>
	                                            <dd>
	                                                <div class="fg-line">
	                                                    <input class="form-control" placeholder="eg. @malinda" type="text">
	                                                </div>
	                                            </dd>
	                                        </dl>
	                                        <dl class="dl-horizontal">
	                                            <dt class="p-t-10">Skype</dt>
	                                            <dd>
	                                                <div class="fg-line">
	                                                    <input class="form-control" placeholder="eg. malinda.hollaway" type="text">
	                                                </div>
	                                            </dd>
	                                        </dl>
	                                        
	                                        <div class="m-t-30">
	                                            <button class="btn btn-primary btn-sm waves-effect waves-button waves-float">Save</button>
	                                            <button data-pmb-action="reset" class="btn btn-link btn-sm waves-effect waves-button waves-float">Cancel</button>
	                                        </div>
	                                    </div>
	                                </div>
	                             </div>
                            </div><!-- TAB 2 -->
                        </div>
                    </div>
                </div>