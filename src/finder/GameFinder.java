package finder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;

public abstract class GameFinder {
	public static ResultSet findAll() throws SQLException {
		String query = "SELECT * from game;";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		return ps.executeQuery();
	}
	
	public static ResultSet find(long id) throws SQLException {
		String query = "SELECT * from game WHERE id = ?;";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1, id);
		return ps.executeQuery();
	}
}
