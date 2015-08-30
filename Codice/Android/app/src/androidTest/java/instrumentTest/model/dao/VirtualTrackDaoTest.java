package instrumentTest.model.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shike.app.model.dao.track.VirtualTrackDao;
import shike.app.model.session.track.Track;
import shike.app.model.session.track.VirtualTrack;

/**
 * Test della gerarchia di VirtualTrackDao
 */
public abstract class VirtualTrackDaoTest<T extends Track> extends DaoBaseTest {
	protected VirtualTrackDao virtualTrackDao;

	public void testMultipleItems() {
		Map<Integer, VirtualTrack> trackMap = new HashMap<>();
		int trackCount = randomGenerator.nextInt(100);

		for (int i = 0; i < trackCount; i++) {
			VirtualTrack track = randomTrack(false);
			trackMap.put(track.get_id(), track);
		}

		trackCount = trackMap.size();
		Collection<VirtualTrack> trackCol = trackMap.values();

		assertEquals(trackCount, virtualTrackDao.add(new ArrayList<>(trackCol), true));

		List<VirtualTrack> dbTracks = virtualTrackDao.getAll();

		assertEquals(trackCount, dbTracks.size());

		assertTrue(trackCol.containsAll(dbTracks) && dbTracks.containsAll(trackCol));

		assertEquals(trackCount, virtualTrackDao.removeAll());
	}

	protected abstract VirtualTrack randomTrack(boolean null_id);

	public void testSingleItem() {
		VirtualTrack track = randomTrack(false);
		int track_id = track.get_id();

		List<VirtualTrack> tracks = new ArrayList<>(1);
		tracks.add(track);

		virtualTrackDao.add(tracks, true);

		VirtualTrack dbTrack = virtualTrackDao.get(track_id);

		assertEquals(track, dbTrack);

		assertEquals(1, virtualTrackDao.removeAll());
	}
}
