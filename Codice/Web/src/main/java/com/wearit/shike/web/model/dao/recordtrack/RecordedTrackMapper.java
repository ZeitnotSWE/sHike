package com.wearit.shike.web.model.dao.recordtrack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;
import com.wearit.shike.web.model.session.track.RecordedTrack;

public class RecordedTrackMapper implements RowMapper<RecordedTrack> {

	@Override
	public RecordedTrack mapRow(ResultSet rs, int rowNum) throws SQLException {
		RecordedTrack recordedTrack = new RecordedTrack();
		recordedTrack.set_id(rs.getInt("_id"));
		recordedTrack.setAuthor_id(rs.getInt("account_id"));
		recordedTrack.setSyncNumber(rs.getInt("syncNumber"));
		recordedTrack.setVirtualId(rs.getInt("virtual_id"));
		recordedTrack.setCreationDate(new Date(rs.getLong("creationDate")));
		recordedTrack.setLength(rs.getDouble("length"));
		recordedTrack.setHeightDiff(rs.getInt("heightDiff"));
		return recordedTrack;
	}

}
