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
import com.wearit.shike.web.model.dao.virtualtrack.VirtualTrackDao;
import com.wearit.shike.web.model.dao.virtualtrack.VirtualTrackDaoImpl;
import com.wearit.shike.web.model.session.track.Location;
import com.wearit.shike.web.model.session.track.Poi;
import com.wearit.shike.web.model.session.track.VirtualTrack;
import com.wearit.shike.web.model.user.CommonAccount;

public class VirtualTrackService {
	private VirtualTrackDao virtualTrackDao;
	private PoiDao poiDao;
	private AccountDao accountDao;

	public VirtualTrackService() {
		setVirtualTrackDao();
	}

	@Autowired
	public void setVirtualTrackDao() {
		ApplicationContext context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		virtualTrackDao = (VirtualTrackDaoImpl) context.getBean("VirtualTrackDaoImpl");
		poiDao = (PoiDaoImpl) context.getBean("PoiDaoImpl");
		accountDao = (AccountDaoImpl) context.getBean("AccountDaoImpl");
		((ClassPathXmlApplicationContext) context).close();
	}

	public int getIdMax() {
		return virtualTrackDao.getIdMax();
	}

	public int getIdNext() {
		return virtualTrackDao.getIdNext();
	}

	public VirtualTrack getById(int _id) {
		VirtualTrack vt = virtualTrackDao.getById(_id);
		try {
			// Recupero il nome dell'utente
			CommonAccount user = accountDao.getCommonById(vt.getAuthor_id());
			vt.setAuthorName(user.getFirstName() + " " + user.getLastName());
		} catch(DataAccessException dae) {
			// Se non viene trovato significa che è stato creato da un admin
			vt.setAuthorName("Amministratore");
		}
		vt.setPoints(this.getAllPoints(_id));
		vt.setCenter(virtualTrackDao.calculateCenter(_id));
		return vt;
	}

	public List<VirtualTrack> getAll() {
		return this.getAll(0, 0);
	}

	public List<VirtualTrack> getAll(int account_id) {
		return this.getAll(account_id, 0);
	}

	public List<VirtualTrack> getAll(int account_id, int limit) {
		List<VirtualTrack> vtl = virtualTrackDao.getAll(limit);
		for(VirtualTrack vt : vtl) {
			try {
				// Recupero il nome dell'utente
				CommonAccount user = accountDao.getCommonById(vt.getAuthor_id());
				vt.setAuthorName(user.getFirstName() + " " + user.getLastName());
				if(account_id > 0) {
					vt.setToSync(virtualTrackDao.isToSync(account_id, vt.get_id()));
				}
			} catch(DataAccessException dae) {
				// Se non viene trovato significa che è stato creato da un admin
				vt.setAuthorName("Amministratore");
			}
		}
		return vtl;
	}

	public List<Location> getAllPoints(int _id) {
		return virtualTrackDao.getAllPoints(_id);
	}

	public void add(VirtualTrack track, List<Location> listOfPoints) {
		virtualTrackDao.add(track);
		int _id = virtualTrackDao.getIdMax();
		virtualTrackDao.addAllPoints(_id, listOfPoints);
	}

	public void delete(int _id) {
		virtualTrackDao.delete(_id);
	}

	public void update(VirtualTrack track) {
		virtualTrackDao.update(track);
	}

	public List<Poi> getAllPoi() {
		return poiDao.getAll();
	}

	public boolean isToSync(int account_id, int track_id) {
		return virtualTrackDao.isToSync(account_id, track_id);
	}

	public void changeSync(int account_id, int track_id) {
		virtualTrackDao.changeSync(account_id, track_id);
	}

	public Location getCenter(int _id) {
		return virtualTrackDao.calculateCenter(_id);
	}

	public List<VirtualTrack> getAllToSync(int account_id) {
		return virtualTrackDao.getAllToSync(account_id);
	}
}
