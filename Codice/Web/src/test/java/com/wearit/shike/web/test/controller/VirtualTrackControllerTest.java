package com.wearit.shike.web.test.controller;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.model;
import java.util.Date;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.wearit.shike.web.controller.VirtualTrackController;
import com.wearit.shike.web.model.Message;
import com.wearit.shike.web.model.service.VirtualTrackService;
import com.wearit.shike.web.model.session.track.VirtualTrack;

public class VirtualTrackControllerTest {

	@Autowired
	WebApplicationContext wac;
	@Autowired
	MockHttpSession session;
	@Autowired
	MockHttpServletRequest request;

	private static MockMvc mockMvc;

	private static Date date;
	private static int userId, id;
	private static VirtualTrack newVTrack;

	@BeforeClass
	public static void setUpClass() throws Exception {
		// Valori per il test
		userId = 1;
		date = new Date(1233425646);

		// Setto VirtualTrack per il test
		newVTrack = new VirtualTrack();
		newVTrack.setAuthor_id(userId);
		newVTrack.setCreationDate(date);

		VirtualTrackService virTSer = new VirtualTrackService();
		VirtualTrackController virTCon = new VirtualTrackController(userId);
		virTCon.setVirtualTrackService(virTSer);

		mockMvc = MockMvcBuilders.standaloneSetup(virTCon).build();
	}

	@Test
	public void testListOfTrack() throws Exception {
		mockMvc.perform(get("/user/virtualtracks/")).andExpect(model().attributeExists("vt"))
				.andExpect(forwardedUrl("track/virtual/ListTrack"));

		mockMvc.perform(get("/admin/virtualtracks/")).andExpect(model().attributeExists("vt"))
				.andExpect(forwardedUrl("track/virtual/ListTrack"));

	}

	@Test
	public void testShowTrack() throws Exception {
		id = 3;
		mockMvc.perform(get("/user/virtualtracks/show/" + id))
				.andExpect(model().attributeExists("vt")).andExpect(model().attributeExists("ltp"))
				.andExpect(model().attributeExists("lpoi"))
				.andExpect(forwardedUrl("track/virtual/DetailsTrack"));
	}

	@Test
	public void testEditTrackName() throws Exception {
		mockMvc.perform(get("/user/virtualtracks/editname/" + id))
				.andExpect(model().attributeExists("vt")).andExpect(model().attributeExists("ltp"))
				.andExpect(forwardedUrl("track/virtual/EditTrack"));

		String name = "";
		Message error = new Message("Modifica fallita",
				"Il nuovo nome è troppo corto o troppo lungo.", 1);

		mockMvc.perform(post("/user/virtualtracks/editname/" + id).param("name", name))
				.andExpect(model().attribute("msg", error))
				.andExpect(forwardedUrl("./inc/Message"));

		name = "pluto2";
		Message success = new Message("Modifica completata", "Modifica del nome completata.");
		mockMvc.perform(post("/user/virtualtracks/editname/" + id).param("name", name))
				.andExpect(model().attribute("msg", success))
				.andExpect(forwardedUrl("./inc/Message"));

	}

	@Test
	public void testDeleteTrack() throws Exception {
		Message expect = new Message("Eliminazione completata",
				"Eliminazione del tracciato completata con successo.");

		mockMvc.perform(get("/user/virtualtracks/delete/" + id))
				.andExpect(model().attribute("msg", expect))
				.andExpect(forwardedUrl("./inc/Message"));
	}
}
