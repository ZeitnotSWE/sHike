package shike.app.model.dao.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shike.app.model.dao.db.DbUtil;
import shike.app.model.dao.db.WeatherForecastsTable;
import shike.app.model.weather.TrackWeather;
import shike.app.model.weather.Weather;

/**
 * Implementazione di {@link TrackWeatherDao}
 */
public class TrackWeatherDaoImpl implements TrackWeatherDao {

	/**
	 * Riferimento al database dell'applicazione
	 */
	private SQLiteDatabase db;

	public TrackWeatherDaoImpl(Context context) {
		db = DbUtil.getInstance(context).getWritableDatabase();
	}

	@Override
	public Map<Integer, TrackWeather> getAll() {
		Map<Integer, TrackWeather> forecastsMap = new HashMap<>(); // Mappa da ritornare
		Map<Integer, List<Weather>> weatherLists = new HashMap<>();


		String orderBy = // Ordina la tabella per _id e per data
			WeatherForecastsTable.VIRTUAL_TRACK_ID + ", "
				+ WeatherForecastsTable.DATE;

		Cursor cur =
			db.query(WeatherForecastsTable.TABLE, null, null, null, null, null,
				orderBy);

		while (cur.moveToNext()) {
			int _id =
				cur.getInt(cur.getColumnIndex(WeatherForecastsTable.VIRTUAL_TRACK_ID));

			List<Weather> forecastList = weatherLists.get(_id);

			if (forecastList == null) {
				forecastList = new ArrayList<>();
				weatherLists.put(_id, forecastList);
			}

			forecastList.add(cursorToWeather(cur));

		}

		for (Map.Entry<Integer, List<Weather>> entry : weatherLists.entrySet()) {
			forecastsMap.put(
				entry.getKey(),
				new TrackWeather(entry.getValue(), entry.getKey())
			);
		}

		cur.close();

		return forecastsMap;
	}

	/**
	 * Metodo di utilità interno che converte una riga della tabella 'weatherforecasts' in un
	 * oggetto di tipo Weather.
	 *
	 * @param cur cursore che indica una riga della tabella 'weatherforecasts'
	 * @return l'oggetto Weather creato dalla riga
	 */
	private Weather cursorToWeather(Cursor cur) {
		Date date = new Date(cur.getLong(
			cur.getColumnIndex(WeatherForecastsTable.DATE)));

		double temperature = cur.getDouble(
			cur.getColumnIndex(WeatherForecastsTable.TEMPERATURE));

		double pressure = cur.getDouble(
			cur.getColumnIndex(WeatherForecastsTable.PRESSURE));

		double humidity = cur.getDouble(
			cur.getColumnIndex(WeatherForecastsTable.HUMIDITY));

		Weather.Wind wind = new Weather.Wind(
			cur.getInt(cur.getColumnIndex(WeatherForecastsTable.WIND_DIRECTION)),
			cur.getDouble(cur.getColumnIndex(WeatherForecastsTable.WIND_AVG_SPEED)),
			cur.getDouble(cur.getColumnIndex(WeatherForecastsTable.WIND_MAX_SPEED))
		);

		Weather.ForecastType type = Weather.ForecastType.getById(cur.getInt(
			cur.getColumnIndex(WeatherForecastsTable.FORECAST_ID)));

		return new Weather(date, temperature, pressure, humidity, wind, type);
	}

	@Override
	public TrackWeather get(int virtualTrackId) {

		List<Weather> weatherList = new ArrayList<>();

		Cursor cur =
			db.query(WeatherForecastsTable.TABLE, null,
				WeatherForecastsTable.VIRTUAL_TRACK_ID + " = " + virtualTrackId,
				null, null, null, WeatherForecastsTable.DATE);

		while (cur.moveToNext()) {
			weatherList.add(cursorToWeather(cur));
		}

		if (weatherList.size() > 0) {
			return new TrackWeather(weatherList, virtualTrackId);
		}

		return null;
	}

	@Override
	public int removeAll() {
		return db.delete(WeatherForecastsTable.TABLE, "1", null);
	}

	@Override
	public int add(List<TrackWeather> trackForecasts, boolean truncateFirst) {

		if (truncateFirst) {
			removeAll();
		}

		int rows = 0;

		for (TrackWeather trackWeather : trackForecasts) {
			db.beginTransaction();
			for (Weather forecast : trackWeather.getForecasts()) {
				ContentValues content = new ContentValues();

				content.put(WeatherForecastsTable.VIRTUAL_TRACK_ID,
					trackWeather.getVirtualTrackId());
				content.put(WeatherForecastsTable.TEMPERATURE, forecast.getTemperature());
				content.put(WeatherForecastsTable.PRESSURE, forecast.getPressure());
				content.put(WeatherForecastsTable.DATE, forecast.getDate().getTime());
				content.put(WeatherForecastsTable.FORECAST_ID,
					forecast.getForecast().getMinId());
				content.put(WeatherForecastsTable.HUMIDITY, forecast.getHumidity());
				content.put(WeatherForecastsTable.WIND_DIRECTION,
					forecast.getWind().getDirection());
				content.put(WeatherForecastsTable.WIND_AVG_SPEED,
					forecast.getWind().getAvgSpeed());
				content.put(WeatherForecastsTable.WIND_MAX_SPEED,
					forecast.getWind().getMaxSpeed());

				if (db.insert(WeatherForecastsTable.TABLE, null, content) > 0) {
					rows++;
				}
			}
			db.setTransactionSuccessful();
			db.endTransaction();
		}

		return rows;
	}
}
