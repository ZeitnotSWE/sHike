package shike.app.model.service;

import android.content.Context;

import java.util.UUID;

import shike.app.model.dao.account.AccountDao;
import shike.app.model.dao.account.AccountDaoImpl;
import shike.app.model.user.Account;

/**
 * Classe service che fa da ponte tra i presenter e il DAO dell'account
 */
public class AccountService {

	private final AccountDao accountDao;

	public AccountService(Context context) {
		accountDao = new AccountDaoImpl(context);
	}

	public Account get() {
		return accountDao.get();
	}

	public boolean set(Account account) {
		return accountDao.set(account);
	}

	public boolean remove() {
		return accountDao.remove();
	}

	public UUID getConnectionToken() {
		return accountDao.getConnectionToken();
	}

}
