package com.wearit.shike.web.model.session.performance;

import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.wearit.shike.web.model.session.track.RecordedTrack;

public class Performance {
	private int track_id, account_id, syncNumber;
	private double maxSpeed, distance;
	private int steps, heightDiff, counter;
	private long time;
	private RecordedTrack recordedTrack;

	/**
	 * @return _id
	 */
	public int getTrack_id() {
		return track_id;
	}

	/**
	 * @param track_id
	 *            il nuovo track_id da impostare
	 */
	public void setTrack_id(int track_id) {
		this.track_id = track_id;
	}

	/**
	 * @return account_id
	 */
	public int getAccount_id() {
		return account_id;
	}

	/**
	 * @param account_id
	 *            il nuovo account_id da impostare
	 */
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	/**
	 * @return syncNumber
	 */
	public int getSyncNumber() {
		return syncNumber;
	}

	/**
	 * @param syncNumber
	 *            il nuovo syncNumber da impostare
	 */
	public void setSyncNumber(int syncNumber) {
		this.syncNumber = syncNumber;
	}

	/**
	 * @return maxSpeed
	 */
	public double getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * @param maxSpeed
	 *            il nuovo maxSpeed da impostare
	 */
	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
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
	 * @return distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @return totalDistance
	 */
	public double getDistanceKm() {
		return distance / 1000;
	}

	/**
	 * @param distance
	 *            il nuovo distance da impostare
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return steps
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * @param steps
	 *            il nuovo steps da impostare
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}

	/**
	 * @return time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time
	 *            il nuovo time da impostare
	 */
	@JsonSetter("totalTime")
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return counter
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * @param counter
	 *            il nuovo counter da impostare
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}

	/**
	 * @return la durata formattata come HH:MM:SS
	 */
	public String getTimeFormatted() {
		return String.format(
				"%02d:%02d:%02d",
				TimeUnit.MILLISECONDS.toHours(time),
				TimeUnit.MILLISECONDS.toMinutes(time)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
				TimeUnit.MILLISECONDS.toSeconds(time)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
	}

	/**
	 * @return rhythm
	 */
	public String getRhythm() {
		int total = (int) (time / (distance / 1000));
		return String.format(
				"%02d:%02d",
				TimeUnit.MILLISECONDS.toMinutes(total)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(total)),
				TimeUnit.MILLISECONDS.toSeconds(total)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(total)));
	}

	/**
	 * @return AvgSpeed
	 */
	public double getAvgSpeed() {
		return (distance / 1000) / ((double) time / 3600000);
	}

	public RecordedTrack getRecordedTrack() {
		return recordedTrack;
	}

	public void setRecordedTrack(RecordedTrack r) {
		recordedTrack = r;
	}

	/**
	 * Metodo equals controlla solo i campi che devono combaciare in due Performance per
	 * essere uguali
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof Performance)) {
			return false;
		}
		Performance other = (Performance) obj;

		if(Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance)) {
			return false;
		}
		if(heightDiff != other.heightDiff) {
			return false;
		}
		if(Double.doubleToLongBits(maxSpeed) != Double.doubleToLongBits(other.maxSpeed)) {
			return false;
		}
		if(steps != other.steps) {
			return false;
		}
		if(time != other.time) {
			return false;
		}
		return true;
	}

}
