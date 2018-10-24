package rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DbRegistry;

public class UserRDG {
	
	private int id;
	private String username;
	private String password;
	
	public UserRDG(int id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}
	
	public static UserRDG find(String username){
		UserRDG user = null;
		return user;
	}
	
	public void insert() throws SQLException{
		Connection connection = new DbRegistry().getConnection();
		String query = "INSERT INTO user (username,password) VALUES (?,?);";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, this.username);
		ps.setString(2, this.password);
		ps.executeUpdate();
		ps.close();
		connection.close();
	}
}
