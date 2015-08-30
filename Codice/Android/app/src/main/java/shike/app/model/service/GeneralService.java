package shike.app.model.service;

import android.content.Context;

import shike.app.model.dao.GeneralDao;
import shike.app.model.dao.GeneralDaoImpl;

/**
 * Service che fa da ponte tra i presenter e GeneralDao
 */
public class GeneralService {
	private GeneralDao generalDao;

	public GeneralService(Context context) {
		generalDao = new GeneralDaoImpl(context);
	}

	public boolean resetAll() {
		return generalDao.resetAll() > 0;
	}
}
