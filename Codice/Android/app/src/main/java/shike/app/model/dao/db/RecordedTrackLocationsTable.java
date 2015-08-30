package shike.app.model.dao.db;

/**
 * Classe contenente i dati riguardanti la tabella 'recordedtracklocations'
 */
public final class RecordedTrackLocationsTable {

	public static final String TABLE = "recordedtracklocations";
	public static final String RECORDED_TRACK_ID = "recordedTrack_id";
	public static final String LOCATION_ORDER = "locationOrder";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String ALTITUDE = "altitude";

	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + "("
		+ RECORDED_TRACK_ID + " INTEGER NOT NULL, "
		+ LOCATION_ORDER + " INTEGER NOT NULL, "
		+ LATITUDE + " REAL NOT NULL, "
		+ LONGITUDE + " REAL NOT NULL, "
		+ ALTITUDE + " REAL NOT NULL, "
		+ "PRIMARY KEY (" + RECORDED_TRACK_ID + ", " + LOCATION_ORDER + "), "
		+ "FOREIGN KEY (" + RECORDED_TRACK_ID + ") REFERENCES "
		+ RecordedTracksTable.TABLE + "(" + RecordedTracksTable._ID + ") "
		+ " ON UPDATE CASCADE ON DELETE CASCADE "
		+ ");";
	public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE;

	private RecordedTrackLocationsTable() {}
}
