package com.wearit.shike.web.model.dao.weather;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import com.wearit.shike.web.model.weather.TrackWeather;
import com.wearit.shike.web.model.weather.Weather;

public class WeatherDaoImpl implements WeatherDao {

	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Weather> getAllWeather() {
		String sql = "SELECT * FROM weatherforecasts";
		List<Weather> weather = jdbcTemplateObject.query(sql, new WeatherMapper());
		return weather;
	}

	@Override
	public List<Weather> getTrackWeather(int _idt) {
		String sql = "SELECT * FROM weatherforecasts w WHERE virtualTrack_id = ? "
				+ " AND (w.date >= UNIX_TIMESTAMP(DATE(NOW())) "
				+ " AND w.date < UNIX_TIMESTAMP(DATE_ADD( DATE(NOW()), INTERVAL 10 DAY)));";
		List<Weather> ws = jdbcTemplateObject
				.query(sql, new Object[] { _idt }, new WeatherMapper());
		return ws;
	}

	@Override
	public Weather getSingleTrackWeather(int _idt, long date) {
		String sql = "SELECT * FROM weatherforecasts WHERE virtualTrack_id = ? AND date = ?;";
		List<Weather> wsl = jdbcTemplateObject.query(sql, new Object[] { _idt, date },
				new WeatherMapper());
		if(wsl.isEmpty())
			return null;
		Weather ws = wsl.get(0);
		return ws;
	}

	/**
	 * Metodo utilizzato solo per test
	 */
	@Override
	public void addTrackWeather(TrackWeather tw) {
		String sql = "INSERT INTO weatherforecasts(virtualTrack_id, temperature, "
				+ "pressure, date, forecast_id, humidity, windDirection, windAvgSpeed, windMaxSpeed) "
				+ "values (?,?,?,?,?,?,?,?,?)";
		int trackId = tw.getVirtualTrackId();
		List<Weather> wList = tw.getForecasts();
		for(Weather w : wList) {
			jdbcTemplateObject.update(sql, trackId, w.getTemperature(), w.getPressure(), w
					.getDate().getTime(), w.getForecast().getMinId(), w.getHumidity(), w.getWind()
					.getDirection(), w.getWind().getAvgSpeed(), w.getWind().getMaxSpeed());
		}

	}

	@Override
	public void deleteWeather(int id, long date) {
		String sql = "DELETE FROM weatherforecasts WHERE virtualTrack_id = ? AND date = ?";
		jdbcTemplateObject.update(sql, id, date);
	}
}
