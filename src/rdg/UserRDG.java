package rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import database.DbRegistry;

public class UserRDG {
	
	private long id;
	private String username;
	private String password;
	
	public UserRDG(long id, String username, String password) {
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

	public long getId() {
		return id;
	}
	
	public static UserRDG find(String username) throws SQLException{
		UserRDG result = null;
		Connection connection = new DbRegistry().getConnection();
		String query = "SELECT * FROM user WHERE username =?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			result = new UserRDG(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
		}
		ps.close();
		connection.close();
		
		return result;
	}
	
	public static UserRDG find(long id) throws SQLException{
		UserRDG result = null;
		Connection connection = new DbRegistry().getConnection();
		String query = "SELECT * FROM user WHERE id =?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, Long.toString(id));
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			result = new UserRDG(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
		}
		ps.close();
		connection.close();
		
		return result;
	}
	public static List<UserRDG> findAll() throws SQLException{
		List<UserRDG> result = new ArrayList<UserRDG>();
		Connection connection = new DbRegistry().getConnection();
		String query = "SELECT * FROM user";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(new UserRDG(rs.getInt("id"), rs.getString("username"), rs.getString("password")));
		}
		
		return result;
	}
	
	public void insert() throws SQLException{
		Connection connection = new DbRegistry().getConnection();
		String query = "INSERT INTO user (username,password) VALUES (?,?);";
		PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, this.username);
		ps.setString(2, this.password);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		if(rs.next()){
			this.id = rs.getInt(1);
		}
		ps.close();
		connection.close();
	}
}
