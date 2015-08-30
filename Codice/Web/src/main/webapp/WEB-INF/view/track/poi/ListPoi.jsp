<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<jsp:include page="../../inc/Header.jsp" />
<title>Lista dei numeri utili - Zeitweb</title>
	<jsp:include page="../../inc/Menu.jsp" />

			<h1>Lista dei numeri utili</h1>
			<nav id="navbar-sec"><a href="./add/" class="btn btn-default">Aggiungi un POI</a></nav>
	       	<div class="col-lg-4 col-lg-offset-4 input-group">
	       		<span class="input-group-addon">Filtra</span>
		    	<input id="inputFilter" type="text" class="form-control" placeholder="Testo da cercare...">
	       	</div>
	    	<table class="table table-striped">
			    <thead>
			      <tr>
			        <th>Nome</th>
			        <th>Categoria</th>
			        <th>Azione</th>
			      </tr>
			    </thead>
				 <tbody>
					<c:forEach items="${listaPoi}" var="i">
				 <tr>
				 	<td>${i.getName()}</td>
			        <td>${i.getType().getName()}</td>
			        <sec:authorize access="hasRole('ROLE_ADMIN')">
			        <td><a href="./${i.get_id()}/">Visualizza</a> | 
			        <a href="<c:url value="./delete/"/>${i.get_id()}">Cancella</a></td>
			        </sec:authorize>
			        <sec:authorize access="hasRole('ROLE_USER')">
			        <td><a href="./${i.get_id()}/">Visualizza</a><c:if test="${currentId == i.getAuthor_id()}"> | 
			        <a href="<c:url value="./delete/"/>${i.get_id()}">Cancella</a></c:if></td>
			        </sec:authorize>
			      </tr>
					</c:forEach>
				</tbody>
			</table>
			
<jsp:include page="../../inc/Footer.jsp" />
	<script src="<c:url value="/resources/js/jquery-tablesorter.js" />"></script>
	<script>
		$(document).ready(function() { 
		    $("table").tablesorter({ 
		        sortList: [[1,0]],  // ordina la tabella con la seconda colonna in ordine crescente 
			        
			    headers: { 
		            2: { 
		                sorter: false // disabilita la colonna delle Azioni 
		            }
		        } 
		    }); 
		}); 
	</script>
</body>
</html>