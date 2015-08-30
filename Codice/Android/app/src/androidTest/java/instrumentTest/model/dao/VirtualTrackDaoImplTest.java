package instrumentTest.model.dao;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import shike.app.model.dao.track.VirtualTrackDaoImpl;
import shike.app.model.session.track.VirtualTrack;

/**
 * Test per la classe VirtualTrackDaoImpl
 */
public class VirtualTrackDaoImplTest extends VirtualTrackDaoTest<VirtualTrack> {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		virtualTrackDao = new VirtualTrackDaoImpl(context);
	}

	@Override
	protected VirtualTrack randomTrack(boolean null_id) {

		int _id = Math.abs(randomGenerator.nextInt()) + 1;
		String name = randomString(100, StringType.ALPHANUMERIC);
		double length = randomGenerator.nextDouble() * 10000;

		List<Location> points = new ArrayList<>();
		int pointCount = randomGenerator.nextInt(100);
		for (int i = 0; i < pointCount; i++) {
			points.add(randomLocation());
		}
		Date creationDate = new Date(Math.abs(randomGenerator.nextLong()));
		Location barycenter = randomLocation();

		return new VirtualTrack(_id, name, length, points, creationDate, barycenter);
	}
}
