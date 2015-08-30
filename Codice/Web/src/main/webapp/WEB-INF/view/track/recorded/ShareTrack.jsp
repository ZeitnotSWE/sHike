<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page session="true"%>

<!DOCTYPE html>
<html>
<head>
	<jsp:include page="../../inc/Header.jsp" />
	<title>Condividi Percorso - Zeitweb</title>
</head>
<body onload="loadMap()">
	<jsp:include page="../../inc/Menu.jsp" />
			<h1>Condividi il percorso su Zeitweb</h1>
			<form method="POST" class="form-inline" action="./">
			<form:errors path="*" cssClass="alert alert-danger" element="div"/>
				<div class="form-group">
				    <label class="sr-only" for="name">Nome percorso</label>
				    <div class="input-group">
				      <div class="input-group-addon">Nome percorso</div>
				      <input type="text" class="form-control" id="name" name="name" />
				    </div>
				</div>
				<div class="form-inline" id="navbar-sec">
					<button type="submit" class="btn btn-primary">Condividi</button>
				</div>  
			</form>

<jsp:include page="../../inc/Footer.jsp" />
</body>
</html>