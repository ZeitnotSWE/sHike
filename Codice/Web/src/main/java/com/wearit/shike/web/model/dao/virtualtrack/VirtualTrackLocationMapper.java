package com.wearit.shike.web.model.dao.virtualtrack;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.wearit.shike.web.model.session.track.Location;

public class VirtualTrackLocationMapper implements RowMapper<Location> {

	@Override
	public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
		Location virtualTrackLoc = new Location();
		virtualTrackLoc.setTrackId(rs.getInt("track_id"));
		virtualTrackLoc.setLocationOrder(rs.getInt("locationOrder"));
		virtualTrackLoc.setLatitude(rs.getDouble("latitude"));
		virtualTrackLoc.setLongitude(rs.getDouble("longitude"));
		virtualTrackLoc.setAltitude(rs.getDouble("altitude"));
		return virtualTrackLoc;
	}
}