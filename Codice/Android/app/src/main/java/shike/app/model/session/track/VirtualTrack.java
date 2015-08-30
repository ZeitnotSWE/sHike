package shike.app.model.session.track;

import android.location.Location;

import java.util.Date;
import java.util.List;

/**
 * Classe che modella un percorso scaricato dalla piattaforma web
 */
public class VirtualTrack extends Track {
	/**
	 * Nome del percorso
	 */
	private String name;
	/**
	 * Lunghezza percorso (in metri)
	 */
	private double length;
	/**
	 * Baricentro del percorso (utilizzato per le previsioni meteo)
	 */
	private Location center;

	public VirtualTrack(int _id, String name, double length, List<Location> points,
						Date creationDate, Location center) {
		super(points, creationDate, _id);
		this.name = name;
		this.length = length;
		this.center = center;
	}

	public Location getCenter() {
		return center;
	}

	public String getName() {
		return name;
	}

	public double getLength() {
		return length;
	}

	@Override
	public boolean equals(Object o) {
		if (super.equals(o)) {
			if (this == o) {
				return true;
			}
			if (!(o instanceof VirtualTrack)) {
				return false;
			}
			if (!super.equals(o)) {
				return false;
			}

			VirtualTrack that = (VirtualTrack) o;

			if (Double.compare(that.length, length) != 0) {
				return false;
			}
			if (!name.equals(that.name)) {
				return false;
			}
			if (center.getLatitude() != that.center.getLatitude()) {
				return false;
			}
			return center.getLongitude() == that.center.getLongitude();
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		long temp;
		result = 31 * result + name.hashCode();
		temp = Double.doubleToLongBits(length);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + Double.valueOf(center.getLatitude()).hashCode();
		result = 31 * result + Double.valueOf(center.getLongitude()).hashCode();
		return result;
	}
}
