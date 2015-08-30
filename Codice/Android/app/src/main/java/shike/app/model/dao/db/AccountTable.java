package shike.app.model.dao.db;

import android.provider.BaseColumns;

/**
 * Classe contenente i dati riguardanti la tabella 'account'
 */
public final class AccountTable implements BaseColumns {

	public static final String TABLE = "account";
	public static final String CONNECTION_TOKEN = "connectionToken";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String HEIGHT = "height";
	public static final String WEIGHT = "weight";
	public static final String GENDER_ID = "gender_id";
	public static final String BIRTH_DATE = "birthDate";

	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + "("
		+ _ID + " INTEGER PRIMARY KEY NOT NULL, "
		+ CONNECTION_TOKEN + " TEXT NOT NULL, "
		+ FIRST_NAME + " TEXT NOT NULL, "
		+ LAST_NAME + " TEXT NOT NULL, "
		+ HEIGHT + " INTEGER NOT NULL, "
		+ WEIGHT + " INTEGER NOT NULL, "
		+ GENDER_ID + " INTEGER NOT NULL, "
		+ BIRTH_DATE + " INTEGER NOT NULL, "
		+ "FOREIGN KEY (" + GENDER_ID + ") REFERENCES "
		+ GendersTable.TABLE + "(" + GendersTable._ID + ") "
		+ "ON UPDATE CASCADE ON DELETE RESTRICT "
		+ ");";
	public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE;

	private AccountTable() {}
}
