package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbRegistry {
	
	private Connection connection;
	private String database = "pokemon_db";
	private String user ="root";
	private String password ="a;sldkfj";
	
	public DbRegistry(){
		
		try {
//			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			
//			this.connection = DriverManager.getConnection(
//					"jdbc:mysql://localhost/" + this.database +
//					"?user="+ this.user+"&password="+this.password+"&characterEncoding=UTF-8&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true");
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.database + "?"
                    +"user="+this.user + "&password=" + this.password);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void closeConnection(){
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
