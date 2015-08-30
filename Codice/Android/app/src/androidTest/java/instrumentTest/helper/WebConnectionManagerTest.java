package instrumentTest.helper;

import android.test.AndroidTestCase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import instrumentTest.model.dao.PerformanceDaoImplTest;
import shike.app.helper.WebConnectionManager;
import shike.app.helper.json.JsonConverter;
import shike.app.model.dao.account.AccountDao;
import shike.app.model.dao.account.AccountDaoImpl;
import shike.app.model.dao.performance.PerformanceDao;
import shike.app.model.dao.performance.PerformanceDaoImpl;
import shike.app.model.session.performance.Performance;
import shike.app.model.sync.AccountLinkData;
import shike.app.model.user.Account;

/**
 * Classe di test dei metodi di {@link WebConnectionManager}
 */
public class WebConnectionManagerTest extends AndroidTestCase {
	AccountDao accountDao;
	PerformanceDao performanceDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		accountDao = new AccountDaoImpl(mContext);
		performanceDao = new PerformanceDaoImpl(mContext);
		WebConnectionManager.setConnUrl("http://www.shike.it/provasync/conn.php");
		WebConnectionManager.setSyncUrl("http://www.shike.it/provasync/sync.php");
	}

	public void testSync() throws Exception {
		// Creazione dati di test
		accountDao.remove();
		UUID testToken = UUID.fromString("790b9ab1-5c5c-4579-8c70-b9f433fcfbf8");
		Account tempAccount = Account.createTempAccount(testToken);
		accountDao.set(tempAccount);
		List<Performance> randPerformance = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			randPerformance.add(new PerformanceDaoImplTest().randomPerformance());
		}
		performanceDao.add(randPerformance, true);

		// Sincronizzazione
		WebConnectionManager.Result result = WebConnectionManager.sync(mContext);

		assertEquals(WebConnectionManager.Result.SUCCESS, result);

	}

	public void testFailedSync() {
		// Creazione dati di test
		accountDao.remove();
		UUID testToken = new UUID(0, 0); // token errato
		Account tempAccount = Account.createTempAccount(testToken);
		accountDao.set(tempAccount);
		List<Performance> randPerformance = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			randPerformance.add(new PerformanceDaoImplTest().randomPerformance());
		}
		performanceDao.add(randPerformance, true);

		// Sincronizzazione
		WebConnectionManager.Result result = WebConnectionManager.sync(mContext);
		assertEquals(WebConnectionManager.Result.UNLINKED, result);

	}

	public void testFirstConnection() throws Exception {
		accountDao.remove();

		String response = WebConnectionManager.linkAccount(mContext);

		String expected = assetToString("account_link_test_output.json");
		AccountLinkData data = JsonConverter.convertToAccountLinkData(expected);


		assertEquals(data.getOneTimeCode(), response);

		UUID token = new AccountDaoImpl(mContext).getConnectionToken();

		assertEquals(data.getConnectionToken(), token);

		accountDao.remove();

	}

	private String assetToString(String assetName) throws IOException {
		InputStream is = mContext.getAssets().open(assetName);
		StringBuilder builder = new StringBuilder();
		Scanner scanner = new Scanner(is).useDelimiter("\\z");
		while (scanner.hasNext()) {
			builder.append(scanner.next());
		}
		return builder.toString();
	}
}
