package com.wearit.shike.web.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import com.wearit.shike.web.model.dao.account.AccountDao;
import com.wearit.shike.web.model.dao.account.AccountDaoImpl;
import com.wearit.shike.web.model.dao.poi.PoiDao;
import com.wearit.shike.web.model.dao.poi.PoiDaoImpl;
import com.wearit.shike.web.model.session.track.Poi;
import com.wearit.shike.web.model.user.CommonAccount;

public class PoiService {
	private PoiDao poiDao;
	private AccountDao accountDao;

	public PoiService() {
		setAccountPoiDao();
	}

	@Autowired
	public void setAccountPoiDao() {
		ApplicationContext context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		poiDao = (PoiDaoImpl) context.getBean("PoiDaoImpl");
		accountDao = (AccountDaoImpl) context.getBean("AccountDaoImpl");
		((ClassPathXmlApplicationContext) context).close();
	}

	public int getIdNext() {
		return poiDao.getIdNext();
	}

	public List<Poi> getAll() {
		return poiDao.getAll(0);
	}

	public List<Poi> getAll(int limit) {
		return poiDao.getAll(limit);
	}

	public Poi get(int id) {
		Poi poi = poiDao.get(id);
		try {
			// Recupero il nome dell'utente
			CommonAccount user = accountDao.getCommonById(poi.getAuthor_id());
			poi.setAuthorName(user.getFirstName() + " " + user.getLastName());
		} catch(DataAccessException dae) {
			// Se non viene trovato significa che è stato creato da un admin
			poi.setAuthorName("Amministratore");
		}
		return poi;
	}

	public void add(Poi poi) {
		poiDao.add(poi);
	}

	public void delete(int id) {
		poiDao.delete(id);
	}

	public void update(int id, Poi poi) {
		poiDao.update(id, poi);
	}
}
