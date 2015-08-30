<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page session="true"%>

<jsp:include page="../inc/Header.jsp" />
<title>Registrazione - Zeitweb</title>
<jsp:include page="../inc/Menu.jsp" />

			<h1>Inserisci i dati per registrarti</h1>
			<div class="row">
				<div class="col-sm-12 col-md-8 col-md-offset-2">
					<form:form method="POST" commandName="account" action="./">
						<form:errors path="*" cssClass="alert alert-danger" element="div"/>	
						<div class="input-group">
							<label for="emailAddress">Email</label>
							<form:input type="email" path="emailAddress" id="emailAddress" placeholder="Email"  class="form-control"/>
						</div><div class="input-group">
							<label for="passwordHash">Password (almeno una lettera e almeno un numero, minimo 8 caratteri)</label>
							<div class="col-xs-6"><form:password path="passwordHash" id="passwordHash" placeholder="Password" pattern="^(?=.*\d)(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$" class="form-control"/></div>
							<div class="col-xs-6"><form:password path="passwordHashRepeat" placeholder="Ripeti Password" pattern="^(?=.*\d)(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$"  class="form-control"/></div>
						</div><div class="input-group">
							<label for="firstName">Nome e Cognome</label>
							<div class="col-xs-6"><form:input path="firstName" id="firstName" placeholder="Nome" class="form-control"/></div>
							<div class="col-xs-6"><form:input path="lastName" id="lastName" placeholder="Cognome" class="form-control"/></div>
						</div><div class="radio-inline">
							<label><input type="radio" name="gender" value="1" id="male" checked="checked" />Maschio</label>
							<label><input type="radio" name="gender" value="2" id="female" />Femmina</label>
						</div><div class="input-group">
							<label for="height">Altezza</label>
							<div class="input-group">
								<span class="input-group-addon">CM</span>
								<form:input type="number" path="height" id="height" min="1" max="300" class="form-control"/>
							</div>
						</div><div class="input-group">
							<label for="weight">Peso</label>
							<div class="input-group">
								<span class="input-group-addon">Kg</span>
								<form:input type="number" path="weight" id="weight" min="1" max="350" step="0.1" class="form-control"/>
							</div>
						</div><div class="input-group">
							<label for="birthDate">Data di nascita</label>
							<form:input path="birthDate" id="birthDate" placeholder="GG/MM/AAAA" pattern="^(0[1-9]|1\d|2\d|3[01])\/(0[1-9]|1[0-2])\/(19|20)\d{2}$" class="form-control" /></div>
						<div class="input-group" id="navbar-sec">
							<button type="submit" class="btn btn-primary">Registrati</button>
						</div>								
					</form:form>
				</div>
			</div>

<jsp:include page="../inc/Footer.jsp" />
</body>
</html>