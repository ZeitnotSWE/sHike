package com.wearit.shike.web.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import com.wearit.shike.web.model.dao.virtualtrack.VirtualTrackDao;
import com.wearit.shike.web.model.dao.virtualtrack.VirtualTrackDaoImpl;
import com.wearit.shike.web.model.session.track.Location;
import com.wearit.shike.web.model.session.track.VirtualTrack;

public class VirtualTrackDaoImplTest {
	private static int userId, id, length;
	private static VirtualTrack newVTrack;
	private static ApplicationContext context;
	private static VirtualTrackDao daoVTrack;

	@BeforeClass
	public static void setUpClass() throws Exception {
		context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		daoVTrack = (VirtualTrackDaoImpl) context.getBean("VirtualTrackDaoImpl");

		userId = 1;
		length = 120;

		newVTrack = new VirtualTrack();
		newVTrack.setAuthor_id(userId);
		newVTrack.setName("Percorso di prova");
		newVTrack.setLength(length);

		Date date = new Date(1233425646);
		newVTrack.setCreationDate(date);

	}

	@After
	public void after() {
		try {
			// Cerco ultimo dao inserito
			// e lo elimino
			int id = daoVTrack.getIdNext() - 1;
			daoVTrack.delete(id);
		} catch(EmptyResultDataAccessException e) {
			// il poi non è presente se sono qui
		}
	}

	@Test
	public void testGetById() throws Exception {
		id = daoVTrack.getIdNext();
		newVTrack.set_id(id);

		daoVTrack.add(newVTrack);
		VirtualTrack vTrack = null;
		try {
			vTrack = daoVTrack.getById(id);
		} catch(EmptyResultDataAccessException e) {
			// Percorso non trovato
			fail("Test fallito - Percorso non trovato");
		}
		assertNotNull("vTrack != null", vTrack);
		assertEquals(newVTrack, vTrack);
	}

	@Test
	public void testAdd() throws Exception {
		id = daoVTrack.getIdNext();
		newVTrack.set_id(id);

		try {
			daoVTrack.add(newVTrack);
			VirtualTrack vTrack = daoVTrack.getById(id);
			assertEquals(newVTrack, vTrack);
		} catch(EmptyResultDataAccessException e) {
			// Il VirtualTrack non è stato inserito
			fail("Test fallito - VirtualTrack non inserito");
		}
	}

	@Test
	public void testDelete() throws Exception {
		id = daoVTrack.getIdNext();
		newVTrack.set_id(id);
		daoVTrack.add(newVTrack);
		VirtualTrack vTrack;

		try {
			daoVTrack.delete(id);
			vTrack = daoVTrack.getById(id);
			fail("Test fallito - VirtualTrack non cancellato");
		} catch(EmptyResultDataAccessException e) {
			// Il percorso è stato cancellato correttamente e quindi non lo trova
			vTrack = null;
		}

		assertNull("vTrack = null", vTrack);
	}

	@Test
	public void testUpdate() throws Exception {
		VirtualTrack track = null;
		id = daoVTrack.getIdNext();
		newVTrack.set_id(id);
		daoVTrack.add(newVTrack);

		newVTrack.setName("track modificato");

		try {
			daoVTrack.update(newVTrack);
			track = daoVTrack.getById(id);
		} catch(EmptyResultDataAccessException e) {
			// Poi non trovato
			fail("Test fallito - VirtualTrack non trovato");
		}
		assertNotNull("track != null", track);
		assertEquals(newVTrack, track);
	}

	@Test
	public void testGetAll() throws Exception {
		int n = 2;

		List<VirtualTrack> list = daoVTrack.getAll(n);
		assertEquals("list.size() = n", n, list.size());

		daoVTrack.add(newVTrack);
		list = daoVTrack.getAll(n + 1);
		assertEquals("list.size() = n + 1", n + 1, list.size());

		id = daoVTrack.getIdNext() - 1;

		daoVTrack.delete(id);
		list = daoVTrack.getAll(n);
		assertEquals("list.size() = n", n, list.size());

		int nLim = 1;

		List<VirtualTrack> listLim = daoVTrack.getAll(nLim);
		assertEquals("listLim.size() = nLim", listLim.size(), nLim);
	}

	@Test
	public void testGetAllPoints() throws Exception {
		int id = 3;
		List<Location> list = daoVTrack.getAllPoints(id);
		assertEquals("list.size() = 500", list.size(), 500);
	}

	@Test
	public void testAddPointsAndCenter() throws Exception {
		List<Location> listT, listP = new ArrayList<Location>();
		Location l1 = new Location(20, 30);
		listP.add(l1);

		id = daoVTrack.getIdNext();
		newVTrack.set_id(id);
		daoVTrack.add(newVTrack);

		listT = null;
		try {
			daoVTrack.addAllPoints(id, listP);
			listT = daoVTrack.getAllPoints(id);
		} catch(EmptyResultDataAccessException e) {
			// Percorso non trovato
			fail("Test fallito - Punti non aggiunti");
		}
		assertNotNull("listT != null", listT);
		assertEquals(listP.size(), listT.size());
		assertEquals(listP.get(0), listT.get(0));

		Location loc = daoVTrack.calculateCenter(id);
		assertEquals(l1, loc);

	}

}
