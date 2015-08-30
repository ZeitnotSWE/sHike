package com.wearit.shike.web.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.wearit.shike.web.model.dao.poi.PoiDao;
import com.wearit.shike.web.model.dao.poi.PoiDaoImpl;
import com.wearit.shike.web.model.dao.recordtrack.RecordedTrackDao;
import com.wearit.shike.web.model.dao.recordtrack.RecordedTrackDaoImpl;
import com.wearit.shike.web.model.session.performance.Performance;
import com.wearit.shike.web.model.session.performance.Stats;
import com.wearit.shike.web.model.session.track.Location;
import com.wearit.shike.web.model.session.track.Poi;
import com.wearit.shike.web.model.session.track.RecordedTrack;

public class RecordedTrackService {
	private RecordedTrackDao recordedTrackDao;
	private PoiDao poiDao;

	public RecordedTrackService() {
		setRecordedTrackDao();
	}

	@Autowired
	public void setRecordedTrackDao() {
		ApplicationContext context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		poiDao = (PoiDaoImpl) context.getBean("PoiDaoImpl");
		recordedTrackDao = (RecordedTrackDaoImpl) context.getBean("RecordedTrackDaoImpl");
		((ClassPathXmlApplicationContext) context).close();
	}

	public RecordedTrack getById(int _id, int account_id, int syncNumber) {
		RecordedTrack track = recordedTrackDao.getById(_id, account_id, syncNumber);
		track.setPoints(this.getAllPoints(_id, account_id, syncNumber));
		track.setCenter(recordedTrackDao.calculateCenter(_id, account_id, syncNumber));
		return track;
	}

	public List<RecordedTrack> getAll(int account_id) {
		List<RecordedTrack> rtl = recordedTrackDao.getAll(account_id);
		for(RecordedTrack track : rtl) {
			if(!(track.getLengthCalc() > 0)) {
				track.setPoints(this.getAllPoints(track.get_id(), track.getAuthor_id(),
						track.getSyncNumber()));
				Double length = track.calculateLength();
				track.setLength(length);
				recordedTrackDao.updateLength(length, track);
			}

			if(track.getHeightDiff() == 0) {
				int heightDiff = track.calculateHeightDiff();
				track.setLength(heightDiff);
				recordedTrackDao.updateHeightDiff(heightDiff, track);
			}

		}
		return rtl;
	}

	public List<Location> getAllPoints(int _id, int account_id, int syncNumber) {
		return recordedTrackDao.getAllPoints(_id, account_id, syncNumber);
	}

	public void add(RecordedTrack rt) {
		recordedTrackDao.add(rt);
	}

	public void delete(int _id, int account_id, int syncNumber) {
		recordedTrackDao.delete(_id, account_id, syncNumber);
	}

	public List<Poi> getAllPoi() {
		return poiDao.getAll();
	}

	public Performance getPerformanceById(int _id, int account_id, int syncNumber) {
		return recordedTrackDao.getPerformanceById(_id, account_id, syncNumber);
	}

	public Stats getStats(int account_id) {
		return recordedTrackDao.getStats(account_id);
	}

	public boolean addPerformance(Performance p, int account_id, int syncNumber) {
		try {
			recordedTrackDao.addPerformance(p, account_id, syncNumber);
			return true;
		}
		catch(Exception e) {
			System.out.println("Salvataggio performance fallito");
		}
		return false;
	}
	
}
