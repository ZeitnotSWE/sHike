<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page session="true"%>

<jsp:include page="../../inc/Header.jsp" />
<title>${vt.getName()} - Percorso - Zeitweb</title>
<jsp:include page="../../inc/Menu.jsp" />

			<h1>Percorso <b>${vt.getName()}</b></h1>
			<i>Informazioni del percorso</i>
			<ul>
				<li><b>Distanza: </b><fmt:formatNumber pattern="#,##0.00" value="${vt.getLength()}" /> Km</li>
				<li><b>Dislivello: </b>${vt.calculateHeightDiff()} m</li>
				<li><b>Difficoltà: </b>${vt.getLevel().getName()}</li>
				<li><b>Autore: </b>${vt.getAuthorName()}</li>
				<li><b>Data Creazione: </b>${vt.getCreationDateFormatted()}</li>
			</ul>			
			<div id="map_container"></div>
			
			<div id="hourly_long_list"></div>
			
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
	     
	     <c:forEach items="${lpoi}" var="i">
	      var contentString${i.get_id()} = "<div><h4>${i.getType().getName()}</h4>${i.getName()}</div>";
      	 
	      var infowindow${i.get_id()} = new google.maps.InfoWindow({
	          content: contentString${i.get_id()}
	      });
	     
	     var marker${i.get_id()} = new google.maps.Marker({
	         position: new google.maps.LatLng(${i.getLatitude()}, ${i.getLongitude()}),
	         map: map,
	         title: "${i.getType().getName()}: ${i.getName()}" 
	     });

	     google.maps.event.addListener(marker${i.get_id()}, 'click', function() {
	    	    infowindow${i.get_id()}.open(map,marker${i.get_id()});
	    	  });

	     </c:forEach>	     
	     
	     setTimeout(loadForecast, 500);
	  });
	</script>
	<script>
	function loadForecast() {
		$.getJSON("http://api.openweathermap.org/data/2.5/forecast?callback=?&lat=${vt.getCenter().getLatitude()}&lon=${vt.getCenter().getLongitude()}&units=metric&lang=it", showForecast).error(errorHandler);
	}
	
	function showForecast(d)
	{
		forecast = d.list;
		showForcastHourlyListLong();
	}

	function showForcastHourlyListLong()
	{
	
	 	var curdate = new Date( (new Date()).getTime()- 180 * 60 * 1000 );

		var html = ''
		var lastday=0;
		var ar_tmin = new Array();
		var ar_tmax = new Array();
		min_cur = 500;
		max_cur = 0;
		lastday=0;

		for(var i = 0; i <  forecast.length-2; i ++){
			if(! forecast[i].main) continue;

			var dt = new Date(forecast[i].dt * 1000);
		
			if( curdate  > dt )	continue;

			var day =dt.getDate(); 
			var hr =dt.getHours(); 
			if(hr < 10) hr = '0'+hr;

			if(day!=lastday){
				html=html+"<tr class='well'><td colspan='2'><b>"+dt.toDateString()+"</b> </td></tr>" ;
				lastday = day;
			}
			//console.log(forecast[i].main);
			var temp = Math.round(10*(forecast[i].main.temp))/10 ;
			var tmin = Math.round(10*(forecast[i].main.temp_min)) / 10;
			var tmax = Math.round(10*(forecast[i].main.temp_max)) / 10 ;

			var text = forecast[i].weather[0].description;

			var img =  forecast[i].weather[0].icon;
			var gust = 0;
			if( forecast[i].wind )
			    gust = forecast[i].wind.speed;
			var pressure = forecast[i].main.pressure ;
			var cloud=forecast[i].clouds.all ;	

	html=html+'<tr><td>' + hr + ':00 <img src="http://openweathermap.org/img/w/'+img+'.png" > </td><td><span class="badge badge-info">'+temp +'°C </span> <i>' +text+'</i> ' +
	'<p> '+tmin+'  '+tmax+'°C,  '+gust+ 'm/s.  '+cloud+'%, ' +pressure+' hpa</p></td></tr>';

		}
		html='<table class="table">'+html+'</table>';

		$("#hourly_long_list").html(html);

	};
	</script>
</body>
</html>
