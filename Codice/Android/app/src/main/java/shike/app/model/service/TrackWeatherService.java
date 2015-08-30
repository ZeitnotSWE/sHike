package shike.app.model.service;

import android.content.Context;

import java.util.List;
import java.util.Map;

import shike.app.model.dao.weather.TrackWeatherDao;
import shike.app.model.dao.weather.TrackWeatherDaoImpl;
import shike.app.model.session.track.VirtualTrack;
import shike.app.model.weather.TrackWeather;

/**
 * Created by Admin on 09/06/2015.
 */
public class TrackWeatherService {
	private final TrackWeatherDao trackWeatherDao;

	public TrackWeatherService(Context context) {
		trackWeatherDao = new TrackWeatherDaoImpl(context);
	}

	public Map<Integer, TrackWeather> getAll() {
		return trackWeatherDao.getAll();
	}

	public TrackWeather get(int virtualTrackId) {
		return trackWeatherDao.get(virtualTrackId);
	}

	public TrackWeather get(final VirtualTrack virtualTrack) {
		return trackWeatherDao.get(virtualTrack.get_id());
	}

	public int removeAll() {
		return trackWeatherDao.removeAll();
	}

	public int add(List<TrackWeather> forecasts) {
		return trackWeatherDao.add(forecasts, true);
	}
}
