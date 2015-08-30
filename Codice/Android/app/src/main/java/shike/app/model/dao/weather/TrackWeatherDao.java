package shike.app.model.dao.weather;

import java.util.List;
import java.util.Map;

import shike.app.model.weather.TrackWeather;

/**
 * Interfaccia che fornisce i metodi per la gestione delle previsioni meteo dei percorsi
 */
public interface TrackWeatherDao {

	/**
	 * Metodo che ritorna tutte le previsioni salvate sul dispositivo
	 *
	 * @return mappa contenente tutte le previsioni, organizzate per id del percorso associato
	 */
	Map<Integer, TrackWeather> getAll();

	/**
	 * Metodo che ritorna le previsioni meteo per un determinato percorso
	 *
	 * @param virtualTrackId id del percorso di cui si desiderano le previsioni
	 * @return previsioni del percorso cercato, null se il percorso non esiste o se non ha
	 * previsioni salvate
	 */
	TrackWeather get(int virtualTrackId);

	/**
	 * Cancella tutte le previsioni meteo salvate sul dispositivo
	 *
	 * @return il numero di previsioni singole rimosse
	 */
	int removeAll();

	/**
	 * Aggiunge (rimpiazzando in caso di conflitti) al database delle previsioni meteo associate a
	 * più percorsi.
	 *
	 * @param trackForecasts lista di previsioni meteo per più percorsi diversi.
	 * @param truncateFirst  se impostato a true elimina prima tutte le previsioni meteo salvate
	 *                       nel
	 *                       dispositivo, altrimenti si limita ad effettuare un rimpiazzo.
	 * @return il numero di previsioni singole aggiunte
	 */
	int add(List<TrackWeather> trackForecasts, boolean truncateFirst);
}
