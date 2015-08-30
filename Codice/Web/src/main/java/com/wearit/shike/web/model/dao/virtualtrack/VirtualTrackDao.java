package com.wearit.shike.web.model.dao.virtualtrack;

import java.util.List;
import com.wearit.shike.web.model.session.track.Location;
import com.wearit.shike.web.model.session.track.VirtualTrack;

/**
 * Interfaccia che rappresenta il DAO per la tabella Tracks. Tale interfaccia definisce
 * tutte le operazioni di CRUD per tale tabella.
 */
public interface VirtualTrackDao {

	/**
	 * @return id max tabella 'virtualtracks'
	 */
	public int getIdMax();

	/**
	 * @return prossimo id per auto_increment tabella 'virtualtracks'
	 */
	public int getIdNext();

	/**
	 * @param _id
	 * @param account_id
	 * @param syncNumber
	 * @return
	 */
	public VirtualTrack getById(int _id);

	/**
	 * @param limit
	 * @return
	 */
	List<VirtualTrack> getAll(int limit);

	/**
	 * @param _id
	 * @return
	 */
	public List<Location> getAllPoints(int _id);

	/**
	 * @param track
	 */
	public void add(VirtualTrack track);

	/**
	 * @param list
	 */
	public void addAllPoints(int track_id, List<Location> listOfPoints);

	/**
	 * @param _id
	 */
	public void delete(int _id);

	/**
	 * @param track
	 */
	public void update(VirtualTrack track);

	/**
	 * @param _id
	 * @return Location della posizione del centro della mappa
	 */
	public Location calculateCenter(int _id);

	/**
	 * @param _id
	 * @param account_id
	 * @return
	 */
	public boolean isToSync(int account_id, int track_id);

	/**
	 * @param account_id
	 * @param track_id
	 */
	public void changeSync(int account_id, int track_id);
	
	
	/**
	 * @param account_id
	 * @return
	 */
	public List<VirtualTrack> getAllToSync(int account_id);

}
