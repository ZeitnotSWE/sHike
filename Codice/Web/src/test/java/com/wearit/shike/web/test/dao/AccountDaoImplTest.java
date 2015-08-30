package com.wearit.shike.web.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.wearit.shike.web.model.dao.account.AccountDao;
import com.wearit.shike.web.model.dao.account.AccountDaoImpl;
import com.wearit.shike.web.model.sync.AccountLinkData;
import com.wearit.shike.web.model.user.Account;
import com.wearit.shike.web.model.user.CommonAccount;

public class AccountDaoImplTest {
	private static ApplicationContext context;
	private static AccountDao daoAccount;
	private static Account newAcc;
	private static CommonAccount newComAcc;
	private static int id;
	private UUID token;
	private String tokenS;

	@BeforeClass
	public static void setUpClass() throws Exception {
		// Id Account per test
		id = 1;

		context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		daoAccount = (AccountDaoImpl) context.getBean("AccountDaoImpl");

		newAcc = new Account();
		newAcc.setEmailAddress("adminEmail");
		newAcc.setPasswordHash("prova");

		newComAcc = new CommonAccount();
		newComAcc.setEmailAddress("userTest");
		newComAcc.setPasswordHash("prova");
		newComAcc.setRole("ROLE_USER");
		newComAcc.setSyncCount(0);
		newComAcc.setFirstName("Test1");
		newComAcc.setLastName("Test1");
		newComAcc.setWeight(0);
		newComAcc.setHeight(0);
		Date date = new Date(1233425646);
		newComAcc.setBirthDate(date);
		newComAcc.setGender(1);

	}

	@After
	public void after() {
		try {
			// Cerco ultimo account inserito
			int id = daoAccount.getIdNext() - 1;
			// e lo elimino
			daoAccount.delete(id);

			// Elimino il nuovo token se presente
			daoAccount.deleteToken(tokenS);
		} catch(EmptyResultDataAccessException e) {
			// account non presente se sono qui
		}
	}

	@Test
	public void testGetAllCommon() throws Exception {
		int nComAccount = 9;
		List<CommonAccount> list = daoAccount.getAllCommon();
		assertEquals("La lista dei common account deve avere " + nComAccount + " account",
				nComAccount, list.size());

		int nComLim = 5;
		List<CommonAccount> listLim = daoAccount.getAllCommon(nComLim);
		assertEquals("La lista limitata dei common account deve avere " + nComLim + " account",
				nComLim, listLim.size());
	}

	@Test
	public void testGetAllAdmin() throws Exception {
		int nAdmAccount = 2;
		List<Account> list = daoAccount.getAllAdmin();
		assertEquals("La lista degli admin account deve avere " + nAdmAccount + " account",
				nAdmAccount, list.size());
	}

	@Test
	public void testAddAdmin() throws Exception {
		int id = daoAccount.getIdNext();
		newAcc.set_id(id);

		Account acc = null;

		try {
			daoAccount.addAdmin(newAcc);
			acc = daoAccount.getAccountById(id);
		} catch(EmptyResultDataAccessException e) {
			// l'account non è stato inserito
			fail("Test fallito - Account non inserito");
		}

		assertNotNull(acc);
		assertEquals("L'account è stato inserito con successo", newAcc, acc);
	}

	@Test
	public void testAddCommon() throws Exception {
		int id = daoAccount.getIdNext();
		newComAcc.set_id(id);

		CommonAccount acc = null;

		try {
			daoAccount.addCommon(newComAcc);
			acc = daoAccount.getCommonById(id);
		} catch(EmptyResultDataAccessException e) {
			// l'account non è stato inserito
			fail("Test fallito - Account non inserito");
		}

		assertNotNull(acc);
		assertEquals("newComAcc = acc", newComAcc, acc);

	}

	@Test
	public void testDelete() throws Exception {
		int id = daoAccount.getIdNext();
		newAcc.set_id(id);

		daoAccount.addAdmin(newAcc);

		try {
			daoAccount.delete(id);
			newAcc = daoAccount.getAccountById(id);
			fail("Test fallito - Account non cancellato");
		} catch(EmptyResultDataAccessException e) {
			// Account eliminato
			newAcc = null;
		}

		assertNull("acc = null", newAcc);
	}

	@Test
	public void testGetAccountById() throws Exception {
		Account testAcc = new Account();
		testAcc.set_id(id);
		testAcc.setEmailAddress("userTest@email.it");
		testAcc.setRole("ROLE_USER");
		Account acc = null;
		try {
			acc = daoAccount.getAccountById(id);
		} catch(EmptyResultDataAccessException e) {
			// l'account non è stato trovato
			fail("Test fallito - Account non trovato");
		}
		assertNotNull(acc);
		assertEquals(testAcc, acc);
	}

	@Test
	public void testGetCommonById() throws Exception {
		int id = daoAccount.getIdNext();
		newComAcc.set_id(id);
		daoAccount.addCommon(newComAcc);

		CommonAccount acc = null;
		try {
			acc = daoAccount.getCommonById(id);
		} catch(EmptyResultDataAccessException e) {
			// l'account non è stato inserito
			fail("Test fallito - Account non trovato");
		}
		assertNotNull(acc);
		assertEquals("getCommonById funziona correttamente", newComAcc, acc);
	}

	@Test
	public void testGetCommonByToken() throws Exception {
		int id = daoAccount.getIdNext();
		daoAccount.addCommon(newComAcc);
		AccountLinkData accLink = daoAccount.generateNewAccLink();

		String codeS = accLink.getOneTimeCode();
		int code = Integer.parseInt(codeS);
		daoAccount.updateToken(code, id);
		token = accLink.getConnectionToken();
		tokenS = token.toString();

		CommonAccount acc = null;
		try {
			acc = daoAccount.getCommonByToken(token);
		} catch(EmptyResultDataAccessException e) {
			// l'account non è stato inserito
			fail("Test fallito - Account non trovato");
		}
		assertNotNull(acc);
		assertEquals(newComAcc, acc);
	}

	@Test
	public void testGetByUsername() throws Exception {
		String username = "userTest@email.it";
		Account acc = null;
		try {
			acc = daoAccount.getByUsername(username);
		} catch(EmptyResultDataAccessException e) {
			// l'account non è presente
			fail("Test fallito - Account non presente");
		}

		assertNotNull(acc);
		assertEquals("acc.getEmailAddress() = username", acc.getEmailAddress(), username);
	}

	@Test
	public void testUpdatePassword() throws Exception {
		String oldPass = "prova", password = "provaPass";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);

		Account acc = null;
		try {
			daoAccount.updatePassword(id, password);
			acc = daoAccount.getAccountById(id);

		} catch(EmptyResultDataAccessException e) {
			// l'account non è presente
			fail("Test fallito - Account non presente");
		}

		assertNotNull("acc != null", acc);
		assertTrue("acc.getPasswordHash() = hashedPassword",
				!BCrypt.checkpw(acc.getPasswordHash(), hashedPassword));

		// Ritorno allo stato precedente

		try {
			daoAccount.updatePassword(id, oldPass);
		} catch(EmptyResultDataAccessException e) {
			// l'account non è presente
			fail("Test fallito - Impossibile riportare allo stato precedente");
		}
	}

	@Test
	public void testUpdateUser() throws Exception {
		int id = daoAccount.getIdNext();
		newComAcc.set_id(id);
		daoAccount.addCommon(newComAcc);

		newComAcc.setFirstName("Test2");

		try {
			daoAccount.updateUser(id, newComAcc);
			CommonAccount acc = daoAccount.getCommonById(id);
			assertEquals("acc = newComAcc", acc, newComAcc);
		} catch(EmptyResultDataAccessException e) {
			// l'account non è presente
			fail("Test fallito - Account non presente");
		}

	}
}
