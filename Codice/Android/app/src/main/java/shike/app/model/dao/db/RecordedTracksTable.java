package shike.app.model.dao.db;

import android.provider.BaseColumns;

/**
 * Classe contenente i dati riguardanti la tabella 'performance'
 */
public final class RecordedTracksTable implements BaseColumns {

	public static final String TABLE = "recordedtracks";
	public static final String VIRTUAL_ID = "virtual_id";
	public static final String DATE = "date";
	public static final String DISTANCE = "distance";
	public static final String TIME = "time";
	public static final String MAX_SPEED = "maxSpeed";
	public static final String STEPS = "steps";

	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + "("
		+ _ID + " INTEGER PRIMARY KEY NOT NULL, "
		+ VIRTUAL_ID + " INTEGER, "
		+ DATE + " INTEGER NOT NULL, "
		+ DISTANCE + " REAL NOT NULL, "
		+ TIME + " INTEGER NOT NULL, "
		+ MAX_SPEED + " REAL NOT NULL, "
		+ STEPS + " INTEGER NOT NULL, "
		+ "FOREIGN KEY (" + VIRTUAL_ID + ") REFERENCES "
		+ VirtualTracksTable.TABLE + "(" + VirtualTracksTable._ID + ") "
		+ "ON UPDATE CASCADE ON DELETE SET NULL "
		+ ");";
	public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE;

	private RecordedTracksTable() {}
}
