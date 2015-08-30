package shike.app.model.dao.poi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import shike.app.model.dao.db.DbUtil;
import shike.app.model.dao.db.PoisTable;
import shike.app.model.session.track.Poi;

/**
 * Implementazione di {@link PoiDao}
 */
public class PoiDaoImpl implements PoiDao {

	/**
	 * Riferimento al database dell'app
	 */
	private SQLiteDatabase db;

	/**
	 * Costruttore necessario per ottenere il riferimento al database
	 *
	 * @param context contesto dell'applicazione
	 */
	public PoiDaoImpl(Context context) {
		db = DbUtil.getInstance(context).getWritableDatabase();
	}

	/**
	 * Ritorna un POI a partire da una riga della tabella 'pois'
	 *
	 * @param cur cursore posizionato su una riga di 'pois'
	 * @return il POI corrispondente alla riga indicata
	 */
	private static Poi cursorToPoi(Cursor cur) {
		// ID del POI
		int id = cur.getInt(cur.getColumnIndex(PoisTable._ID));

		// Posizione del POI
		Location location = new Location(PoisTable.TABLE);
		location.setLatitude(cur.getDouble(cur.getColumnIndex(PoisTable.LATITUDE)));
		location.setLongitude(cur.getDouble(
			cur.getColumnIndex(PoisTable.LONGITUDE)));
		location.setAltitude(cur.getDouble(cur.getColumnIndex(PoisTable.ALTITUDE)));

		// Tipologia POI
		Poi.PoiType type = Poi.PoiType.getById(cur.getInt(
			cur.getColumnIndex(PoisTable.TYPE_ID)));

		// Nome del POI
		String name = cur.getString(cur.getColumnIndex(PoisTable.NAME));

		return new Poi(id, location, name, type);
	}

	@Override
	public List<Poi> getAll() {

		List<Poi> pois = new ArrayList<>();

		// SELECT * FROM pois
		Cursor cur = db.query(PoisTable.TABLE, null, null, null, null, null, null);

		while (cur.moveToNext()) {
			pois.add(cursorToPoi(cur));
		}
		cur.close();

		return pois;
	}

	@Override
	public List<Poi> get(final Location location, @Nullable Double radius) {

		List<Poi> pois = getAll();

		if (radius != null && radius > 0) {
			// Rimuove dalla lista dei POI tutti quelli più distanti di radius da location
			for (Iterator<Poi> it = pois.iterator(); it.hasNext(); ) {
				Poi poi = it.next();
				if (poi.getLocation().distanceTo(location) > radius) {
					it.remove();
				}
			}
		}

		// Ordinamento POI per distanza da location
		Collections.sort(pois,
			new Comparator<Poi>() {
				@Override
				public int compare(Poi lhs, Poi rhs) {

					Float lhsDistance = lhs.getLocation().distanceTo(location);
					Float rhsDistance = rhs.getLocation().distanceTo(location);

					return lhsDistance.compareTo(rhsDistance);
				}
			}
		);

		return pois;
	}


	@Override
	public int add(List<Poi> pois, boolean truncateFirst) {

		if (truncateFirst) {
			removeAll();
		}

		int rows = 0;

		for (Poi poi : pois) {
			// Costruzione riga da inserire
			ContentValues values = new ContentValues();
			values.put(PoisTable._ID, poi.get_id());
			values.put(PoisTable.NAME, poi.getName());
			values.put(PoisTable.TYPE_ID, poi.getPoiType().getId());
			values.put(PoisTable.LATITUDE, poi.getLocation().getLatitude());
			values.put(PoisTable.LONGITUDE, poi.getLocation().getLongitude());
			values.put(PoisTable.ALTITUDE, poi.getLocation().getAltitude());

			if (db.replace(PoisTable.TABLE, null, values) > -1) {
				rows++;
			}
		}

		return rows;
	}

	@Override
	public int removeAll() {
		return db.delete(PoisTable.TABLE, "1", null);
	}
}
