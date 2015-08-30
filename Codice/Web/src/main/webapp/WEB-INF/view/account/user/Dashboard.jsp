<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>

<jsp:include page="../../inc/Header.jsp" />
<title>Dashboard - Zeitweb</title>
<jsp:include page="../../inc/Menu.jsp" />

			<h1>Pagina di controllo utente</h1>
			<p>
				<sec:authorize access="hasRole('ROLE_USER')">
					<c:url value="/j_spring_security_logout" var="logoutUrl" />
					<form action="${logoutUrl}" method="post" id="logoutForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
					<script>
						function formSubmit() {
							document.getElementById("logoutForm").submit();
						}
					</script>				
					<c:if test="${pageContext.request.userPrincipal.name != null}">
					<p>Benvenuto, da questa pagina sarà possibile accedere alle principali informazioni disponibili 
						nella piattaforma web.</p>
					<div class="row">
						<div class="col-md-6 panel panel-success">
					      <div class="panel-heading">
					        <h3 class="panel-title" id="panel-title">Statistiche globali<a class="anchorjs-link" href="#panel-title"><span class="anchorjs-icon"></span></a></h3>
					      </div>
					      <table class="panel-body table table-striped">
							<tbody>
					          <tr>
					            <th scope="row">Distanza</th>
					            <td><fmt:formatNumber pattern="#,##0.00" value="${stats.getTotalDistanceKm()}"/> Km</td>
					          </tr>
					          <tr>
					            <th scope="row">Durata</th>
					            <td>${stats.getTotalTimeFormatted()}</td>
					          </tr>
					          <tr>
					            <th scope="row">Ritmo medio</th>
					            <td>${stats.getRhythm()} min/Km</td>
					          </tr>
					          <tr>
					            <th scope="row">Velocità media</th>
					            <td><fmt:formatNumber pattern="#,##0.00" value="${stats.getAvgSpeed()}"/>  Km/h</td>
					          </tr>
					          <tr>
					            <th scope="row">Velocità massima</th>
					            <td><fmt:formatNumber pattern="#,##0.00" value="${stats.getMaxSpeed()}"/>  Km/h</td>
					          </tr>
					          <tr>
					            <th scope="row">Passi</th>
					            <td>${stats.getTotalSteps()} passi</td>
					          </tr>
							</tbody>
							</table>
							<div class="panel-footer more-link"><a href="../user/tracks/stats/">Vedi tutte le statistiche ►</a></div>
						</div>
						<div class="col-md-5 col-md-offset-1 panel panel-success">
					      <div class="panel-heading">
					        <h3 class="panel-title" id="panel-title">Ultimi POI inseriti<a class="anchorjs-link" href="#panel-title"><span class="anchorjs-icon"></span></a></h3>
					      </div>
					      <table class="panel-body table table-striped">
						    <thead>
						      <tr>
						        <th>Nome</th>
						        <th>Categoria</th>
						      </tr>
						    </thead>
							 <tbody>
								<c:forEach items="${listaPoi}" var="i">
							 <tr>
							 	<td>${i.getName()}</td>
						        <td>${i.getType().getName()}</td>
						      </tr>
								</c:forEach>
							</tbody>
							</table>
							<div class="panel-footer more-link"><a href="../user/tracks/poi/">Vedi tutti i POI ►</a></div>
						</div>
						<div class="col-md-3 panel panel-primary" id="sync">
					      <div class="panel-heading">
					        <h3 class="panel-title" id="panel-title">Dispositivo Wear-IT<a class="anchorjs-link" href="#panel-title"><span class="anchorjs-icon"></span></a></h3>
					      </div>
					      <div class="panel-body">
					      	<img src="<c:url value="/resources/img/wearit.png" />" />					      	
							<c:choose>
					   		<c:when test="${token == null}">
					   			<form method="POST" action="./add-device/">
						      		<div class="input-group">
								   		<input type="text" name="device" class="form-control" placeholder="Codice" />
								   	</div>
						      		<button type="submit" class="btn btn-primary">Collega</button>
						      	</form>
							</c:when>
							<c:otherwise>
								<form method="POST" action="./delete-device/" style="margin-top: 10px;">
						      		<button type="submit" class="btn btn-primary">Disconnetti</button>
								</form>
							</c:otherwise>
							</c:choose>
					      </div>
						</div>  
						
						<div class="col-md-8 col-md-offset-1 panel panel-success">
					      <div class="panel-heading">
					        <h3 class="panel-title" id="panel-title">Ultimi tracciati condivisi<a class="anchorjs-link" href="#panel-title"><span class="anchorjs-icon"></span></a></h3>
					      </div>
					      <table class="panel-body table table-striped">
						    <thead>
						      <tr>
						        <th>Nome</th>
						        <th>Lunghezza</th>
						        <th>Autore</th>
						        <th>Difficoltà</th>
						      </tr>
						    </thead>
							 <tbody>
								<c:forEach items="${listaVirtual}" var="i">
							 <tr>
							 	<td>${i.getName()}</td>
						        <td><fmt:formatNumber pattern="#,##0.00" value="${i.getLength()}" /> Km</td>
						        <td>${i.getAuthorName()}</td>
						        <td>${i.getLevel().getName()}</td>		
						      </tr>
								</c:forEach>
							</tbody>
						  </table>
						  <div class="panel-footer more-link"><a href="../user/virtualtracks/">Vedi tutti i percorsi condivisi ►</a></div>
						</div>  
					</div>
					</c:if>
				</sec:authorize>
				<c:if test="${pageContext.request.userPrincipal.name == null}">
					<% response.sendRedirect("/web/"); %>
				</c:if>
			</p>
			
<jsp:include page="../../inc/Footer.jsp" />
</body>
</html>