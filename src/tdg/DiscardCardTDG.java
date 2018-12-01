package tdg;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DbRegistry;

public abstract class DiscardCardTDG {
	public static void insert(long id, int version, long gameId, long playerId, long cardId) throws SQLException {
		String query = "INSERT INTO discard_card (id, version, game_id, player_id, card_id) VALUES (?,?,?,?,?);";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1, id);
		ps.setInt(2, version);
		ps.setLong(3, gameId);
		ps.setLong(4, playerId);
		ps.setLong(5, cardId);
		ps.executeUpdate();
	}
}
