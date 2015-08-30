package shike.app.model.dao.db;

/**
 * Classe contenente i dati riguardanti la tabella 'virtualtracklocations'
 */
public final class VirtualTrackLocationsTable {

	public static final String TABLE = "virtualtracklocations";
	public static final String VIRTUAL_TRACK_ID = "virtualTrack_id";
	public static final String LOCATION_ORDER = "locationOrder";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String ALTITUDE = "altitude";

	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + "("
		+ VIRTUAL_TRACK_ID + " INTEGER NOT NULL, "
		+ LOCATION_ORDER + " INTEGER NOT NULL, "
		+ LATITUDE + " REAL NOT NULL, "
		+ LONGITUDE + " REAL NOT NULL, "
		+ ALTITUDE + " REAL NOT NULL, "
		+ "PRIMARY KEY (" + VIRTUAL_TRACK_ID + ", " + LOCATION_ORDER + "), "
		+ "FOREIGN KEY (" + VIRTUAL_TRACK_ID + ") REFERENCES "
		+ VirtualTracksTable.TABLE + "(" + VirtualTracksTable._ID + ") "
		+ "ON UPDATE CASCADE ON DELETE CASCADE "
		+ ");";
	public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE;

	private VirtualTrackLocationsTable() {}
}
