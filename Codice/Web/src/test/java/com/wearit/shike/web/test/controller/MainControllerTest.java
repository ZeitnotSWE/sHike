package com.wearit.shike.web.test.controller;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.model;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import com.wearit.shike.web.controller.MainController;

public class MainControllerTest {
	private static MockMvc mockMvc;

	@BeforeClass
	public static void setUpClass() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(new MainController()).build();
	}

	@Test
	public void testHome() throws Exception {
		mockMvc.perform(get("/")).andExpect(forwardedUrl("Home"));
	}

	@Test
	public void testAccessDenied() throws Exception {
		mockMvc.perform(get("/403")).andExpect(forwardedUrl("inc/Message"))
				.andExpect(model().attributeExists("msg"));
	}

	@Test
	public void testNotFound() throws Exception {
		mockMvc.perform(get("/404")).andExpect(forwardedUrl("inc/Message"))
				.andExpect(model().attributeExists("msg"));
	}

}
