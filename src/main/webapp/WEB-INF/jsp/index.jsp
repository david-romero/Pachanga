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
	                 <div class="col-sm-6 col-md-3">
	                     <div class="mini-charts-item bgm-cyan">
	                         <div class="clearfix">
	                             <div class="chart stats-bar"><canvas height="45" width="83" style="display: inline-block; width: 83px; height: 45px; vertical-align: top;"></canvas></div>
	                             <div class="count">
	                                 <small>Website Traffics</small>
	                                 <h2>987,459</h2>
	                             </div>
	                         </div>
	                     </div>
	                 </div>
	                 
	                 <div class="col-sm-6 col-md-3">
	                     <div class="mini-charts-item bgm-lightgreen">
	                         <div class="clearfix">
	                             <div class="chart stats-bar-2"><canvas height="45" width="83" style="display: inline-block; width: 83px; height: 45px; vertical-align: top;"></canvas></div>
	                             <div class="count">
	                                 <small>Website Impressions</small>
	                                 <h2>356,785K</h2>
	                             </div>
	                         </div>
	                     </div>
	                 </div>
	                 
	                 <div class="col-sm-6 col-md-3">
	                     <div class="mini-charts-item bgm-orange">
	                         <div class="clearfix">
	                             <div class="chart stats-line"><canvas height="45" width="85" style="display: inline-block; width: 85px; height: 45px; vertical-align: top;"></canvas></div>
	                             <div class="count">
	                                 <small>Total Sales</small>
	                                 <h2>$ 458,778</h2>
	                             </div>
	                         </div>
	                     </div>
	                 </div>
	                 
	                 <div class="col-sm-6 col-md-3">
	                     <div class="mini-charts-item bgm-bluegray">
	                         <div class="clearfix">
	                             <div class="chart stats-line-2"><canvas height="45" width="85" style="display: inline-block; width: 85px; height: 45px; vertical-align: top;"></canvas></div>
	                             <div class="count">
	                                 <small>Support Tickets</small>
	                                 <h2>23,856</h2>
	                             </div>
	                         </div>
	                     </div>
	                 </div>
	             </div>
	         </div>
         </sec:authorize>
        
        <div class="row">
        

            <!--  <div class="col-md-3">
                <p class="lead">Deportes</p>
                <div class="list-group">
                    <a href="#" class="list-group-item">FÃºtbol</a>
					<a href="#" class="list-group-item">FÃºtbol 7</a>
					<a href="#" class="list-group-item">FÃºtbol Sala</a>
                    <a href="#" class="list-group-item">Paddel</a>
                    <a href="#" class="list-group-item">Baloncesto</a>
                </div>
            </div>-->
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

            <div class="col-md-9">

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
                                    <div class="dash-widget-header card-header" style="padding: 0;">
                                        <div class="dash-widget-title">{{partido.titulo}}</div>
                                        <img src="{{partido.urlImagen}}" alt="">
                                        <div class="main-item">
                                            <small>{{partido.lugar.titulo}}</small>
                                            <h2>{{partido.precio}}&euro;</h2>
                                        </div>
                                        <button ng-click="apuntarseAPartido(partido.id)" class="btn bgm-cyan btn-float waves-effect waves-button waves-float"><i class="md md-person-add"></i></button>
                                    </div>
                                
                                    <div class="listview p-t-5">
                                        <a class="lv-item" href="" ng-repeat='jugador in partido.jugadores'>
                                            <div class="media">
                                                <div class="pull-left">
                                                    <img class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/${jugador.email}" alt="">
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
                                        <!-- <a class="lv-item" href="">
                                            <div class="media">
                                                <div class="pull-left">
                                                    <img class="lv-img-sm" src="img/widgets/mate7.jpg" alt="">
                                                </div>
                                                <div class="media-body">
                                                    <div class="lv-title">Huawei Ascend Mate</div>
                                                    <small class="lv-small">$649.59 - $749.99</small>
                                                </div>
                                            </div>
                                        </a>
                                        <a class="lv-item" href="">
                                            <div class="media">
                                                <div class="pull-left">
                                                    <img class="lv-img-sm" src="img/widgets/535.jpg" alt="">
                                                </div>
                                                <div class="media-body">
                                                    <div class="lv-title">Nokia Lumia 535</div>
                                                    <small class="lv-small">$189.99 - $250.00</small>
                                                </div>
                                            </div>
                                        </a> -->
                                        
                                        <a class="lv-footer" href="">
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
	                            <div class="card" id="profile-main" style="  min-height: initial;">
	                                <div class="card-header ch-alt m-b-20 pmo-pic" style="  margin: 0;">
	                                    <h2>Partidos Recientes <small>&Uacute;ltimos partidos jugados</small></h2>
	                                    <ul class="actions">
	                                        <li>
	                                            <a href="" ng-click="obtenerMisPartidos()">
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
	                                    
	                                    <!--<button class="btn bgm-cyan btn-float waves-effect waves-button waves-float"><i class="md md-add"></i></button>-->
	                                    <!-- --> <div class="dropdown pmop-message" style="bottom: 0px;  left: 100%;">
                                        <a data-toggle="dropdown" href="" class="btn bgm-cyan btn-float z-depth-1 waves-effect waves-button waves-float">
                                            <i class="md md-add"></i>
                                        </a>
                                        
                                        <div class="dropdown-menu" style="left: -250px;  height: 270px;" ng-app="pachanga-app">
                                        	<form  ng-submit="createPartido()" name="form" novalidate="" ng-controller="PartidoController">
                                            <textarea ng-model="titulo" name="username" required="" ng-minlength="6" placeholder="T&iacute;tulo del partido..."></textarea>
                                            <div class="fg-line" style="margin-left: 20px; margin-right: 20px;width: 80%;">
                                            	<input type="text" ng-model="fecha"  class="form-control input-mask" data-mask="00/00/0000 00:00" placeholder="eg: 23/05/2014 22:00" maxlength="10" autocomplete="off">
                                            </div>
                                            <div class="fg-line" style="margin-left: 20px;  margin-right: 20px;width: 80%;">
                                            	<input type="number" ng-model="precio" class="form-control input-mask" data-mask="000.000.000.000.000,00" placeholder="eg: 0,90" maxlength="22" autocomplete="off" step="0.01">
                                            </div>
                                            <textarea placeholder="Lugar..." ng-model="lugar"></textarea>
                                            <button class="btn bgm-green btn-icon waves-effect waves-button waves-float"><i class="md md-send"></i></button>
                                            </form>
                                        </div>
                                        
                                    </div>
	                                </div>
	                                
	                                <div class="card-body">
	                                    <div class="listview">
	                                        <a class="lv-item" href="" ng-repeat='partido in misPartidos'>
	                                            <div class="media">
	                                                <div class="pull-left">
	                                                    <img class="lv-img-sm" src="{{partido.urlImagen}}" alt="">
	                                                </div>
	                                                <div class="media-body">
	                                                    <div class="lv-title">{{partido.titulo}}</div>
	                                                    <small class="lv-small">Jugado en {{partido.lugar.titulo}} a {{partido.precio}} &euro;</small>
	                                                </div>
	                                            </div>
	                                        </a>
	                                        <!--  <a class="lv-item" href="">
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
	                                        </a>-->
	                                        <a class="lv-footer" href="">Ver Todos</a>
	                                    </div>
	                                </div>
	                            </div><!-- Card -->
	                        </div><!-- Col Recent Post -->
					</div>
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
