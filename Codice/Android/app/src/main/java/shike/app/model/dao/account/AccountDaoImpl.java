package shike.app.model.dao.account;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.UUID;

import shike.app.model.dao.db.AccountTable;
import shike.app.model.dao.db.DbUtil;
import shike.app.model.user.Account;

/**
 * Implementazione di {@link AccountDao}
 */
public class AccountDaoImpl implements AccountDao {

	/**
	 * Riferimento al database dell'applicazione
	 */
	private SQLiteDatabase db;

	/**
	 * Ottiene l'istanza di DbUtil e richiede un riferimento al database in lettura
	 *
	 * @param context contesto, necessario per richiedere l'istanza di DbUtil
	 */
	public AccountDaoImpl(Context context) {
		this.db = DbUtil.getInstance(context).getWritableDatabase();
	}

	@Override
	public Account get() {
		Cursor cur = db.query(AccountTable.TABLE, null, null, null, null, null, null);
		int count = cur.getCount();

		if (count == 1) {

			cur.moveToNext();

			Account account =
				new Account(cur.getInt(cur.getColumnIndex(AccountTable._ID)));

			account.setFirstName(cur.getString(cur.getColumnIndex(AccountTable
				.FIRST_NAME)));
			account.setLastName(cur.getString(cur.getColumnIndex(AccountTable
				.LAST_NAME)));
			account.setConnectionToken(UUID.fromString(cur.getString(cur.getColumnIndex(
				AccountTable.CONNECTION_TOKEN))));
			account.setHeight(cur.getInt(cur.getColumnIndex(AccountTable.HEIGHT)));
			account.setWeight(cur.getDouble(cur.getColumnIndex(AccountTable.WEIGHT)));
			account
				.setBirthDate(new Date(cur.getLong(cur.getColumnIndex(AccountTable.BIRTH_DATE))));
			account.setGender(
				Account.Gender.getById(cur.getInt(cur.getColumnIndex(AccountTable.GENDER_ID))));
			cur.close();
			return account;
		}

		cur.close();
		return null;
	}

	/**
	 * Elimina l'account presente nel db e ne inserisce uno nuovo
	 *
	 * @param account il nuovo account da inserire al posto di quello presente nel db
	 * @return true se l'operazione è avvenuta con successo, false se è fallita
	 */
	@Override
	public boolean set(Account account) {

		ContentValues content = new ContentValues();
		content.put(AccountTable._ID, account.get_id());
		content.put(AccountTable.CONNECTION_TOKEN, account.getConnectionToken()
			.toString());
		content.put(AccountTable.FIRST_NAME, account.getFirstName());
		content.put(AccountTable.LAST_NAME, account.getLastName());
		content.put(AccountTable.HEIGHT, account.getHeight());
		content.put(AccountTable.WEIGHT, account.getWeight());
		content.put(AccountTable.GENDER_ID, account.getGender().getId());
		content.put(AccountTable.BIRTH_DATE, account.getBirthDate().getTime());

		Cursor cur = db.query(AccountTable.TABLE, null, null, null, null, null, null);
		int count = cur.getCount();
		cur.close();
		if (count == 1) { // Se c'è un solo record nella tabella viene fatto un UPDATE
			return db.update(AccountTable.TABLE, content, null, null) > -1;
		} else { // Se i record sono 0 o più di 1, la tabella viene svuotata e viene fatto un
			// INSERT
			remove();
			return db.insert(AccountTable.TABLE, null, content) > -1;
		}
	}

	@Override
	public boolean remove() {
		return db.delete(AccountTable.TABLE, "1", null) > 0;
	}

	@Override
	public UUID getConnectionToken() {
		Cursor cur =
			db.query(AccountTable.TABLE, new String[] {AccountTable.CONNECTION_TOKEN}, null, null,
				null, null, null);

		if (cur.getCount() == 1) {
			cur.moveToNext();
			String token = cur.getString(cur.getColumnIndex(AccountTable.CONNECTION_TOKEN));
			if (token != null && token.length() == 36) {
				return UUID.fromString(token);
			}
		}
		return null;
	}
}
