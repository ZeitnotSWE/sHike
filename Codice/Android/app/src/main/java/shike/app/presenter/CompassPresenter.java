package shike.app.presenter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import shike.app.R;
import shike.app.model.dao.track.VirtualTrackDaoImpl;
import shike.app.model.service.PerformanceService;
import shike.app.model.session.performance.Performance;
import shike.app.model.session.track.RecordedTrack;

/**
 * Created by Luca on 23/05/2015.
 */
public class CompassPresenter implements SensorEventListener, LocationListener {
	private NavigationActivity activity;

	private double totalDistance = 0, remainingTrackDistance = 0;

	private int stepCount = 0, startStepCount = 0;

	private float currentDegreeCompass = 0f;
	private float currentDegreePoint = 0f;

	private ImageView compassImage;
	private ImageView arrowImage;

	private boolean navigationEnabled;
	private boolean saveEnabled;
	private boolean hasStarted = false;

	private List<Location> gpsPointsTrack;
	private List<Location> locationsToSave;

	private Location currentPosition;
	private Location targetLocation;

	public CompassPresenter(NavigationActivity act, int trackId) {
		activity = act;
		locationsToSave = new ArrayList<>();

		LocationManager locationManager =
			(LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		SensorManager mSensorManager =
			(SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
		Sensor mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

		mSensorManager
			.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_GAME);
		mSensorManager
			.registerListener(this, mStepCounterSensor, SensorManager.SENSOR_DELAY_FASTEST);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, this);

		if (trackId != 0) {
			navigationEnabled = true;
			VirtualTrackDaoImpl virtualTrackDao = new VirtualTrackDaoImpl(activity);
			gpsPointsTrack = virtualTrackDao.get(trackId).getPoints();
			targetLocation = gpsPointsTrack.remove(0);
			remainingTrackDistance = calcDistancePointRemains();
		} else {
			navigationEnabled = false;
		}

		currentPosition = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		locationsToSave.add(currentPosition);

	}

	@Override
	public void onLocationChanged(Location newPosition) {
		if (saveEnabled) {
			totalDistance += currentPosition.distanceTo(newPosition);
			locationsToSave.add(newPosition);
		}

		if (navigationEnabled) {
			if (newPosition.distanceTo(targetLocation) <= 10) {
				if (gpsPointsTrack.size() >= 2) {
					remainingTrackDistance -= gpsPointsTrack.get(0).distanceTo(gpsPointsTrack.get(1));
					targetLocation = gpsPointsTrack.remove(0);
				}
				else {
					this.finishTrack();
				}
			}
		}
		activity.getPoiPresenter().refresh();
		currentPosition = newPosition;
	}

	public void finishTrack() {
		if (locationsToSave.size() > 1) {
			RecordedTrack recordedTrack = new RecordedTrack(null, null, new Date(), locationsToSave);
			Performance performance = new Performance(recordedTrack);
			performance.setDate(new Date());
			performance.setDistance(totalDistance);
			performance.setMaxSpeed(getMaxSpeed() * 3.6); //conversione m/s -> km/h
			performance.setSteps(stepCount);
			performance.setTotalTime(activity.getDashboardPresenter().getTimer());
			PerformanceService service = new PerformanceService(activity);
			service.add(performance);
			activity.finish();
		} else {
			Toast.makeText(activity, activity.getText(R.string.errorCreationSaveTrack), Toast
				.LENGTH_LONG)
				.show();
		}
	}

	private double calcDistancePointRemains() {
		double result = 0;
		for (int i = 1; gpsPointsTrack.size() >= 2 && i < gpsPointsTrack.size(); i++) {
			result += gpsPointsTrack.get(i - 1).distanceTo(gpsPointsTrack.get(i));
		}
		return result;
	}

	@Override
	public void onProviderEnabled(String provider) {

		/******** Called when User on Gps  *********/

		Toast.makeText(activity, activity.getText(R.string.gpsStatusOn), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onProviderDisabled(String provider) {

		/******** Called when User off Gps *********/

		Toast.makeText(activity, activity.getText(R.string.gpsStatusOff), Toast.LENGTH_LONG).show();
	}

	public String returnStringNormalized(double value) {
		value = Math.floor(value * 100);
		value = value / 100;
		return String.valueOf(value);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		Sensor sensor = event.sensor;
		float[] values = event.values;
		int value = -1;

		if (values.length > 0) {
			value = (int) values[0];
		}

		if (!hasStarted) {
			startStepCount = value;
			hasStarted = true;
		}

		if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
			stepCount = value - startStepCount;
		}

		if (sensor.getType() == Sensor.TYPE_ORIENTATION) {
			float degree = Math.round(event.values[0]);
			RotateAnimation rotateCompass =
				new RotateAnimation(currentDegreeCompass, -degree, Animation
					.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			rotateCompass.setDuration(210);
			rotateCompass.setFillAfter(true);
			compassImage.startAnimation(rotateCompass);
			currentDegreeCompass = -degree;

			if (navigationEnabled) {
				float degreePoint = degree - currentPosition.bearingTo(targetLocation);;
				RotateAnimation rotateArrow =
					new RotateAnimation(currentDegreePoint, -degreePoint,
						Animation.RELATIVE_TO_SELF,
						0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

				rotateArrow.setDuration(210);
				rotateArrow.setFillAfter(true);
				arrowImage.startAnimation(rotateArrow);
				currentDegreePoint = -degreePoint;
			}
		}
	}



	public void setArrowImage(ImageView arrowImage) {
		this.arrowImage = arrowImage;
		if (navigationEnabled) {
			arrowImage.setVisibility(View.VISIBLE);
		}
	}

	public void setCompassImage(ImageView compassImage) {
		this.compassImage = compassImage;
	}

	public void setSaveEnabled(boolean saveEnabled) {
		this.saveEnabled = saveEnabled;
	}

	public void setTargetLocation(Location targetLocation) {
		gpsPointsTrack.add(0, this.targetLocation);
		this.targetLocation = targetLocation;
		if (!navigationEnabled) {
			navigationEnabled = true;
			arrowImage.setVisibility(View.VISIBLE);
		}
	}

	public double getAltitude() {
		if(currentPosition != null) return currentPosition.getAltitude();
		else return 0;
	}

	public double getDistancePoint() {
		if(currentPosition != null) return currentPosition.distanceTo(targetLocation);
		else return 0;
	}

	public double getActualSpeed() {
		if(currentPosition != null) return currentPosition.getSpeed();
		else return 0;
	}

	public double getRemainingTrackDistance() {
		return remainingTrackDistance;
	}

	public double getRemainingTotalDistance() {
		if(currentPosition != null) return currentPosition.distanceTo(targetLocation) +
			remainingTrackDistance;
		else return 0;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public Location getCurrentPosition() {
		return currentPosition;
	}

	public int getStepCount() {
		return stepCount;
	}

	public double getRemainingTime() {
		return (getRemainingTotalDistance()) / (getAvgSpeed());
	} // secondi mancanti

	public double getAvgSpeed() {
		return totalDistance / (activity.getDashboardPresenter().getTimer() / 1000);//timer in
		// millisecondi -> secondi
	}

	public double getMaxSpeed() {
		double maxSpeed = 0;
		for(int i=0; i<locationsToSave.size();i++){
			double speed = locationsToSave.get(0).getSpeed();
			if(speed > maxSpeed) maxSpeed = speed;
		}
		return maxSpeed;
	}

	public boolean isNavigationEnabled() {
		return navigationEnabled;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}
}
