package com.wearit.shike.web.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.wearit.shike.web.model.dao.weather.WeatherDao;
import com.wearit.shike.web.model.dao.weather.WeatherDaoImpl;
import com.wearit.shike.web.model.weather.TrackWeather;
import com.wearit.shike.web.model.weather.Weather;

public class WeatherService {

	private WeatherDao wDao;

	public WeatherService() {
		setWeatherDao();
	}

	@Autowired
	public void setWeatherDao() {
		ApplicationContext context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		wDao = (WeatherDaoImpl) context.getBean("WeatherDaoImpl");
		((ClassPathXmlApplicationContext) context).close();
	}

	public List<Weather> getAllWeather() {
		return wDao.getAllWeather();
	}

	public TrackWeather getTrackWeather(int _idt) {
		List<Weather> w = wDao.getTrackWeather(_idt);
		return new TrackWeather(w, _idt);
	}

}
