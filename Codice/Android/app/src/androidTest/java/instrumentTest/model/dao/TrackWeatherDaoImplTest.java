package instrumentTest.model.dao;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shike.app.model.dao.track.VirtualTrackDaoImpl;
import shike.app.model.dao.weather.TrackWeatherDao;
import shike.app.model.dao.weather.TrackWeatherDaoImpl;
import shike.app.model.session.track.VirtualTrack;
import shike.app.model.weather.TrackWeather;
import shike.app.model.weather.Weather;

/**
 * Test per la classe TrackWeatherDaoImpl
 */
public class TrackWeatherDaoImplTest extends DaoBaseTest {
	private TrackWeatherDao trackWeatherDao;
	private VirtualTrackDaoImpl trackDao;
	private Weather.ForecastType[] forecastTypes = Weather.ForecastType.values();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		trackWeatherDao = new TrackWeatherDaoImpl(context);
		trackDao = new VirtualTrackDaoImpl(context);
	}

	public void testMultipleItems() {
		Map<VirtualTrack, TrackWeather> trackWeatherMap = new HashMap<>();
		int trackWeatherCount = randomGenerator.nextInt(100);
		int totalForecasts = 0;

		for (int i = 0; i < trackWeatherCount; i++) {
			Map.Entry<VirtualTrack, TrackWeather> entry = randomTrackWeather();

			trackWeatherMap.put(entry.getKey(), entry.getValue());
			totalForecasts += entry.getValue().getForecasts().size();
		}

		trackDao.add(new ArrayList<>(trackWeatherMap.keySet()), true);

		Collection<TrackWeather> trackWeatherCol = trackWeatherMap.values();

		assertEquals(totalForecasts, trackWeatherDao.add(new ArrayList<>(trackWeatherCol), true));

		Collection<TrackWeather> dbTrackWeather = trackWeatherDao.getAll().values();

		assertTrue(trackWeatherCol.containsAll(dbTrackWeather) &&
			dbTrackWeather.containsAll(trackWeatherCol));

		assertEquals(totalForecasts, trackWeatherDao.removeAll());

		trackDao.removeAll();

	}

	private Map.Entry<VirtualTrack, TrackWeather> randomTrackWeather() {
		VirtualTrack rndTrack = new VirtualTrackDaoImplTest().randomTrack(false);

		List<Weather> forecasts = new ArrayList<>();
		long prevDate = 0;

		int forecastCount = randomGenerator.nextInt(10) + 1;
		for (int i = 0; i < forecastCount; i++) {
			if (prevDate == 0) {
				prevDate = Math.abs(randomGenerator.nextLong());
			}
			Date date = new Date(prevDate++);
			double temperature = randomGenerator.nextDouble() * 40;
			double pressure = randomGenerator.nextDouble() * 200 + 800;
			double humidity = randomGenerator.nextDouble() * 100;

			Weather.Wind wind
				= new Weather.Wind(randomGenerator.nextInt(360),
				randomGenerator.nextDouble() * 200, randomGenerator.nextDouble() * 200);

			Weather.ForecastType forecastType
				= forecastTypes[randomGenerator.nextInt(forecastTypes.length)];

			forecasts.add(new Weather(date, temperature, pressure, humidity, wind,
				forecastType));
		}

		return new AbstractMap.SimpleEntry<>(rndTrack,
			new TrackWeather(forecasts, rndTrack.get_id()));

	}

	public void testSingleItem() {
		Map.Entry<VirtualTrack, TrackWeather> entry = randomTrackWeather();
		VirtualTrack track = entry.getKey();
		TrackWeather trackWeather = entry.getValue();
		int totalForecasts = trackWeather.getForecasts().size();

		List<VirtualTrack> tracks = new ArrayList<>(1);
		tracks.add(track);

		trackDao.add(tracks, true);

		ArrayList<TrackWeather> forecast = new ArrayList<>(1);
		forecast.add(trackWeather);

		assertEquals(totalForecasts, trackWeatherDao.add(forecast, true));

		TrackWeather dbTrackWeather = trackWeatherDao.get(track.get_id());

		assertEquals(totalForecasts, dbTrackWeather.getForecasts().size());

		assertEquals(trackWeather, dbTrackWeather);

		assertEquals(totalForecasts, trackWeatherDao.removeAll());

		trackDao.removeAll();
	}
}
