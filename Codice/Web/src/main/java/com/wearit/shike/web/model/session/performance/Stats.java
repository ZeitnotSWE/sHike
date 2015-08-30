package com.wearit.shike.web.model.session.performance;

import java.util.concurrent.TimeUnit;

public class Stats {
	private int totalSteps, numberActivity, totalHeightDiff;
	private long totalTime;
	private double totalDistance, expLevel, maxSpeed;

	/**
	 * @return totalSteps
	 */
	public int getTotalSteps() {
		return totalSteps;
	}

	/**
	 * @param totalSteps
	 *            il nuovo totalSteps da impostare
	 */
	public void setTotalSteps(int totalSteps) {
		this.totalSteps = totalSteps;
	}

	/**
	 * @return totalTime
	 */
	public long getTotalTime() {
		return totalTime;
	}

	/**
	 * @return la durata formattata come HH:MM:SS
	 */
	public String getTotalTimeFormatted() {
		return String.format(
				"%02d:%02d:%02d",
				TimeUnit.MILLISECONDS.toHours(totalTime),
				TimeUnit.MILLISECONDS.toMinutes(totalTime)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalTime)),
				TimeUnit.MILLISECONDS.toSeconds(totalTime)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime)));
	}

	/**
	 * @param totalTime
	 *            il nuovo totalTime da impostare
	 */
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	/**
	 * @return totalDistance
	 */
	public double getTotalDistance() {
		return totalDistance;
	}

	/**
	 * @return totalDistance
	 */
	public double getTotalDistanceKm() {
		return totalDistance / 1000;
	}

	/**
	 * @param totalDistance
	 *            il nuovo totalDistance da impostare
	 */
	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	/**
	 * @return totalHeightDiff
	 */
	public int getTotalHeightDiff() {
		return totalHeightDiff;
	}

	/**
	 * @param totalHeightDiff
	 *            il nuovo totalHeightDiff da impostare
	 */
	public void setTotalHeightDiff(int totalHeightDiff) {
		this.totalHeightDiff = totalHeightDiff;
	}

	/**
	 * @return expLevel
	 */
	public double getExpLevel() {
		return expLevel;
	}

	/**
	 * @param expLevel
	 *            il nuovo expLevel da impostare
	 */
	public void setExpLevel(double expLevel) {
		this.expLevel = expLevel;
	}

	/**
	 * @return numberActivity
	 */
	public int getNumberActivity() {
		return numberActivity;
	}

	/**
	 * @param numberActivity
	 *            il nuovo numberActivity da impostare
	 */
	public void setNumberActivity(int numberActivity) {
		this.numberActivity = numberActivity;
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
	 * @return rhythm
	 */
	public String getRhythm() {
		int total = (int) (totalTime / (totalDistance / 1000));
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
		if(totalTime == 0)
			return 0;
		return (totalDistance / 1000) / ((double) totalTime / 3600000);

	}

	/**
	 * @return AvgDistance
	 */
	public double getAvgDistanceKm() {
		if(numberActivity == 0)
			return 0;
		return (totalDistance / 1000) / ((double) numberActivity);

	}

}