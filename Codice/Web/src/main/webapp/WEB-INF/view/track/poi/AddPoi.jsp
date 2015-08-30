<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page session="true"%>

<jsp:include page="../../inc/Header.jsp" />
<title>Aggiungi un POI - Percorso - Zeitweb</title>
<jsp:include page="../../inc/Menu.jsp" />

			<h1>Aggiungi un POI</h1>
			<form:form method="POST" commandName="poi" action="./">
				<div class="input-group">
					<label for="name">Nome del POI:</label>
					<input type="text" name="name" id="name" placeholder="Nome del POI" class="form-control" required />
				</div>
				<div class="input-group">
					<label for="type_id">Tipo:</label>
					<select class="form-control" id="type_id" name="type_id">
					    <option value="1">Rifugio</option>
					    <option value="2">Bivacco</option>
					    <option value="3">Fonte d'acqua</option>
					    <option value="4">Vista panoramica</option>
					    <option value="5">Cima</option>
					    <option value="6">Baita</option>
					    <option value="0">Altro</option>
					</select>
				</div>
				<fieldset class="gllpLatlonPicker">
				 <div class="input-group">
				 	<label for="ricerca">Cerca un POI sulla mappa:</label>
				 	<div class="input-group">
			      		<input type="text" class="gllpSearchField form-control" id="ricerca" />
			      		<span class="input-group-btn">
			        		<button class="gllpSearchButton btn btn-default" type="button">Cerca</button>
			    	  	</span>
			    	</div>
			    </div>
				<p>Sposta il segnaposto o fai un doppio click sulla mappa per riposizionarlo.</p>
				<div id="map_container" class="gllpMap">Google Maps</div>
				
				<div class="form-inline" id="map_controller">
				    <label class="sr-only" for="description">Latitudine</label>
				    <div class="input-group">
				      <div class="input-group-addon">LAT</div>
				      <input type="text" name="latitude" class="gllpLatitude form-control" value="45.251688256117646"  />
				    </div>
				    <label class="sr-only" for="number">Longitudine</label>
				    <div class="input-group">
				      <div class="input-group-addon">LON</div>
				      <input name="longitude" type="text" class="gllpLongitude form-control" value="11.96685791015625" />
				    </div>
				    <input type="hidden" class="gllpZoom" value="10"/> 
				    <input type="hidden" name="altitude" class="gllpElevation" />				    
				    <button type="button" class="gllpUpdateButton btn btn-default">Aggiorna Mappa</button>
				</div>			
				</fieldset>
				
				<div class="form-inline" id="navbar-sec">
					<button type="submit" class="btn btn-primary">Aggiungi</button>
				</div>
			</form:form>
			
<jsp:include page="../../inc/Footer.jsp" />
<script src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script src="<c:url value="/resources/js/gmaps-picker.js" />"></script>
</body>
</html>
