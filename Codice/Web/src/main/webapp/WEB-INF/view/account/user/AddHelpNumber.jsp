<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<jsp:include page="../../inc/Header.jsp" />
<title>Aggiungi un numero - Zeitweb</title>
<jsp:include page="../../inc/Menu.jsp" />

		<h1>Aggiungi un numero</h1>
			<form:form method="POST" cssClass="form-inline" commandName="hnumb" action="./">
				<form:errors path="*" cssClass="alert alert-danger" element="div"/>
				<c:if test="${successMsg != null}"><div id="successBlock" class="block successBlock">${successMsg}</div></c:if>
				<div class="form-group">
				    <label class="sr-only" for="description">Descrizione</label>
				    <div class="input-group">
				      <div class="input-group-addon">Descrizione</div>
				      <form:input cssClass="form-control" id="description" path="description" />
				    </div>
				    <label class="sr-only" for="number">Numero (senza spazi)</label>
				    <div class="input-group">
				      <div class="input-group-addon">Numero (senza spazi)</div>
				      <form:input cssClass="form-control" id="number" path="number" />
				    </div>
				</div>
				<div class="form-inline" id="navbar-sec">
					<button type="submit" class="btn btn-primary">Aggiungi</button>
				</div>
			</form:form>
	
<jsp:include page="../../inc/Footer.jsp" />
</body>
</html>
