package com.wearit.shike.web.model.dao.weather;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;
import com.wearit.shike.web.model.weather.Weather;

public class WeatherMapper implements RowMapper<Weather> {

	@Override
	public Weather mapRow(ResultSet rs, int rowNum) throws SQLException {
		Weather weather = new Weather();
		weather.setTemperature(rs.getDouble("temperature"));
		weather.setPressure(rs.getDouble("pressure"));
		weather.setHumidity(rs.getDouble("humidity"));
		weather.setDate(new Date(rs.getLong("date") * 1000));
		weather.setForecastById(rs.getInt("forecast_id"));
		int direction = rs.getInt("windDirection");
		double avgSpeed = rs.getDouble("windAvgSpeed");
		double maxSpeed = rs.getDouble("windMaxSpeed");

		Weather.Wind wind = new Weather.Wind(direction, avgSpeed, maxSpeed);
		weather.setWind(wind);
		return weather;
	}
}
