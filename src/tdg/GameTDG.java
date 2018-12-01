package tdg;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;

public abstract class GameTDG {
	public static void insert(
			long id, 
			int version, 
			long player1,
			long player2, 
			long currentPlayer, 
			long deck1, 
			long deck2, 
			String p1Status, 
			String p2Status) throws SQLException {
		String query = "INSERT INTO game (id,version, player1,player2,current_player, deck1, deck2, p1_status, p2_status) VALUES (?,?,?,?,?,?,?,?,?);";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1, id);
		ps.setInt(2, version);
		ps.setLong(3, player1);
		ps.setLong(4, player2);
		ps.setLong(5, currentPlayer);
		ps.setLong(6, deck1);
		ps.setLong(7, deck2);
		ps.setString(8, p1Status);
		ps.setString(9, p2Status);
		ps.executeUpdate();
	}
	
	public static ResultSet findAll() throws SQLException {
		String query = "SELECT * from game;";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		return ps.executeQuery();
	}
	
	public static ResultSet find(long id) throws SQLException {
		String query = "SELECT * from game WHERE id = ?;";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1, id);
		return ps.executeQuery();
	}
	
	public static int update(long id, int version, long currentPlayer, String p1Status, String p2Status) throws SQLException {
		String query = "UPDATE game SET version=?, current_player=?, p1_status=?, p2_status=? WHERE id=? AND version=?;";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setInt(1, version + 1);
		ps.setLong(2, currentPlayer);
		ps.setString(3, p1Status);
		ps.setString(4, p2Status);
		ps.setLong(5, id);
		ps.setInt(6, version);
		return ps.executeUpdate();
	}
}
