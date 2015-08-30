package instrumentTest.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.test.AndroidTestCase;

import java.security.SecureRandom;
import java.util.Random;

import shike.app.model.dao.db.DbContract;
import shike.app.model.dao.db.DbUtil;

/**
 * Superclasse di tutti i test del package shike.app.model.dao
 */
public abstract class DaoBaseTest extends AndroidTestCase {

	protected Random randomGenerator = new SecureRandom();
	protected Context context;
	private SQLiteDatabase db;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		context = getContext();
		context.deleteDatabase(DbContract.DATABASE_NAME);
		DbUtil dbUtil = DbUtil.getInstance(context);
		db = dbUtil.getReadableDatabase();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		db.close();
		db = null;
	}

	/**
	 * Verifica le condizioni iniziali dei test (verifica che il db non sia null dopo il setup
	 */
	public void testPreConditions() {
		assertNotNull(db);
	}

	/**
	 * Ritorna una stringa alfanumerica casuale con un determinata lunghezza massima
	 *
	 * @param maxLength lunghezza massima della stringa
	 * @return stringa casuale creata
	 */
	protected String randomString(int maxLength, StringType stringType) {
		String randomStringChars = stringType.chars;
		int length = randomGenerator.nextInt(maxLength) + 1;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int index = randomGenerator.nextInt(randomStringChars.length());
			builder.append(randomStringChars.charAt(index));
		}
		return builder.toString();
	}

	/**
	 * Ritorna una posizione casuale
	 *
	 * @return posizione casuale
	 */
	protected Location randomLocation() {
		Location location = new Location("DaoBaseTest");
		location.setLatitude(randomGenerator.nextDouble() * 90);
		location.setLongitude(randomGenerator.nextDouble() * 90);
		location.setAltitude(randomGenerator.nextDouble() * 3000);

		return location;
	}

	protected enum StringType {
		ALPHA("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ "),
		NUMERIC("0123456789 "),
		ALPHANUMERIC("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ");

		private String chars;

		StringType(String chars) {
			this.chars = chars;
		}
	}

}
