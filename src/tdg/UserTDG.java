package tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;
import rdg.UserRDG;

public abstract class UserTDG {
	public static void insert(long id, int version, String username, String password) throws SQLException {
		Connection connection = new DbRegistry().getConnection();
		String query = "INSERT INTO user (id,version,username,password) VALUES (?,?,?,?);";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setLong(1, id);
		ps.setInt(2, version);
		ps.setString(3, username);
		ps.setString(4, password);
		ps.executeUpdate();
		ps.close();
		connection.close();
	}
	
	public static ResultSet find(long id) throws SQLException {
		Connection connection = new DbRegistry().getConnection();
		String query = "SELECT * FROM user WHERE id =?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, Long.toString(id));
		ResultSet rs = ps.executeQuery();
		connection.close();
		return rs;
	}
	
	public static ResultSet find(String username) throws SQLException {
		Connection connection = new DbRegistry().getConnection();
		String query = "SELECT * FROM user WHERE username =?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
}
