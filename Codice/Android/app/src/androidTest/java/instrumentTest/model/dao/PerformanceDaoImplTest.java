package instrumentTest.model.dao;

import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shike.app.helper.json.JsonDateDeserializer;
import shike.app.helper.json.JsonDateSerializer;
import shike.app.model.dao.performance.PerformanceDao;
import shike.app.model.dao.performance.PerformanceDaoImpl;
import shike.app.model.session.performance.Performance;
import shike.app.model.session.track.RecordedTrack;

/**
 * Test per la classe PerformanceDaoImpl
 */
public class PerformanceDaoImplTest extends DaoBaseTest {
	private PerformanceDao performanceDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		performanceDao = new PerformanceDaoImpl(context);
	}

	public void testMultipleItems() {
		Map<Integer, Performance> perfMap = new HashMap<>();
		int performanceCount = randomGenerator.nextInt(100);

		for (int i = 0; i < performanceCount; i++) {
			Performance rndPerformance = randomPerformance();
			perfMap.put(rndPerformance.get_id(), rndPerformance);
		}

		performanceCount = perfMap.size();
		Collection<Performance> perfCol = perfMap.values();

		assertEquals(performanceCount, performanceDao.add(new ArrayList<>(perfCol), true));

		List<Performance> dbPerfs = performanceDao.getAll();

		assertEquals(perfCol.size(), dbPerfs.size());

		assertTrue(perfCol.containsAll(dbPerfs) && dbPerfs.containsAll(perfCol));

		assertEquals(performanceCount, performanceDao.removeAll());
	}

	public Performance randomPerformance() {
		int _id = Math.abs(randomGenerator.nextInt()) + 1;

		Performance performance = new Performance(_id, randomTrack(true));

		performance.setDistance(randomGenerator.nextDouble() * 100000);
		performance.setSteps(randomGenerator.nextInt(100000));
		performance.setDate(new Date(Math.abs(randomGenerator.nextLong() % 1893456000000l)));
		performance.setTotalTime(Math.abs(randomGenerator.nextLong()) % 36000000);
		performance.setMaxSpeed(randomGenerator.nextDouble() * 20);

		return performance;
	}

	private RecordedTrack randomTrack(boolean null_id) {
		Integer _id = null;
		if (!null_id) {
			_id = Math.abs(randomGenerator.nextInt(10000)) + 1;
		}

		List<Location> points = new ArrayList<>();
		int pointCount = randomGenerator.nextInt(100);
		for (int i = 0; i < pointCount; i++) {
			points.add(randomLocation());
		}
		Date creationDate = new Date(Math.abs(randomGenerator.nextLong() % 1893456000000l));

		return new RecordedTrack(_id, null, creationDate, points);
	}

	public void testSingleItem() {
		Performance performance = randomPerformance();
		int _id = performance.get_id();

		performanceDao.removeAll();

		assertEquals(_id, performanceDao.add(performance));

		Performance dbPerformance = performanceDao.getAll().get(0);

		assertEquals(performance, dbPerformance);

		assertEquals(1, performanceDao.removeAll());
	}

	public void testJsonConversion() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new JsonDateSerializer());
		gsonBuilder.registerTypeAdapter(Date.class, new JsonDateDeserializer());

		Gson gson = gsonBuilder.create();

		Performance performance = randomPerformance();

		String json = gson.toJson(performance);

		Performance performance1 = gson.fromJson(json, Performance.class);

		assertEquals(performance, performance1);
	}
}
