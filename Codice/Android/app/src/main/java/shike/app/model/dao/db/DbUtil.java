package shike.app.model.dao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe che si occupa di inizializzare, popolare, aggiornare e configurare il database interno
 * dell'applicazione, il cui schema e' definito in DbContract.
 * Implementa il pattern Singleton
 */
public final class DbUtil extends SQLiteOpenHelper {

	private static DbUtil instance = null;
	private final Context context;

	/**
	 * Costruttore privato (per evitare l'istanziamento multiplo)
	 *
	 * @param context contesto dell'applicazione, necessario per il costruttore della superclasse
	 *                e per gli altri metodi della classe.
	 */
	private DbUtil(Context context) {
		super(context, DbContract.DATABASE_NAME, null, DbContract.DATABASE_VERSION);
		this.context = context;
	}

	/**
	 * Metodo del pattern Singleton che permette di avere una sola istanza della classe
	 *
	 * @param context contesto dell'applicazione, necessario per il costruttore di DbUtil
	 * @return un riferimento all'unica istanza di DbUtil
	 */
	public static DbUtil getInstance(Context context) {
		if (instance == null) {
			synchronized (DbUtil.class) {
				if (instance == null) {
					instance = new DbUtil(context);
				}
			}
		}
		return instance;
	}

	/**
	 * Metodo eseguito automaticamente durante la configurazione della connessione con il database.
	 * Si limita ad attivare il vincolo di chiave esterna, in quanto di default e' disattivato.
	 *
	 * @param db il database da configurare
	 */
	@Override
	public void onConfigure(SQLiteDatabase db) {
		db.setForeignKeyConstraintsEnabled(true);
		super.onConfigure(db);
	}

	/**
	 * Metodo eseguito automaticamente alla creazione di un nuovo database.
	 * Si occupa di inizializzarlo e di inserire i valori di default.
	 *
	 * @param db il database appena creato
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		/*
		 * Vengono create le tabelle del database a partire dalle query contenute in
		 * DbCreator.CREATE_STATEMENTS
		 */
		for (String query : DbContract.CREATE_STATEMENTS) {
			db.execSQL(query);
		}
		// Vengono inseriti i valori contenuti negli asset indicati da DbCreator.SQL_ASSETS
		List<String> inserts = null;
		try {
			inserts = getAssetsStatements();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (inserts != null && inserts.size() > 0) {
			db.beginTransaction();
			for (String insert : inserts) {
				db.execSQL(insert);
			}
			db.setTransactionSuccessful();
			db.endTransaction();
		}
	}

	/**
	 * Metodo eseguito automaticamente all'aggiornamento (cambio di numero di versione) di un
	 * database
	 *
	 * @param db         il database aggiornato
	 * @param oldVersion la vecchia versione
	 * @param newVersion la nuova versione
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Vengono eliminate le tabelle del database e successivamente ricreate
		for (String query : DbContract.DELETE_STATEMENTS) {
			db.execSQL(query);
		}
		onCreate(db);
	}

	/**
	 * Metodo che prende i file .sql indicati in {@link DbContract#SQL_ASSETS} e ne estrae le query
	 *
	 * @return una lista contenente tutte le query contenute negli asset indicati
	 * @throws IOException per via della gestione di file di input
	 */
	private List<String> getAssetsStatements() throws IOException {
		List<String> statements = new ArrayList<>();

		for (String asset : DbContract.SQL_ASSETS) {
			InputStream is = context.getAssets().open(asset);
			Scanner scanner = new Scanner(is).useDelimiter(";");
			while (scanner.hasNext()) {
				String query = scanner.next().trim();
				if (query.length() > 0) {
					statements.add(query);
				}
			}
		}

		return statements;
	}
}
