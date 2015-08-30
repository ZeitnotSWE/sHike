<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<jsp:include page="../inc/Header.jsp" />
<title>Richiedi nuova password - Zeitweb</title>
<jsp:include page="../inc/Menu.jsp" />

			<h1>Richiedi una nuova password</h1>
			<form:form  method="POST" class="form-inline" action="./">
			  <div class="form-group">
			    <label class="sr-only" for="email">Email di registrazione</label>
			    <div class="input-group">
			      <div class="input-group-addon">Email</div>
			      <input class="form-control" id="email" type="email" name="email" />
			    </div>
			  </div>
			  <button type="submit" class="btn btn-primary">Richiedi</button>
			</form:form >

<jsp:include page="../inc/Footer.jsp" />
</body>
</html>