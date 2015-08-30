<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page session="true"%>

<jsp:include page="../../inc/Header.jsp" />
<title>${poi.getType().getName()} - POI - Zeitweb</title>
<jsp:include page="../../inc/Menu.jsp" />

			<h1>${poi.getName()} - Punto di Interesse</h1>
			<i>Informazioni POI</i>
			<ul>
				<li><b>Categoria: </b>${poi.getType().getName()}</li>
				<li><b>Longitudine: </b>${poi.getLongitude()}</li>
				<li><b>Latitudine: </b>${poi.getLatitude()}</li>
				<li><b>Dal livello del mare: </b><fmt:formatNumber pattern="#,##0.00" value="${poi.getAltitude()}"/> m</li>
				<li><b>Autore: </b>${poi.getAuthorName()}</li>
			</ul>
			<div id="map_container"></div>

<jsp:include page="../../inc/Footer.jsp" />
	<script src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
	<script>
	$(document).ready(function() {
		  var myLatlng = new google.maps.LatLng(${poi.getLatitude()}, ${poi.getLongitude()});
		  var mapOptions = {
		    zoom: 10,
		    center: myLatlng
		  }
		  var map = new google.maps.Map(document.getElementById("map_container"), mapOptions);
	
		  var marker = new google.maps.Marker({
		      position: myLatlng,
		      title:"${poi.getName()} - ${poi.getType().getName()}"
		  });
	
		  // To add the marker to the map, call setMap();
		  marker.setMap(map);
	});
	</script>
</body>
</html>
