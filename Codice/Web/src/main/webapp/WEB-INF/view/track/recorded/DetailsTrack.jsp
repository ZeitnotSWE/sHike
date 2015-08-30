<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page session="true"%>

<jsp:include page="../../inc/Header.jsp" />
<title>Percorso ${track.get_id()} - Zeitweb</title>
<jsp:include page="../../inc/Menu.jsp" />

			<h1>Percorso <b>${track.get_id()}</b></h1>
			<i>Informazioni del percorso</i>
			<ul>
				<li><b>Distanza: </b><fmt:formatNumber pattern="#,##0.00" value="${track.getLength()}" /> Km</li>
				<li><b>Dislivello: </b>${track.getHeightDiff()} m</li>
				<li><b>Data Creazione: </b>${track.getCreationDateFormatted()}</li>
			</ul>
			<div id="map_container"></div>
			<div class="row">
				<div class="panel panel-success col-sm-6 col-md-6 col-md-offset-3">
				  <div class="panel-heading">
        			<h2 class="panel-title" id="panel-title">Statistiche del percorso<a class="anchorjs-link" href="#panel-title"><span class="anchorjs-icon"></span></a></h2>
      			  </div>
			      <div class="panel-body">
			        <p>In questa tabella sono contenute le statistiche relative al percorso sopra descritto.</p>
			      </div>
			      <table class="table table-striped">
			        <tbody>
			          <tr>
			            <th scope="row">Distanza percorsa</th>
			            <td><fmt:formatNumber pattern="#,##0.00" value="${stats.getDistanceKm()}"/> Km</td>
			          </tr>
			          <tr>
			            <th scope="row">Durata</th>
			            <td>${stats.getTimeFormatted()}</td>
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
			            <td>${stats.getSteps()} passi</td>
			          </tr>
			          <tr>
			            <th scope="row">Percorso</th>
			            <td>${stats.getCounter()} <c:if test="${stats.getCounter() == 1}">volta</c:if>
			            <c:if test="${stats.getCounter() > 1}">volte</c:if></td>
			          </tr>
			        </tbody>
			      </table>
		    	</div>
		  	</div>
		  	
<jsp:include page="../../inc/Footer.jsp" />

	<script src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
	<script>
	$(document).ready(function() {
	    var latlng = new google.maps.LatLng(
	    		${track.getCenter().getLatitude()}, 
	    		${track.getCenter().getLongitude()});
	    
	    var myOptions = {
	      zoom: 11,
	      center: latlng,
	      mapTypeId: google.maps.MapTypeId.HYBRID
	    };
	    
	    var map = new google.maps.Map(document.getElementById("map_container"), myOptions);
	 
	    var virtualTrackCoordinates = [
			<c:forEach items="${listPoint}" var="i">
			new google.maps.LatLng(${i.getLatitude()}, ${i.getLongitude()}),
			</c:forEach>
	     ];
	    
	    var trackPath = new google.maps.Polyline({
	       path: virtualTrackCoordinates,
	       geodesic: true,
	       strokeColor: '#FF0000',
	       strokeOpacity: 1.0,
	       strokeWeight: 2
	     });
	
	    trackPath.setMap(map);
	  });
	</script>

</body>
</html>
