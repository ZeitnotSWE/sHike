<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<jsp:include page="../../inc/Header.jsp" />
<title>Gestione Utenti - Zeitweb</title>
<jsp:include page="../../inc/Menu.jsp" />

			<h1>Lista utenti comuni registrati su Zeitweb</h1>	
			<div class="col-lg-4 col-lg-offset-4 input-group">
	       		<span class="input-group-addon">Filtra</span>
		    	<input id="inputFilter" type="text" class="form-control" placeholder="Testo da cercare...">
	       	</div>
			<table class="table table-striped">
		    <thead>
		      <tr>
		        <th>Nome e Cognome</th>
		        <th>Email</th>
		        <th>ID</th>
		        <th>Azione</th>
		      </tr>
		    </thead>
			<tbody>
				<c:forEach items="${listaUser}" var="i">
				<tr>
			        <td>${i.getFirstName()} ${i.getLastName()} </td>
		        	<td>${i.getEmailAddress()}</td>
		        	<td>${i.get_id()}</td>
		        	<td><a href="<c:url value="./del/"/>${i.get_id()}">Cancella</a></td>
			    </tr>
				</c:forEach>
			</tbody>
			</table>
	
<jsp:include page="../../inc/Footer.jsp" />
	<script src="<c:url value="/resources/js/jquery-tablesorter.js" />"></script>
	<script>
		$(document).ready(function() { 
		    $("table").tablesorter({ 
		        sortList: [[2,0]],  // ordina la tabella con la prima colonna in ordine crescente 
			        
			    headers: { 
				    4: { 
		                sorter: false // disabilita la colonna delle Condividi 
		            }
		        } 
		    }); 
		}); 
	</script>
</body>
</html>