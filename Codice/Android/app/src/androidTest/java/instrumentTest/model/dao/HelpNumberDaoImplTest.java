package instrumentTest.model.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shike.app.model.dao.helpnumber.HelpNumberDao;
import shike.app.model.dao.helpnumber.HelpNumberDaoImpl;
import shike.app.model.user.HelpNumber;

/**
 * Classe di test di HelpNumberDaoImpl
 */
public class HelpNumberDaoImplTest extends DaoBaseTest {

	private HelpNumberDao helpNumberDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		helpNumberDao = new HelpNumberDaoImpl(context);
	}

	public void testMultipleItems() {
		Map<String, HelpNumber> helpNumbers = new HashMap<>();
		int numberCount = randomGenerator.nextInt(1000);

		for (int i = 0; i < numberCount; i++) {
			HelpNumber rnd = randomNumber();
			helpNumbers.put(rnd.getNumber(), rnd);
		}

		numberCount = helpNumbers.size();

		Collection<HelpNumber> helpNumberCol = helpNumbers.values();

		assertEquals(helpNumberDao.add(new ArrayList<>(helpNumberCol), true),
			numberCount);

		List<HelpNumber> dbHelpNumbers = helpNumberDao.getAll();

		// Controlla che tutti i numeri ritornati siano nello stesso numero di quelli aggiunti
		assertEquals(numberCount, dbHelpNumbers.size());

		assertTrue(helpNumberCol.containsAll(dbHelpNumbers) &&
			dbHelpNumbers.containsAll(helpNumberCol));

		assertEquals(numberCount, helpNumberDao.removeAll());
	}

	private HelpNumber randomNumber() {
		return new HelpNumber(randomString(30, StringType.NUMERIC), randomString(100, StringType
			.ALPHANUMERIC));
	}
}
