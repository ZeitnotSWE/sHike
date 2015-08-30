package shike.app.model.dao.performance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import shike.app.model.dao.db.DbUtil;
import shike.app.model.dao.db.RecordedTrackLocationsTable;
import shike.app.model.dao.db.RecordedTracksTable;
import shike.app.model.dao.db.VirtualTrackLocationsTable;
import shike.app.model.session.performance.Performance;
import shike.app.model.session.track.RecordedTrack;

/**
 * Implementazione di {@link PerformanceDao}
 */
public class PerformanceDaoImpl implements PerformanceDao {

	private SQLiteDatabase db;

	public PerformanceDaoImpl(Context context) {
		db = DbUtil.getInstance(context).getWritableDatabase();
	}

	@Override
	public List<Performance> getAll() {
		List<Performance> performances = new ArrayList<>();

		Cursor cur = db.query(RecordedTracksTable.TABLE, null, null, null, null, null,
			null);

		while (cur.moveToNext()) {
			performances.add(cursorToPerformance(cur));
		}

		cur.close();
		return performances;
	}

	private Performance cursorToPerformance(Cursor cur) {

		// Creazione attributi performance
		int _id = cur.getInt(cur.getColumnIndex(RecordedTracksTable._ID));
		RecordedTrack track = cursorToTrack(cur);
		Date date = new Date(cur.getLong(cur.getColumnIndex(RecordedTracksTable.DATE)));
		double maxSpeed = cur.getDouble(cur.getColumnIndex(RecordedTracksTable.MAX_SPEED));
		long totalTime = cur.getLong(cur.getColumnIndex(RecordedTracksTable.TIME));
		int steps = cur.getInt(cur.getColumnIndex(RecordedTracksTable.STEPS));
		double distance = cur.getDouble(cur.getColumnIndex(RecordedTracksTable.DISTANCE));

		// Creazione performance
		Performance performance = new Performance(_id, track);
		performance.setDate(date);
		performance.setMaxSpeed(maxSpeed);
		performance.setTotalTime(totalTime);
		performance.setSteps(steps);
		performance.setDistance(distance);

		// Viene ritornata come Performance, così è immutabile (se non viene fatto un cast)
		return performance;

	}

	/**
	 * Crea un RecordedTrack a partire da una riga della tabella 'recordedtracks'
	 *
	 * @param cur cursore che rappresenta una riga di 'recordedtracks'
	 * @return percorso corrispondente alla riga
	 */
	private RecordedTrack cursorToTrack(Cursor cur) {

		int _id = cur.getInt(cur.getColumnIndex(RecordedTracksTable._ID));
		Integer virtual_id =
			(cur.isNull(cur.getColumnIndex(RecordedTracksTable.VIRTUAL_ID)) ?
				null : cur.getInt(cur.getColumnIndex(RecordedTracksTable.VIRTUAL_ID)));
		Date creationDate = new Date(cur.getLong(cur.getColumnIndex(RecordedTracksTable.DATE)));
		List<Location> points = getTrackPoints(_id);

		return new RecordedTrack(_id, virtual_id, creationDate, points);
	}

	/**
	 * Crea una lista ordinata dei punti del percorso
	 *
	 * @param id id del percorso del quale si vogliono i punti
	 * @return sequenza ordinata dei punti del percorso
	 */
	private List<Location> getTrackPoints(int id) {
		Map<Integer, Location> points = new TreeMap<>();

		Cursor cur = db.query(RecordedTrackLocationsTable.TABLE, null,
			RecordedTrackLocationsTable.RECORDED_TRACK_ID + " = " + id,
			null, null, null, null);

		while (cur.moveToNext()) {
			int order = cur.getInt(cur.getColumnIndex(
				VirtualTrackLocationsTable.LOCATION_ORDER));

			Location point = new Location(VirtualTrackLocationsTable.TABLE);
			point.setLatitude(cur.getDouble(
				cur.getColumnIndex(VirtualTrackLocationsTable.LATITUDE)));
			point.setLongitude(cur.getDouble(
				cur.getColumnIndex(VirtualTrackLocationsTable.LONGITUDE)));
			point.setAltitude(cur.getDouble(
				cur.getColumnIndex(VirtualTrackLocationsTable.ALTITUDE)));

			points.put(order, point);
		}

		cur.close();
		return new ArrayList<>(points.values());
	}

	@Override
	public long add(Performance performance) {
		// Se è una nuova Performance va prima salvato il percorso associato
		RecordedTrack track = performance.getRecordedTrack();

		// Preparazione contenuto statement
		ContentValues content = new ContentValues();
		if (performance.get_id() == null) {
			content.putNull(RecordedTracksTable._ID);
		} else {
			content.put(RecordedTracksTable._ID, performance.get_id());
		}
		if (track.getVirtual_id() != null) {
			content.put(RecordedTracksTable.VIRTUAL_ID, track.getVirtual_id());
		} else {
			content.putNull(RecordedTracksTable.VIRTUAL_ID);
		}
		content.put(RecordedTracksTable.DATE, performance.getDate().getTime());
		content.put(RecordedTracksTable.DISTANCE, performance.getDistance());
		content.put(RecordedTracksTable.TIME, performance.getTotalTime());
		content.put(RecordedTracksTable.MAX_SPEED, performance.getMaxSpeed());
		content.put(RecordedTracksTable.STEPS, performance.getSteps());

		long result = db.replaceOrThrow(RecordedTracksTable.TABLE, null, content);

		if (result > -1) {
			db.beginTransaction();
			int index = 0;
			for (Location point : track.getPoints()) { // Inserimento punti del percorso nel db
				ContentValues pointContent = new ContentValues();
				pointContent.put(RecordedTrackLocationsTable.RECORDED_TRACK_ID,
					result);
				pointContent.put(RecordedTrackLocationsTable.LOCATION_ORDER, index++);
				pointContent.put(RecordedTrackLocationsTable.LATITUDE, point.getLatitude());
				pointContent.put(RecordedTrackLocationsTable.LONGITUDE, point.getLongitude
					());
				pointContent.put(RecordedTrackLocationsTable.ALTITUDE, point.getAltitude());

				db.replaceOrThrow(RecordedTrackLocationsTable.TABLE, null, pointContent);
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();

		// Inserimento nel db
		return result;
	}

	@Override
	public int add(List<Performance> performances, boolean truncateFirst) {
		if (truncateFirst) {
			removeAll();
		}
		int sum = 0;

		for (Performance performance : performances) {
			if (add(performance) > -1) {
				++sum;
			}
		}

		return sum;
	}

	@Override
	public int removeAll() {
		return db.delete(RecordedTracksTable.TABLE, "1", null);
	}
}
