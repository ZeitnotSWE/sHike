package com.wearit.shike.web.model.dao.account;

import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.wearit.shike.web.model.sync.AccountLinkData;
import com.wearit.shike.web.model.user.Account;
import com.wearit.shike.web.model.user.CommonAccount;

public class AccountDaoImpl implements AccountDao {

	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int getIdMax() {
		String sqlAcc = "SELECT IFNULL(MAX(_id),0) AS _id FROM accounts";
		return (Integer) jdbcTemplateObject.queryForObject(sqlAcc, Integer.class);
	}

	@Override
	public int getIdNext() {
		String sqlAcc = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES "
				+ "WHERE TABLE_NAME = 'accounts'";
		return (Integer) jdbcTemplateObject.queryForObject(sqlAcc, Integer.class);
	}

	@Override
	public List<CommonAccount> getAllCommon(int limit) {
		String sql = "SELECT * FROM commonaccounts NATURAL JOIN accounts";
		if(limit > 0)
			sql += " ORDER BY _id DESC LIMIT " + limit;
		List<CommonAccount> comaccounts = jdbcTemplateObject.query(sql, new CommonAccountMapper());
		return comaccounts;
	}

	@Override
	public List<CommonAccount> getAllCommon() {
		return this.getAllCommon(0);
	}

	public CommonAccount getCommonById(int id) {
		String sql = "SELECT * FROM commonaccounts NATURAL JOIN accounts WHERE _id = ?";
		CommonAccount user = jdbcTemplateObject.queryForObject(sql, new Object[] { id },
				new CommonAccountMapper());
		return user;
	}

	public CommonAccount getCommonByToken(UUID uiToken) {
		String szt = uiToken.toString();
		String sql = "SELECT ca.*, a.* FROM tokens t JOIN commonaccounts ca ON (t.account_id = ca._id) NATURAL JOIN accounts a WHERE t.token = ?;";
		try {
			CommonAccount user = jdbcTemplateObject.queryForObject(sql, new Object[] { szt },
					new CommonAccountMapper());
			return user;
		} catch(Exception e) {
			System.out.println("Errore nel recupero dell'utente");
		}
		return null;
	}

	@Override
	public List<Account> getAllAdmin() {
		String sql = "SELECT * FROM adminaccounts";
		List<Account> adminaccounts = jdbcTemplateObject.query(sql, new AccountMapper());
		return adminaccounts;
	}

	public Account getAccountById(int id) {
		String sql = "SELECT * FROM accounts WHERE _id = ?";
		Account user = jdbcTemplateObject.queryForObject(sql, new Object[] { id },
				new AccountMapper());
		return user;
	}

	private void add(Account acc) throws DataAccessException {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(acc.getPasswordHash());
		String sql = "INSERT INTO accounts (emailAddress,passwordHash,role) VALUES (?,?,?)";
		jdbcTemplateObject.update(sql, acc.getEmailAddress(), hashedPassword, acc.getRole());
	}

	@Override
	public void addCommon(CommonAccount comAcc) {
		comAcc.setRole("ROLE_USER");
		add(comAcc);
		int id = this.getIdMax();
		String sql = "INSERT INTO commonaccounts(_id,firstName,lastName,height,weight,gender,birthDate)"
				+ "VALUES (?,?,?,?,?,?,?)";

		jdbcTemplateObject.update(sql, id, comAcc.getFirstName(), comAcc.getLastName(), comAcc
				.getHeight(), comAcc.getWeight(), comAcc.getGender(), comAcc.getBirthDate()
				.getTime());
	}

	@Override
	public void addAdmin(Account adminAcc) {
		adminAcc.setRole("ROLE_ADMIN");
		add(adminAcc);
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM accounts WHERE _id = ?";
		jdbcTemplateObject.update(sql, id);
	}

	@Override
	public Account getByUsername(String username) {
		String sql = "SELECT * FROM accounts WHERE emailAddress = ?";
		Account user = jdbcTemplateObject.queryForObject(sql, new Object[] { username },
				new AccountMapper());
		return user;
	}

	@Override
	public void updatePassword(int id, String pass) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(pass);
		String sql = "UPDATE accounts SET passwordHash=? WHERE _id=?";

		jdbcTemplateObject.update(sql, hashedPassword, id);
	}

	@Override
	public void updateUser(int currentId, CommonAccount comAcc) {
		comAcc.setRole("ROLE_USER");
		String sql = "UPDATE commonaccounts "
				+ "SET firstName=?,lastName=?,height=?,weight=?,gender=?,birthDate=? "
				+ "WHERE _id=?";

		jdbcTemplateObject.update(sql, comAcc.getFirstName(), comAcc.getLastName(), comAcc
				.getHeight(), comAcc.getWeight(), comAcc.getGender(), comAcc.getBirthDate()
				.getTime(), currentId);

	}

	public AccountLinkData generateNewAccLink() {
		int retry = 100;
		while(retry > 0) {
			AccountLinkData ac = AccountLinkData.generateRandAcc();
			// provo a salvarlo
			String sql = "INSERT INTO tokens(token, oneTimeCode) VALUES (?, ?);";
			try {
				jdbcTemplateObject.update(sql, ac.getConnectionToken().toString(),
						ac.getOneTimeCode());
				return ac;
			} catch(Exception e) {
				System.out.println("Code already present.. retry");
				retry--;
			}
		}
		// se dopo 100 tentativi nn lo trovo esco con null
		return null;
	}

	@Override
	public boolean checkCode(int code) {
		String sql = "SELECT oneTimeCode FROM tokens WHERE oneTimeCode=?;";
		try {
			jdbcTemplateObject.queryForObject(sql, new Object[] { code }, Integer.class);
			return true;
		} catch(Exception e) {
			return false;
		}
	}

	@Override
	public void updateToken(int code, int currentId) {
		String sql = "UPDATE tokens SET oneTimeCode = null, account_id=? WHERE oneTimeCode=?";
		jdbcTemplateObject.update(sql, currentId, code);
	}

	@Override
	public String getToken(int currentId) {
		String sql = "SELECT token FROM tokens WHERE account_id=?;";
		try {
			String res = jdbcTemplateObject.queryForObject(sql, new Object[] { currentId },
					String.class);
			return res;
		} catch(Exception e) {
			return null;
		}
	}

	@Override
	public void deleteToken(String token) {
		String sql = "DELETE FROM tokens WHERE token=?;";
		jdbcTemplateObject.update(sql, token);
	}
	
	@Override
	public int getNewSyncNum(int currentId) {
		String sql = "CALL SYNCINC(?);";
		Integer ires = jdbcTemplateObject.queryForObject(sql, new Object[] { currentId },
				Integer.class);
		return ires.intValue();
	}
}
