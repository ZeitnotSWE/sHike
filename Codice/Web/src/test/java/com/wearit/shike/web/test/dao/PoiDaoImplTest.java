package com.wearit.shike.web.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import com.wearit.shike.web.model.dao.poi.PoiDao;
import com.wearit.shike.web.model.dao.poi.PoiDaoImpl;
import com.wearit.shike.web.model.session.track.Poi;

public class PoiDaoImplTest {
	private static int id, authorId;
	private static Poi newPoi;
	private static PoiDao daoPoi;
	private static ApplicationContext context;

	@BeforeClass
	public static void setUpClass() throws Exception {
		context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		daoPoi = (PoiDaoImpl) context.getBean("PoiDaoImpl");

		authorId = 1;
		newPoi = new Poi();
		newPoi.setAuthor_id(authorId);
		newPoi.setName("Poi di prova");
		newPoi.setType(1);
		newPoi.setLatitude(11);
		newPoi.setLongitude(22);
		newPoi.setAltitude(30);
	}

	@After
	public void after() {
		try {
			// Cerco ultimo dao inserito
			id = daoPoi.getIdNext() - 1;
			// e lo elimino
			daoPoi.delete(id);
		} catch(EmptyResultDataAccessException e) {
			// il poi non è presente se sono qui
		}
	}

	@Test
	public void testAdd() throws Exception {
		Poi poi = null;
		id = daoPoi.getIdNext();
		newPoi.set_id(id);
		try {
			daoPoi.add(newPoi);
			poi = daoPoi.get(id);
		} catch(EmptyResultDataAccessException e) {
			// Poi non inserito
			fail("Test fallito - Poi non inserito");
		}
		assertNotNull("poi != null", poi);
		assertEquals(newPoi, poi);

	}

	@Test
	public void testDelete() throws Exception {
		Poi poi;
		id = daoPoi.getIdNext();
		newPoi.set_id(id);
		daoPoi.add(newPoi);

		try {
			daoPoi.delete(id);
			poi = daoPoi.get(id);
			fail("Test fallito - Poi non cancellato");
		} catch(EmptyResultDataAccessException e) {
			// Poi non trovato perchè eliminato
			poi = null;
		}
		assertNull("poi = null", poi);
	}

	@Test
	public void testUpdate() throws Exception {
		Poi poi = null;
		id = daoPoi.getIdNext();
		newPoi.set_id(id);
		double altitude = newPoi.getAltitude() + 1;
		try {
			daoPoi.add(newPoi);
			newPoi.setAltitude(altitude);
			daoPoi.update(id, newPoi);
			poi = daoPoi.get(id);
		} catch(EmptyResultDataAccessException e) {
			// Poi non trovato
			fail("Test fallito - Poi non trovato");
		}
		assertNotNull("poi != null", poi);
		assertEquals(newPoi, poi);

	}

	@Test
	public void testGetAll() throws Exception {
		id = daoPoi.getIdNext();

		int n = 8;

		List<Poi> list = daoPoi.getAll();
		assertEquals("list.size() = n", n, list.size());

		daoPoi.add(newPoi);
		list = daoPoi.getAll();
		assertEquals("list.size() = n+1", n + 1, list.size());

		daoPoi.delete(id);
		list = daoPoi.getAll();
		assertEquals("list.size() = n", n, list.size());

		int nLim = 2;

		List<Poi> listLim = daoPoi.getAll(nLim);
		assertEquals("listLim.size() = nLim", nLim, listLim.size());
	}

}
