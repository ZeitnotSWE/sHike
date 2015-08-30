package com.wearit.shike.web.model.dao.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;
import com.wearit.shike.web.model.user.CommonAccount;

class CommonAccountMapper implements RowMapper<CommonAccount> {

	@Override
	public CommonAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
		CommonAccount acc = new CommonAccount();
		acc.set_id(rs.getInt("_id"));
		acc.setEmailAddress(rs.getString("emailAddress"));
		acc.setPasswordHash(rs.getString("passwordHash"));
		acc.setBirthDate(new Date(rs.getLong("birthDate")));
		acc.setFirstName(rs.getString("firstName"));
		acc.setGender(rs.getInt("gender"));
		acc.setHeight(rs.getInt("height"));
		acc.setLastName(rs.getString("lastName"));
		acc.setSyncCount(rs.getInt("syncCount"));
		acc.setWeight(rs.getFloat("weight"));
		return acc;
	}
}