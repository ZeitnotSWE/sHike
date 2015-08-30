package com.wearit.shike.web.model.dao.virtualtrack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;
import com.wearit.shike.web.model.session.track.VirtualTrack;

public class VirtualTrackMapper implements RowMapper<VirtualTrack> {

	@Override
	public VirtualTrack mapRow(ResultSet rs, int rowNum) throws SQLException {
		VirtualTrack virtualTrack = new VirtualTrack();
		virtualTrack.set_id(rs.getInt("_id"));
		virtualTrack.setAuthor_id(rs.getInt("author_id"));
		virtualTrack.setName(rs.getString("name"));
		virtualTrack.setLength(rs.getDouble("length"));
		virtualTrack.setCreationDate(new Date(rs.getLong("creationDate")));
		return virtualTrack;
	}

}