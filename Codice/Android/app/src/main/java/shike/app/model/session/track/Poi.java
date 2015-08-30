package shike.app.model.session.track;

import android.location.Location;

/**
 * Classe che modella un POI (punto di interesse)
 */
public class Poi {

	/**
	 * Identificatore univoco del POI
	 */
	private int _id;

	/**
	 * Posizione geografica del POI. Contiene latitudine, longitudine e altitudine.
	 */
	private Location location;

	/**
	 * Nome del POI
	 */
	private String name;

	/**
	 * Tipologia di POI
	 */
	private PoiType type;

	public Poi(int _id, Location location, String name, PoiType type) {
		this._id = _id;
		this.location = location;
		this.name = name;
		this.type = type;
	}

	public int get_id() {
		return _id;
	}

	public Location getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public PoiType getPoiType() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Poi)) {
			return false;
		}

		Poi poi = (Poi) o;

		if (_id != poi._id) {
			return false;
		}
		if (location.getLatitude() != poi.location.getLatitude()) {
			return false;
		}
		if (location.getLongitude() != poi.location.getLongitude()) {
			return false;
		}
		if (location.getAltitude() != poi.location.getAltitude()) {
			return false;
		}
		if (!name.equals(poi.name)) {
			return false;
		}
		return type == poi.type;

	}

	@Override
	public int hashCode() {
		int result = _id;
		result = 31 * result + location.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + type.hashCode();
		return result;
	}

	/**
	 * Enum che modella le varie tipologie di POI che l'applicazione accetta
	 */
	public enum PoiType {

		/**
		 * Rifugio
		 */
		SHELTER(1, "Rifugio"),
		/**
		 * Bivacco
		 */
		CAMPING(2, "Bivacco"),
		/**
		 * Fonte d'acqua
		 */
		WATER_POINT(3, "Fonte d'acqua"),
		/**
		 * Vista panoramica
		 */
		PANORAMIC_VIEW(4, "Vista panoramica"),
		/**
		 * Cima
		 */
		PEAK(5, "Cima"),
		/**
		 * Baita
		 */
		HUT(6, "Baita"),
		/**
		 * Altro (diverso dai precedenti)
		 */
		OTHER(0, "Altro");

		/**
		 * Identificatore univoco della tipologia di POI
		 */
		private final int id;
		/**
		 * Nome della tipologia di POI
		 */
		private final String name;

		PoiType(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public static PoiType getById(final int id) {
			for (final PoiType poiType : values()) {
				if (poiType.id == id) {
					return poiType;
				}
			}
			return OTHER;
		}

		public static PoiType getByName(final String name) {
			for (final PoiType poiType : values()) {
				if (poiType.name.equals(name)) {
					return poiType;
				}
			}
			return OTHER;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

	}

}
