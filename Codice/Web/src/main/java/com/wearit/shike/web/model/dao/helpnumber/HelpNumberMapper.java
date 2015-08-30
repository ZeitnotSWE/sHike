package com.wearit.shike.web.model.dao.helpnumber;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.wearit.shike.web.model.user.HelpNumber;

class HelpNumberMapper implements RowMapper<HelpNumber> {

	@Override
	public HelpNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
		HelpNumber hlp = new HelpNumber();
		hlp.setNumber(rs.getString("number"));
		hlp.setAccount_id(rs.getInt("account_id"));
		hlp.setDescription(rs.getString("description"));
		return hlp;
	}
}