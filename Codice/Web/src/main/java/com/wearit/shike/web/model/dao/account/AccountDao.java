package com.wearit.shike.web.model.dao.account;

import java.util.List;
import java.util.UUID;
import com.wearit.shike.web.model.sync.AccountLinkData;
import com.wearit.shike.web.model.user.Account;
import com.wearit.shike.web.model.user.CommonAccount;

public interface AccountDao {

	/**
	 * @return id max tabella 'accounts'
	 */
	public int getIdMax();

	/**
	 * @return prossimo id per auto_increment tabella 'account'
	 */
	public int getIdNext();

	/**
	 * @return gli utenti comuni presenti
	 */
	public List<CommonAccount> getAllCommon();

	/**
	 * @param limit
	 *            limita i risultati a quel valore
	 * @return gli utenti comuni presenti
	 */
	public List<CommonAccount> getAllCommon(int limit);

	/**
	 * @param id
	 * @return utente comune con id scelto
	 */
	public CommonAccount getCommonById(int id);

	/**
	 * @param szToken
	 * @return utente comune tramite token
	 */
	public CommonAccount getCommonByToken(UUID uiToken);

	/**
	 * @return la lista degli admin presenti
	 */
	public List<Account> getAllAdmin();

	/**
	 * @param id
	 * @return
	 */
	public Account getAccountById(int id);

	/**
	 * @param comAcc
	 *            account comuni da aggiungere
	 */
	public void addCommon(CommonAccount comAcc);

	/**
	 * @param adminAcc
	 *            account amministratore da aggiungere
	 */
	public void addAdmin(Account adminAcc);

	/**
	 * @param id
	 *            dell'account da eliminare
	 */
	public void delete(int id);

	/**
	 * @param id
	 * @param pass
	 */
	public void updatePassword(int id, String pass);

	/**
	 * @param currentId
	 * @param user
	 */
	public void updateUser(int currentId, CommonAccount comAcc);

	/**
	 * @param username
	 *            dell'utente
	 * @return l'account con l'username selezionata
	 */
	public Account getByUsername(String username);

	/**
	 * @return
	 */
	public AccountLinkData generateNewAccLink();

	/**
	 * @param code
	 * @return true se codice presente
	 */
	public boolean checkCode(int code);

	/**
	 * @param code
	 * @param currentId
	 */
	public void updateToken(int code, int currentId);

	/**
	 * @param currentId
	 * @return
	 */
	public String getToken(int currentId);

	/**
	 * @param token
	 */
	public void deleteToken(String token);
	
	public int getNewSyncNum(int currentId);
}
