package com.wearit.shike.web.model.sync;

import java.util.List;
import com.wearit.shike.web.model.session.track.Poi;
import com.wearit.shike.web.model.session.track.VirtualTrack;
import com.wearit.shike.web.model.user.CommonAccount;
import com.wearit.shike.web.model.user.HelpNumber;
import com.wearit.shike.web.model.weather.TrackWeather;

public class SyncDataWeb {
	/**
	 * Indica se si sono verificati errori lato web
	 */
	private Error error = Error.SYNC_FAILED;
	/**
	 * Lista dei POI aggiornati
	 */
	private List<Poi> pois;
	/**
	 * Informazioni aggiornate sull'account dell'utente
	 */
	private CommonAccount account;
	/**
	 * Lista delle previsioni associate ai percorsi
	 */
	private List<TrackWeather> forecasts;
	/**
	 * Lista dei percorsi scaricati
	 */
	private List<VirtualTrack> virtualTracks;
	/**
	 * Lista aggiornata dei numeri di soccorso dell'utente
	 */
	private List<HelpNumber> helpNumbers;

	public enum Error {
		NONE, TOKEN_NOT_FOUND, SAVE_FAILED, SYNC_FAILED, EPIC_FAIL
	}

	public CommonAccount getAccount() {
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

	public Error getError() {
		return error;
	}

	public void setAccount(CommonAccount a) {
		account = a;
	}

	public void setHelpNumbers(List<HelpNumber> lhn) {
		helpNumbers = lhn;
	}

	public void setPois(List<Poi> lpoi) {
		pois = lpoi;
	}

	public void setForecasts(List<TrackWeather> ltw) {
		forecasts = ltw;
	}

	public void setVirtualTracks(List<VirtualTrack> lvt) {
		virtualTracks = lvt;
	}

	public void setError(Error e) {
		this.error = e;
	}

}
