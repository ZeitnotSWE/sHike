package com.wearit.shike.web.model.dao.poi;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.wearit.shike.web.model.session.track.Poi;

public class PoiMapper implements RowMapper<Poi> {

	@Override
	public Poi mapRow(ResultSet rs, int rowNum) throws SQLException {
		Poi poi = new Poi();
		poi.setAuthor_id(rs.getInt("author_id"));
		poi.set_id(rs.getInt("_id"));
		poi.setName(rs.getString("name"));
		poi.setType(rs.getInt("type_id"));
		poi.setLatitude(rs.getDouble("latitude"));
		poi.setLongitude(rs.getDouble("longitude"));
		poi.setAltitude(rs.getDouble("altitude"));
		return poi;
	}
}
