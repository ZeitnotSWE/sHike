package shike.app.model.service;

import android.content.Context;
import android.location.Location;
import android.support.annotation.Nullable;

import java.util.List;

import shike.app.model.dao.track.VirtualTrackDao;
import shike.app.model.dao.track.VirtualTrackDaoImpl;
import shike.app.model.session.track.VirtualTrack;

/**
 * Created by Admin on 08/06/2015.
 */
public class VirtualTrackService {
	private final VirtualTrackDao virtualTrackDao;

	public VirtualTrackService(Context context) {
		virtualTrackDao = new VirtualTrackDaoImpl(context);
	}

	public List<VirtualTrack> getAll() {
		return virtualTrackDao.getAll();
	}

	public List<VirtualTrack> get(Location center, @Nullable Double radius) {
		return virtualTrackDao.get(center, radius);
	}

	public VirtualTrack get(int virtualTrackId) {
		return virtualTrackDao.get(virtualTrackId);
	}

	public int add(List<VirtualTrack> tracks) {
		return virtualTrackDao.add(tracks, true);
	}

	public int removeAll() {
		return virtualTrackDao.removeAll();
	}
}
