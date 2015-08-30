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
import com.wearit.shike.web.model.dao.helpnumber.HelpNumberDao;
import com.wearit.shike.web.model.dao.helpnumber.HelpNumberDaoImpl;
import com.wearit.shike.web.model.user.HelpNumber;

public class HelpNumberDaoImplTest {

	private static int userId;
	private static String number;
	private static HelpNumber newHelpNum;
	private static ApplicationContext context;
	private static HelpNumberDao daoHelpNum;

	@BeforeClass
	public static void setUpClass() throws Exception {
		userId = 1;
		number = "101010";
		context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		daoHelpNum = (HelpNumberDaoImpl) context.getBean("HelpNumberDaoImpl");

		newHelpNum = new HelpNumber();
		newHelpNum.setAccount_id(userId);
		newHelpNum.setNumber(number);
		newHelpNum.setDescription("Numero di prova test");

	}

	@After
	public void after() {
		try {
			daoHelpNum.delete(userId, number);
			String number = "1515";
			daoHelpNum.delete(userId, number);
		} catch(EmptyResultDataAccessException e) {
			// il numero non è presente se sono qui
		}
	}

	@Test
	public void testGet() throws Exception {
		String number = "1515", description = "Emergenza ambientale";
		HelpNumber testHelpNum = new HelpNumber();
		testHelpNum.setAccount_id(userId);
		testHelpNum.setNumber(number);
		testHelpNum.setDescription(description);
		daoHelpNum.add(userId, testHelpNum);

		HelpNumber helpNum = null;
		try {
			helpNum = daoHelpNum.get(userId, number);
		} catch(EmptyResultDataAccessException e) {
			// HelpNumber non trovato
			fail("Test fallito - HelpNumber non trovato");
		}
		assertNotNull("helpNum != null", helpNum);
		assertEquals(testHelpNum, helpNum);
	}

	@Test
	public void testAdd() throws Exception {
		try {
			daoHelpNum.add(userId, newHelpNum);
			HelpNumber helpNum = daoHelpNum.get(userId, number);
			assertEquals(newHelpNum, helpNum);
		} catch(EmptyResultDataAccessException e) {
			// Il numero non è stato inserito
			fail("Test fallito - Numero non inserito");
		}
	}

	@Test
	public void testDelete() throws Exception {
		daoHelpNum.add(userId, newHelpNum);
		HelpNumber helpNum = null;

		try {
			daoHelpNum.delete(userId, number);
			helpNum = daoHelpNum.get(userId, number);
			fail("Test fallito - HelpNumber non cancellato");
		} catch(EmptyResultDataAccessException e) {
			// Il numero di soccorso è stato cancellato
			// correttamente e quindi non lo trova
			helpNum = null;
		}

		assertNull("helpNum = null", helpNum);
	}

	@Test
	public void testUpdate() throws Exception {
		// Inserisco il numero di test
		daoHelpNum.add(userId, newHelpNum);

		// Modifico i parametri del numero per l'update
		String oldNumber = number;
		number = "102030";
		String newDescription = "Numero di prova test modificato";
		newHelpNum.setNumber(number);
		newHelpNum.setDescription(newDescription);

		HelpNumber helpNum = null;

		try {
			daoHelpNum.update(userId, oldNumber, newHelpNum);
			helpNum = daoHelpNum.get(userId, number);
		} catch(EmptyResultDataAccessException e) {
			// Numero di soccorso non trovato
			fail("Test fallito - Numero non trovato");
		}

		assertNotNull("helpNum != null", helpNum);
		assertEquals(newHelpNum, helpNum);
	}

	@Test
	public void testGetAll() throws Exception {
		// Testo sui numeri di soccorso dell'utente con _id='userId'

		userId = 78;
		newHelpNum.setAccount_id(userId);

		int n = 0;

		List<HelpNumber> list = daoHelpNum.getAll(userId);
		assertEquals("1 list.size() = n", list.size(), n);

		daoHelpNum.add(userId, newHelpNum);
		list = daoHelpNum.getAll(userId);
		assertEquals("2 list.size() = n+1", list.size(), n + 1);

		daoHelpNum.delete(userId, number);
		list = daoHelpNum.getAll(userId);
		assertEquals("3 list.size() = n", list.size(), n);
	}

}
