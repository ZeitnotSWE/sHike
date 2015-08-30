/** 
 * Basato sullo script di Richard Dancsi.
 * http://www.wimagguc.com/
 *
 */

(function($) {

// per ie9 che non supporta il debug 
if (!window.console) window.console = {};
if (!window.console.log) window.console.log = function () { };
// ^^^

$.fn.gMapsLatLonPicker = (function() {

	var _self = this;

	// parametri modificabili per la mappa
	_self.params = {
		defLat : 0,
		defLng : 0,
		defZoom : 10,
		queryLocationNameWhenLatLngChanges: true,
		queryElevationWhenLatLngChanges: true,
		mapOptions : {
			mapTypeId: google.maps.MapTypeId.ROADMAP,
			mapTypeControl: false,
			disableDoubleClickZoom: true,
			zoomControlOptions: true,
			streetViewControl: false
		},
		strings : {
			markerText : "Trascina questo segnaposto",
			error_empty_field : "Non riesco a trovare le coordinate per questo posto",
			error_no_results : "Non riesco a trovare le coordinate per questo posto"
		}
	};


	// variabili usate per lo script
	_self.vars = {
		ID : null,
		LATLNG : null,
		map : null,
		marker : null,
		geocoder : null
	};

	// funzioni private per la gestione dei dati
	var setPosition = function(position) {
		_self.vars.marker.setPosition(position);
		_self.vars.map.panTo(position);

		$(_self.vars.cssID + ".gllpZoom").val( _self.vars.map.getZoom() );
		$(_self.vars.cssID + ".gllpLongitude").val( position.lng() );
		$(_self.vars.cssID + ".gllpLatitude").val( position.lat() );

		$(_self.vars.cssID).trigger("location_changed", $(_self.vars.cssID));

		if (_self.params.queryLocationNameWhenLatLngChanges) {
			getLocationName(position);
		}
		if (_self.params.queryElevationWhenLatLngChanges) {
			getElevation(position);
		}
	};

	// per il reverse geocoding
	var getLocationName = function(position) {
		var latlng = new google.maps.LatLng(position.lat(), position.lng());
		_self.vars.geocoder.geocode({'latLng': latlng}, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK && results[1]) {
				$(_self.vars.cssID + ".gllpLocationName").val(results[1].formatted_address);
			} else {
				$(_self.vars.cssID + ".gllpLocationName").val("");
			}
			$(_self.vars.cssID).trigger("location_name_changed", $(_self.vars.cssID));
		});
	};

	// for getting the elevation value for a position
	var getElevation = function(position) {
		var latlng = new google.maps.LatLng(position.lat(), position.lng());

		var locations = [latlng];

		var positionalRequest = { 'locations': locations };

		_self.vars.elevator.getElevationForLocations(positionalRequest, function(results, status) {
			if (status == google.maps.ElevationStatus.OK) {
				if (results[0]) {
					$(_self.vars.cssID + ".gllpElevation").val( results[0].elevation.toFixed(3));
				} else {
					$(_self.vars.cssID + ".gllpElevation").val("");
				}
			} else {
				$(_self.vars.cssID + ".gllpElevation").val("");
			}
			$(_self.vars.cssID).trigger("elevation_changed", $(_self.vars.cssID));
		});
	};

	// funzione di ricerca
	var performSearch = function(string, silent) {
		if (string == "") {
			if (!silent) {
				displayError( _self.params.strings.error_empty_field );
			}
			return;
		}
		_self.vars.geocoder.geocode(
			{"address": string},
			function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					$(_self.vars.cssID + ".gllpZoom").val(11);
					_self.vars.map.setZoom( parseInt($(_self.vars.cssID + ".gllpZoom").val()) );
					setPosition( results[0].geometry.location );
				} else {
					if (!silent) {
						displayError( _self.params.strings.error_no_results );
					}
				}
			}
		);
	};

	// funzione per errori
	var displayError = function(message) {
		alert(message);
	};

	// funzione pubblica
	var publicfunc = {

		// inizializzo la mappa sul div
		init : function(object) {

			if ( !$(object).attr("id") ) {
				if ( $(object).attr("name") ) {
					$(object).attr("id", $(object).attr("name") );
				} else {
					$(object).attr("id", "_MAP_" + Math.ceil(Math.random() * 10000) );
				}
			}

			_self.vars.ID = $(object).attr("id");
			_self.vars.cssID = "#" + _self.vars.ID + " ";

			_self.params.defLat  = $(_self.vars.cssID + ".gllpLatitude").val()  ? $(_self.vars.cssID + ".gllpLatitude").val()		: _self.params.defLat;
			_self.params.defLng  = $(_self.vars.cssID + ".gllpLongitude").val() ? $(_self.vars.cssID + ".gllpLongitude").val()	    : _self.params.defLng;
			_self.params.defZoom = $(_self.vars.cssID + ".gllpZoom").val()      ? parseInt($(_self.vars.cssID + ".gllpZoom").val()) : _self.params.defZoom;

			_self.vars.LATLNG = new google.maps.LatLng(_self.params.defLat, _self.params.defLng);

			_self.vars.MAPOPTIONS		 = _self.params.mapOptions;
			_self.vars.MAPOPTIONS.zoom   = _self.params.defZoom;
			_self.vars.MAPOPTIONS.center = _self.vars.LATLNG;

			_self.vars.map = new google.maps.Map($(_self.vars.cssID + ".gllpMap").get(0), _self.vars.MAPOPTIONS);
			_self.vars.geocoder = new google.maps.Geocoder();
			_self.vars.elevator = new google.maps.ElevationService();

			_self.vars.marker = new google.maps.Marker({
				position: _self.vars.LATLNG,
				map: _self.vars.map,
				title: _self.params.strings.markerText,
				draggable: true
			});

			// setta la posizione con un doppio click
			google.maps.event.addListener(_self.vars.map, 'dblclick', function(event) {
				setPosition(event.latLng);
			});

			// setta la posizione muovendo il segnaposto 
			google.maps.event.addListener(_self.vars.marker, 'dragend', function(event) {
				setPosition(_self.vars.marker.position);
			});

			// imposta il campo zoom quando l'utente cambia lo zoom dalla mappa
			google.maps.event.addListener(_self.vars.map, 'zoom_changed', function(event) {
				$(_self.vars.cssID + ".gllpZoom").val( _self.vars.map.getZoom() );
				$(_self.vars.cssID).trigger("location_changed", $(_self.vars.cssID));
			});

			// aggiorna i valori (lat/lon/zoom) in base all'input 
			$(_self.vars.cssID + ".gllpUpdateButton").bind("click", function() {
				var lat = $(_self.vars.cssID + ".gllpLatitude").val();
				var lng = $(_self.vars.cssID + ".gllpLongitude").val();
				var latlng = new google.maps.LatLng(lat, lng);
				_self.vars.map.setZoom( parseInt( $(_self.vars.cssID + ".gllpZoom").val() ) );
				setPosition(latlng);
			});

			// funzionalità di ricerca
			$(_self.vars.cssID + ".gllpSearchButton").bind("click", function() {
				performSearch( $(_self.vars.cssID + ".gllpSearchField").val(), false );
			});

			// funzionalità di ricerca tramite il listener gllp_perform_search 
			$(document).bind("gllp_perform_search", function(event, object) {
				performSearch( $(object).attr('string'), true );
			});

			// funzionalità di zoom tramite il listener gllp_perform_search
			$(document).bind("gllp_update_fields", function(event) {
				var lat = $(_self.vars.cssID + ".gllpLatitude").val();
				var lng = $(_self.vars.cssID + ".gllpLongitude").val();
				var latlng = new google.maps.LatLng(lat, lng);
				_self.vars.map.setZoom( parseInt( $(_self.vars.cssID + ".gllpZoom").val() ) );
				setPosition(latlng);
			});
		}

	}

	return publicfunc;
});

}(jQuery));

$(document).ready( function() {
	$(".gllpLatlonPicker").each(function() {
		$(document).gMapsLatLonPicker().init( $(this) );
	});
});

$(document).bind("location_changed", function(event, object) {
	console.log("changed: " + $(object).attr('id') );
});
