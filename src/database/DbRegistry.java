package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DbRegistry {
	
	private static ThreadLocal<Connection> connectionThread = new ThreadLocal<Connection>();
	
	public static void newConnection() {
		String database = "a_nunez_zegarra";
		String user ="a_nunez_zegarra";
		String password ="luntruda";
		Connection connection;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/" + database +
					"?user="+ user+"&password="+password+"&characterEncoding=UTF-8&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database + "?"
//                    +"user="+user + "&password=" + password);
            
            connectionThread.set(connection);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {
		return connectionThread.get();
	}

	public static void closeConnection(){
		try {
			connectionThread.get().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
