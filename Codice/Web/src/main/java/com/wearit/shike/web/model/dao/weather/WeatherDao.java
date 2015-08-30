package com.wearit.shike.web.model.dao.weather;

import java.util.List;
import com.wearit.shike.web.model.weather.TrackWeather;
import com.wearit.shike.web.model.weather.Weather;

public interface WeatherDao {
	/**
	 * Metodo che viene usato per elencare tutti i record appartenenti alla tabella
	 * Weather.
	 * 
	 * @return
	 */
	public List<Weather> getAllWeather();

	/**
	 * Metodo che viene usato per aggiungere informazioni meteo alla tabella Weather.
	 * 
	 * @param w
	 *            meteo da aggiungere al database
	 */
	public void addTrackWeather(TrackWeather tw);

	/**
	 * Metodo che viene usato per estrarre le informazioni meteo di un track.
	 * 
	 * @param idt
	 *            id del virtual track per ottenere il meteo
	 * @return il trackweather associato al tracciato richiesto
	 */
	public List<Weather> getTrackWeather(int idt);

	/**
	 * Metodo che viene usato per estrarre una singola informazione meteo di un track
	 * 
	 * @param _idt
	 *            id del virtual track per ottenere il meteo
	 * @param date
	 *            data della previsione meteo
	 * @return il singolo trackweather associato al tracciato richiesto o null se lista
	 *         trackweather vuota
	 */
	public Weather getSingleTrackWeather(int _idt, long date);

	/**
	 * Metodo che viene usato per togliere un record dalla tabella Weather.
	 * 
	 * @param id
	 *            del meteo da cancellare
	 * @param order
	 *            forecastOrder del meteo da cancellare
	 */
	public void deleteWeather(int id, long order);

}