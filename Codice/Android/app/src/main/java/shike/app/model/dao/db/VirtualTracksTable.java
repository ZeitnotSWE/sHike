package shike.app.model.dao.db;

import android.provider.BaseColumns;

/**
 * Classe contenente i dati riguardanti la tabella 'virtualtracks'
 */
public final class VirtualTracksTable implements BaseColumns {

	public static final String TABLE = "virtualtracks";
	public static final String NAME = "name";
	public static final String CREATION_DATE = "creationDate";
	public static final String LENGTH = "length";
	public static final String BARYCENTER_LATITUDE = "barycenterLatitude";
	public static final String BARYCENTER_LONGITUDE = "baricenterLongitude";

	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + "("
		+ _ID + " INTEGER PRIMARY KEY NOT NULL, "
		+ NAME + " TEXT NOT NULL, "
		+ CREATION_DATE + " INTEGER NOT NULL, "
		+ LENGTH + " REAL NOT NULL, "
		+ BARYCENTER_LATITUDE + " REAL NOT NULL, "
		+ BARYCENTER_LONGITUDE + " REAL NOT NULL "
		+ ");";
	public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE;

	private VirtualTracksTable() {}
}
