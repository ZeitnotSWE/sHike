<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page session="true"%>

<!DOCTYPE html>
<html>
<head>
	<jsp:include page="../../inc/Header.jsp" />
	<title>${vt.getName()} - Percorso - Zeitweb</title>
</head>
<body onload="loadMap()">
	<jsp:include page="../../inc/Menu.jsp" />

			<h1>Modifica nome del percorso</h1>
			
			<form:form method="POST" cssClass="form-inline" commandName="vt">
				<form:errors path="*" cssClass="alert alert-danger" element="div"/>
				<div class="form-group">
				    <label class="sr-only" for="nomepercorso">Nuovo nome percorso</label>
				    <div class="input-group">
				      <div class="input-group-addon">Nuovo nome percorso</div>
				      <form:input cssClass="form-control" id="nomepercorso" path="name" />
				    </div>
				</div>
				<div class="form-inline" id="navbar-sec">
					<button type="submit" class="btn btn-primary">Salva</button>
				</div>  
			</form:form>
			
			
			<div id="map_container"></div>

<jsp:include page="../../inc/Footer.jsp" />
	<script src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
	<script>
	$(document).ready(function() {
	    var latlng = new google.maps.LatLng(${vt.getCenter().getLatitude()}, ${vt.getCenter().getLongitude()});
	    
	    var myOptions = {
	      zoom: 11,
	      center: latlng,
	      mapTypeId: google.maps.MapTypeId.HYBRID
	    };
	    
	    var map = new google.maps.Map(document.getElementById("map_container"), myOptions);
	 
	    var virtualTrackCoordinates = [
			<c:forEach items="${ltp}" var="i">
			new google.maps.LatLng(${i.getLatitude()}, ${i.getLongitude()}),
			</c:forEach>
	     ];
	    
	    var vtPath = new google.maps.Polyline({
	       path: virtualTrackCoordinates,
	       geodesic: true,
	       strokeColor: '#FF0000',
	       strokeOpacity: 1.0,
	       strokeWeight: 2
	     });
	
	     vtPath.setMap(map);
	 	     
	  });
	</script>
</body>
</html>
