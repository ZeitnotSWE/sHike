package com.wearit.shike.web.model.dao.virtualtrack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.wearit.shike.web.model.session.track.Location;
import com.wearit.shike.web.model.session.track.VirtualTrack;

public class VirtualTrackDaoImpl implements VirtualTrackDao {
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int getIdMax() {
		String sqlAcc = "SELECT IFNULL(MAX(_id),0) AS _id FROM virtualtracks";
		return (Integer) jdbcTemplateObject.queryForObject(sqlAcc, Integer.class);
	}

	@Override
	public int getIdNext() {
		String sqlAcc = "SELECT AUTO_INCREMENT FROM  INFORMATION_SCHEMA.TABLES "
				+ "WHERE TABLE_NAME = 'virtualtracks'";
		return (Integer) jdbcTemplateObject.queryForObject(sqlAcc, Integer.class);
	}

	@Override
	public VirtualTrack getById(int _id) {
		String sql = "SELECT * FROM virtualtracks WHERE _id = ?";
		return jdbcTemplateObject.queryForObject(sql, new Object[] { _id },
				new VirtualTrackMapper());
	}

	@Override
	public List<VirtualTrack> getAll(int limit) {
		String sql = "SELECT * FROM virtualtracks";
		if(limit > 0)
			sql += " ORDER BY _id DESC LIMIT " + limit;
		List<VirtualTrack> tracks = jdbcTemplateObject.query(sql, new VirtualTrackMapper());
		return tracks;
	}

	@Override
	public void add(VirtualTrack track) {
		String sql = "INSERT INTO virtualtracks(author_id, name, creationDate, length, heightDiff) "
				+ "values (?,?,?,?,?)";

		jdbcTemplateObject.update(sql, track.getAuthor_id(), track.getName(), track
				.getCreationDate().getTime(), track.getLength(), track.getHeightDiff());
	}

	@Override
	public void update(VirtualTrack track) {
		String sql = "UPDATE virtualtracks SET name = ? WHERE _id = ? AND author_id = ?";
		jdbcTemplateObject.update(sql, track.getName(), track.get_id(), track.getAuthor_id());
	}

	@Override
	public void delete(int _id) {
		String sql = "DELETE FROM virtualtracks WHERE _id = ?";
		jdbcTemplateObject.update(sql, _id);
	}

	@Override
	public List<Location> getAllPoints(int _id) {
		String sql = "SELECT * FROM virtualtracklocations WHERE track_id = ?";
		List<Location> trackLoc = jdbcTemplateObject.query(sql, new Object[] { _id },
				new VirtualTrackLocationMapper());
		return trackLoc;
	}

	@Override
	public void addAllPoints(final int track_id, final List<Location> listOfPoints) {
		String sql = "INSERT INTO virtualtracklocations (track_id, locationOrder, latitude, "
				+ "longitude, altitude) VALUES (?, ?, ?, ?, ?);";

		jdbcTemplateObject.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i) throws SQLException {
				Location point = listOfPoints.get(i);
				ps.setInt(1, track_id);
				ps.setInt(2, point.getLocationOrder());
				ps.setDouble(3, point.getLatitude());
				ps.setDouble(4, point.getLongitude());
				ps.setDouble(5, point.getAltitude());
			}

			@Override
			public int getBatchSize() {
				return listOfPoints.size();
			}
		});
	}

	@Override
	public boolean isToSync(int account_id, int track_id) {
		String sql = "SELECT track_id  FROM tracktosync WHERE track_id = ? AND account_id = ?;";
		try {
			int id = jdbcTemplateObject.queryForObject(sql, new Object[] { track_id, account_id },
					Integer.class);
			return id > 0;
		} catch(EmptyResultDataAccessException e) {
			return false;
		}
	}

	@Override
	public Location calculateCenter(int _id) {
		String sql = "SELECT ((MAX(latitude) + MIN(latitude))/2) as CenterLat, ((MAX(longitude) + "
				+ "MIN(longitude))/2) as CenterLon  FROM virtualtracklocations WHERE track_id = ?;";
		try {
			Location loc = jdbcTemplateObject.queryForObject(sql, new Object[] { _id },
					new RowMapper<Location>() {
						public Location mapRow(ResultSet rs, int rowNum) throws SQLException {

							Location loc = new Location();
							loc.setLatitude(rs.getDouble("CenterLat"));
							loc.setLongitude(rs.getDouble("CenterLon"));

							return loc;
						}
					});
			return loc;
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void changeSync(int account_id, int track_id) {
		if(this.isToSync(account_id, track_id)) {
			String sql = "DELETE FROM tracktosync WHERE account_id=? AND track_id=?;";
			jdbcTemplateObject.update(sql, account_id, track_id);
		} else {
			String sql = "INSERT INTO tracktosync (account_id, track_id) VALUES (?, ?);";
			jdbcTemplateObject.update(sql, account_id, track_id);
		}
	}

	@Override
	public List<VirtualTrack> getAllToSync(int account_id) {
		String sql = "SELECT vt.* "
				+ " FROM virtualtracks vt INNER JOIN tracktosync ts ON (ts.track_id = vt._id) "
				+ " WHERE ts.account_id = ?";
		List<VirtualTrack> tracks = jdbcTemplateObject.query(sql, new Object[] { account_id },
				new VirtualTrackMapper());
		return tracks;
	}
}
