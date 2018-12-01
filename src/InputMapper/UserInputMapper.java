package InputMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.IdentityMap;
import factory.UserFactory;
import finder.UserFinder;
import pojo.User;
import tdg.UserTDG;

public class UserInputMapper {
	public static User find(long id) throws SQLException {
		User u = null;
		if(IdentityMap.contains(id)) {
			return (User)IdentityMap.find(id);
		}
		ResultSet rs = UserFinder.find(id);
		if(rs.next()) {
			u = new User(rs.getLong("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password"));
		}
		return u;
	}
	
	public static User find(String username) throws SQLException {
		User u = null;
		ResultSet rs = UserFinder.find(username);
		if(rs.next()) {
			u = UserFactory.createClean(rs.getLong("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password"));
		}
		return u;
	}
	
	public static List<User> findAll() throws SQLException{
		List<User> list = new ArrayList<User>();
		ResultSet rs = UserFinder.findAll();
		while(rs.next()) {
			list.add(UserFactory.createClean(rs.getLong("id"), rs.getInt("version"), rs.getString("username"), rs.getString("password")));
		}
		return list;
	}
}
