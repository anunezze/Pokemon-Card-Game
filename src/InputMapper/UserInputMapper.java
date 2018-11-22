package InputMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import factory.UserFactory;
import pojo.User;
import tdg.UserTDG;

public class UserInputMapper {
	public static User find(long id) throws SQLException {
		User u = null;
		ResultSet rs = UserTDG.find(id);
		if(rs.next()) {
			u = new User(rs.getLong("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password"));
		}
		return u;
	}
	public static User find(String username) throws SQLException {
		User u = null;
		ResultSet rs = UserTDG.find(username);
		if(rs.next()) {
			u = UserFactory.createClean(rs.getLong("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password"));
		}
		return u;
	}
}
