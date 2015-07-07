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
    
    <style type="text/css">
    	.lv-body .md-star:hover{
    		color: #FF9800 !important;
    		cursor:pointer;
    	}
    </style>
    


</head>

<body ng-app="pachanga">

	 <jsp:include page="masterPage.jsp"></jsp:include>
	
	<div class="container" id="content" ng-app="pachanga" ng-controller="ProfileController">
                    
                    <div class="block-header">
                        <h2><c:out value="${user.email}"></c:out> <small>Web/UI Developer, Dubai, United Arab Emirates</small></h2>
                        
                    </div>
                    
                    <div class="card" id="profile-main" ng-init="userSigned=${userSigned.id}">
                        <div tabindex="4" style="overflow: hidden;" class="pm-overview c-overflow">
                            <div class="pmo-pic">
                                <div class="p-relative" ng-init="urlProfile='${pageContext.request.contextPath}/usuarios/getUserImage/${user.id}'">
                                    <a href="">
                                        <img class="img-responsive" ng-src="{{urlProfile}}" alt=""> 
                                    </a>
                                    <sec:authorize access="isAuthenticated()" >
                                    <c:if test="${userSigned.id  ne user.id }">
	                                    <div class="dropdown pmop-message">
	                                        <a data-toggle="dropdown" href="" class="btn bgm-white btn-float z-depth-1 waves-effect waves-button waves-float">
	                                            <i class="md md-message"></i>
	                                        </a>
	                                        
	                                        <div class="dropdown-menu" ng-controller="MensajeController">
	                                        	<form ng-submit="sendMensaje();contenido= '';">
		                                            <textarea ng-model="contenido" placeholder="Dile algo..."></textarea>
		                                            <input type="hidden" ng-model="receptor" name="receptor"  ng-init="receptor='${user.id}'" ng-value="${user.id}" value="${user.id}" />
		                                            <button type="submit" class="btn bgm-green btn-icon waves-effect waves-button waves-float"><i class="md md-send"></i></button>
	                                            </form>
	                                        </div>
	                                    </div>
                                    </c:if>
                                    <c:if test="${userSigned.id eq user.id }">
	                                    <input type="file" ng-model-instant id="fileToUpload"  onchange="angular.element(this).scope().setFiles(this)" style="display:none;"/>
                                    	
	                                    <a href="" class="pmop-edit" onclick="uploadPhoto();">
	                                        <i class="md md-camera-alt"></i> <span class="hidden-xs">Actualizar Imagen</span>
	                                    </a>
                                    </c:if>
                                    </sec:authorize>
                                </div>
                                
                                
                                <div class="pmo-stat">
                                    <h2 class="m-0 c-white">${visitas}</h2>
                                    Visitas
                                </div>
                            </div>
                            
                            <div class="pmo-block pmo-contact hidden-xs" style="padding-top: 10px;">
                                <div class="card rating-list">
                            <div class="listview">
                                <div class="lv-header">
                                    <div class="m-t-5">
                                        Karma {{ratingMedia}}
                                    </div>
                                    
                                    <div class="clearfix"></div>
                                    
                                    <div class="rl-star" ng-init="initializeKarma(${user.id})">
                                        <i class="md md-star" ng-repeat="karmaPoint  in karma" ng-class="getCssKarma(karmaPoint)"></i>
                                    </div>
                                </div>
                                
                                <div class="lv-body" >
                                    <div class="p-15">
                                        <div class="lv-item" ng-repeat="point in karma">
                                            <div class="media">
                                                <div class="pull-left">
                                                    {{point}} <i class="md md-star" ng-click="vote(${user.id},point)"></i>
                                                </div>
                                                
                                                <div class="pull-right">{{getNumeroKarmaVotes(point)}}</div>
                                                
                                                <div class="media-body">
                                                    <div class="progress">
                                                        <div class="progress-bar " ng-class="getCssProgressBarKarma(point)" role="progressbar" aria-valuenow="{{getNumeroKarmaVotes(point)}}" aria-valuemin="0" aria-valuemax="{{numeroKarmaVotes}}" style="width: {{getNumeroKarmaVotes(point)/numeroKarmaVotes*100}}%">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                            </div>
                            
                            
                        </div>
                        
                        <div class="pm-body clearfix" >
                            <ul tabindex="1" style="overflow: hidden;" class="tab-nav tn-justified">
                                <li ng-click="activeTab = 1;" class="waves-effect" ng-class="getProfileTabCss(1)"><a href="#">Perfil</a></li>
                                <li ng-click="activeTab = 2;" class="waves-effect" ng-class="getProfileTabCss(2)"><a href="#">Timeline</a></li>
                            </ul>
                            
                            <div ng-if="activeTab == 1">
	                            <div class="pmb-block" >
	                                <div class="pmbb-header">
	                                    <h2><i class="md fa fa-bookmark"></i> Biografía</h2>
	                                    <sec:authorize access="isAuthenticated()" >
                                    	<c:if test="${userSigned.id eq user.id }">
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
                                    	<c:if test="${userSigned.id eq user.id }">
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
                                    	<c:if test="${userSigned.id == user.id }">
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
                            </div><!-- TAB 1 -->
                            <div ng-if="activeTab == 2"><!-- Start TAB 2 -->
                            	<div class="timeline" ng-init="getNovedades(${user.id})">
	                                <div class="t-view" data-tv-type="{{getTvType(novedad)}}" ng-repeat="novedad in novedades track by $index">
	                                    <div class="tv-header media">
	                                        <a href="/P/usuarios/profile/{{novedad.emisor.id}}" class="tvh-user pull-left">
	                                            <img class="img-responsive" ng-src="/P/usuarios/getUserImage/{{novedad.emisor.id}}" alt="">
	                                        </a>
	                                        <div class="media-body p-t-5">
	                                            <strong class="d-block">{{novedad.emisor.screenName}}</strong>
	                                            <small class="c-gray">{{novedad.fechaRepresentacion}}</small>
	                                        </div>
	                                        
	                                        <ul class="actions m-t-20 hidden-xs" ng-if="novedad.usuario.id == userSigned.id">
	                                            <li class="dropdown">
	                                                <a href="" data-toggle="dropdown">
	                                                    <i class="md md-more-vert"></i>
	                                                </a>
	                                    
	                                                <ul class="dropdown-menu dropdown-menu-right">
	                                                    <li>
	                                                        <a href="">Edit</a>
	                                                    </li>
	                                                    <li>
	                                                        <a href="">Delete</a>
	                                                    </li>
	                                                </ul>
	                                            </li>
	                                        </ul>
	                                    </div>
	                                    <div class="tv-body">
		                                    <div ng-if="novedad.tipo == 'PARTIDO'" class="lightbox m-b-20">
	                                            <div data-src="img/headers/sm/4.png">
	                                                <div class="lightbox-item pull-left">
	                                                    <img ng-src="img/headers/sm/4.png" alt="">
	                                                </div>
	                                            </div>
	                                        </div>
	                                        <p>
	                                        	{{novedad.contenido}}
	                                        </p>
	                                    
	                                    
	                                        <div class="clearfix"></div>
	                                    
	                                        <ul class="tvb-stats">
	                                            <li class="tvbs-comments"><i class="md md-comment"></i> {{novedad.comentarios.length}} <span class="hidden-xs">Comentarios</span></li>
	                                            <li class="tvbs-likes" style="cursor:pointer" ng-click="upLike(novedad)"><i class="md md-thumb-up"></i> {{novedad.likes}} <span class="hidden-xs">Likes</span></li>
	                                            <li class="tvbs-views"><i class="md md-remove-red-eye"></i> {{novedad.views}} <span class="hidden-xs">Visitas</span></li>
	                                        </ul>
	                                        
	                                        <a ng-if="novedad.comentarios.length > 3" ng-click="disableLimit(novedad)" class="tvc-more" href=""><i class="md md-mode-comment"></i> Ver los {{novedad.comentarios.length}} Comentarios</a>
	                                    </div>
	
	                                    <div class="tv-comments">
	                                    	<form name="sendComentarioToNovedad" >
	                                        <ul class="tvc-lists">
	                                        	
	                                            <li class="media" ng-if="novedad.comentarios" ng-repeat="comentario in novedad.comentarios | limitTo: getLimitComentarios(novedad) ">
	                                                <a href="/P/usuarios/profile/{{comentario.emisor.id}}" class="tvh-user pull-left">
	                                                    <img class="img-responsive" ng-src="/P/usuarios/getUserImage/{{comentario.emisor.id}}" alt="">
	                                                </a>
	                                                <div class="media-body">
	                                                    <strong class="d-block">{{comentario.emisor.screenName}}</strong>
	                                                    <small class="c-gray">{{comentario.fechaRepresentacion}}</small>
	                                                    
	                                                    <div class="m-t-10">{{comentario.contenido}}</div>
	
	                                                </div>
	                                            </li>
	                                            <sec:authorize access="isAuthenticated()">
		                                            <li class="p-20">
		                                                <div class="fg-line" ng-form="comentarioForm_{{novedad.id}}">
		                                                	
		                                                    	<textarea  name="comentario-{{ novedad.id }}"  id="comentario-{{ novedad.id }}" style="overflow: hidden; word-wrap: break-word; height: 50px;" class="form-control auto-size" placeholder="Escr&iacute;bele un comentario..."></textarea>
		                                                    
		                                                </div>
		                                                
		                                                <button ng-click="sendComentario(this,novedad.id);contenidoComentario= '';"  class="m-t-15 btn btn-primary btn-sm waves-effect waves-button waves-float">Enviar</button>
		                                            </li>
	                                            </sec:authorize>
	                                        </ul>
	                                        </form>
	                                    </div>
	                                </div>
	                                <sec:authorize access="isAuthenticated()">
		                                <div class="t-view" data-tv-type="text">
		                                    <div class="tv-header media">
		                                        <a href="" class="tvh-user pull-left">
		                                            <img class="img-responsive" src="/P/usuarios/getUserImage/${userSigned.id}" alt="">
		                                        </a>
		                                        <div class="media-body p-t-5">
		                                            <strong class="d-block">Malinda Hollaway</strong>
		                                            <!-- <small class="c-gray">April 23, 2014 at 05:00</small>-->
		                                        </div>
		                                    </div>
		
		                                    <div class="tv-comments">
		                                        <ul class="tvc-lists">
		                                            
		                                            
		                                            <li class="p-20">
		                                                <div class="fg-line">
		                                                	<form method="POST" name="formCreateNovedad" ng-init="setFormNovedadScope(this)">
		                                                		<input type="hidden" name="usuario" ng-model="usuario" value="${user.id}" ng-value="${user.id}" ng-init="usuario = ${user.id}" />
		                                                    	<textarea ng-model="contenido" style="overflow: hidden; word-wrap: break-word; height: 50px;" class="form-control auto-size" placeholder="Env&iacute;ale un comentario..."></textarea>
		                                                    </form>
		                                                </div>
		                                                
		                                                <button ng-click="sendNovedad();contenido= '';" class="m-t-15 btn btn-primary btn-sm waves-effect waves-button waves-float">Enviar</button>
		                                            </li>
		                                        </ul>
		                                    </div>
		                                </div>
	                                </sec:authorize>
	                                <!--  
	                                <div class="t-view" data-tv-type="image">
	                                    <div class="tv-header media">
	                                        <a href="" class="tvh-user pull-left">
	                                            <img class="img-responsive" src="img/profile-pics/profile-pic-2.jpg" alt="">
	                                        </a>
	                                        <div class="media-body p-t-5">
	                                            <strong class="d-block">Malinda Hollaway</strong>
	                                            <small class="c-gray">April 05, 2014 at 11:00</small>
	                                        </div>
	                                        
	                                        <ul class="actions m-t-20 hidden-xs">
	                                            <li class="dropdown">
	                                                <a href="" data-toggle="dropdown">
	                                                    <i class="md md-more-vert"></i>
	                                                </a>
	                                    
	                                                <ul class="dropdown-menu dropdown-menu-right">
	                                                    <li>
	                                                        <a href="">Edit</a>
	                                                    </li>
	                                                    <li>
	                                                        <a href="">Delete</a>
	                                                    </li>
	                                                </ul>
	                                            </li>
	                                        </ul>
	                                    </div>
	                                    <div class="tv-body">
	                                        
	                                        <div class="lightbox m-b-20">
	                                            <div data-src="img/headers/sm/4.png">
	                                                <div class="lightbox-item pull-left">
	                                                    <img src="img/headers/sm/4.png" alt="">
	                                                </div>
	                                            </div>
	                                        </div>
	
	                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis sem dolor, posuere convallis blandit sit amet, aliquet in est. Ut condimentum magna enim, non venenatis elit interdum accumsan. In hac habitasse platea dictumst. Etiam molestie felis non mollis viverra. In ipsum lorem, fermentum vitae lectus in, accumsan malesuada neque.</p>
	                                                                        
	                                        <div class="clearfix"></div>
	                                    
	                                        <ul class="tvb-stats">
	                                            <li class="tvbs-comments"><i class="md md-comment"></i> 120 <span class="hidden-xs">Comments</span></li>
	                                            <li class="tvbs-likes"><i class="md md-thumb-up"></i> 34K <span class="hidden-xs">Likes</span></li>
	                                            <li class="tvbs-views"><i class="md md-remove-red-eye"></i> 105K <span class="hidden-xs">Views</span></li>
	                                        </ul>
	                                        
	                                        <a class="tvc-more" href=""><i class="md md-mode-comment"></i> View all 290 Comments</a>
	                                    </div>
	
	                                    <div class="tv-comments">
	                                        <ul class="tvc-lists">
	                                            <li class="media">
	                                                <a href="" class="tvh-user pull-left">
	                                                    <img class="img-responsive" src="img/profile-pics/1.jpg" alt="">
	                                                </a>
	                                                <div class="media-body">
	                                                    <strong class="d-block">Jolla Hatkin</strong>
	                                                    <small class="c-gray">April 23, 2014 at 05.00</small>
	                                                    
	                                                    <div class="m-t-10">Donec vel metus nisl. Nam euismod neque et finibus vulputate. Integer in vestibulum orci. Phasellus ut iaculis arcu, vitae commodo justo. Ut eu feugiat lorem, quis ornare risus</div>
	
	                                                </div>
	                                            </li>
	                                            
	                                            <li class="media">
	                                                <a href="" class="tvh-user pull-left">
	                                                    <img class="img-responsive" src="img/profile-pics/2.jpg" alt="">
	                                                </a>
	                                                <div class="media-body">
	                                                    <strong class="d-block">David Simpson</strong>
	                                                    <small class="c-gray">April 23, 2014 at 05.00</small>
	                                                    
	                                                    <div class="m-t-10">Nulla vehicula erat nec odio dignissim, sit amet porttitor lorem auctor. Maecenas fermentum tellus ex, ac aliquet nisl malesuada id.</div>
	
	                                                </div>
	                                            </li>
	                                            <li class="p-20">
	                                                <div class="fg-line">
	                                                    <textarea style="overflow: hidden; word-wrap: break-word; height: 50px;" class="form-control auto-size" placeholder="Write a comment..."></textarea>
	                                                </div>
	                                                
	                                                <button class="m-t-15 btn btn-primary btn-sm waves-effect waves-button waves-float">Post</button>
	                                            </li>
	                                        </ul>
	                                    </div>
	                                </div>
	                                
	                                <div class="t-view" data-tv-type="video">
	                                    <div class="tv-header media">
	                                        <a href="" class="tvh-user pull-left">
	                                            <img class="img-responsive" src="img/profile-pics/profile-pic-2.jpg" alt="">
	                                        </a>
	                                        <div class="media-body p-t-5">
	                                            <strong class="d-block">Malinda Hollaway</strong>
	                                            <small class="c-gray">April 01, 2014 at 15:00</small>
	                                        </div>
	                                        
	                                        <ul class="actions m-t-20 hidden-xs">
	                                            <li class="dropdown">
	                                                <a href="" data-toggle="dropdown">
	                                                    <i class="md md-more-vert"></i>
	                                                </a>
	                                    
	                                                <ul class="dropdown-menu dropdown-menu-right">
	                                                    <li>
	                                                        <a href="">Edit</a>
	                                                    </li>
	                                                    <li>
	                                                        <a href="">Delete</a>
	                                                    </li>
	                                                </ul>
	                                            </li>
	                                        </ul>
	                                    </div>
	                                    <div class="tv-body">
	                                        
	                                        <div class="embed-responsive embed-responsive-16by9 m-b-20">
	                                            <iframe class="embed-responsive-item" src="http://www.youtube.com/embed/aaZXDm3RXuo"></iframe>
	                                        </div>
	                                        
	                                        <p>Duis sem dolor, posuere convallis blandit sit amet, aliquet in est. Ut condimentum magna enim, non venenatis elit interdum accumsan. In hac habitasse platea dictumst. Etiam molestie felis non mollis viverra. In ipsum lorem, fermentum vitae lectus in, accumsan malesuada neque.</p>
	                                                                        
	                                        <div class="clearfix"></div>
	                                    
	                                        <ul class="tvb-stats">
	                                            <li class="tvbs-comments">21 Comments</li>
	                                            <li class="tvbs-likes">156 Likes</li>
	                                            <li class="tvbs-views">2365 Views</li>
	                                        </ul>
	                                        
	                                        <a class="tvc-more" href=""><i class="md md-mode-comment"></i> View all 14 Comments</a>
	                                    </div>
	
	                                    <div class="tv-comments">
	                                        <ul class="tvc-lists">
	                                            <li class="media">
	                                                <a href="" class="tvh-user pull-left">
	                                                    <img class="img-responsive" src="img/profile-pics/6.jpg" alt="">
	                                                </a>
	                                                <div class="media-body">
	                                                    <strong class="d-block">Jolla Hatkin</strong>
	                                                    <small class="c-gray">April 23, 2014 at 05.00</small>
	                                                    
	                                                    <div class="m-t-10">Donec vel metus nisl. Nam euismod neque et finibus vulputate. Integer in vestibulum orci. Phasellus ut iaculis arcu, vitae commodo justo. Ut eu feugiat lorem, quis ornare risus</div>
	
	                                                </div>
	                                            </li>
	                                            
	                                            <li class="media">
	                                                <a href="" class="tvh-user pull-left">
	                                                    <img class="img-responsive" src="img/profile-pics/5.jpg" alt="">
	                                                </a>
	                                                <div class="media-body">
	                                                    <strong class="d-block">Sean Paul Jr.</strong>
	                                                    <small class="c-gray">April 23, 2014 at 05.00</small>
	                                                    
	                                                    <div class="m-t-10">Nulla vehicula erat nec odio dignissim, sit amet porttitor lorem auctor. Maecenas fermentum tellus ex, ac aliquet nisl malesuada id.</div>
	
	                                                </div>
	                                            </li>
	                                            <li class="p-20">
	                                                <div class="fg-line">
	                                                    <textarea style="overflow: hidden; word-wrap: break-word; height: 50px;" class="form-control auto-size" placeholder="Write a comment..."></textarea>
	                                                </div>
	                                                
	                                                <button class="m-t-15 btn btn-primary btn-sm waves-effect waves-button waves-float">Post</button>
	                                            </li>
	                                        </ul>
	                                    </div>
	                                </div>
	                                
	                                <div class="t-view" data-tv-type="image">
	                                    <div class="tv-header media">
	                                        <a href="" class="tvh-user pull-left">
	                                            <img class="img-responsive" src="img/profile-pics/profile-pic-2.jpg" alt="">
	                                        </a>
	                                        <div class="media-body p-t-5">
	                                            <strong class="d-block">Malinda Hollaway</strong>
	                                            <small class="c-gray">March 11, 2014 at 09:00</small>
	                                        </div>
	                                        
	                                        <ul class="actions m-t-20 hidden-xs">
	                                            <li class="dropdown">
	                                                <a href="" data-toggle="dropdown">
	                                                    <i class="md md-more-vert"></i>
	                                                </a>
	                                    
	                                                <ul class="dropdown-menu dropdown-menu-right">
	                                                    <li>
	                                                        <a href="">Edit</a>
	                                                    </li>
	                                                    <li>
	                                                        <a href="">Delete</a>
	                                                    </li>
	                                                </ul>
	                                            </li>
	                                        </ul>
	                                    </div>
	                                    <div class="tv-body">
	                                        
	                                        <div class="lightbox tvb-lightbox clearfix">
	                                            <div data-src="media/gallery/1.jpg" class="col-sm-2 col-xs-3">
	                                                <div class="lightbox-item">
	                                                    <img src="media/gallery/thumbs/1.jpg" alt="">
	                                                </div>
	                                            </div>
	            
	                                            <div data-src="media/gallery/2.jpg" class="col-sm-2 col-xs-3">
	                                                <div class="lightbox-item">
	                                                    <img src="media/gallery/thumbs/2.jpg" alt="">
	                                                </div>
	                                            </div>
	                                       
	                                            <div data-src="media/gallery/3.jpg" class="col-sm-2 col-xs-3">
	                                                <div class="lightbox-item">
	                                                    <img src="media/gallery/thumbs/3.jpg" alt="">
	                                                </div>
	                                            </div>
	                                        
	                                            <div data-src="media/gallery/4.jpg" class="col-sm-2 col-xs-3">
	                                                <div class="lightbox-item">
	                                                    <img src="media/gallery/thumbs/4.jpg" alt="">
	                                                </div>
	                                            </div>
	                                        </div>
	                                        
	                                        <p>Ut condimentum magna enim, non venenatis elit interdum accumsan. In hac habitasse platea dictumst. Etiam molestie felis non mollis viverra. In ipsum lorem, fermentum vitae lectus in, accumsan malesuada neque.</p>
	                                                                        
	                                        <div class="clearfix"></div>
	                                    
	                                        <ul class="tvb-stats">
	                                            <li class="tvbs-comments">33 Comments</li>
	                                            <li class="tvbs-likes">983 Likes</li>
	                                            <li class="tvbs-views">19889 Views</li>
	                                        </ul>
	                                        
	                                        <a class="tvc-more" href=""><i class="md md-mode-comment"></i> View all 89 Comments</a>
	                                    </div>
	
	                                    <div class="tv-comments">
	                                        <ul class="tvc-lists">
	                                            <li class="media">
	                                                <a href="" class="tvh-user pull-left">
	                                                    <img class="img-responsive" src="img/profile-pics/6.jpg" alt="">
	                                                </a>
	                                                <div class="media-body">
	                                                    <strong class="d-block">Jolla Hatkin</strong>
	                                                    <small class="c-gray">March 30, 2014 at 05.00</small>
	                                                    
	                                                    <div class="m-t-10">Donec vel metus nisl. Nam euismod neque et finibus vulputate. Integer in vestibulum orci. Phasellus ut iaculis arcu, vitae commodo justo. Ut eu feugiat lorem, quis ornare risus</div>
	
	                                                </div>
	                                            </li>
	                                            
	                                            <li class="media">
	                                                <a href="" class="tvh-user pull-left">
	                                                    <img class="img-responsive" src="img/profile-pics/5.jpg" alt="">
	                                                </a>
	                                                <div class="media-body">
	                                                    <strong class="d-block">Marwell Wecker</strong>
	                                                    <small class="c-gray">March 31, 2014 at 05.00</small>
	                                                    
	                                                    <div class="m-t-10">Nulla vehicula erat nec odio dignissim, sit amet porttitor lorem auctor. Maecenas fermentum tellus ex, ac aliquet nisl malesuada id.</div>
	
	                                                </div>
	                                            </li>
	                                            <li class="p-20">
	                                                <div class="fg-line">
	                                                    <textarea style="overflow: hidden; word-wrap: break-word; height: 50px;" class="form-control auto-size" placeholder="Write a comment..."></textarea>
	                                                </div>
	                                                
	                                                <button class="m-t-15 btn btn-primary btn-sm waves-effect waves-button waves-float">Post</button>
	                                            </li>
	                                        </ul>
	                                    </div>
	                                </div>-->
	                            
	                                <div class="clearfix"></div>
	                            
	                                <div class="load-more">
	                                    <a href="" ng-click="getNovedades()"><i class="md md-refresh"></i> Load More...</a>
	                                </div>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
	<script type="text/javascript">
	function uploadPhoto(){
		$('input[type=file]').click();
	}
	</script>
</body>
</html>