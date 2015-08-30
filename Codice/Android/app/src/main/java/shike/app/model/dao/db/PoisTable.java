package shike.app.model.dao.db;

import android.provider.BaseColumns;

/**
 * Classe contenente i dati riguardanti la tabella 'pois'
 */
public final class PoisTable implements BaseColumns {

	public static final String TABLE = "pois";
	public static final String NAME = "name";
	public static final String TYPE_ID = "type_id";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String ALTITUDE = "altitude";

	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + "("
		+ _ID + " INTEGER PRIMARY KEY NOT NULL, "
		+ NAME + " TEXT NOT NULL, "
		+ TYPE_ID + " INTEGER NOT NULL, "
		+ LATITUDE + " REAL NOT NULL, "
		+ LONGITUDE + " REAL NOT NULL, "
		+ ALTITUDE + " REAL NOT NULL, "
		+ "FOREIGN KEY (" + TYPE_ID + ") REFERENCES "
		+ PoiTypesTable.TABLE + "(" + PoiTypesTable._ID + ") "
		+ "ON UPDATE CASCADE ON DELETE RESTRICT "
		+ ");";
	public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE;

	private PoisTable() {}
}
