package instrumentTest.model.dao;

import android.location.Location;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shike.app.model.dao.poi.PoiDao;
import shike.app.model.dao.poi.PoiDaoImpl;
import shike.app.model.session.track.Poi;

/**
 * Classe di test per PoiDaoImpl
 */
public class PoiDaoImplTest extends DaoBaseTest {
	private Poi.PoiType[] poiTypes = Poi.PoiType.values();
	private PoiDao poiDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		poiDao = new PoiDaoImpl(context);
	}

	public void testMultipleItems() {
		Map<Integer, Poi> poiMap = new HashMap<>();
		int poiCount = randomGenerator.nextInt(1000) + 1;


		for (int i = 0; i < poiCount; i++) {
			Poi poi = randomPoi(null);
			poiMap.put(poi.get_id(), poi);
		}

		poiCount = poiMap.size();

		Collection<Poi> poiCol = poiMap.values();

		assertEquals(poiCount, poiDao.add(new ArrayList<>(poiCol), true));

		List<Poi> dbPois = poiDao.getAll();

		assertEquals(poiCount, dbPois.size());

		assertTrue(poiCol.containsAll(dbPois) && dbPois.containsAll(poiCol));

		assertEquals(poiCount, poiDao.removeAll());
	}

	private Poi randomPoi(@Nullable Poi.PoiType type) {
		if (type == null) {
			type = poiTypes[randomGenerator.nextInt(poiTypes.length)];
		}

		Location location = randomLocation();

		return new Poi(Math.abs(randomGenerator.nextInt()) + 1,
			location, randomString(100, StringType.ALPHANUMERIC), type);
	}

	public void testByLocation() {
		Map<Integer, Poi> poiMap = new HashMap<>();
		int poiCount = randomGenerator.nextInt(1000) + 1;

		Location searchLocation = randomLocation();
		double searchRadius = randomGenerator.nextDouble() * 10000;
		List<Poi> inRadiusPois = new ArrayList<>();


		for (int i = 0; i < poiCount; i++) {
			Poi poi = randomPoi(null);
			poiMap.put(poi.get_id(), poi);

			if (poi.getLocation().distanceTo(searchLocation) < searchRadius) {
				inRadiusPois.add(poi);
			}
		}

		poiCount = poiMap.size();

		Collection<Poi> poiCol = poiMap.values();

		assertEquals(poiCount, poiDao.add(new ArrayList<>(poiCol), true));

		List<Poi> dbPois = poiDao.get(searchLocation, searchRadius);

		assertEquals(inRadiusPois.size(), dbPois.size());

		assertTrue(inRadiusPois.containsAll(dbPois) && dbPois.containsAll(inRadiusPois));

		assertEquals(poiCount, poiDao.removeAll());
	}
}
