package shike.app.model.dao.db;

/**
 * Classe contenente i dati riguardanti la tabella 'helpnumbers'
 */
public final class HelpNumbersTable {

	public static final String TABLE = "helpnumbers";
	public static final String NUMBER = "number";
	public static final String NAME = "name";

	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + "("
		+ NUMBER + " TEXT PRIMARY KEY NOT NULL, "
		+ NAME + " TEXT NOT NULL "
		+ ");";
	public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE;

	private HelpNumbersTable() {}
}
