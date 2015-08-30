package shike.app.model.sync;

import android.content.Context;

import java.util.List;
import java.util.UUID;

import shike.app.model.dao.performance.PerformanceDaoImpl;
import shike.app.model.service.AccountService;
import shike.app.model.session.performance.Performance;
import shike.app.model.user.Account;

/**
 * Classe che modella un oggetto contenente le informazioni da inviare alla piattaforma web
 * durante la sincronizzazione
 */
public class SyncDataApp {
	/**
	 * Lista delle performance effettuate dall'ultima sincronizzazione
	 */
	private List<Performance> performances;
	/**
	 * Token di connessione univoco per l'utente
	 */
	private UUID connectionToken;

	/**
	 * Costruttore della classe, prende i dati dai DAO di interesse
	 *
	 * @param context contesto dell'applicazione, necessario per i DAO per ottenere il
	 *                riferimento al database
	 */
	public SyncDataApp(Context context) {
		Account account = new AccountService(context).get();
		if (account != null) {
			this.connectionToken = account.getConnectionToken();
			if (this.connectionToken != null) {
				this.performances = new PerformanceDaoImpl(context).getAll();
			}
		}
	}

	public List<Performance> getPerformances() {
		return performances;
	}

	public UUID getConnectionToken() {
		return connectionToken;
	}
}
