package tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;

public abstract class ChallengeTDG {
	public static void insert(long id, int version, long challengerId, long challengeeId, long challengerDeck, int status) throws SQLException {
		String query = "INSERT INTO challenge (id,version,challenger,challengee,challenger_deck,status) VALUES (?,?,?,?,?,?);";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1, id);
		ps.setInt(2, version);
		ps.setLong(3, challengerId);
		ps.setLong(4, challengeeId);
		ps.setLong(5, challengerDeck);
		ps.setInt(6, status);
		ps.executeUpdate();
	}
	
	public static ResultSet find(long id) throws SQLException {		
		Connection connection = DbRegistry.getConnection();
		String query = "SELECT * FROM challenge WHERE id =?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setLong(1, id);
		return ps.executeQuery();
	}
	
	public static int update(long id, int version, long challengerId, long challengeeId, long challengerDeck, int status) throws SQLException {
		String query = "UPDATE challenge SET version=?, challenger=?,challengee=?,challenger_deck=?,status=? WHERE id=? AND version=? ";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setInt(1, version + 1);
		ps.setLong(2, challengerId);
		ps.setLong(3, challengeeId);
		ps.setLong(4, challengerDeck);
		ps.setInt(5, status);
		ps.setLong(6, id);
		ps.setInt(7, version);
		return ps.executeUpdate();
	}
	
	public static ResultSet findAll() throws SQLException {
		Connection connection = DbRegistry.getConnection();
		String query = "SELECT * FROM challenge";
		PreparedStatement ps = connection.prepareStatement(query);
		return ps.executeQuery();
	}
}
