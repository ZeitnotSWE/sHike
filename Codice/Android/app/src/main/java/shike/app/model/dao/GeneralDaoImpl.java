package shike.app.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import shike.app.model.dao.db.AccountTable;
import shike.app.model.dao.db.DbUtil;
import shike.app.model.dao.db.HelpNumbersTable;
import shike.app.model.dao.db.PoisTable;
import shike.app.model.dao.db.RecordedTracksTable;
import shike.app.model.dao.db.VirtualTracksTable;

/**
 * Implementazione di GeneralDao
 */
public class GeneralDaoImpl implements GeneralDao {
	private SQLiteDatabase db;

	public GeneralDaoImpl(Context context) {
		db = DbUtil.getInstance(context).getWritableDatabase();
	}

	@Override
	public int resetAll() {
		int rows = 0;

		db.beginTransaction();

		rows += db.delete(AccountTable.TABLE, null, null);
		rows += db.delete(HelpNumbersTable.TABLE, null, null);
		rows += db.delete(PoisTable.TABLE, null, null);
		rows += db.delete(RecordedTracksTable.TABLE, null, null);
		rows += db.delete(VirtualTracksTable.TABLE, null, null);

		db.setTransactionSuccessful();
		db.endTransaction();

		return rows;
	}
}
