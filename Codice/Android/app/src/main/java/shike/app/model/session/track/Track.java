package shike.app.model.session.track;

import android.location.Location;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

/**
 * Classe astratta che modella un percorso generico
 */
public abstract class Track {
	/**
	 * I punti di cui è composto il percorso
	 */
	private List<Location> points;
	/**
	 * Data di creazione del percorso
	 */
	private Date creationDate;
	/**
	 * Identificatore univoco del percorso (se è un percorso in fase di creazione vale ancora null)
	 */
	private Integer _id;

	/**
	 * Costruttore di Track
	 *
	 * @param points       lista dei punti ordinata del percorso
	 * @param creationDate data di creazione del percorso
	 * @param _id          id del percorso
	 */
	public Track(List<Location> points, Date creationDate, @Nullable Integer _id) {
		this.points = points;
		this.creationDate = creationDate;
		this._id = _id;
	}


	/**
	 * Ritorna i punti di cui è composto il percorso
	 *
	 * @return lista ordinata dei punti del percorso
	 */
	public List<Location> getPoints() {
		return points;
	}

	/**
	 * Ritorna la data di creazione del percorso
	 *
	 * @return data di creazione
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Ritorna l'identificatore univoco del percorso
	 *
	 * @return identificatore del percorso
	 */
	public Integer get_id() {
		return _id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Track)) {
			return false;
		}

		Track track = (Track) o;

		if (points.size() != track.points.size()) {
			return false;
		}
		for (int i = 0; i < points.size(); i++) {
			Location thisPoint = points.get(i);
			Location thatPoint = track.points.get(i);

			if (thisPoint.getLatitude() != thatPoint.getLatitude()) {
				return false;
			}
			if (thisPoint.getLongitude() != thatPoint.getLongitude()) {
				return false;
			}
			if (thisPoint.getAltitude() != thatPoint.getAltitude()) {
				return false;
			}
		}
		if (!creationDate.equals(track.creationDate)) {
			return false;
		}
		return (_id != null && _id.equals(track._id));

	}

	@Override
	public int hashCode() {
		int result = points.hashCode();
		result = 31 * result + creationDate.hashCode();
		result = 31 * result + (_id != null ? _id.hashCode() : 0);
		return result;
	}
}
