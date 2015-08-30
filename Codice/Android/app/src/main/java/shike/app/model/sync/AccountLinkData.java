package shike.app.model.sync;

import java.util.UUID;

/**
 * Classe che modella i dati di collegamento account ricevuti dal web
 */
public class AccountLinkData {
	/**
	 * Codice usa e getta da utilizzare per la prima connessione
	 */
	private String oneTimeCode;
	/**
	 * Token di connessione da usare per le sincronizzazioni
	 */
	private UUID connectionToken;

	public String getOneTimeCode() {
		return oneTimeCode;
	}

	public UUID getConnectionToken() {
		return connectionToken;
	}
}
