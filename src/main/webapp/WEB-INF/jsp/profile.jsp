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

	 <jsp:include page="masterPage.jsp"></jsp:include>
	
	<div class="container" id="content" ng-app="pachanga" ng-controller="ProfileController">
                    
                    <div class="block-header">
                        <h2><c:out value="${userSigned.email}"></c:out> <small>Web/UI Developer, Dubai, United Arab Emirates</small></h2>
                        
                    </div>
                    
                    <div class="card" id="profile-main" >
                        <div tabindex="4" style="overflow: hidden;" class="pm-overview c-overflow">
                            <div class="pmo-pic">
                                <div class="p-relative" ng-init="urlProfile='${pageContext.request.contextPath}/usuarios/getUserImage/${userSigned.id}'">
                                    <a href="">
                                        <img class="img-responsive" src="{{urlProfile}}" alt=""> 
                                    </a>
                                    <sec:authorize access="isAuthenticated()" >
	                				<sec:authentication var="user" property="principal" />
                                    <c:if test="${userSigned.email != user.username }">
	                                    <div class="dropdown pmop-message">
	                                        <a data-toggle="dropdown" href="" class="btn bgm-white btn-float z-depth-1 waves-effect waves-button waves-float">
	                                            <i class="md md-message"></i>
	                                        </a>
	                                        
	                                        <div class="dropdown-menu" ng-controller="MensajeController">
	                                        	<form ng-submit="sendMensaje();contenido= '';">
		                                            <textarea ng-model="contenido" placeholder="Write something..."></textarea>
		                                            <input type="hidden" ng-model="receptor" name="receptor"  ng-init="receptor='${userSigned.id}'" ng-value="${userSigned.id}" value="${userSigned.id}" />
		                                            <button type="submit" class="btn bgm-green btn-icon waves-effect waves-button waves-float"><i class="md md-send"></i></button>
	                                            </form>
	                                        </div>
	                                    </div>
                                    </c:if>
                                    <c:if test="${userSigned.email == user.username }">
	                                    <input type="file" ng-model-instant id="fileToUpload"  onchange="angular.element(this).scope().setFiles(this)" style="display:none;"/>
                                    	
	                                    <a href="" class="pmop-edit" onclick="uploadPhoto();">
	                                        <i class="md md-camera-alt"></i> <span class="hidden-xs">Actualizar Imagen</span>
	                                    </a>
                                    </c:if>
                                    </sec:authorize>
                                </div>
                                
                                
                                <div class="pmo-stat">
                                    <h2 class="m-0 c-white">1562</h2>
                                    Total Connections
                                </div>
                            </div>
                            
                            <div class="pmo-block pmo-contact hidden-xs">
                                <h2>Contact</h2>
                                
                                <ul>
                                    <li><i class="md fa fa-phone"></i> 00971 12345678 9</li>
                                    <li><i class="md fa fa-envelope"></i> malinda-h@gmail.com</li>
                                    <li><i class="md fa fa-skype"></i> malinda.hollaway</li>
                                    <li><i class="md fa fa-twitter"></i> @malinda (twitter.com/malinda)</li>
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
                            </div><!-- TAB 1 -->
                            <div ng-if="activeTab == 2">
                            	<div class="timeline">
                                <div class="t-view" data-tv-type="text">
                                    <div class="tv-header media">
                                        <a href="" class="tvh-user pull-left">
                                            <img class="img-responsive" src="img/profile-pics/profile-pic-2.jpg" alt="">
                                        </a>
                                        <div class="media-body p-t-5">
                                            <strong class="d-block">Malinda Hollaway</strong>
                                            <small class="c-gray">April 23, 2014 at 05:00</small>
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
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis sem dolor, posuere convallis blandit sit amet, aliquet in est. Ut condimentum magna enim, non venenatis elit interdum accumsan. In hac habitasse platea dictumst. Etiam molestie felis non mollis viverra. In ipsum lorem, fermentum vitae lectus in, accumsan malesuada neque.</p>
                                    
                                        <p>Suspendisse vehicula urna nisi, in luctus lacus consequat at. Nam purus dolor, tristique id lacinia sed, tincidunt congue metus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus nec gravida leo. Sed nec ligula porta, dignissim elit molestie, finibus ligula. Nunc venenatis malesuada est ac molestie. Phasellus ornare nibh eu nisl rhoncus, vitae porttitor ante feugiat. Nulla vehicula erat nec odio dignissim, sit amet porttitor lorem auctor. Maecenas fermentum tellus ex, ac aliquet nisl malesuada id.</p>
                                    
                                        <div class="clearfix"></div>
                                    
                                        <ul class="tvb-stats">
                                            <li class="tvbs-comments">54 Comments</li>
                                            <li class="tvbs-likes">254 Likes</li>
                                            <li class="tvbs-views">23K Views</li>
                                        </ul>
                                        
                                        <a class="tvc-more" href=""><i class="md md-mode-comment"></i> View all 54 Comments</a>
                                    </div>

                                    <div class="tv-comments">
                                        <ul class="tvc-lists">
                                            <li class="media">
                                                <a href="" class="tvh-user pull-left">
                                                    <img class="img-responsive" src="img/profile-pics/1.jpg" alt="">
                                                </a>
                                                <div class="media-body">
                                                    <strong class="d-block">David Peiterson</strong>
                                                    <small class="c-gray">April 23, 2014 at 05:10</small>
                                                    
                                                    <div class="m-t-10">Maecenas fermentum tellus ex, ac aliquet nisl malesuada id.</div>

                                                </div>
                                            </li>
                                            
                                            <li class="media">
                                                <a href="" class="tvh-user pull-left">
                                                    <img class="img-responsive" src="img/profile-pics/2.jpg" alt="">
                                                </a>
                                                <div class="media-body">
                                                    <strong class="d-block">Wernall Parnell</strong>
                                                    <small class="c-gray">April 22, 2014 at 13:00</small>
                                                    
                                                    <div class="m-t-10">Nulla vehicula erat nec odio dignissim, sit amet porttitor lorem auctor. Maecenas fermentum tellus ex, ac aliquet nisl malesuada id.</div>

                                                </div>
                                            </li>
                                            
                                            <li class="media">
                                                <a href="" class="tvh-user pull-left">
                                                    <img class="img-responsive" src="img/profile-pics/3.jpg" alt="">
                                                </a>
                                                <div class="media-body">
                                                    <strong class="d-block">Shane Lee Yong</strong>
                                                    <small class="c-gray">April 19, 2014 at 10:10</small>
                                                    
                                                    <div class="m-t-10">Sit amet porttitor lorem auctor. Maecenas fermentum tellus ex, ac aliquet nisl malesuada idwoon lorem ipsum.</div>
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
                                </div>
                            
                                <div class="clearfix"></div>
                            
                                <div class="load-more">
                                    <a href=""><i class="md md-refresh"></i> Load More...</a>
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