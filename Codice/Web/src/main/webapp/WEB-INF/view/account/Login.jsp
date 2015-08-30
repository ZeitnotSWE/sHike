<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<jsp:include page="../inc/Header.jsp" />
<title>Login - Zeitweb</title>
<jsp:include page="../inc/Menu.jsp" />

			<h1>Effettua il login</h1>
		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="alert alert-success">${msg}</div>
		</c:if>
			<!-- start form login -->
			<div class="container">
				<div class="row">
					<div class="col-sm-6 col-md-4 col-md-offset-4">
						<div class="panel panel-default">
							<div class="panel-heading">
								<strong>Fai il login per continuare</strong>
							</div>
							<div class="panel-body">
								<form role="form" name="loginForm" action="<c:url value="/auth/login_check?targetUrl=${targetUrl}" />" method="POST" id="contact-form" data-toggle="validator">
									<fieldset>
										<div class="row">
											<div class="center-block">
												<img class="profile-img" src="<c:url value="/resources/img/avatar_2x.png" />" alt="" />
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 col-md-10 col-md-offset-1 ">
												<div class="center-group"><input type="text" name="username" id="email" class="form-control" placeholder="Email" data-error="Email non valida" required/></div>
												<div class="center-group"><input type="password" name="password" id="password" class="form-control" placeholder="Password" data-minlength="15" required/></div>
												<div class="input-group" id="navbar-sec">
													<input type="submit" class="btn btn-lg btn-primary" value="Accedi">
													<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
												</div>
											</div>
										</div>
									</fieldset>
								</form>
							</div>
							<div class="panel-footer ">
								Non hai un account? <a href="<c:url value="/signup/"/>" onClick="">Registrati</a><br />
								Hai dimenticato la password? <a href="<c:url value="/reset-password/"/>" onClick="">Recuperala</a>								
							</div>
						</div>
					</div>
				</div>
			</div><!-- end div login -->
			
<jsp:include page="../inc/Footer.jsp" />

</body>
</html>
