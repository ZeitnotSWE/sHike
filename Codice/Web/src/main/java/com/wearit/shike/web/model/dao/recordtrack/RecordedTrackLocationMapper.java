package com.wearit.shike.web.model.dao.recordtrack;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.wearit.shike.web.model.dao.virtualtrack.VirtualTrackLocationMapper;
import com.wearit.shike.web.model.session.track.Location;

public class RecordedTrackLocationMapper extends VirtualTrackLocationMapper {

	@Override
	public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
		Location recordedTrackLoc = super.mapRow(rs, rowNum);
		recordedTrackLoc.setAccountId(rs.getInt("account_id"));
		recordedTrackLoc.setSyncNumber(rs.getInt("syncNumber"));
		return recordedTrackLoc;
	}

}
