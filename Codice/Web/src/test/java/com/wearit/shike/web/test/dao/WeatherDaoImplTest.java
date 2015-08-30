package com.wearit.shike.web.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import com.wearit.shike.web.model.dao.weather.WeatherDao;
import com.wearit.shike.web.model.dao.weather.WeatherDaoImpl;
import com.wearit.shike.web.model.weather.TrackWeather;
import com.wearit.shike.web.model.weather.Weather;
import com.wearit.shike.web.model.weather.Weather.Wind;

public class WeatherDaoImplTest {
	private static TrackWeather newTrackW;
	private static List<Weather> forecasts;
	private static Weather w1;
	private static ApplicationContext context;
	private static WeatherDao daoWeather;
	private static int trackId;
	private static long dateTime;

	@BeforeClass
	public static void setUpClass() throws Exception {
		trackId = 3;
		context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		daoWeather = (WeatherDaoImpl) context.getBean("WeatherDaoImpl");

		w1 = new Weather();
		w1.setTemperature(12);
		w1.setPressure(12);
		w1.setHumidity(20);
		w1.setForecastById(800);
		dateTime = 1233425646;
		Date date = new Date(dateTime);
		w1.setDate(date);
		Wind wind = new Wind(1, 10, 10);
		w1.setWind(wind);

		forecasts = new ArrayList<Weather>();
		forecasts.add(w1);

		newTrackW = new TrackWeather(forecasts, trackId);

	}

	@After
	public void after() {
		try {
			daoWeather.deleteWeather(trackId, dateTime);
		} catch(EmptyResultDataAccessException e) {
			// il weather non è presente se sono qui
		}
	}

	@Test
	public void testAddWeather() throws Exception {
		Weather w = null;
		try {
			daoWeather.addTrackWeather(newTrackW);
			w = daoWeather.getSingleTrackWeather(trackId, dateTime);
		} catch(EmptyResultDataAccessException e) {
			// Il TrackWeather non è stato inserito
			fail("Test fallito - TrackWeather non inserito");
		}

		assertNotNull(w);
		assertEquals(newTrackW.getForecasts().get(0), w1);
	}

	@Test
	public void testDeleteWeather() throws Exception {
		Weather w = null;
		daoWeather.addTrackWeather(newTrackW);

		try {
			daoWeather.deleteWeather(trackId, dateTime);
			w = daoWeather.getSingleTrackWeather(trackId, dateTime);
		} catch(EmptyResultDataAccessException e) {
		}

		assertNull(w);
	}

	@Test
	public void testListWeather() throws Exception {
		try {
			int n = 108;

			List<Weather> l1 = daoWeather.getAllWeather();
			assertEquals("l1.size() = n", n, l1.size());

			daoWeather.addTrackWeather(newTrackW);
			l1 = daoWeather.getAllWeather();
			assertEquals("l1.size() = n+1", n + 1, l1.size());

			daoWeather.deleteWeather(trackId, dateTime);
			l1 = daoWeather.getAllWeather();
			assertEquals("l1.size() = n", n, l1.size());

		} catch(EmptyResultDataAccessException e) {
			// Il TrackWeather non è stato inserito
			fail("Test fallito - TrackWeather non inserito");
		}

	}
}
