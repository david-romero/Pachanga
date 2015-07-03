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
<sec:authorize access="isAuthenticated()" >
		<jsp:include page="masterPage.jsp"></jsp:include>
		
		<div class="container" id="content" ng-controller="GrupoController">
			<c:forEach items="${grupos}" var="grupo">
				<div class="col-sm-4">
					<div class="card profile-view">
	                   <div class="pv-header" ng-style="{'background-image': 'url(/P/resources/imgs/headers/sm/{{getBackgroundImage(${grupo.id})}}.png) '}">
	                       <img src="${pageContext.request.contextPath}/rest/comunidad/getImage/<c:out value="${grupo.id}"></c:out>" class="pv-main" alt="">
	                   </div>
	                   
	                   <div class="pv-body">
	                       <h2>${grupo.titulo}</h2>
	                       <br>
	                       <!-- <small>Praesent vitae justo purus. In hendrerit lorem nislac lacinia urnaunc vitae ante id magna </small>
	                   		-->
	                       <ul class="pv-contact">
	                           <li><i class="md md-schedule"></i> ${grupo.fechaRepresentacion}</li>
	                           <li><i class="md md-message"></i> <c:out value="${numeroMensajesGrupo[grupo.id]}" ></c:out> Mensajes</li>
	                       </ul>
	                       
	                       <ul class="pv-follow">
	                           <li>${grupo.size} Usuarios</li>
	                           <li>${grupo.partidos} Partidos</li>
	                       </ul>
	                       
	                       <a href="/P/grupo/show/${grupo.id}" class="pv-follow-btn">Detalles</a>
	                   </div>
	               </div>
				</div>
			</c:forEach>
			<div class="pmb-block" id="messages-main" style="padding: 0px;min-height: 0px;max-height: 0px;margin: 0px;">
				<div id="add-tl-item">
	               <a href="/P/grupo/create" class="btn bgm-red btn-float waves-effect waves-button waves-float waves-effect waves-button waves-float add-new-item" id="ms-compose">
	                   <i class="md md-add"></i>
	               </a>
               </div>
           </div>
		</div>
</sec:authorize>
</body>
</html>