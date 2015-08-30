package com.wearit.shike.web.model.session.track;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	 * Identificatore univoco del percorso (se è un percorso in fase di creazione vale
	 * ancora null)
	 */
	private int _id;

	/**
	 * Centro della mappa in base al percorso
	 */
	private Location center;

	/**
	 * Lunghezza del percorso
	 */
	private double length;

	/**
	 * Autore del tracciato.
	 */
	private int author_id;

	/**
	 * Dislivello del tracciato
	 */
	private int heightDiff;

	public Track() {
		points = new ArrayList<Location>();
	}

	/**
	 * Costruttore di Track
	 * 
	 * @param points
	 *            lista dei punti ordinata del percorso
	 * @param creationDate
	 *            data di creazione del percorso
	 * @param _id
	 *            id del percorso
	 */
	public Track(List<Location> points, Date creationDate, Integer _id) {
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
	 * @return
	 */
	@JsonIgnore
	public String getCreationDateFormatted() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(creationDate);
	}

	@JsonIgnore
	public String getCreationDateTimeFormatted() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return dateFormat.format(creationDate);
	}

	/**
	 * Ritorna l'identificatore univoco del percorso
	 * 
	 * @return identificatore del percorso
	 */
	public int get_id() {
		return _id;
	}

	/**
	 * @param points
	 *            the points to set
	 */
	public void setPoints(List<Location> points) {
		this.points = points;
	}

	/**
	 * @param point
	 */
	public void addPoint(Location point) {
		points.add(point);
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @param _id
	 *            the _id to set
	 */
	public void set_id(int _id) {
		this._id = _id;
	}

	/**
	 * @return center
	 */
	public Location getCenter() {
		return center;
	}

	/**
	 * @param center
	 *            il nuovo center da impostare
	 */
	public void setCenter(Location center) {
		this.center = center;
	}

	/**
	 * @return length
	 */
	public double getLength() {
		return length;
	}

	/**
	 * @param length
	 *            il nuovo length da impostare
	 */
	public void setLength(double length) {
		this.length = length;
	}

	/**
	 * @return author_id
	 */
	public int getAuthor_id() {
		return author_id;
	}

	/**
	 * @param author_id
	 *            il nuovo author_id da impostare
	 */
	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}

	/**
	 * Funzione che permette il calcolo della lunghezza del percorso.
	 * 
	 * @return lunghezza del percorso
	 */
	public double calculateLength() {
		double dst = 0.0;
		Location prvl = null;
		for(Location p : this.getPoints()) {
			if(prvl != null) {
				dst += prvl.getDistance(p);
			}
			prvl = p;
		}

		return dst;
	}

	/**
	 * @return heightDiff
	 */
	public int getHeightDiff() {
		return heightDiff;
	}

	/**
	 * @param heightDiff
	 *            il nuovo heightDiff da impostare
	 */
	public void setHeightDiff(int heightDiff) {
		this.heightDiff = heightDiff;
	}

	/**
	 * Calcola il dislivello del percorso.
	 * 
	 * @return dislivello del percorso
	 */
	public int calculateHeightDiff() {
		int min = (int) Float.MAX_VALUE, max = (int) Float.MIN_VALUE;
		for(Location p : this.getPoints()) {
			if(p.getAltitude() > max) {
				max = (int) p.getAltitude();
			}
			if(p.getAltitude() < min) {
				min = (int) p.getAltitude();
			}
		}
		return (max - min);
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof Track)) {
			return false;
		}

		Track track = (Track) o;

		if(points.size() != track.points.size()) {
			return false;
		}
		for(int i = 0; i < points.size(); i++) {
			Location thisPoint = points.get(i);
			Location thatPoint = track.points.get(i);

			if(thisPoint.getLatitude() != thatPoint.getLatitude()) {
				return false;
			}
			if(thisPoint.getLongitude() != thatPoint.getLongitude()) {
				return false;
			}
			if(thisPoint.getAltitude() != thatPoint.getAltitude()) {
				return false;
			}
		}
		if(!creationDate.equals(track.creationDate)) {
			return false;
		}

		if(_id != track._id) {
			return false;
		}

		return true;

	}

}
