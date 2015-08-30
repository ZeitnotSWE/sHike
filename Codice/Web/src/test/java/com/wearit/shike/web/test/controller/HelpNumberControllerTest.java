package com.wearit.shike.web.test.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.model;
import java.util.Random;
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
import com.wearit.shike.web.controller.HelpNumberController;
import com.wearit.shike.web.helper.validator.HelpNumberValidator;
import com.wearit.shike.web.model.dao.helpnumber.HelpNumberDao;
import com.wearit.shike.web.model.dao.helpnumber.HelpNumberDaoImpl;
import com.wearit.shike.web.model.service.HelpNumberService;
import com.wearit.shike.web.model.user.HelpNumber;

public class HelpNumberControllerTest {

	@Autowired
	WebApplicationContext wac;
	@Autowired
	MockHttpSession session;
	@Autowired
	MockHttpServletRequest request;

	private static MockMvc mockMvc;

	private static int id_user;
	private static String number;
	private static String newNumber;
	private static HelpNumber newHn;
	private static ApplicationContext context;
	private static HelpNumberDao numberDao;

	@BeforeClass
	public static void setUpClass() throws Exception {
		id_user = 1;

		HelpNumberService hlpNumServ = new HelpNumberService();
		HelpNumberController hlpNum = new HelpNumberController(new HelpNumberValidator(), id_user);
		hlpNum.setHelpNumberService(hlpNumServ);

		mockMvc = MockMvcBuilders.standaloneSetup(hlpNum).build();
		Random randomGenerator = new Random();
		number = Integer.toString(randomGenerator.nextInt(1000000));
		newNumber = Integer.toString(randomGenerator.nextInt(1000000));
		context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		numberDao = (HelpNumberDaoImpl) context.getBean("HelpNumberDaoImpl");
		newHn = new HelpNumber();
		newHn.setDescription("desc");
		newHn.setNumber(number);
	}

	@After
	public void after() {
		try {
			numberDao.delete(id_user, number);
		} catch(EmptyResultDataAccessException e) {
			// il numero non è presente se sono qui
		}
	}

	@Test
	public void testListHelpNumber() throws Exception {
		mockMvc.perform(get("/user/helpnumbers/"))
				.andExpect(model().attributeExists("listaHelpNum"))
				.andExpect(forwardedUrl("./account/user/ListHelpNumbers"));
	}

	@Test
	public void testAddHelpNumber() throws Exception {
		mockMvc.perform(get("/user/helpnumbers/add/")).andExpect(model().attributeExists("hnumb"))
				.andExpect(forwardedUrl("./account/user/AddHelpNumber"));

		mockMvc.perform(
				post("/user/helpnumbers/add/").param("account_id", Integer.toString(id_user))
						.param("description", "desc").param("number", number))
				.andExpect(model().attributeExists("msg")).andExpect(forwardedUrl("./inc/Message"))
				.andReturn();

		HelpNumber test = null;
		try {
			test = numberDao.get(id_user, number);
		} catch(EmptyResultDataAccessException e) {
			fail("Test fallito - HelpNumber non inserito");
		}

		assertNotNull(test);

		mockMvc.perform(
				post("/user/helpnumbers/add/").param("account_id", Integer.toString(id_user))
						.param("description", "").param("number", number + 1))
				.andExpect(forwardedUrl("./account/user/AddHelpNumber")).andReturn();
		test = null;
		try {
			test = numberDao.get(id_user, number + 1);
			fail("Test fallito - Inserimento non valido riuscito");
		} catch(EmptyResultDataAccessException e) {
			// se sono qui l'inserimento è fallito com'è giusto che sia
			// visto che la descrizione è vuota
		}
		assertNull(test);
	}

	@Test
	public void testEditHelpNumber() throws Exception {
		try {
			numberDao.get(id_user, number);
		} catch(EmptyResultDataAccessException e) {
			numberDao.add(id_user, newHn);
		}

		mockMvc.perform(get("/user/helpnumbers/edit/" + number))
				.andExpect(model().attributeExists("numbOld"))
				.andExpect(model().attributeExists("hnumb"))
				.andExpect(forwardedUrl("./account/user/EditHelpNumber"));

		mockMvc.perform(
				post("/user/helpnumbers/edit/" + number)
						.param("account_id", Integer.toString(id_user))
						.param("description", "nuovaDesc").param("number", newNumber)
						.param("numOld", number)).andExpect(model().attributeExists("msg"))
				.andExpect(forwardedUrl("./inc/Message")).andReturn();

		HelpNumber test = null;
		try {
			// Cerco il numero modificato
			test = numberDao.get(id_user, newNumber);
		} catch(EmptyResultDataAccessException e) {
			// il numero non è stato modificato se sono qui
			fail("Test fallito - Numero non modificato");
		}
		assertNotNull(test);
	}

	@Test
	public void testDeleteHelpNumber() throws Exception {

		try {
			numberDao.get(id_user, number);
		} catch(EmptyResultDataAccessException e) {
			numberDao.add(id_user, newHn);
		}

		mockMvc.perform(get("/user/helpnumbers/delete/" + number))
				.andExpect(model().attributeExists("msg")).andExpect(forwardedUrl("./inc/Message"))
				.andReturn();

		HelpNumber test = null;
		try {
			test = numberDao.get(id_user, number);
			fail("Test fallito - Numero non cancellato");
		} catch(EmptyResultDataAccessException e) {
			// il numero è stato cancellato correttamente se sono qui
		}

		assertNull(test);

	}

}
