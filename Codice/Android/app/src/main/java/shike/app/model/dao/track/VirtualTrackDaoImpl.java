package shike.app.model.dao.track;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import shike.app.model.dao.db.DbUtil;
import shike.app.model.dao.db.VirtualTrackLocationsTable;
import shike.app.model.dao.db.VirtualTracksTable;
import shike.app.model.session.track.VirtualTrack;

/**
 * Implementazione di {@link VirtualTrackDao} specifica per VirtualTrack
 */
public class VirtualTrackDaoImpl implements VirtualTrackDao {

	/**
	 * Riferimento al database dell'applicazione
	 */
	private SQLiteDatabase db;

	/**
	 * Costruttore necessario per ottenere il riferimento al database
	 *
	 * @param context contesto dell'applicazione, necessario per ottenere il database
	 */
	public VirtualTrackDaoImpl(Context context) {
		db = DbUtil.getInstance(context).getWritableDatabase();
	}

	/**
	 * Ritorna tutti i punti che compongono un percorso
	 *
	 * @param virtualTrackId id del percorso per il quale si vogliono trovare i punti
	 * @return sequenza ordinata dei punti del percorso
	 */
	private List<Location> getTrackPoints(int virtualTrackId) {
		Map<Integer, Location> points = new TreeMap<>();

		Cursor cur = db.query(VirtualTrackLocationsTable.TABLE, null,
			VirtualTrackLocationsTable.VIRTUAL_TRACK_ID + " = " + virtualTrackId,
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

	/**
	 * Crea un VirtualTrack a partire da una riga della tabella 'virtualtracks'
	 *
	 * @param cur cursore che rappresenta una riga di 'virtualtracks'
	 * @return percorso corrispondente alla riga
	 */
	private VirtualTrack cursorToTrack(Cursor cur) {
		int _id = cur.getInt(cur.getColumnIndex(VirtualTracksTable._ID));

		String name = cur.getString(cur.getColumnIndex(VirtualTracksTable.NAME));

		double length = cur.getDouble(cur.getColumnIndex(
			VirtualTracksTable.LENGTH));

		Date creationDate = new Date(cur.getLong(
			cur.getColumnIndex(VirtualTracksTable.CREATION_DATE)));

		Location barycenter = new Location(VirtualTracksTable.TABLE);
		barycenter.setLatitude(cur.getDouble(
			cur.getColumnIndex(VirtualTracksTable.BARYCENTER_LATITUDE)));
		barycenter.setLongitude(cur.getDouble(
			cur.getColumnIndex(VirtualTracksTable.BARYCENTER_LONGITUDE)));

		List<Location> points = getTrackPoints(_id);

		return new VirtualTrack(_id, name, length, points, creationDate, barycenter);
	}

	@Override
	public List<VirtualTrack> getAll() {

		List<VirtualTrack> tracks = new ArrayList<>();

		Cursor cur = db.query(VirtualTracksTable.TABLE, null, null, null, null, null,
			null);

		while (cur.moveToNext()) {
			tracks.add(cursorToTrack(cur));
		}

		cur.close();

		return tracks;
	}

	@Override
	public List<VirtualTrack> get(final Location location, @Nullable Double radius) {
		List<VirtualTrack> tracks = getAll();

		if (radius != null && radius > 0) {
			for (VirtualTrack track : tracks) {
				if (track.getPoints().get(0).distanceTo(location) > radius) {
					tracks.remove(track);
				}
			}
		}

		// Ordinamento percorsi per distanza da location
		Collections.sort(tracks, new Comparator<VirtualTrack>() {
			@Override
			public int compare(VirtualTrack lhs, VirtualTrack rhs) {
				double lhsDistance = lhs.getPoints().get(0).distanceTo(location);
				double rhsDistance = rhs.getPoints().get(0).distanceTo(location);

				if (lhsDistance < rhsDistance) { // lhs è più vicino a location di rhs
					return -1;
				} else if (lhsDistance > rhsDistance) { // rhs è più vicino a location di lhs
					return 1;
				} else { // lhs e rhs sono alla stessa distanza da location
					return 0;
				}
			}
		});

		return tracks;
	}

	@Override
	public VirtualTrack get(int virtualTrackId) {

		Cursor cur = db.query(VirtualTracksTable.TABLE, null,
			VirtualTracksTable._ID + " = " + virtualTrackId, null, null, null, null);
		VirtualTrack track = null;

		if (cur.getCount() == 1) {
			cur.moveToNext();

			track = cursorToTrack(cur);
		}
		cur.close();

		return track;
	}

	@Override
	public int add(List<VirtualTrack> tracks, boolean truncateFirst) {
		if (truncateFirst) {
			removeAll();
		}
		int rows = 0;
		for (VirtualTrack track : tracks) {
			ContentValues content = new ContentValues();
			content.put(VirtualTracksTable._ID, track.get_id());
			content.put(VirtualTracksTable.NAME, track.getName());
			content.put(VirtualTracksTable.CREATION_DATE, track.getCreationDate().getTime
				());
			content.put(VirtualTracksTable.LENGTH, track.getLength());
			content.put(VirtualTracksTable.BARYCENTER_LATITUDE,
				track.getCenter().getLatitude());
			content.put(VirtualTracksTable.BARYCENTER_LONGITUDE,
				track.getCenter().getLongitude());

			long result = db.replace(VirtualTracksTable.TABLE, null, content);

			db.beginTransaction();
			int index = 0;
			for (Location point : track.getPoints()) {
				ContentValues pointContent = new ContentValues();
				pointContent.put(VirtualTrackLocationsTable.VIRTUAL_TRACK_ID,
					track.get_id());
				pointContent.put(VirtualTrackLocationsTable.LOCATION_ORDER, index++);
				pointContent.put(VirtualTrackLocationsTable.LATITUDE, point.getLatitude());
				pointContent.put(VirtualTrackLocationsTable.LONGITUDE,
					point.getLongitude());
				pointContent.put(VirtualTrackLocationsTable.ALTITUDE, point.getAltitude());
				db.replaceOrThrow(VirtualTrackLocationsTable.TABLE, null, pointContent);
			}

			db.setTransactionSuccessful();
			db.endTransaction();

			if (result > -1) {
				++rows;
			}
		}
		return rows;
	}

	@Override
	public int removeAll() {
		return db.delete(VirtualTracksTable.TABLE, "1", null);
	}
}
