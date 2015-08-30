package com.wearit.shike.web.model.dao.recordtrack;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.wearit.shike.web.model.session.performance.Performance;

public class PerformanceMapper implements RowMapper<Performance> {

	@Override
	public Performance mapRow(ResultSet rs, int rowNum) throws SQLException {
		Performance perf = new Performance();
		perf.setTrack_id(rs.getInt("_id"));
		perf.setAccount_id(rs.getInt("account_id"));
		perf.setSyncNumber(rs.getInt("syncNumber"));
		perf.setDistance(rs.getDouble("distance"));
		perf.setTime(rs.getLong("time"));
		perf.setCounter(rs.getInt("counter"));
		perf.setMaxSpeed(rs.getDouble("maxSpeed"));
		perf.setHeightDiff(rs.getInt("heightDiff"));
		perf.setSteps(rs.getInt("steps"));
		return perf;
	}

}
