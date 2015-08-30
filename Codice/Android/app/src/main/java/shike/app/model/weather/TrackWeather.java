package shike.app.model.weather;

import java.util.ArrayList;
import java.util.Calendar;
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
	 * @param forecasts      lista delle previsioni meteo del percorso. Viene resa non
	 *                          modificabile e
	 *                       ordinata per data
	 * @param virtualTrackId id del percorso associato alle previsioni
	 */
	public TrackWeather(List<Weather> forecasts, int virtualTrackId) {
		this.forecasts = Collections.unmodifiableList(forecasts);

		// Ordina forecasts per data
		Collections.sort(forecasts,
			new Comparator<Weather>() {
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

	public List<Weather> getForecastByDay(Date day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

		List<Weather> weatherList = new ArrayList<>();

		for (Weather forecast : forecasts) {
			calendar.setTime(forecast.getDate());
			int thisDay = calendar.get(Calendar.DAY_OF_YEAR);
			if (thisDay == dayOfYear) {
				weatherList.add(forecast);
			}
		}
		return weatherList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TrackWeather)) {
			return false;
		}

		TrackWeather that = (TrackWeather) o;

		if (virtualTrackId != that.virtualTrackId) {
			return false;
		}
		return forecasts.containsAll(that.forecasts) && that.forecasts.containsAll(forecasts);

	}

	@Override
	public int hashCode() {
		int result = forecasts.hashCode();
		result = 31 * result + virtualTrackId;
		return result;
	}
}

