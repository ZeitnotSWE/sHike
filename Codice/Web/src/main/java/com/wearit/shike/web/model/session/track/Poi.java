package com.wearit.shike.web.model.session.track;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Poi {
	private int _id;
	private PoiType type;
	private String name;
	private String authorName;
	@JsonProperty("location")
	private Location location = new Location();

	@JsonIgnore
	private int author_id;

	/**
	 * @return l'id del POI
	 */
	public int get_id() {
		return _id;
	}

	/**
	 * @param id
	 *            il nuovo id da impostare
	 */
	public void set_id(int id) {
		this._id = id;
	}

	/**
	 * @return l'id dell'autore
	 */
	public int getAuthor_id() {
		return author_id;
	}

	/**
	 * @param author_id
	 *            il nuovo id dell'autore da impostare
	 */
	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}

	/**
	 * @return type
	 */
	public PoiType getType() {
		return type;
	}

	/**
	 * @param id
	 *            il nuovo id da impostare
	 */
	public void setType(int id) {
		this.type = PoiType.getById(id);

	}

	/**
	 * @return authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName
	 *            il nuovo authorName da impostare
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _id;
		long temp;
		temp = Double.doubleToLongBits(location.getAltitude());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + author_id;
		temp = Double.doubleToLongBits(location.getLatitude());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(location.getLongitude());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof Poi)) {
			return false;
		}
		Poi other = (Poi) obj;
		if(_id != other._id) {
			return false;
		}
		if(Double.doubleToLongBits(location.getAltitude()) != Double
				.doubleToLongBits(other.location.getAltitude())) {
			return false;
		}
		if(author_id != other.author_id) {
			return false;
		}
		if(Double.doubleToLongBits(location.getLatitude()) != Double
				.doubleToLongBits(other.location.getLatitude())) {
			return false;
		}
		if(Double.doubleToLongBits(location.getLongitude()) != Double
				.doubleToLongBits(other.location.getLongitude())) {
			return false;
		}
		if(name == null) {
			if(other.name != null) {
				return false;
			}
		} else if(!name.equals(other.name)) {
			return false;
		}
		if(type.getId() != other.type.getId()) {
			return false;
		}
		return true;
	}

	/**
	 * Enum che modella le varie tipologie di POI che l'applicazione accetta
	 */
	public enum PoiType {

		OTHER(0, "Altro"), SHELTER(1, "Rifugio"), CAMPING(2, "Bivaco"), WATER_POINT(3,
				"Fonte d'acqua"), PANORAMIC_VIEW(4, "Vista panoramica"), PEAK(5, "Cima"), HUT(6,
				"Baita");

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

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public static PoiType getById(final int id) {
			for(final PoiType poiType : values()) {
				if(poiType.id == id) {
					return poiType;
				}
			}
			return null;
		}

		public static PoiType getByName(final String name) {
			for(final PoiType poiType : values()) {
				if(poiType.name.equals(name)) {
					return poiType;
				}
			}
			return null;
		}

	}

	/**
	 * @return il nome
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            il nuovo nome da impostare
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return la latitudine
	 */
	@JsonIgnore
	public double getLatitude() {
		return location.getLatitude();
	}

	/**
	 * @param latitude
	 *            la nuova latitudine da impostare
	 */
	@JsonIgnore
	public void setLatitude(double latitude) {
		this.location.setLatitude(latitude);
	}

	/**
	 * @return la longitudine
	 */
	@JsonIgnore
	public double getLongitude() {
		return location.getLongitude();
	}

	/**
	 * @param longitude
	 *            la nuova longitudine da impostare
	 */
	@JsonIgnore
	public void setLongitude(double longitude) {
		this.location.setLongitude(longitude);
	}

	/**
	 * @return l'altitudine
	 */
	@JsonIgnore
	public double getAltitude() {
		return location.getAltitude();
	}

	/**
	 * @param altitude
	 *            la nuova altitudine da impostare
	 */
	@JsonIgnore
	public void setAltitude(double altitude) {
		this.location.setAltitude(altitude);
	}

}
