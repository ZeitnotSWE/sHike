package com.wearit.shike.web.model.weather;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Classe che modella le previsioni meteo per un percorso scaricato dalla piattaforma web
 */
public class TrackWeather {

	/**
	 * Mappa contenente le previsioni meteo, organizzate per data.
	 */
	private List<Weather> forecasts;
	/**
	 * Id del percorso associato alle previsioni
	 */
	private int virtualTrackId;

	/**
	 * Costruttore di TrackWeather
	 * 
	 * @param forecasts
	 *            lista delle previsioni meteo del percorso. Viene resa non modificabile e
	 *            ordinata per data
	 * @param virtualTrackId
	 *            id del percorso associato alle previsioni
	 */
	public TrackWeather(List<Weather> forecasts, int virtualTrackId) {
		this.forecasts = Collections.unmodifiableList(forecasts);

		// Ordina forecasts per data
		Collections.sort(forecasts, new Comparator<Weather>() {
			@Override
			public int compare(Weather lhs, Weather rhs) {
				return lhs.getDate().compareTo(rhs.getDate());
			}
		});

		this.virtualTrackId = virtualTrackId;
	}

	public List<Weather> getForecasts() {
		return forecasts;
	}

	public int getVirtualTrackId() {
		return virtualTrackId;
	}

	public Weather getCurrentForecast() {
		return getForecastByDate(new Date());
	}

	public Weather getForecastByDate(Date time) {
		Weather current = null;
		for(Weather forecast : forecasts) {
			if(forecast.getDate().getTime() > time.getTime()) {
				break;
			}
			current = forecast;
		}
		return current;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((forecasts == null) ? 0 : forecasts.hashCode());
		result = prime * result + virtualTrackId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof TrackWeather)) {
			return false;
		}
		TrackWeather other = (TrackWeather) obj;
		if(forecasts == null) {
			if(other.forecasts != null) {
				return false;
			}
		} else if(!forecasts.equals(other.forecasts)) {
			return false;
		}
		if(virtualTrackId != other.virtualTrackId) {
			return false;
		}
		return true;
	}

}
