package shike.app.model.dao;

/**
 * Interfaccia che fornisce un metodo per la cancellazione di tutti i dati utente dal database.
 * (Non cancella i dati delle tabelle costanti, come genders e forecasttypes)
 */
public interface GeneralDao {
	int resetAll();
}
