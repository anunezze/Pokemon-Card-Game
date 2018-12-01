package finder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;

public abstract class ChallengeFinder {
	public static ResultSet find(long id) throws SQLException {		
		Connection connection = DbRegistry.getConnection();
		String query = "SELECT * FROM challenge WHERE id =?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setLong(1, id);
		return ps.executeQuery();
	}
	
	public static ResultSet findAll() throws SQLException {
		Connection connection = DbRegistry.getConnection();
		String query = "SELECT * FROM challenge";
		PreparedStatement ps = connection.prepareStatement(query);
		return ps.executeQuery();
	}
}
