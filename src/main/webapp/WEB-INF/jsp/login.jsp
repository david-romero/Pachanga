<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="login-content">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign In</title>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">


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
<!-- <body>



<div class="container">
    

    <div class="omb_login">
    	<h3 class="omb_authTitle">Login or <a href="#">Sign up</a></h3>
		<div class="row omb_row-sm-offset-3 omb_socialButtons">
    	    <div class="col-xs-4 col-sm-2">
		        <a href="${pageContext.request.contextPath}/auth/facebook" class="btn btn-lg btn-block omb_btn-facebook">
			        <i class="fa fa-facebook visible-xs"></i>
			        <span class="hidden-xs">Facebook</span>
		        </a>
	        </div>
        	<div class="col-xs-4 col-sm-2">
		        <a href="${pageContext.request.contextPath}/auth/twitter" class="btn btn-lg btn-block omb_btn-twitter">
			        <i class="fa fa-twitter visible-xs"></i>
			        <span class="hidden-xs">Twitter</span>
		        </a>
	        </div>	
        	<div class="col-xs-4 col-sm-2">
		        <a href="${pageContext.request.contextPath}/auth/google" class="btn btn-lg btn-block omb_btn-google">
			        <i class="fa fa-google-plus visible-xs"></i>
			        <span class="hidden-xs">Google+</span>
		        </a>
	        </div>	
		</div>

		<div class="row omb_row-sm-offset-3 omb_loginOr">
			<div class="col-xs-12 col-sm-6">
				<hr class="omb_hrOr">
				<span class="omb_spanOr">or</span>
			</div>
		</div>

		<div class="row omb_row-sm-offset-3">
			<div class="col-xs-12 col-sm-6">	
			    <form class="omb_loginForm" action="/P/secure" autocomplete="off" method="POST">
			    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div class="input-group">
						<span class="input-group-addon"><i class="fa fa-user"></i></span>
						<input type="text" class="form-control" name="username" placeholder="email address">
					</div>
					<span class="help-block"></span>
										
					<div class="input-group">
						<span class="input-group-addon"><i class="fa fa-lock"></i></span>
						<input  type="password" class="form-control" name="password" placeholder="Password">
					</div>
					<span class="help-block"></span>
					<c:if test="${not empty error}">
                    	<span class="help-block"><i class="fa fa-times"></i>&nbsp;<c:out value="${error}" /></span>
                    </c:if>

					<button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
				</form>
			</div>
    	</div>
		<div class="row omb_row-sm-offset-3">
			<div class="col-xs-12 col-sm-3" style="padding-left: 3%;">
				<label class="checkbox">
					<input type="checkbox" value="remember-me">Remember Me
				</label>
			</div>
			<div class="col-xs-12 col-sm-3">
				<p class="omb_forgotPwd">
					<a href="#">Forgot password?</a>
				</p>
			</div>
		</div>	    	
	</div>
</div>-->
<body class="login-content">
        <!-- Login -->
        <div class="lc-block toggled" id="l-login">
        	 <form id="formLogin" class="omb_loginForm" action="/P/secure" autocomplete="off" method="POST">
        	 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        	<div class="input-group m-b-20 col-md-12">
        		<button onclick="window.location.href='${pageContext.request.contextPath}/auth/facebook'" class="btn bgm-blue waves-effect waves-button waves-float"><i class="md fa fa-facebook"></i>&nbsp;  <span class="hidden-xs">Facebook</span></button>
        		&nbsp;
        		<button onclick="window.location.href='${pageContext.request.contextPath}/auth/twitter'" class="btn bgm-cyan waves-effect waves-button waves-float"><i class="md fa fa-twitter"></i>&nbsp;  <span class="hidden-xs">Twitter</span></button>
        		&nbsp;
        		<button onclick="window.location.href='${pageContext.request.contextPath}/auth/google'" class="btn bgm-red waves-effect waves-button waves-float"><i class="md fa fa-google"></i>&nbsp;  <span class="hidden-xs">Google</span></button>
        	</div>
        	
            <div class="input-group m-b-20">
                <span class="input-group-addon"><i class="md fa fa-user"></i></span>
                <div class="fg-line">
                    <input class="form-control" placeholder="Usuario" name="username" type="text">
                </div>
            </div>
            
            <div class="input-group m-b-20">
                <span class="input-group-addon"><i class="md fa fa-key"></i></span>
                <div class="fg-line">
                    <input class="form-control" placeholder="Contrase&ntilde;a" name="password" type="password">
                </div>
            </div>
            
            <c:if test="${not empty error}">
	            <div class="input-group m-b-20 has-error">
	                   <span class="help-block "><i class="fa fa-times"></i>&nbsp;<c:out value="${error}" /></span>
	            </div>
            </c:if>
            
            <div class="clearfix"></div>
            
            <div class="checkbox">
                <label>
                    <input value="remember-me" name="remember-me" type="checkbox">
                    <i class="input-helper"></i>
                    Recordar
                </label>
            </div>
            
            <a  href="#" onclick="document.getElementById('formLogin').submit()" class="btn btn-login btn-danger btn-float waves-effect waves-button waves-float"><i class="md md-arrow-forward"></i></a>
            
            <ul class="login-navigation">
                <li data-block="#l-register" class="bgm-red">Registrar</li>
                <li data-block="#l-forget-password" class="bgm-orange">¿Olvidaste la contrase&ntilde;a?</li>
            </ul>
            
            </form>
            
        </div>
        
        <!-- Register -->
        <div class="lc-block" id="l-register">     	
            <div class="input-group m-b-20">
                <span class="input-group-addon"><i class="md md-person"></i></span>
                <div class="fg-line">
                    <input class="form-control" placeholder="Username" type="text">
                </div>
            </div>
            
            <div class="input-group m-b-20">
                <span class="input-group-addon"><i class="md md-mail"></i></span>
                <div class="fg-line">
                    <input class="form-control" placeholder="Email Address" type="text">
                </div>
            </div>
            
            <div class="input-group m-b-20">
                <span class="input-group-addon"><i class="md md-accessibility"></i></span>
                <div class="fg-line">
                    <input class="form-control" placeholder="Password" type="password">
                </div>
            </div>
            
            <div class="clearfix"></div>
            
            <div class="checkbox">
                <label>
                    <input value="" type="checkbox">
                    <i class="input-helper"></i>
                    Accept the license agreement
                </label>
            </div>
            
            <a href="" class="btn btn-login btn-danger btn-float waves-effect waves-button waves-float"><i class="md md-arrow-forward"></i></a>
            
            <ul class="login-navigation">
                <li data-block="#l-login" class="bgm-green">Login</li>
                <li data-block="#l-forget-password" class="bgm-orange">Forgot Password?</li>
            </ul>
        </div>
        
        <!-- Forgot Password -->
        <div class="lc-block" id="l-forget-password">
            <p class="text-left">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla eu risus. Curabitur commodo lorem fringilla enim feugiat commodo sed ac lacus.</p>
            
            <div class="input-group m-b-20">
                <span class="input-group-addon"><i class="md md-email"></i></span>
                <div class="fg-line">
                    <input class="form-control" placeholder="Email Address" type="text">
                </div>
            </div>
            
            <a  href=""  class="btn btn-login btn-danger btn-float waves-effect waves-button waves-float"><i class="md md-arrow-forward"></i></a>
            
            <ul class="login-navigation">
                <li data-block="#l-login" class="bgm-green">Login</li>
                <li data-block="#l-register" class="bgm-red">Register</li>
            </ul>
        </div>
        
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
        
        <!-- jQuery -->
    <script src="/P/resources/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="/P/resources/js/bootstrap.min.js"></script>
    <!-- Waves -->
    <script src="/P/resources/js/waves.min.js"></script>
    <!-- Functions -->
    <script src="/P/resources/js/functions.js"></script>
    
</body>
</html>