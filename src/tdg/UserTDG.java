package tdg;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;

public abstract class UserTDG {
	public static void insert(long id, int version, String username, String password) throws SQLException {
		String query = "INSERT INTO user (id,version,username,password) VALUES (?,?,?,?);";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1, id);
		ps.setInt(2, version);
		ps.setString(3, username);
		ps.setString(4, password);
		ps.executeUpdate();
	}
	
//	public static ResultSet find(long id) throws SQLException {
//		String query = "SELECT * FROM user WHERE id =?";
//		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
//		ps.setString(1, Long.toString(id));
//		return ps.executeQuery();
//	}
//	
//	public static ResultSet find(String username) throws SQLException {
//		String query = "SELECT * FROM user WHERE username =?";
//		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
//		ps.setString(1, username);
//		return ps.executeQuery();
//	}
//	
//	public static ResultSet findAll() throws SQLException {
//		String query = "SELECT * FROM user";
//		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
//		return ps.executeQuery();
//	}
}
