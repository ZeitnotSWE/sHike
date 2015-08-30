package shike.app.model.service;

import android.content.Context;
import android.location.Location;

import java.util.List;

import shike.app.model.dao.poi.PoiDao;
import shike.app.model.dao.poi.PoiDaoImpl;
import shike.app.model.session.track.Poi;

/**
 * Service che fa da ponte tra i presenter e il DAO dei POI
 */
public class PoiService {
	private final PoiDao poiDao;

	public PoiService(Context context) {
		poiDao = new PoiDaoImpl(context);
	}

	public int add(List<Poi> poiList) {
		return poiDao.add(poiList, true);
	}

	public List<Poi> getAll() {
		return poiDao.getAll();
	}

	public List<Poi> get(Location center, Double radius) {
		return poiDao.get(center, radius);
	}

	public int removeAll() {
		return poiDao.removeAll();
	}
}
