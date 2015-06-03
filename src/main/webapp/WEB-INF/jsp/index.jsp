<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" ng-app="pachanga">

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
    </style>

</head>

<body ng-app="pachanga-app" ng-controller="InitController" ng-init="inicio()">

     <jsp:include page="masterPage.jsp"></jsp:include>

    <!-- Page Content -->
    <div class="container" id="content">

        <div class="block-header">
	        <ul class="actions">
	            <li>
	                <a href="/P/app">
	                    <i class="md md-cached"></i>
	                </a>
	            </li>
	        </ul>        
        </div>
        <sec:authorize access="isAuthenticated()" >
	        <div class="mini-charts">
	             <div class="row">
	                 <c:forEach items="${gruposUsrSigned}" var="grupo">
	                 <div class="col-sm-6 col-md-3" >
	                     <div class="mini-charts-item bgm-cyan" ng-click="showGrupo(<c:out value="${grupo.id}"></c:out>)">
	                         <div class="clearfix">
	                             <div class="chart stats-bar">
	                             	<img src="${pageContext.request.contextPath}/rest/comunidad/getImage/<c:out value="${grupo.id}"></c:out>" />
	                             </div>
	                             <div class="count">
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
                                    <div class="dash-widget-header card-header" style="padding: 0;" ng-click="showPartido(partido.id)">
                                        <div class="dash-widget-title">{{partido.titulo}}</div>
                                        <img src="{{partido.urlImagen}}" alt="">
                                        <div class="main-item">
                                            <small>{{partido.lugar.titulo}} - {{partido.fechaRepresentacion}}</small>
                                            <h2>{{partido.precio}}&euro;<small>{{ (partido.plazas * 1) - (partido.jugadores.length * 1) }} <i class="md md-account-child"></i></small></h2>
                                        </div>
                                        <sec:authorize access="isAuthenticated()" >
                                        	<button ng-click="apuntarseAPartido(partido.id)" class="btn bgm-cyan btn-float waves-effect waves-button waves-float"><i class="md md-person-add"></i></button>
                                        </sec:authorize>
                                        <sec:authorize access="isAnonymous()" >
                                        	<button onclick=" window.location.href='/P/secure'"  class="btn bgm-cyan btn-float waves-effect waves-button waves-float"><i class="md md-person-add"></i></button>
                                        </sec:authorize>
                                    </div>
                                
                                    <div class="listview p-t-5">
                                        <a href="/P/usuarios/profile/{{jugador.id}}" class="lv-item" href="" ng-repeat='jugador in partido.jugadores'>
                                            <div class="media">
                                                <div class="pull-left">
                                                    <img class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/{{jugador.id}}" alt="">
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
                                        
                                        <a class="lv-footer" href="/P/partido/show/{{jugador.id}}">
                                            Ver detalles
                                        </a>
                                    </div>
                                </div>
                            </div>
                            
                      </div>
                      <div class="col-lg-12">
                      	  <div class="clearfix"></div>
	                      <div class="load-more">
	                          <a  ng-click="loadMorePartidos()" href=""><i class="md md-refresh"></i> Mostrar M&aacute;s...</a>
	                      </div>
	                      <div class="clearfix"></div>
                      </div>

                </div><!-- End Items -->
                <sec:authorize access="isAuthenticated()" >
            	<div class="row">
	            	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
	               	<div class="col-sm-6 col-xs-12">
	                           <!-- Calendar -->
	                           <div class="fc fc-ltr ui-widget" id="calendar-widget">
	                           </div>
	                           
	                           
	                           
	                </div>
	                
	                <div class="col-sm-6 col-xs-12">
	                                                        
	                            <!-- Recent Posts -->
	                            <div class="card" id="todo-lists">
	                                <div class="card-header ch-alt" >
	                                    <h2>&Uacute;ltimos Partidos <small>Partidos creados o a los que te has apuntado</small></h2>
	                                    <ul class="actions">
	                                        <li>
	                                            <a href="">
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
		                                        <textarea placeholder="What you want to do..." style="color: #FFC107"></textarea>
		                                        
		                                        <div class="add-tl-actions">
		                                            <a href="" data-tl-action="dismiss"><i class="md md-close"></i></a>
		                                            <a href="" data-tl-action="save"><i class="md md-check"></i></a>
		                                        </div>
		                                    </div>
		                                </div>
	                                    <div class="listview" style="margin-top: 6px;">
	                                        <a class="lv-item" href="/P/partido/show/{{partido.id}}" ng-repeat='partido in partidosJugados'>
	                                            <div class="media">
	                                                <div class="pull-left">
	                                                    <img class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/{{partido.propietario.id}}" alt="">
	                                                </div>
	                                                <div class="media-body">
	                                                    <div class="lv-title">{{partido.propietario.firstName}}&nbsp;{{partido.propietario.lastName}} </div>
	                                                    <small class="lv-small">Cum sociis natoque penatibus et magnis dis parturient montes</small>
	                                                </div>
	                                            </div>
	                                        </a>
	                                        <a class="lv-footer" href="">Ver Todos</a>
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

    

</body>

</html>
