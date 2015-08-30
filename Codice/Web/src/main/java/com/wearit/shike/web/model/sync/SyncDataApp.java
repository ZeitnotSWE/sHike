package com.wearit.shike.web.model.sync;

import java.util.List;
import java.util.UUID;
import com.wearit.shike.web.model.session.performance.Performance;

public class SyncDataApp {
	/**
	 * Lista delle performance effettuate dall'ultima sincronizzazione
	 */
	private List<Performance> performances;
	/**
	 * Token di connessione univoco per l'utente
	 */
	private UUID connectionToken;

	public List<Performance> getPerformances() {
		return performances;
	}

	public UUID getConnectionToken() {
		return connectionToken;
	}
}
