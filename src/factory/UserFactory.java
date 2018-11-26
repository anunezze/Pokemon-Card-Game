package factory;

import pojo.User;

public abstract class UserFactory {
	
	public static User createNew(long id, int version, String username, String password) {
		User u = new User(id,version,username,password);
		u.markNew();
		return u;
	}
	
	public static User createClean(long id, int version, String username, String password) {
		User u = new User(id,version,username,password);
		u.markClean();
		return u;
	}

}
