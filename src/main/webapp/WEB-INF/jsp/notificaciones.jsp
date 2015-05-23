<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="masterPage.jsp"></jsp:include>
<sec:authorize access="isAuthenticated()">
	<sec:authentication var="user" property="principal" />
	<div class="container" id="content" ng-app="pachanga">
	    <div ng-controller="NotificacionController" ng-init="loadNotificaciones(1)">
                    <div class="block-header">
                        <h2>Notificaciones</h2>
                    </div>
                
                    <div class="card" >
                        <div class="listview lv-bordered lv-lg" >
                            <div class="lv-header-alt">
                                <h2 class="lvh-label hidden-xs">Some text here</h2>
                                
                                <ul class="lv-actions actions">
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
                                                <a href="">Last Modified</a>
                                            </li>
                                            <li>
                                                <a href="">Last Edited</a>
                                            </li>
                                            <li>
                                                <a href="">Name</a>
                                            </li>
                                            <li>
                                                <a href="">Date</a>
                                            </li>
                                        </ul>
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
                                <div class="lv-item media" ng-repeat="notificacion in notificaciones"><!-- Bloque Notificacion -->
                                    <div class="checkbox pull-left">
                                        <label>
                                            <input type="checkbox" value="">
                                            <i class="input-helper"></i>
                                        </label>
                                    </div>
                                    <div class="pull-left">
                                        <img class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/${user.username}" alt="">
                                    </div>
                                    <div class="media-body">
                                        <div class="lv-title">{{notificacion.titulo}}</div>
                                        <small class="lv-small">{{notificacion.contenido}}</small>
                                        <ul class="lv-attrs">
                                            <li>Date Created: {{notificacion.fechaRepresentacion}}</li>
                                            <li>Members: 78954</li>
                                            <li>Published: No</li>
                                        </ul>
                                        
                                        <div class="lv-actions actions dropdown">
                                            <a href="" data-toggle="dropdown" aria-expanded="true">
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
                                        </div>
                                    </div>
                                </div>
                                <!--  
                                <div class="lv-item media">
                                    <div class="checkbox pull-left">
                                        <label>
                                            <input type="checkbox" value="">
                                            <i class="input-helper"></i>
                                        </label>
                                    </div>
                                    <div class="media-body">
                                        <div class="lv-title">Dulla vel metus scelerisque ante sollicitudin commodo purus odio</div>
                                        <small class="lv-small">Nunc quis diam diamurabitur at dolor elementum, dictum turpis vel</small>
                                    
                                        <div class="lv-actions actions dropdown">
                                            <a href="" data-toggle="dropdown" aria-expanded="true">
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
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="lv-item media">
                                    <div class="checkbox pull-left">
                                        <label>
                                            <input type="checkbox" value="">
                                            <i class="input-helper"></i>
                                        </label>
                                    </div>
                                    <div class="media-body">
                                        <div class="lv-title">Per an error perpetua, fierent fastidii recteque ad pro. Mei id brute intellegam</div>
                                        <ul class="lv-attrs">
                                            <li>Date Created: 09/06/1988</li>
                                            <li>Members: 78954</li>
                                            <li>Published: No</li>
                                        </ul>
                                        
                                        <div class="lv-actions actions dropdown">
                                            <a href="" data-toggle="dropdown" aria-expanded="true">
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
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="lv-item media">
                                    <div class="checkbox pull-left">
                                        <label>
                                            <input type="checkbox" value="">
                                            <i class="input-helper"></i>
                                        </label>
                                    </div>
                                    <div class="media-body">
                                        <div class="lv-title">Per an error perpetua, fierent fastidii recteque ad pro. Mei id brute intellegam</div>
                                        <small class="lv-small">An erant explicari salutatus duo, sumo doming delicata in cum. Eos at augue viderer principes</small>
                                        
                                        <ul class="lv-attrs">
                                            <li>Date Created: 09/06/1988</li>
                                            <li>Members: 78954</li>
                                            <li>Published: No</li>
                                        </ul>
                                       
                                        <div class="lv-actions actions dropdown">
                                            <a href="" data-toggle="dropdown" aria-expanded="true">
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
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="lv-item media">
                                    <div class="checkbox pull-left">
                                        <label>
                                            <input type="checkbox" value="">
                                            <i class="input-helper"></i>
                                        </label>
                                    </div>
                                    <div class="pull-left">
                                        <img class="lv-img-sm" src="${pageContext.request.contextPath}/usuarios/getUserImage/${user.username}" alt="">
                                    </div>
                                    <div class="media-body">
                                        <div class="lv-title">Per an error perpetua, fierent fastidii recteque ad pro. Mei id brute intellegam</div>
                                        <small class="lv-small">Phasellus a ante et est ornare accumsan at vel magnauis blandit turpis at augue ultricies</small>
                                        
                                        <div class="lv-actions actions dropdown">
                                            <a href="" data-toggle="dropdown" aria-expanded="true">
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
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="lv-item media">
                                    <div class="checkbox pull-left">
                                        <label>
                                            <input type="checkbox" value="">
                                            <i class="input-helper"></i>
                                        </label>
                                    </div>
                                    <div class="pull-left">
                                        <img class="lv-img-sm" src="img/profile-pics/2.jpg" alt="">
                                    </div>
                                    <div class="media-body">
                                        <div class="lv-title">Wel ea elit iuvaret. Mea labitur definitiones ex, id atqui accusata vix, vim nibh mandamus ad</div>
                                        <small class="lv-small">Wendy create ipsum dolor sit amet, per cu solet docendi ntoenstion</small>
                                        
                                        <ul class="lv-attrs">
                                            <li>Date Created: 09/06/1988</li>
                                            <li>Members: 78954</li>
                                            <li>Published: No</li>
                                        </ul>
                                        
                                        <div class="lv-actions actions dropdown">
                                            <a href="" data-toggle="dropdown" aria-expanded="true">
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
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="lv-item media">
                                    <div class="checkbox pull-left">
                                        <label>
                                            <input type="checkbox" value="">
                                            <i class="input-helper"></i>
                                        </label>
                                    </div>
                                    <div class="pull-left">
                                        <img class="lv-img-sm" src="img/profile-pics/3.jpg" alt="">
                                    </div>
                                    <div class="media-body">
                                        <div class="lv-title">Per an error perpetua, fierent fastidii recteque ad pro. Mei id brute intellegam</div>
                                        
                                        <ul class="lv-attrs">
                                            <li>Date Created: 11/06/1988</li>
                                            <li>Members: 212</li>
                                            <li>Published: Yes</li>
                                        </ul>
                                        
                                        <div class="lv-actions actions dropdown">
                                            <a href="" data-toggle="dropdown" aria-expanded="true">
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
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="lv-item media">
                                    <div class="checkbox pull-left">
                                        <label>
                                            <input type="checkbox" value="">
                                            <i class="input-helper"></i>
                                        </label>
                                    </div>
                                    <div class="pull-left">
                                        <img class="lv-img-sm" src="img/profile-pics/4.jpg" alt="">
                                    </div>
                                    <div class="media-body">
                                        <div class="lv-title">Per an error perpetua, fierent fastidii recteque ad pro. Mei id brute intellegam</div>
                                        <small class="lv-small">Phasellus a ante et est ornare accumsan at vel magnauis blandit turpis at augue ultricies</small>
                                        
                                        <div class="lv-actions actions dropdown">
                                            <a href="" data-toggle="dropdown" aria-expanded="true">
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
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="lv-item media">
                                    <div class="checkbox pull-left">
                                        <label>
                                            <input type="checkbox" value="">
                                            <i class="input-helper"></i>
                                        </label>
                                    </div>
                                    <div class="media-body">
                                        <div class="lv-title">Dulla vel metus scelerisque ante sollicitudin commodo purus odio</div>
                                        <small class="lv-small">Nunc quis diam diamurabitur at dolor elementum, dictum turpis vel</small>
                                    
                                        <div class="lv-actions actions dropdown">
                                            <a href="" data-toggle="dropdown" aria-expanded="true">
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
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="lv-item media">
                                    <div class="checkbox pull-left">
                                        <label>
                                            <input type="checkbox" value="">
                                            <i class="input-helper"></i>
                                        </label>
                                    </div>
                                    <div class="media-body">
                                        <div class="lv-title">Per an error perpetua, fierent fastidii recteque ad pro. Mei id brute intellegam</div>
                                        <ul class="lv-attrs">
                                            <li>Date Created: 09/06/1988</li>
                                            <li>Members: 78954</li>
                                            <li>Published: No</li>
                                        </ul>
                                        
                                        <div class="lv-actions actions dropdown">
                                            <a href="" data-toggle="dropdown" aria-expanded="true">
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
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="lv-item media">
                                    <div class="checkbox pull-left">
                                        <label>
                                            <input type="checkbox" value="">
                                            <i class="input-helper"></i>
                                        </label>
                                    </div>
                                    <div class="media-body">
                                        <div class="lv-title">Per an error perpetua, fierent fastidii recteque ad pro. Mei id brute intellegam</div>
                                        <small class="lv-small">An erant explicari salutatus duo, sumo doming delicata in cum. Eos at augue viderer principes</small>
                                        
                                        <ul class="lv-attrs">
                                            <li>Date Created: 09/06/1988</li>
                                            <li>Members: 78954</li>
                                            <li>Published: No</li>
                                        </ul>
                                       
                                        <div class="lv-actions actions dropdown">
                                            <a href="" data-toggle="dropdown" aria-expanded="true">
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
                                        </div>
                                    </div>-->
                                </div>
                            </div>
                        </div>
                        
                        <ul class="pagination lv-pagination">
                            <li>
                                <a href="" aria-label="Previous">
                                    <i class="md md-chevron-left"></i>
                                </a>
                            </li>
                            <li ng-click="loadNotificaciones(pagina)" ng-repeat="pagina in paginas" ng-class="getPaginacionClass(pagina)" class="">
                            	<a href="">{{pagina + 1}}</a>
                            </li>
                            <!--  <li><a href="">2</a></li>
                            <li><a href="">3</a></li>
                            <li><a href="">4</a></li>
                            <li><a href="">5</a></li>-->
                            <li>
                                <a href="" aria-label="Next">
                                    <i class="md md-chevron-right"></i>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
</sec:authorize>