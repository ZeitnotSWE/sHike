<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
</head>

<body>
<nav class="navbar navbar-inverse navbar-fixed-top">		
   <div class="container">		
     <div class="navbar-header">
       <a class="navbar-brand" href="<c:url value="/"/>"><img src="<c:url value="/resources/img/logo.png" />" alt="Logo Zeitrack" /></a>		
     </div>		
     <div id="navbar" class="collapse navbar-collapse">		
       <ul class="nav navbar-nav">
       	 <li><a href="<c:url value="/"/>">Home</a></li>
         <sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_ADMIN')">	
         <li><a href="<c:url value="/dashboard/"/>">Dashboard</a></li>
         </sec:authorize>
         <sec:authorize access="hasRole('ROLE_USER')">		
         <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Percorsi<span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
			<li><a href="<c:url value="/user/tracks/"/>">I miei percorsi</a></li>	
	        <li><a href="<c:url value="/user/virtualtracks/"/>">Percorsi degli utenti</a></li>	         	         
	        <li><a href="<c:url value="/user/tracks/poi/"/>">Gestione POI</a></li>
	        <li><a href="<c:url value="/user/tracks/stats/"/>">Statistiche globali</a></li>
          </ul>
         </li>         
		 <li><a href="<c:url value="/user/helpnumbers/"/>">Numeri utili</a></li>
		 </sec:authorize>
         <sec:authorize access="hasRole('ROLE_ADMIN')">
         <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Gestione<span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
	         <li><a href="<c:url value="/admin/accounts/"/>">Lista utenti</a></li>	
	         <li><a href="<c:url value="/admin/virtualtracks/"/>">Percorsi degli utenti</a></li>	         	         
	         <li><a href="<c:url value="/admin/tracks/poi/"/>">Gestione POI</a></li>         
          </ul>
         </li>
         </sec:authorize>
	 	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<li><a href="<c:url value="/admin/edit-password/"/>">Modifica Password</a></li>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_USER')">
		<li><a href="<c:url value="/user/edit/"/>">Modifica profilo</a></li>
		</sec:authorize>
		<c:choose>
   			<c:when test="${pageContext.request.userPrincipal.name == null}">
				<li><a href="<c:url value="/login/"/>">Login</a></li>
				<li><a href="<c:url value="/signup/"/>">Registrati</a></li>
			</c:when>
			<c:otherwise>
				<c:url value="/j_spring_security_logout" var="logoutUrl" />
				<li><form action="${logoutUrl}" method="post" id="logoutForm" style="display: none;">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</form></li>
				<script>
					function formSubmit() {
						document.getElementById("logoutForm").submit();
					}
				</script>
				<li><a href="javascript:formSubmit()">Logout</a></li>
			</c:otherwise>
		</c:choose>
       </ul>		
     </div>		
   </div>		
 </nav>	
 <div class="container" id="content">
	<div class="jumbotron">