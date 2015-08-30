<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>

<jsp:include page="../../inc/Header.jsp" />
<title>Admin - Zeitweb</title>
<jsp:include page="../../inc/Menu.jsp" />

			<h1>Pagina di controllo amministratore</h1>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
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
				<p>Benvenuto, da questa pagina sarà possibile accedere alle principali azioni disponibili 
					nella piattaforma web.</p>
				<div class="row">
					<div class="col-md-6 panel panel-success">
				      <div class="panel-heading">
				        <h3 class="panel-title" id="panel-title">Ultimi utenti registrati<a class="anchorjs-link" href="#panel-title"><span class="anchorjs-icon"></span></a></h3>
				      </div>
				      <table class="panel-body table table-striped">
					    <thead>
					      <tr>
					        <th>Nome e Cognome</th>
					        <th>Email</th>
					        <th>ID</th>
					      </tr>
					    </thead>
						<tbody>
							<c:forEach items="${listaUser}" var="i">
							<tr>
						        <td>${i.getFirstName()} ${i.getLastName()} </td>
					        	<td>${i.getEmailAddress()}</td>
					        	<td>${i.get_id()}</td>
						    </tr>
							</c:forEach>
						</tbody>
						</table>
						<div class="panel-footer more-link"><a href="../admin/accounts/">Vedi tutti gli utenti ►</a></div>
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
						<div class="panel-footer more-link"><a href="../admin/tracks/poi/">Vedi tutti i POI ►</a></div>
					</div>
				  
					<div class="col-md-10 col-md-offset-1 panel panel-success">
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
						<div class="panel-footer more-link"><a href="../admin/virtualtracks/">Vedi tutti i percorsi ►</a></div>
					</div>  
				</div>
			</c:if>
			</sec:authorize>

<jsp:include page="../../inc/Footer.jsp" />
	
</body>
</html>
	
