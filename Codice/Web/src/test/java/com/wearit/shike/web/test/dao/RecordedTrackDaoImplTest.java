package com.wearit.shike.web.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import com.wearit.shike.web.model.dao.recordtrack.RecordedTrackDao;
import com.wearit.shike.web.model.dao.recordtrack.RecordedTrackDaoImpl;
import com.wearit.shike.web.model.service.RecordedTrackService;
import com.wearit.shike.web.model.session.performance.Performance;
import com.wearit.shike.web.model.session.track.Location;
import com.wearit.shike.web.model.session.track.RecordedTrack;

public class RecordedTrackDaoImplTest {
	private static int userId, id, syncNumber, virtualId, heightDiff;
	private static double length;
	private static RecordedTrack newRTrack;
	private static ApplicationContext context;
	private static RecordedTrackDao daoRTrack;

	@BeforeClass
	public static void setUpClass() throws Exception {
		userId = 1;
		id = 1;
		syncNumber = 1;
		virtualId = 3;
		length = 100.0;
		heightDiff = 15;

		context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		daoRTrack = (RecordedTrackDaoImpl) context.getBean("RecordedTrackDaoImpl");
		newRTrack = new RecordedTrack();
		newRTrack.set_id(id);
		newRTrack.setSyncNumber(syncNumber);
		newRTrack.setAuthor_id(userId);
		newRTrack.setVirtualId(virtualId);
		newRTrack.setLength(length);
		newRTrack.setHeightDiff(heightDiff);

		Date date = new Date(1233425646);
		newRTrack.setCreationDate(date);
	}

	@After
	public void after() {
		try {
			// Elimino RecordedTrack inserito per il test
			daoRTrack.delete(id, userId, syncNumber);
		} catch(EmptyResultDataAccessException e) {
			// Il RecordedTrack non è presente se sono qui
		}
	}

	@Test
	public void testGetById() throws Exception {
		daoRTrack.add(newRTrack);

		RecordedTrack rTrack = null;
		try {
			rTrack = daoRTrack.getById(id, userId, syncNumber);
		} catch(EmptyResultDataAccessException e) {
			// Percorso non trovato
			fail("Test fallito - Percorso non trovato");
		}
		assertNotNull("rTrack != null", rTrack);
		assertEquals(rTrack, newRTrack);
	}

	@Test
	public void testAdd() throws Exception {
		try {
			daoRTrack.add(newRTrack);
			RecordedTrack rTrack = daoRTrack.getById(id, userId, syncNumber);
			assertEquals(newRTrack, rTrack);
		} catch(EmptyResultDataAccessException e) {
			// Il Recorded Track non è stato inserito
			fail("Test fallito - Recorded Track non inserito");
		}
	}

	@Test
	public void testDelete() throws Exception {
		daoRTrack.add(newRTrack);
		RecordedTrack rTrack;

		try {
			daoRTrack.delete(id, userId, syncNumber);
			rTrack = daoRTrack.getById(id, userId, syncNumber);
			fail("Test fallito - Percorso non cancellato");
		} catch(EmptyResultDataAccessException e) {
			// Il percorso è stato cancellato correttamente e quindi non lo trova
			rTrack = null;
		}

		assertNull("rTrack = null", rTrack);
	}

	@Test
	public void testGetAll() throws Exception {
		// Testo sui percorsi dell'utente con _id='userId'
		int n = 0;
		List<RecordedTrack> list = daoRTrack.getAll(userId);
		assertEquals("1 list.size() = n", list.size(), n);

		daoRTrack.add(newRTrack);
		list = daoRTrack.getAll(userId);
		assertEquals("2 list.size() = n+1", list.size(), n + 1);

		daoRTrack.delete(id, userId, syncNumber);
		list = daoRTrack.getAll(userId);
		assertEquals("3 list.size() = n", list.size(), n);
	}

	@Test
	public void testGetAllPoints() throws Exception {
		List<Location> list = daoRTrack.getAllPoints(id, userId, syncNumber);
		assertEquals("list.size() = 0", list.size(), 0);
	}

	@Test
	public void testUpdateLength() throws Exception {
		double lengthMod = length + 50;
		RecordedTrack rTrack = null;
		daoRTrack.add(newRTrack);
		try {
			daoRTrack.updateLength(lengthMod, newRTrack);
			rTrack = daoRTrack.getById(id, userId, syncNumber);
		} catch(EmptyResultDataAccessException e) {
			// Il Recorded Track non è stato trovato
			fail("Test fallito - Recorded Track non trovato");
		}
		assertNotNull("rTrack != null", rTrack);
		assertEquals(lengthMod, rTrack.getLength(), 0.1);
	}

	@Test
	public void testUpdateHeightDiff() throws Exception {
		int heightDiffMod = heightDiff + 50;
		RecordedTrack rTrack = null;
		daoRTrack.add(newRTrack);
		try {
			daoRTrack.updateHeightDiff(heightDiffMod, newRTrack);
			rTrack = daoRTrack.getById(id, userId, syncNumber);
		} catch(EmptyResultDataAccessException e) {
			// Il Recorded Track non è stato trovato
			fail("Test fallito - Recorded Track non trovato");
		}
		assertNotNull("rTrack != null", rTrack);
		assertEquals(heightDiffMod, rTrack.getHeightDiff());
	}

	@Test
	public void testGetPerformanceById() throws Exception {
		Performance perf = null;
		Performance expect = new Performance();
		expect.setTrack_id(id);
		expect.setAccount_id(userId);
		expect.setSyncNumber(syncNumber);
		expect.setMaxSpeed(12);
		expect.setDistance(100);
		expect.setHeightDiff(20);
		expect.setSteps(1000);
		expect.setCounter(12);
		expect.setTime(123424);
		expect.setRecordedTrack(newRTrack);

		// Inserisco la Performance per il test
		RecordedTrackService rts = new RecordedTrackService();
		rts.addPerformance(expect, userId, syncNumber);

		try {
			perf = daoRTrack.getPerformanceById(id, userId, syncNumber);
		} catch(EmptyResultDataAccessException e) {
			// Il Recorded Track non è stato trovato
			fail("Test fallito - Recorded Track non trovato");
		}
		assertNotNull("perf != null", perf);
		assertEquals(expect, perf);
	}

}
