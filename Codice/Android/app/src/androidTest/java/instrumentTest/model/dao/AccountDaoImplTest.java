package instrumentTest.model.dao;

import java.util.Date;
import java.util.UUID;

import shike.app.model.dao.account.AccountDao;
import shike.app.model.dao.account.AccountDaoImpl;
import shike.app.model.user.Account;

/**
 * Classe di test di AccountDaoImpl
 */
public class AccountDaoImplTest extends DaoBaseTest {
	private Account.Gender[] genders = Account.Gender.values();

	private AccountDao accountDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		accountDao = new AccountDaoImpl(context);
	}

	/**
	 * Testa tutti i metodi della classe AccountDaoImpl
	 */
	public void testAllMethods() {

		Account testAccount1 = randomAccount();
		Account testAccount2 = randomAccount();

		assertTrue(accountDao.set(testAccount1));
		assertTrue(accountDao.set(testAccount2));

		Account dbAccount = accountDao.get();

		boolean eq = testAccount1.equals(testAccount2);

		assertEquals(testAccount2, dbAccount);

		assertTrue(accountDao.remove());

		assertNull(accountDao.get());

	}

	/**
	 * Genera una account casuale
	 *
	 * @return account di test casuale
	 */
	private Account randomAccount() {
		Account testAccount = new Account(Math.abs(randomGenerator.nextInt() + 1));
		testAccount
			.setGender(genders[randomGenerator.nextInt(genders.length)]);
		testAccount.setFirstName(randomString(50, StringType.ALPHANUMERIC));
		testAccount.setLastName(randomString(50, StringType.ALPHANUMERIC));
		testAccount.setConnectionToken(UUID.randomUUID());
		testAccount.setWeight(randomGenerator.nextDouble());
		testAccount.setBirthDate(new Date(randomGenerator.nextLong()));
		testAccount.setHeight(randomGenerator.nextInt(250));
		return testAccount;
	}

}
