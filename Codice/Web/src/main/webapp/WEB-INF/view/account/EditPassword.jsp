<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page session="true"%>

<jsp:include page="../inc/Header.jsp" />
<title>Modifica password - Zeitweb</title>
<jsp:include page="../inc/Menu.jsp" />

			<h1>Modifica la password del tuo account</h1>
			<div class="row">
				<div class="col-sm-12 col-md-8 col-md-offset-2">
					<form:form method="POST" commandName="account" action="./">
						<form:errors path="*" cssClass="alert alert-danger" element="div"/>
						<div class="input-group">
							<label for="passwordHash">Vecchia Password</label>
							<input name="password" type="password" id="password" placeholder="Password Attuale" class="form-control"/>
						</div>			
						<div class="input-group">
							<label for="passwordHash">Nuova Password (almeno una lettera e almeno un numero, minimo 8 caratteri)</label>
							<div class="col-xs-6"><form:password path="passwordHash" id="passwordHash" placeholder="Password" 
							pattern="^(?=.*\d)(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$"  class="form-control"/></div>
							
							<div class="col-xs-6"><form:password path="passwordHashRepeat" placeholder="Ripeti Password" 
							pattern="^(?=.*\d)(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$"  class="form-control"/></div>
						</div>
						<div class="input-group" id="navbar-sec">
							<button type="submit" class="btn btn-primary">Modifica password</button>
						</div>
					</form:form>
				</div>
			</div>

<jsp:include page="../inc/Footer.jsp" />
</body>
</html>