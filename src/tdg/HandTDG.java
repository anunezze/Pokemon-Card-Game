package tdg;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;
import util.IdGenerator;

public abstract class HandTDG {
	public static void insert(
			long id,
			int version, 
			long gameId, 
			long playerId, 
			int handSize, 
			int deckSize, 
			int discardSize, 
			long benchId,
			int benchSize) throws SQLException {
		String query = "INSERT INTO hand (id,version,game_id,player_id, hand_size, deck_size, discard_size, bench_id,bench_size) VALUES (?,?,?,?,?,?,?,?,?);";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1,IdGenerator.getInstance().createID());
		ps.setInt(2, version);
		ps.setLong(3, gameId);
		ps.setLong(4, playerId);
		ps.setInt(5, handSize);
		ps.setInt(6, deckSize);
		ps.setInt(7, discardSize);
		ps.setLong(8, benchId);
		ps.setInt(9, benchSize);
		ps.executeUpdate();
	}
	
	public static ResultSet find(long gameId, long playerId) throws SQLException {
		String query = "SELECT * FROM hand WHERE game_id = ? AND player_id=?;";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1, gameId);
		ps.setLong(2, playerId);
		return ps.executeQuery();
	}
	
	public static int update(long id, int version, int handSize, int deckSize, int discardSize, int benchSize) throws SQLException {
		String query = "UPDATE hand SET version=?, hand_size=?,deck_size=?, discard_size=?, bench_size =? WHERE id=? AND version=?;";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setInt(1, version + 1);
		ps.setInt(2, handSize);
		ps.setInt(3, deckSize);
		ps.setInt(4, discardSize);
		ps.setInt(5, benchSize);
		ps.setLong(6, id);
		ps.setInt(7, version);
		return ps.executeUpdate();
	}
}
