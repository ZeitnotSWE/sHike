package shike.app.model.dao.poi;

import android.location.Location;
import android.support.annotation.Nullable;

import java.util.List;

import shike.app.model.session.track.Poi;

/**
 * Interfaccia che fornisce i metodi per gestire la tabella 'pois' del database
 */
public interface PoiDao {

	/**
	 * Metodo che ritorna tutti i POI salvati nel dispositivo
	 *
	 * @return lista contenente tutti i POI
	 */
	List<Poi> getAll();

	/**
	 * Ritorna tutti i POI a un certo raggio di distanza da una posizione geografica, ordinati
	 * per distanza crescente
	 *
	 * @param location posizione di riferimento
	 * @param radius   raggio di distanza massimo di ricerca (in metri). Se è 0 o null vengono
	 *                 ritornati tutti i POI
	 * @return lista dei POI trovati, ordinati per distanza crescente da location
	 */
	List<Poi> get(Location location, @Nullable Double radius);

	/**
	 * Aggiunge più POI al database
	 *
	 * @param pois          la lista di POI da aggiungere
	 * @param truncateFirst true se si desidera prima svuotare la tabella 'pois', false altrimenti
	 * @return il numero di POI aggiunti
	 */
	int add(List<Poi> pois, boolean truncateFirst);

	/**
	 * Rimuove tutti i POI salvati nel database
	 *
	 * @return il numero di POI eliminati
	 */
	int removeAll();

}
