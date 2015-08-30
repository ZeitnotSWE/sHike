package com.wearit.shike.web.test.controller;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import java.util.Date;
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
import com.wearit.shike.web.controller.RecordedTrackController;
import com.wearit.shike.web.model.Message;
import com.wearit.shike.web.model.dao.recordtrack.RecordedTrackDao;
import com.wearit.shike.web.model.dao.recordtrack.RecordedTrackDaoImpl;
import com.wearit.shike.web.model.service.RecordedTrackService;
import com.wearit.shike.web.model.session.track.RecordedTrack;

public class RecordedTrackControllerTest {

	@Autowired
	WebApplicationContext wac;
	@Autowired
	MockHttpSession session;
	@Autowired
	MockHttpServletRequest request;

	private static MockMvc mockMvc;

	private static Date date;
	private static int userId, id, syncNumber, virtualId;
	private static RecordedTrack newRTrack;
	private static ApplicationContext context;
	private static RecordedTrackDao daoRTrack;

	@BeforeClass
	public static void setUpClass() throws Exception {
		// Valori per il test
		id = 1;
		userId = 1;
		syncNumber = 1;
		virtualId = 3;
		date = new Date(1233425646);

		// Setto poi per il test
		newRTrack = new RecordedTrack();
		newRTrack.set_id(id);
		newRTrack.setSyncNumber(syncNumber);
		newRTrack.setAuthor_id(userId);
		newRTrack.setVirtualId(virtualId);
		newRTrack.setCreationDate(date);

		RecordedTrackService recTSer = new RecordedTrackService();
		RecordedTrackController recTCon = new RecordedTrackController(userId);
		recTCon.setAccountService(recTSer);

		mockMvc = MockMvcBuilders.standaloneSetup(recTCon).build();
		context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		daoRTrack = (RecordedTrackDaoImpl) context.getBean("RecordedTrackDaoImpl");
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
	public void testDelete() throws Exception {
		daoRTrack.add(newRTrack);

		// Controllo che msg sia quello giusto
		Message expect = new Message("Eliminazione completata",
				"Eliminazione del tracciato selezionato completata.");

		mockMvc.perform(get("/user/tracks/delete/" + id + "/" + syncNumber + "/"))
				.andExpect(model().attribute("msg", expect))
				.andExpect(forwardedUrl("./inc/Message"));

		RecordedTrack test;
		try {
			test = daoRTrack.getById(id, userId, syncNumber);
			fail("Test fallito - RecordedTrack non cancellato");
		} catch(EmptyResultDataAccessException e) {
			// il poi è stato cancellato correttamente se sono qui
			test = null;
		}
		assertNull(test);
	}

	@Test
	public void testListOfTrack() throws Exception {
		mockMvc.perform(get("/user/tracks/")).andExpect(model().attributeExists("tracks"))
				.andExpect(forwardedUrl("track/recorded/ListTrack"));
	}

	@Test
	public void testShowTrack() throws Exception {
		daoRTrack.add(newRTrack);
		mockMvc.perform(get("/user/tracks/show/" + id + "/" + syncNumber + "/"))
				.andExpect(model().attributeExists("track"))
				.andExpect(model().attributeExists("listPoint"))
				.andExpect(model().attributeExists("stats"))
				.andExpect(forwardedUrl("track/recorded/DetailsTrack"));
	}

	@Test
	public void testGetStats() throws Exception {
		int userId = 284;
		RecordedTrackService recTSer = new RecordedTrackService();
		RecordedTrackController recTCon = new RecordedTrackController(userId);
		recTCon.setAccountService(recTSer);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recTCon).build();
		mockMvc.perform(get("/user/tracks/stats/")).andExpect(forwardedUrl("./track/Stats"));
	}

	@Test
	public void testShareTrack() throws Exception {
		daoRTrack.add(newRTrack);

		String name = "Percorso di test condiviso";
		mockMvc.perform(get("/user/tracks/share/" + id + "/" + syncNumber + "/")).andExpect(
				forwardedUrl("track/recorded/ShareTrack"));

		// Controllo che msg sia quello giusto
		Message expect = new Message("Condivisione completata",
				"Ora il tuo percorso è raggiungibile dalla sotto sezione "
						+ "\"Percorsi degli utenti\" nella sezione \"Percorsi\".");

		mockMvc.perform(
				post("/user/tracks/share/" + id + "/" + syncNumber + "/").param("name", name))
				.andExpect(model().attribute("msg", expect)).andExpect(status().isOk())
				.andExpect(forwardedUrl("./inc/Message")).andReturn();

	}

}
