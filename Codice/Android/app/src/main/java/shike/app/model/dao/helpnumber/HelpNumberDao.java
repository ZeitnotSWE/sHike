package shike.app.model.dao.helpnumber;

import java.util.List;

import shike.app.model.user.HelpNumber;

/**
 * Interfaccia che fornisce i metodi per gestire la tabella 'helpnumbers' del database.
 */
public interface HelpNumberDao {

	/**
	 * Ritorna tutti i numeri di soccorso salvati nel database
	 *
	 * @return lista contenente tutti i numeri
	 */
	List<HelpNumber> getAll();

	/**
	 * Aggiunte una lista di numeri al database, sovrascrivendo eventuali numeri già presenti.
	 *
	 * @param numbers       lista di numeri da aggiungere
	 * @param truncateFirst indica se svuotare la tabella prima di fare un aggiunta (true) o no
	 *                      (false).
	 * @return totale dei numeri aggiunti con successo
	 */
	int add(List<HelpNumber> numbers, boolean truncateFirst);

	/**
	 * Svuota la tabella
	 *
	 * @return il numero di righe rimosse
	 */
	int removeAll();
}
