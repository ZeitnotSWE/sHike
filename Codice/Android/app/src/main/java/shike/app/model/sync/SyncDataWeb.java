package shike.app.model.sync;

import java.util.ArrayList;
import java.util.List;

import shike.app.model.session.track.Poi;
import shike.app.model.session.track.VirtualTrack;
import shike.app.model.user.Account;
import shike.app.model.user.HelpNumber;
import shike.app.model.weather.TrackWeather;

/**
 * Classe che modella i dati che l'applicazione riceve dalla piattaforma web durante la
 * sincronizzazione
 */
public class SyncDataWeb {
	/**
	 * Indica se si sono verificati errori lato web
	 */
	private Error error = Error.SYNC_FAILED;
	/**
	 * Lista dei POI aggiornati
	 */
	private List<Poi> pois = new ArrayList<>();
	/**
	 * Informazioni aggiornate sull'account dell'utente
	 */
	private Account account;
	/**
	 * Lista delle previsioni associate ai percorsi
	 */
	private List<TrackWeather> forecasts = new ArrayList<>();
	/**
	 * Lista dei percorsi scaricati
	 */
	private List<VirtualTrack> virtualTracks = new ArrayList<>();
	/**
	 * Lista aggiornata dei numeri di soccorso dell'utente
	 */
	private List<HelpNumber> helpNumbers = new ArrayList<>();

	public Account getAccount() {
		return account;
	}

	public List<HelpNumber> getHelpNumbers() {
		return helpNumbers;
	}

	public List<Poi> getPois() {
		return pois;
	}

	public List<TrackWeather> getForecasts() {
		return forecasts;
	}

	public List<VirtualTrack> getVirtualTracks() {
		return virtualTracks;
	}

	public boolean hasError() {
		return error != Error.NONE;
	}

	public Error getError() {
		return error;
	}

	public enum Error {NONE, TOKEN_NOT_FOUND, SAVE_FAILED, SYNC_FAILED, EPIC_FAIL}
}
