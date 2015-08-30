package shike.app.model.dao.account;

import java.util.UUID;

import shike.app.model.user.Account;

/**
 * Interfaccia che fornisce i metodi per gestire la tabella 'account' del database
 */
public interface AccountDao {

	/**
	 * Ritorna l'unico account salvato nel database
	 *
	 * @return account salvato nel database, null se non c'è un numero di account pari a 1 nel db
	 */
	Account get();

	/**
	 * Aggiorna l'account salvato nel database
	 *
	 * @param account il nuovo account da inserire al posto di quello presente nel db
	 * @return esito dell'operazioe (true: successo, false: fallimento)
	 */
	boolean set(Account account);

	/**
	 * Rimuove l'account dal database
	 *
	 * @return true se ha rimosso l'account, false se non ha rimosso nulla (perchè la tabella era
	 * già vuota)
	 */
	boolean remove();

	/**
	 * Ritorna il token di connessione dell'account salvato nel database
	 *
	 * @return il token di connessione, o null se non è salvato alcun account nel database
	 */
	UUID getConnectionToken();
}
