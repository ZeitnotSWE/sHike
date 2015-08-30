<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page session="true"%>

<jsp:include page="../../inc/Header.jsp" />
<title>Lista dei Percorsi - Zeitweb</title>
<jsp:include page="../../inc/Menu.jsp" />

			<h1>Lista dei Percorsi</h1>
			<div class="col-lg-4 col-lg-offset-4 input-group">
	       		<span class="input-group-addon">Filtra</span>
		    	<input id="inputFilter" type="text" class="form-control" placeholder="Testo da cercare...">
	       	</div>
			
			<table class="table table-striped">
		    <thead>
		      <tr>
		        <th>Numero Percorso</th>
		        <th>Lughezza</th>
		        <th>Data Creazione</th>
		        <th>Azione</th>
		       	<sec:authorize access="hasRole('ROLE_USER')">
		        <th>Condividi</th>
			    </sec:authorize>
		      </tr>
		    </thead>
			 <tbody>
				<c:forEach items="${tracks}" var="i">
			 <tr>
				<td>${i.getSyncNumber()}.${i.get_id()}</td>
				<td><fmt:formatNumber pattern="#,##0.00" value="${i.getLength()}"/> Km</td>
		        <td>${i.getCreationDateTimeFormatted()}</td>
		        <td>
		        <a href="<c:url value="show/"/>${i.get_id()}/${i.getSyncNumber()}/">Visualizza</a>
		        	| <a href="<c:url value="delete/"/>${i.get_id()}/${i.getSyncNumber()}/">Cancella</a> 
		        </td>
		       	<sec:authorize access="hasRole('ROLE_USER')">
		        <td><a href="${fbLink}show/${i.get_id()}/${i.getSyncNumber()}/" target="_blank">su Facebook</a> | 
		        <a href="${twLink}show/${i.get_id()}/${i.getSyncNumber()}/" target="_blank">su Twitter</a> | 
		        <a href="share/${i.get_id()}/${i.getSyncNumber()}/" title="Condividi con gli altri utenti il tuo percorso!">su Zeitweb</a></td>
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
		        sortList: [[0,0]],  // ordina la tabella con la prima colonna in ordine crescente 
			        
			    headers: { 
		            3: { 
		                sorter: false // disabilita la colonna delle Azioni 
		            },
				    4: { 
		                sorter: false // disabilita la colonna delle Condividi 
		            }
		        } 
		    }); 
		}); 
	</script>
</body>
</html>