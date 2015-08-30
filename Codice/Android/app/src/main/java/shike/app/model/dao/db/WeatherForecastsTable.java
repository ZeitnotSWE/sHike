package shike.app.model.dao.db;

/**
 * Classe contenente i dati riguardanti la tabella 'weatherforecasts'
 */
public final class WeatherForecastsTable {

	public static final String TABLE = "weatherforecasts";
	public static final String VIRTUAL_TRACK_ID = "virtualTrack_id";
	public static final String TEMPERATURE = "temperature";
	public static final String PRESSURE = "pressure";
	public static final String DATE = "date";
	public static final String FORECAST_ID = "forecast_id";
	public static final String HUMIDITY = "humidity";
	public static final String WIND_DIRECTION = "windDirection";
	public static final String WIND_AVG_SPEED = "windAvgSpeed";
	public static final String WIND_MAX_SPEED = "windMaxSpeed";

	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + "("
		+ VIRTUAL_TRACK_ID + " INTEGER NOT NULL, "
		+ TEMPERATURE + " REAL NOT NULL, "
		+ PRESSURE + " REAL NOT NULL, "
		+ DATE + " INTEGER NOT NULL, "
		+ FORECAST_ID + " INTEGER NOT NULL, "
		+ HUMIDITY + " REAL NOT NULL, "
		+ WIND_DIRECTION + " INTEGER NOT NULL, "
		+ WIND_AVG_SPEED + " REAL NOT NULL, "
		+ WIND_MAX_SPEED + " REAL NOT NULL, "
		+ "PRIMARY KEY (" + VIRTUAL_TRACK_ID + ", " + DATE + "), "
		+ "FOREIGN KEY (" + VIRTUAL_TRACK_ID + ") REFERENCES "
		+ VirtualTracksTable.TABLE + "(" + VirtualTracksTable._ID + ") "
		+ "ON UPDATE CASCADE ON DELETE CASCADE, "
		+ "FOREIGN KEY (" + FORECAST_ID + ") REFERENCES "
		+ ForecastTypesTable.TABLE + "(" + ForecastTypesTable._ID + ") "
		+ "ON UPDATE CASCADE ON DELETE RESTRICT "
		+ ");";
	public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE;

	private WeatherForecastsTable() {}
}
