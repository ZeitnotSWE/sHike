package shike.app.model.session.track;

import android.location.Location;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe che modella un percorso creato durante una sessione
 */
public class RecordedTrack extends Track {


	/**
	 * Id del VirtualTrack associato al percorso. In caso di sessione libera non c'è alcun
	 * percorso associato, quindi il campo è null
	 */
	private Integer virtual_id;

	/**
	 * Costruttore per un nuovo percorso con VirtualTrack associato
	 *
	 * @param virtual_id id del VirtualTrack associato
	 */
	public RecordedTrack(@Nullable Integer virtual_id) {
		this(null, virtual_id, new Date(), new ArrayList<Location>());
	}

	/**
	 * Costruttore completo di RecordedTrack. Utile per ricreare percorsi già salvati nel database.
	 *
	 * @param _id          id del percorso (null se è un nuovo percorso, non ancora salvato nel
	 *                        database)
	 * @param virtual_id   id del VirtualTrack associato (null se non esiste)
	 * @param creationDate data di creazione del percorso
	 * @param points       lista ordinata dei punti del percorso
	 */
	public RecordedTrack(@Nullable Integer _id, @Nullable Integer virtual_id, Date creationDate,
						 List<Location> points) {
		super(points, creationDate, _id);
		this.virtual_id = virtual_id;
	}

	/**
	 * Costruttore di default, per un nuovo percorso senza VirtualTrack associato
	 */
	public RecordedTrack() {
		this(null, null, new Date(), new ArrayList<Location>());
	}

	public Integer getVirtual_id() {
		return virtual_id;
	}

	@Override
	public boolean equals(Object o) {
		if (super.equals(o)) {
			if (this == o) {
				return true;
			}
			if (!(o instanceof RecordedTrack)) {
				return false;
			}
			if (!super.equals(o)) {
				return false;
			}

			RecordedTrack that = (RecordedTrack) o;

			return !(virtual_id != null ? !virtual_id.equals(that.virtual_id) :
				that.virtual_id != null);
		}
		return false;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (virtual_id != null ? virtual_id.hashCode() : 0);
		return result;
	}
}
