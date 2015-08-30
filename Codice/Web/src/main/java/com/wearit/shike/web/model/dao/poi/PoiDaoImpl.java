package com.wearit.shike.web.model.dao.poi;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import com.wearit.shike.web.model.session.track.Poi;

public class PoiDaoImpl implements PoiDao {

	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int getIdNext() {
		String sqlAcc = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES "
				+ "WHERE TABLE_NAME = 'pois'";
		return (Integer) jdbcTemplateObject.queryForObject(sqlAcc, Integer.class);
	}

	@Override
	public List<Poi> getAll() {
		return this.getAll(0);
	}

	@Override
	public List<Poi> getAll(int limit) {
		String sql = "SELECT * FROM pois";
		if(limit > 0)
			sql += " ORDER BY _id DESC LIMIT " + limit;
		List<Poi> poi = jdbcTemplateObject.query(sql, new PoiMapper());
		return poi;
	}

	@Override
	public Poi get(int id) {
		String sql = "SELECT * FROM pois WHERE _id = ?";
		Poi poi = jdbcTemplateObject.queryForObject(sql, new Object[] { id }, new PoiMapper());
		return poi;
	}

	@Override
	public void add(Poi poi) {
		String sql = "INSERT INTO pois(author_id, name, type_id, latitude,"
				+ "longitude,altitude) values (?,?,?,?,?,?)";
		jdbcTemplateObject.update(sql, poi.getAuthor_id(), poi.getName(), poi.getType().getId(),
				poi.getLatitude(), poi.getLongitude(), poi.getAltitude());
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM pois WHERE _id = ?";
		jdbcTemplateObject.update(sql, id);
	}

	@Override
	public void update(int id, Poi poi) {
		String sql = "UPDATE pois SET name = ?, type_id = ?, latitude = ?, longitude = ?, altitude = ?  WHERE _id = ?";
		jdbcTemplateObject.update(sql, poi.getName(), poi.getType().getId(), poi.getLatitude(),
				poi.getLongitude(), poi.getAltitude(), id);
	}

}
