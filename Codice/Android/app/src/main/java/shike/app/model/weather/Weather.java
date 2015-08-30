package shike.app.model.weather;

import java.util.Date;

public class Weather {

	/**
	 * Temperatura prevista (in K)
	 */
	private double temperature;
	/**
	 * Pressione prevista (in hPa)
	 */
	private double pressure;
	/**
	 * Data iniziale di validità per la previsione. La fine di validità della previsione
	 * dipende dalla data della previsione successiva
	 */
	private Date date;
	/**
	 * Umidità in percentuale
	 */
	private double humidity;
	/**
	 * Informazioni sul vento
	 */
	private Wind wind;
	/**
	 * Tipo di condizioni previste
	 */
	private ForecastType forecast;

	public Weather(Date date, double temperature, double pressure, double humidity, Wind wind,
				   ForecastType forecast) {
		this.date = date;
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
		this.wind = wind;
		this.forecast = forecast;
	}

	public double getTemperature() {
		return temperature;
	}

	public double getPressure() {
		return pressure;
	}

	public Date getDate() {
		return date;
	}

	public double getHumidity() {
		return humidity;
	}

	public Wind getWind() {
		return wind;
	}

	public ForecastType getForecast() {
		return forecast;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Weather)) {
			return false;
		}

		Weather weather = (Weather) o;

		if (Double.compare(weather.temperature, temperature) != 0) {
			return false;
		}
		if (Double.compare(weather.pressure, pressure) != 0) {
			return false;
		}
		if (Double.compare(weather.humidity, humidity) != 0) {
			return false;
		}
		if (!date.equals(weather.date)) {
			return false;
		}
		if (!wind.equals(weather.wind)) {
			return false;
		}
		return forecast == weather.forecast;

	}

	/**
	 * Enum che indica il tipo di condizioni meteo previste. I codici indicati nelle condizioni
	 * si riferiscono agli identificatori di
	 * <a href="http://openweathermap.org/weather-conditions">OpenWeatherMap</a>
	 */
	public enum ForecastType {
		THUNDERSTORM("Tempesta", 200, 232),
		RAIN("Pioggia", 300, 531),
		SNOW("Neve", 600, 622),
		FOG("Nebbia", 701, 781),
		CLEAR("Sereno", 800, 800),
		CLOUDS("Nuvoloso", 801, 804),
		// Qualsiasi condizione meteo non specificata dalle precedenti
		OTHER("Altro", 0, 0);

		/**
		 * Nome del tipo
		 */
		private final String name;
		/**
		 * Limite inferiore (incluso) del codice meteo da considerare appartenente al tipo
		 */
		private final int minId;

		/**
		 * Limite superiore (incluso) del codice meteo da considerare appartenente al tipo
		 */
		private final int maxId;

		ForecastType(final String name, final int minId, final int maxId) {
			this.name = name;
			this.minId = minId;
			this.maxId = maxId;
		}

		public static ForecastType getById(final int id) {
			for (final ForecastType ft : values()) {
				if (id >= ft.minId && id <= ft.maxId) {
					return ft;
				}
			}
			return OTHER;
		}

		public static ForecastType getByName(final String name) {
			for (final ForecastType ft : values()) {
				if (ft.name.equals(name)) {
					return ft;
				}
			}
			return OTHER;
		}

		public String getName() {
			return name;
		}

		public int getMinId() {
			return minId;
		}

		public int getMaxId() {
			return maxId;
		}

	}	@Override
	public int hashCode() {
		int result;
		long temp;
		temp = Double.doubleToLongBits(temperature);
		result = (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(pressure);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + date.hashCode();
		temp = Double.doubleToLongBits(humidity);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + wind.hashCode();
		result = 31 * result + forecast.hashCode();
		return result;
	}

	/**
	 * Classe che modella le informazioni sul vento di una previsione meteo
	 */
	public static class Wind {

		/**
		 * Direzione del vento in gradi
		 */
		private int direction;
		/**
		 * Velocità  media del vento (in m/s)
		 */
		private double avgSpeed;
		/**
		 * Velocità  di picco del vento (in m/s)
		 */
		private double maxSpeed;

		public Wind(int direction, double avgSpeed, double maxSpeed) {
			this.direction = direction;
			this.avgSpeed = avgSpeed;
			this.maxSpeed = maxSpeed;
		}

		public int getDirection() {
			return direction;
		}

		public double getAvgSpeed() {
			return avgSpeed;
		}

		public double getMaxSpeed() {
			return maxSpeed;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (!(o instanceof Wind)) {
				return false;
			}

			Wind wind = (Wind) o;

			if (direction != wind.direction) {
				return false;
			}
			if (Double.compare(wind.avgSpeed, avgSpeed) != 0) {
				return false;
			}
			return Double.compare(wind.maxSpeed, maxSpeed) == 0;

		}

		@Override
		public int hashCode() {
			int result;
			long temp;
			result = direction;
			temp = Double.doubleToLongBits(avgSpeed);
			result = 31 * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(maxSpeed);
			result = 31 * result + (int) (temp ^ (temp >>> 32));
			return result;
		}
	}


}
