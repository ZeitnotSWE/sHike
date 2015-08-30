package com.wearit.shike.web.model.dao.recordtrack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import com.wearit.shike.web.model.session.performance.Performance;
import com.wearit.shike.web.model.session.performance.Stats;
import com.wearit.shike.web.model.session.track.Location;
import com.wearit.shike.web.model.session.track.RecordedTrack;

public class RecordedTrackDaoImpl implements RecordedTrackDao {
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public RecordedTrack getById(int _id, int account_id, int syncNumber) {
		String sql = "SELECT * FROM recordedtracks WHERE _id = ? "
				+ "AND account_id = ? AND syncNumber = ?";
		RecordedTrack rt = jdbcTemplateObject.queryForObject(sql, new Object[] { _id, account_id,
				syncNumber }, new RecordedTrackMapper());
		return rt;
	}

	@Override
	public List<RecordedTrack> getAll(int account_id) {
		String sql = "SELECT * FROM recordedtracks WHERE account_id = ?";
		List<RecordedTrack> tracks = jdbcTemplateObject.query(sql, new Object[] { account_id },
				new RecordedTrackMapper());
		return tracks;
	}

	@Override
	public void add(RecordedTrack track) {
		String sql = "INSERT INTO recordedtracks(_id,account_id,syncNumber,virtual_id,"
				+ "creationDate,length,distance,heightDiff,time,maxSpeed,steps)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplateObject.update(sql, track.get_id(), track.getAuthor_id(), track.getSyncNumber(),
				track.getVirtualId(), track.getCreationDate().getTime(), track.getLengthCalc(), 0, 0,
				0, 0, 0);
	}

	@Override
	public void delete(int _id, int account_id, int syncNumber) {
		String sql = "DELETE FROM recordedtracks WHERE _id = ? AND account_id = ? AND syncNumber = ?";
		jdbcTemplateObject.update(sql, _id, account_id, syncNumber);
	}

	@Override
	public List<Location> getAllPoints(int _id, int account_id, int syncNumber) {
		String sql = "SELECT * FROM recordedtracklocations WHERE track_id = ? AND account_id = ? AND syncNumber = ?";
		List<Location> trackLoc = jdbcTemplateObject.query(sql, new Object[] { _id, account_id,
				syncNumber }, new RecordedTrackLocationMapper());
		return trackLoc;
	}

	@Override
	public Location calculateCenter(int _id, int account_id, int syncNumber) {
		String sql = "SELECT ((MAX(latitude) + MIN(latitude))/2) as CenterLat, ((MAX(longitude) + "
				+ "MIN(longitude))/2) as CenterLon  FROM recordedtracklocations WHERE track_id = ? AND account_id = ? AND syncNumber = ?";
		try {
			Location loc = jdbcTemplateObject.queryForObject(sql, new Object[] { _id, account_id,
					syncNumber }, new RowMapper<Location>() {
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
	public void updateLength(double length, RecordedTrack track) {
		String SQL = "UPDATE recordedtracks SET length=? WHERE _id=? AND syncNumber=? AND account_id=?;";
		jdbcTemplateObject.update(SQL, length, track.get_id(), track.getSyncNumber(),
				track.getAuthor_id());
	}

	@Override
	public void updateHeightDiff(int heightDiff, RecordedTrack track) {
		String SQL = "UPDATE recordedtracks SET heightDiff=? WHERE _id=? AND syncNumber=? AND account_id=?;";
		jdbcTemplateObject.update(SQL, heightDiff, track.get_id(), track.getSyncNumber(),
				track.getAuthor_id());
	}

	@Override
	public Performance getPerformanceById(int _id, int account_id, int syncNumber) {
		String sql = "SELECT *, COUNT(0) AS counter FROM recordedtracks WHERE _id = ? AND account_id = ? AND syncNumber = ?";
		return jdbcTemplateObject.queryForObject(sql, new Object[] { _id, account_id, syncNumber },
				new PerformanceMapper());
	}

	@Override
	public Stats getStats(int account_id) {
		String sql = "SELECT * FROM stats WHERE account_id = ?";
		try {
			return jdbcTemplateObject.queryForObject(sql, new Object[] { account_id },
					new StatsMapper());
		} catch(Exception ex) {
			return null;
		}

	}

	@Transactional
	public void addPerformance(Performance p, int account_id, int syncNumber) throws SQLException {
		String sql = "INSERT INTO recordedtracks(_id,account_id,syncNumber,virtual_id,"
				+ "creationDate,length,distance,heightDiff,time,maxSpeed,steps) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		Connection dbconn = null;
		PreparedStatement ps = null;
		try {
			dbconn = jdbcTemplateObject.getDataSource().getConnection();
			dbconn.setAutoCommit(false);

			ps = dbconn.prepareStatement(sql);
			ps.setInt(1, p.getRecordedTrack().get_id());
			ps.setInt(2, account_id);
			ps.setInt(3, syncNumber);
			if(p.getRecordedTrack().getVirtualId() > 0)
				ps.setInt(4, p.getRecordedTrack().getVirtualId());
			else
				ps.setNull(4, java.sql.Types.INTEGER);
			ps.setLong(5, p.getRecordedTrack().getCreationDate().getTime());
			ps.setDouble(6, p.getRecordedTrack().getLengthCalc());
			ps.setDouble(7, p.getDistance());
			ps.setInt(8, p.getHeightDiff());
			ps.setLong(9, p.getTime());
			ps.setDouble(10, p.getMaxSpeed());
			ps.setInt(11, p.getSteps());
			ps.executeUpdate();
			saveListPoints(p.getRecordedTrack().get_id(), account_id, syncNumber, p
					.getRecordedTrack().getPoints(), dbconn);

			dbconn.commit();
		} catch(Exception e) {
			if(dbconn != null) {
				dbconn.rollback();
			}
		} finally {
			if(ps != null) {
				ps.close();
			}
			dbconn.setAutoCommit(true);
		}
	}

	@Transactional
	private void saveListPoints(int track_id, int account_id, int syncNumber, List<Location> ll,
			Connection dbconn) throws SQLException {
		String sql = "INSERT INTO recordedtracklocations(track_id, account_id, syncNumber, locationOrder, latitude, longitude, altitude) "
				+ " VALUES (?,?,?,?,?,?,?);";
		PreparedStatement ps = null;
		try {
			ps = dbconn.prepareStatement(sql);
			int auxi = 1; // location order
			for(Location l : ll) {
				ps.setInt(1, track_id);
				ps.setInt(2, account_id);
				ps.setInt(3, syncNumber);
				ps.setInt(4, auxi++);
				ps.setDouble(5, l.getLatitude());
				ps.setDouble(6, l.getLongitude());
				ps.setDouble(7, l.getAltitude());
				ps.executeUpdate();
			}

		} catch(SQLException e) {
			System.out
					.println("RecordedTrackDaoImpl.saveListPoints(): Errore nel salvataggio punti");
			if(ps != null) {
				ps.close();
			}
			throw e;
		} finally {
			if(ps != null) {
				ps.close();
			}
		}

	}
}
