package finder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;

public abstract class BenchFinder {
	public static ResultSet findById(long id) throws SQLException {
		String query = "SELECT * FROM bench WHERE id =?;";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setString(1, Long.toString(id));
		return ps.executeQuery();
	}
	
	public static ResultSet findAllByHandId(long handId) throws SQLException {
		String query = "SELECT * FROM bench WHERE hand_id =?;";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setString(1, Long.toString(handId));
		return ps.executeQuery();
	}
}
