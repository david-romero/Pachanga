<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    	.load-more {
		    margin-bottom: 30px;
		}
		#todo-lists .card-body {
		    font-family: roboto;
		    font-size: 13px;
		}
		#todo-lists .lv-footer{
			margin-top: 0px !important;
		}
		#todo-lists .lv-small{
			color: #686565;
		}
		@media (min-width: 1200px){
			.top-menu #toggle-width .toggle-switch {
			  margin: -12px 30px 0 0 !important;
			}
		}
		.form-control {
		    padding: 0px 25px !important;
		}
		.radio {
		    padding-left: 20px !important;
		}
		.no-active {
	       pointer-events: none;
	       cursor: default;
	    } 
    </style>

</head>

<body ng-app="pachanga">

     <jsp:include page="masterPage.jsp"></jsp:include>

    <!-- Page Content -->
    <div class="container" id="content" ng-controller="InitController" ng-init="inicio();getAllPartidos();userSigned.id = ${userSigned.id}">

        <div class="block-header">
	        <ul class="actions">
	            <li>
	                <a href="/P/app">
	                    <i class="md md-cached"></i>
	                </a>
	            </li>
	        </ul>        
        </div>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
        	<div class="card">
                <div class="card-header">
                    <h2>Sales Statistics <small>Vestibulum purus quam scelerisque, mollis nonummy metus</small></h2>

                    <ul class="actions">
                        <li>
                            <a href="">
                                <i class="md md-cached"></i>
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <i class="md md-file-download"></i>
                            </a>
                        </li>
                        <li class="dropdown">
                            <a href="" data-toggle="dropdown" aria-expanded="false">
                                <i class="md md-more-vert"></i>
                            </a>

                            <ul class="dropdown-menu dropdown-menu-right">
                                <li>
                                    <a href="">Change Date Range</a>
                                </li>
                                <li>
                                    <a href="">Change Graph Type</a>
                                </li>
                                <li>
                                    <a href="">Other Settings</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>

                <div class="card-body">
                    <div class="chart-edge">
                        <div class="flot-chart" data-curvedline-chart="" style="padding: 0px; position: relative;">
                        </div>
                    </div>
                </div>
            </div>
            <div class="mini-charts">
                <div class="row">
                    <div class="col-sm-6 col-md-3">
                        <div class="mini-charts-item bgm-cyan">
                            <div class="clearfix">
                                <div id="traffic" class="chart stats-bar" data-sparkline-bar="" datos="${listRequest}"></div>
                                <div class="count">
                                    <small>Website Traffics</small>
                                    <h2>${siteTraffic}</h2>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-6 col-md-3">
                        <div class="mini-charts-item bgm-lightgreen">
                            <div class="clearfix">
                                <div id="login" class="chart stats-bar-2" data-sparkline-bar="" datos="${listLogin}" ></div>
                                <div class="count">
                                    <small>Usuarios Activos</small>
                                    <h2>${usuariosActivos}</h2>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-6 col-md-3">
                        <div class="mini-charts-item bgm-orange">
                            <div class="clearfix">
                                <div id="errores-sparkline" class="chart stats-line" data-sparkline-line="" datos="${listErrores}"></div>
                                <div class="count">
                                    <small>Errores Totales</small>
                                    <h2>${errores}</h2>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-6 col-md-3">
                        <div class="mini-charts-item bgm-bluegray">
                            <div class="clearfix">
                                <div id="others-sparkline" class="chart stats-line-2" data-sparkline-line="" datos="${listMensajes}"></div>
                                <div class="count">
                                    <small>Mensajes</small>
                                    <h2>${mensajes}</h2>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="dash-widgets">
                <div class="row">
                    <div class="col-md-3 col-sm-6">
                        <div id="site-visits" class="dash-widget-item bgm-teal">
                            <div class="dash-widget-header">
                                <div class="p-20">
                                    <div id="visitantes-sparkline" class="dash-widget-visits" data-sparkline-line="" datos="${listSiteVisitors}"></div>
                                </div>

                                <div class="dash-widget-title">Visitantes Últimos <c:out value="${fn:length(listSiteVisitors)}"></c:out> días</div>

                                <ul class="actions actions-alt">
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

                            <div class="p-20">

                                <small>Peticiones</small>
                                <h3 class="m-0 f-400">${peticiones}</h3>

                                <br>

                                <small>Visitantes</small>
                                <h3 class="m-0 f-400">${siteVisitors}</h3>

                                <br>

                                <small>Peticiones Sin Recursos</small>
                                <h3 class="m-0 f-400">${paginas}</h3>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-3 col-sm-6">
                        <div id="pie-charts" class="dash-widget-item">
                            <div class="bgm-pink">
                                <div class="dash-widget-header">
                                    <div class="dash-widget-title">Email Statistics</div>
                                </div>

                                <div class="clearfix"></div>

                                <div class="text-center p-20 m-t-25">
                                    <div class="easy-pie main-pie" data-percent="${mailStadistics}" data-easypie-chart="">
                                        <div class="percent">${mailStadistics}</div>
                                        <div class="pie-title">Total Emails Sent</div>
                                    </div>
                                </div>
                            </div>

                            <div class="p-t-20 p-b-20 text-center">
                                <div class="easy-pie sub-pie-1" data-percent="${mobilePorcentaje}" data-easypie-chart="">
                                    <div class="percent">${mobilePorcentaje}</div>
                                    <div class="pie-title">Mobile</div>
                                </div>
                                <div class="easy-pie sub-pie-2" data-percent="${desktopPorcentaje}" data-easypie-chart="">
                                    <div class="percent">${desktopPorcentaje}</div>
                                    <div class="pie-title">Desktop</div>
                                </div>
                            </div>
                        </div>
                    </div>
					<div class="col-sm-6">
                    <!-- Recent Items -->
                    <div class="card ng-scope" >
                        <div class="card-header">
                            <h2>Ranking Usuarios <small>Usuarios que han creado m&aacute;s partidos</small></h2>
                            <ul class="actions">
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
                                        <li>
                                            <a href="">Other Settings</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>

                        <div class="card-body m-t-0">
                            <table class="table table-inner table-vmiddle">
                                <thead>
                                    <tr>
                                        <th>Username</th>
                                        <th>Full Name</th>
                                        <th style="width: 60px">Partidos</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach var="usuario" items="${partidosUsuarios}">
	                                    <tr  class="">
	                                        <td class="f-500 c-cyan">${usuario.username}</td>
	                                        <td class="ng-binding">${usuario.fullName}</td>
	                                        <td class="f-500 c-cyan">${usuario.count}</td>
	                                    </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div id="recent-items-chart" class="flot-chart" data-line-chart="" datos="${listPartidos}" style="padding: 0px; position: relative;">
                        
                        </div>
                    </div>

                    
                </div>
                    
                </div>
            </div>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()" >
	        <div class="mini-charts">
	             <div class="row">
	                 <c:forEach items="${gruposUsrSigned}" var="grupo">
	                 <div class="col-sm-6 col-md-3" >
	                     <div class="mini-charts-item bgm-cyan" style="cursor: pointer;" ng-click="showGrupo(<c:out value="${grupo.id}"></c:out>)">
	                         <div class="clearfix">
	                             <div class="chart stats-bar" style="padding: 0px;">
	                             	<img style="height: 72px;" src="${pageContext.request.contextPath}/rest/comunidad/getImage/<c:out value="${grupo.id}"></c:out>" />
	                             </div>
	                             <div class="count" style="padding-left: 22px;">
	                                 <small><c:out value="${grupo.titulo}"></c:out> </small>
	                                 <h2><c:out value="${tamGruposUsrSigned[grupo.id]}"></c:out></h2>
	                             </div>
	                         </div>
	                     </div>
	                 </div>
	                 </c:forEach>
	             </div>
	         </div>
         </sec:authorize>
        
        <div class="row">
        
            <div id="categorias" class="col-md-3 col-sm-6">
	            <div id="site-visits" class="dash-widget-item bgm-teal listview lv-bordered lv-lg">
	                
	                <div class="lv-header-alt">
                                <h2 class="lvh-label hidden-xs">Categor&iacute;as</h2>
                                
                                <ul class="lv-actions actions">
                                    <li>
                                        <a href="">
                                            <i class="md md-access-time"></i>
                                        </a>
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
                                            <li>
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
	                
		                <div id="listDeportes" class="">
		                    <div class="lv-item media">
		                    <h3 class="m-0 f-400">F&uacute;tbol</h3>
		                    </div>
		                    <div class="lv-item media">
		                    <h3 class="m-0 f-400">F&uacute;tbol 7</h3>
		                    </div>
		                    <div class="lv-item media">
		                    
		                    <h3 class="m-0 f-400">F&uacute;tbol Sala</h3>
		                    </div>
		                    <div class="lv-item media">
		                    <h3 class="m-0 f-400">Paddel</h3>
		                    </div>
		                    <div class="lv-item media">
		                    
		                    <h3 class="m-0 f-400">Baloncesto</h3>
		                    </div>
		                </div>
		          </div>
	            </div>
	            
	            
	        </div>

            <div class="col-md-9 col-sm-6">

                <div class="row carousel-holder">

                    <div class="col-md-12">
                        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                                <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                                <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                            </ol>
                            <div class="carousel-inner">
                                <div class="item active">
                                    <img class="slide-image" src="http://placehold.it/800x300" alt="">
                                </div>
                                <div class="item">
                                    <img class="slide-image" src="http://placehold.it/800x300" alt="">
                                </div>
                                <div class="item">
                                    <img class="slide-image" src="http://placehold.it/800x300" alt="">
                                </div>
                            </div>
                            <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left"></span>
                            </a>
                            <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right"></span>
                            </a>
                        </div>
                    </div>

                </div>

                
                
                

            </div>
            
            <div class="row" >
            	<div class="col-lg-12" ><!-- Items -->
                    <div ng-repeat='partido in partidos' class="col-md-3 col-sm-6" >
                                <div  id="best-selling" class="dash-widget-item card">
                                    <div class="dash-widget-header card-header" style="padding: 0;" >
                                        <div class="dash-widget-title">{{partido.titulo}}</div>
                                        <img ng-src="{{partido.urlImagen}}" alt="">
                                        <div class="main-item" ng-click="showPartido(partido.id)" style="cursor: pointer;">
                                            <small>{{partido.lugar.titulo}} <div ng-if="partido.lugar != undefined">-</div> {{partido.fechaRepresentacion}}</small>
                                            <h2>{{partido.precio}}&euro;<small>{{ (partido.plazas * 1) - (partido.jugadores.length * 1) }} <i class="md md-account-child"></i>&nbsp;{{partido.fecha}}</small></h2>
                                        </div>
                                        <sec:authorize access="isAuthenticated()" >
	                                        <div ng-if="!comprobarSiEstaApuntado(partido,'${userSigned.id}')">
	                                        	<button ng-if="isPropio(partido)"  class="btn bgm-red btn-float waves-effect waves-button waves-float" style="cursor: initial;" title="Partido creado por ti">
	                                        		<i class="md md-favorite"></i>
	                                        	</button>
	                                        	<button ng-if="!isPropio(partido) && isFull(partido)"  class="btn bgm-red btn-float waves-effect waves-button waves-float" style="cursor: initial;" title="Completo">
	                                        		<i class="md md-dnd-on"></i>
	                                        	</button>
	                                        	<button ng-if="!isFull(partido) && !isPropio(partido)" ng-click="apuntarseAPartido(partido.id)" class="btn bgm-cyan btn-float waves-effect waves-button waves-float" title="Apuntate">
	                                        		<i class="md md-person-add"></i>
	                                        	</button>
	                                       	</div>
	                                       	<div ng-if="comprobarSiEstaApuntado(partido,'${userSigned.id}')"> 
	                                        	<button  class="btn bgm-green btn-float waves-effect waves-button waves-float" style="cursor: initial;" title="Ya Apuntado">
	                                        		<i class="md md-check"></i>
	                                        	</button>
	                                        </div>
                                        </sec:authorize>
                                        <sec:authorize access="isAnonymous()" >
                                        	<button onclick="window.location.href='/P/secure'"  class="btn bgm-cyan btn-float waves-effect waves-button waves-float"><i class="md md-person-add"></i></button>
                                        </sec:authorize>
                                    </div>
                                
                                    <div class="listview p-t-5">
                                        <a href="/P/usuarios/profile/{{jugador.id}}" class="lv-item" href="" ng-repeat='jugador in partido.jugadores'>
                                            <div class="media">
                                                <div class="pull-left" ng-if="jugador.tieneAvatar">
                                                    <img class="lv-img-sm" ng-src="${pageContext.request.contextPath}/usuarios/getUserImage/{{jugador.id}}" alt="">
                                                </div>
                                                <div class="pull-left lv-avatar {{jugador.avatar}}" ng-if="!jugador.tieneAvatar">
                                                	{{jugador.email.substring( 0, 1 )}}
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
                            
                      </div>
                      <div class="col-lg-12">
                      	  <div class="clearfix"></div>
	                      <div class="load-more" ng-class="getCssLoadMoreButton()">
	                          <a  ng-click="loadMorePartidos()" href=""><i class="md md-refresh"></i> {{textLoadMoreButton}}</a>
	                      </div>
	                      <div class="clearfix"></div>
                      </div>

                </div><!-- End Items -->
                <sec:authorize access="isAuthenticated()" >
            	<div class="row">
	            	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
	               	<div class="col-sm-6 col-xs-12">
	                           <!-- Calendar -->
	                           <div class="fc fc-ltr ui-widget" ui-calendar="uiConfig.calendar" ng-model="eventSources">
	                           </div>
	                           
	                           
	                           
	                </div>
	                
	                <div class="col-sm-6 col-xs-12">
	                                                        
	                            <!-- Recent Posts -->
	                            <div class="card" id="todo-lists">
	                                <div class="card-header ch-alt" >
	                                    <h2>&Uacute;ltimos Partidos <small>Partidos creados o a los que te has apuntado</small></h2>
	                                    <ul class="actions">
	                                        <li>
	                                            <a href="" ng-click="refreshPartidosJugados()">
	                                                <i class="md md-cached"></i>
	                                            </a>
	                                        </li>

	                                        <li class="dropdown">
	                                            <a href="" data-toggle="dropdown">
	                                                <i class="md md-more-vert"></i>
	                                            </a>
	                                            
	                                            <ul class="dropdown-menu dropdown-menu-right">
	                                                <li>
	                                                    <a href="">Change Date Range</a>
	                                                </li>
	                                                <li>
	                                                    <a href="">Change Graph Type</a>
	                                                </li>
	                                                <li>
	                                                    <a href="">Other Settings</a>
	                                                </li>
	                                            </ul>
	                                        </li>
	                                    </ul>
	                                    
	                                    
	                                </div>
	                                
	                                <div class="card-body tl-body" style="">
	                                	<div class="" id="add-tl-item" >
		                                    <i class="add-new-item md md-add waves-effect waves-button waves-float" style="color:#FFC107"></i>
		                                    
		                                    <div class="add-tl-body">
		                                    	<form action="#" method="POST" name="formCreatePartido" ng-init="setFormScope(this)">
		                                    			<input type="hidden" name="publico" ng-model="publico" ng-init="publico = true" ng-value="true">
	                           							<input type="hidden" name="propietario" ng-model="propietario" ng-init="propietario = ${userSigned.id}" ng-value="${userSigned.id}">
				                                        <textarea nice-scroll ng-model="titulo" name="plazas" placeholder="Titulo..." style="color: #FFC107;height: 30%;padding-bottom: 0;margin-top: -102px;"></textarea>
	                                                    
				                                        
				                                        <div style="color: rgb(85, 85, 85);margin-top: 102px;margin-left: 22px;">
					                                        <label class="radio radio-inline m-r-20">
								                                <input name="inlineRadioOptions" value="Futbol" ng-model="categoriaTitulo" type="radio">
								                                <i class="input-helper"></i>  
								                                F&uacute;tbol
								                            </label>
								                            <label class="radio radio-inline m-r-15">
								                                <input name="inlineRadioOptions" value="FutbolSala" ng-model="categoriaTitulo" type="radio">
								                                <i class="input-helper"></i>  
								                                F&uacute;tbol Sala
								                            </label>
								                            <label class="radio radio-inline m-r-15">
								                                <input name="inlineRadioOptions" value="Futbol7" ng-model="categoriaTitulo" type="radio">
								                                <i class="input-helper"></i>  
								                                F&uacute;tbol 7
								                            </label>
								                            <label class="radio radio-inline m-r-15">
								                                <input name="inlineRadioOptions" value="Baloncesto" ng-model="categoriaTitulo" type="radio">
								                                <i class="input-helper"></i>  
								                                Baloncesto
								                            </label>
								                            <label class="radio radio-inline m-r-15">
								                                <input name="inlineRadioOptions" value="Paddel" ng-model="categoriaTitulo" type="radio">
								                                <i class="input-helper"></i>  
								                                Paddel
								                            </label>
							                            </div>
							                            <input   ng-model="fecha" name="plazas" autocomplete="off" placeholder="Fecha" maxlength="19" class="form-control input-mask" data-mask="00/00/0000 00:00:00" placeholder="ej: 00/00/0000 00:00:00" type="date">
				                                        <input type="number" placeholder="Plazas" ng-model="plazas" name="plazas" class="form-control" value="0"  min="0" />
				                                        
				                                        <input type="number" placeholder="Precio" ng-model="precio" name="precio" class="form-control" value="0.0" step="0.1" min="0" />
			                                        </form>
		                                        <div class="add-tl-actions">
		                                            <a href="" data-tl-action="dismiss"><i class="md md-close"></i></a>
		                                            <a href="" ng-click="savePartido()" data-tl-action="save"><i class="md md-check"></i></a>
		                                        </div>
		                                    </div>
		                                </div>
	                                    <div class="listview" style="margin-top: 6px;">
	                                        <a class="lv-item" href="/P/partido/show/{{partido.id}}" ng-repeat='partido in partidosJugados'>
	                                            <div class="media">
	                                                <div class="pull-left">
	                                                    <img class="lv-img-sm" ng-src="{{partido.urlImagen}}" alt="">
	                                                </div>
	                                                <div class="media-body">
	                                                    <div class="lv-title">{{partido.titulo}}&nbsp;-&nbsp;{{partido.propietario.firstName}}&nbsp;{{partido.propietario.lastName}} </div>
	                                                    <small class="lv-small">{{partido.lugar.titulo}} - {{partido.fechaRepresentacion}} - {{partido.precio}}&euro;</small>
	                                                </div>
	                                            </div>
	                                        </a>
	                                        <a class="lv-footer" href="/P/partido/all">Ver Todos</a>
	                                    </div>
	                                </div>
	                            </div><!-- Card -->
	                        </div><!-- Col Recent Post -->
					</div>
				</div>
				<div class="pmb-block" id="messages-main" style="padding: 0px;min-height: 0px;max-height: 0px;margin: 0px;" >
                     	<a href="/P/partido/create" class="btn bgm-red btn-float waves-effect waves-button waves-float" id="ms-compose">
                       <i class="md md-add"></i>
                   </a>
               </div>
				</sec:authorize>
        </div><!-- ROW -->

    </div>
    <!-- /.container -->

    <div class="container">

        <hr>

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; David Romero Alcaide 2015</p>
                </div>
            </div>
        </footer>

    </div>
    <!-- /.container -->

<sec:authorize access="isAuthenticated()" >
	<script type="text/javascript">
	$(document).ready(function(){
		
	    
		//Welcome Message (not for login page)
	    function notify(message, type){
	    	$.growl({
	            message: message
	        },{
	            type: type,
	            allow_dismiss: false,
	            label: 'Cancel',
	            className: 'btn-xs btn-inverse',
	            placement: {
	                from: 'top',
	                align: 'right'
	            },
	            delay: 2500,
	            animate: {
	                    enter: 'animated bounceIn',
	                    exit: 'animated bounceOut'
	            },
	            offset: {
	                x: 20,
	                y: 85
	            }
	        });
	    };
	    
	    if (!$('.login-content')[0]) {
	    	var loginName = '<c:out value="${userSigned.email}" />'
	        notify('Bienvenido ' + loginName, 'inverse');
	    } 
	    
	    
	    /*
	     * Calendar Widget
	     */
	    if($('#calendar-widget')[0]) {
	        (function(){
	            $('#calendar-widget').fullCalendar({
			        contentHeight: 'auto',
			        theme: true,
			        editable: false,
			        selectable: false,
	                lang : 'es',
	                header: {
	                    right: '',
	                    center: 'prev, title, next',
	                    left: ''
	                },
	                defaultDate: new Date(),
	                events: angular.element('[ng-controller=InitController]').scope().partidosCalendario
	            });
	        })();
	    }
	    
	    
	});
	</script>
</sec:authorize>    


</body>

</html>
