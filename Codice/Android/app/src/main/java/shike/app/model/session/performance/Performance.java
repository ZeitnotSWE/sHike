package shike.app.model.session.performance;

import android.support.annotation.Nullable;

import java.util.Date;

import shike.app.model.session.track.RecordedTrack;

/**
 * Classe che modella una performance dell'utente.
 */
public class Performance {
	/**
	 * Identificatore univoco della performance (null se la performance è nuova e non è stata
	 * ancora salvata nel database
	 */
	private final Integer _id;
	/**
	 * Riferimento al percorso associato alla performance
	 */
	private final RecordedTrack recordedTrack;
	/**
	 * Massima velocità registrata (in m/s)
	 */
	private double maxSpeed;
	/**
	 * Tempo totale trascorso (in ms)
	 */
	private long totalTime;
	/**
	 * Data di inizio performance
	 */
	private Date date;
	/**
	 * Numero di passi rilevati
	 */
	private int steps;
	/**
	 * Distanza percorsa (in metri)
	 */
	private double distance;

	public Performance(RecordedTrack recordedTrack) {
		this(null, recordedTrack);
	}

	public Performance(@Nullable Integer _id, RecordedTrack recordedTrack) {
		this._id = _id;
		this.recordedTrack = recordedTrack;
	}

	public Integer get_id() {
		return _id;
	}

	public RecordedTrack getRecordedTrack() {
		return recordedTrack;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date newDate) {
		date = newDate;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * Metodo equals per le performance, non controlla l'uguaglianza del percorso associato
	 *
	 * @param o oggetto da confrontare con this
	 * @return true se i due oggetti hanno gli stessi attributi e se i metodi astratti ritornano
	 * gli stessi valori
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Performance)) {
			return false;
		}

		Performance that = (Performance) o;

		if (steps != that.steps) {
			return false;
		}
		if (Double.compare(that.distance, distance) != 0) {
			return false;
		}
		if (_id != null ? !_id.equals(that._id) : that._id != null) {
			return false;
		}
		if ((recordedTrack == null || that.recordedTrack == null) &&
			!(recordedTrack == null && that.recordedTrack == null)) {
			return false;
		}
		if (!date.equals(that.date)) {
			return false;
		}
		if (getAvgSpeed() != that.getAvgSpeed()) {
			return false;
		}
		if (getMaxSpeed() != that.getMaxSpeed()) {
			return false;
		}

		return getTotalTime() == that.getTotalTime();
	}

	public double getAvgSpeed() {
		return distance / (getTotalTime() / 1000);
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = _id != null ? _id.hashCode() : 0;
		result = 31 * result + date.hashCode();
		result = 31 * result + steps;
		temp = Double.doubleToLongBits(distance);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}
