package shike.app.model.dao.track;

import android.location.Location;
import android.support.annotation.Nullable;

import java.util.List;

import shike.app.model.session.track.VirtualTrack;

/**
 * Interfaccia che fornisce i metodi per gestire la tabella 'virtualtracks' del database
 */
public interface VirtualTrackDao {

	/**
	 * Ritorna tutti i percorsi salvati nel db
	 *
	 * @return lista contenente tutti i percorsi del database
	 */
	List<VirtualTrack> getAll();

	/**
	 * Ritorna tutti i percorsi entro una certa distanza da un punto geografico
	 *
	 * @param location punto geografico di ricerca
	 * @param radius   raggio di distanza massimo (in metri) da location entro cui cercare. Se è
	 *                 0 o null, il limite non viene considerato
	 * @return lista contenente tutti i percorsi che rispettano i criteri di ricerca,
	 * ordinati per distanza crescente da location
	 */
	List<VirtualTrack> get(Location location, @Nullable Double radius);

	/**
	 * Ritorna il percorso con l'id richiesto
	 *
	 * @param trackId l'id da cercare
	 * @return il percorso con l'id corrispondente, o null se tale percorso non esiste
	 */
	VirtualTrack get(int trackId);

	/**
	 * Aggiunge più percorsi al database
	 *
	 * @param tracks        lista con i percorsi da aggiungere
	 * @param truncateFirst true se si desidera prima svuotare la tabella 'virtualtracks', false
	 *                      altrimenti
	 * @return il numero di percorsi aggiunti
	 */
	int add(List<VirtualTrack> tracks, boolean truncateFirst);

	/**
	 * Cancella tutti i percorsi dal database
	 *
	 * @return numero di percorsi rimossi
	 */
	int removeAll();

}
