<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="prettyTime" class="org.ocpsoft.prettytime.PrettyTime" />
<!DOCTYPE html>
<html lang="en">

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

<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

<!-- Vendor CSS -->
<link href="/P/resources/css/fullcalendar.css" rel="stylesheet">
<link href="/P/resources/css/animate.min.css" rel="stylesheet">
<link href="/P/resources/css/sweet-alert.min.css" rel="stylesheet">
<link href="/P/resources/css/material-design-iconic-font.min.css"
	rel="stylesheet">
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
</sec:authorize>
<section id="main">
            <section id="content" >
	<div class="container" ng-controller="PartidoController">

		<div class="card">
			<div class="card-header">
				<h2>
					Selection Example <small>Ensure that the data attribute
						[data-identifier="true"] is set on one column header.</small>
				</h2>
			</div>

			<div id="data-table-command-header"
				class="bootgrid-header container-fluid" ng-init="relacionados(1)">
				<div class="row">
				<search-bar busqueda=textoBusqueda></search-bar> 
					<div class="col-sm-12 actionBar">
						<div class="search form-group">
							<div class="input-group">
								<span class="md icon input-group-addon glyphicon-search"></span>
								<input class="search-field form-control" ng-model="query" autofocus placeholder="Search"
									type="text">
							</div>
							 
							
						</div>
						<div class="actions btn-group">
							<div class="dropdown btn-group">
								<button class="btn btn-default dropdown-toggle" type="button"
									data-toggle="dropdown">
									<span class="dropdown-text">{{page.size}}</span> <span class="caret"></span>
								</button>
								<ul class="dropdown-menu pull-right" role="menu">
									<li aria-selected="{{getAriaSelectedPageSize(10)}}" ng-class="getCssPageSize(10)"  ng-click="page.size = 10;relacionados(page.number+1)"><a href="#10"
										class="dropdown-item dropdown-item-button">10</a></li>
									<li aria-selected="{{getAriaSelectedPageSize(25)}}" ng-class="getCssPageSize(25)"><a href="#25" ng-click="page.size = 25;relacionados(page.number+1)"
										class="dropdown-item dropdown-item-button">25</a></li>
									<li aria-selected="{{getAriaSelectedPageSize(50)}}" ng-class="getCssPageSize(50)"><a href="#50" ng-click="page.size = 50;relacionados(page.number+1)"
										class="dropdown-item dropdown-item-button">50</a></li>
									<li aria-selected="{{getAriaSelectedPageSize(99999999)}}" ng-class="getCssPageSize(99999999)"><a href="#-1" ng-click="page.size = 99999999;relacionados(page.number+1)"
										class="dropdown-item dropdown-item-button">All</a></li>
								</ul>
							</div>
							<div class="dropdown btn-group">
								<button class="btn btn-default dropdown-toggle" type="button"
									data-toggle="dropdown">
									<span class="dropdown-text"><span
										class="md icon md-view-module"></span></span> <span class="caret"></span>
								</button>
								<ul class="dropdown-menu pull-right" role="menu">
									<li><div class="checkbox">
											<label class="dropdown-item"><input name="id"
												value="1" class="dropdown-item-checkbox" checked="checked"
												type="checkbox"><i class="input-helper"></i> ID</label>
										</div></li>
									<li><div class="checkbox">
											<label class="dropdown-item"><input name="sender"
												value="1" class="dropdown-item-checkbox" checked="checked"
												type="checkbox"><i class="input-helper"></i> Sender</label>
										</div></li>
									<li><div class="checkbox">
											<label class="dropdown-item"><input name="received"
												value="1" class="dropdown-item-checkbox" checked="checked"
												type="checkbox"><i class="input-helper"></i>
												Received</label>
										</div></li>
									<li><div class="checkbox">
											<label class="dropdown-item"><input name="commands"
												value="1" class="dropdown-item-checkbox" checked="checked"
												type="checkbox"><i class="input-helper"></i>
												Commands</label>
										</div></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<table aria-busy="false" id="data-table-command"
				class="table table-striped table-vmiddle bootgrid-table">
				<thead>
					<tr>
						<th data-identifier="true" data-column-id="id" class="text-left"><a
							href="javascript:void(0);" class="column-header-anchor sortable"><span
								class="text">ID</span><span class="md icon "></span></a></th>
						<th data-column-id="sender" class="text-left"><a
							href="javascript:void(0);" class="column-header-anchor sortable"><span
								class="text">Sender</span><span class="md icon "></span></a></th>
						<th data-column-id="received" class="text-left"><a
							href="javascript:void(0);" class="column-header-anchor sortable"><span
								class="text">Received</span><span class="md icon md-expand-more"></span></a></th>
						<th data-column-id="commands" class="text-left"><a
							href="javascript:void(0);" class="column-header-anchor "><span
								class="text">Commands</span><span class="md icon "></span></a></th>
					</tr>
				</thead>
				<tbody>
						<tr data-row-id="{{partido.id}}" ng-repeat="partido in page.content track by $index | filter: { titulo: query }   ">
							<td class="text-left">{{partido.titulo}}</td>
							<td class="text-left">{{partido.propietario.email}}</td>
							<td class="text-left">{{partido.fechaRepresentacion}}</td>
							<td class="text-left">
									<button type="button"
										class="btn btn-icon command-edit" data-row-id="{{partido.id}}">
										<span class="md md-edit"></span>
									</button>
									<button type="button" ng-click="remove(partido.id)"   class="btn btn-icon command-delete"
										data-row-id="{{partido.id}}">
										<span class="md md-delete"></span>
									</button>
							</td>
						</tr>
				</tbody>
			</table>
			<div id="data-table-command-footer"
				class="bootgrid-footer container-fluid">
				<div class="row">
					<div class="col-sm-6">
						<ul class="pagination">
							<li ng-click="relacionados(1)" aria-disabled="true" class="first" ng-class="page.firstPage == true ? 'disabled' : ''"><a
								href="#first" class="button"><i class="md md-more-horiz"></i></a></li>
							<li ng-click="relacionados(page.number)" aria-disabled="true" class="prev" ng-class="page.firstPage == true ? 'disabled' : ''"><a
								href="#prev" class="button"><i class="md md-chevron-left"></i></a></li>
							<li aria-selected="true" aria-disabled="false" ng-repeat="pagina in paginas"
								class="page-{{pagina}}" ng-click="relacionados(pagina)" ng-class="getCssPaginaActiva(pagina)"><a href="#" class="button">{{pagina}}</a></li>
							<li ng-click="relacionados(page.number+2)" aria-disabled="page.lastPage == true ? 'true' : 'false'" class="next" ng-class="page.lastPage == true ? 'disabled' : ''"><a href="#next"
								class="button"><i class="md md-chevron-right"></i></a></li>
							<li ng-click="relacionados(page.totalPages)" aria-disabled="{{page.lastPage == true ? 'true' : 'false'}}" class="last" ng-class="page.lastPage == true ? 'disabled' : ''"><a href="#last"
								class="button"><i class="md md-more-horiz"><i></i></i></a></li>
						</ul>
					</div>
					<div class="col-sm-6 infoBar">
						<div class="infos">Showing {{1*(page.number + 1)}} to {{page.numberOfElements}} of {{page.totalElements}} entries</div>
					</div>
				</div>
			</div>
		</div>

	</div>
</section>
</section>
	
</body>
</html>