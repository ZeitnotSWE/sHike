package com.wearit.shike.web.test.controller;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.model;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.wearit.shike.web.controller.AuthController;
import com.wearit.shike.web.model.service.AccountService;

public class AuthControllerTest {

	@Autowired
	WebApplicationContext wac;
	@Autowired
	MockHttpSession session;
	@Autowired
	MockHttpServletRequest request;

	private static MockMvc mockMvc;

	@BeforeClass
	public static void setUpClass() throws Exception {
		AccountService accountService = new AccountService();
		AuthController auth = new AuthController();
		auth.setAccountService(accountService);
		mockMvc = MockMvcBuilders.standaloneSetup(auth).build();
	}

	@Test
	public void testLogin() throws Exception {
		// Test con error = true
		mockMvc.perform(get("/login/?error=true")).andExpect(model().attributeExists("error"));

		// Test con logout = true
		mockMvc.perform(get("/login/?logout=true")).andExpect(model().attributeExists("msg"));

		// Test login
		mockMvc.perform(get("/login/")).andExpect(forwardedUrl("./account/Login"));
	}

	@Test
	public void testResetPassword() throws Exception {
		String email = "prova@prova.com";

		mockMvc.perform(get("/reset-password/")).andExpect(forwardedUrl("./account/ResetPassword"));

		mockMvc.perform(post("/reset-password/").param("email", email))
				.andExpect(model().attributeExists("msg")).andExpect(forwardedUrl("./inc/Message"));
	}

}
