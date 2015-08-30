package shike.app.model.service;

import android.content.Context;

import java.util.List;

import shike.app.model.dao.performance.PerformanceDao;
import shike.app.model.dao.performance.PerformanceDaoImpl;
import shike.app.model.session.performance.Performance;

/**
 * Service che fa da ponte tra i presenter e il DAO delle performance
 */
public class PerformanceService {
	private final PerformanceDao performanceDao;

	public PerformanceService(Context context) {
		performanceDao = new PerformanceDaoImpl(context);
	}

	public List<Performance> getAll() {
		return performanceDao.getAll();
	}

	public long add(Performance performance) {
		return performanceDao.add(performance);
	}

	public int add(List<Performance> performances) {
		return performanceDao.add(performances, true);
	}

	public int removeAll() {
		return performanceDao.removeAll();
	}

}
