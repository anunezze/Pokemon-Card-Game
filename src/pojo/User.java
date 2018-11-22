package pojo;

public class User extends DomainObject {
	private String username;
	private String password;
	
	public User(long id, int version, String username, String password) {
		super(id, version);
		this.username = username;
		this.password = password;
		this.markNew();
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
