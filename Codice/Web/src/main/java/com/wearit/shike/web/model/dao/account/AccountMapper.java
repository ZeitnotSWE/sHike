package com.wearit.shike.web.model.dao.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.wearit.shike.web.model.user.Account;

class AccountMapper implements RowMapper<Account> {

	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		String role;
		Account acc = new Account();
		acc.set_id(rs.getInt("_id"));
		acc.setEmailAddress(rs.getString("emailAddress"));
		acc.setPasswordHash(rs.getString("passwordHash"));
		try {
			role = rs.getString("role");
		} catch(Exception ex) {
			// Se entra qui vuol dire che il mapper è stato usato nella vista
			// 'adminaccounts' dove non è presente il campo ruolo
			role = "ROLE_ADMIN";
		}
		acc.setRole(role);
		return acc;
	}

}
