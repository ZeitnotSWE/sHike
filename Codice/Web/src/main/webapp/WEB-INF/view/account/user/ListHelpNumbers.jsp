<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<jsp:include page="../../inc/Header.jsp" />
<title>Lista dei numeri utili - Zeitweb</title>
<jsp:include page="../../inc/Menu.jsp" />

			<h1>Lista dei numeri utili</h1>
			<nav id="navbar-sec"><a href="./add/" class="btn btn-default">Aggiungi un numero</a></nav>
	       	<div class="col-lg-4 col-lg-offset-4 input-group">
	       		<span class="input-group-addon">Filtra</span>
		    	<input id="inputFilter" type="text" class="form-control" placeholder="Testo da cercare...">
	       	</div>
	    	<table class="table table-striped">
			    <thead>
			      <tr>
			        <th>Descrizione</th>
			        <th>Numero</th>
			        <th>Azione</th>
			      </tr>
			    </thead>
				 <tbody>
					<c:forEach items="${listaHelpNum}" var="i">
				 <tr>
				 	<td>${i.getDescription()}</td>
			        <td>${i.getNumber()}</td>
			        <td><a href="<c:url value="./edit/"/>${i.getNumber()}">Modifica</a> | 
			        <a href="<c:url value="./delete/"/>${i.getNumber()}">Cancella</a></td>
			      </tr>
					</c:forEach>
				</tbody>
			</table>

<jsp:include page="../../inc/Footer.jsp" />
</body>
</html>
