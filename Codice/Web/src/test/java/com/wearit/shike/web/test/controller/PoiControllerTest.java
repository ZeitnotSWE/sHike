package com.wearit.shike.web.test.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.model;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.wearit.shike.web.controller.PoiController;
import com.wearit.shike.web.model.dao.poi.PoiDao;
import com.wearit.shike.web.model.dao.poi.PoiDaoImpl;
import com.wearit.shike.web.model.session.track.Poi;
import com.wearit.shike.web.model.user.Account;

public class PoiControllerTest {

	@Autowired
	WebApplicationContext wac;
	@Autowired
	MockHttpSession session;
	@Autowired
	MockHttpServletRequest request;

	private static MockMvc mockMvc;

	private static ApplicationContext context;
	private static PoiDao poiDao;
	private static Poi newPoi;
	private static int id, authorId;
	private static int type_id;
	private static String name;
	private static double lat, lon, alt;

	@BeforeClass
	public static void setUpClass() throws Exception {
		// Valori per il test
		authorId = 1;
		name = "Poi di prova";
		type_id = 1;
		lat = 11;
		lon = 22;
		alt = 30;

		// Setto poi per il test
		newPoi = new Poi();
		newPoi.setAuthor_id(authorId);
		newPoi.setName(name);
		newPoi.setType(type_id);
		newPoi.setLatitude(lat);
		newPoi.setLongitude(lon);
		newPoi.setAltitude(alt);

		Account testAccount = new Account();
		testAccount.set_id(authorId);
		testAccount.setRole("ROLE_USER");

		mockMvc = MockMvcBuilders.standaloneSetup(new PoiController(testAccount)).build();
		context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		poiDao = (PoiDaoImpl) context.getBean("PoiDaoImpl");
	}

	@After
	public void after() {
		try {
			// Cerco ultimo dao inserito
			id = poiDao.getIdNext() - 1;
			// e lo elimino
			poiDao.delete(id);
		} catch(EmptyResultDataAccessException e) {
			// il poi non è presente se sono qui
		}
	}

	@Test
	public void testAddPoi() throws Exception {
		id = poiDao.getIdNext();

		// Trasformo tutti i parametri in stringhe
		String sAuthorId = Integer.toString(authorId);
		String sId = Integer.toString(id);
		String sTypeId = Integer.toString(type_id);
		String sLat = Double.toString(lat);
		String sLon = Double.toString(lon);
		String sAlt = Double.toString(alt);

		mockMvc.perform(
				post("/user/tracks/poi/add/").param("author_id", sAuthorId).param("_id", sId)
						.param("name", name).param("type_id", sTypeId).param("latitude", sLat)
						.param("longitude", sLon).param("altitude", sAlt))
				.andExpect(forwardedUrl("./inc/Message")).andReturn();
		Poi test = null;
		try {
			test = poiDao.get(id);
		} catch(EmptyResultDataAccessException e) {
			// se sono qui l'inserimento è fallito
			fail("Test fallito - Poi non aggiunto");
		}
		assertNotNull(test);
	}

	@Test
	public void testDeletePoi() throws Exception {
		id = poiDao.getIdNext();
		poiDao.add(newPoi);
		mockMvc.perform(get("/user/tracks/poi/delete/" + id)).andReturn();
		Poi test;
		try {
			test = poiDao.get(id);
			fail("Test fallito - Poi non cancellato");
		} catch(EmptyResultDataAccessException e) {
			// il poi è stato cancellato correttamente se sono qui
			test = null;
		}
		assertNull(test);
	}

	@Test
	public void testListPoi() throws Exception {
		mockMvc.perform(get("/user/tracks/poi/")).andExpect(model().attributeExists("currentId"))
				.andExpect(model().attributeExists("listaPoi"))
				.andExpect(forwardedUrl("track/poi/ListPoi"));
	}

	@Test
	public void testListPoiDetails() throws Exception {
		id = poiDao.getIdNext();
		poiDao.add(newPoi);
		mockMvc.perform(get("/user/tracks/poi/" + id + "/"))
				.andExpect(model().attributeExists("poi"))
				.andExpect(forwardedUrl("track/poi/DetailsPoi"));
	}

}
