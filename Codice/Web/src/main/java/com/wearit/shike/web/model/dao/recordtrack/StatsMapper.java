package com.wearit.shike.web.model.dao.recordtrack;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.wearit.shike.web.model.session.performance.Stats;

public class StatsMapper implements RowMapper<Stats> {

	@Override
	public Stats mapRow(ResultSet rs, int rowNum) throws SQLException {
		Stats stats = new Stats();
		stats.setExpLevel(rs.getDouble("expLevel"));
		stats.setTotalDistance(rs.getDouble("totalDistance"));
		stats.setTotalHeightDiff(rs.getInt("totalHeightDiff"));
		stats.setMaxSpeed(rs.getDouble("maxSpeed"));
		stats.setTotalSteps(rs.getInt("totalSteps"));
		stats.setNumberActivity(rs.getInt("numberActivity"));
		stats.setTotalTime(rs.getLong("totalTime"));
		return stats;
	}

}
