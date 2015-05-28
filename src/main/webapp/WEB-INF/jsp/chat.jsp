<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<sec:authorize access="isAuthenticated()" >
		 <jsp:include page="masterPage.jsp"></jsp:include>
		
		<sec:authentication var="user" property="principal" />
		<section id="main">
            <section id="content" ng-app="pachanga">
                <div class="container" ng-controller="MensajeController">
                    <div class="block-header">
                        <h2>Mensajes</h2>
                    </div>
                
                    <div class="card m-b-0" id="messages-main">
                        <a href="" class="btn bgm-red btn-float" id="ms-compose">
                            <i class="md md-add"></i>
                        </a>
                        
                        <div class="ms-menu">
                            <div class="ms-block">
                                <div class="ms-user">
                                    <img src="${pageContext.request.contextPath}/usuarios/getUserImage/${user.username}" alt="">
                                    <div>Logueado como <br> ${user.username}</div>
                                </div>
                            </div>
                            
                            <div class="ms-block">
                                <div class="dropdown" data-animation="flipInX,flipOutX">
                                    <a class="btn btn-primary btn-block" href="" data-toggle="dropdown">Messages <i class="caret m-l-5"></i></a>

                                    <ul class="dropdown-menu dm-icon w-100">
                                        <li><a href=""><i class="md md-mail"></i> Mensajes</a></li>
                                        <li><a href=""><i class="md md-people"></i> Contactos</a></li>
                                    </ul>
                                </div>
                            </div>
                            
                            <div class="listview lv-user m-t-20" ng-init="loadContactos()">
                            
                                <div class="lv-item media " ng-repeat="usuario in usuarios" ng-class="{'active': getCssClass(usuario)}" ng-click="loadConversacion(usuario)">
                                	<div ng-if="usuario.tieneAvatar == false" ng-class="getAvatarCssClass(usuario)"  class="lv-avatar  pull-left">{{usuario.email.substring( 0, 1 )}}</div>
                                    <div ng-if="usuario.tieneAvatar == true" class="lv-avatar pull-left">
                                        <img src="${pageContext.request.contextPath}/usuarios/getUserImage/{{usuario.email}}" alt="">
                                    </div>
                                    <div class="media-body">
                                        <div  class="lv-title">{{usuario.email}}</div>
                                        <div class="lv-small">Fierent fastidii recteque ad pro</div>
                                    </div>
                                </div>
                            </div>

                            
                        </div>
                        
                        <div class="ms-body"><!-- Aqui empieza el cuerpo del chat -->
                            <div class="listview lv-message">
                                <div class="lv-header-alt bgm-white">
                                    <div id="ms-menu-trigger">
                                        <div class="line-wrap">
                                            <div class="line top"></div>
                                            <div class="line center"></div>
                                            <div class="line bottom"></div>
                                        </div>
                                    </div>

                                    <div class="lvh-label hidden-xs">
                                        <div ng-if="conversacion.receptor.tieneAvatar == false" ng-class="getAvatarCssClass(conversacion.receptor)"  class="lv-avatar  pull-left">{{conversacion.receptor.email.substring( 0, 1 )}}</div>
	                                    <div ng-if="conversacion.receptor.tieneAvatar == true" class="lv-avatar pull-left">
	                                        <img src="${pageContext.request.contextPath}/usuarios/getUserImage/{{conversacion.receptor.email}}" alt="">
	                                    </div>
                                        <span class="c-black">{{conversacion.receptor.email}}</span>
                                    </div>
                                    
                                    <ul class="lv-actions actions">
                                        <li>
                                            <a href="">
                                                <i class="md md-delete"></i>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="">
                                                <i class="md md-check"></i>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="">
                                                <i class="md md-access-time"></i>
                                            </a>
                                        </li>
                                        <li class="dropdown">
                                            <a href="" data-toggle="dropdown" aria-expanded="true">
                                                <i class="md md-sort"></i>
                                            </a>
                                
                                            <ul class="dropdown-menu dropdown-menu-right">
                                                <li>
                                                    <a href="">Latest</a>
                                                </li>
                                                <li>
                                                    <a href="">Oldest</a>
                                                </li>
                                            </ul>
                                        </li>                             
                                        <li class="dropdown">
                                            <a href="" data-toggle="dropdown" aria-expanded="true">
                                                <i class="md md-more-vert"></i>
                                            </a>
                                
                                            <ul class="dropdown-menu dropdown-menu-right">
                                                <li>
                                                    <a ng-click="loadConversacion(conversacion.receptor)" href="">Refresh</a>
                                                </li>
                                                <li>
                                                    <a href="">Message Settings</a>
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                                
                                <div class="lv-body" nice-scroll scroll="conversacion.mensajes"><!-- Conversacion -->
                                                                    
                                    <div class="lv-item media" ng-repeat="mensaje in conversacion.mensajes" ng-class="getMensajeCssClass(mensaje)" ><!-- Mensaje -->
                                        <div ng-if="mensaje.emisor.id == conversacion.emisor.id" class="lv-avatar" ng-class="getMensajeAvatarCssClass(mensaje)">
	                                        <img src="${pageContext.request.contextPath}/usuarios/getUserImage/{{mensaje.emisor.email}}" alt="">
	                                    </div>
                                        <div  ng-if="mensaje.emisor.id != conversacion.emisor.id && mensaje.emisor.id == conversacion.receptor.id"  ng-class="getMensajeAvatarCssClass(mensaje)"  class="lv-avatar {{conversacion.receptor.avatarCssClass}} ">
                                        	{{conversacion.receptor.email.substring( 0, 1 )}}
                                        </div>
	                                    
                                        <div class="media-body">
                                            <div class="ms-item">
                                                {{mensaje.contenido}}
                                            </div>
                                            <small class="ms-date"><i class="md md-access-time"></i>  {{mensaje.fechaRepresentacion}}</small>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="lv-footer ms-reply">
                                	<form ng-submit="sendMensaje();contenido= '';">
	                                    <textarea ng-model="contenido" placeholder="What's on your mind..."></textarea>
	                                    
	                                    <button type="submit"  ><i class="md md-send"></i></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </section>
        
        <!-- Older IE warning message -->
        <!--[if lt IE 9]>
            <div class="ie-warning">
                <h1 class="c-white">IE SUCKS!</h1>
                <p>You are using an outdated version of Internet Explorer, upgrade to any of the following web browser <br/>in order to access the maximum functionality of this website. </p>
                <ul class="iew-download">
                    <li>
                        <a href="http://www.google.com/chrome/">
                            <img src="img/browsers/chrome.png" alt="">
                            <div>Chrome</div>
                        </a>
                    </li>
                    <li>
                        <a href="https://www.mozilla.org/en-US/firefox/new/">
                            <img src="img/browsers/firefox.png" alt="">
                            <div>Firefox</div>
                        </a>
                    </li>
                    <li>
                        <a href="http://www.opera.com">
                            <img src="img/browsers/opera.png" alt="">
                            <div>Opera</div>
                        </a>
                    </li>
                    <li>
                        <a href="https://www.apple.com/safari/">
                            <img src="img/browsers/safari.png" alt="">
                            <div>Safari</div>
                        </a>
                    </li>
                    <li>
                        <a href="http://windows.microsoft.com/en-us/internet-explorer/download-ie">
                            <img src="img/browsers/ie.png" alt="">
                            <div>IE (New)</div>
                        </a>
                    </li>
                </ul>
                <p>Upgrade your browser for a Safer and Faster web experience. <br/>Thank you for your patience...</p>
            </div>   
        <![endif]-->
	</sec:authorize>