package shike.app.model.dao.helpnumber;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import shike.app.model.dao.db.DbUtil;
import shike.app.model.dao.db.HelpNumbersTable;
import shike.app.model.user.HelpNumber;

/**
 * Implementazione di {@link HelpNumberDao}
 */
public class HelpNumberDaoImpl implements HelpNumberDao {

	private SQLiteDatabase db;

	public HelpNumberDaoImpl(Context context) {
		db = DbUtil.getInstance(context).getWritableDatabase();
	}

	@Override
	public List<HelpNumber> getAll() {
		Cursor cur =
			db.query(HelpNumbersTable.TABLE, null, null, null, null, null, null);

		List<HelpNumber> numbers = new ArrayList<>();

		while (cur.moveToNext()) {
			numbers.add(new HelpNumber(
				cur.getString(cur.getColumnIndex(HelpNumbersTable.NUMBER)),
				cur.getString(cur.getColumnIndex(HelpNumbersTable.NAME))
			));
		}

		cur.close();

		return numbers;
	}

	@Override
	public int add(List<HelpNumber> numbers, boolean truncateFirst) {

		if (truncateFirst) {
			removeAll();
		}

		int rows = 0;

		// Eseguire più query in una sola transazione permette migliori performance, in SQLite
		db.beginTransaction();
		for (HelpNumber number : numbers) {
			ContentValues content = new ContentValues();
			content.put(HelpNumbersTable.NUMBER, number.getNumber());
			content.put(HelpNumbersTable.NAME, number.getName());

			if (db.replace(HelpNumbersTable.TABLE, null, content) > -1) {
				rows++;
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();

		return rows;
	}

	@Override
	public int removeAll() {
		return db.delete(HelpNumbersTable.TABLE, "1", null);
	}
}
