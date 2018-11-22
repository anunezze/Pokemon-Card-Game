package OutputMapper;

import java.sql.SQLException;

import pojo.User;
import tdg.UserTDG;

public class UserOutputMapper{

	public static void insert(User e) throws SQLException {
		UserTDG.insert(e.getId(), e.getVersion(), e.getUsername(), e.getPassword());
	}
}
