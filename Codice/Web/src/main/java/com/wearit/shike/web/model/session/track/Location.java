package com.wearit.shike.web.model.session.track;

public class Location {
	private int trackId, accountId, syncNumber, locationOrder;
	private double altitude, longitude, latitude;
	private float speed;
	private long time;

	public Location() {
		this.altitude = 0;
		this.longitude = 0;
		this.latitude = 0;
		this.speed = 0;
		this.time = 0;
	}

	/**
	 * @param longitude
	 * @param latitude
	 */
	public Location(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * @param altitude
	 * @param longitude
	 * @param latitude
	 */
	public Location(double altitude, double longitude, double latitude) {
		this.altitude = altitude;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * @return the accountId
	 */
	public int getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the syncNumber
	 */
	public int getSyncNumber() {
		return syncNumber;
	}

	/**
	 * @param syncNumber
	 *            the syncNumber to set
	 */
	public void setSyncNumber(int syncNumber) {
		this.syncNumber = syncNumber;
	}

	/**
	 * @return the locationOrder
	 */
	public int getLocationOrder() {
		return locationOrder;
	}

	/**
	 * @param locationOrder
	 *            the locationOrder to set
	 */
	public void setLocationOrder(int locationOrder) {
		this.locationOrder = locationOrder;
	}

	/**
	 * @return the altitude
	 */
	public double getAltitude() {
		return altitude;
	}

	/**
	 * @param altitude
	 *            the altitude to set
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the trackId
	 */
	public int getTrackId() {
		return trackId;
	}

	/**
	 * @param trackId
	 *            the trackId to set
	 */
	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}

	public double getDistance(Location l) {
		// double R = 6371000; // metres
		return distance(this.latitude, this.longitude, l.latitude, l.longitude);
	}

	private final double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private final double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	private double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
				* Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		return dist;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof Location)) {
			return false;
		}
		Location other = (Location) obj;
		if(Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude)) {
			return false;
		}
		if(Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude)) {
			return false;
		}
		return true;
	}

}
