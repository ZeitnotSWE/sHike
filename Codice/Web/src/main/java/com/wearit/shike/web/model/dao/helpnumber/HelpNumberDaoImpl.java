package com.wearit.shike.web.model.dao.helpnumber;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.wearit.shike.web.model.user.HelpNumber;

public class HelpNumberDaoImpl implements HelpNumberDao {

	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public List<HelpNumber> getAll(int idUser) {
		String sql = "SELECT * FROM helpnumbers WHERE account_id = ?";
		List<HelpNumber> hns = jdbcTemplateObject.query(sql, new HelpNumberMapper(),
				new Object[] { idUser });
		return hns;
	}

	@Override
	public HelpNumber get(int idUser, String number) {
		String sql = "SELECT * FROM helpnumbers WHERE account_id = ? AND number = ?";
		HelpNumber hns = jdbcTemplateObject.queryForObject(sql, new HelpNumberMapper(), idUser,
				number);
		return hns;
	}

	@Override
	public void add(int idUser, HelpNumber number) throws DataAccessException {
		String sql = "INSERT INTO helpnumbers(account_id, number, description)"
				+ "VALUES (?, ?, ?);";
		jdbcTemplateObject.update(sql, idUser, number.getNumber(), number.getDescription());
	}

	@Override
	public void delete(int id, String number) {
		String sql = "DELETE FROM helpnumbers WHERE number = ? AND account_id = ?";
		jdbcTemplateObject.update(sql, number, id);
	}

	@Override
	public void update(int id, String number, HelpNumber newNumber) {
		String sql = "UPDATE helpnumbers SET number = ?, description = ? WHERE number = ? AND account_id = ?";
		jdbcTemplateObject.update(sql, newNumber.getNumber(), newNumber.getDescription(), number,
				id);
	}

}
