package com.wearit.shike.web.model.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.wearit.shike.web.model.dao.account.AccountDao;
import com.wearit.shike.web.model.dao.account.AccountDaoImpl;
import com.wearit.shike.web.model.sync.AccountLinkData;
import com.wearit.shike.web.model.user.Account;
import com.wearit.shike.web.model.user.CommonAccount;

public class AccountService {

	private AccountDao accountDao;

	public AccountService() {
		setAccountDao();
	}

	@Autowired
	public void setAccountDao() {
		ApplicationContext context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		accountDao = (AccountDaoImpl) context.getBean("AccountDaoImpl");
		((ClassPathXmlApplicationContext) context).close();
	}

	public int getIdMax() {
		return accountDao.getIdMax();
	}

	public int getIdNext() {
		return accountDao.getIdNext();
	}

	public List<CommonAccount> getAllCommon() {
		return accountDao.getAllCommon();
	}

	public List<CommonAccount> getAllCommon(int limit) {
		return accountDao.getAllCommon(limit);
	}

	public CommonAccount getCommonById(int id) {
		return accountDao.getCommonById(id);
	}

	public CommonAccount getCommonByToken(UUID token) {
		return accountDao.getCommonByToken(token);
	}

	public List<Account> getAllAdmin() {
		return accountDao.getAllAdmin();
	}

	public Account getAccountById(int id) {
		return accountDao.getAccountById(id);
	}

	public void addCommon(CommonAccount comAcc) {
		accountDao.addCommon(comAcc);
	}

	public void addAdmin(Account adminAcc) {
		accountDao.addAdmin(adminAcc);
	}

	public void delete(int id) {
		accountDao.delete(id);
	}

	public void updatePassword(int id, String pass) {
		accountDao.updatePassword(id, pass);
	}

	public void updateUser(int currentId, CommonAccount comAcc) {
		accountDao.updateUser(currentId, comAcc);
	}

	public Account getByUsername(String username) {
		return accountDao.getByUsername(username);
	}

	public AccountLinkData generateNewAccLink() {
		return accountDao.generateNewAccLink();
	}

	public boolean checkCode(int code) {
		return accountDao.checkCode(code);
	}

	public void updateToken(int code, int currentId) {
		accountDao.updateToken(code, currentId);
	}

	public String getToken(int currentId) {
		return accountDao.getToken(currentId);
	}

	public void deleteToken(String token) {
		accountDao.deleteToken(token);
	}

	public int getNewSyncNum(int i) {
		return accountDao.getNewSyncNum(i);
	}
}
