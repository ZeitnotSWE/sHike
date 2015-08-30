<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		        <th>Nome</th>
		        <th>Lunghezza</th>
		        <th>Autore</th>
		        <th>Difficoltà</th>
		        <sec:authorize access="hasRole('ROLE_USER')">
		        <th>Sincronizzato</th>
		        </sec:authorize>
		        <th>Azione</th>
		      </tr>
		    </thead>
			 <tbody>
				<c:forEach items="${vt}" var="i">
			 <tr>
			 	<td>${i.getName()}</td>
		        <td><fmt:formatNumber pattern="#,##0.00" value="${i.getLength()}" /> Km</td>
		        <td>${i.getAuthorName()}</td>
		        <td>${i.getLevel().getName()}</td>
		        <sec:authorize access="hasRole('ROLE_USER')"><td><c:choose>
   					<c:when test="${i.isToSync() == true}">
						<a href="<c:url value="/user/virtualtracks/change-sync-track/"/>${i.get_id()}">
						<span class="label label-success">Sincronizzato</span></a>
					</c:when>
					<c:otherwise>
						<a href="<c:url value="/user/virtualtracks/change-sync-track/"/>${i.get_id()}">
						<span class="label label-danger">Non Sincronizzato</span></a>
					</c:otherwise>
				</c:choose></td></sec:authorize>
		        <td>
		       	<sec:authorize access="hasRole('ROLE_ADMIN')">
		        	<a href="<c:url value="./editname/"/>${i.get_id()}/">Modifica Nome</a> | 
			    </sec:authorize>		        
		        <a href="<c:url value="show/"/>${i.get_id()}">Visualizza</a>
		       	<sec:authorize access="hasRole('ROLE_ADMIN')">
		        	| <a href="<c:url value="./delete/"/>${i.get_id()}/">Cancella</a> 
			    </sec:authorize>
		        </td>
		        
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
			       	<sec:authorize access="hasRole('ROLE_ADMIN')">	4: {</sec:authorize>
			       	<sec:authorize access="hasRole('ROLE_USER')">	5: {</sec:authorize>
		                sorter: false // disabilita la colonna delle Azioni 
		            }
		        } 
		    }); 
		}); 
	</script>
</body>
</html>

