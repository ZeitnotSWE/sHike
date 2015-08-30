package com.wearit.shike.web.model.dao.poi;

import java.util.List;
import com.wearit.shike.web.model.session.track.Poi;

public interface PoiDao {

	/**
	 * @return prossimo id per auto_increment tabella 'pois'
	 */
	public int getIdNext();

	/**
	 * Metodo che viene usato per elencare tutti i record appartenenti alla tabella POI.
	 * 
	 * @return una lista di POI
	 */
	public List<Poi> getAll();

	/**
	 * @param limit
	 *            limite di risultati
	 * @return una lista di POI
	 */
	public List<Poi> getAll(int limit);

	/**
	 * Ritorna il singolo Poi
	 * 
	 * @param id
	 *            del poi da restiuire
	 * @return poi selezionato con id
	 */
	public Poi get(int id);

	/**
	 * Metodo che viene usato per aggiungere un POI alla tabella POI.
	 * 
	 * @param POI
	 */
	public void add(Poi poi);

	/**
	 * Metodo che viene usato per togliere un POI dalla tabella POI.
	 * 
	 * @param id
	 *            del POI da rimuovere
	 */
	public void delete(int id);

	/**
	 * Metodo che aggiorna il POI selezionato.
	 * 
	 * @param id
	 *            del POI da aggiornare
	 * @param poi
	 *            il nuovo POI
	 */
	public void update(int id, Poi poi);
}
