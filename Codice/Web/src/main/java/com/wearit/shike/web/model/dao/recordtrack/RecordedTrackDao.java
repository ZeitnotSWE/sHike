package com.wearit.shike.web.model.dao.recordtrack;

import java.sql.SQLException;
import java.util.List;
import com.wearit.shike.web.model.session.performance.Performance;
import com.wearit.shike.web.model.session.performance.Stats;
import com.wearit.shike.web.model.session.track.Location;
import com.wearit.shike.web.model.session.track.RecordedTrack;

public interface RecordedTrackDao {

	/**
	 * @param _id
	 * @param account_id
	 * @param syncNumber
	 * @return
	 */
	public RecordedTrack getById(int _id, int account_id, int syncNumber);

	/**
	 * Metodo che viene usato per elencare tutti i record appartenenti alla tabella Tracks
	 * 
	 * @return
	 */
	public List<RecordedTrack> getAll(int account_id);

	/**
	 * @param _id
	 * @param account_id
	 * @param syncNumber
	 * @return
	 */
	public List<Location> getAllPoints(int _id, int account_id, int syncNumber);

	/**
	 * Metodo utilizzato solo per i test
	 * 
	 * Aggiunge un RecordedTrack nel db
	 * 
	 * @param track
	 */
	public void add(RecordedTrack track);

	/**
	 * Metodo che viene usato per togliere un percorso dalla tabella Tracks
	 * 
	 * @param _id
	 * @param account_id
	 * @param syncNumber
	 */
	public void delete(int _id, int account_id, int syncNumber);

	/**
	 * @param _id
	 * @return Location della posizione del centro della mappa
	 */
	public Location calculateCenter(int _id, int account_id, int syncNumber);

	/**
	 * @param length
	 * @param _id
	 * @param account_id
	 * @param syncNumber
	 */
	public void updateLength(double length, RecordedTrack track);

	/**
	 * @param length
	 * @param _id
	 * @param account_id
	 * @param syncNumber
	 */
	public void updateHeightDiff(int heightDiff, RecordedTrack track);

	/**
	 * @param id
	 * @return
	 */
	public Performance getPerformanceById(int _id, int account_id, int syncNumber);

	/**
	 * @param account_id
	 *            del quale si vogliono le statistiche
	 * @return le statistiche
	 */
	public Stats getStats(int account_id);

	/**
	 * 
	 * @param p
	 * @param account_id
	 * @param syncNumber
	 * @throws SQLException
	 */
	public void addPerformance(Performance p, int account_id, int syncNumber) throws SQLException;

}
