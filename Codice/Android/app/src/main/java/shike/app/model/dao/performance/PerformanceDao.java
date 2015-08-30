package shike.app.model.dao.performance;

import java.util.List;

import shike.app.model.session.performance.Performance;

/**
 * Interfaccia che fornisce i metodi per gestire performance della tabella 'recordedtracks'
 */
public interface PerformanceDao {

	/**
	 * Ritorna tutte le performance salvate nel database
	 *
	 * @return lista di tutte le performance
	 */
	List<Performance> getAll();

	/**
	 * Aggiunge nel database una performance. Nel caso di conflitti, la performance nuova
	 * sovrascrive quella precedente.
	 *
	 * @param performance la performance da inserire
	 * @return id della performance aggiunta
	 */
	long add(Performance performance);

	/**
	 * Aggiunge al database più performance (sovrascrivendo in caso di conflitti)
	 *
	 * @param performances  lista delle performance da aggiungere
	 * @param truncateFirst true se si desidera prima svuotare la tabella 'performance', false
	 *                      altrimenti
	 * @return il numero di performance aggiunte con successo
	 */
	int add(List<Performance> performances, boolean truncateFirst);

	/**
	 * Rimuove tutte le performance dal database
	 *
	 * @return il numero di performance eliminate
	 */
	int removeAll();

}
