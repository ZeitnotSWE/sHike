<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true"%>

<jsp:include page="../inc/Header.jsp" />
<title>Statistiche Globali - Zeitweb</title>
<jsp:include page="../inc/Menu.jsp" />

			<h1>Statistiche Globali</h1>
			<div class="row">
				<div class="panel panel-success col-sm-6 col-md-6 col-md-offset-3">
			      <div class="panel-body">
			        <p>In questa tabella sono contenute le statistiche globali, ottenute valutando 
			        tutte le sessioni effettuate dall'utente e salvate all'interno del portale Zeitweb.</p>
			      </div>

			      <table class="table">
			        <tbody>
			          <tr>
			            <th scope="row">Attività</th>
			            <td>${stats.getNumberActivity()}</td>
			          </tr>
			          <tr>
			            <th scope="row">Distanza</th>
			            <td><fmt:formatNumber pattern="#,##0.00" value="${stats.getTotalDistanceKm()}"/> Km</td>
			          </tr>
			          <tr>
			            <th scope="row">Distanza media per percorso</th>
			            <td><fmt:formatNumber pattern="#,##0.00" value="${stats.getAvgDistanceKm()}"/> Km</td>
			          </tr>
			          <tr>
			            <th scope="row">Durata</th>
			            <td>${stats.getTotalTimeFormatted()}</td>
			          </tr>
			          <tr>
			            <th scope="row">Ritmo medio</th>
			            <td>${stats.getRhythm()} min/Km</td>
			          </tr>
			          <tr>
			            <th scope="row">Velocità media</th>
			            <td><fmt:formatNumber pattern="#,##0.00" value="${stats.getAvgSpeed()}"/>  Km/h</td>
			          </tr>
			          <tr>
			            <th scope="row">Velocità massima</th>
			            <td><fmt:formatNumber pattern="#,##0.00" value="${stats.getMaxSpeed()}"/>  Km/h</td>
			          </tr>
			          <tr>
			            <th scope="row">Passi</th>
			            <td>${stats.getTotalSteps()} passi</td>
			          </tr>
			          <tr>
			            <th scope="row">Dislivello</th>
			            <td>${stats.getTotalHeightDiff()} metri</td>
			          </tr>
			          <tr>
			            <th scope="row">Livello esperienza</th>
			            <td><c:choose>
				   			<c:when test="${stats.getExpLevel() > 3}">Esperto</c:when>
							<c:otherwise>Principiante</c:otherwise>
						</c:choose></td>
			          </tr>
			        </tbody>
			      </table>
		    	</div>
		  	</div>
		  	
<jsp:include page="../inc/Footer.jsp" />
</body>
</html>
