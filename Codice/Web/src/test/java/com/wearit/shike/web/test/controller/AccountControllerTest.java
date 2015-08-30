package com.wearit.shike.web.test.controller;

import static org.junit.Assert.assertNotNull;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import com.wearit.shike.web.controller.AccountController;
import com.wearit.shike.web.helper.validator.CommonAccountValidator;
import com.wearit.shike.web.model.dao.account.AccountDao;
import com.wearit.shike.web.model.dao.account.AccountDaoImpl;
import com.wearit.shike.web.model.service.AccountService;
import com.wearit.shike.web.model.user.Account;
import com.wearit.shike.web.model.user.CommonAccount;

public class AccountControllerTest {

	private static MockMvc mockMvc;

	private static ApplicationContext context;
	private static AccountDao accountDao;
	private static Account newAcc;
	private static CommonAccount newComAcc;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private static Date date;
	private static long weight;
	private static int id, sync, heigth, gender;
	private static String sDate, sWeight, sHeigth, sGender;
	private static String email, password, passwordHash, role, firstName, lastName;

	@BeforeClass
	public static void setUpClass() throws Exception {
		// Valori per il test
		email = "emailTest";
		password = "provaprova123";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		passwordHash = passwordEncoder.encode(password);
		role = "ROLE_USER";

		long dateL = 1233425646;
		date = new Date(dateL);
		sync = 0;
		firstName = "Test1";
		lastName = "Test1";
		weight = 0;
		heigth = 0;
		gender = 1;

		// Trasformo tutti i parametri in stringhe
		sDate = "21/05/2015";
		sWeight = Long.toString(weight);
		sHeigth = Integer.toString(heigth);
		sGender = Integer.toString(gender);

		// Setto poi per il test
		context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		accountDao = (AccountDaoImpl) context.getBean("AccountDaoImpl");
		id = accountDao.getIdNext();

		newAcc = new Account();
		newAcc.set_id(id);
		newAcc.setEmailAddress(email);
		newAcc.setPasswordHash(passwordHash);

		newComAcc = new CommonAccount();
		newComAcc.setEmailAddress(email);
		newComAcc.setPasswordHash(passwordHash);
		newComAcc.setRole(role);
		newComAcc.setSyncCount(sync);
		newComAcc.setFirstName(firstName);
		newComAcc.setLastName(lastName);
		newComAcc.setWeight(weight);
		newComAcc.setHeight(heigth);
		newComAcc.setBirthDate(date);
		newComAcc.setGender(gender);

		AccountService accSer = new AccountService();
		AccountController accCon = new AccountController(new CommonAccountValidator(), newAcc);
		accCon.setAccountService(accSer);

		mockMvc = MockMvcBuilders.standaloneSetup(accCon).build();
	}

	@After
	public void after() {
		try {
			// Cerco ultimo account inserito
			id = accountDao.getIdNext() - 1;
			// e lo elimino
			accountDao.delete(id);
		} catch(EmptyResultDataAccessException e) {
			// account non presente se sono qui
		}
	}

	@Test
	public void testAddAccount() throws Exception {
		id = accountDao.getIdNext();

		mockMvc.perform(get("/signup/")).andExpect(forwardedUrl("./account/AddCommonAccount"))
				.andExpect(model().attributeExists("account"));

		mockMvc.perform(
				post("/signup/").param("emailAddress", email).param("passwordHash", password)
						.param("passwordHashRepeat", password).param("firstName", firstName)
						.param("lastName", lastName).param("gender", sGender)
						.param("height", sHeigth).param("weight", sWeight)
						.param("birthDate", sDate)).andExpect(status().isOk())
				.andExpect(forwardedUrl("./inc/Message")).andReturn();

		Account test = null;
		try {
			test = accountDao.getAccountById(id);
		} catch(EmptyResultDataAccessException e) {
			// se sono qui l'inserimento è fallito
			fail("Test fallito - Account non aggiunto");
		}
		assertNotNull(test);
	}

	@Test
	public void testDelAccount() throws Exception {
		id = accountDao.getIdNext();
		accountDao.addAdmin(newAcc);
		mockMvc.perform(get("/admin/accounts/del/" + id)).andExpect(forwardedUrl("./inc/Message"))
				.andExpect(model().attributeExists("msg")).andReturn();

		Account test;
		try {
			test = accountDao.getAccountById(id);
			fail("Test fallito - Account non cancellato");
		} catch(EmptyResultDataAccessException e) {
			// l'account è stato cancellato correttamente se sono qui
			test = null;
		}
		assertNull(test);
	}

	@Test
	public void testEditUser() throws Exception {
		id = accountDao.getIdNext();
		newAcc.set_id(id);

		AccountService accSer = new AccountService();
		AccountController accCon = new AccountController(new CommonAccountValidator(), newAcc);
		accCon.setAccountService(accSer);

		mockMvc = MockMvcBuilders.standaloneSetup(accCon).build();
		accountDao.addCommon(newComAcc);

		mockMvc.perform(get("/user/edit/")).andExpect(forwardedUrl("./account/user/EditUser"))
				.andExpect(model().attributeExists("account"));

		mockMvc.perform(
				post("/user/edit/").param("firstName", firstName).param("lastName", lastName)
						.param("gender", sGender).param("height", sHeigth).param("weight", sWeight)
						.param("birthDate", sDate).param("passwordHash", password))
				.andExpect(forwardedUrl("./inc/Message")).andExpect(model().attributeExists("msg"))
				.andReturn();

		Account test = null;
		try {
			test = accountDao.getAccountById(id);
		} catch(EmptyResultDataAccessException e) {
			// se sono qui l'inserimento è fallito
			fail("Test fallito - Account non modificato");
		}
		assertNotNull(test);
	}

	@Test
	public void testEditPass() throws Exception {
		id = accountDao.getIdNext();
		newAcc.set_id(id);

		AccountService accSer = new AccountService();
		AccountController accCon = new AccountController(new CommonAccountValidator(), newAcc);
		accCon.setAccountService(accSer);

		mockMvc = MockMvcBuilders.standaloneSetup(accCon).build();

		accountDao.addCommon(newComAcc);

		mockMvc.perform(get("/user/edit-password/"))
				.andExpect(forwardedUrl("./account/EditPassword"))
				.andExpect(model().attributeExists("account"));

		mockMvc.perform(
				post("/user/edit-password/").param("password", password)
						.param("passwordHash", "upPass123")
						.param("passwordHashRepeat", "upPass123"))
				.andExpect(forwardedUrl("./inc/Message")).andExpect(model().attributeExists("msg"))
				.andReturn();

		Account test = null;
		try {
			test = accountDao.getAccountById(id);
		} catch(EmptyResultDataAccessException e) {
			// se sono qui l'inserimento è fallito
			fail("Test fallito - Account non modificato");
		}
		assertNotNull(test);
	}

	@Test
	public void testCreateList() throws Exception {
		mockMvc.perform(get("/admin/accounts/"))
				.andExpect(forwardedUrl("./account/admin/ListCommonAccount"))
				.andExpect(model().attributeExists("listaUser")).andReturn();
	}

}
