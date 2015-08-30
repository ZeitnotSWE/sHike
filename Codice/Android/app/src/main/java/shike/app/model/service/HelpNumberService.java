package shike.app.model.service;

import android.content.Context;

import java.util.List;

import shike.app.model.dao.helpnumber.HelpNumberDao;
import shike.app.model.dao.helpnumber.HelpNumberDaoImpl;
import shike.app.model.user.HelpNumber;

/**
 * Classe service che fa da ponte tra i presenter e il DAO dei numeri di soccorso
 */
public class HelpNumberService {
	private final HelpNumberDao helpNumberDao;

	public HelpNumberService(Context context) {
		helpNumberDao = new HelpNumberDaoImpl(context);
	}

	public int add(List<HelpNumber> helpNumbers) {
		return helpNumberDao.add(helpNumbers, true);
	}

	public List<HelpNumber> getAll() {
		return helpNumberDao.getAll();
	}

	public int removeAll() {
		return helpNumberDao.removeAll();
	}


}
