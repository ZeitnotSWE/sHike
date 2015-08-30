package shike.app.model.dao.db;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Classe che contiene le informazioni generali sullo schema del database.
 * Ogni classe che utilizza rappresenta una tabella.
 * La costante TABLE contiene il nome della tabella, mentre CREATE_TABLE lo statement per crearla.
 * Tutte le altre costanti indicano i nomi degli attributi della tabella interessata.
 * Le classi che implementano BaseColumns hanno una costante _ID che indica l'attributo '_id',
 * contenuto in molte delle tabelle
 */
public final class DbContract {

	public static final int DATABASE_VERSION = 14;

	public static final String DATABASE_NAME = "shike.db";
	/**
	 * Lista non modificabile contente tutti i nomi dei file .sql contenuti negli assets
	 */
	public static final List<String> SQL_ASSETS = Collections.unmodifiableList(Arrays.asList(
		"default.sql",
		"example.sql"
	));
	/**
	 * Lista non modificabile contenente tutti gli statement di creazione delle tabelle del
	 * database
	 */
	public static final List<String> CREATE_STATEMENTS =
		Collections.unmodifiableList(Arrays.asList(
			GendersTable.CREATE_TABLE,
			AccountTable.CREATE_TABLE,
			HelpNumbersTable.CREATE_TABLE,
			VirtualTracksTable.CREATE_TABLE,
			VirtualTrackLocationsTable.CREATE_TABLE,
			RecordedTrackLocationsTable.CREATE_TABLE,
			ForecastTypesTable.CREATE_TABLE,
			WeatherForecastsTable.CREATE_TABLE,
			PoiTypesTable.CREATE_TABLE,
			PoisTable.CREATE_TABLE,
			RecordedTracksTable.CREATE_TABLE
		));
	/**
	 * Lista non modificabile contenente tutti gli statement di cancellazione delle tabelle del
	 * database
	 */
	public static final List<String> DELETE_STATEMENTS =
		Collections.unmodifiableList(Arrays.asList(
			AccountTable.DELETE_TABLE,
			GendersTable.DELETE_TABLE,
			HelpNumbersTable.DELETE_TABLE,
			VirtualTrackLocationsTable.DELETE_TABLE,
			RecordedTrackLocationsTable.DELETE_TABLE,
			VirtualTracksTable.DELETE_TABLE,
			WeatherForecastsTable.DELETE_TABLE,
			ForecastTypesTable.DELETE_TABLE,
			PoisTable.DELETE_TABLE,
			PoiTypesTable.DELETE_TABLE,
			RecordedTracksTable.DELETE_TABLE
		));

	private DbContract() {
	} // Per evitare che venga istanziata

}
