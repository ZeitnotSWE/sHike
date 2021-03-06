package shike.app.model.dao.db;

import android.provider.BaseColumns;

/**
 * Classe contenente i dati riguardanti la tabella 'poitypes'
 */
public final class PoiTypesTable implements BaseColumns {

	public static final String TABLE = "poitypes";
	public static final String NAME = "name";

	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + "("
		+ _ID + " INTEGER PRIMARY KEY NOT NULL, "
		+ NAME + " TEXT NOT NULL "
		+ ");";
	public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE;

	private PoiTypesTable() {}
}
